package com.app.net;
import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.app.protocol.data.AbstractData;
import com.app.protocol.data.DataBeanDecoder;
import com.app.protocol.data.DataBeanEncoder;
import com.app.protocol.handler.IDataHandler;
import com.app.protocol.s2s.S2SDecoder;
import com.app.protocol.s2s.S2SEncoder;
public abstract class Connector implements IConnector {
	protected static final Logger log = Logger.getLogger(Connector.class);
	protected InetSocketAddress address;
	protected boolean needRetry = false;
	protected int receiveBufferSize = 65534;
	protected int sendBufferSize = 65534;
	protected Channel channel;
	protected String id;

	public Connector(String id, InetSocketAddress address) {
		this.id = id;
		this.address = address;

	}
	public String getId() {
		return this.id;
	}
	public void connect() throws Exception {
		if (this.isConnected()) {
			throw new IllegalStateException("connection is connected");
		}
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new IdleStateHandler(0, 0, 120));
					p.addLast(new S2SEncoder());
					p.addLast(new S2SDecoder());
					p.addLast(new DataBeanEncoder());
					p.addLast(new DataBeanDecoder());
					p.addLast(new OriginalSessionHandler());
				}
			});
			b.option(ChannelOption.SO_KEEPALIVE, false);
			b.option(ChannelOption.SO_REUSEADDR, true);
			b.option(ChannelOption.TCP_NODELAY, true);
			// 发起异步连接操作
			ChannelFuture f = b.connect(this.address).sync();
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

	public boolean isConnected() {
		if (this.channel == null)
			return false;
		return this.channel.isActive();
	}

	public void send(AbstractData data) {
		try {
			if (this.isConnected() == false) {
				this.connect();
				log.info("重连.");
			}
			// for (int i = 0; i < 100; i++) {
			// this.channel.write(data);
			// }
			// this.channel.write(data);
			this.channel.writeAndFlush(data);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void close() {
		if (isConnected()) {
			this.channel.close();
		}
	}

	public SocketAddress getRemoteAddress() {
		return this.channel.remoteAddress();
	}

	protected abstract void connected();
	protected abstract void idle();

	/**
	 * 原始的会话处理
	 *
	 */
	public class OriginalSessionHandler extends SimpleChannelInboundHandler<Object> {
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			Connector.log.info(cause, cause);
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}
		@Override
		protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
			AbstractData data = (AbstractData) msg;
			if (data == null) {
				Connector.log.error("get a NULL data!");
			} else {
				IDataHandler handler = ProtocolFactory.getDataHandler(data);
				if (handler != null)
					handler.handle(data);
				else
					Connector.log.error("get a NULL handler!");
			}
		}

		@Override
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
			super.channelRegistered(ctx);
			Connector.this.channel = ctx.channel();
		}
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			Connector.this.connected();
		}
		// @Override
		// public void channelInactive(ChannelHandlerContext ctx) throws
		// Exception {
		// System.out.println("----链接 Channel - Inactive ----" + ctx.channel());
		// }
		@Override
		public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
			// 断线重连
			if (Connector.this.needRetry) {
				while (true) {
					if (Connector.this.isConnected())
						return;
					try {
						Thread.sleep(20000L);
						Connector.this.connect();
						System.out.println("断线尝试重连...");
						Connector.log.info("log断线尝试重连...");
					} catch (Exception e) {
						Connector.log.error(e.getMessage());
					}
				}
			}
		}
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt instanceof IdleStateEvent) {
				IdleStateEvent e = (IdleStateEvent) evt;
				if (e.state() == IdleState.READER_IDLE) {
					Connector.this.idle();
//					log.info("READER_IDLE 读超时");
				} else if (e.state() == IdleState.ALL_IDLE) {
					Connector.this.idle();
//					log.info("ALL_IDLE 超时");
				} else if (e.state() == IdleState.WRITER_IDLE) {
					Connector.this.idle();
//					log.info("WRITER_IDLE 写超时");
				}
			}
		}
	}

	public void setNeedRetry(boolean needRetry) {
		this.needRetry = needRetry;
	}

	public boolean isNeedRetry() {
		return this.needRetry;
	}
}
