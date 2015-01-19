package com.wyd.empire.protocol.data.title;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SetTitle extends AbstractData {
    private int ptId;
    private int titleType; // 称号类型0系统1自定义
	public int getTitleType() {
		return titleType;
	}

	public void setTitleType(int titleType) {
		this.titleType = titleType;
	}

	public SetTitle(int sessionId, int serial) {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_SetTitle, sessionId, serial);
	}

	public SetTitle() {
		super(Protocol.MAIN_TITLE, Protocol.TITLE_SetTitle);
	}

    public int getPtId() {
        return ptId;
    }

    public void setPtId(int ptId) {
        this.ptId = ptId;
    }
}
