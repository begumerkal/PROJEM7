package com.wyd.empire.world.server.handler.pet;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.GetInheritanceListOk;
import com.wyd.empire.world.bean.PetItem;
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
 * 取宠物传承列表
 * 
 * @author zengxc
 */
public class GetInheritanceListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetInheritanceListHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int playerId = player.getId();
		playerPetService = manager.getPlayerPetService();
		try {
			List<PlayerPet> playerPets = playerPetService.getPetListByPlayer(playerId);
			int count = playerPets.size();
			int[] petId = new int[count]; // 宠物ID
			String[] name = new String[count]; // 名称
			String[] icon = new String[count]; // 宠物图标
			int[] petLevel = new int[count]; // 级别
			int[] initHps = new int[count]; // 初始生命
			int[] initAttacks = new int[count]; // 初始攻击
			int[] initDefends = new int[count]; // 初始防御
			int[] hasHps = new int[count]; // 已有的生命
			int[] hasAttacks = new int[count]; // 已有的攻击
			int[] hasDefends = new int[count]; // 已有的防御
			int[] isInheritances = new int[count]; // 是否传承过（0没有，1传承过）
			int[] isBeInheriteds = new int[count]; // 是否被传承（0否，1是）
			int[] isPlayeds = new int[count]; // 是否已出战（0否，1是）
			int[] hpGrowth = new int[count]; // 生命成长系数
			int[] attackGrowth = new int[count]; // 攻击成长系数
			int[] defendGrowth = new int[count]; // 防御成长系数
			int[] culHp = new int[count]; // 培养的生命
			int[] culAttack = new int[count]; // 培养的初始攻击
			int[] culDefend = new int[count]; // 培养的初始防御
			int needDiamond = Common.PET_INHERITANCE_DIAMOND;// 传承需要钻石
			for (int i = 0; i < playerPets.size(); i++) {
				PlayerPet playerPet = playerPets.get(i);
				PetItem pet = playerPet.getPet();
				petId[i] = pet.getId();
				name[i] = playerPet.getPetName();
				icon[i] = playerPet.getPetIcon();
				petLevel[i] = playerPet.getLevel();
				initHps[i] = pet.getInitHp();
				initAttacks[i] = pet.getInitAttack();
				initDefends[i] = pet.getInitDefend();
				if (playerPet.getInheritance() == 1) {
					isInheritances[i] = 1;
				} else if (playerPet.getInheritance() == 2) {
					isBeInheriteds[i] = 1;
				} else if (playerPet.getInheritance() == 3) {
					isInheritances[i] = 1;
					isBeInheriteds[i] = 1;
				}
				hasHps[i] = playerPet.getHP();
				hasAttacks[i] = playerPet.getAttack();
				hasDefends[i] = playerPet.getDefend();
				isPlayeds[i] = playerPet.isInUsed() ? 1 : 0;
				hpGrowth[i] = pet.getHpGrowth() * 10000;// 由于是浮点数，所以放1万倍传输
				attackGrowth[i] = pet.getAttackGrowth() * 10000;
				defendGrowth[i] = pet.getDefendGrowth() * 10000;
				culHp[i] = (int) playerPet.getCulHP();
				culAttack[i] = (int) playerPet.getCulAttack();
				culDefend[i] = (int) playerPet.getCulDefend();
			}
			GetInheritanceListOk getListOk = new GetInheritanceListOk(data.getSessionId(), data.getSerial());
			getListOk.setPetId(petId);
			getListOk.setName(name);
			getListOk.setIcon(icon);
			getListOk.setPetLevel(petLevel);
			getListOk.setInitHp(initHps);
			getListOk.setInitAttack(initAttacks);
			getListOk.setInitDefend(initDefends);
			getListOk.setHasHp(hasHps);
			getListOk.setHasAttack(hasAttacks);
			getListOk.setHasDefend(hasDefends);
			getListOk.setIsInheritance(isInheritances);
			getListOk.setIsBeInherited(isBeInheriteds);
			getListOk.setNeedDiamond(needDiamond);
			getListOk.setIsPlayed(isPlayeds);
			getListOk.setHpGrowth(hpGrowth);
			getListOk.setAttackGrowth(attackGrowth);
			getListOk.setDefendGrowth(defendGrowth);
			getListOk.setCulHp(culHp);
			getListOk.setCulAttack(culAttack);
			getListOk.setCulDefend(culDefend);
			session.write(getListOk);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PET_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

}
