package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ChangeMarryStatus extends AbstractData {
	
	private int	coupleId;	//伴侣的Id
	private int 	marryMark;	//婚姻状况标识(0是订婚，1是结婚)
	private boolean	boolIsWillingPropose;	//是否同意
	private int		timeId; //婚礼开始时间（订婚：-1，结婚是相应的ID）

    public ChangeMarryStatus(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_ChangeMarryStatus, sessionId, serial);
    }

    public ChangeMarryStatus() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_ChangeMarryStatus);
    }

	public int getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(int coupleId) {
		this.coupleId = coupleId;
	}

	public int getMarryMark() {
		return marryMark;
	}

	public void setMarryMark(int marryMark) {
		this.marryMark = marryMark;
	}

	public boolean isBoolIsWillingPropose() {
		return boolIsWillingPropose;
	}

	public void setBoolIsWillingPropose(boolean boolIsWillingPropose) {
		this.boolIsWillingPropose = boolIsWillingPropose;
	}

	public int getTimeId() {
		return timeId;
	}

	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}


}
