package com.wyd.empire.world.item;

import java.util.ArrayList;
import java.util.List;

public class DailyRewardVo {
	private float exp = 1f;
	private float gold = 1f;
	private float medal = 1f;
	private float equipmentDay = 1f;
	private float subGold = 1f;
	private float subMedal = 1f;
	private float subTicket = 1f;
	private float subTime = 1f;
	private List<DailyActivityVo> dailyActivityVos = new ArrayList<DailyActivityVo>();

	public float getExp() {
		return exp;
	}

	public void addExp(float exp) {
		this.exp += exp;
	}

	public float getGold() {
		return gold;
	}

	public void addGold(float gold) {
		this.gold += gold;
	}

	public float getMedal() {
		return medal;
	}

	public void addMedal(float medal) {
		this.medal += medal;
	}

	public float getEquipmentDay() {
		return equipmentDay;
	}

	public void addEquipmentDay(float equipmentDay) {
		this.equipmentDay += equipmentDay;
	}

	public float getSubGold() {
		return subGold;
	}

	public void subGold(float subGold) {
		this.subGold *= subGold;
	}

	public float getSubMedal() {
		return subMedal;
	}

	public void subMedal(float subMedal) {
		this.subMedal *= subMedal;
	}

	public float getSubTicket() {
		return subTicket;
	}

	public void subTicket(float subTicket) {
		this.subTicket *= subTicket;
	}

	public float getSubTime() {
		return subTime;
	}

	public void subTime(float subTime) {
		this.subTime *= subTime;
	}

	public List<DailyActivityVo> getDailyActivityVos() {
		return dailyActivityVos;
	}

	public void addReward(DailyActivityVo dailyActivityVo) {
		addExp(dailyActivityVo.getExp() / 100f);
		addGold(dailyActivityVo.getGold() / 100f);
		addMedal(dailyActivityVo.getMedal() / 100f);
		addEquipmentDay(dailyActivityVo.getEquipmentDay() / 100f);
		subGold(dailyActivityVo.getSubGold() / 100f);
		subMedal(dailyActivityVo.getSubMedal() / 100f);
		subTicket(dailyActivityVo.getSubTicket() / 100f);
		subTime(dailyActivityVo.getSubTime() / 100f);
		dailyActivityVos.add(dailyActivityVo);
	}
}
