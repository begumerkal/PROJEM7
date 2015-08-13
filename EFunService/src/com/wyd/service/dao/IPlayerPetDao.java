package com.wyd.service.dao;

import com.app.db.dao.UniversalDao;
import com.wyd.service.bean.PlayerPet;

public interface IPlayerPetDao extends UniversalDao {
	public PlayerPet getInUsePet(int playerId);
}
