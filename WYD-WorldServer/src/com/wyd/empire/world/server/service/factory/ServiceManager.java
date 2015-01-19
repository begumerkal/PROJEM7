package com.wyd.empire.world.server.service.factory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wyd.empire.world.common.util.FundUtil;
import com.wyd.empire.world.common.util.ThreadPool;
import com.wyd.empire.world.server.service.base.IActivitiesAwardService;
import com.wyd.empire.world.server.service.base.IAdminService;
import com.wyd.empire.world.server.service.base.IBossmapBuffService;
import com.wyd.empire.world.server.service.base.IBossmapRewardService;
import com.wyd.empire.world.server.service.base.IBulletinService;
import com.wyd.empire.world.server.service.base.IChallengeService;
import com.wyd.empire.world.server.service.base.IChatRecordService;
import com.wyd.empire.world.server.service.base.IConsortiaService;
import com.wyd.empire.world.server.service.base.IDailyActivityService;
import com.wyd.empire.world.server.service.base.IDoEveryDayService;
import com.wyd.empire.world.server.service.base.IDownloadRewardService;
import com.wyd.empire.world.server.service.base.IDrawService;
import com.wyd.empire.world.server.service.base.IFriendService;
import com.wyd.empire.world.server.service.base.IGetLogService;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.base.IInterfaceService;
import com.wyd.empire.world.server.service.base.IInviteService;
import com.wyd.empire.world.server.service.base.ILimitedPriceService;
import com.wyd.empire.world.server.service.base.ILocalPushListService;
import com.wyd.empire.world.server.service.base.ILotteryService;
import com.wyd.empire.world.server.service.base.IMagnificationService;
import com.wyd.empire.world.server.service.base.IMailService;
import com.wyd.empire.world.server.service.base.IMapService;
import com.wyd.empire.world.server.service.base.IMarryService;
import com.wyd.empire.world.server.service.base.IMonthCardService;
import com.wyd.empire.world.server.service.base.IOperationConfigService;
import com.wyd.empire.world.server.service.base.IOrderService;
import com.wyd.empire.world.server.service.base.IPayAppRewardService;
import com.wyd.empire.world.server.service.base.IPetItemService;
import com.wyd.empire.world.server.service.base.IPictureUploadService;
import com.wyd.empire.world.server.service.base.IPlayerBillService;
import com.wyd.empire.world.server.service.base.IPlayerBossmapService;
import com.wyd.empire.world.server.service.base.IPlayerCardsService;
import com.wyd.empire.world.server.service.base.IPlayerDIYTitleService;
import com.wyd.empire.world.server.service.base.IPlayerFundService;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.base.IPlayerService;
import com.wyd.empire.world.server.service.base.IPlayerSinConsortiaService;
import com.wyd.empire.world.server.service.base.IPlayerStaWeekService;
import com.wyd.empire.world.server.service.base.IPlayerTaskTitleService;
import com.wyd.empire.world.server.service.base.IPracticeService;
import com.wyd.empire.world.server.service.base.IProbabilityService;
import com.wyd.empire.world.server.service.base.IPromotionsService;
import com.wyd.empire.world.server.service.base.IRandomNameService;
import com.wyd.empire.world.server.service.base.IRankRecordService;
import com.wyd.empire.world.server.service.base.IRechargeRewardService;
import com.wyd.empire.world.server.service.base.IRechargeService;
import com.wyd.empire.world.server.service.base.IRewardItemsService;
import com.wyd.empire.world.server.service.base.ISchedulingService;
import com.wyd.empire.world.server.service.base.IShareService;
import com.wyd.empire.world.server.service.base.IShopItemService;
import com.wyd.empire.world.server.service.base.IShopItemsPriceService;
import com.wyd.empire.world.server.service.base.ISingleMapService;
import com.wyd.empire.world.server.service.base.ISpreeGiftService;
import com.wyd.empire.world.server.service.base.IStarSoulService;
import com.wyd.empire.world.server.service.base.IStarsInfoService;
import com.wyd.empire.world.server.service.base.IStrengthenService;
import com.wyd.empire.world.server.service.base.IThirdConfigService;
import com.wyd.empire.world.server.service.base.ITitleService;
import com.wyd.empire.world.server.service.base.IToolsService;
import com.wyd.empire.world.server.service.base.IVigorService;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.impl.AbstractService;
import com.wyd.empire.world.server.service.impl.AdminService;
import com.wyd.empire.world.server.service.impl.BattleTeamService;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.empire.world.server.service.impl.BossRoomService;
import com.wyd.empire.world.server.service.impl.BuffService;
import com.wyd.empire.world.server.service.impl.BulletinService;
import com.wyd.empire.world.server.service.impl.ButtonInfoService;
import com.wyd.empire.world.server.service.impl.ChallengeService;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.server.service.impl.CheckRechargeService;
import com.wyd.empire.world.server.service.impl.ConnectService;
import com.wyd.empire.world.server.service.impl.CrossService;
import com.wyd.empire.world.server.service.impl.DailyActivityService;
import com.wyd.empire.world.server.service.impl.DelayOrderService;
import com.wyd.empire.world.server.service.impl.ExchangeService;
import com.wyd.empire.world.server.service.impl.ExtensionService;
import com.wyd.empire.world.server.service.impl.IntegralService;
import com.wyd.empire.world.server.service.impl.InviteService;
import com.wyd.empire.world.server.service.impl.LocalPushListService;
import com.wyd.empire.world.server.service.impl.MailService;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.server.service.impl.MonthCardService;
import com.wyd.empire.world.server.service.impl.NearbyService;
import com.wyd.empire.world.server.service.impl.OrderSerialService;
import com.wyd.empire.world.server.service.impl.PairService;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.server.service.impl.PracticeService;
import com.wyd.empire.world.server.service.impl.PromotionsService;
import com.wyd.empire.world.server.service.impl.PushService;
import com.wyd.empire.world.server.service.impl.RankPairService;
import com.wyd.empire.world.server.service.impl.RechargeService;
import com.wyd.empire.world.server.service.impl.RobotService;
import com.wyd.empire.world.server.service.impl.RoomService;
import com.wyd.empire.world.server.service.impl.SendMailService;
import com.wyd.empire.world.server.service.impl.StarSoulService;
import com.wyd.empire.world.server.service.impl.SystemLogService;
import com.wyd.empire.world.server.service.impl.TasksService;
import com.wyd.empire.world.server.service.impl.TheadConsortiaService;
import com.wyd.empire.world.server.service.impl.TheadFullServiceRewardService;
import com.wyd.empire.world.server.service.impl.TheadMailService;
import com.wyd.empire.world.server.service.impl.TheadMarryService;
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
	private PlayerService playerService;
	private ConnectService connectService;
	private HandlerMonitorService msghandlerMonitor;
	private RoomService roomService;
	private BattleTeamService battleTeamService;
	private PairService pairService;
	private RankPairService rankPairService;
	private TheadMailService theadMailService;
	private TheadPlayerItemsService theadPlayerItemsService;
	private TheadConsortiaService theadConsortiaService;
	private VersionService versionService;
	private ChatService chatService;
	private MapsService mapsService;
	private BuffService buffService;
	private BulletinService bulletinService;
	private BossRoomService bossRoomService;
	private BossBattleTeamService bossBattleTeamService;
	private RechargeService rechargeService;
	private DelayOrderService delayOrderService;
	private AdminService adminService;
	private MailService mailService;
	private ExtensionService extensionService;
	private ExchangeService exchangeService;
	private PromotionsService promotionsService;
	private TheadMarryService theadMarryService;
	private SendMailService sendMailService;
	private InviteService inviteService;
	private OrderSerialService orderSerialService;
	private PushService pushService;
	private DailyActivityService dailyActivityService;
	private CrossService crossService;
	private ThreadPool httpThreadPool;
	private ThreadPool simpleThreadPool;
	private ChallengeService challengeService;
	private IntegralService threadIntegralService;
	private CheckRechargeService checkRechargeService;
	private ButtonInfoService buttonInfoService;
	private AbstractService abstractService;
	private NearbyService nearbyService;
	private RobotService robotService;
	private StarSoulService starSoulService;
	private PracticeService practiceService;
	private LocalPushListService localPushListService;
	private MonthCardService monthCardService;

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
			// 弹弹岛房间服务
			this.roomService = new RoomService();
			// 弹弹岛房间服务
			this.challengeService = new ChallengeService();
			// 弹弹岛战斗组管理对象
			this.battleTeamService = new BattleTeamService();
			// 弹弹岛随机配对服务
			this.pairService = new PairService();
			// 弹弹岛随机配对服务
			this.rankPairService = new RankPairService();
			// 星魂服务
			this.starSoulService = new StarSoulService((IStarSoulService) context.getBean("StarSoulService"));
			// 月卡服务
			this.monthCardService = new MonthCardService((IMonthCardService) context.getBean("MonthCardService"));
			// 修炼服务
			this.practiceService = new PracticeService((IPracticeService) context.getBean("PracticeService"));
			// 弹弹岛邮件服务
			this.theadMailService = new TheadMailService((IMailService) context.getBean("MailService"));
			// 弹弹岛物品服务
			this.theadPlayerItemsService = new TheadPlayerItemsService(
					(IPlayerItemsFromShopService) context.getBean("PlayerItemsFromShopService"));
			// 弹弹岛公会服务
			this.theadConsortiaService = new TheadConsortiaService((IConsortiaService) context.getBean("ConsortiaService"));
			// 游戏版本管理对象
			this.versionService = new VersionService((IOperationConfigService) context.getBean("OperationConfigService"));
			// 聊天服务管理对象
			this.chatService = new ChatService();
			// 地图服务管理对象
			this.mapsService = new MapsService((IMapService) context.getBean("MapService"));
			// 玩家buff对象管理
			this.buffService = new BuffService();
			// 公告管理对象
			this.bulletinService = new BulletinService((IBulletinService) context.getBean("BulletinService"));
			// 弹弹岛副本房间服务
			this.bossRoomService = new BossRoomService();
			// 弹弹岛副本房间服务
			this.bossBattleTeamService = new BossBattleTeamService();
			this.rechargeService = new RechargeService();
			this.delayOrderService = new DelayOrderService();
			// gm服务
			this.adminService = new AdminService(getBaseAdminService());
			// 邮件批量发送服务
			this.mailService = new MailService();
			// 推广渠道激活服务
			this.extensionService = new ExtensionService();
			// 兑换服务器
			this.exchangeService = new ExchangeService();
			// 积分服务器
			this.threadIntegralService = new IntegralService();
			// 充值对账
			this.checkRechargeService = new CheckRechargeService();
			this.promotionsService = new PromotionsService((IPromotionsService) context.getBean("PromotionsService"));
			// 弹弹岛结婚管理
			this.theadMarryService = new TheadMarryService((IMarryService) context.getBean("MarryService"));
			// 发送电子邮件服务
			this.sendMailService = new SendMailService();
			// 邀请码服务
			this.inviteService = new InviteService((IInviteService) context.getBean("InviteService"));
			this.orderSerialService = new OrderSerialService();
			// 地推验证服务
			this.pushService = new PushService();
			this.dailyActivityService = new DailyActivityService(getIDailyActivityService());
			// 跨服对战相关服务
			this.crossService = new CrossService();
			// 包含http任务的线程池
			this.httpThreadPool = new ThreadPool(20);
			// 简单任务的线程池
			this.simpleThreadPool = new ThreadPool(20);
			// 协议处理线程
			this.abstractService = new AbstractService();
			this.buttonInfoService = new ButtonInfoService();
			this.nearbyService = new NearbyService();
			this.robotService = new RobotService();
			// 弹弹岛本地推送列表服务
			this.localPushListService = new LocalPushListService((ILocalPushListService) context.getBean("LocalPushListService"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		this.connectService.start();
		this.msghandlerMonitor.start();
		this.pairService.start();
		this.rankPairService.start();
		this.promotionsService.start();
		this.roomService.start();
		this.challengeService.start();
		this.playerService.start();
		this.battleTeamService.start();
		this.theadMailService.start();
		this.theadConsortiaService.start();
		this.buffService.start();
		this.bulletinService.start();
		this.bossRoomService.start();
		this.bossBattleTeamService.start();
		this.rechargeService.start();
		this.delayOrderService.start();
		this.mailService.start();
		this.extensionService.start();
		this.theadMarryService.start();
		this.sendMailService.start();
		this.robotService.start();
		// 全服发放奖励线程
		new TheadFullServiceRewardService((IRewardItemsService) context.getBean("RewardItemsService"));
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
		ServiceManager.getManager().getStarsInfoService().initData();
		ServiceManager.getManager().getGuaiService().initData();
		ServiceManager.getManager().getToolsService().initData();
		ServiceManager.getManager().getShopItemService().initData();
		ServiceManager.getManager().getStrengthenService().initData();
		ServiceManager.getManager().getProbabilityService().initData();
		ServiceManager.getManager().getBossmapRewardService().initData();
		ServiceManager.getManager().getTaskService().getService().initData();
		ServiceManager.getManager().getPlayerItemsFromShopService().initData();
		ServiceManager.getManager().getOrderService().initData();
		ServiceManager.getManager().getDrawService().initData();
		ServiceManager.getManager().getDailyActivityService().initData();
		ServiceManager.getManager().getTitleService().initData();
		ServiceManager.getManager().getRechargeService().initData();
		FundUtil.initMap();
		ServiceManager.getManager().getMarryService().initWeddingRoom();
		ServiceManager.getManager().getTaskService().initUpdateTaskTime();
		ServiceManager.getManager().getBossmapBuffService().initData();
		getChallengeSerService().inintData();
		ServiceManager.getManager().getStarSoulService().initStarSoulData();
		ServiceManager.getManager().getDoEveryDayService().initDoEveryDay();
		ServiceManager.getManager().getPracticeService().initPracticeData();
		// 初始化本地推送列表
		ServiceManager.getManager().getLocalPushListService().initPushData();
		ServiceManager.getManager().getMonthCardService().initMonthCardData();
		ServiceManager.getManager().getDownloadRewardService().initData();
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

	public RoomService getRoomService() {
		return roomService;
	}

	public ChallengeService getChallengeService() {
		return challengeService;
	}

	public BattleTeamService getBattleTeamService() {
		return battleTeamService;
	}

	public PairService getPairService() {
		return pairService;
	}

	public RankPairService getRankPairService() {
		return rankPairService;
	}

	public ChatService getChatService() {
		return chatService;
	}

	public IConsortiaService getConsortiaService() {
		return (IConsortiaService) context.getBean("ConsortiaService");
	}

	public IShopItemService getShopItemService() {
		return (IShopItemService) context.getBean("ShopItemService");
	}

	public IDrawService getDrawService() {
		return (IDrawService) context.getBean("DrawService");
	}

	public IShopItemsPriceService getiShopItemsPriceService() {
		return (IShopItemsPriceService) context.getBean("ShopItemsPriceService");
	}

	public IGetLogService getGetLogService() {
		return (IGetLogService) context.getBean("GetLogService");
	}

	public TasksService getTaskService() {
		return (TasksService) context.getBean("TasksService");
	}

	public TheadMailService getTheadMailService() {
		return theadMailService;
	}

	public LocalPushListService getLocalPushListService() {
		return localPushListService;
	}

	public TheadPlayerItemsService getTheadPlayerItemsService() {
		return theadPlayerItemsService;
	}

	public TheadConsortiaService getTheadConsortiaService() {
		return theadConsortiaService;
	}

	public IPlayerItemsFromShopService getPlayerItemsFromShopService() {
		return (IPlayerItemsFromShopService) context.getBean("PlayerItemsFromShopService");
	}

	public IPlayerSinConsortiaService getPlayerSinConsortiaService() {
		return (IPlayerSinConsortiaService) context.getBean("PlayerSinConsortiaService");
	}

	public VersionService getVersionService() {
		return versionService;
	}

	public IToolsService getToolsService() {
		return (IToolsService) context.getBean("ToolsService");
	}

	public IRewardItemsService getRewardItemsService() {
		return (IRewardItemsService) context.getBean("RewardItemsService");
	}

	public IRandomNameService getRandomNameService() {
		return (IRandomNameService) context.getBean("RandomNameService");
	}

	public IMagnificationService getMagnificationService() {
		return (IMagnificationService) context.getBean("MagnificationService");
	}

	public IMailService getMailService() {
		return (IMailService) context.getBean("MailService");
	}

	public IFriendService getFriendService() {
		return (IFriendService) context.getBean("FriendService");
	}

	public IInterfaceService getInterfaceService() {
		return (IInterfaceService) context.getBean("InterfaceService");
	}

	public IPlayerService getIPlayerService() {
		return (IPlayerService) context.getBean("PlayerService");
	}

	public IAdminService getBaseAdminService() {
		return (IAdminService) context.getBean("AdminService");
	}

	public IPlayerStaWeekService getPlayerStaWeekService() {
		return (IPlayerStaWeekService) context.getBean("PlayerStaWeekService");
	}

	public IRechargeService getRechargeService() {
		return (IRechargeService) context.getBean("RechargeService");
	}

	public IChatRecordService getChatRecordService() {
		return (IChatRecordService) context.getBean("ChatRecordService");
	}

	public IPlayerBossmapService getPlayerBossmapService() {
		return (IPlayerBossmapService) context.getBean("PlayerBossmapService");
	}

	public IBossmapRewardService getBossmapRewardService() {
		return (IBossmapRewardService) context.getBean("BossmapRewardService");
	}

	public IGuaiService getGuaiService() {
		return (IGuaiService) context.getBean("GuaiService");
	}

	public IStrengthenService getStrengthenService() {
		return (IStrengthenService) context.getBean("StrengthenService");
	}

	public IMarryService getMarryService() {
		return (IMarryService) context.getBean("MarryService");
	}

	public ILotteryService getLotteryService() {
		return (ILotteryService) context.getBean("LotteryService");
	}

	public IRankRecordService getRankRecordService() {
		return (IRankRecordService) context.getBean("RankRecordService");
	}

	public ISpreeGiftService getSpereeGiftService() {
		return (ISpreeGiftService) context.getBean("SpreeGiftService");
	}

	public IPictureUploadService getPictureUploadService() {
		return (IPictureUploadService) context.getBean("PictureUploadService");
	}

	public TheadMarryService getTheadMaarryService() {
		return theadMarryService;
	}

	public MapsService getMapsService() {
		return mapsService;
	}

	public BuffService getBuffService() {
		return buffService;
	}

	public SystemLogService getLogSerivce() {
		return (SystemLogService) context.getBean("SystemLogService");
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public BossRoomService getBossRoomService() {
		return bossRoomService;
	}

	public BossBattleTeamService getBossBattleTeamService() {
		return bossBattleTeamService;
	}

	/**
	 * 订单验证服务
	 * 
	 * @return
	 */
	public RechargeService getPCRechargeService() {
		return this.rechargeService;
	}

	public DelayOrderService getDelayOrderService() {
		return delayOrderService;
	}

	/**
	 * 代币变更日志
	 * 
	 * @return
	 */
	public IPlayerBillService getPlayerBillService() {
		return (IPlayerBillService) context.getBean("PlayerBillService");
	}

	/**
	 * gm服务
	 * 
	 * @return
	 */
	public AdminService getAdminService() {
		return adminService;
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
	 * 兑换服务
	 */
	public ExchangeService getExchangeService() {
		return exchangeService;
	}

	public IntegralService getThreadIntegralService() {
		return threadIntegralService;
	}

	public CheckRechargeService getCheckRechargeService() {
		return checkRechargeService;
	}

	public PromotionsService getPromotionsService() {
		return promotionsService;
	}

	/**
	 * 获取称号服务
	 */
	public ITitleService getTitleService() {
		return (ITitleService) context.getBean("TitleService");
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
	 * 获取首冲奖励、抽奖服务
	 */
	public IRechargeRewardService getRechargeRewardService() {
		return (IRechargeRewardService) context.getBean("RechargeRewardService");
	}

	/**
	 * 获取下载奖励服务
	 */
	public IDownloadRewardService getDownloadRewardService() {
		return (IDownloadRewardService) context.getBean("DownloadRewardService");
	}

	/**
	 * 活动奖励服务
	 */
	public IActivitiesAwardService getActivitiesAwardService() {
		return (IActivitiesAwardService) context.getBean("ActivitiesAwardService");
	}

	public ISchedulingService getSchedulingService() {
		return (ISchedulingService) context.getBean("SchedulingService");
	}

	/**
	 * 获取基金相关服务
	 * 
	 * @return 基金相关服务
	 */
	public IPlayerFundService getPlayerFundService() {
		return (IPlayerFundService) context.getBean("PlayerFundService");
	}

	/**
	 * 邀请码服务
	 * 
	 * @return 邀请码相关服务
	 */
	public InviteService getInviteService() {
		return inviteService;
	}

	/**
	 * 概率管理服务
	 * 
	 * @return
	 */
	public IProbabilityService getProbabilityService() {
		return (IProbabilityService) context.getBean("ProbabilityService");
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
	 * 升星信息管理服务
	 * 
	 * @return
	 */
	public IStarsInfoService getStarsInfoService() {
		return (IStarsInfoService) context.getBean("StarsInfoService");
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

	/**
	 * 地推帮助服务
	 * 
	 * @return
	 */
	public PushService getPushService() {
		return pushService;
	}

	/**
	 * 日常活动服务
	 */
	public DailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public IDailyActivityService getIDailyActivityService() {
		return (IDailyActivityService) context.getBean("DailyActivityService");
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

	public IPlayerPetService getPlayerPetService() {
		return (IPlayerPetService) context.getBean(IPlayerPetService.SERVICE_BEAN_ID);
	}

	public IPlayerCardsService getPlayerCardsService() {
		return (IPlayerCardsService) context.getBean(IPlayerCardsService.SERVICE_BEAN_ID);
	}

	public IPetItemService getPetItemService() {
		return (IPetItemService) context.getBean(IPetItemService.SERVICE_BEAN_ID);
	}

	public IWorldBossService getWorldBossService() {
		return (IWorldBossService) context.getBean(IWorldBossService.SERVICE_BEAN_ID);
	}

	public ISingleMapService getSingleMapService() {
		return (ISingleMapService) context.getBean(ISingleMapService.SERVICE_BEAN_ID);
	}

	public IChallengeService getChallengeSerService() {
		return (IChallengeService) context.getBean("ChallengeService");
	}

	public IPlayerDIYTitleService getPlayerDIYTitleService() {
		return (IPlayerDIYTitleService) context.getBean(IPlayerDIYTitleService.SERVICE_BEAN_ID);
	}

	public IBossmapBuffService getBossmapBuffService() {
		return (IBossmapBuffService) context.getBean(IBossmapBuffService.SERVICE_BEAN_ID);
	}

	public IVigorService getVigorService() {
		return (IVigorService) context.getBean(IVigorService.SERVICE_BEAN_ID);
	}

	public ILimitedPriceService getLimitedPriceService() {
		return (ILimitedPriceService) context.getBean(ILimitedPriceService.SERVICE_BEAN_ID);
	}

	public CrossService getCrossService() {
		return crossService;
	}

	/**
	 * 获取任务称号服务
	 * 
	 * @return 任务称号服务
	 */
	public IPlayerTaskTitleService getPlayerTaskTitleService() {
		return (IPlayerTaskTitleService) context.getBean("PlayerTaskTitleService");
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
	 * 获取按钮状态管理对象
	 * 
	 * @return
	 */
	public ButtonInfoService getButtonInfoService() {
		return buttonInfoService;
	}

	/**
	 * 获取协议处理线程
	 * 
	 * @return
	 */
	public AbstractService getAbstractService() {
		return abstractService;
	}

	/**
	 * 获取附近好友服务
	 * 
	 * @return
	 */
	public NearbyService getNearbyService() {
		return nearbyService;
	}

	/**
	 * 微博分享
	 * 
	 * @return
	 */
	public IShareService getShareService() {
		return (IShareService) context.getBean(IShareService.SERVICE_BEAN_ID);
	}

	/**
	 * 获取机器人服务
	 * 
	 * @return
	 */
	public RobotService getRobotService() {
		return robotService;
	}

	/**
	 * 星魂服务
	 * 
	 * @return
	 */
	public StarSoulService getStarSoulService() {
		return starSoulService;
	}

	/**
	 * 月卡服务
	 * 
	 * @return
	 */
	public MonthCardService getMonthCardService() {
		return monthCardService;
	}

	/**
	 * 修炼服务
	 * 
	 * @return
	 */
	public PracticeService getPracticeService() {
		return practiceService;
	}

	/**
	 * 获取每日必做，我要变强相关基础数据服务
	 * 
	 * @return
	 */
	public IDoEveryDayService getDoEveryDayService() {
		return (IDoEveryDayService) context.getBean("DoEveryDayService");
	}
}