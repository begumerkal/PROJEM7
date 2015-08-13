package com.app.empire.protocol.data.trate;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>获取购买金币/勋章/星魂价格</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_GetItemPrice(获取商品价格)对应数据封装。
 * 
 * @see AbstractData
 * @author zengxc
 */
public class BuyLimitedItemOk extends AbstractData {
	private int itemId; //物品ID	
	private	int	itemCurNum;//当前的限量物品的个数
	private	int	addItemNum;//增加的限量物品的个数

    public BuyLimitedItemOk(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_BuyLimitedItemOk, sessionId, serial);
    }

    public BuyLimitedItemOk() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_BuyLimitedItemOk);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemCurNum() {
		return itemCurNum;
	}

	public void setItemCurNum(int itemCurNum) {
		this.itemCurNum = itemCurNum;
	}

	public int getAddItemNum() {
		return addItemNum;
	}

	public void setAddItemNum(int addItemNum) {
		this.addItemNum = addItemNum;
	}

	


	

}
