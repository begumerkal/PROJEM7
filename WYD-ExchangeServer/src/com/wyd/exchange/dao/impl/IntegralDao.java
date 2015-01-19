package com.wyd.exchange.dao.impl;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.exchange.bean.Integral;
import com.wyd.exchange.dao.IIntegralDao;
/**
 * The DAO class for the TabConsortiaright entity.
 */
public class IntegralDao extends UniversalDaoHibernate implements IIntegralDao {
    public IntegralDao() {
        super();
    }
    
    /**
     * 根据分区和玩家Id获取积分记录
     * @param playerId
     * @param area
     * @return
     */
    public Integral getIntegralByPlayerIdAndServiceId(int playerId,int serviceId){
    	StringBuffer hsql = new StringBuffer();
        hsql.append("from Integral where playerId=? and serviceId=?");
        return (Integral) this.getClassObj(hsql.toString(), new Object[]{playerId,serviceId});
    }
    
    /**
     * 获取指定组的积分排名
     * @param groupId 组id
     * @param maxResults 返回数量
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integral> getIntegralRank(int groupId, int maxResults) {
        StringBuffer hsql = new StringBuffer();
        hsql.append("from Integral where groupId=? order by integral desc");
        return getList(hsql.toString(), new Object[] { groupId}, maxResults);
    }
        
    /**
     * 每个区的玩家积分
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Integral> getIntegralListByServiceId(int serviceId){
    	StringBuffer hsql = new StringBuffer();
        hsql.append("from Integral where serviceId =?");
        return getList(hsql.toString(), new Object[]{serviceId});
    }
    
    
    /**
     * 清除挑战赛积分
     * @param serviceId
     */
    public void deleteIntegralOverSeason(int serviceId){
    	StringBuffer hsql = new StringBuffer();
        hsql.append("delete from Integral where serviceId = ?");
        execute(hsql.toString(), new Object[]{serviceId});
    }
}