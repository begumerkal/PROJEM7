package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>NotifyMaxPlayer</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_SERVER下子命令SERVER_NotifyMaxPlayer(通知服务器最大在线人数)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class NotifyMaxPlayer extends AbstractData {
    private int  currentCount;
    private int  maxCount;
    private long currentTime;

    public NotifyMaxPlayer(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_NotifyMaxPlayer, sessionId, serial);
    }

    public NotifyMaxPlayer() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_NotifyMaxPlayer);
    }

    public int getCurrentCount() {
        return this.currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
