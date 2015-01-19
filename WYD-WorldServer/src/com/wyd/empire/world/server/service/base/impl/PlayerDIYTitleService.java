package com.wyd.empire.world.server.service.base.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.PlayerDIYTitle;
import com.wyd.empire.world.dao.IPlayerDIYTitleDao;
import com.wyd.empire.world.server.service.base.IPlayerDIYTitleService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class PlayerDIYTitleService extends UniversalManagerImpl implements IPlayerDIYTitleService {
	Logger log = Logger.getLogger(PlayerDIYTitleService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerDIYTitleDao dao;

	public PlayerDIYTitleService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IPlayerDIYTitleService getInstance(ApplicationContext context) {
		return (IPlayerDIYTitleService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerDIYTitleDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerDIYTitleDao getDao() {
		return this.dao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerDIYTitle> getDIYTitles(int playerId) {
		String hql = "from " + PlayerDIYTitle.class.getSimpleName() + " where 1=1 ";
		hql += " and startDate<=? and endDate>=?";
		hql += " and playerId=?";
		Date date = new Date();
		return dao.getList(hql, new Object[]{date, date, playerId});

	}

	@SuppressWarnings("unchecked")
	public List<PlayerDIYTitle> getDIYTitles() {
		String hql = "from " + PlayerDIYTitle.class.getSimpleName() + " where 1=1 ";
		hql += " and startDate<=? and endDate>=?";
		Date date = new Date();
		return dao.getList(hql, new Object[]{date, date});

	}

	@Override
	public PlayerDIYTitle getSelDIYTitle(int playerId) {
		String hql = "from " + PlayerDIYTitle.class.getSimpleName() + " where ";
		hql += " startDate<=? and endDate>=?";
		hql += " and playerId=? and state = 1";
		Date date = new Date();
		return (PlayerDIYTitle) dao.getUniqueResult(hql, new Object[]{date, date, playerId});
	}

	/**
	 * 把传过来的ID更新为选择，其它的更新为未选择。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String selectTitle(int playerId, int titleId) {
		String hql = "from " + PlayerDIYTitle.class.getSimpleName() + " where playerId = ?";
		List<PlayerDIYTitle> allTitle = dao.getList(hql, new Object[]{playerId});
		String title = "";
		for (PlayerDIYTitle diyTitle : allTitle) {
			if (diyTitle.getId().intValue() == titleId) {
				diyTitle.setState(1);
				title = diyTitle.getTitle();
			} else {
				diyTitle.setState(0);
			}
			dao.update(diyTitle);
		}
		return title;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlayerDIYTitle getTitleByName(int playerId, String title) {
		String hql = "from " + PlayerDIYTitle.class.getSimpleName() + " where playerId = ? and title = ?";
		hql += " and startDate<=? and endDate>=?";
		Date date = new Date();
		List<PlayerDIYTitle> titles = dao.getList(hql, new Object[]{playerId, title, date, date});
		if (titles != null && titles.size() > 0) {
			return titles.get(0);
		} else {
			return null;
		}
	}

}