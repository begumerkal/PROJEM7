package com.wyd.empire.world.server.handler.challenge;

import org.apache.log4j.Logger;

import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家退出挑战赛界面
 * 
 * @author Administrator
 */
public class QuitChallengeInterfaceHandler implements IDataHandler {
	Logger log = Logger.getLogger(QuitChallengeInterfaceHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			player.setPlayerInChallenge(false);
			Room room = ServiceManager.getManager().getChallengeService().getRoom(player.getRoomId());
			if (player.getRoomId() != 0 && null != room) {
				int index = ServiceManager.getManager().getChallengeService().getPlayerSeat(player.getRoomId(), player.getId());
				ServiceManager.getManager().getChallengeService().exRoom(player.getRoomId(), index);
			}
			ServiceManager.getManager().getChallengeService().noticeOtherPlayer(player);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
