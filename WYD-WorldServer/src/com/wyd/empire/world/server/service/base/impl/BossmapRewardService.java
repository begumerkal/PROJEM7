package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.PlayerRewardVo;
import com.wyd.empire.world.bean.BossmapReward;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IBossmapRewardDao;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IBossmapRewardService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabBossmapReward entity.
 */
public class BossmapRewardService extends UniversalManagerImpl implements IBossmapRewardService {
	private Logger log = Logger.getLogger(BossmapRewardService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IBossmapRewardDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "BossmapRewardService";

	public BossmapRewardService() {
		super();
	}

	/**
	 * Returns the singleton <code>IBossmapRewardService</code> instance.
	 */
	public static IBossmapRewardService getInstance(ApplicationContext context) {
		return (IBossmapRewardService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IBossmapRewardDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IBossmapRewardDao getDao() {
		return this.dao;
	}

	/**
	 * 根据副本ID获得副本的显示奖励的物品
	 * 
	 * @param mapId
	 *            副本ID
	 * @param count
	 *            返回的奖励物品数量
	 * @return
	 */
	public List<BossmapReward> getBossmapRewardBymapId(int mapId, int sex, int count) {
		List<BossmapReward> brList = dao.getBossmapRewardListByMapId(mapId, 4, sex);
		List<BossmapReward> retList = new ArrayList<BossmapReward>();
		HashSet<Integer> idSet = new HashSet<Integer>();
		while (brList.size() > 0 && retList.size() < count) {
			int index = ServiceUtils.getRandomNum(0, brList.size());
			BossmapReward bossmapReward = brList.get(index);
			if (!idSet.contains(bossmapReward.getShopItemId().intValue())) {
				idSet.add(bossmapReward.getShopItemId().intValue());
				retList.add(bossmapReward);
			}
			brList.remove(index);
		}

		if (retList.size() < count) {
			brList = dao.getBossmapRewardListByMapId(-1, 4, sex);
			while (brList.size() > 0 && retList.size() < count) {
				int index = ServiceUtils.getRandomNum(0, brList.size());
				BossmapReward bossmapReward = brList.get(index);
				if ((sex == bossmapReward.getSex().intValue() || 2 == bossmapReward.getSex().intValue())
						&& !idSet.contains(bossmapReward.getShopItemId().intValue())) {
					idSet.add(bossmapReward.getShopItemId().intValue());
					retList.add(bossmapReward);
				}
				brList.remove(index);
			}
		}
		idSet = null;
		brList = null;
		return retList;
	}

	/**
	 * 随机获取6*n件奖励物品
	 * 
	 * @return
	 */
	public List<RewardItemsVo> getRewardItems(List<Combat> combatList, int battleId) {
		if (null == combatList || combatList.size() <= 0)
			return null;
		List<RewardItemsVo> rivList = new ArrayList<RewardItemsVo>();
		BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
		if (battleTeam == null)
			return null;
		int mapId = battleTeam.getMapId();
		List<BossmapReward> brList = dao.getBossmapRewardListByMapId(mapId, battleTeam.getDifficulty());
		brList.addAll(dao.getBossmapRewardListByMapId(-1, battleTeam.getDifficulty()));
		if (brList.size() < 1) {
			log.error("数据库副本地图mapid=" + mapId + "奖励数据没有");
			return null;
		}
		List<WorldPlayer> playerList = new ArrayList<WorldPlayer>();
		for (Combat c : combatList) {
			playerList.add(c.getPlayer());
		}
		RewardItemsVo riv;
		PlayerRewardVo prvo;
		List<RewardItemsVo> combatRivList;
		DailyRewardVo dailyRewardVo = ServiceManager.getManager().getDailyActivityService().getSmashEggReward();
		for (Combat c : combatList) {
			combatRivList = new ArrayList<RewardItemsVo>();
			riv = getRandShopItemWithRewardType(brList, battleTeam.getDifficulty(), c.getSex(), c.getId(), mapId, false, dailyRewardVo);
			rivList.add(riv);
			combatRivList.add(riv);
			riv = getRandShopItemWithRewardType(brList, battleTeam.getDifficulty(), c.getSex(), c.getId(), mapId, false, dailyRewardVo);
			rivList.add(riv);
			combatRivList.add(riv);
			riv = getRandShopItemWithRewardType(brList, battleTeam.getDifficulty(), c.getSex(), c.getId(), mapId, true, dailyRewardVo);
			rivList.add(riv);
			combatRivList.add(riv);
			riv = getRandShopItemWithRewardType(brList, battleTeam.getDifficulty(), c.getSex(), c.getId(), mapId, true, dailyRewardVo);
			rivList.add(riv);
			combatRivList.add(riv);
			riv = getRandShopItemWithRewardType(brList, battleTeam.getDifficulty(), c.getSex(), c.getId(), mapId, true, dailyRewardVo);
			rivList.add(riv);
			combatRivList.add(riv);
			riv = getRandShopItemWithRewardType(brList, battleTeam.getDifficulty(), c.getSex(), c.getId(), mapId, true, dailyRewardVo);
			rivList.add(riv);
			combatRivList.add(riv);
			c.setRivList(combatRivList);
			prvo = new PlayerRewardVo();
			prvo.setPlayerList(playerList);
			prvo.setRewardList(combatRivList);
			ServiceManager.getManager().getBossBattleTeamService().getPlayerReward().put(c.getId(), prvo);
		}
		while (rivList.size() < 18) {
			rivList.add(getRandShopItemWithRewardType(brList, battleTeam.getDifficulty(), 0, 0, mapId, false, dailyRewardVo));
		}
		brList = null;
		return rivList;
	}

	private RewardItemsVo getRandShopItemWithRewardType(List<BossmapReward> brList, int difficulty, int sex, int playerId, int mapId,
			boolean isDiamond, DailyRewardVo dailyRewardVo) {
		int ranId = ServiceManager.getManager().getProbabilityService()
				.getProbabilityId(dao.getProbabilityIdByMapId(mapId, difficulty, isDiamond));
		List<BossmapReward> rewardList = new ArrayList<BossmapReward>();
		for (BossmapReward br : brList) {
			if (br.getSex().intValue() == sex || br.getSex().intValue() == 2) {
				if (br.getProbabilityId().intValue() != ranId || (!isDiamond && br.getRewardType().intValue() == 4)) {
					continue;
				}
				rewardList.add(br);
			}
		}
		BossmapReward randBr = rewardList.get(ServiceUtils.getRandomNum(0, rewardList.size()));
		rewardList = null;
		RewardItemsVo riv = new RewardItemsVo();
		riv.setOwnerId(playerId);
		riv.setDiamond(isDiamond);
		riv.setItemId(randBr.getShopItemId());
		riv.setItemName(randBr.getName());
		riv.setItemIcon(randBr.getIcon());
		int count = randBr.getCount();
		int days = randBr.getDays();
		com.wyd.empire.world.server.service.impl.DailyActivityService dailyActivityService = ServiceManager.getManager()
				.getDailyActivityService();
		if (riv.getItemId() == Common.GOLDID) {
			count = dailyActivityService.getRewardedVal(count, dailyRewardVo.getGold());
		} else if (riv.getItemId() == Common.BADGEID) {
			count = dailyActivityService.getRewardedVal(count, dailyRewardVo.getMedal());
		} else {
			days = dailyActivityService.getRewardedVal(days, dailyRewardVo.getEquipmentDay());
		}
		riv.setDays(days);
		riv.setCount(count);
		return riv;
	}

	/**
	 * GM工具--根据BOSS地图获取副本奖励
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findMapReward(String key, int pageIndex, int pageSize) {
		return dao.findMapReward(key, pageIndex, pageSize);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}
}