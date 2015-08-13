package com.app.empire.protocol.data.worldbosshall;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 发送结算信息
 * @author zengxc
 *
 */
public class SendSettlementInfo extends AbstractData {
	private int hurtValue;          // 总伤害输出
	private int hurtRank;           // 输出排名
	private int hurtPercent;        // 伤害所占百分比(万分比)
	private boolean lastKillGift; // 是否获得击杀礼包
	private boolean win;          // 是否赢了
	public int getHurtValue() {
		return hurtValue;
	}
	public void setHurtValue(int hurtValue) {
		this.hurtValue = hurtValue;
	}
	public int getHurtRank() {
		return hurtRank;
	}
	public void setHurtRank(int hurtRank) {
		this.hurtRank = hurtRank;
	}
	public int getHurtPercent() {
		return hurtPercent;
	}
	public void setHurtPercent(int hurtPercent) {
		this.hurtPercent = hurtPercent;
	}
	public boolean isLastKillGift() {
		return lastKillGift;
	}
	public void setLastKillGift(boolean lastKillGift) {
		this.lastKillGift = lastKillGift;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public SendSettlementInfo(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_SendSettlementInfo, sessionId, serial);
    }
	public SendSettlementInfo(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_SendSettlementInfo);
	}
	
}
