package com.wyd.service.dao;


import com.app.db.mysql.dao.UniversalDao;
import com.wyd.service.bean.Empireaccount;

public interface IAccountDao extends UniversalDao {
	public Empireaccount getEmpireaccount(String name);
}
