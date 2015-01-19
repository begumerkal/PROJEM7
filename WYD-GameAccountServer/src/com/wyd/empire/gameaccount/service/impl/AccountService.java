package com.wyd.empire.gameaccount.service.impl;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.dao.IAccountDao;
import com.wyd.empire.gameaccount.service.IAccountService;
public class AccountService extends UniversalManagerImpl implements IAccountService {
    public AccountService() {
        super();
    }
    /**
     * The dao instance injected by Spring.
     */
    private IAccountDao         accountDao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "AccountService";

    /**
     * Returns the singleton <code>AccountService</code> instance.
     */
    public static IAccountService getInstance(ApplicationContext context) {
        return (IAccountService) context.getBean(SERVICE_BEAN_ID);
    }

    public IAccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
        super.setDao(accountDao);
    }

    /**
     * 创建<tt>Account</tt>，用户注册
     * @param name
     * @param password
     * @param udid
     * @return
     * @throws CreateAccountException
     */
    public Account createAccount(String name, String password, String udid) {
        Account account = new Account();
        if (null != udid) account.setUdid(udid.trim());
        if (null != name) account.setUsername(name.trim());
        if (null != password) account.setPassword(password.trim());
        account.setStatus(ACCOUNT_STATUS_NORMAL);
        account.setCreatetime(new java.sql.Timestamp(new Date().getTime()));
        account = (Account) accountDao.save(account);
        return account;
    }

    /**
     * 根据用户名取得相关账号信息
     * 
     * @param name
     *            用户名
     * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
     */
    public Account getAccountByName(String name) {
        return this.accountDao.getAccountByName(name);
    }

    /**
     * 根据UDID取得相关账号信息
     * 
     * @param udid
     *            UDID
     * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
     */
    public Account getAccountByUDID(String udid) {
        return this.accountDao.getAccountByUDID(udid);
    }

    /**
     * 检查帐号是否可以使用
     * @param name
     * @return
     */
    public boolean checkName(String name) {
        if (null != getAccountByName(name)) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 根据email获取帐号列表
     * 
     * @param email
     * @return
     */
    public List<Account> getAccountByEmail(String email) {
        return this.accountDao.getAccountByEmail(email);
    }
}
