package com.wyd.rolequery.server;

import java.util.List;
import com.wyd.rolequery.bean.Empireaccount;
import com.wyd.db.service.UniversalManager;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IAccountService extends UniversalManager{
    /**
     * 根据帐号获取分区信息
     * @param userId
     * @return
     */
    public List<Empireaccount> getEmpireaccount(String userId, String serviceId);
}