package com.wyd.dispatch;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.INetData;
public class TimeControlProcessor implements ControlProcessor, Runnable {
	public static final short ADMIN_ADDIP = 243;
	public static final short FINITERELOAD = 195;
	private ChannelService channelService;
	private Dispatcher dispatcher;
	private IpdService ipdService;
	// private TrustIpService trustIpService;
	private ConfigMenger configuration;
	private static final Logger log = Logger.getLogger(TimeControlProcessor.class);
	private BlockingQueue<INetData> datas;

	public TimeControlProcessor() {
		datas = new LinkedBlockingQueue<INetData>();
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setName("Control");
		thread.start();
		log.info("TimeControlProcessor Control start.");
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	// public void setTrustIpService(TrustIpService trustIpService) {
	// this.trustIpService = trustIpService;
	// }
	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setIpdService(IpdService ipdService) {
		this.ipdService = ipdService;
	}

	public void process(INetData data) {
		try {
			datas.put(data);
		} catch (InterruptedException ex1) {
			log.info("TimeControlProcessor process INetData Exception.");
		}
	}

	private void processServerMsg(INetData data) {
		byte type = data.getSubType();
		try {
			switch (type) {
				case Protocol.SERVER_NotifyMaintance : // '\037'
														// Protocol.SERVER_NotifyMaintance
					maintance(data);
					break;
				case Protocol.SERVER_NotifyMaxPlayer : // '\031'
														// Protocol.SERVER_NotifyMaxPlayer
					maxPlayer(data);
					break;
				case Protocol.SERVER_BroadCast : // '\027'
													// Protocol.SERVER_BroadCast
					broadcast(data);
					break;
				case Protocol.SERVER_ForceBroadCast : // '\028'
					forceBroadcast(data);
					break;
				case Protocol.SERVER_Kick : // '\029' Protocol.SERVER_Kick
					kick(data);
					break;
				case Protocol.SERVER_ShutDown : // '\030'
					shutdown();
					break;
				case Protocol.SERVER_UpdateVers : // '\091'
					updateVersion(data);
					break;
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}

	/**
	 * 设置服务器状态状态
	 * 
	 * @param data
	 */
	private void maintance(INetData data) {
		try {
			boolean maintance = data.readBoolean();
			if (ipdService != null)
				ipdService.connect(-1, -1, maintance);
			// configuration.setProperty("maintance",
			// Boolean.valueOf(maintance));
			log.info("maintance:" + maintance);
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	private void processChannelMsg(INetData data) {
		byte type = data.getSubType();
		try {
			switch (type) {
				case Protocol.CHAT_SyncChannels :
					syncChannel(data);
					break;
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}

	private void syncChannel(INetData data) throws Exception {
		int sessionId = data.readInt();
		IoSession session = dispatcher.getSession(sessionId);
		if (session != null) {
			String aChannels[] = data.readStrings();
			String rChannels[] = data.readStrings();
			for (int i = 0; i < aChannels.length; i++) {
				Channel channel = channelService.getAndCreate(aChannels[i]);
				if (channel != null)
					channel.join(session);
			}
			for (int i = 0; i < rChannels.length; i++) {
				Channel channel = channelService.getChannel(rChannels[i]);
				if (channel != null)
					channel.removeSession(session);
			}
		}
	}

	protected void process0(INetData data) {
		byte type = data.getType();
		try {
			switch (type) {
				case Protocol.MAIN_SERVER :
					processServerMsg(data);
					break;
				case Protocol.MAIN_CHAT :
					processChannelMsg(data);
					break;
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}

	public void run() {
		while (true) {
			try {
				// 检索并移除此队列datas的头部，如果此队列不存在任何元素，则一直等待。
				INetData data = (INetData) datas.take();
				process0(data);
			} catch (InterruptedException ex) {
				log.error(ex, ex);
			}
		}
	}

	// private void clearChannels(INetData data) throws Exception {
	// int sessionId = data.readInt();
	// IoSession session = dispatcher.getSession(sessionId);
	// if (session != null) channelService.clearChannels(session);
	// }
	private void shutdown() {
		dispatcher.shutdown();
	}

	/**
	 * 此方法调用<tt>SocketDispatcher</tt>中的<tt>broadcast</tt>方法
	 * 
	 * @see com.wyd.dispatch.SocketDispatcher
	 * @param data
	 * @throws Exception
	 */
	private void forceBroadcast(INetData data) throws Exception {
		byte bytes[] = data.readBytes();
		dispatcher.broadcast(IoBuffer.wrap(bytes));
	}

	/**
	 * 读取对应通道
	 * 
	 * @param data
	 * @throws Exception
	 */
	private void broadcast(INetData data) throws Exception {
		Channel channel = channelService.getChannel(data.readString());
		if (channel != null)
			channel.broadcast(IoBuffer.wrap(data.readBytes()));
	}

	/**
	 * 读取客户端返回的游戏人数,服务器状态
	 * 
	 * @param data
	 * @throws Exception
	 */
	private void maxPlayer(INetData data) throws Exception {
		int current = data.readInt();
		int maxPlayer = data.readInt();
		long c = data.readLong();
		log.info((new StringBuilder()).append("SyncTime[").append(System.currentTimeMillis() - c).append("] ONLINE[").append(current)
				.append("] MAX[").append(maxPlayer).append("]").toString());
		if (ipdService != null) {
			boolean m = configuration.getConfiguration().getBoolean("maintance", true);
			ipdService.connect(current, maxPlayer, m);
		}
	}

	/**
	 * 更新服务器版本
	 * 
	 * @param data
	 * @throws Exception
	 */
	private void updateVersion(INetData data) throws Exception {
		String area = data.readString();
		String machine = data.readString();
		data.readInt();
		String version = data.readString();
		String updateurl = data.readString();
		String remark = data.readString();
		String appraisal = data.readString();
		String group = data.readString();
		int serverId = data.readInt();
		if (ipdService != null) {
			ipdService.updateVersion(area, group, machine, version, updateurl, remark, appraisal, serverId);
		}
	}

	private void kick(INetData data) throws Exception {
		int sessionId = data.readInt();
		dispatcher.unRegisterClient(sessionId);
	}

	public void setConfiguration(ConfigMenger configuration) {
		this.configuration = configuration;
	}
}