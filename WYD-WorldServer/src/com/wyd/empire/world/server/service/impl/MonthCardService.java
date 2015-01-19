package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.monthcard.CardOverdue;
import com.wyd.empire.protocol.data.monthcard.CashProductBuySuccess;
import com.wyd.empire.world.battle.MonthCardVo;
import com.wyd.empire.world.bean.MonthCard;
import com.wyd.empire.world.bean.PlayerBill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IMonthCardService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 月卡服务
 * 
 * @author Administrator
 *
 */
public class MonthCardService {
	private Logger log = Logger.getLogger(MonthCardService.class);
	/** 数据库星图列表 */
	private static List<MonthCard> monthCardList = new ArrayList<MonthCard>();
	private IMonthCardService monthCardService;
	/** 解析后星图数据 */
	private static Map<Integer, MonthCardVo> monthCardMap = new HashMap<Integer, MonthCardVo>();
	// /** 星图属性索引 */
	/** 月卡id【客户端根据此id进行资源加载，服务端不提供资源名称】 */
	private static int[] cardIds;
	/** 月卡购买所需金额 */
	private static int[] purchaseAmount;
	/** 每日返还钻石数 */
	private static int[] dailyRebate;

	// public IStarSoulService getStarSoulService() {
	// return starSoulService;
	// }

	public MonthCardService(IMonthCardService monthCardService) {
		this.monthCardService = monthCardService;
	}

	/**
	 * 初始化月卡数据
	 */
	public void initMonthCardData() {
		monthCardList = monthCardService.getAllMonthCard();
		if (monthCardList.size() > 0) {
			cardIds = new int[monthCardList.size()];
			purchaseAmount = new int[monthCardList.size()];
			dailyRebate = new int[monthCardList.size()];
			int index = 0;
			for (MonthCard monthCard : monthCardList) {
				MonthCardVo vo = new MonthCardVo();
				vo.setCardId(monthCard.getCardId());// 月卡id
				vo.setDailyRebate(monthCard.getDailyRebate());// 每日返还钻石数
				vo.setMonthCardName(monthCard.getMonthCardName());// 月卡名
				vo.setPurchaseAmount(monthCard.getPurchaseAmount());// 月卡购买所需金额
				cardIds[index] = monthCard.getCardId();
				purchaseAmount[index] = monthCard.getPurchaseAmount();
				dailyRebate[index] = monthCard.getDailyRebate();
				monthCardMap.put(monthCard.getCardId(), vo);
				index++;
			}
		}
	}

	/**
	 * 获取星图map
	 * 
	 * @return
	 */
	public Map<Integer, MonthCardVo> getMonthCardMap() {
		return monthCardMap;
	}

	/**
	 * 根据月卡id获取月卡信息
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public MonthCardVo getMonthCardById(int id) {
		return this.monthCardMap.get(id);
	}

	/** 月卡id【客户端根据此id进行资源加载，服务端不提供资源名称】 */
	public static int[] getCardIds() {
		return cardIds;
	}

	/** 月卡购买所需金额 */
	public static int[] getPurchaseAmount() {
		return purchaseAmount;
	}

	/** 每日返还钻石数 */
	public static int[] getDailyRebate() {
		return dailyRebate;
	}

	/**
	 * 发送月卡过期给玩家
	 * 
	 * @param receivedId
	 */
	public void sendCardOverdue(WorldPlayer receiver) {
		CardOverdue cardOverdue = new CardOverdue();
		cardOverdue.setCardId(receiver.getPlayerInfo().getMonthCardId());
		receiver.sendData(cardOverdue);
		// System.out.println("-----------sendCardOverdue----------");
	}

	/**
	 * 获得月卡
	 * 
	 * @param receiver
	 * @param cardId
	 * @param origin
	 *            获得途径 1充值获得 2.GM工具获得
	 * @param remark
	 *            异常信息
	 */
	public void addMonthCard(WorldPlayer receiver, int cardId, int origin, String orderNum, String remark) {
		// System.out.println("------------addMonthCard--------------");
		int remainingDays = -1; // 月卡剩余天数
		if (receiver.getPlayerInfo().getBuyMonthCardTime() != null) {
			Date dueTime = DateUtil.afterNowNDay(receiver.getPlayerInfo().getBuyMonthCardTime(), Common.MonthCardEffectiveDaysNum);
			remainingDays = DateUtil.compareDateOnDay(dueTime, new Date());
		}

		if (remainingDays < 0) {
			if (origin == 1) {
				// 保存代币变更记录
				PlayerBill playerBill = new PlayerBill();
				playerBill.setPlayerId(receiver.getId());
				playerBill.setCreateTime(new Date());
				playerBill.setAmount(0);
				playerBill.setOrigin(origin);
				playerBill.setRemark(remark);
				playerBill.setChargePrice(0f);
				playerBill.setOrderNum(orderNum);
				playerBill.setChannelId("1001");
				playerBill.setCardType("");
				if (origin == TradeService.ORIGIN_RECH) {
					if (ServiceManager.getManager().getPlayerBillService().playerIsFirstCharge(receiver.getPlayer())) {
						playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_N);
					} else {
						playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_Y);
					}
					receiver.updateButtonInfo(Common.BUTTON_ID_RECHARGE, false, 30);
				} else {
					playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_Y);
				}
				ServiceManager.getManager().getPlayerBillService().save(playerBill);
			}
			receiver.getPlayerInfo().setMonthCardId(cardId);
			receiver.getPlayerInfo().setRemainingRemindNum(-1);
			receiver.getPlayerInfo().setBuyMonthCardTime(new Date());
			receiver.updatePlayerInfo();
			CashProductBuySuccess buy = new CashProductBuySuccess();
			buy.setCardId(cardId);
			buy.setRemark(Common.MonthCardEffectiveDaysNum + "," + getMonthCardById(cardId).getDailyRebate());
			receiver.sendData(buy);
			GameLogService.addMonthCardData(receiver.getId(), cardId, origin, orderNum, remark);
		} else {
			System.out.println("验证成功添加月卡失败：-----------player:" + receiver.getId() + "---------剩余过期天数-remainingDays:" + remainingDays
					+ "----途径:" + origin + "------订单号:" + orderNum);
			log.info("验证成功添加月卡失败：-----------player:" + receiver.getId() + "---------剩余过期天数-remainingDays:" + remainingDays + "----途径:"
					+ origin + "------订单号:" + orderNum);
		}
	}

}
