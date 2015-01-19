package com.wyd.empire.world.server.service.base;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.item.DailyRewardVo;

/**
 * 定时服务类
 * 
 * @author qiang
 */
public interface ISchedulingService extends UniversalManager {
	/**
	 * 给玩家发送活动奖励
	 */
	public void sendItemsToPlayer();

	/**
	 * 保存满足活动的玩家信息
	 */
	public void saveLogActivitiesAward();

	/**
	 * 日常活动奖励
	 */
	public void saveLogActivitiesAward(DailyRewardVo dailyRewardVo, int playerId);

	/**
	 * 检查当天登录日常奖励
	 */
	public void saveloginAward(DailyRewardVo dailyRewardVo, int playerId);
}
