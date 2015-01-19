package com.wyd.serial.dao.impl;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.serial.bean.App;
import com.wyd.serial.dao.IPayDao;
/**
 * The DAO class for the TabConsortiaright entity.
 */
public class PayDao extends UniversalDaoHibernate implements IPayDao {
    public PayDao() {
        super();
    }
    
    @SuppressWarnings("unchecked")
    public App getAppByNameEnglish(String nameEnglish) {
        List<App> appList = this.getList("from " + App.class.getSimpleName() + " where nameEnglish=?", new Object[] { nameEnglish});
        if (appList.size() > 0) {
            return appList.get(0);
        } else {
            return null;
        }
    }
}