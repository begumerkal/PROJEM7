package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.QuitBattle;
import com.wyd.empire.protocol.data.battle.QuitBattleOk;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
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
		QuitBattle quitBattle = (QuitBattle) data;
		int roomId = player.getRoomId();
		int battleId = player.getBattleId();
		if (battleId > 0) {
			try {
				QuitBattleOk quitBattleOk = new QuitBattleOk(data.getSessionId(), data.getSerial());
				session.write(quitBattleOk);
				StringBuffer sbf = new StringBuffer();
				sbf.append("玩家主动退出对战---战斗组:");
				sbf.append(battleId);
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam)
					return;
				Combat combat = battleTeam.getCombatMap().get(player.getId());
				if (null == combat)
					return;
				if (!combat.isDead())
					combat.setEnforceQuit(true);// 死亡不算强退
				ServiceManager.getManager().getBattleTeamService().playerLose(battleId, player.getId());
				if (0 != roomId && null != ServiceManager.getManager().getChallengeService().getRoom(roomId)
						&& ServiceManager.getManager().getChallengeService().getRoom(roomId).getBattleMode() == 6) {
					int index = ServiceManager.getManager().getChallengeService().getPlayerSeat(roomId, player.getId());
					ServiceManager.getManager().getChallengeService().exRoom(roomId, index);
				} else if (0 != roomId && null != ServiceManager.getManager().getRoomService().getRoom(roomId)) {
					int index = ServiceManager.getManager().getRoomService().getPlayerSeat(roomId, player.getId());
					ServiceManager.getManager().getRoomService().exRoom(roomId, index, player.getId());
				}
				for (Combat pclost : battleTeam.getCombatList()) {
					if (!pclost.isRobot() && !pclost.isLost() && null != pclost.getPlayer()) {
						sbf.append("---同组玩家:id=");
						sbf.append(pclost.getId());
						sbf.append("---name=");
						sbf.append(pclost.getName());
					}
				}
				player.writeLog(sbf);
			} catch (Exception ex) {
				if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
					log.error(ex, ex);
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			}
		} else if (battleId < 0) {
			ServiceManager.getManager().getCrossService().quitBattle(battleId, quitBattle.getPlayerId(), roomId);
		}
	}
}
