package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetLoveLetterInfo extends AbstractData {
	
	private int 	marryMailId;	 //要获取的求婚信Id


    public GetLoveLetterInfo(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetLoveLetterInfo, sessionId, serial);
    }

    public GetLoveLetterInfo() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetLoveLetterInfo);
    }

	public int getMarryMailId() {
		return marryMailId;
	}

	public void setMarryMailId(int marryMailId) {
		this.marryMailId = marryMailId;
	}

}
