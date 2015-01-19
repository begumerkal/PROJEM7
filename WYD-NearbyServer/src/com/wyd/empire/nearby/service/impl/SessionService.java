package com.wyd.empire.nearby.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.wyd.empire.nearby.service.ISessionService;
import com.wyd.net.ISession;
import com.wyd.protocol.data.AbstractData;
public class SessionService implements ISessionService {
    protected Map<String, ISession> name2sessions;
    protected ReadWriteLock         lock;

    public SessionService() {
        this.name2sessions = new HashMap<String, ISession>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void addSession(ISession session) {
        this.lock.writeLock().lock();
        try {
            this.name2sessions.put(session.getId(), session);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public ISession getSession(String id) {
        this.lock.readLock().lock();
        try {
            ISession localISession = (ISession) this.name2sessions.get(id);
            return localISession;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public void removeSession(ISession session) {
        this.lock.writeLock().lock();
        try {
            ISession old = (ISession) this.name2sessions.get(session.getId());
            if (old == session) this.name2sessions.remove(session.getId());
        } finally {
            this.lock.writeLock().unlock();
        }
    }
    
    public void sendMessageToAllService(AbstractData message) {
        List<ISession> sessionList = new ArrayList<ISession>(name2sessions.values());
        for (ISession session : sessionList) {
            session.send(message);
        }
    }
}
