package com.wyd.empire.world.server.handler.bossmaproom;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.SendBossMapList;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class GetBossMapListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetBossMapListHandler.class);

	// 获得副本列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			List<Map> mapList = ServiceManager.getManager().getMapsService().getBossMapList();
			int mapCount = mapList.size();

			int[] mapId = new int[mapCount];
			String[] mapName = new String[mapCount];
			String[] mapShortName = new String[mapCount];
			String[] mapIcon = new String[mapCount];
			String[] mapAnimationIndexCode = new String[mapCount];
			String[] mapDese = new String[mapCount];
			boolean[] canPlay = new boolean[mapCount];
			int[] playLevel = new int[mapCount];
			int[] useVigor = new int[mapCount];
			int[] passTime = new int[mapCount];
			int[] totalTime = new int[mapCount];
			Map map;
			for (int i = 0; i < mapCount; i++) {
				map = mapList.get(i);
				mapId[i] = map.getId();
				mapName[i] = map.getName();
				mapShortName[i] = map.getNameShort();
				mapIcon[i] = map.getIcon();
				mapAnimationIndexCode[i] = map.getAnimationIndexCode();
				if (map.getBossmapSerial() > player.getBossmap_progress()) {
					canPlay[i] = false;
					int level = 0;
					if (map.getLevel() > 99) {// 转生
						mapDese[i] = TipMessages.BOOSTIP2;
						level = map.getLevel() - 99;
					} else {
						mapDese[i] = TipMessages.BOOSTIP1;
						level = map.getLevel();
					}
					mapDese[i] = mapDese[i].replace("{0}", level + "");
					mapDese[i] = mapDese[i].replace("{1}", mapName[i - 1]);
				} else {
					canPlay[i] = true;
					mapDese[i] = map.getDese();
					PlayerBossmap playerMap = ServiceManager.getManager().getPlayerBossmapService()
							.loadPlayerBossMap(player.getId(), map.getId());
					passTime[i] = playerMap.getPassTimes();
				}
				playLevel[i] = map.getLevel();
				useVigor[i] = map.getVitalityExpend();
				totalTime[i] = map.getPassTimes();
			}
			SendBossMapList sendBossMapList = new SendBossMapList(data.getSessionId(), data.getSerial());
			sendBossMapList.setMapCount(mapCount);
			sendBossMapList.setMapId(mapId);
			sendBossMapList.setMapName(mapName);
			sendBossMapList.setMapShortName(mapShortName);
			sendBossMapList.setMapIcon(mapIcon);
			sendBossMapList.setMapAnimationIndexCode(mapAnimationIndexCode);
			sendBossMapList.setMapDese(mapDese);
			sendBossMapList.setCanPlay(canPlay);
			sendBossMapList.setPlayLevel(playLevel);
			sendBossMapList.setUseVigor(useVigor);
			sendBossMapList.setPassTime(passTime);
			sendBossMapList.setTotalTime(totalTime);
			session.write(sendBossMapList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.MAP_NFM_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
