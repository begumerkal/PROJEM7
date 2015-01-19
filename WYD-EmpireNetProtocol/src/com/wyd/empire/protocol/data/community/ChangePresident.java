package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>ChangePresident</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ChangePresident(会长让位协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ChangePresident extends AbstractData {
	private int oldPresidentId;
	private int newPresidentId;

    public ChangePresident(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePresident, sessionId, serial);
    }

    public ChangePresident() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePresident);
    }


	public int getOldPresidentId() {
		return oldPresidentId;
	}

	public void setOldPresidentId(int oldPresidentId) {
		this.oldPresidentId = oldPresidentId;
	}

	public int getNewPresidentId() {
		return newPresidentId;
	}

	public void setNewPresidentId(int newPresidentId) {
		this.newPresidentId = newPresidentId;
	}

}
