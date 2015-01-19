package com.wyd.empire.world.server.handler.bossmaproom;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.bossmaproom.EnterRoom;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 进入房间列
 * 
 * @author Administrator
 *
 */
public class EnterRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(EnterRoomHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		EnterRoom enterRoom = (EnterRoom) data;
		try {
			ServiceManager.getManager().getPlayerService().clearPlayer(player);
			BossRoom room = ServiceManager.getManager().getBossRoomService().getRoom(enterRoom.getRoomId());
			Map map = ServiceManager.getManager().getMapsService().getBossMapById(room.getMapId());
			int useVigor = map.getVitalityExpend();
			int vigor = player.getVigor();
			int canPassTimes = map.getPassTimes();
			boolean unlimited = map.getPassTimes() == -1;
			PlayerBossmap playerMap = ServiceManager.getManager().getPlayerBossmapService().loadPlayerBossMap(player.getId(), map.getId());
			// 是否足够活力
			if ((vigor - useVigor) < 0) {
				throw new ProtocolException(TipMessages.VIGOR_LOW, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			// 次数上限
			if (!unlimited) {
				if (playerMap != null && playerMap.getPassTimes() >= canPassTimes) {
					throw new ProtocolException(TipMessages.SINGLEMAP_PASS_LIMIT, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}

			ServiceManager.getManager().getBossRoomService().enRoom(enterRoom.getRoomId(), player, false);
			ServiceManager.getManager().getBossRoomService().SynRoomInfo(enterRoom.getRoomId());
		} catch (Exception ex) {
			ex.printStackTrace();
			this.log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}
}
