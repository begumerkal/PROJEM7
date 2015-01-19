package com.wyd.empire.world.server.service.base;

import java.util.Date;
import com.wyd.empire.world.bean.ChallengeRecord;
import com.wyd.empire.world.bean.IntegralArea;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabTool entity.
 */
public interface IChallengeService {
	/**
	 * 初始化积分数据
	 */
	public void inintData();

	/**
	 * 供系统定时任务调用，如果当前时间在挑战赛开启时间内则开启；如果五分钟后开启则发通知（一天只发一次）
	 * 
	 * @return
	 */
	public void sysCheckStartTime();

	/**
	 * 检测当前是否开启挑战赛
	 * 
	 * @return
	 */
	public boolean isInTime();

	public boolean isInTime(Date date);

	/**
	 * 供系统定时任务调用，关闭到时
	 * 
	 * @return
	 */
	public void sysCheckEndTime();

	public void sysCheckStartEndTime();

	/**
	 * 开启及结束时间 [开启时间 ,结束时间]
	 * 
	 * @return
	 */
	public Date[] getTimeScope();

	/**
	 * 清理挑战赛积分
	 */
	public void deleteIntegralByArea();

	/**
	 * 判断弹王挑战赛是否开启
	 * 
	 * @return
	 */
	public boolean isStart();

	/**
	 * 获取玩家弹王积分
	 * 
	 * @param playerId
	 * @return
	 */
	public int getIntegral(int playerId);

	/**
	 * 获取玩家弹王胜利次数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getArrayWinNum(int playerId);

	/**
	 * 玩家获得弹王积分
	 * 
	 * @param player
	 * @param integral
	 * @param isWin
	 * @return
	 */
	public ChallengeRecord addIntegral(WorldPlayer player, int integral, boolean isWin);

	/**
	 * 获得玩家的弹王积分明细
	 * 
	 * @param playerId
	 * @return
	 */
	public ChallengeRecord getChallengeRecord(int playerId);

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