package com.wyd.channel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.wyd.channel.bean.ChannelLogin;
import com.wyd.channel.bean.ChannelLoginHandle;
import com.wyd.channel.bean.ChannelLoginResult;

/**
 * 渠道登陆
 * 
 * @author doter
 * 
 */
public class ChannelLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	Logger log = Logger.getLogger(ChannelLoginServlet.class);

	// private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Map<String, String> parameters = request.getParameterMap();
		HashMap<String, String> requestMap = new HashMap<String, String>();

		StringBuffer sb = new StringBuffer("渠道登录参数：");
		for (Entry<String, String> element : parameters.entrySet()) {
			String key = element.getKey();
			String[] value = (String[]) ((Entry) element).getValue();
			sb.append(key + ":");
			// element.getValue()[0];
			System.out.println(element.getKey());
			sb.append(value[0] + ",");
			requestMap.put(key, value[0]);
		}
		log.info(sb.toString());
		System.out.println(sb.toString());
		String channelStr = request.getParameter("channelid");// 渠道id

		if (channelStr != null) {
			ChannelService.getChannelService().createLoginHandle(requestMap, out);
		} else {
			out.write("接口准备就绪！");
			out.flush();
			out.close();
		}
	}
}
