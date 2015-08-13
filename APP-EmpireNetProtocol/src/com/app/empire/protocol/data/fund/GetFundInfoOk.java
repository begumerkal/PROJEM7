package com.app.empire.protocol.data.fund;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

/**
 * 获取基金信息成功
 * @author sunzx
 *
 */
public class GetFundInfoOk extends AbstractData {
    private int[]   lowValue;           // 初级基金投入收获数值
    private int[]   middleValue;        // 中级基金投入收获数值
    private int[]   highValue;          // 高级基金投入收获数值
	private boolean lowFundStatus;		// 初级基金购买按钮状态
	private boolean middleFundStatus;	// 中级基金购买按钮状态
	private boolean highFundStatus;		// 高级基金购买按钮状态
	private boolean receiveStatus;		// 领取基金返利按钮状态
	private int     nextReceiveLevel;	// 下次领取等级
	private int     nextReceiveMoney;	// 下次领取金额
	private int     pileReceiveMoney;	// 累积领取金额
	private int     currentReceiveMoney;// 当前可领取金额
	private int    	levelLimit;		    // 等级限制
	private int		status;				// 基金状态（0是未购买，1是已购买未到领取时间，2是可以领取）
	
    public GetFundInfoOk(int sessionId, int serial) {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundInfoOk, sessionId, serial);
	}

	public GetFundInfoOk() {
		super(Protocol.MAIN_FUND, Protocol.FUND_GetFundInfoOk);
	}
	
	public int[] getLowValue() {
        return lowValue;
    }

    public void setLowValue(int[] lowValue) {
        this.lowValue = lowValue;
    }

    public int[] getMiddleValue() {
        return middleValue;
    }

    public void setMiddleValue(int[] middleValue) {
        this.middleValue = middleValue;
    }

    public int[] getHighValue() {
        return highValue;
    }

    public void setHighValue(int[] highValue) {
        this.highValue = highValue;
    }

    public boolean isLowFundStatus() {
		return lowFundStatus;
	}

	public void setLowFundStatus(boolean lowFundStatus) {
		this.lowFundStatus = lowFundStatus;
	}

	public boolean isMiddleFundStatus() {
		return middleFundStatus;
	}

	public void setMiddleFundStatus(boolean middleFundStatus) {
		this.middleFundStatus = middleFundStatus;
	}

	public boolean isHighFundStatus() {
		return highFundStatus;
	}

	public void setHighFundStatus(boolean highFundStatus) {
		this.highFundStatus = highFundStatus;
	}

	public boolean isReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(boolean receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public int getNextReceiveLevel() {
		return nextReceiveLevel;
	}

	public void setNextReceiveLevel(int nextReceiveLevel) {
		this.nextReceiveLevel = nextReceiveLevel;
	}

	public int getNextReceiveMoney() {
		return nextReceiveMoney;
	}

	public void setNextReceiveMoney(int nextReceiveMoney) {
		this.nextReceiveMoney = nextReceiveMoney;
	}

	public int getPileReceiveMoney() {
		return pileReceiveMoney;
	}

	public void setPileReceiveMoney(int pileReceiveMoney) {
		this.pileReceiveMoney = pileReceiveMoney;
	}

	public int getCurrentReceiveMoney() {
		return currentReceiveMoney;
	}

	public void setCurrentReceiveMoney(int currentReceiveMoney) {
		this.currentReceiveMoney = currentReceiveMoney;
	}
	
	public int getLevelLimit() {
        return levelLimit;
    }

    public void setLevelLimit(int levelLimit) {
        this.levelLimit = levelLimit;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
}
