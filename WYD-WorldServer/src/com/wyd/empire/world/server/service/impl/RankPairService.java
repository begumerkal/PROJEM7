package com.wyd.empire.world.server.service.impl;

import java.util.List;
import java.util.Vector;

import com.wyd.empire.protocol.data.room.MakePairFail;
import com.wyd.empire.world.room.RandomRoom;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 随机配对服务
 * 
 * @author Administrator
 */
public class RankPairService implements Runnable {
	private List<RandomRoom> randomRoomList;
	private MakePairFail makePairFail;

	public RankPairService() {
		randomRoomList = new Vector<RandomRoom>();
		makePairFail = new MakePairFail();
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("RankPairService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				RandomRoom randomRoomX;
				RandomRoom randomRoomY;
				// 匹配等级相同的玩家
				for (int i = 0; i < randomRoomList.size(); i++) {
					randomRoomX = randomRoomList.get(i);
					randomRoomX.setCount(randomRoomX.getCount() + 1);
					if (!randomRoomX.isReady()) {
						for (int y = i + 1; y < randomRoomList.size(); y++) {
							randomRoomY = randomRoomList.get(y);
							if (!randomRoomY.isReady()) {
								if (randomRoomX.getRoom().getHonorLevel() == randomRoomY.getRoom().getHonorLevel()) {
									randomRoomX.setReady(true);
									randomRoomY.setReady(true);
									int battleId = ServiceManager.getManager().getBattleTeamService().createBattleTeam(1);
									for (Seat seat : randomRoomX.getRoom().getPlayerList()) {
										if (null != seat.getPlayer()) {
											ServiceManager.getManager().getBattleTeamService()
													.enBattleTeam(battleId, seat.getPlayer(), 0, seat.isRobot());
										}
									}
									for (Seat seat : randomRoomY.getRoom().getPlayerList()) {
										if (null != seat.getPlayer()) {
											ServiceManager.getManager().getBattleTeamService()
													.enBattleTeam(battleId, seat.getPlayer(), 1, seat.isRobot());
										}
									}
									ServiceManager.getManager().getBattleTeamService().startBattle(battleId, randomRoomX.getRoom(), 0);
									break;
								}
							} else {
								continue;
							}
						}
					} else {
						continue;
					}
				}

				// 匹配分数相近的玩家
				for (int i = 0; i < randomRoomList.size(); i++) {
					randomRoomX = randomRoomList.get(i);
					randomRoomX.setCount(randomRoomX.getCount() + 1);
					if (!randomRoomX.isReady()) {
						for (int y = i + 1; y < randomRoomList.size(); y++) {
							randomRoomY = randomRoomList.get(y);
							if (!randomRoomY.isReady()) {
								if (randomRoomX.getRoom().getRankScore() - randomRoomX.getRoom().getGap() < randomRoomY.getRoom()
										.getRankScore()
										&& randomRoomX.getRoom().getRankScore() + randomRoomX.getRoom().getGap() > randomRoomY.getRoom()
												.getRankScore()) {
									if (randomRoomY.getRoom().getRankScore() - randomRoomY.getRoom().getGap() < randomRoomX.getRoom()
											.getRankScore()
											&& randomRoomY.getRoom().getRankScore() + randomRoomY.getRoom().getGap() > randomRoomX
													.getRoom().getRankScore()) {
										randomRoomX.setReady(true);
										randomRoomY.setReady(true);
										int battleId = ServiceManager.getManager().getBattleTeamService().createBattleTeam(1);
										for (Seat seat : randomRoomX.getRoom().getPlayerList()) {
											if (null != seat.getPlayer()) {
												ServiceManager.getManager().getBattleTeamService()
														.enBattleTeam(battleId, seat.getPlayer(), 0, seat.isRobot());
											}
										}
										for (Seat seat : randomRoomY.getRoom().getPlayerList()) {
											if (null != seat.getPlayer()) {
												ServiceManager.getManager().getBattleTeamService()
														.enBattleTeam(battleId, seat.getPlayer(), 1, seat.isRobot());
											}
										}
										ServiceManager.getManager().getBattleTeamService().startBattle(battleId, randomRoomX.getRoom(), 0);
										break;
									}
								}
							} else {
								continue;
							}
						}
					} else {
						continue;
					}
				}

				// 随机匹配玩家
				for (int i = 0; i < randomRoomList.size(); i++) {
					randomRoomX = randomRoomList.get(i);
					randomRoomX.setCount(randomRoomX.getCount() + 1);
					if (!randomRoomX.isReady()) {
						for (int y = i + 1; y < randomRoomList.size(); y++) {
							randomRoomY = randomRoomList.get(y);
							if (!randomRoomY.isReady()) {
								randomRoomX.setReady(true);
								randomRoomY.setReady(true);
								int battleId = ServiceManager.getManager().getBattleTeamService().createBattleTeam(1);
								for (Seat seat : randomRoomX.getRoom().getPlayerList()) {
									if (null != seat.getPlayer()) {
										ServiceManager.getManager().getBattleTeamService()
												.enBattleTeam(battleId, seat.getPlayer(), 0, seat.isRobot());
									}
								}
								for (Seat seat : randomRoomY.getRoom().getPlayerList()) {
									if (null != seat.getPlayer()) {
										ServiceManager.getManager().getBattleTeamService()
												.enBattleTeam(battleId, seat.getPlayer(), 1, seat.isRobot());
									}
								}
								ServiceManager.getManager().getBattleTeamService().startBattle(battleId, randomRoomX.getRoom(), 0);
								break;
							} else {
								continue;
							}
						}
					} else {
						continue;
					}
				}

				RandomRoom randomRoom;
				for (int i = randomRoomList.size() - 1; i >= 0; i--) {
					randomRoom = randomRoomList.get(i);
					if (randomRoom.isReady()) {
						randomRoomList.remove(i);
						continue;
					} else {
						if (randomRoom.getCount() >= 15) {// 30秒大概能配对10-12次
							randomRoomList.remove(i);
							List<Seat> seatList = randomRoom.getRoom().getPlayerList();
							for (Seat seat : seatList) {
								if (null != seat.getPlayer() && !seat.isRobot()) {
									seat.getPlayer().sendData(makePairFail);
								}
							}
						}
					}
				}
				// RandomRoom randomRoom;
				// for (int i = randomRoomList.size() - 1; i >= 0; i--) {
				// randomRoom = randomRoomList.get(i);
				// if (randomRoom.isReady()) {
				// randomRoomList.remove(i);
				// continue;
				// }
				// if (randomRoom.getCount() > 1) {
				// randomRoomList.remove(i);
				// List<Seat> seatList = randomRoom.getRoom().getPlayerList();
				// int random = ServiceUtils.getRandomNum(0, 100);
				// if (random < 0) {// 百分之10的机率配对失败
				// randomRoom.getRoom().setBattleStatus(0);
				// for (Seat seat : seatList) {
				// if (null != seat.getPlayer() && !seat.isRobot()) {
				// seat.getPlayer().sendData(makePairFail);
				// }
				// }
				// } else {
				// if
				// (ServiceManager.getManager().getPlayerService().getLostPlayerCount(randomRoom.getRoom().getRoomChannel())
				// < (randomRoom.getRoom().getPlayerNumMode() * 2)) {
				// randomRoom.getRoom().setBattleStatus(0);
				// for (Seat seat : seatList) {
				// if (null != seat.getPlayer() && !seat.isRobot()) {
				// seat.getPlayer().sendData(makePairFail);
				// }
				// }
				// } else {
				// int battleId =
				// ServiceManager.getManager().getBattleTeamService().createBattleTeam();
				// try {
				// Set<Integer> idMap = new HashSet<Integer>();
				// for (Seat seat : seatList) {
				// if (null != seat.getPlayer()) {
				// idMap.add(seat.getPlayer().getPlayer().getId());
				// }
				// }
				// for (Seat seat : seatList) {
				// if (null != seat.getPlayer()) {
				// ServiceManager.getManager().getBattleTeamService().enBattleTeam(battleId,
				// seat.getPlayer(), 0, seat.isRobot());
				// // 加入机器人<还需添加防止重复机器人的代码>
				// WorldPlayer worldPlayer =
				// ServiceManager.getManager().getPlayerService().getLostPlayer(idMap,
				// randomRoom.getRoom().getRoomChannel());
				// idMap.add(worldPlayer.getPlayer().getId());
				// ServiceManager.getManager().getBattleTeamService().enBattleTeam(battleId,
				// worldPlayer, 1, true);
				// }
				// }
				// idMap = null;
				// ServiceManager.getManager().getBattleTeamService().startBattle(battleId,
				// randomRoom.getRoom(), 1);
				// } catch (Exception e) {
				// BattleTeam battleTeam=
				// ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				// if(null!=battleTeam){
				// ServiceManager.getManager().getBattleTeamService().deleteBattleTeam(battleId);
				// battleTeam = null;
				// }
				// randomRoom.getRoom().setBattleStatus(0);
				// for (Seat seat : seatList) {
				// if (null != seat.getPlayer() && !seat.isRobot()) {
				// seat.getPlayer().sendData(makePairFail);
				// }
				// }
				// }
				// }
				// }
				// }
				// }
				Thread.sleep(3000L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 加入随机房间
	 * 
	 * @param randomRoom
	 */
	public void addRandomRoom(RandomRoom randomRoom) {
		randomRoomList.add(randomRoom);
	}

	/**
	 * 删除随机房间
	 * 
	 * @param randomRoom
	 */
	public void deleteRandomRoom(int roomId) {
		for (RandomRoom r : randomRoomList) {
			if (r.getRoom().getRoomId() == roomId) {
				randomRoomList.remove(r);
				break;
			}
		}
	}
}