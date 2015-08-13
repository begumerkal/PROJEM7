package com.app.empire.protocol.data.chat;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SyncChannels</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_CHANNEL下子命令CHANNEL_SyncChannels(同步玩家频道设置)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class SyncChannels extends AbstractData {
    private int      toSession;
    private String[] add;
    private String[] remove;

    public SyncChannels(int sessionId, int serial) {
        super(Protocol.MAIN_CHAT, Protocol.CHAT_SyncChannels, sessionId, serial);
    }

    public SyncChannels() {
        super(Protocol.MAIN_CHAT, Protocol.CHAT_SyncChannels);
    }

    public int getToSession() {
        return this.toSession;
    }

    public void setToSession(int toSession) {
        this.toSession = toSession;
    }

    public String[] getAdd() {
        return this.add;
    }

    public void setAdd(String[] add) {
        this.add = add;
    }

    public String[] getRemove() {
        return this.remove;
    }

    public void setRemove(String[] remove) {
        this.remove = remove;
    }
}
