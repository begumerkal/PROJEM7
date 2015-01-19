package com.wyd.empire.world.server.handler.admin;

import com.wyd.empire.protocol.data.admin.Login;
import com.wyd.empire.protocol.data.admin.LoginResult;
import com.wyd.empire.world.bean.Admin;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code>LoginHandler</code>管理员登陆服务器。<li>实现IDataHandler接口</li>
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class LoginHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		Login login = (Login) data;
		LoginResult result = new LoginResult(data.getSessionId(), data.getSerial());
		result.setUserName(login.getName());

		Admin admin = ServiceManager.getManager().getAdminService().getService().getAdmin(login.getName());
		if (null == admin) {
			admin = new Admin();
			admin.setName(login.getName());
			admin.setPassword(login.getPassword());
			admin.setAuth("");
			admin.setChannel(0);
			admin.setType(0);
			ServiceManager.getManager().getAdminService().getService().save(admin);
		}

		admin = ServiceManager.getManager().getAdminService().getAdmin(login.getName(), login.getPassword());
		if (admin != null) {
			result.setMsg("登录成功");
			result.setLoginCode((byte) 0);
			session.login(admin);
			ServiceManager.getManager().getAdminService().registry(session);
		} else {
			result.setMsg("登录失败!");
			result.setLoginCode((byte) 1);
		}
		session.write(result);
	}
}