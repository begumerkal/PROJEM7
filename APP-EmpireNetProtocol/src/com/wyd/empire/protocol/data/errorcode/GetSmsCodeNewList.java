package com.wyd.empire.protocol.data.errorcode;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 获取短代列表
 * @author cj
 *
 */
public class GetSmsCodeNewList extends AbstractData {
	private int channelId; //渠道号

	public GetSmsCodeNewList(int sessionId, int serial) {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_GetSmsCodeNewList,
				sessionId, serial);
	}

	public GetSmsCodeNewList() {
		super(Protocol.MAIN_ERRORCODE, Protocol.ERRORCODE_GetSmsCodeNewList);
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
}
