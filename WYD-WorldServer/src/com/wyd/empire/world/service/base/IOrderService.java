package com.wyd.empire.world.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.entity.mysql.BillingPoint;
import com.wyd.empire.world.entity.mysql.Order;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IOrderService extends UniversalManager {
	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 获取一般计费点信息
	 * 
	 * @return
	 */
	public List<BillingPoint> getPointList();

	/**
	 * 获取商城计费点信息
	 * 
	 * @return
	 */
	public List<BillingPoint> getShopList();

	public BillingPoint getBillingPointById(int bpId);

	/**
	 * 检查玩家上一条充值记录是否有回调
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean isBeforeOrderHasNotCallBack(int playerId);

	/**
	 * 根据序列号查询两天内玩家的订单信息
	 * 
	 * @param serialNum
	 * @return
	 */
	public Order getOrderBySerial(String serialNum);

	/**
	 * 根据订单号查询玩家的订单信息
	 * 
	 * @param serialNum
	 * @return
	 */
	public Order getOrderByOrderNum(String orderNum);
}