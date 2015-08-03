package com.wyd.empire.protocol.data.trate;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Exchange</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_Exchange(兑换商品)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ExchangeItem extends AbstractData {
	
	private int[] itemId;
	

    public ExchangeItem(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_ExchangeItem, sessionId, serial);
    }

    public ExchangeItem() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_ExchangeItem);
    }

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

}
