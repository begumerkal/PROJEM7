package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SendCommunityFriendList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_SendCommunityFriendList(发送公会好友协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendCommunityFriendList extends AbstractData {
    private int[]     playerId;  // 好友Id
    private String[]  playerName; // 好友名称
    private int[]     level;     // 好友等级
    private boolean[] sex;       // 好友性别，false是男，true是女
    private boolean[] online;    // 好友是否在线
    private int       pageNumber;
    private int       pageCount;
    private int[]     zsleve;    // 转生等级

    public SendCommunityFriendList(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SendCommunityFriendList, sessionId, serial);
    }

    public SendCommunityFriendList() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SendCommunityFriendList);
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

    public int[] getLevel() {
        return level;
    }

    public void setLevel(int[] level) {
        this.level = level;
    }

    public boolean[] getSex() {
        return sex;
    }

    public void setSex(boolean[] sex) {
        this.sex = sex;
    }

    public boolean[] getOnline() {
        return online;
    }

    public void setOnline(boolean[] online) {
        this.online = online;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int[] getZsleve() {
        return zsleve;
    }

    public void setZsleve(int[] zsleve) {
        this.zsleve = zsleve;
    }
}
