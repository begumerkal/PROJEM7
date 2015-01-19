package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.BossmapBuff;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IBossmapBuffDao extends UniversalDao {
	public void initData();

	public BossmapBuff getBossmapBuffById(int bbId);

	public List<BossmapBuff> findByGroup(int group);
}