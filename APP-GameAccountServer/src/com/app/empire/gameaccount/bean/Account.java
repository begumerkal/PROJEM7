package com.app.empire.gameaccount.bean;
import org.springframework.data.mongodb.core.mapping.Document;

import com.app.db.mongo.entity.IEntity;
/**
 * The persistent class for the tab_account database table.
 * 
 * @author doter
 */
@Document(collection = "account")
public class Account extends IEntity{
    /**
	 * 账号表
	 */
	private static final long serialVersionUID = 1L;

    private String             username;
    private String             password;
    private Integer            status;//状态
    private Integer            createTime;
    private String             version;//游戏版本
    private Integer            totalLoginTimes;//总登录次数
    private Integer            onLineTime;//在线时长
    private Integer            lastLoginTime;//上次登录时间
    private Integer            maxLevel;//该账号所有角色最高等级
    private String             serverid;//所在游戏区名
    private Integer            machinecode;//服务器码
    private Integer            channel;//渠道号
    private String             systemName;//客户端系统
    private String             clientModel;//客户端型号
    private String             systemVersion;//客户端系统版本
    private String             ipAddress;//ip 地址
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getTotalLoginTimes() {
		return totalLoginTimes;
	}
	public void setTotalLoginTimes(Integer totalLoginTimes) {
		this.totalLoginTimes = totalLoginTimes;
	}
	public Integer getOnLineTime() {
		return onLineTime;
	}
	public void setOnLineTime(Integer onLineTime) {
		this.onLineTime = onLineTime;
	}
	public Integer getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Integer lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public Integer getMachinecode() {
		return machinecode;
	}
	public void setMachinecode(Integer machinecode) {
		this.machinecode = machinecode;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getClientModel() {
		return clientModel;
	}
	public void setClientModel(String clientModel) {
		this.clientModel = clientModel;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
     
    
    
    
    
}