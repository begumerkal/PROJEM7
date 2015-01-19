package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.CardExchange;
import com.wyd.empire.world.bean.CardGroup;
import com.wyd.empire.world.bean.CardMelt;
import com.wyd.empire.world.bean.DebirsMerge;
import com.wyd.empire.world.bean.ExchangeCard;
import com.wyd.empire.world.bean.PlayerCards;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IPlayerCardsDao;
import com.wyd.empire.world.server.service.base.IPlayerCardsService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabConsortiaright entity.
 */
public class PlayerCardsService extends UniversalManagerImpl implements IPlayerCardsService {
	Logger log = Logger.getLogger(PlayerCardsService.class);

	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerCardsDao dao;

	public PlayerCardsService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IPlayerCardsService getInstance(ApplicationContext context) {
		return (IPlayerCardsService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerCardsDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerCardsDao getDao() {
		return this.dao;
	}

	@Override
	public PlayerCards getByPlayerId(int playerId) {
		PlayerCards card = new PlayerCards();
		List<PlayerItemsFromShop> playerCards = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerCardList(playerId);
		for (PlayerItemsFromShop playerCard : playerCards) {
			if (!playerCard.getIsInUsed())
				continue;
			switch (playerCard.getShopItem().getSubtype()) {
				case 1 :
					card.setJk(playerCard);
					break;
				case 2 :
					card.setMk(playerCard);
					break;
				case 3 :
					card.setSk(playerCard);
					break;
				case 4 :
					card.setHk(playerCard);
					break;
				case 5 :
					card.setTk(playerCard);
					break;
			}
		}
		return card;

	}

	@Override
	public int debrisMergeCard(int level) {
		List<DebirsMerge> mergeConfig = dao.getMegerConfigByLevel(level);
		int totalPrecent = 0;
		for (DebirsMerge config : mergeConfig) {
			totalPrecent += config.getRate();
		}
		int random = ServiceUtils.getRandomNum(1, totalPrecent + 1);
		int precent = 0;
		for (DebirsMerge config : mergeConfig) {
			precent += config.getRate();
			if (precent >= random) {
				return config.getShopItemId();
			}
		}
		return 0;
	}

	@Override
	public int getMeltNum(int cardId) {
		CardMelt melt = dao.getCardMelt(cardId);
		return melt == null ? 0 : melt.getMeltNum();
	}

	@Override
	public CardGroup getCardGroup(int groupId, int num) {
		return dao.getCardGroup(groupId, num);
	}

	@Override
	public String getGroupAddition(int groupId) {
		CardGroup g3 = dao.getCardGroup(groupId, 3);
		CardGroup g5 = dao.getCardGroup(groupId, 5);
		String str = g3.getAddCritical() + "," + g3.getAddReduceCrit() + "," + g3.getAddWreckDefense() + "," + g3.getAddInjuryFree() + ","
				+ g3.getAddHp() + "," + g3.getAddAttack() + "," + g3.getAddDefend() + "|";
		str += g5.getAddCritical() + "," + g5.getAddReduceCrit() + "," + g5.getAddWreckDefense() + "," + g5.getAddInjuryFree() + ","
				+ g5.getAddHp() + "," + g5.getAddAttack() + "," + g5.getAddDefend();
		return str;
	}

	@Override
	public List<ExchangeCard> getExchangeCard() {
		@SuppressWarnings("unchecked")
		List<CardExchange> allCards = dao.getAll(CardExchange.class);
		int totalPrecent = 0;
		for (CardExchange cardex : allCards) {
			totalPrecent += cardex.getShowRate();
		}
		List<ExchangeCard> excardList = new ArrayList<ExchangeCard>();
		while (excardList.size() < 5) {
			int random = ServiceUtils.getRandomNum(1, totalPrecent + 1);
			int precent = 0;
			for (CardExchange cardex : allCards) {
				precent += cardex.getShowRate();
				if (precent >= random) {
					allCards.remove(cardex);
					totalPrecent -= cardex.getShowRate();
					excardList.add(new ExchangeCard(cardex.getShopItemId(), cardex.getPrice(), cardex.getNumber()));
					break;
				}
			}
		}
		return excardList;
	}

}