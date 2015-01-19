package com.wyd.empire.protocol.data.title;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class PlayerGetTitle extends AbstractData {
    private String title;
	public PlayerGetTitle(int sessionId, int serial) {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_PlayerGetTitle, sessionId, serial);
	}

	public PlayerGetTitle() {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_PlayerGetTitle);
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
