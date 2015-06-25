package com.wyd.empire.world.dao.mysql.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.mysql.ILocalPushListDao;
import com.wyd.empire.world.entity.mysql.LocalPushList;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class LocalPushListDao extends UniversalDaoHibernate implements ILocalPushListDao {
	public LocalPushListDao() {
		super();
	}

	/**
	 * 获取本地推送列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllPush(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM " + LocalPushList.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.hasText(params[i])) {
				switch (i) {
					case 0 :
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
	 * 根据多个本地推送ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePushByIds(String ids) {
		this.execute("DELETE " + LocalPushList.class.getSimpleName() + " WHERE id in (" + ids + ") ", new Object[]{});
	}

	/**
	 * 根据区域号查询出活动奖励记录
	 * 
	 * @param areaId
	 *            区域号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LocalPushList> findAllPush() {
		return getList("FROM " + LocalPushList.class.getSimpleName() + " WHERE 1=1", new Object[]{});
	}

	/**
	 * Gm工具 根据条件查询出所有本地推送列表（提供分页） 没有参数用null替代，此key中必须有5个参数
	 * 的长度，否则会报错例如“你好|null|null|null|null”
	 * 
	 * @param content
	 *            推送内容
	 * @param title
	 *            推送标题
	 * @param stime
	 *            时间日期
	 * @param etime
	 *            时间日期
	 * @param startPushTime
	 *            推送执行时间
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findPush(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hsql.append(" FROM  " + LocalPushList.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		boolean stimeNull = true; // 开始时间为空
		boolean etimeNull = true; // 结束时间为空
		if (!params[0].equals(null) && !params[0].equals("") && !params[0].equals(" ")) {
			hsql.append(" AND (content like '" + params[0] + "%' or content like '%" + params[0] + "' or content like '%" + params[0]
					+ "%') ");
		}
		if (!params[1].equals(null) && !params[1].equals("") && !params[1].equals(" ")) {
			hsql.append(" AND title = ? ");
			values.add(params[1]);
		}
		if (!params[2].equals(null) && !params[2].equals("") && !params[2].equals(" ")) {
			stimeNull = false;
		}
		if (!params[3].equals(null) && !params[3].equals("") && !params[3].equals(" ")) {
			etimeNull = false;
		}
		try {
			if (!stimeNull && !etimeNull) {
				hsql.append(" AND startTime BETWEEN ? and ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[3]));
			} else if (!stimeNull) {
				hsql.append(" AND startTime = ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
			} else if (!etimeNull) {
				hsql.append(" AND endTime = ? ");
				values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[3]));
			}
			if (!params[4].equals(null) && !params[4].equals("") && !params[4].equals(" ")) {
				hsql.append(" AND startPushTime = ? ");
				values.add(new SimpleDateFormat("HH:mm:ss").parse(params[4]));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		hsql.append(" ORDER BY id DESC ");
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 更新版本号
	 * 
	 * @param version
	 */
	public void updateVersion(int version) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("UPDATE " + LocalPushList.class.getSimpleName() + " SET version = ? WHERE 1 = 1 ");
		values.add(version);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 通过推送Id获取推送列表
	 * 
	 * @param pushId
	 * @return
	 */
	@Override
	public LocalPushList findLocalPushById(int pushId) {
		return (LocalPushList) this.get(LocalPushList.class, pushId);
	}

}