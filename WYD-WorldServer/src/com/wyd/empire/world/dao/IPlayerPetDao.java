package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.PetBar;
import com.wyd.empire.world.bean.PetCulture;
import com.wyd.empire.world.bean.PetTrain;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPetBar;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerPetDao extends UniversalDao {
	/**
	 * 根据用户取宠物列表
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerPet> getPetListByPlayer(Integer playerId);

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
	 * 根据经验得到最大的训练级别
	 * 
	 * @param quality
	 *            宠物品质
	 * @param exp
	 *            经验
	 * @return
	 */
	public PetTrain getTrainByExp(int quality, int exp);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public PetBar getPetBar(int id);

	/**
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerPetBar getPlayerPetBar(int playerId);

}