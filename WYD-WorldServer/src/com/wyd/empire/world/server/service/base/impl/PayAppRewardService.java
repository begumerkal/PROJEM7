package com.wyd.empire.world.server.service.base.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPayAppReward;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.dao.IPayAppRewardDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPayAppRewardService;
import com.wyd.empire.world.server.service.base.IRechargeRewardService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabConsortiaright entity.
 */
public class PayAppRewardService extends UniversalManagerImpl implements IPayAppRewardService {
	Logger log = Logger.getLogger(PayAppRewardService.class);

	/**
	 * The dao instance injected by Spring.
	 */
	private IPayAppRewardDao dao;

	public PayAppRewardService() {
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
	public void setDao(IPayAppRewardDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	@Override
	public List<PayAppReward> findAllReward(int sex) {
		return dao.findAllReward(sex);
	}

	@Override
	public List<PayAppReward> findAllReward() {
		List<PayAppReward> list = dao.findAllReward();
		for (PayAppReward payAppReward : list) {
			Hibernate.initialize(payAppReward.getShopItem());
		}
		return list;
	}

	@Override
	public boolean isExistCode(String code, String account, int playerId) {
		PlayerPayAppReward log = dao.getByCode(code, account, playerId);
		if (log == null)
			return false;
		String itemIds = log.getItemIds();
		return StringUtils.hasText(itemIds);
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
	public void givenItems(WorldPlayer worldPlayer, int count, int day, int shopItemId, int strongLevel) throws Exception {
		int days = -1;
		int userNum = -1;
		if (count == -1) {
			days = day;
		} else {
			userNum = count;
		}
		if (userNum > 1000 || days > 1000) {
			throw new Exception("物品给予数据异常");
		}
		if (strongLevel == -1) {
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.playerGetItem(worldPlayer.getId(), shopItemId, -1, days, userNum, 28, "付费包奖励", 0, 0, 0);
		} else {
			PlayerItemsFromShop playerItemsFromShop = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(worldPlayer.getId(), shopItemId);
			ShopItem playerItem = null;
			boolean isUpdate = false;
			if (playerItemsFromShop == null) {
				playerItemsFromShop = new PlayerItemsFromShop();
				playerItem = ServiceManager.getManager().getShopItemService().getShopItemById(shopItemId);
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
				playerItemsFromShop.setPLastNum(userNum);
				playerItemsFromShop.setPLastTime(days);
				playerItemsFromShop.setPUseLastTime(0);
				playerItemsFromShop.setSkillful(0);
				playerItemsFromShop.updateStrongLevel(strongLevel);
				playerItemsFromShop.setWeapSkill1(0);
				playerItemsFromShop.setWeapSkill2(0);
			} else {
				isUpdate = true;
				playerItem = playerItemsFromShop.getShopItem();
				if (userNum != -1)
					playerItemsFromShop.setPLastNum(playerItemsFromShop.getPLastNum() + userNum);
				if (playerItemsFromShop.getPLastTime() != -1) {
					if (days != -1)
						playerItemsFromShop.setPLastTime(playerItemsFromShop.getPLastTime() + days);
				}
				if (strongLevel > playerItemsFromShop.getStrongLevel())
					playerItemsFromShop.updateStrongLevel(strongLevel);
			}
			if (isUpdate) {
				ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItemsFromShop);
				Map<String, String> info = new HashMap<String, String>();
				info.put("lastTime", playerItemsFromShop.getPLastTime() + "");
				info.put("lastNum", playerItemsFromShop.getPLastNum() + "");
				ServiceManager.getManager().getPlayerItemsFromShopService().sendUpdateItem(worldPlayer, playerItemsFromShop.getId(), info);
			} else {
				playerItemsFromShop = ServiceManager.getManager().getPlayerItemsFromShopService()
						.savePlayerItemsFromShop(playerItemsFromShop);
				ServiceManager.getManager().getPlayerItemsFromShopService().sendAddItem(worldPlayer, playerItemsFromShop);
			}
			ServiceManager
					.getManager()
					.getPlayerService()
					.writeLog(
							"玩家获得物品记录：玩家Id=" + worldPlayer.getId() + ",物品Id=" + shopItemId + ",获得方式=" + 15 + ",获得数量=" + userNum + ",使用天数="
									+ days + ",获得时间=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(worldPlayer.getId(), shopItemId, days, userNum, 28, 0, "付费包奖励");
			worldPlayer.updateFight();
		}
	}

	/**
	 * 根据id查询出奖励物品
	 * 
	 * @return
	 */
	public PayAppReward findRewardById(int id) {
		PayAppReward reward = (PayAppReward) dao.get(PayAppReward.class, id);
		if (reward != null) {
			Hibernate.initialize(reward.getShopItem());
		}
		return reward;
	}

	/**
	 * 更新所有mail
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public void updateMail(String title, String content) {
		dao.updateMail(title, content);
	}

	/**
	 * 获取付费包奖励列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllPayAppReward(String key, int pageIndex, int pageSize) {
		return dao.findAllPayAppReward(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteAppReward(String ids) {
		dao.deleteAppReward(ids);
	}

}