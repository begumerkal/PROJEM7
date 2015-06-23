package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.ISpreeGiftDao;
import com.wyd.empire.world.entity.mysql.SpreeGift;

/**
 * The DAO class for the TabSpreeGift entity.
 */
public class SpreeGiftDao extends UniversalDaoHibernate implements ISpreeGiftDao {
	public SpreeGiftDao() {
		super();
	}

	/**
	 * 返回开启礼包结果
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SpreeGift> getSpreeGiftResult(int shopItemId, int type) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + SpreeGift.class.getSimpleName() + "  WHERE 1 = 1 ");
		hql.append(" AND shopItemId =  ? ");
		values.add(shopItemId);
		if (type == 13) {
			int romdomNum = ServiceUtils.getRandomNum(0, 100000);
			hql.append(" AND  start_chance <= ? AND ? < end_chance ");
			values.add(romdomNum);
			values.add(romdomNum);
		}

		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 判断是否已经有此礼包的信息
	 * 
	 * @param itmeId
	 * @return
	 */
	public boolean isExistShopItmeId(int itmeId) {
		if (getList("FROM " + SpreeGift.class.getSimpleName() + "  WHERE 1 = 1  AND shopItemId =  ? ", new Object[]{itmeId}).size() == 0) {
			return true;
		}
		return false;
	}
}