package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.entity.mysql.LocalPushList;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface ILocalPushListDao extends UniversalDao {
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
	public PageList findAllPush(String key, int pageIndex, int pageSize);

	/**
	 * 根据多个本地推送ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePushByIds(String ids);

	/**
	 * 根据区域号查询出本地推送记录
	 * 
	 * @return
	 */
	public List<LocalPushList> findAllPush();

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
	public PageList findPush(String key, int pageIndex, int pageSize);

	/**
	 * 更新版本号
	 * 
	 * @param version
	 */
	public void updateVersion(int version);

	/**
	 * 通过推送Id获取推送列表
	 * 
	 * @param pushId
	 * @return
	 */
	public LocalPushList findLocalPushById(int pushId);

}