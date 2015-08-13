package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class StartRaidsOk extends AbstractData {
	private String[] rewardIcon;//奖励图标（全部）
	private String[] rewardName;//奖励名称（全部）
	private int []   addExp;//  得到的经验

	public StartRaidsOk(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartRaidsOk, sessionId, serial);
    }
	public StartRaidsOk(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_StartRaidsOk);
	}
	
	public String[] getRewardIcon() {
		return rewardIcon;
	}
	public void setRewardIcon(String[] rewardIcon) {
		this.rewardIcon = rewardIcon;
	}
	public String[] getRewardName() {
		return rewardName;
	}
	public void setRewardName(String[] rewardName) {
		this.rewardName = rewardName;
	}
	public int[] getAddExp() {
		return addExp;
	}
	public void setAddExp(int[] addExp) {
		this.addExp = addExp;
	}
	
	
}
