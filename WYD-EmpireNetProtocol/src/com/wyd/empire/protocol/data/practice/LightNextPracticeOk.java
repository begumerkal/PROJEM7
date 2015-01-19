package com.wyd.empire.protocol.data.practice;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 属性修炼消耗勋章添加对应属性的经验值成功
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class LightNextPracticeOk extends AbstractData {
	private int	lowMedalnumber;		//玩家拥有的第级勋章总数
	private int	seniorMedlNumber;	//玩家拥有的高级勋章总数
	private int	useTodayNumber;		//今日还可以使用勋章数
	private int	playerBonusExp;		//玩家自身属性exp
	private boolean	upgrade;		//是否升级
//	private	boolean		status;				//是否激活

	
    public LightNextPracticeOk(int sessionId, int serial) {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_LightNextPracticeOk, sessionId, serial);
    }

    public LightNextPracticeOk() {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_LightNextPracticeOk);
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

	public int getUseTodayNumber() {
		return useTodayNumber;
	}

	public void setUseTodayNumber(int useTodayNumber) {
		this.useTodayNumber = useTodayNumber;
	}

	public int getPlayerBonusExp() {
		return playerBonusExp;
	}

	public void setPlayerBonusExp(int playerBonusExp) {
		this.playerBonusExp = playerBonusExp;
	}


	public boolean getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}

//	public boolean getStatus() {
//		return status;
//	}
//
//	public void setStatus(boolean status) {
//		this.status = status;
//	}


	

	
    
    

}
