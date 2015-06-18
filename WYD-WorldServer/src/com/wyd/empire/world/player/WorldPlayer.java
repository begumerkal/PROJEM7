package com.wyd.empire.world.player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.channel.service.impl.Access_VTC;
import com.wyd.empire.protocol.data.player.PlayerButtonInfo;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.ExchangeCard;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
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

	private List<ExchangeCard> exchangeCardList = new ArrayList<ExchangeCard>();// 卡牌兑换列表
	private long exchangeCardTime = 0;// 卡牌兑换列表下次刷新时间
	private boolean cacheOk = false;// 标记是否完全推送了所有缓存
	private Map<Integer, Integer> buyLimitedCount = new HashMap<Integer, Integer>(); // 限量物品购买次数
																						// KEY:itemId,VAL:count

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

		initialPracticeBonusExp();
		// 这个要放在最后
		initialPlayerItem();
		updateFight();
	}

	public void firstLoad() {
		firstOnlin = false;
		initialLastOnLineTime();
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

	/**
	 * 获取玩家显示的称号
	 * 
	 * @return
	 */
	public String getPlayerTitle() {
		String title = "";

		return title;
	}

	/**
	 * 初始化玩家成就及任务列表
	 */
	@SuppressWarnings("unchecked")
	public void initialPlayerTaskTitle() {
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
		return 1;
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
	 * 检查身上装备是否过期
	 */
	public void initialPlayerItem() {
 
	}

	/**
	 * 更新玩家状态 （玩家登录、换装、换宠时需要调用） 并推送相关协议到客户端
	 */
	public void updateFight() {
	}

	/**
	 * 初始化玩家装备.如果没有则给默认装
	 */
	public void initEquipment() {
	}

	/**
	 * 初始化玩家技能道具（角色登录时调用）
	 */
	public void initialPlayerJnDj() {
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

	public Map<Integer, Integer> getStarNumMap() {
		if (starNumMap.isEmpty()) {
			starNumMap.put(26, 0);
			starNumMap.put(27, 0);
			starNumMap.put(28, 0);
		}
		return starNumMap;
	}

	public PlayerPet getPlayerPet() {
		return playerPet;
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

	/**
	 * 初始化抽奖数据
	 */

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

	/**
	 * 更新玩家附加信息
	 */
	public void updatePlayerInfo() {
		getPlayerInfo();
		ServiceManager.getManager().getIPlayerService().update(playerInfo);
	}

	/**
	 * 同步客户端按钮状态
	 */
	public void synButtonInfo() {
		// System.out.println("send buttonInfo ----------------------");
		sendData(buttonInfo);
	}

	/**
	 * 检查按钮是否最新状态
	 */

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

	/**
	 * 每天重置玩家的按钮信息
	 */

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
			ServiceManager.getManager().getPlayerService().clearPlayer(player);

			// 记录玩家退出日志
			GameLogService.logout(player.getId(), player.getLevel(), player.getOnLineTime());
			String area = WorldServer.config.getAreaId().toUpperCase();
			// 越南服玩家下线需回调第三方
			if (Access_VTC.isVTC(player.getChannelId())) {
				Client client = player.getClient();
				String userid = client.getName();
				String username = "";
				int serverid = Integer.parseInt(area.substring(area.indexOf("_") + 1)) + 1;
				Access_VTC vtc = new Access_VTC();
				vtc.logout(userid, username, player.getId(), player.getName(), serverid, player.getLevel());

			}
		}
	}
	public class LoginThread implements Runnable {
		private WorldPlayer player;

		public LoginThread(WorldPlayer player) {
			this.player = player;
		}

		@Override
		public void run() {
		}
	}

	/**
	 * 获取玩家的头像信息
	 * 
	 * @return
	 */

	/**
	 * 初始化玩家的头像信息
	 * 
	 * @return
	 */

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

	/**
	 * 离开单人副本战斗
	 */

	/**
	 * 获取玩家的头像信息
	 * 
	 * @return
	 */

	public int getSingleMapGetDiamond() {
		return singleMapGetDiamond;
	}

	/**
	 * @param singleMapGetDiamond
	 */

	public int getInterfaceId() {
		return interfaceId;
	}

	public long getInTime() {
		return inTime;
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

	/**
	 * 玩家是否新手玩家
	 * 
	 * @return
	 */
	public boolean isNewPlayer() {
		return false;
	}

	public long getLastSendMailTime() {
		return lastSendMailTime;
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

	public long getExchangeCardTime() {
		return exchangeCardTime;
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

	/**
	 * 记录玩家邀请信息
	 * 
	 * @param playerId
	 */

}
