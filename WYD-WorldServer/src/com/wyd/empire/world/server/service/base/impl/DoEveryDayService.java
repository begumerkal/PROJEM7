package com.wyd.empire.world.server.service.base.impl;

import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.DoEveryDay;
import com.wyd.empire.world.dao.IDoEveryDayDao;
import com.wyd.empire.world.dao.impl.DoEveryDayDao.DoEveryDayVo;
import com.wyd.empire.world.server.service.base.IDoEveryDayService;

public class DoEveryDayService extends UniversalManagerImpl implements IDoEveryDayService {
	public DoEveryDayService() {
		super();
	}

	private IDoEveryDayDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "DoEveryDayService";

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IDoEveryDayService getInstance(ApplicationContext context) {
		return (IDoEveryDayService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IDoEveryDayDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IDoEveryDayDao getDao() {
		return this.dao;
	}

	@Override
	public void initDoEveryDay() {
		this.dao.initDoEveryDay();
	}

	@Override
	public List<DoEveryDay> getDoEveryDayListByType(byte type) {
		return this.dao.getDoEveryDayListByType(type);
	}

	@Override
	public List<DoEveryDayVo> getDoEveryDayList() {
		return this.dao.getDoEveryDayList();
	}
}
