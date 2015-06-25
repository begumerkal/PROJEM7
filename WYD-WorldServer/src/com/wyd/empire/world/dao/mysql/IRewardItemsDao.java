package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.FullServiceReward;
import com.wyd.empire.world.entity.mysql.RewardItems;

/**
 * The DAO interface for the TabTask entity.
 */
public interface IRewardItemsDao extends UniversalDao {
	/**
	 * 获取提示分页列表
	 * 
	 * @param pageNum
	 * @param mark
	 * @return
	 */
	public PageList getTipsList(int pageNum, int pageSize);

	/**
	 * 获取通用物品奖励列表
	 * 
	 * @return
	 */
	public List<RewardItems> getRewardItemsList();

	/**
	 * 根据id查询通用物品奖励
	 * 
	 * @param id
	 * @return
	 */
	public RewardItems getRewardItemsById(int id);

	/**
	 * 根据随机数获取奖励物品
	 * 
	 * @param random
	 * @return
	 */
	public List<RewardItems> getRewardItemByRandom(int random);

	/**
	 * 查询出所有全服物品奖励
	 * 
	 * @return
	 */
	public List<FullServiceReward> findAllFullServiceReward();

	/**
	 * 获取未发送的全服物品奖励
	 * 
	 * @return
	 */
	public List<FullServiceReward> findFullServiceRewardByStatus();

	/**
	 * 根据多个ID值删除全服物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByIds(String ids);
}