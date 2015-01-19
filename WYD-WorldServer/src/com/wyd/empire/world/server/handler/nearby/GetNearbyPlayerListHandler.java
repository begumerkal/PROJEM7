package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.GetNearbyPlayerList;
import com.wyd.empire.protocol.data.nearby.GetNearbyPlayerListOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取附近玩家列表
 * 
 * @author zguoqiu
 */
public class GetNearbyPlayerListHandler implements IDataHandler {
	private Logger log;

	public GetNearbyPlayerListHandler() {
		this.log = Logger.getLogger(GetNearbyPlayerListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetNearbyPlayerList getNearbyPlayerList = (GetNearbyPlayerList) data;
		try {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			if (nearbyId > 0) {
				getNearbyPlayerList.setPlayerInfoId(nearbyId);
				ServiceManager.getManager().getNearbyService().sendData(getNearbyPlayerList);
			} else {
				GetNearbyPlayerListOk getNearbyPlayerListOk = new GetNearbyPlayerListOk(data.getSessionId(), data.getSerial());
				getNearbyPlayerListOk.setPlayerId(player.getId());
				getNearbyPlayerListOk.setPlayerInfoId(new int[]{});
				getNearbyPlayerListOk.setAvatarURL(new String[]{});
				getNearbyPlayerListOk.setSex(new byte[]{});
				getNearbyPlayerListOk.setPlayerName(new String[]{});
				getNearbyPlayerListOk.setFighting(new int[]{});
				getNearbyPlayerListOk.setDistance(new int[]{});
				getNearbyPlayerListOk.setMailCount(new int[]{});
				getNearbyPlayerListOk.setIsOnline(new boolean[]{});
				getNearbyPlayerListOk.setIsFriend(new boolean[]{});
				getNearbyPlayerListOk.setSuitHead(new String[]{});
				getNearbyPlayerListOk.setSuitFace(new String[]{});
				session.write(getNearbyPlayerListOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}