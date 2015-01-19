package com.wyd.empire.world.server.handler.worldbosshall;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.worldbosshall.GetRoomState;
import com.wyd.empire.protocol.data.worldbosshall.GetRoomStateOk;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.BossmapReward;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.WorldBossRoomService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.worldbosshall.WorldBossRoom;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 返回大厅信息
 * 
 * @author zengxc
 * 
 */
public class GetRoomStateHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRoomStateHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	IWorldBossService worldBossService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			worldBossService = manager.getWorldBossService();

			ConnectSession session = (ConnectSession) data.getHandlerSource();
			GetRoomState getRoomState = (GetRoomState) data;
			// 如果传过来的ID小于1则使用默认值
			getRoomState.setMapId(getRoomState.getMapId() < 1 ? Common.WORLDBOSS_DEFAULT_MAP : getRoomState.getMapId());
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int playerId = player.getId();
			int mapId = getRoomState.getMapId();

			List<Map> maps = manager.getMapsService().getWorldBossMapList();
			WorldBossRoomService roomService = WorldBossRoomService.getInstance();
			WorldBossRoom room = roomService.getRoomByMap(mapId);
			if (room == null) {
				throw new ProtocolException(ErrorMessages.WORLDBOSS_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			GetRoomStateOk ok = new GetRoomStateOk(data.getSessionId(), data.getSerial());
			ok.setRoomId(room.getId());
			ok.setMapId(getMapIds(maps));
			Combat boss = room.getBoss();
			if (boss != null) {
				ok.setBossBloodMax(boss.getMaxHP());
				ok.setBossBloodCurrent(boss.getHp());
			} else {
				ok.setBossBloodMax(0);
				ok.setBossBloodCurrent(0);
			}
			List<BossmapReward> rewardList = manager.getBossmapRewardService().getBossmapRewardBymapId(mapId, player.getPlayer().getSex(),
					Common.BOSSMAP_REWARD_SHOW_COUNT);
			ok.setReward(getRewardIds(rewardList));
			List<Combat> bossPlayers = room.hurtTop(10);
			ok.setRankPlayerName(getPlayerNames(bossPlayers));
			ok.setRankHurt(getPlayerHurts(bossPlayers));
			Combat combat = room.findPlayer(playerId);
			ok.setHurt(combat == null ? 0 : combat.getTotalHurt());
			// 冷却时间
			int cdTime = worldBossService.getCDTime(playerId, getRoomState.getMapId());
			ok.setCdTime(cdTime);
			ok.setMapIcon(getMapIcons(maps));
			ok.setExplain(manager.getMapsService().getWorldBossMapById(mapId).getDese());
			// 加速钻石
			ok.setAccelerateCost(Common.WORLDBOSS_CLEAR_CDTIME);
			session.write(ok);

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.WORLDBOSS_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private int[] getMapIds(List<Map> maps) {
		int[] ids = new int[maps.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = maps.get(i).getId();
		}
		return ids;
	}

	private String[] getMapIcons(List<Map> maps) {
		String[] icons = new String[maps.size()];
		for (int i = 0; i < icons.length; i++) {
			icons[i] = maps.get(i).getMapIcon();
		}
		return icons;
	}

	private int[] getPlayerHurts(List<Combat> bossPlayers) {
		int[] hurts = new int[bossPlayers.size()];
		for (int i = 0; i < hurts.length; i++) {
			hurts[i] = bossPlayers.get(i).getTotalHurt();
		}
		return hurts;
	}

	private String[] getPlayerNames(List<Combat> bossPlayers) {
		String[] names = new String[bossPlayers.size()];
		for (int i = 0; i < names.length; i++) {
			Combat seat = bossPlayers.get(i);
			if (seat.getPlayer() != null)
				names[i] = seat.getName();
			else {
				names[i] = "";
			}
		}
		return names;
	}

	private int[] getRewardIds(List<BossmapReward> rewardList) {
		int[] ids = new int[rewardList.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = rewardList.get(i).getShopItemId();
		}
		return ids;
	}
}
