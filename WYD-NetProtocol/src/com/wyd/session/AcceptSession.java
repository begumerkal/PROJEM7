package com.wyd.session;
import java.net.SocketAddress;
import org.apache.mina.core.session.IoSession;
import com.wyd.net.ISession;
import com.wyd.protocol.data.AbstractData;
public class AcceptSession implements ISession {
    protected String    id;
    protected IoSession session;
    protected boolean   valid = false;

    public AcceptSession(IoSession session) {
        this.session = session;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public boolean isConnected() {
        if (this.session == null) {
            return false;
        }
        return this.session.isConnected();
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void send(AbstractData message) {
        if ((this.session != null) && (this.session.isConnected())) {
            this.session.write(message);
        }
    }

    public void close() {
        if ((this.session != null) && (this.session.isConnected())) {
            this.session.close(true);
        }
    }

    public SocketAddress getRemoteAddress() {
        if (this.session != null) {
            return this.session.getRemoteAddress();
        }
        return null;
    }

    public boolean equals(ISession session) {
        if (this == session) return true;
        return this.id.equals(session.getId());
    }
}
