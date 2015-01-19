package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Tools;

/**
 * The service interface for the TabTool entity.
 */
public interface IToolsService extends UniversalManager {

	/**
	 * 按顺序获取所有技能道具列表
	 * 
	 * @return
	 */
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
	public List<Tools> getToolsListByIds(int[] ids);

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