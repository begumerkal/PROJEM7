package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.Exchange;
import com.wyd.empire.world.entity.mysql.ShopItemsPrice;

/**
 * The DAO interface for the TabTool entity.
 */
public interface IShopItemsPriceDao extends UniversalDao {

	/**
	 * 根据物品ID，获得物品价格
	 * 
	 * @param itemId
	 *            物品ID
	 * @return 物品价格
	 */
	public List<ShopItemsPrice> getItemPrice(int itemId);

	/**
	 * 根据物品Id获得到物品的底价和付款方式
	 * 
	 * @param itemId
	 *            物品Id
	 * @return 物品的底价和付款方式
	 */
	public ShopItemsPrice getShopItemsPriceByItemId(int itemId);

	/**
	 * 根据物品Id获得到物品兑换的点券
	 * 
	 * @param itemId
	 *            品Id
	 * @return 物品兑换的点券
	 */
	public Exchange getShopItemsExchangeNum(int itemId, int playerId);
}