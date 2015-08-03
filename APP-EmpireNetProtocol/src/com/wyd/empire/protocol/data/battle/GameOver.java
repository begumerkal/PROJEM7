package com.wyd.empire.protocol.data.battle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GameOver extends AbstractData {
	private int battleId;
	private int playerId;
	private int firstHurtPlayerId;
	private int winCamp;
	private int playerCount;
	private int[] playerIds;
	private int[] shootRate;
	private int[] totalHurt;
	private int[] killCount;
	private int[] beKilledCount;
	private int[] addExp;
	private int[] exp;
	private int[] upgradeExp;
	private int[] nextUpgradeExp;
	private int[] itemId;
	private int cardCount;
	private int[] card_shopItemId;
	private String[] card_Name;
	private String[] item_icon;
	private int[] card_num;
	private int[] integral;
	private int battleMode;
	private int playerNumMode;
	private int[] guildAddExp;
	private int[] isMarry;
	private int[] isDoubleExpCard;
	private int[] activityAddExp;
	
	
	
	public GameOver(int sessionId, int serial) {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GameOver, sessionId, serial);
	}

	public GameOver() {
		super(Protocol.MAIN_BATTLE, Protocol.BATTLE_GameOver);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getFirstHurtPlayerId() {
		return firstHurtPlayerId;
	}

	public void setFirstHurtPlayerId(int firstHurtPlayerId) {
		this.firstHurtPlayerId = firstHurtPlayerId;
	}

	public int getWinCamp() {
		return winCamp;
	}

	public void setWinCamp(int winCamp) {
		this.winCamp = winCamp;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int[] getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(int[] playerIds) {
		this.playerIds = playerIds;
	}

	public int[] getShootRate() {
		return shootRate;
	}

	public void setShootRate(int[] shootRate) {
		this.shootRate = shootRate;
	}

	public int[] getTotalHurt() {
		return totalHurt;
	}

	public void setTotalHurt(int[] totalHurt) {
		this.totalHurt = totalHurt;
	}

	public int[] getKillCount() {
		return killCount;
	}

	public void setKillCount(int[] killCount) {
		this.killCount = killCount;
	}

	public int[] getBeKilledCount() {
		return beKilledCount;
	}

	public void setBeKilledCount(int[] beKilledCount) {
		this.beKilledCount = beKilledCount;
	}

	public int[] getAddExp() {
		return addExp;
	}

	public void setAddExp(int[] addExp) {
		this.addExp = addExp;
	}

	public int[] getExp() {
		return exp;
	}

	public void setExp(int[] exp) {
		this.exp = exp;
	}

	public int[] getUpgradeExp() {
		return upgradeExp;
	}

	public void setUpgradeExp(int[] upgradeExp) {
		this.upgradeExp = upgradeExp;
	}

	public int[] getNextUpgradeExp() {
		return nextUpgradeExp;
	}

	public void setNextUpgradeExp(int[] nextUpgradeExp) {
		this.nextUpgradeExp = nextUpgradeExp;
	}

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int getCardCount() {
		return cardCount;
	}

	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}

	public int[] getCard_shopItemId() {
		return card_shopItemId;
	}

	public void setCard_shopItemId(int[] card_shopItemId) {
		this.card_shopItemId = card_shopItemId;
	}

	public String[] getCard_Name() {
		return card_Name;
	}

	public void setCard_Name(String[] card_Name) {
		this.card_Name = card_Name;
	}

	public String[] getItem_icon() {
		return item_icon;
	}

	public void setItem_icon(String[] item_icon) {
		this.item_icon = item_icon;
	}

	public int[] getCard_num() {
		return card_num;
	}

	public void setCard_num(int[] card_num) {
		this.card_num = card_num;
	}

	public int[] getIntegral() {
		return integral;
	}

	public void setIntegral(int[] integral) {
		this.integral = integral;
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

	public int[] getGuildAddExp() {
		return guildAddExp;
	}

	public void setGuildAddExp(int[] guildAddExp) {
		this.guildAddExp = guildAddExp;
	}

	public int[] getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(int[] isMarry) {
		this.isMarry = isMarry;
	}

	public int[] getIsDoubleExpCard() {
		return isDoubleExpCard;
	}

	public void setIsDoubleExpCard(int[] isDoubleExpCard) {
		this.isDoubleExpCard = isDoubleExpCard;
	}

	public int[] getActivityAddExp() {
		return activityAddExp;
	}

	public void setActivityAddExp(int[] activityAddExp) {
		this.activityAddExp = activityAddExp;
	}
	
	
}
