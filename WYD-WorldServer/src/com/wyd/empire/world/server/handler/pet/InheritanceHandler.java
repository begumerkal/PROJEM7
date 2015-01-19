package com.wyd.empire.world.server.handler.pet;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.Inheritance;
import com.wyd.empire.protocol.data.pet.InheritanceOk;
import com.wyd.empire.world.bean.PetCulture;
import com.wyd.empire.world.bean.PetItem;
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
 * 宠物传承
 * 
 * @author Administrator
 */
public class InheritanceHandler implements IDataHandler {
	Logger log = Logger.getLogger(InheritanceHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		Inheritance inheritance = (Inheritance) data;
		playerPetService = manager.getPlayerPetService();
		InheritanceOk ok = new InheritanceOk(data.getSessionId(), data.getSerial());
		int diamondNum = Common.PET_INHERITANCE_DIAMOND;// 传承需要钻石
		try {
			// 传承与被传承不能一样
			if (inheritance.getInheritanceId() == inheritance.getBeInheriedId())
				return;
			PlayerPet fromPet = playerPetService.getByPlayerAndPet(playerId, inheritance.getInheritanceId());
			PlayerPet toPet = playerPetService.getByPlayerAndPet(playerId, inheritance.getBeInheriedId());
			// 未培养过的宠物不能传承
			if (fromPet.getCulHP() == 0 && fromPet.getCulAttack() == 0 && fromPet.getCulDefend() == 0) {
				throw new ProtocolException(TipMessages.PETNOTCULTURE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 支付
			pay(player, diamondNum, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			session.write(ok);
			// 记录日志
			PetRecord logBean = new PetRecord();
			logBean.setRemark("宠物“" + fromPet.getPet().getName() + "（" + fromPet.getLevel() + "）”传承给“" + toPet.getPet().getName() + "("
					+ toPet.getLevel() + ")”");
			// 传承会有20%的损耗
			int fromLevel = (int) (fromPet.getLevel() * 0.8f);
			int fromCulHpNum = (int) (fromPet.getCulHPNum() * 0.8f);
			int fromCulAttackNum = (int) (fromPet.getCulAttackNum() * 0.8f);
			int fromCulDefendNum = (int) (fromPet.getCulDefendNum() * 0.8f);
			if (fromPet.getInheritance() == 2) {
				fromPet.setInheritance(3);
			} else {
				fromPet.setInheritance(1);
			}
			playerPetService.update(fromPet);
			int toLevel = toPet.getLevel();
			int toCulHpNum = toPet.getCulHPNum();
			int toCulAttackNum = toPet.getCulAttackNum();
			int toCulDefendNum = toPet.getCulDefendNum();
			// 传承只使用较大的值
			if (fromLevel > toLevel) {
				// 取刚好升级的经验
				toPet.setPetExp(0);
				toPet.setLevel(fromLevel);
			}
			PetCulture toPetCulture = playerPetService.getCultureByLevel(toPet.getLevel());
			int growthLevel = toPet.getLevel() - 1;
			// 高级宠物和普通宠物培养上限不一样
			int hpmax = toPetCulture.getHpMax(toPet.getPet().getQuality());
			int atkmax = toPetCulture.getAtkMax(toPet.getPet().getQuality());
			int defmax = toPetCulture.getAtkMax(toPet.getPet().getQuality());
			// 传承培养值时，不能大于被传承宠物的上限
			fromCulHpNum = fromCulHpNum > hpmax ? hpmax : fromCulHpNum;
			fromCulAttackNum = fromCulAttackNum > atkmax ? atkmax : fromCulAttackNum;
			fromCulDefendNum = fromCulDefendNum > defmax ? defmax : fromCulDefendNum;
			// 取较的大值
			toPet.setCulHPNum(fromCulHpNum > toCulHpNum ? fromCulHpNum : toCulHpNum);
			toPet.setCulAttackNum(fromCulAttackNum > toCulAttackNum ? fromCulAttackNum : toCulAttackNum);
			toPet.setCulDefendNum(fromCulDefendNum > toCulDefendNum ? fromCulDefendNum : toCulDefendNum);
			toPet.setCulHP(toPetCulture.getHpCoef() * toPet.getCulHPNum());
			toPet.setCulAttack(toPetCulture.getAttackCoef() * toPet.getCulAttackNum());
			toPet.setCulDefend(toPetCulture.getDefendCoef() * toPet.getCulDefendNum());
			PetItem pet = toPet.getPet();
			toPet.setPetHP(pet.getInitHp() + pet.getHpGrowth() * growthLevel);
			toPet.setPetAttack(pet.getInitAttack() + pet.getAttackGrowth() * growthLevel);
			toPet.setPetDefend(pet.getInitDefend() + pet.getDefendGrowth() * growthLevel);
			if (toPet.getInheritance() == 1) {
				toPet.setInheritance(3);
			} else {
				toPet.setInheritance(2);
			}
			// 到了进化级别后，使用进化的技能
			if (toPet.getLevel() >= pet.getEvoLevel()) {
				toPet.setSkill(pet.getEvoSkill());
			}
			playerPetService.update(toPet);
			player.setPlayerPet(toPet);
			logBean.setCreateTime(new Date());
			logBean.setLevel(toPet.getLevel());
			logBean.setPetId(pet.getId());
			logBean.setPlayerId(fromPet.getPlayerId());
			logBean.setType(PetRecord.INHERITANCE);
			playerPetService.save(logBean);
			playerPetService.sendPlayerPet(player);
			GameLogService.inheritPet(playerId, player.getLevel(), fromPet.getPet().getId(), fromPet.getLevel(), toPet.getPet().getId(),
					toPet.getLevel(), diamondNum, 0, 0);

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PET_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 宠物传承支付钻石
	 * 
	 * @param player
	 * @param diamondNum
	 * @throws Exception
	 */
	private void pay(WorldPlayer player, int diamondNum, int serial, int sessionId, byte type, byte subType) throws Exception {
		if (player.getDiamond() < diamondNum) {
			throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, serial, sessionId, type, subType);
		}
		manager.getPlayerService().useTicket(player, diamondNum, TradeService.ORIGIN_INHERITANCE, null, null, "宠物传承");
	}
}
