package com.wyd.empire.world.server.handler.cross;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.cross.CrossPairOk;
import com.wyd.empire.protocol.data.cross.CrossSendPlayerInfo;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跨服对战配对成功
 * 
 * @author zguoqiu
 */
public class CrossPairOkHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossPairOkHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossPairOk pairOk = (CrossPairOk) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(pairOk.getRoomId());
			if (null != room) {
				List<WorldPlayer> playerList = new ArrayList<WorldPlayer>();
				List<Boolean> robotList = new ArrayList<Boolean>();
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()) {
						playerList.add(seat.getPlayer());
						robotList.add(seat.isRobot());
					}
				}
				String animationIndexCode = ServiceManager.getManager().getMapsService().getRandomMap(room.getRoomChannel())
						.getAnimationIndexCode();
				int battleId = pairOk.getBattleId();
				int playerCount = playerList.size();
				int[] playerId = new int[playerCount];
				int[] roomIds = new int[playerCount];
				String[] playerName = new String[playerCount];
				int[] playerLevel = new int[playerCount];
				int[] boyOrGirl = new int[playerCount];
				String[] suit_head = new String[playerCount];
				String[] suit_face = new String[playerCount];
				String[] suit_body = new String[playerCount];
				String[] suit_weapon = new String[playerCount];
				int[] weapon_type = new int[playerCount];
				String[] suit_wing = new String[playerCount];
				String[] player_title = new String[playerCount];
				String[] player_community = new String[playerCount];
				int[] maxHP = new int[playerCount];
				int[] maxPF = new int[playerCount];
				int[] maxSP = new int[playerCount];
				int[] attack = new int[playerCount];
				int[] bigSkillAttack = new int[playerCount];
				int[] critRate = new int[playerCount];
				int[] defence = new int[playerCount];
				int[] bigSkillType = new int[playerCount];
				int[] explodeRadius = new int[playerCount];
				int[] injuryFree = new int[playerCount]; // 免伤
				int[] wreckDefense = new int[playerCount]; // 破防
				int[] reduceCrit = new int[playerCount]; // 免暴
				int[] reduceBury = new int[playerCount]; // 免坑
				int[] zsleve = new int[playerCount]; // 玩家转生等级
				int[] skillful = new int[playerCount]; // 玩家武器熟练度
				int[] petId = new int[playerCount]; // 宠物id，0表示无宠物
				String[] petIcon = new String[playerCount]; // 宠物icon
				int[] petType = new int[playerCount]; // 宠物类型
				int[] petSkillId = new int[playerCount]; // 宠物技能id
				int[] petProbability = new int[playerCount]; // 宠物攻击概率
				int[] petParam1 = new int[playerCount]; // 宠物参数1
				int[] petParam2 = new int[playerCount]; // 宠物参数2
				String[] petEffect = new String[playerCount]; // 宠物攻击特效名称
				int[] petVersion = new int[playerCount]; // 宠物在客户端执行的版本1旧版，2新版
				boolean[] robot = new boolean[playerCount]; // 是否机器人
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
				int[] playerBuffCount = new int[playerCount]; // 表示每一个player,buff的数量,如果没有要填零
				List<Integer> buffType = new ArrayList<Integer>(); // 按角色顺序填入
				List<Integer> buffParam1 = new ArrayList<Integer>(); // 不同类型参数意义不同(按角色顺序填入)
				List<Integer> buffParam2 = new ArrayList<Integer>(); // 预留参数(按角色顺序填入)
				List<Integer> buffParam3 = new ArrayList<Integer>(); // 预留参数(按角色顺序填入)
				int[] playerWeaponSkillCount = new int[playerCount]; // 玩家的武器技能数量
				List<String> weaponSkillName = new ArrayList<String>(); // 武器技能名称
				List<Integer> weaponSkillType = new ArrayList<Integer>(); // 武器技能类型
				List<Integer> weaponSkillChance = new ArrayList<Integer>(); // 武器技能出现概率(0~10000)
				List<Integer> weaponSkillParam1 = new ArrayList<Integer>(); // 武器技能参数1
				List<Integer> weaponSkillParam2 = new ArrayList<Integer>(); // 武器技能参数2
				int[] force = new int[playerCount]; // 力量
				int[] armor = new int[playerCount]; // 护甲
				int[] fighting = new int[playerCount]; // 玩家的战斗力
				int[] winRate = new int[playerCount]; // 玩家的胜率(万分比)
				int[] constitution = new int[playerCount]; // 体质
				int[] agility = new int[playerCount]; // 敏捷
				int[] lucky = new int[playerCount]; // 幸运
				PlayerItemsFromShop pifs;
				WorldPlayer player;
				WeapSkill wp;
				int itemIndex = 0;
				for (int i = 0; i < playerCount; i++) {
					player = playerList.get(i);
					playerId[i] = player.getId();
					roomIds[i] = room.getRoomId();
					playerName[i] = player.getName();
					playerLevel[i] = player.getLevel();
					boyOrGirl[i] = player.getPlayer().getSex();
					suit_head[i] = player.getSuit_head();
					suit_face[i] = player.getSuit_face();
					suit_body[i] = player.getSuit_body();
					suit_weapon[i] = player.getSuit_weapon();
					weapon_type[i] = player.getWeapon_type();
					suit_wing[i] = player.getSuit_wing();
					player_title[i] = player.getPlayerTitle();
					player_community[i] = player.getGuildName();
					maxHP[i] = player.getMaxHP();
					maxPF[i] = player.getMaxPF();
					maxSP[i] = player.getMaxSP();
					attack[i] = player.getAttack();
					bigSkillAttack[i] = player.getBigSkillAttack();
					critRate[i] = player.getCrit();
					defence[i] = player.getDefend();
					bigSkillType[i] = player.getBigSkillType();
					explodeRadius[i] = player.getExplodeRadius();
					injuryFree[i] = player.getInjuryFree();
					wreckDefense[i] = player.getWreckDefense();
					reduceCrit[i] = player.getReduceCrit();
					reduceBury[i] = player.getReduceBury();
					zsleve[i] = player.getPlayer().getZsLevel();
					skillful[i] = player.getProficiency();
					PlayerPet playerPet = player.getPlayerPet();
					force[i] = player.getForce();
					armor[i] = player.getArmor();
					fighting[i] = player.getFighting();
					winRate[i] = player.getWinRate();
					constitution[i] = player.getPhysique();
					agility[i] = player.getAgility();
					lucky[i] = player.getLuck();
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
					robot[i] = robotList.get(i);
					for (int y = 0; y < 4; y++) {
						item_used[itemIndex] = player.getItemJnused()[y];
						if (item_used[itemIndex] > 0) {
							Tools tools = player.getPlayerJNs().get(y);
							item_id[itemIndex] = tools.getId();
							item_img[itemIndex] = tools.getIcon();
							item_name[itemIndex] = tools.getName();
							item_desc[itemIndex] = tools.getDesc();
							item_type[itemIndex] = tools.getType();
							item_subType[itemIndex] = tools.getSubtype();
							item_param1[itemIndex] = tools.getParam1();
							item_param2[itemIndex] = tools.getParam2();
							item_ConsumePower[itemIndex] = tools.getConsumePower();
							specialAttackType[itemIndex] = tools.getSpecialAttackType();
							specialAttackParam[itemIndex] = tools.getSpecialAttackParam();
						} else {
							item_img[itemIndex] = "0";
							item_name[itemIndex] = "0";
							item_desc[itemIndex] = "0";
						}
						itemIndex++;
					}
					for (int y = 0; y < 4; y++) {
						item_used[itemIndex] = player.getItemDjused()[y];
						if (item_used[itemIndex] > 0) {
							Tools tools = player.getPlayerDJs().get(y);
							item_id[itemIndex] = tools.getId();
							item_img[itemIndex] = tools.getIcon();
							item_name[itemIndex] = tools.getName();
							item_desc[itemIndex] = tools.getDesc();
							item_type[itemIndex] = tools.getType();
							item_subType[itemIndex] = tools.getSubtype();
							item_param1[itemIndex] = tools.getParam1();
							item_param2[itemIndex] = tools.getParam2();
							item_ConsumePower[itemIndex] = tools.getConsumePower();
							specialAttackType[itemIndex] = tools.getSpecialAttackType();
							specialAttackParam[itemIndex] = tools.getSpecialAttackParam();
						} else {
							item_img[itemIndex] = "0";
							item_name[itemIndex] = "0";
							item_desc[itemIndex] = "0";
						}
						itemIndex++;
					}
					int buffCount = 0;
					for (Buff b : player.getBuffList()) {
						if (b.getBuffCode().equals("cpower")) {
							buffType.add(b.getBufftype());
							buffParam1.add(b.getQuantity());
							buffParam2.add(0);
							buffParam3.add(0);
							buffCount++;
						}
						if (b.getBuffCode().equals("ccrit")) {
							buffType.add(b.getBufftype());
							buffParam1.add(b.getQuantity());
							buffParam2.add(0);
							buffParam3.add(0);
							buffCount++;
						}
						if (b.getBuffCode().equals("churt")) {
							buffType.add(b.getBufftype());
							buffParam1.add(b.getQuantity());
							buffParam2.add(0);
							buffParam3.add(0);
							buffCount++;
						}
					}
					playerBuffCount[i] = buffCount;
					pifs = player.getWeapon();// player.getPlayer().getSuitWeapon();//ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemsFromShopByPlayerIdAndItemId(player.getPlayer(),
												// player.getPlayer().getSuitWeaponId());
					int skillCount = 0;
					if (pifs.getWeapSkill1() != 0) {
						wp = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
						weaponSkillName.add(wp.getSkillName());
						weaponSkillType.add(wp.getType());
						weaponSkillChance.add(wp.getUseChance());
						weaponSkillParam1.add(wp.getParam1());
						weaponSkillParam2.add(wp.getParam2());
						skillCount++;
					}
					if (pifs.getWeapSkill2() != 0) {
						wp = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
						weaponSkillName.add(wp.getSkillName());
						weaponSkillType.add(wp.getType());
						weaponSkillChance.add(wp.getUseChance());
						weaponSkillParam1.add(wp.getParam1());
						weaponSkillParam2.add(wp.getParam2());
						skillCount++;
					}
					playerWeaponSkillCount[i] = skillCount;
				}

				CrossSendPlayerInfo sendPlayerInfo = new CrossSendPlayerInfo();
				sendPlayerInfo.setRoomId(room.getRoomId());
				sendPlayerInfo.setAnimationIndexCode(animationIndexCode);
				sendPlayerInfo.setBattleId(battleId);
				sendPlayerInfo.setPlayerId(playerId);
				sendPlayerInfo.setRoomIds(roomIds);
				sendPlayerInfo.setPlayerName(playerName);
				sendPlayerInfo.setPlayerLevel(playerLevel);
				sendPlayerInfo.setBoyOrGirl(boyOrGirl);
				sendPlayerInfo.setSuit_head(suit_head);
				sendPlayerInfo.setSuit_face(suit_face);
				sendPlayerInfo.setSuit_body(suit_body);
				sendPlayerInfo.setSuit_weapon(suit_weapon);
				sendPlayerInfo.setWeapon_type(weapon_type);
				sendPlayerInfo.setMaxHP(maxHP);
				sendPlayerInfo.setMaxPF(maxPF);
				sendPlayerInfo.setMaxSP(maxSP);
				sendPlayerInfo.setAttack(attack);
				sendPlayerInfo.setBigSkillAttack(bigSkillAttack);
				sendPlayerInfo.setCritRate(critRate);
				sendPlayerInfo.setDefence(defence);
				sendPlayerInfo.setBigSkillType(bigSkillType);
				sendPlayerInfo.setExplodeRadius(explodeRadius);
				sendPlayerInfo.setItem_id(item_id);
				sendPlayerInfo.setItem_used(item_used);
				sendPlayerInfo.setItem_img(item_img);
				sendPlayerInfo.setItem_name(item_name);
				sendPlayerInfo.setItem_desc(item_desc);
				sendPlayerInfo.setItem_type(item_type);
				sendPlayerInfo.setItem_subType(item_subType);
				sendPlayerInfo.setItem_param1(item_param1);
				sendPlayerInfo.setItem_param2(item_param2);
				sendPlayerInfo.setItem_ConsumePower(item_ConsumePower);
				sendPlayerInfo.setSpecialAttackType(specialAttackType);
				sendPlayerInfo.setSpecialAttackParam(specialAttackParam);
				sendPlayerInfo.setPlayerBuffCount(playerBuffCount);
				sendPlayerInfo.setBuffType(ServiceUtils.getInts(buffType.toArray()));
				sendPlayerInfo.setBuffParam1(ServiceUtils.getInts(buffParam1.toArray()));
				sendPlayerInfo.setBuffParam2(ServiceUtils.getInts(buffParam2.toArray()));
				sendPlayerInfo.setBuffParam3(ServiceUtils.getInts(buffParam3.toArray()));
				sendPlayerInfo.setSuit_wing(suit_wing);
				sendPlayerInfo.setPlayer_title(player_title);
				sendPlayerInfo.setPlayer_community(player_community);
				sendPlayerInfo.setPlayerWeaponSkillCount(playerWeaponSkillCount);
				sendPlayerInfo.setWeaponSkillName(weaponSkillName.toArray(new String[]{}));
				sendPlayerInfo.setWeaponSkillType(ServiceUtils.getInts(weaponSkillType.toArray()));
				sendPlayerInfo.setWeaponSkillChance(ServiceUtils.getInts(weaponSkillChance.toArray()));
				sendPlayerInfo.setWeaponSkillParam1(ServiceUtils.getInts(weaponSkillParam1.toArray()));
				sendPlayerInfo.setWeaponSkillParam2(ServiceUtils.getInts(weaponSkillParam2.toArray()));
				sendPlayerInfo.setInjuryFree(injuryFree);
				sendPlayerInfo.setWreckDefense(wreckDefense);
				sendPlayerInfo.setReduceCrit(reduceCrit);
				sendPlayerInfo.setReduceBury(reduceBury);
				sendPlayerInfo.setZsleve(zsleve);
				sendPlayerInfo.setSkillful(skillful);
				sendPlayerInfo.setPetId(petId);
				sendPlayerInfo.setPetIcon(petIcon);
				sendPlayerInfo.setPetType(petType);
				sendPlayerInfo.setPetSkillId(petSkillId);
				sendPlayerInfo.setPetProbability(petProbability);
				sendPlayerInfo.setPetParam1(petParam1);
				sendPlayerInfo.setPetParam2(petParam2);
				sendPlayerInfo.setPetEffect(petEffect);
				sendPlayerInfo.setPetVersion(petVersion);
				sendPlayerInfo.setRobot(robot);
				sendPlayerInfo.setServerName(WorldServer.config.getServerName());
				sendPlayerInfo.setForce(force);
				sendPlayerInfo.setArmor(armor);
				sendPlayerInfo.setFighting(fighting);
				sendPlayerInfo.setWinRate(winRate);
				sendPlayerInfo.setConstitution(constitution);
				sendPlayerInfo.setAgility(agility);
				sendPlayerInfo.setLucky(lucky);
				ServiceManager.getManager().getCrossService().send(sendPlayerInfo);
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}