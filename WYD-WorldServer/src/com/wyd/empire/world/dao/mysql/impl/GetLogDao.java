package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.dao.mysql.IGetLogDao;
import com.wyd.empire.world.entity.mysql.GetItem;
import com.wyd.empire.world.entity.mysql.GoldCount;
import com.wyd.empire.world.entity.mysql.StrongeRecord;

public class GetLogDao extends UniversalDaoHibernate implements IGetLogDao {

	/**
	 * 获取发放物品列表
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageList getItemLogList(String key, int pageIndex, int pageSize) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + GetItem.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						Date sDate = new Date(Long.parseLong(dates[0]) * 60 * 1000);
						Date eDate = new Date(Long.parseLong(dates[1]) * 60 * 1000);
						hql.append(" and createtime BETWEEN ? and ?");
						values.add(sDate);
						values.add(eDate);
						break;
					case 2 :
						hql.append(" and playerId=?");
						values.add(Integer.parseInt(dates[2]));
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		hql.append(" order by createtime desc");
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	@Override
	public PageList getGoldCountLogList(String key, int pageIndex, int pageSize) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + GoldCount.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						Date sDate = new Date(Long.parseLong(dates[0]) * 60 * 1000);
						Date eDate = new Date(Long.parseLong(dates[1]) * 60 * 1000);
						hql.append(" and createTime BETWEEN ? and ?");
						values.add(sDate);
						values.add(eDate);
						break;
					case 2 :
						hql.append(" and playerId=?");
						values.add(Integer.parseInt(dates[2]));
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		hql.append(" order by createTime desc");
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	@Override
	public PageList getStrongrecordLogList(String key, int pageIndex, int pageSize) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + StrongeRecord.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						Date sDate = new Date(Long.parseLong(dates[0]) * 60 * 1000);
						Date eDate = new Date(Long.parseLong(dates[1]) * 60 * 1000);
						hql.append(" and createTime BETWEEN ? and ?");
						values.add(sDate);
						values.add(eDate);
						break;
					case 2 :
						hql.append(" and playerId=?");
						values.add(Integer.parseInt(dates[2]));
						break;
					case 3 :
						hql.append(" and type=?");
						values.add(Integer.parseInt(dates[3]));
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		hql.append(" order by createTime desc");
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

}
