package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendPeopleInfo extends AbstractData {

	private	String	manName;	//新郎名称
	private	String	womanName;	//新娘名称
	private	String	wedTime;	//婚礼时间
	private	String[]	manIcon;	//新郎装扮（只有头和脸）
	private	String[]	womanIcon;	//新娘装扮（只有头和脸）

    public SendPeopleInfo(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendPeopleInfo, sessionId, serial);
    }

    public SendPeopleInfo() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendPeopleInfo);
    }

	public String getManName() {
		return manName;
	}

	public void setManName(String manName) {
		this.manName = manName;
	}

	public String getWomanName() {
		return womanName;
	}

	public void setWomanName(String womanName) {
		this.womanName = womanName;
	}

	public String getWedTime() {
		return wedTime;
	}

	public void setWedTime(String wedTime) {
		this.wedTime = wedTime;
	}

	public String[] getManIcon() {
		return manIcon;
	}

	public void setManIcon(String[] manIcon) {
		this.manIcon = manIcon;
	}

	public String[] getWomanIcon() {
		return womanIcon;
	}

	public void setWomanIcon(String[] womanIcon) {
		this.womanIcon = womanIcon;
	}

}
