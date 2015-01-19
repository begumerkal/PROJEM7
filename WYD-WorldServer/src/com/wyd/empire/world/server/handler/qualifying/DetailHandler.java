package com.wyd.empire.world.server.handler.qualifying;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.qualifying.DetailOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.handler.task.GetEverydayRewardListHandler;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获得说明界面
 * 
 * @author Administrator
 * 
 */
public class DetailHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetEverydayRewardListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			DetailOk detailOk = new DetailOk(data.getSessionId(), data.getSerial());
			detailOk.setDetail(TipMessages.RANK_DETAIL);
			session.write(detailOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_GRLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
