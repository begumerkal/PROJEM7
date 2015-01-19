package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> ChannelLoginHandler</code>Protocol.ACCOUNT_ChannelLogin 渠道登录验证
 * 
 * @since JDK 1.6
 */
public class FromBackGroundHandler implements IDataHandler {
	Logger log = Logger.getLogger(FromBackGroundHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			if (player != null) {
				if (0 != player.getBattleId()) {
					BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(player.getBattleId());
					if (null != battleTeam && battleTeam.getAicounter() == player.getId()) {
						ServiceManager.getManager().getBattleTeamService().sendAIControlCommon(player.getBattleId());
					}
				}
				if (0 != player.getBossmapBattleId()) {
					BossBattleTeam bossBattleTeam = ServiceManager.getManager().getBossBattleTeamService()
							.getBattleTeam(player.getBossmapBattleId());
					if (null != bossBattleTeam && bossBattleTeam.getAicounter() == player.getId()) {
						ServiceManager.getManager().getBossBattleTeamService().sendAIControlCommon(player.getBattleId());
					}
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}
}