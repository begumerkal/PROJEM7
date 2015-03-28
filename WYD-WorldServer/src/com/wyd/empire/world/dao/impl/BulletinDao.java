package com.wyd.empire.world.dao.impl;

import java.util.Date;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.bean.Push;
import com.wyd.empire.world.dao.IBulletinDao;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class BulletinDao extends UniversalDaoHibernate implements IBulletinDao {
	public BulletinDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Bulletin> getBulletinList() {
		String hsql = "from " + Bulletin.class.getSimpleName()
				+ " where areaId = ? and ? BETWEEN startTime and endTime and isActivation = 'Y'";
		return getList(hsql, new Object[]{WorldServer.config.getAreaId(), new Date()});
	}

	@SuppressWarnings("unchecked")
	public List<Bulletin> getAllBulletinList() {
		String hsql = "from " + Bulletin.class.getSimpleName() + " where areaId = ?";
		return getList(hsql, new Object[]{WorldServer.config.getAreaId()});
	}

	/**
	 * 根据多个ID值删除公告
	 * 
	 * @param ids
	 *            多个公告ID，中间以,分割
	 */
	public void deleteBulletin(String ids) {
		this.execute("DELETE " + Bulletin.class.getSimpleName() + " WHERE id in(" + ids + ")", new Object[]{});
	}

	@SuppressWarnings("unchecked")
	public List<Push> getAllPushList() {
		String hsql = "from " + Push.class.getSimpleName() + " where areaId = ?";
		return getList(hsql, new Object[]{WorldServer.config.getAreaId()});
	}
}