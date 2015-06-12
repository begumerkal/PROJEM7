package com.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.mongo.entity.LogEntity;

@Repository
public class LogDao extends BaseDao<LogEntity, Integer> {
	@Autowired
	public LogDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations) {
		super(factory, mongoOperations, LogEntity.class);
		// System.out.println(mongoOperations);
	}
}
