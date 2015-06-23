package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.admin.AdminUser;
import com.wyd.empire.world.admin.Rights;
import com.wyd.empire.world.dao.IAdminDao;
import com.wyd.empire.world.entity.mysql.Admin;
import com.wyd.empire.world.entity.mysql.AdminAndApp;
import com.wyd.empire.world.entity.mysql.AllowIp;
import com.wyd.empire.world.entity.mysql.Application;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class AdminDao extends UniversalDaoHibernate implements IAdminDao {
	public AdminDao() {
		super();
	}

	/**
	 * 检查访问的ip地址是否有权限
	 * 
	 * @param request
	 * @return
	 */
	public boolean checkIp(String ip) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT COUNT(*) FROM " + AllowIp.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND ip = ? ");
		values.add(ip);
		long count = this.count(hql.toString(), values.toArray());
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 管理员登录
	 * 
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return 用户ID
	 */
	@SuppressWarnings("unchecked")
	public int login(String userName, String passWord) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT id FROM " + Admin.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND name = ? ");
		values.add(userName);
		hql.append(" AND password = ? ");
		values.add(passWord);
		List<Object> idList = this.getList(hql.toString(), values.toArray());
		if (idList != null && !idList.isEmpty()) {
			return (Integer) idList.get(0);
		} else {
			return 0;
		}
	}

	/**
	 * 获取管理员对象
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public Admin getAdmin(String userName, String passWord) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + Admin.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND name = ? ");
		values.add(userName);
		// hql.append(" AND password = ? ");
		// values.add(passWord);
		return (Admin) this.getClassObj(hql.toString(), values.toArray());
	}

	/**
	 * 获取管理员对象
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public Admin getAdmin(String userName) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + Admin.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND name = ? ");
		values.add(userName);
		return (Admin) this.getClassObj(hql.toString(), values.toArray());
	}

	/**
	 * 获取用户的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return 模块列表
	 */
	@SuppressWarnings("unchecked")
	public List<Application> getApplicationByUserId(int userId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT aaa.application FROM " + AdminAndApp.class.getSimpleName() + " AS aaa WHERE 1 = 1 ");
		hql.append(" AND aaa.admin.id = ? ");
		values.add(userId);
		return this.getList(hql.toString(), values.toArray());
	}

	/**
	 * 检查用户是否有访问该模块的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param appCode
	 *            模块编码
	 * @return true: 有权限<br/>
	 *         false: 无权限
	 */
	public boolean checkAppByUser(int userId, String appCode) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT COUNT(*) FROM " + AdminAndApp.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND admin.id = ? ");
		values.add(userId);
		hql.append(" AND application.code = ? ");
		values.add(appCode);
		long count = this.count(hql.toString(), values.toArray());
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加管理员
	 * 
	 * @param userName
	 *            用户名
	 * @param admin
	 *            管理对象
	 * @return true: 添加成功<br/>
	 *         false:添加失败
	 */
	public boolean addAdmin(String userName, Admin admin) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT id FROM " + Admin.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND name = ? ");
		values.add(userName);
		long count = this.count(hql.toString(), values.toArray());
		if (count > 0) {
			return false;
		} else {
			this.save(admin);
			return true;
		}
	}

	/**
	 * 更新管理员密码
	 * 
	 * @param userId
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return true: 修改成功<br/>
	 *         false:修改失败
	 */
	public boolean updateAdmin(int userId, String passWord) {
		try {
			this.execute("UPDATE " + Admin.class.getSimpleName() + " SET password = ? where id = ?", new Object[]{passWord, userId});
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 删除管理员
	 * 
	 * @param userId
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return true: 删除成功<br/>
	 *         false:删除失败
	 */

	public boolean deleteAdmin(int userId) {
		try {
			execute("DELETE FROM " + Admin.class.getSimpleName() + " WHERE id = ? AND type != 1", new Object[]{userId});
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 删除管理IP
	 * 
	 * @param ips
	 * @return
	 */
	public boolean deleteAllowIp(String ips) {
		try {
			this.execute("DELETE FROM " + AllowIp.class.getSimpleName() + " WHERE ip in (?)", new Object[]{ips});
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 获取所有的管理员
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdminUser> getAllAdmin() {
		List<Object[]> objList = getList(
				"select adm.id, adm.type, adm.name, app.id as appId, app.appName, (select count(aaa.id) from "
						+ AdminAndApp.class.getSimpleName() + " aaa where aaa.admin.id=adm.id and aaa.application=app.id) from "
						+ Admin.class.getSimpleName() + " adm, " + Application.class.getSimpleName() + " app order by adm.id, app.id",
				new Object[]{});
		Map<Integer, AdminUser> adminUserMap = new HashMap<Integer, AdminUser>();
		List<AdminUser> adminUserList = new ArrayList<AdminUser>();
		AdminUser adminUser;
		int userId;
		int userType;
		String userName;
		int appId;
		String appName;
		int appCount;
		Rights rights;
		for (Object[] objs : objList) {
			userId = Integer.parseInt(objs[0].toString());
			userType = Integer.parseInt(objs[1].toString());
			userName = objs[2].toString();
			appId = Integer.parseInt(objs[3].toString());
			appName = objs[4].toString();
			appCount = Integer.parseInt(objs[5].toString());
			rights = new Rights();
			rights.setId(appId);
			rights.setAppName(appName);
			rights.setAppCount(appCount);

			adminUser = adminUserMap.get(userId);
			if (null == adminUser) {
				adminUser = new AdminUser();
				adminUser.setUserId(userId);
				adminUser.setUserType(userType);
				adminUser.setUserName(userName);
				adminUserMap.put(userId, adminUser);
				adminUserList.add(adminUser);
			}
			adminUser.getRightsList().add(rights);
		}
		adminUserMap.clear();
		adminUserMap = null;
		return adminUserList;
	}

	/**
	 * 编辑管理员权限
	 * 
	 * @param userId
	 * @param passWord
	 * @return
	 */
	public boolean updateAdminRights(int userId, String rightsIds) {
		try {
			this.execute("delete from " + AdminAndApp.class.getSimpleName() + " where admin.id=?", new Object[]{userId});
			if (rightsIds.length() > 0) {
				String sql = "insert into tab_adminandapp (adminId,appId) values ";
				List<Integer> valueList = new ArrayList<Integer>();
				for (String rightsId : rightsIds.split(",")) {
					int appId = Integer.parseInt(rightsId);
					sql += "(?,?),";
					valueList.add(userId);
					valueList.add(appId);
				}
				sql = sql.substring(0, sql.length() - 1);
				this.executeSql(sql, valueList.toArray());
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 添加管理IP
	 * 
	 * @param allowip
	 * @return
	 */
	public boolean addAllowIp(String allowip) {
		try {
			this.executeSql("insert into tab_allowip (ip) values (?)", new Object[]{allowip});
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}