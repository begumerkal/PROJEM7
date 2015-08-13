package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
