package com.wyd.empire.world.server.handler.pet;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.ReceivePetCultureValue;
import com.wyd.empire.protocol.data.pet.RequestCulture;
import com.wyd.empire.world.bean.PetCulture;
import com.wyd.empire.world.bean.PetRecord;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 请求培养 注意：返回给客户端的是培养数值。培养＝培养数值*培养系数
 * 
 * @author zengxc
 * 
 */
public class RequestCultureHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestCultureHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		RequestCulture petRequestCulture = (RequestCulture) data;
		ReceivePetCultureValue petCultureValue = new ReceivePetCultureValue(data.getSessionId(), data.getSerial());
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		int petId = petRequestCulture.getPetId();
		playerPetService = manager.getPlayerPetService();
		try {
			player.setPetCultureValue(null);

			int pay = 5;
			int payment = petRequestCulture.getPayment();
			PlayerPet playerPet = playerPetService.getByPlayerAndPet(playerId, petId);
			PetCulture petCulture = playerPetService.getCultureByLevel(playerPet.getLevel());
			// 支付方式
			if (payment == 1) {
				// 钻石支付从正数开始
				pay = petCulture.getCultureDiamond().intValue();
			} else {
				pay = petCulture.getCultureGold().intValue();
			}
			// ==扣除费用==
			pay(player, payment, pay, data);
			// ==========
			// 取培养效果
			int[] cultureValues = getCultureValues(petCulture, playerPet, payment);
			// 保存到WorldPlay
			player.setPetCultureValue(cultureValues);
			petCultureValue.setCultureAddHp(cultureValues[0]);
			petCultureValue.setCultureAddAttack(cultureValues[1]);
			petCultureValue.setCultureAddDefend(cultureValues[2]);
			session.write(petCultureValue);
			// 记录日志
			PetRecord logBean = new PetRecord();
			logBean.setCreateTime(new Date());
			logBean.setLevel(playerPet.getLevel());
			logBean.setPetId(playerPet.getPet().getId());
			logBean.setPlayerId(playerPet.getPlayerId());
			// 支付方式
			if (payment == 1) {
				logBean.setType(PetRecord.DIAMONCUL);
				logBean.setRemark("使用钻石培养宠物：" + playerPet.getPet().getId());
			} else {
				logBean.setType(PetRecord.GOLDCUL);
				logBean.setRemark("使用金币培养宠物：" + playerPet.getPet().getId());
			}
			playerPetService.save(logBean);
			ServiceManager.getManager().getTaskService().petCulture(player, petId);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

	private int[] getCultureValues(PetCulture petCulture, PlayerPet playerPet, int payment) {
		int[] vals = new int[]{0, 0, 0};
		// 高级宠物与普通宠物上限不一样
		int hpmax = petCulture.getHpMax(playerPet.getPet().getQuality());
		int atkmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
		int defmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
		int start = 0, end = 0;
		if (payment != 1) {
			start = -20;
			end = 21;
		} else {
			start = 0;
			end = 10;
		}
		vals[0] = getCultureValue(playerPet.getCulHPNum(), hpmax, start, end);
		if (payment != 1) {
			start = -4;
			end = 5;
		} else {
			start = 0;
			end = 2;
		}
		vals[1] = getCultureValue(playerPet.getCulAttackNum(), atkmax, start, end);
		if (payment != 1) {
			start = -5;
			end = 6;
		} else {
			start = 0;
			end = 3;
		}
		vals[2] = getCultureValue(playerPet.getCulDefendNum(), defmax, start, end);
		return vals;
	}

	// 增加后的值不能大于上限也不能小于0
	private int getCultureValue(int val, int max, int CULSTART, int CULEND) {
		int h = ServiceUtils.getRandomNum(CULSTART, CULEND + 1);
		int newVal = h + val;
		h = newVal > max ? (max - val) : h;
		return newVal < 0 ? -val : h;
	}

	/**
	 * 
	 * @param player
	 * @param diamondNum
	 * @throws Exception
	 */
	private void pay(WorldPlayer player, int payment, int pay, AbstractData data) throws Exception {
		if (payment == 1) {
			if (player.getDiamond() < pay) {
				throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			manager.getPlayerService().useTicket(player, pay, TradeService.ORIGIN_CULTURE, null, null, "宠物培养");
		} else {
			if (player.getMoney() < pay) {
				throw new ProtocolException(ErrorMessages.PLAYER_INOC_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			manager.getPlayerService().updatePlayerGold(player, -pay, "宠物培养", "");
		}
	}

}
