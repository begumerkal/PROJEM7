package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.PayAppReward;
import com.wyd.empire.world.entity.mysql.PlayerPayAppReward;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IPayAppRewardDao extends UniversalDao {
	/**
	 * 查询出所有首冲奖励物品 sex 0男 1女。 0、1都会查出不区分性别的物品
	 * 
	 * @return
	 */
	public List<PayAppReward> findAllReward(int sex);

	/**
	 * 根据code,帐号，角色ID获得发放记录
	 * 
	 * @param code
	 * @return
	 */
	public PlayerPayAppReward getByCode(String code, String account, int playerId);

	/**
	 * 查询出所有首冲奖励物品 sex 0男 1女。 0、1都会查出不区分性别的物品
	 * 
	 * @return
	 */
	public List<PayAppReward> findAllReward();

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