package com.wyd.empire.world.player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wyd.empire.channel.service.impl.Access_VTC;
import com.wyd.empire.protocol.data.friend.GetFriendList;
import com.wyd.empire.protocol.data.player.PlayerButtonInfo;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.BuffRecord;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.CardGroup;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.ExchangeCard;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.bean.PlayerCards;
import com.wyd.empire.world.bean.PlayerDIYTitle;
import com.wyd.empire.world.bean.PlayerDraw;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.bean.PlayerTaskTitle;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.SerializeUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.consortia.MaxPrestigeVo;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.room.InviteInfo;
import com.wyd.empire.world.server.handler.friend.GetBlackListHandler;
import com.wyd.empire.world.server.handler.friend.GetFriendListHandler;
import com.wyd.empire.world.server.service.base.IPlayerDIYTitleService;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.IPlayerTaskTitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.DailyActivityService;
import com.wyd.empire.world.server.service.impl.VersionService;
import com.wyd.empire.world.server.service.impl.WorldBossRoomService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.empire.world.title.PlayerTitleVo;
import com.wyd.empire.world.title.TitleIng;
import com.wyd.empire.world.worldbosshall.WorldBossRoom;
import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.AbstractData;

/**
 * 封装游戏世界角色类， 游戏角色的基本属性操作都在本类中继承或实现。
 * 
 * @author guoqiu_zeng
 */
public class WorldPlayer {
	private Logger log = Logger.getLogger(WorldPlayer.class);
	/** 记录fighting变化日志 */
	public static final int PLAYER_STATUS_PEACE = 2;
	public static final byte PLAYER_GAMESTATE_BATTLE = 3;
	public static final byte PLAYER_GAMESTATE_NORMAL = -1;
	public static final byte PLAYER_GAMESTATE_NOCHANGE = -2;
	/** 玩家低级频道 */
	public static final int PLAYER_CHANNEL_LOW = 1;
	/** 玩家高级频道 */
	public static final int PLAYER_CHANNEL_HL = 2;
	/**
	 * 游戏角色状态，默认为下线状态
	 */
	private boolean state = false;
	/**
	 * 最后发送聊天时间
	 */
	private long lastSendMsgTime;
	/**
	 * 3秒钟内发送的聊天记录数量
	 */
	private int lastSendMsgCount;
	/**
	 * 玩家本次在线时间分钟
	 */
	private int onLineTime = 0;
	private long loginTime; // 登录时间
	private boolean everydayFirstLogin; // 是否每天初次登录
	private ConnectSession connSession = null; // 当前所对应的Session
	private Client accountClient = null; // 当前所对应的帐户Client
	private Player player;
	private int roomId; // 对战房间id
	private int battleId; // 对战组id
	private int bossmapBattleId; // 副本战斗Id
	private int bossmapRoomId; // 副本房间Id
	private int channelId = 0; // 玩家当前所在主界面id用于区分玩家频道
	private Consortia guild; // 玩家当前所在公会
	private int win = 0; // 连胜次数
	private int ghwin = 0; // 公会对战连胜次数
	private int hhwin = 0; // 好友对战连胜次数
	private int fqwin = 0; // 夫妻对战连胜次数
	private List<PlayerTask> taskIngList; // 玩家进行中的任务列表
	private List<TitleIng> titleIngList; // 玩家正在进行中的成就任务
	private PlayerItemsFromShop item_head; // 头部装备
	private PlayerItemsFromShop item_face; // 脸部装备
	private PlayerItemsFromShop item_body; // 身体装备
	private PlayerItemsFromShop item_weapon; // 武器装备
	private PlayerItemsFromShop item_wing; // 翅膀装备
	private PlayerItemsFromShop item_ring_L; // 戒指(左)装备
	private PlayerItemsFromShop item_ring_R; // 戒指(右)装备
	private PlayerItemsFromShop item_necklace; // 项链装备
	private PlayerPet playerPet; // 携带的宠物
	// 玩家选择的技能列表
	int[] itemJnused = new int[4];
	List<Tools> playerJNs = new ArrayList<Tools>();
	// 玩家选择的道具列表
	int[] itemDjused = new int[4];
	List<Tools> playerDJs = new ArrayList<Tools>();
	// 玩家身上的BUFF
	List<Buff> buffList = new ArrayList<Buff>();
	// 玩家身上的BUFF的Map<String,Buff>
	Map<String, Buff> buffMap = new HashMap<String, Buff>();
	// 玩家最后登录时间
	private long lastOnLineTime;
	// 推送消息数量
	private int pushCount;
	// 是否有公会戒指
	private boolean hasring = false;
	// 最近一次行动时间
	private long actionTime;
	// 玩家勋章数量
	private int medalNum;
	/** 星魂碎片数量 */
	private int starSoulDebrisNum;
	/** 高级勋章数量 */
	private int seniorMedalNum;
	// 上次强化刷喇叭时间
	private long strengthenTime;
	// 是否初始化玩家数据
	private boolean initial = false;
	// 是否初始化玩家数据
	private boolean firstOnlin = true;
	// 玩家近期进入过的房间id
	private List<Integer> roomIds = new ArrayList<Integer>();
	// 是否有双倍经验卡
	private boolean hasDoubleCard = false;
	private int attack;
	private int defend;
	private int crit;
	private int maxHP;
	private int explodeRadius;
	private int injuryFree;
	private int wreckDefense;
	private int reduceCrit;
	private int reduceBury;
	private int force;
	private int armor;
	private int agility;
	private int physique;
	private int luck;
	private int fighting;
	private int maxPF;
	private Map<Integer, Integer> starNumMap = new HashMap<Integer, Integer>(); // 抽奖星星数的MAP
	private Map<Integer, Integer> drawNumMap = new HashMap<Integer, Integer>(); // 抽奖次数的MAP
	private Map<Integer, Integer> drawFailNumMap = new HashMap<Integer, Integer>(); // 抽奖失败次数的MAP
	private Map<Integer, Integer> refreshNumMap = new HashMap<Integer, Integer>(); // 抽奖刷新的MAP
	private Map<Integer, Boolean> drawRefreshMap = null; // 密境探险是否允许刷新
	// 宠物培养效果临时保存字段。依次为HP,ATTACK,DEFEND
	private int[] petCultureValue;
	// 玩家是否在挑战赛界面
	private boolean playerInChallenge = false;
	// 玩家申请的挑战赛房间列表
	private List<Integer> applyList = new ArrayList<Integer>();
	// 小金人提示标示
	private int tipMark = 0;
	// // 结婚可以赠送的钻石数
	// private int canGiveDiamond;
	// 玩家的附属信息，玩家信息表字段过于多，分开存储
	private PlayerInfo playerInfo;
	// 玩家按钮排序高亮信息
	private PlayerButtonInfo buttonInfo = null;
	// 结婚可以赠送的钻石数1
	private int canGiveDiamond1 = -1;
	// 结婚可以赠送的钻石数2
	private int canGiveDiamond2 = -1;
	// 玩家每天获得结婚贡献度上限
	private int wedGetContribute = 0;
	// 每天战斗的次数
	private int battleNum = 0;
	// 玩家照片信息
	PlayerPicture playerPicture = null;

	// 玩家是否第一次进入婚礼现场
	private boolean fristEnter = true;
	// 玩家所在的婚礼现场编号
	private String wedNum = "";
	// 单人副本
	private int singleMapId = 0; // 单人副本ID
	// 单人副本钻石公告
	private int singleMapGetDiamond = 0; // 0没有需要发送的公告；大于0表示钻石个数且未发公告
	private int interfaceId; // 玩家当前所在界面ID
	private long inTime; // 玩家进入当前界面的时间
	private List<String> robotList;
	private long lastSendMailTime = 0; // 最后发送邮件时间
	private int buyVigorCount = 0; // 购买活力值次数

	/** 修炼属性经验 */
	private List<Integer> practiceBonusExp = new ArrayList<Integer>();

	private int buyVigorMaxCount = 0; // 购买活力值最大次数
	private PlayerCards playerCards; // 卡阵
	private List<ExchangeCard> exchangeCardList = new ArrayList<ExchangeCard>();// 卡牌兑换列表
	private long exchangeCardTime = 0;// 卡牌兑换列表下次刷新时间
	private boolean cacheOk = false;// 标记是否完全推送了所有缓存
	private Map<Integer, Integer> buyLimitedCount = new HashMap<Integer, Integer>(); // 限量物品购买次数
																						// KEY:itemId,VAL:count

	/** 记录玩家邀请信息 */
	private Map<Integer, InviteInfo> inviteInfoMap = new HashMap<Integer, InviteInfo>(); // 记录玩家的邀请信息

	/**
	 * 构造函数，初始化游戏人物各项数值
	 * 
	 * @param player
	 * @throws Exception
	 */
	public WorldPlayer(Player player) {
		this.player = player;
		this.actionTime = System.currentTimeMillis();
	}

	public void initial() {
		initial = true;
		initialPlayerJnDj();
		initialGuild();
		initPlayerPet();
		initPlayerPicture();
		initialPracticeBonusExp();
		initPlayerCard();
		// 这个要放在最后
		initialPlayerItem();
		updateFight();
	}

	public void firstLoad() {
		firstOnlin = false;
		initialLastOnLineTime();
		initialBuff();
		initialMedalNum();
		initialStarSoulDebrisNum();
		wedGetContribute = 0;
		battleNum = 0;
		fristEnter = true;
	}

	/**
	 * 初始化修炼属性经验
	 */
	public void initialPracticeBonusExp() {
		getPlayerInfo();
		if (playerInfo.getPracticeAttributeExp() != null) {
			String[] exp = playerInfo.getPracticeAttributeExp().toString().replace(" ", "").split(",");
			if (exp.length > 0) {
				for (int i = 0; i < exp.length; i++) {
					practiceBonusExp.add(Integer.valueOf(exp[i].trim()));
				}
			}
		} else {
			practiceBonusExp = new ArrayList<Integer>();
			practiceBonusExp.add(0);
			practiceBonusExp.add(0);
			practiceBonusExp.add(0);
			practiceBonusExp.add(0);
			practiceBonusExp.add(0);
		}

	}

	/**
	 * 获取修炼属性经验
	 * 
	 * @return
	 */
	public List<Integer> getPracticeBonusExp() {
		return practiceBonusExp;
	}

	/**
	 * 更改修炼属性经验
	 * 
	 * @param practiceBonusExp
	 */
	public void setPracticeBonusExp(List<Integer> practiceBonusExp) {
		this.practiceBonusExp = practiceBonusExp;
	}

	/**
	 * 设置玩家副本星级
	 * 
	 * @param mapId
	 * @param star
	 */
	public void updatePlayerStarInfo(int mapId, int star) {
		PlayerBossmap pbMap = ServiceManager.getManager().getPlayerBossmapService().loadPlayerBossMap(getId(), mapId);
		com.wyd.empire.world.bean.Map map = ServiceManager.getManager().getMapsService().getBossMapById(mapId);
		Date date = new Date();
		if (map != null) {
			DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
			DailyRewardVo dailyRewardVo = dailyActivityService.getBossmapBattleReward();
			int mapTime = map.getTimes() * 60 * 1000;
			int time = dailyActivityService.getRewardedVal(mapTime, dailyRewardVo.getSubTime());
			date = new Date(date.getTime() - mapTime + time);
		}
		if (star > pbMap.getStar()) {
			pbMap.setStar(star);
		}
		pbMap.setLastPlayTime(date);
		ServiceManager.getManager().getPlayerBossmapService().update(pbMap);
	}

	/**
	 * 玩家是否当天初次登陆
	 * 
	 * @return
	 */
	public boolean isEverydayFirstLogin() {
		return everydayFirstLogin;
	}

	public void setEverydayFirstLogin(boolean everydayFirstLogin) {
		this.everydayFirstLogin = everydayFirstLogin;
	}

	/**
	 * 玩家是否初次加载相关信息
	 * 
	 * @return
	 */
	public boolean isFirstOnlin() {
		return firstOnlin;
	}

	/**
	 * 玩家是否已初始化数据
	 * 
	 * @return
	 */
	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	public int getId() {
		return player.getId();
	}

