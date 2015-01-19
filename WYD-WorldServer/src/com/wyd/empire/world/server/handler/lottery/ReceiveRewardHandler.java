package com.wyd.empire.world.server.handler.lottery;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.lottery.ReceiveRewardOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WishItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 爱心许愿
 * 
 * @author Administrator
 */
public class ReceiveRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(ReceiveRewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.LOVEID);
			if (null == pifs || !(pifs.getPLastNum() > 0)) {
				throw new ProtocolException(ErrorMessages.LOTTERY_ENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			} else {
				WishItem wishItem = ServiceManager.getManager().getLotteryService().getWishResult();
				pifs.setPLastNum(pifs.getPLastNum() - 1);
				if (pifs.getPLastNum() < 1) {// 没有爱心取消高亮
					player.updateButtonInfo(Common.BUTTON_ID_LOVE, false, 0);
				}
				ServiceManager.getManager().getPlayerItemsFromShopService().save(pifs);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.saveGetItemRecord(player.getPlayer().getId(), Common.LOVEID, -1, -1, 14, 1, null);
				RewardInfo rewardInfo = wishItem.getReward(player.getPlayer().getSex());
				// 获得途径标识为14
				if (rewardInfo.getItemId() == Common.GOLDID) {// 金币
					ServiceManager.getManager().getPlayerService().updatePlayerGold(player, rewardInfo.getCount(), "爱心许愿", "-- " + " --");
				} else if (rewardInfo.getItemId() == Common.DIAMONDID) {// 钻石
					ServiceManager.getManager().getPlayerService()
							.addTicket(player, rewardInfo.getCount(), 0, TradeService.ORIGIN_LOVE, 0, "", "爱心许愿", "", "");
				} else {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), rewardInfo.getItemId(), rewardInfo.getCount(), 14, null, 0, 0, 0);
				}
				ReceiveRewardOk receiveRewardOk = new ReceiveRewardOk(data.getSessionId(), data.getSerial());
				receiveRewardOk.setGridId(wishItem.getId());
				receiveRewardOk.setItemId(rewardInfo.getItemId());
				receiveRewardOk.setItemNum(rewardInfo.getCount());
				session.write(receiveRewardOk);
				ServiceManager.getManager().getTaskService().wishing(player);// 检查任务
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
