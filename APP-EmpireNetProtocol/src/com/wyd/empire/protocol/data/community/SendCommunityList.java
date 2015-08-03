package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SendCommunityList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SendCommunityList(发送工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendCommunityList extends AbstractData {
	private int[] communityId;
	private String[] communityName;
	private int[] level;
	private int[] prestige;
	private int[] rank;		//排名
	private boolean isCommunityMember;
	private int	pageNumber; //当前页数
	private int totalNumber; //总页数
	
    public SendCommunityList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendCommunityList, sessionId, serial);
    }

    public SendCommunityList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendCommunityList);
    }

	public int[] getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int[] communityId) {
		this.communityId = communityId;
	}

	public String[] getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String[] communityName) {
		this.communityName = communityName;
	}

	public int[] getLevel() {
		return level;
	}

	public void setLevel(int[] level) {
		this.level = level;
	}

	public int[] getPrestige() {
		return prestige;
	}

	public void setPrestige(int[] prestige) {
		this.prestige = prestige;
	}

	public boolean getIsCommunityMember() {
		return isCommunityMember;
	}

	public int[] getRank() {
		return rank;
	}

	public void setRank(int[] rank) {
		this.rank = rank;
	}

	public void setIsCommunityMember(boolean isCommunityMember) {
		this.isCommunityMember = isCommunityMember;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
    
}
