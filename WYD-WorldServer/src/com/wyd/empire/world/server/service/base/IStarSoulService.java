package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.StarSoul;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IStarSoulService extends UniversalManager {

	/**
	 * 获取所有星图列表
	 * 
	 * @return
	 */
	public List<StarSoul> getAllStarSoul();

}