package com.app.dispatch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.util.StringUtils;

import com.app.dispatch.vo.Player;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.data.account.Heartbeat;
import com.app.empire.protocol.data.system.ShakeHands;
import com.app.protocol.INetData;
import com.app.protocol.ProtocolManager;
import com.app.protocol.s2s.S2SData;
import com.app.protocol.s2s.S2SSegment;

import edu.emory.mathcs.backport.java.util.Arrays;

public class SocketDispatcher implements Dispatcher, Runnable {
	private static final String ATTRIBUTE_STRING = "SESSIONID";
	private static final Logger log = Logger.getLogger(SocketDispatcher.class);
	private AtomicInteger ids = new AtomicInteger(0);
	private ConcurrentHashMap<Integer, IoSession> sessions = new ConcurrentHashMap<Integer, IoSession>();// sessionID-->客户端iosession
	private ConcurrentHashMap<Integer, ClientInfo> allClientInfo = new ConcurrentHashMap<Integer, ClientInfo>();// playerId-->客户端

	private ChannelService channelService = null;
	private NioSocketAcceptor acceptor = null;
	private NioSocketConnector connector = null;
	/** worldServer Iosession */
	private IoSession serverSession = null;
	/** 允许加载的ip段 */
	private TrustIpService trustIpService = null;
	private Configuration configuration = null;
	private SyncService syncService = null;
	public static final String SERVERID = "serverid";
	public static final String SERVERNAME = "servername";
	public static final String SERVERPASSWORD = "serverpassword";
	private SocketAddress address;
	private boolean shutdown = false;
	private Heartbeat heartbeat = new Heartbeat();
	private static final String LOGINMARK_KEY = "ISLOGED";
	protected static final String CLIENTINFO_KEY = "CLIENTINFO";
	protected static final String PLAYERID_KEY = "PLAYERID";
	private static final boolean LOGINMARK_UNLOG = false;
	private static final boolean LOGINMARK_LOGED = true;

	public SocketDispatcher(Configuration configuration) {
		this.configuration = configuration;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setName("OnlinePrinter");
		thread.start();
	}
	/** 设置通道服务 */
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
	/** 设置允许加载的ip段 */
	public void setTrustIpService(TrustIpService trustIpService) {
		this.trustIpService = trustIpService;
		log.info("add trustIpService: " + this.trustIpService);
	}
	/** 设置同屏同步服务 */
	public void setSyncService(SyncService syncService) {
		this.syncService = syncService;
	}

	/** 转发数据至 worldServer */
	public void dispatchToServer(IoSession session, Object object) {
		// System.out.println("* 转发数据至 worldServer *");
		Integer id = (Integer) session.getAttribute(ATTRIBUTE_STRING);
		if (id != null) {
			IoBuffer buffer = (IoBuffer) object;
			byte type = buffer.get(19);
			byte subType = buffer.get(20);
			if (checkProtocol(session, type, subType)) {// 协议检查
				if (type == Protocol.MAIN_ACCOUNT) {
					if (subType == Protocol.ACCOUNT_Heartbeat) {// 回应客户端心跳协议
						IoBuffer byteBuffer = IoBuffer.wrap(ProtocolManager.makeSegment(heartbeat).getPacketByteArray());
						session.write(byteBuffer.duplicate());// 返回心跳协议
					} else if (subType == Protocol.ACCOUNT_Move) {// 玩家移动协议处理
						try {
							ClientInfo client = (ClientInfo) session.getAttribute(CLIENTINFO_KEY);
							Player player = client.getPlayer();

							INetData udata = new S2SData(Arrays.copyOfRange(buffer.array(), 18, buffer.array().length), 1, -1);
							int playerId = udata.readInt();
							byte direction = udata.readByte();// 方向1-12
							int toWidth = udata.readInt();// 所在宽度位置
							int toHigh = udata.readInt();// 所在高度位置
							player.setDirection(direction);
							player.setToWidth(toWidth);
							player.setToHigh(toHigh);
							this.syncService.broadcastingMove(client, toWidth, toHigh);

						} catch (IllegalAccessException e) {
							log.error(e.getMessage());
							e.printStackTrace();
						}
					} else if (subType == Protocol.ACCOUNT_ReportPlace) {// 客户端告知服务器位置
						try {
							ClientInfo client = (ClientInfo) session.getAttribute(CLIENTINFO_KEY);
							Player player = client.getPlayer();

							INetData udata = new S2SData(Arrays.copyOfRange(buffer.array(), 18, buffer.array().length), 1, -1);
							byte direction = udata.readByte();// 方向1-12
							int nowWidth = udata.readInt();// 所在宽度位置
							int nowHigh = udata.readInt();// 所在高度位置


							
							this.syncService.reportPlace(client, nowWidth, nowHigh);

							player.setDirection(direction);
							player.setWidth(nowWidth);
							player.setHigh(nowHigh);

						} catch (IllegalAccessException e) {
							log.error(e.getMessage());
							e.printStackTrace();
						}
					}
				} else if ((Boolean) session.getAttribute(LOGINMARK_KEY) || type == Protocol.MAIN_ACCOUNT
						|| type == Protocol.MAIN_ERRORCODE || type == Protocol.MAIN_SYSTEM) {// 判断用户是否已经登录或者为登录协议
					buffer.putInt(4, id.intValue());// sessionId
					this.serverSession.write(buffer.duplicate());// 发送worldServer
				} else {// 不是心跳，不是登录协议，并且用户未登录则断开socket连接
					log.info("用户未登录Kill Session LOGINMARK:" + session.getAttribute(LOGINMARK_KEY) + "---type:" + type + "---subtype:"
							+ subType);
					session.close(true);
				}
			}
		} else {
			session.close(true);
		}
	}

