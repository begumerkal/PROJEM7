package com.app.empire.world.service.factory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.empire.world.common.util.ThreadPool;
import com.app.empire.world.service.base.impl.ChatService;
import com.app.empire.world.service.base.impl.GameConfigService;
import com.app.empire.world.service.base.impl.GameLogService;
import com.app.empire.world.service.base.impl.PlayerService;
import com.app.empire.world.service.impl.AbstractService;
import com.app.empire.world.service.impl.ConnectService;
//import com.app.empire.world.service.impl.CrossService;
import com.app.empire.world.service.impl.OrderSerialService;
import com.app.empire.world.service.impl.SendMailService;
import com.app.empire.world.skeleton.AccountSkeleton;
import com.app.empire.world.skeleton.BattleSkeleton;
import com.app.net.DefaultRequestService;
import com.app.net.IRequestService;

@Service
public class ServiceManager {
	private static ServiceManager serviceManager;
	private ThreadPool httpThreadPool;
	private ThreadPool simpleThreadPool;
	protected Configuration configuration = null;
	private IRequestService requestService;
	private AccountSkeleton accountSkeleton = null;
	private BattleSkeleton battleSkeleton = null;

	@Autowired
	private GameConfigService gameConfigService;
	@Autowired
	private GameLogService gameLogService;
	@Autowired
	private PlayerService playerService;// 游戏角色服务
	@Autowired
	private ChatService chatService;// 聊天服务管理对象
	@Autowired
	private SendMailService sendMailService;// 发送电子邮件服务
	@Autowired
	private OrderSerialService orderSerialService;
//	@Autowired
//	private CrossService crossService;// 跨服对战相关服务
	@Autowired
	private AbstractService abstractService;// 协议处理线程
	@Autowired
	private ConnectService connectService;// 连接服务

	private ServiceManager() {
		try {
			// 加载游戏配置
			this.configuration = new PropertiesConfiguration("configWorld.properties");
			// 请求服务
			this.requestService = new DefaultRequestService();
			// 包含http任务的线程池
			this.httpThreadPool = new ThreadPool(20);
			// 简单任务的线程池
			this.simpleThreadPool = new ThreadPool(20);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		this.connectService.start();// 通知dispatcher server 服务器最大玩家人数信息
		this.playerService.start();
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

	public ChatService getChatService() {
		return chatService;
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

//	public CrossService getCrossService() {
//		return crossService;
//	}

	/**
	 * 获取协议处理线程
	 * 
	 * @return
	 */
	public AbstractService getAbstractService() {
		return abstractService;
	}

	public GameConfigService getGameConfigService() {
		return gameConfigService;
	}

	public GameLogService getGameLogService() {
		return gameLogService;
	}
	

}