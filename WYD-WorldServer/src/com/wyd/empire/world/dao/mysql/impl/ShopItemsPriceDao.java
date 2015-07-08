package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IShopItemsPriceDao;
import com.wyd.empire.world.entity.mysql.Exchange;
import com.wyd.empire.world.entity.mysql.ShopItemsPrice;

/**
 * The DAO class for the TabShopitem entity.
 */
public class ShopItemsPriceDao extends UniversalDaoHibernate implements IShopItemsPriceDao {
	public ShopItemsPriceDao() {
		super();
	}

	/**
	 * 根据物品ID，获得物品价格
	 * 
	 * @param itemId
	 *            物品ID
	 * @return 物品价格
	 */
	@SuppressWarnings("unchecked")
	public List<ShopItemsPrice> getItemPrice(int shopItemId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + ShopItemsPrice.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND shopItem.id = ? ");
		values.add(shopItemId);
		return this.getList(hql.toString(), values.toArray());
	}

	/**
	 * 根据物品Id获得到物品的底价和付款方式
	 * 
	 * @param itemId
	 *            物品Id
	 * @return 物品的底价和付款方式
	 */
	public ShopItemsPrice getShopItemsPriceByItemId(int itemId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + ShopItemsPrice.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND shopItem.id = ? ");
		values.add(itemId);
		hql.append(" ORDER BY id ASC");
		return (ShopItemsPrice) this.getList(hql.toString(), values.toArray(), 1).get(0);
	}

	/**
	 * 根据物品Id获得到物品兑换的点券
	 * 
	 * @param itemId
	 *            品Id
	 * @return 物品兑换的点券
	 */
	@SuppressWarnings("unchecked")
	public Exchange getShopItemsExchangeNum(int itemId, int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + Exchange.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND shopItem.id = ? and playerId = ? ");
		values.add(itemId);
		values.add(playerId);
		List<Exchange> list = getList(hql.toString(), values.toArray());
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
}