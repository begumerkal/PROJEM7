package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendSkillList extends AbstractData {
	
	private int	itemId;
	private String	icon;
	private int	strengthoneLevel;
	private int[]	skillId;
	private String[]	skillName;
	private boolean[]	lockOr;
	
    public SendSkillList(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_SendSkillList, sessionId, serial);
    }

    public SendSkillList() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_SendSkillList);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getStrengthoneLevel() {
		return strengthoneLevel;
	}

	public void setStrengthoneLevel(int strengthoneLevel) {
		this.strengthoneLevel = strengthoneLevel;
	}

	public int[] getSkillId() {
		return skillId;
	}

	public void setSkillId(int[] skillId) {
		this.skillId = skillId;
	}

	public String[] getSkillName() {
		return skillName;
	}

	public void setSkillName(String[] skillName) {
		this.skillName = skillName;
	}

	public boolean[] getLockOr() {
		return lockOr;
	}

	public void setLockOr(boolean[] lockOr) {
		this.lockOr = lockOr;
	}
    
    
}
