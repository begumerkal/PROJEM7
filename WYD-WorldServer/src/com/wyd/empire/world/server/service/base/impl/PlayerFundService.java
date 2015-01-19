package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.FundRecord;
import com.wyd.empire.world.bean.PlayerFund;
import com.wyd.empire.world.dao.IPlayerFundDao;
import com.wyd.empire.world.server.service.base.IPlayerFundService;

/**
 * The service class for the TabPlayerFund entity.
 */
public class PlayerFundService extends UniversalManagerImpl implements IPlayerFundService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerFundDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PlayerFundService";

	public PlayerFundService() {
		super();
	}

	/**
	 * Returns the singleton <code>IPlayerFundService</code> instance.
	 */
	public static IPlayerFundService getInstance(ApplicationContext context) {
		return (IPlayerFundService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerFundDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerFundDao getDao() {
		return this.dao;
	}

	/**
	 * 根据玩家ID获取玩家基金购买信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家基金购买信息
	 */
	public PlayerFund getPlayerFund(int playerId) {
		return dao.getPlayerFund(playerId);
	}

	/**
	 * 删除玩家的基金购买记录
	 * 
	 * @param playerId
	 */
	public void deletePlayerFundByPlayer(int playerId) {
		dao.deletePlayerFundByPlayer(playerId);
	}

	/**
	 * 根据用户信息获取用户基金领取记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 用户信息获取用户基金领取记录
	 */
	public List<FundRecord> getFundRecord(int playerId) {
		return dao.getFundRecord(playerId);
	}

	/**
	 * 删除玩家的基金领取记录
	 * 
	 * @param playerId
	 */
	public void deleteFundRecordByPlayer(int playerId) {
		dao.deleteFundRecordByPlayer(playerId);
	}
}
