package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.Player;
import com.wyd.empire.world.entity.mysql.PlayerBill;

/**
 * 接口 <code>FeeDAO</code>执行与Fee表相关数据库操作
 * 
 * @see com.wyd.accountserver.account.dao.UniversalDao
 * @author sunzx
 */
public interface IPlayerBillDao extends UniversalDao {
	/**
	 * 验证订单是否已存在
	 * 
	 * @param oNum
	 * @return
	 */
	public boolean checkOrder(String oNum);

	/**
	 * 查询用户是否首充
	 * 
	 * @param player
	 * @return
	 */
	public boolean playerIsFirstCharge(Player player);

	/**
	 * 查询用户是否每日首充
	 * 
	 * @param player
	 * @return
	 */
	public boolean playerIsEveryDayFirstCharge(Player player);

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
	public PageList getBillPageList(String key, int pageIndex, int pageSize);

	/**
	 * 根据玩家ID查询出玩家充值记录数--首冲使用
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getBillCount(int playerId);

	/**
	 * 获取玩家充值钻石数量
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getPlayerBillCount(int playerId);

	/**
	 * 获取玩家第一条充值记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public PlayerBill getFirstBillByPlayer(int playerId);

	/**
	 * 获取玩家每日首充记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param id
	 *            ID
	 * @return
	 */
	public PlayerBill getEveryDayFirstBillByPlayer(int playerId, int id);

	/**
	 * 获得每个分区充值情况
	 * 
	 * @return
	 */
	public List<Object> getRechargeRecordByAreaId();

	/**
	 * 获取玩家某项钻石数
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getPlayerBillCountByOrigin(int origin, int playerId);
}
