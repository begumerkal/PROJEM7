package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossBigSkill;
import com.wyd.empire.protocol.data.cross.CrossOtherBigSkill;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 使用大招
 * @author Administrator
 */
public class CrossBigSkillHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossBigSkillHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossBigSkill bigSkill = (CrossBigSkill) data;
        try {
            int battleId = bigSkill.getBattleId();
            int playerId = bigSkill.getPlayerId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            Combat shootPlayer = battleTeam.getCombatMap().get(playerId);
            if (!shootPlayer.isRobot() && !shootPlayer.isLost() && shootPlayer.getAngryValue() < 100) {
                shootPlayer.killLine();
            }
            float attPercent = 0;
            switch (shootPlayer.getBigSkillType()) {
            case 0:
                attPercent = 0.3f;
                shootPlayer.setPlayerAttackTimes(7);
                break;
            case 1:
                attPercent = 2.5f;
                shootPlayer.setPlayerAttackTimes(1);
                break;
            case 2:
                attPercent = 0.4f;
                shootPlayer.setPlayerAttackTimes(7);
                break;
            }
            shootPlayer.setHurtRate(attPercent);
            shootPlayer.setWillFrozen(0);
            battleTeam.setUseBigKill(true);
            CrossOtherBigSkill bigSkillAvoid = new CrossOtherBigSkill();
            bigSkillAvoid.setBattleId(battleId);
            bigSkillAvoid.setCurrentPlayerId(playerId);
            battleTeam.sendData(bigSkillAvoid);
            // 重置怒气值
            ServiceManager.getManager().getBattleTeamService().updateAngryValue(battleId, playerId, 0, true);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
