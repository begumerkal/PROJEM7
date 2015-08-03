package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class BroadCast extends AbstractData {
    private String channel;
    private byte[] data;

    public BroadCast(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_BroadCast, sessionId, serial);
    }

    public BroadCast() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_BroadCast);
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
