package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.Bulletin;
import com.wyd.empire.world.entity.mysql.Push;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IBulletinDao extends UniversalDao {
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
	 * 获取所有推送信息
	 * 
	 * @return
	 */
	public List<Push> getAllPushList();
}