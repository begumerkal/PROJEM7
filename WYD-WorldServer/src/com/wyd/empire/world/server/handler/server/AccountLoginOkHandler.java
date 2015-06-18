package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.LoginFail;
import com.wyd.empire.protocol.data.account.LoginOk;
import com.wyd.empire.protocol.data.account.RepeatLogin;
import com.wyd.empire.protocol.data.server.AccountLoginOk;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.request.LoginRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 账号登录成功协议处理
 * 
 * @since JDK 1.6
 */
public class AccountLoginOkHandler implements IDataHandler {
	Logger log = Logger.getLogger(AccountLoginOkHandler.class);
	public AbstractData handle(AbstractData data) throws Exception {
		AccountLoginOk legacyLoginOk = (AccountLoginOk) data;
		LoginRequest request = (LoginRequest) ServiceManager.getManager().getRequestService().remove(legacyLoginOk.getSerial());
		ConnectSession session = request.getConnectionSession();
		try {
			if (legacyLoginOk.getStatus() == 0) {//登录成功
				int accountId = legacyLoginOk.getAccountId();
				String name = legacyLoginOk.getName();
				String password = legacyLoginOk.getPassword();
				Client client = session.getClient(request.getSessionId());
				
				if ((client != null) && (client.getStatus() == Client.STATUS.INIT)) {
					Client client1 = session.getClientByAccountId(accountId);
					if (client1 != null) {// 主账号重复登录处理
						RepeatLogin repeatLogin = new RepeatLogin(client1.getSessionId(), 0);
						repeatLogin.setMessage(TipMessages.REPEATLOGIN);
						session.write(repeatLogin);
//						session.killSession(client1.getSessionId());
						ServiceManager.getManager().getConnectService().forceLogout(accountId);
					}
					client.setStatus(Client.STATUS.LOGIN);
					client.setAccountId(accountId);
					client.setName(name);
					client.setPassword(password);
					client.setChannel(request.getSubChannel());
					session.registerClientWithAccountId(client);
					LoginOk loginOk = new LoginOk(request.getSessionId(), request.getId());
					session.write(loginOk);
					this.log.info("AccountID[" + accountId + "][Login Ok]");
				}

			} else if (legacyLoginOk.getStatus()==1) {
				LoginFail loginFail = new LoginFail(request.getSessionId(), request.getId());
				loginFail.setMessage(TipMessages.LOGINFAIL);
				session.write(loginFail);
			}
		} catch (Exception e) {
			log.info(e, e);
		}

		return null;
	}
}