	/**
	 * 检查协议上行数量是否正常 心跳， 正常协议发送频率
	 * 
	 * @param session
	 * @param type
	 * @param subType
	 * @return true正常false异常
	 */
	public boolean checkProtocol(IoSession session, int type, int subType) {
		ClientInfo client = (ClientInfo) session.getAttribute(CLIENTINFO_KEY);
		long nowTime = System.currentTimeMillis();
		if (client != null) {
			if (type == Protocol.MAIN_ACCOUNT) {
				if (subType == Protocol.ACCOUNT_Heartbeat) {// 判断是否心跳
					if (nowTime - client.getHeartbeatTime() <= 10000) {
						client.addHeartbeatCount();
						if (client.getHeartbeatCount() > this.configuration.getInt("heartbeatCount")) {// 10秒钟内心跳数大于2则断开连接
							log.info("Warning SessionId [" + session.getId() + "] HeartbeatCount: + " + client.getHeartbeatCount());
							session.close(true);
							return false;
						}
					} else {
						client.setHeartbeatCount(0);
						client.setHeartbeatTime(nowTime);
					}
				} else if (subType == Protocol.ACCOUNT_Move) {// 移动协议
					if (nowTime - client.getMoveTime() <= 1000) {
						client.addMovecount();
						if (client.getMoveCount() > this.configuration.getInt("moveCount")) {
							return false;
						}
					} else {
						client.setMoveCount(0);
						client.setMoveTime(nowTime);
					}
				}
			} else {// 其他协议
				if (nowTime - client.getProtocolTime() <= 1000) {// 除战斗
					client.addProtocolCount();
					if (client.getProtocolCount() > this.configuration.getInt("protocolCount")) {// 1秒钟内协议大于15则断开连接
						log.info("Warning SessionId [" + session.getId() + "] ProtocolCount: + " + client.getProtocolCount());
						session.close(true);
						return false;
					}
				} else {
					client.setProtocolCount(0);
					client.setProtocolTime(nowTime);
				}
			}
		} else {
			session.close(true);
			return false;
		}
		return true;
	}

	public IoSession getSession(int sessionId) {
		return this.sessions.get(sessionId);
	}

	/** 发数据到 worldServer */
	public void sendControlSegment(S2SSegment seg) {
		try {
			seg.setSessionId(-1);
			this.serverSession.write(IoBuffer.wrap(seg.getPacketByteArray()));
		} catch (NullPointerException e) {
			log.info("this.serverSession is null.");
			if (this.serverSession == null) {
				this.connect();
			}
		}
	}

