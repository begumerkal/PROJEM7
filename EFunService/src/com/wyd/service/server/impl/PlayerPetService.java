package com.wyd.service.server.impl;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.PlayerPet;
import com.wyd.service.dao.IPlayerPetDao;
import com.wyd.service.server.factory.IPlayerPetService;

public class PlayerPetService extends UniversalManagerImpl implements IPlayerPetService {
	private IPlayerPetDao dao;	
	
	public PlayerPetService() {
		super();
	}
	public void setDao(IPlayerPetDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}
	@Override
	/**
	 * 获得玩家正在使用的宠物
	 * @param playerId
	 * @return
	 */
	public PlayerPet getInUsePet(int playerId) {
		return dao.getInUsePet(playerId);
	}

}
