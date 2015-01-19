package com.wyd.server.service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.protocol.data.server.SendAddress;
import com.wyd.empire.protocol.data.server.UpdateVers;
public class LineService {
    private Map<Integer, SendAddress> sendAddressMap = new ConcurrentHashMap<Integer, SendAddress>();
//    int id, String area, String group,int serverId, String version, String updateurl,String appraisal,String bulletin
    
    public void addSendAddress(SendAddress sendAddress){
        sendAddressMap.put(sendAddress.getId(), sendAddress);
            ServiceManager.getManager().getServerListService().addServer(sendAddress.getId(), sendAddress.getArea(),
            		sendAddress.getGroup(), sendAddress.getServerId(),"","","","");
    }

    public void removeAll(int loneId) {
        if (sendAddressMap.containsKey(loneId)) sendAddressMap.remove(loneId);
    }
}
