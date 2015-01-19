package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.cache.WishList;
import com.wyd.empire.protocol.data.cache.ZflhList;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WishItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.dao.ILotteryDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ILotteryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

public class LotteryService extends UniversalManagerImpl implements ILotteryService {
	/**
	 * The dao instance injected by Spring.
	 */
	private ILotteryDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "LotteryService";

	public LotteryService() {
		super();
	}

	/**
	 * Returns the singleton <code>ILotteryService</code> instance.
	 */
	public static ILotteryService getInstance(ApplicationContext context) {
		return (ILotteryService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(ILotteryDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public ILotteryDao getDao() {
		return this.dao;
	}

	/**
	 * 返回许愿结果
	 * 
	 * @return
	 */
	public WishItem getWishResult() {
		int totalPrecent = 0;
		List<WishItem> wishList = dao.getWishItemList();
		for (WishItem wish : wishList) {
			totalPrecent += wish.getRate();
		}
		int random = ServiceUtils.getRandomNum(1, totalPrecent + 1);
		int precent = 0;
		for (WishItem wish : wishList) {
			precent += wish.getRate();
			if (precent >= random) {
				return wish;
			}
		}
		log.error("错误！按概率取不到许愿得到的物品 @zengxc");
		return wishList.get(0);
	}

	/**
	 * 配置表单价
	 **/
	public Map<String, Integer> getUnitPrice() {
		String specialMark = ServiceManager.getManager().getVersionService().getVersion().getUnitPrice();
		specialMark = specialMark.substring(1, specialMark.length());
		specialMark = specialMark.substring(0, specialMark.length() - 1);
		specialMark = specialMark.replaceAll("\"", "");
		String[] markStr = specialMark.split(",");
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] str;
		for (String s : markStr) {
			str = s.split(":");
			map.put(str[0], Integer.parseInt(str[1]));
		}
		return map;
	}

	/**
	 * 获取许愿列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<WishItem> getWishItemList() {
		return dao.getWishItemList();
	}

	/**
	 * 根据id查询许愿
	 * 
	 * @param id
	 * @return
	 */
	public WishItem getById(int id) {
		return dao.getById(id);
	}

	@Override
	public void sendWishList(WorldPlayer player) {
		List<WishItem> wishList = dao.getWishItemList();
		int size = wishList.size(), i = 0;
		int[] id = new int[size];
		int[] num = new int[size];

		for (WishItem item : wishList) {
			if (item.getType() == 0) {// 普通物品
				RewardInfo reward = item.getReward(player.getPlayer().getSex());
				id[i] = reward.getItemId();
				num[i] = reward.getCount();
			} else if (item.getType() == 1) {// 神秘格子
				id[i] = Common.MysteriousGrid;
				num[i] = 1;
			}
			i++;
		}
		WishList list = new WishList();
		list.setNum(num);
		list.setId(id);
		player.sendData(list);
	}

	public void sendZflhList(WorldPlayer player) {
		int zfspNum = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemNum(player.getId(), Common.ZFSPID);
		List<WishItem> zflhList = dao.getZflhList();
		int size = zflhList.size();
		int[] id = new int[size];
		int[] state = new int[size];
		String[] itemId = new String[size];
		String[] itemNum = new String[size];
		ZflhList list = new ZflhList();
		String record = player.getPlayerInfo().getZflhRecord();

		int i = 0;
		for (WishItem zflh : zflhList) {
			id[i] = zflh.getId();
			List<RewardInfo> rewardList = zflh.getRewardList(player.getPlayer().getSex());
			List<Integer> itemIds = new ArrayList<Integer>();
			List<Integer> itemNums = new ArrayList<Integer>();
			for (RewardInfo reward : rewardList) {
				itemIds.add(reward.getItemId());
				itemNums.add(reward.getCount());
			}
			itemId[i] = JSONArray.fromObject(itemIds).toString();
			itemNum[i] = JSONArray.fromObject(itemNums).toString();
			state[i] = 0;
			int minNum = 0;// 领取礼盒需要的最小祝福碎片数量
			switch (zflh.getId()) {
				case 17 :
					minNum = 25;
					break;
				case 18 :
					minNum = 50;
					break;
				case 19 :
					minNum = 75;
					break;
				case 20 :
					minNum = 100;
					break;
			}
			if (zfspNum >= minNum) {
				state[i] = 1;// 可领取
			}

			record = record == null ? "" : record;
			if (record.indexOf(zflh.getId() + "") != -1) {
				state[i] = 2;// 已领取
			}
			i++;
		}
		list.setId(id);
		list.setItemId(itemId);
		list.setItemNum(itemNum);
		list.setState(state);
		player.sendData(list);
	}

	/**
	 * 领取祝福 礼盒
	 * 
	 * @param playerId
	 * @param num
	 */
	@Override
	public boolean playerGetGift(WorldPlayer player, int gridId) {
		PlayerInfo playerInfo = player.getPlayerInfo();
		int playerId = player.getId();
		int sex = player.getPlayer().getSex();
		boolean result = playerGetGift(playerId, playerInfo, sex, gridId);
		if (result) {
			// 更新祝福礼盒
			sendZflhList(player);
		}
		return result;
	}

	private boolean playerGetGift(int playerId, PlayerInfo playerInfo, int sex, int gridId) {
		if (gridId < 17 || gridId > 20)
			return false;
		String record = playerInfo.getZflhRecord();
		record = record == null ? "" : record;
		if (record.indexOf(gridId + "") != -1) {
			return false;// 表示已经领取过了
		}

		int minNum = 0;// 领取礼盒需要的最小祝福碎片数量
		switch (gridId) {
			case 17 :
				minNum = 25;
				break;
			case 18 :
				minNum = 50;
				break;
			case 19 :
				minNum = 75;
				break;
			case 20 :
				minNum = 100;
				break;
		}
		int zfspNum = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(playerId, Common.ZFSPID).getPLastNum();
		if (zfspNum < minNum) {
			return false;
		}
		// 发放奖励
		sendReward(playerId, gridId, sex);
		if (StringUtils.hasText(record)) {
			record += ",";
		}
		// 保存已领取的进度
		playerInfo.setZflhRecord(record + gridId);
		return true;
	}

	// 发放奖励
	private void sendReward(int playerId, int gridId, int sex) {
		WishItem wishitem = (WishItem) dao.get(WishItem.class, gridId);
		List<RewardInfo> rewardList = wishitem.getRewardList(sex);
		for (RewardInfo reward : rewardList) {
			try {
				int getitemid = reward.getItemId(), itemcount = reward.getCount();
				if (getitemid == Common.GOLDID) {
					WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
					ServiceManager.getManager().getPlayerService().updatePlayerGold(player, itemcount, "爱心许愿", "");
				} else if (getitemid == Common.DIAMONDID) {
					WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
					ServiceManager.getManager().getPlayerService()
							.addTicket(player, itemcount, 0, TradeService.ORIGIN_LOVE, 0, "", "", "", "");
				} else {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(playerId, reward.getItemId(), reward.getCount(), 14, "", 0, 0, 0);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 清空祝福碎片数量和祝福礼盒领取记录（满足条件但未领取的则自动发放） <br>
	 * 供定时器每月1号调用</br>
	 */
	@Override
	public void sysResetZfsp() {
		Collection<WorldPlayer> playerList = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
		// 检查玩家是否有未领取的礼盒，有则自动发放
		for (WorldPlayer player : playerList) {
			try {
				resetZfsp(player, false);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * 清空祝福碎片数量和祝福礼盒领取记录（满足条件但未领取的则自动发放）
	 * 
	 * @param playerId
	 * @param validate
	 *            true 需要检查是否跨月
	 */
	@Override
	public void resetZfsp(WorldPlayer player, boolean validate) {
		PlayerInfo playerInfo = player.getPlayerInfo();
		if (validate) {
			if (playerInfo.getZfspUpdateTime() == null)
				return;
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(playerInfo.getZfspUpdateTime());
			int m = cal1.get(Calendar.MONTH) + 1;
			if (m == DateUtil.getCurrentMonth()) {
				return;
			}
		}
		PlayerItemsFromShop zfspItem = ServiceManager.getManager().getPlayerItemsFromShopService()
				.uniquePlayerItem(player.getId(), Common.ZFSPID);
		int zfsp = zfspItem.getPLastNum();
		// 小于25表示没有礼包
		if (zfsp < 25) {
			return;
		}
		int gridId = 0;
		if (zfsp >= 100) {
			gridId = 20;
		} else if (zfsp >= 75) {
			gridId = 19;
		} else if (zfsp >= 50) {
			gridId = 18;
		} else if (zfsp >= 25) {
			gridId = 17;
		}
		while (gridId > 16) {
			String record = playerInfo.getZflhRecord();
			record = record == null ? "" : record;
			// 表示未领取过了
			if (record.indexOf(gridId + "") == -1) {
				sendReward(player.getId(), gridId, player.getPlayer().getSex());
				if (StringUtils.hasText(record)) {
					record += ",";
				}
				// 保存已领取的进度
				playerInfo.setZflhRecord(record + gridId);
			}
			gridId--;
		}
		Calendar cal2 = Calendar.getInstance();
		playerInfo.setZfspUpdateTime(cal2.getTime());
		playerInfo.setZflhRecord("");
		zfspItem.setPLastNum(0);
		dao.update(zfspItem);
		ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, zfspItem);

	}
}