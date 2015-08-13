package com.app.empire.protocol.data.nearby;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class SendNearbyMail extends AbstractData {
    private String theme;
    private int senderId;
    private int receiverId;
    private String sendName;
    private String receiverName;
    private String content;

    public SendNearbyMail(int sessionId, int serial) {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_SendNearbyMail, sessionId, serial);
    }

    public SendNearbyMail() {
        super(Protocol.MAIN_NEARBY, Protocol.NEARBY_SendNearbyMail);
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
