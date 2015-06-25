package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.mysql.IPictureUploadDao;
import com.wyd.empire.world.entity.mysql.PlayerPicture;

public class PictureUploadDao extends UniversalDaoHibernate implements IPictureUploadDao {

	public PictureUploadDao() {
		super();
	}

	/**
	 * 获取玩家上传头像的对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PlayerPicture getPictureUploadById(int playerId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + PlayerPicture.class.getSimpleName() + "  WHERE 1 = 1");
		hql.append(" AND playerId = ? ");
		values.add(playerId);
		List<PlayerPicture> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * GM工具--查询玩家自定义信息情况
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList getPicturePageList(String key, int pageIndex, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hql.append(" FROM  " + PlayerPicture.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						Date sDate = new Date(Long.parseLong(dates[0]) * 60 * 1000);
						Date eDate = new Date(Long.parseLong(dates[1]) * 60 * 1000);
						hql.append(" and updateTime BETWEEN ? and ?");
						values.add(sDate);
						values.add(eDate);
						break;
					case 2 :
						hql.append(" and playerId =?");
						values.add(Integer.parseInt(dates[2]));
						break;
					case 3 :
						if (("N").equals(dates[3])) {
							hql.append(" AND  (pictureUrlTest IS NOT NULL AND pictureUrlTest <> '')");
						} else {
							hql.append(" AND  (pictureUrlTest IS NULL OR pictureUrlTest = '')");
						}
						break;
					default :
						break;
				}
			}
		}
		hql.append(" ORDER BY updateTime ASC,id ASC");
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * GM工具--查询玩家自定义信息情况
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerPicture> getPictureListById(String ids) {
		return this.getList(" FROM  PlayerPicture WHERE id in (" + ids + ")", new Object[]{});
	}

}
