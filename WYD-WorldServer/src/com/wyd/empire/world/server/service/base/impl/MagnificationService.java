package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Magnification;
import com.wyd.empire.world.dao.IMagnificationDao;
import com.wyd.empire.world.server.service.base.IMagnificationService;

/**
 * The service class for the TabGuai entity.
 */
public class MagnificationService extends UniversalManagerImpl implements IMagnificationService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IMagnificationDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "MagnificationService";

	public MagnificationService() {
		super();
	}

	/**
	 * Returns the singleton <code>IGuaiService</code> instance.
	 */
	public static IMagnificationService getInstance(ApplicationContext context) {
		return (IMagnificationService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IMagnificationDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IMagnificationDao getDao() {
		return this.dao;
	}

	/**
	 * 根据区域查询出所有的促销商品
	 * 
	 * @return
	 */
	public List<Magnification> findAllMagnification() {
		return dao.findAllMagnification();
	}

	/**
	 * 根据多个ID值删除活动促销商品
	 * 
	 * @param ids
	 *            多个ID值，中间以,分割
	 */
	public void deleteByIds(String ids) {
		dao.deleteByIds(ids);
	}
}