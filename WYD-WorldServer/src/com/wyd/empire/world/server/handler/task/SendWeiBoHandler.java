package com.wyd.empire.world.server.handler.task;

import org.apache.log4j.Logger;

import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送微博成功
 * 
 * @author sunzx
 *
 */
public class SendWeiBoHandler implements IDataHandler {
	private Logger log = Logger.getLogger(SendWeiBoHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			ServiceManager.getManager().getTaskService().checkWeiBo(player);
			GameLogService.sendWeibo(player.getId(), player.getLevel(), player.getInterfaceId());
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ILLEGAL_OPERATION_ERROR, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
