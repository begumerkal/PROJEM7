package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IMagnificationDao;
import com.wyd.empire.world.entity.mysql.Magnification;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class MagnificationDao extends UniversalDaoHibernate implements IMagnificationDao {
	public MagnificationDao() {
		super();
	}

	/**
	 * 根据区域查询出所有的促销商品
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Magnification> findAllMagnification() {
		return (List<Magnification>) this.getList(" FROM Magnification WHERE areaId = ? ", new Object[]{ServiceManager.getManager()
				.getConfiguration().getString("areaid")});
	}

	/**
	 * 根据多个ID值删除活动促销商品
	 * 
	 * @param ids
	 *            多个ID值，中间以,分割
	 */
	public void deleteByIds(String ids) {
		this.execute(" DELETE Magnification WHERE id in(" + ids + ")", new Object[]{});
	}
}