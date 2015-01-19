package com.wyd.exchange.server;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.exchange.bean.SelectInfo;
import com.wyd.exchange.bean.UpdateInfo;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface ICodeExchangeService extends UniversalManager{
    /**
     * 查询兑换码信息
     * @param selectInfo
     * @return
     */
    public PageList getItemList(SelectInfo selectInfo);
    /**
     * 发放兑换码
     * @param ids
     */
    public void extend(String ids);
    /**
     * 编辑信息
     * @param updateInfo
     */
    public void updateExchange(UpdateInfo updateInfo);
    /**
     * 获取某个用户某个批次的兑换数
     * 
     * @param playerId
     * @param batchNum
     */
    public int getExchangeCount(int playerId, String serviceId, String batchNum);
}