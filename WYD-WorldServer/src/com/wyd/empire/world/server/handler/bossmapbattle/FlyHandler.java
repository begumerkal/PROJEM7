package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.Fly;
import com.wyd.empire.protocol.data.bossmapbattle.OtherFly;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 飞行
 * 
 * @author Administrator
 */
public class FlyHandler implements IDataHandler {
	Logger log = Logger.getLogger(FlyHandler.class);

	public void handle(AbstractData data) throws Exception {
		Fly fly = (Fly) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = fly.getBattleId();
			int playerOrGuai = fly.getPlayerOrGuai();
			int currentId = fly.getCurrentId();
			int speedx = fly.getSpeedx();
			int speedy = fly.getSpeedy();
			byte leftRight = fly.getLeftRight();
			int isEquip = fly.getIsEquip();
			int startx = fly.getStartx();
			int starty = fly.getStarty();
			int playerCount = fly.getPlayerCount();
			int[] playerIds = fly.getPlayerIds();
			int[] curPositionX = fly.getCurPositionX();
			int[] curPositionY = fly.getCurPositionY();
			int guaiCount = fly.getGuaiCount(); // 怪的数量
			int[] guaiBattleId = fly.getGuaiBattleId(); // 怪id
			int[] guaiCurPositionX = fly.getGuaiCurPositionX();// 没飞行前的x坐标
			int[] guaiCurPositionY = fly.getGuaiCurPositionY();// 没飞行前的y坐标

			// System.out.println(curPositionX[0] +"----"+curPositionX[1]);
			// System.out.println(curPositionY[0]+"----"+curPositionY[1]);
			// System.out.println(guaiCurPositionX[0]+"----"+guaiCurPositionX[1]);
			// System.out.println(guaiCurPositionY[0]+"----"+guaiCurPositionX[1]);

			OtherFly otherFly = new OtherFly();
			otherFly.setBattleId(battleId);
			otherFly.setPlayerOrGuai(playerOrGuai);
			otherFly.setCurrentId(currentId);
			otherFly.setSpeedx(speedx);
			otherFly.setSpeedy(speedy);
			otherFly.setLeftRight(leftRight);
			otherFly.setIsEquip(isEquip);
			otherFly.setStartx(startx);
			otherFly.setStarty(starty);
			otherFly.setPlayerCount(playerCount);
			otherFly.setPlayerIds(playerIds);
			otherFly.setCurPositionX(curPositionX);
			otherFly.setCurPositionY(curPositionY);
			otherFly.setGuaiCount(guaiCount);
			otherFly.setGuaiBattleId(guaiBattleId);
			otherFly.setGuaiCurPositionX(guaiCurPositionX);
			otherFly.setGuaiCurPositionY(guaiCurPositionY);

			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);

			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(otherFly);
				}
			}

			// 更新任务
			if (playerOrGuai == 0) {
				Combat combat = battleTeam.getCombatMap().get(currentId);
				if (!combat.isRobot()) {
					ServiceManager.getManager().getTaskService().flySkill(combat.getPlayer());
					ServiceManager.getManager().getTitleService().flySkill(combat.getPlayer());
				}
			}

		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
