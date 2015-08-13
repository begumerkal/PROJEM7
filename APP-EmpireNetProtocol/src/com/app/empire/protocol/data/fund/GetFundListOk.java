package com.app.empire.protocol.data.fund;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

/**
 * 获取基金信息成功
 * @author sunzx
 *
 */
public class GetFundListOk extends AbstractData {
	private String[]   lowLevel;
    private String[]   lowValue;           // 初级基金投入收获数值
    private String[]   middleLevel;
    private String[]   middleValue;        // 中级基金投入收获数值
    private String[]   highLevel;
    private String[]   highValue;          // 高级基金投入收获数值
	
    public GetFundListOk(int sessionId, int serial) {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundListOk, sessionId, serial);
	}

	public GetFundListOk() {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundListOk);
	}

	public String[] getLowLevel() {
		return lowLevel;
	}

	public void setLowLevel(String[] lowLevel) {
		this.lowLevel = lowLevel;
	}

	public String[] getLowValue() {
		return lowValue;
	}

	public void setLowValue(String[] lowValue) {
		this.lowValue = lowValue;
	}

	public String[] getMiddleLevel() {
		return middleLevel;
	}

	public void setMiddleLevel(String[] middleLevel) {
		this.middleLevel = middleLevel;
	}

	public String[] getMiddleValue() {
		return middleValue;
	}

	public void setMiddleValue(String[] middleValue) {
		this.middleValue = middleValue;
	}

	public String[] getHighLevel() {
		return highLevel;
	}

	public void setHighLevel(String[] highLevel) {
		this.highLevel = highLevel;
	}

	public String[] getHighValue() {
		return highValue;
	}

	public void setHighValue(String[] highValue) {
		this.highValue = highValue;
	}
	
}
