package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.SkillEquip;
import com.wyd.empire.protocol.data.cross.CrossSkillEquip;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 机器人使用技能道具
 * 
 * @author zguoqiu
 */
public class CrossSkillEquipHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossSkillEquipHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossSkillEquip cse = (CrossSkillEquip) data;
		try {
			SkillEquip skillEquip = new SkillEquip();
			skillEquip.setBattleId(cse.getBattleId());
			skillEquip.setPlayerId(cse.getPlayerId());
			skillEquip.setItemcount(cse.getItemcount());
			skillEquip.setItmeIds(cse.getItmeIds());
			ServiceManager.getManager().getCrossService().skillEquip(skillEquip);
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}