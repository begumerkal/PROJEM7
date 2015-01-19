package com.wyd.empire.world.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

/**
 * 批量发送奖励
 * 
 * @author Administrator
 *
 */
public class BatchRunSendRewardServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;

	// /private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		JSONObject jsonObject = null;
		String warn = "";
		try {
			byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
			String dataString = CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey"));
			jsonObject = JSONObject.fromObject(dataString);
			String[] playerIds = jsonObject.getString("playerIds").split(",");
			String[] itemIds = jsonObject.getString("itemIds").split(",");
			String[] counts = jsonObject.getString("counts").split(",");
			String[] dayOrcount = jsonObject.getString("dayOrcount").split(",");
			String remark = jsonObject.getString("remark");
			String itemType = jsonObject.getString("itemType");
			String[] levels = jsonObject.getString("levels").split(",");
			if (itemType == null) {
				itemType = "";
			}
			for (int i = 0; i < playerIds.length; i++) {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(Integer.parseInt(playerIds[i]));
				if (null == worldPlayer) {
					throw new Exception();
				}
				if (itemType.contains("2")) {
					for (int j = 0; j < itemIds.length; j++) {
						Map<Integer, PlayerPet> ppsMap = ServiceManager.getManager().getPetItemService()
								.getPlayerPetMap(worldPlayer.getId());
						if (null != ppsMap.get(Integer.parseInt(itemIds[j]))) {// 校验是否已拥有
							warn += "玩家" + worldPlayer.getId() + "已拥有宠物" + itemIds[j] + ", ";
						}
					}
				}
			}
			if (!warn.equals("")) {
				throw new Exception();
			}
			for (int i = 0; i < playerIds.length; i++) {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(Integer.parseInt(playerIds[i]));
				// 2为奖励宠物
				if (itemType.contains("2")) {
					for (int j = 0; j < itemIds.length; j++) {
						ServiceManager.getManager().getPlayerPetService()
								.playerGetPet(worldPlayer.getId(), Integer.parseInt(itemIds[j]), false);
						GameLogService.addPet(worldPlayer.getId(), worldPlayer.getLevel(), Integer.parseInt(itemIds[j]), 2, 0, 0, 0);
					}
				} else {
					for (int j = 0; j < itemIds.length; j++) {
						if (Common.GOLDID == Integer.parseInt(itemIds[j])) {
							ServiceManager.getManager().getPlayerService()
									.updatePlayerGold(worldPlayer, Integer.parseInt(counts[j]), "GM给予", remark);
						} else if (Common.DIAMONDID == Integer.parseInt(itemIds[j])) {
							String rechargeRemark = remark;
							int orgigin = TradeService.ORIGIN_GM;
							String rechargeOrderNum = "";
							Float price = 0f;
							Date currentDate = new Date();
							if (Common.DIAMONDID == Integer.parseInt(itemIds[j])) {
								ServiceManager
										.getManager()
										.getPlayerService()
										.addTicketGm(worldPlayer, Integer.parseInt(counts[j]), orgigin, price, rechargeOrderNum,
												rechargeRemark, "", "", currentDate);
							} else {
								ServiceManager
										.getManager()
										.getPlayerService()
										.addTicketGm(worldPlayer, Integer.parseInt(counts[j]), orgigin, price, rechargeOrderNum,
												rechargeRemark, "", "", currentDate);
							}
						} else {
							int days = -1;
							int userNum = -1;
							if (0 == Integer.parseInt(dayOrcount[j])) {
								days = Integer.parseInt(counts[j]);
							} else {
								userNum = Integer.parseInt(counts[j]);
							}
							ServiceManager
									.getManager()
									.getRechargeRewardService()
									.givenItems(worldPlayer, userNum, days, Integer.parseInt(itemIds[j]), Integer.parseInt(levels[j]),
											remark);
						}
					}
				}
			}
			json.accumulate("stutas", true);
		} catch (Exception e) {
			e.printStackTrace();
			if (null != jsonObject)
				System.out.println(jsonObject.toString());
			json.accumulate("code", "500");
			if (warn.equals("")) {
				json.accumulate("message", "服务器内部错误");
			} else {
				json.accumulate("message", warn.substring(0, warn.length() - 2));
			}
			json.accumulate("stutas", false);
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
