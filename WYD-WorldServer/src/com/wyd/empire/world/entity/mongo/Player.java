package com.wyd.empire.world.entity.mongo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongo.entity.IEntity;

/**
 * 角色表
 * 
 * @author doter
 */

@Document(collection = "player")
public class Player extends IEntity {
	private Integer accountId; // 玩家帐号ID
	private Integer areaId; // 玩家分区ID
	private Integer lv; // 玩家等级
	private Integer money; // 玩家金币数量
	private String nickname; // 玩家角色名称
	private Byte status; // 玩家状态：0封号，1正常
	private Integer vipExp; // 玩家vip经验
	private Integer vipLv; // 玩家vip等级
	private Date gagEndTime; // 玩家禁言结束时间
	private Date bsTime; // 封号开始时间
	private Date beTime; // 封号结束时间
	private String udid;
	private String token;
	private Date createTime;
	private Date loginTime;// 上线时间
	private Date loginOutTime;// 上线时间
	private int fight; // 玩家当前战斗力
	private int onLineTime;// 在线时长
	private String clientModel;// 手机型号
	private String systemName;// 系统名称
	private String systemVersion;// 系统版本

	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getLv() {
		return lv;
	}
	public void setLv(Integer lv) {
		this.lv = lv;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Integer getVipExp() {
		return vipExp;
	}
	public void setVipExp(Integer vipExp) {
		this.vipExp = vipExp;
	}
	public Integer getVipLv() {
		return vipLv;
	}
	public void setVipLv(Integer vipLv) {
		this.vipLv = vipLv;
	}
	public Date getGagEndTime() {
		return gagEndTime;
	}
	public void setGagEndTime(Date gagEndTime) {
		this.gagEndTime = gagEndTime;
	}
	public Date getBsTime() {
		return bsTime;
	}
	public void setBsTime(Date bsTime) {
		this.bsTime = bsTime;
	}
	public Date getBeTime() {
		return beTime;
	}
	public void setBeTime(Date beTime) {
		this.beTime = beTime;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getFight() {
		return fight;
	}
	public void setFight(int fight) {
		this.fight = fight;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getLoginOutTime() {
		return loginOutTime;
	}
	public void setLoginOutTime(Date loginOutTime) {
		this.loginOutTime = loginOutTime;
	}
	public int getOnLineTime() {
		return onLineTime;
	}
	public void setOnLineTime(int onLineTime) {
		this.onLineTime = onLineTime;
	}
	public String getClientModel() {
		return clientModel;
	}
	public void setClientModel(String clientModel) {
		this.clientModel = clientModel;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

}