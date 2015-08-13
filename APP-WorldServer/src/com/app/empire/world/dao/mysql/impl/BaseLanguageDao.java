package com.app.empire.world.dao.mysql.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.db.mysql.dao.impl.GenericDaoHibernate;
import com.app.empire.world.entity.mysql.BaseLanguage;

/**
 * The DAO class for the Mail entity.
 */
@Repository
public class BaseLanguageDao extends GenericDaoHibernate<BaseLanguage, Integer> {
	public BaseLanguageDao() {
		super(BaseLanguage.class);
	}
	public List<BaseLanguage> getAll() {
		return super.getAll();
	}

}