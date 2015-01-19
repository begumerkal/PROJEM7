package com.wyd.empire.world.server.handler.system;

import com.wyd.empire.protocol.data.system.ShakeHands;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code>ShakeHandsHandler</code>客户端网络握手协议。保持中间环节线路通畅<li>实现IDataHandler接口</li>
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 * 
 */
public class ShakeHandsHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		ShakeHands shakehands = (ShakeHands) data;
		if (null != session.getClient(data.getSessionId()) || 1 == shakehands.getCode()) {
			session.write(shakehands);
		}
	}
}