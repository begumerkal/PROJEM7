package com.wyd.empire.world.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.wyd.empire.protocol.data.purchase.SMSProductBuySuccess;
import com.wyd.empire.world.bean.BillingPoint;
import com.wyd.empire.world.bean.Order;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.impl.OrderDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

public class PointCallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	private static final String KEY = "ds45f64sfs5d15fgkjk789";
	private Logger rechargeLog = Logger.getLogger("rechargeLog");

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ret = "200";
		try {
			System.out.println("-------------PointCallBackServlet-------------");
			String playerId = req.getParameter("playerId");
			String orderNum = req.getParameter("orderNum");
			String cardNo = req.getParameter("cardNo");
			String realAmt = req.getParameter("realAmt");
			String channel = req.getParameter("channel");
			String cardType = req.getParameter("cardMedium");
			String message = req.getParameter("message");
			String verify = req.getParameter("verify");
			String productId = req.getParameter("productId");// 亚马逊充值钻石数量
			String keyValue = playerId + orderNum + KEY;
			keyValue = ServiceUtils.getMD5(keyValue);
			StringBuffer data = new StringBuffer();
			data.append("playerId:");
			data.append(playerId);
			data.append("-------orderNum:");
			data.append(orderNum);
			data.append("-------cardNo:");
			data.append(cardNo);
			data.append("-------realAmt:");
			data.append(realAmt);
			data.append("-------channel:");
			data.append(channel);
			data.append("-------cardType:");
			data.append(cardType);
			data.append("-------message:");
			data.append(message);
			if (keyValue.equalsIgnoreCase(verify) && null != playerId && playerId.length() > 0 && null != orderNum && orderNum.length() > 0
					&& null != realAmt && realAmt.length() > 0) {
				Order order = ServiceManager.getManager().getOrderService().getOrderByOrderNum(orderNum);
				if (null != order) {
					float price = Float.parseFloat(realAmt);
					if (price > 0) {
						if (order.getStatus() < OrderDao.ORDER_STATUS_GRANT) {
							WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(order.getPlayerId());
							BillingPoint billingPoint = ServiceManager.getManager().getOrderService()
									.getBillingPointById(order.getPointId());
							if (billingPoint.getItemId() == Common.DIAMONDID) {
								int count = 0;
								if (price == billingPoint.getPrice() && billingPoint.getCount() > 0) {
									count = billingPoint.getCount();
								} else {
									if (price <= 10) {
										count = (int) (price * 10);
									} else if (price <= 50) {
										count = (int) (price * 11);
									} else if (price <= 100) {
										count = (int) (price * 11.5);
									} else if (price <= 300) {
										count = (int) (price * 12);
									} else {
										count = (int) (price * 12.5);
									}
									// 通过productId来确定钻石数的渠道(如：亚马逊充值平台，越南平台)
									if (StringUtils.hasText(productId) && productId.startsWith("diamond")) {
										if (productId.indexOf(":") != -1) {
											count = Integer.valueOf(productId.split(":")[1]);
										}
										System.out.println("----productId:" + productId);
									}

								}
								System.out.println("PointCallBackServlet player:" + player.getId() + ",count:" + count);
								ServiceManager
										.getManager()
										.getPlayerService()
										.addTicket(player, count, 0, TradeService.ORIGIN_RECH, price, order.getOrderNum(), "", channel,
												cardType);
							} else {
								int cardId = 0;
								if (billingPoint.getItemId() == Common.GOLDID) {
									ServiceManager.getManager().getPlayerService()
											.updatePlayerGold(player, billingPoint.getCount(), "计费点购买", billingPoint.getId() + "");
								} else if (billingPoint.getItemId() == Common.NormalMonthCardNumber) {// 普通月卡
									cardId = Common.NormalMonthCardNumber;
								} else if (billingPoint.getItemId() == Common.SuperMonthCardNumber) {// 高级月卡
									cardId = Common.SuperMonthCardNumber;
								} else if (billingPoint.getItemId() == Common.ExtremeMonthCardNumber) {// 至尊月卡
									cardId = Common.ExtremeMonthCardNumber;
								} else {
									int day = -1;
									int count = -1;
									if (billingPoint.getType() == 0) {
										day = billingPoint.getCount();
									} else {
										count = billingPoint.getCount();
									}
									ServiceManager
											.getManager()
											.getPlayerItemsFromShopService()
											.playerGetItem(player.getId(), billingPoint.getItemId(), -1, day, count, 20,
													order.getOrderNum(), 0, 0, 0);
								}
								if (cardId > 0) {
									ServiceManager.getManager().getMonthCardService()
											.addMonthCard(player, cardId, TradeService.ORIGIN_RECH, orderNum, "安卓或越狱充值购买月卡");
								}
								SMSProductBuySuccess spbs = new SMSProductBuySuccess();
								spbs.setSerialNum(order.getSerialNum());
								spbs.setId(order.getPointId());
								spbs.setHasGrant(1);
								player.sendData(spbs);
							}
							order.setStatus(OrderDao.ORDER_STATUS_GRANT);
						}
						order.setStatus(OrderDao.ORDER_STATUS_CALL);
						order.setChannel(channel);
						order.setPrice(price);
						order.setCardType(cardType);
						ServiceManager.getManager().getOrderService().update(order);
						data.append("----验证成功");
					} else {
						data.append("----订单金额不大于0");
					}
				} else {
					data.append("----订单不存在");
				}
			} else {
				data.append("----信息验证失败");
			}
			rechargeLog.info(data.toString());
		} catch (Exception e) {
			ret = "500";
			e.printStackTrace();
		}

		resp.setContentType("text/html");
		resp.setStatus(200);
		System.out.println(resp);
		ServletOutputStream out = resp.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		os.write(ret);
		os.flush();
		os.close();
	}
}