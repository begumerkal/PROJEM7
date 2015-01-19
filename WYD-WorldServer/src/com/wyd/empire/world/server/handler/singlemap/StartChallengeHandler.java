package com.wyd.empire.world.server.handler.singlemap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.singlemap.StartChallenge;
import com.wyd.empire.protocol.data.singlemap.StartChallengeOk;
import com.wyd.empire.world.bean.Guai;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerSingleMap;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.base.ISingleMapService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始挑战
 * 
 * @author zengxc
 *
 */

public class StartChallengeHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartChallengeHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	MapsService mapsService = null;
	ISingleMapService singleMapService = null;
	IGuaiService guaiService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			StartChallenge startChallenge = (StartChallenge) data;
			WorldPlayer player = session.getPlayer(data.getSessionId());
			mapsService = manager.getMapsService();
			singleMapService = manager.getSingleMapService();
			guaiService = manager.getGuaiService();
			com.wyd.empire.world.bean.Map singleMap = (com.wyd.empire.world.bean.Map) singleMapService.get(
					com.wyd.empire.world.bean.Map.class, startChallenge.getPointId());
			PlayerSingleMap playerSingleMap = singleMapService.getPlayerSingleMap(player.getId(), singleMap.getId());
			int useVigor = singleMap.getVitalityExpend();
			int openlevel = singleMap.getLevel();
			int vigor = player.getVigor();
			int canPassTimes = singleMap.getPassTimes();
			int bossmapSerial = singleMap.getBossmapSerial();
			boolean unlimited = singleMap.getPassTimes() == -1;
			// 判断是否够级别
			if (player.getLevel() < openlevel) {
				String openlevelStr = TipMessages.SINGLEMAP_OPEN_LEVEL;
				openlevelStr = openlevelStr.replace("{0}", openlevel + "");
				throw new ProtocolException(openlevelStr, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			// 是否足够活力
			if ((vigor - useVigor) < 0) {
				throw new ProtocolException(TipMessages.VIGOR_LOW, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}

			// 次数上限
			if (!unlimited) {
				if (playerSingleMap != null && playerSingleMap.getPassTimes() >= canPassTimes) {
					throw new ProtocolException(TipMessages.SINGLEMAP_PASS_LIMIT, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}
			// 判断上一个关卡有没有过,bossmapSerial=1表示第一关
			if (bossmapSerial > 1) {
				int prvId = singleMap.getId() - 1;
				PlayerSingleMap prvSingleMap = singleMapService.getPlayerSingleMap(player.getId(), prvId);
				if (prvSingleMap == null || prvSingleMap.getPassTimes() < 0) {
					throw new ProtocolException(TipMessages.SINGLEMAP_PREV_LEVEL, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}

			// ===
			StartChallengeOk ok = new StartChallengeOk(data.getSessionId(), data.getSerial());
			ok.setCleanCondition(singleMap.getMapIcon());
			/** 设置玩家信息 */
			setPlayerInfo(ok, player, singleMap);
			/** 设置玩家道具 */
			setPlayerTool(ok, player);
			/** 宠物信息 */
			setPetInfo(ok, player);
			/** 设置玩家武器信息 */
			setWeaponSkill(ok, player);
			/** 设置怪物信息 */
			setBossInfo(ok, singleMap);
			session.write(ok);
			player.inSingleMap(singleMap.getId());
			GameLogService.challengeSingleMap(player.getId(), player.getLevel(), player.isVip(), singleMap.getId());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}

	}

	private void setBossInfo(StartChallengeOk ok, com.wyd.empire.world.bean.Map map) {
		BossBattleTeamService bossBattleTeamService = manager.getBossBattleTeamService();
		List<Map<String, Integer>> bossPosList = bossBattleTeamService.getplayerPos(map.getGuaiList(), false);
		int guai_count = bossPosList.size();
		int[] guai_posX = new int[guai_count]; // 怪的x轴坐标
		int[] guai_posY = new int[guai_count]; // 怪的Y轴坐标
		int[] guai_id = new int[guai_count]; // 怪在怪表中的id
		String[] guai_name = new String[guai_count]; // 怪的名字
		int[] guai_sex = new int[guai_count]; // 怪的性别 0:男 1:女
		String[] guai_suit_head = new String[guai_count]; // 怪的着装头(如果type==1时,值为stand)
		String[] guai_suit_face = new String[guai_count]; // 怪的着装脸(如果type==1时,值为stand)
		String[] guai_suit_body = new String[guai_count]; // 怪的着装身(如果type==1时,值为stand)
		String[] guai_suit_weapon = new String[guai_count]; // 怪的着装武器(如果type==1时,值为stand)
		int[] guai_weapon_type = new int[guai_count]; // 怪的武器类型
		int[] guai_type = new int[guai_count]; // 怪的类型0:有着装的小怪 1:没有着装的小怪 2:boss
		int[] guai_attacktype = new int[guai_count]; // 怪的攻击类型 0:会远近攻 1:只会远攻
														// 2:只会近攻 3:不会攻击不会移动
														// (type==2时,即大boss一定是0)
		int[] guai_level = new int[guai_count]; // 怪的等级
		int[] guai_sp = new int[guai_count]; // 怪的怒气值
		int[] guai_pf = new int[guai_count]; // 怪的体力
		int[] guai_hp = new int[guai_count]; // 怪的生命值
		int[] guai_defend = new int[guai_count]; // 怪的防御力
		int[] guai_attack = new int[guai_count]; // 怪的攻击力
		int[] guai_physique = new int[guai_count]; // 怪的体质
		int[] guai_p_force = new int[guai_count]; // 怪的力量
		int[] guai_armor = new int[guai_count]; // 怪的护甲
		int[] guai_agility = new int[guai_count]; // 怪的敏捷
		int[] guai_luck = new int[guai_count]; // 怪的幸运
		int[] guai_injuryFree = new int[guai_count]; // 怪的免伤(10000)
		int[] guai_wreckDefense = new int[guai_count]; // 怪的破防(10000)
		int[] guai_reduceCrit = new int[guai_count]; // 怪的免暴(10000)
		int[] guai_reduceBury = new int[guai_count]; // 怪的免坑(10000)
		int[] guai_attackArea = new int[guai_count]; // 怪的攻击范围
		int[] guai_criticalRate = new int[guai_count]; // 怪的暴击率 万份比数值(放大一万陪)
		String[] guai_AniFileId = new String[guai_count]; // 动画文件id.格式:[boss[id]]或[guai[id]],如:boss1,guai1
		String[] guai_ai = new String[guai_count]; // 怪的Ai控制
		String[] guai_dialogue = new String[guai_count]; // 怪的对话文本
		int i = 0;
		for (Map<String, Integer> m : bossPosList) {
			GuaiPlayer guaiPlayer = guaiService.getGuaiById(1, m.get("id").intValue());
			Guai guai = guaiPlayer.getGuai();
			guai_posX[i] = m.get("posX");
			guai_posY[i] = m.get("posY");
			guai_id[i] = guai.getGuaiId();
			guai_name[i] = guai.getName();
			guai_sex[i] = guai.getSex();
			guai_suit_head[i] = guai.getSuit_head();
			guai_suit_face[i] = guai.getSuit_face();
			guai_suit_body[i] = guai.getSuit_body();
			guai_suit_weapon[i] = guai.getSuit_weapon();
			guai_weapon_type[i] = guai.getWeapon_type();
			guai_type[i] = guai.getType();
			guai_attacktype[i] = guai.getAttack_type();
			guai_level[i] = guai.getLevel();
			guai_sp[i] = guaiPlayer.getMaxSP();
			guai_pf[i] = guaiPlayer.getMaxPF();
			guai_hp[i] = guaiPlayer.getMaxHP();
			guai_defend[i] = guaiPlayer.getDefend();
			guai_attack[i] = guaiPlayer.getAttack();
			guai_physique[i] = guaiPlayer.getPhysique();
			guai_p_force[i] = guaiPlayer.getForce();
			guai_armor[i] = guaiPlayer.getArmor();
			guai_agility[i] = guaiPlayer.getAgility();
			guai_luck[i] = guaiPlayer.getLuck();
			guai_injuryFree[i] = guaiPlayer.getInjuryFree();
			guai_wreckDefense[i] = guaiPlayer.getWreckDefense();
			guai_reduceCrit[i] = guaiPlayer.getReduceCrit();
			guai_reduceBury[i] = guaiPlayer.getReduceBury();
			guai_attackArea[i] = guai.getAttackArea();
			guai_criticalRate[i] = guai.getCriticalRate();
			guai_AniFileId[i] = guai.getAniFileId();
			guai_ai[i] = guai.getGuai_ai();
			guai_dialogue[i] = guai.getDialogue();
			i++;
		}
		ok.setGuai_posX(guai_posX);
		ok.setGuai_posY(guai_posY);
		ok.setGuai_id(guai_id);
		ok.setGuai_name(guai_name);
		ok.setGuai_sex(guai_sex);
		ok.setGuai_suit_body(guai_suit_body);
		ok.setGuai_suit_face(guai_suit_face);
		ok.setGuai_suit_head(guai_suit_head);
		ok.setGuai_suit_weapon(guai_suit_weapon);
		ok.setGuai_weapon_type(guai_weapon_type);
		ok.setGuai_type(guai_type);
		ok.setGuai_attacktype(guai_attacktype);
		ok.setGuai_level(guai_level);
		ok.setGuai_sp(guai_sp);
		ok.setGuai_pf(guai_pf);
		ok.setGuai_hp(guai_hp);
		ok.setGuai_defend(guai_defend);
		ok.setGuai_attack(guai_attack);
		ok.setGuai_physique(guai_physique);
		ok.setGuai_p_force(guai_p_force);
		ok.setGuai_armor(guai_armor);
		ok.setGuai_agility(guai_agility);
		ok.setGuai_luck(guai_luck);
		ok.setGuai_injuryFree(guai_injuryFree);
		ok.setGuai_wreckDefense(guai_wreckDefense);
		ok.setGuai_reduceCrit(guai_reduceCrit);
		ok.setGuai_reduceBury(guai_reduceBury);
		ok.setGuai_attackArea(guai_attackArea);
		ok.setGuai_criticalRate(guai_criticalRate);
		ok.setGuai_AniFileId(guai_AniFileId);
		ok.setGuai_ai(guai_ai);
		ok.setGuai_dialogue(guai_dialogue);
	}

	private void setPlayerInfo(StartChallengeOk ok, WorldPlayer player, com.wyd.empire.world.bean.Map map) {
		int posX = -1;// 坐标X
		int posY = -1;// 坐标X
		String playerName = new String();
		int playerLevel = 0;
		int boyOrGirl = 0;
		String suit_head = new String();
		String suit_face = new String();
		String suit_body = new String();
		String suit_weapon = new String();
		String suit_wing = new String();
		String player_title = new String();
		int weapon_type = 0;
		int maxHP = 0;
		int maxPF = 0;
		int maxSP = 0;
		int attack = 0;
		int bigSkillAttack = 0;
		int crit = 0;
		int defence = 0;
		int bigSkillType = 0;
		int explodeRadius = 0;
		int injuryFree = 0;
		int wreckDefense = 0;
		int reduceCrit = 0;
		int reduceBury = 0;
		int zsleve = 0;
		int skillful = 0;

		int force = 0;
		int armor = 0;

		List<Integer> buffType = new ArrayList<Integer>();
		List<Integer> buffParam = new ArrayList<Integer>();
		maxHP = player.getMaxHP();
		maxPF = player.getMaxPF();
		maxSP = player.getMaxSP();
		attack = player.getAttack();
		injuryFree = player.getInjuryFree();
		wreckDefense = player.getWreckDefense();
		reduceCrit = player.getReduceCrit();
		reduceBury = player.getReduceBury();
		bigSkillAttack = player.getBigSkillAttack();
		crit = player.getCrit();
		defence = player.getDefend();
		bigSkillType = player.getBigSkillType();
		explodeRadius = player.getExplodeRadius();
		zsleve = player.getPlayer().getZsLevel();
		skillful = player.getProficiency();
		playerName = player.getName();
		playerLevel = player.getLevel();
		boyOrGirl = player.getPlayer().getSex();
		suit_head = player.getSuit_head();
		suit_face = player.getSuit_face();
		suit_body = player.getSuit_body();
		suit_weapon = player.getSuit_weapon();
		suit_weapon = suit_weapon == null ? "" : suit_weapon;
		suit_wing = player.getSuit_wing() == null ? "" : player.getSuit_wing();
		weapon_type = player.getWeapon_type();
		player_title = player.getPlayerTitle();

		force = player.getForce();
		armor = player.getArmor();
		int constitution = player.getPhysique();
		int agility = player.getAgility();
		int lucky = player.getLuck();
		List<Buff> buffList = player.getBuffList();
		for (Buff b : buffList) {
			if (b.getBuffCode().equals("cpower")) {
				buffType.add(b.getBufftype());
				buffParam.add(b.getQuantity());
			}
			if (b.getBuffCode().equals("ccrit")) {
				buffType.add(b.getBufftype());
				buffParam.add(b.getQuantity());
			}
			if (b.getBuffCode().equals("churt")) {
				buffType.add(b.getBufftype());
				buffParam.add(b.getQuantity());
			}
			if (b.getBuffCode().equals("cpowerlow")) {
				buffType.add(b.getBufftype());
				buffParam.add(b.getQuantity());
			}
		}
		ok.setPlayer_community(player.getGuildName());
		ok.setBossMapName(map.getName());
		ok.setBattleMap(map.getAnimationIndexCode());
		ok.setPosX(posX);
		ok.setPosY(posY);
		ok.setSuit_head(suit_head);
		ok.setSuit_face(suit_face);
		ok.setSuit_body(suit_body);
		ok.setSuit_weapon(suit_weapon);
		ok.setSuit_wing(suit_wing);
		ok.setWeapon_type(weapon_type);
		ok.setMaxHP(maxHP);
		ok.setMaxPF(maxPF);
		ok.setMaxSP(maxSP);
		ok.setAttack(attack);
		ok.setInjuryFree(injuryFree);
		ok.setWreckDefense(wreckDefense);
		ok.setReduceCrit(reduceCrit);
		ok.setReduceBury(reduceBury);
		ok.setZsleve(zsleve);
		ok.setSkillful(skillful);
		ok.setBigSkillAttack(bigSkillAttack);
		ok.setCrit(crit);
		ok.setDefence(defence);
		ok.setBigSkillType(bigSkillType);
		ok.setExplodeRadius(explodeRadius);
		ok.setPlayer_title(player_title);
		ok.setBuffType(ServiceUtils.getInts(buffType.toArray()));
		ok.setBuffParam(ServiceUtils.getInts(buffParam.toArray()));
		ok.setPlayerName(playerName);
		ok.setPlayerLevel(playerLevel);
		ok.setBoyOrGirl(boyOrGirl);
		ok.setArmor(armor);
		ok.setForce(force);
		ok.setConstitution(constitution);
		ok.setAgility(agility);
		ok.setLucky(lucky);
	}

	/**
	 * 一共8个位置，前4个为技能后4个为道具
	 * 
	 * @param ok
	 * @param combat
	 */
	private void setPlayerTool(StartChallengeOk ok, WorldPlayer player) {
		int playerCount = 1, z = 0;
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
		for (int y = 0; y < 4; y++) {
			item_used[z] = player.getItemJnused()[y];
			if (item_used[z] > 0) {
				Tools tools = player.getPlayerJNs().get(y);
				item_id[z] = tools.getId();
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
			item_used[z] = player.getItemDjused()[y];
			if (item_used[z] > 0) {
				Tools tools = player.getPlayerDJs().get(y);
				item_id[z] = tools.getId();
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
		ok.setItem_id(item_id);
		ok.setItem_used(item_used);
		ok.setItem_img(item_img);
		ok.setItem_name(item_name);
		ok.setItem_desc(item_desc);
		ok.setItem_type(item_type);
		ok.setItem_subType(item_subType);
		ok.setItem_param1(item_param1);
		ok.setItem_param2(item_param2);
		ok.setItem_ConsumePower(item_ConsumePower);
		ok.setSpecialAttackParam(specialAttackParam);
		ok.setSpecialAttackType(specialAttackType);
	}

	private void setPetInfo(StartChallengeOk ok, WorldPlayer player) {
		int petId = 0;
		String petIcon = new String();
		int petType = 0;
		int petSkillId = 0;
		int petProbability = 0;
		int petParam1 = 0;
		int petParam2 = 0;
		String petEffect = new String();
		int petVersion = 2;
		PlayerPet playerPet = player.getPlayerPet();
		if (null != playerPet) {
			petId = playerPet.getPet().getId();
			petIcon = playerPet.getPet().getIcon();
			petType = playerPet.getSkill().getType();
			petSkillId = playerPet.getSkill().getId();
			petProbability = playerPet.getSkill().getUseChance();
			petParam1 = playerPet.getSkill().getParam1();
			petParam2 = playerPet.getSkill().getParam2();
			petEffect = playerPet.getSkill().getEffect();
			petVersion = playerPet.getPet().getVersion();
		} else {
			petIcon = "";
			petEffect = "";
		}

		ok.setPetId(petId);
		ok.setPetIcon(petIcon);
		ok.setPetType(petType);
		ok.setPetSkillId(petSkillId);
		ok.setPetProbability(petProbability);
		ok.setPetParam1(petParam1);
		ok.setPetParam2(petParam2);
		ok.setPetEffect(petEffect);
		ok.setPetVersion(petVersion);
	}

	private void setWeaponSkill(StartChallengeOk ok, WorldPlayer player) {
		List<Integer> weaponSkillPlayerId = new ArrayList<Integer>();
		List<String> weaponSkillName = new ArrayList<String>();
		List<Integer> weaponSkillType = new ArrayList<Integer>();
		List<Integer> weaponSkillChance = new ArrayList<Integer>();
		List<Integer> weaponSkillParam1 = new ArrayList<Integer>();
		List<Integer> weaponSkillParam2 = new ArrayList<Integer>();
		WeapSkill wp;
		PlayerItemsFromShop pifs = player.getWeapon();// getSuitWeapon();//manager.getPlayerItemsFromShopService().getPlayerItemsFromShopByPlayerIdAndItemId(player,
														// player.getSuitWeaponId());
		if (pifs.getWeapSkill1() != 0) {
			wp = manager.getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
			weaponSkillPlayerId.add(pifs.getPlayerId());
			weaponSkillName.add(wp.getSkillName());
			weaponSkillType.add(wp.getType());
			weaponSkillChance.add(wp.getUseChance());
			weaponSkillParam1.add(wp.getParam1());
			weaponSkillParam2.add(wp.getParam2());
		}
		if (pifs.getWeapSkill2() != 0) {
			wp = manager.getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
			weaponSkillPlayerId.add(pifs.getPlayerId());
			weaponSkillName.add(wp.getSkillName());
			weaponSkillType.add(wp.getType());
			weaponSkillChance.add(wp.getUseChance());
			weaponSkillParam1.add(wp.getParam1());
			weaponSkillParam2.add(wp.getParam2());
		}
		String[] strings = new String[weaponSkillName.size()];
		ok.setWeaponSkillPlayerId(ServiceUtils.getInts(weaponSkillPlayerId.toArray()));
		ok.setWeaponSkillChance(ServiceUtils.getInts(weaponSkillChance.toArray()));
		ok.setWeaponSkillName(weaponSkillName.toArray(strings));
		ok.setWeaponSkillParam1(ServiceUtils.getInts(weaponSkillParam1.toArray()));
		ok.setWeaponSkillParam2(ServiceUtils.getInts(weaponSkillParam2.toArray()));
		ok.setWeaponSkillType(ServiceUtils.getInts(weaponSkillType.toArray()));
	}
}
