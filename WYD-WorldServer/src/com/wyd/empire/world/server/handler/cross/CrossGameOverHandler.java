package com.wyd.empire.world.server.handler.cross;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.battle.GameOver;
import com.wyd.empire.protocol.data.cross.CrossGameOver;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.IPlayerSinConsortiaService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.DailyActivityService;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.server.service.impl.SystemLogService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跨服对战结束
 * 
 * @author zguoqiu
 */
public class CrossGameOverHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossGameOverHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossGameOver crossGameOver = (CrossGameOver) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(crossGameOver.getRoomId());
			if (null != room) {
				synchronized (room) {
					PlayerService playerService = ServiceManager.getManager().getPlayerService();
					IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
					IPlayerSinConsortiaService playerSinConsortiaService = ServiceManager.getManager().getPlayerSinConsortiaService();
					int battleId = crossGameOver.getBattleId();
					int firstHurtPlayerId = crossGameOver.getFirstHurtPlayerId();
					int winCamp = crossGameOver.getWinCamp();
					int times0 = crossGameOver.getTimes0();
					int times1 = crossGameOver.getTimes1();
					int round = crossGameOver.getRound();
					int playerCount = crossGameOver.getPlayerCount();
					int playerNumMode = crossGameOver.getPlayerNumMode();
					int[] playerIds = crossGameOver.getPlayerIds();
					int[] shootRate = crossGameOver.getShootRate();
					int[] totalHurt = crossGameOver.getTotalHurt();
					int[] killCount = crossGameOver.getKillCount();
					int[] beKilledCount = crossGameOver.getBeKilledCount();
					int[] huntTimes = crossGameOver.getHuntTimes();
					int[] hps = crossGameOver.getHp();
					int[] pLevel = crossGameOver.getpLevel();
					int[] actionTimes = crossGameOver.getActionTimes();
					int[] beKillRound = crossGameOver.getBeKillRound();
					boolean[] isEnforceQuit = crossGameOver.getIsEnforceQuit();
					boolean[] isLost = crossGameOver.getIsLost();
					boolean[] isSuicide = crossGameOver.getIsSuicide();
					boolean[] isWin = crossGameOver.getIsWin();
					int pcount = 0;
					List<WorldPlayer> playerList = new ArrayList<WorldPlayer>();
					List<Integer> pIdList = new ArrayList<Integer>();
					List<Boolean> robotList = new ArrayList<Boolean>();
					List<Integer> shootRateList = new ArrayList<Integer>();
					List<Integer> totalHurtList = new ArrayList<Integer>();
					List<Integer> killCountList = new ArrayList<Integer>();
					List<Integer> beKilledCountList = new ArrayList<Integer>();
					List<Integer> addExpList = new ArrayList<Integer>();
					List<Integer> expList = new ArrayList<Integer>();
					List<Integer> upgradeExpList = new ArrayList<Integer>();
					List<Integer> nextUpgradeExpList = new ArrayList<Integer>();
					List<Integer> huntTimesList = new ArrayList<Integer>();
					List<Integer> actionTimesList = new ArrayList<Integer>();
					List<Integer> beKillRoundList = new ArrayList<Integer>();
					List<Boolean> isLostList = new ArrayList<Boolean>();
					List<Boolean> isSuicideList = new ArrayList<Boolean>();
					List<Boolean> isEnforceQuitList = new ArrayList<Boolean>();
					List<Boolean> isWinList = new ArrayList<Boolean>();
					List<Integer> itemIdList = new ArrayList<Integer>();
					List<RewardItemsVo> rewardList = new ArrayList<RewardItemsVo>();
					int cardCount = 8;
					int[] card_shopItemId = new int[cardCount];
					String[] card_Name = new String[cardCount];
					String[] item_icon = new String[cardCount];
					int[] card_num = new int[cardCount];
					if (1 == room.getBattleStatus()) {// 初始化房间属性
						for (Seat seat : room.getPlayerList()) {
							if (null != seat.getPlayer()) {
								pcount++;
								playerList.add(seat.getPlayer());
								pIdList.add(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
								int sr = 0;
								int th = 0;
								int kc = 0;
								int bc = 0;
								int ht = 0;
								int hp = 0;
								int pl = 0;
								int at = 0;
								int br = 0;
								boolean iq = false;
								boolean il = false;
								boolean is = false;
								boolean iw = false;
								for (int i = 0; i < playerIds.length; i++) {
									if (seat.getPlayer().getId() == ServiceManager.getManager().getCrossService().getPlayerId(playerIds[i])) {
										sr = shootRate[i];
										th = totalHurt[i];
										kc = killCount[i];
										bc = beKilledCount[i];
										ht = huntTimes[i];
										hp = hps[i];
										pl = pLevel[i];
										at = actionTimes[i];
										br = beKillRound[i];
										iq = isEnforceQuit[i];
										il = isLost[i];
										is = isSuicide[i];
										iw = isWin[i];
										break;
									}
								}
								shootRateList.add(sr);
								totalHurtList.add(th);
								killCountList.add(kc);
								beKilledCountList.add(bc);
								if (6 == room.getBattleMode()) {
									addExpList.add(0);
								} else {
									addExpList.add(getExp(seat.getPlayer(), ht, hp, kc, pl, iq, il, iw, times0, times1, round, playerCount,
											firstHurtPlayerId, playerNumMode, room.getPlayerList()));
								}
								expList.add(seat.getPlayer().getPlayer().getExp());
								upgradeExpList.add(ServiceManager.getManager().getPlayerService()
										.getUpgradeExp(seat.getPlayer().getLevel(), seat.getPlayer().getPlayer().getZsLevel()));
								nextUpgradeExpList.add(ServiceManager.getManager().getPlayerService()
										.getUpgradeExp(seat.getPlayer().getLevel() + 1, seat.getPlayer().getPlayer().getZsLevel()));
								huntTimesList.add(ht);
								isLostList.add(il);
								isSuicideList.add(is);
								isEnforceQuitList.add(iq);
								isWinList.add(iw);
								robotList.add(seat.isRobot());
								actionTimesList.add(at);
								beKillRoundList.add(br);
							}
						}
					}
					List<RewardItemsVo> rivList = ServiceManager.getManager().getRewardItemsService().getRewardItems();
					DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
					DailyRewardVo dailyRewardVo = dailyActivityService.getTurnoverReward();
					for (int i = 0; i < cardCount; i++) {
						card_shopItemId[i] = rivList.get(i).getItemId();
						card_Name[i] = rivList.get(i).getItemName();
						item_icon[i] = rivList.get(i).getItemIcon();
						int count = -1 == rivList.get(i).getDays() ? rivList.get(i).getCount() : rivList.get(i).getDays();
						card_num[i] = count;
						if (i < pcount) {
							if (!isLostList.get(i) && huntTimesList.get(i) > 0 && !isSuicideList.get(i)) {// 如果玩家掉线或伤害为0或自杀则不给以物品奖励
								itemIdList.add(card_shopItemId[i]);
								rewardList.add(rivList.get(i));
								if (card_shopItemId[i] == Common.GOLDID) {
									card_num[i] = (int) ServiceManager.getManager().getBuffService()
											.getAddition(playerList.get(i), count, Buff.GOLD);
									card_num[i] = dailyActivityService.getRewardedVal(card_num[i], dailyRewardVo.getGold());
									rivList.get(i).setCount(card_num[i]);
								} else if (card_shopItemId[i] == Common.BADGEID) {
									card_num[i] = dailyActivityService.getRewardedVal(card_num[i], dailyRewardVo.getMedal());
									rivList.get(i).setCount(card_num[i]);
								}
							} else {
								itemIdList.add(-1);
								rewardList.add(null);
							}
						}
					}
					List<Integer> integralList = creditsIssued(room.getBattleMode(), playerList, robotList, isWinList, isEnforceQuitList);
					GameOver gameOver = new GameOver();
					gameOver.setBattleId(battleId);
					gameOver.setFirstHurtPlayerId(firstHurtPlayerId);
					gameOver.setWinCamp(winCamp);
					gameOver.setPlayerCount(pcount);
					gameOver.setPlayerIds(ServiceUtils.getInts(pIdList.toArray()));
					gameOver.setShootRate(ServiceUtils.getInts(shootRateList.toArray()));
					gameOver.setTotalHurt(ServiceUtils.getInts(totalHurtList.toArray()));
					gameOver.setTotalHurt(ServiceUtils.getInts(totalHurtList.toArray()));
					gameOver.setKillCount(ServiceUtils.getInts(killCountList.toArray()));
					gameOver.setBeKilledCount(ServiceUtils.getInts(beKilledCountList.toArray()));
					gameOver.setAddExp(ServiceUtils.getInts(addExpList.toArray()));
					gameOver.setExp(ServiceUtils.getInts(expList.toArray()));
					gameOver.setUpgradeExp(ServiceUtils.getInts(upgradeExpList.toArray()));
					gameOver.setNextUpgradeExp(ServiceUtils.getInts(nextUpgradeExpList.toArray()));
					gameOver.setItemId(ServiceUtils.getInts(itemIdList.toArray()));
					gameOver.setCardCount(cardCount);
					gameOver.setCard_shopItemId(card_shopItemId);
					gameOver.setCard_Name(card_Name);
					gameOver.setItem_icon(item_icon);
					gameOver.setCard_num(card_num);
					gameOver.setIntegral(ServiceUtils.getInts(integralList.toArray()));
					for (Seat player : room.getPlayerList()) {
						if (null != player.getPlayer() && !player.isRobot()) {
							gameOver.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(player.getPlayer().getId()));
							player.getPlayer().setBattleId(0);
							player.getPlayer().sendData(gameOver);
						}
					}

					// 发放奖励
					WorldPlayer player;
					RewardItemsVo riv;
					BattleTeam battleTeam = new BattleTeam();
					battleTeam.setBattleMode(room.getBattleMode());
					battleTeam.setPlayerNumMode(playerNumMode);
					Vector<Combat> combatList = new Vector<Combat>();
					battleTeam.setCombatList(combatList);
					for (int i = 0; i < playerList.size(); i++) {
						if (!robotList.get(i)) {
							player = playerList.get(i);
							riv = rewardList.get(i);
							Combat combat = new Combat();
							combatList.add(combat);
							combat.setPlayer(player);
							combat.setActionTimes(actionTimesList.get(i));
							combat.setBeKilledCount(beKilledCountList.get(i));
							combat.setBeKillRound(beKillRoundList.get(i));
							combat.setHuntTimes(huntTimesList.get(i));
							if (shootRateList.get(i) == 100) {
								combat.setShootTimes(huntTimesList.get(i));
							}
							if (!isLostList.get(i)) {
								int num = 1;
								if (player.hasRing()) {
									num++;
								}
								if (huntTimesList.get(i) > 0 && !isSuicideList.get(i) && room.getBattleMode() != 6 && null != riv) {// 玩家命中次数大于0才给以奖励
									if (itemIdList.get(i) == Common.GOLDID) {
										int gold = ServiceManager.getManager().getVersionService().getAddition(riv.getCount(), 1);
										ServiceManager.getManager().getPlayerService()
												.updatePlayerGold(player, gold, SystemLogService.GSBATTLE, "");
									} else if (riv.getItemId() == Common.DIAMONDID) {// 获得点卷
										ServiceManager.getManager().getPlayerService()
												.addTicket(player, riv.getCount(), 0, TradeService.ORIGIN_BATT, 0, "", "对战奖励", "", "");
									} else {
										if (Common.BADGEID == riv.getItemId()) {
											riv.setCount(ServiceManager.getManager().getVersionService().getAddition(riv.getCount(), 2));
										}
										playerItemsFromShopService.playerGetItem(player.getId(), riv.getItemId(), -1, riv.getDays(),
												riv.getCount(), 12, null, 0, 0, 0);
									}
									if (addExpList.get(i) > 0
											|| (0 == addExpList.get(i) && player.getLevel() >= Server.config.getMaxLevel(player.getPlayer()
													.getZsLevel()))) {
										playerSinConsortiaService.updatePlayerContribute(player, num);
										playerItemsFromShopService.updateItmeSkillful(player);
									}
								}
							}
							if (isEnforceQuitList.get(i)) {
								playerService.updateBattleHistory(player.getPlayer(), room.getBattleMode(), playerNumMode, false);
							} else {
								playerService
										.updateBattleHistory(player.getPlayer(), room.getBattleMode(), playerNumMode, isWinList.get(i));
							}
							int exp = addExpList.get(i);
							if (exp > 0) {
								exp = ServiceManager.getManager().getVersionService().getAddition(exp, 0);
							}
							playerService.updatePlayerEXP(player, exp);
							ServiceManager.getManager().getPlayerPetService()
									.updateExp(player, player.getPlayerPet(), (int) Math.ceil(exp * 0.1));
						}
					}
					ServiceManager.getManager().getTaskService().battleTask(battleTeam, gameOver.getWinCamp());// 更新玩家任务
					ServiceManager.getManager().getTitleService().battleTask(battleTeam, gameOver.getWinCamp());// 检查称号任务
				}
				for (int i = 0; i < crossGameOver.getPlayerIds().length; i++) {
					ServiceManager.getManager().getCrossService().extBattle(crossGameOver.getPlayerIds()[i], crossGameOver.getRoomId());
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}

	/**
	 * 计算玩家可以获得的经验
	 * 
	 * @param player
	 *            计算经验的玩家
	 * @param huntTimes
	 *            玩家有效伤害次数
	 * @param hp
	 *            玩家当前的血量
	 * @param killCount
	 *            玩家击杀敌对玩家数量
	 * @param pLevel
	 *            敌对阵营平均等级
	 * @param isEnforceQuit
	 *            玩家是否强退
	 * @param isLost
	 *            玩家是否掉线
	 * @param isWin
	 *            玩家是否胜利
	 * @param times0
	 *            阵营0射击次数
	 * @param times1
	 *            阵营1射击次数
	 * @param round
	 *            游戏回合数
	 * @param pcount
	 *            战斗玩家数量
	 * @param firstHurtPlayerId
	 *            首先造成伤害的玩家
	 * @param playerNumMode
	 *            对战人数模式
	 * @param seatList
	 *            本房间的人员列表
	 * @return
	 */
	private int getExp(WorldPlayer player, int huntTimes, int hp, int killCount, int pLevel, boolean isEnforceQuit, boolean isLost,
			boolean isWin, int times0, int times1, int round, int pcount, int firstHurtPlayerId, int playerNumMode, Vector<Seat> seatList) {
		if (times0 > 0 && times1 > 0 && round > 1) {// 正常战斗
			int coupleId = 0;
			int addExp = 0;
			MarryRecord mr = ServiceManager.getManager().getMarryService()
					.getSingleMarryRecordByPlayerId(player.getPlayer().getSex(), player.getId(), 1);
			if (null != mr && mr.getStatusMode() > 1) {
				if (player.getPlayer().getSex() == 0) {
					coupleId = mr.getWomanId();
				} else {
					coupleId = mr.getManId();
				}
			}
			if (!isLost) {
				if (isWin) {
					if (firstHurtPlayerId == player.getId() && playerNumMode != 1) {
						addExp += 10;
					}
					if (hp > 0) {
						addExp += (0.07 * pcount + 0.79) * (1 / (1 + (pLevel - player.getLevel()) * (pLevel - player.getLevel()) * 0.0008))
								* (killCount * 20 + 15) + player.getLevel() / 4;
					} else {
						addExp += ((0.07 * pcount + 0.79)
								* (1 / (1 + (pLevel - player.getLevel()) * (pLevel - player.getLevel()) * 0.0008)) * (killCount * 20 + 15))
								/ 2 + player.getLevel() / 8;
					}
					int exp1 = ServiceManager.getManager().getBuffService().getExp(player, addExp) - addExp;
					// 添加公会技能加成
					int exp2 = (int) ServiceManager.getManager().getBuffService().getAddition(player, addExp, Buff.CEXP) - addExp;
					// 添加结婚经验加成
					int exp3 = 0;
					if (coupleId != 0) {
						for (Seat seat : seatList) {
							if (null != seat.getPlayer() && seat.getPlayer().getId() == coupleId) {
								exp3 = (int) ServiceManager.getManager().getBuffService().getAddition(player, addExp, Buff.MEXP) - addExp;
							}
						}
					}
					addExp = addExp + exp1 + exp2 + exp3;
				} else {
					addExp += 10;
					int exp1 = ServiceManager.getManager().getBuffService().getExp(player, addExp) - addExp;
					// 添加公会技能加成
					int exp2 = (int) ServiceManager.getManager().getBuffService().getAddition(player, addExp, Buff.CEXP) - addExp;
					// 添加结婚经验加成
					int exp3 = 0;
					if (coupleId != 0) {
						for (Seat seat : seatList) {
							if (null != seat.getPlayer() && seat.getPlayer().getId() == coupleId) {
								exp3 = (int) ServiceManager.getManager().getBuffService().getAddition(player, addExp, Buff.MEXP) - addExp;
							}
						}
					}
					addExp = addExp + exp1 + exp2 + exp3;
				}
			} else if (isEnforceQuit) {
				addExp = 0;
				if (huntTimes > 0) {
					addExp = -5;
				} else {
					addExp = -10;
				}
			}
			if (huntTimes > 0 && player.getLevel() == Server.config.getMaxLevel(player.getPlayer().getZsLevel())) {
				int sjexp = ServiceManager.getManager().getPlayerService()
						.getUpgradeExp(player.getLevel(), player.getPlayer().getZsLevel());
				int dqexp = player.getPlayer().getExp() + addExp;
				if (sjexp <= dqexp) {
					// 实际增加
					int actualAddExp = sjexp - 1 - player.getPlayer().getExp();
					if (actualAddExp < 1) {
						// 玩家满级后不加经验，但宠物要加
						ServiceManager.getManager().getPlayerPetService()
								.updateExp(player, player.getPlayerPet(), (int) Math.ceil(addExp * 0.1));
						addExp = 0;
					} else {
						addExp = actualAddExp;
					}
				}
			}

			if (addExp > 0 && isWin) {
				// 经验打折
				int rate = ServiceManager.getManager().getVersionService().getExpRateByBattleNum(player.getBattleNum());
				addExp = addExp * rate / 100;
				player.setBattleNum(player.getBattleNum() + 1);
			}
			return addExp;
		} else {
			int exp = 0;
			if (isEnforceQuit) {
				if (huntTimes > 0) {
					exp = -5;
				} else {
					exp = -10;
				}
			}
			return exp;
		}
	}

	/**
	 * 计算玩家的弹王积分
	 * 
	 * @param battleMode
	 * @param playerList
	 * @param robotList
	 * @param isWinList
	 * @param isEnforceQuitList
	 * @return
	 */
	public List<Integer> creditsIssued(int battleMode, List<WorldPlayer> playerList, List<Boolean> robotList, List<Boolean> isWinList,
			List<Boolean> isEnforceQuitList) {
		List<Integer> integralList = new ArrayList<Integer>();
		// 挑战赛核算积分
		if (battleMode == 6 && ServiceManager.getManager().getChallengeSerService().isInTime()) {
			WorldPlayer player;
			for (int i = 0; i < playerList.size(); i++) {
				player = playerList.get(i);
				if (robotList.get(i))
					break;
				int credits = 0;
				int arrayWin = ServiceManager.getManager().getChallengeSerService().getArrayWinNum(player.getId());
				boolean isWin = false;
				if (ServiceManager.getManager().getChallengeSerService().isInTime()) {
					if (isWinList.get(i)) {
						if (isEnforceQuitList.get(i)) {
							credits = 0;
							arrayWin = 0;
						} else {
							isWin = true;
							arrayWin = arrayWin + 1;
							if (arrayWin > 3) {
								credits = 5 + (arrayWin - 3) * 2;
							} else {
								credits = 5;
							}
						}
					} else {
						if (isEnforceQuitList.get(i)) {
							credits = 0;
						} else {
							credits = 1;
						}
						arrayWin = 0;
					}
				} else {
					credits = 0;
					arrayWin = 0;
				}
				ServiceManager.getManager().getChallengeSerService().addIntegral(player, credits, isWin);
				integralList.add(credits);
			}
		} else {
			for (int i = 0; i < playerList.size(); i++) {
				integralList.add(0);
			}
		}
		return integralList;
	}
}