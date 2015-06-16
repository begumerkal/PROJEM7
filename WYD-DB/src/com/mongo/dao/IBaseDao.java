package com.mongo.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseDao<T, ID extends Serializable> extends MongoRepository<T, ID> {
 
	public List<T> findList(int skip, int limit);

	
}