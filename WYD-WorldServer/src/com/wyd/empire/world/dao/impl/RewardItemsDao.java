package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.dao.IRewardItemsDao;
import com.wyd.empire.world.entity.mysql.FullServiceReward;
import com.wyd.empire.world.entity.mysql.RewardItems;
import com.wyd.empire.world.entity.mysql.Tips;

/**
 * The DAO class for the TabTask entity.
 */
public class RewardItemsDao extends UniversalDaoHibernate implements IRewardItemsDao {
	public RewardItemsDao() {
		super();
	}

	/**
	 * 获取提示分页列表
	 * 
	 * @param pageNum
	 * @param mark
	 * @return
	 */
	public PageList getTipsList(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Tips.class.getSimpleName() + " ORDER BY id asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum, pageSize);
	}

	/**
	 * 获取通用物品奖励列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RewardItems> getRewardItemsList() {
		return getList("FROM " + RewardItems.class.getSimpleName() + " WHERE 1=1 ORDER BY id", new Object[]{});
	}

	/**
	 * 根据id查询通用物品奖励
	 * 
	 * @param id
	 * @return
	 */
	public RewardItems getRewardItemsById(int id) {
		return (RewardItems) this.getUniqueResult(" FROM RewardItems WHERE id = ? ", new Object[]{id});
	}

	/**
	 * 查询出所有全服物品奖励
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FullServiceReward> findAllFullServiceReward() {
		return getList("FROM FullServiceReward WHERE areaId = ?", new Object[]{WorldServer.config.getAreaId()});
	}

	/**
	 * 获取未发送的全服物品奖励
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FullServiceReward> findFullServiceRewardByStatus() {
		return getList("FROM FullServiceReward WHERE areaId = ? AND isAll = 'N' ", new Object[]{WorldServer.config.getAreaId()});
	}

	@SuppressWarnings("unchecked")
	public List<RewardItems> getRewardItemByRandom(int random) {
		return getList("FROM RewardItems as ri WHERE ri.start<=? and ?<=ri.end", new Object[]{random, random});
	}

	/**
	 * 根据多个ID值删除全服物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByIds(String ids) {
		this.execute(" DELETE FullServiceReward WHERE id in (" + ids + ")", new Object[]{});
	}
}