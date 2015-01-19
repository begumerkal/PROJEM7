package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.ModifyPassword;
import com.wyd.empire.protocol.data.server.LegacyModifyPassword;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 修改用户密码
 * 
 * @since JDK 1.6
 */
public class ModifyPasswordHandler implements IDataHandler {
	Logger log = Logger.getLogger(ModifyPasswordHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ModifyPassword modifyPassword = (ModifyPassword) data;
		try {
			LegacyModifyPassword legacyModifyPassword = new LegacyModifyPassword();
			legacyModifyPassword.setAccountId(player.getClient().getAccountId());
			legacyModifyPassword.setPlayerId(player.getId());
			legacyModifyPassword.setOldPassWold(modifyPassword.getOldPassword());
			legacyModifyPassword.setNewPassWord(modifyPassword.getNewPassword());
			ServiceManager.getManager().getAccountSkeleton().send(legacyModifyPassword);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}