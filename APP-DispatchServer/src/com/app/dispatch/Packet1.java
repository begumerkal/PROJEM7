package com.app.dispatch;

import java.nio.ByteBuffer;

public class Packet1 {
	public TYPE type = TYPE.BUFFER;
	public ByteBuffer buffer;
	public int sessionId = 0;
	public int param = 0;

	public Packet1(ByteBuffer buffer, int sessionId) {
		this.type = TYPE.BUFFER;
		this.buffer = buffer;
		this.sessionId = sessionId;
	}

	public Packet1(int sessionId) {
		this.type = TYPE.CONTROL;
		this.sessionId = sessionId;
	}
	public static enum TYPE {
		BUFFER, CONTROL;
	}
}