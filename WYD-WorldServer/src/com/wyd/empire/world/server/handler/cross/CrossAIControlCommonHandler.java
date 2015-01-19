package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.AIControlCommon;
import com.wyd.empire.protocol.data.cross.CrossAIControlCommon;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送ai控制
 * 
 * @author zguoqiu
 */
public class CrossAIControlCommonHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossAIControlCommonHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossAIControlCommon acc = (CrossAIControlCommon) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService()
					.getOnlineWorldPlayer(ServiceManager.getManager().getCrossService().getPlayerId(acc.getPlayerId()));
			if (null != player) {
				AIControlCommon aiControlCommon = new AIControlCommon();
				aiControlCommon.setBattleId(acc.getBattleId());
				aiControlCommon.setIdcount(acc.getIdcount());
				aiControlCommon.setPlayerIds(acc.getPlayerIds());
				aiControlCommon.setAiCtrlId(acc.getAiCtrlId());
				player.sendData(aiControlCommon);
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}