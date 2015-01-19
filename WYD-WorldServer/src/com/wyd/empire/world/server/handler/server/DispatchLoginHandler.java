package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.server.DispatchLogin;
import com.wyd.empire.protocol.data.server.ServerLoginOk;
import com.wyd.empire.protocol.data.server.UpdateVers;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.common.util.VersionUtils;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code>DispatchLoginHandler</code>分发服务器登陆协议处理
 * 
 * @since JDK 1.6
 */
public class DispatchLoginHandler implements IDataHandler {
	Logger log;

	public DispatchLoginHandler() {
		this.log = Logger.getLogger(DispatchLoginHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		boolean success = false;
		DispatchLogin msg = (DispatchLogin) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			String id = msg.getId();
			String password = msg.getPassword();
			String serverPassword = ServiceManager.getManager().getConfiguration().getString("serverpassword");
			int maxPlayer = msg.getMaxPlayer();
			session.setMaxPlayer(maxPlayer);
			if ((password != null) && (password.equals(serverPassword))) {
				session.setName(id);
				ServerLoginOk seg = new ServerLoginOk();
				session.write(seg);
				success = true;
				long current = System.currentTimeMillis();
				session.notifyMaxPlayer(current);
				session.notifyMaintanceStatus();

				UpdateVers updateVers = new UpdateVers();
				updateVers.setArea(Server.config.getArea());
				updateVers.setGroup(Server.config.getGroup());
				updateVers.setMachine(Server.config.getMachineCode() + "");
				updateVers.setVersion(VersionUtils.select("num"));
				updateVers.setUpdateurl(VersionUtils.select("updateurl"));
				updateVers.setRemark(VersionUtils.select("remark"));
				updateVers.setAppraisal(VersionUtils.select("appraisal"));
				updateVers.setServerId(Server.config.getServerId());
				session.write(updateVers);
			}
		} catch (Exception e) {
			this.log.error(e, e);
		}
		if (!(success))
			session.close();
	}
}