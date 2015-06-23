package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.Verification;
import com.wyd.empire.protocol.data.server.LegacyVerification;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> LoginHandler</code>Protocol.ACCOUNT_Login登陆协议处理
 * 
 * @since JDK 1.6
 */
public class VerificationHandler implements IDataHandler {
	private Logger log;

	public VerificationHandler() {
		this.log = Logger.getLogger(VerificationHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Verification verification = (Verification) data;
		try {
			String accountName = CryptionUtil.Decrypt(verification.getAccountName(), ServiceManager.getManager().getConfiguration()
					.getString("deckey"));
			String passWord = CryptionUtil.Decrypt(verification.getPassWord(),
					ServiceManager.getManager().getConfiguration().getString("deckey"));
			LegacyVerification legacyVerification = new LegacyVerification();
			legacyVerification.setAccountId(player.getClient().getAccountId());
			legacyVerification.setPlayerId(player.getId());
			legacyVerification.setAccountName(accountName);
			legacyVerification.setPassWord(passWord);
			ServiceManager.getManager().getAccountSkeleton().send(legacyVerification);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
		return null;
	}
}