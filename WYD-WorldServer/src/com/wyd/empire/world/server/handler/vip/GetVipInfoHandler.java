package com.wyd.empire.world.server.handler.vip;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.vip.GetVipInfoOK;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetVipInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetVipInfoHandler.class);
	private int[] exp = {100, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000, 150000};

	/**
	 * 获取会员vip信息
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			int vipLv = player.getPlayer().getVipLevel();
			int vipExp = player.getPlayer().getVipExp();
			int index = vipLv >= exp.length ? exp.length - 1 : vipLv;
			int nextLvExp = exp[index] - vipExp;
			boolean isReceiveDayPack = ServiceManager.getManager().getTaskService().getService().checkIsGetREward(player.getId(), 1, 1);

			GetVipInfoOK ok = new GetVipInfoOK(data.getSessionId(), data.getSerial());
			ok.setVipLv(vipLv);
			ok.setVipExp(vipExp);
			ok.setNextLvExp(nextLvExp);
			ok.setIsReceiveDayPack(isReceiveDayPack);
			session.write(ok);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
