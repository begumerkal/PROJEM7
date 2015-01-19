package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.protocol.data.room.EnterRoomOk;
import com.wyd.empire.protocol.data.room.PlayerQuit;
import com.wyd.empire.protocol.data.room.QuitRoomOk;
import com.wyd.empire.protocol.data.room.SoundRoom;
import com.wyd.empire.protocol.data.room.UpdateSeat;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.common.util.Common;
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
public class RoomService implements Runnable {
	/** 房间ID与房间对象对应哈希表 */
	private ConcurrentHashMap<Integer, Room> mapRoom;
	/** 房间列表 */
	private List<Room> listRoom;
	/** 房间等级列表 */
	private List<Room> leaveRoom;
	/** 房间名称 */
	private String[] names = new String[]{TipMessages.ROOMNAME1, TipMessages.ROOMNAME2, TipMessages.ROOMNAME3, TipMessages.ROOMNAME4,
			TipMessages.ROOMNAME5};

	public RoomService() {
		mapRoom = new ConcurrentHashMap<Integer, Room>();
		listRoom = new Vector<Room>();
		leaveRoom = new ArrayList<Room>();
		for (int i = 0; i < 10; i++) {
			Room room = new Room((int) (999999 * Math.random()));
			leaveRoom.add(room);
			mapRoom.put(room.getRoomId(), room);
			room.setRoomName(names[ServiceUtils.getRandomNum(0, 5)]);
			room.setBattleStatus(ServiceUtils.getRandomNum(0, 2));
			room.setBattleMode(ServiceUtils.getRandomNum(1, 3));
			if (3 == room.getBattleMode()) {
				room.setPlayerNumMode(3);
				room.setStartMode(2);
			} else {
				room.setPlayerNumMode(ServiceUtils.getRandomNum(1, 4));
				room.setStartMode(ServiceUtils.getRandomNum(1, 3));
			}
			room.setPassWord(ServiceUtils.getRandomNum(-2, 0) + "");
			room.setPlayerNum(room.getPlayerNumMode() * room.getStartMode());
			room.setLeaveRoom(true);
		}
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("RoomService-Thread");
		t.start();
	}

