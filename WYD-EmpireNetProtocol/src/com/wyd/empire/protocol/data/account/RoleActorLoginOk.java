package com.wyd.empire.protocol.data.account;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class RoleActorLoginOk extends AbstractData {
	private Integer id;
	private String nickname; // 玩家角色名称
	private Integer lv; // 玩家等级
	private Integer lvExp; // 玩家等级
	private Integer vipLv; // 玩家vip等级
	private Integer vipExp; // 玩家vip经验
	private Integer money; // 玩家金币数量
	private String property;// 属性
	private int fight; // 玩家当前战斗力

	public RoleActorLoginOk(int sessionId, int serial) {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RoleActorLoginOk, sessionId, serial);
	}

	public RoleActorLoginOk() {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_RoleActorLoginOk);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Integer getLvExp() {
		return lvExp;
	}

	public void setLvExp(Integer lvExp) {
		this.lvExp = lvExp;
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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public int getFight() {
		return fight;
	}

	public void setFight(int fight) {
		this.fight = fight;
	}

}
