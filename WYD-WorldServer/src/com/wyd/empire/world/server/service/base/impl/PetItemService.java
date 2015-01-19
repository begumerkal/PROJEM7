package com.wyd.empire.world.server.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.dao.IPetItemDao;
import com.wyd.empire.world.server.service.base.IPetItemService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class PetItemService extends UniversalManagerImpl implements IPetItemService {
	Logger log = Logger.getLogger(PetItemService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IPetItemDao dao;

	public PetItemService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IPetItemService getInstance(ApplicationContext context) {
		return (IPetItemService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPetItemDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPetItemDao getDao() {
		return this.dao;
	}

	@Override
	public PetItem getById(int petId) {

		return null;
	}

	/**
	 * 获得玩家宠物列表
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerPet> getPlayerPetList(int playerId) {
		return dao.getPlayerPetList(playerId);
	}

	/**
	 * 获得玩家宠物MAP
	 * 
	 * @param playerId
	 * @return
	 */
	public Map<Integer, PlayerPet> getPlayerPetMap(int playerId) {
		List<PlayerPet> list = getPlayerPetList(playerId);
		Map<Integer, PlayerPet> map = new HashMap<Integer, PlayerPet>();
		for (PlayerPet pp : list) {
			map.put(pp.getPet().getId(), pp);
		}
		return map;
	}

	/**
	 * 获得所有可召唤宠物列表
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PetItem> getAllPetList() {
		return dao.getAllPetList();
	}

	/**
	 * 根据ID获取宠物列表
	 */
	public List<PetItem> getPetList(String ids) {
		return dao.getPetList(ids);
	}

	/**
	 * 获得玩家宠物数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getPlayerPetNum(int playerId) {
		return getPlayerPetList(playerId).size();
	}
}