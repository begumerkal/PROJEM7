package com.wyd.empire.protocol.data.title;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SetTitleOk extends AbstractData {
    private String title;
	public SetTitleOk(int sessionId, int serial) {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_SetTitleOk, sessionId, serial);
	}

	public SetTitleOk() {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_SetTitleOk);
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
