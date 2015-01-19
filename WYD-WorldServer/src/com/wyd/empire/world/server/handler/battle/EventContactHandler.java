package com.wyd.empire.world.server.handler.battle;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherEventContact;
import com.wyd.empire.protocol.data.battle.EventContact;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.MapEvent;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BattleTeamService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 触发地图事件　
 * 
 * @author zengxc
 *
 */
public class EventContactHandler implements IDataHandler {
	Logger log = Logger.getLogger(EventContactHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	BattleTeamService battleTeamService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		EventContact contact = (EventContact) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		battleTeamService = manager.getBattleTeamService();
		BattleTeam battleTeam = battleTeamService.getBattleTeam(contact.getBattleId());
		if (battleTeam == null) {
			log.info("battleTeam is null");
			return;
		}
		Combat shootPlayer = battleTeam.getCombatMap().get(contact.getPlayerId());
		if (contact.getEventId() != battleTeam.getEventId()) {
			shootPlayer.killLine(contact.getPlayerId());
			GameLogService.battleCheat(player.getId(), player.getLevel(), battleTeam.getMapId(), battleTeam.getBattleMode(), 0, 8, "");
			return;
		}
		int eventId = contact.getEventId();
		MapEvent Event = ServiceManager.getManager().getMapsService().getMapEvent(eventId);
		switch (eventId) {
			case 1 :// 龙卷风
				break;
			case 2 :// 熔岩柱
				shootPlayer.setHurtRate(shootPlayer.getHurtRate() * (1 + Event.getEffect2() / 10000f));
				break;
			case 3 :// 陨石雨
				shootPlayer.setMapEvent(Event.getEffect1(), 1);
				break;
			case 4 :// 泡泡
				shootPlayer.setAddAttackTimes(1);
				break;
		}
		OtherEventContact other = new OtherEventContact();
		other.setBattleId(contact.getBattleId());
		other.setPlayerId(contact.getPlayerId());
		other.setEventId(contact.getEventId());
		Vector<Combat> combatList = battleTeam.getCombatList();
		for (Combat combat : combatList) {
			combat.getPlayer().sendData(other);
		}
	}

}
