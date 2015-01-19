package com.wyd.empire.protocol.data.fund;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

/**
 * 领取基金协议
 * @author sunzx
 *
 */
public class GetFundDetailOk extends AbstractData {
	
	private int[]	lowLevel;
	private int[]	lowValue;
	private int[]	midLevel;
	private int[]	midValue;
	private int[]	highLevel;
	private int[]	highValue;

    
    public GetFundDetailOk(int sessionId, int serial) {
        super(Protocol.MAIN_FUND, Protocol.FUND_GetFundDetailOk, sessionId, serial);
    }

    public GetFundDetailOk() {
        super(Protocol.MAIN_FUND, Protocol.FUND_GetFundDetailOk);
    }

	public int[] getLowLevel() {
		return lowLevel;
	}

	public void setLowLevel(int[] lowLevel) {
		this.lowLevel = lowLevel;
	}

	public int[] getLowValue() {
		return lowValue;
	}

	public void setLowValue(int[] lowValue) {
		this.lowValue = lowValue;
	}

	public int[] getMidLevel() {
		return midLevel;
	}

	public void setMidLevel(int[] midLevel) {
		this.midLevel = midLevel;
	}

	public int[] getMidValue() {
		return midValue;
	}

	public void setMidValue(int[] midValue) {
		this.midValue = midValue;
	}

	public int[] getHighLevel() {
		return highLevel;
	}

	public void setHighLevel(int[] highLevel) {
		this.highLevel = highLevel;
	}

	public int[] getHighValue() {
		return highValue;
	}

	public void setHighValue(int[] highValue) {
		this.highValue = highValue;
	}
    
}
