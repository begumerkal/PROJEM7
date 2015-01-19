package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.admin.AdminUser;
import com.wyd.empire.world.bean.Admin;
import com.wyd.empire.world.bean.Application;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IAdminService extends UniversalManager {

	/**
	 * 检查访问的ip地址是否有权限
	 * 
	 * @param request
	 * @return
	 */
	public boolean checkIp(String ip);

	/**
	 * 管理员登录
	 * 
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return 用户ID
	 */
	public int login(String userName, String passWord);

	/**
	 * 获取管理员对象
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public Admin getAdmin(String userName, String passWord);

	/**
	 * 获取管理员对象
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public Admin getAdmin(String userName);

	/**
	 * 获取用户的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return 模块列表
	 */
	public List<Application> getApplicationByUserId(int userId);

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
	public boolean checkAppByUser(int userId, String appCode);

	/**
	 * 获取所有的管理员
	 * 
	 * @return
	 */
	public List<AdminUser> getAllAdmin();

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
	public boolean addAdmin(String userName, String passWord);

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
	public boolean updateAdmin(int userId, String passWord);

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

	public boolean deleteAdmin(int userId);

	/**
	 * 编辑管理员权限
	 * 
	 * @param userId
	 * @param passWord
	 * @return
	 */
	public boolean updateAdminRights(int userId, String rightsIds);

	/**
	 * 添加管理IP
	 * 
	 * @param allowip
	 * @return
	 */
	public boolean addAllowIp(String allowip);

	/**
	 * 删除管理IP
	 * 
	 * @param ips
	 * @return
	 */
	public boolean deleteAllowIp(String ips);
}