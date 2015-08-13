package com.wyd.service.dao;



import com.app.db.dao.UniversalDao;
import com.wyd.service.bean.MarryRecord;

/**
 * The DAO interface for the TabConsortia entity.
 */
public interface IMarryDao extends UniversalDao{
	
	public MarryRecord getSingleMarryRecordByPlayerId(int sexmark,int playerId,int status);
}