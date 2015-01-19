package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Channel;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.bean.RechargeCrit;
import com.wyd.empire.world.server.service.base.impl.RechargeService.RechargeCritCount;

public interface IRechargeService extends UniversalManager {
	/**
	 * 初始化充值暴击数据
	 */
	public void initData();

	/**
	 * 获取当天的充值暴击数据
	 * 
	 * @param rechargeCritId
	 * @return
	 */
	public RechargeCritCount getRechargeCritCountById(int rechargeCritId);

	/**
	 * 根据商品id返回 对应的商品
	 * 
	 * @param cid
	 * @return
	 */
	public Recharge getRechargeByCID(String cid);

	/**
	 * 验证订单是否已存在
	 * 
	 * @param oNum
	 * @return
	 */
	public boolean checkOrder(String oNum);

	/**
	 * 根据钻石数查询出充值对象
	 * 
	 * @param amount
	 *            钻石数
	 * @return
	 */
	public Recharge getRechargeByAmount(int amount);

	/**
	 * 根据渠道号获取商品列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<Recharge> getRechargeListByChannel(int channelId);

	/**
	 * 根据渠道号获取订单验证id
	 * 
	 * @param channelId
	 * @return
	 */
	public String getDidByChannel(int channelId);

	/**
	 * 根据产品ID查询出商品价格对象
	 * 
	 * @param productId
	 *            产品ID
	 * @param channelId
	 *            渠道ID
	 * @return
	 */
	public Recharge findByProductId(String productId, int channelId);

	/**
	 * 通过充值钻石数量查询对应的对象
	 */
	public RechargeCrit rechargeCritByAmount(int amount);

	/**
	 * 获取充值暴击概率分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getRechargeCritList(int pageNum, int pageSize);

	/**
	 * 根据id查询Channel信息
	 * 
	 * @param channelId
	 * @return
	 */
	public Channel getChannelById(int channelId);
}