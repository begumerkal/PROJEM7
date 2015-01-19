package com.wyd.empire.world.server.handler.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.PositionsInMap;
import com.wyd.empire.protocol.data.battle.PostionsForPlayers;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 提交地图可选择的出现位置给服务器
 * 
 * @author Administrator
 */
public class PositionsInMapHandler implements IDataHandler {
	Logger log = Logger.getLogger(PositionsInMapHandler.class);

	// 读取房间列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		PositionsInMap positionsInMap = (PositionsInMap) data;
		if (positionsInMap.getBattleId() > 0) {
			try {
				List<Integer> indexList = new ArrayList<Integer>();
				for (int i = 0; i < positionsInMap.getPostionCount(); i++) {
					indexList.add(i);
				}
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(positionsInMap.getBattleId());
				if (null == battleTeam) {
					throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				Random random = new Random();
				if (positionsInMap.getPostionCount() < battleTeam.getCombatList().size()) {
					throw new ProtocolException(ErrorMessages.MAP_SETERROR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				PostionsForPlayers postionsForPlayers = battleTeam.getPostionsForPlayers();
				if (null == postionsForPlayers) {
					postionsForPlayers = new PostionsForPlayers();
					battleTeam.setPostionsForPlayers(postionsForPlayers);
					int idcount = battleTeam.getCombatList().size();
					int[] playerIds = new int[idcount];
					int[] postionX = new int[idcount];
					int[] postionY = new int[idcount];
					Combat combat;
					for (int i = 0; i < idcount; i++) {
						combat = battleTeam.getCombatList().get(i);
						int index = 0;
						if (indexList.size() > 1) {
							index = random.nextInt(indexList.size() - 1);
						}
						int pindex = indexList.get(index);
						indexList.remove(index);
						playerIds[i] = combat.getId();
						postionX[i] = positionsInMap.getX()[pindex];
						postionY[i] = positionsInMap.getY()[pindex];
						combat.setX(postionX[i]);
						combat.setY(postionY[i]);
					}
					postionsForPlayers.setBattleId(battleTeam.getBattleId());
					postionsForPlayers.setIdcount(idcount);
					postionsForPlayers.setPlayerIds(playerIds);
					postionsForPlayers.setPostionX(postionX);
					postionsForPlayers.setPostionY(postionY);
				}
				postionsForPlayers.setPlayerId(player.getId());
				player.sendData(postionsForPlayers);
			} catch (ProtocolException ex) {
				throw ex;
			} catch (Exception ex) {
				log.error(ex, ex);
				throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		} else {
			ServiceManager.getManager().getCrossService()
					.positionsInMap(positionsInMap, ServiceManager.getManager().getCrossService().getCrossPlayerId(player.getId()));
		}
	}
}
