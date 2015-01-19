package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.ExchangeItem;
import com.wyd.empire.world.bean.ExchangeRate;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IPlayerItemsFromShopDao;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PlayerItemsFromShopDao extends UniversalDaoHibernate implements IPlayerItemsFromShopDao {
	private Map<Integer, VipRate> itemMap = null;
	private Map<Integer, Map<Integer, PlayerItemsFromShop>> playerItemMap = new HashMap<Integer, Map<Integer, PlayerItemsFromShop>>();

	public PlayerItemsFromShopDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		if (itemMap == null) {
			itemMap = new HashMap<Integer, VipRate>();
		} else {
			itemMap.clear();
		}
		List<VipRate> vrList = getAll(VipRate.class);
		for (VipRate vr : vrList) {
			itemMap.put(vr.getId(), vr);
		}
	}

	/**
	 * 加载玩家装备
	 * 
	 * @param playerId
	 */
	@SuppressWarnings("unchecked")
	public void loadPlayerItem(int playerId) {
		if (!playerItemMap.containsKey(playerId)) {
			String hql = "from PlayerItemsFromShop where playerId=?";
			List<PlayerItemsFromShop> pifList = getList(hql, new Object[]{playerId});
			Map<Integer, PlayerItemsFromShop> itemMap = new HashMap<Integer, PlayerItemsFromShop>();
			playerItemMap.put(playerId, itemMap);
			for (PlayerItemsFromShop pifs : pifList) {
				pifs.initAllItemProperty();
				itemMap.put(pifs.getId(), pifs);
				// 计算数量的物品剩余数量不能小于0
				if (pifs.getShopItem().getUseType() == 0 && pifs.getPLastNum() < 0) {
					pifs.setPLastNum(0);
					update(pifs);
				}
				// 计算数量的物品剩余天数必须为-1
				if (pifs.getShopItem().getUseType() == 0 && pifs.getPLastTime() != -1) {
					pifs.setPLastTime(-1);
					update(pifs);
				}
				// 计算天数的物品剩余数量必须为-1
				if (pifs.getShopItem().getUseType() == 1 && pifs.getPLastNum() != -1) {
					pifs.setPLastNum(-1);
					update(pifs);
				}
			}
		}
	}

	/**
	 * 重新加载玩家装备
	 * 
	 * @param playerId
	 * @param playerItemId
	 */
	public void reloadPlayerItem(int playerId, int playerItemId) {
		System.out.println("重新加载玩家装备 ");
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(playerId);
		if (null != itemMap) {
			PlayerItemsFromShop item = (PlayerItemsFromShop) get(PlayerItemsFromShop.class, playerItemId);
			if (null != item) {
				item.initAllItemProperty();
				itemMap.put(playerItemId, item);
			} else {
				itemMap.remove(playerItemId);
			}
			log.info("重载玩家:" + playerId + " 玩家物品:" + playerItemId);
		}
	}

	/**
	 * 从数据库查询玩家的物品
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerItemsFromShop> getItemForPlayerByBD(int playerId, int itemId) {
		String hql = "from PlayerItemsFromShop where playerId=? and shopItem.id=?";
		List<PlayerItemsFromShop> playerItemList = getList(hql, new Object[]{playerId, itemId});
		if (playerItemList != null && playerItemList.size() > 0) {
			return playerItemList;
		}
		return null;
	}

	/**
	 * 卸载玩家装备
	 * 
	 * @param playerId
	 */
	public void unLoadPlayerItem(int playerId) {
		playerItemMap.remove(playerId);
	}

	/**
	 * 玩家新增物品
	 * 
	 * @param pifs
	 */
	public PlayerItemsFromShop savePlayerItemsFromShop(PlayerItemsFromShop pifs) {
		pifs = (PlayerItemsFromShop) save(pifs);
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(pifs.getPlayerId());
		if (null != itemMap) {
			itemMap.put(pifs.getId(), pifs);
		}
		return pifs;
	}

	/**
	 * 玩家删除物品
	 * 
	 * @param pifs
	 */
	public void deletePlayerItem(PlayerItemsFromShop pifs) {
		if (null == pifs)
			return;
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(pifs.getPlayerId());
		if (null != itemMap) {
			itemMap.remove(pifs.getId().intValue());
		}
		remove(pifs.getClass(), pifs.getId());
	}

	/**
	 * 根据玩家ID获取玩家装备列表(物品数量不为0)
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家装备列表
	 */
	public List<PlayerItemsFromShop> getPlayerItemsFromShopByPlayerId(int playerId) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		List<PlayerItemsFromShop> list = new ArrayList<PlayerItemsFromShop>();
		PlayerItemsFromShop pifs;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getPLastNum() != Common.PLAYER_ITEMS_FROM_SHOP_P_LAST_NUM) {
					list.add(pifs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 根据玩家ID获取玩家装备列表（其中装备必须是剩余数量不为0，剩余时间不为0）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家装备列表
	 */
	public PageList getPlayerItemsFromShopByPlayerId(int playerId, int pageNum, int pageSize) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		List<PlayerItemsFromShop> list = new ArrayList<PlayerItemsFromShop>();
		PlayerItemsFromShop pifs;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getPLastNum() != Common.PLAYER_ITEMS_FROM_SHOP_P_LAST_NUM) {
					list.add(pifs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Comparator<PlayerItemsFromShop> ascComparator = new BuyTimeComparator();
		Collections.sort(list, ascComparator);
		List<Object> valueList = new ArrayList<Object>();
		int fullSize = list.size();
		for (int i = (pageNum - 1) * pageSize; i <= pageNum * pageSize && i < fullSize; i++) {
			i = i > 0 ? i : 0;
			valueList.add(list.get(i));
		}
		PageList pageList = new PageList(fullSize, valueList, pageSize, pageNum - 1);
		return pageList;
	}

	/**
	 * 根据玩家ID及关务类型，获得原装备的id
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param itemType
	 *            装备类型
	 * @return 装备ID
	 */
	public Integer getOldItemId(int playerId, int itemType) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		PlayerItemsFromShop pifs;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getIsInUsed() && pifs.getShopItem().getType() == itemType) {
					return pifs.getShopItem().getId();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据玩家ID获得特殊效果对象（Vip，双倍卡）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 特殊效果对象（Vip，双倍卡）
	 */
	public List<PlayerItemsFromShop> getSpecialEffectId(int playerId) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		List<PlayerItemsFromShop> list = new ArrayList<PlayerItemsFromShop>();
		PlayerItemsFromShop pifs;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getShopItem().getId().intValue() == Common.DOUBLEEXPID || pifs.getShopItem().getId().intValue() == Common.VIPID) {
					list.add(pifs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 根据玩家角色ID和物品ID获得玩家指定物品对象
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @param itemId
	 *            物品ID
	 * @return 玩家指定物品对象
	 */
	public List<PlayerItemsFromShop> getPlayerItemByItemId(int playerId, int itemId) {
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(playerId);
		List<PlayerItemsFromShop> list;
		if (null != itemMap) {
			List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(itemMap.values());
			list = new ArrayList<PlayerItemsFromShop>();
			for (PlayerItemsFromShop pifs : pifList) {
				if (pifs.getShopItem().getId().intValue() == itemId) {
					list.add(pifs);
				}
			}
		} else {
			list = getItemForPlayerByBD(playerId, itemId);
			for (PlayerItemsFromShop pifs : list) {
				pifs.initAllItemProperty();
			}
		}
		return list;
	}

	/**
	 * 获取玩家指定的物品(唯一)
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	@Override
	public PlayerItemsFromShop uniquePlayerItem(int playerId, int itemId) {
		List<PlayerItemsFromShop> listPlayerItem = getPlayerItemByItemId(playerId, itemId);
		if (listPlayerItem.size() > 1) {
			for (int i = 1; i < listPlayerItem.size(); i++) {
				deletePlayerItem(listPlayerItem.get(i));
				log.info("删除玩家：" + playerId + " 重复物品：" + listPlayerItem.get(i).getShopItem().getId());
			}
		}
		if (listPlayerItem.size() > 0) {
			return listPlayerItem.get(0);
		}
		return null;
	}

	/**
	 * 玩家角色ID和物品ID获得玩家勋章数量
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @return 玩家勋章数量
	 */
	public PlayerItemsFromShop getBadgeByPlayerId(int playerId) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		PlayerItemsFromShop pifs;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getShopItem().getId().intValue() == Common.BADGEID) {
					return pifs;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 检测装备第N天后过期
	 * 
	 * @param num
	 *            为0时，表示当天过期
	 * @return 过期物品玩家ID和物品ID
	 */
	public List<Object> checkEquipmentOverTimeAfterNDays(int playerId, Long num) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		List<Object> list = new ArrayList<Object>();
		PlayerItemsFromShop pifs;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getPLastTime() != -1) {
					Date buyTime = pifs.getBuyTime();
					Calendar c = Calendar.getInstance();
					c.setTime(buyTime);
					c.add(Calendar.DAY_OF_MONTH, (int) (pifs.getPLastTime() - num));
					buyTime = c.getTime();
					if (DateUtil.compareDateOnDay(buyTime, new Date()) == 0) {
						list.add(new Object[]{playerId, pifs.getShopItem().getName()});
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 根据玩家角色Id，获取所拥有的可以强化或合成的物品列表
	 * 
	 * @param playerId
	 *            玩家角色Id
	 * @return 可以强化或合成的物品列表
	 */
	public List<PlayerItemsFromShop> getStrengthenShopItemList(int playerId) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		List<PlayerItemsFromShop> list = new ArrayList<PlayerItemsFromShop>();
		PlayerItemsFromShop pifs;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getPLastNum() != 0
						&& (pifs.getShopItem().getType() <= Common.SHOP_ITEM_TYPE_ARMS_OTHER || pifs.getShopItem().getType() >= Common.SHOP_ITEM_TYPE_ARMS_MOSAIC1)) {
					list.add(pifs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Comparator<PlayerItemsFromShop> ascComparator = new ItemIdComparator();
		Collections.sort(list, ascComparator);
		return list;
	}

	@SuppressWarnings("unchecked")
	public void updateVipRank(int areaId) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select tv.id,tv.rank from tab_viprecord tv,tab_player tp where tv.playerId = tp.id and tp.areaId = ?  order by tv.vipExp desc");
		values.add(areaId);
		List<Object> list = this.getListBySql(hql.toString(), values.toArray());
		hql.setLength(0);
		hql.append("update tab_viprecord set rankChange = ?,rank = ? where id = ?");
		int index = 1;
		Object[] obj;
		for (Object vr : list) {
			obj = (Object[]) vr;
			executeSql(hql.toString(),
					new Object[]{Integer.parseInt(obj[1].toString()) - index, index, Integer.parseInt(obj[0].toString())});
			index++;
		}
	}

	/**
	 * 获得兑换商品列表
	 * 
	 * @param player
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Exchange> checkExchangeList(WorldPlayer player) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM Exchange AS ex WHERE 1 = 1 ");
		hql.append(" AND ex.playerId = ?");
		values.add(player.getId());
		List<Exchange> list = getList(hql.toString(), values.toArray());
		if (list.size() == 0 || (new Date().getTime() - list.get(0).getTime().getTime()) > 2 * 60 * 60 * 1000) {
			long timeDiff = 0;
			List<Exchange> eList = getList("from Exchange where playerId = ? ", new Object[]{player.getId()}, 1);
			if (eList.size() != 0) {
				timeDiff = (new Date().getTime() - eList.get(0).getTime().getTime()) % (2 * 60 * 60 * 1000);
			}
			long timeStart = new Date().getTime() - timeDiff;
			list = refreshExchange(player, timeStart);
		}
		return list;
	}

	/**
	 * 刷新兑换列表
	 * 
	 * @param player
	 */
	@SuppressWarnings("unchecked")
	public List<Exchange> refreshExchange(WorldPlayer player, long timeStart) {
		deleteExchange(player.getId());
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		// 添加新的记录
		hql.append("FROM ExchangeItem ex WHERE ex.shopItem.sex in (?,2)");
		values.add(player.getPlayer().getSex().intValue());
		List<ExchangeItem> exList = getList(hql.toString(), values.toArray());
		ExchangeItem ei;
		ExchangeRate er;
		int randomNum = 0;
		List<Exchange> list = new ArrayList<Exchange>();
		for (int i = 0; i < 3; i++) {
			ei = exList.get(ServiceUtils.getRandomNum(0, exList.size()));
			randomNum = ServiceUtils.getRandomNum(0, 9999);
			er = (ExchangeRate) getUniqueResult("from ExchangeRate where  startRate <=? and ?<endRate", new Object[]{randomNum, randomNum});
			Exchange ex = new Exchange();
			ex.setCount(-1);
			ex.setDays(er.getDay());
			ex.setPlayerId(player.getId());
			ex.setShopItem(ei.getShopItem());
			ex.setTime(new Date(timeStart));
			if (ei.getShopItem().isWeapon()) {
				ex.setCostUseBadge(er.getWpPrice());
			} else if (ei.getShopItem().isBody()) {
				ex.setCostUseBadge(er.getBodyPrice());
			} else if (ei.getShopItem().isFace()) {
				ex.setCostUseBadge(er.getFacePrice());
			} else if (ei.getShopItem().isHead()) {
				ex.setCostUseBadge(er.getHeadPrice());
			}
			this.save(ex);
			list.add(ex);
			exList.remove(ei);
		}
		return list;
	}

	public void deleteExchange(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("DELETE FROM Exchange AS ex WHERE 1 = 1 ");
		hql.append(" AND ex.playerId = ?");
		values.add(playerId);
		this.execute(hql.toString(), values.toArray());
	}

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
	public PageList findAllByPlayerId(String key, int pageIndex, int pageSize) {
		loadPlayerItem(Integer.parseInt(key));
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(Integer.parseInt(key)).values());
		List<Object> valueList = new ArrayList<Object>();
		int fullSize = pifList.size();
		pageIndex--;
		for (int i = pageIndex * pageSize; i <= (pageIndex + 1) * pageSize && i < fullSize; i++) {
			i = i > 0 ? i : 0;
			valueList.add(pifList.get(i));
		}
		PageList pageList = new PageList(fullSize, valueList, pageSize, pageIndex);
		return pageList;
	}

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
	public void updateInUseByShopId(int playerId, int shopId, boolean isInUse) {
		loadPlayerItem(playerId);
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(playerId);
		PlayerItemsFromShop pifs = itemMap.get(shopId);
		if (null != pifs) {
			pifs.setIsInUsed(isInUse);
			pifs.setUpdateTime(new Date());
			update(pifs);
		}
	}

	/**
	 * 清除过期记录(每隔N天清理一次)
	 */
	public void deleteOverDateRecord(int days) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("DELETE FROM " + StrongeRecord.class.getSimpleName() + "  WHERE 1 = 1 ");
		hql.append(" AND TO_DAYS(now()) - TO_DAYS(createTime) >= ? ");
		values.add((long) days);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 获取玩家物品强化的最高等级
	 */
	public int getTopLevelForItemByPlayer(int playerId) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		PlayerItemsFromShop pifs;
		int maxStrongLevel = 0;
		for (int i = 0; i < pifList.size(); i++) {
			try {
				pifs = pifList.get(i);
				if (pifs.getStrongLevel() > maxStrongLevel) {
					maxStrongLevel = pifs.getStrongLevel();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxStrongLevel;
	}

	/**
	 * 获取玩家指定物品的数量
	 */
	public int getPlayerItemNum(int playerId, int itemId) {
		loadPlayerItem(playerId);
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(playerId);
		PlayerItemsFromShop pifs = itemMap.get(itemId);
		if (null != pifs) {
			return pifs.getPLastNum();
		}
		return 0;
	}

	/**
	 * 更新玩家指定物品的数量
	 */
	public void updatePlayerItemNum(int playerId, int itemId, int speakerCount) {
		loadPlayerItem(playerId);
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(playerId);
		PlayerItemsFromShop pifs = itemMap.get(itemId);
		if (null != pifs) {
			pifs.setPLastNum(speakerCount);
			pifs.setUpdateTime(new Date());
			update(pifs);
		}
	}

	/**
	 * 根据ID获得viprate
	 * 
	 * @param id
	 * @return
	 */
	public VipRate getVipRateById(int id) {
		return itemMap.get(id);
	}

	/**
	 * 获取玩家物品最高星级
	 * 
	 * @param playerId
	 */
	public int getTopStarForPlayer(int playerId) {
		List<PlayerItemsFromShop> pifsList = getStrengthenShopItemList(playerId);
		int star = 0;
		for (PlayerItemsFromShop pifs : pifsList) {
			if (pifs.getStars() > star)
				star = pifs.getStars();
		}
		return star;
	}

	/**
	 * 玩家在使用的物品
	 */
	@Override
	public List<PlayerItemsFromShop> getInUseItem(int playerId) {
		loadPlayerItem(playerId);
		List<PlayerItemsFromShop> pifList = new ArrayList<PlayerItemsFromShop>(playerItemMap.get(playerId).values());
		List<PlayerItemsFromShop> list = new ArrayList<PlayerItemsFromShop>();
		for (PlayerItemsFromShop pifs : pifList) {
			if (pifs.getIsInUsed()) {
				list.add(pifs);
			}
		}
		return list;
	}

	/**
	 * 根据id查找玩家物品
	 * 
	 * @param playerId
	 * @param id
	 * @return
	 */
	public PlayerItemsFromShop getPlayerItemById(int playerId, int playerItemId) {
		Map<Integer, PlayerItemsFromShop> itemMap = playerItemMap.get(playerId);
		if (null != itemMap) {
			return itemMap.get(playerItemId);
		}
		return null;
	}

	public class BuyTimeComparator implements Comparator<PlayerItemsFromShop> {
		public int compare(PlayerItemsFromShop combat1, PlayerItemsFromShop combat2) {
			return (int) (combat2.getBuyTime().getTime() - combat1.getBuyTime().getTime());
		}
	}

	public class ItemIdComparator implements Comparator<PlayerItemsFromShop> {
		public int compare(PlayerItemsFromShop combat1, PlayerItemsFromShop combat2) {
			return (combat1.getShopItem().getId() - combat2.getShopItem().getId());
		}
	}
}