package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>CheckRightFailed</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_CheckRightFailed(创建公会验证权限失败协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CheckRightFailed extends AbstractData {
	private String errorMsg;

    public CheckRightFailed(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CheckRightFailed, sessionId, serial);
    }

    public CheckRightFailed() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CheckRightFailed);
    }

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
