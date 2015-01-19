package com.wyd.empire.world.server.handler.bossmaproom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.GetRoomInfo;
import com.wyd.empire.protocol.data.bossmaproom.SendRoomInfo;
import com.wyd.empire.world.bean.BossmapReward;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class GetRoomInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRoomInfoHandler.class);

	// 获得房间信息
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetRoomInfo getRoomInfo = (GetRoomInfo) data;
		try {
			Map map = ServiceManager.getManager().getMapsService().getBossMapById(getRoomInfo.getMapId());
			List<BossmapReward> brList = ServiceManager.getManager().getBossmapRewardService()
					.getBossmapRewardBymapId(map.getId(), player.getPlayer().getSex().intValue(), Common.BOSSMAP_REWARD_SHOW_COUNT);
			List<Integer> rewardList = new ArrayList<Integer>();
			for (BossmapReward br : brList) {
				rewardList.add(br.getShopItemId());
			}
			SendRoomInfo sendRoomInfo = new SendRoomInfo(data.getSessionId(), data.getSerial());
			sendRoomInfo.setMapShortName(map.getNameShort());
			sendRoomInfo.setPlayLevel(map.getLevel());
			sendRoomInfo.setRewardList(ServiceUtils.ListToInts(rewardList));
			session.write(sendRoomInfo);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
