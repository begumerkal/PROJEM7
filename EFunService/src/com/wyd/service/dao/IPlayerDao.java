package com.wyd.service.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.service.bean.Player;

public interface IPlayerDao extends UniversalDao {
	public Player getById(int playerId);
	public List<Player> getByAccountId(int accountId);
	public PageList findPageList(int areaId,int index,int size);
}
