package com.wyd.dispatch;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.server.SyncLoad;
import com.wyd.empire.protocol.data.server.UpdateServerInfo;

public class IpdService implements Runnable {
	private BlockingQueue<SyncLoad> messages = new LinkedBlockingQueue<SyncLoad>();
	private static final Logger log = Logger.getLogger(IpdService.class);
	private IpdConnector connector;
	private String mode;

	/**
	 * 初始化IP分配器，并连接IpdService服务器
	 * 
	 * @param configuration
	 */
	public IpdService(Configuration configuration) {
		String ip = configuration.getString("ipdServer");
		int port = configuration.getInt("ipdPort");
		this.connector = new IpdConnector("Ipd Connector", new InetSocketAddress(ip, port), configuration);
		this.mode = configuration.getString("servertype");
		if (!(this.mode.equals("singlesocket")))
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
			new Thread(this, "IPD Service").start();

			if (this.connector.isConnected())
				System.out.println("ipd 服务链接成功！");
		} catch (ConnectException e) {
			log.warn("分配器连接失败！");
		}
	}

	public void connect(int current, int maxPlayer, boolean maintance) throws Exception {
		if ((this.connector != null) && (this.connector.isConnected())) {
			SyncLoad notify = new SyncLoad();
			notify.setCurrOnline(current);
			notify.setMaxOnline(maxPlayer);
			notify.setMaintance(maintance);
			this.messages.put(notify);
		}
	}

	/**
	 * 发送数据给ipdservice
	 * 
	 * @param data
	 */
	public void updateVersion(String area, String group, String machine, String version, String updateurl, String remark, String appraisal,
			int serverId) {
		UpdateServerInfo updateVers = new UpdateServerInfo();
		updateVers.setArea(area);
		updateVers.setGroup(group);
		updateVers.setMachine(machine);
		updateVers.setLine(Main.configuration.getConfiguration().getInt("id"));
		updateVers.setVersion(version);
		updateVers.setUpdateurl(updateurl);
		updateVers.setRemark(remark);
		updateVers.setAppraisal(appraisal);
		updateVers.setServerId(serverId);
		this.connector.send(updateVers);
	}

	public void run() {
		while (true) {
			try {
				SyncLoad notify = (SyncLoad) this.messages.take();
				this.connector.send(notify);
			} catch (InterruptedException ex1) {
				log.debug(ex1, ex1);
			}
		}
	}
}