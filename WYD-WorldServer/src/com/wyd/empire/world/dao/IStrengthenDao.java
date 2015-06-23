package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.StoneRate;
import com.wyd.empire.world.entity.mysql.Successrate;
import com.wyd.empire.world.entity.mysql.WeapSkill;

public interface IStrengthenDao extends UniversalDao {

	/**
	 * 根据随机数获得洗练对象
	 * 
	 * @param randomNum
	 * @return
	 */
	public WeapSkill getWeapSkillByRandom(int randomNum);

	/**
	 * 获得1级的洗练技能
	 * 
	 * @return
	 */
	public List<WeapSkill> getWeapSkillOneLevel();

	/**
	 * 根据类型和数量获得相应石头的概率
	 * 
	 * @param type
	 * @param num
	 * @return
	 */
	public StoneRate getStoneRateByNumAndType(int type, int num);

	/**
	 * 初始化数据
	 */
	public void initData();

	public WeapSkill getWeapSkillById(int itemId);

	/**
	 * 获取武器被动技能分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getWeapSkillList(int pageNum, int pageSize);

	/**
	 * 根据强化等级获取对应的强化数据
	 * 
	 * @param level
	 * @return
	 */
	public Successrate getSuccessRateByLevel(int level);
}