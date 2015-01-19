package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerSingleMap;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.SingleMapDrop;
import com.wyd.empire.world.dao.ISingleMapDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class SingleMapDao extends UniversalDaoHibernate implements ISingleMapDao {
	public SingleMapDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SingleMapDrop> getDropList(int dropId) {
		String hql = "from SingleMapDrop where dropId=?";
		List<Object> values = new ArrayList<Object>();
		values.add(dropId);
		return getList(hql.toString(), values.toArray());
	}

	@Override
	public Map getMapById(int id) {
		return (Map) get(Map.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerSingleMap> findPlayerMapBy(int playerId) {
		String hql = "from PlayerSingleMap where playerId=?";
		List<Object> values = new ArrayList<Object>();
		values.add(playerId);
		return getList(hql.toString(), values.toArray());
	}

	@Override
	public PlayerSingleMap getPlayerSingleMap(int playerId, int mapId) {
		String hql = "from PlayerSingleMap where playerId=? and mapId=?";
		List<Object> values = new ArrayList<Object>();
		values.add(playerId);
		values.add(mapId);
		@SuppressWarnings("unchecked")
		List<PlayerSingleMap> results = getList(hql.toString(), values.toArray());
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerSingleMap> getPlayerSingleMap(int playerId) {
		String hql = "from PlayerSingleMap where playerId=?";
		List<Object> values = new ArrayList<Object>();
		values.add(playerId);
		return getList(hql.toString(), values.toArray());
	}

	@Override
	public Map getMaxPassMap(int playerId) {
		List<Object> values = new ArrayList<Object>();
		values.add(playerId);
		values.add(0);
		String hql = "from PlayerSingleMap where playerId=? and totalPassTimes>? order by mapId desc";
		@SuppressWarnings("unchecked")
		List<PlayerSingleMap> results = getList(hql.toString(), values.toArray());
		PlayerSingleMap playerSingleMap = results != null && results.size() > 0 ? results.get(0) : null;
		if (playerSingleMap == null)
			return null;
		return getMapById(playerSingleMap.getMapId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopItem> getShowItems(int dropId) {
		String hsql = "select DISTINCT shopItem1 from SingleMapDrop as smp where smp.dropId=? ";
		return getList(hsql, new Object[]{dropId});

	}
}