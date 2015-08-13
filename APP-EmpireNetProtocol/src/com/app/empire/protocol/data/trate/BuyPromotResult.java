package com.app.empire.protocol.data.trate;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>BuyResult</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_TRATE下子命令TRATE_BuyResult(购买物品结果)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class BuyPromotResult extends AbstractData {
	private boolean buyResult;	//购买成功还是失败
	private String content;		//结果内容
	private int costTicks;		//花费掉多少点券
	private int costGold;		//花费掉多少金币
	private int costMedal;		//花费掉多少勋章
	private int itemId;			//商品Id
	private String lastTime;	//剩余天数（String）
	private int	  lastTimeMark;			//剩余天数（-1表示不按天数计算或无限期，10期添加）
	

    public BuyPromotResult(int sessionId, int serial) {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_BuyPromotResult, sessionId, serial);
    }

    public BuyPromotResult() {
        super(Protocol.MAIN_TRATE, Protocol.TRATE_BuyPromotResult);
    }

	public boolean isBuyResult() {
		return buyResult;
	}

	public void setBuyResult(boolean buyResult) {
		this.buyResult = buyResult;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCostTicks() {
		return costTicks;
	}

	public void setCostTicks(int costTicks) {
		this.costTicks = costTicks;
	}

	public int getCostGold() {
		return costGold;
	}

	public void setCostGold(int costGold) {
		this.costGold = costGold;
	}

	public int getCostMedal() {
		return costMedal;
	}

	public void setCostMedal(int costMedal) {
		this.costMedal = costMedal;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public int getLastTimeMark() {
		return lastTimeMark;
	}

	public void setLastTimeMark(int lastTimeMark) {
		this.lastTimeMark = lastTimeMark;
	}


}
