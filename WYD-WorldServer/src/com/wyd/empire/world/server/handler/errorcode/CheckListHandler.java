package com.wyd.empire.world.server.handler.errorcode;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.errorcode.CheckOk;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取错误列表
 * 
 * @author Administrator
 */
public class CheckListHandler implements IDataHandler {
	Logger log = Logger.getLogger(CheckListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			OperationConfig config = (OperationConfig) ServiceManager.getManager().getVersionService().getService()
					.get(OperationConfig.class, 1);
			CheckOk checkOK = new CheckOk(data.getSessionId(), data.getSerial());
			checkOK.setCheckVersion(config.getCheckVersion());
			session.write(checkOK);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
