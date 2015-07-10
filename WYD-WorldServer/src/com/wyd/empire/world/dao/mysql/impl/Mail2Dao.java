package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wyd.db.mysql.dao.impl.GenericDaoHibernate;
import com.wyd.empire.world.entity.mysql.Mail;

/**
 * The DAO class for the Mail entity.
 */
@Repository
public class Mail2Dao extends GenericDaoHibernate<Mail, Integer> {
	public Mail2Dao() {
		super(Mail.class);
	}
	public List<Mail> getAll() {
		return super.getAll();
	}

}