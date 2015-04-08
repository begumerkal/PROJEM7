package com.wyd.protocol.data;
import com.wyd.net.IConnector;
import com.wyd.session.Session;
/**
 * 抽象类 <code>AbstractData</code>对应前后台协议接口常量定义类Protocol传输时产生的对应协议抽象bean数据
 * @author mazheng
 *
 */
public abstract class AbstractData {
    protected byte     type;
    protected byte     subType;
    protected IConnector source;
    protected int      serial;
    protected int      sessionId;
    protected Session  handlerSource;
    protected byte     flag          = 0;
    private static int static_serial = 0;

    public AbstractData(byte type, byte subType, int sessionId, int serial) {
        this.type = type;
        this.subType = subType;
        this.serial = serial;
        this.sessionId = sessionId;
    }

    public AbstractData(byte type, byte subType) {
        this.type = type;
        this.subType = subType;
        this.source = null;
        this.serial = getIncrementSerial();
        this.sessionId = -1;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getSubType() {
        return this.subType;
    }

    public void setSubType(byte subType) {
        this.subType = subType;
    }

    public IConnector getSource() {
        return this.source;
    }

    public void setSource(IConnector session) {
        this.source = session;
    }

    public int getSerial() {
        return this.serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Session getHandlerSource() {
        return this.handlerSource;
    }

    public void setHandlerSource(Session handlerSource) {
        this.handlerSource = handlerSource;
    }

    public byte getFlag() {
        return this.flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public void addFlag(byte flag) {
        AbstractData tmp1_0 = this;
        tmp1_0.flag = (byte) (tmp1_0.flag | flag);
    }

    public void removeFlag(byte flag) {
        AbstractData tmp1_0 = this;
        tmp1_0.flag = (byte) (tmp1_0.flag & (flag ^ 0xFFFFFFFF));
    }

    public String getTypeString() {
        return this.type + "." + this.subType;
    }

    public static final synchronized int getIncrementSerial() {
        static_serial += 1;
        if (static_serial < 0) static_serial = 1;
        return static_serial;
    }
}
