package com.wyd.empire.world.server.service.base;

import java.util.List;
import java.util.Map;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.PlayerPet;

/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IPetItemService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "PetItemService";

	/**
	 * 根据宠物Id取宠物
	 * 
	 * @param playerId
	 * @return
	 */
	public PetItem getById(int petId);

	/**
	 * 获得玩家宠物列表
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerPet> getPlayerPetList(int playerId);

	/**
	 * 获得玩家宠物MAP
	 * 
	 * @param playerId
	 * @return
	 */
	public Map<Integer, PlayerPet> getPlayerPetMap(int playerId);

	/**
	 * 获得所有可召唤宠物列表
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PetItem> getAllPetList();

	/**
	 * 根据ID获取宠物列表
	 */
	public List<PetItem> getPetList(String ids);

	/**
	 * 获得玩家宠物数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getPlayerPetNum(int playerId);
}