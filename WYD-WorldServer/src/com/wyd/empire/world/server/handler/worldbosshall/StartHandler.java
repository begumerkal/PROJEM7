package com.wyd.empire.world.server.handler.worldbosshall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.worldbosshall.Start;
import com.wyd.empire.protocol.data.worldbosshall.StartOk;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.Guai;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.server.service.impl.WorldBossRoomService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.worldbosshall.WorldBossRoom;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始世界BOSS 世界BOSS使用CombatGuai里的 HP
 * 
 * @author zengxc
 * 
 */
public class StartHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	IWorldBossService worldBossService = null;
	MapsService mapsService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		mapsService = manager.getMapsService();
		WorldBossRoomService roomService = WorldBossRoomService.getInstance();

		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			Start start = (Start) data;
			int playerId = player.getId();
			int playerCount = 1;// 世界BOSS战斗里只有一个玩家
			int[] roomIds = new int[playerCount];
			String[] player_title = new String[playerCount];
			String[] player_community = new String[playerCount];
			worldBossService = manager.getWorldBossService();

			int roomId = start.getRoomId(), i = 0;
			WorldBossRoom room = roomService.getRoomById(roomId);

			// 如果还没有开放则同时返回一个错误：提示玩家未开放
			boolean isOpen = worldBossService.isInTime();
			if (!isOpen || (room != null && !room.isOpen())) {
				throw new ProtocolException(TipMessages.WORLDBOSS_NOOPEN, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 验证CDTime
			int cdTime = worldBossService.getCDTime(playerId, room.getMapId());
			if (cdTime > 0) {
				throw new ProtocolException(ErrorMessages.ROOM_COLD_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			roomIds[0] = roomId;
			/** 进入战斗 **/
			int battleId = worldBossService.enter(player, roomId);
			if (battleId > 0) {

				Combat combat = room.findPlayer(playerId);
				player_title[i] = combat.getTitle();
				player_community[i] = combat.getGuildName();
				StartOk ok = new StartOk(data.getSessionId(), data.getSerial());
				com.wyd.empire.world.bean.Map map = mapsService.getWorldBossMapById(room.getMapId());
				ok.setBattlemap_icon(map.getMapIcon());
				/** 设置玩家信息 */
				setPlayerInfo(ok, combat, map);
				/** 设置BOSS信息 **/
				setBossInfo(ok, room.getBoss());
				/** 设置武器技能 */
				setWeaponSkill(ok, player);
				/** 设置宠物信息 */
				setPetInfo(ok, player);
				/** 设置工具栏信息 */
				setPlayerTool(ok, combat);
				/** Buff */
				setBuff(ok, player.getBuffList());
				ok.setBattleId(battleId);// 世界BOSS战斗ID
				ok.setRoomId(roomIds);
				ok.setIdcount(10);
				ok.setPlayer_title(player_title);
				ok.setPlayer_community(player_community);
				ok.setDifficulty(1);
				BossBattleTeam bossBattleTeam = manager.getBossBattleTeamService().getBattleTeam(battleId);
				bossBattleTeam.setSkillHurt(ok.getSkillHurt());
				session.write(ok);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.WORLDBOSS_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private void setBuff(StartOk ok, List<Buff> buffList) {
		int[] playerBuffCount = new int[1];
		List<Integer> buffType = new ArrayList<Integer>();
		List<Integer> buffParam1 = new ArrayList<Integer>();
		int[] buffParam2 = new int[0];
		int[] buffParam3 = new int[0];
		int playerIndex = 0;
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
		}
		playerBuffCount[playerIndex] = buffcount;
		ok.setPlayerBuffCount(playerBuffCount);
		ok.setBuffType(ServiceUtils.getInts(buffType.toArray()));
		ok.setBuffParam1(ServiceUtils.getInts(buffParam1.toArray()));
		ok.setBuffParam2(buffParam2);
		ok.setBuffParam3(buffParam3);
	}

	private void setPlayerInfo(StartOk ok, Combat combat, com.wyd.empire.world.bean.Map map) {

		int playerCount = 1, i = 0;
		int[] playerIds = new int[playerCount];

		int[] posX = new int[playerCount];// 坐标X
		int[] posY = new int[playerCount];// 坐标X
		String[] playerName = new String[playerCount];
		int[] playerLevel = new int[playerCount];
		int[] boyOrGirl = new int[playerCount];
		String[] suit_head = new String[playerCount];
		String[] suit_face = new String[playerCount];
		String[] suit_body = new String[playerCount];
		String[] suit_weapon = new String[playerCount];
		String[] suit_wing = new String[playerCount];
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
		int[] zsleve = new int[playerCount];
		int[] skillful = new int[playerCount];

		int[] force = new int[playerCount];
		int[] armor = new int[playerCount];
		int[] constitution = new int[playerCount];
		int[] agility = new int[playerCount];
		int[] lucky = new int[playerCount];

		BossBattleTeamService bossBattleTeamService = manager.getBossBattleTeamService();
		List<Map<String, Integer>> posList = bossBattleTeamService.getplayerPos(map.getGuaiList(), true);
		int posIndex = ServiceUtils.getRandomNum(0, posList.size());
		posX[i] = posList.get(posIndex).get("posX");
		posY[i] = posList.get(posIndex).get("posY");
		playerIds[i] = combat.getId();
		maxHP[i] = combat.getMaxHP();
		maxPF[i] = combat.getMaxPF();
		maxSP[i] = combat.getMaxSP();
		attack[i] = combat.getAttack(map.getId());
		camp[i] = combat.getCamp();
		suit_wing[i] = combat.getSuit_wing();
		injuryFree[i] = combat.getInjuryFree();
		wreckDefense[i] = combat.getWreckDefense();
		reduceCrit[i] = combat.getReduceCrit();
		reduceBury[i] = combat.getReduceBury();
		bigSkillAttack[i] = combat.getBigSkillAttack();
		critRate[i] = combat.getCrit();
		defence[i] = combat.getDefend();
		bigSkillType[i] = combat.getBigSkillType();
		explodeRadius[i] = combat.getExplodeRadius();
		zsleve[i] = combat.getPlayer().getPlayer().getZsLevel();
		skillful[i] = combat.getProficiency();
		playerName[i] = combat.getName();
		playerLevel[i] = combat.getLevel();
		boyOrGirl[i] = combat.getSex();
		suit_head[i] = combat.getSuit_head();
		suit_face[i] = combat.getSuit_face();
		suit_body[i] = combat.getSuit_body();
		suit_weapon[i] = combat.getSuit_weapon();
		weapon_type[i] = combat.getWeapon_type();
		force[i] = combat.getPlayer().getForce();
		armor[i] = combat.getPlayer().getArmor();
		constitution[i] = combat.getPlayer().getPhysique();
		agility[i] = combat.getPlayer().getAgility();
		lucky[i] = combat.getPlayer().getLuck();
		ok.setBossMapName(map.getNameShort());
		ok.setBattleMap(map.getAnimationIndexCode());
		ok.setPlayerCount(playerCount);
		ok.setPlayerId(playerIds);
		ok.setPosX(posX);
		ok.setPosY(posY);
		ok.setPlayerName(playerName);
		ok.setPlayerLevel(playerLevel);
		ok.setBoyOrGirl(boyOrGirl);
		ok.setSuit_head(suit_head);
		ok.setSuit_face(suit_face);
		ok.setSuit_body(suit_body);
		ok.setSuit_weapon(suit_weapon);
		ok.setWeapon_type(weapon_type);
		ok.setCamp(camp);
		ok.setMaxHP(maxHP);
		ok.setMaxPF(maxPF);
		ok.setMaxSP(maxSP);
		ok.setAttack(attack);
		ok.setSuit_wing(suit_wing);
		ok.setInjuryFree(injuryFree);
		ok.setWreckDefense(wreckDefense);
		ok.setReduceCrit(reduceCrit);
		ok.setReduceBury(reduceBury);
		ok.setZsleve(zsleve);
		ok.setSkillful(skillful);
		ok.setBigSkillAttack(bigSkillAttack);
		ok.setCritRate(critRate);
		ok.setDefence(defence);
		ok.setBigSkillType(bigSkillType);
		ok.setExplodeRadius(explodeRadius);
		ok.setForce(force);
		ok.setArmor(armor);
		ok.setConstitution(constitution);
		ok.setLucky(lucky);
		ok.setAgility(agility);
	}

	/**
	 * 一共8个位置，前4个为技能后4个为道具
	 * 
	 * @param ok
	 * @param combat
	 */
	private void setPlayerTool(StartOk ok, Combat combat) {
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
			item_used[z] = combat.getItemJnused()[y];
			if (item_used[z] > 0) {
				Tools tools = combat.getPlayer().getPlayerJNs().get(y);
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
			item_used[z] = combat.getItemDjused()[y];
			if (item_used[z] > 0) {
				Tools tools = combat.getPlayer().getPlayerDJs().get(y);
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
		ok.setSpecialAttackType(specialAttackType);
		ok.setSpecialAttackParam(specialAttackParam);
	}

	private void setPetInfo(StartOk ok, WorldPlayer player) {
		int[] petId = new int[1];
		String[] petIcon = new String[1];
		int[] petType = new int[1];
		int[] petSkillId = new int[1];
		int[] petProbability = new int[1];
		int[] petParam1 = new int[1];
		int[] petParam2 = new int[1];
		String[] petEffect = new String[1];
		int[] petVersion = new int[1];
		PlayerPet playerPet = player.getPlayerPet();
		if (null != playerPet) {
			petId[0] = playerPet.getPet().getId();
			petIcon[0] = playerPet.getPet().getIcon();
			petType[0] = playerPet.getSkill().getType();
			petSkillId[0] = playerPet.getSkill().getId();
			petProbability[0] = playerPet.getSkill().getUseChance();
			petParam1[0] = playerPet.getSkill().getParam1();
			petParam2[0] = playerPet.getSkill().getParam2();
			petEffect[0] = playerPet.getSkill().getEffect();
			petVersion[0] = playerPet.getPet().getVersion();
		} else {
			petIcon[0] = "";
			petEffect[0] = "";
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

	private void setWeaponSkill(StartOk ok, WorldPlayer player) {
		List<Integer> weaponSkillPlayerId = new ArrayList<Integer>();
		List<String> weaponSkillName = new ArrayList<String>();
		List<Integer> weaponSkillType = new ArrayList<Integer>();
		List<Integer> weaponSkillChance = new ArrayList<Integer>();
		List<Integer> weaponSkillParam1 = new ArrayList<Integer>();
		List<Integer> weaponSkillParam2 = new ArrayList<Integer>();
		WeapSkill wp;
		PlayerItemsFromShop pifs = player.getWeapon();// manager.getPlayerItemsFromShopService().getPlayerItemsFromShopByPlayerIdAndItemId(player,
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

	private void setBossInfo(StartOk ok, Combat boss) {
		// 创建怪的信息
		int guaiCount = 1;
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
		int[] guai_build_guai_id = new int[1]; // 放出的小怪在怪表中的id
		int[] build_guai_id_list = new int[1]; // 如果招唤小怪就会给其这些后续的id,是不会与之前的哪此冲突的
		int[] guai_injuryFree = new int[guaiCount]; // 免伤
		int[] guai_wreckDefense = new int[guaiCount]; // 破防
		int[] guai_reduceCrit = new int[guaiCount]; // 免暴
		int[] guai_reduceBury = new int[guaiCount]; // 免坑
		List<Integer> skillHurt = new ArrayList<Integer>(); // boss的技能伤害
		int i = 0;
		Guai guaiTable = boss.getGuai().getGuai();
		guaiBattleId[i] = boss.getBattleId();
		guaiId[i] = boss.getGuai().getGuai().getGuaiId();
		guaiposX[i] = (int) boss.getX();
		guaiposY[i] = (int) boss.getY();
		guai_name[i] = boss.getGuai().getGuai().getName();
		guai_camp[i] = boss.getCamp();
		guai_sex[i] = guaiTable.getSex();
		guai_suit_head[i] = guaiTable.getSuit_head();
		guai_suit_face[i] = guaiTable.getSuit_face();
		guai_suit_body[i] = guaiTable.getSuit_body();
		guai_suit_weapon[i] = guaiTable.getSuit_weapon();
		guai_weapon_type[i] = guaiTable.getWeapon_type();
		guai_type[i] = guaiTable.getType();
		guai_level[i] = guaiTable.getLevel();
		guai_attacktype[i] = guaiTable.getAttack_type();
		// 世界BOSS使用CombatGuai里的 HP
		guai_hp[i] = boss.getHp();
		guai_sp[i] = boss.getMaxSP();
		guai_pf[i] = boss.getMaxPF();
		guai_defend[i] = boss.getDefend();
		guai_attack[i] = boss.getAttack(0);
		guai_attackArea[i] = guaiTable.getAttackArea();
		guai_criticalRate[i] = 0;
		guai_bigSkillType[i] = guaiTable.getBigSkillType();
		guai_explode[i] = guaiTable.getExplode();
		guai_broken[i] = guaiTable.getBroken();
		guai_AniFileId[i] = guaiTable.getAniFileId();
		guai_injuryFree[i] = 0;
		guai_wreckDefense[i] = 0;
		guai_reduceCrit[i] = 0;
		guai_reduceBury[i] = 0;

		skillHurt.add(guaiTable.getSkill_1());
		skillHurt.add(guaiTable.getSkill_2());
		skillHurt.add(guaiTable.getSkill_3());
		skillHurt.add(guaiTable.getSkill_4());
		skillHurt.add(guaiTable.getSkill_5());
		guai_could_build_guai[i] = 0;
		guai_build_guai_id[i] = 0;

		ok.setBuild_guai_id_list(build_guai_id_list);
		ok.setGuaiCount(guaiCount);
		ok.setGuaiBattleId(guaiBattleId);
		ok.setGuaiId(guaiId);
		ok.setGuaiposX(guaiposX);
		ok.setGuaiposY(guaiposY);
		ok.setGuai_name(guai_name);
		ok.setGuai_camp(guai_camp);
		ok.setGuai_sex(guai_sex);
		ok.setGuai_suit_head(guai_suit_head);
		ok.setGuai_suit_face(guai_suit_face);
		ok.setGuai_suit_body(guai_suit_body);
		ok.setGuai_suit_weapon(guai_suit_weapon);
		ok.setGuai_weapon_type(guai_weapon_type);
		ok.setGuai_type(guai_type);
		ok.setGuai_level(guai_level);
		ok.setGuai_attacktype(guai_attacktype);
		ok.setGuai_hp(guai_hp);
		ok.setGuai_sp(guai_sp);
		ok.setGuai_pf(guai_pf);
		ok.setGuai_defend(guai_defend);
		ok.setGuai_attack(guai_attack);
		ok.setGuai_attackArea(guai_attackArea);
		ok.setGuai_criticalRate(guai_criticalRate);
		ok.setGuai_bigSkillType(guai_bigSkillType);
		ok.setGuai_explode(guai_explode);
		ok.setGuai_broken(guai_broken);
		ok.setGuai_AniFileId(guai_AniFileId);
		ok.setGuai_could_build_guai(guai_could_build_guai);
		ok.setGuai_build_guai_id(guai_build_guai_id);
		ok.setGuai_injuryFree(guai_injuryFree);
		ok.setGuai_wreckDefense(guai_wreckDefense);
		ok.setGuai_reduceCrit(guai_reduceCrit);
		ok.setGuai_reduceBury(guai_reduceBury);
		ok.setSkillHurt(ServiceUtils.getInts(skillHurt.toArray()));

	}

}
