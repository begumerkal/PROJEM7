package com.wyd.empire.world.dao.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.wyd.db.mongo.dao.impl.BaseDao;
import com.wyd.empire.world.entity.mongo.Player;

/**
 * 执行与Player表相关数据库操作
 * 
 * @author doter
 */

@Repository
public class PlayerDao extends BaseDao<Player, Integer> {

	@Autowired
	public PlayerDao(MongoRepositoryFactory factory, MongoTemplate mongoOperations) {
		super(factory, mongoOperations, Player.class);
	}
	
	
	/** 根据账号id获取角色列表 */
	public List<Player> getPlayerListByAccountId(Integer accountId) {
		Query query = new Query();
		query.addCriteria(new Criteria("accountId").is(accountId));
		return this.mongoTemplate.find(query, Player.class);
	}
	/** 根据角色id获取角色 */
	public Player getPlayerById(Integer playerId) {
		Query query = new Query();
		query.addCriteria(new Criteria("id").is(playerId));
		return this.mongoTemplate.findOne(query, Player.class);
	}
	/** 根据角色名获取角色 */
	public Player getPlayerByName(Integer accountId, String nickname) {
		Query query = new Query();
		query.addCriteria(new Criteria("nickname").is(nickname));
		query.addCriteria(new Criteria("accountId").is(accountId));
		return this.mongoTemplate.findOne(query, Player.class);
	}
	/** 根据角色名获取角色 */
	public Player getPlayerByName(String nickname) {
		Query query = new Query();
		query.addCriteria(new Criteria("nickname").is(nickname));
		return this.mongoTemplate.findOne(query, Player.class);
	}
}
