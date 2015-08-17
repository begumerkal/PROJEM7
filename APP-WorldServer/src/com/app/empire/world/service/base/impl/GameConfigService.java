package com.app.empire.world.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.app.db.mysql.dao.impl.UniversalDaoHibernate;
import com.app.empire.world.dao.mysql.impl.BaseLanguageDao;
import com.app.empire.world.dao.mysql.impl.BaseLanguageDaoDemo;
import com.app.empire.world.entity.mysql.BaseLanguage;

/**
 * 加载游戏配置
 */

@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class GameConfigService {
	private BaseLanguageDao baseLanguageDao;
	@Autowired
	private BaseLanguageDaoDemo demo;
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
	}

	public void load() {
		this.loadConfig(baseLanguageDao, BaseLanguage.class);
		
		List<?> rsl = demo.getAll();
		for (Object object : rsl) {
			System.out.println( JSON.toJSON(object));
		}
	}

	public ConcurrentHashMap<String, Map<Integer, Map>> getGameConfig() {
		return this.gameConfig;
	}
	public void setBaseLanguageDao(BaseLanguageDao baseLanguageDao) {
		this.baseLanguageDao = baseLanguageDao;
	}
}