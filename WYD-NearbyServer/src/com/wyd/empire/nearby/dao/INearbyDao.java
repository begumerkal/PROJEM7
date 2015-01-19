package com.wyd.empire.nearby.dao;



import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;

/**
 * The DAO interface for the TabConsortia entity.
 */
public interface INearbyDao extends UniversalDao{
	/**
	 * 获取指定表的最大id
	 * @param clazz
	 * @return
	 */
	public int getMaxId(Class<?> clazz);
	
	/**
	 * 获取附近玩家
	 * @param lon 参考经度
	 * @param lat 参考纬度
	 * @param threshold 经纬度阈值
	 * @param maxResults 最大返回记录数
	 * @return
	 */
	public List<PlayerInfo> getNearbyPlayer(int lon, int lat, int threshold, int maxResults);
	
	/**
	 * 获取邮件发送列表
	 * @param sendId
	 * @return
	 */
	public List<Mail> getSendMailList(int sendId);
	
	/**
	 * 获取邮件接收列表
	 * @param receivedId
	 * @return
	 */
	public List<Mail> getReceivedMailList(int receivedId);
}