package com.wyd.dispatch;

import org.apache.log4j.Logger;

import com.app.empire.protocol.Protocol;
import com.app.net.ProtocolFactory;

public class DisServer {
	private static final Logger log = Logger.getLogger(DisServer.class);
	/** 配置信息 */
	public static ConfigMenger configuration = null;

	public static void main(String[] args) throws Throwable {
		DisServer main = new DisServer();
		main.launch();
	}

	private void launch() throws Exception {
		// 初始化协议接口
		ProtocolFactory.init(Protocol.class, "com.app.empire.protocol.data", "com.app.empire.server.handler");
		// 加载配置文件
		configuration = new ConfigMenger("configDispatch.properties");
		// 初始化ipd服务，启动Ipdservice ,作为客户端，连接ipd server
		new ipdServer(configuration.getConfiguration());
	}
}