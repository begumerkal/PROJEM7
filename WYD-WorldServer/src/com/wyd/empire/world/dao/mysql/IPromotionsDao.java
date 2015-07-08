package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.entity.mysql.Promotions;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IPromotionsDao extends UniversalDao {
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