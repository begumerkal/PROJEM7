package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetUsedSkillOk extends AbstractData {
	private int[] skillId;		//技能id
	private String[] skillIcon;		//技能图标
	private String[] skillName;		//技能等级
	private int[] countdownTime;		//技能可以使用倒计时
	private String[] skillDetail;			//技能描述


    public GetUsedSkillOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetUsedSkillOk, sessionId, serial);
    }

    public GetUsedSkillOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetUsedSkillOk);
    }

	public int[] getSkillId() {
		return skillId;
	}

	public void setSkillId(int[] skillId) {
		this.skillId = skillId;
	}

	public String[] getSkillIcon() {
		return skillIcon;
	}

	public void setSkillIcon(String[] skillIcon) {
		this.skillIcon = skillIcon;
	}

	public String[] getSkillName() {
		return skillName;
	}

	public void setSkillName(String[] skillName) {
		this.skillName = skillName;
	}

	public int[] getCountdownTime() {
		return countdownTime;
	}

	public void setCountdownTime(int[] countdownTime) {
		this.countdownTime = countdownTime;
	}

	public String[] getSkillDetail() {
		return skillDetail;
	}

	public void setSkillDetail(String[] skillDetail) {
		this.skillDetail = skillDetail;
	}

}
