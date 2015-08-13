package com.app.empire.protocol.data.draw;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendDrawTypeList extends AbstractData {
	
	private	int[]	id;  	//物品ID
	private	String[]	icon;  	//relativePath/图标名称.png(资源会放到同一个目录下)
	private	String[]	name;  	//物品名称
	private String[]	miniIcon;  //小图标ICON
	private String		detail;

	
    public SendDrawTypeList(int sessionId, int serial) {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_SendDrawTypeList, sessionId, serial);
    }

    public SendDrawTypeList() {
        super(Protocol.MAIN_DRAW, Protocol.DRAW_SendDrawTypeList);
    }

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public String[] getIcon() {
		return icon;
	}

	public void setIcon(String[] icon) {
		this.icon = icon;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getMiniIcon() {
		return miniIcon;
	}

	public void setMiniIcon(String[] miniIcon) {
		this.miniIcon = miniIcon;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
