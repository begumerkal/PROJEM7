package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.RegisterFail;
import com.wyd.empire.protocol.data.account.RegisterOk;
import com.wyd.empire.protocol.data.server.LegacyRegisterOk;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家注册
 * 
 * @since JDK 1.6
 */
public class LegacyRegisterOkHandler implements IDataHandler {
	private Logger log = Logger.getLogger(LegacyRegisterOkHandler.class);

	public void handle(AbstractData data) {
		LegacyRegisterOk legacyRegisterOk = (LegacyRegisterOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(legacyRegisterOk.getPlayerId());
			if (null != player) {
				if (0 == legacyRegisterOk.getStatus()) {
					RegisterOk registerOk = new RegisterOk();
					player.sendData(registerOk);
					// ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
					ServiceManager.getManager().getTaskService().bindAccount(player);
				} else {
					RegisterFail registerFail = new RegisterFail();
					if (1 == legacyRegisterOk.getStatus()) {
						registerFail.setMessage(TipMessages.REGISTERREPEAT);
					} else {
						registerFail.setMessage(TipMessages.REGISTERFAILL);
					}
					player.sendData(registerFail);
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}
}
