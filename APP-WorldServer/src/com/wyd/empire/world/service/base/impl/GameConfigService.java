package com.wyd.empire.world.service.base.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wyd.db.mysql.dao.impl.GenericDaoHibernate;
import com.wyd.empire.world.dao.mysql.impl.BaseLanguageDao;

/**
 * 加载游戏配置
 */
@Service
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class GameConfigService {
	@Autowired
	private BaseLanguageDao baseLanguageDao;
	// 一般格式配置　id->map
	ConcurrentHashMap<String, Map<Integer, Map>> gameConfig = new ConcurrentHashMap<String, Map<Integer, Map>>();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 一般格式配置　id->map
	 * */
	private void loadConfig(GenericDaoHibernate<?, ?> dao) {
		List<?> rsl = baseLanguageDao.getAll();
		String key = dao.getClass().getSimpleName();
		gameConfig.remove(key);
		Map map2 = new HashMap();
		for (Object object : rsl) {
			Map map1 = (Map) JSON.toJSON(object);
			map2.put(map1.get("id"), map1);
		}
		gameConfig.put(key, map2);
	}

	public void load() {
		this.loadConfig(this.baseLanguageDao);
	}

	public ConcurrentHashMap<String, Map<Integer, Map>> getGameConfig() {
		return gameConfig;
	}

}