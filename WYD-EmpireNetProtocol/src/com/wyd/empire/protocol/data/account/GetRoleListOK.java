package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class GetRoleListOK extends AbstractData {
	private int playerCount; // 角色数量
	private String[] playerName; // 角色名称
	private int[] playerLevel; // 玩家等级
	private int[] playerSex; // 玩家性别0男1女
	private int[] playerDiamond; // 玩家蓝钻数量
	private int[] playerGold; // 玩家金币数量
	private int[] zsLevel; // 玩家转生等级
	private boolean[] doubleCard; // 双倍经验卡
	private int[] vipLevel; // vip等级
	private int[] playerRank; // 玩家军衔
	private String[] headMessage; // 头部装备信息
	private String[] faceMessage; // 脸部装备信息
	private String[] bodyMessage; // 身体装备信息
	private String[] weapMessage; // 武器信息
	private String[] wingMessage; // 翅膀装备信息
	private String[] petMessage; // 宠物信息
	private int[] weapProf; // 武器熟练度
	private byte[] weapLevel; // 武器熟练度
	private byte[] weapSkillType; // 武器熟练度

	public GetRoleListOK(int sessionId, int serial) {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetRoleListOK, sessionId, serial);
	}

	public GetRoleListOK() {
		super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetRoleListOK);
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public int[] getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int[] playerLevel) {
		this.playerLevel = playerLevel;
	}

	public int[] getPlayerSex() {
		return playerSex;
	}

	public void setPlayerSex(int[] playerSex) {
		this.playerSex = playerSex;
	}

	public int[] getPlayerDiamond() {
		return playerDiamond;
	}

	public void setPlayerDiamond(int[] playerDiamond) {
		this.playerDiamond = playerDiamond;
	}

	public int[] getPlayerGold() {
		return playerGold;
	}

	public void setPlayerGold(int[] playerGold) {
		this.playerGold = playerGold;
	}

	public int[] getZsLevel() {
		return zsLevel;
	}

	public void setZsLevel(int[] zsLevel) {
		this.zsLevel = zsLevel;
	}

	public boolean[] getDoubleCard() {
		return doubleCard;
	}

	public void setDoubleCard(boolean[] doubleCard) {
		this.doubleCard = doubleCard;
	}

	public int[] getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int[] vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int[] getPlayerRank() {
		return playerRank;
	}

	public void setPlayerRank(int[] playerRank) {
		this.playerRank = playerRank;
	}

	public String[] getHeadMessage() {
		return headMessage;
	}

	public void setHeadMessage(String[] headMessage) {
		this.headMessage = headMessage;
	}

	public String[] getFaceMessage() {
		return faceMessage;
	}

	public void setFaceMessage(String[] faceMessage) {
		this.faceMessage = faceMessage;
	}

	public String[] getBodyMessage() {
		return bodyMessage;
	}

	public void setBodyMessage(String[] bodyMessage) {
		this.bodyMessage = bodyMessage;
	}

	public String[] getWeapMessage() {
		return weapMessage;
	}

	public void setWeapMessage(String[] weapMessage) {
		this.weapMessage = weapMessage;
	}

	public String[] getWingMessage() {
		return wingMessage;
	}

	public void setWingMessage(String[] wingMessage) {
		this.wingMessage = wingMessage;
	}

	public String[] getPetMessage() {
		return petMessage;
	}

	public void setPetMessage(String[] petMessage) {
		this.petMessage = petMessage;
	}

	public int[] getWeapProf() {
		return weapProf;
	}

	public void setWeapProf(int[] weapProf) {
		this.weapProf = weapProf;
	}

	public byte[] getWeapLevel() {
		return weapLevel;
	}

	public void setWeapLevel(byte[] weapLevel) {
		this.weapLevel = weapLevel;
	}

	public byte[] getWeapSkillType() {
		return weapSkillType;
	}

	public void setWeapSkillType(byte[] weapSkillType) {
		this.weapSkillType = weapSkillType;
	}
}
