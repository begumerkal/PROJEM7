package com.wyd.empire.world.battle;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.protocol.data.battle.PostionsForPlayers;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.TipMessages;

/**
 * 副本，世界Boss战斗组对象
 * 
 * @author Administrator
 */
public class BossBattleTeam {
	public static final int BEMODEJJ = 1;
	public static final int BEMODEFH = 2;
	public static final int BEMODEPW = 3;
	public static final int PEMODE1V1 = 1;
	public static final int PEMODE2V2 = 2;
	public static final int PEMODE3V3 = 3;
	private int battleId; // 战斗组id
	private int battleMode; // 战斗模式,1、小副本，2、大副本
	private int playerNumMode; // 对战人数模式,1=1v1，3=3v3
	private int togetherType; // 撮合类型（未用）
	private int fightWithAI; // 是否与AI对战(0否，1是)
	private long startTime; // 对战开始时间
	private int aicounter = 0; // 控制者id
	private Vector<Combat> combatList; // 战斗组里面的成员
	private Vector<Combat> trueCombatList; // 战斗组里面的真实玩家的数量
	private ConcurrentHashMap<Integer, Combat> combatMap; // 战斗组里面的成员
	// private List<WorldPlayer> pList;//玩家列表
	private Vector<Combat> combatGuaiList; // 战斗组里面的成员
	private ConcurrentHashMap<Integer, Combat> guaiMap; // 战斗组里面的成员
	private Vector<Integer> curRoundSequence; // 当前回合战斗序列
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
	private int guaiIdDistribution = -21; // 怪的id分配(一定是负的),预留20个用于特殊处理
											// private int bossMapExp=0;
											// private int mapId=-1;
											// private int mapProgress; //当前副本进度
	private int runTimes; // 副本回合数限制
	private int difficulty; // 副本难度(1=普通,2=困难,3=地狱)
	private BossRoom room; // 副本房间
	private int mapId; // 副本地图id
	private String useTools; // 战斗中使用的技能
	private int[] battleRand; // 游戏随机数
	private int[] skillHurt; // boss的技能伤害
	private boolean canInvincible = false;// 玩家本回合是否可以无敌
	private HashSet<Integer> buffSet; // 存放世界BOSS的触发BUFF
	private boolean over = false;// 本次战斗是否已结束

