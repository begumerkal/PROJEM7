package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.LocalPushList;
import com.wyd.empire.world.dao.ILocalPushListDao;
import com.wyd.empire.world.server.service.base.ILocalPushListService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class LocalPushListService extends UniversalManagerImpl implements ILocalPushListService {
	Logger log = Logger.getLogger(LocalPushListService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private ILocalPushListDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "LocalPushListService";

	public LocalPushListService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static ILocalPushListService getInstance(ApplicationContext context) {
		return (ILocalPushListService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(ILocalPushListDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public ILocalPushListDao getDao() {
		return this.dao;
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
		return dao.findAllPush(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个本地推送ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePushByIds(String ids) {
		dao.deletePushByIds(ids);
	}

	/**
	 * 根据区域号查询出本地推送记录
	 * 
	 * @param areaId
	 *            区域号
	 * @return
	 */
	public List<LocalPushList> findAllPush() {
		return dao.findAllPush();
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
		return dao.findPush(key, pageIndex, pageSize);
	}

	/**
	 * 更新版本号
	 * 
	 * @param version
	 */
	public void updateVersion(int version) {
		dao.updateVersion(version);
	}

	/**
	 * 通过id获取推送
	 * 
	 * @param pushId
	 * @return
	 */
	@Override
	public LocalPushList findLocalPushById(int pushId) {
		return dao.findLocalPushById(pushId);
	}

	/**
	 * 添加推送里列表
	 * 
	 * @param localPushList
	 */
	@Override
	public void saveLocalPush(LocalPushList localPushList) {
		// dao.saveLocalPush(localPushList);
		dao.save(localPushList);
	}
}