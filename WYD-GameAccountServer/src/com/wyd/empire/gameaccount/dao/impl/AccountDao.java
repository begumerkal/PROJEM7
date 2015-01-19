package com.wyd.empire.gameaccount.dao.impl;
import java.util.ArrayList;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.dao.IAccountDao;
/**
 * 类 <code>AccountDaoImpl</code>执行与Account表相关数据库操作
 * 
 * @see com.wyd.accountserver.account.dao.impl.UniversalDaoHibernate
 * @author sunzx
 */
public class AccountDao extends UniversalDaoHibernate implements IAccountDao {
    public AccountDao() {
        super();
    }

    /**
     * 根据用户名取得相关账号信息
     * 
     * @param name
     *            用户名
     * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
     */
    public Account getAccountByName(String name) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Account.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append("and  username = ? ");
        values.add(name);
        return (Account) this.getUniqueResult(hsql.toString(), values.toArray());
    }

    /**
     * 根据UDID取得相关账号信息
     * 
     * @param udid
     *            UDID
     * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
     */
    public Account getAccountByUDID(String udid) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Account.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append("and  udid = ? ");
        values.add(udid);
        return (Account) this.getUniqueResult(hsql.toString(), values.toArray());
    }

    /**
     * 根据email获取帐号列表
     * 
     * @param email
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Account> getAccountByEmail(String email) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Account.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append("and  address = ? ");
        values.add(email);
        return getList(hsql.toString(), values.toArray());
    }

    /**
     * 根据用户名，密码取得相关账号信息
     * 
     * @param name
     *            用户名
     * @param password
     *            用户密码
     * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
     */
    public Account getAccountByNameAndPassword(String name, String password) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Account.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append("and  username = ? ");
        values.add(name);
        hsql.append("and  password = ? ");
        values.add(password);
        return (Account) this.getUniqueResult(hsql.toString(), values.toArray());
    }

    /**
     * 根据用户名，密码，用户状态取得相关账号信息
     * 
     * @param name
     *            用户名
     * @param password
     *            密码
     * @param status
     *            状态
     * @return <tt>账号信息</tt> 如果存在此用户名相关记录； <tt>null</tt> 如果不存在此用户相关记录。
     */
    public Account getAccountByNameAndPasswordAndStatus(String name, String password, int status) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Account.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append("and  username = ? ");
        values.add(name);
        hsql.append("and  password = ? ");
        values.add(password);
        hsql.append("and  status = ? ");
        values.add(Integer.valueOf(status));
        return (Account) this.getUniqueResult(hsql.toString(), values.toArray());
    }

    /**
     * 根据账号编号取得用户名
     * 
     * @param id
     *            账号编号
     * @return <tt>用户名</tt>
     */
    public String getAccountNameById(int id) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append("SELECT username FROM  " + Account.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append("and  id = ? ");
        values.add(id);
        return (String) this.getUniqueResult(hsql.toString(), values.toArray());
    }

    /**
     * 保存账号信息
     * 
     * @param account
     *            账号信息对象
     */
    public void create(Account account) {
        this.save(account);
    }

    /**
     * 更新账号信息 其中account 对象中 id 属性不能为空
     * 
     * @param account
     *            账号信息对象
     */
    public void update(Account account) {
        this.save(account);
    }
}
