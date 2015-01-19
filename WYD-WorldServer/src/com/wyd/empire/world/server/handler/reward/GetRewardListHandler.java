package com.wyd.empire.world.server.handler.reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.reward.GetRewardListOk;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 充值奖励、抽奖
 * 
 * @author Administrator
 * 
 */
public class GetRewardListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRewardListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			GetRewardListOk getRewardListOk = new GetRewardListOk(data.getSessionId(), data.getSerial());
			long count = ServiceManager.getManager().getPlayerBillService().getBillCount(player.getPlayer().getId());
			List<RechargeReward> rechargeRewardList = ServiceManager.getManager().getRechargeRewardService().findAllRechargeReward();
			List<RechargeReward> r = ServiceManager.getManager().getRechargeRewardService().findAllEveryDayRechargeReward();
			List<RechargeReward> reward = new ArrayList<RechargeReward>();
			boolean isRecharge = ServiceManager.getManager().getRechargeRewardService().getRechargeRewardLog(player.getId());

			if (count == 1 && (!isRecharge)) {
				reward.addAll(rechargeRewardList);
				getRewardListOk.setIsReward(true);
			} else {
				if (isRecharge) {
					reward.addAll(r);
				} else {
					reward.addAll(rechargeRewardList);
				}
				getRewardListOk.setIsReward(false);
			}

			List<LotteryReward> lotteryRewardList = ServiceManager.getManager().getRechargeRewardService().findAllLotteryReward();
			String[] rechargeReward = new String[reward.size()];
			String[] rechargeRewardRemark = new String[reward.size()];
			String[] rechargeRewardNum = new String[reward.size()];
			int[] rewardId = new int[reward.size()];
			int[] rechargeStrongLevel = new int[reward.size()];
			int[] rewardType = new int[reward.size()];

			String[] lotteryItems = new String[lotteryRewardList.size()];
			String[] lotteryItemsRemark = new String[lotteryRewardList.size()];
			String[] lotteryItemsNum = new String[lotteryRewardList.size()];
			int[] lotteryStrongLevel = new int[lotteryRewardList.size()];
			int[] lotteryItemsId = new int[lotteryRewardList.size()];
			for (int i = 0; i < reward.size(); i++) {
				rechargeReward[i] = reward.get(i).getShopItem().getIcon();
				rechargeRewardRemark[i] = reward.get(i).getShopItem().getName();
				rechargeStrongLevel[i] = reward.get(i).getStrongLevel();
				if (reward.get(i).getCount() == -1 && reward.get(i).getDays() == -1) {
					rechargeReward[i] = "-1";
					rechargeRewardRemark[i] = "-1";
					rechargeRewardNum[i] = "-1";
				} else if (reward.get(i).getDays() == -1) {
					rechargeRewardNum[i] = "X" + reward.get(i).getCount();
				} else if (reward.get(i).getCount() == -1) {
					rechargeRewardNum[i] = "X" + reward.get(i).getDays();
				}
				rewardId[i] = reward.get(i).getShopItem().getId();
				rewardType[i] = reward.get(i).getType();
			}
			for (int i = 0; i < lotteryRewardList.size(); i++) {
				lotteryItems[i] = lotteryRewardList.get(i).getShopItem().getIcon();
				lotteryItemsRemark[i] = lotteryRewardList.get(i).getShopItem().getName();
				lotteryItemsId[i] = lotteryRewardList.get(i).getShopItem().getId();
				lotteryStrongLevel[i] = lotteryRewardList.get(i).getStrongLevel();
				if (lotteryRewardList.get(i).getDays() == -1) {
					lotteryItemsNum[i] = "X" + lotteryRewardList.get(i).getCount();
				} else {
					lotteryItemsNum[i] = "X" + lotteryRewardList.get(i).getDays();
				}
			}
			long currentAmount = ServiceManager.getManager().getPlayerBillService().getPlayerBillCount(player.getPlayer().getId());
			int lotteryNum = (int) (Math.sqrt(currentAmount / 1000.0));
			int countNum = 0;
			for (int i = 1; i < lotteryNum + 1; i++) {
				countNum += i;
			}

			getRewardListOk.setRechargeReward(rechargeReward);
			getRewardListOk.setRechargeRewardRemark(rechargeRewardRemark);
			getRewardListOk.setRechargeRewardNum(rechargeRewardNum);
			getRewardListOk.setRechargeStrongLevel(rechargeStrongLevel);
			getRewardListOk.setRewardType(rewardType);
			getRewardListOk.setLotteryItems(lotteryItems);
			getRewardListOk.setLotteryItemsRemark(lotteryItemsRemark);
			getRewardListOk.setLotteryItemsNum(lotteryItemsNum);
			getRewardListOk.setLotteryItemsId(lotteryItemsId);
			getRewardListOk.setLotteryStrongLevel(lotteryStrongLevel);
			getRewardListOk.setCurrentAmount((int) currentAmount);
			getRewardListOk.setNextAmount(1000 * (lotteryNum + 1) * (lotteryNum + 1));
			getRewardListOk.setNextLottery(lotteryNum + 1);

			Map<Integer, Integer> map = ServiceManager.getManager().getVersionService().getRechargeIntervalMap();
			int[] rewardNum = new int[map.size()];
			int[] rechargeNum = new int[map.size()];
			int i = 0;
			for (int key : map.keySet()) {
				rewardNum[i] = key;
				rechargeNum[i] = map.get(key);
				i++;
			}
			getRewardListOk.setRewardNum(rewardNum);
			getRewardListOk.setRechargeNum(rechargeNum);
			Integer limitNum = ServiceManager.getManager().getVersionService().getSpecialMarkByKey("numberCaps");
			limitNum = (limitNum == null) ? 10 : limitNum;
			getRewardListOk.setMaxNum(limitNum);

			if (countNum - player.getPlayer().getLotteryCount() <= 0) {
				getRewardListOk.setCurrentLottery(0);
				getRewardListOk.setIsLottery(false);
			} else {
				getRewardListOk.setCurrentLottery(countNum - player.getPlayer().getLotteryCount());
				getRewardListOk.setIsLottery(true);
			}
			getRewardListOk.setEveryDayRewardNum(player.getPlayerInfo().getEveryDayFirstChargeNum());
			getRewardListOk.setRewardId(rewardId);
			session.write(getRewardListOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
