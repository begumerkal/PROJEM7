package com.wyd.empire.world.battle;

import java.util.ArrayList;
import java.util.List;

import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 战斗成员
 * 
 * @author Administrator
 */
public class Combat {
	/** 角色对象 */
	private WorldPlayer player;
	/** 怪物对象 */
	private GuaiPlayer guai;
	/** 用户所属阵营 */
	private int camp;
	/** 是否机器人，默认为否 */
	private boolean robot = false;
	/** 当前状态： 0表示等待加载，1表示正在加载，2表示加载完成，3表示正在战斗 */
	private int state = 0;
	/** 玩家当前所在X坐标 */
	private float x = 0f;
	/** 玩家当前所在Y坐标 */
	private float y = 0f;
	/** 玩家当血量 */
	private int hp;
	/** 玩家体力值 */
	private float pf;
	/** 玩家疲劳值，此值决定行动的次序 */
	private int tiredValue;
	/** 玩家怒气值 */
	private int angryValue;
	/** 本轮操作是否完成，默认为否 */
	private boolean curRound = false;
	/** 行动次数，默认为0 */
	private int actionTimes = 0;
	/** 攻击次数，默认为0 */
	private int shootTimes = 0;
	/** 命中次数，默认为0 */
	private int huntTimes = 0;
	/** dps（伤害输出），默认为0 */
	private int totalHurt = 0;
	/** 击退的玩家次数，默认为0 */
	private int killCount = 0;
	/** 被击退的次数，默认为0 */
	private int beKilledCount = 0;
	/** 被杀回合数，默认为1 */
	private int beKillRound = 1;
	/** 玩家复活次数，默认为0 */
	private int fhTimes = 0;
	/** 玩家是否掉线，默认为否 */
	private boolean lost = false;
	/** 玩家获得的经验 */
	private int exp;
	/** 玩家获得的任务奖励 */
	private RewardItemsVo riv;
	/** 玩家是否掉胜利，默认为否 */
	private boolean win = false;
	/** 是否已经计算命中次数，默认为否 */
	private boolean hit = false;
	/** 玩家是否死亡，默认为否 */
	private boolean dead = false;
	/** 玩家是否自杀 */
	private boolean suicide = false;
	/** ai技能方案 */
	private int aiCtrlId;
	/** 玩家副本获得的任务奖励 */
	private List<RewardItemsVo> rivList;
	/** 玩家是否强退，默认为否 */
	private boolean enforceQuit = false;
	/** 是不是由怪的数据生成的角色 */
	private boolean guaiPlayer = false;
	/** 怪的id */
	private int guaiId;
	/** 变机器人后实际获得的经验 */
	private int cexp;
	/** 冰冻剩余回合数 -1表示等待解除冰冻 */
	private int frozenTimes = 0;
	/** 玩家挑战赛获得积分 */
	private int integral = 0;
	// 合并CombatGuai对象------------------------------------------------------------
	public static final int GUAI_TYPE_LIKE_PLAYER = 0; // 跟玩家类似的怪
	public static final int GUAI_TYPE_MONSTER = 1; // 小怪
	public static final int GUAI_TYPE_BOSS = 2; // BOSS
	public static final int GUAI_ATTACK_TYPE_MELEE_RANGED = 0; // 会远近攻的怪
	public static final int GUAI_ATTACK_TYPE_RANGED = 1; // 会远攻
	public static final int GUAI_ATTACK_TYPE_MELEE = 2; // 只会近攻
	public static final int GUAI_ATTACK_TYPE_NONE = 3; // 不会攻击不会移动
	private int battleId; // 系统生成的Id
	private boolean couldBuildGuai = false;// 是否可以招怪
	private ArrayList<Integer> buildGuaiList = null; // 可以生成怪的列表
	private int changeToGuaiId = -1; // 可以变身的id
	// 防外挂验证---------------------------------------------------------------------
	private int playerAttackTimes = 1; // 玩家本回合可攻击次数
	private int skillAttackTimes = 6; // 玩家技能本回合可攻击次数
	private int continued = 1; // 连射数
	private int scatter = 1; // 散射数
	private int petAttackTimes = 1; // 宠物本回合可攻击次数
	private float hurtRate = 1f; // 本回合伤害倍率
	private boolean hide = false; // 是否隐身
	private int hideRound = 0; // 隐身 剩余回合数
	private int invincible = 0; // 是否无敌状态回合数 (在副本6和世界BOSS中会用到)
	private int CABId = 0; // 持续扣血 buff ID
	private int CABPlayer = 0; // 持续扣血 buff 触发玩家ID
	private int CABRound = 0; // 持续扣血 buff 剩余回合数
	private boolean worldBossFrozen = false; // 世界BOSS是否冰冻
	private int addAttackValue = 0; // 本回合攻击附加伤害
	private int crit = 0; // 本回合附加暴击
	private int hurtToBloodRate = 0; // 本回合吸血比率
	private WeapSkill weapSkill = null; // 本回合触发的持续伤害BUFF
	private int willFrozen = 0; // 本回合是否使用冰冻弹0表示不会其，他表示冰冻的回合数
	private int playerAttack = 0; // 玩家的攻击力
	private int playerMaxHP = 0; // 玩家的血量
	private boolean isNuke = false; // 玩家本回合是否触发核弹
	private int addAttackTimes = 0; // 本回合额外增加的攻击次数(地图事件中用)
	private int mapEventHurt = 0; // 地图事件伤害
	private int mapEventRound = 0; // 地图事件伤害回合数
	private boolean backToRoom = true; // 游戏结束后是否返回房间

