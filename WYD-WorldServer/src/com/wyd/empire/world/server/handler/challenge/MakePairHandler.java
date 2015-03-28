package com.wyd.empire.world.server.handler.challenge;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.MakePair;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.RandomRoom;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 生成战斗组
 * 
 * @author Administrator
 */
public class MakePairHandler implements IDataHandler {
	Logger log = Logger.getLogger(MakePairHandler.class);

	// 生成战斗组
	public void handle(AbstractData data) throws Exception {
		MakePair makePair = (MakePair) data;
		try {
			Room room = ServiceManager.getManager().getChallengeService().getRoom(makePair.getBattleTeamId());
			if (null == room || 1 == room.getBattleStatus()) {
				return;
			}
			synchronized (room) {

				if (!ServiceManager.getManager().getChallengeSerService().isInTime()) {
					throw new ProtocolException(ErrorMessages.CHALLENGE_NOT_INTIME, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}

				for (Seat seat : room.getPlayerList()) {
					if (seat.isUsed() && null == seat.getPlayer())
						seat.setReady(true);
				}

				if (!ServiceManager.getManager().getChallengeService().isAllReady(makePair.getBattleTeamId())) {
					throw new ProtocolException(ErrorMessages.ROOM_PNR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				room.setBattleStatus(1);
				List<WorldPlayer> playerList = ServiceManager.getManager().getChallengeService().getPlayerList(makePair.getBattleTeamId());
				for (WorldPlayer player : playerList) {
					if (null != player && player.getId() != room.getWnersId()) {
						player.sendData(makePair);
					}
				}

				RandomRoom randomRoom = new RandomRoom();
				randomRoom.setRoom(room);
				if (1 == makePair.getServiceMode() && WorldServer.config.isCross()) {
					// System.out.println("跨服");
					ServiceManager.getManager().getCrossService().sendPairInfo(room);
				} else {
					// System.out.println("本服");
					ServiceManager.getManager().getPairService().addRandomRoom(randomRoom);
				}

			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.CHALLENGE_NOT_INTIME, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
