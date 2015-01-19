package com.wyd.empire.world.server.service.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IProbabilityDao;
import com.wyd.empire.world.server.service.base.IProbabilityService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class ProbabilityService extends UniversalManagerImpl implements IProbabilityService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IProbabilityDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ProbabilityService";

	public ProbabilityService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IProbabilityService getInstance(ApplicationContext context) {
		return (IProbabilityService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IProbabilityDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IProbabilityDao getDao() {
		return this.dao;
	}

	/**
	 * 根据权值随机获取一个概率id
	 * 
	 * @param idList
	 * @return
	 */
	public int getProbabilityId(List<Integer> idList) {
		Map<Integer, Integer> pMap = dao.getpMap();
		int sum = 0;
		for (Integer id : idList) {
			sum += pMap.get(id);
		}
		int ran = ServiceUtils.getRandomNum(1, sum + 1);
		sum = 0;
		for (Integer id : idList) {
			sum += pMap.get(id);
			if (ran <= sum) {
				return id;
			}
		}
		return 0;
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}
}