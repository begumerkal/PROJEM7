package com.app.empire.protocol.data.strengthen;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PurifyOk extends AbstractData {
    
    private int skillId1;
    private int skillId2;
    
    public PurifyOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_PurifyOk, sessionId, serial);
    }

    public PurifyOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_PurifyOk);
    }

	public int getSkillId1() {
		return skillId1;
	}

	public void setSkillId1(int skillId1) {
		this.skillId1 = skillId1;
	}

	public int getSkillId2() {
		return skillId2;
	}

	public void setSkillId2(int skillId2) {
		this.skillId2 = skillId2;
	}

	
}
