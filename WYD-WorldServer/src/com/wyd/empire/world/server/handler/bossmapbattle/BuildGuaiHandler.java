package com.wyd.empire.world.server.handler.bossmapbattle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.BossOtherMapBuildGuai;
import com.wyd.empire.protocol.data.bossmapbattle.BuildGuai;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.CombatChara;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 生成怪
 * 
 * @author Administrator
 */
public class BuildGuaiHandler implements IDataHandler {
	Logger log = Logger.getLogger(BuildGuaiHandler.class);

	public void handle(AbstractData data) throws Exception {
		BuildGuai buildGuai = (BuildGuai) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = buildGuai.getBattleId();
			int playerOrGuai = buildGuai.getPlayerOrGuai();
			int currentId = buildGuai.getCurrentId();
			int guaiCount = buildGuai.getGuaiCount();
			int[] guaiBattleId = buildGuai.getGuaiBattleId();
			int[] guaiId = buildGuai.getGuaiId();
			int[] guaiPositionX = buildGuai.getGuaiPositionX();
			int[] guaiPositionY = buildGuai.getGuaiPositionY();
			BossBattleTeam bossBattleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (playerOrGuai == 1) {
				Combat guaiVoMother = bossBattleTeam.getGuaiMap().get(currentId);
				GuaiPlayer guai;
				IGuaiService iGuaiSevice = ServiceManager.getManager().getGuaiService();
				Combat combatGuai;
				boolean neadAddMelee0 = true;
				boolean neadAddMelee1 = true;
				// 可能类型已经在,可以不需要增加了
				for (Integer i : bossBattleTeam.getCurRoundSequence()) {
					if (i.intValue() == CombatChara.CHARA_TYPE_ENERMY_MELEE) {
						neadAddMelee1 = false;
					} else if (i.intValue() == CombatChara.CHARA_TYPE_MINE_MELEE) {
						neadAddMelee0 = false;
					}
					if (!neadAddMelee0 && !neadAddMelee1)
						break;
				}
				List<Integer> curAddList = new ArrayList<Integer>();
				int i = 0;
				for (int gId : guaiId) {
					combatGuai = new Combat();
					guai = iGuaiSevice.getGuaiById(bossBattleTeam.getDifficulty(), gId);
					combatGuai.setBattleId(guaiBattleId[i]);
					combatGuai.setGuai(guai);
					combatGuai.setCouldBuildGuai(false);
					combatGuai.setCamp(guaiVoMother.getCamp());
					combatGuai.setX(guaiPositionX[i]);
					combatGuai.setY(guaiPositionY[i]);
					combatGuai.setHp(guai.getMaxHP());
					combatGuai.setPf(guai.getMaxPF(), bossBattleTeam.getMapId(), 6, player);
					if (guai.getGuai().getAttack_type() == Combat.GUAI_ATTACK_TYPE_MELEE) {
						if (combatGuai.getCamp() == 0 && neadAddMelee0) {
							curAddList.add(CombatChara.CHARA_TYPE_MINE_MELEE);
							neadAddMelee0 = false;
						} else if (combatGuai.getCamp() == 1 && neadAddMelee1) {
							curAddList.add(CombatChara.CHARA_TYPE_ENERMY_MELEE);
							neadAddMelee1 = false;
						}
					} else if (guai.getGuai().getAttack_type() != Combat.GUAI_ATTACK_TYPE_NONE) {
						curAddList.add(combatGuai.getBattleId());
					}
					bossBattleTeam.enterGuai(combatGuai);
					i++;
				}
				if (curAddList.size() > 0) {
					Vector<Integer> newCurSequence = new Vector<Integer>();
					// 找到最后一个怪
					int lastGuaiInlist = -1;
					int lastGuaiId = 0;
					for (int curLast = bossBattleTeam.getCurRoundSequence().size() - 1; curLast >= 0; curLast--) {
						Integer curId = bossBattleTeam.getCurRoundSequence().get(curLast);
						if (curId.intValue() < 0) {
							lastGuaiInlist = curLast;
							lastGuaiId = curId.intValue();
							break;
						}
					}
					if (lastGuaiInlist == -1) {// 没有怪
						for (Integer curId0 : curAddList) {
							newCurSequence.add(curId0);
						}
						for (Integer curId1 : bossBattleTeam.getCurRoundSequence()) {
							newCurSequence.add(curId1);
						}
					} else {
						for (Integer curId1 : bossBattleTeam.getCurRoundSequence()) {
							if (curId1.intValue() != lastGuaiId) {
								newCurSequence.add(curId1);
							} else {
								newCurSequence.add(curId1);
								// 在怪的最后加上新的怪
								for (Integer curId0 : curAddList) {
									newCurSequence.add(curId0);
								}
							}
						}
					}
					// 重新找回当前角色的索引的位置
					int curIndexBattleId = bossBattleTeam.getCurRoundSequence().get(bossBattleTeam.getActionIndex()).intValue();
					int index = 0;
					for (Integer curId : newCurSequence) {
						if (curId.intValue() == curIndexBattleId) {
							break;
						}
						index++;
					}
					bossBattleTeam.setCurRoundSequence(newCurSequence);
					bossBattleTeam.setActionIndex(index);
				}
			} else {
				throw new Exception("客户端发送的数据不对,只有怪才能招唤怪");
			}
			BossOtherMapBuildGuai bossOtherMapBuildGuai = new BossOtherMapBuildGuai();
			bossOtherMapBuildGuai.setBattleId(battleId);
			bossOtherMapBuildGuai.setPlayerOrGuai(playerOrGuai);
			bossOtherMapBuildGuai.setCurrentId(currentId);
			bossOtherMapBuildGuai.setGuaiCount(guaiCount);
			bossOtherMapBuildGuai.setGuaiBattleId(guaiBattleId);
			bossOtherMapBuildGuai.setGuaiId(guaiId);
			bossOtherMapBuildGuai.setGuaiPositionX(guaiPositionX);
			bossOtherMapBuildGuai.setGuaiPositionY(guaiPositionY);
			for (Combat combat : bossBattleTeam.getTrueCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(bossOtherMapBuildGuai);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
