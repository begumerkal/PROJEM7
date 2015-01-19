package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.ShopItemsPrice;

/**
 * The service interface for the TabShopitem entity.
 */
public interface IShopItemsPriceService extends UniversalManager {

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