package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossOutOfScene;
import com.wyd.empire.protocol.data.cross.CrossSomeOneDead;
import com.wyd.empire.protocol.data.cross.CrossUpdateHP;
import com.wyd.empire.protocol.data.cross.CrossUpdateMedal;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 角色掉出地图外
 * @author Administrator
 */
public class CrossOutOfSceneHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOutOfSceneHandler.class);
	public void handle(AbstractData data) throws Exception {
        CrossOutOfScene outOfScene = (CrossOutOfScene) data;
        try {
            int battleId = outOfScene.getBattleId();
            int playerId = outOfScene.getPlayerId();
            int currentPlayerId = outOfScene.getCurrentPlayerId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            Combat hurtPlayer = battleTeam.getCombatMap().get(playerId);
            if (hurtPlayer.isDead()) {
                return;
            }
            boolean firstBlood = false;
            hurtPlayer.setHp(0);
            hurtPlayer.setDead(true);
            hurtPlayer.setBeKilledCount(hurtPlayer.getBeKilledCount() + 1);
            hurtPlayer.setBeKillRound(battleTeam.getRound());
            Combat shootPlayer = battleTeam.getCombatMap().get(currentPlayerId);
            // 判断玩家杀死的是否是队友
            if (shootPlayer.getCamp() != hurtPlayer.getCamp()) {
                shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
                if (0 == battleTeam.getFirstHurtPlayerId()) {// 首杀
                    battleTeam.setFirstHurtPlayerId(playerId);
                    firstBlood = true;
                }
            }
            if (0 == hurtPlayer.getCamp()) {
                battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
            } else {
                battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
            }
            // 玩家自杀
            if (currentPlayerId == playerId) {
                hurtPlayer.setSuicide(true);
            }
            CrossUpdateHP updateHP = new CrossUpdateHP();
            updateHP.setBattleId(battleId);
            updateHP.setHurtcount(1);
            updateHP.setPlayerIds(new int[] { playerId});
            updateHP.setHp(new int[] { 0});
            updateHP.setAttackType(0);
            CrossSomeOneDead someOneDead = new CrossSomeOneDead();
            someOneDead.setBattleId(battleId);
            someOneDead.setDeadPlayerCount(1);
            someOneDead.setPlayerIds(new int[] { playerId});
            someOneDead.setPlayerId(shootPlayer.getId());
            someOneDead.setShootSex(shootPlayer.getSex());
            someOneDead.setShootCamp(shootPlayer.getCamp());
            someOneDead.setDeadSex(new int[] { hurtPlayer.getSex()});
            someOneDead.setDeadCamp(new int[] { hurtPlayer.getCamp()});
            someOneDead.setKillType(1);
            someOneDead.setFirstBlood(firstBlood);
            CrossUpdateMedal updateMedal = new CrossUpdateMedal();
            updateMedal.setBattleId(battleId);
            updateMedal.setCampCount(2);
            updateMedal.setCampId(new int[] { 0, 1});
            updateMedal.setCampMedalNum(new int[] { battleTeam.getCamp1BeKillCount(), battleTeam.getCamp0BeKillCount()});
            battleTeam.sendData(updateHP);
            if (null != someOneDead) {
                battleTeam.sendData(someOneDead);
                battleTeam.sendData(updateMedal);
            }
            ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, shootPlayer.getCamp());
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
