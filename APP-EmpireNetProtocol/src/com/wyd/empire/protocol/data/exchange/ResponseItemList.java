package com.wyd.empire.protocol.data.exchange;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zengxc
 */
public class ResponseItemList extends AbstractData {
	
	private	int[]	itemId;	//物品Id
	private	int[]	strengthenlevel;	//强化等级
	private	int[]	day;	//天数
	private	int[]	useTimes;	//使用次数
	private	int[]	price;	//价格
	private	int	lastTime;	//距离下次刷新的秒数
	private int refreshDramond;


    public ResponseItemList(int sessionId, int serial) {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_ResponseItemList, sessionId, serial);
    }

    public ResponseItemList() {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_ResponseItemList);
    }

	public int[] getItemId() {
		return itemId;
	}

	public void setItemId(int[] itemId) {
		this.itemId = itemId;
	}

	public int[] getStrengthenlevel() {
		return strengthenlevel;
	}

	public void setStrengthenlevel(int[] strengthenlevel) {
		this.strengthenlevel = strengthenlevel;
	}

	public int[] getDay() {
		return day;
	}

	public void setDay(int[] day) {
		this.day = day;
	}

	public int[] getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(int[] useTimes) {
		this.useTimes = useTimes;
	}

	public int[] getPrice() {
		return price;
	}

	public void setPrice(int[] price) {
		this.price = price;
	}


	public int getLastTime() {
		return lastTime;
	}

	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	public int getRefreshDramond() {
		return refreshDramond;
	}

	public void setRefreshDramond(int refreshDramond) {
		this.refreshDramond = refreshDramond;
	}

	

	

}
