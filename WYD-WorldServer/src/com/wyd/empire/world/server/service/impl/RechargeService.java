package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.purchase.AndroidSendProductCheckInfo;
import com.wyd.empire.protocol.data.purchase.BuyFailed;
import com.wyd.empire.protocol.data.purchase.IOSSendProductCheckInfo;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.purchase.Order;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;

public class RechargeService implements Runnable {
	// 订单类型
	public static final int IOSORDER = 0;
	public static final int ANDROIDORDER = 1;
	private Logger errorLog = Logger.getLogger(RechargeService.class);
	private Logger log = Logger.getLogger("rechargeLog");
	private static final String sandboxUrl = "https://sandbox.itunes.apple.com/verifyReceipt"; // 测试环境
	private static final String buyurl = "https://buy.itunes.apple.com/verifyReceipt"; // 正式环境
	private static final String androidbuyurl = "https://pay2.zhwyd.com/wydpay/get_order"; // android支付接口
	// private static final String androidbuyurl =
	// "http://192.168.1.12:8080/wydpay/get_order"; // android支付接口
	private List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();

	public void start() {
		Thread t = new Thread(this);
		t.setName("RechargeService-Thread");
		t.start();
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while (true) {
			try {
				synchronized (RechargeService.this) {
					if (0 == orderInfoList.size()) {
						this.wait();
					}
				}
				OrderInfo orderInfo = orderInfoList.remove(0);
				WorldPlayer player = orderInfo.getPlayer();
				int fillCode = 0;
				String orderNum;
				// String playerId;
				String key;
				int channel;
				if (IOSORDER == orderInfo.getOrderType()) {
					IOSSendProductCheckInfo spci = (IOSSendProductCheckInfo) orderInfo.getSpci();
					orderNum = spci.getOrderNum();
					// playerId = spci.getPlayerId() + "";
					key = spci.getKey();
					channel = spci.getChannelId();
				} else {
					AndroidSendProductCheckInfo spci = (AndroidSendProductCheckInfo) orderInfo.getSpci();
					orderNum = spci.getOrderNum();
					// playerId = spci.getPlayerId() + "";
					key = spci.getKey();
					channel = spci.getChannelId();
				}
				String bid = ServiceManager.getManager().getRechargeService().getDidByChannel(channel);
				try {
					if (key.length() < 1000) {// key小于100的为假订单
						if ("-1".equals(key)) {
							fillCode = 3;
						}
						throw new Exception(Common.ERRORKEY + ErrorMessages.PURCHASE_OCF_MESSAGE);
					}
					// if (null == PurchaseUtils.getPurchasesByNo(orderNum)) {
					// PurchaseUtils.addPurchases(orderNum, playerId, key,
					// orderInfo.getOrderType() + "");// 保存到订单备份
					// }
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("receipt-data", key);
					Order order;
					if (IOSORDER == orderInfo.getOrderType()) {
						String receipt = HttpClientUtil.PostData(buyurl, JSONObject.fromObject(dataMap).toString());
						JSONObject jsonObject = JSONObject.fromObject(receipt);
						order = (Order) jsonObject.toBean(jsonObject, Order.class);
						if (21007 == order.getStatus()) {
							receipt = HttpClientUtil.PostData(sandboxUrl, JSONObject.fromObject(dataMap).toString());
							jsonObject = JSONObject.fromObject(receipt);
							order = (Order) jsonObject.toBean(jsonObject, Order.class);
						}
					} else {
						String receipt = HttpClientUtil.PostData(androidbuyurl, JSONObject.fromObject(dataMap).toString());
						JSONObject jsonObject = JSONObject.fromObject(receipt);
						order = (Order) jsonObject.toBean(jsonObject, Order.class);
					}
					dataMap = null;
					if (0 != order.getStatus() || !order.getReceipt().getBid().equals(bid)
							|| !ServiceManager.getManager().getRechargeService().checkOrder(order.getReceipt().getTransaction_id())) {
						if (1 == order.getStatus()) {// android 支付延时情况处理
							fillCode = 2;
							if (orderInfo.getTimes() < 10) {
								ServiceManager.getManager().getDelayOrderService().addOrder(orderInfo);
							}
							orderInfo.setTimes(orderInfo.getTimes() + 1);
							if (orderInfo.getTimes() == 1) {
								log.info("player:" + player.getId() + "-----------order:" + orderNum + "-----------status:"
										+ order.getStatus() + "-----------message:" + order.getMsssage());
								throw new Exception(Common.ERRORKEY + ErrorMessages.PURCHASE_OCF_MESSAGE);
							} else {
								continue;
							}
						}
						// PurchaseUtils.removePurchases(orderNum);
						log.info("player:" + player.getId() + "-----------order:" + orderNum + "-----------status:" + order.getStatus()
								+ "-----------message:" + order.getMsssage());
						throw new Exception(Common.ERRORKEY + ErrorMessages.PURCHASE_OCF_MESSAGE);
					}
					Recharge recharge;
					if (IOSORDER == orderInfo.getOrderType()) {
						recharge = ServiceManager.getManager().getRechargeService().getRechargeByCID(order.getReceipt().getProduct_id());
						if (null == recharge) {
							fillCode = 1;
							throw new Exception(Common.ERRORKEY + ErrorMessages.PURCHASE_CNF_MESSAGE);
						}
					} else {
						recharge = new Recharge();
						recharge.setPrice(Float.parseFloat(order.getReceipt().getProduct_id()));
						// Recharge rh;
						int number = 0;
						if (recharge.getPrice() <= 10) {
							number = (int) (recharge.getPrice() * 10);
							// rh =
							// (Recharge)ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 1);
						} else if (recharge.getPrice() <= 50) {
							number = (int) (recharge.getPrice() * 11);
							// rh =
							// (Recharge)ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 2);
						} else if (recharge.getPrice() <= 100) {
							number = (int) (recharge.getPrice() * 11.5);
							// rh =
							// (Recharge)ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 3);
						} else if (recharge.getPrice() <= 300) {
							number = (int) (recharge.getPrice() * 12);
							// rh =
							// (Recharge)ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 4);
						} else {
							number = (int) (recharge.getPrice() * 12.5);
							// rh =
							// (Recharge)ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 5);
						}
						recharge.setNumber(number);
						// recharge.setFirstCharge(rh.getFirstCharge());
						// recharge.setMoreCharge(rh.getMoreCharge());
					}
					// 充值奖励
					// ServiceManager.getManager().getRechargeService().chargeReward(player,
					// recharge);
					// 普通月卡
					if (recharge.getNumber() < 0) {
						int cardId = 0;
						// 普通月卡
						if (recharge.getNumber() == (Common.NormalMonthCardNumber * -1)) {
							cardId = Common.NormalMonthCardNumber;
						} else if (recharge.getNumber() == (Common.SuperMonthCardNumber * -1)) {// 高级月卡
							cardId = Common.SuperMonthCardNumber;
						} else if (recharge.getNumber() == (Common.ExtremeMonthCardNumber * -1)) {// 至尊月卡
							cardId = Common.ExtremeMonthCardNumber;
						}
						ServiceManager.getManager().getMonthCardService()
								.addMonthCard(player, cardId, TradeService.ORIGIN_RECH, orderNum, "苹果充值购买月卡");
					} else {
						ServiceManager
								.getManager()
								.getPlayerService()
								.addTicket(player, recharge.getNumber(), recharge.getGiftNumber(), TradeService.ORIGIN_RECH,
										recharge.getPrice(), order.getReceipt().getTransaction_id(), "苹果充值", "1001", "");
					}

					log.info("验证成功：-----------player:" + player.getId() + "---------order:" + orderNum + "-----------key:" + key);
				} catch (Exception ex) {
					log.info("验证失败：-----------player:" + player.getId() + "---------order:" + orderNum + "-----------key:" + key);
					errorLog.error(ex, ex);
					BuyFailed buyFailed = new BuyFailed();
					buyFailed.setOrderNum(orderNum);
					buyFailed.setCode(fillCode);
					player.sendData(buyFailed);
				}
			} catch (Exception e) {
				errorLog.error(e, e);
			}
		}
	}

	public void addspci(WorldPlayer player, AbstractData spci, int orderType) {
		synchronized (RechargeService.this) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setPlayer(player);
			orderInfo.setSpci(spci);
			orderInfo.setOrderType(orderType);
			orderInfoList.add(orderInfo);
			// this.notify();
			this.notifyAll();
		}
	}

	public void addOrder(OrderInfo orderInfo) {
		synchronized (RechargeService.this) {
			orderInfoList.add(orderInfo);
			// this.notify();
			this.notifyAll();
		}
	}

	public class OrderInfo {
		private WorldPlayer player;
		private AbstractData spci;
		private int orderType;
		private int times;

		public WorldPlayer getPlayer() {
			return player;
		}

		public void setPlayer(WorldPlayer player) {
			this.player = player;
		}

		public AbstractData getSpci() {
			return spci;
		}

		public void setSpci(AbstractData spci) {
			this.spci = spci;
		}

		/**
		 * 订单支付类型 0=IOS，1=android
		 * 
		 * @return
		 */
		public int getOrderType() {
			return orderType;
		}

		public void setOrderType(int orderType) {
			this.orderType = orderType;
		}

		public int getTimes() {
			return times;
		}

		public void setTimes(int times) {
			this.times = times;
		}
	}
}
