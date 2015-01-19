package com.wyd.empire.battle;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.Protocol;
import com.wyd.net.ProtocolFactory;
public class BattleServer { 
    private static final Logger log = Logger.getLogger(BattleServer.class);

    public void launch() throws Exception {
        ProtocolFactory.init(Protocol.class, "com.wyd.empire.protocol.data", "com.wyd.empire.battle.handler");
        ServiceManager.getManager().getSessionStub().start();
        log.info("Server Binded.");
        log.info("跨服对战服务启动...");
        System.out.println("跨服对战服务启动...");
        
    }

    public static void main(String[] args) throws Exception {
        BattleServer server = new BattleServer();
        server.launch();
    }
}
