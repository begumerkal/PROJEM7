package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>ModifyNotice</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ModifyNotice(修改公会内部公告协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ModifyNotice extends AbstractData {
	private int communityId;
	private String notice;

    public ModifyNotice(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyNotice, sessionId, serial);
    }

    public ModifyNotice() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyNotice);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}



}
