package com.app.empire.world.dao.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.app.db.mongo.dao.impl.BaseDao;
import com.app.empire.world.entity.mongo.Mail;

/**
 * 执行与邮件表相关数据库操作
 * 
 * @author doter
 */
@Repository
public class MailDao extends BaseDao<Mail, Integer> {

	@Autowired
	public MailDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations) {
		super(factory, mongoOperations, Mail.class);
	}

	public List<Mail> getMailListByPlayerId(Integer playerId, int skip, int limit) {
		Query query = new Query();
		query.skip(skip).limit(limit);
		query.addCriteria(new Criteria("playerId").is(playerId));
		query.with(new Sort(Direction.DESC, "createTime"));
		return this.mongoTemplate.find(query, Mail.class);
	}
	public Mail getMailById(int mailId) {
		Query query = new Query();
		query.addCriteria(new Criteria("id").is(mailId));
		return this.mongoTemplate.findOne(query, Mail.class);
	}

}
