package com.wyd.empire.gameaccount.handler.server;
import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.bean.Empireaccount;
import com.wyd.empire.gameaccount.service.IEmpireaccountService;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.service.impl.AccountService;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.empire.protocol.data.server.LegacyLogin;
import com.wyd.empire.protocol.data.server.LegacyLoginOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 类 <code>LegacyLoginHandler</code>Protocol.SERVER_LegacyLogin 角色重新登录协议处理
 * 
 * @since JDK 1.6
 */
public class LegacyLoginHandler implements IDataHandler {
	private Logger log = Logger.getLogger(LegacyLoginHandler.class);

	public AbstractData handle(AbstractData data) {
		LegacyLogin login = (LegacyLogin) data;
		AcceptSession session = (AcceptSession) data.getSource();
		String udid = login.getUdid();
		String name = login.getName();
		String pwd = login.getPassword();
		int channel = login.getChannel();
		try {
			LegacyLoginOk loginOk = new LegacyLoginOk(data.getSessionId(), data.getSerial());
			Account account = ServiceFactory.getFactory().getAccountService().getAccountByName(name);
			if (account == null) {
				account = ServiceFactory.getFactory().getAccountService().createAccount(name, pwd, udid);
			}

			if (account != null) {
				loginOk.setAccountId(account.getId());
				loginOk.setUdid(account.getUdid());
				loginOk.setName(account.getUsername());
				loginOk.setPassword(account.getPassword());
				loginOk.setChannel(channel);
				if (AccountService.ACCOUNT_STATUS_NORMAL == account.getStatus()) {
					IEmpireaccountService empireaccountService = ServiceFactory.getFactory().getEmpireaccountService();
					Empireaccount gameAccount = empireaccountService.login(account.getId(), login.getSource().getId());
					if (gameAccount == null) {
						String model = "Default";
						String version = "1.0";
						gameAccount = empireaccountService.createGameAccount(account.getId(), account.getUsername(), model, version,
								new Date(), "", login.getSource().getId(), channel);
					}else{
						gameAccount.setLastLoginTime(new java.sql.Timestamp(new Date().getTime()));
						gameAccount.setTotalLoginTimes(gameAccount.getTotalLoginTimes() + 1);
						gameAccount.setName(loginOk.getName());
						empireaccountService.updateGameAccount(gameAccount);
					}
					loginOk.setGameAccountId(gameAccount.getId());
				} else {
					loginOk.setStatus(2);
				}
			} else {
				loginOk.setUdid("");
				loginOk.setName("");
				loginOk.setPassword("");
				loginOk.setStatus(1);
			}
			return loginOk;
			// session.send(loginOk);
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
			LegacyLoginOk loginOk = new LegacyLoginOk(data.getSessionId(), data.getSerial());
			loginOk.setUdid("");
			loginOk.setName("");
			loginOk.setPassword("");
			loginOk.setStatus(2);
			session.send(loginOk);
		}
		return null;
	}
}
