package com.wyd.empire.world.server.handler.battle;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.GetTipsOk;
import com.wyd.empire.world.bean.Tips;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获得提示
 * 
 * @author Administrator
 *
 */
public class GetTipsHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetTipsHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<Tips> list = ServiceManager.getManager().getRewardItemsService().getTips();
			String[] tips = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				tips[i] = list.get(i).getTipStr();
			}

			GetTipsOk tipsOk = new GetTipsOk(data.getSessionId(), data.getSerial());
			tipsOk.setTips(tips);
			session.write(tipsOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
