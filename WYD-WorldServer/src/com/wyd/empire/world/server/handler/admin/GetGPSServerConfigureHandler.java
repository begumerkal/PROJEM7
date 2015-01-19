package com.wyd.empire.world.server.handler.admin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.admin.GetGPSServerConfigure;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.request.GetGPSServerInfoRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取GPS服务配置
 * 
 * @author chenjie
 */
public class GetGPSServerConfigureHandler implements IDataHandler {
	private Logger log = Logger.getLogger(GetGPSServerConfigureHandler.class);

	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		try {
			GetGPSServerConfigure getCfg = new GetGPSServerConfigure(data.getSessionId(), data.getSerial());
			GetGPSServerInfoRequest request = new GetGPSServerInfoRequest(data.getSerial(), data.getSessionId(), session);
			ServiceManager.getManager().getRequestService().add(getCfg.getSerial(), request);
			ServiceManager.getManager().getNearbyService().sendData(getCfg);
		} catch (Exception ex) {
			if (!ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}