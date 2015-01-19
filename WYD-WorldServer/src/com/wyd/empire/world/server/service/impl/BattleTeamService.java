package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.battle.AIControlCommon;
import com.wyd.empire.protocol.data.battle.ChangeAngryValue;
import com.wyd.empire.protocol.data.battle.EndCurRound;
import com.wyd.empire.protocol.data.battle.FinishLoading;
import com.wyd.empire.protocol.data.battle.FrozenOver;
import com.wyd.empire.protocol.data.battle.GameOver;
import com.wyd.empire.protocol.data.battle.Pass;
import com.wyd.empire.protocol.data.battle.PlayerLose;
import com.wyd.empire.protocol.data.room.MakePairOk;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.CombatComparator;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.RankRecord;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.handler.battle.EndCurRoundHandler;
import com.wyd.empire.world.server.handler.battle.FinishLoadingHandler;
import com.wyd.empire.world.server.handler.battle.PassHandler;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.base.IPlayerSinConsortiaService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 类<code>BattleTeamService</code> 战斗组管理服务
 * 
 * @author Administrator
 */
public class BattleTeamService implements Runnable {
	private Logger log = Logger.getLogger(BattleTeamService.class);
	private Logger rankLog = Logger.getLogger("rankLog");
	/** 自动断线时间 */
	public static final int OUT_TIME = 60000;
	/** 休眠时间 */
	private static final int SLEEP_TIME = 10000;
	/** 战斗组ID号与战斗级对象对应哈希表 */
	private Map<Integer, BattleTeam> battleTeamMap;
	private List<BattleTeam> battleTeamList;

	/**
	 * 无参构造函数，启动服务
	 */
	public BattleTeamService() {
		battleTeamMap = new ConcurrentHashMap<Integer, BattleTeam>();
		battleTeamList = new Vector<BattleTeam>();
	}

	/**
	 * 启动服务
	 */
	public void start() {
		Thread t = new Thread(this);
		t.setName("BattleTeamService-Thread");
		t.start();
	}

