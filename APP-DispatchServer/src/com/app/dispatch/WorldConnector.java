//package com.app.dispatch;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.timeout.IdleStateHandler;
//
//import java.net.InetSocketAddress;
//
//import org.apache.log4j.Logger;
//import org.apache.mina.core.service.IoHandlerAdapter;
//import org.apache.mina.core.session.IdleStatus;
//import org.apache.mina.core.session.IoSession;
//
//import com.app.dispatch.SocketDispatcher.ServerSessionHandler;
//import com.app.empire.protocol.Protocol;
//import com.app.empire.protocol.data.server.Heartbeat;
//import com.app.net.Connector;
//import com.app.net.Connector.OriginalSessionHandler;
//import com.app.protocol.data.DataBeanDecoder;
//import com.app.protocol.data.DataBeanEncoder;
//import com.app.protocol.s2s.S2SDecoder;
//import com.app.protocol.s2s.S2SEncoder;
//import com.app.protocol.s2s.S2SSegment;
//public class WorldConnector extends Connector {
//	private static final Logger log = Logger.getLogger(IpdConnector.class);
//	/**
//	 * 初始化Connector
//	 * 
//	 * @param id
//	 * @param address
//	 * @param configuration
//	 */
//	public WorldConnector(String id, InetSocketAddress address) {
//		super(id, address);
//	}
//	@Override
//	public void connect() throws Exception {
//		
//		// 配置客户端NIO线程组
//		EventLoopGroup group = new NioEventLoopGroup();
//		try {
//			Bootstrap b = new Bootstrap();
//			b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
//				@Override
//				public void initChannel(SocketChannel ch) throws Exception {
//					ChannelPipeline p = ch.pipeline();
//					p.addLast(new IdleStateHandler(0, 0, 120));
//					p.addLast(new ServerWYDEncoder());
//					p.addLast(new ServerWYDDecoder());
//					p.addLast(new ServerSessionHandler());
//				}
//			});
//			b.option(ChannelOption.SO_KEEPALIVE, false);
//			b.option(ChannelOption.SO_REUSEADDR, true);
//			b.option(ChannelOption.TCP_NODELAY, true);
//			// 发起异步连接操作
//			ChannelFuture f = b.connect(address).sync();
//			// 等待客户端链路关闭
//			f.channel().closeFuture().sync();
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		} finally {
//			// 释放NIO线程组
//			System.out.println("释放NIO线程组");
//			group.shutdownGracefully();
//		}
//	
//	}
//
//	/**
//	 * 连接IPD Server服务器<br>
//	 * 发送游戏登录服务器 id，名称，地址<br>
//	 * 发送在线人数，最大人数限制，服务器状态
//	 */
//	@Override
//	protected void connected() {
//		log.info("链接worldServer成功。。。");
//	}
//
//	@Override
//	protected void idle() {
//		Heartbeat heart = new Heartbeat();
//		send(heart);
//	}
//	
//	/**
//	 * DispatchServer处理WorldServer数据信息,做为客户端的处理Handler，第一次登陆时注册dispatcher
//	 * server信息
//	 */
//	class ServerSessionHandler extends IoHandlerAdapter {
//		@Override
//		public void exceptionCaught(IoSession sesion, Throwable throwable) throws Exception {
//			// sesion.close(true);
//			WorldConnector.log.error(throwable, throwable);
//		}
//		@Override
//		public void messageReceived(IoSession session, Object object) throws Exception {
//			Packet packet = (Packet) object;
//
//			if (packet.type == Packet.TYPE.BUFFER) {
//				// System.out.println("dis收到WORLD数据发前端：" +
//				// packet.data.toString());
//				SocketDispatcher.this.dispatchToClient(packet);
//			} else {
//				System.out.println("dis收到WORLD数据发系统：" + packet.data.toString());
//				SocketDispatcher.this.processControl(packet);
//			}
//		}
//		@Override
//		public void sessionClosed(IoSession session) throws Exception {
//			serverSession = null;
//			// 断线重连worldServer
//			try {
//				Thread.sleep(12000L);
//				SocketDispatcher.this.connect();
//				SocketDispatcher.log.info("worldServer 断线重连。。。");
//			} catch (Exception e) {
//				SocketDispatcher.log.error(e.getMessage());
//			}
//		}
//
//		// I/O processor线程触发
//		@Override
//		public void sessionCreated(IoSession session) throws Exception {
//			serverSession = session;
//		}
//		@Override
//		public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
//			S2SSegment seg = new S2SSegment(Protocol.MAIN_SERVER, Protocol.SERVER_Heartbeat);
//			SocketDispatcher.this.sendControlSegment(seg);
//		}
//		@Override
//		public void sessionOpened(IoSession session) {
//			S2SSegment seg = new S2SSegment(Protocol.MAIN_SERVER, Protocol.SERVER_DispatchLogin);
//			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("area"));
//			seg.writeString((String) SocketDispatcher.this.configuration.getProperty("serverpassword"));
//			seg.writeInt(SocketDispatcher.this.configuration.getInt("maxplayer"));
//			SocketDispatcher.this.sendControlSegment(seg);
//		}
//	}
//	
//	
//}