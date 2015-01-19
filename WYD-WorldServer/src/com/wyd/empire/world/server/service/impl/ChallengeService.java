package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.protocol.data.challenge.GetInBattleTeam;
import com.wyd.empire.protocol.data.challenge.NoticeLeader;
import com.wyd.empire.protocol.data.challenge.PlayerQuit;
import com.wyd.empire.protocol.data.challenge.QuitRoomOk;
import com.wyd.empire.protocol.data.challenge.UpdateSeat;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 房间数据管理
 * 
 * @author Administrator
 */
public class ChallengeService implements Runnable {
	/** 房间ID与房间对象对应哈希表 */
	private ConcurrentHashMap<Integer, Room> mapRoom;
	/** 房间列表 */
	private List<Room> listRoom;

	public ChallengeService() {
		mapRoom = new ConcurrentHashMap<Integer, Room>();
		listRoom = new Vector<Room>();
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("ChallengeService-Thread");
		t.start();
	}

	@Override
	public void run() {
		ChallengeService challengeService = ServiceManager.getManager().getChallengeService();
		PlayerService playerService = ServiceManager.getManager().getPlayerService();
		while (true) {
			try {
				Thread.sleep(ServiceManager.getManager().getVersionService().getVersion().getRoomRefreshTime() * 1000);
				Room room;
				for (int i = listRoom.size() - 1; i >= 0; i--) {
					room = listRoom.get(i);
					if (room.getPassWord().equals("-1") && Room.BATTLE_STATUS_WAIT == room.getBattleStatus() && !room.isNewPlayerRoom()) {
						ServiceManager.getManager().getSimpleThreadPool().execute(createTask(room, challengeService, playerService));
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private Runnable createTask(Room room, ChallengeService challengeService, PlayerService playerService) {
		return new ChallengeThread(room, challengeService, playerService);
	}

	public class ChallengeThread implements Runnable {
		private Room room;
		private ChallengeService challengeService;
		private PlayerService playerService;

		public ChallengeThread(Room room, ChallengeService challengeService, PlayerService playerService) {
			this.room = room;
			this.challengeService = challengeService;
			this.playerService = playerService;
		}

		@Override
		public void run() {
			if (Room.BATTLE_STATUS_WAIT == room.getBattleStatus()) {
				int random = ServiceUtils.getRandomNum(0, 100);
				Seat seat = room.getPlayerList().get(getPlayerSeat(room.getRoomId(), room.getWnersId()));
				if (seat.isRobot()) {
					challengeService.exRoom(room.getRoomId(), seat.getSeatIndex());
				} else {
					random = ServiceUtils.getRandomNum(0, room.getPlayerList().size());
					seat = room.getPlayerList().get(random);
					if (seat.isRobot() && null != seat.getPlayer()) {
						challengeService.exRoom(room.getRoomId(), seat.getSeatIndex());
					}
				}
			}
			if (System.currentTimeMillis() - room.getCreateTime() > 10000) {
				boolean deadRoom = true;
				for (Seat seat : room.getPlayerList()) {
					if (!seat.isRobot() && null != seat.getPlayer() && null != playerService.getOnlineWorldPlayer(seat.getPlayer().getId())) {
						deadRoom = false;
						break;
					}
				}
				if (deadRoom) {
					for (Seat seat : room.getPlayerList()) {
						if (null != seat.getPlayer()) {
							seat.getPlayer().setRoomId(0);
						}
					}
					deletRoom(room);
				}
			}
		}
	}

	/**
	 * 创建房间并返回房间的id
	 * 
	 * @param player
	 *            房间创建人
	 * @param roomName
	 *            房间名称
	 * @param battleMode
	 *            对战模式 1、竞技模式，2、复活模式，3、混战模式 4、排位赛,5公会战,6弹王挑战赛
	 * @param playerNumMode
	 *            对战人数模式，1=1v1，2=2v2，3=3v3
	 * @param passWord
	 *            若为-1则无密码
	 * @param startMode
	 *            撮合方式,1、随机撮合,2、自由模式
	 * @param roomType
	 *            房间类型： 0正常房间， 1机器人房间
	 * @param eventMode
	 *            是否开启特殊事件
	 * @return
	 */
	public int createRoom(WorldPlayer player, String roomName, int battleMode, int playerNumMode, String passWord, int startMode,
			int roomType) {
		Room room = new Room();
		Vector<Seat> seatList = new Vector<Seat>();
		Seat seat;
		while (seatList.size() < 3) {// 创建座位信息
			seat = new Seat();
			seat.setCamp(0);
			seat.setUsed(true);
			seatList.add(seat);
		}
		while (seatList.size() < 6) {// 创建空座位信息
			seat = new Seat();
			seat.setCamp(0);
			seat.setUsed(false);
			seatList.add(seat);
		}

		for (int i = 0; i < 6; i++) {
			seat = seatList.get(i);
			seat.setSeatIndex(i);
		}

		roomName = KeywordsUtil.filterKeywords(roomName);

		room.setMapId(ServiceManager.getManager().getMapsService().getRandomId());
		room.setRoomChannel(1);
		room.setPlayerList(seatList);
		room.setHonorLevel(player.getPlayer().getHonorLevel());
		if (roomName.trim().length() == 0) {
			room.setRoomName(TipMessages.BATTLETEAM + room.getRoomId());
		} else {
			room.setRoomName(roomName);
		}
		room.setBattleMode(battleMode);
		room.setPlayerNumMode(playerNumMode);
		room.setPassWord(passWord);
		room.setWnersId(player.getId());
		room.setStartMode(startMode);
		room.setRoomType(roomType);
		mapRoom.put(room.getRoomId(), room);
		listRoom.add(room);
		// ServiceUtils.out("player:"+player.getId()+"-----------CreateRoom:-----------"+room.getRoomId());
		return room.getRoomId();
	}

	/**
	 * 更新房间信息
	 * 
	 * @param room
	 * @throws Exception
	 */
	public void updateRoom(Room room) throws Exception {
		synchronized (room) {
			if (3 == room.getBattleMode()) {
				Seat seat;
				for (int i = 0; i < 6; i++) {
					seat = room.getPlayerList().get(i);
					seat.setUsed(true);
					seat.setCamp(i);
				}
			} else {
				if (1 == room.getStartMode()) {
					Seat seat;
					for (int i = 0; i < 6; i++) {
						seat = room.getPlayerList().get(i);
						if (i < room.getPlayerNumMode()) {
							seat.setUsed(true);
							seat.setCamp(0);
						} else {
							seat.setUsed(false);
							seat.setCamp(1);
							if (null != seat.getPlayer()) {
								int extSeat = -1;
								int oseat = 0;
								int nseat = 0;
								boolean ru = false;
								if (room.getWnersId() == seat.getPlayer().getId()) {
									oseat = i;
									for (int y = 0; y < room.getPlayerNumMode(); y++) {
										if (null == room.getPlayerList().get(y).getPlayer()) {
											nseat = y;
											break;
										}
									}
									if (null != room.getPlayerList().get(nseat).getPlayer()) {
										extSeat = nseat;
									}
									ru = true;
								} else {
									extSeat = i;
								}
								PlayerQuit playerQuit = null;
								UpdateSeat updateSeat = null;
								if (-1 != extSeat) {
									Seat mSeat = room.getPlayerList().get(extSeat);
									if (!mSeat.isRobot()) {
										mSeat.getPlayer().setRoomId(0);
										mSeat.getPlayer().sendData(new QuitRoomOk());
									}
									mSeat.setPlayer(null);
									room.setPlayerNum(room.getPlayerNum() - 1);
									playerQuit = new PlayerQuit();
									playerQuit.setOldSeat(extSeat);
								}
								if (ru) {
									updateSeat(room.getRoomId(), oseat, nseat);
									updateSeat = new UpdateSeat();
									updateSeat.setBattleTeamId(room.getRoomId());
									updateSeat.setOldSeat(oseat);
									updateSeat.setNewSeat(nseat);
								}
								for (Seat st : room.getPlayerList()) {
									if (null != st.getPlayer()) {
										if (-1 != extSeat) {
											st.getPlayer().sendData(playerQuit);
										}
										if (ru) {
											st.getPlayer().sendData(updateSeat);
										}
									}
								}
							}
						}
					}
				} else {
					Seat seat;
					List<Seat> usList = new ArrayList<Seat>();
					for (int i = 0; i < 3; i++) {
						seat = room.getPlayerList().get(i);
						seat.setCamp(0);
						if (i < room.getPlayerNumMode()) {
							seat.setUsed(true);
						} else {
							seat.setUsed(false);
							if (null != seat.getPlayer()) {
								usList.add(seat);
							}
						}
					}
					for (int i = 0; i < 3; i++) {
						seat = room.getPlayerList().get(i + 3);
						seat.setCamp(1);
						if (i < room.getPlayerNumMode()) {
							seat.setUsed(true);
						} else {
							seat.setUsed(false);
							if (null != seat.getPlayer()) {
								usList.add(seat);
							}
						}
					}
					for (Seat us : usList) {
						boolean uok = false;
						for (Seat oseat : room.getPlayerList()) {
							if (oseat.isUsed() && oseat.getPlayer() == null) {
								updateSeat(room.getRoomId(), us.getSeatIndex(), oseat.getSeatIndex());
								UpdateSeat updateSeat = new UpdateSeat();
								updateSeat.setBattleTeamId(room.getRoomId());
								updateSeat.setOldSeat(us.getSeatIndex());
								updateSeat.setNewSeat(oseat.getSeatIndex());
								for (Seat st : room.getPlayerList()) {
									if (null != st.getPlayer()) {
										st.getPlayer().sendData(updateSeat);
									}
								}
								uok = true;
								break;
							}
						}
						if (!uok) {
							us.getPlayer().setRoomId(0);
							us.getPlayer().sendData(new QuitRoomOk());
							us.setPlayer(null);
							room.setPlayerNum(room.getPlayerNum() - 1);
							PlayerQuit playerQuit = new PlayerQuit();
							playerQuit.setOldSeat(us.getSeatIndex());
							for (Seat st : room.getPlayerList()) {
								if (null != st.getPlayer()) {
									st.getPlayer().sendData(playerQuit);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 进入房间
	 * 
	 * @param roomId
	 *            房间id
	 * @param player
	 * @throws Exception
	 * @return 返回座位号
	 */
	public int enRoom(int roomId, WorldPlayer player, boolean isRobot) throws Exception {
		Room room = mapRoom == null ? null : mapRoom.get(roomId);
		if (null == room) {
			throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_NOTFOUND_MESSAGE);
		}
		synchronized (room) {
			if (1 == room.getBattleStatus()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_BHS_MESSAGE);
			}
			if (room.isFull()) {
				room.getApplyList().clear();
				throw new Exception(Common.ERRORKEY + ErrorMessages.BATTLETEAM_FULL);
			}
			Vector<Seat> seatList = room.getPlayerList();
			Seat playerSeat = null;
			for (Seat seat : seatList) {
				if ((null == playerSeat && null == seat.getPlayer() && seat.isUsed())
						|| (null != seat.getPlayer() && seat.getPlayer().getId() == player.getId())) {
					playerSeat = seat;
				}
			}
			int index = playerSeat.getSeatIndex();
			playerSeat.setPlayer(player);
			playerSeat.setRobot(isRobot);
			room.setPlayerNum(room.getPlayerNum() + 1);
			if (!isRobot)
				player.setRoomId(room.getRoomId());
			if (room.isFull()) {
				room.getApplyList().clear();
			}
			noticeOtherPlayer(player);
			return index;
		}
	}

	/**
	 * 同步房间状态
	 * 
	 * @param roomId
	 */
	public void SynRoomInfo(int roomId) {
		Room room = getRoom(roomId);
		synchronized (room) {
			Vector<Seat> seatList = room.getPlayerList();
			for (Seat seat : seatList) {
				if (null != seat.getPlayer() && (seat.isRobot() || seat.getPlayer().getId() == room.getWnersId())) {
					seat.setReady(true);
				}
			}
			GetInBattleTeam getInBattleTeam = new GetInBattleTeam();
			getInBattleTeam.setBattleTeamId(roomId);
			getInBattleTeam.setBattleMode(room.getBattleMode());
			getInBattleTeam.setPlayerNumMode(room.getPlayerNumMode());
			getInBattleTeam.setTeamLeader(room.getWnersId());
			int playerNum = seatList.size();
			getInBattleTeam.setPlayerNum(playerNum);
			boolean[] seatUsed = new boolean[playerNum];
			int[] playerId = new int[playerNum];
			String[] playerName = new String[playerNum];
			int[] playerLevel = new int[playerNum];
			boolean[] playerReady = new boolean[playerNum];
			int[] playerSex = new int[playerNum];
			List<String> playerEquipment = new ArrayList<String>();
			int[] skillType = new int[playerNum * 2];
			int[] skillLevel = new int[playerNum * 2];
			int[] playerProficiency = new int[playerNum];
			int[] playerEquipmentLevel = new int[playerNum];
			int[] vipLevel = new int[playerNum];
			String[] playerWing = new String[playerNum];
			String[] player_title = new String[playerNum];
			int[] qualifyingLevel = new int[playerNum];
			int[] zsleve = new int[playerNum];
			boolean[] doubleMark = new boolean[playerNum];
			boolean[] isvip = new boolean[playerNum];
			Seat seat;
			PlayerItemsFromShop pifs;
			WeapSkill ws;
			for (int i = 0; i < playerNum; i++) {
				seat = seatList.get(i);
				seatUsed[i] = seat.isUsed();
				if (null != seat.getPlayer()) {
					playerId[i] = seat.getPlayer().getId();
					playerName[i] = seat.getPlayer().getName();
					playerLevel[i] = seat.getPlayer().getLevel();
					playerReady[i] = seat.isReady();
					playerSex[i] = seat.getPlayer().getPlayer().getSex();
					playerEquipment.add(seat.getPlayer().getSuit_head());
					playerEquipment.add(seat.getPlayer().getSuit_face());
					playerEquipment.add(seat.getPlayer().getSuit_body());
					playerEquipment.add(seat.getPlayer().getSuit_weapon());
					playerEquipmentLevel[i] = seat.getPlayer().getWeaponLevel();
					playerProficiency[i] = seat.getPlayer().getProficiency();
					zsleve[i] = seat.getPlayer().getPlayer().getZsLevel();
					if (null != seat.getPlayer().getPlayer().getVipTime()
							&& System.currentTimeMillis() <= seat.getPlayer().getPlayer().getVipTime().getTime()) {
						vipLevel[i] = seat.getPlayer().getPlayer().getVipLevel();
					} else {
						vipLevel[i] = 0;
					}
					playerWing[i] = seat.getPlayer().getSuit_wing();
					player_title[i] = seat.getPlayer().getPlayerTitle();
					qualifyingLevel[i] = seat.getPlayer().getPlayer().getHonorLevel();
					pifs = seat.getPlayer().getWeapon();
					if (pifs.getWeapSkill1() != 0) {
						ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
						skillType[2 * i] = ws.getType();
						skillLevel[2 * i] = ws.getLevel();
					} else {
						skillType[2 * i] = 0;
						skillLevel[2 * i] = 0;
					}
					if (pifs.getWeapSkill2() != 0) {
						ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
						skillType[2 * i + 1] = ws.getType();
						skillLevel[2 * i + 1] = ws.getLevel();
					} else {
						skillType[2 * i + 1] = 0;
						skillLevel[2 * i + 1] = 0;
					}
					doubleMark[i] = seat.getPlayer().isHasDoubleCard();
					if (seat.getPlayer().isVip() && seat.getPlayer().getPlayer().getVipLevel() > 3) {
						isvip[i] = true;
					} else {
						isvip[i] = false;
					}
				} else {
					playerName[i] = "";
					playerWing[i] = "";
					player_title[i] = "";
					playerEquipment.add("");
					playerEquipment.add("");
					playerEquipment.add("");
					playerEquipment.add("");
					skillType[2 * i] = 0;
					skillLevel[2 * i] = 0;
					skillType[2 * i + 1] = 0;
					skillLevel[2 * i + 1] = 0;
				}
			}
			getInBattleTeam.setSeatUsed(seatUsed);
			getInBattleTeam.setPlayerId(playerId);
			getInBattleTeam.setPlayerName(playerName);
			getInBattleTeam.setPlayerLevel(playerLevel);
			getInBattleTeam.setPlayerReady(playerReady);
			getInBattleTeam.setPlayerSex(playerSex);
			getInBattleTeam.setPlayerProficiency(playerProficiency);
			getInBattleTeam.setPlayerEquipment(playerEquipment.toArray(new String[0]));
			getInBattleTeam.setPlayerWeaponLevel(playerEquipmentLevel);
			getInBattleTeam.setVipLevel(vipLevel);
			getInBattleTeam.setPlayerWing(playerWing);
			getInBattleTeam.setPlayerTitle(player_title);
			getInBattleTeam.setQualifyingLevel(qualifyingLevel);
			getInBattleTeam.setSkillType(skillType);
			getInBattleTeam.setSkillLevel(skillLevel);
			getInBattleTeam.setZsleve(zsleve);
			getInBattleTeam.setDoublemark(doubleMark);
			getInBattleTeam.setIsVip(isvip);
			checkApplyList(room);
			getInBattleTeam.setApplyNum(room.getApplyList().size());
			getInBattleTeam.setTeamName(room.getRoomName());
			for (Seat s : seatList) {
				if (null != s.getPlayer() && !s.isRobot()) {
					s.getPlayer().sendData(getInBattleTeam);
				}
			}
		}
	}

	/**
	 * 获取用户所在位置
	 * 
	 * @param roomId
	 *            房间ID
	 * @param playerId
	 *            角色ID
	 * @return 用户所在位置
	 */
	public int getPlayerSeat(int roomId, int playerId) {
		int index = -1;
		try {
			Room room = mapRoom.get(roomId);
			Vector<Seat> seatList = room.getPlayerList();
			for (int i = 0; i < seatList.size(); i++) {
				if (null != seatList.get(i).getPlayer() && seatList.get(i).getPlayer().getId() == playerId) {
					return i;
				}
			}
			index = 0;
		} catch (Exception e) {
		}
		return index;
	}

	/**
	 * 退出房间
	 * 
	 * @param roomId
	 *            房间id
	 * @param oldseat
	 *            当前座位号
	 */
	public void exRoom(int roomId, int oldseat) {
		Room room = mapRoom == null ? null : mapRoom.get(roomId);
		if (null == room) {
			return;
		}
		synchronized (room) {
			int playerId = 0;
			Vector<Seat> seatList = room.getPlayerList();
			Seat seat = seatList.get(oldseat);
			if (null != seat.getPlayer()) {
				playerId = seat.getPlayer().getId();
				if (!seat.isRobot()) {
					seat.getPlayer().setRoomId(0);
					seat.getPlayer().sendData(new QuitRoomOk());
				}
				seat.setPlayer(null);
				seat.setReady(false);
				room.setPlayerNum(room.getPlayerNum() - 1);
			}
			if (room.isAllRobot()) {
				deletRoom(room);
			} else {
				if (playerId == room.getWnersId()) {// 如果房主退出则更新房主
					for (Seat st : seatList) {
						if (null != st.getPlayer() && !st.isRobot()) {
							room.setWnersId(st.getPlayer().getId());
						}
					}
				}
				SynRoomInfo(roomId);
			}
		}
	}

	/**
	 * 更换座位
	 * 
	 * @param roomId
	 *            房间id
	 * @param oldSeat
	 *            当前座位号
	 * @param newSeat
	 *            新座位号
	 * @throws Exception
	 */
	public void updateSeat(int roomId, int oldSeat, int newSeat) throws Exception {
		Room room = mapRoom.get(roomId);
		if (room == null)
			return;
		synchronized (room) {
			if (room != null) {
				Vector<Seat> seatList = room.getPlayerList();
				Seat nseat = seatList.get(newSeat);
				if (null != nseat.getPlayer()) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_SHP_MESSAGE);
				}
				if (!nseat.isUsed()) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_SNA_MESSAGE);
				}
				Seat oseat = seatList.get(oldSeat);
				WorldPlayer player = oseat.getPlayer();
				if (null == player) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_SNP_MESSAGE);
				}
				oseat.setPlayer(null);
				nseat.setPlayer(player);
				nseat.setRobot(oseat.isRobot());
				nseat.setReady(oseat.isReady());
				oseat.setReady(false);
			}
		}
	}

	/**
	 * 删除房间
	 * 
	 * @param room
	 */
	public void deletRoom(Room room) {
		System.out.println("DeletRoom------------" + room.getRoomId());
		mapRoom.remove(room.getRoomId());
		listRoom.remove(room);
		room = null;
	}

	/**
	 * 获取房间列表
	 * 
	 * @return
	 */
	public List<Room> getListRoom() {
		return listRoom;
	}

	/**
	 * 获取房间
	 * 
	 * @param roomId
	 * @return
	 */
	public Room getRoom(int roomId) {
		return mapRoom.get(roomId);
	}

	/**
	 * 获取房间内玩家列表(不包含机器人)
	 * 
	 * @param roomId
	 * @return
	 */
	public List<WorldPlayer> getPlayerList(int roomId) {
		List<WorldPlayer> playerList = new ArrayList<WorldPlayer>();
		Vector<Seat> seatList = mapRoom.get(roomId).getPlayerList();
		for (Seat seat : seatList) {
			if (null != seat.getPlayer() && !seat.isRobot()) {
				playerList.add(seat.getPlayer());
			}
		}
		return playerList;
	}

	/**
	 * 判断是否所有玩家都已准备
	 * 
	 * @param roomId
	 */
	public boolean isAllReady(int roomId) {
		Room room = mapRoom.get(roomId);
		if (room == null)
			return true;
		synchronized (room) {
			Vector<Seat> seatList = room.getPlayerList();
			boolean ready = true;
			for (Seat seat : seatList) {
				if (seat.isUsed() && !seat.isReady()) {
					ready = false;
				}
			}
			return ready;
		}
	}

	/**
	 * 检查玩家申请列表
	 * 
	 * @param room
	 */
	public void checkApplyList(Room room) {
		List<WorldPlayer> list = new ArrayList<WorldPlayer>();
		for (WorldPlayer wp : room.getApplyList()) {
			if (wp.getBattleId() != 0 || wp.getRoomId() != 0 || wp.getBossmapBattleId() != 0 || wp.getBossmapRoomId() != 0
					|| !wp.isPlayerInChallenge() || wp.isInSingleMap()) {
				list.add(wp);
			}
		}
		room.getApplyList().removeAll(list);
	}

	/**
	 * 玩家退出或者传建房间后推送人数通知
	 * 
	 * @param player
	 */
	public void noticeOtherPlayer(WorldPlayer player) {
		Room room;
		for (int roomId : player.getApplyList()) {
			room = getRoom(roomId);
			if (null != room && room.getBattleStatus() != 1) {
				for (Seat seat : room.getPlayerList()) {
					NoticeLeader noticeLeader = new NoticeLeader();
					checkApplyList(room);
					noticeLeader.setNum(room.getApplyList().size());
					if (null != seat.getPlayer()) {
						seat.getPlayer().sendData(noticeLeader);
					}
				}
			}
		}
		player.getApplyList().clear();
	}
}