	/**
	 * 实现DispatchServer连接WorldServer，这时DispatchServer做为Client端
	 * 
	 * @param address
	 *            连接地址
	 * @param config
	 *            配置信息
	 * @return ConnectFuture 连接状态
	 */
	public ConnectFuture connect() {
		int worldreceivebuffsize = this.configuration.getInt("worldreceivebuffsize");
		int worldwritebuffsize = this.configuration.getInt("worldwritebuffsize");
		String worldIp = this.configuration.getString("worldip");
		int worldPort = this.configuration.getInt("worldport");
		InetSocketAddress address = new InetSocketAddress(worldIp, worldPort);

		this.connector = new NioSocketConnector(Runtime.getRuntime().availableProcessors() + 1);
		SocketSessionConfig cfg = acceptor.getSessionConfig();
		cfg.setIdleTime(IdleStatus.BOTH_IDLE, 120);
		cfg.setTcpNoDelay(true);
		cfg.setReceiveBufferSize(worldreceivebuffsize);
		cfg.setSendBufferSize(worldwritebuffsize);
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ServerWYDEncoder(), new ServerWYDDecoder()));
		connector.setHandler(new ServerSessionHandler());
		connector.setDefaultRemoteAddress(address);
		ConnectFuture future = this.connector.connect();
		future.awaitUninterruptibly();

		if (this.serverSession == null || !this.serverSession.isConnected()) {
			log.error("WorldServer 连接失败!");
		} else {
			System.out.println("WorldServer 连接成功!");
		}
		return future;
	}

	/**
	 * 实现DispatchServer 监听客户端请求
	 * 
	 * @param address
	 *            套接字地址
	 * @param clientreceivebuffsize
	 *            输入缓冲区大小
	 * @param clientwritebuffsize
	 *            输出缓冲区大小
	 * @throws IOException
	 *             绑定监听出错时抛出些异常
	 */
	public void bind(SocketAddress address, int clientreceivebuffsize, int clientwritebuffsize) throws IOException {
		this.address = address;
		this.acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		SocketSessionConfig cfg = acceptor.getSessionConfig();
		cfg.setIdleTime(IdleStatus.BOTH_IDLE, 60);
		cfg.setTcpNoDelay(true);
		cfg.setReuseAddress(true);

		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.getSessionConfig().setReceiveBufferSize(clientreceivebuffsize);
		acceptor.getSessionConfig().setSendBufferSize(clientwritebuffsize);
		// 添加Protocol编码过滤器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new SimpleWYDEncoder(), new SimpleWYDDecoderForSocket()));
		acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(1, 4));
		acceptor.setHandler(new ClientSessionHandler());
		acceptor.setDefaultLocalAddress(address);
		this.acceptor.bind();
	}

	/** 转发数据至前端 */
	public void dispatchToClient(Packet packet) {
		int sessionId = packet.sessionId;
		IoBuffer buffer = packet.buffer;
		IoSession session = (IoSession) this.sessions.get(Integer.valueOf(sessionId));
		if (session != null) {
			byte type = buffer.get(19);
			if (type == Protocol.MAIN_ACCOUNT) {
				byte subType = buffer.get(20);
				if (subType == Protocol.ACCOUNT_LoginOk) {
					// 账号登录成功
					session.setAttribute(LOGINMARK_KEY, LOGINMARK_LOGED);
					SocketDispatcher.this.channelService.getWorldChannel().join(session);
				} else if (subType == Protocol.ACCOUNT_RoleLoginOk) {
					// 角色登录成功添加Player对象
					try {
						ClientInfo client = (ClientInfo) session.getAttribute(CLIENTINFO_KEY);
						if (client == null)
							throw new Exception("ClientInfo is null");
						INetData udata = new S2SData(Arrays.copyOfRange(buffer.array(), 18, buffer.array().length), 1, sessionId);
						int playerId = udata.readInt();// 角色id
						int heroId = udata.readInt();// 英雄id
						String nickname = udata.readString();// 玩家角色名称
						int lv = udata.readInt(); // 玩家等级
						int vipLv = udata.readInt(); // 玩家vip等级
						String property = udata.readString();// 属性
						int fight = udata.readInt(); // 玩家当前战斗力
						Player player = new Player(playerId);
						player.setHeroId(heroId);
						player.setNickname(nickname);
						player.setLv(lv);
						player.setVipLv(vipLv);
						player.setProperty(property);
						player.setFight(fight);
						client.setPlayer(player);
						session.setAttribute(PLAYERID_KEY, playerId);
						allClientInfo.put(playerId, client);

					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex, ex);
					}
				}
			}
			session.write(buffer);
		}
	}
	/** 用户上线，注册客户端 */
	public void registerClient(IoSession session) {
		Integer sessionId = this.ids.incrementAndGet();
		if (sessionId < 0) {
			log.info("用户链接SessionId: " + sessionId);
		}
		// session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);// 空闲时间60秒
		session.setAttribute(ATTRIBUTE_STRING, sessionId);
		session.setAttribute(LOGINMARK_KEY, LOGINMARK_UNLOG);
		// 设置客户端消息初始化
		session.setAttribute(CLIENTINFO_KEY, new ClientInfo(session));

		this.sessions.put(sessionId, session);
		// this.channelService.getWorldChannel().join(session);
		// ====发送ip地址到worldserver===
		InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
		String hostAddress = address.getAddress().getHostAddress();
		if (StringUtils.hasText(hostAddress)) {
			S2SSegment seg = new S2SSegment((byte) Protocol.MAIN_SERVER, (byte) Protocol.SERVER_SetClientIPAddress);
			seg.writeInt(sessionId.intValue());
			seg.writeString(hostAddress);
			sendControlSegment(seg);
		}
		// =======
	}
	/** 用户下线，注销客户端 */
	protected void unRegisterClient(IoSession session) {
		if ((Boolean) session.getAttribute(LOGINMARK_KEY)) {
			this.channelService.getWorldChannel().removeSession(session);
			// this.channelService.removeSessionFromAllChannel(session);
		}
		Integer sessionId = (Integer) session.getAttribute(ATTRIBUTE_STRING);
		Integer playerId = (Integer) session.getAttribute(PLAYERID_KEY);
		if (sessionId != null) {
			S2SSegment seg = new S2SSegment((byte) Protocol.MAIN_SERVER, (byte) Protocol.SERVER_SessionClosed);
			seg.writeInt(sessionId.intValue());
			sendControlSegment(seg);
			this.sessions.remove(sessionId);
			if (playerId != null)
				this.allClientInfo.remove(playerId);
		}
	}
	/** 提玩家下线 */
	public void unRegisterClient(int sessionId) {
		IoSession session = (IoSession) this.sessions.remove(Integer.valueOf(sessionId));
		if ((session != null) && (session.isConnected()))
			session.close(true);
	}
	/** 执行worldServer 发来的协议 */
	protected void processControl(Packet packet) {
		TimeControlProcessor.getControlProcessor().process(packet.data);
	}
	/** 广播线上所有用户 */
	public void broadcast(IoBuffer buffer) {
		for (IoSession session : this.sessions.values()) {
			session.write(buffer.duplicate());
		}
		buffer.clear();
	}
	/** 重新开启服务 */
	public void shutdown() {
		this.acceptor.unbind();
		try {
			this.acceptor.setHandler(new ClientSessionHandler());
			this.acceptor.setDefaultLocalAddress(this.address);
			this.acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/***
	 * 同步玩家角色信息
	 */
	@Override
	public void syncPlayer(INetData data) {
		try {
			int playerId = data.readInt();// 角色id
			int heroId = data.readInt();// 英雄id
			String nickname = data.readString();// 玩家角色名称
			int lv = data.readInt(); // 玩家等级
			int vipLv = data.readInt(); // 玩家vip等级
			String property = data.readString();// 属性
			int fight = data.readInt(); // 玩家当前战斗力
			ClientInfo clientInfo = this.allClientInfo.get(playerId);
			Player player = clientInfo.getPlayer();
			player.setHeroId(heroId);
			player.setNickname(nickname);
			player.setLv(lv);
			player.setVipLv(vipLv);
			player.setProperty(property);
			player.setFight(fight);

		} catch (Exception ex) {
			log.error(ex, ex);
		}

	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(60000L);
			} catch (InterruptedException ex) {
			}
			log.info("ONLINE Session[" + this.sessions.size() + "]");
		}
	}

	/**
	 * DispatchServer处理WorldServer数据信息,做为客户端的处理Handler，第一次登陆时注册dispatcher
	 * server信息
	 */
	class ServerSessionHandler extends IoHandlerAdapter {
		@Override
		public void exceptionCaught(IoSession sesion, Throwable throwable) throws Exception {
			// sesion.close(true);
			SocketDispatcher.log.error(throwable, throwable);
		}
		@Override
		public void messageReceived(IoSession session, Object object) throws Exception {
			Packet packet = (Packet) object;

			if (packet.type == Packet.TYPE.BUFFER) {
				// System.out.println("dis收到WORLD数据发前端：" +
				// packet.data.toString());
				SocketDispatcher.this.dispatchToClient(packet);
			} else {
				System.out.println("dis收到WORLD数据发系统：" + packet.data.toString());
				SocketDispatcher.this.processControl(packet);
			}
		}
		@Override
		public void sessionClosed(IoSession session) throws Exception {
			serverSession = null;
			// 断线重连worldServer
			try {
				Thread.sleep(12000L);
				SocketDispatcher.this.connect();
				SocketDispatcher.log.info("worldServer 断线重连。。。");
			} catch (Exception e) {
				SocketDispatcher.log.error(e.getMessage());
			}
		}

		// I/O processor线程触发
		@Override
		public void sessionCreated(IoSession session) throws Exception {
			serverSession = session;
		}
		@Override
		public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
			S2SSegment seg = new S2SSegment(Protocol.MAIN_SERVER, Protocol.SERVER_Heartbeat);
			SocketDispatcher.this.sendControlSegment(seg);
		}
		@Override
		public void sessionOpened(IoSession session) {
			S2SSegment seg = new S2SSegment(Protocol.MAIN_SERVER, Protocol.SERVER_DispatchLogin);
			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("area"));
			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("serverpassword"));
			seg.writeInt(SocketDispatcher.this.configuration.getInt("maxplayer"));
			SocketDispatcher.this.sendControlSegment(seg);
		}
	}

	/**
	 * DispatchServer做为服务器端的处理Handler，处理手机客户端的数据信息
	 */
	class ClientSessionHandler extends IoHandlerAdapter {
		@Override
		public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
			SocketDispatcher.log.error(throwable, throwable);
			session.close(true);
		}
		@Override
		public void messageReceived(IoSession session, Object object) throws Exception {
			SocketDispatcher.this.dispatchToServer(session, object);
		}
		@Override
		public void sessionClosed(IoSession session) throws Exception {
			if (!(SocketDispatcher.this.shutdown))
				SocketDispatcher.this.unRegisterClient(session);
		}
		@Override
		public void sessionCreated(IoSession session) throws Exception {
		}
		@Override
		public void sessionOpened(IoSession session) throws Exception {
			InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
			SocketDispatcher.this.registerClient(session);
			log.info("ok:" + address.getAddress().getHostAddress());
		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
			session.close(true);
		}
	}

	public class ClientInfo {
		private long heartbeatTime = 0;// 心跳时间
		private int heartbeatCount = 0;// 心跳次数
		private long protocolTime = 0;// 协议时间
		private int protocolCount = 0;// 协议次数
		private long moveTime = 0;// 移动时间
		private int moveCount = 0;// 移动次数
		private IoSession ioSession;
		private Player player;
		private Timer timer = new Timer();

		public ClientInfo(IoSession ioSession) {
			this.ioSession = ioSession;
		}

		public long getHeartbeatTime() {
			return heartbeatTime;
		}

		public void setHeartbeatTime(long heartbeatTime) {
			this.heartbeatTime = heartbeatTime;
		}

		public int getHeartbeatCount() {
			return heartbeatCount;
		}

		public void setHeartbeatCount(int heartbeatCount) {
			this.heartbeatCount = heartbeatCount;
		}

		public long getProtocolTime() {
			return protocolTime;
		}

		public void setProtocolTime(long protocolTime) {
			this.protocolTime = protocolTime;
		}

		public int getProtocolCount() {
			return protocolCount;
		}

		public void setProtocolCount(int protocolCount) {
			this.protocolCount = protocolCount;
		}

		public IoSession getIoSession() {
			return ioSession;
		}

		public Player getPlayer() {
			return player;
		}

		public void setPlayer(Player player) {
			this.player = player;
		}

		public long getMoveTime() {
			return moveTime;
		}

		public void setMoveTime(long moveTime) {
			this.moveTime = moveTime;
		}

		public int getMoveCount() {
			return moveCount;
		}

		public void setMoveCount(int moveCount) {
			this.moveCount = moveCount;
		}

		public void addHeartbeatCount() {
			this.heartbeatCount++;
		}
		public void addProtocolCount() {
			this.protocolCount++;
		}
		public void addMovecount() {
			this.moveCount++;
		}
		public Timer getTimer() {
			return timer;
		}

	}
}