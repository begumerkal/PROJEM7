package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IPlayerCardsDao;
import com.wyd.empire.world.entity.mysql.CardGroup;
import com.wyd.empire.world.entity.mysql.CardMelt;
import com.wyd.empire.world.entity.mysql.DebirsMerge;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PlayerCardsDao extends UniversalDaoHibernate implements IPlayerCardsDao {
	public PlayerCardsDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DebirsMerge> getMegerConfigByLevel(int level) {
		String hql = "FROM " + DebirsMerge.class.getSimpleName() + " where level=?";
		Object[] values = new Object[]{level};
		return getList(hql, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CardMelt getCardMelt(int cardId) {
		String hql = "FROM " + CardMelt.class.getSimpleName() + " where shopItemId=?";
		Object[] values = new Object[]{cardId};
		List<CardMelt> list = getList(hql, values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取套卡属性
	 * 
	 * @param groupId
	 * @param num
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CardGroup getCardGroup(int groupId, int num) {
		String hql = "FROM " + CardGroup.class.getSimpleName() + " where groupId=? and num=?";
		Object[] values = new Object[]{groupId, num};
		List<CardGroup> list = getList(hql, values);
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

}