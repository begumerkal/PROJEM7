package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.GetEventInfo;
import com.wyd.empire.protocol.data.battle.GetEventInfoOk;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.MapEvent;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取特殊事件
 * 
 * @author zengxc
 *
 */
public class GetEventInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetEventInfoHandler.class);

	@Override
	public void handle(AbstractData data) throws Exception {
		GetEventInfo info = (GetEventInfo) data;
		int battleId = info.getBattleId();
		BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
		if (null == battleTeam) {
			return;
		}
		int eventId = battleTeam.getEventId();
		String name = "";
		int effect1 = -1, effect2 = -1;
		if (eventId > 0) {
			MapEvent Event = ServiceManager.getManager().getMapsService().getMapEvent(eventId);
			name = Event.getName();
			effect1 = Event.getEffect1();
			effect2 = Event.getEffect2();
		}
		GetEventInfoOk ok = new GetEventInfoOk();
		ok.setBattleId(battleId);
		ok.setEventId(eventId);
		ok.setName(name);
		ok.setEffect1(effect1);
		ok.setEffect2(effect2);
		for (Combat combat : battleTeam.getCombatList()) {
			WorldPlayer player = combat.getPlayer();
			if (null != player && !combat.isRobot()) {
				player.sendData(ok);
			}
		}
	}

}
