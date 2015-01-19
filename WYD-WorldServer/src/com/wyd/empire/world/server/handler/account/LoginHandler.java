package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.Login;
import com.wyd.empire.protocol.data.server.LegacyLogin;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.exception.ErrorMessages;
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
	private Logger log;
	private Logger logingLog = Logger.getLogger("logingLog");

	public LoginHandler() {
		this.log = Logger.getLogger(LoginHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		Login login = (Login) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		String udid = CryptionUtil.Decrypt(login.getUdid(), ServiceManager.getManager().getConfiguration().getString("deckey"));
		String accountName = CryptionUtil.Decrypt(login.getAccountName(), ServiceManager.getManager().getConfiguration()
				.getString("deckey"));
		String passWord = CryptionUtil.Decrypt(login.getPassWord(), ServiceManager.getManager().getConfiguration().getString("deckey"));
		String oldUdid = CryptionUtil.Decrypt(login.getOldUdid(), ServiceManager.getManager().getConfiguration().getString("deckey"));
		if (udid.equals(accountName)) {
			if (!udid.equals(passWord)) {
				throw new ProtocolException(ErrorMessages.LOGIN_FIELD_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
		logingLog.info("account:" + accountName);
		String version = login.getVersion();
		int channel = login.getChannel();
		// 根据用户名是否为白名单
		if (Server.config.isMaintance()) {
			String ms = ServiceManager.getManager().getConfiguration().getString("");
			if ((ms == null) || (ms.trim().equals(""))) {
				ms = TipMessages.LOGIN_SIM_MESSAGE;
			}
			throw new ProtocolException(ms, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
		// 根据session id创建客户端对象
		Client client = session.getAndCreateClient(data.getSessionId());
		if (client.getStatus() == Client.STATUS.INIT) {
			LegacyLogin legacyLogin = new LegacyLogin();
			legacyLogin.setUdid(udid);
			legacyLogin.setName(accountName);
			legacyLogin.setPassword(passWord);
			legacyLogin.setChannel(channel);
			legacyLogin.setIsChannelLogon(login.getIsChannelLogon());
			legacyLogin.setOldUdid(oldUdid);
			LoginRequest loginRequest = new LoginRequest(data.getSerial(), data.getSessionId(), session, accountName, passWord, version,
					channel, false, null);
			// 根据登陆请求的参数创建loginRequset对象，接着往GameAccount服务器发送验证请求,
			// 根据serial值把loginRequset对象加入requestService里的map里
			ServiceManager.getManager().getRequestService().add(legacyLogin.getSerial(), loginRequest);
			ServiceManager.getManager().getAccountSkeleton().send(legacyLogin);
		}
		// for (int i = 0; i < 5; i++) {
		// MyThread myThread = new MyThread();
		// myThread.start();
		// }
	}

	// public class MyThread extends Thread {
	// public void run() {
	// String baseUdid = "0000000000000000000000000000000000";
	// for (int i = 0; i < 100000; i++) {
	// try {
	// Thread.sleep(10);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// String udid = baseUdid+ServiceUtils.getRandomNum(100000, 1000000);
	// LegacyLogin legacyLogin = new LegacyLogin();
	// legacyLogin.setUdid(udid);
	// legacyLogin.setName(udid);
	// legacyLogin.setPassword(udid);
	// legacyLogin.setChannel(1001);
	// ServiceManager.getManager().getAccountSkeleton().send(legacyLogin);
	// }
	// }
	// }
}