package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class JoinWeddingOk extends AbstractData {

	private	String	manName;	//新郎名称
	private	String	womanName;	//新娘名称
	private	String[]	manIcon;	//新郎名称
	private	String[]	womanIcon;	//新娘名称
	private	int[]	playerId;	//来宾玩家id
	private	String[]	playerName;	//来宾玩家昵称
	private	String[]	playerEquipment;	//来宾身上的装备
	private int			rewardNum;			//红包数
	private int			blessingNum;		//拥有礼炮数
	private int			rewardPrice;		//红包单价
	private int			wedType;			//结婚类型
	private int			rewardLowNum;		//红包底数
	private int			rewardHighNum;		//红包最高数
	private String		wedNum;				//婚礼标号
	private String[]	priestSay;			//牧师语言
	private boolean[]	sex;				//性别
	private boolean		fristEnter;			//是否第一次进入
	private	int	 randomClothes;			//来宾随机衣服（1或者2，有客户端安排衣服）


    public JoinWeddingOk(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_JoinWeddingOk , sessionId, serial);
    }

    public JoinWeddingOk() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_JoinWeddingOk );
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

	public int[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int[] playerId) {
		this.playerId = playerId;
	}

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public String[] getPlayerEquipment() {
		return playerEquipment;
	}

	public void setPlayerEquipment(String[] playerEquipment) {
		this.playerEquipment = playerEquipment;
	}

	public int getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}

	public int getBlessingNum() {
		return blessingNum;
	}

	public void setBlessingNum(int blessingNum) {
		this.blessingNum = blessingNum;
	}

	public int getRewardPrice() {
		return rewardPrice;
	}

	public void setRewardPrice(int rewardPrice) {
		this.rewardPrice = rewardPrice;
	}

	public int getWedType() {
		return wedType;
	}

	public void setWedType(int wedType) {
		this.wedType = wedType;
	}

	public int getRewardLowNum() {
		return rewardLowNum;
	}

	public void setRewardLowNum(int rewardLowNum) {
		this.rewardLowNum = rewardLowNum;
	}

	public int getRewardHighNum() {
		return rewardHighNum;
	}

	public void setRewardHighNum(int rewardHighNum) {
		this.rewardHighNum = rewardHighNum;
	}

	public String getWedNum() {
		return wedNum;
	}

	public void setWedNum(String wedNum) {
		this.wedNum = wedNum;
	}

	public String[] getPriestSay() {
		return priestSay;
	}

	public void setPriestSay(String[] priestSay) {
		this.priestSay = priestSay;
	}

	public boolean[] getSex() {
		return sex;
	}

	public void setSex(boolean[] sex) {
		this.sex = sex;
	}

	public boolean isFristEnter() {
		return fristEnter;
	}

	public void setFristEnter(boolean fristEnter) {
		this.fristEnter = fristEnter;
	}

	public int getRandomClothes() {
		return randomClothes;
	}

	public void setRandomClothes(int randomClothes) {
		this.randomClothes = randomClothes;
	}


}
