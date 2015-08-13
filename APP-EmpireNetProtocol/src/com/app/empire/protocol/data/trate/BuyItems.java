package com.app.empire.protocol.data.trate;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>BuyItems</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_BuyItems(购买商品)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class BuyItems extends AbstractData {
	
	private int count;
	private int[] itemId;
	private int[] itemPriceId;
	

    public BuyItems(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_BuyItems, sessionId, serial);
    }

    public BuyItems() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_BuyItems);
    }

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int[] getItemPriceId() {
		return itemPriceId;
	}

	public void setItemPriceId(int[] itemPriceId) {
		this.itemPriceId = itemPriceId;
	}

}
