package com.wyd.empire.world.server.handler.bulletin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bulletin.GetHelpOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取公告
 * 
 * @author Administrator
 */
public class GetHelpHandler implements IDataHandler {
	private Logger log;

	public GetHelpHandler() {
		this.log = Logger.getLogger(GetHelpHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			GetHelpOk getHelpOk = new GetHelpOk(data.getSessionId(), data.getSerial());
			getHelpOk.setHelp(ServiceManager.getManager().getVersionService().getHelp());
			session.write(getHelpOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}