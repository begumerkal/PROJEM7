package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherPlayerMove;
import com.wyd.empire.protocol.data.bossmapbattle.PlayerMove;
import com.wyd.empire.world.battle.BossBattleTeam;
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
		PlayerMove playerMove = (PlayerMove) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = playerMove.getBattleId();
			int playerOrGuai = playerMove.getPlayerOrGuai();
			int currentId = playerMove.getCurrentId();
			int movecount = playerMove.getMovecount();
			byte[] movestep = playerMove.getMovestep();
			int curPositionX = playerMove.getCurPositionX();
			int curPositionY = playerMove.getCurPositionY();
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam)
				return;
			if (playerOrGuai == 0) {
				Combat cb = battleTeam.getCombatMap().get(currentId);
				// 公会技能加成(除2表示消耗减半)
				float movecountFloat = ServiceManager.getManager().getBuffService().getAddition(player, movecount, Buff.CPOWERLOW) / 2;
				float pf = cb.getPf() - movecountFloat;
				if (pf >= 0) {
					cb.setPf(pf, battleTeam.getMapId(), 6, player);
				} else {
					cb.setPf(0, battleTeam.getMapId(), 6, player);
				}
				// // 防作弊
				// if (cb.getPf() < -10 && !cb.isRobot()) {
				// log.info("作弊踢下线playerid:"+cb.getId());
				// ServiceManager.getManager().getPlayerService().killLine(cb.getId());
				// return;
				// }
			} else {
				Combat gv = battleTeam.getGuaiMap().get(currentId);
				if (gv != null) {
					int pf = (int) gv.getPf() - movecount;
					if (pf >= 0) {
						gv.setPf(pf, battleTeam.getMapId(), 6, player);
					} else {
						gv.setPf(pf, battleTeam.getMapId(), 6, player);
					}
				}
			}
			OtherPlayerMove otherPlayerMove = new OtherPlayerMove();
			otherPlayerMove.setBattleId(battleId);
			otherPlayerMove.setPlayerOrGuai(playerOrGuai);
			otherPlayerMove.setCurrentId(currentId);
			otherPlayerMove.setMovecount(movecount);
			otherPlayerMove.setMovestep(movestep);
			otherPlayerMove.setCurPositionX(curPositionX);
			otherPlayerMove.setCurPositionY(curPositionY);
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(otherPlayerMove);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
