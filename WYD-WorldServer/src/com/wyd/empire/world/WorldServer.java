package com.wyd.empire.world;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.servlet.BatchRunSendRewardServlet;
import com.wyd.empire.world.servlet.CallBackServlet;
import com.wyd.empire.world.servlet.CheckPlayerInfoServlet;
import com.wyd.empire.world.servlet.ExportExcelOPServlet;
import com.wyd.empire.world.servlet.GetGoldCountLogServlet;
import com.wyd.empire.world.servlet.GetItemLogServlet;
import com.wyd.empire.world.servlet.GetPlayerInfoServlet;
import com.wyd.empire.world.servlet.GetStrongRecordLogServlet;
import com.wyd.empire.world.servlet.GiftPetByGMServlet;
import com.wyd.empire.world.servlet.ItemsGivenServlet;
import com.wyd.empire.world.servlet.PointCallBackServlet;
import com.wyd.empire.world.servlet.RechargeNewServlet;
import com.wyd.empire.world.servlet.RechargeServlet;
import com.wyd.empire.world.servlet.SynchronousServlet;
import com.wyd.empire.world.servlet.TapzoyServlet;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.empire.world.session.AuthSession;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.session.WorldHandler;
import com.wyd.empire.world.skeleton.AccountSkeleton;
import com.wyd.empire.world.skeleton.BattleSkeleton;
import com.wyd.empire.world.skeleton.NearbySkeleton;
import com.wyd.net.IRequestService;
import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.DataBeanFilter;
import com.wyd.protocol.s2s.S2SDecoder;
import com.wyd.protocol.s2s.S2SEncoder;
import com.wyd.session.Session;
import com.wyd.session.SessionHandler;
import com.wyd.session.SessionRegistry;

/**
 * 类 WorldServer 游戏世界入口类。 创建游戏世界中各服务，创建监听端口、连接帐号服务器
 * 
 * @since JDK 1.7
 */

public class WorldServer {
	private static final Logger log = Logger.getLogger(WorldServer.class);
	public IRequestService requestService;
	public static WorldServer instance = null;
	public static ServerConfig config;

