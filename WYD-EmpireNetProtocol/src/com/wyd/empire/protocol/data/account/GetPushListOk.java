package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_Login(账户登录)对应数据封装。
 * 
 * @see AbstractData
 * @author chenjie
 */
public class GetPushListOk extends AbstractData {
	
	private String 	version;			//服务器最新版本号
	private String 	pushList;			//推送列表数组里面是json字符串
	private boolean updates;			//是否需要更新客户端列表
	
    public GetPushListOk(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetPushListOk, sessionId, serial);
    }

    public GetPushListOk() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_GetPushListOk);
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPushList() {
		return pushList;
	}

	public void setPushList(String pushList) {
		this.pushList = pushList;
	}

	public boolean getUpdates() {
		return updates;
	}

	public void setUpdates(boolean updates) {
		this.updates = updates;
	}

	
    
}
