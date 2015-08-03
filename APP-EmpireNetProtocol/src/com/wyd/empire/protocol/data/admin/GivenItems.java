package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 物品给予
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GivenItems extends AbstractData {
    private int[]    playerIds;
    private int[]    itemIds;
    private int[]    counts;
    private int[]    dayOrcount; // 物品给予天数还是使用次数 0天数，1使用次数
    private String   remark;
    private int[]    strengthen;
    private String[] orderNum;
    private int[] 	 itemType; //物品类型 0是物品，1是宠物

    public GivenItems(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GivenItems, sessionId, serial);
    }

    public GivenItems() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GivenItems);
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
