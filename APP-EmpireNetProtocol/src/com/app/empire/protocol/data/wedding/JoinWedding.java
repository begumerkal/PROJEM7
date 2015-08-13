package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class JoinWedding extends AbstractData {
	private String wedNum;   //婚礼编号
	private String password; //房间密码

    public JoinWedding(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_JoinWedding, sessionId, serial);
    }

    public JoinWedding() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_JoinWedding);
    }

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
