package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.ItemPrice;
import com.wyd.empire.world.bean.Recommend;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.bean.ShoppingEveryday;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IShopItemDao;

/**
 * The DAO class for the TabShopitem entity.
 */
public class ShopItemDao extends UniversalDaoHibernate implements IShopItemDao {
	private Map<Integer, ShopItem> itemMap = null;
	private Map<Integer, ItemPrice> itemRecycleMap = null;
	private static List<ShopItemsPrice> manRecommendationList = null;
	private static List<ShopItemsPrice> womanRecommendationList = null;
	private static long manrecommendationTime = 0;
	private static long womanrecommendationTime = 0;
	private PageList manPl = null;
	private PageList womanPl = null;

	public ShopItemDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		Map<Integer, ShopItem> itemMap = new HashMap<Integer, ShopItem>();
		List<ShopItem> shopItemList = getList("FROM ShopItem ", new Object[]{});
		for (ShopItem item : shopItemList) {
			itemMap.put(item.getId(), item);
		}
		this.itemMap = itemMap;
		Map<Integer, ItemPrice> itemRecycleMap = new HashMap<Integer, ItemPrice>();
		List<ItemPrice> itemRecycleList = getList("FROM ItemPrice ", new Object[]{});
		for (ItemPrice itemRecycle : itemRecycleList) {
			itemRecycleMap.put(itemRecycle.getId(), itemRecycle);
		}
		this.itemRecycleMap = itemRecycleMap;
	}

	/**
	 * 查询商品列表
	 * 
	 * @param actType
	 *            1:推荐 2:武器 3:头饰 4：脸谱 5：衣服 6：其他 7：兑换
	 * @param sex
	 *            装备性别限制
	 * @return 物品列表
	 */
	@SuppressWarnings("unchecked")
	public List<ShopItemsPrice> getShopList(int actType, int sex) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		if (actType == Common.SHOP_ITEM_ACT_TYPE_RECOMMEND) {
			hql.append("SELECT sip FROM " + ShoppingEveryday.class.getSimpleName() + " AS e," + ShopItemsPrice.class.getSimpleName()
					+ " AS sip WHERE 1 = 1 ");
			hql.append(" AND e.shopItem.id = sip.shopItem.id ");
			hql.append(" AND (e.shopItem.sex = ? OR e.shopItem.sex = ? )");
			hql.append(" AND (now() BETWEEN e.shopItem.onSaleTime AND e.shopItem.offSaleTime) ");
			hql.append(" AND e.shopItem.isOnSale = ? ");
			hql.append(" AND e.staTime > ? ");
			values.add(sex);
			values.add(Common.SHOP_ITEM_SEX_ALL);
			values.add(Common.SHOP_ITEM_ON_SALE_YES);
			values.add(DateUtil.afterNowNDay(-7));
			hql.append(" GROUP BY sip.shopItem.id ORDER BY SUM(e.selledNum) DESC ");
		} else {
			hql.append("FROM " + ShopItemsPrice.class.getSimpleName() + " AS e WHERE 1 = 1 ");
			hql.append(" AND (e.shopItem.sex = ? OR e.shopItem.sex = ? )");
			hql.append(" AND (now() BETWEEN e.shopItem.onSaleTime AND e.shopItem.offSaleTime) ");
			hql.append(" AND e.shopItem.isOnSale = ? ");
			values.add(sex);
			values.add(Common.SHOP_ITEM_SEX_ALL);
			values.add(Common.SHOP_ITEM_ON_SALE_YES);
			hql.append(" GROUP BY e.shopItem.id ORDER BY e.shopItem.addAttack DESC,e.shopItem.addHp DESC,e.shopItem.onSaleTime DESC, e.shopItem.id ASC ");
		}
		if (actType == Common.SHOP_ITEM_ACT_TYPE_RECOMMEND) {
			if (sex == 0) {
				if ((System.currentTimeMillis() - manrecommendationTime) > 43200000) {
					manRecommendationList = this.getList(hql.toString(), values.toArray(), 11);
					manrecommendationTime = System.currentTimeMillis();
				}
				return manRecommendationList;
			} else {
				if ((System.currentTimeMillis() - womanrecommendationTime) > 43200000) {
					womanRecommendationList = this.getList(hql.toString(), values.toArray(), 11);
					womanrecommendationTime = System.currentTimeMillis();
				}
				return womanRecommendationList;
			}
		} else {
			return this.getList(hql.toString(), values.toArray());
		}
	}

	/**
	 * 查询商品列表(分页版)
	 * 
	 * @param actType
	 *            1:推荐 2:武器 3:头饰 4：脸谱 5：衣服 6：其他 7：兑换
	 * @param sex
	 *            装备性别限制
	 * @return 物品列表
	 */
	@SuppressWarnings("unchecked")
	public PageList getShopList(int actType, int sex, int pageNum, int pageSize) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		String sqltmp = "";
		if (actType == Common.SHOP_ITEM_ACT_TYPE_RECOMMEND) {
			hql.append("SELECT sip FROM " + ShoppingEveryday.class.getSimpleName() + " AS e," + ShopItemsPrice.class.getSimpleName()
					+ " AS sip WHERE 1 = 1 ");
			hql.append(" AND e.shopItem.id = sip.shopItem.id ");
			hql.append(" AND (e.shopItem.sex = ? OR e.shopItem.sex = ? )");
			hql.append(" AND (now() BETWEEN e.shopItem.onSaleTime AND e.shopItem.offSaleTime) ");
			hql.append(" AND e.shopItem.isOnSale = ? ");
			hql.append(" AND e.staTime > ? ");
			values.add(sex);
			values.add(Common.SHOP_ITEM_SEX_ALL);
			values.add(Common.SHOP_ITEM_ON_SALE_YES);
			values.add(DateUtil.afterNowNDay(-7));
		} else {
			hql.append("FROM " + ShopItemsPrice.class.getSimpleName() + " AS e WHERE 1 = 1 ");
			hql.append(" AND (e.shopItem.sex = ? OR e.shopItem.sex = ? )");
			hql.append(" AND (now() BETWEEN e.shopItem.onSaleTime AND e.shopItem.offSaleTime) ");
			hql.append(" AND e.shopItem.isOnSale = ? ");
			values.add(sex);
			values.add(Common.SHOP_ITEM_SEX_ALL);
			values.add(Common.SHOP_ITEM_ON_SALE_YES);
		}
		if (actType == Common.SHOP_ITEM_ACT_TYPE_RECOMMEND) {
			if (sex == 0) {
				if ((System.currentTimeMillis() - manrecommendationTime) > 43200000) {
					manPl = new PageList(this.getList(hql.toString(), values.toArray(), 10).size(), this.getList(hql.toString(),
							values.toArray(), 10), 10, 0);
					manrecommendationTime = System.currentTimeMillis();
				}
				return manPl;
			} else {
				if ((System.currentTimeMillis() - womanrecommendationTime) > 43200000) {
					womanPl = new PageList(this.getList(hql.toString(), values.toArray(), 10).size(), this.getList(hql.toString(),
							values.toArray(), 10), 10, 0);
					womanrecommendationTime = System.currentTimeMillis();
				}
				return womanPl;
			}
		} else {
			String countHql = "SELECT COUNT(si.id) from ShopItem si where (si.sex = ? OR si.sex = ? )"
					+ " AND (now() BETWEEN si.onSaleTime AND si.offSaleTime) " + " AND si.isOnSale = ? " + sqltmp;
			return getPageList(hql.toString(), countHql, values.toArray(), pageNum - 1, pageSize);
		}
	}

	/**
	 * 根据物品类型，玩家性别查询兑换物品列表
	 * 
	 * @param type
	 *            物品类型
	 * @param sex
	 *            物品使用性别限制
	 * @return 物品列表
	 */
	@SuppressWarnings("unchecked")
	public List<ShopItem> getExchangeShopList(int type, int sex) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT e.shopItem FROM " + Exchange.class.getSimpleName() + " AS e WHERE 1 = 1 ");
		hql.append(" AND (now() between e.shopItem.onSaleTime and e.shopItem.offSaleTime)");
		hql.append(" AND e.shopItem.isOnSale = ? ");
		values.add(Common.SHOP_ITEM_ON_SALE_YES);
		hql.append(" AND (e.shopItem.sex = ? OR e.shopItem.sex = ? ) AND playerId = 0");
		values.add(sex);
		values.add(Common.SHOP_ITEM_SEX_ALL);
		return this.getList(hql.toString(), values.toArray());
	}

	/**
	 * 获得所有正在销售的物品列表
	 * 
	 * @return 正在销售的物品列表
	 * @param mark标识
	 *            ：0表示所有商品，1表示所有销售的武器，2表示所有销售的装备
	 */
	@SuppressWarnings("unchecked")
	public List<ShopItem> getAllOnSaleShopList(int mark) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + ShopItem.class.getSimpleName() + " WHERE (now() BETWEEN onSaleTime AND offSaleTime) AND isOnSale = ? ");
		values.add(Common.SHOP_ITEM_ON_SALE_YES);
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 清除兑换表的所有数据
	 */
	public void deleteAllExchangeItems() {
		StringBuilder hql = new StringBuilder();
		hql.append("DELETE FROM " + Exchange.class.getSimpleName() + " WHERE playerId = 0");
		execute(hql.toString(), new Object[]{});
	}

	/**
	 * 查询物品
	 * 
	 * @param key
	 *            查询参数
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示大小
	 * @return
	 */
	public PageList getItemList(String key, int pageIndex, int pageSize) {
		String[] dates = key.split("\\|");
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hsql.append(" FROM  " + ShopItem.class.getSimpleName() + " WHERE 1 = 1 ");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						if ("Y".equals(dates[0])) {
							hsql.append(" AND isOnSale = ?");
							values.add(true);
							hsql.append(" AND ? BETWEEN onSaleTime and offSaleTime");
							values.add(new Date());
						} else if ("N".equals(dates[0])) {
							hsql.append(" AND (isOnSale = ? or ? NOT BETWEEN onSaleTime and offSaleTime)");
							values.add(false);
							values.add(new Date());
						} else {
							if (!("0").equals(dates[0])) {
								hsql.append(" AND id in (" + dates[0] + ")");
							}
						}
						break;
					case 1 :
						if ("1".equals(dates[1])) {
							hsql.append(" AND sex = ?");
							values.add(0);
						} else if ("2".equals(dates[1])) {
							hsql.append(" AND sex = ?");
							values.add(1);
						} else if ("3".equals(dates[1])) {
							hsql.append(" AND sex = ?");
							values.add(2);
						}
						break;
					case 2 :
						hsql.append(" AND type = ?");
						values.add(Byte.parseByte(dates[2]));
						break;
					case 3 :
						if (ServiceUtils.isNumeric(dates[3])) {
							hsql.append(" AND id = ? ");
							values.add(Integer.parseInt(dates[3]));
						} else {
							hsql.append(" AND (name like '" + dates[3] + "%' or name like '%" + dates[3] + "' or name like '%" + dates[3]
									+ "%') ");
						}
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hsql.toString().replace(",)", ")");
		hsql.append(" order by id desc");
		return getPageList(hsql.toString().replace(",)", ")"), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 获取推荐列表
	 * 
	 * @param sex
	 *            性别
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShopItemsPrice> findAllRecommend(int sex) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT e FROM " + ShopItemsPrice.class.getSimpleName() + " AS e , " + Recommend.class.getSimpleName()
				+ " AS r WHERE 1 = 1 ");
		hql.append(" AND e.shopItem.id = r.shopId ");
		hql.append(" AND (e.shopItem.sex = ? OR e.shopItem.sex = ? ) ");
		hql.append(" AND (now() BETWEEN e.shopItem.onSaleTime AND e.shopItem.offSaleTime) ");
		hql.append(" AND e.shopItem.isOnSale = ? ");
		hql.append(" GROUP BY e.shopItem.id ");
		values.add(sex);
		values.add(Common.SHOP_ITEM_SEX_ALL);
		values.add(Common.SHOP_ITEM_ON_SALE_YES);
		hql.append(" ORDER BY r.sort ASC ");
		return (List<ShopItemsPrice>) this.getList(hql.toString(), values.toArray());
	}

	/**
	 * 查询出所有的推荐列表
	 * 
	 * @param key
	 *            查询参数
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少
	 * @return
	 */
	public PageList findRecommendList(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Recommend.class.getSimpleName() + " ORDER BY id asc");
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除玩家成功邀请物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByRecommendIds(String ids) {
		this.execute("DELETE " + Recommend.class.getSimpleName() + " WHERE id in (" + ids + ")", new Object[]{});
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getItemIconById(String itemIds) {
		List<Object[]> objs = getList("select id, icon from ShopItem where id in(" + itemIds + ")", new Object[]{});
		Map<Integer, String> itemMap = new HashMap<Integer, String>();
		for (Object[] obj : objs) {
			itemMap.put(Integer.parseInt(obj[0].toString()), obj[1].toString());
		}
		return itemMap;
	}

	public ShopItem getShopItemById(int itemId) {
		return itemMap.get(itemId);
	}

	public ItemPrice getItemRecycleById(int id) {
		return itemRecycleMap.get(id);
	}

	/**
	 * 根据时间查询出优惠的商品道具和折扣
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findByTime(Date date) {
		StringBuffer hsql = new StringBuffer();
		hsql.append("SELECT shopId,discount FROM  Magnification WHERE startTime <= ? AND endTime >= ?");
		return this.getList(hsql.toString(), new Object[]{date, date});
	}
}