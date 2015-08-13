package com.wyd.service.server.factory;
import com.app.db.service.UniversalManager;
import com.wyd.service.bean.Empireaccount;
/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IAccountService extends UniversalManager {
	/**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
	public static final String     SERVICE_BEAN_ID = "AccountService";
	public Empireaccount getEmpireaccount(String name);
	public Empireaccount getById(Integer id);

}