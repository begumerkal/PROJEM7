package com.wyd.empire.world.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class GetPlayerInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	private static final String CONTENT_TYPE = "text/html";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONObject json = new JSONObject();
		try {
			if (("efun").equals(req.getParameter("type"))) {
				List<Player> list = ServiceManager.getManager().getIPlayerService().findByAccountIds(req.getParameter("accountId"));
				if (list != null && list.size() > 0) {
					List<Properties> data = new ArrayList<Properties>();
					for (Player player : list) {
						Properties properties = new Properties();
						properties.put("roleId", player.getId());
						properties.put("roleName", player.getName());
						properties.put("level", player.getLevel());
						properties.put("status", player.getStatus());
						properties.put("mac", player.getMac());
						data.add(properties);
					}
					json.accumulate("data", data);
				}
				json.accumulate("success", true);
				json.accumulate("info", "请求信息成功！");
			} else {
				byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
				String dataString = CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey"));
				JSONObject jsonObject = JSONObject.fromObject(dataString);
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(jsonObject.getInt("playerId"));
				if (worldPlayer == null) {
					json.accumulate("info", "用户id错误！");
					json.accumulate("success", false);
				} else {
					// id，等级，名称，现有钻石
					json.accumulate("info", "请求信息成功！");
					json.accumulate("id", worldPlayer.getId());
					json.accumulate("name", worldPlayer.getName());
					json.accumulate("level", worldPlayer.getLevel());
					json.accumulate("status", worldPlayer.getPlayer().getStatus());
					json.accumulate("mac", worldPlayer.getPlayer().getMac());
					json.accumulate("success", true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("info", "用户id错误！");
			json.accumulate("success", false);
		}
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType(CONTENT_TYPE);
		resp.setStatus(200);
		ServletOutputStream out = resp.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		os.write(json.toString());
		os.flush();
		os.close();
	}
}
