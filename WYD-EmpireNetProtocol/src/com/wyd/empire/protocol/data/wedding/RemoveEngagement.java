package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class RemoveEngagement extends AbstractData {
	
//	private int 	marryMark;	 //婚姻状况标识(0是订婚，1是结婚)


    public RemoveEngagement(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RemoveEngagement, sessionId, serial);
    }

    public RemoveEngagement() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RemoveEngagement);
    }

//	public int getMarryMark() {
//		return marryMark;
//	}
//
//	public void setMarryMark(int marryMark) {
//		this.marryMark = marryMark;
//	}

}
