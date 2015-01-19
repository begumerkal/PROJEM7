package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Magnification;

/**
 * The service interface for the TabGuai entity.
 */
public interface IMagnificationService extends UniversalManager {
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