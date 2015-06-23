package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.entity.mysql.PayAppReward;
import com.wyd.empire.world.model.player.WorldPlayer;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IPayAppRewardService extends UniversalManager {
	static final String SERVICE_BEAN_ID = "PayAppRewardService";

	/**
	 * 查询出所有奖励物品 sex 0男 1女。 0、1都会查出不区分性别的物品
	 * 
	 * @return
	 */
	public List<PayAppReward> findAllReward(int sex);

	/**
	 * 查询出所有奖励物品
	 * 
	 * @return
	 */
	public List<PayAppReward> findAllReward();

	/**
	 * 根据id查询出奖励物品
	 * 
	 * @return
	 */
	public PayAppReward findRewardById(int id);

	/**
	 * 判断记录是否已存 只要机器码，帐号，角色ID任一一个存在
	 */
	public boolean isExistCode(String code, String account, int playerId);

	/**
	 * 给予装备物品
	 * 
	 * @param player
	 *            玩家对象
	 * @param count
	 *            使用个数
	 * @param day
	 *            使用天数
	 * @param shopItemId
	 *            商品ID
	 * @param strongLevel
	 *            强化等级
	 */
	public void givenItems(WorldPlayer player, int count, int day, int shopItemId, int strongLevel) throws Exception;

	/**
	 * 更新所有mail
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public void updateMail(String title, String content);

	/**
	 * 获取付费包奖励列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllPayAppReward(String key, int pageIndex, int pageSize);

	/**
	 * 根据多个ID删除
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteAppReward(String ids);

}