package com.wyd.empire.world.server.service.base.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.dao.IToolsDao;
import com.wyd.empire.world.server.service.base.IToolsService;

/**
 * The service class for the TabTool entity.
 */
public class ToolsService extends UniversalManagerImpl implements IToolsService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IToolsDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ToolService";

	public ToolsService() {
		super();
	}

	/**
	 * Returns the singleton <code>IToolService</code> instance.
	 */
	public static IToolsService getInstance(ApplicationContext context) {
		return (IToolsService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IToolsDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IToolsDao getDao() {
		return this.dao;
	}

	/**
	 * 按顺序获取所有技能道具列表
	 * 
	 * @return
	 */
	public List<Tools> getAllTools() {
		return this.dao.getAllTools();
	}

	/**
	 * 按顺序获取所有技能或道具
	 * 
	 * @param type
	 *            0技能,1道具
	 * @return
	 */
	public List<Tools> getToolsListByType(int type) {
		return this.dao.getToolsListByType(type);
	}

	/**
	 * 根据id获取技能道具列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<Tools> getToolsListByIds(int[] ids) {
		HashSet<Integer> idSet = new HashSet<Integer>();
		for (int i : ids) {
			idSet.add(i);
		}
		return this.dao.getToolsListByIds(idSet);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	@Override
	public Tools getToolById(int id) {
		return this.dao.getToolById(id);
	}

	/**
	 * 获取道具分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getToolsList(int pageNum, int pageSize) {
		return dao.getToolsList(pageNum, pageSize);
	}
}