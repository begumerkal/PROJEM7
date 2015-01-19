package com.wyd.empire.world.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Guai;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IGuaiDao;

/**
 * The DAO class for the TabGuai entity.
 */
public class GuaiDao extends UniversalDaoHibernate implements IGuaiDao {
	private Map<String, GuaiPlayer> guaiMap = new HashMap<String, GuaiPlayer>();

	public GuaiDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public void initData() {
		List<Guai> guaiList = getList("FROM " + Guai.class.getSimpleName(), new Object[]{});
		Map<String, GuaiPlayer> guaiMap = new HashMap<String, GuaiPlayer>();
		for (Guai guai : guaiList) {
			guaiMap.put(guai.getDungeon() + "_" + guai.getGuaiId(), new GuaiPlayer(guai));
		}
		this.guaiMap = guaiMap;
	}

	/**
	 * GM工具查询出怪物列表
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllGuai(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Guai.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.hasText(params[i])) {
				switch (i) {
					case 0 :
						if (ServiceUtils.isNumeric(params[0])) {
							hsql.append(" AND id = ? ");
							values.add(Integer.parseInt(params[0]));
						} else {
							hsql.append(" AND (name like '" + params[0] + "%' or name like '%" + params[0] + "' or name like '%"
									+ params[0] + "%') ");
						}
						break;
					case 1 :
						hsql.append(" AND sex = ? ");
						values.add(Integer.parseInt(params[1]));
						break;
					default :
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	public GuaiPlayer getGuaiById(int difficulty, int guaiId) {
		return this.guaiMap.get(difficulty + "_" + guaiId);
	}
}