package com.wyd.empire.protocol.data.bossmapbattle;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class ChangePosition extends AbstractData {
    private int battleId; // 战斗id
    private int playerOrGuai;// 0:player 1:guai
    private int currentId; // 角色id
    private int playerCount;// 同步角色数量
    private int[] playerIds;// 用户id
    private int[] curPositionX; // 没飞行前的x坐标
    private int[] curPositionY; // 没飞行前的y坐标
    private int guaiCount; //怪的数量
    private int[] guaiBattleIds; //怪id
    private int[] guaiCurPositionX; //没飞行前的x坐标
    private int[] guaiCurPositionY; //没飞行前的y坐标
    
    public ChangePosition(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_ChangePosition, sessionId, serial);
    }

    public ChangePosition() {
        super(Protocol.MAIN_BOSSMAPBATTLE, Protocol.BOSSMAPBATTLE_ChangePosition);
    }

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public int getPlayerOrGuai() {
		return playerOrGuai;
	}

	public void setPlayerOrGuai(int playerOrGuai) {
		this.playerOrGuai = playerOrGuai;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
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

	public int[] getCurPositionX() {
		return curPositionX;
	}

	public void setCurPositionX(int[] curPositionX) {
		this.curPositionX = curPositionX;
	}

	public int[] getCurPositionY() {
		return curPositionY;
	}

	public void setCurPositionY(int[] curPositionY) {
		this.curPositionY = curPositionY;
	}

	public int getGuaiCount() {
		return guaiCount;
	}

	public void setGuaiCount(int guaiCount) {
		this.guaiCount = guaiCount;
	}

	public int[] getGuaiBattleIds() {
		return guaiBattleIds;
	}

	public void setGuaiBattleIds(int[] guaiBattleIds) {
		this.guaiBattleIds = guaiBattleIds;
	}

	public int[] getGuaiCurPositionX() {
		return guaiCurPositionX;
	}

	public void setGuaiCurPositionX(int[] guaiCurPositionX) {
		this.guaiCurPositionX = guaiCurPositionX;
	}

	public int[] getGuaiCurPositionY() {
		return guaiCurPositionY;
	}

	public void setGuaiCurPositionY(int[] guaiCurPositionY) {
		this.guaiCurPositionY = guaiCurPositionY;
	}
}
