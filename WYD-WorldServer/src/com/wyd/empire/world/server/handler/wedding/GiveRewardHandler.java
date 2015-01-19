package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.GiveReward;
import com.wyd.empire.protocol.data.wedding.GiveRewardOk;
import com.wyd.empire.protocol.data.wedding.PeopleGetRewardOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 派发红包
 * 
 * @author Administrator
 */
public class GiveRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(GiveRewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GiveReward giveReward = (GiveReward) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			String wedNum = giveReward.getWedNum();
			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}
			int rewardNum = giveReward.getRewardNum();
			Integer price = ServiceManager.getManager().getVersionService().getWedConfigByKey("rewardprice");
			if (null == price || price < 1) {// 校验红包价格
				price = 1;
			}
			if (player.getDiamond() < rewardNum * price) {// 玩家金币是否足够
				throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			Integer rewardhigh = ServiceManager.getManager().getVersionService().getWedConfigByKey("rewardhigh");
			Integer rewardlow = ServiceManager.getManager().getVersionService().getWedConfigByKey("rewardlow");
			if (null == rewardhigh)
				rewardhigh = 9999;
			if (null == rewardlow)
				rewardlow = 10;
			// 校验玩家输入红包的数量
			if (rewardNum < rewardlow || rewardNum > rewardhigh) {
				throw new ProtocolException(ErrorMessages.WEDDING_REWARDNUM_WRONG, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int useTicket = rewardNum * price;
			ServiceManager.getManager().getPlayerService()
					.useTicket(player, useTicket, TradeService.ORIGIN_BUYREWARD, new int[]{}, null, "购买结婚红包");
			Integer goldprice = ServiceManager.getManager().getVersionService().getWedConfigByKey("goldprice");
			if (null == goldprice || goldprice < 1) {// 校验红包对应金币单价
				goldprice = 100;
			}
			Integer poundage = ServiceManager.getManager().getVersionService().getWedConfigByKey("poundage");// 手续费
			Integer rewardavg = ServiceManager.getManager().getVersionService().getWedConfigByKey("rewardavg");// 平均比例
			if (null == poundage)
				poundage = 5;
			if (null == rewardavg)
				rewardavg = 66;
			// 计算剩余金币
			int totalGold = weddingRoom.getWedHall().getAvgGoldNum() * weddingRoom.getWedHall().getRewardNum()
					+ weddingRoom.getWedHall().getOtherGoldNum() + rewardNum * goldprice * (100 - poundage) / 100;
			weddingRoom.getWedHall().setRewardNum(weddingRoom.getWedHall().getRewardNum() + rewardNum);
			weddingRoom.getWedHall().setRewardGoldNum(totalGold);
			// 计算平均每个红包的最少金币数和机动金币数
			int avggold = ((weddingRoom.getWedHall().getRewardGoldNum() * rewardavg) / 100) / rewardNum;
			int rewardother = weddingRoom.getWedHall().getRewardGoldNum() - avggold * rewardNum;
			weddingRoom.getWedHall().setAvgGoldNum(avggold);
			weddingRoom.getWedHall().setOtherGoldNum(rewardother);
			ServiceManager.getManager().getMarryService().update(weddingRoom.getWedHall());
			GiveRewardOk giveRewardOk = new GiveRewardOk(data.getSessionId(), data.getSerial());
			session.write(giveRewardOk);
			// 刷新红包数
			PeopleGetRewardOk peopleGetRewardOk = new PeopleGetRewardOk();
			peopleGetRewardOk.setGoldNum(-1);
			peopleGetRewardOk.setRewardNum(weddingRoom.getWedHall().getRewardNum());
			// 给宾客发送
			for (WorldPlayer wp : weddingRoom.getPlayerList()) {
				if (wp.getId() != player.getId()) {
					wp.sendData(peopleGetRewardOk);
				}
			}
			// 给新郎发送
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService()
					.getWorldPlayerById(weddingRoom.getWedHall().getManId());
			worldPlayer.sendData(peopleGetRewardOk);
			// 给新娘发送
			worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
			worldPlayer.sendData(peopleGetRewardOk);

			if (player.getId() == weddingRoom.getWedHall().getManId()) {
				// 记录新郎进入时间
				weddingRoom.setBgGifts(useTicket);
			} else if (player.getId() == weddingRoom.getWedHall().getWomanId()) {
				// 记录新娘进入时间
				weddingRoom.setbGifts(useTicket);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
