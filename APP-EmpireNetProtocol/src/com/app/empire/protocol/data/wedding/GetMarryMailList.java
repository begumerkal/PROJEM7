package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetMarryMailList extends AbstractData {
	
	private int[]	marryMailId;	//求婚信的Id
	private String[]	marryMailInfo;	//求婚信的简述
	private String[]	sendTime;	//发送时间
	private String detail;


    public GetMarryMailList(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetMarryMailList, sessionId, serial);
    }

    public GetMarryMailList() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetMarryMailList);
    }

	public int[] getMarryMailId() {
		return marryMailId;
	}

	public void setMarryMailId(int[] marryMailId) {
		this.marryMailId = marryMailId;
	}

	public String[] getMarryMailInfo() {
		return marryMailInfo;
	}

	public void setMarryMailInfo(String[] marryMailInfo) {
		this.marryMailInfo = marryMailInfo;
	}

	public String[] getSendTime() {
		return sendTime;
	}

	public void setSendTime(String[] sendTime) {
		this.sendTime = sendTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
