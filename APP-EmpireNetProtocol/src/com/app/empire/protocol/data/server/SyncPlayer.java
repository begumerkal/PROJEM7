package com.app.empire.protocol.data.server;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/****
 * 玩家信息同步
 * 
 * @author doter
 *
 */
public class SyncPlayer extends AbstractData {
	private int id;// 角色id
	private int heroId;// 英雄id
	private String nickname; // 玩家角色名称
	private int lv; // 玩家等级
	private int vipLv; // 玩家vip等级
	private String property;// 属性
	private int fight; // 玩家当前战斗力
	

	public SyncPlayer(int sessionId, int serial) {
		super(Protocol.MAIN_ACCOUNT, Protocol.SERVER_SyncPlayer, sessionId, serial);
	}

	public SyncPlayer() {
		super(Protocol.MAIN_ACCOUNT, Protocol.SERVER_SyncPlayer);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public int getVipLv() {
		return vipLv;
	}

	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
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
