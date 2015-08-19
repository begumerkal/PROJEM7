package com.app.empire.world.dao.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.app.db.mongo.dao.impl.BaseDao;
import com.app.empire.world.entity.mongo.Hero;

/**
 * 执行与hero表相关数据库操作
 * 
 * @author doter
 */
@Repository
public class HeroDao extends BaseDao<Hero, Integer> {
	@Autowired
	public HeroDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations) {
		super(factory, mongoOperations, Hero.class);
	}

}
