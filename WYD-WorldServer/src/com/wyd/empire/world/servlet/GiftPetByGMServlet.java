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

import org.apache.log4j.Logger;

import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 批量发送宠物奖励
 */
public class GiftPetByGMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(GiftPetByGMServlet.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		JSONObject jsonObject = null;
		String warn = "";
		try {
			byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
			String dataString = CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey"));
			jsonObject = JSONObject.fromObject(dataString);
			String[] playerIds = jsonObject.getString("playerIds").split(",");
			String[] petId = jsonObject.getString("petId").split(",");
			String remark = jsonObject.getString("remark");
			String title = jsonObject.getString("mailTitle");
			String content = jsonObject.getString("mail");
			for (int i = 0; i < playerIds.length; i++) {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(Integer.parseInt(playerIds[i]));
				if (null == worldPlayer) {
					throw new Exception();
				}
				for (int j = 0; j < petId.length; j++) {
					Map<Integer, PlayerPet> ppsMap = ServiceManager.getManager().getPetItemService().getPlayerPetMap(worldPlayer.getId());
					if (null != ppsMap.get(Integer.parseInt(petId[j]))) {// 校验是否已拥有
						warn += "玩家" + worldPlayer.getId() + "已拥有宠物" + petId[j] + ", ";
					}
				}
			}
			if (!warn.equals("")) {
				throw new Exception(warn);
			}
			for (int i = 0; i < playerIds.length; i++) {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(Integer.parseInt(playerIds[i]));
				for (int j = 0; j < petId.length; j++) {
					ServiceManager.getManager().getPlayerPetService().playerGetPet(worldPlayer.getId(), Integer.parseInt(petId[j]), false);
					GameLogService.addPet(worldPlayer.getId(), worldPlayer.getLevel(), Integer.parseInt(petId[j]), 2, 0, 0, 0);
				}
				// 保存邮件
				Mail mail = new Mail();
				mail.setTheme(title);
				mail.setContent(content);
				mail.setIsRead(false);
				mail.setReceivedId(worldPlayer.getId());
				mail.setSendId(0);
				mail.setSendName(TipMessages.SYSNAME_MESSAGE);
				mail.setSendTime(new Date());
				mail.setType(1);
				mail.setBlackMail(false);
				mail.setIsStick(Common.IS_STICK);
				ServiceManager.getManager().getMailService().saveMail(mail, null);
			}
			json.accumulate("stutas", true);
		} catch (Exception e) {
			log.error(e, e);
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
