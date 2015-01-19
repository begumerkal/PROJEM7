package com.wyd.empire.world.battle;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.protocol.data.battle.PostionsForPlayers;
import com.wyd.empire.protocol.data.system.TopHands;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 类 <code>BattleTeam</code> 战斗组
 * 
 * @author Administrator
 */
public class BattleTeam {
	public static final int BEMODEJJ = 1;
	public static final int BEMODEFH = 2;
	public static final int BEMODEPW = 3;
	public static final int PEMODE1V1 = 1;
	public static final int PEMODE2V2 = 2;
	public static final int PEMODE3V3 = 3;
	private int battleId; // 战斗组id
	private int battleMode; // 战斗模式,1、竞技模式，2、复活模式，3、混战模式，4、排位赛，5公会战，6挑战赛
	private int playerNumMode; // 对战人数模式,1=1v1，2=2v2，3=3v3
	private int togetherType; // 撮合类型（1随机、2自由）
	private int fightWithAI; // 是否与AI对战(0否，1是)
	private long startTime; // 对战开始时间
	private int aicounter = 0; // 控制者id
	private Vector<Combat> combatList; // 战斗组里面的成员
	private ConcurrentHashMap<Integer, Combat> combatMap; // 战斗组里面的成员
	private Vector<Integer> playerIds;
	private boolean curRound = false; // 本轮操作是否完成
	private int firstHurtPlayerId = 0; // 第一个打中别人的角色
	private int round = 1; // 战斗回合数
	private int wind = 0; // 当前战斗风况
	private int camp0BeKillCount = 0; // 阵营1玩家被击杀数
	private int camp1BeKillCount = 0; // 阵营2玩家被击杀数
	private int actionIndex = 0; // 当前行动角色序列
	private boolean useKill; // 是否使用用技能
	private boolean useBigKill; // 是否使用大招
	private int stat = 0; // 战斗阶段 0：加载阶段，1：战斗阶段
	private PostionsForPlayers postionsForPlayers; // 玩家所在地图初始位置
	private boolean newRun; // 是否新回合
	private boolean over = false; // 该对战是否已结束
	private boolean enemyMark = false; // 是否是敌对工会
	private int mapId; // 对战的地图ID
	private int startMode; // 撮合方式,1、随机撮合, 2、自由模式
	private String useTools; // 战斗中使用的技能
	private int[] battleRand; // 游戏随机数
	private int eventId = 0; // 特殊事件ID

	public BattleTeam() {
		battleId = this.hashCode();
		combatList = new Vector<Combat>();
		combatMap = new ConcurrentHashMap<Integer, Combat>();
		wind = ServiceUtils.getRandomNum(0, 7) - ServiceUtils.getRandomNum(0, 7);
	}

	public int getBattleId() {
		return battleId;
	}

