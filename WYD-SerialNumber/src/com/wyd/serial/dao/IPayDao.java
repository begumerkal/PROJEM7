package com.wyd.serial.dao;
import com.wyd.db.dao.UniversalDao;
import com.wyd.serial.bean.App;
/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IPayDao extends UniversalDao {
    /**
     * 根据应用的英文名称查询应用信息
     * @param code 应用的英文名称
     * @return
     */
    public App getAppByNameEnglish(String nameEnglish);
}