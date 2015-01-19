package com.wyd.empire.protocol.data.vip;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetVipPrivilegeInfoOk extends AbstractData {
	private int[]	vipLevel;	//vip等级
	private int[]	experienceAddPer;	//经验加成比例
	private int[]	shopDiscoun;	//上品折扣比例
	private int[]	strengthenAddPer;	//强化加成比例


	
	public GetVipPrivilegeInfoOk(int sessionId, int serial) {
		super(Protocol.MAIN_VIP, Protocol.VIP_GetVipPrivilegeInfoOk, sessionId, serial);
	}

	public GetVipPrivilegeInfoOk() {
		super(Protocol.MAIN_VIP, Protocol.VIP_GetVipPrivilegeInfoOk);
	}

	public int[] getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int[] vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int[] getExperienceAddPer() {
		return experienceAddPer;
	}

	public void setExperienceAddPer(int[] experienceAddPer) {
		this.experienceAddPer = experienceAddPer;
	}

	public int[] getShopDiscoun() {
		return shopDiscoun;
	}

	public void setShopDiscoun(int[] shopDiscoun) {
		this.shopDiscoun = shopDiscoun;
	}

	public int[] getStrengthenAddPer() {
		return strengthenAddPer;
	}

	public void setStrengthenAddPer(int[] strengthenAddPer) {
		this.strengthenAddPer = strengthenAddPer;
	}


}
