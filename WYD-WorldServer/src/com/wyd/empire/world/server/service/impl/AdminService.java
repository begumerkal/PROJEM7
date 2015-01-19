package com.wyd.empire.world.server.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.world.bean.Admin;
import com.wyd.empire.world.server.service.base.IAdminService;
import com.wyd.empire.world.session.AdminSession;

public class AdminService {
	private IAdminService adminService;
	private ConcurrentHashMap<Integer, AdminSession> id2session = new ConcurrentHashMap<Integer, AdminSession>();

	public AdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	public Admin getAdmin(String name, String password) {
		return this.adminService.getAdmin(name, password);
	}

	public IAdminService getService() {
		return adminService;
	}

	/**
	 * 发送对应对话信息给管理员
	 * 
	 * @param srcId
	 * @param srcName
	 * @param destId
	 * @param destName
	 * @param msg
	 * @return
	 */
	public boolean receiveChatMessage(int srcId, String srcName, int destId, String destName, String msg) {
		boolean ret = false;
		// Iterator ite = this.id2session.values().iterator();
		// while (ite.hasNext()) {
		// AdminSession session = (AdminSession) ite.next();
		for (AdminSession session : id2session.values()) {
			session.receiveChatMessage(srcId, srcName, destId, destName, msg);
			ret = true;
		}
		return ret;
	}

	/**
	 * 注册管理session
	 * 
	 * @param session
	 */
	public void registry(AdminSession session) {
		AdminSession s = this.id2session.get(session.getId());
		if (s != null) {
			s.close();
		}
		this.id2session.put(session.getId(), session);
	}
}