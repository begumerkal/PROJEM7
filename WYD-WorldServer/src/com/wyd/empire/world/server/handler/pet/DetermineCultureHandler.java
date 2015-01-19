package com.wyd.empire.world.server.handler.pet;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.DetermineCulture;
import com.wyd.empire.world.bean.PetCulture;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 确定培养 把之前得到的培养值保存起来
 * 
 * @author zengxc
 *
 */
public class DetermineCultureHandler implements IDataHandler {
	Logger log = Logger.getLogger(DetermineCultureHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		DetermineCulture confirmCulture = (DetermineCulture) data;
		int petId = confirmCulture.getPetId();
		playerPetService = manager.getPlayerPetService();
		try {
			// 到WorldPlayer取之前保存下的培养方案
			int[] cultureValus = player.getPetCultureValue();
			if (cultureValus == null) {
				// 找不到培养方案
				throw new ProtocolException(TipMessages.PETNOTCULTUREOBJECT, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int addHp = cultureValus[0];
			int addAtk = cultureValus[1];
			int addDef = cultureValus[2];
			// 清空方案，防止被重复刷
			player.setPetCultureValue(null);
			// 更新宠物属性
			PlayerPet playerPet = playerPetService.getByPlayerAndPet(playerId, petId);
			PetCulture petCulture = playerPetService.getCultureByLevel(playerPet.getLevel());
			float petHP = playerPet.getCulHP();
			float petAttack = playerPet.getCulAttack();
			float petDefend = playerPet.getCulDefend();
			int petHPNum = playerPet.getCulHPNum();
			int petAttackNum = playerPet.getCulAttackNum();
			int petDefendNum = playerPet.getCulDefendNum();
			playerPet.setCulHPNum(petHPNum + addHp);
			playerPet.setCulAttackNum(petAttackNum + addAtk);
			playerPet.setCulDefendNum(petDefendNum + addDef);

			petHP += addHp * petCulture.getHpCoef();
			petAttack += addAtk * petCulture.getAttackCoef();
			petDefend += addDef * petCulture.getDefendCoef();
			playerPet.setCulHP(petHP);
			playerPet.setCulAttack(petAttack);
			playerPet.setCulDefend(petDefend);
			playerPetService.update(playerPet);
			// 结束后，推送数据
			Map<String, String> info = new HashMap<String, String>();
			info.put("culHp", playerPet.getCulHPNum() + "");
			info.put("culAttack", playerPet.getCulAttackNum() + "");
			info.put("culDefend", playerPet.getCulDefendNum() + "");
			info.put("culDefend", playerPet.getCulDefendNum() + "");
			info.put("hasHp", playerPet.getHP() + "");
			info.put("hasAttack", playerPet.getAttack() + "");
			info.put("hasDefend", playerPet.getDefend() + "");
			playerPetService.sendUpdatePet(player, playerPet.getPet().getId(), info);

			// 更新战力
			player.setPlayerPet(playerPet);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

}
