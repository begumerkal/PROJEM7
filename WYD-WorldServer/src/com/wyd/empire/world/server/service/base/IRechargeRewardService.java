package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IRechargeRewardService extends UniversalManager {
	/**
	 * 查询出所有首冲奖励物品
	 * 
	 * @return
	 */
	public List<RechargeReward> findAllRechargeReward();

	/**
	 * 查询出所有首冲奖励物品
	 * 
	 * @return
	 */
	public List<RechargeReward> findAllEveryDayRechargeReward();

	/**
	 * 查询出所有抽奖奖励物品
	 * 
	 * @return
	 */
	public List<LotteryReward> findAllLotteryReward();

	/**
	 * 随机获取抽奖奖励物品
	 * 
	 * @param randomNum
	 *            随机概率数
	 * @return
	 */
	public LotteryReward getLotteryReward(int randomNum);

	/**
	 * 根据玩家ID查询玩家是否领取过首冲奖励
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public boolean getRechargeRewardLog(int playerId);

	/**
	 * 初始化首冲奖励、抽奖列表
	 */
	public void findInitList();

	/**
	 * 给予强化装备物品
	 * 
	 * @param player
	 *            玩家对象
	 * @param count
	 *            使用个数
	 * @param day
	 *            使用天数
	 * @param shopItemId
	 *            商品ID
	 * @param strongLevel
	 *            强化等级
	 * @param remark
	 *            GM工具物品给予时做备注说明
	 */
	public void givenItems(WorldPlayer player, int count, int day, int shopItemId, int strongLevel, String remark) throws Exception;

	/**
	 * 根据充值抽奖ID查询出充值抽奖对象
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public LotteryReward findLotteryRewardById(int id);

	/**
	 * 获取强化概率分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getSuccessrateList(int pageNum, int pageSize);

	/**
	 * 获取首充奖励分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Object> getRechargeRewardList(int pageNum, int pageSize);

	/**
	 * 根据id首充奖励
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public RechargeReward getRechargeRewardById(int id);
}