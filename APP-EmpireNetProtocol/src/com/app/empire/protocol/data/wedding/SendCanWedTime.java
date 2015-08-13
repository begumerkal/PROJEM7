package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendCanWedTime extends AbstractData {

	private	int[]	timeId;		//可举办时间的编号
	private	String[]	startTime;		//开始时间
	private	String[]	endTime;		//结束时间

    public SendCanWedTime(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendCanWedTime, sessionId, serial);
    }

    public SendCanWedTime() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendCanWedTime);
    }

	public int[] getTimeId() {
		return timeId;
	}

	public void setTimeId(int[] timeId) {
		this.timeId = timeId;
	}

	public String[] getStartTime() {
		return startTime;
	}

	public void setStartTime(String[] startTime) {
		this.startTime = startTime;
	}

	public String[] getEndTime() {
		return endTime;
	}

	public void setEndTime(String[] endTime) {
		this.endTime = endTime;
	}

}
