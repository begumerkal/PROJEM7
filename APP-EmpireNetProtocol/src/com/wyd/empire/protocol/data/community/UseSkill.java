package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class UseSkill extends AbstractData {
	private int skillId;

    public UseSkill(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_UseSkill, sessionId, serial);
    }

    public UseSkill() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_UseSkill);
    }

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

}
