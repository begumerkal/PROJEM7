package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.Map;
import com.wyd.empire.world.entity.mysql.PlayerSingleMap;
import com.wyd.empire.world.entity.mysql.ShopItem;
import com.wyd.empire.world.entity.mysql.SingleMapDrop;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface ISingleMapDao extends UniversalDao {
	public List<SingleMapDrop> getDropList(int dropId);

	public com.wyd.empire.world.entity.mysql.Map getMapById(int id);

	public List<PlayerSingleMap> findPlayerMapBy(int playerId);

	public PlayerSingleMap getPlayerSingleMap(int playerId, int mapId);

	public List<PlayerSingleMap> getPlayerSingleMap(int playerId);

	/**
	 * 取玩家最后一个通关地图
	 * 
	 * @return
	 */
	public Map getMaxPassMap(int playerId);

	/**
	 * 根据掉落ID返回掉落展示品
	 * 
	 * @param dropId
	 * @return
	 */
	List<ShopItem> getShowItems(int dropId);

}