package com.wyd.service.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.service.bean.Player;
import com.wyd.service.dao.IPlayerDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PlayerDao extends UniversalDaoHibernate implements IPlayerDao {

	public PlayerDao() {
		super();
	}

	@Override
	public Player getById(int playerId) {
		return (Player)get(Player.class, playerId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getByAccountId(int accountId) {
		String hql = "from " + Player.class.getSimpleName() + " where accountId=? order by id desc";
		return getList(hql, new Object[] { accountId});
	}

	@Override
	public PageList findPageList(int areaId, int index, int size) {
		String filter="",hql="from Player p where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		if(areaId>-1){
			filter=" and areaId=?";
			values.add(areaId);
		}
		hql+=filter;
		String countHql = "SELECT COUNT(p.id) from Player p where 1=1 "+filter;
        return getPageList(hql, countHql, values.toArray(), index - 1, size);
	}

	

}