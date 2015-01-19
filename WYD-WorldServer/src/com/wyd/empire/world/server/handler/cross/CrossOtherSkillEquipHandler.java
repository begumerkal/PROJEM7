package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherSkillEquip;
import com.wyd.empire.protocol.data.cross.CrossOtherSkillEquip;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家使用技能道具
 * 
 * @author zguoqiu
 */
public class CrossOtherSkillEquipHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherSkillEquipHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherSkillEquip cose = (CrossOtherSkillEquip) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cose.getRoomId());
			if (null != room) {
				OtherSkillEquip otherSkillEquip = new OtherSkillEquip();
				otherSkillEquip.setBattleId(cose.getBattleId());
				otherSkillEquip.setCurrentPlayerId(cose.getCurrentPlayerId());
				otherSkillEquip.setItemcount(cose.getItemcount());
				otherSkillEquip.setItmeIds(cose.getItmeIds());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != cose
									.getCurrentPlayerId()) {
						otherSkillEquip.setPlayerId(ServiceManager.getManager().getCrossService()
								.getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(otherSkillEquip);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}