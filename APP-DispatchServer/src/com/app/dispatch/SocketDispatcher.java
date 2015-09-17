package com.app.dispatch;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.app.dispatch.vo.ClientInfo;
import com.app.dispatch.vo.Player;
import com.app.empire.protocol.Protocol;
import com.app.empire.protocol.data.account.Heartbeat;
import com.app.protocol.INetData;
import com.app.protocol.ProtocolManager;
import com.app.protocol.s2s.S2SData;
import com.app.protocol.s2s.S2SSegment;

import edu.emory.mathcs.backport.java.util.Arrays;

public class SocketDispatcher implements Dispatcher, Runnable {
	private static final Logger log = Logger.getLogger(SocketDispatcher.class);
	private AtomicInteger ids = new AtomicInteger(0);
	private ConcurrentHashMap<Integer, Channel> sessions = new ConcurrentHashMap<Integer, Channel>();// sessionID-->客户端Channel
	private ConcurrentHashMap<Integer, ClientInfo> allClientInfo = new ConcurrentHashMap<Integer, ClientInfo>();// playerId-->客户端

	private ChannelService channelService = null;
	private Channel worldChannel = null;
	/** worldServer Iosession */
	private Channel serverSession = null;
	/** 允许加载的ip段 */
	private TrustIpService trustIpService = null;
	private Configuration configuration = null;
	private SyncService syncService = null;
	public static final String SERVERID = "serverid";
	public static final String SERVERNAME = "servername";
	public static final String SERVERPASSWORD = "serverpassword";
	private boolean shutdown = false;
	private Heartbeat heartbeat = new Heartbeat();
	private static final AttributeKey<Boolean> LOGINMARK_KEY = AttributeKey.valueOf("ISLOGED");
	protected static final AttributeKey<ClientInfo> CLIENTINFO_KEY = AttributeKey.valueOf("CLIENTINFO");
	protected static final AttributeKey<Integer> PLAYERID_KEY = AttributeKey.valueOf("PLAYERID");
	private static final AttributeKey<Integer> ATTRIBUTE_STRING = AttributeKey.valueOf("SESSIONID");
	private static final boolean LOGINMARK_UNLOG = false;
	private static final boolean LOGINMARK_LOGED = true;

	public SocketDispatcher(Configuration configuration) {
		this.configuration = configuration;
	}

