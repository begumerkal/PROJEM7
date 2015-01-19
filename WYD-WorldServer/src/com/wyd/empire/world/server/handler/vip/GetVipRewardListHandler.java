package com.wyd.empire.world.server.handler.vip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.vip.GetVipRewardList;
import com.wyd.empire.protocol.data.vip.GetVipRewardListOk;
import com.wyd.empire.world.bean.Rewardrecord;
import com.wyd.empire.world.bean.SpreeGift;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * vip奖励列表
 * 
 * @author Administrator
 * 
 */
public class GetVipRewardListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetVipRewardListHandler.class);
	private int[] lvAward = {554, 555, 556, 557, 558, 559, 560, 561, 562, 563}; // 等级礼包
	private int[] everyDayAward = {544, 545, 546, 547, 548, 549, 550, 551, 552, 553}; // 每日礼包

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetVipRewardList request = (GetVipRewardList) data;
		List<Integer> vipLevel = new ArrayList<Integer>();
		List<Integer> itemId = new ArrayList<Integer>();
		List<Integer> count = new ArrayList<Integer>();
		List<Integer> days = new ArrayList<Integer>();

		try {
			int sex = player.getPlayer().getSex(); // 0男 1 女
			int type = request.getRewardType(); // 1等级奖励 2每日奖励
			int[] isReceiveLvPack = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			int[] award = type == 1 ? lvAward : everyDayAward;
			int shopItemIdReward = 0;
			int i = 1;

			for (int ShopItemId : award) {
				List<SpreeGift> list = ServiceManager.getManager().getSpereeGiftService().getSpreeGiftResult(ShopItemId, 12);
				for (SpreeGift spreeGift : list) {
					vipLevel.add(i);
					shopItemIdReward = sex == 0 ? spreeGift.getShopItem1().getId() : spreeGift.getShopItem2().getId();
					itemId.add(shopItemIdReward);
					count.add(spreeGift.getCount());
					days.add(spreeGift.getDays());
				}
				i++;
			}

			// 等级奖励领取情况
			if (type == 1) {
				List<Rewardrecord> rewardRecord = ServiceManager.getManager().getTaskService().getService()
						.getVipLvPackReceive(player.getId(), 2);
				if (rewardRecord != null) {
					for (Rewardrecord r : rewardRecord) {
						if (r.getVipMark() > 0)
							isReceiveLvPack[r.getVipMark() - 1] = 1;
					}
				}
			}

			GetVipRewardListOk ok = new GetVipRewardListOk(data.getSessionId(), data.getSerial());
			ok.setVipLevel(ArrayUtils.toPrimitive(vipLevel.toArray(new Integer[vipLevel.size()])));
			ok.setItemId(ArrayUtils.toPrimitive(itemId.toArray(new Integer[itemId.size()])));
			ok.setCount(ArrayUtils.toPrimitive(count.toArray(new Integer[count.size()])));
			ok.setDays(ArrayUtils.toPrimitive(days.toArray(new Integer[days.size()])));
			ok.setRewardType(type);
			ok.setIsReceiveLvPack(isReceiveLvPack);
			session.write(ok);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
