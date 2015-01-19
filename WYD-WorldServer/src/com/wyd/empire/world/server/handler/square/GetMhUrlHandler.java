package com.wyd.empire.world.server.handler.square;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.square.GetMhUrlOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取慕和tickets url
 * 
 * @author Administrator
 * 
 */
public class GetMhUrlHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetMhUrlHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			GetMhUrlOk getMhUrlOk = new GetMhUrlOk(data.getSessionId(), data.getSerial());
			getMhUrlOk.setSubmitMh(ServiceManager.getManager().getConfiguration().getString("submitMh"));
			getMhUrlOk.setListMh(ServiceManager.getManager().getConfiguration().getString("listMh"));
			session.write(getMhUrlOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
