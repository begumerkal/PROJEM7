package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SendCommunityList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SendCommunityList(发送工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendSituationList extends AbstractData {
	private int[] communityId;
	private String[] communityName;
	private int[]	rank;
	private int[] winNumber;
	private int[] winOdds;
	private int	pageNumber; //当前页数
	private int totalNumber; //总页数
	
    public SendSituationList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendSituationList, sessionId, serial);
    }

    public SendSituationList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendSituationList);
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

	public int[] getWinNumber() {
		return winNumber;
	}

	public void setWinNumber(int[] winNumber) {
		this.winNumber = winNumber;
	}

	public int[] getWinOdds() {
		return winOdds;
	}

	public void setWinOdds(int[] winOdds) {
		this.winOdds = winOdds;
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

	public int[] getRank() {
		return rank;
	}

	public void setRank(int[] rank) {
		this.rank = rank;
	}
    
}
