package com.wyd.service.server.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.Player;
import com.wyd.service.dao.IPlayerDao;
import com.wyd.service.server.factory.IPlayerService;

public class PlayerService  extends UniversalManagerImpl implements IPlayerService {
    Logger log= Logger.getLogger(PlayerService.class);
    /**
     * The dao instance injected by Spring.
     */
    private IPlayerDao dao;
    

    public PlayerService() {
        super();
    }

    

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IPlayerDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }



	@Override
	public Player getById(int playerId) {
		return dao.getById(playerId);
	}



	@Override
	public List<Player> getByAccountId(int accountId) {
		return dao.getByAccountId(accountId);
	}

	@Override
	public PageList findPageList(int areaId, int index, int size) {
		return dao.findPageList(areaId, index, size);
	}
	
	
   

	
   
	
    
    
}
