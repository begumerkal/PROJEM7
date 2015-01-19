package com.wyd.empire.battle.handler.cross;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.bean.CombatComparator;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.battle.util.ServiceUtils;
import com.wyd.empire.protocol.data.cross.CrossFrozenOver;
import com.wyd.empire.protocol.data.cross.CrossHurt;
import com.wyd.empire.protocol.data.cross.CrossSomeOneDead;
import com.wyd.empire.protocol.data.cross.CrossUpdateHP;
import com.wyd.empire.protocol.data.cross.CrossUpdateMedal;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 发射完成
 * @author Administrator
 */
public class CrossHurtHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossHurtHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossHurt hurt = (CrossHurt) data;
        try {
            int battleId = hurt.getBattleId();
            int playerId = hurt.getPlayerId();
            int hurtcount = hurt.getHurtcount();
            int[] pIds = hurt.getPlayerIds();
            int[] hurtvalue = hurt.getHurtvalue();
            int[] distance = hurt.getDistance();
            int[] targetRandom = hurt.getTargetRandom(); // 被攻击目标武器技能触发使用随机数下标
            int[] attackerRandom = hurt.getAttackerRandom(); // 攻击者武器技能触发使用随机数下标
            int hurtToBloodRate = hurt.getHurtToBloodRate();
            int attackType = hurt.getAttackType();
            int hurtFloat = hurt.getHurtFloat();
            List<Integer> playerIds = new ArrayList<Integer>();
            List<Integer> hps = new ArrayList<Integer>();
            List<Integer> deadPlayerIdList = new ArrayList<Integer>();
            List<Integer> deadSex = new ArrayList<Integer>();
            List<Integer> deadCamp = new ArrayList<Integer>();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            Combat shootPlayer = battleTeam.getCombatMap().get(playerId);
            if (null == shootPlayer) {
                return;
            }
            if (shootPlayer.checkShoot(attackType)) {
                return;
            }
            int playerIndex = -1;
            int playerHurtTotal = 0;
            List<Integer> frozeIdList = new ArrayList<Integer>();
            boolean firstBlood = false;
            for (int i = 0; i < hurtcount; i++) {
                if (hurtvalue[i] < 0) {
                    continue;
                }
                Combat combat = battleTeam.getCombatMap().get(pIds[i]);
                if (null == combat || combat.isDead()) {
                    playerIds.add(pIds[i]);
                    hps.add(0);
                    continue;
                }
                if (shootPlayer.verifyHurt(hurtvalue[i], attackType, combat, distance[i], hurtFloat, battleTeam.getCritValue(), hurtToBloodRate, attackerRandom, new int[]{targetRandom[2*i], targetRandom[2*i+1]}, battleTeam.getBattleRand())) {
                    return;
                }
                int hyrt = hurtvalue[i];
                int hp = combat.getHp() - hyrt;
                if (combat.getFrozenTimes() > 0 && shootPlayer.getWillFrozen() == 0) {
                    combat.setFrozenTimes(0);
                    frozeIdList.add(combat.getId());
                }
                if (hp <= 0) {
                    hyrt = combat.getHp();
                    if (playerId == pIds[i]) {
                        hp = 1;
                    } else {
                        hp = 0;
                        deadPlayerIdList.add(combat.getPlayer().getId());
                        if (!shootPlayer.isRobot()) {
                            deadSex.add(combat.getSex());
                            deadCamp.add(combat.getCamp());
                        }
                        if (shootPlayer.getCamp() != combat.getCamp()) {
                            shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
                            if (0 == battleTeam.getFirstHurtPlayerId()) {// 首杀
                                battleTeam.setFirstHurtPlayerId(playerId);
                                firstBlood = true;
                            }
                        }
                        combat.setDead(true);
                        combat.setBeKilledCount(combat.getBeKilledCount() + 1);
                        combat.setBeKillRound(battleTeam.getRound());
                        if (0 == combat.getCamp()) {
                            battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
                        } else {
                            battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
                        }
                    }
                }
                combat.setHp(hp);
                if (playerId != pIds[i]) {
                    int av = (int) ((hurtvalue[i] / (float) shootPlayer.getPlayer().getAttack()) * 17);// 收到伤害后增加的怒气值
                    ServiceManager.getManager().getBattleTeamService().updateAngryValue(battleId, combat.getPlayer().getId(), av, false);
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
                hps.add(hp);
                shootPlayer.setTotalHurt(shootPlayer.getTotalHurt() + hyrt);
            }
            if (hurtToBloodRate > 0) {
                shootPlayer.setHp(shootPlayer.getHp() + playerHurtTotal * hurtToBloodRate / 10000);
                if (shootPlayer.getHp() > shootPlayer.getPlayer().getMaxHP()) {
                    shootPlayer.setHp(shootPlayer.getPlayer().getMaxHP());
                }
                if (playerIndex >= 0) {
                    hps.set(playerIndex, shootPlayer.getHp());
                } else {
                    playerIds.add(shootPlayer.getPlayer().getId());
                    hps.add(shootPlayer.getHp());
                }
            }
            CrossUpdateHP updateHP = new CrossUpdateHP();
            updateHP.setBattleId(battleId);
            updateHP.setHurtcount(playerIds.size());
            updateHP.setPlayerIds(ServiceUtils.getInts(playerIds.toArray()));
            updateHP.setHp(ServiceUtils.getInts(hps.toArray()));
            updateHP.setAttackType(hurt.getAttackType());
            CrossSomeOneDead someOneDead = null;
            if (deadPlayerIdList.size() > 0) {
                someOneDead = new CrossSomeOneDead();
                someOneDead.setBattleId(battleId);
                someOneDead.setDeadPlayerCount(deadPlayerIdList.size());
                someOneDead.setPlayerIds(ArrayUtils.toPrimitive(deadPlayerIdList.toArray(new Integer[0])));
                someOneDead.setPlayerId(shootPlayer.getId());
                someOneDead.setShootSex(shootPlayer.getSex());
                someOneDead.setShootCamp(shootPlayer.getCamp());
                someOneDead.setDeadSex(ArrayUtils.toPrimitive(deadSex.toArray(new Integer[0])));
                someOneDead.setDeadCamp(ArrayUtils.toPrimitive(deadCamp.toArray(new Integer[0])));
                someOneDead.setFirstBlood(firstBlood);
            }
            CrossUpdateMedal updateMedal = null;
            if (null != someOneDead) {
                updateMedal = new CrossUpdateMedal();
                updateMedal.setBattleId(battleId);
                updateMedal.setCampCount(2);
                updateMedal.setCampId(new int[] { 0, 1});
                updateMedal.setCampMedalNum(new int[] { battleTeam.getCamp1BeKillCount(), battleTeam.getCamp0BeKillCount()});
            }
            CrossFrozenOver frozenOver = null;
            if (frozeIdList.size() > 0) {
                frozenOver = new CrossFrozenOver();
                frozenOver.setPlayerIds(ServiceUtils.getInts(frozeIdList.toArray()));
            }
            battleTeam.sendData(updateHP);
            if (null != someOneDead) {
                battleTeam.sendData(someOneDead);
                battleTeam.sendData(updateMedal);
            }
            if (frozeIdList.size() > 0) {
                battleTeam.sendData(frozenOver);
            }
            if (!ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, shootPlayer.getCamp()) && frozeIdList.size() > 0) {
                Vector<Combat> combatList = new Vector<Combat>();
                List<Combat> hasRunList = new ArrayList<Combat>();
                List<Combat> notRunList = new ArrayList<Combat>();
                int i = 0;
                for (Combat combat : battleTeam.getCombatList()) {
                    if (i < battleTeam.getActionIndex()) {
                        hasRunList.add(combat);
                    } else {
                        notRunList.add(combat);
                    }
                    i++;
                }
                Comparator<Combat> ascComparator = new CombatComparator();
                Collections.sort(notRunList, ascComparator);
                combatList.addAll(hasRunList);
                combatList.addAll(notRunList);
                Vector<Integer> palyerIdList = new Vector<Integer>();
                for (Combat combat : combatList) {
                    palyerIdList.add(combat.getId());
                }
                battleTeam.setPlayerIds(palyerIdList);
                battleTeam.setCombatList(combatList);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
