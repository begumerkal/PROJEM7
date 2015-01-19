package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Promotions;
import com.wyd.empire.world.dao.IPromotionsDao;
import com.wyd.empire.world.server.service.base.IPromotionsService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class PromotionsService extends UniversalManagerImpl implements IPromotionsService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPromotionsDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PromotionsService";

	public PromotionsService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IPromotionsService getInstance(ApplicationContext context) {
		return (IPromotionsService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPromotionsDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPromotionsDao getDao() {
		return this.dao;
	}

	/**
	 * 获取促销列表
	 * 
	 * @return
	 */
	public List<Promotions> getPromotionsList(int sex) {
		return this.dao.getPromotionsList(sex);
	}

	/**
	 * 获取商城促销列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllPromotions(String key, int pageIndex, int pageSize) {
		return this.dao.findAllPromotions(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePromotions(String ids) {
		dao.deletePromotions(ids);
	}
}