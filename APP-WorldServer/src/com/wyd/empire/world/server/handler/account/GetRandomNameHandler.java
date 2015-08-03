package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.GetRandomName;
import com.wyd.empire.protocol.data.account.GetRandomNameOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取随机角色名
 * 
 * @author Administrator
 */
public class GetRandomNameHandler implements IDataHandler {
	private Logger log;

	public GetRandomNameHandler() {
		this.log = Logger.getLogger(GetRandomNameHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetRandomName getRandomName = (GetRandomName) data;
		try {
			String name = "---";
			GetRandomNameOk getRandomNameOk = new GetRandomNameOk(data.getSessionId(), data.getSerial());
			getRandomNameOk.setName(name);
			return getRandomNameOk;
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
		return null;
	}

}