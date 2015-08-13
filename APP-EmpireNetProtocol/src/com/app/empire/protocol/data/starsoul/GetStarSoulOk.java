package com.app.empire.protocol.data.starsoul;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取星图列表成功
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class GetStarSoulOk extends AbstractData {
	private int         mapLeve;				   //玩家当前星图等级                                      
	private int         playerLeve;				   //玩家所需等级                                          
	private int         starLeve;				   //当前星图星点等级                                      
	private String[]    bonusAttribute;			   //星点显示的加成属性（服务端传key，客户端显示value）   
	private int[]   	bonusValue;				   //属性加成值         
	private int         consumptionGold;			   //消耗金币数                                            
	private int         goldTotalNumber;			   //玩家拥有的金币总数                                    
	private int         consumptionDebris;			   //消耗星魂碎片数                                        
	private int         debrisTotalNumber;			   //玩家拥有的星魂碎片总数    
	private int[]   	playerBonus;				//玩家自身属性
	private int[]   	coordinateX;				//X坐标
	private int[]   	coordinateY;				//Y坐标
	private int[]   	bonusIndex;				    //属性索引
	private boolean		highestLeve;           	    //是否顶级
	private int			playerId;					//玩家id

	
    public GetStarSoulOk(int sessionId, int serial) {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_GetStarSoulOk, sessionId, serial);
    }

    public GetStarSoulOk() {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_GetStarSoulOk);
    }

	public int getMapLeve() {
		return mapLeve;
	}

	public void setMapLeve(int mapLeve) {
		this.mapLeve = mapLeve;
	}

	public int getPlayerLeve() {
		return playerLeve;
	}

	public void setPlayerLeve(int playerLeve) {
		this.playerLeve = playerLeve;
	}

	public int getStarLeve() {
		return starLeve;
	}

	public void setStarLeve(int starLeve) {
		this.starLeve = starLeve;
	}

	public String[] getBonusAttribute() {
		return bonusAttribute;
	}

	public void setBonusAttribute(String[] bonusAttribute) {
		this.bonusAttribute = bonusAttribute;
	}


	public int[] getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(int[] bonusValue) {
		this.bonusValue = bonusValue;
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

	public int[] getPlayerBonus() {
		return playerBonus;
	}

	public void setPlayerBonus(int[] playerBonus) {
		this.playerBonus = playerBonus;
	}

	public int[] getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int[] coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int[] getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int[] coordinateY) {
		this.coordinateY = coordinateY;
	}

	public int[] getBonusIndex() {
		return bonusIndex;
	}

	public void setBonusIndex(int[] bonusIndex) {
		this.bonusIndex = bonusIndex;
	}

	public boolean getHighestLeve() {
		return highestLeve;
	}

	public void setHighestLeve(boolean highestLeve) {
		this.highestLeve = highestLeve;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
    
	
    

}
