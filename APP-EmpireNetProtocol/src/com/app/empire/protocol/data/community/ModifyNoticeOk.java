package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ModifyNoticeOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ModifyNoticeOk(修改公会内部公告成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ModifyNoticeOk extends AbstractData {
	private String notice;
	
    public ModifyNoticeOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyNoticeOk, sessionId, serial);
    }

    public ModifyNoticeOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ModifyNoticeOk);
    }

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
    
}
