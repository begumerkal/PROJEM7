package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.AttackSequence;
import com.wyd.empire.protocol.data.cross.CrossAttackSequence;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 返回跨服工具顺序
 * 
 * @author zguoqiu
 */
public class CrossAttackSequenceHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossAttackSequenceHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossAttackSequence crossAttackSequence = (CrossAttackSequence) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService()
					.getOnlineWorldPlayer(ServiceManager.getManager().getCrossService().getPlayerId(crossAttackSequence.getPlayerId()));
			if (null != player) {
				AttackSequence attackSequence = new AttackSequence();
				attackSequence.setBattleId(crossAttackSequence.getBattleId());
				attackSequence.setPlayerId(crossAttackSequence.getPlayerId());
				attackSequence.setIdcount(crossAttackSequence.getIdcount());
				attackSequence.setPlayerIds(crossAttackSequence.getPlayerIds());
				player.sendData(attackSequence);
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}