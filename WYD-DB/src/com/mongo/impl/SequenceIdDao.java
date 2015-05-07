package com.mongo.impl;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.entity.SequenceIdEntity;

@Repository
public class SequenceIdDao extends SimpleMongoRepository<SequenceIdEntity, Serializable> {

	AtomicInteger sequence = null;
	@Autowired
	public SequenceIdDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations) {
		super(factory.<SequenceIdEntity, Serializable> getEntityInformation(SequenceIdEntity.class), mongoOperations);
	}

	// 获取下一个id 值
	public long getNextSequenceId(String key) {

		if (this.sequence == null) {
			SequenceIdEntity sid = findOne(key);
			if (sid == null) {
				this.sequence = new AtomicInteger(0);
			} else {
				this.sequence = new AtomicInteger((int) sid.getSeq());
			}
		}
		long id = this.sequence.incrementAndGet();
		SequenceIdEntity se = new SequenceIdEntity();
		se.setObjectId(key);
		se.setSeq(id);
		this.save(se);

		return id;
	}
}
