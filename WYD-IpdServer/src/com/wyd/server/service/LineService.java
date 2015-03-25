package com.wyd.server.service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.protocol.data.server.SendAddress;

public class LineService {
	private Map<Integer, SendAddress> addressMap = new ConcurrentHashMap<Integer, SendAddress>();

	public void addServer(SendAddress sendAddress) {
		addressMap.put(sendAddress.getId(), sendAddress);
		ServiceManager
				.getManager()
				.getServerListService()
				.addServer(sendAddress.getId(), sendAddress.getArea(), sendAddress.getGroup(), sendAddress.getServerId(),
						sendAddress.getVersion(), sendAddress.getAddress(), "", "", "");
	}

	public void removeAll(int loneId) {
		if (addressMap.containsKey(loneId))
			addressMap.remove(loneId);
	}
}
