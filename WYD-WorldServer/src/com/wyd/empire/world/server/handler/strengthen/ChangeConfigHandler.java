package com.wyd.empire.world.server.handler.strengthen;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.ChangeConfigOk;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 属性转移配置
 * 
 * @author zengxc
 */
public class ChangeConfigHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChangeConfigHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		// 获得特殊标示
		Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
		Integer diamondToGold = map.get("diamondToGold");
		Integer discount = map.get("discount");
		diamondToGold = diamondToGold == null ? 0 : diamondToGold;
		discount = discount == null ? 0 : discount;
		ChangeConfigOk ok = new ChangeConfigOk(data.getSessionId(), data.getSerial());
		ok.setDiamondToGold(diamondToGold);
		ok.setDiscount(discount);
		session.write(ok);
	}
}
