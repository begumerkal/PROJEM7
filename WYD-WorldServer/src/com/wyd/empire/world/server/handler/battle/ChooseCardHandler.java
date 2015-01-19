package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.ChooseCard;
import com.wyd.empire.protocol.data.battle.OhterChooseCard;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 选择卡牌
 * 
 * @author Administrator
 */
public class ChooseCardHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChooseCardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ChooseCard chooseCard = (ChooseCard) data;
		try {
			int battleId = chooseCard.getBattleId();
			int playerId = chooseCard.getPlayerId();
			int cardPos = chooseCard.getCardPos();

			OhterChooseCard ohterChooseCard = new OhterChooseCard();
			ohterChooseCard.setBattleId(battleId);
			ohterChooseCard.setCurrentPlayerId(playerId);
			ohterChooseCard.setCardPos(cardPos);

			BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);

			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
					ohterChooseCard.setPlayerId(combat.getId());
					combat.getPlayer().sendData(ohterChooseCard);
				}
			}

		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
