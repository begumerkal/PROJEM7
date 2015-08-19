package com.app.empire.world.dao.mysql.gameLog.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.db.mysql.dao.impl.GenericDaoHibernate;
import com.app.empire.world.entity.mysql.gameConfig.BaseLanguage;

/**
 * The DAO class for the Mail entity.
 */
@Repository
public class BaseLanguageDaoDemo extends GenericDaoHibernate<BaseLanguage, Integer> {
	public BaseLanguageDaoDemo() {
		super(BaseLanguage.class);
	}
	public List<BaseLanguage> getAll() {
		return super.getAll();
	}

}