	@Override
	public void run() {
		RoomService roomService = ServiceManager.getManager().getRoomService();
		while (true) {
			try {
				Thread.sleep(SLEEP_TIME);
				long nowTime = System.currentTimeMillis();
				BattleTeam battleTeam;
				for (int i = battleTeamList.size() - 1; i >= 0; i--) {
					battleTeam = battleTeamList.get(i);
					ServiceManager.getManager().getSimpleThreadPool().execute(createTask(nowTime, battleTeam, roomService));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Runnable createTask(long nowTime, BattleTeam battleTeam, RoomService roomService) {
		return new BattleThread(nowTime, battleTeam, roomService);
	}

	public class BattleThread implements Runnable {
		private long nowTime;
		private BattleTeam battleTeam;
		private RoomService roomService;

		public BattleThread(long nowTime, BattleTeam battleTeam, RoomService roomService) {
			this.nowTime = nowTime;
			this.battleTeam = battleTeam;
			this.roomService = roomService;
		}

		@Override
		public void run() {
			try {
				for (Combat combat : battleTeam.getCombatList()) {
					WorldPlayer player = combat.getPlayer();
					if (null != player && !combat.isRobot() && !combat.isLost()) {
						if ((nowTime - player.getActionTime()) > OUT_TIME) {
							int index = roomService.getPlayerSeat(player.getRoomId(), player.getId());
							roomService.exRoom(player.getRoomId(), index, 0);
							index = ServiceManager.getManager().getChallengeService().getPlayerSeat(player.getRoomId(), player.getId());
							ServiceManager.getManager().getChallengeService().exRoom(player.getRoomId(), index);
							playerLose(battleTeam.getBattleId(), player.getPlayer().getId());
							PlayerLose playerLose = new PlayerLose();
							playerLose.setBattleId(battleTeam.getBattleId());
							playerLose.setCurrentPlayerId(player.getId());
							player.sendData(playerLose);
							StringBuffer buf = new StringBuffer();
							buf.append("玩家超过");
							buf.append(OUT_TIME);
							buf.append("秒没有响应强制退出对战。");
							buf.append("---战斗组:");
							buf.append(battleTeam.getBattleId());
							player.writeLog(buf);
						}
					}
				}
				if (null != battleTeam) {
					gameOver(battleTeam, 0);
				}
				// if (null != battleTeam) {
				// battleTeam.sendEmptMessage(thh);
				// }
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
	public int createBattleTeam(int startMode) {
		BattleTeam battleTeam = new BattleTeam();
		// System.out.println("CreateBattleTeam------------" +
		// battleTeam.getBattleId());
		battleTeam.setStartTime(System.currentTimeMillis());
		battleTeamMap.put(battleTeam.getBattleId(), battleTeam);
		battleTeamList.add(battleTeam);
		return battleTeam.getBattleId();
	}

	/**
	 * 加入战斗组
	 * 
	 * @param battleId
	 * @param player
	 * @param camp
	 *            玩家阵营
	 * @param isRobot
	 *            是否机器人
	 */
	public void enBattleTeam(int battleId, WorldPlayer player, int camp, boolean isRobot) {
		BattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			Combat combat = new Combat();
			combat.setPlayer(player);
			combat.setCamp(camp);
			combat.setRobot(isRobot);
			combat.setHp(player.getMaxHP());
			combat.setPf(player.getMaxPF(), battleTeam.getMapId(), battleTeam.getBattleMode(), player);
			player.setActionTime(System.currentTimeMillis());
			combat.setAiCtrlId(ServiceUtils.getRandomNum(0, 3));
			battleTeam.enter(combat);
			if (!isRobot)
				player.setBattleId(battleId);
		}
	}

	/**
	 * 通知战斗组成员开始战斗
	 * 
	 * @param battleId
	 * @param room
	 * @param fightWithAI
	 *            是否与机器人对战
	 */
	public void startBattle(int battleId, Room room, int fightWithAI) {
		BattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			battleTeam.setBattleMode(room.getBattleMode());
			int pnm = 0;
			if (1 == room.getStartMode()) {
				pnm = room.getPlayerNum();
			} else {
				pnm = room.getPlayerNum() / 2;
			}
			battleTeam.setPlayerNumMode(pnm);
			battleTeam.setTogetherType(room.getStartMode());
			battleTeam.setFightWithAI(fightWithAI);
			MakePairOk makePairOk = new MakePairOk();
			int playerCount = battleTeam.getCombatList().size();
			int[] playerId = new int[playerCount];
			int[] roomId = new int[playerCount];
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
			int[] crit = new int[playerCount];
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
			String[] petEffect = new String[playerCount];
			String[] serverName = new String[playerCount];
			int[] petVersion = new int[playerCount];

			int[] force = new int[playerCount];
			int[] armor = new int[playerCount];
			int[] fighting = new int[playerCount];
			int[] winRate = new int[playerCount];
			int[] constitution = new int[playerCount];
			int[] agility = new int[playerCount];
			int[] lucky = new int[playerCount];

			Combat combat;
			int z = 0;
			MarryRecord mr;
			int coupleId = 0;
			for (int i = 0; i < battleTeam.getCombatList().size(); i++) {

				combat = battleTeam.getCombatList().get(i);
				combat.setTiredValue(combat.getLevel());
				mr = ServiceManager.getManager().getMarryService().getSingleMarryRecordByPlayerId(combat.getSex(), combat.getId(), 1);
				if (null != mr && mr.getStatusMode() > 1) {
					if (combat.getSex() == 0) {
						coupleId = mr.getWomanId();
					} else {
						coupleId = mr.getManId();
					}
				}
				writeLog("battleId:" + battleId + "---player:" + combat.getId() + "---Camp:" + combat.getCamp());
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
				serverName[i] = Server.config.getServerName();
				playerId[i] = combat.getId();
				zsleve[i] = combat.getPlayer().getPlayer().getZsLevel();
				skillful[i] = combat.getProficiency();
				roomId[i] = combat.getPlayer().getRoomId();
				playerName[i] = combat.getName();
				playerLevel[i] = combat.getLevel();
				boyOrGirl[i] = combat.getSex();
				suit_head[i] = combat.getPlayer().getSuit_head();
				suit_face[i] = combat.getPlayer().getSuit_face();
				suit_body[i] = combat.getPlayer().getSuit_body();
				suit_weapon[i] = combat.getPlayer().getSuit_weapon();
				weapon_type[i] = combat.getPlayer().getWeapon_type();
				suit_wing[i] = combat.getPlayer().getSuit_wing();
				player_title[i] = combat.getPlayer().getPlayerTitle();
				player_community[i] = combat.getPlayer().getGuildName();
				camp[i] = combat.getCamp();
				if (room.isNewPlayerRoom() && combat.isRobot()) {
					maxHP[i] = 700;
					combat.setHp(700);
				} else {
					maxHP[i] = combat.getPlayer().getMaxHP();
				}
				combat.setPlayerMaxHP(maxHP[i]);
				maxPF[i] = combat.getMaxPF();
				maxSP[i] = combat.getMaxSP();
				attack[i] = combat.getPlayer().getAttack();
				injuryFree[i] = combat.getInjuryFree();
				wreckDefense[i] = combat.getWreckDefense();
				reduceCrit[i] = combat.getReduceCrit();
				reduceBury[i] = combat.getReduceBury();
				force[i] = combat.getPlayer().getForce();
				armor[i] = combat.getPlayer().getArmor();
				constitution[i] = combat.getPlayer().getPhysique();
				agility[i] = combat.getPlayer().getAgility();
				lucky[i] = combat.getPlayer().getLuck();
				fighting[i] = combat.getPlayer().getFighting();
				winRate[i] = combat.getPlayer().getWinRate();
				if (coupleId != 0) {
					for (Combat c : battleTeam.getCombatList()) {
						if (c.getId() == coupleId) {
							attack[i] = (int) ServiceManager.getManager().getBuffService()
									.getAddition(combat.getPlayer(), combat.getPlayer().getAttack(), Buff.MHURT);
						}
					}
				}
				combat.setPlayerAttack(attack[i]);
				bigSkillAttack[i] = combat.getBigSkillAttack();
				crit[i] = combat.getCrit();
				defence[i] = combat.getDefend();
				bigSkillType[i] = combat.getBigSkillType();
				explodeRadius[i] = combat.getExplodeRadius();
				if (room.getBattleMode() != 4) {
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
							item_id[z] = (!combat.getPlayer().isVip() && y == 3) ? -1 : 0;
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
						item_used[z] = combat.getPlayer().getItemDjused()[y];
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
							item_id[z] = (!combat.getPlayer().isVip() && y == 3) ? -1 : 0;
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
				} else {
					Map<String, Tools> map = getRandomTools();
					Tools jntools;
					Tools djtools;
					for (int k = 0; k < 4; k++) {
						jntools = map.get("jn" + k);
						item_used[8 * i + k] = 1;
						item_id[8 * i + k] = jntools.getId();
						item_img[8 * i + k] = jntools.getIcon();
						item_name[8 * i + k] = jntools.getName();
						item_desc[8 * i + k] = jntools.getDesc();
						item_type[8 * i + k] = jntools.getType();
						item_subType[8 * i + k] = jntools.getSubtype();
						item_param1[8 * i + k] = jntools.getParam1();
						item_param2[8 * i + k] = jntools.getParam2();
						item_ConsumePower[8 * i + k] = jntools.getConsumePower();
						specialAttackType[8 * i + k] = jntools.getSpecialAttackType();
						specialAttackParam[8 * i + k] = jntools.getSpecialAttackParam();
						djtools = map.get("dj" + k);
						item_used[8 * i + 4 + k] = 1;
						item_id[8 * i + 4 + k] = djtools.getId();
						item_img[8 * i + 4 + k] = djtools.getIcon();
						item_name[8 * i + 4 + k] = djtools.getName();
						item_desc[8 * i + 4 + k] = djtools.getDesc();
						item_type[8 * i + 4 + k] = djtools.getType();
						item_subType[8 * i + 4 + k] = djtools.getSubtype();
						item_param1[8 * i + 4 + k] = djtools.getParam1();
						item_param2[8 * i + 4 + k] = djtools.getParam2();
						item_ConsumePower[8 * i + 4 + k] = djtools.getConsumePower();
						specialAttackType[8 * i + 4 + k] = djtools.getSpecialAttackType();
						specialAttackParam[8 * i + 4 + k] = djtools.getSpecialAttackParam();
					}
				}
			}
			makePairOk.setBattleId(battleId);
			makePairOk.setBattleMode(room.getBattleMode());
			if (room.getMapId() == ServiceManager.getManager().getMapsService().getRandomId()) {
				com.wyd.empire.world.bean.Map map = ServiceManager.getManager().getMapsService().getRandomMap(room.getRoomChannel());
				makePairOk.setBattleMap(map.getAnimationIndexCode());
				battleTeam.setMapId(map.getId());
			} else {
				com.wyd.empire.world.bean.Map map = ServiceManager.getManager().getMapsService().getMapById(room.getMapId());
				makePairOk.setBattleMap(map.getAnimationIndexCode());
				battleTeam.setMapId(map.getId());
			}
			makePairOk.setPlayerCount(playerCount);
			makePairOk.setPlayerId(playerId);
			makePairOk.setRoomId(roomId);
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
			makePairOk.setCrit(crit);
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
			for (int i = 0; i < battleTeam.getCombatList().size(); i++) {
				combat = battleTeam.getCombatList().get(i);
				int buffcount = 0;
				List<Buff> buffList = combat.getPlayer().getBuffList();
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

				pifs = combat.getPlayer().getWeapon();
				if (pifs.getWeapSkill1() != 0) {
					wp = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
					weaponSkillPlayerId.add(pifs.getPlayerId());
					weaponSkillName.add(wp.getSkillName());
					weaponSkillType.add(wp.getType());
					weaponSkillChance.add(wp.getUseChance());
					weaponSkillParam1.add(wp.getParam1());
					weaponSkillParam2.add(wp.getParam2());
				}
				if (pifs.getWeapSkill2() != 0) {
					wp = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
					weaponSkillPlayerId.add(pifs.getPlayerId());
					weaponSkillName.add(wp.getSkillName());
					weaponSkillType.add(wp.getType());
					weaponSkillChance.add(wp.getUseChance());
					weaponSkillParam1.add(wp.getParam1());
					weaponSkillParam2.add(wp.getParam2());
				}
				playerBuffCount[playerIndex] = buffcount;
				playerIndex++;
			}
			String[] strings = new String[weaponSkillName.size()];

			sort(battleTeam);
			makePairOk.setPlayerBuffCount(playerBuffCount);
			makePairOk.setBuffType(ServiceUtils.getInts(buffType.toArray()));
			makePairOk.setBuffParam1(ServiceUtils.getInts(buffParam1.toArray()));
			makePairOk.setBuffParam2(buffParam2);
			makePairOk.setBuffParam3(buffParam3);
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
			makePairOk.setBeEnemyCommunity(room.isEnemyCon());
			makePairOk.setZsleve(zsleve);
			makePairOk.setSkillful(skillful);
			makePairOk.setPetId(petId);
			makePairOk.setPetIcon(petIcon);
			makePairOk.setPetType(petType);
			makePairOk.setPetSkillId(petSkillId);
			makePairOk.setPetProbability(petProbability);
			makePairOk.setPetParam1(petParam1);
			makePairOk.setPetParam2(petParam2);
			makePairOk.setPetEffect(petEffect);
			makePairOk.setServerName(serverName);
			makePairOk.setPetVersion(petVersion);
			makePairOk.setPower(force);
			makePairOk.setArmor(armor);
			makePairOk.setFighting(fighting);
			makePairOk.setWinRate(winRate);
			makePairOk.setRobotSkill(ServiceManager.getManager().getVersionService().getVersion().getRobotSkill());
			makePairOk.setConstitution(constitution);
			makePairOk.setLucky(lucky);
			makePairOk.setAgility(agility);

			for (Combat player : battleTeam.getCombatList()) {
				if (!player.isLost() && !player.isRobot()) {
					makePairOk.setSelfId(player.getId());
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
	public BattleTeam getBattleTeam(int battleId) {
		return battleTeamMap.get(battleId);
	}

	/**
	 * 删除战斗组
	 * 
	 * @param battleId
	 */
	public void deleteBattleTeam(BattleTeam battleTeam) {
		// System.out.println("DeleteBattleTeam------------" +
		// battleTeam.getBattleId());
		battleTeamMap.remove(battleTeam.getBattleId());
		battleTeamList.remove(battleTeam);
	}

	/**
	 * 加载完成准备进入战斗
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void ready(int battleId, int playerId) {
		BattleTeam battleTeam = battleTeamMap.get(battleId);
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
		BattleTeam battleTeam = battleTeamMap.get(battleId);
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

	/**
	 * 回合结束发送新一轮的行动次序
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void sendSort(BattleTeam battleTeam, int playerId) {
		synchronized (battleTeam) {
			if (null == battleTeam) {
				return;
			}
			battleTeam.setNewRun(false);
			Vector<Combat> combatList = battleTeam.getCombatList();
			Combat combat = null;
			boolean chack = false;
			List<Combat> fList = new ArrayList<Combat>();
			for (int i = combatList.size() - 1; i > -1; i--) {
				combat = combatList.get(i);
				if (!combat.isLost() && !combat.isDead() && combat.getFrozenTimes() < 1) {
					break;
				} else if (combat.getPlayer().getId() == playerId || -1 == playerId) {
					chack = true;
				}
				fList.add(combat);
				combat = null;
			}
			if (null == combat) {
				try {
					if (gameOver(battleTeam, 0))
						return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (chack || combat.getId() == playerId) {
				for (Combat c : fList) {
					frozenMinus(c);
				}
				battleTeam.setNewRun(true);
				battleTeam.setActionIndex(0);
				battleTeam.setRound(battleTeam.getRound() + 1);
				battleTeam.setPlayerIds(null);
				frozenOver(battleTeam);
				sort(battleTeam);
			}
		}
	}

	/**
	 * 返回角色攻击顺序
	 * 
	 * @param battleId
	 * @return
	 */
	public int[] sort(BattleTeam battleTeam) {
		if (null != battleTeam.getPlayerIds()) {
			return ArrayUtils.toPrimitive(battleTeam.getPlayerIds().toArray(new Integer[0]));
		}
		Vector<Combat> combatList = battleTeam.getCombatList();
		Vector<Integer> palyerIdList = new Vector<Integer>();
		battleTeam.setPlayerIds(palyerIdList);
		int size = combatList.size();
		int[] playerIds = new int[size];
		Comparator<Combat> ascComparator = new CombatComparator();
		Collections.sort(combatList, ascComparator);
		Combat combat;
		for (int i = 0; i < size; i++) {
			combat = combatList.get(i);
			combat.initPlayerBattleInfo();
			playerIds[i] = combat.getPlayer().getId();
			palyerIdList.add(playerIds[i]);
		}
		return playerIds;
	}

	/**
	 * 减少冰冻回合
	 * 
	 * @param combat
	 */
	public void frozenMinus(Combat combat) {
		if (combat.getFrozenTimes() > 0) {
			combat.setFrozenTimes(combat.getFrozenTimes() - 1);
			if (combat.getFrozenTimes() == 0) {
				// 等待新回合解除冰冻
				combat.setFrozenTimes(-1);
			}
		}
	}

	/**
	 * 解除玩家冰冻状态
	 * 
	 * @param battleTeam
	 */
	public void frozenOver(BattleTeam battleTeam) {
		synchronized (battleTeam) {
			List<Integer> frozeIdList = new ArrayList<Integer>();
			for (Combat combat : battleTeam.getCombatList()) {
				if (combat.getFrozenTimes() == -1) {
					combat.setFrozenTimes(0);
					frozeIdList.add(combat.getId());
				}
			}
			if (frozeIdList.size() > 0) {
				FrozenOver frozenOver = new FrozenOver();
				frozenOver.setPlayerIds(ServiceUtils.getInts(frozeIdList.toArray()));
				for (Combat combat : battleTeam.getCombatList()) {
					if (!combat.isLost() && !combat.isRobot()) {
						combat.getPlayer().sendData(frozenOver);
					}
				}
			}
		}
	}

	/**
	 * 获得当前行动玩家
	 * 
	 * @param battleId
	 * @return
	 */
	public WorldPlayer getActionPlayer(int battleId) {
		BattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			Combat actionCombat = battleTeam.getActionPlayer();
			int i = 0;
			while (actionCombat.isLost() || actionCombat.isDead() || actionCombat.getFrozenTimes() > 0) {
				frozenMinus(actionCombat);
				actionCombat = battleTeam.getActionPlayer();
				i++;
				if (i >= battleTeam.getCombatNum()) {
					break;
				}
			}
			if (null != actionCombat) {
				return actionCombat.getPlayer();
			} else {
				return null;
			}
		}
	}

	/**
	 * 同步怒气值
	 * 
	 * @param battleId
	 * @param playerId
	 * @param value
	 */
	public void updateAngryValue(int battleId, int playerId, int value, boolean useBigKill) {
		BattleTeam battleTeam = battleTeamMap.get(battleId);
		if (null == battleTeam)
			return;
		synchronized (battleTeam) {
			Combat combat = battleTeam.getCombatMap().get(playerId);
			if (useBigKill) {
				combat.setAngryValue(0);
			} else {
				if (combat.getLevel() < 4) // 玩家等级小于4则获得的怒气加3倍
					value *= 3;
				int angryValue = combat.getAngryValue() + value;
				angryValue = angryValue > 100 ? 100 : angryValue;
				combat.setAngryValue(angryValue);
			}
			// System.out.println("playerId:"+playerId+"-----AngryValue:"+combat.getAngryValue());
			ChangeAngryValue changeAngryValue = new ChangeAngryValue();
			changeAngryValue.setBattleId(battleId);
			changeAngryValue.setPlayerId(playerId);
			changeAngryValue.setAngryValue(combat.getAngryValue());
			for (Combat cb : battleTeam.getCombatList()) {
				if (!cb.isRobot() && !cb.isLost() && null != cb.getPlayer()) {
					cb.getPlayer().sendData(changeAngryValue);
				}
			}
		}
	}

	/**
	 * 判断游戏是否结束
	 * 
	 * @param battleId
	 *            对战组id
	 * @param operatCamp
	 *            触发检测游戏结束的玩家阵营
	 * @throws Exception
	 */
	public boolean gameOver(BattleTeam battleTeam, int operatCamp) throws Exception {
		if (null == battleTeam) {
			return true;
		}
		Vector<Combat> combatList = battleTeam.getCombatList();
		boolean isOver = false;
		int winCamp = 0;
		if (battleTeam.getBattleMode() == 1 || battleTeam.getBattleMode() == 5 || battleTeam.getBattleMode() == 6) {
			boolean allDead0 = true;
			boolean allDead1 = true;
			for (Combat combat : combatList) {
				if (0 == combat.getCamp() && !combat.isDead()) {
					allDead0 = false;
				}
				if (1 == combat.getCamp() && !combat.isDead()) {
					allDead1 = false;
				}
			}
			if (allDead0 || allDead1) {
				isOver = true;
				if (allDead0 && allDead1) {
					winCamp = operatCamp;
				} else if (allDead0) {
					winCamp = 1;
				}
			}
		}
		if (battleTeam.getBattleMode() == 2) {
			if (battleTeam.isCampAllLost(0)) {
				isOver = true;
				winCamp = 1;
			}
			if (battleTeam.isCampAllLost(1)) {
				isOver = true;
				winCamp = 0;
			}
			if (battleTeam.getCamp0BeKillCount() >= 3
					|| battleTeam.getCamp1BeKillCount() >= 3
					|| (1 == battleTeam.getPlayerNumMode() && (battleTeam.getCamp0BeKillCount() >= 2 || battleTeam.getCamp1BeKillCount() >= 2))) {
				isOver = true;
				if (battleTeam.getCamp0BeKillCount() == battleTeam.getCamp1BeKillCount()) {
					winCamp = operatCamp;
				} else if (battleTeam.getCamp0BeKillCount() > battleTeam.getCamp1BeKillCount()) {
					winCamp = 1;
				}
			}
		}
		if (battleTeam.getBattleMode() == 3 || battleTeam.getBattleMode() == 4) {
			int aliveNum = 0;
			for (Combat combat : combatList) {
				if (!combat.isDead()) {
					winCamp = combat.getCamp();
					aliveNum++;
				}
			}
			if (0 == aliveNum) {
				isOver = true;
				winCamp = operatCamp;
			} else if (1 == aliveNum) {
				isOver = true;
			}
		}
		if (isOver) {
			try {
				synchronized (battleTeam) {
					if (battleTeam.isOver())
						return isOver;
					battleTeam.setOver(isOver);
				}
				// 计算玩家本次战斗获得的经验数
				Map<String, int[]> m = setPlayerExp(battleTeam, winCamp);
				int[] mapGuildExp = m.get("mapGuildExp");
				int[] mapActivityExp = m.get("mapActivityExp");
				int[] mapmarryExp = m.get("mapmarryExp");

				GameOver gameOver = new GameOver();
				gameOver.setBattleId(battleTeam.getBattleId());
				gameOver.setFirstHurtPlayerId(battleTeam.getFirstHurtPlayerId());
				gameOver.setWinCamp(winCamp);
				gameOver.setGuildAddExp(mapGuildExp);// 公会技能加的经验
				gameOver.setActivityAddExp(mapActivityExp);// 活动是否加了经验
				gameOver.setIsMarry(mapmarryExp); // 结婚加的经验

				int playerCount = battleTeam.getCombatList().size();
				gameOver.setPlayerCount(playerCount);
				int[] playerIds = new int[playerCount];
				int[] shootRate = new int[playerCount];
				int[] totalHurt = new int[playerCount];
				int[] killCount = new int[playerCount];
				int[] beKilledCount = new int[playerCount];
				int[] addExp = new int[playerCount];
				int[] Exp = new int[playerCount];
				int[] upgradeExp = new int[playerCount];
				int[] nextUpgradeExp = new int[playerCount];
				int[] itemId = new int[playerCount];
				int[] integral = new int[playerCount];

				int[] isDoubleExpCard = new int[playerCount];

				int i = 0;
				for (Combat combat : combatList) {
					if (gameOver.getWinCamp() == combat.getCamp()) {
						combat.setWin(true);
					}
					playerIds[i] = combat.getId();
					shootRate[i] = (int) ((combat.getHuntTimes() / (float) combat.getShootTimes()) * 100);
					totalHurt[i] = combat.getTotalHurt();
					killCount[i] = combat.getKillCount();
					beKilledCount[i] = combat.getBeKilledCount();
					addExp[i] = combat.getExp();
					Player player = combat.getPlayer().getPlayer();
					Exp[i] = player.getExp();

					PlayerItemsFromShop item = ServiceManager.getManager().getPlayerItemsFromShopService()
							.uniquePlayerItem(player.getId(), 5);
					isDoubleExpCard[i] = item != null && item.getPLastTime() > 1 ? 1 : 0;// 双倍经验卡

					upgradeExp[i] = ServiceManager.getManager().getPlayerService()
							.getUpgradeExp(combat.getLevel(), combat.getPlayer().getPlayer().getZsLevel());
					nextUpgradeExp[i] = ServiceManager.getManager().getPlayerService()
							.getUpgradeExp(combat.getLevel() + 1, combat.getPlayer().getPlayer().getZsLevel());
					integral[i] = combat.getIntegral();
					i++;
				}
				gameOver.setPlayerIds(playerIds);
				gameOver.setShootRate(shootRate);
				gameOver.setTotalHurt(totalHurt);
				gameOver.setKillCount(killCount);
				gameOver.setBeKilledCount(beKilledCount);
				gameOver.setAddExp(addExp);
				gameOver.setExp(Exp);
				gameOver.setUpgradeExp(upgradeExp);
				gameOver.setNextUpgradeExp(nextUpgradeExp);
				gameOver.setItemId(itemId);
				gameOver.setIntegral(integral);
				gameOver.setBattleMode(battleTeam.getBattleMode());
				gameOver.setPlayerNumMode(battleTeam.getPlayerNumMode());
				gameOver.setIsDoubleExpCard(isDoubleExpCard);

				if (battleTeam.getBattleMode() == 4 || battleTeam.getBattleMode() == 6) {
					int cardCount = -1;
					int[] card_shopItemId = new int[0];
					String[] card_Name = new String[0];
					String[] item_icon = new String[0];
					int[] card_num = new int[0];
					gameOver.setCardCount(cardCount);
					gameOver.setCard_shopItemId(card_shopItemId);
					gameOver.setCard_Name(card_Name);
					gameOver.setItem_icon(item_icon);
					gameOver.setCard_num(card_num);
				} else {
					int cardCount = 8;
					int[] card_shopItemId = new int[cardCount];
					String[] card_Name = new String[cardCount];
					String[] item_icon = new String[cardCount];
					int[] card_num = new int[cardCount];
					List<RewardItemsVo> rivList = ServiceManager.getManager().getRewardItemsService().getRewardItems();
					DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
					DailyRewardVo dailyRewardVo = dailyActivityService.getTurnoverReward();
					for (i = 0; i < cardCount; i++) {
						card_shopItemId[i] = rivList.get(i).getItemId();
						card_Name[i] = rivList.get(i).getItemName();
						item_icon[i] = rivList.get(i).getItemIcon();
						int count = -1 == rivList.get(i).getDays() ? rivList.get(i).getCount() : rivList.get(i).getDays();
						card_num[i] = count;
						if (i < playerCount) {
							if (!combatList.get(i).isLost() && combatList.get(i).getHuntTimes() > 0 && !combatList.get(i).isSuicide()) {// 如果玩家掉线或伤害为0或自杀则不给以物品奖励
								itemId[i] = card_shopItemId[i];
								combatList.get(i).setRiv(rivList.get(i));
								if (card_shopItemId[i] == Common.GOLDID) {
									card_num[i] = (int) ServiceManager.getManager().getBuffService()
											.getAddition(combatList.get(i).getPlayer(), count, Buff.GOLD);
									card_num[i] = dailyActivityService.getRewardedVal(card_num[i], dailyRewardVo.getGold());
									rivList.get(i).setCount(card_num[i]);
								} else if (card_shopItemId[i] == Common.BADGEID) {
									card_num[i] = dailyActivityService.getRewardedVal(card_num[i], dailyRewardVo.getMedal());
									rivList.get(i).setCount(card_num[i]);
								}
							} else {
								itemId[i] = -1;
							}
						}
					}
					gameOver.setCardCount(cardCount);
					gameOver.setCard_shopItemId(card_shopItemId);
					gameOver.setCard_Name(card_Name);
					gameOver.setItem_icon(item_icon);
					gameOver.setCard_num(card_num);
				}
				for (Combat combat : combatList) {
					if (!combat.isRobot() && !combat.isLost() && null != combat.getPlayer()) {
						gameOver.setPlayerId(combat.getId());
						combat.getPlayer().sendData(gameOver);
					}
				}
				ServiceManager.getManager().getTaskService().battleTask(battleTeam, gameOver.getWinCamp());// 更新玩家任务
				ServiceManager.getManager().getTitleService().battleTask(battleTeam, gameOver.getWinCamp());// 检查称号任务
			} catch (Exception e) {
				e.printStackTrace();
			}
			exitBattle(battleTeam);
		}
		return isOver;
	}

	/**
	 * 战斗退出
	 * 
	 * @param battleId
	 * @throws Exception
	 */
	public void exitBattle(BattleTeam battleTeam) {
		try {
			WorldPlayer player;
			PlayerService playerService = ServiceManager.getManager().getPlayerService();
			IPlayerPetService playerPetService = ServiceManager.getManager().getPlayerPetService();
			IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
			IPlayerSinConsortiaService playerSinConsortiaService = ServiceManager.getManager().getPlayerSinConsortiaService();
			int saveMarkId = 0;
			StringBuffer playerBattleInfo = new StringBuffer();
			for (Combat combat : battleTeam.getCombatList()) {
				player = combat.getPlayer();
				player.setBattleId(0);
				playerBattleInfo.append(player.getId());
				playerBattleInfo.append("*");
				playerBattleInfo.append(player.getLevel());
				playerBattleInfo.append("*");
				if (battleTeam.getBattleMode() != 4) {// 普通战斗
					if (!combat.isRobot() || combat.isEnforceQuit()) {
						if (battleTeam.getBattleMode() != 6) {
							if (!combat.isLost() && !combat.isEnforceQuit()) {
								int num = 1;
								if (player.hasRing()) {
									num++;
								}
								if (combat.getHuntTimes() > 0 && !combat.isSuicide()) {// 玩家命中次数大于0才给以奖励
									if (combat.getRiv() != null && combat.getRiv().getItemId() == Common.GOLDID) {
										int gold = ServiceManager.getManager().getVersionService()
												.getAddition(combat.getRiv().getCount(), 1);
										ServiceManager.getManager().getPlayerService()
												.updatePlayerGold(player, gold, SystemLogService.GSBATTLE, "");
									} else if (combat.getRiv() != null && combat.getRiv().getItemId() == Common.DIAMONDID) {// 获得点卷
										ServiceManager
												.getManager()
												.getPlayerService()
												.addTicket(player, combat.getRiv().getCount(), 0, TradeService.ORIGIN_BATT, 0, "", "对战奖励",
														"", "");
									} else {
										if (combat.getRiv() != null && Common.BADGEID == combat.getRiv().getItemId()) {
											combat.getRiv().setCount(
													ServiceManager.getManager().getVersionService()
															.getAddition(combat.getRiv().getCount(), 2));
										}
										playerItemsFromShopService.playerGetItem(player.getId(), combat.getRiv().getItemId(), -1, combat
												.getRiv().getDays(), combat.getRiv().getCount(), 12, null, 0, 0, 0);
									}
									if (combat.getExp() > 0
											|| (0 == combat.getExp() && player.getLevel() >= Server.config.getMaxLevel(player.getPlayer()
													.getZsLevel()))) {
										if (battleTeam.getBattleMode() == 5) {
											if (combat.isWin()) {
												num = 2 + combat.getKillCount() * 1;
											} else {
												num = 1 + combat.getKillCount() * 1;
											}
											if (player.hasRing()) {
												num = num * 2;
											}
											if (battleTeam.isEnemyMark()) {
												combat.setExp((int) Math.ceil(combat.getExp() * 1.5));// 敌对公会多加1.5倍
												num = num * 2;
											} else {
												combat.setExp((int) Math.ceil(combat.getExp() * 1.2));
											}
										}
										playerSinConsortiaService.updatePlayerContribute(player, num);
										playerItemsFromShopService.updateItmeSkillful(player);
									}
								}
							}
						}
						if (battleTeam.getBattleMode() == 5) {
							ServiceManager.getManager().getConsortiaService()
									.saveConsortiaBattleLog(player, combat.isWin(), battleTeam.getBattleMode());
							if (player.getGuildId() != saveMarkId) {
								if (combat.isWin() && !combat.isRobot()) {
									ServiceManager.getManager().getConsortiaService().updateConsortiaBattleNum(player.getGuildId(), 1);
								} else {
									ServiceManager.getManager().getConsortiaService().updateConsortiaBattleNum(player.getGuildId(), 0);
								}
								saveMarkId = player.getGuildId();
							}
						} else if (!combat.isLost() || combat.isEnforceQuit()) {
							if (combat.isEnforceQuit()) {
								playerService.updateBattleHistory(player.getPlayer(), battleTeam.getBattleMode(),
										battleTeam.getPlayerNumMode(), false);
							} else {
								playerService.updateBattleHistory(player.getPlayer(), battleTeam.getBattleMode(),
										battleTeam.getPlayerNumMode(), combat.isWin());
							}
						}
						if (!combat.isEnforceQuit()) {
							if (!combat.isLost()) {
								int exp = combat.getExp();
								exp = ServiceManager.getManager().getVersionService().getAddition(combat.getExp(), 0);
								playerService.updatePlayerEXP(player, exp);
								playerBattleInfo.append(exp);
								playerBattleInfo.append("*");
								playerPetService.updateExp(player, player.getPlayerPet(), (int) Math.ceil(exp * 0.1));
							} else {
								playerBattleInfo.append(0);
								playerBattleInfo.append("*");
							}
						} else {
							int exp = combat.getCexp();
							playerService.updatePlayerEXP(player, exp);
							playerBattleInfo.append(exp);
							playerBattleInfo.append("*");
						}
					} else {
						playerBattleInfo.append(0);
						playerBattleInfo.append("*");
					}
					if (battleTeam.getBattleMode() == 6) {
						playerBattleInfo.append(combat.getIntegral());
						playerBattleInfo.append("*");
						ServiceManager.getManager().getChallengeSerService()
								.addIntegral(combat.getPlayer(), combat.getIntegral(), combat.isWin() && !combat.isEnforceQuit());
					}
				} else {// 排位赛
					int num = 1;
					if (player.hasRing()) {
						num++;
					}
					RankRecord rr = ServiceManager.getManager().getRankRecordService().getPlayerRankByPlayerId(combat.getId());
					if (null == rr) {
						rr = new RankRecord();
						if (!combat.isWin()) {
							rr.setIntegral(0);
							rr.setWinNum(0);
						} else {
							rr.setIntegral(combat.getExp());
							rr.setWinNum(1);
						}
						rr.setPlayer(combat.getPlayer().getPlayer());
						rr.setTotalNum(1);
						ServiceManager.getManager().getRankRecordService().save(rr);
					} else {
						rr.setIntegral(rr.getIntegral() + combat.getExp());
						if (rr.getIntegral() < 0) {
							rr.setIntegral(0);
						}
						if (combat.isWin()) {
							rr.setWinNum(rr.getWinNum() + 1);
						}
						rr.setTotalNum(rr.getTotalNum() + 1);
						ServiceManager.getManager().getRankRecordService().update(rr);
					}
					if (!combat.isLost()) {
						playerSinConsortiaService.updatePlayerContribute(player, num);
						playerItemsFromShopService.updateItmeSkillful(player);
					}
					rankLog.info("玩家排位赛记录--玩家ID：" + player.getId() + "--现有积分：" + rr.getIntegral() + "--本局积分：" + combat.getExp());
					playerBattleInfo.append(combat.getExp());
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
			if (battleTeam.getBattleMode() != 5 && battleTeam.getBattleMode() != 4 && battleTeam.getBattleMode() != 6) {
				ServiceManager.getManager().getLogSerivce().updateBattleLog(battleTeam);
			}
			deleteBattleTeam(battleTeam);
			String useTools = battleTeam.getUseTools();
			useTools = useTools.length() > 1 ? useTools.substring(0, useTools.length() - 1) : useTools;
			if (playerBattleInfo.length() > 0)
				playerBattleInfo.deleteCharAt(playerBattleInfo.length() - 1);
			if (battleTeam.getBattleMode() != 4 && battleTeam.getBattleMode() != 6) {
				GameLogService.battle(playerBattleInfo.toString(), battleTeam.getMapId(), battleTeam.getBattleMode(),
						battleTeam.getStartMode(), battleTeam.getCombatList().size(), battleTeam.getRound(), useTools);
			} else if (battleTeam.getBattleMode() == 6) {
				GameLogService.kingBattle(playerBattleInfo.toString(), battleTeam.getMapId(), battleTeam.getRound(), useTools);
			} else if (battleTeam.getBattleMode() == 4) {
				GameLogService.qualifying(playerBattleInfo.toString(), battleTeam.getMapId(), battleTeam.getRound(), useTools);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 玩家掉线
	 * 
	 * @param battleId
	 * @param playerId
	 * @param isDropped
	 *            是否掉线
	 * @throws Exception
	 */
	public void playerLose(int battleId, int playerId) throws Exception {
		// writeLog("battleId:" + battleId + "---playerLose:" + playerId);
		BattleTeam battleTeam = battleTeamMap.get(battleId);
		if (null == battleTeam) {
			return;
		}
		synchronized (battleTeam) {
			Combat combat = battleTeam.getCombatMap().get(playerId);
			if (null != combat.getPlayer()) {
				combat.getPlayer().setBattleId(0);
			}
			boolean saicc = ServiceManager.getManager().getVersionService().versionService.getConfig().getBecomeRobot() != 0 ? true : false;
			if (saicc && battleTeam.getBattleMode() != 4 && battleTeam.getBattleMode() != 6) {
				combat.setRobot(true);
			} else {
				combat.setHp(0);
				combat.setLost(true);
				combat.setDead(true);
			}
			if (playerId == battleTeam.getAicounter() || saicc) {
				sendAIControlCommon(battleId);
			}
			int camp = 0;
			PlayerLose playerLose = new PlayerLose();
			playerLose.setBattleId(battleId);
			playerLose.setCurrentPlayerId(playerId);
			for (Combat cb : battleTeam.getCombatList()) {
				if (!cb.isLost() && !cb.isRobot() && null != cb.getPlayer()) {
					if (!saicc || battleTeam.getBattleMode() == 4 || battleTeam.getBattleMode() == 6) {
						playerLose.setPlayerId(cb.getId());
						cb.getPlayer().sendData(playerLose);
					}
					camp = cb.getCamp();
				}
			}
			if (0 != battleTeam.getStat()) {
				if (!gameOver(battleTeam, camp)) {
					int actionIndex = battleTeam.getActionIndex() - 1;
					actionIndex = actionIndex < 0 ? battleTeam.getCombatList().size() - 1 : actionIndex;
					if (playerId == battleTeam.getCombatList().get(actionIndex).getId()) {
						Pass pass = new Pass();
						pass.setBattleId(battleId);
						pass.setPlayerId(playerId);
						PassHandler passHandler = new PassHandler();
						passHandler.handle(pass);
					}
					EndCurRound endCurRound = new EndCurRound();
					endCurRound.setBattleId(battleId);
					endCurRound.setPlayerId(playerId);
					endCurRound.setCurrentPlayerId(playerId);
					EndCurRoundHandler endCurRoundHandler = new EndCurRoundHandler();
					endCurRoundHandler.handle(endCurRound);
				}
			} else {
				if (battleTeam.isCampAllLost(0) && battleTeam.isCampAllLost(1)) {
					gameOver(battleTeam, camp);
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
	 * @param playerLose
	 * @throws Exception
	 */
	public void sendAIControlCommon(int battleId) throws Exception {
		BattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			Combat combat = null;
			if (0 != battleTeam.getAicounter()) {
				combat = battleTeam.getCombatMap().get(battleTeam.getAicounter());
				combat = combat.isLost() || combat.isRobot() ? null : combat;
			}
			List<Integer> playerId = new ArrayList<Integer>();
			List<Integer> aiCtrlId = new ArrayList<Integer>();
			for (Combat cb : battleTeam.getCombatList()) {
				if (null != cb.getPlayer()) {
					if (cb.isRobot()) {
						playerId.add(cb.getId());
						aiCtrlId.add(cb.getAiCtrlId());
					} else if (null == combat && !cb.isLost()) {
						combat = cb;
					}
				}
			}
			if (playerId.size() > 0) {
				if (null != combat) {
					AIControlCommon aIControlCommon = new AIControlCommon();
					aIControlCommon.setBattleId(battleTeam.getBattleId());
					aIControlCommon.setIdcount(playerId.size());
					aIControlCommon.setPlayerIds(ArrayUtils.toPrimitive(playerId.toArray(new Integer[0])));
					aIControlCommon.setAiCtrlId(ArrayUtils.toPrimitive(aiCtrlId.toArray(new Integer[0])));
					battleTeam.setAicounter(combat.getId());
					combat.getPlayer().sendData(aIControlCommon);
					writeLog("BattleTeam:" + battleId + "----AiCounter:" + battleTeam.getAicounter());
				} else {
					for (Combat cb : battleTeam.getCombatList()) {
						if (cb.isRobot()) {
							cb.setHp(0);
							cb.setLost(true);
							cb.setDead(true);
						}
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
	public void playerRun(int battleId, int playerId) {
		BattleTeam battleTeam = battleTeamMap.get(battleId);
		synchronized (battleTeam) {
			Combat cb = battleTeam.getCombatMap().get(playerId);
			cb.setCurRound(true);
			boolean c = true;
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
	 * @param battleTeam
	 *            战斗组
	 * @param winCamp
	 *            胜利阵营
	 * @return int 公会技能加经验数
	 */
	public Map<String, int[]> setPlayerExp(BattleTeam battleTeam, int winCamp) {
		int[] mapGuildExp = new int[battleTeam.getCombatList().size()];// 公会技能加经验数的情况
		int[] mapActivityExp = new int[battleTeam.getCombatList().size()];// 活动经验
		int[] mapmarryExp = new int[battleTeam.getCombatList().size()];// 活动经验
		mapGuildExp[0] = 0;
		mapActivityExp[0] = 0;
		mapmarryExp[0] = 0;
		if (battleTeam.getBattleMode() == 4) {
			int failscore = 0;
			int winscore = 0;
			int highscore = 0; // 两个人积分较高的那个
			boolean escape = false;
			RankRecord rr;
			// 获得失败方积分
			for (Combat combat : battleTeam.getCombatList()) {
				rr = ServiceManager.getManager().getRankRecordService().getPlayerRankByPlayerId(combat.getId());
				if (combat.getCamp() != winCamp) {
					failscore = null == rr ? 0 : rr.getIntegral();
					escape = combat.isEnforceQuit();
				} else {
					winscore = null == rr ? 0 : rr.getIntegral();
				}
			}

			highscore = winscore > failscore ? winscore : failscore;
			// 核算积分
			for (Combat combat : battleTeam.getCombatList()) {
				if (combat.getCamp() == winCamp) {
					if (escape) {// 强退胜利不加分，输的扣分
						combat.setExp(0);
					} else {
						if (highscore < 1000) {
							// 分差小于500
							if (Math.abs(winscore - failscore) < 500) {
								combat.setExp((int) (Math.ceil(failscore / 30.0) + 25));
							} else {
								combat.setExp((int) (Math.ceil(failscore / 40.0) + 25));
							}
						} else if (highscore > 1000 && highscore <= 5000) {
							// 分差小于1000
							if (Math.abs(winscore - failscore) <= 1000) {
								combat.setExp((int) (Math.ceil(failscore / 50.0) + 15));
							} else if (Math.abs(winscore - failscore) > 1000 && Math.abs(winscore - failscore) <= 3000) {
								combat.setExp((int) (Math.ceil(failscore / 120.0) + 15));
							} else {// 分差大于3000
								combat.setExp((int) (Math.ceil(failscore / 120.0) + 10));
							}
						} else {
							combat.setExp((int) (Math.ceil(failscore / 120.0) + 1));
						}
					}
				} else {
					if (highscore < 1000) {
						combat.setExp((int) (-Math.ceil(failscore / 40.0) - 10));
					} else if (highscore > 1000 && highscore <= 5000) {
						// 分差小于1000
						if (Math.abs(winscore - failscore) <= 1000) {
							combat.setExp((int) (-Math.ceil(failscore / 50.0) - 10));
						} else {// 分差大于1000
							combat.setExp((int) (-Math.ceil(failscore / 110.0) - 10));
						}
					} else {
						// 分差小于5000
						if (Math.abs(winscore - failscore) <= 1000) {
							combat.setExp((int) (-Math.ceil(failscore / 120.0) - 10));
						} else if (Math.abs(winscore - failscore) > 5000 && Math.abs(winscore - failscore) <= 10000) {
							combat.setExp((int) (-Math.ceil(failscore / 110.0) - 10));
						} else if (Math.abs(winscore - failscore) > 10000 && Math.abs(winscore - failscore) <= 20000) {// 分差大于3000
							combat.setExp((int) (-Math.ceil(failscore / 80.0) - 10));
						} else if (Math.abs(winscore - failscore) > 20000 && Math.abs(winscore - failscore) <= 40000) {
							combat.setExp((int) (-Math.ceil(failscore / 60.0) - 10));
						} else {
							combat.setExp((int) (-Math.ceil(failscore / 40.0) - 10));
						}
					}
				}
			}
		} else {
			if (6 != battleTeam.getBattleMode()) {
				RoomService roomService = ServiceManager.getManager().getRoomService();
				int playerCount = battleTeam.getCombatList().size();
				int coupleId = 0;
				if (battleTeam.getRound() > 1) {// 正常战斗
					int sumLevel = 0;
					for (Combat combat : battleTeam.getCombatList()) {
						sumLevel += combat.getLevel();
					}
					int exp = 0;
					int i = 0;
					for (Combat combat : battleTeam.getCombatList()) {
						MarryRecord mr = ServiceManager.getManager().getMarryService()
								.getSingleMarryRecordByPlayerId(combat.getSex(), combat.getId(), 1);
						if (null != mr && mr.getStatusMode() > 1) {
							if (combat.getSex() == 0) {
								coupleId = mr.getWomanId();
							} else {
								coupleId = mr.getManId();
							}
						}
						double avgLevel = sumLevel * 1d / playerCount;
						int level = combat.getLevel(), killCount = combat.getKillCount();
						// 经验=（（参数人数*0.5+0.5)*((参战平均等级-自身等级）/(1.5*(自身等级+参战平均等级))+1)*(杀人数*0.15+0.5)*100+20）*（自身等级/20+1)
						exp = (int) ((playerCount * 0.5 + 0.5) * ((avgLevel - level) / (1.5 * (level + avgLevel)) + 1)
								* (killCount * 0.15 + 0.5) * 100 + 20)
								* (level / 20 + 1);
						if (combat.getCamp() == winCamp) {
							exp = exp * 1;
						} else {
							exp = (int) (exp * 0.5);
						}
						if (battleTeam.getFirstHurtPlayerId() == combat.getId() && battleTeam.getPlayerNumMode() != 1) {
							exp += 10;
						}
						// buff加成
						int exp1 = ServiceManager.getManager().getBuffService().getExp(combat.getPlayer(), exp) - exp;
						// 添加公会技能加成
						int exp2 = (int) ServiceManager.getManager().getBuffService().getAddition(combat.getPlayer(), exp, Buff.CEXP) - exp;
						mapGuildExp[i] = exp2;
						// 结婚加成
						int exp3 = 0;
						if (coupleId != 0) {
							for (Combat c : battleTeam.getCombatList()) {
								if (c.getId() == coupleId) {
									exp3 = (int) ServiceManager.getManager().getBuffService()
											.getAddition(combat.getPlayer(), exp, Buff.MEXP)
											- exp;
								}
							}
						}
						mapmarryExp[i] = exp3;
						combat.setExp(exp + exp1 + exp2 + exp3);
						if (combat.getExp() > 0
								&& combat.getLevel() == Server.config.getMaxLevel(combat.getPlayer().getPlayer().getZsLevel())) {
							int sjexp = ServiceManager.getManager().getPlayerService()
									.getUpgradeExp(combat.getLevel(), combat.getPlayer().getPlayer().getZsLevel());
							int dqexp = combat.getExp() + combat.getExp();
							if (sjexp <= dqexp) {
								if ((sjexp - 1 - combat.getExp()) < 0) {
									WorldPlayer player = combat.getPlayer();
									// 玩家满级后不加经验，但宠物要加
									ServiceManager.getManager().getPlayerPetService()
											.updateExp(player, player.getPlayerPet(), (int) Math.ceil(combat.getExp() * 0.1));
									combat.setExp(0);
								} else {
									combat.setExp(sjexp - 1 - combat.getExp());
								}
							}
						}
						if (3 == battleTeam.getBattleMode()) {
							// 如果是机器人并且获得经验后会升级则退出房间
							if (combat.isRobot()
									&& ServiceManager.getManager().getPlayerService()
											.getUpgradeExp(combat.getLevel(), combat.getPlayer().getPlayer().getZsLevel()) <= combat
											.getExp() + exp) {
								int roomId = combat.getPlayer().getRoomId();
								if (0 != roomId && null != roomService.getRoom(roomId)) {
									int index = roomService.getPlayerSeat(roomId, combat.getId());
									roomService.exRoom(roomId, index, 0);
								}
							}
						}
						if (combat.isEnforceQuit()) {
							combat.setCexp(0);
						}
						i++;
					}
				}
			}

			// 经验双倍活动排位赛积分不应该算在内
			DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
			DailyRewardVo dailyRewardVo = dailyActivityService.getBattleReward();
			int i = 0;
			for (Combat combat : battleTeam.getCombatList()) {
				combat.setExp(dailyActivityService.getRewardedVal(combat.getExp(), dailyRewardVo.getExp()));
				mapActivityExp[i] = dailyRewardVo.getExp() > 1 ? 1 : 0;
				if (combat.getExp() > 0 && combat.getCamp() == winCamp) {// 经验大于0打折
					// 经验打折
					int rate = ServiceManager.getManager().getVersionService().getExpRateByBattleNum(combat.getPlayer().getBattleNum());
					combat.setExp(combat.getExp() * rate / 100);
					combat.getPlayer().setBattleNum(combat.getPlayer().getBattleNum() + 1);
				}
				i++;
			}
		}

		// 挑战赛核算积分
		if (battleTeam.getBattleMode() == 6) {
			for (Combat combat : battleTeam.getCombatList()) {
				int arrayWin = ServiceManager.getManager().getChallengeSerService().getArrayWinNum(combat.getId());
				if (ServiceManager.getManager().getChallengeSerService().isInTime()) {
					if (combat.getCamp() == winCamp) {
						if (combat.isEnforceQuit()) {
							combat.setIntegral(0);
							if (arrayWin != 0) {
								arrayWin = 0;
							}
						} else {
							arrayWin = arrayWin + 1;
							if (arrayWin > 3) {
								combat.setIntegral(5 + (arrayWin - 3) * 2);
							} else {
								combat.setIntegral(5);
							}

						}
					} else {
						if (combat.isEnforceQuit()) {
							combat.setIntegral(0);
						} else {
							combat.setIntegral(1);
						}
						if (arrayWin != 0) {
							arrayWin = 0;
						}
					}
				} else {
					combat.setIntegral(0);
				}
			}
		}
		HashMap<String, int[]> m = new HashMap<String, int[]>();
		m.put("mapGuildExp", mapGuildExp);
		m.put("mapActivityExp", mapActivityExp);
		m.put("mapmarryExp", mapmarryExp);
		return m;
	}

	public void writeLog(Object message) {
		log.info(message);
	}

	/**
	 * 获得随机道具和技能(排位赛使用）
	 * 
	 * @return
	 */
	public Map<String, Tools> getRandomTools() {
		Map<String, Tools> map = new HashMap<String, Tools>();
		// 获得道具和技能的列表
		List<Tools> jnList = ServiceManager.getManager().getToolsService().getToolsListByType(0);
		List<Tools> djList = ServiceManager.getManager().getToolsService().getToolsListByType(1);
		int randomNum = 0;
		for (int i = 0; i < 4; i++) {
			randomNum = ServiceUtils.getRandomNum(0, jnList.size());
			map.put("jn" + i, jnList.get(randomNum));
			jnList.remove(randomNum);
		}
		for (int i = 0; i < 4; i++) {
			randomNum = ServiceUtils.getRandomNum(0, djList.size());
			map.put("dj" + i, djList.get(randomNum));
		}
		return map;
	}
}