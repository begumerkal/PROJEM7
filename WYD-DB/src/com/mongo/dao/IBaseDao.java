package com.mongo.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseDao<T, ID extends Serializable> extends MongoRepository<T, ID> {
	public T getTestObject(ID id);
}