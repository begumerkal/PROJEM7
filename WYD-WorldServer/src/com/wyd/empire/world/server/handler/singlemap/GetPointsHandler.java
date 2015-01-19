package com.wyd.empire.world.server.handler.singlemap;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.db.page.PageList;
import com.wyd.empire.protocol.data.singlemap.GetPoints;
import com.wyd.empire.protocol.data.singlemap.GetPointsOk;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerSingleMap;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ISingleMapService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetPointsHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPointsHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	MapsService mapsService = null;
	ISingleMapService singleMapService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			GetPoints getPoints = (GetPoints) data;
			WorldPlayer player = session.getPlayer(data.getSessionId());
			mapsService = manager.getMapsService();
			singleMapService = manager.getSingleMapService();
			int id = getPoints.getId();
			// 如果没有传大关卡ID过来就取当前最新的ID
			id = id < 1 ? singleMapService.getCurrBigPointId(player.getId()) : id;
			PageList singleMapPage = mapsService.getSingleMapList(id);
			List<Object> singleMapList = singleMapPage.getList();
			int size = singleMapList.size();
			int[] pointX = new int[size];
			int[] pointY = new int[size];
			int[] useVigor = new int[size];
			int[] pointId = new int[size];
			int[] mode = new int[size];
			String[] pointTitle = new String[size];
			String[] pointDesc = new String[size];
			int[] passTime = new int[size];
			int[] totalTime = new int[size];
			int[] rewardCount = new int[size];
			List<Integer> rewards = new ArrayList<Integer>();
			for (int i = 0; i < size; i++) {
				Map singleMap = (Map) singleMapList.get(i);
				int[] coor = singleMap.passCoor();
				pointX[i] = coor[0];
				pointY[i] = coor[1];
				pointId[i] = singleMap.getId();
				pointTitle[i] = singleMap.getName();
				pointDesc[i] = singleMap.getDese();
				useVigor[i] = singleMap.getVitalityExpend();
				PlayerSingleMap playerSingleMap = singleMapService.getPlayerSingleMap(player.getId(), singleMap.getId());
				if (playerSingleMap != null && playerSingleMap.getPassTimes() > -1) {
					mode[i] = 1;
					passTime[i] = playerSingleMap.getPassTimes();

				} else {
					mode[i] = 0;
				}
				totalTime[i] = singleMap.getPassTimes();
				List<Integer> rewardList = singleMapService.getReward(singleMap.getDropId1());
				rewardCount[i] = rewardList.size();
				rewards.addAll(rewardList);
			}
			// 大于十钻才发公告
			if (player.getSingleMapGetDiamond() > 10) {
				manager.getChatService().singleMap_GetDiamond(player.getName(), player.getSingleMapGetDiamond());
				player.setSingleMapGetDiamond(0);
			}
			GetPointsOk ok = new GetPointsOk(data.getSessionId(), data.getSerial());
			ok.setId(singleMapPage.getPageIndex());
			ok.setDesc(TipMessages.SINGLEMAP_DESC);
			ok.setPointDesc(pointDesc);
			ok.setPointId(pointId);
			ok.setPointTitle(pointTitle);
			ok.setPointX(pointX);
			ok.setPointY(pointY);

			ok.setUseVigor(useVigor);
			ok.setReward(ServiceUtils.ListToInts(rewards));
			ok.setMode(mode);
			ok.setPassTime(passTime);
			ok.setTotalTime(totalTime);
			ok.setRewardCount(rewardCount);
			session.write(ok);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}

	}
}
