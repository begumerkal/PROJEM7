package com.wyd.service.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.service.bean.BuffRecord;
import com.wyd.service.bean.Consortia;

public interface IConsortiaDao extends UniversalDao {
	/**
     * 获得玩家的buff记录
     * @param playerId
     * @return
     */
	public List<BuffRecord> getBuffRecordByPlayerId(int playerId);
	
	/**
	 * 根据玩家Id获取公会对象
	 * @param playerId 玩家Id
	 * @return         公会对象
	 */
	public Consortia getConsortiaByPlayerId(int playerId);
}
