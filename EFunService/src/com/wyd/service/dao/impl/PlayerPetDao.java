package com.wyd.service.dao.impl;

import java.util.List;

import com.app.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.PlayerPet;
import com.wyd.service.dao.IPlayerPetDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PlayerPetDao extends UniversalDaoHibernate implements IPlayerPetDao {
	public PlayerPetDao() {
		super();
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public PlayerPet getInUsePet(int playerId) {
		String hql="FROM "+PlayerPet.class.getSimpleName()+" where playerId=? and isInUsed=true";
		Object[] values = new Object[]{playerId};
        List<PlayerPet> list = this.getList(hql.toString(), values);
        if(null!= list&&list.size()!=0){
        	return list.get(0);
        }
        return null;
	}

}