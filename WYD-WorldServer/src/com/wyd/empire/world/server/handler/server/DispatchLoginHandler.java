package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.server.DispatchLogin;
import com.wyd.empire.protocol.data.server.UpdateServerInfo;
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

	public AbstractData handle(AbstractData data) throws Exception {
		UpdateServerInfo updateData = null;
		DispatchLogin msg = (DispatchLogin) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			String id = msg.getId();
			String password = msg.getPassword();
			String serverPassword = ServiceManager.getManager().getConfiguration().getString("serverpassword");
			int maxPlayer = msg.getMaxPlayer();
			
			if ((password != null) && (password.equals(serverPassword))) {
				session.setName(id);
				session.setMaxPlayer(maxPlayer);
				
//				ServerLoginOk seg = new ServerLoginOk();
//				session.write(seg);
//				session.notifyMaxPlayer();
//				session.notifyMaintanceStatus();
				
				//更新服务器信息
				updateData = new UpdateServerInfo();
				updateData.setArea(Server.config.getArea());
				updateData.setGroup(Server.config.getGroup());
				updateData.setMachine(Server.config.getMachineCode() + "");
				updateData.setVersion(VersionUtils.select("num"));
				updateData.setUpdateurl(VersionUtils.select("updateurl"));
				updateData.setRemark(VersionUtils.select("remark"));
				updateData.setAppraisal(VersionUtils.select("appraisal"));
				updateData.setServerId(Server.config.getServerId());
//				session.write(updateData);
			}else{
				session.close();
			}
		} catch (Exception e) {
			this.log.error(e, e);
		}
		return updateData;
	}
}