package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Rename</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_Rename(公会改名)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class Rename extends AbstractData {
	private String newName;

    public Rename(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_Rename, sessionId, serial);
    }

    public Rename() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_Rename);
    }

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

}
