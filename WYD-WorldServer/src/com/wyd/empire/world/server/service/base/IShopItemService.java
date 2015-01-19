package com.wyd.empire.world.server.service.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.ItemPrice;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabShopitem entity.
 */
public interface IShopItemService extends UniversalManager {
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
	 * @param mark标识
	 *            ：0表示所有商品，1表示所有销售的武器，2表示所有销售的装备
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

	public List<ShopItem> getListById(String ids);

	/**
	 * 保存获得物品的记录
	 * 
	 * @param playerId
	 * @param itemId
	 * @param getway
	 *            获得物品的渠道：0是购买，1兑换，2任务奖励，3对战奖励
	 * @param countNum
	 *            获得个数
	 * @param days
	 *            获得天数
	 */
	// public void saveGetItemLog(int playerId,int itemId,int getway,int
	// countNum,int days);
	/**
	 * 清除兑换表的所有数据
	 */
	public void deleteAllExchangeItems();

	/**
	 * 查询物品
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getItemList(String key, int pageIndex, int pageSize);

	/**
	 * 初始化自定义推荐列表
	 */
	public void getRecommendList();

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

	public int getParmById(int itemId);

	/**
	 * 根据时间查询出优惠的商品道具
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public List<Object[]> findByTime(Date date);

	/**
	 * 获取加成熟悉
	 * 
	 * @param stoneId
	 *            镶嵌的石头id
	 * @param type
	 *            加成的熟悉 1攻击，2血量，3防御，4破防，5免暴，6力量，7护甲，8敏捷，9体质，10幸运，11暴击
	 * @return
	 */
	public int getMosaicAddition(int stoneId, int type);

	/**
	 * 按id集合返回实体集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<ShopItem> getEntityByIds(int[] ids);

	/**
	 * 向客户端推送缓存数据
	 * 
	 * @param player
	 */
	public void sendList(WorldPlayer player);
}