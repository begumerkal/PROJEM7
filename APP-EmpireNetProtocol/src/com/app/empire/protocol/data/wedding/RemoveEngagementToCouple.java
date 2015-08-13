package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class RemoveEngagementToCouple extends AbstractData {
	
	private String coupleName;
	private int marryMark;

    public RemoveEngagementToCouple(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RemoveEngagementToCouple, sessionId, serial);
    }

    public RemoveEngagementToCouple() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RemoveEngagementToCouple);
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
