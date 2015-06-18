package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.Login;
import com.wyd.empire.protocol.data.server.AccountLogin;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.request.LoginRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> LoginHandler</code>Protocol.ACCOUNT_Login登陆协议处理
 * 
 * @since JDK 1.6
 */
public class LoginHandler implements IDataHandler {
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(LoginHandler.class);
	private Logger logingLog = Logger.getLogger("logingLog");

	public AbstractData handle(AbstractData data) throws Exception {
		Login login = (Login) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		String accountName = login.getAccountName();
		String passWord = login.getPassWord();
		String version = login.getVersion();
		int channel = login.getChannel();
		logingLog.info("account:" + accountName);
		// WorldServer 是否在维护
		if (WorldServer.config.isMaintance()) {
			String ms = TipMessages.LOGIN_SIM_MESSAGE;
			throw new ProtocolException(ms, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
		// 根据session id创建客户端对象
		Client client = session.getAndCreateClient(data.getSessionId());
		if (client.getStatus() == Client.STATUS.INIT) {
			AccountLogin accountLogin = new AccountLogin();
			accountLogin.setName(accountName);
			accountLogin.setPassword(passWord);
			accountLogin.setChannel(channel);
			accountLogin.setIp(client.getIp());
			LoginRequest loginRequest = new LoginRequest(data.getSerial(), data.getSessionId(), session, accountName, passWord, version,
					channel, false, null);
			// 根据登陆请求的参数创建loginRequset对象，接着往GameAccount服务器发送验证请求,
			// 根据serial值把loginRequset对象加入requestService里的map里
			ServiceManager.getManager().getRequestService().add(accountLogin.getSerial(), loginRequest);
			// 发送至账号服务器
			ServiceManager.getManager().getAccountSkeleton().send(accountLogin);
		}
		return null;
	}
}