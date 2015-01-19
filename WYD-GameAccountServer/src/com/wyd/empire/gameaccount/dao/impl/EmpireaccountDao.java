package com.wyd.empire.gameaccount.dao.impl;
import java.util.ArrayList;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.gameaccount.bean.Empireaccount;
import com.wyd.empire.gameaccount.dao.IEmpireaccountDao;
public class EmpireaccountDao extends UniversalDaoHibernate implements IEmpireaccountDao {
    public EmpireaccountDao() {
        super();
    }

    /**
     * 根据角色id，取得角色账号信息
     * 
     * @param accountId
     *            角色id
     * @return
     */
    public Empireaccount getGameAccountByAccountId(int accountId, String serverId) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Empireaccount.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append(" AND accountid = ? ");
        values.add(accountId);
        hsql.append(" AND serverId = ? ");
        values.add(serverId);
        return (Empireaccount) this.getUniqueResult(hsql.toString(), values.toArray());
    }

    /**
     * 根据角色名称，取得角色账号信息
     * 
     * @param name
     *            角色名称
     * @return
     */
    public Empireaccount getGameAccountByName(String name, String serverId) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Empireaccount.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append(" AND name = ? ");
        values.add(name);
        hsql.append(" AND serverId = ? ");
        values.add(serverId);
        return (Empireaccount) this.getUniqueResult(hsql.toString(), values.toArray());
    }

    /**
     * 保存游戏角色账号信息
     * 
     * @param account
     *            游戏角色账号对象
     */
    public Empireaccount create(Empireaccount account) {
        return (Empireaccount) this.save(account);
    }

    /**
     * 更新游戏角色账号信息
     * 
     * @param account
     *            游戏角色账号对象
     */
    public Empireaccount update(Empireaccount account) {
        return (Empireaccount) this.save(account);
    }

    /**
     * 根据角色账号id，取得游戏角色账号用户名
     * @param id 角色账号id
     * @return <tt>GameAccountName</tt>游戏角色账号用户名
     */
    @SuppressWarnings("unchecked")
    public String getNameByAccountId(int accountId) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append("SELECT name FROM " + Empireaccount.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append(" AND accountid = ? ");
        values.add(accountId);
        List<String> names = this.getList(hsql.toString(), values.toArray());
        if(names.size()>0)
        	return names.get(0);
        else
        	return null;
    }

    @Override
    public Empireaccount getGameAccountById(int id) {
        return (Empireaccount) this.get(Empireaccount.class, id);
    }
    
    /**
     * 根据角色名称，取得角色账号信息
     * 
     * @param name
     *            角色名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Empireaccount> getGameAccountByName(String name) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + Empireaccount.class.getSimpleName() + " WHERE 1 = 1 ");
        hsql.append(" AND name = ? ");
        values.add(name);
        return this.getList(hsql.toString(), values.toArray());
    }
    
    public void setClientInfo(int accountId, String clientModel, String systemName, String systemVersion){
        StringBuffer hsql = new StringBuffer();
        hsql.append("UPDATE ");
        hsql.append(Empireaccount.class.getSimpleName());
        hsql.append(" SET clientModel = ? ");
        hsql.append(" ,systemName = ? ");
        hsql.append(" ,systemVersion = ? ");
        hsql.append(" WHERE id = ? ");
        List<Object> values = new ArrayList<Object>();
        values.add(clientModel);
        values.add(systemName);
        values.add(systemVersion);
        values.add(accountId);
        this.execute(hsql.toString(), values.toArray());
    }
}
