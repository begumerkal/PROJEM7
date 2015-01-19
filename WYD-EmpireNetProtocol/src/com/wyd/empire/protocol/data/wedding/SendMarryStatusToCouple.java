package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendMarryStatusToCouple extends AbstractData {
	
	private boolean	boolIsWillingPropose;	//是否同意
	private String coupleName;
	private int marryMark;


    public SendMarryStatusToCouple(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendMarryStatusToCouple, sessionId, serial);
    }

    public SendMarryStatusToCouple() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendMarryStatusToCouple);
    }

	public boolean isBoolIsWillingPropose() {
		return boolIsWillingPropose;
	}

	public void setBoolIsWillingPropose(boolean boolIsWillingPropose) {
		this.boolIsWillingPropose = boolIsWillingPropose;
	}

	public String getCoupleName() {
		return coupleName;
	}

	public void setCoupleName(String coupleName) {
		this.coupleName = coupleName;
	}

	public int getMarryMark() {
		return marryMark;
	}

	public void setMarryMark(int marryMark) {
		this.marryMark = marryMark;
	}


}
