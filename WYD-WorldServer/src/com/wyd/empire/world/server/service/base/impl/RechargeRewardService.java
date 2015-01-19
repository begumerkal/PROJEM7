package com.wyd.empire.world.server.service.base.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IRechargeRewardDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.IRechargeRewardService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabConsortiaright entity.
 */
public class RechargeRewardService extends UniversalManagerImpl implements IRechargeRewardService {
	Logger log = Logger.getLogger(RechargeRewardService.class);
	List<RechargeReward> rechargeRewardList = null;
	List<RechargeReward> everyDayRechargeRewardList = null;
	List<LotteryReward> LotteryRewardList = null;
	/**
	 * The dao instance injected by Spring.
	 */
	private IRechargeRewardDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "RechargeRewardService";

	public RechargeRewardService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IRechargeRewardService getInstance(ApplicationContext context) {
		return (IRechargeRewardService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IRechargeRewardDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IRechargeRewardDao getDao() {
		return this.dao;
	}

	/**
	 * 初始化首冲奖励、抽奖列表
	 */
	public void findInitList() {

		rechargeRewardList = dao.findAllRechargeReward((short) 1);
		for (RechargeReward rechargeReward : rechargeRewardList) {
			Hibernate.initialize(rechargeReward.getShopItem());
		}
		everyDayRechargeRewardList = dao.findAllRechargeReward((short) 2);
		for (RechargeReward rechargeReward : everyDayRechargeRewardList) {
			Hibernate.initialize(rechargeReward.getShopItem());
		}
		LotteryRewardList = dao.findAllLotteryReward();
		for (LotteryReward lotteryReward : LotteryRewardList) {
			Hibernate.initialize(lotteryReward.getShopItem());
		}
	}

	/**
	 * 查询出所有首冲奖励物品
	 * 
	 * @return
	 */
	public List<RechargeReward> findAllRechargeReward() {
		return rechargeRewardList;
	}

	/**
	 * 查询出所有每日首冲奖励物品
	 * 
	 * @return
	 */
	public List<RechargeReward> findAllEveryDayRechargeReward() {
		return everyDayRechargeRewardList;
	}

	/**
	 * 查询出所有抽奖奖励物品
	 * 
	 * @return
	 */
	public List<LotteryReward> findAllLotteryReward() {
		return LotteryRewardList;
	}

	/**
	 * 随机获取抽奖奖励物品
	 * 
	 * @param randomNum
	 *            随机概率数
	 * @return
	 */
	public LotteryReward getLotteryReward(int randomNum) {
		List<LotteryReward> list = dao.getLotteryReward(randomNum);
		LotteryReward lotteryReward = list.get(ServiceUtils.getRandomNum(0, list.size()));
		Hibernate.initialize(lotteryReward.getShopItem());
		return lotteryReward;
	}

	/**
	 * 根据玩家ID查询玩家是否领取过首冲奖励
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public boolean getRechargeRewardLog(int playerId) {
		long count = dao.getRechargeRewardLog(playerId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 给予强化装备物品
	 * 
	 * @param worldPlayer
	 *            玩家对象
	 * @param count
	 *            使用个数
	 * @param day
	 *            使用天数
	 * @param shopItemId
	 *            商品ID
	 * @param strongLevel
	 *            强化等级
	 * @param remark
	 *            GM工具物品给予时做备注说明
	 * @throws Exception
	 */
	public void givenItems(WorldPlayer worldPlayer, int num, int day, int shopItemId, int strongLevel, String remark) throws Exception {
		int count = num == -1 ? day : num;
		if (count > 1000) {
			throw new Exception("物品给予数据异常");
		}
		IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
		if (strongLevel == -1) {
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.playerGetItem(worldPlayer.getId(), shopItemId, count, 15, remark, 0, 0, 0);
		} else {
			PlayerItemsFromShop playerItemsFromShop = playerItemsFromShopService.uniquePlayerItem(worldPlayer.getId(), shopItemId);
			ShopItem playerItem = ServiceManager.getManager().getShopItemService().getShopItemById(shopItemId);
			// 卡牌要特殊处理.卡牌每张都是一个独立的物品
			boolean isCard = playerItem.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARD;
			if (isCard) {
				playerGetCard(worldPlayer, playerItem, count, remark);
				return;
			}
			if (playerItemsFromShop == null) {
				playerItemsFromShop = new PlayerItemsFromShop();
				playerItemsFromShop.setShopItem(playerItem);
				playerItemsFromShop.setPlayerId(worldPlayer.getId());
				playerItemsFromShop.setBuyTime(new Date());
				playerItemsFromShop.setDispearAtOverTime(false);
				playerItemsFromShop.setHollNum(-1);
				playerItemsFromShop.setHollUsedNum(0);
				playerItemsFromShop.setIsInUsed(false);
				playerItemsFromShop.setPExpExtraRate(0);
				playerItemsFromShop.setAttackStone(-1);
				playerItemsFromShop.setDefenseStone(-1);
				playerItemsFromShop.setSpecialStone(-1);
				playerItemsFromShop.setPLastNum(-1);
				playerItemsFromShop.setPLastTime(-1);
				if (playerItem.getUseType() == 0) {
					playerItemsFromShop.setPLastNum(count);
				} else {
					playerItemsFromShop.setPLastTime(count);
				}
				playerItemsFromShop.setPUseLastTime(0);
				playerItemsFromShop.setSkillful(0);
				playerItemsFromShop.updateStrongLevel(strongLevel);
				playerItemsFromShop.setWeapSkill1(0);
				playerItemsFromShop.setWeapSkill2(0);
				playerItemsFromShop = playerItemsFromShopService.savePlayerItemsFromShop(playerItemsFromShop);
				playerItemsFromShopService.sendAddItem(worldPlayer, playerItemsFromShop);
			} else {
				playerItem = playerItemsFromShop.getShopItem();
				if (playerItem.getUseType() == 0) {
					playerItemsFromShop.setPLastNum(playerItemsFromShop.getPLastNum() + count);
				} else {
					playerItemsFromShop.setPLastTime(playerItemsFromShop.getPLastTime() + count);
				}
				Map<String, String> info = new HashMap<String, String>();
				info.put("lastTime", playerItemsFromShop.getPLastTime() + "");
				info.put("lastNum", playerItemsFromShop.getPLastNum() + "");
				playerItemsFromShopService.sendUpdateItem(worldPlayer, playerItemsFromShop.getId(), info);
				if (strongLevel > playerItemsFromShop.getStrongLevel())
					playerItemsFromShop.updateStrongLevel(strongLevel);
				playerItemsFromShopService.update(playerItemsFromShop);
			}
			if (null != worldPlayer) {
				if (Common.BUFFTYPE == playerItem.getType()) {
					worldPlayer.initialBuff();
				}
				if (Common.RING == playerItem.getId()) {
					worldPlayer.setHasring(true);
				}
				if (Common.BADGEID == playerItem.getId()) {
					worldPlayer.setMedalNum(playerItemsFromShop.getPLastNum());
				}
				if (Common.STARSOULDEBRISID == playerItem.getId()) {
					worldPlayer.setStarSoulDebrisNum(playerItemsFromShop.getPLastNum());
				}
			}
			ServiceManager
					.getManager()
					.getPlayerService()
					.writeLog(
							"玩家获得物品记录：玩家Id=" + worldPlayer.getId() + ",物品Id=" + shopItemId + ",获得方式=" + 15 + ",获得数量=" + num + ",使用天数="
									+ day + ",获得时间=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			playerItemsFromShopService.saveGetItemRecord(worldPlayer.getId(), shopItemId, day, num, 15, 0, remark);
			worldPlayer.updateFight();
		}
	}

	/**
	 * 卡牌要特殊处理.卡牌每张都是一个独立的物品
	 * 
	 * @param worldPlayer
	 * @param playerId
	 * @param si
	 * @param num
	 */
	private void playerGetCard(WorldPlayer worldPlayer, ShopItem si, int num, String remark) {
		for (int i = 0; i < num; i++) {
			PlayerItemsFromShop pifs = new PlayerItemsFromShop();
			pifs.setPlayerId(worldPlayer.getId());
			pifs.setShopItem(si);
			pifs.setBuyTime(new Date());
			pifs.setPLastNum(1);
			pifs.setPLastTime(-1);
			pifs.setIsInUsed(false);
			pifs.setPExpExtraRate(0);
			pifs.setHollNum(si.getHollForStoneId());
			pifs.setHollUsedNum(0);
			pifs.updateStrongLevel(0);
			pifs.setSkillful(0);
			pifs.setDispearAtOverTime(si.getDispearAtOverTime());
			pifs.setPUseLastTime(si.getUseLastTime());
			pifs.setWeapSkill1(0);
			pifs.setWeapSkill2(0);
			pifs.updateAttackStone(-1);
			pifs.updateDefenseStone(-1);
			pifs.updateSpecialStone(-1);
			// 保存PlayerItemsFromShop对象
			pifs = ServiceManager.getManager().getPlayerItemsFromShopService().savePlayerItemsFromShop(pifs);
			// 推送玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().sendAddItem(worldPlayer, pifs);
			ServiceManager
					.getManager()
					.getPlayerService()
					.writeLog(
							"玩家获得物品记录：玩家Id=" + worldPlayer.getId() + ",物品Id=" + si.getId() + ",获得方式=" + 15 + ",获得数量=" + 1 + ",使用天数=" + -1
									+ ",获得时间=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(worldPlayer.getId(), si.getId(), -1, 1, 15, 0, remark);
		}
	}

	/**
	 * 根据充值抽奖ID查询出充值抽奖对象
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public LotteryReward findLotteryRewardById(int id) {
		LotteryReward lotteryReward = dao.findLotteryRewardById(id);
		if (lotteryReward != null) {
			Hibernate.initialize(lotteryReward.getShopItem());
		}
		return lotteryReward;
	}

	/**
	 * 根据id首充奖励
	 * 
	 * @param id
	 *            充值抽奖ID
	 * @return
	 */
	public RechargeReward getRechargeRewardById(int id) {
		// return dao.rechargeRewardById(id);
		RechargeReward rechargeReward = dao.getRechargeRewardById(id);
		if (rechargeReward != null) {
			Hibernate.initialize(rechargeReward.getShopItem());
		}
		return rechargeReward;
	}

	/**
	 * 获取强化概率分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getSuccessrateList(int pageNum, int pageSize) {
		return dao.getSuccessrateList(pageNum, pageSize);
	}

	/**
	 * 获取首充奖励分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Object> getRechargeRewardList(int pageNum, int pageSize) {
		List<Object> list = dao.getRechargeRewardList(pageNum, pageSize).getList();
		if (list != null && list.size() > 0) {
			for (Object object : list) {
				Hibernate.initialize(((RechargeReward) object).getShopItem());
			}
		}
		return list;
	}
}