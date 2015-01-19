package com.wyd.empire.world.server.service.base.impl;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerBill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IPlayerBillDao;
import com.wyd.empire.world.server.service.base.IPlayerBillService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class PlayerBillService extends UniversalManagerImpl implements IPlayerBillService {
	public PlayerBillService() {
		super();
	}

	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerBillDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PlayerBillService";

	/**
	 * Returns the singleton <code>FeeService</code> instance.
	 */
	public static IPlayerBillService getInstance(ApplicationContext context) {
		return (IPlayerBillService) context.getBean(SERVICE_BEAN_ID);
	}

	public void setDao(IPlayerBillDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerBillDao getDao() {
		return this.dao;
	}

	/**
	 * 验证订单是否已存在
	 * 
	 * @param oNum
	 * @return
	 */
	public boolean checkOrder(String oNum) {
		return this.dao.checkOrder(oNum);
	}

	/**
	 * 查询用户是否首充
	 * 
	 * @param player
	 * @return
	 */
	public boolean playerIsFirstCharge(Player player) {
		return this.dao.playerIsFirstCharge(player);
	}

	/**
	 * 查询用户是否当日首充了
	 * 
	 * @param player
	 * @return
	 */
	public boolean playerIsEveryDayFirstCharge(Player player) {
		return this.dao.playerIsEveryDayFirstCharge(player);
	}

	/**
	 * GM工具--查询玩家消费情况
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList getBillPageList(String key, int pageIndex, int pageSize) {
		return dao.getBillPageList(key, pageIndex, pageSize);
	}

	/**
	 * 根据玩家ID查询出玩家充值记录数--首冲使用
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getBillCount(int playerId) {
		return dao.getBillCount(playerId);
	}

	/**
	 * 获取玩家充值钻石数量
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getPlayerBillCount(int playerId) {
		return dao.getPlayerBillCount(playerId);
	}

	/**
	 * 获取玩家第一条充值记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public PlayerBill getFirstBillByPlayer(int playerId) {
		return dao.getFirstBillByPlayer(playerId);
	}

	/**
	 * 获取玩家每日首充记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param id
	 *            ID
	 * @return
	 */
	public PlayerBill getEveryDayFirstBillByPlayer(int playerId, int id) {
		return dao.getEveryDayFirstBillByPlayer(playerId, id);
	}

	/**
	 * 获得每个分区充值情况
	 * 
	 * @return
	 */
	public void getRechargeRecordByAreaId() {
		try {
			ServiceManager.getManager().getCheckRechargeService().addIntegralInfo(dao.getRechargeRecordByAreaId());
		} catch (InterruptedException e) {
			e.printStackTrace();
			if (null == e.getMessage() || !e.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(e, e);
		}
	}

	/**
	 * 获取玩家某项钻石数
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getPlayerBillCountByOrigin(int origin, int playerId) {
		return dao.getPlayerBillCountByOrigin(origin, playerId);
	}
}
