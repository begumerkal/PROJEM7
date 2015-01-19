package com.wyd.empire.world.server.service.base.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.MonthCard;
import com.wyd.empire.world.dao.IMonthCardDao;
import com.wyd.empire.world.server.service.base.IMonthCardService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class MonthCardService extends UniversalManagerImpl implements IMonthCardService {
	Logger log = Logger.getLogger(MonthCardService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IMonthCardDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "MonthCardService";

	public MonthCardService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IMonthCardService getInstance(ApplicationContext context) {
		return (IMonthCardService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IMonthCardDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IMonthCardDao getDao() {
		return this.dao;
	}

	/**
	 * 获取所有月卡列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MonthCard> getAllMonthCard() {
		return dao.getAll(MonthCard.class);
	}
}