package com.app.empire.protocol.data.challenge;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetInBattleTeam extends AbstractData {
	private	int	battleTeamId	;//	战队ID（相当于战斗时的房间ID）
	private	int	battleMode	;//	战斗模式
	private	int	playerNumMode	;//	对战人数模式
	private	int	teamLeader	;//	战队队长（房主id）
	private	int	playerNum	;//	房间座位数量
	private	boolean[]	seatUsed	;//	该座位是否使用
	private	int[]	playerId	;//	房间内玩家id
	private	String[]	playerName	;//	房间内玩家昵称
	private	int[]	playerLevel	;//	房间内玩家等级
	private	boolean[]	playerReady	;//	玩家是否已准备
	private	int[]	playerSex	;//	玩家性别
	private	String[]	playerEquipment	;//	玩家身上的装备
	private	int[]	playerProficiency	;//	武器熟练度
	private	int[]	playerWeaponLevel	;//	玩家装备等级
	private	int[]	vipLevel	;//	玩家vip等级0表示非vip
	private	String[]	playerWing	;//	玩家身上的翅膀“null”表示未装备翅膀
	private	String[]	playerTitle	;//	玩家称号
	private	int[]	qualifyingLevel	;//	荣誉等级
	private	int[]	skillType	;//	玩家装备的武器的技能类型
	private	int[]	skillLevel	;//	玩家装备的武器的技能等级
	private	int[]	zsleve	;//	玩家转生等级
	private	boolean[]	doublemark	;//	双倍经验卡标识
	private	String	teamName	;//	战队名称（房间名称）
	private int		applyNum; //申请人数
	private boolean[] isVip; //是否是VIP

	public GetInBattleTeam(int sessionId, int serial) {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetInBattleTeam, sessionId, serial);
	}

	public GetInBattleTeam() {
		super(Protocol.MAIN_CHALLENGE, Protocol.CHALLENGE_GetInBattleTeam);
	}

	public int getBattleTeamId() {
		return battleTeamId;
	}

	public void setBattleTeamId(int battleTeamId) {
		this.battleTeamId = battleTeamId;
	}

	public int getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int battleMode) {
		this.battleMode = battleMode;
	}

	public int getPlayerNumMode() {
		return playerNumMode;
	}

	public void setPlayerNumMode(int playerNumMode) {
		this.playerNumMode = playerNumMode;
	}

	public int getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(int teamLeader) {
		this.teamLeader = teamLeader;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public boolean[] getSeatUsed() {
		return seatUsed;
	}

	public void setSeatUsed(boolean[] seatUsed) {
		this.seatUsed = seatUsed;
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

	public int[] getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int[] playerLevel) {
		this.playerLevel = playerLevel;
	}

	public boolean[] getPlayerReady() {
		return playerReady;
	}

	public void setPlayerReady(boolean[] playerReady) {
		this.playerReady = playerReady;
	}

	public int[] getPlayerSex() {
		return playerSex;
	}

	public void setPlayerSex(int[] playerSex) {
		this.playerSex = playerSex;
	}

	public String[] getPlayerEquipment() {
		return playerEquipment;
	}

	public void setPlayerEquipment(String[] playerEquipment) {
		this.playerEquipment = playerEquipment;
	}

	public int[] getPlayerProficiency() {
		return playerProficiency;
	}

	public void setPlayerProficiency(int[] playerProficiency) {
		this.playerProficiency = playerProficiency;
	}

	public int[] getPlayerWeaponLevel() {
		return playerWeaponLevel;
	}

	public void setPlayerWeaponLevel(int[] playerWeaponLevel) {
		this.playerWeaponLevel = playerWeaponLevel;
	}

	public int[] getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int[] vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String[] getPlayerWing() {
		return playerWing;
	}

	public void setPlayerWing(String[] playerWing) {
		this.playerWing = playerWing;
	}

	public String[] getPlayerTitle() {
		return playerTitle;
	}

	public void setPlayerTitle(String[] playerTitle) {
		this.playerTitle = playerTitle;
	}

	public int[] getQualifyingLevel() {
		return qualifyingLevel;
	}

	public void setQualifyingLevel(int[] qualifyingLevel) {
		this.qualifyingLevel = qualifyingLevel;
	}

	public int[] getSkillType() {
		return skillType;
	}

	public void setSkillType(int[] skillType) {
		this.skillType = skillType;
	}

	public int[] getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int[] skillLevel) {
		this.skillLevel = skillLevel;
	}

	public int[] getZsleve() {
		return zsleve;
	}

	public void setZsleve(int[] zsleve) {
		this.zsleve = zsleve;
	}

	public boolean[] getDoublemark() {
		return doublemark;
	}

	public void setDoublemark(boolean[] doublemark) {
		this.doublemark = doublemark;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public boolean[] getIsVip() {
		return isVip;
	}

	public void setIsVip(boolean[] isVip) {
		this.isVip = isVip;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}
	
}
