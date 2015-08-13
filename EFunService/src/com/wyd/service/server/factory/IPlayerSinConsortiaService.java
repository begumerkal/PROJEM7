package com.wyd.service.server.factory;


import com.app.db.service.UniversalManager;
import com.wyd.service.bean.PlayerSinConsortia;

/**
 * The service interface for the TabPlayersinconsortia entity.
 */
public interface IPlayerSinConsortiaService extends UniversalManager{
	
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	public static final String SERVICE_BEAN_ID = "PlayerSinConsortiaService";
    /**
     * 根据用户ID，获取所属公会(条件必须是这个玩家已经是公会的正式会员)
     * @param playerId  玩家ID
     * @return          所属于公会ID
     */
    public PlayerSinConsortia findPlayerSinConsortiaByPlayerId(int playerId);
	
    
}