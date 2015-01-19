package com.wyd.empire.world.server.handler.bossmapbattle;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherSkillEquip;
import com.wyd.empire.protocol.data.bossmapbattle.SkillEquip;
import com.wyd.empire.protocol.data.bossmapbattle.UpdateHP;
import com.wyd.empire.world.battle.BossBattleTeam;
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
		SkillEquip skillEquip = (SkillEquip) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
			int battleId = skillEquip.getBattleId();
			int playerOrGuai = skillEquip.getPlayerOrGuai();
			int currentId = skillEquip.getCurrentId();
			int itemcount = skillEquip.getItemcount();
			int[] itmeIds = skillEquip.getItmeIds();
			OtherSkillEquip otherSkillEquip = new OtherSkillEquip();
			otherSkillEquip.setBattleId(battleId);
			otherSkillEquip.setPlayerOrGuai(playerOrGuai);
			otherSkillEquip.setCurrentId(currentId);
			otherSkillEquip.setItemcount(itemcount);
			otherSkillEquip.setItmeIds(itmeIds);
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			battleTeam.setUseKill(true);
			if (playerOrGuai == 0) {
				Combat combat = battleTeam.getCombatMap().get(currentId);
				List<Combat> cbList = new ArrayList<Combat>();// 获取同阵营玩家
				List<Combat> gvList = new ArrayList<Combat>();// 获取同阵营怪物
				for (Combat cb : battleTeam.getCombatList()) {
					if (cb.getCamp() == combat.getCamp()) {
						cbList.add(cb);
					}
					if (!cb.isLost() && !cb.isRobot() && cb.getId() != worldPlayer.getId()) {
						cb.getPlayer().sendData(otherSkillEquip);
					}
				}
				for (Combat gv : battleTeam.getCombatGuaiList()) {
					if (gv.getCamp() == combat.getCamp()) {
						gvList.add(gv);
					}
				}
				List<Integer> playerIds = new ArrayList<Integer>();
				List<Integer> guaiIds = new ArrayList<Integer>();
				List<Integer> phps = new ArrayList<Integer>();
				List<Integer> ghps = new ArrayList<Integer>();
				List<Tools> toolsList = ServiceManager.getManager().getToolsService().getToolsListByIds(itmeIds);
				for (Tools tools : toolsList) {
					battleTeam.setUseTools(battleTeam.getUseTools() + tools.getId() + ",");
					ServiceManager.getManager().getLogSerivce().updatePropsSkillLog(tools);
					combat.setTiredValue(combat.getTiredValue() + tools.getTireValue());
					combat.setPf(combat.getPf() - tools.getConsumePower(), battleTeam.getMapId(), 6, worldPlayer);
					if (1 == tools.getType().intValue()) {
						switch (tools.getSubtype().intValue()) {
							case 0 :
								int hp = tools.getParam1();
								hp = hp > combat.getMaxHP() ? combat.getMaxHP() : hp;
								if (!combat.isGuaiPlayer()) {
									// 公会技能加成
									hp = (int) ServiceManager.getManager().getBuffService()
											.getAddition(combat.getPlayer(), hp, Buff.CTREAT);
									hp = (int) ServiceManager.getManager().getBuffService()
											.getAddition(combat.getPlayer(), hp, Buff.CADDHP);
								}
								hp += combat.getHp();
								hp = hp > combat.getMaxHP() ? combat.getMaxHP() : hp;
								combat.setHp(hp);
								playerIds.add(combat.getId());
								phps.add(combat.getHp());
								break;
							case 1 :
								for (Combat cb : cbList) {
									if (!cb.isDead()) {
										int chp = tools.getParam1();
										if (!combat.isGuaiPlayer()) {
											chp = (int) ServiceManager.getManager().getBuffService()
													.getAddition(combat.getPlayer(), chp, Buff.CTREAT);
											chp = (int) ServiceManager.getManager().getBuffService()
													.getAddition(combat.getPlayer(), chp, Buff.CADDHP);
										}
										chp += cb.getHp();
										chp = chp > cb.getMaxHP() ? cb.getMaxHP() : chp;
										cb.setHp(chp);
										playerIds.add(cb.getId());
										phps.add(cb.getHp());
									}
								}
								for (Combat gv : gvList) {
									if (!gv.isDead()) {
										gv.setHp(gv.getHp() + tools.getParam1());
										guaiIds.add(gv.getBattleId());
										ghps.add(gv.getHp());
									}
								}
								break;
							case 2 :
								// 副本用冰冻无效
								// combat.setHurtRate(0);
								// combat.setWillFrozen(tools.getParam1());
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
								for (Combat gv : gvList) {
									if (!gv.isDead()) {
										gv.setHide(true, tools.getParam1());
									}
								}
								break;
							case 7 :
								ServiceManager.getManager().getBossBattleTeamService()
										.updateAngryValue(battleId, 0, combat.getId(), (int) (tools.getParam1() / 10f), false);
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
					updateHP.setPlayercount(playerIds.size());
					updateHP.setPlayerIds(ServiceUtils.getInts(playerIds.toArray()));
					updateHP.setHp(ServiceUtils.getInts(phps.toArray()));
					updateHP.setGuaicount(guaiIds.size());
					updateHP.setGuaiBattleIds(ServiceUtils.getInts(guaiIds.toArray()));
					updateHP.setGuaiHp(ServiceUtils.getInts(ghps.toArray()));
					updateHP.setAttackType(-1);
					for (Combat cb : battleTeam.getCombatList()) {
						if (!cb.isLost() && !cb.isRobot()) {
							cb.getPlayer().sendData(updateHP);
						}
					}
				}
				// 副本任务没作
				// //更新战斗道具使用的任务
				// ServiceManager.getManager().getTaskService().battleToolsTask(combat,
				// battleTeam, cbList.size(), toolsList);
				ServiceManager.getManager().getTitleService().battleToolsTask(combat, toolsList);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
