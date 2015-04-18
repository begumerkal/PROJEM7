package com.wyd.empire.gameaccount.service;
import java.util.List;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.gameaccount.bean.Account;
public interface IAccountService extends UniversalManager {
	public static final int ACCOUNT_STATUS_INVALID = 0;// 无效
	public static final int ACCOUNT_STATUS_NORMAL = 1;// 正常
	public static final int ACCOUNT_STATUS_FREEZE = 2;// 冻结

	/**
	 * 创建<tt>Account</tt>，用户注册
	 * 
	 * @param name
	 * @param password
	 * @param udid
	 * @return
	 * @throws CreateAccountException
	 */
	public Account createAccount(String name, String password, String udid);

	/**
	 * 根据用户名取得相关账号信息
	 * 
	 * @param name
	 *            用户名
	 * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
	 */
	public Account getAccountByName(String name);

	/**
	 * 根据UDID取得相关账号信息
	 * 
	 * @param udid
	 *            UDID
	 * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
	 */
	public Account getAccountByUDID(String udid);

	/**
	 * 检查帐号是否可以使用
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkName(String name);

	/**
	 * 根据email获取帐号列表
	 * 
	 * @param email
	 * @return
	 */
	public List<Account> getAccountByEmail(String email);
}
