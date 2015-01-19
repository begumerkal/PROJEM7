package com.wyd.exchange.bean;
public class CreateInfo {
    private String  createNum; // 生成数量
    private String  channel;   // 渠道号
    private String  valid;     // 有效期
    private String[]  content;   // 奖励物品
    private String  describe;  // 奖励描述
    private String  creator;   // 创建人
    private int     groupId;   // 兑换码所属组
    private String  batchNum;  // 批次码
    private Integer minlevel;  // 领取奖励的最低等级
    private Integer maxlevel;  // 领取奖励的最高等级
    private int     rewardType; // 奖励的类型 0游戏物品奖励，1邮件奖励

    public String getCreateNum() {
        return createNum;
    }

    public void setCreateNum(String createNum) {
        this.createNum = createNum;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Integer getMinlevel() {
        return minlevel;
    }

    public void setMinlevel(Integer minlevel) {
        this.minlevel = minlevel;
    }

    public Integer getMaxlevel() {
        return maxlevel;
    }

    public void setMaxlevel(Integer maxlevel) {
        this.maxlevel = maxlevel;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }
}
