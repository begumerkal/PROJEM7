package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherShoot;
import com.wyd.empire.protocol.data.bossmapbattle.Shoot;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发射
 * 
 * @author Administrator
 */
public class ShootHandler implements IDataHandler {
	Logger log = Logger.getLogger(ShootHandler.class);

	public void handle(AbstractData data) throws Exception {
		Shoot shoot = (Shoot) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = shoot.getBattleId();// 战斗id
			int playerOrGuai = shoot.getPlayerOrGuai();//
			int currentId = shoot.getCurrentId();//
			int speedx = shoot.getSpeedx();// 发射速度
			int speedy = shoot.getSpeedy();// 力度速度
			byte leftRight = shoot.getLeftRight();// 1：左 0：右（向左还是向右）
			int startx = shoot.getStartx();// 发射初始位置
			int starty = shoot.getStarty();// 发射初始位置
			int playerCount = shoot.getPlayerCount();// 同步角色数量
			int[] playerIds = shoot.getPlayerIds();// 用户id
			int[] curPositionX = shoot.getCurPositionX();// 没飞行前的x坐标
			int[] curPositionY = shoot.getCurPositionY();// 没飞行前的y坐标
			int guaiCount = shoot.getGuaiCount();// 怪的数量
			int[] guaiBattleIds = shoot.getGuaiBattleId();// 怪id
			int[] guaiCurPositionX = shoot.getGuaiCurPositionX();// 没飞行前的x坐标
			int[] guaiCurPositionY = shoot.getGuaiCurPositionY();// 没飞行前的y坐标
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null != battleTeam) {
				OtherShoot otherShoot = new OtherShoot();
				otherShoot.setBattleId(battleId);
				otherShoot.setPlayerOrGuai(playerOrGuai);
				otherShoot.setCurrentId(currentId);
				otherShoot.setSpeedx(speedx);
				otherShoot.setSpeedy(speedy);
				otherShoot.setLeftRight(leftRight);
				otherShoot.setStartx(startx);
				otherShoot.setStarty(starty);
				otherShoot.setPlayerCount(playerCount);
				otherShoot.setPlayerIds(playerIds);
				otherShoot.setCurPositionX(curPositionX);
				otherShoot.setCurPositionY(curPositionY);
				otherShoot.setGuaiCount(guaiCount);
				otherShoot.setGuaiBattleId(guaiBattleIds);
				otherShoot.setGuaiCurPositionX(guaiCurPositionX);
				otherShoot.setGuaiCurPositionY(guaiCurPositionY);
				for (Combat combat : battleTeam.getCombatList()) {
					if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
						combat.getPlayer().sendData(otherShoot);
					}
				}
				if (playerOrGuai == 0) {
					Combat combat = battleTeam.getCombatMap().get(currentId);
					combat.setShootTimes(combat.getShootTimes() + 1);
					combat.setActionTimes(combat.getActionTimes() + 1);
					if (!battleTeam.isUseKill()) {
						combat.setTiredValue(combat.getTiredValue() + 5);
					} else {
						battleTeam.setUseKill(false);
					}
				} else {
					Combat guaiVo = battleTeam.getGuaiMap().get(currentId);
					guaiVo.setShootTimes(guaiVo.getShootTimes() + 1);
					guaiVo.setActionTimes(guaiVo.getActionTimes() + 1);
					if (!battleTeam.isUseKill()) {
						guaiVo.setTiredValue(guaiVo.getTiredValue() + 5);
					} else {
						battleTeam.setUseKill(false);
					}
				}
				if (!battleTeam.isUseBigKill()) {
					ServiceManager.getManager().getBossBattleTeamService().updateAngryValue(battleId, playerOrGuai, currentId, 17, false);
				} else {
					battleTeam.setUseBigKill(false);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
			ex.printStackTrace();
		}
	}
}
