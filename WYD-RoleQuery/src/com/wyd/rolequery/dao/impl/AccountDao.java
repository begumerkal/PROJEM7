package com.wyd.rolequery.dao.impl;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.rolequery.bean.Empireaccount;
import com.wyd.rolequery.dao.IAccountDao;
/**
 * The DAO class for the TabConsortiaright entity.
 */
public class AccountDao extends UniversalDaoHibernate implements IAccountDao {
    public AccountDao() {
        super();
    }
    
    @SuppressWarnings("unchecked")
    public List<Empireaccount> getEmpireaccount(String userId, String serviceId) {
        return getList("from Empireaccount where name=? and serverid=?", new Object[] { userId, serviceId});
    }
}