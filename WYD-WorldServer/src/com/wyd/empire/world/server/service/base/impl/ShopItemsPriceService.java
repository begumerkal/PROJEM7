package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.dao.IShopItemsPriceDao;
import com.wyd.empire.world.server.service.base.IShopItemService;
import com.wyd.empire.world.server.service.base.IShopItemsPriceService;

/**
 * The service class for the TabShopitem entity.
 */
public class ShopItemsPriceService extends UniversalManagerImpl implements IShopItemsPriceService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IShopItemsPriceDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ShopitemService";

	public ShopItemsPriceService() {
		super();
	}

	/**
	 * Returns the singleton <code>IShopitemService</code> instance.
	 */
	public static IShopItemService getInstance(ApplicationContext context) {
		return (IShopItemService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IShopItemsPriceDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IShopItemsPriceDao getDao() {
		return this.dao;
	}

	/**
	 * 根据物品ID，获得物品价格
	 * 
	 * @param itemId
	 *            物品ID
	 * @return 物品价格
	 */
	public List<ShopItemsPrice> getItemPrice(int itemId) {
		return dao.getItemPrice(itemId);
	}

	/**
	 * 根据物品Id获得到物品的底价和付款方式
	 * 
	 * @param itemId
	 *            物品Id
	 * @return 物品的底价和付款方式
	 */
	public ShopItemsPrice getShopItemsPriceByItemId(int itemId) {
		return dao.getShopItemsPriceByItemId(itemId);
	}

	/**
	 * 根据物品Id获得到物品兑换的点券
	 * 
	 * @param itemId
	 *            品Id
	 * @return 物品兑换的点券
	 */
	public Exchange getShopItemsExchangeNum(int itemId, int playerId) {
		return dao.getShopItemsExchangeNum(itemId, playerId);
	}

}