package com.app.empire.world;

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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.app.empire.protocol.Protocol;
import com.app.empire.world.common.util.HttpClientUtil;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.empire.world.session.ConnectSession;
import com.app.empire.world.session.WorldHandler;
import com.app.empire.world.skeleton.AccountSkeleton;
import com.app.empire.world.skeleton.BattleSkeleton;
import com.app.net.IRequestService;
import com.app.net.ProtocolFactory;
import com.app.protocol.data.DataBeanDecoder;
import com.app.protocol.data.DataBeanEncoder;
import com.app.protocol.s2s.S2SDecoder;
import com.app.protocol.s2s.S2SEncoder;
import com.app.session.Session;
import com.app.session.SessionRegistry;

/**
 * 类 WorldServer 游戏世界入口类。 创建游戏世界中各服务，创建监听端口、连接帐号服务器
 * 
 * @since JDK 1.7
 */

public class WorldServer {
	private static final Logger log = Logger.getLogger(WorldServer.class);
	public IRequestService requestService;
	public static WorldServer instance = null;
	private Configuration configuration = null;
	public static ServerConfig config;
	private ApplicationContext context;

	/**
	 * 描述：启动服务
	 * 
	 * @throws Exception
	 */

	public void launch() throws Exception {
		long time = System.currentTimeMillis();
		// 加载协议BeanData和Handler类及对象
		ProtocolFactory.init(Protocol.class, "com.app.empire.protocol.data", "com.app.empire.world.server.handler");
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ServiceManager sm = context.getBean(ServiceManager.class);
		ServiceManager.setServiceManager(sm);
		SessionRegistry registry = new SessionRegistry();
		sm.getConnectService().setRegistry(registry);

		// 加载游戏配置数据
		ServiceManager.getManager().getGameConfigService().load();
		// 加载configWorld.properties配置,读取配置文件内最大等级限制
		this.configuration = ServiceManager.getManager().getConfiguration();
		config = new ServerConfig(this.configuration);
		// 初始化进程
		ServiceManager.getManager().init();
		// 是否在维护
		String maintance = this.configuration.getString("maintance");
		if (maintance == null) {
			config.setMaintance(true);
		} else if (maintance.toLowerCase().equals("false")) {
			config.setMaintance(false);
		}
		log.info("connect auth");
		new Thread(new ConnectAccount()).start();
		// connectBattleService();//跨服对战
		// 启动世界服务器
		new Thread(new Bind(new ConnectSessionHandler(registry))).start();
		// bind(new ConnectSessionHandler(registry));
		// 启动游戏管理服务
		openManagerServlet();
		System.out.println("游戏世界服务器启动......");
		System.out.println("use time:" + ((System.currentTimeMillis() - time) / 1000) + "秒");

		// 同步GM工具中的服务器信息
		if (null != configuration.getString("adminurl")) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("{\"serverId\":\"");
				sb.append(configuration.getString("serviceid"));
				sb.append("\",\"ip\":\"");
				sb.append(configuration.getString("callbackip"));
				sb.append("\",\"port\":\"");
				sb.append(configuration.getString("adminport"));
				sb.append("\",\"area\":\"");
				sb.append(configuration.getString("areaid"));
				sb.append("\"}");
				List<NameValuePair> data = new ArrayList<NameValuePair>();
				data.add(new NameValuePair("param", sb.toString()));
				System.out.println(HttpClientUtil.PostData(this.configuration.getString("adminurl") + "/updateWorld/updateWorld.action",
						data));
			} catch (Exception e) {
			}
		}
		// 同步充值服务器信息
		if (null != configuration.getString("rechargeurl")) {
			try {
				// 同步GM工具中的服务器信息
				StringBuffer sb = new StringBuffer();
				sb.append("{\"serverId\":\"");
				sb.append(configuration.getString("serviceid"));
				sb.append("\",\"ip\":\"");
				sb.append(configuration.getString("callbackip"));
				sb.append("\",\"port\":\"");
				sb.append(configuration.getString("http"));
				sb.append("\"}");
				List<NameValuePair> data = new ArrayList<NameValuePair>();
				data.add(new NameValuePair("param", sb.toString()));
				System.out.println(HttpClientUtil.PostData(this.configuration.getString("rechargeurl") + "/updateWorld/updateWorld.action",
						data));
			} catch (Exception e) {
			}
		}
	}
	/**
	 * 连接帐号服务
	 * 
	 * @param handler
	 * @throws Exception
	 */
	private class ConnectAccount implements Runnable {
		@Override
		public void run() {
			try {
				String authip = ServiceManager.getManager().getConfiguration().getString("authip");
				String authport = ServiceManager.getManager().getConfiguration().getString("authport");
				AccountSkeleton accountSkeleton = new AccountSkeleton("accountskeleton", new InetSocketAddress(authip,
						Integer.parseInt(authport)));
				ServiceManager.getManager().setAccountSkeleton(accountSkeleton);
				accountSkeleton.connect();
				log.info("Account auth connected");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 启动游戏世界，启动端口监听
	 * 
	 * @param registry
	 * @param sessionHandler
	 * @throws Exception
	 */
	class Bind implements Runnable {
		WorldHandler sessionHandler;
		public Bind(WorldHandler sessionHandler) {
			this.sessionHandler = sessionHandler;
		}
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
						p.addLast(new IdleStateHandler(0, 0, 180));
						p.addLast(new S2SEncoder());
						p.addLast(new S2SDecoder());
						p.addLast(new DataBeanEncoder());
						p.addLast(new DataBeanDecoder());
						p.addLast(sessionHandler);
					}
				});
				b.option(ChannelOption.SO_REUSEADDR, true);
				b.option(ChannelOption.TCP_NODELAY, true);
				b.childOption(ChannelOption.SO_KEEPALIVE, false);
				b.option(ChannelOption.SO_BACKLOG, 128);
				// 绑定端口，同步等待成功
				ChannelFuture f = b
						.bind(WorldServer.this.configuration.getString("localip"), WorldServer.this.configuration.getInt("port")).sync();
				System.out.println("游戏逻辑服启动成功。。。");
				// 等待服务端监听端口关闭
				f.channel().closeFuture().sync();
			} catch (Exception e) {
				System.out.println("游戏逻辑服启动失败!!!");
				WorldServer.log.error(e.getMessage(), e);
			} finally {
				// 释放线程池资源
				System.out.println("释放线程池资源");
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		}
	}

	/**
	 * 后台管理服务
	 *
	 * @throws Exception
	 */
	private void openManagerServlet() throws Exception {
		org.mortbay.jetty.Server server = new org.mortbay.jetty.Server();
		// // 设置jetty线程池
		// BoundedThreadPool threadPool = new BoundedThreadPool();
		// // 设置连接参数
		// threadPool.setMinThreads(10);
		// threadPool.setMaxThreads(50);
		// 设置监听端口，ip地址
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(ServiceManager.getManager().getConfiguration().getInt("httpPort"));
		connector.setHost(ServiceManager.getManager().getConfiguration().getString("localip"));
		server.addConnector(connector);
		// 访问项目地址import org.mortbay.jetty.servlet.Context;
		Context root = new Context(server, "/", 1);

		//
		// // 网易的充值平台回调地址
		// root.addServlet(new ServletHolder(new CallBackServlet()),
		// "/callback/*");
		// root.addServlet(new ServletHolder(new PointCallBackServlet()),
		// "/pointcallback/*");
		//
		// // 爱游戏平台服务器验证
		// root.addServlet(new ServletHolder(new SynchronousServlet()),
		// "/synchronous/*");
		// // EFUN充值接入
		// root.addServlet(new ServletHolder(new RechargeServlet()),
		// "/recharge/*");
		// // EFUN充值接入(新)
		// root.addServlet(new ServletHolder(new RechargeNewServlet()),
		// "/rechargeNew/*");
		// // tapjoy接入
		// root.addServlet(new ServletHolder(new TapzoyServlet()), "/tapjoy/*");
		// // 第三方支付获取玩家信息接口
		// root.addServlet(new ServletHolder(new GetPlayerInfoServlet()),
		// "/getPlayerInfo/*");
		// // 外部抽奖检查用户信息是否正确
		// root.addServlet(new ServletHolder(new CheckPlayerInfoServlet()),
		// "/checkinfo/*");
		// // 外部抽奖发放奖励
		// root.addServlet(new ServletHolder(new ItemsGivenServlet()),
		// "/itemsgiven/*");
		// // 批量发放奖励
		// root.addServlet(new ServletHolder(new BatchRunSendRewardServlet()),
		// "/BatchRunSendReward/*");
		// // 批量发放宠物
		// root.addServlet(new ServletHolder(new GiftPetByGMServlet()),
		// "/GiftPetByGM/*");
		// // 意见箱导出excel
		// root.addServlet(new ServletHolder(new ExportExcelOPServlet()),
		// "/ExportExcelOP/*");
		// // 查询发放物品日志
		// root.addServlet(new ServletHolder(new GetItemLogServlet()),
		// "/GetItemLog/*");
		// // 查询发放金币日志
		// root.addServlet(new ServletHolder(new GetGoldCountLogServlet()),
		// "/GetGoldCountLog/*");
		// // 查询强化日志
		// root.addServlet(new ServletHolder(new GetStrongRecordLogServlet()),
		// "/GetStrongRecordLog/*");
		//
		//
		server.start();
	}
	/**
	 * 跨服对战服务
	 * 
	 * @param handler
	 * @throws Exception
	 */
	private void connectBattleService() throws Exception {
		String battleip = ServiceManager.getManager().getConfiguration().getString("battleip");
		String battleport = ServiceManager.getManager().getConfiguration().getString("battleport");
		if (null != battleip && null != battleport) {
			BattleSkeleton battleSkeleton = new BattleSkeleton("battleSkeleton", new InetSocketAddress(battleip,
					Integer.parseInt(battleport)));
			battleSkeleton.connect();
			log.info("Battle auth connected");
			ServiceManager.getManager().setBattleSkeleton(battleSkeleton);
		}
	}

	public static void main(String[] args) {
		try {
			instance = new WorldServer();
			instance.launch();
		} catch (Exception e) {
			e.printStackTrace();
			// log.info(e);
			try {
				System.in.read();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	class ConnectSessionHandler extends WorldHandler {
		
		public ConnectSessionHandler(SessionRegistry sessionRegistry) {
			super(sessionRegistry);
		}
		/**
		 * 创建一个　Session　类
		 * 
		 * @param IoSession
		 *            　 Dispatch的链接IoSession
		 * @return ConnectSession
		 */
		@Override
		public Session createSession(Channel channel) {
			ConnectSession connSession = new ConnectSession(channel);
			ServiceManager serviceManager = ServiceManager.getManager();
			connSession.setAccountSkeleton(serviceManager.getAccountSkeleton());
			connSession.setPlayerService(serviceManager.getPlayerService());
			return connSession;
		}
	}
}
