package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.FindPassword;
import com.wyd.empire.protocol.data.account.FindPasswordOk;
import com.wyd.empire.protocol.data.server.LegacyFindPassword;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 用户注册协议
 * 
 * @since JDK 1.6
 */
public class FindPasswordHandler implements IDataHandler {
	Logger log = Logger.getLogger(FindPasswordHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		FindPassword findPassword = (FindPassword) data;
		try {
			String email = findPassword.getEmail(); // 邮箱地址（加密后的字符串）
			LegacyFindPassword legacyFindPassword = new LegacyFindPassword();
			legacyFindPassword.setAccountId(player.getClient().getAccountId());
			legacyFindPassword.setPlayerId(player.getId());
			legacyFindPassword.setEmail(email);
			ServiceManager.getManager().getAccountSkeleton().send(legacyFindPassword);

			FindPasswordOk findPasswordOk = new FindPasswordOk(data.getSessionId(), data.getSerial());
			findPasswordOk.setMessage("");
			session.write(findPasswordOk);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
		return null;
	}
}