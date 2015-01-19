package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.RequestGuaiBattleId;
import com.wyd.empire.protocol.data.bossmapbattle.RequestGuaiBattleIdOk;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 请求怪物战斗id
 * 
 * @author Administrator
 */
public class RequestGuaiBattleIdHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestGuaiBattleIdHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		RequestGuaiBattleId requestGuaiBattleId = (RequestGuaiBattleId) data;
		try {
			int battleId = requestGuaiBattleId.getBattleId();
			int count = requestGuaiBattleId.getCount();
			int[] guaiBattleId = new int[count];

			RequestGuaiBattleIdOk requestGuaiBattleIdOk = new RequestGuaiBattleIdOk(data.getSessionId(), data.getSerial());
			requestGuaiBattleIdOk.setBattleId(battleId);
			requestGuaiBattleIdOk.setCount(count);

			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);

			for (int i = 0; i < guaiBattleId.length; i++) {
				guaiBattleId[i] = battleTeam.getGuaiIdDistribution();
			}

			requestGuaiBattleIdOk.setGuaiBattleId(guaiBattleId);

			session.write(requestGuaiBattleIdOk);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
