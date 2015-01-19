package com.wyd.empire.world.server.handler.pet;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.GetTrainTimeLeftOk;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 取训练还剩余时间
 * 
 * @author zengxc
 * 
 */
public class GetTrainTimeLeftHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetTrainTimeLeftHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		GetTrainTimeLeftOk getTrainTimeLeftOk = new GetTrainTimeLeftOk(data.getSessionId(), data.getSerial());
		playerPetService = manager.getPlayerPetService();
		try {
			PlayerPet trainPet = playerPetService.getInTrainPet(playerId);
			if (trainPet != null) {
				getTrainTimeLeftOk.setDiamondMin(Common.PET_COMPLETION_DIAMOND);
				getTrainTimeLeftOk.setTrainTimeLeft(TrainTimeLeft(trainPet.getTrainEndTime()));
				if (getTrainTimeLeftOk.getTrainTimeLeft() == 0) {
					trainPet.setTrainEndTime(null);
					playerPetService.update(trainPet);
					Map<String, String> info = new HashMap<String, String>();
					info.put("inTrain", "0");
					playerPetService.sendUpdatePet(player, trainPet.getPet().getId(), info);
				}
				session.write(getTrainTimeLeftOk);
			} else {
				getTrainTimeLeftOk.setDiamondMin(Common.PET_COMPLETION_DIAMOND);
				getTrainTimeLeftOk.setTrainTimeLeft(0);
				session.write(getTrainTimeLeftOk);
			}

		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PET_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}

	}

	private int TrainTimeLeft(Date trainEndTime) {
		Calendar cal = Calendar.getInstance();
		long nowTime = cal.getTime().getTime();
		cal.setTime(trainEndTime);
		long endTime = cal.getTime().getTime();
		if (endTime <= nowTime) {
			return 0;
		} else {
			long cha = endTime - nowTime;
			return (int) cha / 1000;
		}

	}

}