	// 当前所对应的Session
	public void setConnectSession(ConnectSession session) {
		this.connSession = session;
	}

	public ConnectSession getConnectSession() {
		return this.connSession;
	}

	// 当前所对应的帐户Client
	public void setAccountClient(Client client) {
		this.accountClient = client;
	}

	public Client getClient() {
		return this.accountClient;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * 角色是否在线
	 * 
	 * @return
	 */
	public boolean isOnline() {
		return this.state;
	}

	public void sendData(AbstractData data) {
		// System.out.println("null != connSession"+(null != connSession));
		if (null != connSession)
			connSession.write(data, player.getId());
	}

	public boolean offlinemode() {
		return false;
	}

	public Player getPlayer() {
		return player;
	}

	public long getLastSendMsgTime() {
		return this.lastSendMsgTime;
	}

	public void setLastSendMsgTime(long lastSendMsgTime) {
		this.lastSendMsgTime = lastSendMsgTime;
	}

	public int getLastSendMsgCount() {
		return lastSendMsgCount;
	}

	public void setLastSendMsgCount(int lastSendMsgCount) {
		this.lastSendMsgCount = lastSendMsgCount;
	}

	public int getOnLineTime() {
		return onLineTime;
	}

	public void setOnLineTime(int onLineTime) {
		this.onLineTime = onLineTime;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public String getName() {
		return this.player.getName();
	}

	/**
	 * 获取玩家金币数量
	 * 
	 * @return 金币数量
	 */
	public int getMoney() {
		return this.player.getMoneyGold();
	}

	public int getGameAccountId() {
		return this.player.getAccountId();
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getBattleId() {
		return battleId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * 获得玩家副本进度
	 * 
	 * @return
	 */
	public int getBossmap_progress() {
		return this.player.getBossmapProgress();
	}

	public int getGuildId() {
		if (null != guild) {
			return guild.getId();
		} else {
			return 0;
		}
	}

	public String getGuildName() {
		if (null != guild) {
			return guild.getName();
		} else {
			return "";
		}
	}

	public Consortia getGuild() {
		return this.guild;
	}

	public void setGuild(Consortia guild) {
		this.guild = guild;
		if (guild == null) {
			Map<String, String> info = new HashMap<String, String>();
			info.put("guildName", "");
			info.put("position", "");
			info.put("title", getPlayerTitle());
			ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, this);
		}
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getGhwin() {
		return ghwin;
	}

	public void setGhwin(int ghwin) {
		this.ghwin = ghwin;
	}

	public int getHhwin() {
		return hhwin;
	}

	public void setHhwin(int hhwin) {
		this.hhwin = hhwin;
	}

	public int getFqwin() {
		return fqwin;
	}

	public void setFqwin(int fqwin) {
		this.fqwin = fqwin;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public List<PlayerTask> getTaskIngList() {
		return taskIngList;
	}

	public void setTaskIngList(List<PlayerTask> taskIngList) {
		this.taskIngList = taskIngList;
	}

	public List<TitleIng> getTitleIngList() {
		return titleIngList;
	}

	public void setTitleIngList(List<TitleIng> titleIngList) {
		this.titleIngList = titleIngList;
	}

	/**
	 * 获取玩家显示的称号
	 * 
	 * @return
	 */
	public String getPlayerTitle() {
		String title = "";
		IPlayerDIYTitleService playerDIYTitleService = ServiceManager.getManager().getPlayerDIYTitleService();
		PlayerDIYTitle diyTitle = playerDIYTitleService.getSelDIYTitle(getId());
		if (diyTitle != null) {
			return "[" + diyTitle.getIcon() + "]" + diyTitle.getTitle();
		} else if (null != titleIngList) {
			for (TitleIng titleIng : titleIngList) {
				if (3 == titleIng.getStatus()) {
					if (1 == titleIng.getTitleId()) {// 工会称号
						title = ServiceManager.getManager().getTitleService().guildTitle(this);
					} else if (2 == titleIng.getTitleId()) {// 结婚
						title = ServiceManager.getManager().getTitleService().getMarryTitle(player);
					} else {
						title = ServiceManager.getManager().getTitleService().getTitleById(titleIng.getTitleId()).getTitle();
					}
					break;
				}
			}
		}
		return title;
	}

	/**
	 * 初始化玩家成就及任务列表
	 */
	@SuppressWarnings("unchecked")
	public void initialPlayerTaskTitle() {
		try {
			IPlayerTaskTitleService playerTaskTitleService = ServiceManager.getManager().getPlayerTaskTitleService();
			PlayerTaskTitle playerTaskTitle = playerTaskTitleService.getPlayerTaskTitleByPlayerId(this.getId());
			if (playerTaskTitle == null) {
				playerTaskTitle = playerTaskTitleService.createDefaultTaskAndTitleList(this);
			}
			List<PlayerTask> taskList;
			try {
				taskList = (List<PlayerTask>) SerializeUtil.unserialize(playerTaskTitle.getTaskList());
			} catch (Exception e) {
				// 处理老玩家任务
				taskList = ServiceManager.getManager().getPlayerTaskTitleService().createPlayerTask();
				for (PlayerTask playerTask : taskList) {
					if (playerTask.getTaskType() == Common.TASK_TYPE_MAIN) {
						Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(playerTask.getTaskId());
						if (task.getLevel() < getLevel()) {
							playerTaskTitleService.updatePlayerTaskStatus(this, playerTask, Common.TASK_STATUS_FINISHED, false);
						}
					}
				}
				ServiceManager.getManager().getPlayerTaskTitleService().initialTask(this, taskList, getLevel(), getId());
				ServiceManager.getManager().getPlayerTaskTitleService().activateDayTask(this, taskList);
			}
			setTaskIngList(taskList);
			initialTitle((List<PlayerTitleVo>) SerializeUtil.unserialize(playerTaskTitle.getTitleList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化玩家正在进行的成就任务
	 */
	public void initialTitle(List<PlayerTitleVo> playerTitleVoList) {
		setTitleIngList(ServiceManager.getManager().getTitleService().createTitleByPlayer(playerTitleVoList));
	}

	public String getSuit_head() {
		return item_head.getShopItem().getAnimationIndexCode();
	}

	public String getSuit_face() {
		return item_face.getShopItem().getAnimationIndexCode();
	}

	public String getSuit_body() {
		return item_body.getShopItem().getAnimationIndexCode();
	}

	public String getSuit_weapon() {
		return item_weapon.getShopItem().getAnimationIndexCode();
	}

	public int getWeapon_type() {
		return item_weapon.getShopItem().getType();
	}

	public int getWeaponLevel() {
		return item_weapon.getStrongLevel();
	}

	public String getSuit_wing() {
		if (null == item_wing) {
			return "null";
		} else {
			return item_wing.getShopItem().getAnimationIndexCode();
		}
	}

	/**
	 * 获取玩家的装备加成属性
	 * 
	 * @param type
	 *            1攻击力，2血量，3防御力，4体力值，5暴击系数，6免伤，7破防，
	 *            8免暴，9免坑，10力量，11护甲，12敏捷，13体质，14幸运,15暴击
	 * @return
	 */
	private int getItemProperty(int type) {
		return getItemProperty(type, null);
	}

	// temp_item临时物品
	private int getItemProperty(int type, PlayerItemsFromShop temp_item) {
		PlayerItemsFromShop item_head = this.item_head;
		PlayerItemsFromShop item_face = this.item_face;
		PlayerItemsFromShop item_body = this.item_body;
		PlayerItemsFromShop item_weapon = this.item_weapon;
		PlayerItemsFromShop item_wing = this.item_wing;
		PlayerItemsFromShop item_ring_L = this.item_ring_L;
		PlayerItemsFromShop item_necklace = this.item_necklace;
		if (temp_item != null) {
			// 使用临时物品
			if (temp_item.getShopItem().isHead()) {
				item_head = temp_item;
			} else if (temp_item.getShopItem().isFace()) {
				item_face = temp_item;
			} else if (temp_item.getShopItem().isBody()) {
				item_body = temp_item;
			} else if (temp_item.getShopItem().isWeapon()) {
				item_weapon = temp_item;
			} else if (temp_item.getShopItem().isWing()) {
				item_wing = temp_item;
			} else if (temp_item.getShopItem().isRing()) {
				item_ring_L = temp_item;
			} else if (temp_item.getShopItem().isNecklace()) {
				item_necklace = temp_item;
			}
		}
		List<PlayerItemsFromShop> playerItems = new ArrayList<PlayerItemsFromShop>();
		playerItems.add(item_head);
		playerItems.add(item_face);
		playerItems.add(item_body);
		playerItems.add(item_weapon);
		playerItems.add(item_wing);
		playerItems.add(item_ring_L);
		playerItems.add(item_ring_R);
		playerItems.add(item_necklace);
		if (playerCards != null) {
			playerItems.add(playerCards.getJk());
			playerItems.add(playerCards.getMk());
			playerItems.add(playerCards.getSk());
			playerItems.add(playerCards.getHk());
			playerItems.add(playerCards.getTk());
		}
		int ret = 0;
		switch (type) {
			case 1 :
				ret = getPlayerItemAttack(playerItems);
				break;
			case 2 :
				ret += getPlayerItemHp(playerItems);
				break;
			case 3 :
				ret += getPlayerItemDefend(playerItems);
				break;
			case 4 :
				ret += getPlayerItemPower(playerItems);
				break;
			case 6 :
				ret += getPlayerItemInjuryFree(playerItems);
				break;
			case 7 :
				ret += getPlayerItemWreckDefense(playerItems);
				break;
			case 8 :
				ret += getPlayerItemReduceCrit(playerItems);
				break;
			case 9 :
				ret += getPlayerItemReduceBury(playerItems);
				break;
			case 10 :
				ret += getPlayerItemForce(playerItems);
				break;
			case 11 :
				ret += getPlayerItemArmor(playerItems);
				break;
			case 12 :
				ret += getPlayerItemAgility(playerItems);
				break;
			case 13 :
				ret += getPlayerItemPhysique(playerItems);
				break;
			case 14 :
				ret = getPlayerItemLuck(playerItems);
				break;
			case 15 :
				ret = getPlayerItemCrit(playerItems);
				break;
		}
		return ret;
	}

	private int getPlayerItemAttack(List<PlayerItemsFromShop> playerItems) {
		int attack = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			attack += playerItem.getPAddAttack() + playerItem.getShopItem().getAddAttack();
		}
		return attack;
	}

	private int getPlayerItemCrit(List<PlayerItemsFromShop> playerItems) {
		int crit = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			crit += playerItem.getpAddcrit() + playerItem.getShopItem().getAddCritical();
		}
		return crit;
	}

	private int getPlayerItemLuck(List<PlayerItemsFromShop> playerItems) {
		int luck = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			luck += playerItem.getpAddLuck() + playerItem.getShopItem().getAddLuck();
		}
		return luck;
	}

	private int getPlayerItemPhysique(List<PlayerItemsFromShop> playerItems) {
		int physique = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			physique += playerItem.getpAddPhysique() + playerItem.getShopItem().getAddPhysique();
		}
		return physique;
	}

	private int getPlayerItemAgility(List<PlayerItemsFromShop> playerItems) {
		int agility = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			agility += playerItem.getpAddAgility() + playerItem.getShopItem().getAddAgility();
		}
		return agility;
	}

	private int getPlayerItemHp(List<PlayerItemsFromShop> playerItems) {
		int hp = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			hp += playerItem.getPAddHp() + playerItem.getShopItem().getAddHp();
		}
		return hp;
	}

	private int getPlayerItemDefend(List<PlayerItemsFromShop> playerItems) {
		int defend = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			defend += playerItem.getPAddDefend() + playerItem.getShopItem().getAddDefend();
		}
		return defend;
	}

	private int getPlayerItemPower(List<PlayerItemsFromShop> playerItems) {
		int power = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			power += playerItem.getShopItem().getAddPower();
		}
		return power;
	}

	private int getPlayerItemForce(List<PlayerItemsFromShop> playerItems) {
		int force = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			force += playerItem.getpAddForce() + playerItem.getShopItem().getAddForce();
		}
		return force;
	}

