package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.AIControlCommon;
import com.wyd.empire.protocol.data.bossmapbattle.ChangeAngryValue;
import com.wyd.empire.protocol.data.bossmapbattle.EndCurRound;
import com.wyd.empire.protocol.data.bossmapbattle.FinishLoading;
import com.wyd.empire.protocol.data.bossmapbattle.GameOver;
import com.wyd.empire.protocol.data.bossmapbattle.Pass;
import com.wyd.empire.protocol.data.bossmapbattle.PlayerLose;
import com.wyd.empire.protocol.data.bossmaproom.MakePairOk;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.CombatChara;
import com.wyd.empire.world.battle.PlayerRewardVo;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.bossmapbattle.EndCurRoundHandler;
import com.wyd.empire.world.server.handler.bossmapbattle.FinishLoadingHandler;
import com.wyd.empire.world.server.handler.bossmapbattle.PassHandler;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.worldbosshall.WorldBossRoom;

/**
 * 战斗组管理服务
 * 
 * @author Administrator
 */
public class BossBattleTeamService implements Runnable {
	private Logger log = Logger.getLogger(BossBattleTeamService.class);
	/** 自动断线时间 */
	public static final int OUT_TIME = 60000;
	/** 休眠时间 */
	private static final int SLEEP_TIME = 10000;
	private ConcurrentHashMap<Integer, BossBattleTeam> battleTeamMap;
	private List<BossBattleTeam> battleTeamList;
	private ConcurrentHashMap<Integer, PlayerRewardVo> playerReward;

	public BossBattleTeamService() {
		battleTeamMap = new ConcurrentHashMap<Integer, BossBattleTeam>();
		battleTeamList = new Vector<BossBattleTeam>();
		playerReward = new ConcurrentHashMap<Integer, PlayerRewardVo>();
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("BattleTeamService-Thread");
		t.start();
	}

