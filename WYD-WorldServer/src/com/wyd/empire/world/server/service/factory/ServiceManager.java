package com.wyd.empire.world.server.service.factory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyd.empire.world.common.util.ThreadPool;
import com.wyd.empire.world.server.service.base.IPlayerService;
import com.wyd.empire.world.server.service.base.impl.MailService;
import com.wyd.empire.world.server.service.impl.AbstractService;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.server.service.impl.ConnectService;
import com.wyd.empire.world.server.service.impl.CrossService;
import com.wyd.empire.world.server.service.impl.ExtensionService;
import com.wyd.empire.world.server.service.impl.OrderSerialService;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.server.service.impl.SendMailService;
import com.wyd.empire.world.server.service.impl.TheadPlayerItemsService;
import com.wyd.empire.world.skeleton.AccountSkeleton;
import com.wyd.empire.world.skeleton.BattleSkeleton;
import com.wyd.net.DefaultRequestService;
import com.wyd.net.IRequestService;
import com.wyd.session.HandlerMonitorService;
@Service
public class ServiceManager {
	private static ServiceManager serviceManager;
	private ThreadPool httpThreadPool;
	private ThreadPool simpleThreadPool;
	protected Configuration configuration = null;
	private IRequestService requestService;
	private AccountSkeleton accountSkeleton = null;
	private BattleSkeleton battleSkeleton = null;
	private ConnectService connectService;
	private HandlerMonitorService msghandlerMonitor;
	private TheadPlayerItemsService theadPlayerItemsService;

	@Autowired
	private MailService mailService;// 邮件批量发送服务
	@Autowired
	private PlayerService playerService;// 游戏角色服务
	@Autowired
	private ChatService chatService;// 聊天服务管理对象
	@Autowired
	private ExtensionService extensionService;// 推广渠道激活服务
	@Autowired
	private SendMailService sendMailService;// 发送电子邮件服务
	@Autowired
	private OrderSerialService orderSerialService;
	@Autowired
	private CrossService crossService;// 跨服对战相关服务
	@Autowired
	private AbstractService abstractService;// 协议处理线程

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

			// this.versionService = new
			// VersionService((IOperationConfigService)
			// context.getBean("OperationConfigService"));
			// 聊天服务管理对象
			this.chatService = new ChatService();
			// 公告管理对象
			// 邮件批量发送服务
			this.mailService = new MailService();
			// 推广渠道激活服务
			this.extensionService = new ExtensionService();

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
		this.extensionService.start();
		this.sendMailService.start();
	}

	public static ServiceManager getManager() {
		return serviceManager;
	}

	public static void setServiceManager(ServiceManager serviceManager) {
		ServiceManager.serviceManager = serviceManager;
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

	public IPlayerService getIPlayerService() {
		return (IPlayerService) playerService;
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

	/**
	 * 发送电子邮件服务
	 * 
	 * @return
	 */
	public SendMailService getEMailService() {
		return sendMailService;
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

	public CrossService getCrossService() {
		return crossService;
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