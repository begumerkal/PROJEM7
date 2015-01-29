package com.wyd.dispatch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.util.StringUtils;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.data.system.ShakeHands;
import com.wyd.protocol.ProtocolManager;
import com.wyd.protocol.s2s.S2SSegment;

public class SocketDispatcher implements Dispatcher, Runnable {
	private static final String ATTRIBUTE_STRING = "UWAPSESSIONID";
	private static final Logger log = Logger.getLogger(SocketDispatcher.class);
	private AtomicInteger ids = new AtomicInteger(0);
	private ConcurrentHashMap<Integer, IoSession> sessions = new ConcurrentHashMap<Integer, IoSession>();// 客户端iosession
	private ControlProcessor processor = null;
	private ChannelService channelService = null;
	private NioSocketAcceptor acceptor = null;
	private NioSocketConnector connector = null;
	/** worldServer Iosession */
	private IoSession serverSession = null;
	/** 允许加载的ip段 */
	private TrustIpService trustIpService = null;
	private Configuration configuration = null;
	private byte[] lock = new byte[0];
	public static final String SERVERID = "serverid";
	public static final String SERVERNAME = "servername";
	public static final String SERVERPASSWORD = "serverpassword";
	private SocketAddress address;
	private boolean connected = false;
	private boolean shutdown = false;
	private ShakeHands shakeHands = new ShakeHands();
	private static final String LOGINMARK_KEY = "ISLOGED";
	private static final int LOGINMARK_UNLOG = 0;
	private static final int LOGINMARK_LOGED = 1;
	private static final String CLIENTINFO_KEY = "CLIENTINFO";

