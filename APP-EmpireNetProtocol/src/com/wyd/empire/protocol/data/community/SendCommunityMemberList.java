package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SendCommunityMemberList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SendCommunityMemberList(发送公会成员列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendCommunityMemberList extends AbstractData {
    private String[]  playerName;
    private int[]     position;
    private int[]     playerContribution;
    private boolean[] onLine;
    private int[]     rank;              // 玩家排名
    private int[]     playerId;          // 玩家id
    private int[]     level;             // 玩家等级
    private String[]  onLineState;
    private int       pageNumber;        // 当前页数
    private int       totalNumber;       // 总页数
    private int[]     todayContribution; // 玩家当天贡献度
    private int[]     zsleve;            // 转生等级

    public SendCommunityMemberList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendCommunityMemberList, sessionId, serial);
    }

    public SendCommunityMemberList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendCommunityMemberList);
    }

    public String[] getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPlayerContribution() {
        return playerContribution;
    }

    public void setPlayerContribution(int[] playerContribution) {
        this.playerContribution = playerContribution;
    }

    public String[] getOnLineState() {
        return onLineState;
    }

    public void setOnLineState(String[] onLineState) {
        this.onLineState = onLineState;
    }

    public int[] getRank() {
        return rank;
    }

    public void setRank(int[] rank) {
        this.rank = rank;
    }

    public int[] getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int[] playerId) {
        this.playerId = playerId;
    }

    public int[] getLevel() {
        return level;
    }

    public void setLevel(int[] level) {
        this.level = level;
    }

    public boolean[] getOnLine() {
        return onLine;
    }

    public void setOnLine(boolean[] onLine) {
        this.onLine = onLine;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int[] getTodayContribution() {
        return todayContribution;
    }

    public void setTodayContribution(int[] todayContribution) {
        this.todayContribution = todayContribution;
    }

    public int[] getZsleve() {
        return zsleve;
    }

    public void setZsleve(int[] zsleve) {
        this.zsleve = zsleve;
    }
}
