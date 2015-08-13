package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSkillOk extends AbstractData {
	private int[] skillId;		//技能id
	private String[] skillIcon;		//技能图标
	private String[] skillName;		//技能等级
	private int[] countdownTime;		//技能可以使用倒计时
	private int[] conLev;			//技能对应的公会等级
	private int   playerContribution;
	private int	  conContribution;


    public GetSkillOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkillOk, sessionId, serial);
    }

    public GetSkillOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkillOk);
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

	public int[] getConLev() {
		return conLev;
	}

	public void setConLev(int[] conLev) {
		this.conLev = conLev;
	}

	public int getPlayerContribution() {
		return playerContribution;
	}

	public void setPlayerContribution(int playerContribution) {
		this.playerContribution = playerContribution;
	}

	public int getConContribution() {
		return conContribution;
	}

	public void setConContribution(int conContribution) {
		this.conContribution = conContribution;
	}

	

}
