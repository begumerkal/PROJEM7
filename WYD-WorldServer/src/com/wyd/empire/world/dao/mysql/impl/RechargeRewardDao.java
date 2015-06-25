package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.mysql.IRechargeRewardDao;
import com.wyd.empire.world.entity.mysql.LogRechargeReward;
import com.wyd.empire.world.entity.mysql.LotteryReward;
import com.wyd.empire.world.entity.mysql.RechargeReward;
import com.wyd.empire.world.entity.mysql.Successrate;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class RechargeRewardDao extends UniversalDaoHibernate implements IRechargeRewardDao {
	public RechargeRewardDao() {
		super();
	}

	/**
	 * 查询出所有首冲奖励物品
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RechargeReward> findAllRechargeReward(Short type) {
		return getList("FROM " + RechargeReward.class.getSimpleName() + " WHERE type=?", new Object[]{type});
	}

	/**
	 * 查询出所有抽奖奖励物品
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryReward> findAllLotteryReward() {
		return getList("FROM " + LotteryReward.class.getSimpleName(), new Object[]{});
	}

	/**
	 * 随机获取抽奖奖励物品
	 * 
	 * @param randomNum
	 *            随机概率数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryReward> getLotteryReward(int randomNum) {
		return getList("FROM " + LotteryReward.class.getSimpleName() + " WHERE startChance <= ? and ? < endChance", new Object[]{randomNum,
				randomNum});
	}

	/**
	 * 根据玩家ID查询玩家是否领取过首冲奖励
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getRechargeRewardLog(int playerId) {
		return count("SELECT COUNT(*) FROM " + LogRechargeReward.class.getSimpleName() + " WHERE type=1 and playerId=? ",
				new Object[]{playerId});
	}

	/**
	 * 根据充值抽奖ID查询出充值抽奖对象
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public LotteryReward findLotteryRewardById(int id) {
		return (LotteryReward) this.getUniqueResult(" FROM LotteryReward WHERE id = ? ", new Object[]{id});
	}

	/**
	 * 根据id首充奖励
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public RechargeReward getRechargeRewardById(int id) {
		return (RechargeReward) this.getUniqueResult(" FROM RechargeReward WHERE id = ? ", new Object[]{id});
	}

	/**
	 * 获取强化概率分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getSuccessrateList(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Successrate.class.getSimpleName() + " ORDER BY id asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum, pageSize);
	}

	/**
	 * 获取首充奖励分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getRechargeRewardList(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + RechargeReward.class.getSimpleName() + " ORDER BY id asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum, pageSize);
	}

}