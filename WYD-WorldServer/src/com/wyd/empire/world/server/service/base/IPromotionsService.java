package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Promotions;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IPromotionsService extends UniversalManager {
	/**
	 * 获取促销列表
	 * 
	 * @param sex
	 * @return
	 */
	public List<Promotions> getPromotionsList(int sex);

	/**
	 * 获取商城促销列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllPromotions(String key, int pageIndex, int pageSize);

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePromotions(String ids);
}