package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SetEnemyCommunityOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityList(获取工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CommunitySetEnemyOk extends AbstractData {
	private boolean result; //true:设置敌对公会成功；false:取消敌对公会成功

    public CommunitySetEnemyOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CommunitySetEnemyOk, sessionId, serial);
    }

    public CommunitySetEnemyOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CommunitySetEnemyOk);
    }

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
