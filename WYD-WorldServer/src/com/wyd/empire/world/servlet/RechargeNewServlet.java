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
public class RechargeNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	private static final String KEY = "U4Ns757u8QxRd63z9ciS";
	private Logger rechargeLog = Logger.getLogger("rechargeLog");

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ret = "{\"result\":\"0000\"}";
		StringBuffer data = new StringBuffer();
		try {
			String pOrderId = req.getParameter("pOrderId"); // 合作商的訂單id(必填，固定20位)（EFUN訂單號,確保唯一，請用這個字段做唯一限制）
			String Uid = req.getParameter("userId"); // 帳號的id(必填)（EFUN帳號ID）
			String playerId = req.getParameter("creditId"); // 儲值id(必填，調用儲值時SDK傳送的值，根據這個id可以發送遊戲幣到對應的角色，最大長度超過50)
			String currency = req.getParameter("currency"); // 币种（美元）
			String price = req.getParameter("amount"); // 币种对应的总金额(美金)
			String rCurrency = req.getParameter("RCurrency"); // 对账金额对应的币种(可选)
			String rAmount = req.getParameter("RAmount"); // 对账总金额，此字段做为对账依据（可选）
			String serverCode = req.getParameter("serverCode"); // 服务器代碼(必填)
			String amt = req.getParameter("stone"); // 遊戲幣数量(必填)
			String md5Str = req.getParameter("md5Str"); // new
														// MD5(serverCode+creditId+userId
														// +amount +stone
														// +time+key)
			String time = req.getParameter("time"); // 加密串中的time=System.currentTimeMillis()
													// + “”;
			String remark = req.getParameter("remark"); // 自定义数据串（选填）
			String activityExtra = req.getParameter("activityExtra"); // 活动赠送的游戏币（若无活动则默认传0）
			String channelId = req.getParameter("channelId");
			String orderStateMonth = req.getParameter("orderStateMonth");

			String gameCode = req.getParameter("gameCode");// 游戏代码（可选字段）
			String point = req.getParameter("point");// 平台点数（平台点数系统上线后会传，google/IOS该字段为0）
			String freePoint = req.getParameter("freePoint");// 是否免费点（1：付费点，0：免费点
																// ）

			gameCode = gameCode == null ? "" : gameCode;
			point = StringUtils.hasText(point) ? point : "0";// 默认0
			freePoint = StringUtils.hasText(freePoint) ? freePoint : "1";// 默认1
			activityExtra = StringUtils.hasText(activityExtra) ? activityExtra : "0";
			String keyValue = serverCode + playerId + Uid + price + amt + time + KEY;
			keyValue = ServiceUtils.getMD5(keyValue);
			data.append("playerId:");
			data.append(playerId);
			data.append("-------orderNum:");
			data.append(pOrderId);
			data.append("-------amount:");
			data.append(amt);
			data.append("-------currencytype:");
			data.append(currency);
			data.append("-------price:");
			data.append(price);
			data.append("-------RCurrency:");
			data.append(rCurrency);
			data.append("-------RAmount:");
			data.append(rAmount);
			data.append("-------serverCode:");
			data.append(serverCode);
			data.append("-------channelid:");
			data.append(channelId);
			data.append("-------remark:");
			data.append(remark);
			data.append("-------activityExtra:");
			data.append(req.getParameter("activityExtra"));
			data.append("-------md5Str:");
			data.append(md5Str);
			data.append("-------orderStateMonth:");
			data.append(orderStateMonth);
			data.append("-------gameCode:");
			data.append(gameCode);
			data.append("-------point:");
			data.append(point);
			data.append("-------freePoint:");
			data.append(freePoint);
			if (keyValue.equalsIgnoreCase(md5Str)) {
				int pid = Integer.parseInt(playerId);
				int amount = Integer.parseInt(amt);
				WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(pid);
				if (amount > 0) {
					synchronized (player) {
						if (ServiceManager.getManager().getRechargeService().checkOrder(pOrderId)) {
							float pricef = Float.parseFloat(price);
							int origin = TradeService.ORIGIN_RECH;
							origin = "0".equals(freePoint) ? TradeService.ORIGIN_FREEPOINT : origin;
							ServiceManager
									.getManager()
									.getPlayerService()
									.addTicket(player, amount, parseInt(activityExtra), TradeService.ORIGIN_RECH, pricef, pOrderId,
											currency + "_" + channelId + "_" + orderStateMonth + "_" + point + "_" + gameCode, channelId,
											"");
							// 用免费点数的订单不参与首储双倍、活动积分等任何活动
							// if(!"0".equals(freePoint)){
							// //活动 赠送的
							// int extra = parseInt(activityExtra);
							// if(extra>0){
							// ServiceManager.getManager().getPlayerService().addTicket(player,
							// extra, TradeService.ORIGIN_ACTIVITY_EXTRA,
							// pricef, pOrderId, currency + "_" + channelId,
							// channelId, "");
							// }
							// }
							data.append("----验证成功");
						} else {
							data.append("----订单已存在");
						}
					}
				} else {
					BuyFailed buyFailed = new BuyFailed();
					buyFailed.setOrderNum(pOrderId);
					buyFailed.setCode(0);
					player.sendData(buyFailed);
				}
			} else {
				ret = "{\"result\":\"0011\"}";
				data.append("----信息验证失败");
			}
		} catch (Exception e) {
			ret = "{\"result\":\"0100\"}";
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