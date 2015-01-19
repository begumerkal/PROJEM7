package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.CardGroup;
import com.wyd.empire.world.bean.ExchangeCard;
import com.wyd.empire.world.bean.PlayerCards;

/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerCardsService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "PlayerCardsService";

	/**
	 * 获取玩家卡阵
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerCards getByPlayerId(int playerId);

	/**
	 * 根据品质取合成的卡牌
	 * 
	 * @param level
	 * @return
	 */
	public int debrisMergeCard(int level);

	/**
	 * 获取卡牌熔炼后产出的碎片数量;以后还要增加洗练等条件
	 * 
	 * @param cardId
	 * @return
	 */
	public int getMeltNum(int cardId);

	/**
	 * 获取套卡属性
	 * 
	 * @param groupId
	 * @param num
	 */
	public CardGroup getCardGroup(int groupId, int num);

	/**
	 * 套卡属性（暴击,免爆,破防,免伤,生命,攻击,防御 ）。三张｜五张。如： 25,25,20,30,0,0,0|0,0,0,0,200,40,30
	 * 
	 * @param groupId
	 * @return
	 */
	public String getGroupAddition(int groupId);

	/**
	 * 可以兑换的卡牌列表
	 * 
	 * @param playerId
	 * @return
	 */
	public List<ExchangeCard> getExchangeCard();

}