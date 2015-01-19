package com.wyd.empire.world.server.service.base.impl;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.dao.IGuaiDao;
import com.wyd.empire.world.server.service.base.IGuaiService;

/**
 * The service class for the TabGuai entity.
 */
public class GuaiService extends UniversalManagerImpl implements IGuaiService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IGuaiDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "GuaiService";

	public GuaiService() {
		super();
	}

	/**
	 * Returns the singleton <code>IGuaiService</code> instance.
	 */
	public static IGuaiService getInstance(ApplicationContext context) {
		return (IGuaiService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IGuaiDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IGuaiDao getDao() {
		return this.dao;
	}

	/**
	 * GM工具查询出怪物列表
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllGuai(String key, int pageIndex, int pageSize) {
		return dao.findAllGuai(key, pageIndex, pageSize);
	}

	@Override
	public void initData() {
		dao.initData();
	}

	@Override
	public GuaiPlayer getGuaiById(int difficulty, int guaiId) {
		return dao.getGuaiById(difficulty, guaiId);
	}
}