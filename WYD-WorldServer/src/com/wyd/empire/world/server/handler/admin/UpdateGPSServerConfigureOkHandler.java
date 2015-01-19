package com.wyd.empire.world.server.handler.admin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.admin.UpdateGPSServerConfigureOk;
import com.wyd.empire.world.request.UpdateGPSServerInfoRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新GPS服务配置成功
 * 
 * @author chenjie
 */
public class UpdateGPSServerConfigureOkHandler implements IDataHandler {
	private Logger log;

	public UpdateGPSServerConfigureOkHandler() {
		this.log = Logger.getLogger(UpdateGPSServerConfigureOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {

		UpdateGPSServerConfigureOk updateCfgOk = (UpdateGPSServerConfigureOk) data;
		UpdateGPSServerInfoRequest request = (UpdateGPSServerInfoRequest) ServiceManager.getManager().getRequestService()
				.remove(updateCfgOk.getSerial());
		ConnectSession session = request.getConnectionSession();
		try {
			UpdateGPSServerConfigureOk infoOk = new UpdateGPSServerConfigureOk(data.getSessionId(), data.getSerial());
			infoOk.setResult(updateCfgOk.getResult());
			session.write(infoOk);
		} catch (Exception e) {
			log.info(e, e);
			System.out.println(e.getMessage());
		}
	}
}