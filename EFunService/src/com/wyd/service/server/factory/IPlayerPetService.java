package com.wyd.service.server.factory;

import com.wyd.db.service.UniversalManager;
import com.wyd.service.bean.PlayerPet;

public interface IPlayerPetService extends UniversalManager {
	/**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
	public static final String SERVICE_BEAN_ID = "PlayerPetService";
	
	public PlayerPet getInUsePet(int playerId);

}
