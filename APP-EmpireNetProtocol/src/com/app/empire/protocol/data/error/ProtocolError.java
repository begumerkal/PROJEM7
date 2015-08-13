package com.app.empire.protocol.data.error;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
import com.app.protocol.exception.ProtocolException;
/**
 * 类 <code>ProtocolError</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ERROR下子命令ERROR_ProtocolError(接口错误)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class ProtocolError extends AbstractData {
    private int    code;
    private String cause;

    public ProtocolError(byte type, byte subType, int sessionId, int serial) {
        super(type, subType, sessionId, serial);
        this.cause = "";
        addFlag(Protocol.MAIN_ERROR);
    }

    public ProtocolError(byte type, byte subType) {
        this(type, subType, -1, -1);
    }

    public ProtocolError() {
        this(Protocol.MAIN_ERROR, Protocol.ERROR_ProtocolError);
    }

    public ProtocolError(ProtocolException ex) {
        this(ex.getType(), ex.getSubType(), ex.getSessionId(), ex.getSerial());
        setCause(ex.getMessage());
        if (ex.getMessage() == null) setCause("");
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCause() {
        return this.cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
