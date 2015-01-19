package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.cross.CrossPlayerInfoOk;
import com.wyd.empire.protocol.data.player.GetPlayerInfoOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

public class CrossPlayerInfoOkHandler implements IDataHandler {
	private static final Logger log = Logger.getLogger(CrossPlayerInfoOkHandler.class);

	public void handle(AbstractData message) throws Exception {
		CrossPlayerInfoOk cse = (CrossPlayerInfoOk) message;
		try {
			int playerId = ServiceManager.getManager().getCrossService().getPlayerId(cse.getPlayerId());
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			if (null == player) {
				return;
			}
			GetPlayerInfoOk playerInfo = new GetPlayerInfoOk();
			playerInfo.setId(player.getId());
			playerInfo.setName(cse.getName());
			playerInfo.setSex(cse.getSex());
			playerInfo.setTitle(cse.getTitle());
			playerInfo.setGuildName(cse.getGuildName());
			playerInfo.setPosition(cse.getPosition());
			playerInfo.setLevel(cse.getLevel());
			playerInfo.setRank(cse.getRank());
			playerInfo.setVipLevel(cse.getVipLevel());
			playerInfo.setWinNum(cse.getWinNum());
			playerInfo.setPlayNum(cse.getPlayNum());
			playerInfo.setZsLevel(cse.getZsLevel());
			playerInfo.setFighting(cse.getFighting());
			playerInfo.setForce(cse.getForce());
			playerInfo.setHp(cse.getHp());
			playerInfo.setArmor(cse.getArmor());
			playerInfo.setAttack(cse.getAttack());
			playerInfo.setAgility(cse.getAgility());
			playerInfo.setDefend(cse.getDefend());
			playerInfo.setPhysique(cse.getPhysique());
			playerInfo.setCritRate(cse.getCritRate());
			playerInfo.setInjuryFree(cse.getInjuryFree());
			playerInfo.setReduceCrit(cse.getReduceCrit());
			playerInfo.setPhysical(cse.getPhysical());
			playerInfo.setWreckDefense(cse.getWreckDefense());
			playerInfo.setLuck(cse.getLuck());
			playerInfo.setAge(cse.getAge());
			playerInfo.setSignature(cse.getSignature());
			playerInfo.setConstellation(cse.getConstellation());
			playerInfo.setPictureUrl(cse.getPictureUrl());
			playerInfo.setMateName(cse.getMateName());
			playerInfo.setFriend(false);
			playerInfo.setExtranInfo(cse.getExtranInfo());
			playerInfo.setItemId(cse.getItemId());
			playerInfo.setMaintype(cse.getMaintype());
			playerInfo.setSubtype(cse.getSubtype());
			playerInfo.setPetInfo(cse.getPetInfo());
			playerInfo.setWeibo(cse.getWeibo());
			player.sendData(playerInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
		}
	}
}
