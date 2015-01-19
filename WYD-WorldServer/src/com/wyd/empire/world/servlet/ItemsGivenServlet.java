package com.wyd.empire.world.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

/**
 * 检查用户的角色名称和邀请码是否正确
 * 
 * @author Administrator
 *
 */
public class ItemsGivenServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;

	// /private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		JSONObject jsonObject = null;
		try {
			byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
			String dataString = CryptionUtil.Decrypt(data, CheckPlayerInfoServlet.EFUNKEY);
			jsonObject = JSONObject.fromObject(dataString);
			String inviteCode = jsonObject.getString("inviteCode");
			int playerId = Integer.parseInt(inviteCode.substring(inviteCode.indexOf("N") + 1), 16);
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			if (null != worldPlayer) {
				int itemId = jsonObject.getInt("itemId");
				int count = jsonObject.getInt("count");

				if (itemId == Common.GOLDID) {
					ServiceManager.getManager().getPlayerService().updatePlayerGold(worldPlayer, count, "外部抽奖获得", "外部抽奖获得");
				} else if (itemId == Common.DIAMONDID) {
					ServiceManager.getManager().getPlayerService()
							.addTicket(worldPlayer, count, 0, TradeService.ORIGIN_OUTLOTTERY, 0, "", "外部抽奖获得", "", "");
				} else {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(worldPlayer.getId(), itemId, count, 20, "外部抽奖获得", 0, 0, 0);
				}

				json.accumulate("code", "0");
				json.accumulate("message", "物品发放成功");
			} else {
				json.accumulate("code", "1");
				json.accumulate("message", "角色不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (null != jsonObject)
				System.out.println(jsonObject.toString());
			json.accumulate("code", "500");
			json.accumulate("message", "服务器内部错误");
		}
		resp.setContentType("text/html");
		resp.setStatus(200);
		ServletOutputStream out = resp.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		os.write(json.toString());
		os.flush();
		os.close();
	}
}
