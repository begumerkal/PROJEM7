package com.wyd.empire.world.server.handler.rebirth;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.rebirth.GetRebirthInfoOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家的转生信息
 * 
 * @since JDK 1.6
 */
public class GetRebirthInfoHandler implements IDataHandler {
	private Logger log;

	public GetRebirthInfoHandler() {
		this.log = Logger.getLogger(GetRebirthInfoHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			int rebirthItemNum = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getPlayerItemNum(player.getId(), Common.REBIRTHLEVEL1ID);
			GetRebirthInfoOk getRebirthInfoOk = new GetRebirthInfoOk(data.getSessionId(), data.getSerial());
			getRebirthInfoOk.setRebirthLevel(player.getPlayer().getZsLevel());
			getRebirthInfoOk.setRebirthTopLevel(Common.REBIRTHTOPLEVEL);
			getRebirthInfoOk.setRebirthNum(rebirthItemNum);
			getRebirthInfoOk.setRebirthNeedNum(Common.REBIRTHLEVEL1NUM);
			getRebirthInfoOk.setRebirthRemark(ServiceManager.getManager().getVersionService().getVersion().getRebirthRemark());
			getRebirthInfoOk.setDiamonds(player.getDiamond());
			int pLevel = player.getLevel();
			int needDiamonds = (int) (10 * Math.pow(99 - pLevel, 2));
			getRebirthInfoOk.setRebirthDiamonds(needDiamonds);
			session.write(getRebirthInfoOk);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}