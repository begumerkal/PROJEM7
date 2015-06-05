package com.wyd.dispatch;

import java.net.ConnectException;
import java.net.InetSocketAddress;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.Login;

public class ipdServer {
	 
	private static final Logger log = Logger.getLogger(ipdServer.class);
	private IpdConnector connector;
	private String mode;
	private Configuration configuration;
	/**
	 * 初始化IP分配器，并连接IpdService服务器
	 * 
	 * @param configuration
	 */
	public ipdServer(Configuration configuration) {
		this.configuration = configuration;
		String ip = configuration.getString("ip");
		int port = configuration.getInt("port");
		this.connector = new IpdConnector("Ipd Connector", new InetSocketAddress(ip, port));
		this.mode = configuration.getString("servertype");
		ipdConnect();
	}

	/**
	 * 连接IpdService服务器
	 */
	public void ipdConnect() {
		try {
			if (this.connector.isConnected())
				this.connector.close();
			this.connector.connect();

			if (this.connector.isConnected())
				System.out.println("服务链接成功！");
			else
				System.out.println("服务链接失败！");
			sendData();
		} catch (ConnectException e) {
			log.warn("分配器连接失败！");
		}
	}
	
	public void sendData() {
		System.out.println("发送登录数据");
		Login login = new Login();
		login.setUdid("xxx1-22-2-2-2-3");
		login.setAccountName("doter");
		login.setPassWord("123456");
		login.setVersion("1.0.0.0");
		login.setChannel(1000);
		this.connector.send(login);
		
		
		
		
		
		
		
		
		
		
	}
 
}