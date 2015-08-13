package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetSituation</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityList(获取工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendSituation extends AbstractData {
	private int	enemyCommunityId;	//敌对公会id（-1：无敌对公会）
	private String	totalSituation;	//	总战况
	private String	daySituation;	//	日战况
	private String[]    enemyNameList; //敌对公会id名称列表
	private String[]	enemySituationList;	//	敌对公会战况列表


    public SendSituation(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendSituation, sessionId, serial);
    }

    public SendSituation() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendSituation);
    }

	public int getEnemyCommunityId() {
		return enemyCommunityId;
	}

	public void setEnemyCommunityId(int enemyCommunityId) {
		this.enemyCommunityId = enemyCommunityId;
	}

	public String getTotalSituation() {
		return totalSituation;
	}

	public void setTotalSituation(String totalSituation) {
		this.totalSituation = totalSituation;
	}

	public String getDaySituation() {
		return daySituation;
	}

	public void setDaySituation(String daySituation) {
		this.daySituation = daySituation;
	}

	public String[] getEnemySituationList() {
		return enemySituationList;
	}

	public void setEnemySituationList(String[] enemySituationList) {
		this.enemySituationList = enemySituationList;
	}

	public String[] getEnemyNameList() {
		return enemyNameList;
	}

	public void setEnemyNameList(String[] enemyNameList) {
		this.enemyNameList = enemyNameList;
	}


}
