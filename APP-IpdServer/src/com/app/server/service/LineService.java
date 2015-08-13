package com.app.server.service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.app.empire.protocol.data.server.UpdateServerInfo;

public class LineService {
	private Map<Integer, UpdateServerInfo> addressMap = new ConcurrentHashMap<Integer, UpdateServerInfo>();

	public void addServer(UpdateServerInfo sendAddress) {
		addressMap.put(sendAddress.getLine(), sendAddress);
		ServiceManager
				.getManager()
				.getServerListService()
				.addServer(sendAddress.getLine(), sendAddress.getArea(), sendAddress.getGroup(), sendAddress.getMachineId(),
						sendAddress.getVersion(), sendAddress.getAddress(), "", "", "");
	}

	public void removeAll(int loneId) {
		if (addressMap.containsKey(loneId))
			addressMap.remove(loneId);
	}
}
