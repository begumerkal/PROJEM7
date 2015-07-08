package com.wyd.empire.world.dao.mysql;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.entity.mysql.ItemPrice;
import com.wyd.empire.world.entity.mysql.ShopItem;
import com.wyd.empire.world.entity.mysql.ShopItemsPrice;

/**
 * The DAO interface for the TabShopitem entity.
 */
public interface IShopItemDao extends UniversalDao {
	/**
	 * 查询商品列表
	 * 
	 * @param actType
	 *            1:推荐 2:武器 3:头饰 4：脸谱 5：衣服 6：其他 7：兑换
	 * @param sex
	 *            装备性别限制
	 * @return 物品列表
	 */
	public PageList getShopList(int actType, int sex, int pageNum, int pageSize);

	/**
	 * 查询商品列表
	 * 
	 * @param actType
	 *            1:推荐 2:武器 3:头饰 4：脸谱 5：衣服 6：其他 7：兑换
	 * @param sex
	 *            装备性别限制
	 * @return 物品列表
	 */
	public List<ShopItemsPrice> getShopList(int actType, int sex);

	/**
	 * 获得所有正在销售的物品列表
	 * 
	 * @return 正在销售的物品列表
	 */
	public List<ShopItem> getAllOnSaleShopList(int mark);

	/**
	 * 根据物品类型，玩家性别查询兑换物品列表
	 * 
	 * @param type
	 *            物品类型
	 * @param sex
	 *            物品使用性别限制
	 * @return 物品列表
	 */
	public List<ShopItem> getExchangeShopList(int type, int sex);

	/**
	 * 清除兑换表的所有数据
	 */
	public void deleteAllExchangeItems();

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
	public PageList getItemList(String key, int pageIndex, int pageSize);

	/**
	 * 获取推荐列表
	 * 
	 * @param sex
	 *            性别
	 * @return
	 */
	public List<ShopItemsPrice> findAllRecommend(int sex);

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
	public PageList findRecommendList(String key, int pageIndex, int pageSize);

	/**
	 * 根据多个ID删除玩家成功邀请物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByRecommendIds(String ids);

	/**
	 * 查询物品的显示icon
	 * 
	 * @param itemIds
	 * @return
	 */
	public Map<Integer, String> getItemIconById(String itemIds);

	/**
	 * 初始化数据
	 */
	public void initData();

	public ShopItem getShopItemById(int itemId);

	public ItemPrice getItemRecycleById(int id);

	/**
	 * 根据时间查询出优惠的商品道具
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public List<Object[]> findByTime(Date date);
}