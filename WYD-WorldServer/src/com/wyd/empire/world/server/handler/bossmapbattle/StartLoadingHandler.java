package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.StartLoading;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 通知服务器已经开始加载
 * 
 * @author Administrator
 */
public class StartLoadingHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartLoadingHandler.class);

	// 读取房间列表
	public void handle(AbstractData data) throws Exception {
		StartLoading startLoading = (StartLoading) data;
		try {
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(startLoading.getBattleId());
			if (null == battleTeam)
				return;
			Combat combat = battleTeam.getCombatMap().get(startLoading.getPlayerId());
			combat.setState(1);
			ServiceManager.getManager().getBossBattleTeamService().sendAIControlCommon(startLoading.getBattleId());
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
