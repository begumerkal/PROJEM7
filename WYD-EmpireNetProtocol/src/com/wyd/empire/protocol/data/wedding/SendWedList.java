package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendWedList extends AbstractData {

	private	String[]	wedNum;			//婚礼编号
	private	int[]		wedStatus;		//婚礼状态
	private	int[]		wedType;		//婚礼类型
	private	String[]	manName;		//新郎名称
	private	String[]	womanName;		//新娘名称
	private	String[]	startTime;		//开始时间
	private	String[]	wedTime;		//婚礼时间
	private	String[]	manIcon;		//新郎装扮（只有头和脸）
	private	String[]	womanIcon;		//新娘装扮（只有头和脸）
	private	boolean[]	usePassword; 	//新娘装扮（只有头和脸）
	
    public SendWedList(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendWedList, sessionId, serial);
    }

    public SendWedList() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendWedList);
    }

	public String[] getWedNum() {
		return wedNum;
	}

	public void setWedNum(String[] wedNum) {
		this.wedNum = wedNum;
	}

	public int[] getWedStatus() {
		return wedStatus;
	}

	public void setWedStatus(int[] wedStatus) {
		this.wedStatus = wedStatus;
	}

	public int[] getWedType() {
		return wedType;
	}

	public void setWedType(int[] wedType) {
		this.wedType = wedType;
	}

	public String[] getManName() {
		return manName;
	}

	public void setManName(String[] manName) {
		this.manName = manName;
	}

	public String[] getWomanName() {
		return womanName;
	}

	public void setWomanName(String[] womanName) {
		this.womanName = womanName;
	}

	public String[] getStartTime() {
		return startTime;
	}

	public void setStartTime(String[] startTime) {
		this.startTime = startTime;
	}

	public String[] getWedTime() {
		return wedTime;
	}

	public void setWedTime(String[] wedTime) {
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

	public boolean[] getUsePassword() {
		return usePassword;
	}

	public void setUsePassword(boolean[] usePassword) {
		this.usePassword = usePassword;
	}
	
	

}
