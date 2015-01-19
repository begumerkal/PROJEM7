package com.wyd.empire.world.server.handler.bulletin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bulletin.GetAboutOk;
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
public class GetAboutHandler implements IDataHandler {
	private Logger log;

	public GetAboutHandler() {
		this.log = Logger.getLogger(GetAboutHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			GetAboutOk getAboutOk = new GetAboutOk(data.getSessionId(), data.getSerial());
			getAboutOk.setAbout(ServiceManager.getManager().getVersionService().getAbout());
			session.write(getAboutOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}