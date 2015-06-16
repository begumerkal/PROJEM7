package com.mongo.dao.impl;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.entity.SequenceIdEntity;

@Repository
public class SequenceIdDao extends SimpleMongoRepository<SequenceIdEntity, Serializable> {
	ConcurrentHashMap<String, AtomicInteger>  chm = new ConcurrentHashMap<String, AtomicInteger>();
	@Autowired
	public SequenceIdDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations) {
		super(factory.<SequenceIdEntity, Serializable> getEntityInformation(SequenceIdEntity.class), mongoOperations);
	}

	// 获取下一个id 值
	public int getNextSequenceId(String key) {
		AtomicInteger sequence = chm.get(key);
		if (sequence == null) {
			SequenceIdEntity sid = findOne(key);
			if (sid == null) {
				sequence = new AtomicInteger(0);
			} else {
				sequence = new AtomicInteger((int) sid.getSeq());
			}
			chm.put(key, sequence);
		}
		int id = sequence.incrementAndGet();
		SequenceIdEntity se = new SequenceIdEntity();
		se.setObjectId(key);
		se.setSeq(id);
		this.save(se);
		return id;
	}
}
