package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 物品给予结果
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GivenItemsResult extends AbstractData {
    private int      length;
    private int[]    playerIds; // 玩家id
    private int[]    itemIds;   // 物品id
    private int[]    counts;    // 给予数量
    private int[]    dayOrcount; // 物品给予天数还是使用次数 0天数，1使用次数
    private int[]    strengthen;
    private String[] orderNum;
    private int[] itemType; //发放类型 0是物品 1是宠物

    public GivenItemsResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GivenItemsResult, sessionId, serial);
    }

    public GivenItemsResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GivenItemsResult);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(int[] playerIds) {
        this.playerIds = playerIds;
    }

    public int[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(int[] itemIds) {
        this.itemIds = itemIds;
    }

    public int[] getCounts() {
        return counts;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }

    public int[] getDayOrcount() {
        return dayOrcount;
    }

    public void setDayOrcount(int[] dayOrcount) {
        this.dayOrcount = dayOrcount;
    }

    public int[] getStrengthen() {
        return strengthen;
    }

    public void setStrengthen(int[] strengthen) {
        this.strengthen = strengthen;
    }

    public String[] getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String[] orderNum) {
        this.orderNum = orderNum;
    }

	public int[] getItemType() {
		return itemType;
	}

	public void setItemType(int[] itemType) {
		this.itemType = itemType;
	}
}
