package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerItemsFromShopDao extends UniversalDao {
	/**
	 * 加载玩家装备
	 * 
	 * @param playerId
	 */
	public void loadPlayerItem(int playerId);

	/**
	 * 重新加载玩家装备
	 * 
	 * @param playerId
	 * @param playerItemId
	 */
	public void reloadPlayerItem(int playerId, int playerItemId);

	/**
	 * 卸载玩家装备
	 * 
	 * @param playerId
	 */
	public void unLoadPlayerItem(int playerId);

	/**
	 * 玩家新增物品
	 * 
	 * @param pifs
	 */
	public PlayerItemsFromShop savePlayerItemsFromShop(PlayerItemsFromShop pifs);

	/**
	 * 玩家删除物品
	 * 
	 * @param pifs
	 */
	public void deletePlayerItem(PlayerItemsFromShop pifs);

	/**
	 * 根据玩家ID获取玩家装备列表（其中装备必须是剩余数量不为0，剩余时间不为0）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家装备列表
	 */
	public PageList getPlayerItemsFromShopByPlayerId(int playerId, int pageNum, int pageSize);

	/**
	 * 根据玩家ID获取玩家装备列表（其中装备必须是剩余数量不为0，剩余时间不为0）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家装备列表
	 */
	public List<PlayerItemsFromShop> getPlayerItemsFromShopByPlayerId(int playerId);

	/**
	 * 获得原装备的id
	 * 
	 * @param playerId
	 * @param itemType
	 * @return
	 */
	public Integer getOldItemId(int playerId, int itemType);

	/**
	 * 根据玩家ID获得特殊效果对象（Vip，双倍卡）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 特殊效果对象（Vip，双倍卡）
	 */
	public List<PlayerItemsFromShop> getSpecialEffectId(int playerId);

	/**
	 * 根据玩家角色ID和物品ID获得玩家指定物品对象
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @param itemId
	 *            物品ID
	 * @return 玩家指定物品对象
	 */
	public List<PlayerItemsFromShop> getPlayerItemByItemId(int playerId, int itemId);

	/**
	 * 获取玩家指定的物品(唯一)
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	public PlayerItemsFromShop uniquePlayerItem(int playerId, int itemId);

	/**
	 * 玩家角色ID和物品ID获得玩家勋章数量
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @param itemId
	 *            物品ID
	 * @return 玩家勋章数量
	 */
	public PlayerItemsFromShop getBadgeByPlayerId(int playerId);

	/**
	 * 检测装备第N天后过期
	 * 
	 * @param num
	 *            为0时，表示当天过期
	 * @return 过期物品玩家ID和物品ID
	 */
	public List<Object> checkEquipmentOverTimeAfterNDays(int playerId, Long num);

	/**
	 * 根据玩家角色Id，获取所拥有的可以强化或合成的物品列表
	 * 
	 * @param playerId
	 *            玩家角色Id
	 * @return 可以强化或合成的物品列表
	 */
	public List<PlayerItemsFromShop> getStrengthenShopItemList(int playerId);

	/**
	 * 获得兑换商品列表
	 * 
	 * @param player
	 * @return
	 */
	public List<Exchange> checkExchangeList(WorldPlayer player);

	/**
	 * 刷新兑换列表
	 * 
	 * @param player
	 */
	public List<Exchange> refreshExchange(WorldPlayer player, long timeStart);

	/**
	 * GM工具-根据玩家ID查询出玩家物品
	 * 
	 * @param key
	 *            玩家ID
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllByPlayerId(String key, int pageIndex, int pageSize);

	/**
	 * 更换玩家身上物品的使用状态
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param shopId
	 *            商品ID
	 * @param isInUse
	 *            是否正在使用
	 */
	public void updateInUseByShopId(int playerId, int shopId, boolean isInUse);

	/**
	 * 清除过期记录(每隔N天清理一次)
	 */
	public void deleteOverDateRecord(int days);

	/**
	 * 获取玩家物品的最高强化等级
	 * 
	 * @param playerId
	 * @return
	 */
	public int getTopLevelForItemByPlayer(int playerId);

	/**
	 * 获取玩家指定物品的数量
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	public int getPlayerItemNum(int playerId, int itemId);

	/**
	 * 更新玩家指定物品的数量
	 * 
	 * @param playerId
	 * @param itemId
	 * @param speakerCount
	 */
	public void updatePlayerItemNum(int playerId, int itemId, int speakerCount);

	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 根据ID获得viprate
	 * 
	 * @param id
	 * @return
	 */
	public VipRate getVipRateById(int id);

	/**
	 * 获取玩家物品最高星级
	 * 
	 * @param playerId
	 */
	public int getTopStarForPlayer(int playerId);

	/**
	 * 获取玩家在使用的物品
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerItemsFromShop> getInUseItem(int playerId);

	/**
	 * 根据id查找玩家物品
	 * 
	 * @param playerId
	 * @param id
	 * @return
	 */
	public PlayerItemsFromShop getPlayerItemById(int playerId, int playerItemId);
}