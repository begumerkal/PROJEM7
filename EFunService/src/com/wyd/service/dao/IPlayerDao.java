package com.wyd.service.dao;

import java.util.List;

import com.app.db.dao.UniversalDao;
import com.app.db.page.PageList;
import com.wyd.service.bean.Player;

public interface IPlayerDao extends UniversalDao {
	public Player getById(int playerId);
	public List<Player> getByAccountId(int accountId);
	public PageList findPageList(int areaId,int index,int size);
}
