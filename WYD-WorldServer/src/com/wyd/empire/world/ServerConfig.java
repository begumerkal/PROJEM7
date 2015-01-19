package com.wyd.empire.world;

import org.apache.commons.configuration.Configuration;

public class ServerConfig {
	private boolean isMaintance = true;
	private int maxLevel;
	private int zsMaxLevel;
	private String area;
	private int machineCode = 0;
	private String areaId;
	private String group;
	private int serverId;
	private boolean cross;
	private String version;// 服务器版本用于跨服配对
	private String serverName;// 服务器名称用于跨服配对

	public ServerConfig(Configuration configuration) {
		maxLevel = configuration.getInt("maxlevel", 99);
		zsMaxLevel = configuration.getInt("zsMaxlevel", 219);
		setAreaId(configuration.getString("areaid"));
		setGroup(configuration.getString("group", "group1"));
		setMachineCode(configuration.getInt("machinecode"));
		setServerId(configuration.getInt("serviceid"));
		setVersion(configuration.getString("version"));
		String battleip = configuration.getString("battleip");
		String battleport = configuration.getString("battleport");
		setServerName(configuration.getString("servername"));
		if (null != battleip && null != battleport)
			cross = true;
	}

	/**
	 * 游戏是否在维护
	 * 
	 * @return
	 */
	public boolean isMaintance() {
		return isMaintance;
	}

	public void setMaintance(boolean isMaintance) {
		this.isMaintance = isMaintance;
	}

	/**
	 * 游戏最大级别
	 * 
	 * @return
	 */
	public int getMaxLevel(int zsLevel) {
		if (zsLevel > 0) {
			return zsMaxLevel;
		} else {
			return maxLevel;
		}
	}

	/**
	 * 服务器分区号
	 * 
	 * @return
	 */
	public String getArea() {
		return area;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
		this.area = areaId.substring(0, areaId.indexOf("_"));
	}

	/**
	 * 服务器ID
	 * 
	 * @return
	 */
	public int getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(int machineCode) {
		this.machineCode = machineCode;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	/**
	 * 是否开启跨服战斗
	 * 
	 * @return
	 */
	public boolean isCross() {
		return cross;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getServerName() {
		return serverName == null ? "" : serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
