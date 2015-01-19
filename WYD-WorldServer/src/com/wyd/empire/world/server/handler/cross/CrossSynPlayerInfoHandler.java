package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.cross.CrossSynPlayerInfo;
import com.wyd.empire.protocol.data.room.MakePairOk;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 同步对战玩家信息
 * 
 * @author zguoqiu
 */
public class CrossSynPlayerInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossSynPlayerInfoHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossSynPlayerInfo synPlayerInfo = (CrossSynPlayerInfo) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(synPlayerInfo.getRoomId());
			if (null != room) {
				MakePairOk makePairOk = new MakePairOk();
				makePairOk.setBattleId(synPlayerInfo.getBattleId());
				makePairOk.setBattleMode(synPlayerInfo.getBattleMode());
				makePairOk.setBattleMap(synPlayerInfo.getBattleMap());
				makePairOk.setPlayerCount(synPlayerInfo.getPlayerId().length);
				makePairOk.setPlayerId(synPlayerInfo.getPlayerId());
				makePairOk.setRoomId(synPlayerInfo.getRoomIds());
				makePairOk.setPlayerName(synPlayerInfo.getPlayerName());
				makePairOk.setPlayerLevel(synPlayerInfo.getPlayerLevel());
				makePairOk.setBoyOrGirl(synPlayerInfo.getBoyOrGirl());
				makePairOk.setSuit_head(synPlayerInfo.getSuit_head());
				makePairOk.setSuit_face(synPlayerInfo.getSuit_face());
				makePairOk.setSuit_body(synPlayerInfo.getSuit_body());
				makePairOk.setSuit_weapon(synPlayerInfo.getSuit_weapon());
				makePairOk.setWeapon_type(synPlayerInfo.getWeapon_type());
				makePairOk.setCamp(synPlayerInfo.getCamp());
				makePairOk.setMaxHP(synPlayerInfo.getMaxHP());
				makePairOk.setMaxPF(synPlayerInfo.getMaxPF());
				makePairOk.setMaxSP(synPlayerInfo.getMaxSP());
				makePairOk.setAttack(synPlayerInfo.getAttack());
				makePairOk.setBigSkillAttack(synPlayerInfo.getBigSkillAttack());
				makePairOk.setCrit(synPlayerInfo.getCritRate());
				makePairOk.setDefence(synPlayerInfo.getDefence());
				makePairOk.setBigSkillType(synPlayerInfo.getBigSkillType());
				makePairOk.setExplodeRadius(synPlayerInfo.getExplodeRadius());
				makePairOk.setItem_id(synPlayerInfo.getItem_id());
				makePairOk.setItem_used(synPlayerInfo.getItem_used());
				makePairOk.setItem_img(synPlayerInfo.getItem_img());
				makePairOk.setItem_name(synPlayerInfo.getItem_name());
				makePairOk.setItem_desc(synPlayerInfo.getItem_desc());
				makePairOk.setItem_type(synPlayerInfo.getItem_type());
				makePairOk.setItem_subType(synPlayerInfo.getItem_subType());
				makePairOk.setItem_param1(synPlayerInfo.getItem_param1());
				makePairOk.setItem_param2(synPlayerInfo.getItem_param2());
				makePairOk.setItem_ConsumePower(synPlayerInfo.getItem_ConsumePower());
				makePairOk.setSpecialAttackType(synPlayerInfo.getSpecialAttackType());
				makePairOk.setSpecialAttackParam(synPlayerInfo.getSpecialAttackParam());
				makePairOk.setPlayerBuffCount(synPlayerInfo.getPlayerBuffCount());
				makePairOk.setBuffType(synPlayerInfo.getBuffType());
				makePairOk.setBuffParam1(synPlayerInfo.getBuffParam1());
				makePairOk.setBuffParam2(synPlayerInfo.getBuffParam2());
				makePairOk.setBuffParam3(synPlayerInfo.getBuffParam3());
				makePairOk.setSuit_wing(synPlayerInfo.getSuit_wing());
				makePairOk.setPlayer_title(synPlayerInfo.getPlayer_title());
				makePairOk.setPlayer_community(synPlayerInfo.getPlayer_community());
				makePairOk.setWeaponSkillPlayerId(synPlayerInfo.getWeaponSkillPlayerId());
				makePairOk.setWeaponSkillName(synPlayerInfo.getWeaponSkillName());
				makePairOk.setWeaponSkillType(synPlayerInfo.getWeaponSkillType());
				makePairOk.setWeaponSkillChance(synPlayerInfo.getWeaponSkillChance());
				makePairOk.setWeaponSkillParam1(synPlayerInfo.getWeaponSkillParam1());
				makePairOk.setWeaponSkillParam2(synPlayerInfo.getWeaponSkillParam2());
				makePairOk.setInjuryFree(synPlayerInfo.getInjuryFree());
				makePairOk.setWreckDefense(synPlayerInfo.getWreckDefense());
				makePairOk.setReduceCrit(synPlayerInfo.getReduceCrit());
				makePairOk.setReduceBury(synPlayerInfo.getReduceBury());
				makePairOk.setZsleve(synPlayerInfo.getZsleve());
				makePairOk.setSkillful(synPlayerInfo.getSkillful());
				makePairOk.setPetId(synPlayerInfo.getPetId());
				makePairOk.setPetIcon(synPlayerInfo.getPetIcon());
				makePairOk.setPetType(synPlayerInfo.getPetType());
				makePairOk.setPetSkillId(synPlayerInfo.getPetSkillId());
				makePairOk.setPetProbability(synPlayerInfo.getPetProbability());
				makePairOk.setPetParam1(synPlayerInfo.getPetParam1());
				makePairOk.setPetParam2(synPlayerInfo.getPetParam2());
				makePairOk.setPetEffect(synPlayerInfo.getPetEffect());
				makePairOk.setServerName(synPlayerInfo.getServerName());
				makePairOk.setPetVersion(synPlayerInfo.getPetVersion());
				makePairOk.setRobotSkill(ServiceManager.getManager().getVersionService().getVersion().getRobotSkill());
				makePairOk.setPower(synPlayerInfo.getForce());
				makePairOk.setArmor(synPlayerInfo.getArmor());
				makePairOk.setFighting(synPlayerInfo.getFighting());
				makePairOk.setWinRate(synPlayerInfo.getWinRate());
				makePairOk.setConstitution(synPlayerInfo.getConstitution());
				makePairOk.setAgility(synPlayerInfo.getAgility());
				makePairOk.setLucky(synPlayerInfo.getLucky());
				for (int i = 0; i < synPlayerInfo.getPlayerId().length; i++) {
					ServiceManager
							.getManager()
							.getCrossService()
							.enBattle(synPlayerInfo.getPlayerId()[i], synPlayerInfo.getServerId()[i], synPlayerInfo.getRoomIds()[i],
									synPlayerInfo.getSourcePlayerId()[i]);
				}
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						seat.getPlayer().setBattleId(makePairOk.getBattleId());
						makePairOk.setSelfId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(makePairOk);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}