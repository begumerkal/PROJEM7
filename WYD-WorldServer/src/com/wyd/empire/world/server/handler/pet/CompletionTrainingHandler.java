package com.wyd.empire.world.server.handler.pet;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import com.wyd.empire.world.bean.PetRecord;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 完成训练 如果到时间完成，刚更新完成时间 如果还未到时间完成，则使用钻石加速 完成。 加速公式 diamondNum = (int) Math.ceil(
 * diamondMin*timeLeft/240*Math.pow(0.999, 240-timeLeft)); 升级要更新WorldPlayer的宠物
 * 
 * @author zengxc
 *
 */

public class CompletionTrainingHandler implements IDataHandler {
	Logger log = Logger.getLogger(CompletionTrainingHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		playerPetService = manager.getPlayerPetService();
		try {
			int diamondMin = Common.PET_COMPLETION_DIAMOND;// 训练加速钻石单位
			PlayerPet trainPet = playerPetService.getInTrainPet(playerId);
			if (trainPet == null) {
				// 没有正在训练的宠物
				throw new ProtocolException(TipMessages.TRAINPETNOTFOUND, data.getSessionId(), data.getType(), data.getSubType());
			} else {
				// 如果还没完成则扣除钻石
				if (!isCompletion(trainPet.getTrainEndTime())) {
					int timeLeft = TrainTimeLeft(trainPet.getTrainEndTime());
					int diamondNum = (int) Math.ceil(diamondMin * timeLeft / 240 * Math.pow(0.999, 240 - timeLeft));
					if (!pay(player, diamondNum, trainPet)) {
						throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSessionId(), data.getType(),
								data.getSubType());
					}
					// 记录日志
					PetRecord logBean = new PetRecord();
					logBean.setCreateTime(new Date());
					logBean.setLevel(trainPet.getLevel());
					logBean.setPetId(trainPet.getPet().getId());
					logBean.setPlayerId(trainPet.getPlayerId());
					logBean.setRemark("使用钻石加速训练宠物：" + trainPet.getPet().getId());
					logBean.setType(PetRecord.DIAMONDTRAIN);
					playerPetService.save(logBean);
				}

				// 设置完成训练
				trainPet.setTrainEndTime(null);
				playerPetService.update(trainPet);
				// 更新WorldPlayer
				if (trainPet.isInUsed()) {
					player.setPlayerPet(trainPet);
				}
			}
			// 结束后，重新返回宠物列表
			Map<String, String> info = new HashMap<String, String>();
			info.put("inTrain", "0");
			playerPetService.sendUpdatePet(player, trainPet.getPet().getId(), info);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}

	}

	private boolean isCompletion(Date trainEndTime) {
		return TrainTimeLeft(trainEndTime) == 0;
	}

	/**
	 * 训练剩余时间（分）
	 * 
	 * @param trainEndTime
	 * @return
	 */
	private int TrainTimeLeft(Date trainEndTime) {
		Calendar cal = Calendar.getInstance();
		long nowTime = cal.getTime().getTime();
		cal.setTime(trainEndTime);
		long endTime = cal.getTime().getTime();
		if (endTime <= nowTime) {
			return 0;
		} else {
			long cha = endTime - nowTime;
			return (int) cha / (1000 * 60);
		}
	}

	/**
	 * 训练加速支付钻石
	 * 
	 * @param player
	 * @param diamondNum
	 * @throws Exception
	 */
	private boolean pay(WorldPlayer player, int diamondNum, PlayerPet trainPet) throws Exception {
		if (player.getDiamond() < diamondNum) {
			return false;
		}
		manager.getPlayerService().useTicket(player, diamondNum, TradeService.ORIGIN_TRAIN, null, null, "宠物训练加速");
		GameLogService.trainPet(player.getId(), player.getLevel(), trainPet.getPet().getId(), false, diamondNum, 0, 0);
		return true;
	}

}
