package com.wyd.serial.server;

import com.wyd.db.service.UniversalManager;
import com.wyd.serial.bean.App;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IPayService extends UniversalManager{
    /**
     * 根据应用的英文名称查询应用信息
     * @param code 应用的英文名称
     * @return
     */
    public App getAppByNameEnglish(String nameEnglish);
}