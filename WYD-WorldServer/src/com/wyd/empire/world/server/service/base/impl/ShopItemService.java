package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.cache.ShopList;
import com.wyd.empire.world.bean.ItemPrice;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IShopItemDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IShopItemService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabShopitem entity.
 */
public class ShopItemService extends UniversalManagerImpl implements IShopItemService {
	public List<ShopItemsPrice> recommendListBoy = null;
	public List<ShopItemsPrice> recommendListGril = null;
	/**
	 * The dao instance injected by Spring.
	 */
	private IShopItemDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ShopitemService";

	public ShopItemService() {
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
	public void setDao(IShopItemDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IShopItemDao getDao() {
		return this.dao;
	}

	/**
	 * 初始化自定义推荐列表
	 */
	public void getRecommendList() {
		recommendListBoy = null;
		recommendListGril = null;
		recommendListBoy = dao.findAllRecommend(Common.SHOP_ITEM_SEX_MAN);
		recommendListGril = dao.findAllRecommend(Common.SHOP_ITEM_SEX_WOMAN);
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
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public PageList getShopList(int actType, int sex, int pageNum, int pageSize) {
		if (actType == Common.SHOP_ITEM_ACT_TYPE_RECOMMEND) {
			List shopItemsPriceList = getShopList(actType, sex);
			PageList pageList = new PageList();
			pageList.setPageIndex(1);
			pageList.setPageSize(1);
			pageList.setList(shopItemsPriceList);
			return pageList;
		}
		return dao.getShopList(actType, sex, pageNum, pageSize);
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
	public List<ShopItemsPrice> getShopList(int actType, int sex) {
		if (actType == Common.SHOP_ITEM_ACT_TYPE_RECOMMEND) {
			List<ShopItemsPrice> newShopItemsList = null;
			if (sex == Common.SHOP_ITEM_SEX_MAN) {
				newShopItemsList = recommendListBoy;
			} else {
				newShopItemsList = recommendListGril;
			}
			List<ShopItemsPrice> recommendList = dao.getShopList(actType, sex);
			List<ShopItemsPrice> newList = new ArrayList<ShopItemsPrice>();
			for (ShopItemsPrice shopItemsPrice : newShopItemsList) {
				newList.add(shopItemsPrice);
			}
			for (ShopItemsPrice shopItemsPrice : recommendList) {
				boolean isAdd = true;
				for (ShopItemsPrice price : newShopItemsList) {
					if (shopItemsPrice.getShopItem().getId() == price.getShopItem().getId()) {
						isAdd = false;
						continue;
					}
				}
				if (isAdd) {
					newList.add(shopItemsPrice);
				}
			}
			return newList;
		}
		return dao.getShopList(actType, sex);
	}

	/**
	 * 获得所有正在销售的物品列表
	 * 
	 * @return 正在销售的物品列表
	 */
	public List<ShopItem> getAllOnSaleShopList(int mark) {
		return dao.getAllOnSaleShopList(mark);
	}

	@SuppressWarnings("unchecked")
	public List<ShopItem> getListById(String ids) {
		return dao.getList("from ShopItem where id in(?)", new Object[]{ids});
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
	public List<ShopItem> getExchangeShopList(int type, int sex) {
		return dao.getExchangeShopList(type, sex);
	}

	/**
	 * 清除兑换表的所有数据
	 */
	public void deleteAllExchangeItems() {
		dao.deleteAllExchangeItems();
	}

	/**
	 * 查询物品
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getItemList(String key, int pageIndex, int pageSize) {
		return dao.getItemList(key, pageIndex, pageSize);
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
		return dao.findRecommendList(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除玩家成功邀请物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByRecommendIds(String ids) {
		dao.deleteByRecommendIds(ids);
	}

	/**
	 * 查询物品的显示icon
	 * 
	 * @param itemIds
	 * @return
	 */
	public Map<Integer, String> getItemIconById(String itemIds) {
		return dao.getItemIconById(itemIds);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	public ShopItem getShopItemById(int itemId) {
		return dao.getShopItemById(itemId);
	}

	public ItemPrice getItemRecycleById(int id) {
		return dao.getItemRecycleById(id);
	}

	public int getParmById(int itemId) {
		int parm = 0;
		ShopItem si = getShopItemById(itemId);
		if (si == null) {
			log.info("itemId:" + itemId + " 返回空对象");
			return parm;
		}
		if (si.getType() == 11) {
			if (si.getSubtype() == 1) {
				parm = si.getAddForce();
			} else if (si.getSubtype() == 2) {
				parm = si.getAddAttack();
			} else if (si.getSubtype() == 3) {
				parm = si.getAddAgility();
			} else if (si.getSubtype() == 4) {
				parm = si.getAddCritical();
			}
		} else if (si.getType() == 12) {
			if (si.getSubtype() == 1) {
				parm = si.getAddArmor();
			} else if (si.getSubtype() == 2) {
				parm = si.getAddDefend();
			} else if (si.getSubtype() == 3) {
				parm = si.getAddPhysique();
			} else if (si.getSubtype() == 4) {
				parm = si.getAddHp();
			}
		} else if (si.getType() == 13) {
			if (si.getSubtype() == 1) {
				parm = si.getAddLuck();
			} else if (si.getSubtype() == 2) {
				parm = si.getAddWreckDefense();
			} else if (si.getSubtype() == 3) {
				parm = si.getAddReduceCrit();
			}
		} else {
			parm = 0;
		}
		return parm;
	}

	/**
	 * 根据时间查询出优惠的商品道具
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public List<Object[]> findByTime(Date date) {
		return dao.findByTime(date);
	}

	public int getMosaicAddition(int stoneId, int type) {
		ShopItem shopItem = getShopItemById(stoneId);
		if (null == shopItem)
			return 0;
		int value = 0;
		switch (type) {
			case 1 :
				if (11 == shopItem.getType() && 2 == shopItem.getSubtype()) {
					value = shopItem.getAddAttack();
				}
				break;
			case 2 :
				if (12 == shopItem.getType() && 4 == shopItem.getSubtype()) {
					value = shopItem.getAddHp();
				}
				break;
			case 3 :
				if (12 == shopItem.getType() && 2 == shopItem.getSubtype()) {
					value = shopItem.getAddDefend();
				}
				break;
			case 4 :
				if (13 == shopItem.getType() && 2 == shopItem.getSubtype()) {
					value = shopItem.getAddWreckDefense();
				}
				break;
			case 5 :
				if (13 == shopItem.getType() && 3 == shopItem.getSubtype()) {
					value = shopItem.getAddReduceCrit();
				}
				break;
			case 6 :
				if (11 == shopItem.getType() && 1 == shopItem.getSubtype()) {
					value = shopItem.getAddForce();
				}
				break;
			case 7 :
				if (12 == shopItem.getType() && 1 == shopItem.getSubtype()) {
					value = shopItem.getAddArmor();
				}
				break;
			case 8 :
				if (11 == shopItem.getType() && 3 == shopItem.getSubtype()) {
					value = shopItem.getAddAgility();
				}
				break;
			case 9 :
				if (12 == shopItem.getType() && 3 == shopItem.getSubtype()) {
					value = shopItem.getAddPhysique();
				}
				break;
			case 10 :
				if (13 == shopItem.getType() && 1 == shopItem.getSubtype()) {
					value = shopItem.getAddLuck();
				}
				break;
			case 11 :
				if (11 == shopItem.getType() && 4 == shopItem.getSubtype()) {
					value = shopItem.getAddCritical();
				}
				break;
		}
		return value;
	}

	@Override
	public List<ShopItem> getEntityByIds(int[] ids) {
		List<ShopItem> entityList = new ArrayList<ShopItem>();
		for (int id : ids) {
			entityList.add(getShopItemById(id));
		}
		return entityList;
	}

	@Override
	public void sendList(WorldPlayer player) {
		if (player == null)
			return;
		List<Object[]> magnificationList = findByTime(new Date());
		List<Integer> id = new ArrayList<Integer>();
		List<Byte> maintype = new ArrayList<Byte>();
		List<Byte> subtype = new ArrayList<Byte>();
		List<Boolean> isOnSale = new ArrayList<Boolean>();
		List<Integer> floorPrice = new ArrayList<Integer>();
		List<Integer> payType = new ArrayList<Integer>();
		List<Boolean> newMark = new ArrayList<Boolean>();
		List<Integer> discount = new ArrayList<Integer>();
		List<Boolean> recommended = new ArrayList<Boolean>();

		for (int shopType = 1; shopType < 3; shopType++) {
			List<ShopItemsPrice> itemPriceList = getShopList(shopType, player.getPlayer().getSex());
			for (ShopItemsPrice itemPrice : itemPriceList) {
				ShopItem item = itemPrice.getShopItem();
				id.add(item.getId());
				maintype.add(item.getType());
				subtype.add(item.getSubtype());
				isOnSale.add(item.getIsOnSale());
				long onSaleLong = item.getOnSaleTime().getTime();
				long nowLong = System.currentTimeMillis();
				newMark.add(nowLong - onSaleLong < 30 * Common.ONEDAYLONG);
				int fprice = 0;
				if (itemPrice.getCostType() == 0) {
					payType.add(0);
					fprice = itemPrice.getCostUseTickets();
				} else {
					payType.add(1);
					fprice = itemPrice.getCostUseGold();
				}
				floorPrice.add(fprice);
				discount.add(100);
				OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
				boolean isDiscount = true;
				if (operationConfig.getShopDiscount() < Common.SHOP_DISCOUNT_DEF) {
					if (operationConfig.getShopNoDiscountId() != null && !("").equals(operationConfig.getShopNoDiscountId())) {
						String[] shopIds = operationConfig.getShopNoDiscountId().split(",");
						for (String shopId : shopIds) {
							if (Integer.parseInt(shopId) == itemPrice.getShopItem().getId()) {
								isDiscount = false;
								break;
							}
						}
					}
					if (isDiscount) {
						floorPrice.add((int) Math.ceil(fprice * (operationConfig.getShopDiscount() / 100.0)));
						discount.add(operationConfig.getShopDiscount());
					}
				} else {
					if (magnificationList != null && magnificationList.size() > 0) {
						for (Object[] obj : magnificationList) {
							if (Integer.parseInt(obj[0].toString()) == itemPrice.getShopItem().getId()) {
								floorPrice.add((int) Math.ceil(fprice * (Integer.parseInt(obj[1].toString()) / 100.0)));
								discount.add(Integer.parseInt(obj[1].toString()));
								break;
							}
						}
					}
				}
				recommended.add(shopType == 1);
			}
		}
		ShopList list = new ShopList();
		list.setId(ServiceUtils.getInts(id.toArray()));
		list.setMaintype(ServiceUtils.getBytes(maintype.toArray()));
		list.setSubtype(ServiceUtils.getBytes(subtype.toArray()));
		list.setIsOnSale(ServiceUtils.getBooleans(isOnSale.toArray()));
		list.setFloorPrice(ServiceUtils.getInts(floorPrice.toArray()));
		list.setPayType(ServiceUtils.getInts(payType.toArray()));
		list.setNewMark(ServiceUtils.getBooleans(newMark.toArray()));
		list.setDiscount(ServiceUtils.getInts(discount.toArray()));
		list.setRecommended(ServiceUtils.getBooleans(recommended.toArray()));
		player.sendData(list);
	}

}