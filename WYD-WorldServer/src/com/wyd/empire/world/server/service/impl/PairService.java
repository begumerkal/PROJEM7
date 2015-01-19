package com.wyd.empire.world.server.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import com.wyd.empire.protocol.data.room.MakePairFail;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.RandomRoom;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 随机配对服务
 * 
 * @author Administrator
 */
public class PairService implements Runnable {
	private List<RandomRoom> randomRoomList;
	private MakePairFail makePairFail;

	public PairService() {
		randomRoomList = new Vector<RandomRoom>();
		makePairFail = new MakePairFail();
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("PairService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				RandomRoom randomRoomX;
				RandomRoom randomRoomY;
				for (int i = 0; i < randomRoomList.size(); i++) {
					randomRoomX = randomRoomList.get(i);
					randomRoomX.setCount(randomRoomX.getCount() + 1);
					if (!randomRoomX.isReady() && 0 == randomRoomX.getRoom().getRoomType()) {
						for (int y = i + 1; y < randomRoomList.size(); y++) {
							randomRoomY = randomRoomList.get(y);
							if (randomRoomX.getRoom().getBattleMode() == randomRoomY.getRoom().getBattleMode()
									&& randomRoomX.getRoom().getPlayerNum() == randomRoomY.getRoom().getPlayerNum()
									&& randomRoomX.getRoom().getRoomChannel() == randomRoomY.getRoom().getRoomChannel()) {
								float fightingX, fightingY;
								if (randomRoomX.getRoom().getBattleMode() == 6) {
									fightingX = randomRoomX.getRoom().getAvgIntegral();
									fightingY = randomRoomY.getRoom().getAvgIntegral();
								} else {
									fightingX = randomRoomX.getAverageFighting();
									fightingY = randomRoomY.getAverageFighting();
								}
								int rate = 100;
								if (fightingX >= 0)
									rate = Math.abs((int) (((fightingX - fightingY) / fightingX) * 100f));
								if (!randomRoomY.isReady()
										&& 0 == randomRoomX.getRoom().getRoomType()
										&& rate <= 30
										&& !ServiceManager.getManager().getRoomService()
												.checkPlayerInRoom(randomRoomX.getRoom(), randomRoomY.getRoom())) {
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
									int fwa = 0;
									if (Room.BATTLE_TYPE_MACHINE == randomRoomX.getRoom().getRoomType()
											|| Room.BATTLE_TYPE_MACHINE == randomRoomY.getRoom().getRoomType()) {
										fwa = 1;
									}
									ServiceManager.getManager().getBattleTeamService().startBattle(battleId, randomRoomX.getRoom(), fwa);
									break;
								}
							}
						}
					}
					if (!randomRoomX.isReady() && randomRoomX.getRoom().getBattleMode() == 6) {
						for (int y = i + 1; y < randomRoomList.size(); y++) {
							randomRoomY = randomRoomList.get(y);
							if (!randomRoomY.isReady() && randomRoomX.getRoom().getBattleMode() == randomRoomY.getRoom().getBattleMode()) {
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
					}
				}
				RandomRoom randomRoom;
				for (int i = randomRoomList.size() - 1; i >= 0; i--) {
					randomRoom = randomRoomList.get(i);
					if (randomRoom.isReady()) {
						randomRoomList.remove(i);
						continue;
					}
					if (randomRoom.getRoom().getBattleMode() < 4) {
						if (randomRoom.getCount() > 1) {
							randomRoomList.remove(i);
							List<Seat> seatList = randomRoom.getRoom().getPlayerList();
							if (ServiceManager.getManager().getRobotService().getRobotCount(randomRoom.getRoom().getRoomChannel()) < (randomRoom
									.getRoom().getPlayerNumMode() * 2)) {
								randomRoom.getRoom().setBattleStatus(0);
								for (Seat seat : seatList) {
									if (null != seat.getPlayer() && !seat.isRobot()) {
										seat.getPlayer().sendData(makePairFail);
									}
								}
							} else {
								int battleId = ServiceManager.getManager().getBattleTeamService().createBattleTeam(1);
								try {
									Set<String> idMap = new HashSet<String>();
									int count = 0;
									for (Seat seat : seatList) {
										if (null != seat.getPlayer()) {
											idMap.add(seat.getPlayer().getPlayer().getId().toString());
											count++;
										}
									}
									if (count < 1) {
										ServiceManager.getManager().getRoomService().deletRoom(randomRoom.getRoom());
										continue;
									}
									for (Seat seat : seatList) {
										if (null != seat.getPlayer()) {
											ServiceManager.getManager().getBattleTeamService()
													.enBattleTeam(battleId, seat.getPlayer(), 0, seat.isRobot());
											// 加入机器人<还需添加防止重复机器人的代码>
											WorldPlayer worldPlayer = ServiceManager
													.getManager()
													.getRobotService()
													.getRobot(randomRoom.getRoom().getAvgLevel(), randomRoom.getRoom().getAvgFighting(),
															idMap, randomRoom.getRoom().getRoomChannel());
											if (null == worldPlayer)
												throw new Exception("缺少机器人");
											idMap.add(worldPlayer.getPlayer().getId().toString());
											ServiceManager.getManager().getBattleTeamService().enBattleTeam(battleId, worldPlayer, 1, true);
										}
									}
									idMap = null;
									ServiceManager.getManager().getBattleTeamService().startBattle(battleId, randomRoom.getRoom(), 1);
								} catch (Exception e) {
									if (!"缺少机器人".equals(e.getMessage()))
										e.printStackTrace();
									BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
									if (null != battleTeam) {
										ServiceManager.getManager().getBattleTeamService().deleteBattleTeam(battleTeam);
									}
									randomRoom.getRoom().setBattleStatus(0);
									for (Seat seat : seatList) {
										if (null != seat.getPlayer() && !seat.isRobot()) {
											seat.getPlayer().sendData(makePairFail);
										}
									}
								}
							}
						}
					} else {
						if (randomRoom.getCount() >= 2) {
							randomRoomList.remove(i);
							List<Seat> seatList = randomRoom.getRoom().getPlayerList();
							randomRoom.getRoom().setBattleStatus(0);
							for (Seat seat : seatList) {
								if (null != seat.getPlayer() && !seat.isRobot()) {
									seat.getPlayer().sendData(makePairFail);
								}
							}
						}
					}
				}
				Thread.sleep(5000L);
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
}