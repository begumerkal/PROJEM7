package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SetPasswordOk extends AbstractData {

	private String password;
	
    public SetPasswordOk(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SetPasswordOk, sessionId, serial);
    }

    public SetPasswordOk() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SetPasswordOk);
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
 
}
