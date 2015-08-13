package com.app.empire.protocol.data.cache;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 玩家物品
 * @author zengxc
 *
 */
public class PlayerItem extends AbstractData {
	private int[]     itemId;               // 物品ID
    private byte[]    maintype;             // 0:投掷武器 1:射击武器 2:身躯装扮 3:脸谱 4:头发 5:一般道具（只能在战场上使用的道具） 6:合成类（合成时使用的） 7：镶嵌（镶嵌时使用的）8:其它"
    private byte[]    subtype;              // 主类型是其它类型可以使用这个再分类
    private int[]     lastNum;              // 剩余数量，如果是-1，就是不限数量使用
    private int[]     lastTime;             // 剩余的天数，如果是-1，就是不限时间使用
    private boolean[] isUse;                // 是否装备在身上
    private int[]     recyclePrice;         // 回收价格（－1不能回收）
    private boolean[] expired;              // 是否过期 (true为已过期)
    private boolean[] recommended;          // true为推荐
    private String[]  data;                 // 内容JSON格式如：{"starLevel":8,"strongLevel":3} 详见：数据字典
    private int[]     playerItemId;         // 玩家物品ID

	
	
	
	public PlayerItem(int sessionId, int serial) {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_PlayerItem, sessionId, serial);
	}

	public PlayerItem() {
		super(Protocol.MAIN_CACHE, Protocol.CACHE_PlayerItem);
	}

	
	public byte[] getMaintype() {
		return maintype;
	}
	public void setMaintype(byte[] maintype) {
		this.maintype = maintype;
	}
	public byte[] getSubtype() {
		return subtype;
	}
	public void setSubtype(byte[] subtype) {
		this.subtype = subtype;
	}
	public int[] getLastTime() {
		return lastTime;
	}
	public void setLastTime(int[] lastTime) {
		this.lastTime = lastTime;
	}
	public int[] getLastNum() {
		return lastNum;
	}
	public void setLastNum(int[] lastNum) {
		this.lastNum = lastNum;
	}
	public boolean[] getIsUse() {
		return isUse;
	}
	public void setIsUse(boolean[] isUse) {
		this.isUse = isUse;
	}

	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public int[] getRecyclePrice() {
		return recyclePrice;
	}
	public void setRecyclePrice(int[] recyclePrice) {
		this.recyclePrice = recyclePrice;
	}
	public boolean[] getExpired() {
		return expired;
	}
	public void setExpired(boolean[] expired) {
		this.expired = expired;
	}
	public boolean[] getRecommended() {
		return recommended;
	}
	public void setRecommended(boolean[] recommended) {
		this.recommended = recommended;
	}

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int[] getPlayerItemId() {
		return playerItemId;
	}

	public void setPlayerItemId(int[] playerItemId) {
		this.playerItemId = playerItemId;
	}

	
	
	
}
