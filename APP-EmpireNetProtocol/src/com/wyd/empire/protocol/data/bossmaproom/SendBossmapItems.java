package com.wyd.empire.protocol.data.bossmaproom;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class SendBossmapItems extends AbstractData {
    private int       mapCount;    // 地图数量
    private int[]     mapId;       // 地图id
    private String[]  mapShortName;// 地图名称缩写
    private String[]  mapIcon;     // 地图图标
    private boolean[] hasOpened;   // 地图是否已开启（true表示开启）
    private int[]     starLevel;   // 副本星级（未打过的副本星级为0）
    private int[]     rewardList;  // 奖励物品ID
    private int[]     mapMode;     // 0小怪副本，1BOSS副本（现在都为BOSS副本）
    private int[]     mapLevel;    // 挑战副本所需的等级

    public SendBossmapItems(int sessionId, int serial) {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendBossmapItems, sessionId, serial);
    }

    public SendBossmapItems() {
        super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendBossmapItems);
    }

    public int getMapCount() {
        return mapCount;
    }

    public void setMapCount(int mapCount) {
        this.mapCount = mapCount;
    }

    public int[] getMapId() {
        return mapId;
    }

    public void setMapId(int[] mapId) {
        this.mapId = mapId;
    }

    public String[] getMapShortName() {
        return mapShortName;
    }

    public void setMapShortName(String[] mapShortName) {
        this.mapShortName = mapShortName;
    }

    public String[] getMapIcon() {
        return mapIcon;
    }

    public void setMapIcon(String[] mapIcon) {
        this.mapIcon = mapIcon;
    }

    

    public boolean[] getHasOpened() {
        return hasOpened;
    }

    public void setHasOpened(boolean[] hasOpened) {
        this.hasOpened = hasOpened;
    }

    public int[] getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(int[] starLevel) {
        this.starLevel = starLevel;
    }

   

    public int[] getRewardList() {
        return rewardList;
    }

    public void setRewardList(int[] rewardList) {
        this.rewardList = rewardList;
    }

    public int[] getMapMode() {
        return mapMode;
    }

    public void setMapMode(int[] mapMode) {
        this.mapMode = mapMode;
    }

    public int[] getMapLevel() {
        return mapLevel;
    }

    public void setMapLevel(int[] mapLevel) {
        this.mapLevel = mapLevel;
    }
}