	@Override
	public void run() {
		RoomService rooMservice = ServiceManager.getManager().getRoomService();
		RobotService robotService = ServiceManager.getManager().getRobotService();
		while (true) {
			try {
				Thread.sleep(ServiceManager.getManager().getVersionService().getVersion().getRoomRefreshTime() * 1000);
				for (Room room : leaveRoom) {
					room.setRoomName(names[ServiceUtils.getRandomNum(0, 5)]);
					room.setBattleStatus(ServiceUtils.getRandomNum(0, 2));
					if (ServiceManager.getManager().getConfiguration().getBoolean("jinjimoshi")) {
						room.setBattleMode(ServiceUtils.getRandomNum(1, 4));
					} else {
						room.setBattleMode(ServiceUtils.getRandomNum(1, 3));
					}
					if (3 == room.getBattleMode()) {
						room.setPlayerNumMode(3);
						room.setStartMode(2);
					} else {
						room.setPlayerNumMode(ServiceUtils.getRandomNum(1, 4));
						room.setStartMode(ServiceUtils.getRandomNum(1, 3));
					}
					room.setPassWord(ServiceUtils.getRandomNum(-2, 0) + "");
					room.setPlayerNum(room.getPlayerNumMode() * room.getStartMode());
				}
				Room room;
				for (int i = listRoom.size() - 1; i >= 0; i--) {
					room = listRoom.get(i);
					// 没密码，房间在等待中，不是新手房间，战斗模式为竞技、复活、混战，为本服战斗
					if (room.getPassWord().equals("-1") && Room.BATTLE_STATUS_WAIT == room.getBattleStatus() && !room.isNewPlayerRoom()
							&& room.getBattleMode() < 4 && room.getServiceMode() == 0) {
						ServiceManager.getManager().getSimpleThreadPool().execute(createTask(room, rooMservice, robotService));
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private Runnable createTask(Room room, RoomService rooMservice, RobotService robotService) {
		return new RoomThread(room, rooMservice, robotService);
	}

	public class RoomThread implements Runnable {
		private Room room;
		private RoomService rooMservice;
		private RobotService robotService;

		public RoomThread(Room room, RoomService rooMservice, RobotService robotService) {
			this.room = room;
			this.rooMservice = rooMservice;
			this.robotService = robotService;
		}

		@Override
		public void run() {
			if (Room.BATTLE_STATUS_WAIT == room.getBattleStatus()) {
				int random = ServiceUtils.getRandomNum(0, 100);
				if (random < ServiceManager.getManager().getVersionService().getVersion().getRerp()) {
					// 如果有空位置则随机加入机器人
					if (room.getPlayerNum() < room.getPlayerNumMode() * room.getStartMode()) {
						Set<String> idMap = new HashSet<String>();
						int count = 0;
						int fighting = 0;
						for (Seat seat : room.getPlayerList()) {
							if (null != seat.getPlayer()) {
								idMap.add(seat.getPlayer().getPlayer().getId().toString());
								fighting += seat.getPlayer().getFighting();
								count++;
							}
						}
						if (count > 0)
							fighting = fighting / count;
						try {
							WorldPlayer worldPlayer = robotService.getRobot(room.getAvgLevel(), room.getAvgFighting(), idMap,
									room.getRoomChannel());
							int result = rooMservice.enRoom(room.getRoomId(), worldPlayer, true);
							if (result > -1) {
								SynRoomInfo(room.getRoomId());
							}
						} catch (Exception e) {
						}
						idMap = null;
					}
				} else {
					Seat seat = room.getPlayerList().get(getPlayerSeat(room.getRoomId(), room.getWnersId()));
					if (seat.isRobot()) {
						rooMservice.exRoom(room.getRoomId(), seat.getSeatIndex(), 0);
					} else {
						random = ServiceUtils.getRandomNum(0, room.getPlayerList().size());
						seat = room.getPlayerList().get(random);
						if (seat.isRobot() && null != seat.getPlayer()) {
							rooMservice.exRoom(room.getRoomId(), seat.getSeatIndex(), 0);
						}
					}
				}
			}
			// 清理死房间
			if (System.currentTimeMillis() - room.getCreateTime() > 10000) {
				boolean deadRoom = true;
				for (Seat seat : room.getPlayerList()) {
					if (!seat.isRobot() && null != seat.getPlayer()
							&& null != ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(seat.getPlayer().getId())) {
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
	 * @param serviceMode
	 *            0本服对战，1跨服对战
	 * @param isNewPlayerRoom
	 *            是否新手快速房间
	 * @param eventMode
	 *            是否开启特殊事件
	 * @return
	 */
	public int createRoom(WorldPlayer player, String roomName, int battleMode, int playerNumMode, String passWord, int startMode,
			int roomType, int serviceMode, boolean isNewPlayerRoom, boolean eventMode) {
		// 对战人数模式取值范围1-3
		playerNumMode = playerNumMode > 3 ? 3 : playerNumMode;
		playerNumMode = playerNumMode < 0 ? 1 : playerNumMode;
		Room room = new Room();
		Vector<Seat> seatList = new Vector<Seat>();
		Seat seat;
		if (1 == startMode) {
			while (seatList.size() < playerNumMode) {// 创建座位信息
				seat = new Seat();
				seat.setUsed(true);
				seat.setCamp(0);
				seat.setSeatIndex(seatList.size());
				seatList.add(seat);
			}
			while (seatList.size() < 6) {
				seat = new Seat();
				seat.setUsed(false);
				seat.setCamp(1);
				seat.setSeatIndex(seatList.size());
				seatList.add(seat);
			}
		} else {
			if (battleMode != 3) {// 1、竞技模式，2、复活模式
				while (seatList.size() < (playerNumMode * 2)) {// 创建座位信息
					seat = new Seat();
					if (seatList.size() < playerNumMode) {// 前半部分座位为同一个阵营
						seat.setCamp(0);
					} else {
						seat.setCamp(1);
					}
					seat.setUsed(true);
					seatList.add(seat);
				}
				while (seatList.size() < 6) {
					int index = seatList.size() / 2;
					seat = new Seat();
					seat.setUsed(false);
					seat.setCamp(2);
					seatList.add(index, seat);
					seat = new Seat();
					seat.setUsed(false);
					seat.setCamp(2);
					seatList.add(seat);
				}
				for (int i = 0; i < 6; i++) {
					seat = seatList.get(i);
					seat.setSeatIndex(i);
				}
			} else {// 3、混战模式
				for (int i = 0; i < 6; i++) {
					seat = new Seat();
					seat.setUsed(true);
					seat.setCamp(i);
					seat.setSeatIndex(i);
					seatList.add(seat);
				}
				playerNumMode = 3;
				startMode = 2;
			}
		}
		// if (player.isNewPlayer() && battleMode == 1 && playerNumMode == 1 &&
		// startMode == 1) {// 新手教程房间指定为沙漠桥
		// int[] mapIds = new int[] { 2, 3, 12, 13};
		// room.setMapId(mapIds[ServiceUtils.getRandomNum(0, 4)]);
		// room.setNewPlayerRoom(true);
		// } else {
		// room.setMapId(ServiceManager.getManager().getMapsService().getRandomId());
		// }
		room.setMapId(ServiceManager.getManager().getMapsService().getRandomId());
		room.setRoomChannel(player.getBattleChannel());
		room.setPlayerList(seatList);
		room.setHonorLevel(player.getPlayer().getHonorLevel());
		room.setRoomName(roomName);
		room.setBattleMode(battleMode);
		room.setServiceMode(serviceMode);
		room.setPlayerNumMode(playerNumMode);
		room.setPassWord(passWord);
		room.setWnersId(player.getId());
		room.setStartMode(startMode);
		room.setRoomType(roomType);
		room.setNewPlayerRoom(isNewPlayerRoom);
		room.setEventMode(eventMode);
		mapRoom.put(room.getRoomId(), room);
		listRoom.add(room);
		return room.getRoomId();
	}

	/**
	 * 更新房间信息
	 * 
	 * @param room
	 * @throws Exception
	 */
	public void updateRoom(Room room) throws Exception {
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
								updateSeat.setRoomId(room.getRoomId());
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
							updateSeat.setRoomId(room.getRoomId());
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

	/**
	 * 进入房间
	 * 
	 * @param roomId
	 *            房间id
	 * @param player
	 * @throws Exception
	 * @return 返回座位号 ;负数表示进入房间错误
	 */
	public int enRoom(int roomId, WorldPlayer player, boolean isRobot) {
		Room room = mapRoom == null ? null : mapRoom.get(roomId);
		if (null == room) {
			return -1;
		}
		if (1 == room.getBattleStatus()) {
			return -2;
		}
		if (room.isFull()) {
			return -3;
		}
		if (room.getBattleMode() == 5) {
			WorldPlayer owner = ServiceManager.getManager().getPlayerService().getWorldPlayerById(room.getWnersId());
			if (owner.getGuildId() != player.getGuildId()) {
				return -4;
			}
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
		if (!isRobot) {
			player.setRoomId(room.getRoomId());
			// 发送语音房间相关协议
			SoundRoom soundRoom = new SoundRoom();
			soundRoom.setSeverId(Server.config.getServerId());
			soundRoom.setRoomId(room.getRoomId());
			soundRoom.setLocation(1);
			soundRoom.setMark(0);
			player.sendData(soundRoom);
		}
		// ServiceUtils.out("player:"+player.getId()+"-----------EnterRoom:-----------"+roomId);
		return index;
	}

	/**
	 * 同步房间状态
	 * 
	 * @param roomId
	 */
	public void SynRoomInfo(int roomId) {
		Room room = getRoom(roomId);
		Vector<Seat> seatList = room.getPlayerList();
		for (Seat seat : seatList) {
			if (null != seat.getPlayer() && (seat.isRobot() || seat.getPlayer().getId() == room.getWnersId())) {
				seat.setReady(true);
			}
		}
		EnterRoomOk enterRoomOk = new EnterRoomOk();
		enterRoomOk.setRoomId(roomId);
		enterRoomOk.setBattleMode(room.getBattleMode());
		enterRoomOk.setServiceMode(room.getServiceMode());
		enterRoomOk.setRoomChannel(room.getRoomChannel());
		enterRoomOk.setPlayerNumMode(room.getPlayerNumMode());
		enterRoomOk.setMapId(room.getMapId());
		enterRoomOk.setWnersId(room.getWnersId());
		enterRoomOk.setStartMode(room.getStartMode());
		enterRoomOk.setEventMode(room.getEventMode());
		int playerNum = seatList.size();
		enterRoomOk.setPlayerNum(playerNum);
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
		enterRoomOk.setSeatUsed(seatUsed);
		enterRoomOk.setPlayerId(playerId);
		enterRoomOk.setPlayerName(playerName);
		enterRoomOk.setPlayerLevel(playerLevel);
		enterRoomOk.setPlayerReady(playerReady);
		enterRoomOk.setPlayerSex(playerSex);
		enterRoomOk.setPlayerProficiency(playerProficiency);
		enterRoomOk.setPlayerEquipment(playerEquipment.toArray(new String[0]));
		enterRoomOk.setPlayerEquipmentLevel(playerEquipmentLevel);
		enterRoomOk.setVipLevel(vipLevel);
		enterRoomOk.setPlayerWing(playerWing);
		enterRoomOk.setPlayer_title(player_title);
		enterRoomOk.setQualifyingLevel(qualifyingLevel);
		enterRoomOk.setSkillType(skillType);
		enterRoomOk.setSkillLevel(skillLevel);
		enterRoomOk.setZsleve(zsleve);
		enterRoomOk.setDoubleMark(doubleMark);
		enterRoomOk.setIsvip(isvip);
		for (Seat s : seatList) {
			if (null != s.getPlayer() && !s.isRobot()) {
				s.getPlayer().sendData(enterRoomOk);
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
	public void exRoom(int roomId, int oldseat, int currentPlayerId) {
		Room room = mapRoom == null ? null : mapRoom.get(roomId);
		if (null == room) {
			return;
		}
		int playerId = 0;
		Vector<Seat> seatList = room.getPlayerList();
		Seat seat = seatList.get(oldseat);
		if (null != seat.getPlayer()) {
			playerId = seat.getPlayer().getId();
			if (!seat.isRobot()) {
				seat.getPlayer().setRoomId(0);
				QuitRoomOk quitRoomOk = new QuitRoomOk();
				quitRoomOk.setMark(playerId != currentPlayerId && 0 != currentPlayerId);
				seat.getPlayer().sendData(quitRoomOk);
				// 发送语音房间相关协议
				SoundRoom soundRoom = new SoundRoom();
				soundRoom.setSeverId(Server.config.getServerId());
				soundRoom.setRoomId(roomId);
				soundRoom.setLocation(2);
				soundRoom.setMark(0);
				seat.getPlayer().sendData(soundRoom);
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

	/**
	 * 删除房间
	 * 
	 * @param room
	 */
	public void deletRoom(Room room) {
		// System.out.println("DeletRoom------------" + room.getRoomId());
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
		Room room = mapRoom.get(roomId);
		if (null == room) {
			room = ServiceManager.getManager().getChallengeService().getRoom(roomId);
		}
		return room;
	}

	/**
	 * 获取玩家信息
	 * 
	 * @param roomId
	 * @param playerId
	 * @return
	 */
	public Seat getSeat(int roomId, int playerId) {
		Room room = getRoom(roomId);
		if (null == room) {
			room = ServiceManager.getManager().getChallengeService().getRoom(roomId);
		}
		if (null == room)
			return null;
		for (Seat seat : room.getPlayerList()) {
			if (null != seat.getPlayer() && seat.getPlayer().getId() == playerId) {
				return seat;
			}
		}
		return null;
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
		Vector<Seat> seatList = room.getPlayerList();
		boolean ready = true;
		for (Seat seat : seatList) {
			if (seat.isUsed() && !seat.isReady()) {
				ready = false;
			}
		}
		return ready;
	}

	/**
	 * 获取假房间列表
	 * 
	 * @return
	 */
	public List<Room> getLeaveRoom() {
		return leaveRoom;
	}

	/**
	 * 检查两间房间中是否存在相同的玩家
	 * 
	 * @param roomx
	 * @param roomy
	 * @return true是,false否
	 */
	public boolean checkPlayerInRoom(Room roomx, Room roomy) {
		Vector<Seat> seatListX = roomx.getPlayerList();
		Vector<Seat> seatListY = roomy.getPlayerList();
		StringBuffer robotIdx = new StringBuffer(",");
		for (Seat seat : seatListX) {
			if (null != seat.getPlayer()) {
				robotIdx.append(seat.getPlayer().getId());
				robotIdx.append(",");
			}
		}
		for (Seat seat : seatListY) {
			if (null != seat.getPlayer() && robotIdx.indexOf("," + seat.getPlayer().getId() + ",") >= 0) {
				return true;
			}
		}
		return false;
	}
}
