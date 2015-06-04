package com.wyd.empire.world.server.handler.purchase;

import org.apache.log4j.Logger;

import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取兑换比率列表
 * 
 * @author Administrator
 */
public class RequestSmsCodeSerialidHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestSmsCodeSerialidHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ServiceManager.getManager().getOrderSerialService().addSerialInfo(data);
		return null;
	}
}
