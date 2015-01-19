package com.wyd.empire.protocol.data.starsoul;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 点亮下一个星点成功
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class LightNextStarOk extends AbstractData {
	private int		  consumptionGold;       	    //消耗金币数                    
	private int		  goldTotalNumber;       	    //玩家拥有的金币总数            
	private int		  consumptionDebris;     	    //消耗星魂碎片数                
	private int		  debrisTotalNumber;     	    //玩家拥有的星魂碎片总数        
	private boolean		  highestLeve;              //是否最高等级                       
	private boolean	  upgrade;               	    //是否升级                

	
    public LightNextStarOk(int sessionId, int serial) {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_LightNextStarOk, sessionId, serial);
    }

    public LightNextStarOk() {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_LightNextStarOk);
    }

	public int getConsumptionGold() {
		return consumptionGold;
	}

	public void setConsumptionGold(int consumptionGold) {
		this.consumptionGold = consumptionGold;
	}

	public int getGoldTotalNumber() {
		return goldTotalNumber;
	}

	public void setGoldTotalNumber(int goldTotalNumber) {
		this.goldTotalNumber = goldTotalNumber;
	}

	public int getConsumptionDebris() {
		return consumptionDebris;
	}

	public void setConsumptionDebris(int consumptionDebris) {
		this.consumptionDebris = consumptionDebris;
	}

	public int getDebrisTotalNumber() {
		return debrisTotalNumber;
	}

	public void setDebrisTotalNumber(int debrisTotalNumber) {
		this.debrisTotalNumber = debrisTotalNumber;
	}

	public boolean getHighestLeve() {
		return highestLeve;
	}

	public void setHighestLeve(boolean highestLeve) {
		this.highestLeve = highestLeve;
	}

	public boolean getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}

	
    
    

}
