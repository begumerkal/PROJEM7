package com.app.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;

import com.app.net.ProtocolFactory;
import com.app.protocol.data.AbstractData;
import com.app.protocol.exception.ProtocolException;
import com.app.protocol.handler.IDataHandler;

/**
 * 类 <code>SessionHandler</code> Session处理类<br>
 * 继承IoHandler，并提供createSession接口，由子类负责创建不同类型的Session类<br>
 * 抽象Session处理方法，实现基本处理逻辑
 * 
 * @since JDK 1.6
 */
public abstract class ServerHandler extends SimpleChannelInboundHandler<Object> {
	protected SessionRegistry registry = null;
	private Logger log = Logger.getLogger(ServerHandler.class);

	// SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSSS");
	/**
	 * 构造函数，初始化<tt>SessionRegistry</tt>值
	 * 
	 * @param registry
	 */
	public ServerHandler(SessionRegistry registry) {
		this.registry = registry;
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// session.close(true);
		log.error(cause.getMessage(), cause);
	}
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		AbstractData dataobj = (AbstractData) msg;
		Session session = this.registry.getSession(ctx.channel());
		if (session != null) {
			IDataHandler handler = ProtocolFactory.getDataHandler(dataobj);
			System.out.println("handler: " + handler + " session: " + session);
			if (handler == null) {
				session.handle(dataobj);
			} else {
				try {
					// 调试用记录日志
					// log.info("receive data class name:" +
					// dataobj.getClass().getName());.
					// System.out.println("receive data class name:" +
					// dataobj.getClass().getName());
					dataobj.setHandlerSource(session);
					// HandlerMonitorService.addMonitor(dataobj);
					AbstractData abstractData = handler.handle(dataobj);
					// HandlerMonitorService.delMonitor(dataobj);
					if (abstractData != null)
						session.write(abstractData);
				} catch (ProtocolException e) {
					HandlerMonitorService.delMonitor(dataobj);
					session.sendError(e);
				} catch (Exception e) {
					HandlerMonitorService.delMonitor(dataobj);
					e.printStackTrace();
					this.log.error("messageReceived-handle-error", e);
				}
			}
		}
	}
	// 通道注册
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Session s = createSession(ctx.channel());
		if (s != null) {
			this.registry.registry(s);
			s.created();
		}
		super.channelRegistered(ctx);
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 与客户端建立连接后
		Session s = this.registry.getSession(ctx.channel());
		if (s != null)
			s.opened();
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("----链接 Channel - Inactive ----" + ctx.channel());
	}
	// 通道取消注册
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		Session s = this.registry.removeSession(ctx.channel());
		if (s != null)
			s.closed();
		super.channelUnregistered(ctx);
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
	public void channelIdle(Channel channel, IdleState status) throws Exception {
		System.out.println(channel+"________________");
		Session s = this.registry.getSession(channel);
		if (s != null) {
			s.idle(channel, status);
		}
	}
	public SessionRegistry getSessionRegistry() {
		return this.registry;
	}
	public abstract Session createSession(Channel channel);

}