	/**
	 * 描述：启动服务
	 * 
	 * @throws Exception
	 */
	public void launch() throws Exception {
		long time = System.currentTimeMillis();
		// 加载协议BeanData和Handler类及对象
		ProtocolFactory.init(Protocol.class, "com.wyd.empire.protocol.data", "com.wyd.empire.world.server.handler");
		// 加载configWorld.properties配置,读取配置文件内最大等级限制
		Configuration configuration = ServiceManager.getManager().getConfiguration();
		WorldServer.config = new ServerConfig(configuration);
		// 初始化基础数据
		ServiceManager.getManager().initBaseData();
		// 初始化进程
		ServiceManager.getManager().init();
		// 初始化游戏日志服务
		ServiceManager.getManager().getLogSerivce().initialLogService();
		// 初始化首冲奖励、抽奖列表
		ServiceManager.getManager().getRechargeRewardService().findInitList();
		// 初始化自定义推荐列表
		ServiceManager.getManager().getShopItemService().getRecommendList();
		// 初始化排行榜数据
		ServiceManager.getManager().getLogSerivce().initialTopRecord();
		// 是否在维护
		String maintance = null;
		maintance = configuration.getString("maintance");
		if (maintance == null) {
			config.setMaintance(true);
		} else if (maintance.toLowerCase().equals("false")) {
			config.setMaintance(false);
		}
		// 创建SessionRegistry会话注册类，用于快速查找IoSession与Session子类的映射关系。
		SessionRegistry registry = new SessionRegistry();
		// 创建ConnectSessionHandler，是IoHandler子类，并创建会话ConnectSession——
		// 其包含IoSession接口，并持有所需服务的引用。
		log.info("connect auth");
		// 连接GameAccountServer服务器
		connectAccountService(new AuthSessionHandler(registry));
		log.info("AccountService connected");
		connectBattleService(new AuthSessionHandler(registry));
		log.info("BattleService connected");
		connectNearbyService(new AuthSessionHandler(registry));
		log.info("NearbyService connected");
		// 启动世界服务器
		WorldHandler connectSessionHandler = new ConnectSessionHandler(registry);
		bind(registry, connectSessionHandler);
		// 启动游戏管理服务
		AdminSessionHandler adminSessionHandler = new AdminSessionHandler(registry);
		bindAdmin(adminSessionHandler);
		openManagerServlet();
		log.info("《弹弹岛》游戏世界服务器启动...");
		System.out.println("《弹弹岛》游戏世界服务器启动...");
		System.out.println("login time:" + ((System.currentTimeMillis() - time) / 1000) + "秒");
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
				System.out.println(HttpClientUtil.PostData(configuration.getString("adminurl") + "/updateWorld/updateWorld.action", data));
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
				System.out
						.println(HttpClientUtil.PostData(configuration.getString("rechargeurl") + "/updateWorld/updateWorld.action", data));
			} catch (Exception e) {
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
		connector.setPort(ServiceManager.getManager().getConfiguration().getInt("http"));
		connector.setHost(ServiceManager.getManager().getConfiguration().getString("localip"));
		server.addConnector(connector);
		// 访问项目地址
		Context root = new Context(server, "/", 1);
		// 网易的充值平台回调地址
		root.addServlet(new ServletHolder(new CallBackServlet()), "/callback/*");
		root.addServlet(new ServletHolder(new PointCallBackServlet()), "/pointcallback/*");

		// 爱游戏平台服务器验证
		root.addServlet(new ServletHolder(new SynchronousServlet()), "/synchronous/*");
		// EFUN充值接入
		root.addServlet(new ServletHolder(new RechargeServlet()), "/recharge/*");
		// EFUN充值接入(新)
		root.addServlet(new ServletHolder(new RechargeNewServlet()), "/rechargeNew/*");
		// tapjoy接入
		root.addServlet(new ServletHolder(new TapzoyServlet()), "/tapjoy/*");
		// 第三方支付获取玩家信息接口
		root.addServlet(new ServletHolder(new GetPlayerInfoServlet()), "/getPlayerInfo/*");
		// 外部抽奖检查用户信息是否正确
		root.addServlet(new ServletHolder(new CheckPlayerInfoServlet()), "/checkinfo/*");
		// 外部抽奖发放奖励
		root.addServlet(new ServletHolder(new ItemsGivenServlet()), "/itemsgiven/*");
		// 批量发放奖励
		root.addServlet(new ServletHolder(new BatchRunSendRewardServlet()), "/BatchRunSendReward/*");
		// 批量发放宠物
		root.addServlet(new ServletHolder(new GiftPetByGMServlet()), "/GiftPetByGM/*");
		// 意见箱导出excel
		root.addServlet(new ServletHolder(new ExportExcelOPServlet()), "/ExportExcelOP/*");
		// 查询发放物品日志
		root.addServlet(new ServletHolder(new GetItemLogServlet()), "/GetItemLog/*");
		// 查询发放金币日志
		root.addServlet(new ServletHolder(new GetGoldCountLogServlet()), "/GetGoldCountLog/*");
		// 查询强化日志
		root.addServlet(new ServletHolder(new GetStrongRecordLogServlet()), "/GetStrongRecordLog/*");
		server.start();
	}

	/**
	 * 连接帐号服务
	 * 
	 * @param handler
	 * @throws Exception
	 */
	private void connectAccountService(AuthSessionHandler handler) throws Exception {
		String authip = ServiceManager.getManager().getConfiguration().getString("authip");
		String authport = ServiceManager.getManager().getConfiguration().getString("authport");
		AccountSkeleton accountSkeleton = new AccountSkeleton("accountskeleton", new InetSocketAddress(authip, Integer.parseInt(authport)));
		accountSkeleton.setUserName(config.getAreaId());
		accountSkeleton.setPassword(ServiceManager.getManager().getConfiguration().getString("serverpassword"));
		accountSkeleton.connect();
		if (accountSkeleton.isConnected())
			System.out.println("账号服务器链接成功！");
		else
			System.out.println("账号服务器链接失败！");

		log.info("Account auth connected");
		ServiceManager.getManager().setAccountSkeleton(accountSkeleton);
	}

	/**
	 * 跨服对战服务
	 * 
	 * @param handler
	 * @throws Exception
	 */
	private void connectBattleService(AuthSessionHandler handler) throws Exception {
		String battleip = ServiceManager.getManager().getConfiguration().getString("battleip");
		String battleport = ServiceManager.getManager().getConfiguration().getString("battleport");
		if (null != battleip && null != battleport) {
			BattleSkeleton battleSkeleton = new BattleSkeleton("battleSkeleton", new InetSocketAddress(battleip,
					Integer.parseInt(battleport)));
			battleSkeleton.setUserName(config.getMachineCode() + "");
			battleSkeleton.setPassword(ServiceManager.getManager().getConfiguration().getString("serverpassword"));
			battleSkeleton.connect();
			log.info("Battle auth connected");
			ServiceManager.getManager().setBattleSkeleton(battleSkeleton);
		}
	}

