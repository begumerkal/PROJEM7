package com.wyd.service.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.BuffRecord;
import com.wyd.service.bean.Consortia;
import com.wyd.service.bean.PlayerSinConsortia;
import com.wyd.service.dao.IConsortiaDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class ConsortiaDao extends UniversalDaoHibernate implements IConsortiaDao {
	public ConsortiaDao() {
		super();
	}
	/**
     * 获得玩家的buff记录
     * @param playerId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<BuffRecord> getBuffRecordByPlayerId(int playerId){
    	 StringBuffer hql = new StringBuffer();
         List<Object> values = new ArrayList<Object>();
         hql.append("FROM " + BuffRecord.class.getSimpleName() + " WHERE 1 = 1 ");
         hql.append(" AND playerId = ? ");
         values.add(playerId);
         hql.append(" AND endtime > now() ");
         List<BuffRecord> list = getList(hql.toString(), values.toArray());
         return list;
    }
    /**
	 * 根据玩家Id获取公会对象
	 * @param playerId 玩家Id
	 * @return         公会对象
	 */
	@SuppressWarnings("unchecked")
	public Consortia getConsortiaByPlayerId(int playerId){
		StringBuffer hql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hql.append(" SELECT pc.consortia FROM " + PlayerSinConsortia.class.getSimpleName() + " AS pc WHERE 1 = 1 ");
        hql.append(" AND pc.player.id = ? ");
        values.add(playerId);
        hql.append(" AND pc.identity = ? ");
        values.add(1);
		List<Consortia> list = getList(hql.toString(), values.toArray());
		if(list != null && !list.isEmpty()){
            return list.get(0);
		}else{
            return null;      
        }
	}
	

}