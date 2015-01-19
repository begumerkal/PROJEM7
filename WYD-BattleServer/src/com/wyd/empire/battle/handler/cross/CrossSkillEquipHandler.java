package com.wyd.empire.battle.handler.cross;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.battle.util.ServiceUtils;
import com.wyd.empire.protocol.data.cross.CrossOtherSkillEquip;
import com.wyd.empire.protocol.data.cross.CrossSkillEquip;
import com.wyd.empire.protocol.data.cross.CrossUpdateHP;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 使用技能和道具
 * @author Administrator
 */
public class CrossSkillEquipHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossSkillEquipHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossSkillEquip skillEquip = (CrossSkillEquip) data;
        try {
            int battleId = skillEquip.getBattleId();
            int playerId = skillEquip.getPlayerId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            Combat combat = battleTeam.getCombatMap().get(playerId);
            if (null == combat) {
                return;
            }
            if (skillEquip.getCompleted()) {
                int itemcount = skillEquip.getItemcount();
                int[] itmeIds = skillEquip.getItmeIds();
                int tiredValue = skillEquip.getTiredValue();
                int consumePower = skillEquip.getConsumePower();
                int selfAddHP = skillEquip.getSelfAddHP();
                int[] ahps = skillEquip.getPlayerIds();
                int[] allAddHP = skillEquip.getAllAddHP();
                int[] toolsType = skillEquip.getToolsType();
                int[] toolsSubType = skillEquip.getToolsSubType();
                int[] toolsParam1 = skillEquip.getToolsParam1();
                int[] toolsParam2 = skillEquip.getToolsParam2();
                CrossOtherSkillEquip otherSkillEquip = new CrossOtherSkillEquip();
                otherSkillEquip.setBattleId(battleId);
                otherSkillEquip.setCurrentPlayerId(playerId);
                otherSkillEquip.setItemcount(itemcount);
                otherSkillEquip.setItmeIds(itmeIds);
                battleTeam.setUseKill(true);
                battleTeam.sendData(otherSkillEquip);
                List<Integer> playerIds = new ArrayList<Integer>();
                List<Integer> hps = new ArrayList<Integer>();
                if (tiredValue > 0) {
                    combat.setTiredValue(combat.getTiredValue() + tiredValue);
                }
                if (consumePower > 0) {
                    combat.setPf(combat.getPf() - consumePower);
                }
                if (selfAddHP > 0) {
                    int hp = combat.getHp() + selfAddHP;
                    hp = hp > combat.getPlayer().getMaxHP() ? combat.getPlayer().getMaxHP() : hp;
                    combat.setHp(hp);
                    playerIds.add(combat.getPlayer().getId());
                    hps.add(combat.getHp());
                }
                for (int i = 0; i < toolsType.length; i++) {
                    if (1 == toolsType[i]) {
                        switch (toolsSubType[i]) {
                        case 2:
                            combat.setHurtRate(0);
                            combat.setWillFrozen(toolsParam1[i]);
                            break;
                        case 3:
                            combat.setHurtRate(combat.getHurtRate() * (toolsParam1[i] / 1000f));
                            break;
                        case 4:
                            combat.setHide(true, toolsParam1[i]);
                            break;
                        case 5:
                            for (Combat cb : battleTeam.getCombatList()) {
                                if (!cb.isDead() && cb.getCamp() == combat.getCamp()) {
                                    cb.setHide(true, toolsParam1[i]);
                                }
                            }
                            break;
                        case 7:
                            ServiceManager.getManager().getBattleTeamService().updateAngryValue(battleId, combat.getId(), (int) (toolsParam1[i] / 10f), false);
                            break;
                        }
                    } else if (0 == toolsType[i]) {
                        float rate = combat.getHurtRate();
                        switch (toolsSubType[i]) {
                        case 0:
                            combat.setContinued(combat.getContinued() + toolsParam1[i]);
                            combat.setPlayerAttackTimes(combat.getContinued() * combat.getScatter());
                            rate *= toolsParam2[i]/ 1000f;
                            break;
                        case 1:
                            combat.setScatter(combat.getScatter() * toolsParam1[i]);
                            combat.setPlayerAttackTimes(combat.getContinued() * combat.getScatter());
                            rate *= toolsParam2[i]/ 1000f;
                            break;
                        case 2:
                            rate *= (1f + toolsParam1[i] / 1000f);
                            break;
                        }
                        combat.setHurtRate(rate);
                    }
                }
                if (ahps.length > 0) {
                    for (int i = 0; i < ahps.length; i++) {
                        int PID = ahps[i];
                        int AHP = allAddHP[i];
                        Combat cb = battleTeam.getCombatMap().get(PID);
                        int chp = cb.getHp() + AHP;
                        chp = chp > cb.getPlayer().getMaxHP() ? cb.getPlayer().getMaxHP() : chp;
                        cb.setHp(chp);
                        playerIds.add(cb.getPlayer().getId());
                        hps.add(cb.getHp());
                    }
                }
                if (playerIds.size() > 0) {
                    CrossUpdateHP updateHP = new CrossUpdateHP();
                    updateHP.setBattleId(battleId);
                    updateHP.setHurtcount(playerIds.size());
                    updateHP.setPlayerIds(ServiceUtils.getInts(playerIds.toArray()));
                    updateHP.setHp(ServiceUtils.getInts(hps.toArray()));
                    updateHP.setAttackType(-1);
                    battleTeam.sendData(updateHP);
                }
            } else {
                combat.getPlayer().sendData(skillEquip);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
