package com.wyd.empire.world.server.handler.bossmapbattle;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.RequestSynchroClients;
import com.wyd.empire.protocol.data.bossmapbattle.SendSynchroClients;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 请求同步客户端
 * 
 * @author zengxc
 */
public class RequestSynchroClientsHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestSynchroClientsHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	BossBattleTeamService bossBattleTeamService = null;

	public void handle(AbstractData data) throws Exception {
		RequestSynchroClients treasureContact = (RequestSynchroClients) data;
		int battleId = treasureContact.getBattleId();
		bossBattleTeamService = manager.getBossBattleTeamService();
		BossBattleTeam bossBattleTeam = bossBattleTeamService.getBattleTeam(battleId);
		Vector<Combat> combatList = bossBattleTeam.getCombatList();
		if (treasureContact.getPlayerOrGuai() == 0) {
			// 同步到同一队列里的其它玩家
			SendSynchroClients other = getSync(treasureContact);
			for (Combat combat : combatList) {
				combat.getPlayer().sendData(other);
			}
		}
	}

	private SendSynchroClients getSync(RequestSynchroClients sync) {
		SendSynchroClients other = new SendSynchroClients();
		other.setBattleId(sync.getBattleId());
		other.setCurrentId(sync.getCurrentId());
		other.setPlayerOrGuai(sync.getPlayerOrGuai());
		other.setParameter(sync.getParameter());
		other.setState(sync.getState());
		return other;
	}
}
