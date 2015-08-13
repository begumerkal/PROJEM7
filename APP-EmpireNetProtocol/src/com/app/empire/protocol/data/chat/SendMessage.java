package com.app.empire.protocol.data.chat;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class SendMessage extends AbstractData {
    private int    channel;
    private String playerName;
    private String message;
    private int    playerId;   // 信息接收人ID（私聊时用,用名称时该字段为-1）

    public SendMessage(int sessionId, int serial) {
        super(Protocol.MAIN_CHAT, Protocol.CHAT_SendMessage, sessionId, serial);
    }

    public SendMessage() {
        super(Protocol.MAIN_CHAT, Protocol.CHAT_SendMessage);
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
