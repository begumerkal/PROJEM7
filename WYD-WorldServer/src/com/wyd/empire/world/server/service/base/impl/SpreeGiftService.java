package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.SpreeGift;
import com.wyd.empire.world.dao.ISpreeGiftDao;
import com.wyd.empire.world.server.service.base.ISpreeGiftService;

public class SpreeGiftService extends UniversalManagerImpl implements ISpreeGiftService {
	/**
	 * The dao instance injected by Spring.
	 */
	private ISpreeGiftDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "SpreeGiftService";

	public SpreeGiftService() {
		super();
	}

	/**
	 * Returns the singleton <code>ISpreeGiftService</code> instance.
	 */
	public static ISpreeGiftService getInstance(ApplicationContext context) {
		return (ISpreeGiftService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(ISpreeGiftDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public ISpreeGiftDao getDao() {
		return this.dao;
	}

	/**
	 * 返回开启礼包结果
	 * 
	 * @return
	 */
	public List<SpreeGift> getSpreeGiftResult(int shopItemId, int type) {
		return dao.getSpreeGiftResult(shopItemId, type);
	}

	/**
	 * 判断是否已经有此礼包的信息
	 * 
	 * @param itmeId
	 * @return
	 */
	public boolean isExistShopItmeId(int itmeId) {
		return dao.isExistShopItmeId(itmeId);
	}
}