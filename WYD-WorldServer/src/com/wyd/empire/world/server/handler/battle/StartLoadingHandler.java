package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.StartLoading;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类<code>StartLoadingHandler</code> 通知服务器已经开始加载
 * 
 * @author Administrator
 */
public class StartLoadingHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartLoadingHandler.class);

	/**
	 * 读取房间列表
	 */
	public void handle(AbstractData data) throws Exception {
		StartLoading startLoading = (StartLoading) data;
		if (startLoading.getBattleId() > 0) {
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(startLoading.getBattleId());
				if (null == battleTeam) {
					throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				Combat combat = battleTeam.getCombatMap().get(startLoading.getPlayerId());
				combat.setState(1);
				ServiceManager.getManager().getBattleTeamService().sendAIControlCommon(startLoading.getBattleId());
			} catch (ProtocolException ex) {
				throw ex;
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().startLoading(startLoading.getBattleId(), startLoading.getPlayerId());
		}
	}
}
