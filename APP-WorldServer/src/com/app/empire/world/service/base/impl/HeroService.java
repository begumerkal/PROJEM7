package com.app.empire.world.service.base.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.empire.world.dao.mongo.impl.HeroDao;
import com.app.empire.world.entity.mongo.Hero;

/**
 * HeroService 处理与英雄相关操作业务处理逻辑层
 * 
 * @since JDK 1.6
 */

@Service
public class HeroService {
	private Logger log = Logger.getLogger(HeroService.class);

	@Autowired
	private HeroDao heroDao;
	/**
	 * 获得一个新英雄
	 */
	public void addHero(int hero_ext_id) {
		Hero newHero = new Hero();

		newHero = this.heroDao.insert(newHero);
	}

}
