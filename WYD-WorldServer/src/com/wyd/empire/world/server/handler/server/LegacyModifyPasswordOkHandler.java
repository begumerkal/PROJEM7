package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.ModifyPasswordFail;
import com.wyd.empire.protocol.data.account.ModifyPasswordOk;
import com.wyd.empire.protocol.data.server.LegacyModifyPasswordOk;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家修改密码
 * 
 * @since JDK 1.6
 */
public class LegacyModifyPasswordOkHandler implements IDataHandler {
	private Logger log = Logger.getLogger(LegacyModifyPasswordOkHandler.class);

	public AbstractData handle(AbstractData data) {
		LegacyModifyPasswordOk legacyModifyPasswordOk = (LegacyModifyPasswordOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(legacyModifyPasswordOk.getPlayerId());
			if (null != player) {
				if (0 == legacyModifyPasswordOk.getStatus()) {
					ModifyPasswordOk modifyPasswordOk = new ModifyPasswordOk();
					player.sendData(modifyPasswordOk);
				} else {
					ModifyPasswordFail modifyPasswordFail = new ModifyPasswordFail();
					if (1 == legacyModifyPasswordOk.getStatus()) {
						modifyPasswordFail.setMessage(TipMessages.MODIFYPASSWORLDERROR);
					} else {
						modifyPasswordFail.setMessage(TipMessages.MODIFYPASSWORLDFAILL);
					}
					player.sendData(modifyPasswordFail);
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
}
