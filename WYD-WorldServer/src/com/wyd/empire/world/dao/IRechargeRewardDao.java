package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.RechargeReward;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IRechargeRewardDao extends UniversalDao {
	/**
	 * 查询出所有首冲奖励物品
	 * 
	 * @return
	 */
	public List<RechargeReward> findAllRechargeReward(Short type);

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
	public List<LotteryReward> getLotteryReward(int randomNum);

	/**
	 * 根据玩家ID查询玩家是否领取过首冲奖励
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getRechargeRewardLog(int playerId);

	/**
	 * 根据充值抽奖ID查询出充值抽奖对象
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public LotteryReward findLotteryRewardById(int id);

	/**
	 * 根据id首充奖励
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public RechargeReward getRechargeRewardById(int id);

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
	public PageList getRechargeRewardList(int pageNum, int pageSize);

}