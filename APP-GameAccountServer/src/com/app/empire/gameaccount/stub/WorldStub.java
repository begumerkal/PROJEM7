package com.app.empire.gameaccount.stub;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.app.empire.gameaccount.session.AcceptSession;
import com.app.protocol.data.DataBeanDecoder;
import com.app.protocol.data.DataBeanEncoder;
import com.app.protocol.s2s.S2SDecoder;
import com.app.protocol.s2s.S2SEncoder;
import com.app.session.Session;
import com.app.session.ServerHandler;
import com.app.session.SessionRegistry;
/**
 * 账号服务
 * 
 * @author doter
 *
 */
public class WorldStub {
	private Configuration configuration;
	private int receiveBufferSize = 32767;
	private int sendBufferSize = 32767;
	private static final Logger log = Logger.getLogger(WorldStub.class);
	private SessionRegistry registry;

	public WorldStub(Configuration configuration, SessionRegistry registry) {
		this.registry = registry;
		this.configuration = configuration;
	}

	public void start() throws Exception {
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
					p.addLast(new IdleStateHandler(0, 0, 180));
					p.addLast(new S2SEncoder());
					p.addLast(new S2SDecoder());
					p.addLast(new DataBeanEncoder());
					p.addLast(new DataBeanDecoder());
					p.addLast(new ClientSessionHandler(WorldStub.this.registry));
				}
			});
			b.option(ChannelOption.SO_REUSEADDR, true);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.childOption(ChannelOption.SO_KEEPALIVE, false);
			b.option(ChannelOption.SO_BACKLOG, 128);
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(this.configuration.getString("serverip"), this.configuration.getInt("port")).sync();
			System.out.println("游戏分区帐号数据服务器启动..."+this.configuration.getString("serverip")+":"+this.configuration.getString("port"));
			log.info("游戏分区帐号数据服务器启动..."+this.configuration.getString("serverip")+":"+this.configuration.getString("port"));
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			// 释放线程池资源
			// System.out.println("释放线程池资源");
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
		
	class ClientSessionHandler extends ServerHandler {
		public ClientSessionHandler(SessionRegistry registry) {
			super(registry);
		}
		@Override
		public Session createSession(Channel channel) {
//			System.out.println("有 WorldServer 链接过来..");
			return new AcceptSession(channel);
		}

	}
}
