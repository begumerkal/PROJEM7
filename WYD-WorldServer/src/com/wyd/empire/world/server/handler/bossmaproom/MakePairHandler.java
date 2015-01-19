package com.wyd.empire.world.server.handler.bossmaproom;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.MakePair;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.bossmaproom.BossSeat;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 生成战斗组
 * 
 * @author Administrator
 * 
 */
public class MakePairHandler implements IDataHandler {
	Logger log = Logger.getLogger(MakePairHandler.class);

	// 生成战斗组
	public void handle(AbstractData data) throws Exception {
		MakePair makePair = (MakePair) data;
		try {
			BossRoom room = ServiceManager.getManager().getBossRoomService().getRoom(makePair.getRoomId());
			if (null == room || 1 == room.getBattleStatus()) {
				return;
			}
			synchronized (room) {
				Map map = ServiceManager.getManager().getMapsService().getBossMapById(room.getMapId());
				int canPassTimes = map.getPassTimes();
				boolean unlimited = map.getPassTimes() == -1;
				int camp0 = 0;
				int camp1 = 0;
				for (BossSeat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()) {
						// 次数上限
						if (!unlimited) {
							PlayerBossmap playerMap = ServiceManager.getManager().getPlayerBossmapService()
									.loadPlayerBossMap(seat.getPlayer().getId(), map.getId());
							if (playerMap != null && playerMap.getPassTimes() >= canPassTimes) {
								throw new ProtocolException(TipMessages.SINGLEMAP_PASS_LIMIT, data.getSerial(), data.getSessionId(),
										data.getType(), data.getSubType());
							}
						}

						if (0 == seat.getCamp()) {
							camp0++;
						} else {
							camp1++;
						}
					}
				}
				if (camp0 == camp1 && camp0 != room.getPlayerNumMode()) {
					for (BossSeat seat : room.getPlayerList()) {
						seat.setReady(true);
					}
				}
				if (!ServiceManager.getManager().getBossRoomService().isAllReady(makePair.getRoomId())) {
					throw new ProtocolException(ErrorMessages.ROOM_PNR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				room.setBattleStatus(1);

				// 给其他队友发送makepair协议
				List<WorldPlayer> playerList = ServiceManager.getManager().getBossRoomService().getPlayerList(makePair.getRoomId());
				for (WorldPlayer player : playerList) {
					if (null != player && player.getId() != room.getWnersId()) {
						player.sendData(makePair);
					}
				}
				// 创建战斗组
				int battleId = ServiceManager.getManager().getBossBattleTeamService().createBattleTeam(room);

				for (BossSeat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()) {

						ServiceManager.getManager().getBossBattleTeamService().enBattleTeam(battleId, seat.getPlayer(), seat.getCamp());
					}
				}
				ServiceManager.getManager().getBossBattleTeamService().startBattle(battleId, room);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_PNR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
