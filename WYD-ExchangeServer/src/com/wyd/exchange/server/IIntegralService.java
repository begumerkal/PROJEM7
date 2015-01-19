package com.wyd.exchange.server;

import java.util.List;
import com.wyd.db.service.UniversalManager;
import com.wyd.exchange.bean.GroupIntegral;
import com.wyd.exchange.bean.InteRank;
import com.wyd.exchange.bean.Integral;
import com.wyd.exchange.bean.IntegralInfo;

/**
 * The service interface for the TabIntegral entity.
 */
public interface IIntegralService extends UniversalManager{
	
    /**
     * 初始化弹王分组信息
     */
    public void initData();
    
    /**
     * 获取指定服的分组id
     * @param serviceId
     * @return
     */
    public int getGroupIdByServiceId(Integer serviceId);
    
    /**
     * 获取指定服的组信息
     * @param serviceId
     * @return
     */
    public GroupIntegral getGroupByServiceId(String serviceId);
    
    /**
     * 更新积分
     * @param integralInfo
     */
    public void updatePlayerIntegral(IntegralInfo integralInfo);
    
    /**
     * 根据分区和玩家Id获取积分记录
     * @param playerId
     * @param serviceId
     * @return
     */
    public Integral getIntegralByPlayerIdAndArea(int playerId, int serviceId);
    
    /**
     * 清除挑战赛积分
     * @param area
     */
    public void deleteIntegralOverSeason(int serviceId);
    
    /**
     * 获取指定组的积分排名
     * @param groupId 组id
     * @param maxResults 返回数量
     * @return
     */
    public List<Integral> getIntegralRank(int groupId, int maxResults);
    
    /**
     * 获取指定服的弹王排名
     * @param groupId
     * @param serviceId
     * @return
     */
    public List<InteRank> getRankInfoByService(int groupId, int serviceId);
}