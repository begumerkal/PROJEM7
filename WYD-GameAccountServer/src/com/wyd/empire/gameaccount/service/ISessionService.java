package com.wyd.empire.gameaccount.service;
import com.wyd.net.IService;
import com.wyd.net.ISession;
public abstract interface ISessionService extends IService {
    public abstract void addSession(ISession paramISession);

    public abstract void removeSession(ISession paramISession);

    public abstract ISession getSession(String paramString);
}
