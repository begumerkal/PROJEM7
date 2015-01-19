package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerSingleMap;
import com.wyd.empire.world.bean.SingleMapDrop;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface ISingleMapService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "SingleMapService";

	/**
	 * 获取可能掉落的物品ID
	 * 
	 * @return
	 */
	public List<Integer> getReward(int dropId);

	/**
	 * 取得单人副本记录
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerSingleMap getPlayerSingleMap(int playerId, int mapId);

	/**
	 * 保存通关记录
	 * 
	 * @param playerId
	 * @param mapId
	 */
	public PlayerSingleMap savePassTimes(int playerId, int mapId);

	/**
	 * 获取掉落ID dropId2格式:(150,2) 150 表示需要达到的次数，2表示掉落ID
	 * 
	 * @param playerId
	 * @param map
	 * @return
	 */
	public int getDropId(int playerId, com.wyd.empire.world.bean.Map map);

	/**
	 * 获取奖励
	 * 
	 * @param playerId
	 * @param map
	 * @return
	 */
	public List<RewardItemsVo> getRewardList(int playerId, int sex, com.wyd.empire.world.bean.Map map);

	/**
	 * 获取奖励
	 * 
	 * @param playerId
	 * @param map
	 * @return
	 */
	public RewardItemsVo getOneReward(int playerId, int sex, com.wyd.empire.world.bean.Map map);

	/**
	 * 获取掉落
	 * 
	 * @param dropId
	 * @return
	 */
	public List<SingleMapDrop> getDropList(int dropId);

	/**
	 * 取当前进行中的大关卡ID
	 * 
	 * @return
	 */
	public int getCurrBigPointId(int playerId);

	/**
	 * 取得单人副本记录
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerSingleMap> getPlayerSingleMap(int playerId);

	/**
	 * 清空通关次数
	 * 
	 * @param playerId
	 * @param validate
	 *            为true时会检查resettime是否跨天
	 */
	public void resetPassTimes(int playerId, boolean validate);

	/**
	 * 最大通关的地图
	 * 
	 * @param playerId
	 * @return
	 */
	public Map getMaxPassMap(int playerId);

	/**
	 * 供定时器调用，每天清空在线玩家通关次数
	 */
	public void sysResetTimes();

	/**
	 * 战斗过程检验防外挂
	 * 
	 * @param data
	 * @return
	 */
	public String battleVerify(WorldPlayer player, List<GuaiPlayer> guaiPlayer, byte[] data);

}