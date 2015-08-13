package com.wyd.service.server.factory;
import java.util.List;

import com.app.db.service.UniversalManager;
import com.wyd.service.bean.BuffRecord;
import com.wyd.service.bean.Consortia;
/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IConsortiaService extends UniversalManager {
	/**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
	public static final String     SERVICE_BEAN_ID = "ConsortiaService";
	/**
     * 获得玩家的buff记录
     * 
     * @param playerId
     * @return
     */
    public List<BuffRecord> getBuffRecordByPlayerId(int playerId);
    
    public Consortia getConsortiaByPlayerId(int playerId);

}