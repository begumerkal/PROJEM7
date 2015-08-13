package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetCommunityInfoOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityInfoOk(获取公会信息成功)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetCommunityInfoOk extends AbstractData {
	private int communityId;
	private String communityName;
	private int presidentId;
	private String presidentName;
	private int memberCount;
	private int totalMember;
	private int level;
	private int prestige;
	private int money;
	private String declaration;
	private boolean isCanApply;
	private String[] enemyNameList;
	private String[] enemyCommunityList;
	//enemyNameList	string[]	敌对公会id名称列表
	//enemySituationList	string[]	敌对公会战况列表

	
    public GetCommunityInfoOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityInfoOk, sessionId, serial);
    }

    public GetCommunityInfoOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityInfoOk);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public int getTotalMember() {
		return totalMember;
	}

	public void setTotalMember(int totalMember) {
		this.totalMember = totalMember;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPrestige() {
		return prestige;
	}

	public void setPrestige(int prestige) {
		this.prestige = prestige;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}


	public int getPresidentId() {
		return presidentId;
	}

	public void setPresidentId(int presidentId) {
		this.presidentId = presidentId;
	}

	public String getPresidentName() {
		return presidentName;
	}

	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public boolean getIsCanApply() {
		return isCanApply;
	}

	public void setIsCanApply(boolean isCanApply) {
		this.isCanApply = isCanApply;
	}

	public String[] getEnemyCommunityList() {
		return enemyCommunityList;
	}

	public void setEnemyCommunityList(String[] enemyCommunityList) {
		this.enemyCommunityList = enemyCommunityList;
	}

	public String[] getEnemyNameList() {
		return enemyNameList;
	}

	public void setEnemyNameList(String[] enemyNameList) {
		this.enemyNameList = enemyNameList;
	}

}
