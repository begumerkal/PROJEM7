package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.LoadingPercent;
import com.wyd.empire.protocol.data.bossmapbattle.OtherLoadingPercent;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送加载百份比
 * 
 * @author Administrator
 */
public class LoadingPercentHandler implements IDataHandler {
	Logger log = Logger.getLogger(LoadingPercentHandler.class);

	public void handle(AbstractData data) throws Exception {
		LoadingPercent loadingPercent = (LoadingPercent) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = loadingPercent.getBattleId();
			int playerOrGuai = loadingPercent.getPlayerOrGuai();
			int currentId = loadingPercent.getCurrentId();
			int percent = loadingPercent.getPercent();
			OtherLoadingPercent otherLoadingPercent = new OtherLoadingPercent();
			otherLoadingPercent.setBattleId(battleId);
			otherLoadingPercent.setPlayerOrGuai(playerOrGuai);
			otherLoadingPercent.setCurrentId(currentId);
			otherLoadingPercent.setPercent(percent);
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam)
				return;
			// System.out.println("LoadingPercentHandler:" + battleId);
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(otherLoadingPercent);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
		}
	}
}
