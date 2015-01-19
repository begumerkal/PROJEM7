package com.wyd.exchange.dao;
import java.util.List;
import com.wyd.db.dao.UniversalDao;
import com.wyd.exchange.bean.Integral;
/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IIntegralDao extends UniversalDao {
    /**
     * 根据分区和玩家Id获取积分记录
     * @param playerId
     * @param area
     * @return
     */
    public Integral getIntegralByPlayerIdAndServiceId(int playerId,int serviceId);
    
    /**
     * 获取指定组的积分排名
     * @param groupId 组id
     * @param maxResults 返回数量
     * @return
     */
    public List<Integral> getIntegralRank(int groupId, int maxResults);
    
    /**
     * 每个区的玩家积分
     * @return
     */
    public List<Integral> getIntegralListByServiceId(int serviceId);
    
    /**
     * 清除挑战赛积分
     * @param serviceId
     */
    public void deleteIntegralOverSeason(int serviceId);
}