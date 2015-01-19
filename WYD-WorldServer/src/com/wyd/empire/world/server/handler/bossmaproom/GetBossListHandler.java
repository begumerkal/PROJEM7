package com.wyd.empire.world.server.handler.bossmaproom;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.SendBossList;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获得副本列表
 * 
 * @author Administrator
 */
public class GetBossListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetBossListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SendBossList sendBossList = new SendBossList(data.getSessionId(), data.getSerial());
		try {
			List<Map> mapList = ServiceManager.getManager().getMapsService().getBossMapList();
			int mapCount = mapList.size();
			int[] mapId = new int[mapCount];
			String[] mapShortName = new String[mapCount];
			int[] bossmapState = new int[mapCount];
			int[] bossmapLevel = new int[mapCount];
			Map map;
			PlayerBossmap pb;
			for (int i = 0; i < mapCount; i++) {
				map = mapList.get(i);
				mapId[i] = map.getId();
				mapShortName[i] = map.getNameShort();
				pb = ServiceManager.getManager().getPlayerBossmapService().loadPlayerBossMap(player.getId(), map.getId());
				if (map.getBossmapSerial() > player.getBossmap_progress()) {
					bossmapState[i] = 0;
				} else {
					if (null != pb && pb.getStar() > 0) {
						bossmapState[i] = 2;
					} else {
						bossmapState[i] = 1;
					}
				}
			}
			sendBossList.setBossmapId(mapId);
			sendBossList.setBossmapShortName(mapShortName);
			sendBossList.setBossmapState(bossmapState);
			sendBossList.setBossmapLevel(bossmapLevel);
			session.write(sendBossList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
