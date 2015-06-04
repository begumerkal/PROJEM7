package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.VerificationResult;
import com.wyd.empire.protocol.data.server.LegacyVerificationResult;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家修改密码
 * 
 * @since JDK 1.6
 */
public class LegacyVerificationResultHandler implements IDataHandler {
	private Logger log = Logger.getLogger(LegacyVerificationResultHandler.class);

	public AbstractData handle(AbstractData data) {
		LegacyVerificationResult legacyVerificationResult = (LegacyVerificationResult) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService()
					.getOnlineWorldPlayer(legacyVerificationResult.getPlayerId());
			if (null != player) {
				VerificationResult verificationResult = new VerificationResult();
				verificationResult.setStatus(legacyVerificationResult.getStatus());
				player.sendData(verificationResult);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
}
