package com.wyd.exchange.bean;

import java.util.List;

public class InviteListResult {
    private int    pageIndex;
    private int    pageCount;
    private List<String> serviceName;
    private List<String> playerName;
    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public List<String> getServiceName() {
        return serviceName;
    }
    public void setServiceName(List<String> serviceName) {
        this.serviceName = serviceName;
    }
    public List<String> getPlayerName() {
        return playerName;
    }
    public void setPlayerName(List<String> playerName) {
        this.playerName = playerName;
    }
}
