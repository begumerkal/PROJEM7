package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.Register;
import com.wyd.empire.protocol.data.server.LegacyRegister;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 用户注册协议
 * 
 * @since JDK 1.7
 */
public class RegisterHandler implements IDataHandler {
	Logger log = Logger.getLogger(RegisterHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Register register = (Register) data;
		try {
			String accountName = CryptionUtil.Decrypt(register.getAccountName(),
					ServiceManager.getManager().getConfiguration().getString("deckey")); // 帐号（加密后的字符串）
			String passWord = CryptionUtil.Decrypt(register.getPassWord(),
					ServiceManager.getManager().getConfiguration().getString("deckey")); // 密码（加密后的字符串）
			String version = register.getVersion(); // 用版本字串来区分不同的主渠道
			String model = register.getModel(); // 手机操作系统版本
			int channel = register.getChannel(); // 子渠道标示
			String email = CryptionUtil.Decrypt(register.getEmail(), ServiceManager.getManager().getConfiguration().getString("deckey")); // 邮箱地址（加密后的字符串）
			String inviteAccount = "";
			if (register.getInviteAccount().length > 0) {
				inviteAccount = CryptionUtil.Decrypt(register.getInviteAccount(),
						ServiceManager.getManager().getConfiguration().getString("deckey")); // 邀请人（加密后的字符串）
			}

			LegacyRegister legacyRegister = new LegacyRegister();
			legacyRegister.setAccountId(player.getClient().getAccountId());
			legacyRegister.setPlayerId(player.getId());
			legacyRegister.setAccountName(accountName);
			legacyRegister.setPassWord(passWord);
			legacyRegister.setVersion(version);
			legacyRegister.setModel(model);
			legacyRegister.setChannel(channel);
			legacyRegister.setEmail(email);
			legacyRegister.setInviteAccount(inviteAccount);
			if (player.getPlayer().getBindInviteCode() == null || player.getPlayer().getBindInviteCode().length() == 0)
				player.getPlayer().setBindInviteCode(inviteAccount.toUpperCase());
			ServiceManager.getManager().getAccountSkeleton().send(legacyRegister);

		} catch (Exception ex) {
			log.error(ex, ex);
		}
		return null;
	}
}