package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherRewardOk;
import com.wyd.empire.protocol.data.bossmapbattle.RewardOk;
import com.wyd.empire.world.battle.PlayerRewardVo;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.DailyActivityService;
import com.wyd.empire.world.server.service.impl.SystemLogService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 砸蛋
 * 
 * @author Administrator
 */
public class RewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(RewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		PlayerRewardVo playerRewardVo = ServiceManager.getManager().getBossBattleTeamService().getPlayerReward().get(player.getId());
		if (null == playerRewardVo || null == playerRewardVo.getRewardItem()) {
			return;
		}
		RewardItemsVo riv = playerRewardVo.getRewardItem();
		if (!riv.isDiamond()) {
			if (riv.getItemId() == Common.GOLDID) {
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, riv.getCount(), SystemLogService.GSBATTLE, "");
			} else if (riv.getItemId() == Common.DIAMONDID) {// 获得点卷
				ServiceManager.getManager().getPlayerService()
						.addTicket(player, riv.getCount(), 0, TradeService.ORIGIN_BATT, 0, "", "副本对战", "", "");
			} else {
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.playerGetItem(player.getId(), riv.getItemId(), -1, riv.getDays(), riv.getCount(), 4, null, 0, 0, 0);
			}
			playerRewardVo.setIndex(playerRewardVo.getIndex() + 1);
			RewardOk rewardOk = new RewardOk();
			OtherRewardOk orOk = new OtherRewardOk();
			orOk.setPlayerId(player.getId());
			for (WorldPlayer worldPlayer : playerRewardVo.getPlayerList()) {
				if (worldPlayer.getId() == player.getId()) {
					worldPlayer.sendData(rewardOk);
				} else {
					worldPlayer.sendData(orOk);
				}
			}
			GameLogService.smashEgg(player.getId(), player.getLevel(), playerRewardVo.getMapId(), riv.getItemId(), 0);
		} else {
			int[] prices = getEggPrices(player);
			int price = prices[playerRewardVo.getIndex() - 2];
			if (player.getDiamond() < price) {
				throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			} else {
				ServiceManager.getManager().getPlayerService()
						.useTicket(player, price, TradeService.ORIGIN_EGGS, null, null, playerRewardVo.getMapId() + "");
				GameLogService.smashEgg(player.getId(), player.getLevel(), playerRewardVo.getMapId(), riv.getItemId(), price);
			}
		}
	}

	/**
	 * 副本砸蛋价格
	 * 
	 * @param player
	 * @return
	 */
	private int[] getEggPrices(WorldPlayer player) {
		int[] prices = ServiceManager.getManager().getVersionService().getVersion().priceArray();
		int[] finalPrices = new int[prices.length];
		VipRate vipRate = null;
		if (player.isVip()) {
			vipRate = ServiceManager.getManager().getPlayerItemsFromShopService().getVipRateById(player.getPlayer().getVipLevel());
		}
		for (int i = 0; i < prices.length; i++) {
			finalPrices[i] = getEggPrice(player, vipRate, prices[i]);
		}
		return finalPrices;
	}

	/**
	 * VIP打折，活动打折
	 * 
	 * @return
	 */
	private int getEggPrice(WorldPlayer player, VipRate vr, int price) {
		if (null != vr) {
			price = price * vr.getBossRate() / 100;
		}
		DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
		DailyRewardVo dailyRewardVo = dailyActivityService.getSmashEggReward();
		return dailyActivityService.getRewardedVal(price, dailyRewardVo.getSubTicket());
	}
}
