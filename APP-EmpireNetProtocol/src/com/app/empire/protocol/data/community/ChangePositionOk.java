package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ChangePositionOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ChangePositionOk(会员升职成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ChangePositionOk extends AbstractData {
	private boolean isUp;

    public ChangePositionOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePositionOk, sessionId, serial);
    }

    public ChangePositionOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePositionOk);
    }

	public boolean getIsUp() {
		return isUp;
	}

	public void setIsUp(boolean isUp) {
		this.isUp = isUp;
	}

}