	public WorldPlayer getPlayer() {
		return player;
	}

	public void setPlayer(WorldPlayer player) {
		this.player = player;
		this.playerAttack = player.getAttack();
		this.playerMaxHP = player.getMaxHP();
	}

	public GuaiPlayer getGuai() {
		return guai;
	}

	public void setGuai(GuaiPlayer guai) {
		this.guai = guai;
	}

	public int getCamp() {
		return camp;
	}

	public int getAddAttackTimes() {
		return addAttackTimes;
	}

	public void setAddAttackTimes(int addAttackTimes) {
		this.addAttackTimes = addAttackTimes;
		this.playerAttackTimes += 1;
	}

	public int getMapEventHurt() {
		return mapEventHurt;
	}

	public void setMapEvent(int mapEventHurt, int mapEventRound) {
		this.mapEventHurt = mapEventHurt;
		this.mapEventRound = mapEventRound;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public boolean isRobot() {
		return robot;
	}

	public void setRobot(boolean robot) {
		this.robot = robot;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public float getPf() {
		return pf;
	}

	public void setPf(float pf, int mapId, int battleMode, WorldPlayer currentPlayer) {
		this.pf = pf;
		if (this.pf < 0 && !this.isRobot() && !this.isLost() && !this.isGuaiPlayer() && null != this.player) {
			killLine(currentPlayer.getId());
			GameLogService.battleCheat(currentPlayer.getId(), currentPlayer.getLevel(), mapId, battleMode, 0, 3, "");
		}
	}

	public int getTiredValue() {
		return tiredValue;
	}

	public void setTiredValue(int tiredValue) {
		this.tiredValue = tiredValue;
	}

	public int getAngryValue() {
		return angryValue;
	}

	public void setAngryValue(int angryValue) {
		this.angryValue = angryValue;
	}

	public boolean isCurRound() {
		return curRound;
	}

	public void setCurRound(boolean curRound) {
		this.curRound = curRound;
	}

	public int getActionTimes() {
		return actionTimes;
	}

	public void setActionTimes(int actionTimes) {
		this.actionTimes = actionTimes;
	}

	public int getShootTimes() {
		return shootTimes;
	}

	public void setShootTimes(int shootTimes) {
		this.shootTimes = shootTimes;
	}

	public int getHuntTimes() {
		return huntTimes;
	}

	public void setHuntTimes(int huntTimes) {
		this.huntTimes = huntTimes;
	}

	public int getTotalHurt() {
		return totalHurt;
	}

	public void setTotalHurt(int totalHurt) {
		this.totalHurt = totalHurt;
	}

	public int getKillCount() {
		return killCount;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public int getBeKilledCount() {
		return beKilledCount;
	}

	public void setBeKilledCount(int beKilledCount) {
		this.beKilledCount = beKilledCount;
	}

	public int getBeKillRound() {
		return beKillRound;
	}

	public void setBeKillRound(int beKillRound) {
		this.beKillRound = beKillRound;
	}

	public int getFhTimes() {
		return fhTimes;
	}

	public void setFhTimes(int fhTimes) {
		this.fhTimes = fhTimes;
	}

	public boolean isLost() {
		return lost;
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public RewardItemsVo getRiv() {
		return riv;
	}

	public void setRiv(RewardItemsVo riv) {
		this.riv = riv;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isSuicide() {
		return suicide;
	}

	public void setSuicide(boolean suicide) {
		this.suicide = suicide;
	}

	public int getAiCtrlId() {
		return aiCtrlId;
	}

	public void setAiCtrlId(int aiCtrlId) {
		this.aiCtrlId = aiCtrlId;
	}

	public List<RewardItemsVo> getRivList() {
		return rivList;
	}

	public void setRivList(List<RewardItemsVo> rivList) {
		this.rivList = rivList;
	}

	public boolean isEnforceQuit() {
		return enforceQuit;
	}

	public void setEnforceQuit(boolean enforceQuit) {
		this.enforceQuit = enforceQuit;
	}

	public boolean isGuaiPlayer() {
		return guaiPlayer;
	}

	public void setGuaiPlayer(boolean guaiPlayer) {
		this.guaiPlayer = guaiPlayer;
	}

	public int getGuaiId() {
		return guaiId;
	}

	public void setGuaiId(int guaiId) {
		this.guaiId = guaiId;
	}

	public int getCexp() {
		return cexp;
	}

	public void setCexp(int cexp) {
		this.cexp = cexp;
	}

	public int getId() {
		if (guaiPlayer) {
			return guai.getGuai().getGuaiId();
		} else {
			return player.getId();
		}
	}

	public int getLevel() {
		if (guaiPlayer) {
			return guai.getGuai().getLevel();
		} else {
			return player.getLevel();
		}
	}

	public int getSex() {
		if (guaiPlayer) {
			return guai.getGuai().getSex();
		} else {
			return player.getPlayer().getSex();
		}
	}

	public int getBossmapRoomId() {
		if (guaiPlayer) {
			return 0;
		} else {
			return player.getBossmapRoomId();
		}
	}

	public String getName() {
		if (guaiPlayer) {
			return guai.getGuai().getName();
		} else {
			return player.getPlayer().getName();
		}
	}

	public String getSuit_head() {
		if (guaiPlayer) {
			return guai.getGuai().getSuit_head();
		} else {
			return player.getSuit_head();
		}
	}

	public String getSuit_face() {
		if (guaiPlayer) {
			return guai.getGuai().getSuit_face();
		} else {
			return player.getSuit_face();
		}
	}

	public String getSuit_body() {
		if (guaiPlayer) {
			return guai.getGuai().getSuit_body();
		} else {
			return player.getSuit_body();
		}
	}

	public String getSuit_weapon() {
		if (guaiPlayer) {
			return guai.getGuai().getSuit_weapon();
		} else {
			return player.getSuit_weapon();
		}
	}

	public int getWeapon_type() {
		if (guaiPlayer) {
			return guai.getGuai().getWeapon_type();
		} else {
			return player.getWeapon_type();
		}
	}

	public String getSuit_wing() {
		if (guaiPlayer) {
			return "null";
		} else {
			return player.getSuit_wing();
		}
	}

	public String getTitle() {
		if (guaiPlayer) {
			return "";
		} else {
			return player.getPlayerTitle();
		}
	}

	public String getGuildName() {
		if (guaiPlayer) {
			return "";
		} else {
			return player.getGuildName();
		}
	}

	public int getMaxHP() {
		if (guaiPlayer) {
			return guai.getMaxHP();
		} else {
			return this.playerMaxHP;
		}
	}

	public int getMaxPF() {
		if (guaiPlayer) {
			return guai.getMaxPF();
		} else {
			return player.getMaxPF();
		}
	}

	public int getMaxSP() {
		if (guaiPlayer) {
			return guai.getMaxSP();
		} else {
			return player.getMaxSP();
		}
	}

	public int getAttack(int mapId) {
		if (guaiPlayer) {
			return guai.getAttack();
		} else {
			if (ServiceManager.getManager().getWorldBossService().isWorldBossBattle(mapId)) {
				return playerAttack + 3000;// 玩家增加3000攻击(详情问策划)
			}
			return playerAttack;
		}
	}

	public int getBigSkillAttack() {
		if (guaiPlayer) {
			return guai.getAttack() * 2;
		} else {
			return player.getBigSkillAttack();
		}
	}

	public int getDefend() {
		if (guaiPlayer) {
			return guai.getDefend();
		} else {
			return player.getDefend();
		}
	}

	public int getCrit() {
		if (guaiPlayer) {
			return guai.getCrit();
		} else {
			return player.getCrit();
		}
	}

	public int getBigSkillType() {
		if (guaiPlayer) {
			return guai.getGuai().getBigSkillType();
		} else {
			return player.getBigSkillType();
		}
	}

	public int getExplodeRadius() {
		if (guaiPlayer) {
			return guai.getExplodeRadius();
		} else {
			return player.getExplodeRadius();
		}
	}

	public int[] getItemJnused() {
		if (guaiPlayer) {
			return new int[4];
		} else {
			return player.getItemJnused();
		}
	}

	public int[] getItemDjused() {
		if (guaiPlayer) {
			return new int[4];
		} else {
			return player.getItemDjused();
		}
	}

	public int getInjuryFree() {
		if (guaiPlayer) {
			return guai.getInjuryFree();
		} else {
			return player.getInjuryFree();
		}
	}

	public int getWreckDefense() {
		if (guaiPlayer) {
			return guai.getWreckDefense();
		} else {
			return player.getWreckDefense();
		}
	}

	// 免爆
	public int getReduceCrit() {
		if (guaiPlayer) {
			return guai.getReduceCrit();
		} else {
			return player.getReduceCrit();
		}
	}

	public int getReduceBury() {
		if (guaiPlayer) {
			return guai.getReduceBury();
		} else {
			return player.getReduceBury();
		}
	}

	public int getAgility() {
		if (guaiPlayer) {
			return guai.getAgility();
		} else {
			return player.getAgility();
		}
	}

	public int getProficiency() {
		if (guaiPlayer) {
			return guai.getProficiency();
		} else {
			return player.getProficiency();
		}
	}

	public int getLuck() {
		if (guaiPlayer) {
			return guai.getLuck();
		} else {
			return player.getLuck();
		}
	}

	public int getFrozenTimes() {
		return frozenTimes;
	}

	public void setFrozenTimes(int frozenTimes) {
		this.frozenTimes = frozenTimes;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	// 合并CombatGuai对象------------------------------------------------------------
	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public boolean isCouldBuildGuai() {
		return couldBuildGuai;
	}

	public void setCouldBuildGuai(boolean couldBuildGuai) {
		this.couldBuildGuai = couldBuildGuai;
	}

	public ArrayList<Integer> getBuildGuaiList() {
		return buildGuaiList;
	}

	public void setBuildGuaiList(ArrayList<Integer> buildGuaiList) {
		this.buildGuaiList = buildGuaiList;
	}

	public int getChangeToGuaiId() {
		return changeToGuaiId;
	}

	public void setChangeToGuaiId(int changeToGuaiId) {
		this.changeToGuaiId = changeToGuaiId;
	}

	// 防外挂验证---------------------------------------------------------------------
	public void setPlayerAttackTimes(int playerAttackTimes) {
		this.playerAttackTimes = playerAttackTimes + this.addAttackTimes;
	}

	public void setSkillAttackTimes(int skillAttackTimes) {
		this.skillAttackTimes = skillAttackTimes;
	}

	public int getContinued() {
		return continued;
	}

	public void setContinued(int continued) {
		this.continued = continued;
	}

	public int getScatter() {
		return scatter;
	}

	public void setScatter(int scatter) {
		this.scatter = scatter;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide, int hideRound) {
		this.hide = hide;
		this.hideRound = hideRound;
		// System.out.println("SetHide Hide:"+hide+"---HideRound:"+hideRound);
	}

	public boolean isInvincible() {
		return invincible > 0;
	}

	public void setInvincible(int invincible) {
		this.invincible = invincible;
	}

	public boolean isWorldBossFrozen() {
		return worldBossFrozen;
	}

	public void setWorldBossFrozen(boolean worldBossFrozen) {
		this.worldBossFrozen = worldBossFrozen;
	}

	public void setWillFrozen(int willFrozen) {
		this.willFrozen = willFrozen;
	}

	public int getWillFrozen() {
		return willFrozen;
	}

	public void setPlayerAttack(int playerAttack) {
		this.playerAttack = playerAttack;
	}

	public void setPlayerMaxHP(int playerMaxHP) {
		this.playerMaxHP = playerMaxHP;
	}

	public boolean isBackToRoom() {
		return backToRoom;
	}

	public void setBackToRoom(boolean backToRoom) {
		this.backToRoom = backToRoom;
	}

	public void initPlayerBattleInfo() {
		this.tiredValue = 0;// 初始化疲劳值
		this.hit = false;
		this.pf = this.player.getMaxPF();// 初始化体力值
		// 防外挂验证---------------------------------------------------------------------------------------
		this.playerAttackTimes = 1; // 玩家本回合可攻击次数
		this.skillAttackTimes = 6; // 玩家技能本回合可攻击次数
		this.continued = 1; // 连射数
		this.scatter = 1; // 散射数
		this.petAttackTimes = 1; // 宠物本回合可攻击次数
		this.hurtRate = 1f; // 本回合伤害倍率
		if (this.invincible > 0)
			this.invincible--;// 是无敌状态回合数
		this.worldBossFrozen = false;// 世界BOSS是否被冰冻
		this.addAttackValue = 0; // 本回合攻击附加伤害
		this.crit = 0; // 本回合附加暴击率
		this.hurtToBloodRate = 0; // 本回合吸血比率
		this.weapSkill = null;// 本回合触发的持续伤害BUFF
		this.willFrozen = 0; // 本回合是否使用冰冻弹
		this.isNuke = false; // 本回合是否使触发核弹
		this.addAttackTimes = 0;// 本回合额外增加攻击次数
		if (mapEventRound > 0) {
			mapEventRound--;
			if (mapEventRound < 0) {
				this.mapEventHurt = 0; // 地图事件伤害
			}
		}
		if (this.CABId > 0 && frozenTimes == 0) {
			this.CABRound--;
			if (this.CABRound < 0) {
				resetCAB();
			}
		}
		if (this.hide && frozenTimes == 0) {
			this.hideRound--;
			if (this.hideRound <= 0) {// 隐身结束
				setHide(false, 0);
			}
		}
	}

	/**
	 * 重置玩家持续扣血 buff信息
	 */
	public void resetCAB() {
		this.CABId = 0; // 持续扣血 buff ID
		this.CABPlayer = 0; // 持续扣血 buff 触发玩家ID
		this.CABRound = 0; // 持续扣血 buff 剩余回合数
	}

	/**
	 * 初始化玩家持续扣血 buff信息
	 * 
	 * @param buffId
	 * @param playerId
	 */
	public void initCAB(WeapSkill weapSkill, int playerId) {
		if (null != weapSkill) {
			this.CABId = weapSkill.getId();
			this.CABPlayer = playerId;
			this.CABRound = weapSkill.getParam2();
			// System.out.println("触发 CABRound:" + CABRound + "--------CABId:" +
			// CABId + "------hurtplayer:" + getId() + "------shootplayer:" +
			// playerId);
		}
	}

	/**
	 * 获取玩家持续伤害值
	 * 
	 * @return
	 */
	public int getCABHurt(int playerId) {
		int maxHurt = 0;
		// System.out.println("验证 CABRound:" + CABRound + "--------CABId:" +
		// CABId + "------hurtplayer:" + getId() + "------shootplayer:" +
		// playerId);
		if (CABId > 0 && CABRound >= 0 && CABPlayer == playerId) {
			WeapSkill weapSkill = ServiceManager.getManager().getStrengthenService().getWeapSkillById(CABId);
			if (null != weapSkill) {
				maxHurt = weapSkill.getParam1();
			}
		}
		return maxHurt;
	}

	/**
	 * 检查玩家的射击次数
	 * 
	 * @param attackType
	 * @param mapId
	 *            地图ID
	 * @param battleMode
	 *            战斗模式
	 * @param currentPlayer
	 *            操作玩家
	 * @return true 则踢下线
	 */
	public boolean checkShoot(int attackType, int mapId, int battleMode, WorldPlayer currentPlayer) {
		boolean kill = true;
		if (isGuaiPlayer())
			return false;
		if (0 == attackType) {
			playerAttackTimes--;
		} else if (-2 == attackType) {
			petAttackTimes--;
		} else {
			skillAttackTimes--;
		}
		if (!this.isRobot() && !this.isLost() && !this.guaiPlayer && null != this.player) {
			if (playerAttackTimes >= 0 && petAttackTimes >= 0 && skillAttackTimes >= 0) {
				kill = false;
			}
		} else {
			kill = false;
		}
		// 当玩家或玩家宠物攻击次数超出范围则踢下线
		if (kill) {
			System.out.println("攻击次数不一致" + playerAttackTimes);
			killLine(currentPlayer.getId());
			GameLogService.battleCheat(currentPlayer.getId(), currentPlayer.getLevel(), mapId, battleMode, attackType, 1, "");
		}
		return kill;
	}

	public float getHurtRate() {
		return hurtRate;
	}

	public void setHurtRate(float hurtRate) {
		this.hurtRate = hurtRate;
	}

	/**
	 * 验证玩家伤害
	 * 
	 * @param hurt
	 *            客户端计算的伤害值
	 * @param attackType
	 *            攻击类型（-2:宠物攻击，-1:加血，0:普通伤害，其他:技能伤害的Id,GuaiPlayer中该参数为使用技能下标+1）
	 * @param targetHero
	 *            被攻击者
	 * @param distance
	 *            爆炸距离(isPVP为false时该值为BOSS技能伤害数值下标)
	 * @param hurtFloat
	 *            伤害浮动空间 0~8（百分比）
	 * @param hurtToBloodRate
	 *            受伤多少伤害转换为血量的比率(放大1万倍)
	 * @param attackerRandom
	 *            攻击者武器技能触发使用随机数下标(isPVP为false时该值为BOSS伤害数组)
	 * @param targetRandom
	 *            被攻击目标武器技能触发使用随机数下标(isPVP为false时该值为BOSS技能伤害数组)
	 * @param battleRand
	 *            游戏随机数
	 * @param isPVP
	 *            是否PVP对战
	 * @param mapId
	 *            地图ID
	 * @param battleMode
	 *            战斗模式
	 * @param currentPlayer
	 *            操作玩家
	 * @return
	 */
	public boolean verifyHurt(int hurt, int attackType, Combat targetHero, int distance, int hurtFloat, int hurtToBloodRate,
			int[] attackerRandom, int[] targetRandom, int[] battleRand, boolean isPVP, int mapId, int battleMode, WorldPlayer currentPlayer) {
		boolean kill = true;
		int maxHurt = 0;
		if (!this.guaiPlayer && null != this.player) {// 玩家攻击
			if (0 == attackType) {// 普通攻击

				maxHurt = (int) calculateHurt(targetHero, distance, hurtFloat, hurtToBloodRate, attackerRandom, targetRandom, battleRand,
						isPVP, hurt, mapId);
				// System.out.println("attackType:" + attackType + "---maxHurt:"
				// + maxHurt + "---hurt:" + hurt + "---hurtplayer:" +
				// targetHero.getId() + "---shootplayer:" + getId());
				if (hurt == maxHurt || (targetHero.isGuaiPlayer() && hurt < maxHurt)) {
					kill = false;
				}
			} else if (-2 == attackType) {// 宠物攻击
				PlayerPet playerPet = player.getPlayerPet();
				if (targetHero.isInvincible()) {
					maxHurt = 1;
				} else {
					maxHurt = (int) (getAttack(mapId) * (playerPet.getSkill().getParam1() * 0.0001));
				}
				if (hurt == maxHurt || (targetHero.isGuaiPlayer() && hurt < maxHurt)) {
					kill = false;
				}
			} else if (-3 == attackType) {// 地图事件:陨石攻击
				maxHurt = mapEventHurt;
				if (hurt == maxHurt) {
					kill = false;
				}
			} else if (0 < attackType) {// 技能攻击
				if (targetHero.isInvincible()) {
					maxHurt = 1;
				} else {
					maxHurt = targetHero.getCABHurt(getId());
				}
				// System.out.println("attackType:" + attackType + "---maxHurt:"
				// + maxHurt + "---hurt:" + hurt + "---hurtplayer:" +
				// targetHero.getId() + "---shootplayer:" + getId());
				if (hurt == maxHurt || (targetHero.isGuaiPlayer() && hurt < maxHurt)) {
					kill = false;
				}
			}
		} else if (this.guaiPlayer) {// 怪物攻击
			if (0 == attackType) {// 普通攻击
				maxHurt = (int) calculateHurt(targetHero, distance, hurtFloat, hurtToBloodRate, attackerRandom, targetRandom, battleRand,
						isPVP, hurt, mapId);
				// System.out.println("attackType:" + attackType + "---maxHurt:"
				// + maxHurt + "---hurt:" + hurt + "---hurtplayer:" +
				// targetHero.getId() + "---shootplayer:" + getId());
				if (hurt >= maxHurt) {
					kill = false;
				}
			} else {// 伤害技能类型
				maxHurt = calculateBossSkillHurt(targetHero, targetRandom, attackType, distance, attackerRandom);
				// System.out.println("attackType:" + attackType + "---maxHurt:"
				// + maxHurt + "---hurt:" + hurt + "---hurtplayer:" +
				// targetHero.getId() + "---shootplayer:" + getId());
				if (hurt >= maxHurt) {
					kill = false;
				}
			}
		}
		if (kill) {
			killLine(currentPlayer.getId());
			System.out.println("attackType " + attackType + " this.guaiPlayer " + this.guaiPlayer + " this.player " + this.player);
			GameLogService.battleCheat(currentPlayer.getId(), currentPlayer.getLevel(), mapId, battleMode, attackType, 2, hurt + "*"
					+ maxHurt);
		}
		return kill;
	}

	/**
	 * 踢玩家下线
	 */
	public void killLine(int currentPlayerId) {
		System.out.println("作弊踢下线:" + currentPlayerId);
		ServiceManager.getManager().getPlayerService().killLine(currentPlayerId);
	}

	/**
	 * 计算玩家攻击伤害
	 * 
	 * @param targetHero
	 *            被伤害玩家
	 * @param distance
	 *            爆炸距离
	 * @param hurtFloat
	 *            伤害浮动空间 0~8（百分比）
	 * @param hurtToBloodRate
	 *            受伤多少伤害转换为血量的比率(放大1万倍)
	 * @param attackerRandom
	 *            攻击者武器技能触发使用随机数下标
	 * @param targetRandom
	 *            被攻击目标武器技能触发使用随机数下标
	 * @param battleRand
	 *            游戏随机数
	 * @param isPVP
	 *            是否PVP战斗
	 * @param mapId
	 *            地图ID
	 * @return
	 */
	private double calculateHurt(Combat targetHero, int distance, int hurtFloat, int hurtToBloodRate, int[] attackerRandom,
			int[] targetRandom, int[] battleRand, boolean isPVP, int realHurt, int mapId) {
		targetHero.initCAB(this.weapSkill, getId());
		if (willFrozen > 0) {
			targetHero.setFrozenTimes(willFrozen);
			return 0;
		}
		if (targetHero.checkTargetSkill(targetRandom, battleRand, isPVP)) {
			return 0;
		}
		if (hurtToBloodRate != this.hurtToBloodRate) {
			// 触发吸血数据不正确则返回伤害为0，使之踢下线
			return 0;
		}
		boolean wudi = targetHero.isInvincible();
		if (wudi) {
			return 1;
		}
		double hurt = 0;
		// 攻击方攻击
		double attack0 = getAttack(mapId) * 1.0;
		// 攻击方力量
		double force0 = getPlayer() == null ? 0d : getPlayer().getForce() * 1.0;
		// 攻击方体质
		double physique0 = getPlayer() == null ? 0d : getPlayer().getPhysique() * 1.0;
		// 攻击方体敏捷
		double agility0 = getPlayer() == null ? 0d : getPlayer().getAgility() * 1.0;
		// 攻击方等级
		int level0 = getLevel();

		// 攻击方破防
		double wreckDefense0 = getWreckDefense() * 0.0001;
		// 被攻击方护甲
		double armor1 = targetHero.getPlayer() == null ? 0d : targetHero.getPlayer().getArmor() * 1.0;
		// 被攻击方体质
		double physique1 = targetHero.getPlayer() == null ? 0d : targetHero.getPlayer().getPhysique() * 1.0;
		// 被攻击方幸运
		double luck1 = targetHero.getPlayer() == null ? 0d : targetHero.getPlayer().getLuck() * 1.0;
		// 被攻击方防御
		double defend1 = targetHero.getPlayer() == null ? 0d : targetHero.getPlayer().getDefend() * 1.0;
		// 被攻击方等级
		double level1 = targetHero.getPlayer() == null ? 0d : targetHero.getPlayer().getLevel() * 1.0;
		// 被攻击方免伤
		double injuryFree1 = targetHero.getPlayer() == null ? 0d : targetHero.getPlayer().getInjuryFree() * 0.0001;

		// 弹弹岛新伤害公式=攻击方攻击*（0.75+0.5*（攻击方力量+500）/(攻击方力量+被攻击方护甲+1000））*（0.5+1.0*（攻击方体质+500）/(攻击方体质+被攻击方体质+1000））*（0.75+
		// 0.5*（攻击方体敏捷+500）/(攻击方敏捷+被攻击方幸运+1000））*（1-（被攻击方防御*（1-攻击方破防/(攻击方破防+500)）/（2*被攻击方防御*（1-攻击方破防/(攻击方破防+500)）+1200））*（1+（攻击方等级-被攻击方等级）/100）*(1-0.3*被攻击方免伤/(被攻击方免伤+1000））*(96%~104%)*（爆炸范围百分比伤害））
		hurt = attack0 * (0.75 + 0.5 * (force0 + 500) / (force0 + armor1 + 1000))
				* (0.5 + 1.0 * (physique0 + 500) / (physique0 + physique1 + 1000));
		double hurt1 = (0.75 + 0.5 * (agility0 + 500) / (agility0 + luck1 + 1000));
		double hurt2 = (1 - (defend1 * (1 - wreckDefense0 / (wreckDefense0 + 500)) / (2 * defend1
				* (1 - wreckDefense0 / (wreckDefense0 + 500)) + 1200))
				* (1 + (level0 - level1) / 100) * (1 - 0.3 * injuryFree1 / (injuryFree1 + 1000)));
		hurt = hurt * hurt1 * hurt2;
		boolean iscrit = isCrit(targetHero, battleRand);
		// 伤害值在96%~104%之间浮动
		hurt = hurt * (hurtFloat + 96) * 0.01;

		// 技能损耗或加成
		hurt = hurt * this.hurtRate;
		if (isPVP) {// pve对战伤害100%
			int explodeRadius = isNuke ? 100 : this.getExplodeRadius();
			if (distance >= (int) (explodeRadius * 0.6)) {
				hurt *= 0.3;
			} else if (distance >= (int) (explodeRadius * 0.3)) {
				hurt *= 0.7;
			}
		}
		if (targetHero.isHide()) {
			targetHero.setHide(false, 0);
			hurt *= 0.5;
		}
		hurt = hurt + this.addAttackValue;
		if (realHurt != (int) hurt) {
			System.out.println("伤害验证不一致 realHurt:" + realHurt + "   maxhurt:" + (int) hurt + "    attack:" + attack0 + "   wreckDefense:"
					+ wreckDefense0 + "   targetDefend:" + defend1 + " injuryFree1:" + injuryFree1 + "   iscrit:" + iscrit
					+ "   hurtFloat:" + hurtFloat + " hurtRate:" + hurtRate + "   distance:" + distance + "   ExplodeRadius:"
					+ this.getExplodeRadius() + "   targetHide:" + targetHero.isHide() + "  addAttackValue:" + addAttackValue);
		}
		return hurt;
	}

	/**
	 * 检查攻击者单个炸弹触发的武器技能
	 * 
	 * @param attackerRandom
	 *            攻击者武器技能触发使用随机数下标
	 * @param battleRand
	 *            游戏随机数
	 * @return 返回 {0:战斗伤害加成数值, 1:攻击力加成后数值, 2:被动技能暴击概率, 3:吸血比率}
	 */
	public void checkAttackerSkill(int[] attackerRandom, int[] battleRand, boolean isPVP) {
		if (isPVP) {
			PlayerItemsFromShop pifs = this.player.getWeapon();
			boolean trigger = false;
			if (pifs.getWeapSkill1() > 0) {
				WeapSkill weapSkill = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
				if (null != weapSkill && attackerRandom[0] > -1 && attackerRandom[0] < battleRand.length
						&& battleRand[attackerRandom[0]] < weapSkill.getUseChance().intValue()) {
					trigger = true;
					switch (weapSkill.getType().intValue()) {
						case 1 :// 攻击
							setHurtRate(getHurtRate() * (1f + weapSkill.getParam1() / 10000f));
							break;
						case 2 :// 伤害
							this.addAttackValue = weapSkill.getParam1();
							break;
						case 3 :// 暴击
							this.crit = weapSkill.getParam1();
							break;
						case 7 :// 吸血
							this.hurtToBloodRate = weapSkill.getParam1();
							break;
						case 9 :// 连击
							setContinued(getContinued() + weapSkill.getParam2());
							setPlayerAttackTimes(getContinued() * getScatter());
							setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
							break;
						case 11 :// 核弹
							isNuke = true;
						case 10 :// 引导
							setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
							break;
						case 4 :// 灼伤
						case 12 :// 毒素
						case 13 :// 寒冰
							this.weapSkill = weapSkill;
							break;
					}
				}
			}
			if (!trigger && pifs.getWeapSkill2() > 0) {
				WeapSkill weapSkill = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
				if (null != weapSkill && attackerRandom[1] > -1 && attackerRandom[1] < battleRand.length
						&& battleRand[attackerRandom[1]] < weapSkill.getUseChance().intValue()) {
					switch (weapSkill.getType().intValue()) {
						case 1 :// 攻击
							setHurtRate(getHurtRate() * (1f + weapSkill.getParam1() / 10000f));
							break;
						case 2 :// 伤害
							this.addAttackValue = weapSkill.getParam1();
							break;
						case 3 :// 暴击
							this.crit = weapSkill.getParam1();
							break;
						case 7 :// 吸血
							this.hurtToBloodRate = weapSkill.getParam1();
							break;
						case 9 :// 连击
							setContinued(getContinued() + weapSkill.getParam2());
							setPlayerAttackTimes(getContinued() * getScatter());
							setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
							break;
						case 11 :// 核弹
							isNuke = true;
						case 10 :// 引导
							setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
							break;
						case 4 :// 灼伤
						case 12 :// 毒素
						case 13 :// 寒冰
							this.weapSkill = weapSkill;
							break;
					}
				}
			}
		}
	}

	/**
	 * 检查被攻击者触发的武器技能
	 * 
	 * @param targetRandom
	 *            被攻击目标武器技能触发使用随机数下标
	 * @param battleRand
	 *            游戏随机数
	 * @return 返回被攻击者是否免伤
	 */
	private boolean checkTargetSkill(int[] targetRandom, int[] battleRand, boolean isPVP) {
		boolean notHurt = false;
		if (isPVP) {
			PlayerItemsFromShop pifs = this.player.getWeapon();
			boolean trigger = false;
			if (pifs.getWeapSkill1() > 0) {
				WeapSkill weapSkill = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
				if (null != weapSkill && targetRandom[0] > -1 && targetRandom[0] < battleRand.length
						&& battleRand[targetRandom[0]] < weapSkill.getUseChance().intValue()) {
					trigger = true;
					switch (weapSkill.getType().intValue()) {
						case 19 :// 免疫
							resetCAB();
						case 18 :// 吸收
							notHurt = true;
							break;
					}
				}
			}
			if (!trigger && pifs.getWeapSkill2() > 0) {
				WeapSkill weapSkill = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
				if (null != weapSkill && targetRandom[1] > -1 && targetRandom[1] < battleRand.length
						&& battleRand[targetRandom[1]] < weapSkill.getUseChance().intValue()) {
					switch (weapSkill.getType().intValue()) {
						case 19 :// 免疫
							resetCAB();
						case 18 :// 吸收
							notHurt = true;
							break;
					}
				}
			}
		}
		return notHurt;
	}

	/**
	 * 计算玩家是否暴击
	 * 
	 * @param targetHero
	 *            被攻击者
	 * @param battleRand
	 *            游戏随机数
	 * @return
	 */
	private boolean isCrit(Combat targetHero, int[] battleRand) {
		double bj0 = getCrit() + this.crit * 1d;// 攻击方暴击
		double mb1 = targetHero.getReduceCrit() * 1d;// 防御方免爆
		double mj0 = getAgility();// 攻击方敏捷
		double xy1 = targetHero.getLuck();// 被攻击方幸运
		double sld = getProficiency();// 武器熟练度
		// 暴击率=((0.25+0.25*（攻击方暴击-防御方免爆)/(攻击方暴击+防御方免爆+1)）+攻击方敏捷/(攻击方敏捷+被攻击方幸运+1)*0.25)*(0.5+熟练度/20000)
		int critRate = (int) ((((0.25 + 0.25 * (bj0 - mb1) / (bj0 + mb1 + 1)) + mj0 / (mj0 + xy1 + 1) * 0.25) * (0.5 + sld / 20000)) * 10000);
		int randNumber = battleRand[0] + 1;
		System.out.println("暴击率:" + critRate + " randNumber:" + randNumber + " 攻击方暴击:" + bj0 + " 防御方免爆:" + mb1 + " 攻击方敏捷:" + mj0
				+ " 被攻击方幸运:" + xy1 + " 武器熟练度:" + sld);
		return critRate >= randNumber;
	}

	/**
	 * 验证世界BOSS被冰冻状态是否被修改
	 * 
	 * @param bossBeFrozen
	 *            是否冰冻
	 * @param mapId
	 *            地图ID
	 * @param battleMode
	 *            战斗模式
	 * @param currentPlayer
	 *            操作玩家
	 * @return
	 */
	public boolean verifyBossBeFrozen(boolean bossBeFrozen, int mapId, int battleMode, WorldPlayer currentPlayer) {
		boolean verify = false;
		if (worldBossFrozen != bossBeFrozen) {
			verify = true;
			killLine(currentPlayer.getId());
			GameLogService.battleCheat(currentPlayer.getId(), currentPlayer.getLevel(), mapId, battleMode, 0, 5, "");
		}
		if (bossBeFrozen == true) {
			worldBossFrozen = false;
		}
		return verify;
	}

	/**
	 * 计算BOSS技能伤害
	 * 
	 * @param targetHero
	 *            受伤对象
	 * @param skillHurt
	 *            BOSS技能伤害数组
	 * @param attackType
	 *            BOSS技能伤害类型
	 * @param attackIndex
	 *            BOSS技能伤害下标
	 * @param hurtvalue
	 *            BOSS技能伤害
	 * @return BOSS技能伤害
	 */
	public int calculateBossSkillHurt(Combat targetHero, int[] skillHurt, int attackType, int attackIndex, int[] hurtvalue) {
		boolean wudi = targetHero.isInvincible();
		if (wudi) {
			return 1;
		}
		int hurt = 0;
		if (null != skillHurt && attackIndex < skillHurt.length) {
			switch (attackType) {
				case 1 :// 普通技能攻击
					hurt = skillHurt[attackIndex];
					break;
				case 2 :// 平分伤害类型技能
					int hurtcount = 0;
					if (null != hurtvalue) {
						for (int hv : hurtvalue) {
							if (hv > 0)
								hurtcount++;
						}
					}
					if (hurtcount > 0)
						hurt = skillHurt[attackIndex] / hurtcount;
					break;
				case 3 :// 世界BOSS 3发 散射伤害
					hurt = skillHurt[attackIndex] / 3;
					break;
			}
		}
		return hurt;
	}
}