	@Override
	public void run() {
		BossRoomService roomService = ServiceManager.getManager().getBossRoomService();
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
				long nowTime = (new Date()).getTime();
				BossBattleTeam battleTeam;
				for (int i = battleTeamList.size() - 1; i >= 0; i--) {
					battleTeam = battleTeamList.get(i);
					ServiceManager.getManager().getSimpleThreadPool().execute(createTask(nowTime, battleTeam, roomService));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Runnable createTask(long nowTime, BossBattleTeam battleTeam, BossRoomService roomService) {
		return new BattleThread(nowTime, battleTeam, roomService);
	}

	public class BattleThread implements Runnable {
		private long nowTime;
		private BossBattleTeam battleTeam;
		private BossRoomService roomService;

		public BattleThread(long nowTime, BossBattleTeam battleTeam, BossRoomService roomService) {
			this.nowTime = nowTime;
			this.battleTeam = battleTeam;
			this.roomService = roomService;
		}

		@Override
		public void run() {
			try {
				for (Combat combat : battleTeam.getCombatList()) {
					if (null != combat.getPlayer() && !combat.isRobot() && !combat.isLost()) {
						if ((nowTime - combat.getPlayer().getActionTime()) > OUT_TIME) {
							WorldPlayer player = combat.getPlayer();
							int index = roomService.getPlayerSeat(player.getBossmapRoomId(), player.getId());
							roomService.extRoom(player.getBossmapRoomId(), index, 0);
							playerLose(battleTeam.getBattleId(), combat.getId());
							PlayerLose playerLose = new PlayerLose();
							playerLose.setBattleId(battleTeam.getBattleId());
							playerLose.setPlayerId(player.getId());
							player.sendData(playerLose);
							StringBuffer buf = new StringBuffer();
							buf.append("玩家超过");
							buf.append(OUT_TIME);
							buf.append("毫秒没有响应强制退出对战。");
							buf.append("---战斗组:");
							buf.append(battleTeam.getBattleId());
							player.writeLog(buf);
						}
					}
				}
				if (null != battleTeam) {
					gameOver(battleTeam.getBattleId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建战斗组
	 * 
	 * @return 返回战斗组的id
	 */
	public int createBattleTeam(BossRoom room) {
		BossBattleTeam battleTeam = new BossBattleTeam();
		battleTeam.setStartTime((new Date()).getTime());
		battleTeam.setMapId(room.getMapId());
		battleTeam.setBossRoom(room);
		battleTeam.setDifficulty(room.getDifficulty());
		battleTeam.setRunTimes(room.getRunTimes());
		battleTeamMap.put(battleTeam.getBattleId(), battleTeam);
		battleTeamList.add(battleTeam);
		return battleTeam.getBattleId();
	}

	/**
	 * 为兼容世界BOSS创建 一个玩家一个Team
	 */
	public int createWordBossBattleTeam(Combat player, Combat boss, int mapId) {
		BossBattleTeam battleTeam = new BossBattleTeam();
		battleTeam.setBuffSet(new HashSet<Integer>());
		battleTeam.setStartTime((new Date()).getTime());
		battleTeam.setMapId(mapId);
		battleTeam.setDifficulty(1);
		battleTeamMap.put(battleTeam.getBattleId(), battleTeam);
		battleTeamList.add(battleTeam);
		// 加入玩家
		battleTeam.enterPlayer(player);
		battleTeam.getTrueCombatList().add(player);
		// 加入BOSS
		battleTeam.enterGuai(boss);
		System.out.println("玩家：" + player.getId() + " 进入BattleTeam:" + battleTeam.getBattleId());
		// 更新玩家任务
		ServiceManager.getManager().getTaskService().worldBoss(player.getPlayer());
		return battleTeam.getBattleId();
	}

	/**
	 * 加入战斗组
	 * 
	 * @param battleId
	 * @param room
	 *            房间对象
	 */
	public void enBattleTeam(int battleId, WorldPlayer player, int camp) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			if (battleTeam == null)
				return;
			Combat combat = new Combat();
			combat.setPlayer(player);
			combat.setCamp(camp);
			combat.setRobot(false);
			combat.setHp(player.getMaxHP());
			combat.setPf(player.getMaxPF(), battleTeam.getMapId(), 6, player);
			player.setActionTime(System.currentTimeMillis());
			combat.setAiCtrlId(ServiceUtils.getRandomNum(0, 3));
			battleTeam.enterPlayer(combat);
			battleTeam.getTrueCombatList().add(combat);
			player.setBossmapBattleId(battleId);
			if (playerReward.containsKey(player.getId())) {
				playerReward.remove(player.getId());
			}
			// 保存打副本记录
			int mapId = battleTeam.getMapId();
			PlayerBossmap playerMap = ServiceManager.getManager().getPlayerBossmapService().loadPlayerBossMap(player.getId(), mapId);
			playerMap.setTotalPassTimes(playerMap.getTotalPassTimes() + 1);
			playerMap.setPassTimes(playerMap.getPassTimes() + 1);
			ServiceManager.getManager().getPlayerBossmapService().update(playerMap);
		}
	}

	/**
	 * 计算计录可以生成怪id的数组缓冲需要多大
	 * 
	 * @param battleId
	 * @return
	 */
	public int getCouldBeBuildedGuaiBufferSize(List<Combat> combatGuaiList) {
		if (null == combatGuaiList)
			return 0;
		int count = 0;
		count += combatGuaiList.size();
		for (Combat combatGuai : combatGuaiList) {
			if (combatGuai.getBuildGuaiList() != null) {
				count += combatGuai.getBuildGuaiList().size();
			}
		}
		return count;
	}

	/**
	 * 通知战斗组成员开始战斗
	 * 
	 * @param battleId
	 * @param room
	 */
	public void startBattle(int battleId, BossRoom room) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			battleTeam.setBattleMode(room.getBattleMode());
			battleTeam.setPlayerNumMode(room.getPlayerNumMode());
			battleTeam.setFightWithAI(0);
			int truePlayerCount = battleTeam.getCombatList().size();// 先记录真实玩家的数量
			List<Combat> guaiList = setGuaiToCombat(battleId, room.getGuaiList());
			MakePairOk makePairOk = new MakePairOk();
			int playerCount = battleTeam.getCombatList().size();
			int[] playerId = new int[playerCount];
			int[] roomId = new int[playerCount];
			int[] posX = new int[playerCount];// 坐标X
			int[] posY = new int[playerCount];// 坐标X
			String[] playerName = new String[playerCount];
			int[] playerLevel = new int[playerCount];
			int[] boyOrGirl = new int[playerCount];
			String[] suit_head = new String[playerCount];
			String[] suit_face = new String[playerCount];
			String[] suit_body = new String[playerCount];
			String[] suit_weapon = new String[playerCount];
			int[] weapon_type = new int[playerCount];
			int[] camp = new int[playerCount];
			int[] maxHP = new int[playerCount];
			int[] maxPF = new int[playerCount];
			int[] maxSP = new int[playerCount];
			int[] attack = new int[playerCount];
			int[] bigSkillAttack = new int[playerCount];
			int[] critRate = new int[playerCount];
			int[] defence = new int[playerCount];
			int[] bigSkillType = new int[playerCount];
			int[] explodeRadius = new int[playerCount];
			int[] injuryFree = new int[playerCount];
			int[] wreckDefense = new int[playerCount];
			int[] reduceCrit = new int[playerCount];
			int[] reduceBury = new int[playerCount];
			int[] item_id = new int[playerCount * 8];
			int[] item_used = new int[playerCount * 8];
			String[] item_img = new String[playerCount * 8];
			String[] item_name = new String[playerCount * 8];
			String[] item_desc = new String[playerCount * 8];
			int[] item_type = new int[playerCount * 8];
			int[] item_subType = new int[playerCount * 8];
			int[] item_param1 = new int[playerCount * 8];
			int[] item_param2 = new int[playerCount * 8];
			int[] item_ConsumePower = new int[playerCount * 8];
			int[] specialAttackType = new int[playerCount * 8];
			int[] specialAttackParam = new int[playerCount * 8];
			String[] suit_wing = new String[playerCount];
			String[] player_title = new String[playerCount];
			String[] player_community = new String[playerCount];
			int[] zsleve = new int[playerCount];
			int[] skillful = new int[playerCount];
			int[] petId = new int[playerCount];
			String[] petIcon = new String[playerCount];
			int[] petType = new int[playerCount];
			int[] petSkillId = new int[playerCount];
			int[] petProbability = new int[playerCount];
			int[] petParam1 = new int[playerCount];
			int[] petParam2 = new int[playerCount];
			int[] power = new int[playerCount];
			int[] armor = new int[playerCount];
			int[] constitution = new int[playerCount];
			int[] agility = new int[playerCount];
			int[] lucky = new int[playerCount];
			String[] petEffect = new String[playerCount];
			int[] petVersion = new int[playerCount];
			Combat combat;
			int z = 0;
			List<Map<String, Integer>> posList = getplayerPos(room.getGuaiList(), true);
			// 这里是为了保证,数据库数据,如果填错了,不会出现死机
			if (posList.size() < truePlayerCount) {
				log.error("地图mapId:" + room.getMapId() + "人物坐标数据数量不足");
				Map<String, Integer> pos;
				if (posList.size() <= 0) {
					pos = new HashMap<String, Integer>();
					pos.put("id", -1);
					pos.put("Camp", 0);
					pos.put("posX", 900);
					pos.put("posY", 1000);
				} else {
					pos = posList.get(0);
				}
				while (posList.size() < truePlayerCount) {
					posList.add(pos);
				}
			}
			int posIndex;
			MarryRecord mr;
			int coupleId = 0;
			for (int i = 0; i < battleTeam.getCombatList().size(); i++) {
				combat = battleTeam.getCombatList().get(i);
				int mapId = battleTeam.getMapId();
				// 设为等级,用于第一次排序
				combat.setTiredValue(combat.getLevel());
				mr = ServiceManager.getManager().getMarryService().getSingleMarryRecordByPlayerId(combat.getSex(), combat.getId(), 1);
				if (null != mr && mr.getStatusMode() > 1) {
					if (combat.getSex() == 0) {
						coupleId = mr.getWomanId();
					} else {
						coupleId = mr.getManId();
					}
				}
				PlayerPet playerPet = combat.getPlayer().getPlayerPet();
				if (null != playerPet) {
					petId[i] = playerPet.getPet().getId();
					petIcon[i] = playerPet.getPet().getIcon();
					petType[i] = playerPet.getSkill().getType();
					petSkillId[i] = playerPet.getSkill().getId();
					petProbability[i] = playerPet.getSkill().getUseChance();
					petParam1[i] = playerPet.getSkill().getParam1();
					petParam2[i] = playerPet.getSkill().getParam2();
					petEffect[i] = playerPet.getSkill().getEffect();
					petVersion[i] = playerPet.getPet().getVersion();
				} else {
					petIcon[i] = "";
					petEffect[i] = "";
				}
				playerId[i] = combat.getId();
				if (null != combat.getPlayer()) {
					zsleve[i] = combat.getPlayer().getPlayer().getZsLevel();
					skillful[i] = combat.getProficiency();
				} else {
					zsleve[i] = 0;
					skillful[i] = 0;
				}
				roomId[i] = combat.getBossmapRoomId();
				if (combat.isGuaiPlayer()) {
					posX[i] = (int) combat.getX();
					posY[i] = (int) combat.getY();
				} else {
					posIndex = ServiceUtils.getRandomNum(0, posList.size());
					posX[i] = posList.get(posIndex).get("posX");
					posY[i] = posList.get(posIndex).get("posY");
					posList.remove(posIndex);
				}
				playerName[i] = combat.getName();
				playerLevel[i] = combat.getLevel();
				boyOrGirl[i] = combat.getSex();
				suit_head[i] = combat.getSuit_head();
				suit_face[i] = combat.getSuit_face();
				suit_body[i] = combat.getSuit_body();
				suit_weapon[i] = combat.getSuit_weapon();
				weapon_type[i] = combat.getWeapon_type();
				suit_wing[i] = combat.getSuit_wing();
				player_title[i] = combat.getTitle();
				player_community[i] = combat.getGuildName();
				camp[i] = combat.getCamp();
				maxHP[i] = combat.getMaxHP();
				combat.setPlayerMaxHP(maxHP[i]);
				maxPF[i] = combat.getMaxPF();
				maxSP[i] = combat.getMaxSP();
				attack[i] = combat.getAttack(mapId);
				injuryFree[i] = combat.getInjuryFree();
				wreckDefense[i] = combat.getWreckDefense();
				reduceCrit[i] = combat.getReduceCrit();
				reduceBury[i] = combat.getReduceBury();
				if (coupleId != 0) {
					for (Combat c : battleTeam.getCombatList()) {
						if (c.getId() == coupleId) {
							if (c.isGuaiPlayer()) {
								attack[i] = combat.getAttack(mapId);
							} else {
								attack[i] = (int) ServiceManager.getManager().getBuffService()
										.getAddition(combat.getPlayer(), combat.getAttack(mapId), Buff.MHURT);
							}
						}
					}
				}
				combat.setPlayerAttack(attack[i]);
				bigSkillAttack[i] = combat.getBigSkillAttack();
				critRate[i] = combat.getCrit();
				defence[i] = combat.getDefend();
				bigSkillType[i] = combat.getBigSkillType();
				explodeRadius[i] = combat.getExplodeRadius();
				power[i] = combat.getPlayer().getForce();
				armor[i] = combat.getPlayer().getArmor();
				constitution[i] = combat.getPlayer().getPhysique();
				agility[i] = combat.getPlayer().getAgility();
				lucky[i] = combat.getPlayer().getLuck();
				for (int y = 0; y < 4; y++) {
					item_used[z] = combat.getItemJnused()[y];
					if (item_used[z] > 0) {
						Tools tools = combat.getPlayer().getPlayerJNs().get(y);
						item_id[z] = (!combat.getPlayer().isVip() && y == 3) ? -1 : tools.getId();
						item_img[z] = tools.getIcon();
						item_name[z] = tools.getName();
						item_desc[z] = tools.getDesc();
						item_type[z] = tools.getType();
						item_subType[z] = tools.getSubtype();
						item_param1[z] = tools.getParam1();
						item_param2[z] = tools.getParam2();
						item_ConsumePower[z] = tools.getConsumePower();
						specialAttackType[z] = tools.getSpecialAttackType();
						specialAttackParam[z] = tools.getSpecialAttackParam();
					} else {
						item_id[z] = 0;
						item_img[z] = "0";
						item_name[z] = "0";
						item_desc[z] = "0";
						item_type[z] = 0;
						item_subType[z] = 0;
						item_param1[z] = 0;
						item_param2[z] = 0;
						item_ConsumePower[z] = 0;
						specialAttackType[z] = 0;
						specialAttackParam[z] = 0;
					}
					z++;
				}
				for (int y = 0; y < 4; y++) {
					item_used[z] = combat.getItemDjused()[y];
					if (item_used[z] > 0) {
						Tools tools = combat.getPlayer().getPlayerDJs().get(y);
						item_id[z] = (!combat.getPlayer().isVip() && y == 3) ? -1 : tools.getId();
						item_img[z] = tools.getIcon();
						item_name[z] = tools.getName();
						item_desc[z] = tools.getDesc();
						item_type[z] = tools.getType();
						item_subType[z] = tools.getSubtype();
						item_param1[z] = tools.getParam1();
						item_param2[z] = tools.getParam2();
						item_ConsumePower[z] = tools.getConsumePower();
						specialAttackType[z] = tools.getSpecialAttackType();
						specialAttackParam[z] = tools.getSpecialAttackParam();
					} else {
						item_id[z] = 0;
						item_img[z] = "0";
						item_name[z] = "0";
						item_desc[z] = "0";
						item_type[z] = 0;
						item_subType[z] = 0;
						item_param1[z] = 0;
						item_param2[z] = 0;
						item_ConsumePower[z] = 0;
						specialAttackType[z] = 0;
						specialAttackParam[z] = 0;
					}
					z++;
				}
			}
			// 创建怪的信息
			int guaiCount = guaiList.size();
			int[] guaiBattleId = new int[guaiCount]; // 在本次对战中的独立id
			int[] guaiId = new int[guaiCount]; // 怪在怪表中的id
			int[] guaiposX = new int[guaiCount];// 怪坐标X
			int[] guaiposY = new int[guaiCount];// 怪坐标X
			String[] guai_name = new String[guaiCount]; // 名字
			int[] guai_camp = new int[guaiCount]; // 怪的阵营，0是玩家队友，1是怪
			int[] guai_sex = new int[guaiCount]; // 0:男 1:女
			String[] guai_suit_head = new String[guaiCount]; // 着装头(如果type==1时,值为stand)
			String[] guai_suit_face = new String[guaiCount]; // 着装脸(如果type==1时,值为stand)
			String[] guai_suit_body = new String[guaiCount]; // 着装身(如果type==1时,值为stand)
			String[] guai_suit_weapon = new String[guaiCount]; // 着装武器(如果type==1时,值为stand)
			int[] guai_weapon_type = new int[guaiCount]; // 武器类型
			int[] guai_type = new int[guaiCount]; // 0:有着装的小怪 1:没有着装的小怪 2:boss
			int[] guai_level = new int[guaiCount]; // 等级
			int[] guai_attacktype = new int[guaiCount];// 攻击类型
			int[] guai_hp = new int[guaiCount]; // 生命值
			int[] guai_sp = new int[guaiCount]; // 怒气值
			int[] guai_pf = new int[guaiCount]; // 体力
			int[] guai_defend = new int[guaiCount]; // 防御力
			int[] guai_attack = new int[guaiCount]; // 攻击力
			int[] guai_attackArea = new int[guaiCount]; // 攻击范围
			int[] guai_criticalRate = new int[guaiCount]; // 万份比数值(放大一万陪) 增加暴击率
			int[] guai_bigSkillType = new int[guaiCount]; // 大招类型.没有大招:-1
			String[] guai_explode = new String[guaiCount]; // 爆炸图(type==0时,这里不需要,直接根武器相关)
			String[] guai_broken = new String[guaiCount]; // 坑图(type==0时,这里不需要,直接根武器相关)
			String[] guai_AniFileId = new String[guaiCount]; // 动画文件id.格式:[boss[id]]或[guai[id]],如:boss1,guai1
			int[] guai_could_build_guai = new int[guaiCount]; // 是否可以招唤小怪
			int[] guai_build_guai_id = new int[getCouldBeBuildedGuaiBufferSize(guaiList)]; // 放出的小怪在怪表中的id
			int[] build_guai_id_list = new int[10]; // 如果招唤小怪就会给其这些后续的id,是不会与之前的哪此冲突的
			int[] guai_injuryFree = new int[guaiCount]; // 免伤
			int[] guai_wreckDefense = new int[guaiCount]; // 破防
			int[] guai_reduceCrit = new int[guaiCount]; // 免暴
			int[] guai_reduceBury = new int[guaiCount]; // 免坑
			String[] guai_ai = new String[guaiCount];
			String[] guai_dialogue = new String[guaiCount];
			List<Integer> skillHurt = new ArrayList<Integer>(); // boss的技能伤害
			Combat combatGuai;
			int guai_bulid_guai_id_index = 0;
			for (int i = 0; i < guaiList.size(); i++) {
				combatGuai = guaiList.get(i);
				GuaiPlayer guai = combatGuai.getGuai();
				guaiBattleId[i] = combatGuai.getBattleId();
				guaiId[i] = combatGuai.getGuai().getGuai().getGuaiId();
				guaiposX[i] = (int) combatGuai.getX();
				guaiposY[i] = (int) combatGuai.getY();
				guai_name[i] = combatGuai.getGuai().getGuai().getName();
				guai_camp[i] = combatGuai.getCamp();
				guai_sex[i] = guai.getGuai().getSex();
				guai_suit_head[i] = guai.getGuai().getSuit_head();
				guai_suit_face[i] = guai.getGuai().getSuit_face();
				guai_suit_body[i] = guai.getGuai().getSuit_body();
				guai_suit_weapon[i] = guai.getGuai().getSuit_weapon();
				guai_weapon_type[i] = guai.getGuai().getWeapon_type();
				guai_type[i] = guai.getGuai().getType();
				guai_level[i] = guai.getGuai().getLevel();
				guai_attacktype[i] = guai.getGuai().getAttack_type();
				guai_hp[i] = guai.getMaxHP();
				guai_sp[i] = guai.getMaxSP();
				guai_pf[i] = guai.getMaxPF();
				guai_defend[i] = guai.getDefend();
				guai_attack[i] = guai.getAttack();
				guai_attackArea[i] = guai.getGuai().getAttackArea();
				guai_criticalRate[i] = guai.getCritAttackRate();
				guai_bigSkillType[i] = guai.getGuai().getBigSkillType();
				guai_explode[i] = guai.getGuai().getExplode();
				guai_broken[i] = guai.getGuai().getBroken();
				guai_AniFileId[i] = guai.getGuai().getAniFileId();
				guai_injuryFree[i] = guai.getInjuryFree();
				guai_wreckDefense[i] = guai.getWreckDefense();
				guai_reduceCrit[i] = guai.getReduceCrit();
				guai_reduceBury[i] = guai.getReduceBury();
				guai_ai[i] = guai.getGuai().getGuai_ai();
				guai_dialogue[i] = guai.getGuai().getDialogue();
				skillHurt.add(guai.getGuai().getSkill_1());
				skillHurt.add(guai.getGuai().getSkill_2());
				skillHurt.add(guai.getGuai().getSkill_3());
				skillHurt.add(guai.getGuai().getSkill_4());
				skillHurt.add(guai.getGuai().getSkill_5());
				if (combatGuai.isCouldBuildGuai()) {
					guai_could_build_guai[i] = 1;
					guai_build_guai_id[guai_bulid_guai_id_index++] = combatGuai.getBuildGuaiList().size();
					for (Integer ii : combatGuai.getBuildGuaiList()) {
						guai_build_guai_id[guai_bulid_guai_id_index++] = ii.intValue();
					}
				} else {
					guai_could_build_guai[i] = 0;
					guai_build_guai_id[guai_bulid_guai_id_index++] = 0;
				}
			}
			makePairOk.setBattleId(battleId);
			makePairOk.setBattleMode(room.getBattleMode());
			makePairOk.setBossMapName(room.getRoomShortName());
			makePairOk.setBattleMap(room.getAnimationIndexCode());
			makePairOk.setBattlemap_icon(room.getMapIcon());
			makePairOk.setPlayerCount(playerCount);
			makePairOk.setPlayerId(playerId);
			makePairOk.setRoomId(roomId);
			makePairOk.setPosX(posX);
			makePairOk.setPosY(posY);
			makePairOk.setPlayerName(playerName);
			makePairOk.setPlayerLevel(playerLevel);
			makePairOk.setBoyOrGirl(boyOrGirl);
			makePairOk.setSuit_head(suit_head);
			makePairOk.setSuit_face(suit_face);
			makePairOk.setSuit_body(suit_body);
			makePairOk.setSuit_weapon(suit_weapon);
			makePairOk.setWeapon_type(weapon_type);
			makePairOk.setCamp(camp);
			makePairOk.setMaxHP(maxHP);
			makePairOk.setMaxPF(maxPF);
			makePairOk.setMaxSP(maxSP);
			makePairOk.setAttack(attack);
			makePairOk.setBigSkillAttack(bigSkillAttack);
			makePairOk.setCrit(critRate);
			makePairOk.setDefence(defence);
			makePairOk.setBigSkillType(bigSkillType);
			makePairOk.setExplodeRadius(explodeRadius);
			makePairOk.setItem_id(item_id);
			makePairOk.setItem_used(item_used);
			makePairOk.setItem_img(item_img);
			makePairOk.setItem_name(item_name);
			makePairOk.setItem_desc(item_desc);
			makePairOk.setItem_type(item_type);
			makePairOk.setItem_subType(item_subType);
			makePairOk.setItem_param1(item_param1);
			makePairOk.setItem_param2(item_param2);
			makePairOk.setItem_ConsumePower(item_ConsumePower);
			makePairOk.setSpecialAttackType(specialAttackType);
			makePairOk.setSpecialAttackParam(specialAttackParam);
			makePairOk.setGuaiCount(guaiCount);
			makePairOk.setGuaiBattleId(guaiBattleId);
			makePairOk.setGuaiId(guaiId);
			makePairOk.setGuaiposX(guaiposX);
			makePairOk.setGuaiposY(guaiposY);
			makePairOk.setGuai_name(guai_name);
			makePairOk.setGuai_camp(guai_camp);
			makePairOk.setGuai_sex(guai_sex);
			makePairOk.setGuai_suit_head(guai_suit_head);
			makePairOk.setGuai_suit_face(guai_suit_face);
			makePairOk.setGuai_suit_body(guai_suit_body);
			makePairOk.setGuai_suit_weapon(guai_suit_weapon);
			makePairOk.setGuai_weapon_type(guai_weapon_type);
			makePairOk.setGuai_type(guai_type);
			makePairOk.setGuai_level(guai_level);
			makePairOk.setGuai_attacktype(guai_attacktype);
			makePairOk.setGuai_hp(guai_hp);
			makePairOk.setGuai_sp(guai_sp);
			makePairOk.setGuai_pf(guai_pf);
			makePairOk.setGuai_defend(guai_defend);
			makePairOk.setGuai_attack(guai_attack);
			makePairOk.setGuai_attackArea(guai_attackArea);
			makePairOk.setGuai_criticalRate(guai_criticalRate);
			makePairOk.setGuai_bigSkillType(guai_bigSkillType);
			makePairOk.setGuai_explode(guai_explode);
			makePairOk.setGuai_broken(guai_broken);
			makePairOk.setGuai_AniFileId(guai_AniFileId);
			makePairOk.setGuai_could_build_guai(guai_could_build_guai);
			makePairOk.setGuai_build_guai_id(guai_build_guai_id);
			makePairOk.setGuai_ai(guai_ai);
			makePairOk.setGuai_dialogue(guai_dialogue);
			makePairOk.setIdcount(10);
			makePairOk.setPower(power);
			makePairOk.setArmor(armor);
			makePairOk.setConstitution(constitution);
			makePairOk.setLucky(lucky);
			makePairOk.setAgility(agility);
			for (int i = 0; i < 10; i++) {
				build_guai_id_list[i] = battleTeam.getGuaiIdDistribution();
			}
			makePairOk.setBuild_guai_id_list(build_guai_id_list);
			battleTeam.setNewRun(true);
			battleTeam.setActionIndex(0);
			battleTeam.setCurRoundSequence(null);
			int[] playerBuffCount = new int[playerCount];
			List<Integer> buffType = new ArrayList<Integer>();
			List<Integer> buffParam1 = new ArrayList<Integer>();
			int[] buffParam2 = new int[0];
			int[] buffParam3 = new int[0];
			int playerIndex = 0;
			List<Integer> weaponSkillPlayerId = new ArrayList<Integer>();
			List<String> weaponSkillName = new ArrayList<String>();
			List<Integer> weaponSkillType = new ArrayList<Integer>();
			List<Integer> weaponSkillChance = new ArrayList<Integer>();
			List<Integer> weaponSkillParam1 = new ArrayList<Integer>();
			List<Integer> weaponSkillParam2 = new ArrayList<Integer>();
			PlayerItemsFromShop pifs;
			WeapSkill wp;
			for (Combat player : battleTeam.getCombatList()) {
				if (!player.isLost() && !player.isRobot()) {
					List<Buff> buffList = player.getPlayer().getBuffList();
					int buffcount = 0;
					for (Buff b : buffList) {
						if (b.getBuffCode().equals("cpower")) {
							buffType.add(b.getBufftype());
							buffParam1.add(b.getQuantity());
							buffcount++;
						}
						if (b.getBuffCode().equals("ccrit")) {
							buffType.add(b.getBufftype());
							buffParam1.add(b.getQuantity());
							buffcount++;
						}
						if (b.getBuffCode().equals("churt")) {
							buffType.add(b.getBufftype());
							buffParam1.add(b.getQuantity());
							buffcount++;
						}
						if (b.getBuffCode().equals("cpowerlow")) {
							buffType.add(b.getBufftype());
							buffParam1.add(b.getQuantity());
							buffcount++;
						}
					}
					pifs = player.getPlayer().getWeapon(); // ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemsFromShopByPlayerIdAndItemId(player.getPlayer().getPlayer(),
															// player.getPlayer().getPlayer().getSuitWeaponId());
					if (pifs != null && pifs.getWeapSkill1() != 0) {
						wp = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
						weaponSkillPlayerId.add(pifs.getPlayerId());
						weaponSkillName.add(wp.getSkillName());
						weaponSkillType.add(wp.getType());
						weaponSkillChance.add(wp.getUseChance());
						weaponSkillParam1.add(wp.getParam1());
						weaponSkillParam2.add(wp.getParam2());
					}
					if (pifs != null && pifs.getWeapSkill2() != 0) {
						wp = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
						weaponSkillPlayerId.add(pifs.getPlayerId());
						weaponSkillName.add(wp.getSkillName());
						weaponSkillType.add(wp.getType());
						weaponSkillChance.add(wp.getUseChance());
						weaponSkillParam1.add(wp.getParam1());
						weaponSkillParam2.add(wp.getParam2());
					}
					playerBuffCount[playerIndex] = buffcount;
					makePairOk.setPlayerBuffCount(playerBuffCount);
					makePairOk.setBuffType(ServiceUtils.getInts(buffType.toArray()));
					makePairOk.setBuffParam1(ServiceUtils.getInts(buffParam1.toArray()));
					makePairOk.setBuffParam2(buffParam2);
					makePairOk.setBuffParam3(buffParam3);
					playerIndex++;
				}
			}
			String[] strings = new String[weaponSkillName.size()];
			sort(battleId);
			makePairOk.setWeaponSkillPlayerId(ServiceUtils.getInts(weaponSkillPlayerId.toArray()));
			makePairOk.setWeaponSkillChance(ServiceUtils.getInts(weaponSkillChance.toArray()));
			makePairOk.setWeaponSkillName(weaponSkillName.toArray(strings));
			makePairOk.setWeaponSkillParam1(ServiceUtils.getInts(weaponSkillParam1.toArray()));
			makePairOk.setWeaponSkillParam2(ServiceUtils.getInts(weaponSkillParam2.toArray()));
			makePairOk.setWeaponSkillType(ServiceUtils.getInts(weaponSkillType.toArray()));
			makePairOk.setSuit_wing(suit_wing);
			makePairOk.setPlayer_title(player_title);
			makePairOk.setPlayer_community(player_community);
			makePairOk.setInjuryFree(injuryFree);
			makePairOk.setWreckDefense(wreckDefense);
			makePairOk.setReduceCrit(reduceCrit);
			makePairOk.setReduceBury(reduceBury);
			makePairOk.setZsleve(zsleve);
			makePairOk.setSkillful(skillful);
			makePairOk.setDifficulty(battleTeam.getDifficulty());
			makePairOk.setGuai_injuryFree(guai_injuryFree);
			makePairOk.setGuai_wreckDefense(guai_wreckDefense);
			makePairOk.setGuai_reduceCrit(guai_reduceCrit);
			makePairOk.setGuai_reduceBury(guai_reduceBury);
			makePairOk.setSkillHurt(ServiceUtils.getInts(skillHurt.toArray()));
			battleTeam.setSkillHurt(makePairOk.getSkillHurt());
			makePairOk.setPetId(petId);
			makePairOk.setPetIcon(petIcon);
			makePairOk.setPetType(petType);
			makePairOk.setPetSkillId(petSkillId);
			makePairOk.setPetProbability(petProbability);
			makePairOk.setPetParam1(petParam1);
			makePairOk.setPetParam2(petParam2);
			makePairOk.setPetEffect(petEffect);
			makePairOk.setPetVersion(petVersion);
			for (Combat player : battleTeam.getCombatList()) {
				if (!player.isLost() && !player.isRobot()) {
					player.getPlayer().sendData(makePairOk);
				}
			}
		}
	}

	/**
	 * 获取战斗组
	 * 
	 * @param battleId
	 * @return
	 */
	public BossBattleTeam getBattleTeam(int battleId) {
		return battleTeamMap.get(battleId);
	}

	/**
	 * 加载完成准备进入战斗
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void ready(int battleId, int playerId) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		if (null != battleTeam)
			battleTeam.ready(playerId);
	}

	/**
	 * 是否所有玩家加载完成
	 * 
	 * @param battleId
	 * @return
	 */
	public boolean isReady(int battleId) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		if (null == battleTeam) {
			return false;
		}
		synchronized (battleTeam) {
			Vector<Combat> combatList = battleTeam.getCombatList();
			for (Combat combat : combatList) {
				if (!combat.isRobot() && !combat.isLost()) {
					if (2 != combat.getState()) {
						return false;
					}
				}
			}
			return true;
		}
	}

	public int getWind() {
		int num = ServiceUtils.getRandomNum(1, 101) - ServiceUtils.getRandomNum(1, 101);
		if (num == 0) {
			return 0;
		}
		int fh = Math.abs(num) / num;
		if (num < 13) {
			return 0;
		} else if (num < 38) {
			return 1 * fh;
		} else if (num < 60) {
			return 2 * fh;
		} else if (num < 79) {
			return 3 * fh;
		} else if (num < 90) {
			return 4 * fh;
		} else if (num < 97) {
			return 5 * fh;
		} else {
			return 6 * fh;
		}
	}

	/**
	 * 回合结束发送新一轮的行动次序
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void sendSort(int battleId) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			if (null == battleTeam) {
				return;
			}
			battleTeam.setNewRun(true);
			battleTeam.setActionIndex(0);
			battleTeam.setWind(getWind());
			battleTeam.setRound(battleTeam.getRound() + 1);
			battleTeam.setCurRoundSequence(null);
			sort(battleId);
		}
	}

	/**
	 * 设置角色攻击顺序
	 * 
	 * @param battleId
	 * @return
	 * @return
	 */
	public void sort(int battleId) {
		IWorldBossService wroldBossService = ServiceManager.getManager().getWorldBossService();
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		int mapId = battleTeam.getMapId();
		// 是否世界BOSS
		if (wroldBossService.isWorldBossBattle(mapId)) {
			battleTeam.worldBossSort();
		} else {
			battleTeam.sort();
		}
	}

	/**
	 * 设置角色攻击顺序
	 * 
	 * @param battleId
	 * @return
	 * @return
	 */
	public void worldBossSort(int battleId) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		battleTeam.worldBossSort();
	}

	/**
	 * 获得当前行动玩家(玩家)
	 * 
	 * @param battleId
	 * @return
	 */
	public CombatChara getActionChara(int battleId) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		return battleTeam.getNextActionChara();
	}

