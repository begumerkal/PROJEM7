package com.wyd.empire.nearby.service;
import com.wyd.net.IService;
import com.wyd.net.ISession;
import com.wyd.protocol.data.AbstractData;
public abstract interface ISessionService extends IService {
    public abstract void addSession(ISession paramISession);

    public abstract void removeSession(ISession paramISession);

    public abstract ISession getSession(String paramString);
    
    public void sendMessageToAllService(AbstractData message);
}
