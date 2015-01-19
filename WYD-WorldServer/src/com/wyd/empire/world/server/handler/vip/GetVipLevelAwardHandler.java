package com.wyd.empire.world.server.handler.vip;

import java.util.List;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.vip.GetVipLevelAward;
import com.wyd.empire.protocol.data.vip.GetVipLevelAwardOK;
import com.wyd.empire.world.bean.SpreeGift;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/*
 *	获取vip等级奖励
 */
public class GetVipLevelAwardHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetVipLevelAwardHandler.class);
	private int[] award = {554, 555, 556, 557, 558, 559, 560, 561, 562, 563}; // 礼包id

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (!player.isVip())
			return;

		GetVipLevelAward d = (GetVipLevelAward) data;
		int vipLv = d.getVipLv();
		int playervipLv = player.getPlayer().getVipLevel();
		vipLv = vipLv > playervipLv ? playervipLv : vipLv;
		int itemId = award[vipLv - 1];

		boolean isReceive = ServiceManager.getManager().getTaskService().getService().checkIsGetREward(player.getId(), vipLv, 2);
		if (isReceive)
			throw new Exception(Common.ERRORKEY + ErrorMessages.TASK_EVERYDAYGOT_MESSAGE);
		List<SpreeGift> list = ServiceManager.getManager().getSpereeGiftService().getSpreeGiftResult(itemId, 12);
		int[] itemIds = new int[list.size()];
		int[] num = new int[list.size()];
		int i = 0;
		for (SpreeGift sg : list) {
			int shopItemId = player.getPlayer().getSex() == 0 ? sg.getShopItem1().getId() : sg.getShopItem2().getId();
			if (shopItemId == Common.DIAMONDID) {
				ServiceManager.getManager().getPlayerService()
						.addTicket(player, sg.getCount(), 0, TradeService.VIP_RWARD, 0, "", "vip等级奖励", "", "");
			} else if (shopItemId == Common.GOLDID) {
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, sg.getCount(), "vip等级奖励", "-- " + " --");
			} else {
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.playerGetItem(player.getId(), shopItemId, -1, sg.getDays(), sg.getCount(), 5, null, 0, 0, 0);
			}
			itemIds[i] = shopItemId;
			num[i] = sg.getCount() > 0 ? sg.getCount() : sg.getDays();
			i++;
		}
		// ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(player.getId(),
		// itemId, -1, -1, 1, 9, "vip等级奖励"+vipLv+"级", 0, 0, 0);

		// 保存领取记录
		ServiceManager.getManager().getTaskService().getService().saveRecord(player.getId(), vipLv, 2);
		GetVipLevelAwardOK getVipLevelAwardOk = new GetVipLevelAwardOK(data.getSessionId(), data.getSerial());
		getVipLevelAwardOk.setItemId(itemIds);
		getVipLevelAwardOk.setNum(num);
		session.write(getVipLevelAwardOk);

	}
}
