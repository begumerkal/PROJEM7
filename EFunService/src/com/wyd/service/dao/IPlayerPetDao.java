package com.wyd.service.dao;

import com.wyd.db.dao.UniversalDao;
import com.wyd.service.bean.PlayerPet;

public interface IPlayerPetDao extends UniversalDao {
	public PlayerPet getInUsePet(int playerId);
}
