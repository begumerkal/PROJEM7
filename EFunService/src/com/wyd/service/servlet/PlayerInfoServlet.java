package com.wyd.service.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wyd.service.bean.Empireaccount;
import com.wyd.service.bean.Player;
import com.wyd.service.bean.PlayerVO;
import com.wyd.service.server.factory.ServiceManager;
import com.wyd.service.utils.HexBin;

/**
 * 获取角色信息
 * 
 * @author zengxc
 * 
 */
public class PlayerInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(PlayerInfoServlet.class);
	private Map<Integer,String> codeMap;
	public PlayerInfoServlet(){
		codeMap = new HashMap<Integer,String>();
		codeMap.put(1000, "success");
		codeMap.put(1002, "system error");
		codeMap.put(1003, "param empty");
		codeMap.put(1004, "db error");
		codeMap.put(1010, "sign error");
		codeMap.put(1038, "user not exist");
		codeMap.put(1039, "serverId not exist");
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private final String key = "#7*aUm~^)_mk@_?";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		String userId = request.getParameter("userid");
		String roleId = request.getParameter("roleid");
		String sign = request.getParameter("sign");
		String gameCode = request.getParameter("gameCode");
		String serverCode = request.getParameter("serverCode");
		roleId = roleId == null ? "" : roleId;
		userId = userId == null ? "" : userId;
		gameCode = gameCode == null ? "" : gameCode;
		serverCode = serverCode == null ? "" : serverCode;
		String result = "{\"result\":{1},\"message\":\"{2}\",\"role\":{3}}";
		String playerJson ="{}";
		if (sign == null) {
			os.write("接口准备就绪！");
		} else {
			int code = 1000;
			try {
				code = validate(userId, roleId, serverCode, gameCode, sign);
				if (code == 1000) {
					Result player = find(roleId, serverCode, gameCode);
					if (player != null) {
						JSONObject jsonOjbect = JSONObject.fromObject(player);
						playerJson = jsonOjbect.toString();
					} else {
						code = 1038;
					}
					response.setStatus(200);
				} 
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e, e);
				code = 1002;
			}
			result = result.replace("{1}", code + "");
			result = result.replace("{2}", codeMap.get(code));
			result = result.replace("{3}", playerJson);
			os.write(result);
		}
		os.flush();
		os.close();
	}

	private Result find(String roleId, String serverCode, String gameCode) {
		Result results = null;
		Player player = ServiceManager.getManager().getPlayerService().getById(Integer.parseInt(roleId));
		if (player == null)
			return null;
		PlayerVO vo = new PlayerVO(player);
		results = toResults(vo, roleId, serverCode, gameCode);
		return results;
	}

	private Result toResults(PlayerVO playervo, String userId, String serverCode, String gameCode) {
		Result result = new Result(userId, serverCode, gameCode);
		Player player = playervo.getPlayer();
		result.setName(player.getName());
		result.setTitle(playervo.getPlayerTitle());
		result.setCommunityName(playervo.getCommunityName());
		result.setCommunityPosition(playervo.getCommunityPosition());
		result.setSex(player.getSex());
		result.setLevel(playervo.getLevel());
		result.setWinNum(player.getWinNum());
		result.setPlayNum(player.getPlayNum());
		result.setForce(playervo.getForce());
		result.setArmor(playervo.getArmor());
		result.setAgility(playervo.getAgility());
		result.setCritAttackRate(playervo.getCritAttackRate());
		result.setPhysique(playervo.getPhysique());
		result.setExplodeRadius(playervo.getExplodeRadius());
		result.setWreckDefense(playervo.getWreckDefense());
		result.setReduceCrit(playervo.getReduceCrit());
		result.setLuck(playervo.getLuck());
		result.setReduceBury(playervo.getReduceBury());
		result.setMaxPF(playervo.getMaxPF());
		result.setMaxHP(playervo.getMaxHP());
		result.setInjuryFree(playervo.getInjuryFree());
		result.setFighting(playervo.getFighting());
		return result;
	}

	/**
	 * 数据有效性验证
	 * 
	 * @param data
	 * @param sign
	 * @return
	 */
	private int validate(String userId, String roleId, String serverCode, String gameCode, String sign) {
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
		// 验证角色ID,分区，帐号是否正确
		// 验证 areaId
		Player player = ServiceManager.getManager().getPlayerService().getById(Integer.parseInt(roleId));
		if (player == null) {
			return 1038;
		} else {
			int areaId = player.getAreaId();
			if (!serverCode.equals("" + areaId)) {
				System.out.println("分区不一致 " + areaId);
				return 1039;
			}
		}
		if(!"".equals(userId)){
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

		return 1000;
	}
	
	// 如果不是public在JSONArray.fromObject时会报错
	public class Result {
		private String roleId;
		private String serverCode;
		private String gameCode;
		private String name;
		private String title;
		private String communityName;
		private String communityPosition;
		private int sex;
		private int level;
		private int winNum; // 胜利次数
		private int playNum; // 游戏次数

		private int force;//
		private int armor;
		private int agility;
		private int critAttackRate;
		private int physique;
		private int explodeRadius;
		private int wreckDefense;
		private int reduceCrit;
		private int luck;
		private int reduceBury;
		private int maxPF;
		private int maxHP;
		private int injuryFree;
		private int fighting;

		public Result(String roleId, String serverCode, String gameCode) {
			this.roleId = roleId;
			this.serverCode = serverCode;
			this.gameCode = gameCode;
		}

		public String getRoleId() {
			return roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}

		public String getServerCode() {
			return serverCode;
		}

		public void setServerCode(String serverCode) {
			this.serverCode = serverCode;
		}

		public String getGameCode() {
			return gameCode;
		}

		public void setGameCode(String gameCode) {
			this.gameCode = gameCode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getCommunityName() {
			return communityName;
		}

		public void setCommunityName(String communityName) {
			this.communityName = communityName;
		}

		public String getCommunityPosition() {
			return communityPosition;
		}

		public void setCommunityPosition(String communityPosition) {
			this.communityPosition = communityPosition;
		}

		public int getSex() {
			return sex;
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getWinNum() {
			return winNum;
		}

		public void setWinNum(int winNum) {
			this.winNum = winNum;
		}

		public int getPlayNum() {
			return playNum;
		}

		public void setPlayNum(int playNum) {
			this.playNum = playNum;
		}

		public int getForce() {
			return force;
		}

		public void setForce(int force) {
			this.force = force;
		}

		public int getArmor() {
			return armor;
		}

		public void setArmor(int armor) {
			this.armor = armor;
		}

		public int getAgility() {
			return agility;
		}

		public void setAgility(int agility) {
			this.agility = agility;
		}

		public int getCritAttackRate() {
			return critAttackRate;
		}

		public void setCritAttackRate(int critAttackRate) {
			this.critAttackRate = critAttackRate;
		}

		public int getPhysique() {
			return physique;
		}

		public void setPhysique(int physique) {
			this.physique = physique;
		}

		public int getExplodeRadius() {
			return explodeRadius;
		}

		public void setExplodeRadius(int explodeRadius) {
			this.explodeRadius = explodeRadius;
		}

		public int getWreckDefense() {
			return wreckDefense;
		}

		public void setWreckDefense(int wreckDefense) {
			this.wreckDefense = wreckDefense;
		}

		public int getReduceCrit() {
			return reduceCrit;
		}

		public void setReduceCrit(int reduceCrit) {
			this.reduceCrit = reduceCrit;
		}

		public int getLuck() {
			return luck;
		}

		public void setLuck(int luck) {
			this.luck = luck;
		}

		public int getReduceBury() {
			return reduceBury;
		}

		public void setReduceBury(int reduceBury) {
			this.reduceBury = reduceBury;
		}

		public int getMaxPF() {
			return maxPF;
		}

		public void setMaxPF(int maxPF) {
			this.maxPF = maxPF;
		}

		public int getMaxHP() {
			return maxHP;
		}

		public void setMaxHP(int maxHP) {
			this.maxHP = maxHP;
		}

		public int getInjuryFree() {
			return injuryFree;
		}

		public void setInjuryFree(int injuryFree) {
			this.injuryFree = injuryFree;
		}

		public int getFighting() {
			return fighting;
		}

		public void setFighting(int fighting) {
			this.fighting = fighting;
		}

	}

	private String getAccountId(String accountId) {
		int index_ = accountId.indexOf("_");
		if (accountId != null && index_ != -1) {
			return accountId.substring(index_ + 1);
		}
		return accountId;
	}
}