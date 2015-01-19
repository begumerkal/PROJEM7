package com.wyd.empire.world.server.handler.errorcode;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.errorcode.GetListOk;
import com.wyd.empire.world.bean.ErrorCode;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取错误列表
 * 
 * @author Administrator
 * 
 */
public class GetListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetListHandler.class);

	// 读取房间列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			@SuppressWarnings("unchecked")
			List<ErrorCode> errorlist = ServiceManager.getManager().getVersionService().getService().getAll(ErrorCode.class);
			int[] errorCodes = new int[errorlist.size()];
			String[] errorContent = new String[errorlist.size()];
			ErrorCode errorCode;
			for (int i = 0; i < errorlist.size(); i++) {
				errorCode = errorlist.get(i);
				errorCodes[i] = errorCode.getErrorCode();
				errorContent[i] = errorCode.getErrorContent();
			}
			GetListOk getListOk = new GetListOk(data.getSessionId(), data.getSerial());
			getListOk.setErrorCode(errorCodes);
			getListOk.setErrorContent(errorContent);
			session.write(getListOk);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
