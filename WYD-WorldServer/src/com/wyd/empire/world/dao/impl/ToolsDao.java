package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.IToolsDao;
import com.wyd.empire.world.entity.mysql.Tools;

/**
 * The DAO class for the TabTool entity.
 */
public class ToolsDao extends UniversalDaoHibernate implements IToolsDao {
	private Map<Integer, Tools> toolMap = null;
	private List<Tools> toolsList = null;

	public ToolsDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		if (toolMap == null) {
			toolMap = new HashMap<Integer, Tools>();
			toolsList = new ArrayList<Tools>();
		} else {
			toolMap.clear();
			toolsList.clear();
		}
		List<Tools> toolList = getList("FROM Tools order by sort", new Object[]{});
		for (Tools tool : toolList) {
			toolsList.add(tool);
			toolMap.put(tool.getId(), tool);
		}
	}

	/**
	 * 获取所有的技能和道具
	 */
	public List<Tools> getAllTools() {
		List<Tools> toolList = new ArrayList<Tools>();
		for (Tools tool : toolsList) {
			if (tool.getType() < 2) {
				toolList.add(tool);
			}
		}
		return toolList;
	}

	/**
	 * 按顺序获取所有技能或道具
	 * 
	 * @param type
	 *            0技能,1道具
	 * @return
	 */
	public List<Tools> getToolsListByType(int type) {
		List<Tools> toolList = new ArrayList<Tools>();
		for (Tools tool : toolsList) {
			if (tool.getType().intValue() == type) {
				toolList.add(tool);
			}
		}
		return toolList;
	}

	/**
	 * 根据id获取技能道具列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<Tools> getToolsListByIds(HashSet<Integer> ids) {
		List<Tools> toolList = new ArrayList<Tools>();
		for (Tools tool : toolsList) {
			if (ids.contains(tool.getId())) {
				toolList.add(tool);
			}
		}
		return toolList;
	}

	public Tools getToolById(int id) {
		return toolMap.get(id);
	}

	/**
	 * 获取道具分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getToolsList(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Tools.class.getSimpleName() + " ORDER BY id asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum, pageSize);
	}
}