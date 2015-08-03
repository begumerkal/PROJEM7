package com.wyd.dispatch;

import com.wyd.protocol.INetData;

public abstract interface ControlProcessor {
	public abstract void process(INetData paramINetData);
}