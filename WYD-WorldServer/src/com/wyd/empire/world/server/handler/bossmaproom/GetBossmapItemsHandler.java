package com.wyd.empire.world.server.handler.bossmaproom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.SendBossmapItems;
import com.wyd.empire.world.bean.BossmapReward;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
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
public class GetBossmapItemsHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetBossmapItemsHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SendBossmapItems sendBossmapItems = new SendBossmapItems(data.getSessionId(), data.getSerial());
		try {
			// long time = System.currentTimeMillis();
			List<Map> mapList = ServiceManager.getManager().getMapsService().getBossMapList();
			if (null == mapList || mapList.size() < 1) {
				throw new Exception();
			}
			int mapCount = mapList.size();
			int[] mapId = new int[mapCount]; // 地图id
			String[] mapShortName = new String[mapCount]; // 地图名称缩写
			String[] mapIcon = new String[mapCount]; // 地图图标
			boolean[] hasOpened = new boolean[mapCount]; // 地图是否已开启（true表示开启）
			int[] starLevel = new int[mapCount]; // 副本星级（未打过的副本星级为0）
			int[] mapMode = new int[mapCount];
			int[] mapLevel = new int[mapCount];
			Map map;
			PlayerBossmap pb;
			List<Integer> rewardList = new ArrayList<Integer>();
			for (int i = 0; i < mapCount; i++) {
				map = mapList.get(i);
				mapId[i] = map.getId();
				mapShortName[i] = map.getNameShort();
				mapIcon[i] = map.getIcon();
				mapLevel[i] = map.getLevel();
				pb = ServiceManager.getManager().getPlayerBossmapService().loadPlayerBossMap(player.getId(), map.getId());
				if (null != pb) {
					starLevel[i] = pb.getStar();
				} else {
					starLevel[i] = 0;
				}
				if (map.getBossmapSerial() > player.getBossmap_progress()) {
					hasOpened[i] = false;
				} else {
					hasOpened[i] = true;
				}
				mapMode[i] = 1;
				List<BossmapReward> brList = ServiceManager.getManager().getBossmapRewardService()
						.getBossmapRewardBymapId(map.getId(), player.getPlayer().getSex(), Common.BOSSMAP_REWARD_SHOW_COUNT);
				for (BossmapReward br : brList) {
					rewardList.add(br.getShopItemId());
				}
			}
			sendBossmapItems.setHasOpened(hasOpened);
			sendBossmapItems.setMapCount(mapCount);
			sendBossmapItems.setMapIcon(mapIcon);
			sendBossmapItems.setMapId(mapId);
			sendBossmapItems.setMapShortName(mapShortName);
			sendBossmapItems.setRewardList(ServiceUtils.ListToInts(rewardList));
			sendBossmapItems.setMapMode(mapMode);
			sendBossmapItems.setStarLevel(starLevel);
			sendBossmapItems.setMapLevel(mapLevel);
			session.write(sendBossmapItems);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
