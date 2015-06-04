package com.wyd.empire.world.server.handler.system;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.system.GetItemPriceAndVipOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetItemPriceAndVipHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetItemPriceAndVipHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			GetItemPriceAndVipOk getItemPriceAndVipOk = new GetItemPriceAndVipOk(data.getSessionId(), data.getSerial());

			getItemPriceAndVipOk.setDiscount(100);

			getItemPriceAndVipOk.setUnitPrice(ServiceManager.getManager().getVersionService().getVersion().getUnitPrice());
			session.write(getItemPriceAndVipOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
		return null;
	}
}