	public SocketDispatcher(ControlProcessor processor, Configuration configuration) {
		this.processor = processor;
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
	/** 转发前端数据至worldServer */
	public void dispatchToServer(IoSession session, Object object) {
		Integer id = (Integer) session.getAttribute(ATTRIBUTE_STRING);
		if (id != null) {
			IoBuffer buffer = (IoBuffer) object;
			if (checkProtocol(session, buffer.get(19), buffer.get(20))) {
				// 回应客户端心跳协议
				if (buffer.limit() >= 20 && buffer.get(19) == Protocol.MAIN_SYSTEM && buffer.get(20) == Protocol.SYSTEM_ShakeHands
						&& 1 == buffer.getShort(16)) {// 一个包&长度大于20
					IoBuffer byteBuffer = IoBuffer.wrap(ProtocolManager.makeSegment(shakeHands).getPacketByteArray());
					session.write(byteBuffer.duplicate());
				}
				// 判断用户是否已经登录或者为登录协议
				else if (LOGINMARK_LOGED == ((Integer) session.getAttribute(LOGINMARK_KEY)).intValue()
						|| buffer.get(19) == Protocol.MAIN_ACCOUNT || buffer.get(19) == Protocol.MAIN_ERRORCODE
						|| buffer.get(19) == Protocol.MAIN_SYSTEM) {
					buffer.putInt(4, id.intValue());
					this.serverSession.write(buffer.duplicate());
				}
				// 不是心跳，不是登录协议，并且用户未登录则断开socket连接
				else {
					System.out.println("Kill Session LOGINMARK:" + session.getAttribute(LOGINMARK_KEY) + "---type:" + buffer.get(19)
							+ "---subtype:" + buffer.get(20));
					session.close(true);
				}
			}
		} else {
			session.close(true);
		}
	}


	/**
	 * 检查协议上行数量是否正常
	 * 心跳， 正常协议发送频率
	 * 
	 * @param session
	 * @param type
	 * @param subType
	 * @return true正常false异常
	 */
	public boolean checkProtocol(IoSession session, int type, int subType) {
		ClientInfo client = (ClientInfo) session.getAttribute(CLIENTINFO_KEY);
		if (client != null) {
			if (type == Protocol.MAIN_SYSTEM && subType == Protocol.SYSTEM_ShakeHands) {// 判断是否心跳
				if (System.currentTimeMillis() - client.getHeartbeatTime() <= 10000) {
					client.addHeartbeatCount();
					if (client.getHeartbeatCount() > this.configuration.getInt("heartbeatcount")) {// 10秒钟内心跳数大于2则断开连接
						log.info("Warning SessionId [" + session.getId() + "] HeartbeatCount: + " + client.getHeartbeatCount());
						session.close(true);
						return false;
					}
				} else {
					client.setHeartbeatCount(0);
					client.setHeartbeatTime(System.currentTimeMillis());
				}
			} else {// 其他协议
				if (System.currentTimeMillis() - client.getProtocolTime() <= 1000 && type != Protocol.MAIN_BATTLE
						&& type != Protocol.MAIN_BOSSMAPBATTLE) {// 除战斗
					client.addProtocolCount();
					if (client.getProtocolCount() > this.configuration.getInt("protocolcount")) {// 1秒钟内相同协议大于10则断开连接
						log.info("Warning SessionId [" + session.getId() + "] ProtocolCount: + " + client.getProtocolCount());
						session.close(true);
						return false;
					}
				} else {
					client.setProtocolCount(0);
					client.setProtocolTime(System.currentTimeMillis());
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

	/** 发数据到worldServer */
	public void sendControlSegment(S2SSegment seg) {
		seg.setSessionId(-1);
		this.serverSession.write(IoBuffer.wrap(seg.getPacketByteArray()));
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
	public ConnectFuture connect(InetSocketAddress address, int worldreceivebuffsize, int worldwritebuffsize) {
		this.connector = new NioSocketConnector(Runtime.getRuntime().availableProcessors() + 1);
		connector.getSessionConfig().setTcpNoDelay(true);
		connector.getSessionConfig().setReceiveBufferSize(worldreceivebuffsize);
		connector.getSessionConfig().setSendBufferSize(worldwritebuffsize);
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ServerWYDEncoder(), new ServerWYDDecoder()));
		connector.setHandler(new ServerSessionHandler());
		connector.setDefaultRemoteAddress(address);
		ConnectFuture future = this.connector.connect();
		synchronized (this.lock) {
			try {
				this.lock.wait(10000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!(this.connected)) {
				log.error("世界服务器连接失败!");
			}
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

	public void dispatchToClient(Packet packet) {
		dispatchToClient(packet.sessionId, packet.buffer);
	}

	public void dispatchToClient(int sessionId, IoBuffer buffer) {
		IoSession session = (IoSession) this.sessions.get(Integer.valueOf(sessionId));
		if (session != null) {
			if (Protocol.MAIN_ACCOUNT == buffer.get(19) && Protocol.ACCOUNT_LoginOk == buffer.get(20)) {
				session.setAttribute(LOGINMARK_KEY, LOGINMARK_LOGED);
				SocketDispatcher.this.channelService.getWorldChannel().join(session);
			}
			session.write(buffer);
		}
	}
	/** 用户上线，注册客户端 */
	public void registerClient(IoSession session) {
		int id = this.ids.incrementAndGet();
		if (id < 0) {
			log.info("SessionId: " + id);
		}
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
		session.setAttribute(ATTRIBUTE_STRING, Integer.valueOf(id));
		session.setAttribute(LOGINMARK_KEY, LOGINMARK_UNLOG);
		session.setAttribute(CLIENTINFO_KEY, new ClientInfo());
		this.sessions.put(Integer.valueOf(id), session);
		// this.channelService.getWorldChannel().join(session);
		// ====发送ip地址到worldserver===
		InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
		String hostAddress = address.getAddress().getHostAddress();
		Integer sessionId = (Integer) session.getAttribute(ATTRIBUTE_STRING);
		if (sessionId != null && StringUtils.hasText(hostAddress)) {
			S2SSegment seg = new S2SSegment((byte) Protocol.MAIN_SERVER, (byte) Protocol.SERVER_SetClientIPAddress);
			seg.writeInt(sessionId.intValue());
			seg.writeString(hostAddress);
			sendControlSegment(seg);
		}
		// =======
	}
	/** 用户下线，注销客户端 */
	protected void unRegisterClient(IoSession session) {
		if (LOGINMARK_LOGED == ((Integer) session.getAttribute(LOGINMARK_KEY)).intValue()) {
			this.channelService.removeSessionFromAllChannel(session);
		}
		Integer sessionId = (Integer) session.getAttribute(ATTRIBUTE_STRING);
		if (sessionId != null) {
			S2SSegment seg = new S2SSegment((byte) Protocol.MAIN_SERVER, (byte) Protocol.SERVER_SessionClosed);
			seg.writeInt(sessionId.intValue());
			sendControlSegment(seg);
			this.sessions.remove(sessionId);
		}
	}

	public void unRegisterClient(int sessionId) {
		IoSession session = (IoSession) this.sessions.remove(Integer.valueOf(sessionId));
		if ((session != null) && (session.isConnected()))
			session.close(true);
	}

	protected void processControl(Packet packet) {
		this.processor.process(packet.data);
	}

	public void broadcast(IoBuffer buffer) {
		for (IoSession session : this.sessions.values()) {
			session.write(buffer.duplicate());
		}
		buffer.clear();
	}

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
		public void exceptionCaught(IoSession sesion, Throwable throwable) throws Exception {
			SocketDispatcher.log.error(throwable, throwable);
		}

		public void messageReceived(IoSession session, Object object) throws Exception {
			Packet packet = (Packet) object;
			if (packet.type == Packet.TYPE.BUFFER)
				SocketDispatcher.this.dispatchToClient(packet);
			else
				SocketDispatcher.this.processControl(packet);
		}

		public void sessionClosed(IoSession session) throws Exception {
			serverSession = null;
			connected = false;
		}

		// I/O processor线程触发
		public void sessionCreated(IoSession session) throws Exception {
			serverSession = session;
			S2SSegment seg = new S2SSegment(Protocol.MAIN_SERVER, Protocol.SERVER_DispatchLogin);
			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("area"));
			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("serverpassword"));
			seg.writeInt(SocketDispatcher.this.configuration.getInt("maxplayer"));
			SocketDispatcher.this.sendControlSegment(seg);
			connected = true;
			synchronized (lock) {
				SocketDispatcher.this.lock.notify();
			}
		}

		public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
		}
	}

	/**
	 * DispatchServer做为服务器端的处理Handler，处理手机客户端的数据信息
	 */
	class ClientSessionHandler extends IoHandlerAdapter {
		public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
			SocketDispatcher.log.error(throwable, throwable);
			session.close(true);
		}

		public void messageReceived(IoSession session, Object object) throws Exception {
			SocketDispatcher.this.dispatchToServer(session, object);
		}

		public void sessionClosed(IoSession session) throws Exception {
			if (!(SocketDispatcher.this.shutdown))
				SocketDispatcher.this.unRegisterClient(session);
		}

		public void sessionCreated(IoSession session) throws Exception {
			InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
			SocketDispatcher.this.registerClient(session);
			log.info("ok:" + address.getAddress().getHostAddress());
		}

		public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
			session.close(true);
		}
	}

	public class ClientInfo {
		private long heartbeatTime = 0;
		private int heartbeatCount = 0;
		private long protocolTime = 0;
		private int protocolCount = 0;

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

		public void addHeartbeatCount() {
			this.heartbeatCount++;
		}

		public void addProtocolCount() {
			this.protocolCount++;
		}
	}
}