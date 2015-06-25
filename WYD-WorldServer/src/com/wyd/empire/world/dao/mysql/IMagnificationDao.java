package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.Magnification;

/**
 * The DAO interface for the TabTask entity.
 */
public interface IMagnificationDao extends UniversalDao {
	/**
	 * 根据区域查询出所有的促销商品
	 * 
	 * @return
	 */
	public List<Magnification> findAllMagnification();

	/**
	 * 根据多个ID值删除活动促销商品
	 * 
	 * @param ids
	 *            多个ID值，中间以,分割
	 */
	public void deleteByIds(String ids);
}