	public BossBattleTeam() {
		battleId = Math.abs(this.hashCode());
		combatList = new Vector<Combat>();
		trueCombatList = new Vector<Combat>();
		combatMap = new ConcurrentHashMap<Integer, Combat>();
		combatGuaiList = new Vector<Combat>();
		guaiMap = new ConcurrentHashMap<Integer, Combat>();
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

	public ConcurrentHashMap<Integer, Combat> getCombatMap() {
		return combatMap;
	}

	public void enterPlayer(Combat combat) {
		combatList.add(combat);
		combatMap.put(combat.getId(), combat);
	}

	public void enterGuai(Combat guaiVo) {
		guaiVo.setGuaiPlayer(true);
		combatGuaiList.add(guaiVo);
		guaiMap.put(guaiVo.getBattleId(), guaiVo);
	}

	public int getAicounter() {
		return aicounter;
	}

	public void setAicounter(int aicounter) {
		this.aicounter = aicounter;
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
	public CombatChara getNextActionChara() {
		int re = 0;
		Combat reCombat = null;
		Combat reCombatGuai = null;
		for (int i = actionIndex; i < curRoundSequence.size(); i++) {
			Integer curIndex = curRoundSequence.get(i);
			if (CombatChara.CHARA_TYPE_ENERMY_MELEE == curIndex.intValue())// 进攻的敌人
			{
				for (Combat combatGuai : combatGuaiList) {
					if (combatGuai.getCamp() == 1 && combatGuai.getGuai().getGuai().getType() == Combat.GUAI_ATTACK_TYPE_MELEE
							&& !combatGuai.isDead() && !combatGuai.isLost()) {
						re = 1;
						break;
					}
				}
				if (1 == re) {
					break;
				}
			} else if (CombatChara.CHARA_TYPE_MINE_MELEE == curIndex.intValue()) {// 进攻的自己人
				for (Combat combatGuai : combatGuaiList) {
					if (combatGuai.getCamp() == 0 && combatGuai.getGuai().getGuai().getType() == Combat.GUAI_ATTACK_TYPE_MELEE
							&& !combatGuai.isDead() && !combatGuai.isLost()) {
						re = 2;
						break;
					}
				}
				if (2 == re) {
					break;
				}
			} else {
				// 查一下是不是人
				for (Combat combat : combatList) {
					if (combat.getId() == curIndex.intValue() && !combat.isDead() && !combat.isLost()) {
						re = 3;
						reCombat = combat;
					}
				}
				if (3 == re)
					break;
				// 查一下是不是怪
				for (Combat guai : combatGuaiList) {
					if (guai.getBattleId() == curIndex.intValue() && !guai.isDead() && !guai.isLost()) {
						reCombatGuai = guai;
						re = 4;
					}
				}
				if (4 == re)
					break;
			}
			actionIndex++;
		}
		CombatChara chara;
		switch (re) {
			case 1 :
				chara = new CombatChara(CombatChara.CHARA_TYPE_ENERMY_MELEE, null, null);
				actionIndex++;
				break;
			case 2 :
				chara = new CombatChara(CombatChara.CHARA_TYPE_MINE_MELEE, null, null);
				actionIndex++;
				break;
			case 3 :
				chara = new CombatChara(CombatChara.CHARA_TYPE_PLAYER, reCombat, null);
				actionIndex++;
				break;
			case 4 :
				chara = new CombatChara(CombatChara.CHARA_TYPE_GUAI, null, reCombatGuai);
				actionIndex++;
				break;
			default :
				chara = null;
				break;
		}
		return chara;
	}

	public class CombatComparator implements Comparator<Combat> {
		public int compare(Combat combat1, Combat combat2) {
			return (combat1.getTiredValue() - (int) combat1.getPf()) - (combat2.getTiredValue() - (int) combat2.getPf());
		}
	}

	/**
	 * 返回角色攻击顺序
	 * 
	 * @param battleId
	 * @return
	 * @return
	 */
	public void sort() {
		Vector<Combat> combatList = getCombatList();
		Vector<Combat> gList = getCombatGuaiList();
		Vector<Integer> curRoundSequence = new Vector<Integer>();
		if (gList.size() > 0) {
			// boss怪先行动
			for (Combat g : gList) {
				if (g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_BOSS) {
					curRoundSequence.add(g.getBattleId());
				}
				// 顺便初始一个怪的值
				g.setTiredValue(0);// 初始化疲劳值
				g.setHit(false);
				g.setPf(g.getMaxPF(), getMapId(), 6, null);// 初始化体力值
			}
			// 敌营近攻行动
			for (Combat g : gList) {
				if (g.getCamp() == 1 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() == Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(CombatChara.CHARA_TYPE_ENERMY_MELEE);
					break;
				}
			}
			// 我营近攻行动
			for (Combat g : gList) {
				if (g.getCamp() == 0 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() == Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(CombatChara.CHARA_TYPE_MINE_MELEE);
					break;
				}
			}
			// 敌营其它攻击
			for (Combat g : gList) {
				if (g.getCamp() == 1 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_NONE
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(g.getBattleId());
				}
			}
			// 我营的其它攻击
			for (Combat g : gList) {
				if (g.getCamp() == 0 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_NONE
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(g.getBattleId());
				}
			}
		}
		int size = combatList.size();
		Comparator<Combat> ascComparator = new CombatComparator();
		Collections.sort(combatList, ascComparator);
		Combat combat;
		for (int i = 0; i < size; i++) {
			combat = combatList.get(i);
			combat.initPlayerBattleInfo();
			curRoundSequence.add(combat.getId());
		}
		// 重新开始
		actionIndex = 0;
		setCurRoundSequence(curRoundSequence);
	}

	/**
	 * 返回角色攻击顺序
	 * 
	 * @param battleId
	 * @return
	 * @return
	 */
	public void worldBossSort() {
		Vector<Combat> combatList = getCombatList();
		Vector<Combat> gList = getCombatGuaiList();
		Vector<Integer> curRoundSequence = new Vector<Integer>();
		int size = combatList.size();
		Comparator<Combat> ascComparator = new CombatComparator();
		Collections.sort(combatList, ascComparator);
		// 玩家先行动
		Combat combat;
		for (int i = 0; i < size; i++) {
			combat = combatList.get(i);
			combat.initPlayerBattleInfo();
			curRoundSequence.add(combat.getId());
		}
		if (gList.size() > 0) {
			for (Combat g : gList) {
				if (g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_BOSS) {
					curRoundSequence.add(g.getBattleId());
				}
				// 顺便初始一个怪的值
				g.setTiredValue(0);// 初始化疲劳值
				g.setHit(false);
				g.setPf(g.getMaxPF(), getMapId(), 6, null);// 初始化体力值
			}
			// 敌营近攻行动
			for (Combat g : gList) {
				if (g.getCamp() == 1 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() == Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(CombatChara.CHARA_TYPE_ENERMY_MELEE);
					break;
				}
			}
			// 我营近攻行动
			for (Combat g : gList) {
				if (g.getCamp() == 0 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() == Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(CombatChara.CHARA_TYPE_MINE_MELEE);
					break;
				}
			}
			// 敌营其它攻击
			for (Combat g : gList) {
				if (g.getCamp() == 1 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_NONE
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(g.getBattleId());
				}
			}
			// 我营的其它攻击
			for (Combat g : gList) {
				if (g.getCamp() == 0 && g.getGuai().getGuai().getType() == Combat.GUAI_TYPE_MONSTER
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_NONE
						&& g.getGuai().getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_MELEE) {
					curRoundSequence.add(g.getBattleId());
				}
			}
		}
		// 重新开始
		actionIndex = 0;
		setCurRoundSequence(curRoundSequence);
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

	// public List<WorldPlayer> getpList() {
	// return pList;
	// }
	// public void setpList(List<WorldPlayer> pList) {
	// this.pList = pList;
	// }
	public Vector<Combat> getCombatGuaiList() {
		return combatGuaiList;
	}

	public void setCombatGuaiList(Vector<Combat> gList) {
		this.combatGuaiList = gList;
	}

	public ConcurrentHashMap<Integer, Combat> getGuaiMap() {
		return guaiMap;
	}

	public void setGuaiMap(ConcurrentHashMap<Integer, Combat> guaiMap) {
		this.guaiMap = guaiMap;
	}

	public int getGuaiIdDistribution() {
		int re = guaiIdDistribution--;
		return re;
	}

	public void setGuaiIdDistribution(int guaiIdDistribution) {
		this.guaiIdDistribution = guaiIdDistribution;
	}

	public Vector<Integer> getCurRoundSequence() {
		return curRoundSequence;
	}

	public void setCurRoundSequence(Vector<Integer> curRoundSequence) {
		this.curRoundSequence = curRoundSequence;
	}

	public Vector<Combat> getTrueCombatList() {
		return trueCombatList;
	}

	public void setTrueCombatList(Vector<Combat> trueCombatList) {
		this.trueCombatList = trueCombatList;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	// 获取副本回合限制次数
	public int getRunTimes() {
		return this.runTimes;
	}

	public void setRunTimes(int runTimes) {
		this.runTimes = runTimes;
	}

	public String getDifficultyString() {
		if (1 == difficulty) {
			return TipMessages.BOSSMAPLEVEL1;
		} else if (2 == difficulty) {
			return TipMessages.BOSSMAPLEVEL2;
		} else {
			return TipMessages.BOSSMAPLEVEL3;
		}
	}

	public BossRoom getBossRoom() {
		return this.room;
	}

	public void setBossRoom(BossRoom room) {
		this.room = room;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
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

	public int[] getSkillHurt() {
		return skillHurt;
	}

	public void setSkillHurt(int[] skillHurt) {
		this.skillHurt = skillHurt;
	}

	public boolean isCanInvincible() {
		return canInvincible;
	}

	public void setCanInvincible(boolean canInvincible) {
		this.canInvincible = canInvincible;
	}

	public HashSet<Integer> getBuffSet() {
		return buffSet;
	}

	public void setBuffSet(HashSet<Integer> buffSet) {
		this.buffSet = buffSet;
	}

	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}
}
