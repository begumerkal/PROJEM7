package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class BuySkill extends AbstractData {
	private int skillId;
	private int buySkillWayTag;

    public BuySkill(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_BuySkill, sessionId, serial);
    }

    public BuySkill() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_BuySkill);
    }

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getBuySkillWayTag() {
		return buySkillWayTag;
	}

	public void setBuySkillWayTag(int buySkillWayTag) {
		this.buySkillWayTag = buySkillWayTag;
	}


}
