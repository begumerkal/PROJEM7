package com.wyd.empire.world.server.service.factory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.common.util.FundUtil;
import com.wyd.empire.world.common.util.ThreadPool;
import com.wyd.empire.world.server.service.base.IChatRecordService;
import com.wyd.empire.world.server.service.base.IMailService;
import com.wyd.empire.world.server.service.base.IOperationConfigService;
import com.wyd.empire.world.server.service.base.IOrderService;
import com.wyd.empire.world.server.service.base.IPayAppRewardService;
import com.wyd.empire.world.server.service.base.IPlayerService;
import com.wyd.empire.world.server.service.base.IThirdConfigService;
import com.wyd.empire.world.server.service.impl.AbstractService;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.server.service.impl.CheckRechargeService;
import com.wyd.empire.world.server.service.impl.ConnectService;
import com.wyd.empire.world.server.service.impl.CrossService;
import com.wyd.empire.world.server.service.impl.ExtensionService;
import com.wyd.empire.world.server.service.impl.MailService;
import com.wyd.empire.world.server.service.impl.OrderSerialService;
import com.wyd.empire.world.server.service.impl.SendMailService;
import com.wyd.empire.world.server.service.impl.TheadPlayerItemsService;
import com.wyd.empire.world.server.service.impl.VersionService;
import com.wyd.empire.world.skeleton.AccountSkeleton;
import com.wyd.empire.world.skeleton.BattleSkeleton;
import com.wyd.net.DefaultRequestService;
import com.wyd.net.IRequestService;
import com.wyd.session.HandlerMonitorService;

public class ServiceManager {
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml", "application-scheduling.xml"});
	private static ServiceManager instance = null;
	protected Configuration configuration = null;
	private IRequestService requestService;
	private AccountSkeleton accountSkeleton = null;
	private BattleSkeleton battleSkeleton = null;
	private ConnectService connectService;
	private HandlerMonitorService msghandlerMonitor;
	private TheadPlayerItemsService theadPlayerItemsService;
	private VersionService versionService;
	private ChatService chatService;
    private PlayerService  playerService;
	private MailService mailService;
	private ExtensionService extensionService;
	private SendMailService sendMailService;
	private OrderSerialService orderSerialService;
	private CrossService crossService;
	private ThreadPool httpThreadPool;
	private ThreadPool simpleThreadPool;
	private CheckRechargeService checkRechargeService;
	private AbstractService abstractService;

	private ServiceManager() {
		try {
			// 加载游戏配置
			this.configuration = new PropertiesConfiguration("configWorld.properties");
			// 请求服务
			this.requestService = new DefaultRequestService();
			// 连接服务
			this.connectService = new ConnectService();
			// 游戏角色服务
			this.playerService = new PlayerService();
			// 消息处理监控线程
			this.msghandlerMonitor = new HandlerMonitorService();
			// 游戏版本管理对象
			this.versionService = new VersionService((IOperationConfigService) context.getBean("OperationConfigService"));
			// 聊天服务管理对象
			this.chatService = new ChatService();
			// 公告管理对象
			// 邮件批量发送服务
			this.mailService = new MailService();
			// 推广渠道激活服务
			this.extensionService = new ExtensionService();
			// 充值对账
			this.checkRechargeService = new CheckRechargeService();
			// 发送电子邮件服务
			this.sendMailService = new SendMailService();
			this.orderSerialService = new OrderSerialService();
			// 跨服对战相关服务
			this.crossService = new CrossService();
			// 包含http任务的线程池
			this.httpThreadPool = new ThreadPool(20);
			// 简单任务的线程池
			this.simpleThreadPool = new ThreadPool(20);
			// 协议处理线程
			this.abstractService = new AbstractService();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		this.connectService.start();
		this.msghandlerMonitor.start();
		this.playerService.start();
		this.mailService.start();
		this.extensionService.start();
		this.sendMailService.start();
	}

	public static ServiceManager getManager() {
		synchronized (ServiceManager.class) {
			if (null == instance) {
				instance = new ServiceManager();
			}
		}
		return instance;
	}

	/**
	 * 初始化基础数据
	 */
	public void initBaseData() {
		System.out.println("initData...");
		ServiceManager.getManager().getVersionService().getService().initData();
		ServiceManager.getManager().getOrderService().initData();
		FundUtil.initMap();
	}

	public IRequestService getRequestService() {
		return requestService;
	}

	public PlayerService getPlayerService() {
		return playerService;
	}

	public ConnectService getConnectService() {
		return connectService;
	}

	public AccountSkeleton getAccountSkeleton() {
		return this.accountSkeleton;
	}

	public void setAccountSkeleton(AccountSkeleton accountSkeleton) {
		this.accountSkeleton = accountSkeleton;
	}

	public BattleSkeleton getBattleSkeleton() {
		return battleSkeleton;
	}

	public void setBattleSkeleton(BattleSkeleton battleSkeleton) {
		this.battleSkeleton = battleSkeleton;
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public HandlerMonitorService getHandlerMonitor() {
		return msghandlerMonitor;
	}

	public ChatService getChatService() {
		return chatService;
	}

	public TheadPlayerItemsService getTheadPlayerItemsService() {
		return theadPlayerItemsService;
	}

	public VersionService getVersionService() {
		return versionService;
	}


	public IMailService getMailService() {
		return (IMailService) context.getBean("MailService");
	}


	public IPlayerService getIPlayerService() {
		return (IPlayerService) context.getBean("PlayerService");
	}


	public IChatRecordService getChatRecordService() {
		return (IChatRecordService) context.getBean("ChatRecordService");
	}


	/**
	 * 邮件批量发送服务
	 * 
	 * @return
	 */
	public MailService getSendMailService() {
		return mailService;
	}

	/**
	 * 推广渠道激活服务
	 */
	public ExtensionService getExtensionService() {
		return extensionService;
	}

	public CheckRechargeService getCheckRechargeService() {
		return checkRechargeService;
	}


	/**
	 * 发送电子邮件服务
	 * 
	 * @return
	 */
	public SendMailService getEMailService() {
		return sendMailService;
	}

	/**
	 * 订单与计费点管理服务
	 * 
	 * @return
	 */
	public IOrderService getOrderService() {
		return (IOrderService) context.getBean("OrderService");
	}

	/**
	 * 第三方渠道配置表 by: zengxc
	 * 
	 * @return
	 */
	public IThirdConfigService getThirdConfigService() {
		return (IThirdConfigService) context.getBean("ThirdConfigService");
	}

	public OrderSerialService getOrderSerialService() {
		return orderSerialService;
	}

	public ThreadPool getHttpThreadPool() {
		return httpThreadPool;
	}

	public ThreadPool getSimpleThreadPool() {
		return simpleThreadPool;
	}

	public IOperationConfigService getOperationConfigService() {
		return (IOperationConfigService) context.getBean("OperationConfigService");
	}


	public CrossService getCrossService() {
		return crossService;
	}

	/**
	 * BM付费包服务
	 * 
	 * @return
	 */
	public IPayAppRewardService getPayAppRewardService() {
		return (IPayAppRewardService) context.getBean(IPayAppRewardService.SERVICE_BEAN_ID);
	}


	/**
	 * 获取协议处理线程
	 * 
	 * @return
	 */
	public AbstractService getAbstractService() {
		return abstractService;
	}
 
}