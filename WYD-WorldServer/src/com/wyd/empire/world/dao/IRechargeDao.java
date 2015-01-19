package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Channel;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.bean.RechargeCrit;

public interface IRechargeDao extends UniversalDao {
	/**
	 * 根据产品id查询产品
	 * 
	 * @param cid
	 * @return
	 */
	public Recharge getRechargeByCID(String cid);

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
	 * 
	 * 充值暴击值
	 */
	public RechargeCrit findByAmount(int amount);

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