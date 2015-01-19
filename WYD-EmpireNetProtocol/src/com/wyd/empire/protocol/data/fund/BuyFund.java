package com.wyd.empire.protocol.data.fund;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

/**
 * 购买基金
 * @author sunzx
 *
 */
public class BuyFund extends AbstractData {
    private int fundType;

    public BuyFund(int sessionId, int serial) {
        super(Protocol.MAIN_FUND, Protocol.FUND_BuyFund, sessionId, serial);
    }

    public BuyFund() {
        super(Protocol.MAIN_FUND, Protocol.FUND_BuyFund);
    }

    public int getFundType() {
        return fundType;
    }

    public void setFundType(int fundType) {
        this.fundType = fundType;
    }
}
