package com.wyd.empire.world.server.handler.purchase;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.purchase.SendProductId;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 商品列表
 * 
 * @author Administrator
 */
public class SendProductIdHandler implements IDataHandler {
	private Logger log = Logger.getLogger("rechargeLog");

	// 商品列表
	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SendProductId sendProductId = (SendProductId) data;
		try {
			log.info("player:" + player.getId() + "-----------productid:" + sendProductId.getProductId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
