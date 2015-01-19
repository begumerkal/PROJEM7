package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class SendSignInList extends AbstractData {
    private String    yearMonth;    // 年月
    private int       maxDay;       // 本月最大天数
    private int       dayToWeek;    // 本月第一天对应的周数
    private int       toDay;        // 今天日期
    private int[]     signDays;     // 本月签到日期
    private int       totalSign;    // 累计签到次数
    private int       arraySign;    // 连续签到次数
    private int       supplSign;    // 本月需补签次数
    private int       supplTimes;   // 本月已补签次数
    private int[]     supplPrice;   // 补签所需价格
    private int       arrayDays;    // 下级连续签到奖励所需天数
    private int[]     arrayItems;   // 下级连续签到奖励物品ID
    private int[]     arrayCount;   // 下级连续签到奖励物品数量
    private int[]     totalDays;    // 累计签到奖励所需天数
    private boolean[] totalRewards; // 累计签到奖励是否领取
    private int[]     rewardCount;  // 累计签到奖励物品数量
    private int[]     totalItems;   // 累计签到奖励物品ID
    private int[]     totalCount;   // 累计签到奖励物品数量

    public SendSignInList(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendSignInList, sessionId, serial);
    }

    public SendSignInList() {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendSignInList);
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public int getDayToWeek() {
        return dayToWeek;
    }

    public void setDayToWeek(int dayToWeek) {
        this.dayToWeek = dayToWeek;
    }

    public int getToDay() {
        return toDay;
    }

    public void setToDay(int toDay) {
        this.toDay = toDay;
    }

    public int[] getSignDays() {
        return signDays;
    }

    public void setSignDays(int[] signDays) {
        this.signDays = signDays;
    }

    public int getTotalSign() {
        return totalSign;
    }

    public void setTotalSign(int totalSign) {
        this.totalSign = totalSign;
    }

    public int getArraySign() {
        return arraySign;
    }

    public void setArraySign(int arraySign) {
        this.arraySign = arraySign;
    }

    public int getSupplSign() {
        return supplSign;
    }

    public void setSupplSign(int supplSign) {
        this.supplSign = supplSign;
    }

    public int getSupplTimes() {
        return supplTimes;
    }

    public void setSupplTimes(int supplTimes) {
        this.supplTimes = supplTimes;
    }

    public int[] getSupplPrice() {
        return supplPrice;
    }

    public void setSupplPrice(int[] supplPrice) {
        this.supplPrice = supplPrice;
    }

    public int getArrayDays() {
        return arrayDays;
    }

    public void setArrayDays(int arrayDays) {
        this.arrayDays = arrayDays;
    }

    public int[] getArrayItems() {
        return arrayItems;
    }

    public void setArrayItems(int[] arrayItems) {
        this.arrayItems = arrayItems;
    }

    public int[] getArrayCount() {
        return arrayCount;
    }

    public void setArrayCount(int[] arrayCount) {
        this.arrayCount = arrayCount;
    }

    public int[] getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int[] totalDays) {
        this.totalDays = totalDays;
    }

    public boolean[] getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(boolean[] totalRewards) {
        this.totalRewards = totalRewards;
    }

    public int[] getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(int[] rewardCount) {
        this.rewardCount = rewardCount;
    }

    public int[] getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int[] totalItems) {
        this.totalItems = totalItems;
    }

    public int[] getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int[] totalCount) {
        this.totalCount = totalCount;
    }
}
