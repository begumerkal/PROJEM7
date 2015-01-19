package com.wyd.empire.world.server.handler.battle;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.BackToRoom;
import com.wyd.empire.protocol.data.room.EnterRoomOk;
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
			// System.out.println("BackToRoom------------" +
			// backToRoom.getRoomId());
			Room room = ServiceManager.getManager().getRoomService().getRoom(backToRoom.getRoomId());
			if (null == room) {
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
			EnterRoomOk enterRoomOk = new EnterRoomOk(data.getSessionId(), data.getSerial());
			enterRoomOk.setRoomId(backToRoom.getRoomId());
			enterRoomOk.setBattleMode(room.getBattleMode());
			enterRoomOk.setServiceMode(room.getServiceMode());
			enterRoomOk.setRoomChannel(room.getRoomChannel());
			enterRoomOk.setPlayerNumMode(room.getPlayerNumMode());
			enterRoomOk.setMapId(room.getMapId());
			enterRoomOk.setWnersId(room.getWnersId());
			enterRoomOk.setStartMode(room.getStartMode());
			enterRoomOk.setEventMode(room.getEventMode());
			int playerNum = seatList.size();
			enterRoomOk.setPlayerNum(playerNum);
			boolean[] seatUsed = new boolean[playerNum];
			int[] playerId = new int[playerNum];
			String[] playerName = new String[playerNum];
			int[] playerLevel = new int[playerNum];
			boolean[] playerReady = new boolean[playerNum];
			int[] playerSex = new int[playerNum];
			List<String> playerEquipment = new ArrayList<String>();
			int[] playerProficiency = new int[playerNum];
			int[] playerEquipmentLevel = new int[playerNum];
			int[] vipLevel = new int[playerNum];
			String[] playerWing = new String[playerNum];
			String[] player_title = new String[playerNum];
			int[] qualifyingLevel = new int[playerNum];
			int[] skillType = new int[playerNum * 2];
			int[] skillLevel = new int[playerNum * 2];
			int[] zsleve = new int[playerNum];
			boolean[] doubleMark = new boolean[playerNum];
			boolean[] isvip = new boolean[playerNum];
			boolean[] isvipNew = new boolean[playerNum];
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
					if (seat.getPlayer().isVip()) {
						isvipNew[i] = true;
					} else {
						isvipNew[i] = false;
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
			enterRoomOk.setSeatUsed(seatUsed);
			enterRoomOk.setPlayerId(playerId);
			enterRoomOk.setPlayerName(playerName);
			enterRoomOk.setPlayerLevel(playerLevel);
			enterRoomOk.setPlayerReady(playerReady);
			enterRoomOk.setPlayerSex(playerSex);
			enterRoomOk.setPlayerProficiency(playerProficiency);
			enterRoomOk.setPlayerEquipment(playerEquipment.toArray(new String[0]));
			enterRoomOk.setPlayerEquipmentLevel(playerEquipmentLevel);
			enterRoomOk.setVipLevel(vipLevel);
			enterRoomOk.setPlayerWing(playerWing);
			enterRoomOk.setPlayer_title(player_title);
			enterRoomOk.setQualifyingLevel(qualifyingLevel);
			enterRoomOk.setSkillLevel(skillLevel);
			enterRoomOk.setSkillType(skillType);
			enterRoomOk.setZsleve(zsleve);
			enterRoomOk.setDoubleMark(doubleMark);
			enterRoomOk.setIsvip(isvip);
			session.write(enterRoomOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
