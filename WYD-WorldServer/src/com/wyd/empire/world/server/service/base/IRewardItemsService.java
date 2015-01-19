package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.FullServiceReward;
import com.wyd.empire.world.bean.RewardItems;
import com.wyd.empire.world.bean.Tips;
import com.wyd.empire.world.item.RewardItemsVo;

/**
 * The service interface for the TabTool entity.
 */
public interface IRewardItemsService extends UniversalManager {
	/**
	 * 随机获取8件奖励物品
	 * 
	 * @return
	 */
	public List<RewardItemsVo> getRewardItems();

	/**
	 * 获得所有提示
	 * 
	 * @return
	 */
	public List<Tips> getTips();

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