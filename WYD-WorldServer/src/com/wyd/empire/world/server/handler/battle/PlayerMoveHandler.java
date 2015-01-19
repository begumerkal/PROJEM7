package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherPlayerMove;
import com.wyd.empire.protocol.data.battle.PlayerMove;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 角色移动
 * 
 * @author Administrator
 */
public class PlayerMoveHandler implements IDataHandler {
	Logger log = Logger.getLogger(PlayerMoveHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer me = session.getPlayer(data.getSessionId());
		PlayerMove playerMove = (PlayerMove) data;
		int battleId = playerMove.getBattleId();
		int playerId = playerMove.getPlayerId();
		int movecount = playerMove.getMovecount();
		byte[] movestep = playerMove.getMovestep();
		int curPositionX = playerMove.getCurPositionX();
		int curPositionY = playerMove.getCurPositionY();
		// 公会技能加成
		float movecountFloat = ServiceManager.getManager().getBuffService().getAddition(me, movecount, Buff.CPOWERLOW) / 2;
		if (battleId > 0) {
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam)
					return;
				Combat cb = battleTeam.getCombatMap().get(playerId);
				float pf = cb.getPf() - movecountFloat;
				if (pf >= 0) {
					cb.setPf(pf, battleTeam.getMapId(), battleTeam.getBattleMode(), me);
				} else {
					cb.setPf(0, battleTeam.getMapId(), battleTeam.getBattleMode(), me);
				}
				OtherPlayerMove otherPlayerMove = new OtherPlayerMove();
				otherPlayerMove.setBattleId(battleId);
				otherPlayerMove.setCurrentPlayerId(playerId);
				otherPlayerMove.setMovecount(movecount);
				otherPlayerMove.setMovestep(movestep);
				otherPlayerMove.setCurPositionX(curPositionX);
				otherPlayerMove.setCurPositionY(curPositionY);
				for (Combat combat : battleTeam.getCombatList()) {
					if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
						otherPlayerMove.setPlayerId(combat.getId());
						// System.out.println(playerMove.getSerial()+"----OtherPlayerMove MovePlayer:"+playerId+"---ReceivePlayer:"+combat.getPlayer().getId()+"---x:"+curPositionX+"---y:"+curPositionY+"---Serial:"+otherPlayerMove.getSerial());
						combat.getPlayer().sendData(otherPlayerMove);
					}
				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService()
					.playerMove(battleId, playerId, movecount, movestep, curPositionX, curPositionY, movecountFloat);
		}
	}
}
