package com.wyd.empire.world.server.handler.singlemap;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.singlemap.GetBigPointListOk;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ISingleMapService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetBigPointListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetBigPointListHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	MapsService mapsService = null;
	ISingleMapService singleMapService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			mapsService = manager.getMapsService();
			singleMapService = manager.getSingleMapService();
			List<Map> mapList = manager.getMapsService().getSingleMapList();
			int mapSize = mapList.size();
			int bigPointSize = mapSize / 5;// 五个小地图为一关
			bigPointSize = bigPointSize < 1 ? 1 : bigPointSize;
			int[] id = new int[bigPointSize];
			String[] name = new String[bigPointSize];
			int[] status = new int[bigPointSize];
			int[] passPoint = new int[bigPointSize];
			int[] totalPoint = new int[bigPointSize];
			int currBigPointId = singleMapService.getCurrBigPointId(player.getId());
			Map maxPassMap = singleMapService.getMaxPassMap(player.getId());
			int maxPass = maxPassMap == null ? 1 : maxPassMap.getBossmapSerial();
			for (int bigPointId = 1, i = 0; bigPointId <= bigPointSize; bigPointId++, i++) {
				id[i] = bigPointId;
				name[i] = mapList.get(bigPointId * 5 - 1).getNameShort();
				if (bigPointId == currBigPointId) {
					status[i] = 1;// 大关卡ID等于正在进行中的大关卡ID，标记状态为开启
					if (maxPass != 0 && maxPass % 5 == 0) {
						status[i] = 2;// 正好打完最后一关，标记为通关
					}
				} else if (bigPointId < currBigPointId) {
					status[i] = 2;// 大关卡ID小于正在进行中的大关卡ID，标记状态为通关
				}
				if (i > 0 && status[i - 1] == 2 && status[i] != 2) {
					status[i] = 1;// 上一关是通关，标记为开启
				}
				if (status[i] == 1) {
					passPoint[i] = maxPass % 5;
					totalPoint[i] = 5;
				} else if (status[i] == 2) {
					passPoint[i] = 5;
					totalPoint[i] = 5;
				}
			}
			GetBigPointListOk ok = new GetBigPointListOk(data.getSessionId(), data.getSerial());
			ok.setId(id);
			ok.setName(name);
			ok.setStatus(status);
			ok.setPassPoint(passPoint);
			ok.setTotalPoint(totalPoint);
			session.write(ok);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}

	}

}
