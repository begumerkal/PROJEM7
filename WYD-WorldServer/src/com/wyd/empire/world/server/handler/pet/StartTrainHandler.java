package com.wyd.empire.world.server.handler.pet;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.StartTrain;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.PetRecord;
import com.wyd.empire.world.bean.PetTrain;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始训练
 * 
 * @author zengxc
 */
public class StartTrainHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartTrainHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		StartTrain startTrain = (StartTrain) data;
		int trainPetId = startTrain.getPetId();
		playerPetService = manager.getPlayerPetService();
		try {

			PlayerPet trainPet = playerPetService.getInTrainPet(playerId);
			if (trainPet != null) {
				// 同时只能训练一个宠物
				throw new ProtocolException(data, TipMessages.TOWPETTRAIN);
			} else {
				PlayerPet playerPet = playerPetService.getByPlayerAndPet(playerId, trainPetId);
				// 该宠物不存在
				if (playerPet == null)
					throw new ProtocolException(data, TipMessages.PETNOTEXIST);
				if (playerPet.getLevel() >= Server.config.getMaxLevel(0))
					return;
				int result = pay(player, playerPet.getLevel());
				if (result == -1) {
					throw new ProtocolException(data, ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE);
				} else if (result == -2) {
					throw new ProtocolException(data, ErrorMessages.PLAYER_INOC_MESSAGE);
				}
				playerPet.setTrainEndTime(trainEndTime(playerPet));
				playerPetService.update(playerPet);
				player.setPlayerPet(playerPet);
				// 更新经验
				PetTrain train = playerPetService.getTrainByLevel(playerPet.getLevel());
				int trainExp = train.getTrainExp();// 训练得到的经验
				int addLevel = playerPetService.updateExp(player, playerPet, trainExp);

				// 记录日志
				PetRecord logBean = new PetRecord();
				logBean.setCreateTime(new Date());
				logBean.setLevel(playerPet.getLevel());
				logBean.setPetId(playerPet.getPet().getId());
				logBean.setPlayerId(playerPet.getPlayerId());
				logBean.setRemark("训练宠物：" + playerPet.getPet().getId());
				logBean.setType(PetRecord.TRAIN);
				playerPetService.save(logBean);
				ServiceManager.getManager().getTaskService().petTraining(player, playerPet.getPet().getId());
				GameLogService.trainPet(player.getId(), player.getLevel(), trainPetId, addLevel > 0, 0, 0, 0);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(data, ex.getMessage());
		}
	}

	private Date trainEndTime(PlayerPet playePet) {
		PetTrain train = playerPetService.getTrainByLevel(playePet.getLevel());
		int trainTime = train.getTrainTime();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, trainTime);
		return cal.getTime();
	}

	private int pay(WorldPlayer player, int level) throws Exception {
		IPlayerPetService playerPetService = manager.getPlayerPetService();
		PlayerService playerService = manager.getPlayerService();
		PetTrain train = playerPetService.getTrainByLevel(level);
		int needMoney = train.getTrainPrice();

		if (player.getMoney() < needMoney) {
			return -2;
		}
		playerService.updatePlayerGold(player, -needMoney, "宠物训练", "");
		return 0;
	}
}
