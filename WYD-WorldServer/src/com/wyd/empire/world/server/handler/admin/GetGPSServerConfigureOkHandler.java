package com.wyd.empire.world.server.handler.admin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.admin.GetGPSServerConfigureOk;
import com.wyd.empire.world.request.GetGPSServerInfoRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取GPS服务配置成功
 * 
 * @author chenjie
 */
public class GetGPSServerConfigureOkHandler implements IDataHandler {
	private Logger log;

	public GetGPSServerConfigureOkHandler() {
		this.log = Logger.getLogger(GetGPSServerConfigureOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {

		GetGPSServerConfigureOk getCfgOk = (GetGPSServerConfigureOk) data;
		GetGPSServerInfoRequest request = (GetGPSServerInfoRequest) ServiceManager.getManager().getRequestService()
				.remove(getCfgOk.getSerial());
		ConnectSession session = request.getConnectionSession();
		try {
			GetGPSServerConfigureOk infoOk = new GetGPSServerConfigureOk(data.getSessionId(), data.getSerial());
			infoOk.setMaxresults(getCfgOk.getMaxresults());
			infoOk.setPagesize(getCfgOk.getPagesize());
			infoOk.setThreshold(getCfgOk.getThreshold());
			infoOk.setUpdatetime(getCfgOk.getUpdatetime());
			infoOk.setMaxfriendcount(getCfgOk.getMaxfriendcount());
			session.write(infoOk);
		} catch (Exception e) {
			log.info(e, e);
			System.out.println(e.getMessage());
		}
	}
}