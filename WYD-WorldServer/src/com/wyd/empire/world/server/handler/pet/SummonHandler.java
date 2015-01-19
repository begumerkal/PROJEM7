package com.wyd.empire.world.server.handler.pet;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.Summon;
import com.wyd.empire.protocol.data.pet.SummonOk;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 宠物驯服
 * 
 * @author Administrator
 */
public class SummonHandler implements IDataHandler {
	Logger log = Logger.getLogger(SummonHandler.class);

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Summon petSummon = (Summon) data;
		SummonOk petSummonOk = new SummonOk(data.getSessionId(), data.getSerial());
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			int petId = petSummon.getPetId();
			int useDiamond = 0, useGold = 0, useBadge = 0;
			Map<Integer, PlayerPet> ppsMap = ServiceManager.getManager().getPetItemService().getPlayerPetMap(player.getId());
			if (null != ppsMap.get(petId)) {// 校验是否被召唤
				throw new ProtocolException(ErrorMessages.HAS_BEEN_CALLED, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			PetItem pi = (PetItem) ServiceManager.getManager().getPetItemService().get(PetItem.class, petId);
			if (player.getLevel() < pi.getCallLevel()) {
				throw new ProtocolException(ErrorMessages.PET_CALLLEVL_LOW, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int petNum = ServiceManager.getManager().getPlayerPetService().openBarNum(player.getId()); // 玩家携带宠物上限
			if (!(petNum > ppsMap.size())) {
				throw new ProtocolException(ErrorMessages.PET_TOO_MANY, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int needMoney = pi.getMoney();
			if (needMoney > player.getMoney()) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDGOLD_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -needMoney, "宠物驯服", petId + "");
			useGold = needMoney;
			// 如果没有出战的宠物则设置当前宠物为出战
			PlayerPet inPlayed = ServiceManager.getManager().getPlayerPetService().getInUsePet(player.getId());
			boolean isUse = (null == inPlayed || inPlayed.getId() == null);
			PlayerPet playerPet = ServiceManager.getManager().getPlayerPetService().playerGetPet(player.getId(), petId, isUse);
			if (isUse) {
				player.setPlayerPet(playerPet);
			}
			petSummonOk.setName(pi.getName());
			session.write(petSummonOk);
			ServiceManager.getManager().getTaskService().petTame(player, petId);
			GameLogService.addPet(player.getId(), player.getLevel(), petId, 1, useDiamond, useGold, useBadge);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
