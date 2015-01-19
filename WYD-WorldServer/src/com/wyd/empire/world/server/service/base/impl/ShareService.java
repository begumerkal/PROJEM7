package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Share;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IShareDao;
import com.wyd.empire.world.server.service.base.IShareService;

/**
 * 
 * The service class for the TabConsortiaright entity.
 */
public class ShareService extends UniversalManagerImpl implements IShareService {
	Logger log = Logger.getLogger(ShareService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IShareDao dao;

	public ShareService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IShareService getInstance(ApplicationContext context) {
		return (IShareService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IShareDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	/**
	 * 随机取可编辑的分享内容
	 * 
	 * @return
	 */
	@Override
	public String getEditContent() {
		List<Share> shareList = dao.findBy(1);
		int index = ServiceUtils.getRandomNum(0, shareList.size());
		return shareList.get(index).getContent();
	}

	@Override
	public Share getShareByTarget(String target) {
		return dao.getByTarget(target);
	}

}