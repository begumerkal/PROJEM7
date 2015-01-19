package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>Kick</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_SERVER下子命令SERVER_Kick(踢出玩家)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class Kick extends AbstractData {
    private int session;

    public Kick(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_Kick, sessionId, serial);
    }

    public Kick() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_Kick);
    }

    public int getSession() {
        return this.session;
    }

    public void setSession(int session) {
        this.session = session;
    }
}
