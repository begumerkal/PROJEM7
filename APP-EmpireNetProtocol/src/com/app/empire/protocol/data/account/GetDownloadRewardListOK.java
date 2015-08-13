package com.app.empire.protocol.data.account;

 
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>LoginOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_LoginOk(账户登录成功)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GetDownloadRewardListOK extends AbstractData {
	private int[] itemsId;
	private int[] itemsNum;
	
    public GetDownloadRewardListOK(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetDownloadRewardListOK, sessionId, serial);
    }

    public GetDownloadRewardListOK() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetDownloadRewardListOK);
    }

	public int[] getItemsId() {
		return itemsId;
	}

	public void setItemsId(int[] itemsId) {
		this.itemsId = itemsId;
	}

	public int[] getItemsNum() {
		return itemsNum;
	}

	public void setItemsNum(int[] itemsNum) {
		this.itemsNum = itemsNum;
	}

 
 
}