package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>LoginOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ACCOUNT下子命令ACCOUNT_LoginOk(账户登录成功)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class NoviceSteps extends AbstractData {
    private int steps;

    public NoviceSteps(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_NoviceSteps, sessionId, serial);
    }

    public NoviceSteps() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_NoviceSteps);
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
