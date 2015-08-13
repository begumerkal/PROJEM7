package com.wyd.service.server.impl;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.app.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.Empireaccount;
import com.wyd.service.dao.IAccountDao;
import com.wyd.service.server.factory.IAccountService;


/**
 * 
 * The service class for the TabConsortiaright entity.
 */
public class AccountService extends UniversalManagerImpl implements IAccountService {
	Logger log = Logger.getLogger(AccountService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IAccountDao dao;

	public AccountService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IAccountService getInstance(ApplicationContext context) {
		return (IAccountService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IAccountDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	@Override
	public Empireaccount getEmpireaccount(String name) {		
		return dao.getEmpireaccount(name);
	}

	@Override
	public Empireaccount getById(Integer id) {
		return (Empireaccount) dao.get(Empireaccount.class, id);
	}
	
	
	
	
}