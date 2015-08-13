package com.app.empire.protocol.data.reward;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetRewardListOk extends AbstractData {
    private String[] rechargeReward;      // 首冲奖励物品
    private String[] rechargeRewardRemark; // 首冲奖励物品描述
    private String[] rechargeRewardNum;   // 首冲奖励物品数量
    private int[]    rechargeStrongLevel; // 首冲奖励物品强化等级
    private int      currentAmount;       // 当前累积钻石
    private int      currentLottery;      // 当前抽奖次数
    private int      nextAmount;          // 下阶累积钻石
    private int      nextLottery;         // 下阶抽奖次数
    private String[] lotteryItems;        // 抽奖物品
    private String[] lotteryItemsRemark;  // 抽奖物品描述
    private String[] lotteryItemsNum;     // 抽奖物品数量
    private int[]    lotteryItemsId;      // 抽奖物品Id
    private int[]    lotteryStrongLevel;  // 抽奖物品强化等级
    private boolean  isReward;            // 是否可以领取
    private boolean  isLottery;           // 是否可以抽奖
    private int[]	 rewardId;			  // 奖励ID
    private int[]    rewardType;		  // 1 首充奖励物品，2 每日首充奖励物品
    private int      everyDayRewardNum; //可以每日奖励领取的次数
    private int[]   rewardNum;      // 奖励次数
    private int[]   rechargeNum;    // 充值额度
    private int      maxNum;        //最大累计数量
    
    
    public GetRewardListOk(int sessionId, int serial) {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetRewardListOk, sessionId, serial);
    }

    public GetRewardListOk() {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetRewardListOk);
    }

    public String[] getRechargeReward() {
        return rechargeReward;
    }

    public void setRechargeReward(String[] rechargeReward) {
        this.rechargeReward = rechargeReward;
    }

    public String[] getRechargeRewardRemark() {
        return rechargeRewardRemark;
    }

    public void setRechargeRewardRemark(String[] rechargeRewardRemark) {
        this.rechargeRewardRemark = rechargeRewardRemark;
    }

    public String[] getRechargeRewardNum() {
        return rechargeRewardNum;
    }

    public void setRechargeRewardNum(String[] rechargeRewardNum) {
        this.rechargeRewardNum = rechargeRewardNum;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getCurrentLottery() {
        return currentLottery;
    }

    public void setCurrentLottery(int currentLottery) {
        this.currentLottery = currentLottery;
    }

    public int getNextAmount() {
        return nextAmount;
    }

    public void setNextAmount(int nextAmount) {
        this.nextAmount = nextAmount;
    }

    public int getNextLottery() {
        return nextLottery;
    }

    public void setNextLottery(int nextLottery) {
        this.nextLottery = nextLottery;
    }

    public String[] getLotteryItems() {
        return lotteryItems;
    }

    public void setLotteryItems(String[] lotteryItems) {
        this.lotteryItems = lotteryItems;
    }

    public String[] getLotteryItemsRemark() {
        return lotteryItemsRemark;
    }

    public void setLotteryItemsRemark(String[] lotteryItemsRemark) {
        this.lotteryItemsRemark = lotteryItemsRemark;
    }

    public String[] getLotteryItemsNum() {
        return lotteryItemsNum;
    }

    public void setLotteryItemsNum(String[] lotteryItemsNum) {
        this.lotteryItemsNum = lotteryItemsNum;
    }

    public boolean getIsReward() {
        return isReward;
    }

    public void setIsReward(boolean isReward) {
        this.isReward = isReward;
    }

    public boolean getIsLottery() {
        return isLottery;
    }

    public void setIsLottery(boolean isLottery) {
        this.isLottery = isLottery;
    }

    public int[] getLotteryItemsId() {
        return lotteryItemsId;
    }

    public void setLotteryItemsId(int[] lotteryItemsId) {
        this.lotteryItemsId = lotteryItemsId;
    }

    public int[] getRechargeStrongLevel() {
        return rechargeStrongLevel;
    }

    public void setRechargeStrongLevel(int[] rechargeStrongLevel) {
        this.rechargeStrongLevel = rechargeStrongLevel;
    }

    public int[] getLotteryStrongLevel() {
        return lotteryStrongLevel;
    }

    public void setLotteryStrongLevel(int[] lotteryStrongLevel) {
        this.lotteryStrongLevel = lotteryStrongLevel;
    }

	public int[] getRewardId() {
		return rewardId;
	}

	public void setRewardId(int[] rewardId) {
		this.rewardId = rewardId;
	}

	public int[] getRewardType() {
		return rewardType;
	}

	public void setRewardType(int[] rewardType) {
		this.rewardType = rewardType;
	}

	public int getEveryDayRewardNum() {
		return everyDayRewardNum;
	}

	public void setEveryDayRewardNum(int everyDayRewardNum) {
		this.everyDayRewardNum = everyDayRewardNum;
	}


	public int[] getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(int[] rewardNum) {
		this.rewardNum = rewardNum;
	}

	public int[] getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(int[] rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
}
