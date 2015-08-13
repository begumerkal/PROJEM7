package com.app.empire.protocol.data.vip;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetVipRewardListOk extends AbstractData {
	private int[]	    vipLevel;	//会员等级
	private int[]		itemId;     //物品id
	private int[] count;
	private int[] days;
	private int rewardType;
	private int[] isReceiveLvPack; //是否领取了vip等级礼包

	public GetVipRewardListOk(int sessionId, int serial) {
		super(Protocol.MAIN_VIP, Protocol.VIP_GetVipRewardListOk, sessionId, serial);
	}

	public GetVipRewardListOk() {
		super(Protocol.MAIN_VIP, Protocol.VIP_GetVipRewardListOk);
	}

	public int[] getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int[] ints) {
		this.vipLevel = ints;
	}

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}


	public int[] getCount() {
		return count;
	}

	public void setCount(int[] count) {
		this.count = count;
	}

	public int[] getDays() {
		return days;
	}

	public void setDays(int[] days) {
		this.days = days;
	}

	public int getRewardType() {
		return rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}

	public int[] getIsReceiveLvPack() {
		return isReceiveLvPack;
	}

	public void setIsReceiveLvPack(int[] isReceiveLvPack) {
		this.isReceiveLvPack = isReceiveLvPack;
	}
 
}
