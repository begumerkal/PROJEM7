package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.IStrengthenDao;
import com.wyd.empire.world.entity.mysql.StoneRate;
import com.wyd.empire.world.entity.mysql.Successrate;
import com.wyd.empire.world.entity.mysql.WeapSkill;

public class StrengthenDao extends UniversalDaoHibernate implements IStrengthenDao {
	private Map<Integer, WeapSkill> itemMap = null;
	private List<Successrate> srList = null;

	public StrengthenDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		if (itemMap == null) {
			itemMap = new HashMap<Integer, WeapSkill>();
		} else {
			itemMap.clear();
		}
		List<WeapSkill> weapList = getList("FROM WeapSkill ", new Object[]{});
		for (WeapSkill ws : weapList) {
			itemMap.put(ws.getId(), ws);
		}
		srList = getList("FROM Successrate ORDER BY id asc", new Object[]{});
	}

	/**
	 * 根据随机数获得洗练对象
	 * 
	 * @param randomNum
	 * @return
	 */
	public WeapSkill getWeapSkillByRandom(int randomNum) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + WeapSkill.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND   startChance<=? and ? < endChance AND canGet = 1");
		values.add(randomNum);
		values.add(randomNum);
		return (WeapSkill) this.getUniqueResult(hql.toString(), values.toArray());
	}

	/**
	 * 获得1级的洗练技能
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WeapSkill> getWeapSkillOneLevel() {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + WeapSkill.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND   level = 1 AND canGet = 1");
		List<WeapSkill> list = getList(hql.toString(), values.toArray());
		return list;
	}

	/**
	 * 根据类型和数量获得相应石头的概率
	 * 
	 * @param type
	 * @param num
	 * @return
	 */
	public StoneRate getStoneRateByNumAndType(int type, int num) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + StoneRate.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND   type=? and num =? ");
		values.add(type);
		values.add(num);
		return (StoneRate) this.getUniqueResult(hql.toString(), values.toArray());
	}

	public WeapSkill getWeapSkillById(int itemId) {
		return itemMap.get(itemId);
	}

	/**
	 * 获取武器被动技能分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getWeapSkillList(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + WeapSkill.class.getSimpleName() + " ORDER BY id asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum, pageSize);
	}

	public Successrate getSuccessRateByLevel(int level) {
		int index = level - 1;
		if (index < 0 || index >= srList.size()) {
			return null;
		}
		return srList.get(index);
	}

}