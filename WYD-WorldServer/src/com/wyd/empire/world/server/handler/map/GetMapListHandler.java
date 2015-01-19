package com.wyd.empire.world.server.handler.map;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.map.GetMapListOk;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取地图列表
 * 
 * @author Administrator
 * 
 */
public class GetMapListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetMapListHandler.class);

	// 获取地图列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<Map> mapList = ServiceManager.getManager().getMapsService().getMapList();
			int mapCount = mapList.size();
			int[] mapId = new int[mapCount];
			String[] mapName = new String[mapCount];
			int[] mapStar = new int[mapCount];
			int[] mapChannel = new int[mapCount];
			String[] mapIcon = new String[mapCount];
			String[] mapAnimationIndexCode = new String[mapCount];
			String[] mapDese = new String[mapCount];
			Map map;
			for (int i = 0; i < mapCount; i++) {
				map = mapList.get(i);
				mapId[i] = map.getId();
				mapName[i] = map.getName();
				mapStar[i] = map.getStar();
				mapChannel[i] = map.getChannel();
				mapIcon[i] = map.getIcon();
				mapAnimationIndexCode[i] = map.getAnimationIndexCode();
				mapDese[i] = map.getDese();
			}
			GetMapListOk getMapListOk = new GetMapListOk(data.getSessionId(), data.getSerial());
			getMapListOk.setMapCount(mapCount);
			getMapListOk.setMapId(mapId);
			getMapListOk.setMapName(mapName);
			getMapListOk.setMapStar(mapStar);
			getMapListOk.setMapChannel(mapChannel);
			getMapListOk.setMapIcon(mapIcon);
			getMapListOk.setMapAnimationIndexCode(mapAnimationIndexCode);
			getMapListOk.setMapDese(mapDese);
			session.write(getMapListOk);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
