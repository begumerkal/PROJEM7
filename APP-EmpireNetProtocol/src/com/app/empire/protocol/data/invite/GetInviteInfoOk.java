package com.app.empire.protocol.data.invite;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取邀请码信息
 * 
 * @author zguoqiu
 */
public class GetInviteInfoOk extends AbstractData {
    private String   inviteCode;
    private int      total;
    private int      amount;
    private String[] names;
    private String[] rewards;
    private int[]    counts;
    private String   remark;
    private boolean  canReward;
    private String   bindInviteCode;

    public GetInviteInfoOk(int sessionId, int serial) {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteInfoOk, sessionId, serial);
    }

    public GetInviteInfoOk() {
        super(Protocol.MAIN_INVITE, Protocol.INVITE_GetInviteInfoOk);
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String[] getRewards() {
        return rewards;
    }

    public void setRewards(String[] rewards) {
        this.rewards = rewards;
    }

    public int[] getCounts() {
        return counts;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isCanReward() {
        return canReward;
    }

    public void setCanReward(boolean canReward) {
        this.canReward = canReward;
    }

    public String getBindInviteCode() {
        return bindInviteCode;
    }

    public void setBindInviteCode(String bindInviteCode) {
        this.bindInviteCode = bindInviteCode;
    }
}
