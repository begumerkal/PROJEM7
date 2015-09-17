package com.app.empire.world.skeleton;

import java.net.InetSocketAddress;

import com.app.empire.protocol.data.server.Heartbeat;
import com.app.empire.protocol.data.server.WorldServerToAccountServer;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.net.Connector;
import com.app.protocol.data.AbstractData;
import com.app.protocol.handler.IDataHandler;

public class AccountSkeleton extends Connector implements IDataHandler {

	public AccountSkeleton(String id, InetSocketAddress address) {
		super(id, address);
	}

	@Override
	public AbstractData handle(AbstractData message) throws Exception {
		System.out.println("handle not found:" + message);
		return null;
	}
	@Override
	protected void connected() {
		System.out.println("账号服务器链接..成功！");
		
		String area = ServiceManager.getManager().getConfiguration().getString("area");
		String group = ServiceManager.getManager().getConfiguration().getString("group");
		String machinecode = ServiceManager.getManager().getConfiguration().getString("machinecode");
		WorldServerToAccountServer wta = new WorldServerToAccountServer();
		wta.setWorldServerId(area + "-" + group + "-" + machinecode);
		send(wta);
	}
	@Override
	protected void idle() {
		Heartbeat heart = new Heartbeat();
		send(heart);
	}
}