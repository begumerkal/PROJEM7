package com.wyd.empire.protocol.data.trate;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetItemPrice</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_GetItemPrice(获取商品价格)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetItemPrice extends AbstractData {
	private int itemId; //物品ID
	private int shopType; //商品类型

    public GetItemPrice(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetItemPrice, sessionId, serial);
    }

    public GetItemPrice() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_GetItemPrice);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getShopType() {
		return shopType;
	}

	public void setShopType(int shopType) {
		this.shopType = shopType;
	}

}
