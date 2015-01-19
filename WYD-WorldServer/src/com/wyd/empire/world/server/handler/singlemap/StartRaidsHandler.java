package com.wyd.empire.world.server.handler.singlemap;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.singlemap.StartRaids;
import com.wyd.empire.protocol.data.singlemap.StartRaidsOk;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerSingleMap;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.base.ISingleMapService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始扫荡
 * 
 * @author zengxc
 *
 */
public class StartRaidsHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartChallengeHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	MapsService mapsService = null;
	ISingleMapService singleMapService = null;
	IGuaiService guaiService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			StartRaids startRaids = (StartRaids) data;
			WorldPlayer player = session.getPlayer(data.getSessionId());
			mapsService = manager.getMapsService();
			singleMapService = manager.getSingleMapService();
			guaiService = manager.getGuaiService();
			int times = startRaids.getTimes();
			times = times < 1 ? 1 : times;
			int[] addExp = new int[times];
			com.wyd.empire.world.bean.Map singleMap = (com.wyd.empire.world.bean.Map) singleMapService.get(
					com.wyd.empire.world.bean.Map.class, startRaids.getPointId());
			PlayerSingleMap playerSingleMap = singleMapService.getPlayerSingleMap(player.getId(), singleMap.getId());
			int useVigor = singleMap.getVitalityExpend() * times;
			int vigor = player.getVigor();
			boolean unlimited = singleMap.getPassTimes() == -1;
			int canPassTimes = singleMap.getPassTimes() - times;
			List<String> rewardIcon = new ArrayList<String>();
			List<String> rewardName = new ArrayList<String>();
			// 是否已经通关了
			PlayerSingleMap prvSingleMap = singleMapService.getPlayerSingleMap(player.getId(), startRaids.getPointId());
			if (prvSingleMap == null || prvSingleMap.getPassTimes() < 0) {
				// 非法操作
				throw new ProtocolException(ErrorMessages.ILLEGAL_OPERATION_ERROR, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 是否足够活力
			if ((vigor - useVigor) < 0) {
				throw new ProtocolException(TipMessages.VIGOR_LOW, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			// 次数上限
			if (!unlimited) {
				if (playerSingleMap != null && playerSingleMap.getPassTimes() > canPassTimes) {
					throw new ProtocolException(TipMessages.SINGLEMAP_PASS_LIMIT, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}
			// 扣除活力
			player.useVigor(useVigor);
			// =====
			int addExp1 = 0, exp2 = 0, gold = 0;
			List<RewardInfo> mapRewards = ServiceUtils.getRewardInfo(singleMap.getReward(), player.getPlayer().getSex().intValue());
			for (RewardInfo reward : mapRewards) {
				switch (reward.getItemId()) {
					case Common.GOLDID :
						gold = reward.getCount();
						break;
					case Common.EXPID :
						addExp1 = reward.getCount();
						// buff加成,VIP加成
						int exp1 = ServiceManager.getManager().getBuffService().getExp(player, addExp1) - addExp1;
						// 添加公会技能加成
						exp2 = (int) ServiceManager.getManager().getBuffService().getAddition(player, addExp1, Buff.CEXP) - addExp1;
						addExp1 = addExp1 + exp1 + exp2;
						break;
				}
			}
			for (int i = 0; i < times; i++) {
				// 保存通关记录
				singleMapService.savePassTimes(player.getId(), singleMap.getId());
				RewardItemsVo reward = getReward(player, singleMap, manager);
				rewardIcon.add(reward.getItemIcon());
				rewardName.add(reward.getItemName() + "x" + reward.getCount());
				addExp[i] = addExp1;
				ServiceManager.getManager().getPlayerService().updatePlayerEXP(player, addExp[i]);
				if (gold > 0) {
					manager.getPlayerService().updatePlayerGold(player, gold, "地图掉落", "");
				}
				int getitemid = reward.getItemId();
				int itemcount = reward.getCount();
				int addDiamond = 0, addGold = 0, addBadge = 0;
				// 发放奖励
				if (getitemid == Common.GOLDID) {
					manager.getPlayerService().updatePlayerGold(player, itemcount, "单人副本掉落", "");
					addGold = itemcount;
				} else if (getitemid == Common.DIAMONDID) {
					manager.getPlayerService().addTicket(player, itemcount, 0, TradeService.ORIGIN_SINGLEMAP, 0, "", "单人副本掉落", "", "");
					// 记录获得钻石个数，等到GetPointsHandler协议时发送公告
					player.setSingleMapGetDiamond(itemcount);
					addDiamond = itemcount;
				} else {
					manager.getPlayerItemsFromShopService().playerGetItem(player.getId(), getitemid, itemcount, 29, null, 0, 0, 0);
					if (getitemid == Common.BADGEID) {
						addBadge = itemcount;
					}
				}
				GameLogService.raidsSingleMap(player.getId(), player.getLevel(), player.isVip(), singleMap.getId(), addDiamond, addGold,
						addBadge);
				ServiceManager.getManager().getTaskService().singMap(player, singleMap.getId());
			}
			StartRaidsOk ok = new StartRaidsOk(data.getSessionId(), data.getSerial());
			ok.setRewardIcon(ServiceUtils.ListToStrs(rewardIcon));
			ok.setRewardName(ServiceUtils.ListToStrs(rewardName));
			ok.setAddExp(addExp);
			session.write(ok);

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
			throw new ProtocolException(data, ex.getMessage());
		}
	}

	private RewardItemsVo getReward(WorldPlayer player, Map singleMap, ServiceManager manager) {
		RewardItemsVo reward = manager.getSingleMapService().getOneReward(player.getId(), player.getPlayer().getSex(), singleMap);
		int getitemid = reward.getItemId(), itemcount = reward.getCount();
		int addDiamond = 0, addGold = 0, addBadge = 0;
		// 发放奖励
		if (getitemid == Common.GOLDID) {
			try {
				manager.getPlayerService().updatePlayerGold(player, itemcount, "单人副本掉落", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			addGold = itemcount;
		} else if (getitemid == Common.DIAMONDID) {
			manager.getPlayerService().addTicket(player, itemcount, 0, TradeService.ORIGIN_SINGLEMAP, 0, "", "单人副本掉落", "", "");
			// 记录获得钻石个数，等到GetPointsHandler协议时发送公告
			player.setSingleMapGetDiamond(player.getSingleMapGetDiamond() + itemcount);
			addDiamond = itemcount;
		} else {
			manager.getPlayerItemsFromShopService().playerGetItem(player.getId(), getitemid, itemcount, 29, null, 0, 0, 0);
			if (getitemid == Common.BADGEID) {
				addBadge = itemcount;
			}
		}
		GameLogService.raidsSingleMap(player.getId(), player.getLevel(), player.isVip(), singleMap.getId(), addDiamond, addGold, addBadge);
		return reward;
	}

}
