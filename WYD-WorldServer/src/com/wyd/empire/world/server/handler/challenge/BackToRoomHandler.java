package com.wyd.empire.world.server.handler.challenge;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.BackToRoom;
import com.wyd.empire.protocol.data.challenge.GetInBattleTeam;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 返回房间
 * 
 * @author Administrator
 */
public class BackToRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(BackToRoomHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		BackToRoom backToRoom = (BackToRoom) data;
		try {
			// System.out.println("BackToBattleTeam------------" +
			// backToRoom.getBattleTeamId());
			Room room = ServiceManager.getManager().getChallengeService().getRoom(backToRoom.getBattleTeamId());
			if (null == room) {
				// System.out.println("BackToRoom"+backToRoom.getRoomId());
				throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (1 == room.getBattleStatus()) {// 初始化房间属性
				room.setBattleStatus(0);
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && (seat.isRobot() || seat.getPlayer().getId() == room.getWnersId())) {
						seat.setReady(true);
					} else {
						seat.setReady(false);
					}
				}
			}
			List<Seat> seatList = room.getPlayerList();
			GetInBattleTeam getInBattleTeam = new GetInBattleTeam(data.getSessionId(), data.getSerial());
			getInBattleTeam.setBattleTeamId(backToRoom.getBattleTeamId());
			getInBattleTeam.setBattleMode(room.getBattleMode());
			getInBattleTeam.setPlayerNumMode(room.getPlayerNumMode());
			getInBattleTeam.setTeamLeader(room.getWnersId());
			int playerNum = seatList.size();
			getInBattleTeam.setPlayerNum(playerNum);
			boolean[] seatUsed = new boolean[playerNum];
			int[] playerId = new int[playerNum];
			String[] playerName = new String[playerNum];
			int[] playerLevel = new int[playerNum];
			boolean[] playerReady = new boolean[playerNum];
			int[] playerSex = new int[playerNum];
			List<String> playerEquipment = new ArrayList<String>();
			int[] skillType = new int[playerNum * 2];
			int[] skillLevel = new int[playerNum * 2];
			int[] playerProficiency = new int[playerNum];
			int[] playerEquipmentLevel = new int[playerNum];
			int[] vipLevel = new int[playerNum];
			String[] playerWing = new String[playerNum];
			String[] player_title = new String[playerNum];
			int[] qualifyingLevel = new int[playerNum];
			int[] zsleve = new int[playerNum];
			boolean[] doubleMark = new boolean[playerNum];
			boolean[] isvip = new boolean[playerNum];
			Seat seat;
			PlayerItemsFromShop pifs;
			WeapSkill ws;
			for (int i = 0; i < playerNum; i++) {
				seat = seatList.get(i);
				seatUsed[i] = seat.isUsed();
				if (null != seat.getPlayer()) {
					playerId[i] = seat.getPlayer().getId();
					playerName[i] = seat.getPlayer().getName();
					playerLevel[i] = seat.getPlayer().getLevel();
					playerReady[i] = seat.isReady();
					playerSex[i] = seat.getPlayer().getPlayer().getSex();
					playerEquipment.add(seat.getPlayer().getSuit_head());
					playerEquipment.add(seat.getPlayer().getSuit_face());
					playerEquipment.add(seat.getPlayer().getSuit_body());
					playerEquipment.add(seat.getPlayer().getSuit_weapon());
					playerEquipmentLevel[i] = seat.getPlayer().getWeaponLevel();
					playerProficiency[i] = seat.getPlayer().getProficiency();
					zsleve[i] = seat.getPlayer().getPlayer().getZsLevel();
					if (null != seat.getPlayer().getPlayer().getVipTime()
							&& System.currentTimeMillis() <= seat.getPlayer().getPlayer().getVipTime().getTime()) {
						vipLevel[i] = seat.getPlayer().getPlayer().getVipLevel();
					} else {
						vipLevel[i] = 0;
					}
					playerWing[i] = seat.getPlayer().getSuit_wing();
					player_title[i] = seat.getPlayer().getPlayerTitle();
					qualifyingLevel[i] = seat.getPlayer().getPlayer().getHonorLevel();
					pifs = seat.getPlayer().getWeapon();
					if (pifs.getWeapSkill1() != 0) {
						ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
						skillType[2 * i] = ws.getType();
						skillLevel[2 * i] = ws.getLevel();
					} else {
						skillType[2 * i] = 0;
						skillLevel[2 * i] = 0;
					}
					if (pifs.getWeapSkill2() != 0) {
						ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
						skillType[2 * i + 1] = ws.getType();
						skillLevel[2 * i + 1] = ws.getLevel();
					} else {
						skillType[2 * i + 1] = 0;
						skillLevel[2 * i + 1] = 0;
					}
					doubleMark[i] = seat.getPlayer().isHasDoubleCard();
					if (seat.getPlayer().isVip() && seat.getPlayer().getPlayer().getVipLevel() > 3) {
						isvip[i] = true;
					} else {
						isvip[i] = false;
					}
				} else {
					playerName[i] = "";
					playerWing[i] = "";
					player_title[i] = "";
					playerEquipment.add("");
					playerEquipment.add("");
					playerEquipment.add("");
					playerEquipment.add("");
					skillType[2 * i] = 0;
					skillLevel[2 * i] = 0;
					skillType[2 * i + 1] = 0;
					skillLevel[2 * i + 1] = 0;
				}
			}
			getInBattleTeam.setSeatUsed(seatUsed);
			getInBattleTeam.setPlayerId(playerId);
			getInBattleTeam.setPlayerName(playerName);
			getInBattleTeam.setPlayerLevel(playerLevel);
			getInBattleTeam.setPlayerReady(playerReady);
			getInBattleTeam.setPlayerSex(playerSex);
			getInBattleTeam.setPlayerProficiency(playerProficiency);
			getInBattleTeam.setPlayerEquipment(playerEquipment.toArray(new String[0]));
			getInBattleTeam.setPlayerWeaponLevel(playerEquipmentLevel);
			getInBattleTeam.setVipLevel(vipLevel);
			getInBattleTeam.setPlayerWing(playerWing);
			getInBattleTeam.setPlayerTitle(player_title);
			getInBattleTeam.setQualifyingLevel(qualifyingLevel);
			getInBattleTeam.setSkillType(skillType);
			getInBattleTeam.setSkillLevel(skillLevel);
			getInBattleTeam.setZsleve(zsleve);
			getInBattleTeam.setDoublemark(doubleMark);
			getInBattleTeam.setIsVip(isvip);
			getInBattleTeam.setApplyNum(room.getApplyList().size());
			getInBattleTeam.setTeamName(room.getRoomName());
			session.write(getInBattleTeam);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
