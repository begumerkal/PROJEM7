package com.wyd.empire.world.server.handler.battle;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherSkillEquip;
import com.wyd.empire.protocol.data.battle.SkillEquip;
import com.wyd.empire.protocol.data.battle.UpdateHP;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 使用技能和道具
 * 
 * @author Administrator
 */
public class SkillEquipHandler implements IDataHandler {
	Logger log = Logger.getLogger(SkillEquipHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer me = session.getPlayer(data.getSessionId());
		SkillEquip skillEquip = (SkillEquip) data;
		int battleId = skillEquip.getBattleId();
		if (battleId > 0) {
			try {
				int playerId = skillEquip.getPlayerId();
				int itemcount = skillEquip.getItemcount();
				int[] itmeIds = skillEquip.getItmeIds();
				OtherSkillEquip otherSkillEquip = new OtherSkillEquip();
				otherSkillEquip.setBattleId(battleId);
				otherSkillEquip.setCurrentPlayerId(playerId);
				otherSkillEquip.setItemcount(itemcount);
				otherSkillEquip.setItmeIds(itmeIds);
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}
				battleTeam.setUseKill(true);
				Combat combat = battleTeam.getCombatMap().get(playerId);
				List<Combat> cbList = new ArrayList<Combat>();// 获取同阵营玩家
				for (Combat cb : battleTeam.getCombatList()) {
					if (cb.getCamp() == combat.getCamp()) {
						cbList.add(cb);
					}
					if (!cb.isLost() && !cb.isRobot() && cb.getId() != playerId) {
						otherSkillEquip.setPlayerId(cb.getId());
						cb.getPlayer().sendData(otherSkillEquip);
					}
				}
				List<Integer> playerIds = new ArrayList<Integer>();
				List<Integer> hps = new ArrayList<Integer>();
				List<Tools> toolsList = ServiceManager.getManager().getToolsService().getToolsListByIds(itmeIds);
				for (Tools tools : toolsList) {
					battleTeam.setUseTools(battleTeam.getUseTools() + tools.getId() + ",");
					ServiceManager.getManager().getLogSerivce().updatePropsSkillLog(tools);
					combat.setTiredValue(combat.getTiredValue() + tools.getTireValue());
					combat.setPf(combat.getPf() - tools.getConsumePower(), battleTeam.getMapId(), battleTeam.getBattleMode(), me);
					if (1 == tools.getType().intValue()) {
						switch (tools.getSubtype().intValue()) {
							case 0 :
								int hp = tools.getParam1();
								// 公会技能加成
								hp = (int) ServiceManager.getManager().getBuffService().getAddition(combat.getPlayer(), hp, Buff.CTREAT);
								hp = (int) ServiceManager.getManager().getBuffService().getAddition(combat.getPlayer(), hp, Buff.CADDHP);
								hp += combat.getHp();
								hp = hp > combat.getMaxHP() ? combat.getMaxHP() : hp;
								combat.setHp(hp);
								playerIds.add(combat.getId());
								hps.add(combat.getHp());
								break;
							case 1 :
								for (Combat cb : cbList) {
									if (!cb.isDead()) {
										int chp = tools.getParam1();
										chp = (int) ServiceManager.getManager().getBuffService()
												.getAddition(combat.getPlayer(), chp, Buff.CTREAT);
										chp = (int) ServiceManager.getManager().getBuffService()
												.getAddition(combat.getPlayer(), chp, Buff.CADDHP);
										chp += cb.getHp();
										chp = chp > cb.getMaxHP() ? cb.getMaxHP() : chp;
										cb.setHp(chp);
										playerIds.add(cb.getId());
										hps.add(cb.getHp());
									}
								}
								break;
							case 2 :
								combat.setHurtRate(0);
								combat.setWillFrozen(tools.getParam1());
								break;
							case 3 :
								combat.setHurtRate(combat.getHurtRate() * (tools.getParam1() / 1000f));
								break;
							case 4 :
								combat.setHide(true, tools.getParam1());
								break;
							case 5 :
								for (Combat cb : cbList) {
									if (!cb.isDead()) {
										cb.setHide(true, tools.getParam1());
									}
								}
								break;
							case 7 :
								ServiceManager.getManager().getBattleTeamService()
										.updateAngryValue(battleId, combat.getId(), (int) (tools.getParam1() / 10f), false);
								break;
						}
					} else if (0 == tools.getType().intValue()) {
						float rate = combat.getHurtRate();
						switch (tools.getSubtype().intValue()) {
							case 0 :
								combat.setContinued(combat.getContinued() + tools.getParam1());
								combat.setPlayerAttackTimes(combat.getContinued() * combat.getScatter());
								rate *= tools.getParam2() / 1000f;
								break;
							case 1 :
								combat.setScatter(combat.getScatter() * tools.getParam1());
								combat.setPlayerAttackTimes(combat.getContinued() * combat.getScatter());
								rate *= tools.getParam2() / 1000f;
								break;
							case 2 :
								rate *= (1f + tools.getParam1() / 1000f);
								break;
						}
						combat.setHurtRate(rate);
					}
				}
				if (playerIds.size() > 0) {
					UpdateHP updateHP = new UpdateHP();
					updateHP.setBattleId(battleId);
					updateHP.setHurtcount(playerIds.size());
					updateHP.setPlayerIds(ServiceUtils.getInts(playerIds.toArray()));
					updateHP.setHp(ServiceUtils.getInts(hps.toArray()));
					updateHP.setAttackType(-1);
					for (Combat cb : battleTeam.getCombatList()) {
						if (!cb.isLost() && !cb.isRobot()) {
							cb.getPlayer().sendData(updateHP);
						}
					}
				}
				// 更新战斗道具使用的任务
				ServiceManager.getManager().getTaskService().battleToolsTask(combat, battleTeam, cbList.size(), toolsList);
				ServiceManager.getManager().getTitleService().battleToolsTask(combat, toolsList);
			} catch (Exception ex) {
				ex.printStackTrace();
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().skillEquip(skillEquip);
		}
	}
}
