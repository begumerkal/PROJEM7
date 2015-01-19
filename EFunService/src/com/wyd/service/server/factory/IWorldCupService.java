package com.wyd.service.server.factory;
import com.wyd.db.service.UniversalManager;
/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IWorldCupService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "WorldCupService";

	public int addPoints(String accountId, int points);
	
	public int addPoints(String accountId, String points);
	
	public int getPoints(String accountId);
	/**
	 * 不够扣时，返回-1
	 * @param accountId
	 * @param points
	 * @return
	 */
	public int usePoints(String accountId,int points);
	/**
	 * 取兑换码
	 * 取不到时，返回－1
	 * @param accountId
	 * @param goals 进球数
	 * @return
	 */
	public String exchangeCode(String accountId,int goals);

	public int addDiamond(String accountId, int diamond);
	
	public int addDiamond(String accountId, String diamond);

	public int getUseDiam(String accountId);
	
}