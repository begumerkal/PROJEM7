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

import com.wyd.empire.protocol.data.purchase.BuyFailed;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

/**
 * EFUN充值回调
 * 
 * @author Administrator
 *
 */
public class RechargeServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	private static final String KEY = "U4Ns757u8QxRd63z9ciS";
	private Logger rechargeLog = Logger.getLogger("rechargeLog");

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ret = "{\"code \":\"0\", \"message \":\"充值成功\"}";
		StringBuffer data = new StringBuffer();
		try {
			String playerId = req.getParameter("playerid");
			String orderNum = req.getParameter("ordernum");
			String amt = req.getParameter("amount");
			String currencytype = req.getParameter("currencytype");
			String price = req.getParameter("price");
			String serverid = req.getParameter("serverid");
			String channelid = req.getParameter("channelid");
			String message = req.getParameter("message");
			String status = req.getParameter("status");
			String verify = req.getParameter("verify");
			String activityExtra = req.getParameter("activityExtra");
			activityExtra = StringUtils.hasText(activityExtra) ? activityExtra : "";
			String keyValue = playerId + orderNum + amt + currencytype + price + serverid + activityExtra + KEY;
			System.out.println("keyValue1:" + keyValue);
			keyValue = ServiceUtils.getMD5(keyValue);
			data.append("playerId:");
			data.append(playerId);
			data.append("-------orderNum:");
			data.append(orderNum);
			data.append("-------amount:");
			data.append(amt);
			data.append("-------currencytype:");
			data.append(currencytype);
			data.append("-------price:");
			data.append(price);
			data.append("-------serverid:");
			data.append(serverid);
			data.append("-------channelid:");
			data.append(channelid);
			data.append("-------message:");
			data.append(message);
			data.append("-------status:");
			data.append(status);
			data.append("-------activityExtra:");
			data.append(req.getParameter("activityExtra"));
			data.append("-------verify:");
			data.append(verify);
			System.out.println("keyValue2:" + keyValue);
			System.out.println("verify:" + verify);
			if (keyValue.equalsIgnoreCase(verify)) {
				int pid = Integer.parseInt(playerId);
				int amount = Integer.parseInt(amt);
				WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(pid);
				if (amount > 0 && status.equals("0")) {
					synchronized (player) {
						if (ServiceManager.getManager().getRechargeService().checkOrder(orderNum)) {
							Recharge recharge = new Recharge();
							recharge.setPrice(Float.parseFloat(price));
							recharge.setNumber(amount);
							// Recharge rh;
							// if (recharge.getPrice() <= 10) {
							// rh = (Recharge)
							// ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 1);
							// } else if (recharge.getPrice() <= 50) {
							// rh = (Recharge)
							// ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 2);
							// } else if (recharge.getPrice() <= 100) {
							// rh = (Recharge)
							// ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 3);
							// } else if (recharge.getPrice() <= 300) {
							// rh = (Recharge)
							// ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 4);
							// } else {
							// rh = (Recharge)
							// ServiceManager.getManager().getRechargeService().get(Recharge.class,
							// 5);
							// }
							// recharge.setFirstCharge(rh.getFirstCharge());
							// recharge.setFirstChargeRemark(rh.getFirstChargeRemark());
							// recharge.setMoreCharge(rh.getMoreCharge());
							// recharge.setMoreChargeRemark(rh.getMoreChargeRemark());
							// 充值奖励
							// ServiceManager.getManager().getRechargeService().chargeReward(player,
							// recharge);
							ServiceManager
									.getManager()
									.getPlayerService()
									.addTicket(player, amount, parseInt(activityExtra), TradeService.ORIGIN_RECH, recharge.getPrice(),
											orderNum, currencytype + "_" + channelid, channelid, "");
							// 活动 赠送的
							// int extra = parseInt(activityExtra);
							// if(extra>0){
							// ServiceManager.getManager().getPlayerService().addTicket(player,
							// extra, TradeService.ORIGIN_ACTIVITY_EXTRA,
							// recharge.getPrice(), orderNum, currencytype + "_"
							// + channelid, channelid, "");
							// }
							data.append("----验证成功");
						} else {
							data.append("----订单已存在");
							// ret =
							// "{\"code \":\"1\", \"message \":\"订单已存在\"}";
						}
					}
				} else {
					BuyFailed buyFailed = new BuyFailed();
					buyFailed.setOrderNum(orderNum);
					buyFailed.setCode(0);
					player.sendData(buyFailed);
				}
			} else {
				ret = "{\"code \":\"400\", \"message \":\"验证失败\"}";
				data.append("----信息验证失败");
			}
		} catch (Exception e) {
			ret = "{\"code \":\"500\", \"message \":\"系统错误\"}";
			data.append("----信息错误");
			e.printStackTrace();
		}
		rechargeLog.info(data.toString());
		resp.setContentType("text/html");
		resp.setStatus(200);
		ServletOutputStream out = resp.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		os.write(ret);
		os.flush();
		os.close();
	}

	private int parseInt(String v) {
		if ("".equals(v))
			return 0;
		try {
			return Integer.parseInt(v);
		} catch (Exception ex) {
			return 0;
		}
	}
}