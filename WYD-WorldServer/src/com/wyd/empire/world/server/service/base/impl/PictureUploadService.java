package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.dao.IPictureUploadDao;
import com.wyd.empire.world.server.service.base.IPictureUploadService;

public class PictureUploadService extends UniversalManagerImpl implements IPictureUploadService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPictureUploadDao dao;

	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PictureUploadService";

	public PictureUploadService() {
		super();
	}

	/**
	 * Returns the singleton <code>IPictureUploadService</code> instance.
	 */
	public static IPictureUploadService getInstance(ApplicationContext context) {
		return (IPictureUploadService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPictureUploadDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPictureUploadDao getDao() {
		return this.dao;
	}

	@Override
	public PlayerPicture getPictureUploadById(int playerId) {
		return dao.getPictureUploadById(playerId);
	}

	/**
	 * GM工具--查询玩家自定义信息情况
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList getPicturePageList(String key, int pageIndex, int pageSize) {
		return dao.getPicturePageList(key, pageIndex, pageSize);
	}

	/**
	 * GM工具--查询玩家自定义信息情况
	 * 
	 * @param ids
	 * @return
	 */
	public List<PlayerPicture> getPictureListById(String ids) {
		return dao.getPictureListById(ids);
	}
}
