package com.wyd.empire.world.server.handler.vip;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.vip.ReceiveGiftBagOk;
import com.wyd.empire.world.bean.SpreeGift;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 领取每日奖励
 * 
 * @author Administrator
 */
public class ReceiveGiftBagHandler implements IDataHandler {
	Logger log = Logger.getLogger(ReceiveGiftBagHandler.class);
	private int[] award = {544, 545, 546, 547, 548, 549, 550, 551, 552, 553}; // 礼包id

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int vipLv = player.getPlayer().getVipLevel();
		boolean mark = false;
		try {
			if (!player.isVip())
				return;
			int itemId = award[vipLv - 1];
			// 判断是否已领取
			boolean isget = ServiceManager.getManager().getTaskService().getService().checkIsGetREward(player.getId(), 1, 1);
			if (isget) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.TASK_EVERYDAYGOT_MESSAGE);
			}
			List<SpreeGift> list = ServiceManager.getManager().getSpereeGiftService().getSpreeGiftResult(itemId, 12);
			int[] itemIds = new int[list.size()];
			int[] num = new int[list.size()];
			int i = 0;
			for (SpreeGift sg : list) {
				int shopItemId = player.getPlayer().getSex() == 0 ? sg.getShopItem1().getId() : sg.getShopItem2().getId();
				if (shopItemId == Common.DIAMONDID) {
					ServiceManager.getManager().getPlayerService()
							.addTicket(player, sg.getCount(), 0, TradeService.VIP_RWARD, 0, "", "vip" + vipLv + "每日奖励", "", "");
				} else if (shopItemId == Common.GOLDID) {
					ServiceManager.getManager().getPlayerService()
							.updatePlayerGold(player, sg.getCount(), "vip" + vipLv + "每日奖励", "-- " + " --");
				} else {
					ServiceManager
							.getManager()
							.getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), shopItemId, -1, sg.getDays(), sg.getCount(), 9, "vip" + vipLv + "级每日奖励 ", 0, 0,
									0);
				}
				itemIds[i] = shopItemId;
				num[i] = sg.getCount() > 0 ? sg.getCount() : sg.getDays();
				i++;
			}

			// ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(player.getId(),
			// itemId, -1, -1, 1, 9, "vip每日奖励 "+vipLv+"级", 0, 0, 0);
			ReceiveGiftBagOk receiveGiftBagOk = new ReceiveGiftBagOk(data.getSessionId(), data.getSerial());
			receiveGiftBagOk.setItemid(itemIds);
			receiveGiftBagOk.setNum(num);
			session.write(receiveGiftBagOk);
			// 保存领取记录
			ServiceManager.getManager().getTaskService().getService().saveRecord(player.getId(), 1, 1);
			GameLogService.getVIPReward(player.getId(), player.getLevel(), player.getPlayer().getVipLevel(), itemId);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
	}
}
