package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.LocalPushList;
import com.wyd.empire.world.server.service.base.ILocalPushListService;

public class LocalPushListService {
	private static int version = 1;
	private static List<LocalPushList> pushList = new ArrayList<LocalPushList>();
	private ILocalPushListService localPushListService;

	public LocalPushListService(ILocalPushListService localPushListService) {
		this.localPushListService = localPushListService;
	}

	public ILocalPushListService getLocalPushListService() {
		return localPushListService;
	}

	private static String jsonPushList = "";

	public void initPushData() {
		pushList = localPushListService.findAllPush();
		if (pushList.size() > 0) {
			version = pushList.get(0).getVersion();
			JSONArray jsonArray = JSONArray.fromObject(pushList);
			jsonPushList = "{\"pushList\":[" + jsonArray.toString().substring(1, jsonArray.toString().length() - 1) + "]}";
			// System.out.println(jsonPushList);
		}
	}

	/**
	 * 获取当前版本号
	 * 
	 * @return
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 手动设置版本号
	 * 
	 * @param version
	 */
	public static void setVersion(int version) {
		LocalPushListService.version = version;
	}

	/**
	 * 获取本地推送列表
	 * 
	 * @return
	 */
	public List<LocalPushList> getPushList() {
		return localPushListService.findAllPush();
	}

	public void setLocalPushListService(ILocalPushListService localPushListService) {
		this.localPushListService = localPushListService;
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
		return localPushListService.findAllPush(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个本地推送ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePushByIds(String ids) {
		localPushListService.deletePushByIds(ids);
	}

	/**
	 * 根据单个本地推送ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deletePushByIds(int id) {
		localPushListService.remove(LocalPushList.class, id);
	}

	/**
	 * 根据区域号查询出本地推送记录
	 * 
	 * @return
	 */
	public List<LocalPushList> findAllPush() {
		return localPushListService.findAllPush();
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
		return localPushListService.findPush(key, pageIndex, pageSize);
	}

	/**
	 * 更新版本号加1
	 * 
	 * @param newVersion
	 *            (参数值小于等于0 版本号默认更新一次+1,参数值大于0,版本号则更新为newVersion)
	 */
	public void updateVersion(int newVersion) {
		if (newVersion < 0) {
			version++;
		} else {
			version = newVersion;
		}

		localPushListService.updateVersion(version);
	}

	/**
	 * 添加推送里列表
	 * 
	 * @param localPushList
	 */
	public void saveLocalPush(LocalPushList localPushList) {
		localPushListService.saveLocalPush(localPushList);
	}

	/**
	 * 通过id获取推送
	 * 
	 * @param pushId
	 * @return
	 */
	public LocalPushList findLocalPushById(int pushId) {
		return localPushListService.findLocalPushById(pushId);

	}

	/**
	 * 更新本地推送信息
	 * 
	 * @param pushId
	 * @return
	 * @return
	 */
	public void update(LocalPushList localPushList) {
		// System.out.println("update");
		localPushListService.update(localPushList);
	}

	/**
	 * 获取本地推送列表json字符串
	 * 
	 * @return
	 */
	public String getJsonPushList() {
		return jsonPushList;
	}

}
