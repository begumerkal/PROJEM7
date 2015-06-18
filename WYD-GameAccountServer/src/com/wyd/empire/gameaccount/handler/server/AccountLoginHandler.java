package com.wyd.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;

import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.service.impl.AccountService;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.empire.protocol.data.server.AccountLogin;
import com.wyd.empire.protocol.data.server.AccountLoginOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 账号登录
 * 
 * @since JDK 1.6
 */
public class AccountLoginHandler implements IDataHandler {
	private Logger log = Logger.getLogger(AccountLoginHandler.class);

	public AbstractData handle(AbstractData data) {
		AccountLogin login = (AccountLogin) data;
		AcceptSession session = (AcceptSession) data.getHandlerSource();
		String name = login.getName();
		String pwd = login.getPassword();
		int channel = login.getChannel();
		String worldServerId = session.getWorldServerId();
		String ip = login.getIp();
		String[] strArr = worldServerId.split("_");
		int machinecode = Integer.valueOf(strArr[1]);

		int nowTime = (int) (System.currentTimeMillis() / 100);
		AccountService accountService = ServiceFactory.getServiceFactory().getAccountService();
		try {
			AccountLoginOk loginOk = new AccountLoginOk(data.getSessionId(), data.getSerial());
			Account account = accountService.login(name, session.getWorldServerId());
			if (account == null) {
				Account newAccount = new Account();
				newAccount.setUsername(name);
				newAccount.setPassword(pwd);
				newAccount.setChannel(channel);
				newAccount.setServerid(worldServerId);
				newAccount.setCreateTime(nowTime);
				newAccount.setMachinecode(machinecode);
				newAccount.setStatus(1);
				newAccount.setTotalLoginTimes(0);
				newAccount.setIpAddress(ip);
				account = accountService.createAccount(newAccount);
			}
			if (account != null) {
				loginOk.setAccountId((int) account.getId());
				loginOk.setName(account.getUsername());
				loginOk.setPassword(account.getPassword());
				loginOk.setChannel(channel);
				if (account.getStatus() == AccountService.ACCOUNT_STATUS_NORMAL) {
					account.setLastLoginTime(nowTime);
					account.setTotalLoginTimes(account.getTotalLoginTimes() + 1);
					account.setOnLineTime(0);
					accountService.saveAccount(account);
					loginOk.setStatus(0);
				} else {
					loginOk.setStatus(2);
				}
			} else {
				loginOk.setName("");
				loginOk.setPassword("");
				loginOk.setStatus(1);
			}
			return loginOk;
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
			AccountLoginOk loginOk = new AccountLoginOk(data.getSessionId(), data.getSerial());
			loginOk.setName("");
			loginOk.setPassword("");
			loginOk.setStatus(2);
			session.send(loginOk);
		}
		return null;
	}
}
