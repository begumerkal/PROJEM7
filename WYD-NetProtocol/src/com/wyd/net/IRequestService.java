package com.wyd.net;
public abstract interface IRequestService extends IService {
    public abstract void add(int paramInt, IRequest paramIRequest);

    public abstract IRequest remove(int paramInt);
}
