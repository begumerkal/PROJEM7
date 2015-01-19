package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SendOnlinePlayer</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_SendOnlinePlayer(发送在线玩家协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendOnlinePlayer extends AbstractData {
    private int[]     playerId;  // 好友Id
    private String[]  playerName; // 好友名称
    private int[]     level;     // 好友等级
    private boolean[] sex;       // 好友性别，false是男，true是女
    private boolean[] online;    // 好友是否在线
    private int       pageNumber; // 当前页数
    private int       totalPage; // 总页数
    private int[]     zsleve;    // 转生等级

    public SendOnlinePlayer(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_SendOnlinePlayer, sessionId, serial);
    }

    public SendOnlinePlayer() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_SendOnlinePlayer);
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int[] getZsleve() {
        return zsleve;
    }

    public void setZsleve(int[] zsleve) {
        this.zsleve = zsleve;
    }
}
