package com.wyd.service.server.impl;


import org.hibernate.Hibernate;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.PlayerSinConsortia;
import com.wyd.service.dao.IPlayerSinConsortiaDao;
import com.wyd.service.server.factory.IPlayerSinConsortiaService;

/**
 * The service class for the TabPlayersinconsortia entity.
 */
public class PlayerSinConsortiaService extends UniversalManagerImpl implements IPlayerSinConsortiaService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerSinConsortiaDao dao;
	public PlayerSinConsortiaService() {
		super();
	}
	
	public void setDao(IPlayerSinConsortiaDao dao) {
        super.setDao(dao);
        this.dao = dao;
	}
    /**
     * 根据用户ID，获取所属公会(条件必须是这个玩家已经是公会的正式会员)
     * @param playerId  玩家ID
     * @return          所属于公会ID
     */
    public PlayerSinConsortia findPlayerSinConsortiaByPlayerId(int playerId){
    	PlayerSinConsortia psc = dao.findPlayerSinConsortiaByPlayerId(playerId);
    	if(null!=psc){
    		Hibernate.initialize(psc.getPlayer());
            Hibernate.initialize(psc.getConsortia());
    	}
		return psc;
	}
}