package com.app.empire.protocol.data.task;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * 成功发送微博协议
 * @author sunzx
 *
 */
public class SendWeiBo extends AbstractData {
    public SendWeiBo(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendWeiBo, sessionId, serial);
    }

    public SendWeiBo() {
        super(Protocol.MAIN_TASK, Protocol.TASK_SendWeiBo);
    }
}
