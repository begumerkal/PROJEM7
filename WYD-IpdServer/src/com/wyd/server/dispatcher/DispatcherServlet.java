package com.wyd.server.dispatcher;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wyd.server.service.ServerInfo;
import com.wyd.server.service.ServiceManager;
public class DispatcherServlet extends HttpServlet {
	/*
	 * 玩家ip 申请
	 */
	private static final String CONTENT_TYPE = "text/html;charset=utf-8";
	private static final long serialVersionUID = 110325631288123751L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String udid = req.getParameter("udid");
		String area = req.getParameter("area");// 地区
		String group = req.getParameter("group");// 组
		String channel = req.getParameter("channel");// 渠道
		String serverid = req.getParameter("serverid");// 服务器id
		String versionString = req.getParameter("version");// 版本

		if (null == area)
			area = "CN";
		if (null == versionString)
			versionString = "1.0.0";
		if (null == channel)
			channel = "1000";
		if (serverid == null)
			serverid = "0";

		StringBuffer sb = new StringBuffer();
		if (!ServiceManager.getManager().getConfigService().exisArea(area)) {
			area = ServiceManager.getManager().getConfiguration().getString("defaultArea");
		}
		group = channel + "_" + group;
		if (!ServiceManager.getManager().getConfigService().exisGroup(area, group)) {
			group = ServiceManager.getManager().getConfiguration().getString("defaultGroup");
		}
		ServerInfo serverInfo = null;
		if (ServiceManager.getManager().getVersionService().isTestVersion(versionString)
				|| ServiceManager.getManager().getVersionService().isTestChannel(channel)) {// 测试版本或者是测试渠道
			serverInfo = ServiceManager.getManager().getServerListService().getTestServerInfo(area, group);
		}
		if (serverInfo == null) {
			if (ServiceManager.getManager().getConfigService().exisMachine(area, group, Integer.parseInt(serverid))) {// 服存在
				serverInfo = ServiceManager.getManager().getServerListService().getServerInfoMap().get(area).get(group)
						.get(Integer.parseInt(serverid));
			} else {// 指定的服不存在根据几率随机一个
				serverInfo = ServiceManager.getManager().getServerListService().getServerInfo(area, group);
			}
			if (serverInfo != null) {
				sb.append(ServiceManager.getManager().getUserInfoService().infoToString(serverInfo, versionString, udid, channel));
			} else {
				sb.append("0,");
				sb.append(ServiceManager.getManager().getConfiguration().getString("busyMessage"));
			}
		} else {
			sb.append(ServiceManager.getManager().getUserInfoService().infoToString(serverInfo, versionString, channel));
		}

		resp.setContentType(CONTENT_TYPE);
		resp.setStatus(200);
		ServletOutputStream out = resp.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		os.write(sb.toString());
		os.flush();
		os.close();
	}
}