	public int getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int battleMode) {
		this.battleMode = battleMode;
	}

	public Vector<Combat> getCombatList() {
		return combatList;
	}

	public void setCombatList(Vector<Combat> combatList) {
		this.combatList = combatList;
	}

	public Map<Integer, Combat> getCombatMap() {
		return combatMap;
	}

	public void enter(Combat combat) {
		combatList.add(combat);
		combatMap.put(combat.getId(), combat);
	}

	public int getAicounter() {
		return aicounter;
	}

	public void setAicounter(int aicounter) {
		this.aicounter = aicounter;
	}

	public Vector<Integer> getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(Vector<Integer> playerIds) {
		this.playerIds = playerIds;
	}

	public void ready(int playerId) {
		Combat combat = combatMap.get(playerId);
		combat.setState(2);
	}

	public int getPlayerCount() {
		int i = 0;
		for (Combat combat : combatList) {
			if (!combat.isRobot() && !combat.isLost()) {
				i++;
			}
		}
		return i;
	}

	public boolean isCurRound() {
		return curRound;
	}

	public void setCurRound(boolean curRound) {
		this.curRound = curRound;
	}

	public int getFirstHurtPlayerId() {
		return firstHurtPlayerId;
	}

	public void setFirstHurtPlayerId(int firstHurtPlayerId) {
		this.firstHurtPlayerId = firstHurtPlayerId;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getPlayerNumMode() {
		return playerNumMode;
	}

	public void setPlayerNumMode(int playerNumMode) {
		this.playerNumMode = playerNumMode;
	}

	public int getTogetherType() {
		return togetherType;
	}

	public void setTogetherType(int togetherType) {
		this.togetherType = togetherType;
	}

	public int getFightWithAI() {
		return fightWithAI;
	}

	public void setFightWithAI(int fightWithAI) {
		this.fightWithAI = fightWithAI;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getWind() {
		return wind;
	}

	public void setWind(int wind) {
		this.wind = wind;
	}

	public int getCamp0BeKillCount() {
		return camp0BeKillCount;
	}

	public void setCamp0BeKillCount(int camp0BeKillCount) {
		this.camp0BeKillCount = camp0BeKillCount;
	}

	public int getCamp1BeKillCount() {
		return camp1BeKillCount;
	}

	public void setCamp1BeKillCount(int camp1BeKillCount) {
		this.camp1BeKillCount = camp1BeKillCount;
	}

	public boolean isCampAllLost(int camp) {
		for (Combat combat : combatList) {
			if (combat.getCamp() == camp && !combat.isLost()) {
				return false;
			}
		}
		return true;
	}

	public int getActionIndex() {
		return actionIndex;
	}

	public void setActionIndex(int actionIndex) {
		this.actionIndex = actionIndex;
	}

	/**
	 * 获取本次行动的角色
	 * 
	 * @return
	 */
	public Combat getActionPlayer() {
		if (actionIndex >= combatList.size()) {
			actionIndex = 0;
		}
		// System.out.println("actionIndex:"+actionIndex);
		Combat combat = combatList.get(actionIndex++);
		// ServiceManager.getManager().getBattleTeamService().sendSort(battleId,
		// combat.getPlayer().getId());
		return combat;
	}

	public boolean isUseKill() {
		return useKill;
	}

	public void setUseKill(boolean useKill) {
		this.useKill = useKill;
	}

	public boolean isUseBigKill() {
		return useBigKill;
	}

	public void setUseBigKill(boolean useBigKill) {
		this.useBigKill = useBigKill;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public PostionsForPlayers getPostionsForPlayers() {
		return postionsForPlayers;
	}

	public void setPostionsForPlayers(PostionsForPlayers postionsForPlayers) {
		this.postionsForPlayers = postionsForPlayers;
	}

	public boolean isNewRun() {
		return newRun;
	}

	public void setNewRun(boolean newRun) {
		this.newRun = newRun;
	}

	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public boolean isEnemyMark() {
		return enemyMark;
	}

	public void setEnemyMark(boolean enemyMark) {
		this.enemyMark = enemyMark;
	}

	public void sendEmptMessage(TopHands thh) {
		for (Combat combat : combatList) {
			if (!combat.isRobot() && !combat.isLost() && null != combat.getPlayer()) {
				combat.getPlayer().sendData(thh);
			}
		}
	}

	/**
	 * 获取战斗成员数量
	 * 
	 * @return
	 */
	public int getCombatNum() {
		return combatList.size();
	}

	/**
	 * 判断是否所有玩家都被冰冻
	 * 
	 * @return
	 */
	public boolean allFrozen() {
		for (Combat combat : combatList) {
			if (!combat.isLost() && !combat.isDead() && combat.getFrozenTimes() < 1) {
				return false;
			}
		}
		return true;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getStartMode() {
		return startMode;
	}

	public void setStartMode(int startMode) {
		this.startMode = startMode;
	}

	public String getUseTools() {
		return useTools == null ? "" : useTools;
	}

	public void setUseTools(String useTools) {
		this.useTools = useTools;
	}

	public int[] getBattleRand() {
		return battleRand;
	}

	public void setBattleRand(int[] battleRand) {
		this.battleRand = battleRand;
	}

	/**
	 * 地图特殊事件
	 * 
	 * @return
	 */
	public void createEventId() {
		eventId = -1;
		com.wyd.empire.world.bean.Map map = ServiceManager.getManager().getMapsService().getMapById(getMapId());
		if (map.getEventId() != -1) {
			int random = ServiceUtils.getRandomNum(1, 10000);
			if (random < map.getEventRate()) {
				eventId = map.getEventId();
			}
		}
		// 龙卷风必须要有一个风力
		if (eventId == 1) {
			while (wind == 0) {
				createWind();
			}
		}

	}

	public void createWind() {
		int num = ServiceUtils.getRandomNum(1, 101) - ServiceUtils.getRandomNum(1, 101);
		if (num == 0) {
			wind = 0;
			return;
		}
		int fh = Math.abs(num) / num;
		if (num < 13) {
			wind = 0;
		} else if (num < 38) {
			wind = 1 * fh;
		} else if (num < 60) {
			wind = 2 * fh;
		} else if (num < 79) {
			wind = 3 * fh;
		} else if (num < 90) {
			wind = 4 * fh;
		} else if (num < 97) {
			wind = 5 * fh;
		} else {
			wind = 6 * fh;
		}

	}

	/**
	 * 地图特殊事件ID
	 * 
	 * @return
	 */
	public int getEventId() {
		return eventId;
	}

}
