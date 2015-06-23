package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.ChallengeRecord;
import com.wyd.empire.world.entity.mysql.IntegralArea;
import com.wyd.empire.world.entity.mysql.RankRecord;
import com.wyd.empire.world.entity.mysql.ShopItem;

public interface IRankRecordDao extends UniversalDao {
	/**
	 * 获得排位赛排名
	 * 
	 * @return
	 */
	public List<RankRecord> getRankRecordList(int limitNum);

	/**
	 * 获得玩家排名
	 * 
	 * @return
	 */
	public Integer getPlayerRankNum(int playerId);

	/**
	 * 获得玩家排名赛对象
	 * 
	 * @return
	 */
	public RankRecord getPlayerRankByPlayerId(int playerId);

	/**
	 * 清除排位赛记录
	 */
	public void deleteRankRecordForStart();

	/**
	 * 获得符合等级的排位赛
	 * 
	 * @param sex
	 * @param honorLevel
	 * @return
	 */
	public List<ShopItem> getHonorReward(int sex, int honorLevel);

	/**
	 * 获取本服玩家的挑战赛积分
	 * 
	 * @return
	 */
	public List<ChallengeRecord> getAllIntegral();

	/**
	 * 获得玩家挑战赛应得奖励
	 * 
	 * @param rankNum
	 * @return
	 */
	public IntegralArea getIntegralAreaByRank(int rankNum);

	/**
	 * 清除挑战赛记录
	 */
	public void deleteChallengeRecord();

	/**
	 * 更新积分
	 */
	public void updateIntegralByArea();
}