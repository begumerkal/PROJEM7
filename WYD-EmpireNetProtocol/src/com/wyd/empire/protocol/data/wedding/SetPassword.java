package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SetPassword extends AbstractData {
	private boolean usePassword; //是否使用密码
	private String  password;		//密码
	private String  wedNum;			//婚礼编号

    public SetPassword(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SetPassword, sessionId, serial);
    }

    public SetPassword() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SetPassword);
    }

	public boolean getUsePassword() {
		return usePassword;
	}

	public void setUsePassword(boolean usePassword) {
		this.usePassword = usePassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}
 
}
