package com.wyd.service.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wyd.db.page.PageList;
import com.wyd.service.bean.Empireaccount;
import com.wyd.service.bean.Player;
import com.wyd.service.server.factory.ServiceManager;
import com.wyd.service.utils.HexBin;

/**
 * 郵件
 * 
 * @author zengxc
 * 
 */
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	Logger log = Logger.getLogger(MailServlet.class);
	private ServiceManager manager;
	private Map<Integer, String> codeMap;

	public MailServlet() {
		codeMap = new HashMap<Integer, String>();
		codeMap.put(1000, "success");
		codeMap.put(1002, "system error");
		codeMap.put(1003, "param empty");
		codeMap.put(1004, "db error");
		codeMap.put(1010, "sign error");
		codeMap.put(1038, "user not exist");
		codeMap.put(1039, "serverId not exist");
		codeMap.put(1012, "title or content too long");
	}

	// private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	private final String key = "#7*aUm~^)_mk@_?";
	private final int SEND = 1;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		manager = ServiceManager.getManager();
		switch (method(request)) {
		case SEND:
			Send(request, response, os);
			break;
		}
		os.flush();
		os.close();
	}

	public void Send(HttpServletRequest request, HttpServletResponse response, OutputStreamWriter os) throws IOException {
		String userId = request.getParameter("userid");
		userId = userId == null ? "" : userId;
		String roleId = request.getParameter("roleid");
		roleId = roleId == null ? "" : roleId;
		String sign = request.getParameter("sign");
		String gameCode = request.getParameter("gameCode");
		gameCode = gameCode == null ? "" : gameCode;
		String serverCode = request.getParameter("serverCode");
		serverCode = serverCode == null ? "" : serverCode;
		String title = request.getParameter("title");
		title = title == null ? "" : title;
		String content = request.getParameter("content");
		content = content == null ? "" : content;
		String result = "{\"result\":{1},\"message\":\"{2}\"}";
		if (sign == null) {
			os.write("接口准备就绪！");
		} else {
			int code = 1000;
			try {
				code = validate(userId, roleId, serverCode, gameCode, title, content, sign);
				if (code == 1000) {
					// 有角色ID，发给角色；有帐号ID给帐号下所有角色发；有区服，发给所有区服；什么也没有则发给全游戏
					if (!"".equals(roleId)) {
						Player player = ServiceManager.getManager().getPlayerService().getById(Integer.parseInt(roleId));
						sendToPlayer(player, title, content);

					} else if (!"".equals(userId)) {
						Empireaccount empireaccount = ServiceManager.getManager().getAccountService().getEmpireaccount("EFHK_" + userId);
						int accountId = empireaccount.getId();
						List<Player> players = ServiceManager.getManager().getPlayerService().getByAccountId(accountId);
						if (players == null || players.size() < 1) {
							code = 1038;
						} else {
							sendToPlayer(players, title, content);
						}

					} else {
						int size = 1000, index = 1, total = 1, areaId = -1;
						if (!"".equals(serverCode)) {
							Integer.parseInt(serverCode);
						}
						do {
							PageList pageList = ServiceManager.getManager().getPlayerService().findPageList(areaId, index, size);
							List<Object> objectList = pageList.getList(); 
							for(int i=0;i>objectList.size();i++){
								Player player = (Player)objectList.get(i);
								sendToPlayer(player, title, content);
							}							
							total = pageList.getTotalSize();
							index++;
						} while (index <= total);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				code = 1002;
			}
			result = result.replace("{1}", code + "");
			result = result.replace("{2}", codeMap.get(code));
			os.write(result);
		}
	}

	/**
	 * 向玩家发送
	 * 
	 * @param player
	 * @param title
	 * @param content
	 */
	private void sendToPlayer(Player player, String title, String content) {
		manager.getMailService().saveMail(player, title, content);
	}

	private void sendToPlayer(List<Player> players, String title, String content) {
		for (Player player : players) {
			manager.getMailService().saveMail(player, title, content);
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param request
	 * @return
	 */
	private int method(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		if (requestURI.indexOf("Send") != -1) {
			return SEND;
		}

		return -1;
	}

	private String getAccountId(String accountId) {
		int index_ = accountId.indexOf("_");
		if (accountId != null && index_ != -1) {
			return accountId.substring(index_ + 1);
		}
		return accountId;
	}

	/**
	 * 数据有效性验证
	 * 
	 * @param data
	 * @param sign
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private int validate(String userId, String roleId, String serverCode, String gameCode, String title, String content, String sign) throws UnsupportedEncodingException {
		String sign_content = userId + serverCode + roleId + gameCode + key;
		if (sign == null)
			return 1010;
		String checkSign = HexBin.HashToMD5Hex(sign_content);
		sign = sign.toUpperCase();
		if (!sign.equals(checkSign)) {
			System.out.println(sign_content);
			System.out.println(checkSign);
			return 1010;
		}
		// 如果角色ID不为空，则分区，帐号都不能为空
		if (!"".equals(roleId)) {
			if ("".equals(serverCode) || "".equals(userId)) {
				return 1003;
			}
		}
		Player player = null;
		// 验证角色ID,分区，帐号是否正确
		// 验证 areaId
		if (!"".equals(serverCode) && !"".equals(roleId)) {
			player = ServiceManager.getManager().getPlayerService().getById(Integer.parseInt(roleId));
			if (player == null) {
				return 1038;
			} else {
				int areaId = player.getAreaId();
				if (!serverCode.equals("" + areaId)) {
					System.out.println("分区不一致 " + areaId);
					return 1039;
				}
			}
		}
		if (!"".equals(userId) && !"".equals(roleId)) {
			// 验证accountId
			Empireaccount empireaccount = ServiceManager.getManager().getAccountService().getById(player.getAccountId());
			if (empireaccount == null) {
				return 1039;
			} else {
				String accountName = empireaccount.getName();
				if (!userId.equals(getAccountId(accountName))) {
					System.out.println("帐号不一致 " + accountName);
					return 1039;
				}
			}
		}
		if (title.getBytes("gbk").length > 30) {// 主题15个汉字
			return 1012;
		}
		if (content.getBytes("gbk").length > 500) {// 内容250个汉字
			return 1012;
		}
		return 1000;
	}
public static void main(String[] args) throws UnsupportedEncodingException {
	System.out.println("中文".getBytes("gbk").length);
}
}
