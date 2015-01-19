package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.bean.Push;

/**
 * The service interface for the TabGuai entity.
 */
public interface IBulletinService extends UniversalManager {
	/**
	 * 获取本服公告
	 * 
	 * @return
	 */
	public List<Bulletin> getBulletinList();

	/**
	 * 获取本服公告
	 * 
	 * @return
	 */
	public List<Bulletin> getAllBulletinList();

	/**
	 * 根据多个ID值删除公告
	 * 
	 * @param ids
	 *            多个公告ID，中间以,分割
	 */
	public void deleteBulletin(String ids);

	/**
	 * 增加一条push规则
	 * 
	 * @param pushId
	 * @param lostTime
	 * @param message
	 * @param isRepeat
	 */
	public void addPush(int lostTime, String message, int isRepeat);

	/**
	 * 移除一条push规则
	 * 
	 * @param pushId
	 */
	public void removePush(int pushId);

	/**
	 * 更新一条push规则
	 * 
	 * @param push
	 */
	public void updatePush(Push push);

	/**
	 * 获取Push规则列表
	 * 
	 * @return
	 */
	public List<Push> getAllPush();

	/**
	 * 查询push规则
	 * 
	 * @param pushId
	 * @return
	 */
	public Push getPush(String pushId);
}