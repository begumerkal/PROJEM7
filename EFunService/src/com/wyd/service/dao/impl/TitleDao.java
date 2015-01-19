package com.wyd.service.dao.impl;

import java.util.Date;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.PlayerDIYTitle;
import com.wyd.service.bean.PlayerTaskTitle;
import com.wyd.service.bean.Title;
import com.wyd.service.dao.ITittleDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class TitleDao extends UniversalDaoHibernate implements ITittleDao {
	public TitleDao() {
		super();
	}

	
	@Override
	public Title getTitleById(int titleId) {
		return (Title)get(Title.class, titleId);
	}

	@Override
	public PlayerTaskTitle getPlayerTaskTitle(int playerId) {
		String hql = "from " + PlayerTaskTitle.class.getSimpleName() + " where playerId=? order by id desc";
        @SuppressWarnings("unchecked")
		List<PlayerTaskTitle> playerTaskTitle = getList(hql, new Object[] { playerId});
        if(playerTaskTitle==null || playerTaskTitle.size()<1)return null;
        return playerTaskTitle.get(0);
	}


	@Override
	public PlayerDIYTitle getSelDIYTitle(int playerId) {
		String hql = "from " + PlayerDIYTitle.class.getSimpleName() + " where ";
		hql += " startDate<=? and endDate>=?";
		hql += " and playerId=? and state = 1";
		Date date = new Date();
		@SuppressWarnings("unchecked")
		List<PlayerDIYTitle> playerDIYTitle = getList(hql, new Object[] { date, date, playerId });
		if(playerDIYTitle==null || playerDIYTitle.size()<1)return null;
	    return playerDIYTitle.get(0);
	}

	
	

}