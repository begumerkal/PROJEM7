package com.wyd.net;
import java.net.SocketAddress;
import com.wyd.protocol.data.AbstractData;
public abstract interface ISession {
    public abstract String getId();

    public abstract boolean isConnected();

    public abstract boolean isValid();

    public abstract void send(AbstractData paramAbstractData);

    public abstract void close();

    public abstract SocketAddress getRemoteAddress();

    public abstract boolean equals(ISession paramISession);
}
