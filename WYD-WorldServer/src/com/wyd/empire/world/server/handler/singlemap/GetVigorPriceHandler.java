package com.wyd.empire.world.server.handler.singlemap;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.singlemap.GetVigorPriceOk;
import com.wyd.empire.world.bean.VigorPrice;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetVigorPriceHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPointsHandler.class);
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int count = player.getBuyVigorCount();
			count++;
			int price = -1, vigor = 0;
			// 根据次数去获得配置
			VigorPrice vigorPrice = manager.getVigorService().getPriceByCount(count);
			if (vigorPrice != null) {
				int vipLevel = vigorPrice.getVipLevel();
				if (vipLevel > 0) {
					if (player.isVip() && player.getPlayer().getVipLevel() >= vipLevel) {
						if (player.getDiamond() < vigorPrice.getPrice()) {
							price = -2;
						} else {
							price = vigorPrice.getPrice();
							vigor = vigorPrice.getVigor();
						}
					}
				} else {
					if (player.getDiamond() < vigorPrice.getPrice()) {
						price = -2;
					} else {
						price = vigorPrice.getPrice();
						vigor = vigorPrice.getVigor();
					}
				}
			}
			GetVigorPriceOk ok = new GetVigorPriceOk(data.getSessionId(), data.getSerial());
			ok.setPrice(price);
			ok.setVigor(vigor);
			session.write(ok);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}
}
