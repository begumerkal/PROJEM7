package com.wyd.empire.protocol.data.practice;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 激活修炼成功
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class ActivatePracticeOk extends AbstractData {
	private	int			lowMedalnumber;		//玩家拥有的低级勋章总数
	private	int			seniorMedlNumber;	//玩家拥有的高级勋章总数     
//	private	boolean		status;				//是否激活
	
    public ActivatePracticeOk(int sessionId, int serial) {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_ActivatePracticeOk, sessionId, serial);
    }

    public ActivatePracticeOk() {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_ActivatePracticeOk);
    }

	public int getLowMedalnumber() {
		return lowMedalnumber;
	}

	public void setLowMedalnumber(int lowMedalnumber) {
		this.lowMedalnumber = lowMedalnumber;
	}

	public int getSeniorMedlNumber() {
		return seniorMedlNumber;
	}

	public void setSeniorMedlNumber(int seniorMedlNumber) {
		this.seniorMedlNumber = seniorMedlNumber;
	}

//	public boolean getStatus() {
//		return status;
//	}
//
//	public void setStatus(boolean status) {
//		this.status = status;
//	}

	
    
    

}
