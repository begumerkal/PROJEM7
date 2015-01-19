package com.wyd.empire.world.server.handler.strengthen;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.GetConsumeInfo;
import com.wyd.empire.protocol.data.strengthen.GetConsumeInfoOk;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

public class GetConsumeInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetConsumeInfoHandler.class);
	/** 单颗石头合成成功率 */
	public static final int MERGERATE = 2000;

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetConsumeInfo getConsumeInfo = (GetConsumeInfo) data;
		try {
			int infoType = getConsumeInfo.getInfoType();
			int gold = 0;
			int successRate = 10000;
			switch (infoType) {
				case 1 :
					gold = (int) ServiceManager.getManager().getBuffService().getAddition(player, MergeHandler.MERGEGOLD, Buff.CGOLDLOW);
					successRate = MERGERATE;
					break;
				case 2 :
					gold = (int) ServiceManager.getManager().getBuffService().getAddition(player, MosaicHandler.MOSAICGOLD, Buff.CGOLDLOW);
					break;
				case 3 :
					gold = (int) ServiceManager.getManager().getBuffService().getAddition(player, 1000, Buff.CGOLDLOW);
					break;
			}
			GetConsumeInfoOk getConsumeInfoOk = new GetConsumeInfoOk(data.getSessionId(), data.getSerial());
			getConsumeInfoOk.setGold(gold);
			getConsumeInfoOk.setSuccessRate(successRate);
			session.write(getConsumeInfoOk);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}