package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.RemoveNearbyFriend;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 添加附近好友
 * 
 * @author zguoqiu
 */
public class RemoveNearbyFriendHandler implements IDataHandler {
	private Logger log;

	public RemoveNearbyFriendHandler() {
		this.log = Logger.getLogger(RemoveNearbyFriendHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		RemoveNearbyFriend removeNearbyFriend = (RemoveNearbyFriend) data;
		try {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			if (nearbyId > 0 && removeNearbyFriend.getFriendInfoId() > 0) {
				removeNearbyFriend.setMyInfoId(nearbyId);
				ServiceManager.getManager().getNearbyService().sendData(removeNearbyFriend);
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			GameLogService.nearbyFriend(player.getId(), player.getLevel(), 2, removeNearbyFriend.getFriendInfoId());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}