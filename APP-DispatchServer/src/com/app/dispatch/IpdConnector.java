package com.app.dispatch;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.app.empire.protocol.data.server.Heartbeat;
import com.app.net.Connector;
public class IpdConnector extends Connector {
	private static final Logger log = Logger.getLogger(IpdConnector.class);
	/**
	 * 初始化Connector
	 * 
	 * @param id
	 * @param address
	 * @param configuration
	 */
	public IpdConnector(String id, InetSocketAddress address) {
		super(id, address);
	}

	/**
	 * 连接IPD Server服务器<br>
	 * 发送游戏登录服务器 id，名称，地址<br>
	 * 发送在线人数，最大人数限制，服务器状态
	 */
	@Override
	protected void connected() {
		System.out.println("链接ipd成功。。。");
		log.info("链接ipd成功。。。");
	}

	@Override
	protected void idle() {
		Heartbeat heart = new Heartbeat();
		send(heart);
	}
}