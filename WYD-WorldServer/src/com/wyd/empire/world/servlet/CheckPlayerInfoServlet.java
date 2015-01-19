package com.wyd.empire.world.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 检查用户的角色名称和邀请码是否正确
 * 
 * @author Administrator
 *
 */
public class CheckPlayerInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;

	public static final String EFUNKEY = "gf51dfg98jhjk4";// efun抽奖加密key

	// /private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		JSONObject jsonObject = null;
		try {
			byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
			String dataString = CryptionUtil.Decrypt(data, EFUNKEY);
			jsonObject = JSONObject.fromObject(dataString);
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService()
					.getWorldPlayerByName(jsonObject.getString("playerName"));
			if (null != worldPlayer) {
				String inviteCode = ServiceManager.getManager().getInviteService().makeInviteCode(worldPlayer);
				if (inviteCode.equals(jsonObject.getString("inviteCode"))) {
					json.accumulate("code", "0");
					json.accumulate("message", "角色信息正确");
				} else {
					json.accumulate("code", "2");
					json.accumulate("message", "角色信息错误");
				}
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
