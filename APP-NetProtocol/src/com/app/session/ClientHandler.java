//package com.app.session;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//
//import org.apache.log4j.Logger;
//import org.apache.mina.core.session.IdleStatus;
//import org.apache.mina.core.session.IoSession;
//
//import com.app.net.Connector;
//import com.app.net.ProtocolFactory;
//import com.app.protocol.data.AbstractData;
//import com.app.protocol.exception.ProtocolException;
//import com.app.protocol.handler.IDataHandler;
//
///**
// * 类 <code>SessionHandler</code> Session处理类<br>
// * 继承IoHandler，并提供createSession接口，由子类负责创建不同类型的Session类<br>
// * 抽象Session处理方法，实现基本处理逻辑
// * 
// * @since JDK 1.6
// */
//public abstract class ClientHandler extends SimpleChannelInboundHandler<Object> {
//	private Logger log = Logger.getLogger(ClientHandler.class);
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		log.info(cause, cause);
//	}
//	@Override
//	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
//		AbstractData data = (AbstractData) msg;
//		if (data == null) {
//			log.error("get a NULL data!");
//		} else {
//			IDataHandler handler = ProtocolFactory.getDataHandler(data);
//			if (handler != null)
//				handler.handle(data);
//			else
//				log.error("get a NULL handler!");
//		}
//	}
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
//    }
//	@Override
//	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//		super.sessionClosed(session);
//		// 断线重连
//		if (Connector.this.needRetry) {
//			while (true) {
//				if (Connector.this.isConnected())
//					return;
//				try {
//					Thread.sleep(20000L);
//					// Connector.this.initConnector();
//					// Connector.this.connect();
//					Connector.this.connector.connect();
//					System.out.println("断线尝试重连...");
//					Connector.log.info("log断线尝试重连...");
//				} catch (Exception e) {
//					Connector.log.error(e.getMessage());
//				}
//			}
//		}
//	}
//	@Override
//	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//		super.sessionCreated(ctx.channel());
//		Connector.this.session = session;
//	}
//	@Override
//	public void sessionOpened(IoSession session) throws Exception {
//		super.sessionOpened(session);
//		// Connector.this.session = session;
//		Connector.this.connected();
//	}
//	@Override
//	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//		super.sessionIdle(session, status);
//		Connector.this.idle();
//	}
//}