package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.AddNearbyFriend;
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
public class AddNearbyFriendHandler implements IDataHandler {
	private Logger log;

	public AddNearbyFriendHandler() {
		this.log = Logger.getLogger(AddNearbyFriendHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		AddNearbyFriend addNearbyFriend = (AddNearbyFriend) data;
		try {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			if (nearbyId > 0 && addNearbyFriend.getFriendInfoId() > 0 && nearbyId != addNearbyFriend.getFriendInfoId()) {
				addNearbyFriend.setMyInfoId(nearbyId);
				ServiceManager.getManager().getNearbyService().sendData(addNearbyFriend);
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			GameLogService.nearbyFriend(player.getId(), player.getLevel(), 1, addNearbyFriend.getFriendInfoId());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}