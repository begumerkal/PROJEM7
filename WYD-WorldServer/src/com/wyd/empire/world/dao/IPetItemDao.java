package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.PlayerPet;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IPetItemDao extends UniversalDao {
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
}