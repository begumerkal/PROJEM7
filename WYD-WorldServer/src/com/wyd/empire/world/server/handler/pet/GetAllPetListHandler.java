package com.wyd.empire.world.server.handler.pet;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.GetAllPetListOk;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetAllPetListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetAllPetListHandler.class);

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetAllPetListOk getAllPetListOk = new GetAllPetListOk(data.getSessionId(), data.getSerial());
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			List<PetItem> piList = ServiceManager.getManager().getPetItemService().getAllPetList();
			Map<Integer, PlayerPet> ppsMap = ServiceManager.getManager().getPetItemService().getPlayerPetMap(player.getId());
			int[] petId = new int[piList.size()]; // 宠物ID
			String[] name = new String[piList.size()]; // 名称
			String[] icon = new String[piList.size()]; // 宠物图标
			String[] picture = new String[piList.size()]; // 宠物大图
			int[] petLevel = new int[piList.size()]; // 级别
			int[] quality = new int[piList.size()]; // 类型0普通1高级
			int[] hp = new int[piList.size()]; // 生命
			int[] attack = new int[piList.size()]; // 攻击
			int[] defend = new int[piList.size()]; // 防御
			String[] skillIcon = new String[piList.size()]; // 技能图标
			int[] getLevel = new int[piList.size()]; // 宠物召唤等级
			int[] getMoney = new int[piList.size()]; // 购买宠物金币
			int[] playerGet = new int[piList.size()]; // 玩家是否拥有宠物（0不拥有1拥有）
			int[] payType = new int[piList.size()]; // 宠物的支付类型
			String[] skillDesc = new String[piList.size()]; // 宠物技能描述

			int petNum = ServiceManager.getManager().getPlayerPetService().openBarNum(player.getId()); // 玩家携带宠物上限
			int playerPetNum = ppsMap.size(); // 玩家拥有宠物数

			PlayerPet pps;
			int index = 0;
			for (PetItem pi : piList) {
				petId[index] = pi.getId();
				name[index] = pi.getName();
				icon[index] = pi.getIcon();
				picture[index] = pi.getPicture();
				quality[index] = pi.getQuality();
				skillIcon[index] = pi.getSkill().getIcon();
				getLevel[index] = pi.getCallLevel();
				getMoney[index] = pi.getMoney();
				pps = ppsMap.get(pi.getId());
				if (null == pps) {
					playerGet[index] = 0;
					petLevel[index] = 0;
					hp[index] = pi.getInitHp();
					attack[index] = pi.getInitAttack();
					defend[index] = pi.getInitDefend();
				} else {
					playerGet[index] = 1;
					petLevel[index] = pps.getLevel();
					hp[index] = pps.getHP();
					attack[index] = pps.getAttack();
					defend[index] = pps.getDefend();
				}
				payType[index] = pi.getPayType();
				skillDesc[index] = pi.getSkill().getName() + ":" + pi.getSkill().getDesc();

				index++;
			}

			getAllPetListOk.setPetId(petId);
			getAllPetListOk.setName(name);
			getAllPetListOk.setIcon(icon);
			getAllPetListOk.setPicture(picture);
			getAllPetListOk.setPetLevel(petLevel);
			getAllPetListOk.setQuality(quality);
			getAllPetListOk.setHp(hp);
			getAllPetListOk.setAttack(attack);
			getAllPetListOk.setDefend(defend);
			getAllPetListOk.setSkillIcon(skillIcon);
			getAllPetListOk.setGetLevel(getLevel);
			getAllPetListOk.setGetMoney(getMoney);
			getAllPetListOk.setPetNum(petNum);
			getAllPetListOk.setPlayerPetNum(playerPetNum);
			getAllPetListOk.setPlayerGet(playerGet);
			getAllPetListOk.setPayType(payType);
			getAllPetListOk.setSkillDesc(skillDesc);

			session.write(getAllPetListOk);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(data, ErrorMessages.COMMUNITY_GETLIST_MESSAGE);
		}
	}

}
