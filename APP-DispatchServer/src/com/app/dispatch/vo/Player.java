package com.app.dispatch.vo;

public class Player {
	private int playerId;
	private int mapId;// 玩家所在的地图id
	private byte direction;// 方向1-12
	private int width;// 所在宽度位置
	private int high;// 所在高度位置
	private int toWidth;// 目标宽度位置
	private int toHigh;// 目标高度位置
	private String nickname;// 玩家昵称
	private int heroId;// 英雄id
	private int lv;// 等级
	private int vipLv; // 玩家vip等级
	private String property;// 属性
	private int fight; // 玩家当前战斗力
	private float speed = 3.3f;// 移动速度

	public Player(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public byte getDirection() {
		return direction;
	}
	public void setDirection(byte direction) {
		this.direction = direction;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public int getToWidth() {
		return toWidth;
	}

	public void setToWidth(int toWidth) {
		this.toWidth = toWidth;
	}

	public int getToHigh() {
		return toHigh;
	}

	public void setToHigh(int toHigh) {
		this.toHigh = toHigh;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
