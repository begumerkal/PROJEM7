package com.wyd.empire.world.server.service.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.cache.StrongRateList;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StoneRate;
import com.wyd.empire.world.bean.StrenthPercent;
import com.wyd.empire.world.bean.Successrate;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IStrengthenDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IStrengthenService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class StrengthenService extends UniversalManagerImpl implements IStrengthenService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IStrengthenDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "StrengthenService";

	public StrengthenService() {
		super();
	}

	/**
	 * Returns the singleton <code>IStrengthenService</code> instance.
	 */
	public static IStrengthenService getInstance(ApplicationContext context) {
		return (IStrengthenService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IStrengthenDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IStrengthenDao getDao() {
		return this.dao;
	}

	/**
	 * 根据随机数获得洗练对象
	 * 
	 * @param randomNum
	 * @return
	 */
	public WeapSkill getWeapSkillByRandom(int randomNum) {
		return dao.getWeapSkillByRandom(randomNum);
	}

	/**
	 * 玩家强化获得洗练技能
	 * 
	 * @param strongeLevel
	 * @param startLevel
	 * @param pifs
	 */
	public PlayerItemsFromShop giveWeapSkillToPlayer(int strongeLevel, PlayerItemsFromShop pifs) {
		List<WeapSkill> list = dao.getWeapSkillOneLevel();
		// 获得特殊标示
		Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
		int startLevel1 = map.get("strongeLevel1");
		int startLevel2 = map.get("strongeLevel2");
		WeapSkill ws = list.get(ServiceUtils.getRandomNum(0, list.size()));
		if (strongeLevel >= startLevel1) {
			if (strongeLevel < startLevel2 && pifs.getWeapSkill1() == 0) {
				if (pifs.getWeapSkill2() != 0) {
					pifs.setWeapSkill1(pifs.getWeapSkill2());
					pifs.setWeapSkill2(0);
				} else {
					pifs.setWeapSkill1(ws.getId());
				}
			} else if (strongeLevel >= startLevel2 && (pifs.getWeapSkill2() == 0 || pifs.getWeapSkill1() == 0)) {
				if (pifs.getWeapSkill1() == 0) {
					pifs.setWeapSkill1(ws.getId());
					list.remove(ws);
					ws = list.get(ServiceUtils.getRandomNum(0, list.size()));
				}
				// if(ws.getId()==pifs.getWeapSkill1()){
				// list.remove(ws);
				// ws = list.get(ServiceUtils.getRandomNum(0, list.size()));
				// }
				pifs.setWeapSkill2(ws.getId());
			}
		}
		return pifs;
	}

	/**
	 * 强化失败清除洗练技能
	 * 
	 * @param pifs
	 */
	public void deleteWeapSkill(PlayerItemsFromShop pifs) {
		if (pifs.getWeapSkill2() != 0) {
			if (pifs.getWeapSkill1() < pifs.getWeapSkill2()) {
				pifs.setWeapSkill1(pifs.getWeapSkill2());
			}
			pifs.setWeapSkill2(0);
		} else {
			pifs.setWeapSkill1(0);
		}
		dao.update(pifs);
	}

	/**
	 * 根据类型和数量获得相应石头的概率
	 * 
	 * @param type
	 * @param num
	 * @return
	 */
	public StoneRate getStoneRateByNumAndType(int type, int num) {
		return dao.getStoneRateByNumAndType(type, num);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	public WeapSkill getWeapSkillById(int itemId) {
		return dao.getWeapSkillById(itemId);
	}

	/**
	 * 获取武器被动技能分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getWeapSkillList(int pageNum, int pageSize) {
		return dao.getWeapSkillList(pageNum, pageSize);
	}

	/**
	 * 获得强化加成数据
	 * 
	 * @param item
	 *            物品
	 * @param level
	 *            强化等级
	 * @param type
	 *            属性类型 1攻击，2血量，3防御 ，4暴击，5免暴,6破防
	 * @return
	 */
	@Override
	public int getStrengthenAddition(ShopItem item, int level, int type) {
		Successrate successrate = dao.getSuccessRateByLevel(level);
		if (null == successrate)
			return 0;
		int value = 0;
		switch (type) {
			case 1 :
				value = item.getAddAttack();
				break;
			case 2 :
				value = item.getAddHp();
				break;
			case 3 :
				value = item.getAddDefend();
				break;
			case 4 :// 暴击
				value = item.getAddCritical();
				break;
			case 5 :// 免暴
				value = item.getAddReduceCrit();
				break;
			case 6 :// 破防
				value = item.getAddWreckDefense();
				break;
		}
		if (item.isWeapon()) {
			value = (value * successrate.getAddpower() / 10000);
		} else if (item.isBody()) {
			value = (value * successrate.getAddbody() / 10000);
		} else if (item.isWing()) {
			value = (value * successrate.getAddwing() / 10000);
		} else if (item.isRing()) {
			value = (value * successrate.getAddring() / 10000);
		} else if (item.isNecklace()) {
			value = (value * successrate.getAddnecklace() / 10000);
		} else if (item.isFace()) {
			value = (value * successrate.getAddface() / 10000);
		} else if (item.isHead()) {
			value = (value * successrate.getAddhead() / 10000);
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public void sendRate(WorldPlayer player) {
		List<Successrate> list = getAll(Successrate.class);
		int[] rateId = new int[list.size()];
		int[] rateGold = new int[list.size()];
		int[] rateAddpower = new int[list.size()];
		int[] rateStone1 = new int[list.size()];
		int[] rateStone2 = new int[list.size()];
		int[] rateStone3 = new int[list.size()];
		int[] rateStone4 = new int[list.size()];
		int[] rateStone5 = new int[list.size()];
		int[] addhead = new int[list.size()]; // 头饰血量增幅
		int[] addface = new int[list.size()]; // 脸谱血量增幅
		int[] addbody = new int[list.size()]; // 着装血量增幅
		int[] addwing = new int[list.size()]; // 翅膀血量增幅
		int[] rateRealStone1 = new int[list.size()];
		int[] rateRealStone2 = new int[list.size()];
		int[] rateRealStone3 = new int[list.size()];
		int[] rateRealStone4 = new int[list.size()];
		int[] rateRealStone5 = new int[list.size()];
		int[] addRing = new int[list.size()]; // 戒指血量增幅
		int[] addNecklace = new int[list.size()]; // 项链血量增幅

		int index = 0;
		for (Successrate sr : list) {
			rateId[index] = sr.getId();
			rateGold[index] = (int) ServiceManager.getManager().getBuffService().getAddition(player, sr.getGold(), Buff.CGOLDLOW);
			rateAddpower[index] = sr.getAddpower();
			rateStone1[index] = sr.getStone1();
			rateStone2[index] = sr.getStone2();
			rateStone3[index] = sr.getStone3();
			rateStone4[index] = sr.getStone4();
			rateStone5[index] = sr.getStone5();
			rateRealStone1[index] = sr.getStone1real();
			rateRealStone2[index] = sr.getStone2real();
			rateRealStone3[index] = sr.getStone3real();
			rateRealStone4[index] = sr.getStone4real();
			rateRealStone5[index] = sr.getStone5real();
			addhead[index] = sr.getAddhead();
			addface[index] = sr.getAddface();
			addbody[index] = sr.getAddbody();
			addwing[index] = sr.getAddwing();
			addRing[index] = sr.getAddring();
			addNecklace[index] = sr.getAddnecklace();
			index++;
		}
		List<StrenthPercent> list1 = getAll(StrenthPercent.class);
		int[] percentLow = new int[list1.size()];
		int[] percentHigh = new int[list1.size()];
		String[] wordDesc = new String[list1.size()];

		index = 0;
		for (StrenthPercent str1 : list1) {
			percentLow[index] = str1.getPercentLow();
			percentHigh[index] = str1.getPercentHigh();
			wordDesc[index] = str1.getWordDesc();
			index++;
		}
		StrongRateList rate = new StrongRateList();
		rate.setRateId(rateId);
		rate.setRateGold(rateGold);
		rate.setRateAddpower(rateAddpower);
		rate.setRateStone1(rateStone1);
		rate.setRateStone2(rateStone2);
		rate.setRateStone3(rateStone3);
		rate.setRateStone4(rateStone4);
		rate.setRateStone5(rateStone5);
		rate.setAddbody(addbody);
		rate.setAddface(addface);
		rate.setAddhead(addhead);
		rate.setAddwing(addwing);
		rate.setRateRealStone1(rateRealStone1);
		rate.setRateRealStone2(rateRealStone2);
		rate.setRateRealStone3(rateRealStone3);
		rate.setRateRealStone4(rateRealStone4);
		rate.setRateRealStone5(rateRealStone5);

		rate.setAddRing(addRing);
		rate.setAddNecklace(addNecklace);

		rate.setPercentLow(percentLow);
		rate.setPercentHigh(percentHigh);
		rate.setWordDesc(wordDesc);

		if (null == ServiceManager.getManager().getVersionService().getVersion().getStrenthFlag()
				|| 0 == ServiceManager.getManager().getVersionService().getVersion().getStrenthFlag()) {
			rate.setStrenthFlag(0);
		} else {
			rate.setStrenthFlag(1);
		}
		player.sendData(rate);
	}
}