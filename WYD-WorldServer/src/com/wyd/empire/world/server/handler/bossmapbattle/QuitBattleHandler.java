package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.QuitBattleOk;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家强退游戏
 * 
 * @author Administrator
 */
public class QuitBattleHandler implements IDataHandler {
	Logger log = Logger.getLogger(QuitBattleHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			int roomId = player.getBossmapRoomId();
			int battleId = player.getBossmapBattleId();
			if (0 != battleId) {
				Combat combat = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId).getCombatMap()
						.get(player.getId());
				if (!combat.isDead()) {
					combat.setEnforceQuit(true);
				}
				ServiceManager.getManager().getBossBattleTeamService().playerLose(battleId, player.getId());
				// 世界BOSS
				BossBattleTeamService bossBattleTeamService = ServiceManager.getManager().getBossBattleTeamService();
				bossBattleTeamService.worldBossLose(battleId, player.getId());

			}
			if (0 != roomId && null != ServiceManager.getManager().getBossRoomService().getRoom(roomId)) {
				int index = ServiceManager.getManager().getBossRoomService().getPlayerSeat(roomId, player.getId());
				ServiceManager.getManager().getBossRoomService().extRoom(roomId, index, player.getId());
			}
			QuitBattleOk quitBattleOk = new QuitBattleOk(data.getSessionId(), data.getSerial());
			quitBattleOk.setBattleId(battleId);
			session.write(quitBattleOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
