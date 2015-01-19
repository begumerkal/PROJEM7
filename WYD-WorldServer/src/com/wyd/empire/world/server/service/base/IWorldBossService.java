package com.wyd.empire.world.server.service.base;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabTool entity.
 */
public interface IWorldBossService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "WorldBossService";

	/**
	 * 供系统定时任务调用，如果当前时间在世界BOSS开启时间内则开启；如果五分钟后开启则发通知（一天只发一次）
	 * 
	 * @return
	 */
	public void sysCheckStartTime();

	/**
	 * 当前时间是否在世界BOSS开启时间内
	 * 
	 * @return
	 */
	public boolean isInTime();

	/**
	 * 当前时间是否离世界BOSS开启时间差MINUTE分钟
	 * 
	 * @param MINUTE
	 * @return
	 */
	public boolean isBeforeTime(int MINUTE);

	/**
	 * 获取玩家冷却时间
	 * 
	 * @param playerId
	 * @param mapId
	 * @return
	 */
	public int getCDTime(int playerId, int mapId);

	public void setCDTime(int playerId, int mapId);

	public void clearCDTime(int playerId, int mapId);

	/**
	 * 游戏结束 时间到或BOSS被杀死调用
	 * 
	 * @param mapId
	 */
	public void gameOver(int mapId);

	/**
	 * 供系统定时任务调用，关闭到时且BOSS未完成的活动
	 * 
	 * @return
	 */
	public void sysCheckEndTime();

	public void sysCheckStartEndTime();

	/**
	 * 进入房间 返回 （战斗ID）大于1 表示成功 0 战斗房间未开启 －1 冷却中
	 * 
	 */
	public int enter(WorldPlayer player, int roomId);

	/**
	 * 通过地图ID，判断该地图是否为世界BOSS
	 * 
	 * @param mapId
	 * @return
	 */
	public boolean isWorldBossBattle(int mapId);

	/**
	 * 玩家伤害输出达到条件后，发系统公告
	 * 
	 * @param mapId
	 * @param combat
	 */
	public void checkHurt(int mapId, Combat combat);

	/**
	 * BOSS被杀死
	 * 
	 * @param mapId
	 */
	public void bossDead(int mapId);

	/**
	 * 开启及结束时间 [开启时间 ,结束时间]
	 * 
	 * @return
	 */
	public Date[] getTimeScope();

	/**
	 * 玩家被杀死
	 */
	public void playerDead(int battleId, int mapId, List<Combat> combatList);

	/**
	 * 设置世界BOSS的可触发BUFF
	 * 
	 * @param buffSet
	 */
	public void refreshWorldBossBuffSet(HashSet<Integer> buffSet, int mapId);
}