package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.MapEvent;
import com.wyd.empire.world.dao.IMapDao;
import com.wyd.empire.world.server.service.base.IMapService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class MapService extends UniversalManagerImpl implements IMapService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IMapDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "MapService";

	public MapService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IMapService getInstance(ApplicationContext context) {
		return (IMapService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IMapDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IMapDao getDao() {
		return this.dao;
	}

	/**
	 * 获得普通战斗地图列表
	 * 
	 * @return 普通战斗地图列表
	 */
	public List<Map> getBattleMap() {
		return dao.getBattleMap();
	}

	/**
	 * 获取非普通战斗副本地图列表
	 * 
	 * @return 非普通战斗副本地图列表
	 */
	public List<Map> getBossMap() {
		return dao.getBossMap();
	}

	/**
	 * GM工具查询地图列表
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllMap(String key, int pageIndex, int pageSize) {
		return dao.findAllMap(key, pageIndex, pageSize);
	}

	@Override
	public List<Map> getWorldBossMap() {

		return dao.getWorldBossMap();
	}

	@Override
	public List<Map> getSingleMap() {

		return dao.getSingleMap();
	}

	@Override
	public MapEvent getMapEvent(int eventId) {
		return (MapEvent) dao.get(MapEvent.class, eventId);
	}
}