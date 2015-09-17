package com.app.dispatch;
import java.nio.ByteBuffer;

import com.app.protocol.INetData;
public class Packet {
	public TYPE type = TYPE.BUFFER;
	public INetData data;
	public ByteBuffer buffer;
	public int sessionId = 0;
	public byte pType;
	public byte pSubType;

	public Packet(ByteBuffer buffer, int sessionId) {
		this.type = TYPE.BUFFER;
		this.buffer = buffer;
		this.sessionId = sessionId;
	}

	public Packet(INetData data) {
		this.type = TYPE.UWAPDATA;
		this.data = data;
	}
	public static enum TYPE {
		BUFFER, UWAPDATA;
	}
}