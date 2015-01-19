package com.wyd.empire.world.server.handler.bossmapbattle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.Hurt;
import com.wyd.empire.protocol.data.bossmapbattle.SomeOneDead;
import com.wyd.empire.protocol.data.bossmapbattle.UpdateHP;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.CombatChara;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发射完成
 * 
 * @author Administrator
 */
public class HurtHandler implements IDataHandler {
	Logger log = Logger.getLogger(HurtHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		Hurt hurt = (Hurt) data;
		try {
			IWorldBossService wroldBossService = ServiceManager.getManager().getWorldBossService();
			int battleId = hurt.getBattleId();
			int playerOrGuai = hurt.getPlayerOrGuai();
			int currentId = hurt.getCurrentId();
			int hurtcount = hurt.getHurtcount();
			int[] pIds = hurt.getPlayerIds();
			int[] hurtvalue = hurt.getHurtvalue();
			int guaihurtcount = hurt.getGuaihurtcount();
			int[] guaiBattleIds = hurt.getGuaiBattleIds();
			int[] guaiHurtValue = hurt.getGuaiHurtValue();
			List<Integer> playerIds = new ArrayList<Integer>();
			List<Integer> playerhps = new ArrayList<Integer>();
			List<Integer> guaiIds = new ArrayList<Integer>();
			List<Integer> guaihps = new ArrayList<Integer>();
			int hurtToBloodRate = hurt.getHurtToBloodRate(); // 受伤多少伤害转换为血量的比率(放大1万倍)
			int attackType = hurt.getAttackType();
			int hurtFloat = hurt.getHurtFloat();
			int attackIndex = hurt.getAttackIndex();
			boolean bossBeFrozen = hurt.getBossBeFrozen();
			List<Integer> deadPlayerIdList = new ArrayList<Integer>();
			List<Integer> deadGuaiIdList = new ArrayList<Integer>();
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam) {
				return;
			}
			if (0 == battleTeam.getFirstHurtPlayerId() && playerOrGuai == 0) {// 记录第一打中别人的角色
				battleTeam.setFirstHurtPlayerId(currentId);
			}
			int mapId = battleTeam.getMapId();
			if (playerOrGuai == 0) {
				Combat shootPlayer = battleTeam.getCombatMap().get(currentId);
				if (shootPlayer.checkShoot(attackType, battleTeam.getMapId(), 6, player)
						|| shootPlayer.verifyBossBeFrozen(bossBeFrozen, battleTeam.getMapId(), 6, player)) {
					return;
				}
				Combat combat;
				int playerIndex = -1;
				int guaiHurtTotal = 0;
				int playerHurtTotal = 0;
				// 玩家输出伤害，打中玩家
				for (int i = 0; i < hurtcount; i++) {
					if (hurtvalue[i] < 0) {
						continue;
					}
					combat = battleTeam.getCombatMap().get(pIds[i]);
					if (combat.isDead()) {
						playerIds.add(pIds[i]);
						playerhps.add(0);
						continue;
					}
					if (shootPlayer.verifyHurt(hurtvalue[i], attackType, combat, attackIndex, hurtFloat, hurtToBloodRate, hurtvalue,
							battleTeam.getSkillHurt(), battleTeam.getBattleRand(), false, battleTeam.getMapId(), 6, player)) {
						return;
					}
					int hp = combat.getHp() - hurtvalue[i];
					int hyrt = hurtvalue[i];
					if (hp <= 0) {
						hyrt = combat.getHp();
						if (currentId == pIds[i]) {
							hp = 1;
						} else {
							hp = 0;
							deadPlayerIdList.add(combat.getId());
							if (shootPlayer.getCamp() != combat.getCamp()) {
								shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
							}
							combat.setDead(true);
							combat.setBeKilledCount(combat.getBeKilledCount() + 1);
							combat.setBeKillRound(battleTeam.getRound());
							if (combat.isGuaiPlayer() && !shootPlayer.isGuaiPlayer()) {// 击杀怪物任务
								ServiceManager.getManager().getTaskService().killGuai(shootPlayer.getPlayer(), combat.getGuaiId());
								ServiceManager.getManager().getTitleService().killGuai(shootPlayer.getPlayer(), combat.getGuaiId());
							}
							if (0 == combat.getCamp()) {
								battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
							} else {
								battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
							}
						}
					}
					combat.setHp(hp);
					if (currentId != pIds[i]) {
						int av = (int) ((hurtvalue[i] / (float) shootPlayer.getAttack(mapId)) * 17);// 收到伤害后增加的怒气值
						if (av > 0) {
							ServiceManager.getManager().getBossBattleTeamService().updateAngryValue(battleId, 0, combat.getId(), av, false);
						}
						if (!shootPlayer.isHit()) {
							shootPlayer.setHit(true);
							shootPlayer.setHuntTimes(shootPlayer.getHuntTimes() + 1);
						}
						if (hurtToBloodRate > 0) {
							playerHurtTotal += hurtvalue[i];
						}
					} else {
						playerIndex = i;
					}
					playerIds.add(pIds[i]);
					playerhps.add(hp);
					if (shootPlayer.getCamp() != combat.getCamp()) {
						shootPlayer.setTotalHurt(shootPlayer.getTotalHurt() + hyrt);
						wroldBossService.checkHurt(mapId, shootPlayer);
					}
				}
				// 玩家输出伤害，打中怪物
				Combat guaiVo;
				for (int i = 0; i < guaihurtcount; i++) {
					if (guaiHurtValue[i] <= 0) {
						continue;
					}
					guaiVo = battleTeam.getGuaiMap().get(guaiBattleIds[i]);
					if (null == guaiVo || guaiVo.isDead()) {
						continue;
					}
					if (shootPlayer.verifyHurt(guaiHurtValue[i], attackType, guaiVo, attackIndex, hurtFloat, hurtToBloodRate, hurtvalue,
							battleTeam.getSkillHurt(), battleTeam.getBattleRand(), false, battleTeam.getMapId(), 6, player)) {
						return;
					}
					int hp = guaiVo.getHp() - guaiHurtValue[i];
					int hyrt = guaiHurtValue[i];
					if (hp <= 0) {
						hyrt = guaiVo.getHp();
						hp = 0;
						deadGuaiIdList.add(guaiVo.getBattleId());
						if (shootPlayer.getCamp() != guaiVo.getCamp()) {
							shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
						}
						guaiVo.setDead(true);
						guaiVo.setBeKilledCount(guaiVo.getBeKilledCount() + 1);
						guaiVo.setBeKillRound(battleTeam.getRound());
						// 击杀怪物任务
						ServiceManager.getManager().getTaskService()
								.killGuai(shootPlayer.getPlayer(), guaiVo.getGuai().getGuai().getGuaiId());
						ServiceManager.getManager().getTitleService()
								.killGuai(shootPlayer.getPlayer(), guaiVo.getGuai().getGuai().getGuaiId());
						if (0 == guaiVo.getCamp()) {
							battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
						} else {
							battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
						}
						battleTeam.getGuaiMap().remove(guaiBattleIds[i]);
						battleTeam.getCombatGuaiList().remove(guaiVo);
						checkGuai(battleTeam);
					}
					guaiIds.add(guaiBattleIds[i]);
					guaihps.add(hp);
					guaiVo.setHp(hp);
					if (currentId != guaiBattleIds[i]) {
						int av = (int) (guaiHurtValue[i] / (float) shootPlayer.getPlayer().getAttack() * 17);// 收到伤害后增加的怒气值
						ServiceManager.getManager().getBossBattleTeamService()
								.updateAngryValue(battleId, 1, guaiVo.getBattleId(), av, false);
						if (!shootPlayer.isHit()) {
							shootPlayer.setHit(true);
							shootPlayer.setHuntTimes(shootPlayer.getHuntTimes() + 1);
						}
					}
					if (guaiVo.getCamp() != shootPlayer.getCamp()) {
						shootPlayer.setTotalHurt(shootPlayer.getTotalHurt() + hyrt);
						wroldBossService.checkHurt(mapId, shootPlayer);
					}
					if (hurtToBloodRate > 0) {
						guaiHurtTotal += guaiHurtValue[i];
					}
				}
				if (hurtToBloodRate > 0) {
					shootPlayer.setHp(shootPlayer.getHp() + (guaiHurtTotal + playerHurtTotal) * hurtToBloodRate / 10000);
					if (shootPlayer.getHp() > shootPlayer.getPlayer().getMaxHP()) {
						shootPlayer.setHp(shootPlayer.getPlayer().getMaxHP());
					}
					if (playerIndex >= 0) {
						playerhps.set(playerIndex, shootPlayer.getHp());
					} else {
						playerIds.add(shootPlayer.getPlayer().getId());
						playerhps.add(shootPlayer.getHp());
					}
				}
			} else if (playerOrGuai == 1) {
				Combat shootPlayer = battleTeam.getGuaiMap().get(currentId);
				Combat combat;
				// 怪物输出伤害
				for (int i = 0; i < hurtcount; i++) {
					if (hurtvalue[i] <= 0) {
						continue;
					}
					combat = battleTeam.getCombatMap().get(pIds[i]);
					if (combat.isDead()) {
						playerIds.add(pIds[i]);
						playerhps.add(0);
						continue;
					}
					if (shootPlayer.verifyHurt(hurtvalue[i], attackType, combat, attackIndex, hurtFloat, hurtToBloodRate, hurtvalue,
							battleTeam.getSkillHurt(), battleTeam.getBattleRand(), false, battleTeam.getMapId(), 6, player)) {
						return;
					}
					int hp = combat.getHp() - hurtvalue[i];
					int hyrt = hurtvalue[i];
					if (hp <= 0) {
						hyrt = combat.getHp();
						if (currentId == pIds[i]) {
							hp = 1;
						} else {
							hp = 0;
							deadPlayerIdList.add(combat.getPlayer().getId());
							if (shootPlayer.getCamp() != combat.getCamp()) {
								shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
							}
							combat.setDead(true);
							combat.setBeKilledCount(combat.getBeKilledCount() + 1);
							combat.setBeKillRound(battleTeam.getRound());
							// ServiceManager.getManager().getTaskService().battleBeatTask(shootPlayer,
							// combat);
							if (0 == combat.getCamp()) {
								battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
							} else {
								battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
							}
						}
					}
					playerIds.add(pIds[i]);
					playerhps.add(hp);
					combat.setHp(hp);
					if (currentId != pIds[i]) {
						int av = (int) (hurtvalue[i] / (float) shootPlayer.getGuai().getAttack() * 17);// 收到伤害后增加的怒气值
						ServiceManager.getManager().getBossBattleTeamService()
								.updateAngryValue(battleId, 0, combat.getPlayer().getId(), av, false);
						if (!shootPlayer.isHit()) {
							shootPlayer.setHit(true);
							shootPlayer.setHuntTimes(shootPlayer.getHuntTimes() + 1);
						}
					}
					if (combat.getCamp() != shootPlayer.getCamp()) {
						shootPlayer.setTotalHurt(shootPlayer.getTotalHurt() + hyrt);
					}
				}
				// 怪物输出伤害
				Combat guaiVo;
				for (int i = 0; i < guaihurtcount; i++) {
					if (guaiHurtValue[i] <= 0) {
						continue;
					}
					guaiVo = battleTeam.getGuaiMap().get(guaiBattleIds[i]);
					if (guaiVo.isDead()) {
						continue;
					}
					if (shootPlayer.verifyHurt(guaiHurtValue[i], attackType, guaiVo, attackIndex, hurtFloat, hurtToBloodRate, hurtvalue,
							battleTeam.getSkillHurt(), battleTeam.getBattleRand(), false, battleTeam.getMapId(), 6, player)) {
						return;
					}
					int hp = guaiVo.getHp() - guaiHurtValue[i];
					int hyrt = guaiHurtValue[i];
					if (hp <= 0) {
						hyrt = guaiVo.getHp();
						hp = 0;
						deadGuaiIdList.add(guaiVo.getBattleId());
						if (shootPlayer.getCamp() != guaiVo.getCamp()) {
							shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
						}
						guaiVo.setDead(true);
						guaiVo.setBeKilledCount(guaiVo.getBeKilledCount() + 1);
						guaiVo.setBeKillRound(battleTeam.getRound());
						if (0 == guaiVo.getCamp()) {
							battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
						} else {
							battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
						}
						battleTeam.getGuaiMap().remove(guaiBattleIds[i]);
						battleTeam.getCombatGuaiList().remove(guaiVo);
						checkGuai(battleTeam);
					}
					guaiIds.add(guaiBattleIds[i]);
					guaihps.add(hp);
					guaiVo.setHp(hp);
					if (currentId != guaiBattleIds[i]) {
						int av = (int) (guaiHurtValue[i] / (float) shootPlayer.getGuai().getAttack() * 17);// 收到伤害后增加的怒气值
						ServiceManager.getManager().getBossBattleTeamService()
								.updateAngryValue(battleId, 1, guaiVo.getBattleId(), av, false);
						if (!shootPlayer.isHit()) {
							shootPlayer.setHit(true);
							shootPlayer.setHuntTimes(shootPlayer.getHuntTimes() + 1);
						}
					}
					if (guaiVo.getCamp() != shootPlayer.getCamp()) {
						shootPlayer.setTotalHurt(shootPlayer.getTotalHurt() + hyrt);
					}
				}
			}
			UpdateHP updateHP = new UpdateHP();
			updateHP.setBattleId(battleId);
			updateHP.setPlayercount(playerIds.size());
			updateHP.setPlayerIds(ServiceUtils.getInts(playerIds.toArray()));
			updateHP.setHp(ServiceUtils.getInts(playerhps.toArray()));
			updateHP.setGuaicount(guaiIds.size());
			updateHP.setGuaiBattleIds(ServiceUtils.getInts(guaiIds.toArray()));
			updateHP.setGuaiHp(ServiceUtils.getInts(guaihps.toArray()));
			updateHP.setAttackType(attackType);
			SomeOneDead someOneDead = null;
			if (deadPlayerIdList.size() > 0 || deadGuaiIdList.size() > 0) {
				someOneDead = new SomeOneDead();
				someOneDead.setBattleId(battleId);
				someOneDead.setDeadPlayerCount(deadPlayerIdList.size());
				someOneDead.setPlayerIds(ArrayUtils.toPrimitive(deadPlayerIdList.toArray(new Integer[0])));
				someOneDead.setDeadGuaiCount(deadGuaiIdList.size());
				someOneDead.setGuaiBattleIds(ArrayUtils.toPrimitive(deadGuaiIdList.toArray(new Integer[0])));
			}
			for (Combat cb : battleTeam.getTrueCombatList()) {
				if (!cb.isLost() && !cb.isRobot()) {
					cb.getPlayer().sendData(updateHP);
					if (null != someOneDead) {
						cb.getPlayer().sendData(someOneDead);
					}
				}
			}
			ServiceManager.getManager().getBossBattleTeamService().gameOver(battleId);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
		}
	}

	@SuppressWarnings("static-access")
	private void checkGuai(BossBattleTeam battleTeam) {
		boolean check = true;
		Combat combatGuai;
		for (int index = 0; index < battleTeam.getCombatGuaiList().size(); index++) {
			combatGuai = battleTeam.getCombatGuaiList().get(index);
			if (combatGuai.getGuai().getGuai().getAttack_type().intValue() == combatGuai.GUAI_ATTACK_TYPE_MELEE
					&& combatGuai.getCamp() == 1) {
				check = false;
				break;
			}
		}
		if (check) {
			for (int index = 0; index < battleTeam.getCurRoundSequence().size(); index++) {
				if (CombatChara.CHARA_TYPE_ENERMY_MELEE == battleTeam.getCurRoundSequence().get(index).intValue()) {
					battleTeam.getCurRoundSequence().remove(index);
					break;
				}
			}
		}
		check = true;
		for (int index = 0; index < battleTeam.getCombatGuaiList().size(); index++) {
			combatGuai = battleTeam.getCombatGuaiList().get(index);
			if (combatGuai.getGuai().getGuai().getAttack_type().intValue() == combatGuai.GUAI_ATTACK_TYPE_MELEE
					&& combatGuai.getCamp() == 0) {
				check = false;
				break;
			}
		}
		if (check) {
			for (int index = 0; index < battleTeam.getCurRoundSequence().size(); index++) {
				if (CombatChara.CHARA_TYPE_MINE_MELEE == battleTeam.getCurRoundSequence().get(index).intValue()) {
					battleTeam.getCurRoundSequence().remove(index);
					break;
				}
			}
		}
	}
}
