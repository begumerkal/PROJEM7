package com.wyd.service.dao;


import com.wyd.db.dao.UniversalDao;
import com.wyd.service.bean.PlayerSinConsortia;

/**
 * The DAO interface for the TabPlayersinconsortia entity.
 */
public interface IPlayerSinConsortiaDao extends UniversalDao{
	
    
    
    /**
     * 根据用户ID，获取所属公会(条件必须是这个玩家已经是公会的正式会员)
     * @param playerId  玩家ID
     * @return          所属于公会ID
     */
    public PlayerSinConsortia findPlayerSinConsortiaByPlayerId(int playerId);
	
    
}