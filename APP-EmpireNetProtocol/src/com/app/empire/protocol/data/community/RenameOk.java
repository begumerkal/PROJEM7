package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>RenameOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_Rename(公会改名)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class RenameOk extends AbstractData {
	private String newName;

    public RenameOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_RenameOk, sessionId, serial);
    }

    public RenameOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_RenameOk);
    }

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
