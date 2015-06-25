package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.MarryRecord;
import com.wyd.empire.world.entity.mysql.WeddingHall;

public interface IMarryDao extends UniversalDao {

	/**
	 * 根据playerId获得结婚记录
	 * 
	 * @param playerId
	 * @return
	 */
	public List<MarryRecord> getMarryRecordByPlayerId(int sexmark, int playerId, int status);

	/**
	 * 根据playerId获得单个结婚记录
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @return
	 */
	public MarryRecord getSingleMarryRecordByPlayerId(int sexmark, int playerId, int status);

	/**
	 * 根据婚姻的两个人获得对象
	 * 
	 * @param manId
	 * @param womanId
	 * @return
	 */
	public MarryRecord getMarryRecordByTWOPlayerId(int manId, int womanId);

	/**
	 * 删除多余的记录
	 * 
	 * @param playerId
	 * @return
	 */
	public void deleteMarryRecord(int sexmark, int playerId, int status);

	/**
	 * 删除过期的求婚记录
	 * 
	 * @return
	 */
	public void deleteMarryRecordByCreateTime();

	/**
	 * 离婚删除buff
	 * 
	 * @return
	 */
	public void deleteBuffRecord(int playerId);

	/**
	 * 判断用户是否已经结婚
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean checkPlayerMarriage(int playerId);

	/**
	 * 批量获取结婚记录
	 */
	public List<MarryRecord> getMarryRecordByPlayerIds(List<Integer> ids);

	/**
	 * 获得全区的婚礼
	 * 
	 * @return
	 */
	public List<WeddingHall> getWeddingHallByAreaId();

	/**
	 * 根据playerId获得结婚礼堂
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @return
	 */
	public WeddingHall getWeddingHallByPlayerId(int sexmark, int playerId);

	/**
	 * 删除婚礼
	 * 
	 * @param mr
	 */
	public void deleteWeddingHall(MarryRecord mr);
}