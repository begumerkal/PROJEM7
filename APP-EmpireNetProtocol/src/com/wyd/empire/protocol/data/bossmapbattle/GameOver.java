package com.wyd.empire.protocol.data.bossmapbattle;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GameOver extends AbstractData {
	private int battleId;
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
	private int[] star;//星级评价
	private int eggCount;//蛋的数量
	private int[] egg_playeId;//属于谁的奖品
	private String[] egg_Item_Name;//对应的商品名称
	private String[] egg_item_icon;//物品对应的icon
	private int[] egg_ItemNum;//赠送数量
	private int[] pices;//砸蛋的价格
	private int[] guildAddExp; //公会技能加的经验
	private int[] isMarry; // 结婚加的经验
	private int[] isDoubleExpCard; //是否具有双倍经验卡
	private int[] activityAddExp; //活动是否加了经验
	private boolean isBackToRoom;//是否还返回房间

	public GameOver(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_GameOver, sessionId, serial);
	}

	public GameOver() {
		super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_GameOver);
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
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


	public int[] getStar() {
		return star;
	}

	public void setStar(int[] star) {
		this.star = star;
	}

	public int getEggCount() {
		return eggCount;
	}

	public void setEggCount(int eggCount) {
		this.eggCount = eggCount;
	}

	public int[] getEgg_playeId() {
		return egg_playeId;
	}

	public void setEgg_playeId(int[] eggPlayeId) {
		egg_playeId = eggPlayeId;
	}

	public String[] getEgg_Item_Name() {
		return egg_Item_Name;
	}

	public void setEgg_Item_Name(String[] eggItemName) {
		egg_Item_Name = eggItemName;
	}

	public String[] getEgg_item_icon() {
		return egg_item_icon;
	}

	public void setEgg_item_icon(String[] eggItemIcon) {
		egg_item_icon = eggItemIcon;
	}

	public int[] getEgg_ItemNum() {
		return egg_ItemNum;
	}

	public void setEgg_ItemNum(int[] eggItemNum) {
		egg_ItemNum = eggItemNum;
	}

    public int[] getPices() {
        return pices;
    }

    public void setPices(int[] pices) {
        this.pices = pices;
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

    public boolean getIsBackToRoom() {
        return isBackToRoom;
    }

    public void setIsBackToRoom(boolean isBackToRoom) {
        this.isBackToRoom = isBackToRoom;
    }
}
