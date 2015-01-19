package com.wyd.empire.protocol.data.practice;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 获取修炼列表成功
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class GetPracticeOk extends AbstractData {
	private	String[]	bonusAttribute;		//修炼显示的加成属性（服务端传key，客户端显示value）
	private	int[]		bonusIndex;			//属性索引
	private	int			bonusLeve;			//玩家修炼属性对应的等级
	private	int[]		playerBonusExp;		//玩家自身属性exp
	private	int[]		playerBonus;		//玩家自身属性加成值
	private	int[]		bonusValue;			//属性下一级别等级加成值
	private	int			playerLeve;			//玩家所需等级
	private	int			canLightLeve;		//玩家可以点亮最高等级
	private	int			needExp;			//当前等级需要总的经验（进度条中的总数）
	private	int			lowMedalNumber;		//玩家拥有的低级勋章总数
	private	int			seniorMedlNumber;	//玩家拥有的高级勋章总数
	private	int			useLimitNumber;		//使用勋章上限数
	private	int			useTodayNumber;		//今日还可以使用勋章数
//	private	boolean		status;				//是否激活
	private	int			consumeMedalNumber;	//当前等级激活每日扣除勋章数
	
    public GetPracticeOk(int sessionId, int serial) {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_GetPracticeOk, sessionId, serial);
    }

    public GetPracticeOk() {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_GetPracticeOk);
    }

	public String[] getBonusAttribute() {
		return bonusAttribute;
	}

	public void setBonusAttribute(String[] bonusAttribute) {
		this.bonusAttribute = bonusAttribute;
	}

	public int[] getBonusIndex() {
		return bonusIndex;
	}

	public void setBonusIndex(int[] bonusIndex) {
		this.bonusIndex = bonusIndex;
	}

	public int getBonusLeve() {
		return bonusLeve;
	}

	public void setBonusLeve(int bonusLeve) {
		this.bonusLeve = bonusLeve;
	}

	public int[] getPlayerBonusExp() {
		return playerBonusExp;
	}

	public void setPlayerBonusExp(int[] playerBonusExp) {
		this.playerBonusExp = playerBonusExp;
	}

	public int[] getPlayerBonus() {
		return playerBonus;
	}

	public void setPlayerBonus(int[] playerBonus) {
		this.playerBonus = playerBonus;
	}

	public int[] getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(int[] bonusValue) {
		this.bonusValue = bonusValue;
	}

	public int getPlayerLeve() {
		return playerLeve;
	}

	public void setPlayerLeve(int playerLeve) {
		this.playerLeve = playerLeve;
	}

	public int getCanLightLeve() {
		return canLightLeve;
	}

	public void setCanLightLeve(int canLightLeve) {
		this.canLightLeve = canLightLeve;
	}

	public int getNeedExp() {
		return needExp;
	}

	public void setNeedExp(int needExp) {
		this.needExp = needExp;
	}



	public int getLowMedalNumber() {
		return lowMedalNumber;
	}

	public void setLowMedalNumber(int lowMedalNumber) {
		this.lowMedalNumber = lowMedalNumber;
	}

	public int getSeniorMedlNumber() {
		return seniorMedlNumber;
	}

	public void setSeniorMedlNumber(int seniorMedlNumber) {
		this.seniorMedlNumber = seniorMedlNumber;
	}

	public int getUseLimitNumber() {
		return useLimitNumber;
	}

	public void setUseLimitNumber(int useLimitNumber) {
		this.useLimitNumber = useLimitNumber;
	}

	public int getUseTodayNumber() {
		return useTodayNumber;
	}

	public void setUseTodayNumber(int useTodayNumber) {
		this.useTodayNumber = useTodayNumber;
	}

//	public boolean getStatus() {
//		return status;
//	}
//
//	public void setStatus(boolean status) {
//		this.status = status;
//	}

	public int getConsumeMedalNumber() {
		return consumeMedalNumber;
	}

	public void setConsumeMedalNumber(int consumeMedalNumber) {
		this.consumeMedalNumber = consumeMedalNumber;
	}

    
	
    

}
