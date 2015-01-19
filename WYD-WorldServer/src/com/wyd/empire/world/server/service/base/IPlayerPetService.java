package com.wyd.empire.world.server.service.base;

import java.util.List;
import java.util.Map;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.PetCulture;
import com.wyd.empire.world.bean.PetTrain;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPetBar;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerPetService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "PlayerPetService";

	/**
	 * 根据用户取宠物列表 在使用中的宠物排第一位及等级高的排前面
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerPet> getPetListByPlayer(Integer playerId);

	/**
	 * 根据宠物级别取宠物训练参数
	 * 
	 * @param level
	 * @return
	 */
	public PetTrain getTrainByLevel(int level);

	/**
	 * 判断宠物是否在训练
	 * 
	 * @param playerId
	 * @param petId
	 * @return
	 */
	public boolean isInTrain(int playerId, int petId);

	/**
	 * 取得正在使用的宠物
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerPet getInUsePet(int playerId);

	/**
	 * 根据玩家ID和宠物ID取玩家宠物
	 * 
	 * @param playerId
	 * @param petId
	 * @return
	 */
	public PlayerPet getByPlayerAndPet(Integer playerId, Integer petId);

	/**
	 * 根据宠物级别取宠物培养参数
	 * 
	 * @param level
	 * @return
	 */
	public PetCulture getCultureByLevel(Integer level);

	/**
	 * 取得正在训练的宠物
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerPet getInTrainPet(int playerId);

	/**
	 * 更新宠物经验 宠物级别不能大于玩家的级别
	 * 
	 * @param playerPet
	 * @param exp
	 * @return 升级次数。大于0表示升级了
	 */
	public int updateExp(WorldPlayer player, PlayerPet playerPet, int exp);

	/**
	 * 根据增加的经验计算能升多少级
	 * 
	 * @param addexp
	 *            增加的经验
	 * @param playerLevel
	 * @return
	 */
	public int expToLevel(PlayerPet playerPet, int addExp, int playerLevel);

	/**
	 * 取玩家开启的宠物槽的个数
	 * 
	 * @param playerId
	 * @return
	 */
	public int openBarNum(int playerId);

	/**
	 * 根据槽位数取得开启价格
	 * 
	 * @param num
	 * @return
	 */
	public int getOpenBarDiamond(int num);

	public PlayerPetBar getPlayerPetBar(int playerId);

	/**
	 * 给玩家发放宠物
	 * 
	 * @param player
	 * @param petId
	 * @param isUse
	 *            是否出战
	 * @return
	 */
	public PlayerPet playerGetPet(int playerId, int petId, boolean isUse);

	public PlayerPet playerGetPet(int playerId, int petId);

	/**
	 * 发送玩家宠物到客户端
	 * 
	 * @param player
	 */
	public void sendPlayerPet(WorldPlayer player);

	/**
	 * 更新宠物到客户端
	 * 
	 * @param player
	 * @param petId
	 * @param info
	 */
	public void sendUpdatePet(WorldPlayer player, int petId, Map<String, String> info);

}