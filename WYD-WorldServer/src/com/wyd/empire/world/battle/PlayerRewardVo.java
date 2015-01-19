package com.wyd.empire.world.battle;

import java.util.List;

import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.player.WorldPlayer;

public class PlayerRewardVo {
	private int mapId;
	private int index = 2;// 玩家当前领取的奖励数量
	private List<WorldPlayer> playerList;
	private List<RewardItemsVo> rewardList;

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<WorldPlayer> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<WorldPlayer> playerList) {
		this.playerList = playerList;
	}

	public List<RewardItemsVo> getRewardList() {
		return rewardList;
	}

	public void setRewardList(List<RewardItemsVo> rewardList) {
		this.rewardList = rewardList;
	}

	public RewardItemsVo getRewardItem() {
		if (rewardList.size() > index) {
			return rewardList.get(index);
		} else {
			return null;
		}
	}
}
