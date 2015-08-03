package com.wyd.empire.protocol.data.bulletin;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 获取服务器公告
 * @see AbstractData
 * @author mazheng
 */
public class GetAboutOk extends AbstractData {
	private String about;
    public GetAboutOk(int sessionId, int serial) {
        super(Protocol.MAIN_BULLETIN, Protocol.BULLETIN_GetAboutOk, sessionId, serial);
    }

    public GetAboutOk() {
        super(Protocol.MAIN_BULLETIN, Protocol.BULLETIN_GetAboutOk);
    }

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
}
