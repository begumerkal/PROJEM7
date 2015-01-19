package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.dao.IPetItemDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PetItemDao extends UniversalDaoHibernate implements IPetItemDao {
	public PetItemDao() {
		super();
	}

	@Override
	public PetItem getById(int id) {
		return getById(id);
	}

	/**
	 * 获得玩家宠物列表
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerPet> getPlayerPetList(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("From PlayerPet as pp where pp.playerId = ?");
		values.add(playerId);
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 获得所有可召唤宠物列表
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PetItem> getAllPetList() {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("From PetItem as pp where pp.callLevel != -1");
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 根据ID获取宠物列表
	 */
	@SuppressWarnings("unchecked")
	public List<PetItem> getPetList(String ids) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("From PetItem as pp where pp.id in (" + ids + ") ");
		return getList(hql.toString(), values.toArray());
	}
}