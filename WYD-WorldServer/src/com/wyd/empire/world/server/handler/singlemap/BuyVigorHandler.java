package com.wyd.empire.world.server.handler.singlemap;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.singlemap.BuyVigorOk;
import com.wyd.empire.world.bean.VigorPrice;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class BuyVigorHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPointsHandler.class);
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int count = player.getBuyVigorCount();
			if (count >= player.getMaxBuyVigorCount()) {
				// 非法操作
				throw new ProtocolException(ErrorMessages.ILLEGAL_OPERATION_ERROR, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			count++;
			int price = 0, vigor = 0;
			// 根据次数去获得配置
			VigorPrice vigorPrice = manager.getVigorService().getPriceByCount(count);
			if (vigorPrice != null) {
				vigor = vigorPrice.getVigor();
				price = vigorPrice.getPrice();
				if (player.getDiamond() < price) {
					throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				boolean result = player.buyVigor(vigor);
				if (result) {
					try {
						manager.getPlayerService().useTicket(player, price, TradeService.ORIGIN_BUYVIGOR, null, null,
								"第" + count + "次购买，得到活力" + vigor);
						player.addBuyVigorCount();
					} catch (Exception ex) {
						player.useVigor(-vigor);// 扣钱失败则回滚
					}
				} else {
					// TODO 购买失败
					throw new ProtocolException(ErrorMessages.ILLEGAL_OPERATION_ERROR, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
			} else {
				// 非法操作
				throw new ProtocolException(ErrorMessages.ILLEGAL_OPERATION_ERROR, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			BuyVigorOk ok = new BuyVigorOk(data.getSessionId(), data.getSerial());
			ok.setVigor(player.getVigor());
			session.write(ok);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}
}
