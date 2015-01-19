package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.KillGuai;
import com.wyd.empire.protocol.data.bossmapbattle.SomeOneDead;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 杀死怪
 * 
 * @author Administrator
 *
 */
public class KillGuaiHandler implements IDataHandler {
	Logger log = Logger.getLogger(KillGuaiHandler.class);

	public void handle(AbstractData data) throws Exception {
		KillGuai killGuai = (KillGuai) data;
		try {
			int battleId = killGuai.getBattleId();
			int[] guaiBattleIds = killGuai.getGuaiBattleIds();
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			SomeOneDead SomeOneDead = new SomeOneDead();
			SomeOneDead.setBattleId(battleId);
			SomeOneDead.setPlayerIds(new int[0]);
			SomeOneDead.setDeadPlayerCount(0);
			SomeOneDead.setDeadGuaiCount(guaiBattleIds.length);
			SomeOneDead.setGuaiBattleIds(guaiBattleIds);

			Combat guaiVo;
			for (int guaiId : guaiBattleIds) {
				guaiVo = battleTeam.getGuaiMap().get(guaiId);
				guaiVo.setHp(0);
				guaiVo.setDead(true);
				battleTeam.getGuaiMap().remove(guaiId);
				battleTeam.getCombatGuaiList().remove(guaiVo);
			}

			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot()) {
					combat.getPlayer().sendData(SomeOneDead);
				}
			}

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
