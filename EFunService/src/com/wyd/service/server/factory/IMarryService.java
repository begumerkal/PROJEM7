package com.wyd.service.server.factory;

import com.wyd.db.service.UniversalManager;
import com.wyd.service.bean.MarryRecord;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IMarryService extends UniversalManager{
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "MarryService";
	/**
     * 根据playerId获得单个结婚记录
     * @param playerId
     * @param sexmark 性别标记
     * @return
     */
	public MarryRecord getSingleMarryRecordByPlayerId(int sexmark,int playerId,int status);
	
}