	/**
	 * 附近好友服务
	 * 
	 * @param handler
	 * @throws Exception
	 */
	private void connectNearbyService(AuthSessionHandler handler) throws Exception {
		String nearbyip = ServiceManager.getManager().getConfiguration().getString("nearbyip");
		String nearbyport = ServiceManager.getManager().getConfiguration().getString("nearbyport");
		if (null != nearbyip && null != nearbyport) {
			NearbySkeleton nearbySkeleton = new NearbySkeleton("nearbySkeleton", new InetSocketAddress(nearbyip,
					Integer.parseInt(nearbyport)));
			nearbySkeleton.setUserName(config.getMachineCode() + "");
			nearbySkeleton.setPassword(ServiceManager.getManager().getConfiguration().getString("serverpassword"));
			nearbySkeleton.connect();
			log.info("Nearby auth connected");
			ServiceManager.getManager().getNearbyService().setNearbySkeleton(nearbySkeleton);
		}
	}

	private void bindAdmin(SessionHandler sessionHandler) throws Exception {
		NioSocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		acceptor.getFilterChain().addFirst("uwap2databean", new DataBeanFilter());
		acceptor.getFilterChain().addFirst("uwap2codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
		acceptor.setHandler(sessionHandler);
		acceptor.setDefaultLocalAddress(new InetSocketAddress(ServiceManager.getManager().getConfiguration().getString("localip"),
				ServiceManager.getManager().getConfiguration().getInt("adminport")));
		acceptor.bind();
	}

	/**
	 * 启动游戏世界，启动端口监听
	 * 
	 * @param registry
	 * @param sessionHandler
	 * @throws Exception
	 */
	private void bind(SessionRegistry registry, WorldHandler sessionHandler) throws Exception {
		NioSocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		// 添加IoHandler处理线程池
		acceptor.getFilterChain().addFirst("uwap2databean", new DataBeanFilter());
		acceptor.getFilterChain().addFirst("uwap2codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
		acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(4, 16));
		// 会话配置
		acceptor.getSessionConfig().setReceiveBufferSize(ServiceManager.getManager().getConfiguration().getInt("receivebuffsize"));
		acceptor.getSessionConfig().setSendBufferSize(ServiceManager.getManager().getConfiguration().getInt("writebuffsize"));
		acceptor.getSessionConfig().setTcpNoDelay(ServiceManager.getManager().getConfiguration().getBoolean("tcpnodelay"));
		acceptor.setHandler(sessionHandler);
		acceptor.setDefaultLocalAddress(new InetSocketAddress(ServiceManager.getManager().getConfiguration().getString("localip"),
				ServiceManager.getManager().getConfiguration().getInt("port")));
		// 监听
		acceptor.bind();
		ServiceManager.getManager().getConnectService().setAcceptor(acceptor);
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

	class AuthSessionHandler extends SessionHandler {
		public AuthSessionHandler(SessionRegistry paramSessionRegistry) {
			super(paramSessionRegistry);
		}

		@Override
		public Session createSession(IoSession session) {
			return new AuthSession(session);
		}

		@Override
		public void inputClosed(IoSession arg0) throws Exception {
			// TODO Auto-generated method stub

		}
	}

	static class AdminSessionHandler extends SessionHandler {
		@Override
		public Session createSession(IoSession session) {
			AdminSession ret = new AdminSession(session);
			return ret;
		}

		public AdminSessionHandler(SessionRegistry paramSessionRegistry) {
			super(paramSessionRegistry);
		}

		@Override
		public void messageReceived(IoSession session, Object msg) throws Exception {
			super.messageReceived(session, msg);
		}

		@Override
		public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
			super.exceptionCaught(arg0, arg1);
			// arg1.printStackTrace();
		}

		@Override
		public void inputClosed(IoSession arg0) throws Exception {
			// TODO Auto-generated method stub

		}
	}

	static class ConnectSessionHandler extends WorldHandler {

		/**
		 * 创建一个　Session　类
		 * 
		 * @param IoSession
		 *            　 Dispatch的链接IoSession
		 * @return ConnectSession
		 */
		@Override
		public Session createSession(IoSession session) {
			ConnectSession connSession = new ConnectSession(session);
			ServiceManager serviceManager = ServiceManager.getManager();
			connSession.setConnectService(serviceManager.getConnectService());
			connSession.setAccountSkeleton(serviceManager.getAccountSkeleton());
			connSession.setPlayerService(serviceManager.getPlayerService());
			return connSession;
		}

		public ConnectSessionHandler(SessionRegistry paramSessionRegistry) {
			super(paramSessionRegistry);
		}

	}
}
