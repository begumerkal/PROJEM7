package com.wyd.empire.world.dao;

import java.util.HashSet;
import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.Tools;

/**
 * The DAO interface for the TabTool entity.
 */
public interface IToolsDao extends UniversalDao {
	public List<Tools> getAllTools();

	/**
	 * 按顺序获取所有技能或道具
	 * 
	 * @param type
	 *            0技能,1道具
	 * @return
	 */
	public List<Tools> getToolsListByType(int type);

	/**
	 * 根据id获取技能道具列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<Tools> getToolsListByIds(HashSet<Integer> ids);

	/**
	 * 初始化数据
	 */
	public void initData();

	public Tools getToolById(int id);

	/**
	 * 获取道具分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getToolsList(int pageNum, int pageSize);

}