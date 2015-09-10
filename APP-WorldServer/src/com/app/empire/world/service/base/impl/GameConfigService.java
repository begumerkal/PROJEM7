package com.app.empire.world.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.db.mysql.dao.impl.UniversalDaoHibernate;
import com.app.empire.world.dao.mysql.gameConfig.impl.BaseLanguageDao;
import com.app.empire.world.dao.mysql.gameLog.impl.LogAccountLoginDao;
import com.app.empire.world.entity.mysql.gameConfig.BaseLanguage;

/**
 * 加载游戏配置
 */

@SuppressWarnings(value = {"rawtypes", "unchecked"})
@Service
public class GameConfigService {
	@Autowired
	private BaseLanguageDao baseLanguageDao;
	// 一般格式配置　id->map
	ConcurrentHashMap<String, Map<Integer, Map>> gameConfig = new ConcurrentHashMap<String, Map<Integer, Map>>();

	/**
	 * 一般格式配置　id->map
	 * */
	private void loadConfig(UniversalDaoHibernate dao, Class clazz) {
		List<?> rsl = dao.getAll(clazz);
		String key = clazz.getSimpleName();
		this.gameConfig.remove(key);
		Map map2 = new HashMap();
		for (Object object : rsl) {
			Map map1 = (Map) JSON.toJSON(object);
			map2.put(map1.get("id"), map1);
		}
		this.gameConfig.put(key, map2);
		System.out.println(this.gameConfig);
	}
	
	

	public void load() {
		this.loadConfig(baseLanguageDao, BaseLanguage.class);
	}
	public ConcurrentHashMap<String, Map<Integer, Map>> getGameConfig() {
		return this.gameConfig;
	}

}