package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.Share;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IShareDao extends UniversalDao {

	public List<Share> findBy(int shareType);

	public Share getByTarget(String target);

}