	public void start() {
		new Thread(this, "OnlinePrinter").start();
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
	public void dispatchToServer(Channel channel, Object msg) {
		// System.out.println("* 转发数据至 worldServer *");
		Integer id = (Integer) channel.attr(ATTRIBUTE_STRING).get();

		if (id != null) {
			ByteBuffer data = (ByteBuffer) msg;

			// byte type = data[18];// 第19个字节
			// byte subType = data[19];// 第20个字节
			// IoBuffer buffer = (IoBuffer) msg;
			byte type = data.get(19);
			byte subType = data.get(20);
			if (checkProtocol(channel, type, subType)) {// 协议检查
				if (type == Protocol.MAIN_ACCOUNT) {
					if (subType == Protocol.ACCOUNT_Heartbeat) {// 回应客户端心跳协议
						ByteBuffer byteBuffer = ByteBuffer.wrap(ProtocolManager.makeSegment(heartbeat).getPacketByteArray());
						channel.writeAndFlush(byteBuffer.duplicate());// 返回心跳协议
					} else if (subType == Protocol.ACCOUNT_Move) {// 玩家移动协议处理
						try {
							ClientInfo client = (ClientInfo) channel.attr(CLIENTINFO_KEY).get();
							Player player = client.getPlayer();
							INetData udata = new S2SData(Arrays.copyOfRange(data.array(), 18, data.array().length), 1, -1);
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
							ClientInfo client = (ClientInfo) channel.attr(CLIENTINFO_KEY).get();
							Player player = client.getPlayer();
							INetData udata = new S2SData(Arrays.copyOfRange(data.array(), 18, data.array().length), 1, -1);
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
					} else {
						data.putInt(4, id.intValue());// sessionId
						this.serverSession.writeAndFlush(data.duplicate());// 发送worldServer
					}
				} else if ((Boolean) channel.attr(LOGINMARK_KEY).get() == true || type == Protocol.MAIN_ERRORCODE
						|| type == Protocol.MAIN_SYSTEM) {// 判断用户是否已经登录或者为登录协议
					data.putInt(4, id.intValue());// sessionId
					this.serverSession.writeAndFlush(data.duplicate());// 发送worldServer
				} else {// 不是心跳，不是登录协议，并且用户未登录则断开socket连接
					log.info("用户未登录Kill Session LOGINMARK:" + (Boolean) channel.attr(LOGINMARK_KEY).get() + "---type:" + type
							+ "---subtype:" + subType);
					channel.close();
				}
			}
		} else {
			channel.close();
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
	public boolean checkProtocol(Channel channel, int type, int subType) {
		ClientInfo client = (ClientInfo) channel.attr(CLIENTINFO_KEY).get();// session.getAttribute(CLIENTINFO_KEY);
		long nowTime = System.currentTimeMillis();
		if (client != null) {
			if (type == Protocol.MAIN_ACCOUNT) {
				if (subType == Protocol.ACCOUNT_Heartbeat) {// 判断是否心跳
					if (nowTime - client.getHeartbeatTime() <= 10000) {
						client.addHeartbeatCount();
						if (client.getHeartbeatCount() > this.configuration.getInt("heartbeatCount")) {// 10秒钟内心跳数大于2则断开连接
							log.info("Warning SessionId [" + channel.id() + "] HeartbeatCount: + " + client.getHeartbeatCount());
							channel.close();
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
						log.info("Warning SessionId [" + channel.id() + "] ProtocolCount: + " + client.getProtocolCount());
						channel.close();
						return false;
					}
				} else {
					client.setProtocolCount(0);
					client.setProtocolTime(nowTime);
				}
			}
		} else {
			channel.close();
			return false;
		}
		return true;
	}

	public Channel getSession(int sessionId) {
		return this.sessions.get(sessionId);
	}

	/** 发数据到 worldServer */
	public void sendControlSegment(S2SSegment seg) {
		try {
			seg.setSessionId(-1);
			this.serverSession.writeAndFlush(ByteBuffer.wrap(seg.getPacketByteArray()));
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
	 */
	public void connect() {
		new Thread(new ConnectWorld()).start();
	}
	private class ConnectWorld implements Runnable {
		@Override
		public void run() {
			String worldIp = SocketDispatcher.this.configuration.getString("worldip");
			int worldPort = SocketDispatcher.this.configuration.getInt("worldport");
			InetSocketAddress address = new InetSocketAddress(worldIp, worldPort);
			// 配置客户端NIO线程组
			EventLoopGroup group = new NioEventLoopGroup();
			try {
				Bootstrap b = new Bootstrap();
				b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new IdleStateHandler(0, 0, 120));
						p.addLast(new ServerWYDEncoder());
						p.addLast(new ServerWYDDecoder());
						p.addLast(new ServerSessionHandler());
					}
				});
				b.option(ChannelOption.SO_KEEPALIVE, false);
				b.option(ChannelOption.SO_REUSEADDR, true);
				b.option(ChannelOption.TCP_NODELAY, true);
				// 发起异步连接操作
				ChannelFuture f = b.connect(address).sync();
				// 等待客户端链路关闭
				f.channel().closeFuture().sync();
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				// 释放NIO线程组
				System.out.println("释放NIO线程组");
				group.shutdownGracefully();
			}
		}
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
	public void bind() throws IOException {
		new Thread(new Bind(), "Dispatch_Service").start();
	}
	private class Bind implements Runnable {
		@Override
		public void run() {
			// bossGroup线程池用来接受客户端的连接请求
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			// workerGroup线程池用来处理boss线程池里面的连接的数据
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			try {
				// 配置服务器的NIO线程组
				ServerBootstrap b = new ServerBootstrap();
				b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new IdleStateHandler(0, 0, 60));
						 p.addLast(new SimpleWYDEncoder());
						p.addLast(new SimpleWYDDecoderForSocket());
						p.addLast(new ClientSessionHandler());
					}
				});
				b.option(ChannelOption.SO_REUSEADDR, true);
				b.option(ChannelOption.TCP_NODELAY, true);
				b.childOption(ChannelOption.SO_KEEPALIVE, false);
				b.option(ChannelOption.SO_BACKLOG, 128);
				// 绑定端口，同步等待成功
				ChannelFuture f = b.bind(SocketDispatcher.this.configuration.getString("localip"),
						SocketDispatcher.this.configuration.getInt("port")).sync();
				SocketDispatcher.this.worldChannel = f.channel();
				System.out.println("dis启动成功。。。");
				// 等待服务端监听端口关闭
				f.channel().closeFuture().sync();
			} catch (Exception e) {
				System.out.println("dis启动成功。。。");
				SocketDispatcher.log.error(e.getMessage(), e);
			} finally {
				// 释放线程池资源
				System.out.println("释放线程池资源");
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		}
	}

	/** 转发数据至前端 */
	public void dispatchToClient(Packet packet) {
		int sessionId = packet.sessionId;
		ByteBuffer buffer = packet.buffer;
		Channel channel = (Channel) this.sessions.get(Integer.valueOf(sessionId));
		if (channel != null) {
			byte type = buffer.get(19);
			if (type == Protocol.MAIN_ACCOUNT) {
				byte subType = buffer.get(20);
				if (subType == Protocol.ACCOUNT_LoginOk) {
					// 账号登录成功
					channel.attr(LOGINMARK_KEY).set(LOGINMARK_LOGED);
					SocketDispatcher.this.channelService.getWorldChannel().join(channel);
				} else if (subType == Protocol.ACCOUNT_RoleLoginOk) {
					// 角色登录成功添加Player对象
					try {
						ClientInfo client = (ClientInfo) channel.attr(CLIENTINFO_KEY).get();
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

						channel.attr(PLAYERID_KEY).set(playerId);
						allClientInfo.put(playerId, client);
					} catch (Exception ex) {
						ex.printStackTrace();
						log.error(ex, ex);
					}
				}
			}
			channel.writeAndFlush(buffer);
		}
	}
	/** 用户上线，注册客户端 */
	public void registerClient(Channel channel) {
		Integer sessionId = this.ids.incrementAndGet();
		if (sessionId < 0) {
			log.info("用户链接SessionId: " + sessionId);
		}
		channel.attr(ATTRIBUTE_STRING).set(sessionId);
		channel.attr(LOGINMARK_KEY).set(LOGINMARK_UNLOG);
		channel.attr(CLIENTINFO_KEY).set(new ClientInfo(channel));

		this.sessions.put(sessionId, channel);
		// ====发送ip地址到worldserver===
		InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
		String hostAddress = address.getAddress().getHostAddress();
		if (StringUtils.hasText(hostAddress)) {
			S2SSegment seg = new S2SSegment((byte) Protocol.MAIN_SERVER, (byte) Protocol.SERVER_SetClientIPAddress);
			seg.writeInt(sessionId.intValue());
			seg.writeString(hostAddress);
			sendControlSegment(seg);
		}
	}
	/** 用户下线，注销客户端 */
	protected void unRegisterClient(Channel channel) {
		if ((Boolean) channel.attr(LOGINMARK_KEY).get() == true) {
			this.channelService.getWorldChannel().removeSession(channel);
			// this.channelService.removeSessionFromAllChannel(session);
		}
		Integer sessionId = (Integer) channel.attr(ATTRIBUTE_STRING).get();
		Integer playerId = (Integer) channel.attr(PLAYERID_KEY).get();
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
		Channel session = (Channel) this.sessions.remove(Integer.valueOf(sessionId));
		if ((session != null) && (session.isActive()))
			session.close();
	}
	/** 执行worldServer 发来的协议 */
	protected void processControl(Packet packet) {
		TimeControlProcessor.getControlProcessor().process(packet.data);
	}
	/** 广播线上所有用户 */
	@Override
	public void broadcast(ByteBuffer buffer) {
		for (Channel channel : this.sessions.values()) {
			channel.writeAndFlush(buffer.duplicate());
		}
	}
	/** 重新开启服务 */
	public void shutdown() {
		this.worldChannel.close();
		try {
			bind();
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
				log.info("ONLINE 链接数量[" + this.sessions.size() + "]");
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * DispatchServer处理WorldServer数据信息,做为客户端的处理Handler，第一次登陆时注册dispatcher
	 * server信息
	 */
	class ServerSessionHandler extends SimpleChannelInboundHandler<Object> {
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// sesion.close(true);
			SocketDispatcher.log.error(cause, cause);
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}
		@Override
		protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
			Packet packet = (Packet) msg;
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
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
			serverSession = ctx.channel();
		}
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("WorldServer 链接成功！！！");
			S2SSegment seg = new S2SSegment(Protocol.MAIN_SERVER, Protocol.SERVER_DispatchLogin);
			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("area"));
			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("serverpassword"));
			seg.writeInt(SocketDispatcher.this.configuration.getInt("maxplayer"));
			SocketDispatcher.this.sendControlSegment(seg);
		}
		@Override
		public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
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

		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt instanceof IdleStateEvent) {
				S2SSegment seg = new S2SSegment(Protocol.MAIN_SERVER, Protocol.SERVER_Heartbeat);
				IdleStateEvent e = (IdleStateEvent) evt;
				if (e.state() == IdleState.READER_IDLE) {
					SocketDispatcher.this.sendControlSegment(seg);
					log.info("READER_IDLE 读超时");
				} else if (e.state() == IdleState.ALL_IDLE) {
					SocketDispatcher.this.sendControlSegment(seg);
					log.info("ALL_IDLE 超时");
				} else if (e.state() == IdleState.WRITER_IDLE) {
					SocketDispatcher.this.sendControlSegment(seg);
					log.info("WRITER_IDLE 写超时");
				}
			}
		}
	}

	/**
	 * DispatchServer做为服务器端的处理Handler，处理手机客户端的数据信息
	 */
	class ClientSessionHandler extends SimpleChannelInboundHandler<Object> {
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			SocketDispatcher.log.error(cause.getMessage(), cause);
			ctx.channel().close();
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}
		@Override
		protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
			SocketDispatcher.this.dispatchToServer(ctx.channel(), msg);
		}
		// 通道注册
		@Override
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		}
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			// 与客户端建立连接后
			InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
			SocketDispatcher.this.registerClient(ctx.channel());
			log.info("ok:" + address.getAddress().getHostAddress());
		}

		// 通道取消注册
		@Override
		public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
			if (!(SocketDispatcher.this.shutdown))
				SocketDispatcher.this.unRegisterClient(ctx.channel());
		}
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt instanceof IdleStateEvent) {
				IdleStateEvent e = (IdleStateEvent) evt;
				if (e.state() == IdleState.READER_IDLE) {
					this.channelIdle(ctx.channel(), e.state());
					log.info("READER_IDLE 读超时--");
				} else if (e.state() == IdleState.ALL_IDLE) {
					this.channelIdle(ctx.channel(), e.state());
					log.info("ALL_IDLE 超时--");
				} else if (e.state() == IdleState.WRITER_IDLE) {
					this.channelIdle(ctx.channel(), e.state());
					log.info("WRITER_IDLE 写超时--");
				}
			}
		}
		private void channelIdle(Channel channel, IdleState state) throws Exception {
			channel.close();
		}

	}
}