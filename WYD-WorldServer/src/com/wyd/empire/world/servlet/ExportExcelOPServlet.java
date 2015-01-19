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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 导出意见箱
 * 
 * @author Administrator
 *
 */
public class ExportExcelOPServlet extends HttpServlet {
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
			String params = jsonObject.getString("params");
			int pageIndex = 0;
			int pageSize = 5000;
			String[] dates = params.split("\\|");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < dates.length; i++) {
				switch (i) {
					case 0 :
						sb.append(dates[0]).append("|").append(dates[1]).append("|");
						break;
					case 2 :
						if (ServiceUtils.isNumeric(dates[2])) {
							sb.append(dates[2]);
						} else {
							Player player = ServiceManager.getManager().getIPlayerService().getPlayerByName(dates[2], true);
							if (player != null) {
								sb.append(player.getId());
							}
						}
						sb.append("|");
						break;
					case 3 :
						sb.append(dates[3]).append("|");
						break;
					case 4 :
						sb.append(dates[4]).append("|");
						break;
					case 5 :
						sb.append(dates[5]).append("|");
						break;
					case 6 :
						sb.append(dates[6]);
						break;
				}
			}
			PageList pageList = ServiceManager.getManager().getMailService().getSuggestionBox(sb.toString(), pageIndex, pageSize);
			JSONArray jsonArray = new JSONArray();
			if (null != pageList && pageList.getList().size() > 0) {
				List<Properties> mailList = new ArrayList<Properties>();
				for (Object obj : pageList.getList()) {
					Mail mail = (Mail) obj;
					Properties p = new Properties();
					p.put("id", mail.getId());
					p.put("sendId", mail.getSendId());
					if (0 != mail.getSendId()) {
						Player sended = (Player) ServiceManager.getManager().getIPlayerService().get(Player.class, mail.getSendId());
						if (sended != null) {
							p.put("sendName", sended.getName());
							p.put("serverId",
									ServiceManager.getManager().getConfiguration().getString("areaid").split("_")[0] + sended.getAreaId() == null
											? ""
											: sended.getAreaId());
						} else {
							p.put("sendName", "");
							p.put("serverId", "");
						}
					} else {
						p.put("sendName", "");
						p.put("serverId", "");
					}
					p.put("receivedId", mail.getReceivedId());
					Player received = (Player) ServiceManager.getManager().getIPlayerService().get(Player.class, mail.getReceivedId());
					if (received != null) {
						p.put("receivedName", received.getName());
					} else {
						p.put("receivedName", "");
					}
					p.put("theme", mail.getTheme());
					p.put("content", mail.getContent());
					p.put("type", mail.getType());
					p.put("sendTime", mail.getSendTime());
					p.put("isRead", mail.getIsRead());
					p.put("blackMail", mail.getBlackMail());
					p.put("remark", mail.getRemark() == null ? "" : mail.getRemark());
					p.put("isHandle", mail.getIsHandle() == null ? "" : mail.getIsHandle());
					p.put("isStick", mail.getIsStick());
					mailList.add(p);
				}
				jsonArray = JSONArray.fromObject(mailList);
				jsonObject.accumulate("content", jsonArray);
			} else {
				jsonObject.accumulate("content", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (null != jsonObject)
				System.out.println(jsonObject.toString());
			if (warn.equals("")) {
				json.accumulate("message", "服务器内部错误");
			} else {
				json.accumulate("message", warn.substring(0, warn.length() - 2));
			}
		}
		resp.setContentType("text/html");
		resp.setStatus(200);
		ServletOutputStream out = resp.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		os.write(jsonObject.toString());
		os.flush();
		os.close();
	}
}
