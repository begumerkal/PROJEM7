package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendLoveLetterToCouple extends AbstractData {
	
	private int 	sendId;	 //发送人Id
	private String	sendName;	 //发送人名称
	private int 	proposeItemId;	 //信物Id
	private int 	marryMark;
	private int		timeId; //婚礼开始时间（订婚：-1，结婚是相应的ID）


    public SendLoveLetterToCouple(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendLoveLetterToCouple, sessionId, serial);
    }

    public SendLoveLetterToCouple() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendLoveLetterToCouple);
    }

	public int getSendId() {
		return sendId;
	}

	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public int getProposeItemId() {
		return proposeItemId;
	}

	public void setProposeItemId(int proposeItemId) {
		this.proposeItemId = proposeItemId;
	}

	public int getMarryMark() {
		return marryMark;
	}

	public void setMarryMark(int marryMark) {
		this.marryMark = marryMark;
	}

	public int getTimeId() {
		return timeId;
	}

	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}

}
