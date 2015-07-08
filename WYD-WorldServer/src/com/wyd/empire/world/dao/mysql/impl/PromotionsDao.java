package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.mysql.IPromotionsDao;
import com.wyd.empire.world.entity.mysql.Promotions;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class PromotionsDao extends UniversalDaoHibernate implements IPromotionsDao {
	public PromotionsDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Promotions> getPromotionsList(int sex) {
		return getList("FROM " + Promotions.class.getSimpleName() + " WHERE (shopItem.sex=? or shopItem.sex=2) AND isActivate='Y' ",
				new Object[]{sex});
	}

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
	public PageList findAllPromotions(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Promotions.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.hasText(params[i])) {
				switch (i) {
					case 0 :
						if (ServiceUtils.isNumeric(params[0])) {
							hsql.append(" AND shopItem.id = ? ");
							values.add(Integer.parseInt(params[0]));
						} else {
							hsql.append(" AND (shopItem.name like '" + params[0] + "%' or shopItem.name like '%" + params[0]
									+ "' or shopItem.name like '%" + params[0] + "%') ");
						}
						break;
					default :
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePromotions(String ids) {
		this.execute(" DELETE Promotions WHERE id in (" + ids + ")", new Object[]{});
	}
}