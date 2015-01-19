package com.wyd.empire.world.server.service.base.impl;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.cache.StarConfig;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StarsInfo;
import com.wyd.empire.world.dao.IStarsInfoDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IStarsInfoService;

/**
 * The service class for the TabTool entity.
 */
public class StarsInfoService extends UniversalManagerImpl implements IStarsInfoService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IStarsInfoDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "StarsInfoService";

	public StarsInfoService() {
		super();
	}

	/**
	 * Returns the singleton <code>IStarsInfoervice</code> instance.
	 */
	public static IStarsInfoService getInstance(ApplicationContext context) {
		return (IStarsInfoService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IStarsInfoDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IStarsInfoDao getDao() {
		return this.dao;
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		this.dao.initData();
	}

	@Override
	public StarsInfo getByLevel(int Level) {
		return this.dao.getByLevel(Level);
	}

	/**
	 * 获取所有的升星信息
	 * 
	 * @return
	 */
	public Collection<StarsInfo> getAllStarsConfig() {
		return dao.getAllStarsInfo();
	}

	/**
	 * 获取升星属性加成
	 * 
	 * @param item
	 *            基础数据
	 * @param star
	 *            装备星级
	 * @param type
	 *            属性类型 1攻击，2血量，3防御，4暴击，5免暴,6破防
	 * @return
	 */
	@Override
	public int getStarsAddition(ShopItem item, int star, int type) {
		StarsInfo config = getByLevel(star);
		int value = 0;
		if (null == config)
			return 0;
		switch (type) {
			case 1 :
				value = (item.getAddAttack() * config.getAddATRate() / 100);
				break;
			case 2 :
				value = (item.getAddHp() * config.getAddHPRate() / 100);
				break;
			case 3 :
				value = (item.getAddDefend() * config.getAddDFRate() / 100);
				break;
			case 4 :
				value = (item.getAddCritical() * config.getAddCriticalRate() / 100);
				break;
			case 5 :
				value = (item.getAddReduceCrit() * config.getAddReduceRate() / 100);
				break;
			case 6 :
				value = (item.getAddWreckDefense() * config.getAddWreckRate() / 100);
				break;
			default :
				value = 0;
				break;
		}
		return value;
	}

	public void sendConfig(WorldPlayer player) {
		if (player == null || !player.isOnline())
			return;
		Collection<StarsInfo> starInfos = dao.getAllStarsInfo();
		int size = starInfos.size();
		int[] starLevel = new int[size];
		int[] attackRate = new int[size];
		int[] defendRate = new int[size];
		int[] hpRate = new int[size];
		int[] starExp = new int[size];

		int goldRate = 0, i = 0;

		for (StarsInfo config : starInfos) {
			starLevel[i] = i + 1;
			attackRate[i] = config.getAddATRate();
			defendRate[i] = config.getAddDFRate();
			hpRate[i] = config.getAddHPRate();
			starExp[i] = config.getNeedExp();
			goldRate = config.getGoldRate();
			i++;
		}
		StarConfig config = new StarConfig();
		config.setAttackRate(attackRate);
		config.setDefendRate(defendRate);
		config.setStarExp(starExp);
		config.setStarLevel(starLevel);
		config.setHpRate(hpRate);
		config.setGoldRate(goldRate);
		player.sendData(config);
	}

	@Override
	public StarsInfo getStarConfig(int level) {
		return dao.getStarConfig(level);
	}

}