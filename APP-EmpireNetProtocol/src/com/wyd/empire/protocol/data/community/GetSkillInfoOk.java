package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSkillInfoOk extends AbstractData {
	private String skillDetail;
	private String skillBuyByGold;
	private String skillBuyByDiamond;
	private String skillBuyByContribution;
	private String skillIcon;


    public GetSkillInfoOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkillInfoOk, sessionId, serial);
    }

    public GetSkillInfoOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSkillInfoOk);
    }

	public String getSkillDetail() {
		return skillDetail;
	}

	public void setSkillDetail(String skillDetail) {
		this.skillDetail = skillDetail;
	}

	public String getSkillBuyByGold() {
		return skillBuyByGold;
	}

	public void setSkillBuyByGold(String skillBuyByGold) {
		this.skillBuyByGold = skillBuyByGold;
	}

	public String getSkillBuyByDiamond() {
		return skillBuyByDiamond;
	}

	public void setSkillBuyByDiamond(String skillBuyByDiamond) {
		this.skillBuyByDiamond = skillBuyByDiamond;
	}

	public String getSkillBuyByContribution() {
		return skillBuyByContribution;
	}

	public void setSkillBuyByContribution(String skillBuyByContribution) {
		this.skillBuyByContribution = skillBuyByContribution;
	}

	public String getSkillIcon() {
		return skillIcon;
	}

	public void setSkillIcon(String skillIcon) {
		this.skillIcon = skillIcon;
	}

}
