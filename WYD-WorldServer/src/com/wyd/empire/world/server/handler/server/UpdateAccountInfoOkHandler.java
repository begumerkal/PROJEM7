package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.admin.UpdateAccountResultOk;
import com.wyd.empire.protocol.data.server.UpdateAccountInfoOk;
import com.wyd.empire.world.request.UpdateAccountRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code>UpdateAccountInfoOKHandler</code>更新个人信息
 * 
 * @since JDK 1.6
 */
public class UpdateAccountInfoOkHandler implements IDataHandler {
	Logger log;

	/**
	 * 初始化日志
	 */
	public UpdateAccountInfoOkHandler() {
		this.log = Logger.getLogger(UpdateAccountInfoOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		UpdateAccountInfoOk updateAccountInfoOk = (UpdateAccountInfoOk) data;
		UpdateAccountRequest request = (UpdateAccountRequest) ServiceManager.getManager().getRequestService()
				.remove(updateAccountInfoOk.getSerial());
		ConnectSession session = request.getConnectionSession();
		try {
			UpdateAccountResultOk infoOk = new UpdateAccountResultOk(data.getSessionId(), data.getSerial());
			infoOk.setContent(updateAccountInfoOk.getContent());
			session.write(infoOk);
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}