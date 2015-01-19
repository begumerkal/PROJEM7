package com.wyd.empire.world.server.handler.pet;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.pet.GuideRequestCulture;
import com.wyd.empire.protocol.data.pet.ReceivePetCultureValue;
import com.wyd.empire.world.bean.PetCulture;
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
public class GuideRequestCultureHandler implements IDataHandler {
	Logger log = Logger.getLogger(GuideRequestCultureHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();
	// 取值范围
	int CULSTART = -6, CULEND = 3;

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GuideRequestCulture petRequestCulture = (GuideRequestCulture) data;
		ReceivePetCultureValue petCultureValue = new ReceivePetCultureValue(data.getSessionId(), data.getSerial());
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		int petId = petRequestCulture.getPetId();
		playerPetService = manager.getPlayerPetService();
		try {
			CULSTART = -6;
			CULEND = 3;
			int pay = 5;
			int payment = petRequestCulture.getPayment();
			PlayerPet playerPet = playerPetService.getByPlayerAndPet(playerId, petId);
			PetCulture petCulture = playerPetService.getCultureByLevel(playerPet.getLevel());
			// 支付方式
			if (payment == 1) {
				// 钻石支付从正数开始
				CULSTART = 0;
				pay = petCulture.getCultureDiamond().intValue();
			} else {
				pay = petCulture.getCultureGold().intValue();
			}
			// TODO 不知是否还需要判断教学进度
			// boolean petComplete =
			// ServiceManager.getManager().getGuideService().checkPlayerGuideStatus(player.getId(),
			// Common.GUIDE_PET_ID);
			// if (petComplete) {
			// ==扣除费用==
			pay(player, payment, pay);
			// } else {
			// ServiceManager.getManager().getGuideService().guideFlish(player.getId(),
			// Common.GUIDE_PET_ID);
			// }
			// ==========
			// 取培养效果
			int[] cultureValues = getCultureValues(petCulture, playerPet);
			// 保存到WorldPlay
			player.setPetCultureValue(cultureValues);
			petCultureValue.setCultureAddHp(cultureValues[0]);
			petCultureValue.setCultureAddAttack(cultureValues[1]);
			petCultureValue.setCultureAddDefend(cultureValues[2]);
			session.write(petCultureValue);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

	private int[] getCultureValues(PetCulture petCulture, PlayerPet playerPet) {
		int[] vals = new int[]{0, 0, 0};
		// 高级宠物与普通宠物上限不一样
		int hpmax = petCulture.getHpMax(playerPet.getPet().getQuality());
		int atkmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
		int defmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
		vals[0] = getCultureValue(playerPet.getCulHPNum(), hpmax);
		vals[1] = getCultureValue(playerPet.getCulAttackNum(), atkmax);
		vals[2] = getCultureValue(playerPet.getCulDefendNum(), defmax);
		return vals;
	}

	// 随机取增加量
	private int getCultureNums() {
		return ServiceUtils.getRandomNum(CULSTART, CULEND);
	}

	// 增加后的值不能大于上限也不能小于0
	private int getCultureValue(int val, int max) {
		int h = getCultureNums();
		int newVal = h + val;
		h = newVal > max ? (max - val) : h;
		return newVal < 0 ? -val : h;
	}

	/**
	 * @param player
	 * @param diamondNum
	 * @throws Exception
	 */
	private void pay(WorldPlayer player, int payment, int pay) throws Exception {
		if (payment == 1) {
			if (player.getDiamond() < pay) {
				throw new Exception(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE);
			}
			manager.getPlayerService().useTicket(player, pay, TradeService.ORIGIN_CULTURE, null, null, "宠物培养");
		} else {
			if (player.getMoney() < pay) {
				throw new Exception(ErrorMessages.PLAYER_INOC_MESSAGE);
			}
			manager.getPlayerService().updatePlayerGold(player, -pay, "宠物培养", "");
		}
	}
}
