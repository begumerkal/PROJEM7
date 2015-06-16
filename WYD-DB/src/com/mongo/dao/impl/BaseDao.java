package com.mongo.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.mongo.dao.IBaseDao;
import com.mongo.entity.IEntity;
import com.mongo.entity.SequenceIdEntity;

@NoRepositoryBean
public abstract class BaseDao<T extends IEntity, ID extends Serializable> extends SimpleMongoRepository<T, ID>
		implements IBaseDao<T, ID> {

	@Autowired
	SequenceIdDao sequenceIdDao;
	protected MongoTemplate mongoTemplate;

	private BaseDao(MongoEntityInformation<T, ID> metadata, MongoTemplate mongoOperations) {
		super(metadata, mongoOperations);
	}

	public BaseDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations, Class<T> clazz) {
		this(factory.<T, ID> getEntityInformation(clazz), mongoOperations);
		this.mongoTemplate = mongoOperations;
	}

	@Override
	public <S extends T> S insert(S entity) {
		if (!entity.getClass().getName().equals(SequenceIdEntity.class.getName())) {
			int id = sequenceIdDao.getNextSequenceId(entity.getClass().getName());
			entity.setId(id);
		}
		return super.insert(entity);
	}

	
	@Override
	public List<T> findList(int skip, int limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
