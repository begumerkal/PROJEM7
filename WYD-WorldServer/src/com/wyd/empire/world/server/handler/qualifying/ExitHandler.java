package com.wyd.empire.world.server.handler.qualifying;

import org.apache.log4j.Logger;

import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.task.GetEverydayRewardListHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 退出排位赛界面
 * 
 * @author Administrator
 * 
 */
public class ExitHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetEverydayRewardListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			ServiceManager.getManager().getRankPairService().deleteRandomRoom(player.getRoomId());

			if (null == ServiceManager.getManager().getRoomService().getRoom(player.getRoomId())) {
				player.setRoomId(0);
				return;
			}
			ServiceManager.getManager().getRoomService().exRoom(player.getRoomId(), 0, 0);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_QUIT_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
