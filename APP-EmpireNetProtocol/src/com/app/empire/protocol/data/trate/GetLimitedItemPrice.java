package com.app.empire.protocol.data.trate;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>获取购买限量物品价格</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_GetItemPrice(获取商品价格)对应数据封装。
 * 
 * @see AbstractData
 * @author zengxc
 */
public class GetLimitedItemPrice extends AbstractData {
	private int itemId; //物品Id

    public GetLimitedItemPrice(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetLimitedItemPrice, sessionId, serial);
    }

    public GetLimitedItemPrice() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetLimitedItemPrice);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	

}
