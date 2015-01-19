package com.wyd.empire.world.server.service.base;

import java.util.List;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.RankRecord;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;

public interface IRankRecordService extends UniversalManager {
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
	 * 将积分更新到玩家表
	 */
	public void updateHonorToPlayer();

	/**
	 * 获得排位赛前十的奖励
	 * 
	 * @return
	 */
	public List<RewardInfo> getRankReward();

	/**
	 * 玩家获得排位赛奖励（前十名）
	 */
	public void playerGetRankReward();

	/**
	 * 给予排位赛第一名奖励
	 */
	public void giveFirstRankReward();

	/**
	 * 更新排位赛相关
	 */
	public void updateRank();
}