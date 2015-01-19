package com.wyd.service.dao.impl;

import java.util.List;
import java.util.Vector;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.PlayerSinConsortia;
import com.wyd.service.dao.IPlayerSinConsortiaDao;

/**
 * The DAO class for the TabPlayersinconsortia entity.
 */
public class PlayerSinConsortiaDao extends UniversalDaoHibernate implements IPlayerSinConsortiaDao {
    
	public PlayerSinConsortiaDao() {
		super();
	}

	@Override
	 /**
     * 根据用户ID，获取所属公会(条件必须是这个玩家已经是公会的正式会员)
     * @param playerId  玩家ID
     * @return          所属于公会ID
     */
	@SuppressWarnings("unchecked")
    public PlayerSinConsortia findPlayerSinConsortiaByPlayerId(int playerId){
        StringBuilder hql = new StringBuilder();
        List<Object> values = new Vector<Object>();
        hql.append("FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psa WHERE 1 =  1 ");
        hql.append(" AND psa.player.id = ? ");
        values.add(playerId);
        hql.append(" AND psa.identity = ? ");
        values.add(1);
        List<PlayerSinConsortia> list = this.getList(hql.toString(), values.toArray());
        if(null!= list&&list.size()!=0){
        	return list.get(0);
        }
        return null;
	}
	
	
}