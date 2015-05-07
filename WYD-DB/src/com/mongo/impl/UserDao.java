package com.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.mongo.entity.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity, Integer> {
	// @Autowired
	// static MongoRepositoryFactory factory;
	// @Autowired
	// static MongoTemplate mongoOperations;

	@Autowired
	public UserDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations) {
		super(factory, mongoOperations, UserEntity.class);
		// System.out.println(mongoOperations);
	}



	// public void createCollection() {
	// if (!this.mongoTemplate.collectionExists(UserEntity.class)) {
	// this.mongoTemplate.createCollection(UserEntity.class);
	// }
	// }
	//
	// public List<UserEntity> findList(int skip, int limit) {
	// Query query = new Query();
	// query.with(new Sort(new Order(Direction.ASC, "_id")));
	// query.skip(skip).limit(limit);
	// return this.mongoTemplate.find(query, UserEntity.class);
	// }
	//
	// public List<UserEntity> findListByAge(int age) {
	// Query query = new Query();
	// query.addCriteria(new Criteria("age").is(age));
	// return this.mongoTemplate.find(query, UserEntity.class);
	// }
	//
	// public UserEntity findOne(String id) {
	// Query query = new Query();
	// query.addCriteria(new Criteria("_id").is(id));
	// return this.mongoTemplate.findOne(query, UserEntity.class);
	// }
	//
	// public UserEntity findOneByUsername(String username) {
	// Query query = new Query();
	// query.addCriteria(new Criteria("name.username").is(username));
	// return this.mongoTemplate.findOne(query, UserEntity.class);
	// }
	//
	// public void insert(UserEntity entity) {
	// this.mongoTemplate.insert(entity);
	// }
	//
	// public void update(UserEntity entity) {
	// Query query = new Query();
	// query.addCriteria(new Criteria("_id").is(entity.getId()));
	// Update update = new Update();
	// update.set("age", entity.getAge());
	// update.set("password", entity.getPassword());
	// update.set("regionName", entity.getRegionName());
	// update.set("special", entity.getSpecial());
	// update.set("works", entity.getWorks());
	// update.set("name", entity.getName());
	// this.mongoTemplate.updateFirst(query, update, UserEntity.class);
	// }

}
