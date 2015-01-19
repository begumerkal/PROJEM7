package com.wyd.rolequery.dao;
import java.util.List;
import com.wyd.db.dao.UniversalDao;
import com.wyd.rolequery.bean.Empireaccount;
/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IAccountDao extends UniversalDao {
    /**
     * 根据帐号获取分区信息
     * @param userId
     * @return
     */
    public List<Empireaccount> getEmpireaccount(String userId, String serviceId);
}