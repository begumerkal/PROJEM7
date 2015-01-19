package com.wyd.empire.world.server.handler.pet;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.Played;
import com.wyd.empire.protocol.data.pet.PlayedOk;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 宠物出战
 * 
 * @author zengxc
 */
public class PlayedHandler implements IDataHandler {
	Logger log = Logger.getLogger(PlayedHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		PlayedOk playedOk = new PlayedOk(data.getSessionId(), data.getSerial());
		int playerId = player.getId();
		Played played = (Played) data;
		int petId = played.getPetId();
		playerPetService = manager.getPlayerPetService();
		try {
			// 清空出战宠物
			PlayerPet playedPet = playerPetService.getInUsePet(playerId);
			int oldPetId = 0;
			if (playedPet != null) {
				oldPetId = playedPet.getPet().getId();
				playedPet.setInUsed(false);
				playerPetService.update(playedPet);
			}
			// 设置传过来的宠物为出战宠物
			playedPet = playerPetService.getByPlayerAndPet(playerId, petId);
			playedPet.setInUsed(true);
			playerPetService.update(playedPet);
			// 更新WorldPlayer
			player.setPlayerPet(playedPet);
			session.write(playedOk);
			GameLogService.playPet(player.getId(), player.getLevel(), petId, oldPetId);
			Map<String, String> info = new HashMap<String, String>();
			if (oldPetId > 0) {
				info.put("isPlayed", "0");
				playerPetService.sendUpdatePet(player, oldPetId, info);
			}
			info.put("isPlayed", "1");
			playerPetService.sendUpdatePet(player, playedPet.getPet().getId(), info);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}
}