	/**
	 * 同步怒气值
	 * 
	 * @param battleId
	 *            战斗Id
	 * @param playerOrGuai
	 *            角色Id
	 * @param currentId
	 *            角色Id
	 * @param value
	 *            怒气值
	 * @param useBigKill
	 *            是否使用大招
	 */
	public void updateAngryValue(int battleId, int playerOrGuai, int currentId, int value, boolean useBigKill) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		if (battleTeam == null)
			return;
		synchronized (battleTeam) {
			if (playerOrGuai == 0) {
				Combat combat = battleTeam.getCombatMap().get(currentId);
				if (useBigKill) {
					combat.setAngryValue(0);
				} else {
					int angryValue = combat.getAngryValue() + value;
					angryValue = angryValue > 100 ? 100 : angryValue;
					combat.setAngryValue(angryValue);
				}
				// System.out.println("playerId:"+playerId+"-----AngryValue:"+combat.getAngryValue());
				ChangeAngryValue changeAngryValue = new ChangeAngryValue();
				changeAngryValue.setBattleId(battleId);
				changeAngryValue.setPlayerOrGuai(playerOrGuai);
				changeAngryValue.setCurrentId(currentId);
				changeAngryValue.setAngryValue(combat.getAngryValue());
				for (Combat cb : battleTeam.getCombatList()) {
					if (!cb.isRobot() && !cb.isLost() && null != cb.getPlayer()) {
						cb.getPlayer().sendData(changeAngryValue);
					}
				}
			}
		}
	}

	/**
	 * 判断游戏是否结束
	 * 
	 * @param battleId
	 * @throws Exception
	 */
	public boolean gameOver(int battleId) throws Exception {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		IWorldBossService wroldBossService = ServiceManager.getManager().getWorldBossService();
		if (null == battleTeam) {
			return true;
		}
		Vector<Combat> combatList = battleTeam.getCombatList();
		Vector<Combat> combatGuaiList = battleTeam.getCombatGuaiList();
		// List<Integer> playerIdsList = battleTeam.getCurRoundSequence();
		boolean isOver = false;
		int winCamp = -1;
		boolean combatIsOver0 = true;
		boolean combatGuaiIsOver0 = true;
		boolean combatBossOver = true;
		boolean combatIsOver1 = true;
		for (Combat combat : combatList) {
			// System.out.println("camp:"+combat.getCamp()+","+"isLost:"+combat.isLost()
			// +","+"isDead:"+combat.isDead());
			if (combat.getCamp() == 0 && !combat.isLost() && !combat.isDead()) {
				combatIsOver0 = false;
				break;
			}
		}
		for (Combat combatGuai : combatGuaiList) {
			// System.out.println("camp:"+combatGuai.getCamp()+","+"isLost:"+combatGuai.isLost()
			// +","+"isDead:"+combatGuai.isDead());
			if (combatGuai.getCamp() == 0 && !combatGuai.isLost() && !combatGuai.isDead()) {
				combatGuaiIsOver0 = false;
				break;
			}
		}
		if (!combatIsOver0 || !combatGuaiIsOver0) {// 是否都还没死光
			// boss都死光
			for (Combat combatGuai : combatGuaiList) {
				if (combatGuai.getCamp() == 1 && !combatGuai.isLost() && !combatGuai.isDead()
						&& combatGuai.getGuai().getGuai().getType() == Combat.GUAI_TYPE_BOSS) {
					combatBossOver = false;
					break;
				}
			}
			// 另一方的玩家都死光
			for (Combat combat : combatList) {
				if (combat.getCamp() == 1 && !combat.isLost() && !combat.isDead()) {
					combatIsOver1 = false;
					break;
				}
			}
			if (combatBossOver && combatIsOver1) {
				winCamp = 0;
				isOver = true;
			}
		} else {
			isOver = true;
			winCamp = 1;
		}
		if (isOver) {
			synchronized (battleTeam) {
				if (battleTeam.isOver())
					return isOver;
				battleTeam.setOver(isOver);
			}
			Vector<Combat> trueCombatList = battleTeam.getTrueCombatList();
			int mapId = battleTeam.getMapId();
			// 真实玩家
			int playerCount = trueCombatList.size();
			int[] playerIds = new int[playerCount];
			int[] shootRate = new int[playerCount];
			int[] totalHurt = new int[playerCount];
			int[] killCount = new int[playerCount];
			int[] beKilledCount = new int[playerCount];
			int[] addExp = new int[playerCount];
			int[] Exp = new int[playerCount];
			int[] upgradeExp = new int[playerCount];
			int[] nextUpgradeExp = new int[playerCount];
			int[] star = new int[playerCount];
			int[] isDoubleExpCard = new int[playerCount];
			int eggCount = 18;
			int[] egg_playeId = new int[18];
			String[] egg_Item_Name = initStrings(eggCount);
			String[] egg_item_icon = initStrings(eggCount);
			int[] egg_ItemNum = new int[18];
			// 是否世界BOSS
			if (wroldBossService.isWorldBossBattle(mapId)) {
				ServiceUtils.out("世界boss-battleId:" + battleId + " isOver");

				WorldBossRoomService roomService = WorldBossRoomService.getInstance();
				WorldBossRoom room = roomService.getRoomByMap(mapId);
				if (room.getBoss().isDead()) {
					wroldBossService.bossDead(mapId);
				} else {
					wroldBossService.playerDead(battleId, mapId, combatList);
				}
				removeBattelTeam(battleId);
				Combat combat = combatList.get(0);
				if (null != combat) {
					String useTools = battleTeam.getUseTools();
					useTools = useTools.length() > 1 ? useTools.substring(0, useTools.length() - 1) : useTools;
					GameLogService.dareWordBoss(combat.getId(), combat.getLevel(), battleTeam.getRound(), combat.getTotalHurt(), useTools);
				}
			} else {

				GameOver gameOver = new GameOver();
				gameOver.setBattleId(battleId);
				gameOver.setFirstHurtPlayerId(battleTeam.getFirstHurtPlayerId());
				gameOver.setWinCamp(winCamp);
				gameOver.setPlayerCount(playerCount);
				int i = 0;
				for (Combat combat : trueCombatList) {
					if (gameOver.getWinCamp() == combat.getCamp()) {
						combat.setWin(true);
						int useVigor = battleTeam.getBossRoom().getMapVigor(), vigor = combat.getPlayer().getVigor();
						// 是否足够活力
						if ((vigor - useVigor) < 0) {
							throw new Exception("活力值不足");
						}
						// 扣除活力
						combat.getPlayer().useVigor(useVigor);
						if (battleTeam.getBossRoom().getMapType() == Common.MAP_TYPE_BIG) {
							star[i] = battleTeam.getDifficulty();
						}

					}

					PlayerItemsFromShop item = ServiceManager.getManager().getPlayerItemsFromShopService()
							.uniquePlayerItem(combat.getPlayer().getId(), 5);
					isDoubleExpCard[i] = item != null && item.getPLastTime() > 1 ? 1 : 0;// 双倍经验卡

					playerIds[i] = combat.getId();
					shootRate[i] = (int) ((combat.getHuntTimes() / (float) combat.getShootTimes()) * 100);
					totalHurt[i] = combat.getTotalHurt();
					killCount[i] = combat.getKillCount();
					beKilledCount[i] = combat.getBeKilledCount();
					addExp[i] = combat.getExp();
					Exp[i] = combat.getPlayer().getPlayer().getExp();
					upgradeExp[i] = ServiceManager.getManager().getPlayerService()
							.getUpgradeExp(combat.getLevel(), combat.getPlayer().getPlayer().getZsLevel());
					nextUpgradeExp[i] = ServiceManager.getManager().getPlayerService()
							.getUpgradeExp(combat.getLevel() + 1, combat.getPlayer().getPlayer().getZsLevel());
					i++;
				}
				// 分经验
				Map<String, int[]> m = setPlayerExp(battleId, winCamp);
				gameOver.setPlayerIds(playerIds);
				gameOver.setShootRate(shootRate);
				gameOver.setTotalHurt(totalHurt);
				gameOver.setKillCount(killCount);
				gameOver.setBeKilledCount(beKilledCount);
				gameOver.setAddExp(addExp);
				gameOver.setExp(Exp);
				gameOver.setUpgradeExp(upgradeExp);
				gameOver.setNextUpgradeExp(nextUpgradeExp);
				gameOver.setStar(star);
				gameOver.setIsDoubleExpCard(isDoubleExpCard);
				gameOver.setActivityAddExp(m.get("activityExp"));
				gameOver.setIsMarry(m.get("marryExp"));
				gameOver.setGuildAddExp(m.get("guildSkillsExp"));

				if (winCamp == 0 && battleTeam.getBossRoom().getMapType() == Common.MAP_TYPE_BIG) {
					RewardItemsVo riv;
					List<RewardItemsVo> rivList = ServiceManager.getManager().getBossmapRewardService()
							.getRewardItems(trueCombatList, battleId);
					if (null != rivList && rivList.size() > 17) {
						for (i = 0; i < 18; i++) {
							riv = rivList.get(i);
							egg_playeId[i] = riv.getOwnerId();
							egg_Item_Name[i] = riv.getItemName();
							egg_item_icon[i] = riv.getItemIcon();
							int count = -1 == riv.getDays() ? riv.getCount() : riv.getDays();
							egg_ItemNum[i] = count;
						}
					} else {
						for (i = 0; i < 18; i++) {
							egg_playeId[i] = 0;
							egg_Item_Name[i] = "";
							egg_item_icon[i] = "";
							egg_ItemNum[i] = 0;
						}
						eggCount = 0;
					}
				} else {
					for (i = 0; i < 18; i++) {
						egg_playeId[i] = 0;
						egg_Item_Name[i] = "";
						egg_item_icon[i] = "";
						egg_ItemNum[i] = 0;
					}
					eggCount = 0;
				}
				gameOver.setEggCount(eggCount);
				gameOver.setEgg_item_icon(egg_item_icon);
				gameOver.setEgg_Item_Name(egg_Item_Name);
				gameOver.setEgg_ItemNum(egg_ItemNum);
				gameOver.setEgg_playeId(egg_playeId);
				exitBattle(battleId);
				if (winCamp == 0) {
					// 如果是通过BOSS地图，发送贺喜公告
					if (Common.MAP_TYPE_BIG == battleTeam.getBossRoom().getMapType()) {
						String names = " ";
						for (Combat combat : battleTeam.getTrueCombatList()) {
							if (null != combat && null != combat.getPlayer() && !combat.isEnforceQuit()) {
								names += "[" + combat.getName() + "] ";
							}
						}
						String bbp = TipMessages.BOSSBATTLEPASS;
						bbp = bbp.replace("###", names);
						bbp = bbp.replace("@@@", battleTeam.getDifficultyString());
						bbp = bbp.replace("***", battleTeam.getBossRoom().getRoomName());
						ServiceManager.getManager().getChatService().sendBulletinToWorld(bbp, "", false);
					}
					// 增加进度
					battleTeam.getBossRoom().addPoints();
				}
				for (Combat combat : combatList) {
					if (!combat.isRobot() && !combat.isLost() && null != combat.getPlayer()) {
						gameOver.setPices(getEggPrices(combat.getPlayer()));
						gameOver.setIsBackToRoom(combat.isBackToRoom());
						combat.getPlayer().sendData(gameOver);
					}
				}
			}
		}
		return isOver;
	}

	/**
	 * 战斗退出
	 * 
	 * @param battleId
	 * @throws Exception
	 */
	public void exitBattle(int battleId) {
		try {
			BossBattleTeam battleTeam = battleTeamMap.get(battleId);
			if (null == battleTeam)
				return;
			StringBuffer playerBattleInfo = new StringBuffer();
			boolean isSuccess = false;
			WorldPlayer player;
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isRobot()) {
					player = combat.getPlayer();
					player.setBossmapBattleId(0);
					playerBattleInfo.append(player.getId());
					playerBattleInfo.append("*");
					playerBattleInfo.append(player.getLevel());
					playerBattleInfo.append("*");
					if (!combat.isLost()) {
						int num = 1;
						if (player.hasRing()) {
							num++;
						}
						if (combat.getRivList() != null) {// 玩家命中次数大于0才给以奖励
							for (RewardItemsVo riv : combat.getRivList()) {
								if (riv != null && !riv.isDiamond()) {
									if (riv.getItemId() == Common.GOLDID) {
										int gold = (int) ServiceManager.getManager().getBuffService()
												.getAddition(player, riv.getCount(), Buff.GOLD);
										gold = ServiceManager.getManager().getVersionService().getAddition(gold, 1);
										ServiceManager.getManager().getPlayerService()
												.updatePlayerGold(player, gold, SystemLogService.GSBATTLE, "");
									} else if (riv.getItemId() == Common.DIAMONDID) {// 获得点卷
										ServiceManager
												.getManager()
												.getPlayerService()
												.addTicket(player, combat.getRiv().getCount(), 0, TradeService.ORIGIN_BATT, 0, "", "副本战斗",
														"", "");
									} else {
										if (Common.BADGEID == riv.getItemId()) {
											riv.setCount(ServiceManager.getManager().getVersionService().getAddition(riv.getCount(), 2));
										}
										ServiceManager
												.getManager()
												.getPlayerItemsFromShopService()
												.playerGetItem(player.getId(), riv.getItemId(), -1, riv.getDays(), riv.getCount(), 4, null,
														0, 0, 0);
									}
								}
							}
						}
						if (combat.isWin()) {
							isSuccess = true;
							if (combat.getExp() > 0
									|| (0 == combat.getExp() && player.getLevel() >= Server.config.getMaxLevel(player.getPlayer()
											.getZsLevel()))) {
								ServiceManager.getManager().getPlayerSinConsortiaService().updatePlayerContribute(player, num);
								ServiceManager.getManager().getPlayerItemsFromShopService().updateItmeSkillful(player);
							}
							// 通关副本
							if (Common.MAP_TYPE_BIG == battleTeam.getBossRoom().getMapType() && !combat.isEnforceQuit()) {
								combat.getPlayer().updatePlayerStarInfo(battleTeam.getMapId(), battleTeam.getDifficulty());
								int index = ServiceManager.getManager().getBossRoomService()
										.getPlayerSeat(player.getBossmapRoomId(), player.getId());
								ServiceManager.getManager().getBossRoomService().extRoom(player.getBossmapRoomId(), index, 0);
								combat.setBackToRoom(false);
								// 设置玩家副本进度
								if (combat.getPlayer().getPlayer().getBossmapProgress() == battleTeam.getBossRoom().getBossmapSerial()) {
									combat.getPlayer().getPlayer().setBossmapProgress(battleTeam.getBossRoom().getBossmapSerial() + 1);
								}
								ServiceManager.getManager().getTaskService()
										.tgfb(combat.getPlayer(), battleTeam.getMapId(), battleTeam.getDifficulty());
								ServiceManager.getManager().getTitleService().tgfb(combat.getPlayer(), battleTeam.getMapId());
							}
						} else {

							int index = ServiceManager.getManager().getBossRoomService()
									.getPlayerSeat(player.getBossmapRoomId(), player.getId());
							ServiceManager.getManager().getBossRoomService().extRoom(player.getBossmapRoomId(), index, 0);
							combat.setBackToRoom(false);
							// com.wyd.empire.world.bean.Map map =
							// ServiceManager.getManager().getMapsService().getBossMapById(battleTeam.getBossRoom().getMapId());
							// int useVigor = map.getVitalityExpend();
							// int vigor = player.getVigor();
							// int canPassTimes = map.getPassTimes();
							// boolean unlimited = map.getPassTimes()==-1;
							// PlayerBossmap playerMap =
							// ServiceManager.getManager().getPlayerBossmapService().getPlayerBossMap(player.getId(),
							// map.getId());
							// // 是否足够活力
							// if ((vigor - useVigor) < 0) {
							// int index =
							// ServiceManager.getManager().getBossRoomService().getPlayerSeat(player.getBossmapRoomId(),
							// player.getId());
							// ServiceManager.getManager().getBossRoomService().extRoom(player.getBossmapRoomId(),
							// index, 0);
							// }
							// // 次数上限
							// if(!unlimited){
							// if(playerMap!=null &&
							// playerMap.getPassTimes()>=canPassTimes){
							// int index =
							// ServiceManager.getManager().getBossRoomService().getPlayerSeat(player.getBossmapRoomId(),
							// player.getId());
							// ServiceManager.getManager().getBossRoomService().extRoom(player.getBossmapRoomId(),
							// index, 0);
							// }
							// }
						}
					}
					if (combat.getExp() > 0) {
						int exp = ServiceManager.getManager().getVersionService().getAddition(combat.getExp(), 0);
						ServiceManager.getManager().getPlayerService().updatePlayerEXP(player, exp);
						ServiceManager.getManager().getPlayerPetService()
								.updateExp(player, player.getPlayerPet(), (int) Math.ceil(exp * 0.1));
						playerBattleInfo.append(exp);
						playerBattleInfo.append("*");
					} else {
						int exp = combat.getExp();
						// if(combat.getPlayer().isVip()&&null!=combat.getPlayer().getVipInfo()&&combat.getPlayer().getVipInfo().getLevel()>3){//vip强退不扣经验,2.1改为3级以上不扣经验
						// exp = 0;
						// }
						ServiceManager.getManager().getPlayerService().updatePlayerEXP(player, exp);
						playerBattleInfo.append(exp);
						playerBattleInfo.append("*");
					}
					playerBattleInfo.append(combat.getCamp());
					playerBattleInfo.append("*");
					playerBattleInfo.append(combat.getBeKillRound());
					playerBattleInfo.append("*");
					playerBattleInfo.append(combat.isEnforceQuit());
					playerBattleInfo.append("*");
					playerBattleInfo.append(combat.isLost());
					playerBattleInfo.append("*");
					playerBattleInfo.append(combat.getTotalHurt());
					playerBattleInfo.append("*");
					playerBattleInfo.append(combat.isSuicide());
					playerBattleInfo.append("*");
					playerBattleInfo.append(combat.getShootTimes());
					playerBattleInfo.append("*");
					playerBattleInfo.append(combat.getHuntTimes());
					playerBattleInfo.append(",");
				}
			}
			String useTools = battleTeam.getUseTools();
			useTools = useTools.length() > 1 ? useTools.substring(0, useTools.length() - 1) : useTools;
			if (playerBattleInfo.length() > 0)
				playerBattleInfo.deleteCharAt(playerBattleInfo.length() - 1);
			int bossHP = 0;
			Vector<Combat> listGuai = battleTeam.getCombatGuaiList();
			if (null != listGuai) {
				for (Combat guai : listGuai) {
					if (guai.getGuai().getGuai().getType() == Combat.GUAI_TYPE_BOSS) {
						bossHP = guai.getHp() > 0 ? guai.getHp() : 0;
						break;
					}
				}
			}
			GameLogService.duplicateBattle(playerBattleInfo.toString(), battleTeam.getMapId(), battleTeam.getDifficulty(),
					battleTeam.getRound(), bossHP, isSuccess, useTools);
		} catch (Exception e) {
			log.error(e, e);
		}
		removeBattelTeam(battleId);
	}

	/**
	 * 玩家掉线
	 * 
	 * @param battleId
	 * @param playerId
	 * @throws Exception
	 */
	public void playerLose(int battleId, int playerId) throws Exception {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		if (null == battleTeam) {
			return;
		}
		synchronized (battleTeam) {
			Combat combat = battleTeam.getCombatMap().get(playerId);
			if (null != combat.getPlayer() && !combat.isRobot()) {
				combat.getPlayer().setBossmapBattleId(0);
			}
			combat.setHp(0);
			combat.setLost(true);
			combat.setDead(true);
			IWorldBossService wroldBossService = ServiceManager.getManager().getWorldBossService();
			int mapId = battleTeam.getMapId();
			// 是否世界BOSS
			if (wroldBossService.isWorldBossBattle(mapId)) {
				// 设置CDTIME
				wroldBossService.setCDTime(combat.getId(), mapId);
			}
			if (playerId == battleTeam.getAicounter()) {
				battleTeam.setAicounter(0);
				sendAIControlCommon(battleId);
			}
			PlayerLose playerLose = new PlayerLose();
			playerLose.setBattleId(battleId);
			playerLose.setPlayerId(playerId);
			for (Combat cb : battleTeam.getCombatList()) {
				if (!cb.isLost() && !cb.isRobot() && null != cb.getPlayer()) {
					cb.getPlayer().sendData(playerLose);
				}
			}

			// 如果玩家强退
			if (combat.isEnforceQuit()) {
				battleTeam.getTrueCombatList().remove(combat);
			}

			if (0 != battleTeam.getStat()) {
				if (!gameOver(battleId)) {
					int actionIndex = battleTeam.getActionIndex() - 1;
					actionIndex = actionIndex < 0 ? battleTeam.getCurRoundSequence().size() - 1 : actionIndex;
					Vector<Integer> curRoundSequence = battleTeam.getCurRoundSequence();
					int sequenceSize = curRoundSequence == null ? 0 : curRoundSequence.size();
					if (actionIndex < sequenceSize) {
						if (playerId == curRoundSequence.get(actionIndex).intValue()) {
							Pass pass = new Pass();
							pass.setBattleId(battleId);
							pass.setPlayerOrGuai(0);
							pass.setCurrentId(playerId);
							PassHandler passHandler = new PassHandler();
							passHandler.handle(pass);
						}
					}
					EndCurRound endCurRound = new EndCurRound();
					endCurRound.setBattleId(battleId);
					endCurRound.setPlayerOrGuai(0);
					endCurRound.setCurrentId(playerId);
					EndCurRoundHandler endCurRoundHandler = new EndCurRoundHandler();
					endCurRoundHandler.handle(endCurRound);
				}
			} else {
				if (battleTeam.isCampAllLost(0) && battleTeam.isCampAllLost(1)) {
					gameOver(battleId);
				} else {
					FinishLoading finishLoading = new FinishLoading();
					finishLoading.setBattleId(battleId);
					finishLoading.setPlayerId(playerId);
					FinishLoadingHandler finishLoadingHandler = new FinishLoadingHandler();
					finishLoadingHandler.handle(finishLoading);
				}
			}
		}
	}

	/**
	 * 发送ai玩家信息
	 * 
	 * @param battleId
	 * @throws Exception
	 */
	public void sendAIControlCommon(int battleId) throws Exception {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		if (battleTeam == null)
			return;
		synchronized (battleTeam) {
			Combat combat = null;
			if (0 != battleTeam.getAicounter()) {
				combat = battleTeam.getCombatMap().get(battleTeam.getAicounter());
			}
			List<Integer> playerId = new ArrayList<Integer>();
			List<Integer> aiCtrlId = new ArrayList<Integer>();
			List<Integer> guaiId = new ArrayList<Integer>();
			List<Integer> guaiAiCtrlId = new ArrayList<Integer>();
			for (Combat cb : battleTeam.getCombatList()) {
				if (null != cb.getPlayer() || null != cb.getGuai()) {
					if (cb.isRobot()) {
						playerId.add(cb.getId());
						aiCtrlId.add(cb.getAiCtrlId());
					} else if (null == combat && !cb.isLost()) {
						combat = cb;
					}
				}
			}
			for (Combat gv : battleTeam.getCombatGuaiList()) {
				if (null != gv.getGuai()) {
					guaiId.add(gv.getBattleId());
					guaiAiCtrlId.add(gv.getAiCtrlId());
				}
			}
			if (null != combat) {
				AIControlCommon aIControlCommon = new AIControlCommon();
				aIControlCommon.setBattleId(battleTeam.getBattleId());
				aIControlCommon.setIdcount(playerId.size());
				aIControlCommon.setPlayerIds(ArrayUtils.toPrimitive(playerId.toArray(new Integer[0])));
				aIControlCommon.setAiCtrlId(ArrayUtils.toPrimitive(aiCtrlId.toArray(new Integer[0])));
				aIControlCommon.setGuaiIdCount(guaiId.size());
				aIControlCommon.setGuaiAiCtrlId(ArrayUtils.toPrimitive(guaiAiCtrlId.toArray(new Integer[0])));
				aIControlCommon.setGuaiBattleIds(ArrayUtils.toPrimitive(guaiId.toArray(new Integer[0])));
				battleTeam.setAicounter(combat.getId());
				combat.getPlayer().sendData(aIControlCommon);
				// System.out.println("battleId:" + battleId + "-------counter:"
				// + battleTeam.getAicounter());
			} else {
				for (Combat cb : battleTeam.getCombatList()) {
					if (cb.isRobot()) {
						cb.setHp(0);
						cb.setLost(true);
					}
				}
			}
		}
	}

	/**
	 * 角色已完成当前动作，可以开始新的动作
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void playerRun(int battleId, int playerOrGuai, int currentId) {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			boolean c = true;
			Combat cb = battleTeam.getCombatMap().get(currentId);
			cb.setCurRound(true);
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && !combat.isCurRound()) {
					c = false;
					break;
				}
			}
			battleTeam.setCurRound(c);
		}
	}

	/**
	 * 计算玩家本次战斗获得的经验数
	 * 
	 * @param battleId
	 * @return
	 */
	@SuppressWarnings("unused")
	public Map<String, int[]> setPlayerExp(int battleId, int winCamp) {

		try {
			BossBattleTeam battleTeam = battleTeamMap.get(battleId);
			int[] guildSkillsExp = new int[battleTeam.getCombatList().size()]; // 公会技能加成
			int[] marryExp = new int[battleTeam.getCombatList().size()]; // 结婚加成
			int[] activityExp = new int[battleTeam.getCombatList().size()];// 是否有活动经验
			if (null == battleTeam)
				return null;
			guildSkillsExp[0] = 0;
			marryExp[0] = 0;
			activityExp[0] = 0;
			int loseShootHurt = 0;
			MarryRecord mr;
			int coupleId = 0;
			if (battleTeam.getCombatList().size() > 0) {
				int pcount = 0;
				int bossMapExp = 0;
				int pLevel = 0, pLevel0 = 0, pLevel1 = 0, gold = 0;
				List<RewardInfo> mapRewards = ServiceUtils.getRewardInfo(battleTeam.getBossRoom().getReward(), 2);
				for (RewardInfo reward : mapRewards) {
					switch (reward.getItemId()) {
						case Common.GOLDID :
							gold = reward.getCount();
							break;
						case Common.EXPID :
							bossMapExp = reward.getCount();
							break;
					}
				}
				for (Combat combat : battleTeam.getCombatList()) {
					if (combat.isRobot())
						break;
					pcount++;
					if (combat.getCamp() != winCamp) {
						loseShootHurt += combat.getTotalHurt();
					}
					if (0 == combat.getCamp()) {
						pLevel0 += combat.getLevel();
					} else {
						pLevel1 += combat.getLevel();
					}
				}
				if (0 == pcount)
					return null;
				pLevel0 = pLevel0 / pcount;
				pLevel1 = pLevel1 / pcount;

				int i = 0;
				for (Combat combat : battleTeam.getCombatList()) {
					if (combat.isRobot())
						break;

					mr = ServiceManager.getManager().getMarryService().getSingleMarryRecordByPlayerId(combat.getSex(), combat.getId(), 1);
					if (null != mr && mr.getStatusMode() > 1) {
						if (combat.getSex() == 0) {
							coupleId = mr.getWomanId();
						} else {
							coupleId = mr.getManId();
						}
					}
					if (0 == combat.getCamp()) {
						pLevel = pLevel1;
					} else {
						pLevel = pLevel0;
					}
					if (combat.isEnforceQuit()) {// 强退
						if (combat.getTotalHurt() > 0) {
							combat.setExp(-10);
						} else {
							combat.setExp(-50);
						}
					} else if (combat.isLost()) {// 掉线
						if (combat.getTotalHurt() > 0) {
							combat.setExp(-5);
						} else {
							combat.setExp(-10);
						}
					} else {
						int exp = 0;
						if (combat.getCamp() == winCamp) {// 胜利

							exp = bossMapExp;

							exp = ServiceManager.getManager().getBuffService().getExp(combat.getPlayer(), exp);
							// 公会技能加成
							int addition = (int) Math.ceil(ServiceManager.getManager().getBuffService()
									.getAddition(combat.getPlayer(), exp, Buff.CEXP));
							guildSkillsExp[i] = addition - exp;
							exp = addition;

							// 添加结婚经验加成
							if (coupleId != 0) {
								for (Combat c : battleTeam.getCombatList()) {
									if (c.getId() == coupleId) {
										exp = (int) Math.ceil(ServiceManager.getManager().getBuffService()
												.getAddition(combat.getPlayer(), exp, Buff.MEXP));
									}
								}
								marryExp[i] = addition - exp;
							} else {
								marryExp[i] = 0;
							}

							combat.setExp(exp);
							if (gold > 0) {
								ServiceManager.getManager().getPlayerService()
										.updatePlayerGold(combat.getPlayer(), gold, "地图掉落", "-- " + " --");
							}

						}
					}
					if (combat.getExp() > 0 && combat.getLevel() == Server.config.getMaxLevel(combat.getPlayer().getPlayer().getZsLevel())) {
						int sjexp = ServiceManager.getManager().getPlayerService()
								.getUpgradeExp(combat.getLevel(), combat.getPlayer().getPlayer().getZsLevel());
						int dqexp = combat.getExp() + combat.getExp();
						if (sjexp <= dqexp) {
							if ((sjexp - 1 - combat.getExp()) < 0) {
								combat.setExp(0);
							} else {
								combat.setExp(sjexp - 1 - combat.getExp());
							}
						}
					}
					i++;
				}
			} else {
				int i = 0;
				for (Combat combat : battleTeam.getCombatList()) {
					if (combat.isRobot())
						break;
					if (combat.isEnforceQuit()) {
						if (combat.getTotalHurt() > 0) {
							combat.setExp(-10);
						} else {
							combat.setExp(-50);
						}
					} else if (combat.isLost()) {
						if (combat.getTotalHurt() > 0) {
							combat.setExp(-5);
						} else {
							combat.setExp(-10);
						}
					} else {
						if (loseShootHurt > 0) {
							if (combat.getCamp() == winCamp) {
								if (combat.getTotalHurt() > 0) {
									// 添加公会技能加成

									int exp = (int) Math.ceil(ServiceManager.getManager().getBuffService()
											.getAddition(combat.getPlayer(), 5, Buff.CEXP));
									int addition = ServiceManager.getManager().getBuffService().getExp(combat.getPlayer(), exp);
									guildSkillsExp[i] = addition - exp;
									combat.setExp(addition);
									// 添加结婚经验加成
									if (coupleId != 0) {
										for (Combat c : battleTeam.getCombatList()) {
											if (c.getId() == coupleId) {
												combat.setExp((int) Math.ceil(ServiceManager.getManager().getBuffService()
														.getAddition(combat.getPlayer(), combat.getExp(), Buff.MEXP)));
											}
										}
										marryExp[i] = addition - combat.getExp();
									} else {
										marryExp[i] = 0;
									}
								} else {
									// 添加公会技能加成
									int exp = (int) Math.ceil(ServiceManager.getManager().getBuffService()
											.getAddition(combat.getPlayer(), 2, Buff.CEXP));
									combat.setExp(ServiceManager.getManager().getBuffService().getExp(combat.getPlayer(), exp));
									guildSkillsExp[i] = exp;
									// 添加结婚经验加成
									if (coupleId != 0) {
										exp = combat.getExp();
										for (Combat c : battleTeam.getCombatList()) {
											if (c.getId() == coupleId) {
												combat.setExp((int) ServiceManager.getManager().getBuffService()
														.getAddition(combat.getPlayer(), combat.getExp(), Buff.MEXP));
											}
										}
										marryExp[i] = combat.getExp() - exp;
									} else {
										marryExp[i] = 0;
									}
								}
							} else {
								guildSkillsExp[i] = 0;
								marryExp[i] = 0;
							}
						}
					}
					i++;
				}
			}
			// 活动
			DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
			DailyRewardVo dailyRewardVo = dailyActivityService.getBossmapBattleReward();
			int i = 0;
			for (Combat combat : battleTeam.getCombatList()) {
				combat.setExp(dailyActivityService.getRewardedVal(combat.getExp(), dailyRewardVo.getExp()));
				activityExp[i] = dailyRewardVo.getExp() > 1 ? 1 : 0;
				i++;
			}

			HashMap<String, int[]> m = new HashMap<String, int[]>();
			m.put("guildSkillsExp", guildSkillsExp);
			m.put("marryExp", marryExp);
			m.put("activityExp", activityExp);
			return m;
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;

	}

	/**
	 * 获得玩家和怪物初始化坐标
	 * 
	 * @param posStr
	 * @param isplayer
	 *            true表示玩家，false表示怪
	 * @return
	 */
	public List<Map<String, Integer>> getplayerPos(String posStr, boolean isplayer) {
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		String[] posOut = posStr.split("\\),\\(");
		String[] posIn;
		String posInStr = "";
		for (int i = 0; i < posOut.length; i++) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			posInStr = posOut[i];
			if (i == 0) {
				posInStr = posInStr.substring(1);
			} else if (i == posOut.length - 1) {
				posInStr = posInStr.substring(0, posOut[i].length() - 1);
			}
			posIn = posInStr.split(",");
			// 玩家坐标
			if (isplayer && "-1".equals(posIn[0])) {
				map.put("id", -1);
				map.put("camp", 0);
				map.put("posX", Integer.parseInt(posIn[2]));
				map.put("posY", Integer.parseInt(posIn[3]));
				list.add(map);
			}
			// 怪物坐标
			if (!isplayer && !"-1".equals(posIn[0])) {
				map.put("id", Integer.parseInt(posIn[0]));
				map.put("camp", Integer.parseInt(posIn[1]));
				map.put("posX", Integer.parseInt(posIn[2]));
				map.put("posY", Integer.parseInt(posIn[3].replace(")", "")));
				list.add(map);
			}
		}
		return list;
	}

	public List<Combat> setGuaiToCombat(int battleId, String guaiList) {
		BossBattleTeam bossBattleTeam = getBattleTeam(battleId);
		synchronized (bossBattleTeam) {
			List<Combat> pGuaiList = new ArrayList<Combat>();// 返回给客户端的怪列表
			IGuaiService iGuaiService = ServiceManager.getManager().getGuaiService();
			// 所有要能被招唤出来的怪的数据库列表id
			List<Integer> buildGuaiList = new ArrayList<Integer>();
			// 获取真角色中playerId最大者,用以作为假角色的id
			int playeridMax = -1;
			for (Combat combat : bossBattleTeam.getCombatList()) {
				if (!combat.isGuaiPlayer() && combat.getId() > playeridMax) {
					playeridMax = combat.getId();
				}
			}
			playeridMax++;// 自增1,由这个id开始
			List<Map<String, Integer>> bossPosList = getplayerPos(guaiList, false);
			GuaiPlayer guai;
			Combat combatGuai;
			for (Map<String, Integer> m : bossPosList) {
				guai = iGuaiService.getGuaiById(bossBattleTeam.getDifficulty(), m.get("id").intValue());
				// 有穿着的怪当做人来做
				if (guai.getGuai().getType() == Combat.GUAI_TYPE_LIKE_PLAYER) {
					Combat combat = new Combat();
					guai.getGuai().setGuaiId(playeridMax++);
					combat.setGuai(guai);
					combat.setCamp(m.get("camp"));
					combat.setRobot(true);
					combat.setGuaiPlayer(true);
					combat.setGuaiId(m.get("id"));
					combat.setX(m.get("posX"));
					combat.setY(m.get("posY"));
					combat.setHp(guai.getMaxHP());
					combat.setPf(guai.getMaxPF(), bossBattleTeam.getMapId(), 6, null);
					combat.setAiCtrlId(ServiceUtils.getRandomNum(0, 3));
					bossBattleTeam.enterPlayer(combat);
				} else {// 没衣服的怪作为怪
					combatGuai = new Combat();
					combatGuai.setBattleId(bossBattleTeam.getGuaiIdDistribution());
					combatGuai.setGuai(guai);
					combatGuai.setCamp(m.get("camp"));
					combatGuai.setRobot(true);
					if (guai.getGuai().getCould_build_guai() == 1) {
						combatGuai.setCouldBuildGuai(true);
						combatGuai.setBuildGuaiList(getGuaiBuildList(guai.getGuai().getBuild_guai_id()));
						for (Integer i : combatGuai.getBuildGuaiList()) {
							GuaiPlayer tempGuai = iGuaiService.getGuaiById(bossBattleTeam.getDifficulty(), i.intValue());
							if (tempGuai.getGuai().getType() == Combat.GUAI_TYPE_BOSS && combatGuai.getChangeToGuaiId() == -1) {
								combatGuai.setChangeToGuaiId(tempGuai.getGuai().getGuaiId());
							}
							if (!buildGuaiList.contains(i)) {
								buildGuaiList.add(i);// 加入生成怪列表
							}
						}
					}
					combatGuai.setX(m.get("posX"));
					combatGuai.setY(m.get("posY"));
					combatGuai.setHp(guai.getMaxHP());
					combatGuai.setPf(guai.getMaxPF(), bossBattleTeam.getMapId(), 6, null);
					combatGuai.setAngryValue(0);
					combatGuai.setAiCtrlId(ServiceUtils.getRandomNum(0, 3));
					bossBattleTeam.enterGuai(combatGuai);
					pGuaiList.add(combatGuai);
				}
			}
			List<Integer> beBuildedGuaiList = new ArrayList<Integer>();
			int bossGuai = 10; // 一场战斗最多只能招出10种boss怪,这里主要是防止数据库设置错误而无限循环
			int monsterGuai = 30; // 一场战斗最多只能招出30种小怪,这里主要是防止数据库设置错误而无限循环
			while (buildGuaiList.size() > 0 && (bossGuai > 0 || monsterGuai > 0)) {
				Integer guaiId = buildGuaiList.get(0);
				buildGuaiList.remove(0);
				beBuildedGuaiList.add(guaiId);
				guai = iGuaiService.getGuaiById(bossBattleTeam.getDifficulty(), guaiId.intValue());
				if (bossGuai > 0 && guai.getGuai().getType() == Combat.GUAI_TYPE_BOSS) {
					bossGuai--;
				} else if (monsterGuai > 0) {
					monsterGuai--;
				} else {
					continue;
				}
				combatGuai = new Combat();
				combatGuai.setBattleId(-1);
				combatGuai.setGuai(guai);
				combatGuai.setCamp(-1);
				combatGuai.setRobot(true);
				if (guai.getGuai().getCould_build_guai() == 1) {
					combatGuai.setCouldBuildGuai(true);
					combatGuai.setBuildGuaiList(getGuaiBuildList(guai.getGuai().getBuild_guai_id()));
					for (Integer i : combatGuai.getBuildGuaiList()) {
						GuaiPlayer tempGuai = iGuaiService.getGuaiById(bossBattleTeam.getDifficulty(), i.intValue());
						if (tempGuai.getGuai().getType() == Combat.GUAI_TYPE_BOSS && combatGuai.getChangeToGuaiId() == -1) {
							combatGuai.setChangeToGuaiId(tempGuai.getGuai().getGuaiId());
						}
						// 保证不重复,
						if (!buildGuaiList.contains(i) && !beBuildedGuaiList.contains(i)) {
							buildGuaiList.add(i);// 加入生成怪列表
						}
					}
				}
				combatGuai.setX(0);
				combatGuai.setY(0);
				combatGuai.setHp(guai.getMaxHP());
				combatGuai.setPf(guai.getMaxPF(), bossBattleTeam.getMapId(), 6, null);
				combatGuai.setAngryValue(guai.getMaxSP());
				pGuaiList.add(combatGuai);
			}
			return pGuaiList;
		}
	}

	public ArrayList<Integer> getGuaiBuildList(String guaiListStr) {
		ArrayList<Integer> guaiList = new ArrayList<Integer>();
		String[] strList = guaiListStr.split(",");
		for (String sub : strList) {
			guaiList.add(Integer.parseInt(sub));
		}
		return guaiList;
	}

	public Map<Integer, PlayerRewardVo> getPlayerReward() {
		return playerReward;
	}

	// 建立并初始化数组
	private String[] initStrings(int size) {
		String[] strs = new String[size];
		for (int i = 0; i < size; i++) {
			strs[i] = "";
		}
		return strs;
	}

	public void removeBattelTeam(int battleId) {
		BossBattleTeam battleTeam = battleTeamMap.remove(battleId);
		battleTeamList.remove(battleTeam);
	}

	/**
	 * 世界BOSS掉线处理
	 * 
	 * @param battleId
	 * @param playerId
	 * @throws Exception
	 */
	public void worldBossLose(int battleId, int playerId) throws Exception {
		BossBattleTeam battleTeam = battleTeamMap.get(battleId);
		if (null == battleTeam) {
			return;
		}
		synchronized (battleTeam) {
			int mapId = battleTeam.getMapId();
			IWorldBossService wroldBossService = ServiceManager.getManager().getWorldBossService();
			// 是否世界BOSS
			if (wroldBossService.isWorldBossBattle(mapId)) {
				// System.out.println("worldboss battleId:" + battleId +
				// "---playerLose:" + playerId);
				Combat combat = battleTeam.getCombatMap().get(playerId);
				// 设置CDTIME
				wroldBossService.setCDTime(combat.getId(), mapId);
				combat.setHp(0);
				combat.setLost(true);
				combat.setDead(true);
				removeBattelTeam(battleId);
			}
		}
	}

	/**
	 * 副本砸蛋价格
	 * 
	 * @param player
	 * @return
	 */
	private int[] getEggPrices(WorldPlayer player) {
		int[] prices = ServiceManager.getManager().getVersionService().getVersion().priceArray();
		int[] finalPrices = new int[prices.length];
		VipRate vipRate = null;
		if (player.isVip()) {
			vipRate = ServiceManager.getManager().getPlayerItemsFromShopService().getVipRateById(player.getPlayer().getVipLevel());
		}
		for (int i = 0; i < prices.length; i++) {
			finalPrices[i] = getEggPrice(player, vipRate, prices[i]);
		}
		return finalPrices;
	}

	/**
	 * VIP打折，活动打折
	 * 
	 * @return
	 */
	private int getEggPrice(WorldPlayer player, VipRate vr, int price) {
		if (null != vr) {
			price = price * vr.getBossRate() / 100;
		}
		DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
		DailyRewardVo dailyRewardVo = dailyActivityService.getSmashEggReward();
		return dailyActivityService.getRewardedVal(price, dailyRewardVo.getSubTicket());
	}
}