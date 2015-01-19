package com.wyd.empire.world.server.handler.worldbosshall;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.worldbosshall.GetHallState;
import com.wyd.empire.protocol.data.worldbosshall.GetHallStateOk;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.impl.WorldBossRoomService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.worldbosshall.WorldBossRoom;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取挑战大厅状态
 * 
 * @author zengxc
 *
 */
public class GetHallStateHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetHallStateHandler.class);

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetHallState getHallState = (GetHallState) data;
		// 如果传过来的ID小于1则使用默认值
		getHallState.setMapId(getHallState.getMapId() < 1 ? Common.WORLDBOSS_DEFAULT_MAP : getHallState.getMapId());
		WorldBossRoomService roomService = WorldBossRoomService.getInstance();
		WorldBossRoom room = roomService.getRoomByMap(getHallState.getMapId());

		int playerSize = room.getPlayerSize();
		String[] playerNames = new String[playerSize];
		int[] hurts = new int[playerSize];
		List<Combat> combatList = room.getCombatList();
		int i = 0;
		for (Combat combat : combatList) {
			if (combat.getPlayer() == null) {
				continue;
			}
			playerNames[i] = combat.getName();
			hurts[i] = combat.getTotalHurt();
			i++;
		}
		GetHallStateOk ok = new GetHallStateOk(data.getSessionId(), data.getSerial());
		ok.setPlayerName(playerNames);
		ok.setHurt(hurts);
		session.write(ok);
	}
}
