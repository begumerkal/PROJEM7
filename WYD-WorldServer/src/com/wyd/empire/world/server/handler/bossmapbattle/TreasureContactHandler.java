package com.wyd.empire.world.server.handler.bossmapbattle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherTreasureContact;
import com.wyd.empire.protocol.data.bossmapbattle.TreasureContact;
import com.wyd.empire.protocol.data.bossmapbattle.UpdateHP;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.BossmapBuff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 与宝箱接触
 * 
 * @author zengxc
 */
public class TreasureContactHandler implements IDataHandler {
	Logger log = Logger.getLogger(TreasureContactHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	BossBattleTeamService bossBattleTeamService = null;
	int maxCount = 2; // 最大接触两个宝箱（防作弊）

	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			TreasureContact treasureContact = (TreasureContact) data;
			int battleId = treasureContact.getBattleId();
			int[] buffIds = treasureContact.getItemId();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int currentId = treasureContact.getCurrentId();
			bossBattleTeamService = manager.getBossBattleTeamService();
			BossBattleTeam bossBattleTeam = bossBattleTeamService.getBattleTeam(battleId);
			if (bossBattleTeam == null) {
				log.info("bossBattleTeam is null");
				return;
			}
			Vector<Combat> combatList = bossBattleTeam.getCombatList();
			Vector<Combat> combatGuaiList = bossBattleTeam.getCombatGuaiList();
			if (treasureContact.getPlayerOrGuai() == 0) {
				Combat shootPlayer = bossBattleTeam.getCombatMap().get(currentId);
				int buffCount = buffIds.length;
				buffCount = buffCount > maxCount ? maxCount : buffCount;
				for (int i = 0; i < buffCount; i++) {
					if (buffIds[i] > 0) {// id大于0为世界BOSS BUFF
						if (!bossBattleTeam.getBuffSet().contains(buffIds[i])) {
							shootPlayer.killLine(player.getId());
							GameLogService.battleCheat(player.getId(), player.getLevel(), bossBattleTeam.getMapId(), 6, 0, 6, "");
							return;
						}
						BossmapBuff buff = getBuff(buffIds[i]);
						bossBattleTeam.getBuffSet().remove(buffIds[i]);

						switch (buff.getType()) {
							case 0 :// 攻击
								shootPlayer.setHurtRate(shootPlayer.getHurtRate() * (1 + buff.getEffect1() / 100f));
								break;
							case 1 :// 加血
								addHP(battleId, shootPlayer, combatList, combatGuaiList, buff.getEffect1());
								break;
							case 2 :// 无敌 本回合
								shootPlayer.setInvincible(1);
								break;
							case 3 :// 加金币
								addGold(player, buff.getEffect1());
								break;
							case 4 :// 冰冻
								shootPlayer.setWorldBossFrozen(true);
								break;
							case 5 :// 加金币
								addAngry(battleId, shootPlayer, buff.getEffect1());
								break;
							case 6 :// 星魂碎片
								addStarSoul(player, buff.getEffect1());
								break;
						}

					} else {// id不大于0为副本的 BUFF
						switch (buffIds[i]) {
							case -1 :// 群体无敌 下一回合
								if (bossBattleTeam.isCanInvincible()) {
									allInvincible(shootPlayer, 2, bossBattleTeam);
								} else {
									shootPlayer.killLine(player.getId());
									GameLogService.battleCheat(player.getId(), player.getLevel(), bossBattleTeam.getMapId(), 6, 0, 7, "");
									return;
								}
								break;
						}
					}
				}
				// 通知同一队列里的其它玩家
				OtherTreasureContact other = getOtherTreasureContact(treasureContact);
				for (Combat combat : combatList) {
					combat.getPlayer().sendData(other);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}

	private void addHP(int battleId, Combat combatPlayer, List<Combat> combatList, List<Combat> combatGuaiList, int val) {
		List<Integer> playerIds = new ArrayList<Integer>();
		List<Integer> playerhps = new ArrayList<Integer>();
		List<Combat> gvList = new ArrayList<Combat>();// 获取同阵营怪物
		List<Integer> guaiIds = new ArrayList<Integer>();
		List<Integer> ghps = new ArrayList<Integer>();
		for (Combat combat : combatList) {
			playerIds.add(combat.getId());
			if (combat.getId() == combatPlayer.getId()) {
				// 加血的玩家
				int maxHP = combat.getMaxHP();
				int newHP = combat.getHp() + val;
				combat.setHp(newHP > maxHP ? maxHP : newHP);
			}
			playerhps.add(combat.getHp());
		}
		for (Combat combatGuai : combatGuaiList) {
			if (combatGuai.getCamp() == combatPlayer.getCamp()) {
				gvList.add(combatGuai);
			}
		}
		for (Combat combatGuai : gvList) {
			if (!combatGuai.isDead()) {
				guaiIds.add(combatGuai.getBattleId());
				ghps.add(combatGuai.getHp());
			}
		}
		UpdateHP updateHP = new UpdateHP();
		updateHP.setBattleId(battleId);
		updateHP.setPlayercount(playerIds.size());
		updateHP.setPlayerIds(ServiceUtils.getInts(playerIds.toArray()));
		updateHP.setHp(ServiceUtils.getInts(playerhps.toArray()));
		updateHP.setGuaicount(guaiIds.size());
		updateHP.setGuaiBattleIds(ServiceUtils.getInts(guaiIds.toArray()));
		updateHP.setGuaiHp(ServiceUtils.getInts(ghps.toArray()));
		updateHP.setAttackType(-1);
		for (Combat cb : combatList) {
			if (!cb.isLost() && !cb.isRobot()) {
				cb.getPlayer().sendData(updateHP);
			}
		}
	}

	/**
	 * 副本群体无敌
	 * 
	 * @param shootPlayer
	 *            操作角色
	 * @param round
	 *            无敌回合数
	 * @param bossBattleTeam
	 *            战斗组对象
	 */
	private void allInvincible(Combat shootPlayer, int round, BossBattleTeam bossBattleTeam) {
		for (Combat combat : bossBattleTeam.getCombatList()) {
			if (combat.getCamp() == shootPlayer.getCamp()) {
				combat.setInvincible(round);
			}
		}
		for (Combat combat : bossBattleTeam.getCombatGuaiList()) {
			if (combat.getCamp() == shootPlayer.getCamp()) {
				combat.setInvincible(round);
			}
		}
	}

	private void addAngry(int battleId, Combat combat, int val) {
		ServiceManager.getManager().getBossBattleTeamService().updateAngryValue(battleId, 0, combat.getId(), val, false);
	}

	private void addStarSoul(WorldPlayer worldPlayer, int val) {
		ServiceManager.getManager().getPlayerItemsFromShopService()
				.playerGetItem(worldPlayer.getId(), Common.STARSOULDEBRISID, val, 25, "", 0, 0, 0);
	}

	private void addGold(WorldPlayer worldPlayer, int val) {
		try {
			ServiceManager.getManager().getPlayerService().updatePlayerGold(worldPlayer, val, "世界BOSS－Buff", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BossmapBuff getBuff(int id) {
		return (BossmapBuff) ServiceManager.getManager().getBossmapBuffService().getBossmapBuffById(id);
	}

	private OtherTreasureContact getOtherTreasureContact(TreasureContact treasureContact) {
		OtherTreasureContact other = new OtherTreasureContact();
		other.setBattleId(treasureContact.getBattleId());
		other.setCurrentId(treasureContact.getCurrentId());
		other.setItemCount(treasureContact.getItemCount());
		other.setItemId(treasureContact.getItemId());
		other.setPlayerOrGuai(treasureContact.getPlayerOrGuai());
		return other;
	}
}
