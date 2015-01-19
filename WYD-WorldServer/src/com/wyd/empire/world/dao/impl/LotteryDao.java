package com.wyd.empire.world.dao.impl;

import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.bean.WishItem;
import com.wyd.empire.world.dao.ILotteryDao;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class LotteryDao extends UniversalDaoHibernate implements ILotteryDao {
	public LotteryDao() {
		super();
	}

	/**
	 * 返回许愿结果
	 * 
	 * @return
	 */
	public WishItem getWishResult() {
		return null;
	}

	/**
	 * 获取许愿物品列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WishItem> getWishItemList() {
		String hql = "from " + WishItem.class.getSimpleName() + " wi where wi.type=0 or wi.type=1";
		return getList(hql, new Object[]{});
	}

	/**
	 * 根据id查询许愿
	 * 
	 * @param id
	 * @return
	 */
	public WishItem getById(int id) {
		return (WishItem) get(WishItem.class, id);
	}

	/**
	 * 获取祝福礼盒
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WishItem> getZflhList() {
		String hql = "from " + WishItem.class.getSimpleName() + " wi where wi.type>1";
		return getList(hql, new Object[]{});
	}

}