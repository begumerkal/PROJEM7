package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.CardGroup;
import com.wyd.empire.world.entity.mysql.CardMelt;
import com.wyd.empire.world.entity.mysql.DebirsMerge;

/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerCardsDao extends UniversalDao {

	/**
	 * 按碎片品质取合成配置
	 * 
	 * @param level
	 * @return
	 */
	public List<DebirsMerge> getMegerConfigByLevel(int level);

	/**
	 * 获取熔炼碎片
	 * 
	 * @param cardId
	 * @return
	 */
	public CardMelt getCardMelt(int cardId);

	/**
	 * 获取套卡属性
	 * 
	 * @param groupId
	 * @param num
	 */
	public CardGroup getCardGroup(int groupId, int num);

}