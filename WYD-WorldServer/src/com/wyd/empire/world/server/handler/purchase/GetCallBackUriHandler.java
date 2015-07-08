package com.wyd.empire.world.server.handler.purchase;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.purchase.GetCallBackUriOk;
import com.wyd.empire.world.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取充值验证回调地址和端口
 * 
 * @author Administrator
 */
public class GetCallBackUriHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetCallBackUriHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			GetCallBackUriOk getCallBackUriOk = new GetCallBackUriOk(data.getSessionId(), data.getSerial());
			getCallBackUriOk.setIp(ServiceManager.getManager().getConfiguration().getString("callbackip"));
			// getCallBackUriOk.setIp("183.63.8.194");
			getCallBackUriOk.setPort(ServiceManager.getManager().getConfiguration().getString("http"));
			session.write(getCallBackUriOk);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
