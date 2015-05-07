package com.mongo.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.mongo.dao.IEntity;
import com.mongo.entity.SequenceIdEntity;

@NoRepositoryBean
public abstract class BaseDao<T extends IEntity, ID extends Serializable> extends SimpleMongoRepository<T, ID>
		implements
			com.mongo.dao.IBaseDao<T, ID> {

	@Autowired
	SequenceIdDao sequenceIdDao;

	private BaseDao(MongoEntityInformation<T, ID> metadata, MongoTemplate mongoOperations) {
		super(metadata, mongoOperations);
	}
	public BaseDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations, Class<T> clazz) {
		this(factory.<T, ID> getEntityInformation(clazz), mongoOperations);
	}
	@Override
	public <S extends T> S insert(S entity) {
		if (!entity.getClass().getName().equals(SequenceIdEntity.class.getName())) {
			long id = sequenceIdDao.getNextSequenceId(entity.getClass().getName());
			entity.setId(id);
		}
		return super.insert(entity);
	}

	public T getTestObject(ID id) {
		return null;
	}
}
