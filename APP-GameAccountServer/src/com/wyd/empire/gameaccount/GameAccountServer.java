package com.wyd.empire.gameaccount;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.protocol.Protocol;
import com.wyd.net.ProtocolFactory;
public class GameAccountServer {
	private static final Logger log = Logger.getLogger(GameAccountServer.class);
	private ApplicationContext context;

	public void launch() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProtocolFactory.init(Protocol.class, "com.wyd.empire.protocol.data", "com.wyd.empire.gameaccount.handler");
		ServiceFactory  sf = context.getBean(ServiceFactory.class);
		sf.setServiceFactory(sf);
		sf.getWorldStub().start();
		
		System.out.println("游戏分区帐号数据服务器启动...");
	}

	public static void main(String[] args) throws Exception {
		GameAccountServer server = new GameAccountServer();
		server.launch();
	}
}
