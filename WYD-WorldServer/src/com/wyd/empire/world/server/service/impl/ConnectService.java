package com.wyd.empire.world.server.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;

public class ConnectService implements Runnable {
	private ConnectSession[] connects = new ConnectSession[10];
	private NioSocketAcceptor acceptor;
	private AtomicInteger ids = new AtomicInteger(1);
	public long lastOperationTime = 0L;

	public void start() {
		Thread t = new Thread(this);
		t.setName("ConnectService-Thread");
		t.start();
	}

	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public void stop() {
		this.acceptor.unbind();
	}

	public void addConnect(ConnectSession session) {
		int id = this.ids.incrementAndGet();
		synchronized (this) {
			for (int i = 0; i < this.connects.length; ++i)
				if (this.connects[i] == null) {
					this.connects[i] = session;
					session.setId(id);
					break;
				}
		}
	}

	public void broadcast(AbstractData seg) {
		for (int i = 0; i < this.connects.length; ++i)
			if (this.connects[i] != null)
				this.connects[i].write(seg);
	}

	/**
	 * 发送对应数据包信息给所有注册链接服务器
	 * 
	 * @param seg
	 * @param playerId
	 */
	public void writeTo(AbstractData seg, int playerId) {
		for (int i = 0; i < this.connects.length; ++i)
			if (this.connects[i] != null)
				this.connects[i].write(seg, playerId);
	}

	public void removeConnect(ConnectSession session) {
		synchronized (this) {
			for (int i = 0; i < this.connects.length; ++i)
				if (this.connects[i] == session)
					this.connects[i] = null;
		}
	}

	public ConnectSession[] getConnectSession() {
		return this.connects;
	}

	public void shutdown() {
		for (int i = 0; i < this.connects.length; ++i)
			if (this.connects[i] != null)
				this.connects[i].shutdown();
	}

	public void logOnline() {
		for (int i = 0; i < this.connects.length; ++i)
			if (this.connects[i] != null)
				this.connects[i].loginOnline();
	}

	public int getOnline() {
		int ret = 0;
		for (int i = 0; i < this.connects.length; ++i) {
			if (this.connects[i] != null)
				ret += this.connects[i].sessionSize();
		}
		return ret;
	}

	/**
	 * 根据账号id，注销账号
	 * 
	 * @param accountId
	 *            据账号id
	 */
	public void forceLogout(int accountId) {
		for (int i = 0; i < this.connects.length; ++i)
			if (this.connects[i] != null)
				this.connects[i].forceLogout(accountId);
	}

	public void kick(int playerId) {
		for (int i = 0; i < this.connects.length; ++i)
			if (this.connects[i] != null)
				this.connects[i].kick(playerId);
	}

	public void run() {
		while (true) {
			lastOperationTime = System.currentTimeMillis();
			try {
				Thread.sleep(90000L);
			} catch (InterruptedException ex) {
			}
			try {
				for (int i = 0; i < this.connects.length; ++i)
					if (this.connects[i] != null)
						this.connects[i].notifyMaxPlayer();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

//	/**
//	 * 更新服务器版本
//	 */
//	public void UpdateVersion() {
//		UpdateServerInfo updateVers = new UpdateServerInfo();
//		updateVers.setArea(WorldServer.config.getArea());
//		updateVers.setGroup(WorldServer.config.getGroup());
//		updateVers.setMachine(WorldServer.config.getMachineCode() + "");
//		updateVers.setVersion(VersionUtils.select("num"));
//		updateVers.setUpdateurl(VersionUtils.select("updateurl"));
//		updateVers.setRemark(VersionUtils.select("remark"));
//		updateVers.setAppraisal(VersionUtils.select("appraisal"));
//		for (int i = 0; i < this.connects.length; ++i)
//			if (this.connects[i] != null)
//				this.connects[i].write(updateVers);
//	}
}