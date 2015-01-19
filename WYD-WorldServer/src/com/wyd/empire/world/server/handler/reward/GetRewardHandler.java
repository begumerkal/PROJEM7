package com.wyd.empire.world.server.handler.reward;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.reward.GetReward;
import com.wyd.empire.protocol.data.reward.GetRewardOk;
import com.wyd.empire.world.bean.LogRechargeReward;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.PlayerBill;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 领取充值奖励、抽奖
 * 
 * @author Administrator
 */
public class GetRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetReward getReward = (GetReward) data;
		try {
			GetRewardOk getRewardOk = new GetRewardOk(data.getSessionId(), data.getSerial());
			getRewardOk.setMsg("");
			getRewardOk.setStatus(0);
			switch (getReward.getRewardType()) {
				case Common.RECHARGE_REWARD :
					PlayerBill playerBill = ServiceManager.getManager().getPlayerBillService().getFirstBillByPlayer(player.getId());
					if (playerBill != null) {
						List<RechargeReward> rechargeRewardList = ServiceManager.getManager().getRechargeRewardService()
								.findAllRechargeReward();
						StringBuilder sb = new StringBuilder();
						for (RechargeReward rechargeReward : rechargeRewardList) {
							if (Common.GOLDID == rechargeReward.getShopItem().getId()) {
								ServiceManager.getManager().getPlayerService()
										.updatePlayerGold(player, rechargeReward.getCount(), "首冲领取", "首冲领取");
							} else if (Common.DIAMONDID == rechargeReward.getShopItem().getId()) {
								ServiceManager
										.getManager()
										.getPlayerService()
										.addTicket(player, playerBill.getAmount(), 0, TradeService.ORIGIN_RECHARGE_RWARD, 0, "", "首冲领取",
												"", "");
							} else {
								ServiceManager
										.getManager()
										.getRechargeRewardService()
										.givenItems(player, rechargeReward.getCount(), rechargeReward.getDays(),
												rechargeReward.getShopItem().getId(), rechargeReward.getStrongLevel(), null);
							}
							if (Common.DIAMONDID == rechargeReward.getShopItem().getId()) {
								sb.append(rechargeReward.getShopItem().getName()).append("*").append(playerBill.getAmount()).append(",");
							} else {
								sb.append(rechargeReward.getShopItem().getName()).append("*")
										.append(rechargeReward.getCount() == -1 ? rechargeReward.getDays() : rechargeReward.getCount())
										.append(",");
							}
						}
						LogRechargeReward logRechargeReward = new LogRechargeReward();
						logRechargeReward.setPlayerId(player.getId());
						logRechargeReward.setCreateTime(new Date());
						logRechargeReward.setRemark(sb.toString().substring(0, sb.length() - 1));
						logRechargeReward.setType((short) 1);
						ServiceManager.getManager().getRechargeRewardService().save(logRechargeReward);
						playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_Y);
						ServiceManager.getManager().getPlayerBillService().update(playerBill);
						getRewardOk.setStatus(1);
					} else {
						getRewardOk.setMsg(ErrorMessages.PLAYER_BILL_MESSAGE);
						// throw new Exception(Common.ERRORKEY +
						// ErrorMessages.PLAYER_BILL_MESSAGE);
					}
					getRewardOk.setItemId(-1);
					break;
				case Common.EVERYDAY_RECHARGE_REWARD :
					// PlayerBill everyDayplayerBill =
					// ServiceManager.getManager().getPlayerBillService().getEveryDayFirstBillByPlayer(player.getId(),getReward.getId());
					int num = player.getPlayerInfo().getEveryDayFirstChargeNum();
					if (num > 0) {
						List<RechargeReward> rechargeRewardList = ServiceManager.getManager().getRechargeRewardService()
								.findAllEveryDayRechargeReward();
						StringBuilder sb = new StringBuilder();
						for (RechargeReward rechargeReward : rechargeRewardList) {
							if (Common.GOLDID == rechargeReward.getShopItem().getId()) {
								ServiceManager.getManager().getPlayerService()
										.updatePlayerGold(player, rechargeReward.getCount(), "每日首冲领取", "每日首冲领取");
							} else if (Common.DIAMONDID == rechargeReward.getShopItem().getId()) {
								ServiceManager
										.getManager()
										.getPlayerService()
										.addTicket(player, rechargeReward.getCount(), 0, TradeService.DAY_ORIGIN_RECHARGE_RWARD, 0, "",
												"每日首充领取 ", "", "");
							} else {
								ServiceManager
										.getManager()
										.getRechargeRewardService()
										.givenItems(player, rechargeReward.getCount(), rechargeReward.getDays(),
												rechargeReward.getShopItem().getId(), rechargeReward.getStrongLevel(), null);
							}
							if (Common.DIAMONDID == rechargeReward.getShopItem().getId()) {
								sb.append(rechargeReward.getShopItem().getName()).append("*").append(rechargeReward.getCount()).append(",");
							} else {
								sb.append(rechargeReward.getShopItem().getName()).append("*")
										.append(rechargeReward.getCount() == -1 ? rechargeReward.getDays() : rechargeReward.getCount())
										.append(",");
							}
						}
						LogRechargeReward logRechargeReward = new LogRechargeReward();
						logRechargeReward.setPlayerId(player.getId());
						logRechargeReward.setCreateTime(new Date());
						logRechargeReward.setRemark(sb.toString().substring(0, sb.length() - 1));
						logRechargeReward.setType((short) 2);
						ServiceManager.getManager().getRechargeRewardService().save(logRechargeReward);
						player.getPlayerInfo().setEveryDayFirstChargeNum(--num);
						player.updatePlayerInfo();
						getRewardOk.setStatus(1);
						if (ServiceManager.getManager().getPlayerBillService().playerIsEveryDayFirstCharge(player.getPlayer())) {
							getRewardOk.setMsg(ErrorMessages.RECEIVE_SUCCESS_CHARGE);
						} else {
							getRewardOk.setMsg(ErrorMessages.RECEIVE_SUCCESS_NOT_CHARGE);
						}
					} else {
						getRewardOk.setMsg(ErrorMessages.NO_RECHARGE_RECORD);
						// throw new Exception(Common.ERRORKEY +
						// ErrorMessages.NO_RECHARGE_RECORD);
					}
					getRewardOk.setItemId(-2);
					break;

				case Common.LOTTERY_REWARD :
					long currentAmount = ServiceManager.getManager().getPlayerBillService().getPlayerBillCount(player.getPlayer().getId());
					int lotteryNum = (int) (Math.sqrt(currentAmount / 1000.0));
					int countNum = 0;
					for (int i = 1; i < lotteryNum + 1; i++) {
						countNum += i;
					}
					if (player.getPlayer().getLotteryCount() >= 0 && countNum - player.getPlayer().getLotteryCount() > 0) {
						player.getPlayer().setLotteryCount(player.getPlayer().getLotteryCount() + 1);
						ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
						int randomNum = ServiceUtils.getRandomNum(0, 10000);
						LotteryReward lotteryReward = ServiceManager.getManager().getRechargeRewardService().getLotteryReward(randomNum);
						if (Common.GOLDID == lotteryReward.getShopItem().getId()) {
							ServiceManager.getManager().getPlayerService()
									.updatePlayerGold(player, lotteryReward.getCount(), "抽奖奖励", "抽奖奖励");
						} else if (Common.DIAMONDID == lotteryReward.getShopItem().getId()) {
							ServiceManager.getManager().getPlayerService()
									.addTicket(player, lotteryReward.getCount(), 0, TradeService.ORIGIN_LOTTERY, 0, "", "抽奖奖励", "", "");
						} else {
							ServiceManager
									.getManager()
									.getRechargeRewardService()
									.givenItems(player, lotteryReward.getCount(), lotteryReward.getDays(),
											lotteryReward.getShopItem().getId(), lotteryReward.getStrongLevel(), null);
						}
						getRewardOk.setItemId(lotteryReward.getShopItem().getId());
						getRewardOk.setStatus(1);
					} else {
						getRewardOk.setMsg(ErrorMessages.LOTTERY_MESSAGE);
						// throw new Exception(Common.ERRORKEY +
						// ErrorMessages.LOTTERY_MESSAGE);
					}
					break;
				default :
					break;
			}
			session.write(getRewardOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
