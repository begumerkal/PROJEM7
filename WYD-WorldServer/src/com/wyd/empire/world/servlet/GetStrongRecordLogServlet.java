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
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class GetStrongRecordLogServlet extends HttpServlet {

	private static final long serialVersionUID = 6352375886972568488L;

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
			int pageIndex = jsonObject.getInt("pageIndex") - 1;
			int pageSize = jsonObject.getInt("pageSize");
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

				}
			}
			PageList pageList = ServiceManager.getManager().getGetLogService().getStrongrecordLogList(sb.toString(), pageIndex, pageSize);
			JSONArray jsonArray = new JSONArray();
			if (null != pageList && pageList.getList().size() > 0) {
				List<Properties> mailList = new ArrayList<Properties>();
				for (Object obj : pageList.getList()) {
					StrongeRecord strongRecord = (StrongeRecord) obj;
					Properties p = new Properties();
					p.put("id", strongRecord.getId());
					p.put("playerId", strongRecord.getPlayerId());
					p.put("level", strongRecord.getLevel());
					p.put("remark", strongRecord.getRemark() == null ? "" : strongRecord.getRemark());
					p.put("createTime", strongRecord.getCreateTime());
					p.put("type", strongRecord.getType());
					p.put("itemId", strongRecord.getItemId());
					mailList.add(p);
				}
				jsonArray = JSONArray.fromObject(mailList);
				jsonObject.accumulate("content", jsonArray);
			} else {
				jsonObject.accumulate("content", "");
			}
			jsonObject.accumulate("pageIndex", pageIndex);
			jsonObject.accumulate("pageSize", pageSize);
			jsonObject.accumulate("dataCount", pageList.getFullListSize());
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
