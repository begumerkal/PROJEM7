package com.wyd.empire.protocol.data.cross;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class CrossSkillEquip extends AbstractData {
    private int     battleId;
    private int     playerId;
    private int     itemcount;
    private int[]   itmeIds;
    private int     tiredValue;
    private int     consumePower;
    private int     selfAddHP;
    private int[]   playerIds;
    private int[]   allAddHP;
    private boolean completed;
    private int[]   toolsType;
    private int[]   toolsSubType;
    private int[]   toolsParam1;
    private int[]   toolsParam2;

	public CrossSkillEquip() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossSkillEquip);
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

    public int getItemcount() {
        return itemcount;
    }

    public void setItemcount(int itemcount) {
        this.itemcount = itemcount;
    }

    public int[] getItmeIds() {
        return itmeIds;
    }

    public void setItmeIds(int[] itmeIds) {
        this.itmeIds = itmeIds;
    }

    public int getTiredValue() {
        return tiredValue;
    }

    public void setTiredValue(int tiredValue) {
        this.tiredValue = tiredValue;
    }

    public int getConsumePower() {
        return consumePower;
    }

    public void setConsumePower(int consumePower) {
        this.consumePower = consumePower;
    }

    public int getSelfAddHP() {
        return selfAddHP;
    }

    public void setSelfAddHP(int selfAddHP) {
        this.selfAddHP = selfAddHP;
    }

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }

    public int[] getAllAddHP() {
        return allAddHP;
    }

    public void setAllAddHP(int[] allAddHP) {
        this.allAddHP = allAddHP;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int[] getToolsType() {
        return toolsType;
    }

    public void setToolsType(int[] toolsType) {
        this.toolsType = toolsType;
    }

    public int[] getToolsSubType() {
        return toolsSubType;
    }

    public void setToolsSubType(int[] toolsSubType) {
        this.toolsSubType = toolsSubType;
    }

    public int[] getToolsParam1() {
        return toolsParam1;
    }

    public void setToolsParam1(int[] toolsParam1) {
        this.toolsParam1 = toolsParam1;
    }

    public int[] getToolsParam2() {
        return toolsParam2;
    }

    public void setToolsParam2(int[] toolsParam2) {
        this.toolsParam2 = toolsParam2;
    }
}
