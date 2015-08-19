package com.app.empire.world.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.empire.world.dao.mysql.gameLog.impl.BaseLanguageDaoDemo;

@Service
public class GameLogService {
	@Autowired
	private BaseLanguageDaoDemo demo;
	
	public void demo(){
		List<?> rsl = demo.getAll();
		for (Object object : rsl) {
			System.out.println( JSON.toJSON(object));
		}
	}

}
