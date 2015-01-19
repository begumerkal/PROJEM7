package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SetEnemyCommunity</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityList(获取工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CommunitySetEnemy extends AbstractData {
	private int communityId;
	private int enemyCommunityId; //敌对公会id（-1取消敌对公会）

    public CommunitySetEnemy(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CommunitySetEnemy, sessionId, serial);
    }

    public CommunitySetEnemy() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CommunitySetEnemy);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public int getEnemyCommunityId() {
		return enemyCommunityId;
	}

	public void setEnemyCommunityId(int enemyCommunityId) {
		this.enemyCommunityId = enemyCommunityId;
	}

}
