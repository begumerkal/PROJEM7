package com.app.server.dispatcher;

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

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.app.empire.protocol.Protocol;
import com.app.net.ProtocolFactory;
import com.app.protocol.data.DataBeanDecoder;
import com.app.protocol.data.DataBeanEncoder;
import com.app.protocol.s2s.S2SDecoder;
import com.app.protocol.s2s.S2SEncoder;
import com.app.server.service.ServiceManager;
import com.app.session.Session;
import com.app.session.ServerHandler;
import com.app.session.SessionRegistry;

public class IpdServer {
	private PropertiesConfiguration configuration;
	private static final Logger log = Logger.getLogger(IpdServer.class);

	public static void main(String[] args) {
		new IpdServer().launch();
	}

	private void launch() {
		ProtocolFactory.init(Protocol.class, "com.app.empire.protocol.data", "com.app.server.handler");
		try {
			ServiceManager serviceManager = ServiceManager.getManager();
			serviceManager.initService();
			this.configuration = serviceManager.getConfiguration();
			openSetverListServlet();
			openServerListener();
			System.out.println("服务分区公告器启动!");
		} catch (Exception e) {
			log.error("server dispatcher launch failed!", e);
			e.printStackTrace();
		}
	}

	private void openSetverListServlet() throws Exception {
		Server server = new Server();
		// // 设置jetty线程池
		// BoundedThreadPool threadPool = new BoundedThreadPool();
		// // 设置连接参数
		// threadPool.setMinThreads(50);
		// threadPool.setMaxThreads(1000);
		// 设置监听端口，ip地址
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(this.configuration.getInt("http"));
		connector.setHost(this.configuration.getString("localip"));
		server.addConnector(connector);
		// 访问项目地址
		Context root = new Context(server, "/", 1);
		root.addServlet(new ServletHolder(new DispatcherServlet()), "/");// http://localhost:6887/?area=CN&group=1000_G1&serverid=0
		root.addServlet(new ServletHolder(new ServerLoadServlet()), "/load/*");
		/**
		 * Get current online user of a specific server parameter:serverid add
		 * by zengxc 2014-3-14
		 */
		root.addServlet(new ServletHolder(new GetCCUServlet()), "/GetCCU/*");
		server.start();
		log.info("服务分区公告器servlet启动");
	}

	/**
	 * 打开服务器监听
	 * 
	 * @throws IOException
	 */
	private void openServerListener() throws Exception {

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
					p.addLast(new DispatchSessionHandler(new SessionRegistry()));
				}
			});
			b.option(ChannelOption.SO_REUSEADDR, true);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.childOption(ChannelOption.SO_KEEPALIVE, false);
			b.option(ChannelOption.SO_BACKLOG, 128);
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(this.configuration.getString("localip"), this.configuration.getInt("port")).sync();
			System.out.println("启动成功。。。");
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			// 释放线程池资源
			// System.out.println("释放线程池资源");
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

		log.info("服务分区公告器启动!");
	}
	/**
	 * 内部类 <code>DispatchSessionHandler</code>ip 分配器相关Handler
	 * 
	 * @see com.app.session.ServerHandler
	 * @since JDK 1.6
	 */
	class DispatchSessionHandler extends ServerHandler {
		public DispatchSessionHandler(SessionRegistry paramSessionRegistry) {
			super(paramSessionRegistry);
		}
		@Override
		public Session createSession(Channel channel) {
			return new DispatchSession(channel);
		}
	}
}
