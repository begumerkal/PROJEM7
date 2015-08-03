package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetSkillList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetSkillList(获取技能列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetTopRecordOk extends AbstractData {
	private int[] ranking;//名次
	private String[] name;//昵称
	private int[] amount;//数量
	private int[] playerId;//玩家ID
	private int[] trendRank;///趋势排行

    public GetTopRecordOk(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetTopRecordOk, sessionId, serial);
    }

    public GetTopRecordOk() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetTopRecordOk);
    }

	public int[] getRanking() {
		return ranking;
	}

	public void setRanking(int[] ranking) {
		this.ranking = ranking;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public int[] getAmount() {
		return amount;
	}

	public void setAmount(int[] amount) {
		this.amount = amount;
	}

	public int[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int[] playerId) {
		this.playerId = playerId;
	}

	public int[] getTrendRank() {
		return trendRank;
	}

	public void setTrendRank(int[] trendRank) {
		this.trendRank = trendRank;
	}


	
}
