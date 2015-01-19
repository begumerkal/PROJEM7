package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.admin.AdminUser;
import com.wyd.empire.world.bean.Admin;
import com.wyd.empire.world.bean.Application;
import com.wyd.empire.world.dao.IAdminDao;
import com.wyd.empire.world.server.service.base.IAdminService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class AdminService extends UniversalManagerImpl implements IAdminService {
	Logger log = Logger.getLogger(AdminService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IAdminDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "AdminService";

	public AdminService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IAdminService getInstance(ApplicationContext context) {
		return (IAdminService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IAdminDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IAdminDao getDao() {
		return this.dao;
	}

	/**
	 * 检查访问的ip地址是否有权限
	 * 
	 * @param ip
	 * @return
	 */
	public boolean checkIp(String ip) {
		return dao.checkIp(ip);
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
	public int login(String userName, String passWord) {
		return dao.login(userName, passWord);
	}

	public Admin getAdmin(String userName, String passWord) {
		return dao.getAdmin(userName, passWord);
	}

	public Admin getAdmin(String userName) {
		return dao.getAdmin(userName);
	}

	/**
	 * 获取用户的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return 模块列表
	 */
	public List<Application> getApplicationByUserId(int userId) {
		return dao.getApplicationByUserId(userId);
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
		return dao.checkAppByUser(userId, appCode);
	}

	/**
	 * 获取所有的管理员
	 * 
	 * @return
	 */
	public List<AdminUser> getAllAdmin() {
		return dao.getAllAdmin();
	}

	/**
	 * 添加管理员
	 * 
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return true: 增加成功<br/>
	 *         false: 增加失败
	 */
	public boolean addAdmin(String userName, String passWord) {
		Admin admin = new Admin();
		admin.setName(userName);
		admin.setPassword(passWord);
		admin.setType(2);
		return this.dao.addAdmin(userName, admin);
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
		return dao.updateAdmin(userId, passWord);
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
		return dao.deleteAdmin(userId);
	}

	/**
	 * 编辑管理员权限
	 * 
	 * @param userId
	 * @param passWord
	 * @return
	 */
	public boolean updateAdminRights(int userId, String rightsIds) {
		return dao.updateAdminRights(userId, rightsIds);
	}

	/**
	 * 添加管理IP
	 * 
	 * @param allowip
	 * @return
	 */
	public boolean addAllowIp(String allowip) {
		return dao.addAllowIp(allowip);
	}

	/**
	 * 删除管理IP
	 * 
	 * @param ips
	 * @return
	 */
	public boolean deleteAllowIp(String ips) {
		return dao.deleteAllowIp(ips);
	}
}