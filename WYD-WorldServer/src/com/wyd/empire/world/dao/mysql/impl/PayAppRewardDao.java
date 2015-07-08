package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.mysql.IPayAppRewardDao;
import com.wyd.empire.world.entity.mysql.PayAppReward;
import com.wyd.empire.world.entity.mysql.PlayerPayAppReward;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class PayAppRewardDao extends UniversalDaoHibernate implements IPayAppRewardDao {
	public PayAppRewardDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayAppReward> findAllReward(int sex) {
		String hql = "FROM " + PayAppReward.class.getSimpleName() + " where shopItem.sex=? or shopItem.sex=2";
		return getList(hql, new Object[]{sex});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PlayerPayAppReward getByCode(String code, String account, int playerId) {
		String hql = "FROM " + PlayerPayAppReward.class.getSimpleName() + " where code=? or account=? or playerId=?";
		List results = getList(hql, new Object[]{code, account, playerId});
		if (results == null || results.size() < 1)
			return null;
		return (PlayerPayAppReward) results.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayAppReward> findAllReward() {
		return getList("FROM " + PayAppReward.class.getSimpleName(), new Object[]{});
	}

	/**
	 * 更新所有mail
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public void updateMail(String title, String content) {
		// this.execute("UPDATE " + Admin.class.getSimpleName() +
		// " SET password = ? where id = ?", new Object[]{passWord,userId});
		String sql = "update tab_payapp_reward as tapp  set tapp.mail_title = ?, tapp.mail_content = ? ";
		this.executeSql(sql, new Object[]{title, content});
	}

	/**
	 * 获取付费包奖励列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllPayAppReward(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + PayAppReward.class.getSimpleName() + " WHERE 1 = 1 ");
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
	public void deleteAppReward(String ids) {
		this.execute(" DELETE PayAppReward WHERE id in (" + ids + ")", new Object[]{});
	}
}