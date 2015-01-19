package com.wyd.empire.world.server.service.base;

import java.util.List;
import java.util.Map;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.WishItem;
import com.wyd.empire.world.player.WorldPlayer;

public interface ILotteryService extends UniversalManager {
	/**
	 * 返回许愿结果
	 * 
	 * @return
	 */
	public WishItem getWishResult();

	/**
	 * 配置表单价
	 **/
	public Map<String, Integer> getUnitPrice();

	/**
	 * 获取许愿列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<WishItem> getWishItemList();

	/**
	 * 根据id查询许愿
	 * 
	 * @param id
	 * @return
	 */
	public WishItem getById(int id);

	/**
	 * 向客户端推送许愿物品列表
	 * 
	 * @param player
	 */
	public void sendWishList(WorldPlayer player);

	/**
	 * 向客户端推祝福礼盒列表
	 * 
	 * @param player
	 */
	public void sendZflhList(WorldPlayer player);

	/**
	 * 根据祝福碎片发放礼包 <br>
	 * （同一礼包只能获得一次）</br>
	 * 
	 * @param playerId
	 * @param girdId
	 */
	public boolean playerGetGift(WorldPlayer player, int girdId);

	/**
	 * 清空祝福碎片数量和祝福礼盒领取记录（满足条件但未领取的则自动发放） <br>
	 * 供定时器每月1号调用</br>
	 */
	public void sysResetZfsp();

	/**
	 * 清空祝福碎片数量和祝福礼盒领取记录（满足条件但未领取的则自动发放）
	 * 
	 * @param playerId
	 * @param validate
	 *            true 需要检查是否跨月
	 */
	public void resetZfsp(WorldPlayer player, boolean validate);
}