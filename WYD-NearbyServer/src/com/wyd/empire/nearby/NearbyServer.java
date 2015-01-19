package com.wyd.empire.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.Protocol;
import com.wyd.net.ProtocolFactory;
public class NearbyServer { 
    private static final Logger log = Logger.getLogger(NearbyServer.class);

    public void launch() throws Exception {
        ProtocolFactory.init(Protocol.class, "com.wyd.empire.protocol.data", "com.wyd.empire.nearby.handler");
        ServiceManager.getManager().getSessionStub().start();
        log.info("Server Binded.");
        log.info("附近好友服务服务启动...");
        System.out.println("附近好友服务启动...");
        
    }

    public static void main(String[] args) throws Exception {
        NearbyServer server = new NearbyServer();
        server.launch();
    }
}
