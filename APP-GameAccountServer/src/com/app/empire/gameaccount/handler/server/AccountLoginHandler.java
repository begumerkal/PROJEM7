package com.app.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;

import com.app.empire.gameaccount.bean.Account;
import com.app.empire.gameaccount.service.factory.ServiceFactory;
import com.app.empire.gameaccount.service.impl.AccountService;
import com.app.empire.gameaccount.session.AcceptSession;
import com.app.empire.protocol.data.server.AccountLogin;
import com.app.empire.protocol.data.server.AccountLoginOk;
import com.app.protocol.data.AbstractData;
import com.app.protocol.handler.IDataHandler;
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
		String ip = login.getIp();
		String clientModel = login.getClientModel();// 手机型号
		String systemName = login.getSystemName();// 手机系统
		String systemVersion = login.getSystemVersion();// 系统版本

		String worldServerId = session.getWorldServerId();

		String[] strArr = worldServerId.split("-");
		int machinecode = Integer.valueOf(strArr[2]);

		int nowTime = (int) (System.currentTimeMillis() / 100);
		AccountService accountService = ServiceFactory.getServiceFactory().getAccountService();
		try {
			AccountLoginOk loginOk = new AccountLoginOk(data.getSessionId(), data.getSerial());
			Account account = accountService.login(name, channel, worldServerId);
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
				newAccount.setClientModel(clientModel);
				newAccount.setSystemName(systemName);
				newAccount.setSystemVersion(systemVersion);
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