	private int getPlayerItemArmor(List<PlayerItemsFromShop> playerItems) {
		int armor = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			armor += playerItem.getpAddArmor() + playerItem.getShopItem().getAddArmor();
		}
		return armor;
	}

	private int getPlayerItemReduceBury(List<PlayerItemsFromShop> playerItems) {
		int reduceBury = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			reduceBury += playerItem.getShopItem().getAddReduceBury();
		}
		return reduceBury;
	}

	private int getPlayerItemReduceCrit(List<PlayerItemsFromShop> playerItems) {
		int reduceCrit = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			reduceCrit += playerItem.getpAddReduceCrit() + playerItem.getShopItem().getAddReduceCrit();
		}
		return reduceCrit;
	}

	private int getPlayerItemWreckDefense(List<PlayerItemsFromShop> playerItems) {
		int wreckDefense = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			wreckDefense += playerItem.getpAddWreckDefense() + playerItem.getShopItem().getAddWreckDefense();
		}
		return wreckDefense;
	}

	private int getPlayerItemInjuryFree(List<PlayerItemsFromShop> playerItems) {
		int injuryFree = 0;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem == null)
				continue;
			injuryFree += playerItem.getShopItem().getAddInjuryFree();
		}
		return injuryFree;
	}

	/**
	 * 获取玩家等级
	 * 
	 * @return
	 */
	public int getLevel() {
		return this.player.getLevel();
	}

	/**
	 * 获取玩家当前的攻击力
	 * 
	 * @return
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * 获取玩家当前防御力
	 * 
	 * @return
	 */
	public int getDefend() {
		return defend;
	}

	/**
	 * 获得玩家当前暴击值
	 * 
	 * @return
	 */
	public int getCrit() {
		return crit;
	}

	/**
	 * 当前血量
	 * 
	 * @return
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**
	 * 获取爆破范围
	 * 
	 * @return
	 */
	public int getExplodeRadius() {
		return explodeRadius;
	}

	/**
	 * 获取免伤
	 * 
	 * @return
	 */
	public int getInjuryFree() {
		return injuryFree;
	}

	/**
	 * 获取破防
	 * 
	 * @return
	 */
	public int getWreckDefense() {
		return wreckDefense;
	}

	/**
	 * 获取免暴
	 * 
	 * @return
	 */
	public int getReduceCrit() {
		return reduceCrit;
	}

	/**
	 * 获取免坑 (10000倍)
	 * 
	 * @return
	 */
	public int getReduceBury() {
		return reduceBury;
	}

	/**
	 * 获取力量
	 * 
	 * @return
	 */
	public int getForce() {
		return force;
	}

	/**
	 * 获取护甲
	 * 
	 * @return
	 */
	public int getArmor() {
		return armor;
	}

	/**
	 * 获取敏捷
	 * 
	 * @return
	 */
	public int getAgility() {
		return agility;
	}

	/**
	 * 获取体质
	 * 
	 * @return
	 */
	public int getPhysique() {
		return physique;
	}

	/**
	 * 获取幸运
	 * 
	 * @return
	 */
	public int getLuck() {
		return luck;
	}

	/**
	 * 获取玩家的战斗力
	 * 
	 * @return
	 */
	public int getFighting() {
		return fighting;
	}

	/**
	 * 当前最大体力值 （包括公会技能加成）
	 * 
	 * @return
	 */
	public int getMaxPF() {
		return maxPF;
	}

	/**
	 * 当前最大怒气值
	 * 
	 * @return
	 */
	public int getMaxSP() {
		return 100;
	}

	/**
	 * 当前大招攻击力 注意：该方法可能过时。
	 * 
	 * @return
	 */
	public int getBigSkillAttack() {
		return getAttack() * 2;
	}

	/**
	 * 更新武器熟练度
	 * 
	 * @param proficiency
	 */
	public void setProficiency(int proficiency) {
		item_weapon.setSkillful(proficiency);
		// 更新武器熟练度
		Map<String, String> data = new HashMap<String, String>();
		data.put("proficiency", item_weapon.getSkillful() + "");
		ServiceManager.getManager().getPlayerItemsFromShopService().sendUpdateItem(this, item_weapon.getId(), data);
	}

	/**
	 * 获取武器熟练度
	 * 
	 * @return
	 */
	public int getProficiency() {
		if (item_weapon == null) {
			initEquipment();
		}
		return item_weapon.getSkillful();
	}

	/**
	 * 获取武器熟练度
	 * 
	 * @return
	 */
	public int getProficiency(PlayerItemsFromShop item_weapon) {
		return item_weapon.getSkillful() > 10000 ? 10000 : item_weapon.getSkillful();
	}

	/**
	 * 获取大招类型
	 * 
	 * @return
	 */
	public int getBigSkillType() {
		if (item_weapon == null) {
			initEquipment();
		}
		return item_weapon.getShopItem().getPowerskill();
	}

	/**
	 * 检查身上装备是否过期
	 */
	public void initialPlayerItem() {
		boolean mark = ServiceManager.getManager().getPlayerItemsFromShopService().dispearItemAtOverTime(this);
		if (mark) {
			updateFight();
		}
	}

	/**
	 * 更新玩家状态 （玩家登录、换装、换宠时需要调用） 并推送相关协议到客户端
	 */
	public void updateFight() {
		// 计算玩家战斗力
		attack = 0;
		defend = 0;
		maxHP = 0;
		crit = 0;

		try {
			initEquipment();
			playerCards = ServiceManager.getManager().getPlayerCardsService().getByPlayerId(getId());
			// 计算玩家力量
			force = player.getForce() + getPlayerInfo().getForce() + getPlayerInfo().getPracticeForce() + getItemProperty(10);
			// 计算玩家护甲
			armor = player.getArmor() + getPlayerInfo().getArmor() + getPlayerInfo().getPracticeArmor() + getItemProperty(11);
			// 计算玩家敏捷
			agility = player.getAgility() + getPlayerInfo().getAgility() + getPlayerInfo().getPracticeAgility() + getItemProperty(12);
			// 计算玩家体质
			physique = player.getPhysique() + getPlayerInfo().getPhysique() + getPlayerInfo().getPracticePhysique() + getItemProperty(13);
			// 计算玩家爆破范围
			explodeRadius = item_weapon.getShopItem().getAttackArea();
			// 计算玩家破防率
			wreckDefense = player.getWreckDefense() + getPlayerInfo().getWreckDefense() + getPlayerInfo().getPracticeWreckDefense()
					+ getItemProperty(7);
			// 计算玩家免暴率
			reduceCrit = player.getReduceCrit() + getPlayerInfo().getReduceCrit() + getPlayerInfo().getPracticeReduceCrit()
					+ getItemProperty(8);
			// 计算玩家的幸运值
			luck = player.getLuck() + getPlayerInfo().getLuck() + getPlayerInfo().getPracticeLuck() + getItemProperty(14);
			// 计算玩家免坑率
			reduceBury = player.getReduceBury() + getPlayerInfo().getReduceBury() + getPlayerInfo().getPracticeReduceBury()
					+ getItemProperty(9) + getLuck() * 10000 / 8333;
			// 当前最大体力值
			maxPF = (int) ServiceManager.getManager().getBuffService().getAddition(this, 100 + getItemProperty(4), Buff.CPOWER);
			// 计算玩家免伤率
			injuryFree = (int) ((player.getInjuryFree() + getPlayerInfo().getInjuryFree() + getPlayerInfo().getPracticeInjuryFree() + getItemProperty(6)));
			// 爆击值(武器的增加的爆击与武器熟练度有关。其它的的直接加)
			crit += player.getDefense() + getPlayerInfo().getDefense() + getPlayerInfo().getPracticeDefense();
			crit += getItemProperty(15);
			// 卡阵套卡加成
			int groupId = getCardGroupId();
			if (groupId > 0) {
				int num = getCardGroupNum(groupId);
				// System.out.println("套卡："+groupId+" 数量："+num);
				CardGroup groupAddition = null;
				if (num == 5) {
					// 5张卡属性
					groupAddition = ServiceManager.getManager().getPlayerCardsService().getCardGroup(groupId, 5);
				} else if (num > 2) {
					// 3张卡属性
					groupAddition = ServiceManager.getManager().getPlayerCardsService().getCardGroup(groupId, 3);
				}
				if (groupAddition != null) {

					crit += groupAddition.getAddCritical();
					reduceCrit += groupAddition.getAddReduceCrit();
					wreckDefense += groupAddition.getAddWreckDefense();
					injuryFree += groupAddition.getAddInjuryFree();
					attack += groupAddition.getAddAttack();
					maxHP += groupAddition.getAddHp();
					defend += groupAddition.getAddDefend();
				}
			}

			// 等级属性加成
			if (player.getZsLevel() > 0) {
				defend += 99.5 + 1 * (getLevel() - 99);
				attack += 199 + 2 * (getLevel() - 99);
				maxHP += 1595 + 10 * (getLevel() - 99);
			} else {
				defend += 50 + 0.5 * getLevel();
				attack += 100 + 1 * getLevel();
				maxHP += 1100 + 5 * getLevel();
			}
			// 宠物加成
			if (getPlayerPet() != null && getPlayerPet().isInUsed()) {
				attack += getPlayerPet().getAttack();
				defend += getPlayerPet().getDefend();
				maxHP += getPlayerPet().getHP();
			}

			// attack += player.getAttack() + getItemProperty(1) + (getForce() *
			// 4 / 25);
			attack += player.getAttack() + getItemProperty(1) + getPlayerInfo().getAttack() + getPlayerInfo().getPracticeAttack();
			// defend += player.getDefend() + getItemProperty(3) + getArmor() *
			// 9 / 50;
			defend += player.getDefend() + getItemProperty(3) + getPlayerInfo().getDefend() + getPlayerInfo().getPracticeDefend();
			maxHP += player.getHp() + getItemProperty(2) + getPhysique() + getPlayerInfo().getHp() + getPlayerInfo().getPracticeHp();
			// 加BUFF得出最终值
			defend = (int) ServiceManager.getManager().getBuffService().getAddition(this, defend, Buff.CDEF);
			maxHP = (int) ServiceManager.getManager().getBuffService().getAddition(this, maxHP, Buff.CHPCAP);

			// 计算玩家战斗力
			float bj = getCrit();// 暴击
			float gj = getAttack();// 攻击
			float sm = getMaxHP();// 生命
			float pf = getWreckDefense();// 破防
			float mb = getReduceCrit();// 免爆
			float ms = getInjuryFree();// 免伤
			float bp = getExplodeRadius(); // 爆破范围
			float ll = getForce();// 力量
			float hj = getArmor();// 护甲
			float mj = getAgility();// 敏捷
			float xy = getLuck();// 幸运
			float tz = getPhysique();// 体质
			float fy = getDefend(); // 防御
			// 新战斗力=(1+0.5*力量/(500+力量）+0.5*护甲/(500+护甲）)*(1+0.5*敏捷/(500+敏捷）+0.5*幸运/(500+幸运）)*（1+0.5*体质/(体质+500))
			// *sqrt(4*攻击*生命*(1+破防/(破防+500))/(1-防御/（2*防御+600)))*(1+0.5*暴击/(500+暴击）+0.5*免爆/(500+免爆）)/(1-0.3*免伤/(免伤+1000）)
			// *(爆破范围/（100*2）+1)
			fighting = (int) ((1 + 0.5 * ll / (500 + ll) + 0.5 * hj / (500 + hj)) * (1 + 0.5 * mj / (500 + mj) + 0.5 * xy / (500 + xy))
					* (1 + 0.5 * tz / (tz + 500)) * Math.sqrt(4 * gj * sm * (1 + pf / (pf + 500)) / (1 - fy / (2 * fy + 600)))
					* (1 + 0.5 * bj / (500 + bj) + 0.5 * mb / (500 + mb)) / (1 - 0.3 * ms / (ms + 1000)) * (bp / 200 + 1));
			player.setFight(fighting);
			// 推送玩家信息
			ServiceManager.getManager().getPlayerService().updateAttribute(this);
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	/**
	 * 初始化玩家装备.如果没有则给默认装
	 */
	public void initEquipment() {
		IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
		VersionService versionService = ServiceManager.getManager().getVersionService();
		List<PlayerItemsFromShop> playerItems = playerItemsFromShopService.getInUseItem(getId());
		item_head = null; // 头部装备
		item_face = null; // 脸部装备
		item_body = null; // 身体装备
		item_weapon = null; // 武器装备
		item_wing = null; // 翅膀装备
		item_ring_L = null; // 戒指1装备
		item_ring_R = null; // 戒指2装备
		item_necklace = null;
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem.getIsInUsed()) {
				if (playerItem.getShopItem().isWeapon()) {
					item_weapon = playerItem;
				} else if (playerItem.getShopItem().isBody()) {
					item_body = playerItem;
				} else if (playerItem.getShopItem().isWing()) {
					item_wing = playerItem;
				} else if (playerItem.getShopItem().isNecklace()) {
					item_necklace = playerItem;
				} else if (playerItem.getShopItem().isFace()) {
					item_face = playerItem;
				} else if (playerItem.getShopItem().isHead()) {
					item_head = playerItem;
				} else if (playerItem.getShopItem().isRing()) {
					if (playerItem.getShopItem().isRing_L()) {
						item_ring_L = playerItem;
					} else if (playerItem.getShopItem().isRing_R()) {
						item_ring_R = playerItem;
					} else {
						if (item_ring_L == null) {
							item_ring_L = playerItem;
						} else if (item_ring_R == null) {
							item_ring_R = playerItem;
						}
					}
				}
			}
		}
		if (null == item_head) {
			int headId = versionService.getDefaultItemHeadId(player.getSex());
			item_head = checkPlayerItemById(headId);
			item_head.setIsInUsed(true);
			playerItemsFromShopService.update(item_head);
		}
		if (null == item_face) {
			int faceId = versionService.getDefaultItemFaceId(player.getSex());
			item_face = checkPlayerItemById(faceId);
			item_face.setIsInUsed(true);
			playerItemsFromShopService.update(item_face);
		}
		if (null == item_body) {
			int bodyId = versionService.getDefaultItemBodyId(player.getSex());
			item_body = checkPlayerItemById(bodyId);
			item_body.setIsInUsed(true);
			playerItemsFromShopService.update(item_body);
		}
		if (null == item_weapon) {
			int weaponId = versionService.getDefaultItemWeaponId();
			item_weapon = checkPlayerItemById(weaponId);
			item_weapon.setIsInUsed(true);
			playerItemsFromShopService.update(item_weapon);
		}
	}

	/**
	 * 检查玩家是否有该物品，没有则发放一个给玩家
	 * 
	 * @param itemId
	 */
	public PlayerItemsFromShop checkPlayerItemById(int itemId) {
		PlayerItemsFromShop playerItem = ServiceManager.getManager().getPlayerItemsFromShopService()
				.uniquePlayerItem(player.getId(), itemId);
		if (null == playerItem) {
			return ServiceManager.getManager().getPlayerItemsFromShopService()
					.playerGetItem(getId(), itemId, -1, -1, -1, -10, null, 0, 0, 0);
		}
		return playerItem;
	}

	/**
	 * 初始化玩家技能道具（角色登录时调用）
	 */
	public void initialPlayerJnDj() {
		if (isVip()) {
			if (-1 == player.getItemJn4().intValue()) {
				player.setItemJn4(0);
			}
			if (-1 == player.getItemDj4().intValue()) {
				player.setItemDj4(0);
			}
		} else {
			player.setItemJn4(-1);
			player.setItemDj4(-1);
		}
		playerJNs.clear();
		playerDJs.clear();
		Tools tools;
		// 初始化玩家技能栏
		if (player.getItemJn1().intValue() != 0) {
			itemJnused[0] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemJn1());
			if (null != tools && 0 == tools.getType().intValue()) {
				tools.checkingData();
				playerJNs.add(tools);
			} else {
				player.setItemJn1(0);
				itemJnused[0] = 0;
				playerJNs.add(null);
			}
		} else {
			itemJnused[0] = 0;
			playerJNs.add(null);
		}
		if (player.getItemJn2().intValue() != 0) {
			itemJnused[1] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemJn2());
			if (null != tools && 0 == tools.getType().intValue()) {
				tools.checkingData();
				playerJNs.add(tools);
			} else {
				player.setItemJn2(0);
				itemJnused[1] = 0;
				playerJNs.add(null);
			}
		} else {
			itemJnused[1] = 0;
			playerJNs.add(null);
		}
		if (player.getItemJn3().intValue() != 0) {
			itemJnused[2] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemJn3());
			if (null != tools && 0 == tools.getType().intValue()) {
				tools.checkingData();
				playerJNs.add(tools);
			} else {
				player.setItemJn3(0);
				itemJnused[2] = 0;
				playerJNs.add(null);
			}
		} else {
			itemJnused[2] = 0;
			playerJNs.add(null);
		}
		if (player.getItemJn4().intValue() > 0) {
			itemJnused[3] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemJn4());
			if (null != tools && 0 == tools.getType().intValue()) {
				tools.checkingData();
				playerJNs.add(tools);
			} else {
				player.setItemJn4(0);
				itemJnused[3] = 0;
				playerJNs.add(null);
			}
		} else {
			itemJnused[3] = player.getItemJn4();
			playerJNs.add(null);
		}
		// 初始化玩家道具栏
		if (player.getItemDj1().intValue() != 0) {
			itemDjused[0] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemDj1());
			if (null != tools && 1 == tools.getType().intValue()) {
				tools.checkingData();
				playerDJs.add(tools);
			} else {
				player.setItemDj1(0);
				itemDjused[0] = 0;
				playerDJs.add(null);
			}
		} else {
			itemDjused[0] = 0;
			playerDJs.add(null);
		}
		if (player.getItemDj2().intValue() != 0) {
			itemDjused[1] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemDj2());
			if (null != tools && 1 == tools.getType().intValue()) {
				tools.checkingData();
				playerDJs.add(tools);
			} else {
				player.setItemDj2(0);
				itemDjused[1] = 0;
				playerDJs.add(null);
			}
		} else {
			itemDjused[1] = 0;
			playerDJs.add(null);
		}
		if (player.getItemDj3().intValue() != 0) {
			itemDjused[2] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemDj3());
			if (null != tools && 1 == tools.getType().intValue()) {
				tools.checkingData();
				playerDJs.add(tools);
			} else {
				player.setItemDj3(0);
				itemDjused[2] = 0;
				playerDJs.add(null);
			}
		} else {
			itemDjused[2] = 0;
			playerDJs.add(null);
		}
		if (player.getItemDj4().intValue() > 0) {
			itemDjused[3] = 1;
			tools = (Tools) ServiceManager.getManager().getToolsService().get(Tools.class, player.getItemDj4());
			if (null != tools && 1 == tools.getType().intValue()) {
				tools.checkingData();
				playerDJs.add(tools);
			} else {
				player.setItemDj4(0);
				itemDjused[3] = 0;
				playerDJs.add(null);
			}
		} else {
			itemDjused[3] = player.getItemDj4();
			playerDJs.add(null);
		}
	}

	public int[] getItemJnused() {
		return itemJnused;
	}

	public int[] getItemDjused() {
		return itemDjused;
	}

	public List<Tools> getPlayerJNs() {
		return playerJNs;
	}

	public List<Tools> getPlayerDJs() {
		return playerDJs;
	}

	/**
	 * 玩家选择装备技能
	 * 
	 * @param tools
	 * @param index
	 */
	public void changeSkill(Tools tools, int index) {
		switch (index) {
			case 0 :
				player.setItemJn1(null == tools ? 0 : tools.getId());
				break;
			case 1 :
				player.setItemJn2(null == tools ? 0 : tools.getId());
				break;
			case 2 :
				player.setItemJn3(null == tools ? 0 : tools.getId());
				break;
			case 3 :
				player.setItemJn4(null == tools ? 0 : tools.getId());
				break;
		}
		itemJnused[index] = null == tools ? 0 : 1;
		if (null != tools) {
			tools.checkingData();
		}
		playerJNs.set(index, tools);
	}

	/**
	 * 玩家选择装备道具
	 * 
	 * @param tools
	 * @param index
	 */
	public void changeProp(Tools tools, int index) {
		switch (index) {
			case 0 :
				player.setItemDj1(null == tools ? 0 : tools.getId());
				break;
			case 1 :
				player.setItemDj2(null == tools ? 0 : tools.getId());
				break;
			case 2 :
				player.setItemDj3(null == tools ? 0 : tools.getId());
				break;
			case 3 :
				player.setItemDj4(null == tools ? 0 : tools.getId());
				break;
		}
		itemDjused[index] = null == tools ? 0 : 1;
		if (null != tools) {
			tools.checkingData();
		}
		playerDJs.set(index, tools);
	}

	/**
	 * 初始化玩家公会
	 */
	public void initialGuild() {
		this.guild = ServiceManager.getManager().getConsortiaService().getConsortiaByPlayerId(player.getId());
		PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
				.uniquePlayerItem(player.getId(), Common.RING);
		if (null != pifs && pifs.getPLastTime() != 0 && pifs.getPLastNum() != 0) {
			hasring = true;
		} else {
			hasring = false;
		}
	}

	/**
	 * 是否有公会戒指
	 * 
	 * @return
	 */
	public boolean hasRing() {
		return hasring;
	}

	/**
	 * 设置是否有公会戒指
	 * 
	 * @param hasring
	 */
	public void setHasring(boolean hasring) {
		this.hasring = hasring;
	}

	/**
	 * 获取玩家所在的战斗频道
	 * 
	 * @return 1低级频道，2高级频道
	 */
	public int getBattleChannel() {
		if (player.getLevel() > Common.PLAYER_CHANNEL_LOW_LEVEL) {
			return PLAYER_CHANNEL_HL;
		} else {
			return PLAYER_CHANNEL_LOW;
		}
	}

	/**
	 * 获取玩家身上的BUFF
	 * 
	 * @return
	 */
	public List<Buff> getBuffList() {
		return buffList;
	}

	/**
	 * 获取玩家身上的BUFF(Map)
	 * 
	 * @return
	 */
	public Map<String, Buff> getBuffMap() {
		return buffMap;
	}

	public long getActionTime() {
		return actionTime;
	}

	public void setActionTime(long actionTime) {
		this.actionTime = actionTime;
	}

	/**
	 * 初始化玩家身上的buff
	 */
	public void initialBuff() {
		buffList.clear();
		buffMap.clear();
		PlayerItemsFromShop pifs;
		Buff buff;
		long lostTime = 0;
		long nowTime = System.currentTimeMillis();
		pifs = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(player.getId(), Common.DOUBLEEXPID);
		if (null != pifs && pifs.getPLastTime() > 0) {
			buff = new Buff();
			lostTime = pifs.getBuyTime().getTime() + (pifs.getPLastTime() * Common.ONEDAYLONG);
			buff.setBuffName(pifs.getShopItem().getName());
			buff.setBuffCode(Buff.EXP);
			buff.setIcon(pifs.getShopItem().getIcon());
			buff.setAddType(0);
			buff.setQuantity(200);
			buff.setEndtime(lostTime);
			buff.setSurplus((int) (lostTime - nowTime) / 1000);
			buff.setSkillId(0);
			buff.setSkillDetail("");
			ServiceManager.getManager().getBuffService().addBuff(this, buff);
			setHasDoubleCard(true);
		}
		pifs = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(player.getId(), Common.WEALTHID);
		if (null != pifs && pifs.getPLastTime() > 0) {
			buff = new Buff();
			lostTime = pifs.getBuyTime().getTime() + (pifs.getPLastTime() * Common.ONEDAYLONG);
			buff = new Buff();
			buff.setBuffName(pifs.getShopItem().getName());
			buff.setBuffCode(Buff.GOLD);
			buff.setIcon(pifs.getShopItem().getIcon());
			buff.setAddType(0);
			buff.setQuantity(200);
			buff.setEndtime(lostTime);
			buff.setSurplus((int) (lostTime - nowTime) / 1000);
			buff.setSkillId(0);
			buff.setSkillDetail("");
			ServiceManager.getManager().getBuffService().addBuff(this, buff);
		}
		// 获得公会技能
		ServiceManager.getManager().getConsortiaService().deleteBuffRecordOverTime(player.getId());
		List<BuffRecord> skillList = ServiceManager.getManager().getConsortiaService().getBuffRecordByPlayerId(player.getId());
		for (BuffRecord br : skillList) {
			// Hibernate.initialize(br.getConsortiaSkill());
			buff = new Buff();
			buff.setBuffName(br.getBuffName());
			buff.setBuffCode(br.getBuffCode());
			buff.setIcon(br.getConsortiaSkill() != null ? br.getConsortiaSkill().getIcon() : "");
			buff.setAddType(br.getAddType());
			buff.setQuantity(br.getQuantity());
			buff.setEndtime(br.getEndtime().getTime());
			buff.setSurplus(br.getSurplus());
			buff.setSkillId(br.getConsortiaSkill() != null ? br.getConsortiaSkill().getId() : 0);
			buff.setSkillDetail(br.getConsortiaSkill() != null ? br.getConsortiaSkill().getDesc() : "");
			buff.setBufftype(br.getBuffType());
			ServiceManager.getManager().getBuffService().addBuff(this, buff);
		}
	}

	public boolean isVip() {
		if (player.getVipLevel() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取玩家钻石数量
	 * 
	 * @return
	 */
	public int getDiamond() {
		return player.getAmount();
	}

	/**
	 * 初始化玩家勋章数量
	 */
	public void initialMedalNum() {
		PlayerItemsFromShop pif = ServiceManager.getManager().getPlayerItemsFromShopService().getBadgeByPlayerId(getId());
		if (null != pif) {
			this.medalNum = pif.getPLastNum();
		}
	}

	/**
	 * 获取玩家勋章数量
	 * 
	 * @return
	 */
	public int getMedalNum() {
		return this.medalNum;
	}

	public void setMedalNum(int medalNum) {
		this.medalNum = medalNum;
		Map<String, String> info = new HashMap<String, String>();
		info.put("medal", medalNum + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, this);
	}

	/**
	 * 初始化玩家勋章数量
	 */
	public void initialStarSoulDebrisNum() {
		PlayerItemsFromShop pif = ServiceManager.getManager().getPlayerItemsFromShopService()
				.uniquePlayerItem(getId(), Common.STARSOULDEBRISID);
		if (null != pif) {
			this.starSoulDebrisNum = pif.getPLastNum();
		}
	}

	/**
	 * 获取玩家星魂碎片数
	 * 
	 * @return
	 */
	public int getStarSoulDebrisNum() {
		return starSoulDebrisNum;
	}

	/**
	 * 更新玩家星魂碎片数量
	 * 
	 * @param starSoulDebrisNum
	 */
	public void setStarSoulDebrisNum(int starSoulDebrisNum) {
		this.starSoulDebrisNum = starSoulDebrisNum;
	}

	/**
	 * 初始化玩家高级勋章数量
	 */
	public void initialSeniorMedalNum() {
		PlayerItemsFromShop pif = ServiceManager.getManager().getPlayerItemsFromShopService()
				.uniquePlayerItem(getId(), Common.SENIORBADGEID);
		if (null != pif) {
			this.seniorMedalNum = pif.getPLastNum();
		}
	}

	/**
	 * 获取玩家高级勋章数
	 * 
	 * @return
	 */
	public int getSeniorMedalNum() {
		return seniorMedalNum;
	}

	public void setSeniorMedalNum(int seniorMedalNum) {
		this.seniorMedalNum = seniorMedalNum;
	}

	/**
	 * 获取最后上线时间
	 * 
	 * @return
	 */
	public long getLastOnLineTime() {
		if (0 == lastOnLineTime) {
			initialLastOnLineTime();
		}
		return lastOnLineTime;
	}

	/**
	 * 设置最后上线时间
	 * 
	 * @param lastOnLineTime
	 */
	public void setLastOnLineTime(long lastOnLineTime) {
		this.lastOnLineTime = lastOnLineTime;
	}

	private void initialLastOnLineTime() {
		this.lastOnLineTime = ServiceManager.getManager().getPlayerService().getService().getPlayerLastOnLinTime(getId());
	}

	public int getPushCount() {
		return ++pushCount;
	}

	public void setPushCount(int pushCount) {
		this.pushCount = pushCount;
	}

	public void writeLog(Object message) {
		StringBuffer msg = new StringBuffer();
		msg.append("玩家:playerId=");
		msg.append(this.getId());
		msg.append("---playerName:");
		msg.append(this.getName());
		msg.append("---message=");
		msg.append(message);
		log.info(msg);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getBossmapBattleId() {
		return bossmapBattleId;
	}

	public void setBossmapBattleId(int bossmapBattleId) {
		this.bossmapBattleId = bossmapBattleId;
	}

	public int getBossmapRoomId() {
		return bossmapRoomId;
	}

	public void setBossmapRoomId(int bossmapRoomId) {
		this.bossmapRoomId = bossmapRoomId;
	}

	public long getStrengthenTime() {
		return strengthenTime;
	}

	public void setStrengthenTime(long strengthenTime) {
		this.strengthenTime = strengthenTime;
	}

	public void enterRoom(int roomId) {
		roomIds.add(roomId);
		if (roomIds.size() > 5) {
			roomIds.remove(0);
		}
	}

	public boolean checkPlayerRoom(int roomId) {
		return !roomIds.contains(roomId);
	}

	public PlayerItemsFromShop getHead() {
		if (item_head == null) {
			initEquipment();
		}
		return item_head;
	}

	public PlayerItemsFromShop getFace() {
		if (item_face == null) {
			initEquipment();
		}
		return item_face;
	}

	public PlayerItemsFromShop getBody() {
		if (item_body == null) {
			initEquipment();
		}
		return item_body;
	}

	public PlayerItemsFromShop getWeapon() {
		if (item_weapon == null) {
			initEquipment();
		}
		return item_weapon;
	}

	public PlayerItemsFromShop getWing() {
		return item_wing;
	}

	public PlayerItemsFromShop getRing_L() {
		return item_ring_L;
	}

	public PlayerItemsFromShop getRing_R() {
		return item_ring_R;
	}

	public PlayerItemsFromShop getNecklace() {
		return item_necklace;
	}

	public void setHead(PlayerItemsFromShop head) {
		item_head = head;

	}

	public void setFace(PlayerItemsFromShop face) {
		item_face = face;

	}

	public void setBody(PlayerItemsFromShop body) {
		item_body = body;

	}

	public void setWeapon(PlayerItemsFromShop weapon) {
		item_weapon = weapon;

	}

	public void setWing(PlayerItemsFromShop wing) {
		item_wing = wing;

	}

	public void setRing_L(PlayerItemsFromShop ringL) {
		item_ring_L = ringL;

	}

	public void setRing_R(PlayerItemsFromShop ringR) {
		item_ring_R = ringR;

	}

	public void setNecklace(PlayerItemsFromShop necklace) {
		item_necklace = necklace;

	}

	public boolean isHasDoubleCard() {
		return hasDoubleCard;
	}

	public void setHasDoubleCard(boolean hasDoubleCard) {
		this.hasDoubleCard = hasDoubleCard;
	}

	/**
	 * 获取玩家指定转生等级转生前的等级，0表示未找到该转生等级的数据
	 * 
	 * @param rebirthLevel
	 * @return
	 */
	public int getBeforeRebirthLevel(int rebirthLevel) {
		String brl = player.getZsOldLevel();
		if (null != brl && brl.length() > 0) {
			String[] ls = brl.split(",");
			if (rebirthLevel <= ls.length && rebirthLevel > 0) {
				return Integer.parseInt(ls[rebirthLevel - 1]);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public PlayerTask getTaskIngByTaskId(int taskId, byte taskType) {
		if (null != taskIngList) {
			for (PlayerTask ti : taskIngList) {
				if (ti.getTaskId() == taskId && ti.getTaskType() == taskType) {
					return ti;
				}
			}
		}
		return null;
	}

	public int getFight(PlayerItemsFromShop playerItem) {
		int fighting = 0;
		try {
			// 计算玩家力量
			int force = getItemProperty(10, playerItem);
			// 计算玩家敏捷
			int agility = getItemProperty(12, playerItem);
			// 计算玩家爆破范围
			int explodeRadius = item_weapon.getShopItem().getAttackArea();
			// 计算玩家破防率
			int wreckDefense = getItemProperty(7, playerItem);
			// 计算玩家免暴率
			int reduceCrit = getItemProperty(8, playerItem);
			// 计算玩家暴击
			int crit = getItemProperty(15, playerItem);
			// 计算玩家护甲
			int armor = getItemProperty(11, playerItem);
			// 计算玩家的幸运值
			int luck = getItemProperty(14, playerItem);
			// 计算玩家体质
			int physique = getItemProperty(13, playerItem);
			int attack = getItemProperty(1, playerItem);
			int defend = getItemProperty(3, playerItem);
			int maxHP = getItemProperty(2, playerItem);
			// 计算玩家免伤率
			int injuryFree = getItemProperty(6, playerItem);
			// 计算玩家战斗力
			float bj = crit;// 暴击
			float gj = attack;// 攻击
			float sm = maxHP;// 生命
			float pf = wreckDefense / 10000f;// 破防
			float mb = reduceCrit / 10000f;// 免爆
			float ms = injuryFree;// 免伤
			float bp = explodeRadius; // 爆破范围
			float ll = force;// 力量
			float hj = armor;// 护甲
			float mj = agility;// 敏捷
			float xy = luck;// 幸运
			float tz = physique;// 体质
			float fy = defend; // 防御
			// 新战斗力=(1+0.5*力量/(500+力量）+0.5*护甲/(500+护甲）)*(1+0.5*敏捷/(500+敏捷）+0.5*幸运/(500+幸运）)*（1+0.5*体质/(体质+500))
			// *sqrt(4*攻击*生命*(1+破防/(破防+500))/(1-防御/（2*防御+600)))*(1+0.5*暴击/(500+暴击）+0.5*免爆/(500+免爆）)/(1-0.3*免伤/(免伤+1000）)
			// *(爆破范围/（100*2）+1)
			fighting = (int) ((1 + 0.5 * ll / (500 + ll) + 0.5 * hj / (500 + hj)) * (1 + 0.5 * mj / (500 + mj) + 0.5 * xy / (500 + xy))
					* (1 + 0.5 * tz / (tz + 500)) * Math.sqrt(4 * gj * sm * (1 + pf / (pf + 500)) / (1 - fy / (2 * fy + 600)))
					* (1 + 0.5 * bj / (500 + bj) + 0.5 * mb / (500 + mb)) / (1 - 0.3 * ms / (ms + 1000)) * (bp / 200 + 1));
			player.setFight(fighting);
		} catch (Exception e) {
			log.error(e, e);
		}
		return fighting;
	}

	public Map<Integer, Integer> getStarNumMap() {
		if (starNumMap.isEmpty()) {
			starNumMap.put(26, 0);
			starNumMap.put(27, 0);
			starNumMap.put(28, 0);
		}
		return starNumMap;
	}

	/**
	 * 初始化玩家宠物
	 */
	private void initPlayerPet() {
		playerPet = ServiceManager.getManager().getPlayerPetService().getInUsePet(getId());
	}

	/**
	 * 初始化玩家卡阵
	 */
	private void initPlayerCard() {
		try {
			playerCards = ServiceManager.getManager().getPlayerCardsService().getByPlayerId(getId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public PlayerPet getPlayerPet() {
		return playerPet;
	}

	public PlayerCards getPlayerCards() {
		return playerCards;
	}

	/**
	 * 初始化玩家活力值 由于与VIP有关，初始时要放在VIP后
	 * 
	 * @return
	 */
	private void initVigor() {
		if (getPlayerInfo().getVigorUpdateTime() == null) {
			Calendar cal = Calendar.getInstance();
			playerInfo.setVigorUpdateTime(cal.getTime());
			playerInfo.setVigor(getMaxVigor());
		}
		buyVigorMaxCount = ServiceManager.getManager().getVigorService().getMaxCount(player.getVipLevel());
	}

	private int getMaxVigor() {
		return isVip() ? 150 : 120;
	}

	/**
	 * 活力值增涨一点(但不超过最大值) 跨天回复到最大值
	 */
	public int vigorUp(int add) {
		Date vigorUpdateTime = playerInfo.getVigorUpdateTime();
		int vigor = playerInfo.getVigor();
		Calendar cal = Calendar.getInstance();
		if (!DateUtil.isSameDate(vigorUpdateTime, cal.getTime())) {
			// 购买次数清零
			buyVigorCount = 0;
			buyLimitedCount.clear();
		}
		int max = getMaxVigor();
		if (getVigor() >= max) {
			return 0;
		}
		vigor += add;
		playerInfo.setVigor(vigor);
		Map<String, String> info = new HashMap<String, String>();
		info.put("vigor", getVigor() + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, this);
		return add;
	}

	/**
	 * 使用道具增加活力值
	 */
	public int useGoodsUpVigor(int add) {
		Date vigorUpdateTime = playerInfo.getVigorUpdateTime();
		int vigor = playerInfo.getVigor();
		Calendar cal = Calendar.getInstance();
		if (!DateUtil.isSameDate(vigorUpdateTime, cal.getTime())) {
			// 购买次数清零
			buyVigorCount = 0;
			buyLimitedCount.clear();
		}
		vigor += add;
		playerInfo.setVigor(vigor);
		Map<String, String> info = new HashMap<String, String>();
		info.put("vigor", getVigor() + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, this);
		return add;
	}

	/**
	 * 购买活力（当前达到上限则不能买）
	 * 
	 * @param add
	 */
	public boolean buyVigor(int add) {
		return vigorUp(add) > 0;
	}

	/**
	 * 根据上次更新时间到当前时间每5分钟增加一点。
	 */
	public void addVigorFormTime() {
		Date vigorUpdateTime = playerInfo.getVigorUpdateTime();
		if (vigorUpdateTime == null)
			return;
		Calendar cal = Calendar.getInstance();
		long cha = cal.getTime().getTime() - vigorUpdateTime.getTime();
		long min = cha / (1000 * 60);
		// 6分钟加一点 注意：更改这个时间同时也要把定时器时间更改过来
		int addVigor = (int) min / 6;
		if (addVigor > 0) {
			addVigor = addVigor > 120 ? 120 : addVigor;
			for (int i = 0; i < addVigor; i++) {
				vigorUp(1);
			}
			playerInfo.setVigorUpdateTime(cal.getTime());
		}
	}

	/**
	 * 获取活力
	 * 
	 * @return
	 */
	public int getVigor() {
		return playerInfo.getVigor();
	}

	public Date getVigorUpdateTime() {
		return playerInfo.getVigorUpdateTime();
	}

	public void setVigorUpdateTime(Date time) {
		playerInfo.setVigorUpdateTime(time);
	}

	/**
	 * 扣除活力 输入正数
	 * 
	 * @return
	 */
	public void useVigor(int val) {
		int vigor = getVigor();
		if (getVigor() < 0)
			return;
		vigor -= val;
		vigor = vigor < 0 ? 0 : vigor;
		playerInfo.setVigor(vigor);
		Map<String, String> info = new HashMap<String, String>();
		info.put("vigor", getVigor() + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, this);
	}

	/**
	 * 增加购买次数
	 */
	public void addBuyVigorCount() {
		buyVigorCount++;
		Map<String, String> info = new HashMap<String, String>();
		info.put("buyVigorTimes", buyVigorCount + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, this);
	}

	/**
	 * 已购买次数
	 * 
	 * @return
	 */
	public int getBuyVigorCount() {
		return buyVigorCount;
	}

	/**
	 * 最大可以购买次数
	 * 
	 * @return
	 */
	public int getMaxBuyVigorCount() {
		return buyVigorMaxCount;
	}

	public int getWedGetContribute() {
		return wedGetContribute;
	}

	public void setWedGetContribute(int wedGetContribute) {
		this.wedGetContribute = wedGetContribute;
	}

	public int getBattleNum() {
		return battleNum;
	}

	public void setBattleNum(int battleNum) {
		this.battleNum = battleNum;
	}

	/**
	 * 设置时会重新计算玩家的战斗力
	 * 
	 * @param playerPet
	 */
	public void setPlayerPet(PlayerPet playerPet) {
		if (playerPet.isInUsed()) {
			this.playerPet = playerPet;
			updateFight();// 重新计算
		}
	}

	public void setVipInfo(int vipLv, int vipExp) {
		if (vipExp == 0) {
			vipExp = ServiceManager.getManager().getPlayerService().getVIPExp(vipLv);
		}
		player.setVipExp(vipExp);
		player.setVipLevel(vipLv);
		ServiceManager.getManager().getPlayerService().savePlayerData(player);
		// 更新角色信息- VIP等级
		Map<String, String> info = new HashMap<String, String>();
		info.put("vipLevel", player.getVipLevel() + "");
		if (!isVip()) {
			info.put("vipLevel", "0");
			info.put("maxBuyVigorTimes", "1");
		} else {
			buyVigorMaxCount = ServiceManager.getManager().getVigorService().getMaxCount(player.getVipLevel());
			info.put("maxBuyVigorTimes", buyVigorMaxCount + "");
		}
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, this);
	}

	public int[] getPetCultureValue() {
		return petCultureValue;
	}

	public void setPetCultureValue(int[] petCultureValue) {
		this.petCultureValue = petCultureValue;
	}

	public Map<Integer, Integer> getDrawNumMap() {
		return drawNumMap;
	}

	public void setDrawNumMap(Map<Integer, Integer> drawNumMap) {
		this.drawNumMap = drawNumMap;
	}

	public List<Integer> getApplyList() {
		return applyList;
	}

	public void setApplyList(List<Integer> applyList) {
		this.applyList = applyList;
	}

	public Map<Integer, Integer> getDrawFailNumMap() {
		return drawFailNumMap;
	}

	public void setDrawFailNumMap(Map<Integer, Integer> drawFailNumMap) {
		this.drawFailNumMap = drawFailNumMap;
	}

	public Map<Integer, Integer> getRefreshNumMap() {
		return refreshNumMap;
	}

	public void setRefreshNumMap(Map<Integer, Integer> refreshNumMap) {
		this.refreshNumMap = refreshNumMap;
	}

	public boolean isDrawCanRefresh(int type) {
		Boolean canRefresh = drawRefreshMap.get(type);
		if (null == canRefresh) {
			canRefresh = true;
			drawRefreshMap.put(type, canRefresh);
		}
		return canRefresh;
	}

	public void setDrawCanRefresh(int type, boolean drawCanRefresh) {
		this.drawRefreshMap.put(type, drawCanRefresh);
	}

	public boolean isPlayerInChallenge() {
		return playerInChallenge;
	}

	public void setPlayerInChallenge(boolean playerInChallenge) {
		this.playerInChallenge = playerInChallenge;
	}

	public int getTipMark() {
		return tipMark;
	}

	public void setTipMark(int tipMark) {
		this.tipMark = tipMark;
	}

	public boolean isFristEnter() {
		return fristEnter;
	}

	public void setFristEnter(boolean fristEnter) {
		this.fristEnter = fristEnter;
	}

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

	/**
	 * 获得玩家可赠送钻石数方式2
	 * 
	 * @return
	 */
	public int getCanGiveDiamondMode2() {
		if (player.getCanGiveDiamond2() == -1) {
			int rechargeDiamond = (int) ServiceManager.getManager().getPlayerBillService().getPlayerBillCount(player.getId());
			if (ServiceManager.getManager().getVersionService().getVersion().getGiveDiamondRatio() != 0) {
				int webGiveDiamond = (int) ServiceManager.getManager().getPlayerBillService()
						.getPlayerBillCountByOrigin(14, player.getId());
				canGiveDiamond2 = rechargeDiamond * ServiceManager.getManager().getVersionService().getVersion().getGiveDiamondRatio()
						+ webGiveDiamond;
				if (canGiveDiamond2 < 0) {
					canGiveDiamond2 = 0;
				}
			} else {
				canGiveDiamond2 = 0;
			}
			player.setCanGiveDiamond2(canGiveDiamond2);
			ServiceManager.getManager().getPlayerService().savePlayerData(player);
		} else {
			canGiveDiamond2 = player.getCanGiveDiamond2();
		}
		return canGiveDiamond2;
	}

	/**
	 * 获得玩家可赠送钻石数方式1
	 * 
	 * @return
	 */
	public int getCanGiveDiamondMode1() {
		if (player.getCanGiveDiamond() == -1) {
			int rechargeDiamond = ServiceManager.getManager().getIPlayerService().getPlayerRechargeDiamond(player.getId());
			if (rechargeDiamond < 0) {
				canGiveDiamond1 = 0;
				player.setCanGiveDiamond(0);
				ServiceManager.getManager().getPlayerService().savePlayerData(player);
			} else {
				canGiveDiamond1 = rechargeDiamond;
			}
		} else {
			canGiveDiamond1 = player.getCanGiveDiamond();
		}
		return canGiveDiamond1;
	}

	public void setCanGiveDiamond(int canGiveDiamond) {
		if (player.getCanGiveDiamond() == -1) {
			player.setCanGiveDiamond(getCanGiveDiamondMode1());
		} else {
			player.setCanGiveDiamond(player.getCanGiveDiamond() + canGiveDiamond);
		}
		if (player.getCanGiveDiamond() < 0) {
			player.setCanGiveDiamond(0);
		}
		canGiveDiamond1 = player.getCanGiveDiamond();
		if (player.getCanGiveDiamond2() == -1) {
			player.setCanGiveDiamond2(getCanGiveDiamondMode2());
		} else {
			if (canGiveDiamond > 0) {// 充值获得
				player.setCanGiveDiamond2(player.getCanGiveDiamond2() + canGiveDiamond
						* ServiceManager.getManager().getVersionService().getVersion().getGiveDiamondRatio());
			} else {// 转钻扣除
				player.setCanGiveDiamond2(player.getCanGiveDiamond2() + canGiveDiamond);
			}
		}
		if (player.getCanGiveDiamond2() < 0) {
			player.setCanGiveDiamond2(0);
		}
		canGiveDiamond2 = player.getCanGiveDiamond2();
		ServiceManager.getManager().getPlayerService().savePlayerData(player);
	}

	/**
	 * 初始化抽奖数据
	 */
	public void initDrawData() {
		int todayNum = DateUtil.getCurrentDay();
		int playerRefreshDay = player.getRefreshDay();
		if (null != drawRefreshMap && todayNum == playerRefreshDay)
			return;
		player.setRefreshDay(DateUtil.getCurrentDay());
		if (todayNum != playerRefreshDay) {
			starNumMap.clear();
			ServiceManager.getManager().getDrawService().refreshDrawItem(player.getId(), Common.GOLDID, 1);
			ServiceManager.getManager().getDrawService().refreshDrawItem(player.getId(), Common.DIAMONDID, 1);
			ServiceManager.getManager().getDrawService().refreshDrawItem(player.getId(), Common.BADGEID, 1);
		}
		drawRefreshMap = new HashMap<Integer, Boolean>();
		PlayerDraw pd = ServiceManager.getManager().getDrawService().checkPlayerDrawByPlayerId(player.getId());
		String[] markStr;
		String[] str;
		if (null != pd.getDrawFailNum() && pd.getDrawFailNum().length() != 0) {
			markStr = pd.getDrawFailNum().split(",");
			for (String s : markStr) {
				str = s.split("=");
				if (todayNum != playerRefreshDay) {
					drawFailNumMap.put(Integer.parseInt(str[0]), 0);
				} else {
					drawFailNumMap.put(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
				}
			}
		}
		if (null != pd.getDrawNum() && pd.getDrawNum().length() != 0) {
			markStr = pd.getDrawNum().split(",");
			for (String s : markStr) {
				str = s.split("=");
				if (todayNum != playerRefreshDay) {
					drawNumMap.put(Integer.parseInt(str[0]), 0);
				} else {
					drawNumMap.put(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
				}
			}
		}
		if (null != pd.getStarNum() && pd.getStarNum().length() != 0) {
			markStr = pd.getStarNum().split(",");
			for (String s : markStr) {
				str = s.split("=");
				if (todayNum != playerRefreshDay) {
					starNumMap.put(Integer.parseInt(str[0]), 0);
				} else {
					starNumMap.put(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
				}
			}
		}
		if (null != pd.getRefreshNum() && pd.getRefreshNum().length() != 0) {
			markStr = pd.getRefreshNum().split(",");
			for (String s : markStr) {
				str = s.split("=");
				if (todayNum != playerRefreshDay) {
					refreshNumMap.put(Integer.parseInt(str[0]), 0);
				} else {
					refreshNumMap.put(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
				}
			}
		}
	}

	/**
	 * 获取玩家的附加信息
	 * 
	 * @return
	 */
	public PlayerInfo getPlayerInfo() {
		if (null == playerInfo && player.getPlayerInfoId() > 0) {
			playerInfo = ServiceManager.getManager().getIPlayerService().getPlayerInfoById(player.getPlayerInfoId());
		}
		if (null == playerInfo) {
			playerInfo = new PlayerInfo();
			playerInfo = (PlayerInfo) ServiceManager.getManager().getIPlayerService().save(playerInfo);
			player.setPlayerInfoId(playerInfo.getId());
			ServiceManager.getManager().getPlayerService().savePlayerData(player);
		}
		return playerInfo;
	}

	/**
	 * 重置玩家的活跃度奖励信息
	 */
	public void resetActiveReward() {
		getPlayerInfo();
		playerInfo.setActivity(0);
		playerInfo.updateActiveRewards(-1);
		updatePlayerInfo();
		updateButtonInfo(Common.BUTTON_ID_ACTIVE, false, 0);
		GameLogService.addActivity(getId(), getLevel(), 0, 0, 0);
	}

	/**
	 * 更新玩家附加信息
	 */
	public void updatePlayerInfo() {
		getPlayerInfo();
		ServiceManager.getManager().getIPlayerService().update(playerInfo);
	}

	public void initButtonInfo() {
		if (null == buttonInfo) {
			buttonInfo = new PlayerButtonInfo();
			List<ButtonInfo> buttonList = ServiceManager.getManager().getOperationConfigService().getButtonList();
			int buttonCount = buttonList.size();
			int[] buttonId = new int[buttonCount];
			byte[] buttonType = new byte[buttonCount];
			int[] buttonSort = new int[buttonCount];
			boolean[] isHighlight = new boolean[buttonCount];
			this.buttonInfo.setButtonId(buttonId);
			this.buttonInfo.setButtonType(buttonType);
			this.buttonInfo.setButtonSort(buttonSort);
			this.buttonInfo.setIsHighlight(isHighlight);
			ButtonInfo buttonInfo;
			for (int i = 0; i < buttonCount; i++) {
				buttonInfo = buttonList.get(i);
				buttonId[i] = buttonInfo.getButtonId();
				buttonType[i] = buttonInfo.getButtonType();
				buttonSort[i] = buttonInfo.getButtonSort();
				if (Common.BUTTON_ID_VIP == buttonInfo.getButtonId() && isVip()) {
					buttonSort[i] += 30;
				} else if (Common.BUTTON_ID_RECHARGE == buttonInfo.getButtonId()
						&& !ServiceManager.getManager().getPlayerBillService().playerIsFirstCharge(getPlayer())) {
					buttonSort[i] += 30;
				} else if (Common.BUTTON_ID_FUND == buttonInfo.getButtonId()
						&& null != ServiceManager.getManager().getPlayerFundService().getPlayerFund(getId())) {
					buttonSort[i] += 30;
				} else if (Common.BUTTON_ID_REG == buttonInfo.getButtonId()
						&& !ServiceManager.getManager().getTaskService().getService().playerRewardInfo(this.getId())
								.isSign(DateUtil.getCurrentDay())) {
					isHighlight[i] = true;
				} else if (Common.BUTTON_ID_LOVE == buttonInfo.getButtonId()) {
					PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
							.uniquePlayerItem(getId(), Common.LOVEID);
					if (null != pifs && pifs.getPLastNum() > 0) {
						isHighlight[i] = true;
					}
				} else if (Common.BUTTON_ID_ACTIVE == buttonInfo.getButtonId()
						&& ServiceManager.getManager().getTaskService().getService().hasActiveReward(this)) {
					isHighlight[i] = true;
				}
			}
		}
	}

	/**
	 * 同步客户端按钮状态
	 */
	public void synButtonInfo() {
		checkButtonInfo();
		// System.out.println("send buttonInfo ----------------------");
		sendData(buttonInfo);
	}

	/**
	 * 检查按钮是否最新状态
	 */
	public void checkButtonInfo() {
		initButtonInfo();
		for (int i = 0; i < buttonInfo.getButtonId().length; i++) {
			switch (buttonInfo.getButtonId()[i]) {
				case Common.BUTTON_ID_KING :
					// System.out.println("----------------"+ServiceManager.getManager().getChallengeSerService().isInTime());
					if (ServiceManager.getManager().getChallengeSerService().isStart()) {
						buttonInfo.getIsHighlight()[i] = true;
						buttonInfo.getButtonSort()[i] = 4;
					} else {
						buttonInfo.getIsHighlight()[i] = false;
						buttonInfo.getButtonSort()[i] = 34;
					}
					break;
				case Common.BUTTON_ID_BOSS :
					WorldBossRoomService roomService = WorldBossRoomService.getInstance();
					WorldBossRoom worldBossRoom = roomService.getRoomByMap(Common.WORLDBOSS_DEFAULT_MAP);
					// BOSS已死了，让图标熄灭
					if (worldBossRoom == null || worldBossRoom.getBoss() == null || worldBossRoom.getBoss().isDead()) {
						buttonInfo.getIsHighlight()[i] = false;
						buttonInfo.getButtonSort()[i] = 35;
					} else {
						buttonInfo.getIsHighlight()[i] = true;
						buttonInfo.getButtonSort()[i] = 5;
					}

					break;
			}
		}
	}

	/**
	 * 更新玩家的按钮信息
	 * 
	 * @param buttonId
	 *            按钮id
	 * @param isHighlight
	 *            是否高亮
	 * @param sort
	 *            增加的权值
	 */
	public void updateButtonInfo(int buttonId, boolean isHighlight, int sort) {
		initButtonInfo();
		ButtonInfo button = ServiceManager.getManager().getOperationConfigService().getButtonInfoById(buttonId);
		for (int i = 0; i < buttonInfo.getButtonId().length; i++) {
			if (buttonInfo.getButtonId()[i] == button.getButtonId()) {
				buttonInfo.getIsHighlight()[i] = isHighlight;
				if (sort < 0 || buttonInfo.getButtonSort()[i] == button.getButtonSort())
					buttonInfo.getButtonSort()[i] += sort;
			}
		}
		synButtonInfo();
	}

	/**
	 * 每天重置玩家的按钮信息
	 */
	public void resetButtonInfo() {
		initButtonInfo();
		for (int i = 0; i < buttonInfo.getButtonId().length; i++) {
			if (buttonInfo.getButtonId()[i] == Common.BUTTON_ID_REG || buttonInfo.getButtonId()[i] == Common.BUTTON_ID_ACTIVE) {
				ButtonInfo button = ServiceManager.getManager().getOperationConfigService().getButtonInfoById(buttonInfo.getButtonId()[i]);
				buttonInfo.getButtonSort()[i] = button.getButtonSort();
				if (buttonInfo.getButtonId()[i] == Common.BUTTON_ID_REG) {
					buttonInfo.getIsHighlight()[i] = true;
				}
			}
		}
		synButtonInfo();
	}

	public PlayerButtonInfo getButtonInfo() {
		return buttonInfo;
	}

	public void setButtonInfo(PlayerButtonInfo buttonInfo) {
		this.buttonInfo = buttonInfo;
	}

	/**
	 * 玩家离线处理
	 */
	public void logout() {
		ServiceManager.getManager().getHttpThreadPool().execute(new LogoutThread(this));
	}

	/**
	 * 玩家上线处理
	 */
	public void login() {
		ServiceManager.getManager().getHttpThreadPool().execute(new LoginThread(this));
	}

	public boolean isCacheOk() {
		return cacheOk;
	}

	public void setCacheOk(boolean cacheOk) {
		this.cacheOk = cacheOk;
	}

	public class LogoutThread implements Runnable {
		private WorldPlayer player;

		public LogoutThread(WorldPlayer player) {
			this.player = player;
		}

		public void run() {
			// 更新玩家信息
			player.updatePlayerInfo();
			player.setChannelId(0);
			ServiceManager.getManager().getPlayerTaskTitleService().savePlayerTaskTitle(player);
			ServiceManager.getManager().getRobotService().addRobot(player);
			ServiceManager.getManager().getPlayerService().clearPlayer(player);
			ServiceManager.getManager().getLogSerivce().savePlayerOnlineLog(player);
			ServiceManager.getManager().getLogSerivce().saveWOMTopRecord(player.getPlayer());

			// 更新玩家附近在线状态
			ServiceManager.getManager().getNearbyService().PlayerOffline(player);
			// 清除玩家在婚礼大厅的状态
			ServiceManager.getManager().getMarryService().deleteWeddingRoomPlayer(player);
			// 记录玩家退出日志
			GameLogService.logout(player.getId(), player.getLevel(), player.getOnLineTime());
			String area = Server.config.getAreaId().toUpperCase();
			// 越南服玩家下线需回调第三方
			if (Access_VTC.isVTC(player.getChannelId())) {
				Client client = player.getClient();
				String userid = client.getName();
				String username = "";
				int serverid = Integer.parseInt(area.substring(area.indexOf("_") + 1)) + 1;
				Access_VTC vtc = new Access_VTC();
				vtc.logout(userid, username, player.getId(), player.getName(), serverid, player.getLevel());
			}
			GetFriendListHandler handler = (GetFriendListHandler) ProtocolFactory.getDataHandler(GetFriendList.class);
			// 下线通知粉丝
			handler.notifyFans(player);
		}
	}

	public class LoginThread implements Runnable {
		private WorldPlayer player;

		public LoginThread(WorldPlayer player) {
			this.player = player;
		}

		@Override
		public void run() {
			try {
				// 更新玩家的最新登录时间
				long loginTime = player.getLoginTime();
				player.setLoginTime(System.currentTimeMillis());
				player.setCacheOk(false);
				player.setPushCount(0);
				try {
					// 初化活力
					initVigor();
					// 卡阵
					initPlayerCard();
					// 同步并重置每日任务
					ServiceManager.getManager().getPlayerService().updateTask(player);
					// 同步玩家可提交任务数量
					ServiceManager.getManager().getPlayerTaskTitleService().synCommitTaskNum(player);
					// 同步玩家可领取奖励数量
					ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
					// 检查玩家的任务是否完整
					ServiceManager.getManager().getTaskService().checkRegisterTask(player);
					// 推送vip 领取情况
					ServiceManager.getManager().getPlayerService().sendVipReceiveInfo(player);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				// 同步玩家任务列表
				ServiceManager.getManager().getTaskService().getService().synPlayerTaskList(player);
				ServiceManager.getManager().getRobotService().removeRobot(player);
				long nowTime = ServiceUtils.getMorning(new Date()).getTime();
				// 每天首次登录执行
				if (loginTime < nowTime) {
					// 初始化玩家装备,buff等属性
					player.initialPlayerItem();
					player.initialBuff();
					player.setEverydayFirstLogin(true);
					ServiceManager.getManager().getTaskService().getService().playerLogin(player.getId());
					ServiceManager.getManager().getPlayerItemsFromShopService().checkOvertimeAfterNDay(player.getId());
					// 置换石头
					ServiceManager.getManager().getPlayerItemsFromShopService().checkPlayerStone(player);
				} else {
					player.setEverydayFirstLogin(false);
				}
				// 重置玩家的在线时间
				player.setOnLineTime(0);
				// 缓存中心
				ServiceManager.getManager().getPlayerService().sendPlayerInfo(player);
				ServiceManager.getManager().getPlayerItemsFromShopService().sendPlayerItem(player);
				ServiceManager.getManager().getStrengthenService().sendRate(player);
				ServiceManager.getManager().getStarsInfoService().sendConfig(player);
				ServiceManager.getManager().getPlayerPetService().sendPlayerPet(player);
				ServiceManager.getManager().getStarsInfoService().sendConfig(player);
				ServiceManager.getManager().getShopItemService().sendList(player);
				ServiceManager.getManager().getLotteryService().sendWishList(player);
				ServiceManager.getManager().getLotteryService().sendZflhList(player);
				player.setCacheOk(true);
				// 推送玩家按钮信息
				player.synButtonInfo();
				ServiceManager.getManager().getChatService().initPlayerChannels(player);
				// 玩家召回奖励
				ServiceManager.getManager().getPlayerService().recallPlayer(player);

				// vip上线公告
				ServiceManager.getManager().getChatService().wellcomVIP(player);
				ServiceManager.getManager().getDailyActivityService().loginDailyActivity(player.getId());
				if (!DateUtil.isSameDate(player.getPlayer().getUpdateTime(), new Date())) {
					ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
				}
				if (player.isEverydayFirstLogin()) {
					ServiceManager.getManager().getTaskService().logined(player);
				}
				// 更新玩家附近在线状态
				ServiceManager.getManager().getNearbyService().PlayerOnline(player);
				// 更新玩家活力值
				player.addVigorFormTime();
				// 更新玩家单人副本通过次数
				ServiceManager.getManager().getSingleMapService().resetPassTimes(player.getId(), true);
				// 更新玩家组队副本通过次数
				ServiceManager.getManager().getPlayerBossmapService().resetPassTimes(player.getId(), true);
				// 推送好友列表
				new GetFriendListHandler().sendFriendList(player);
				// 推送黑名单列表
				new GetBlackListHandler().sendBlackList(player);
				// 上线通知粉丝
				new GetFriendListHandler().notifyFans(player);
				// 推送未读邮件个数
				ServiceManager.getManager().getMailService().sendMailStatus(player);
				// 跨月清空祝福碎片
				ServiceManager.getManager().getLotteryService().resetZfsp(player, true);

				// 月卡已经过期并且还有提醒次数
				if (player.getPlayerInfo().getRemainingRemindNum() > 0) {
					ServiceManager.getManager().getMonthCardService().sendCardOverdue(player);
					player.getPlayerInfo().setRemainingRemindNum(player.getPlayerInfo().getRemainingRemindNum() - 1);
					player.updatePlayerInfo();
				} else {
					if (player.getPlayerInfo().getBuyMonthCardTime() != null) {
						// 判断月卡是否到期
						Date dueTime = DateUtil
								.afterNowNDay(player.getPlayerInfo().getBuyMonthCardTime(), Common.MonthCardEffectiveDaysNum);
						int remainingDays = DateUtil.compareDateOnDay(dueTime, new Date());
						if (remainingDays < 0 && player.getPlayerInfo().getRemainingRemindNum() < 0) {
							ServiceManager.getManager().getMonthCardService().sendCardOverdue(player);
							player.getPlayerInfo().setRemainingRemindNum(player.getPlayerInfo().getRemainingRemindNum() - 1);
							player.updatePlayerInfo();
						}
					}
				}

				// 排名第一的公会会长上线发公告
				if (ServiceManager.getManager().getTheadConsortiaService().isMaxPrestige()) {
					MaxPrestigeVo maxPrestigeVo = ServiceManager.getManager().getTheadConsortiaService().getMaxPrestige();
					// 公会信息要动态取，要不然会长让位还会显示旧会长
					Consortia consortia = ServiceManager.getManager().getConsortiaService()
							.getConsortiaById(maxPrestigeVo.getConsortiaId());
					if (consortia != null && player.getId() == consortia.getPresident().getId()) {
						String msg;
						switch (ServiceUtils.getRandomNum(0, 3)) {
							case 1 :
								msg = TipMessages.MAXPRESTIGE1;
								break;
							case 2 :
								msg = TipMessages.MAXPRESTIGE2;
								break;
							default :
								msg = TipMessages.MAXPRESTIGE3;
								break;
						}
						Player presiden = ServiceManager.getManager().getPlayerService().getPlayerById(consortia.getPresident().getId());
						msg = msg.replaceAll(Pattern.quote("{1}"), maxPrestigeVo.getConsortiaName());
						msg = msg.replaceAll(Pattern.quote("{2}"), presiden.getName());
						ServiceManager.getManager().getChatService().sendBulletinToWorld(msg, "", true);
					}
				}
				// 记录玩家登入日志
				GameLogService.login(player.getId(), player.getLevel());
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		}
	}

	/**
	 * 获取玩家的头像信息
	 * 
	 * @return
	 */
	public PlayerPicture getPlayerPicture() {
		if (null == this.playerPicture) {
			this.playerPicture = ServiceManager.getManager().getPictureUploadService().getPictureUploadById(getId());
			if (null == playerPicture) {
				playerPicture = new PlayerPicture();
				playerPicture.setAge(0);
				playerPicture.setConstellation(1);
				playerPicture.setPlayerId(player.getId());
				playerPicture.setPersonContext("");
				playerPicture.setPictureUrlPass("");
				playerPicture.setPictureUrlTest("");
				playerPicture.setUpdateTime(new Date());
				playerPicture = (PlayerPicture) ServiceManager.getManager().getRechargeService().save(playerPicture);
			}
		}
		return this.playerPicture;
	}

	/**
	 * 初始化玩家的头像信息
	 * 
	 * @return
	 */
	public void initPlayerPicture() {
		this.playerPicture = ServiceManager.getManager().getPictureUploadService().getPictureUploadById(getId());
		if (null == playerPicture) {
			playerPicture = new PlayerPicture();
			playerPicture.setAge(0);
			playerPicture.setConstellation(1);
			playerPicture.setPlayerId(player.getId());
			playerPicture.setPersonContext("");
			playerPicture.setPictureUrlPass("");
			playerPicture.setPictureUrlTest("");
			playerPicture.setUpdateTime(new Date());
			playerPicture = (PlayerPicture) ServiceManager.getManager().getRechargeService().save(playerPicture);
		}
	}

	public int getSingleMapId() {
		return singleMapId;
	}

	/**
	 * 是否在单人副本战斗中
	 * 
	 * @return
	 */
	public boolean isInSingleMap() {
		return singleMapId > 0;
	}

	/**
	 * 进入单人副本战斗
	 * 
	 * @param mapId
	 */
	public void inSingleMap(int mapId) {
		this.singleMapId = mapId;
	}

	/**
	 * 离开单人副本战斗
	 */
	public void outSingleMap() {
		this.singleMapId = 0;
	}

	/**
	 * 获取玩家的头像信息
	 * 
	 * @return
	 */
	public void setPlayerPicture(PlayerPicture playerPicture) {
		this.playerPicture = playerPicture;
		ServiceManager.getManager().getPlayerService().sendUpdatePrivateInfo(this, playerPicture);
	}

	public int getSingleMapGetDiamond() {
		return singleMapGetDiamond;
	}

	/**
	 * @param singleMapGetDiamond
	 */
	public void setSingleMapGetDiamond(int singleMapGetDiamond) {
		this.singleMapGetDiamond = singleMapGetDiamond;
	}

	public int getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	public long getInTime() {
		return inTime;
	}

	public void setInTime(long inTime) {
		this.inTime = inTime;
	}

	public void setRobotList(List<String> robotList) {
		this.robotList = robotList;
	}

	/**
	 * 获取玩家所在的机器人列表
	 * 
	 * @return
	 */
	public List<String> getRobotList() {
		return robotList;
	}

	/**
	 * 判断玩家是否在机器人列表中
	 * 
	 * @return
	 */
	public boolean isInRobotList() {
		return this.robotList != null;
	}

	/**
	 * 玩家是否新手玩家
	 * 
	 * @return
	 */
	public boolean isNewPlayer() {
		return player.getLevel() <= ServiceManager.getManager().getVersionService().getVersion().getNewPlayerLevel();
	}

	public long getLastSendMailTime() {
		return lastSendMailTime;
	}

	public void setLastSendMailTime(long lastSendMailTime) {
		this.lastSendMailTime = lastSendMailTime;
	}

	/**
	 * 获取玩家的胜率
	 * 
	 * @return
	 */
	public int getWinRate() {
		if (player.getPlayNum() > 0) {
			return (player.getWinNum() / player.getPlayNum()) * 10000;
		} else {
			return 0;
		}
	}

	/**
	 * 获取卡阵里套卡的ID(数量大于等于3)
	 * 
	 * @return
	 */
	public int getCardGroupId() {
		if (playerCards == null)
			return -1;
		int count = 0;
		List<Integer> list = new ArrayList<Integer>();
		list.add(playerCards.getGroupJ());
		list.add(playerCards.getGroupM());
		list.add(playerCards.getGroupS());
		list.add(playerCards.getGroupH());
		list.add(playerCards.getGroupT());
		count = countListItem(list, playerCards.getGroupJ());
		if (count > 2)
			return playerCards.getGroupJ(); // 任意一个元素与其它两个或以上元素属同一套的，就没必要统计其它元素。
		count = countListItem(list, playerCards.getGroupM());
		if (count > 2)
			return playerCards.getCardMId();
		count = countListItem(list, playerCards.getGroupS());
		if (count > 2)
			return playerCards.getCardSId();
		count = countListItem(list, playerCards.getGroupH());
		if (count > 2)
			return playerCards.getGroupH();
		count = countListItem(list, playerCards.getGroupT());
		if (count > 2)
			return playerCards.getGroupT();
		return -1;
	}

	/**
	 * 获取卡阵里套卡的数量
	 * 
	 * @return
	 */
	public int getCardGroupNum(int groupId) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(playerCards.getGroupJ());
		list.add(playerCards.getGroupM());
		list.add(playerCards.getGroupS());
		list.add(playerCards.getGroupH());
		list.add(playerCards.getGroupT());
		return countListItem(list, groupId);
	}

	private int countListItem(List<Integer> list, int item) {
		int count = 0;
		for (Integer i : list) {
			if (i.intValue() == item) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 获取卡牌兑换列表
	 * 
	 * @return
	 */
	public List<ExchangeCard> getExchangeCardList() {
		Calendar cal = Calendar.getInstance();
		long nowTime = cal.getTime().getTime();
		if (exchangeCardTime == 0 || exchangeCardTime < nowTime || exchangeCardList.size() < 1) {
			reExchangeCardList();
		}
		return exchangeCardList;
	}

	/**
	 * 刷新卡片兑换列表
	 * 
	 * @return
	 */
	public List<ExchangeCard> reExchangeCardList() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 2);// 两小时后刷 新
		exchangeCardTime = cal.getTime().getTime();
		exchangeCardList = ServiceManager.getManager().getPlayerCardsService().getExchangeCard();
		return exchangeCardList;
	}

	public void setExchangeCardList(List<ExchangeCard> exchangeCardList) {
		this.exchangeCardList = exchangeCardList;
	}

	public long getExchangeCardTime() {
		return exchangeCardTime;
	}

	public void setExchangeCardTime(long exchangeCardTime) {
		this.exchangeCardTime = exchangeCardTime;
	}

	public Map<Integer, Integer> getBuyLimitedCount() {
		return buyLimitedCount;
	}

	/**
	 * 获取玩家邀请次数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getInviteTimes(Integer playerId) {
		InviteInfo inviteInfo = inviteInfoMap.get(playerId);
		if (null == inviteInfo) {
			return 0;
		} else if ((System.currentTimeMillis() - inviteInfo.getInviteTime()) > 60000) {
			inviteInfo.setInviteTime(System.currentTimeMillis());
			inviteInfo.setInviteCount(0);
		}
		return inviteInfo.getInviteCount();
	}

	/**
	 * 记录玩家邀请信息
	 * 
	 * @param playerId
	 */
	public void invitePlayer(Integer playerId) {
		InviteInfo inviteInfo = inviteInfoMap.get(playerId);
		if (null == inviteInfo) {
			inviteInfo = new InviteInfo();
			inviteInfo.setInviteTime(System.currentTimeMillis());
			inviteInfoMap.put(playerId, inviteInfo);
		}
		inviteInfo.addInviteCount();
	}

}
