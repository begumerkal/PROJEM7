package com.wyd.empire.world.server.handler.admin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.admin.UpdateGPSServerConfigure;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.request.UpdateGPSServerInfoRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新GPS服务配置
 * 
 * @author chenjie
 */
public class UpdateGPSServerConfigureHandler implements IDataHandler {
	private Logger log = Logger.getLogger(UpdateGPSServerConfigureHandler.class);

	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		try {
			UpdateGPSServerConfigure updateCfg = (UpdateGPSServerConfigure) data;
			UpdateGPSServerInfoRequest request = new UpdateGPSServerInfoRequest(data.getSerial(), data.getSessionId(), session,
					updateCfg.getThreshold(), updateCfg.getMaxresults(), updateCfg.getUpdatetime(), updateCfg.getPagesize(),
					updateCfg.getMaxfriendcount());
			ServiceManager.getManager().getRequestService().add(updateCfg.getSerial(), request);
			ServiceManager.getManager().getNearbyService().sendData(updateCfg);
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