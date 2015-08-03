package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SendApprovingMemberList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SendApprovingMemberList(发送公会待审批成员列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendApprovingMemberList extends AbstractData {
    private int[]    playerId;
    private String[] playerName;
    private int[]    playerLevel;   // 玩家等级
    private boolean  acceptMemberOr; // 公会是否接受会员申请
    private int[]    sex;           // 性别
    private int[]    zsleve;        // 转生等级

    public SendApprovingMemberList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendApprovingMemberList, sessionId, serial);
    }

    public SendApprovingMemberList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendApprovingMemberList);
    }

    public int[] getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int[] playerId) {
        this.playerId = playerId;
    }

    public String[] getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public int[] getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int[] playerLevel) {
        this.playerLevel = playerLevel;
    }

    public boolean getAcceptMemberOr() {
        return acceptMemberOr;
    }

    public void setAcceptMemberOr(boolean acceptMemberOr) {
        this.acceptMemberOr = acceptMemberOr;
    }

    public int[] getSex() {
        return sex;
    }

    public void setSex(int[] sex) {
        this.sex = sex;
    }

    public int[] getZsleve() {
        return zsleve;
    }

    public void setZsleve(int[] zsleve) {
        this.zsleve = zsleve;
    }
}
