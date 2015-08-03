package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetLevelRewardListOk extends AbstractData {
    private int       playerLevel;  // 玩家当前等级
    private boolean   playerRebirth;// 玩家当前等级
    private int[]     types;        // 奖励类型 4等级奖励，5等级目标奖励
    private int[]     levels;       // 奖励等级需求
    private boolean[] rewards;      // 奖励是否领取
    private int[]     rewardCount;  // 奖励物品数量
    private int[]     itemIds;      // 奖励物品id
    private int[]     itemCount;    // 奖励物品数量

    public GetLevelRewardListOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetLevelRewardListOk, sessionId, serial);
    }

    public GetLevelRewardListOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetLevelRewardListOk);
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    public boolean isPlayerRebirth() {
        return playerRebirth;
    }

    public void setPlayerRebirth(boolean playerRebirth) {
        this.playerRebirth = playerRebirth;
    }

    public int[] getTypes() {
        return types;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }

    public int[] getLevels() {
        return levels;
    }

    public void setLevels(int[] levels) {
        this.levels = levels;
    }

    public boolean[] getRewards() {
        return rewards;
    }

    public void setRewards(boolean[] rewards) {
        this.rewards = rewards;
    }

    public int[] getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(int[] rewardCount) {
        this.rewardCount = rewardCount;
    }

    public int[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(int[] itemIds) {
        this.itemIds = itemIds;
    }

    public int[] getItemCount() {
        return itemCount;
    }

    public void setItemCount(int[] itemCount) {
        this.itemCount = itemCount;
    }
}
