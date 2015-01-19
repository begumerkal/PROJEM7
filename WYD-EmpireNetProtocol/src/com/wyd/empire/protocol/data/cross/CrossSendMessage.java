package com.wyd.empire.protocol.data.cross;

import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.cross.CrossDate;

public class CrossSendMessage extends CrossDate {
    private int    roomId;
    private int    channel;
    private int    sendId;
    private String sendName;
    private int    receiveId;
    private String receiveName;
    private String message;
	public CrossSendMessage(int sessionId, int serial) {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossSendMessage, sessionId, serial);
	}

	public CrossSendMessage() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossSendMessage);
	}

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
