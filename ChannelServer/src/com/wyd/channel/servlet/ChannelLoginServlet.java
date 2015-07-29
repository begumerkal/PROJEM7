package com.wyd.channel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Enumeration<?> params = request.getParameterNames();
		log.info("登陆请求信息:");
		String paramstr = "";
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			paramstr += paramName + "=" + paramValues[0] + "&";
		}
		log.info(paramstr);
		System.out.println(paramstr);
		String channelStr = request.getParameter("channelid");// 渠道id

		if (channelStr != null) {
			ChannelService service = ChannelService.getChannelService();
			service.createLoginHandle(request, out);
		} else {
			out.write("接口准备就绪！");
			out.flush();
			out.close();
		}
	}

}
