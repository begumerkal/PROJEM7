package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.LoginFail;
import com.wyd.empire.protocol.data.account.LoginOk;
import com.wyd.empire.protocol.data.account.RepeatLogin;
import com.wyd.empire.protocol.data.account.RoleActorLogin;
import com.wyd.empire.protocol.data.server.LegacyLoginOk;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.request.LoginRequest;
import com.wyd.empire.world.server.handler.account.RoleActorLoginHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code>LegacyLoginOkHandler</code>账号(重新)登录成功协议处理
 * 
 * @since JDK 1.6
 */
public class LegacyLoginOkHandler implements IDataHandler {
	Logger log = Logger.getLogger(LegacyLoginOkHandler.class);
	static int i = 0;

	public AbstractData handle(AbstractData data) throws Exception {
		// System.out.println("----------------------------"+i++);
		LegacyLoginOk legacyLoginOk = (LegacyLoginOk) data;
		LoginRequest request = (LoginRequest) ServiceManager.getManager().getRequestService().remove(legacyLoginOk.getSerial());
		ConnectSession session = request.getConnectionSession();
		try {
			if (0 == legacyLoginOk.getStatus()) {
				int accountId = legacyLoginOk.getAccountId();
				int gameAccountId = legacyLoginOk.getGameAccountId();
				String name = legacyLoginOk.getName();
				String password = legacyLoginOk.getPassword();
				int tokenAmount = legacyLoginOk.getTokenAmount();
				Client client = session.getClient(request.getSessionId());
				if ((client != null) && (client.getStatus() == Client.STATUS.INIT)) {
					Client client1 = session.getClientByAccountId(accountId);
					if (client1 != null) {// 主账号重复登录处理
						RepeatLogin repeatLogin = new RepeatLogin(client1.getSessionId(), 0);
						repeatLogin.setMessage(TipMessages.REPEATLOGIN);
						session.write(repeatLogin);
//						ServiceManager.getManager().getConnectService().writeTo(repeatLogin, client1.getPlayerId());
						session.killSession(client1.getSessionId());
						// ServiceManager.getManager().getConnectService().forceLogout(accountId);
					}
					client.setStatus(Client.STATUS.LOGIN);
					client.setAccountId(accountId);
					client.setGameAccountId(gameAccountId);
					client.setUdid(legacyLoginOk.getUdid());
					client.setName(name);
					client.setPassword(password);
					client.setTokenAmount(tokenAmount);
					client.setChannel(request.getSubChannel());
					session.registerClientWithAccountId(client);
					LoginOk loginOk = new LoginOk(request.getSessionId(), request.getId());
					
					session.write(loginOk);
					this.log.info("AccountID[" + accountId + "][Login Ok]");
				}
			} else if (1 == legacyLoginOk.getStatus()) {
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