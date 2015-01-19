package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.PlayerStaWeek;
import com.wyd.empire.world.dao.IPlayerStaWeekDao;
import com.wyd.empire.world.server.service.base.IPlayerStaWeekService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class PlayerStaWeekService extends UniversalManagerImpl implements IPlayerStaWeekService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerStaWeekDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ConsortiarightService";

	public PlayerStaWeekService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IPlayerStaWeekService getInstance(ApplicationContext context) {
		return (IPlayerStaWeekService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerStaWeekDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerStaWeekDao getDao() {
		return this.dao;
	}

	/**
	 * 根据玩家id获取玩家指定周或月的记录
	 * 
	 * @param playerId
	 *            用户角色
	 * @param type
	 *            0周，1月
	 * @param wrmNum
	 *            周或月数
	 * @return 用户角色排行数据
	 */
	public PlayerStaWeek getPlayerStaWeekByPlayerId(int playerId, int type, int wrmNum) {
		return dao.getPlayerStaWeekByPlayerId(playerId, type, wrmNum);
	}

	/**
	 * 获得玩家排行榜
	 * 
	 * @param type
	 *            0周榜，1月榜
	 * @param orderBy
	 * @param max
	 *            返回最大记录数
	 * @return
	 */
	public List<PlayerStaWeek> getTopRecord(int type, int wrmNum, String property, String orderBy, int max) {
		return dao.getTopRecord(type, wrmNum, property, orderBy, max);
	}

	public void clearTopData(int type, int wrmNum, int lastWrmNum) {
		dao.clearTopData(type, wrmNum, lastWrmNum);
	}
}