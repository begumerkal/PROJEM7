package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSkillInfo extends AbstractData {
	private int skillId;

    public GetSkillInfo(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkillInfo, sessionId, serial);
    }

    public GetSkillInfo() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkillInfo);
    }

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

}
