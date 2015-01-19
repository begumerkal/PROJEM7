package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.GetNearbyFriendList;
import com.wyd.empire.protocol.data.nearby.GetNearbyFriendListOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取附近好友列表
 * 
 * @author zguoqiu
 */
public class GetNearbyFriendListHandler implements IDataHandler {
	private Logger log;

	public GetNearbyFriendListHandler() {
		this.log = Logger.getLogger(GetNearbyFriendListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetNearbyFriendList getNearbyFriendList = (GetNearbyFriendList) data;
		try {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			if (nearbyId > 0) {
				getNearbyFriendList.setPlayerInfoId(nearbyId);
				ServiceManager.getManager().getNearbyService().sendData(getNearbyFriendList);
			} else {
				GetNearbyFriendListOk getNearbyFriendListOk = new GetNearbyFriendListOk(data.getSessionId(), data.getSerial());
				getNearbyFriendListOk.setPlayerId(player.getId());
				getNearbyFriendListOk.setPlayerInfoId(new int[]{});
				getNearbyFriendListOk.setAvatarURL(new String[]{});
				getNearbyFriendListOk.setSex(new byte[]{});
				getNearbyFriendListOk.setPlayerName(new String[]{});
				getNearbyFriendListOk.setFighting(new int[]{});
				getNearbyFriendListOk.setDistance(new int[]{});
				getNearbyFriendListOk.setMailCount(new int[]{});
				getNearbyFriendListOk.setIsOnline(new boolean[]{});
				getNearbyFriendListOk.setSuitHead(new String[]{});
				getNearbyFriendListOk.setSuitFace(new String[]{});
				session.write(getNearbyFriendListOk);
			}
			GameLogService.getNearbyFriendList(player.getId(), player.getLevel());
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}