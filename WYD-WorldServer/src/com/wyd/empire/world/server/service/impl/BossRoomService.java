package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import com.wyd.empire.protocol.data.bossmaproom.EnterRoomOk;
import com.wyd.empire.protocol.data.bossmaproom.PlayerQuit;
import com.wyd.empire.protocol.data.bossmaproom.QuitRoomOk;
import com.wyd.empire.protocol.data.bossmaproom.UpdateSeat;
import com.wyd.empire.protocol.data.room.SoundRoom;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.bossmaproom.BossSeat;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 房间数据管理
 * 
 * @author Administrator
 */
public class BossRoomService implements Runnable {
	private ConcurrentHashMap<Integer, BossRoom> mapRoom;
	private Vector<BossRoom> listRoom;
	private List<BossRoom> leaveRoom;
	String[] names = new String[]{TipMessages.BOSSROOMNAME1, TipMessages.BOSSROOMNAME2, TipMessages.BOSSROOMNAME3,
			TipMessages.BOSSROOMNAME4, TipMessages.BOSSROOMNAME5};
	int[] maps = new int[]{22, 23, 28, 37, 38};

	public BossRoomService() {
		mapRoom = new ConcurrentHashMap<Integer, BossRoom>();
		listRoom = new Vector<BossRoom>();
		leaveRoom = new ArrayList<BossRoom>();
		for (int i = 0; i < 10; i++) {
			BossRoom room = new BossRoom((int) (999999 * Math.random()));
			leaveRoom.add(room);
			mapRoom.put(room.getRoomId(), room);
			room.setRoomShortName(names[ServiceUtils.getRandomNum(0, names.length)]);
			room.setPlayerNumMode(3);
			room.setPlayerNum(3);
			room.setDifficulty(ServiceUtils.getRandomNum(1, 4));
			room.setMapId(maps[ServiceUtils.getRandomNum(0, maps.length)]);
			if (room.getPlayerNumMode() != 3) {
				room.setBattleStatus(1);
			} else {
				room.setBattleStatus(ServiceUtils.getRandomNum(0, 2));
			}
			room.setPassWord("-1");
			room.setLeaveRoom(true);
		}
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("BossRoomService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10000);
				for (BossRoom room : leaveRoom) {
					room.setRoomShortName(names[ServiceUtils.getRandomNum(0, names.length)]);
					room.setBattleStatus(ServiceUtils.getRandomNum(0, 2));
					// room.setPlayerNumMode(3);
					room.setPassWord("-1");
					// room.setPlayerNum(room.getPlayerNumMode());
				}
				for (BossRoom room : listRoom) {
					if (room.getPlayerList().size() == 0) {
						deletRoom(room);
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建房间并返回房间的id
	 * 
	 * @param player
	 *            房间创建人
	 * @param map
	 *            副本地图
	 * @param playerNumMode
	 *            对战人数模式，1=1v1，2=2v2，3=3v3
	 * @param passWord
	 *            若为-1则无密码
	 * @param bossmap_serial
	 *            副本序列
	 * @param difficulty
	 *            副本难度0=普通,1=困难,2=地狱
	 * @return
	 */
	public int createRoom(WorldPlayer player, com.wyd.empire.world.bean.Map map, int playerNumMode, String passWord, int bossmap_serial,
			int difficulty) {
		BossRoom room = new BossRoom();
		List<BossSeat> seatList = new ArrayList<BossSeat>();
		BossSeat seat;
		if (1 == playerNumMode) {
			seat = new BossSeat();
			seat.setCamp(0);
			seat.setUsed(true);
			seat.setSeatIndex(seatList.size());
			seatList.add(seat);
			while (seatList.size() < 6) {
				seat = new BossSeat();
				seat.setCamp(0);
				seat.setUsed(false);
				seat.setSeatIndex(seatList.size());
				seatList.add(seat);
			}
		} else {
			while (seatList.size() < 3) {// 创建座位信息
				seat = new BossSeat();
				seat.setCamp(0);
				seat.setUsed(true);
				seatList.add(seat);
			}
			while (seatList.size() < 6) {// 创建空座位信息
				seat = new BossSeat();
				seat.setCamp(0);
				seat.setUsed(false);
				seatList.add(seat);
			}
		}
		room.setMapId(map.getId());
		room.setPlayerList(seatList);
		room.setRoomName(map.getName());
		room.setPlayerNumMode(playerNumMode);
		room.setPassWord(passWord);
		room.setWnersId(player.getId());
		room.setProgress(bossmap_serial);
		room.setTimes(map.getTimes());
		room.setLevel(map.getLevel());
		room.setDifficulty(difficulty);
		// 2.1修改内容 通关副本后需要进行更新
		room.setRoomShortName(map.getNameShort());
		room.setReward(map.getReward());
		room.setMapType(map.getMapType());
		room.setMapIcon(map.getMapIcon());
		room.setGuaiList(map.getGuaiList());
		room.setRunTime1(map.getRunTime1());
		room.setRunTime2(map.getRunTime2());
		room.setRunTime3(map.getRunTime3());
		room.setNpcNumber(map.getNpcNumber());
		room.setAnimationIndexCode(map.getAnimationIndexCode());
		room.setBossmapSerial(map.getBossmapSerial());
		room.setMapVigor(map.getVitalityExpend());
		if (room.getPlayerNumMode() > 1) {
			room.setBattleMode(2);
		} else {
			room.setBattleMode(1);
		}
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
	public void updateRoom(BossRoom room) throws Exception {
		synchronized (room) {
			BossSeat seat;
			for (int i = 0; i < 3; i++) {
				seat = room.getPlayerList().get(i);
				if (i < room.getPlayerNumMode()) {
					seat.setUsed(true);
				} else {
					seat.setUsed(false);
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
							room.getPlayerList().get(extSeat).getPlayer().setBossmapRoomId(0);
							room.getPlayerList().get(extSeat).getPlayer().sendData(new QuitRoomOk());
							room.getPlayerList().get(extSeat).setPlayer(null);
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
						for (BossSeat st : room.getPlayerList()) {
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
		BossRoom room = mapRoom.get(roomId);
		if (null == room) {
			throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_NOTFOUND_MESSAGE);
		}
		synchronized (room) {

			if (1 == room.getBattleStatus()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_BHS_MESSAGE);
			}
			if (room.isFull(room.getPlayerNumMode()) || room.isLeaveRoom()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_FULL_MESSAGE);
			}
			if (room.getProgress() > player.getBossmap_progress()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_OPEN_MESSAGE);
			}
			// 校验等级
			if (room.getLevel() > player.getLevel()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_LEVEL_MESSAGE);
			}
			PlayerBossmap playerBossmap = ServiceManager.getManager().getPlayerBossmapService()
					.loadPlayerBossMap(player.getId(), room.getMapId());
			int start = 0;
			if (null != playerBossmap) {
				start = playerBossmap.getStar();
			}
			if (start < room.getDifficulty() - 1) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.BOSS_DIFFICULTY_HARD);
			}
			List<BossSeat> seatList = room.getPlayerList();
			int index = -1;
			for (BossSeat seat : seatList) {
				if (null == seat.getPlayer() && seat.isUsed()) {
					index = seat.getSeatIndex();
					seat.setPlayer(player);
					room.setPlayerNum(room.getPlayerNum() + 1);
					if (!isRobot) {
						player.setBossmapRoomId(room.getRoomId());
						// 发送语音房间相关协议
						SoundRoom soundRoom = new SoundRoom();
						soundRoom.setSeverId(Server.config.getServerId());
						soundRoom.setRoomId(room.getRoomId());
						soundRoom.setLocation(1);
						soundRoom.setMark(1);
						player.sendData(soundRoom);
					}
					break;
				}
			}
			// ServiceUtils.out("player:" + player.getId() +
			// "-----------EnterBossRoom:-----------" + roomId);
			return index;
		}
	}

	/**
	 * 同步房间状态
	 * 
	 * @param roomId
	 */
	public void SynRoomInfo(int roomId) {
		BossRoom room = getRoom(roomId);
		synchronized (room) {
			List<BossSeat> seatList = room.getPlayerList();
			for (BossSeat seat : seatList) {
				if (null != seat.getPlayer() && (seat.getPlayer().getId() == room.getWnersId())) {
					seat.setReady(true);
				}
			}
			EnterRoomOk enterRoomOk = new EnterRoomOk();
			enterRoomOk.setRoomId(roomId);
			enterRoomOk.setPlayerNumMode(room.getPlayerNumMode());
			enterRoomOk.setMapId(room.getMapId());
			enterRoomOk.setWnersId(room.getWnersId());
			int playerNum = seatList.size();
			enterRoomOk.setPlayerNum(playerNum);
			boolean[] seatUsed = new boolean[playerNum];
			int[] playerId = new int[playerNum];
			String[] playerName = new String[playerNum];
			int[] playerLevel = new int[playerNum];
			boolean[] playerReady = new boolean[playerNum];
			int[] playerSex = new int[playerNum];
			List<String> playerEquipment = new ArrayList<String>();
			int[] playerProficiency = new int[playerNum];
			int[] playerEquipmentLevel = new int[playerNum];
			int[] vipLevel = new int[playerNum];
			String[] playerWing = new String[playerNum];
			String[] player_title = new String[playerNum];
			int[] qualifyingLevel = new int[playerNum];
			int[] skillType = new int[playerNum * 2];
			int[] skillLevel = new int[playerNum * 2];
			int[] zsleve = new int[playerNum];
			int[] playerStar = new int[playerNum];
			boolean[] doubleMark = new boolean[playerNum];
			boolean[] isvip = new boolean[playerNum];
			BossSeat seat;
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
					playerStar[i] = ServiceManager.getManager().getPlayerBossmapService()
							.loadPlayerBossMap(seat.getPlayer().getId(), room.getMapId()).getStar();
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
					playerStar[i] = -1;
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
			enterRoomOk.setMapName(room.getRoomName());
			enterRoomOk.setMapShortName(room.getRoomShortName());
			enterRoomOk.setPlayerEquipmentLevel(playerEquipmentLevel);
			enterRoomOk.setVipLevel(vipLevel);
			enterRoomOk.setPlayerWing(playerWing);
			enterRoomOk.setPlayer_title(player_title);
			enterRoomOk.setQualifyingLevel(qualifyingLevel);
			enterRoomOk.setSkillLevel(skillLevel);
			enterRoomOk.setSkillType(skillType);
			enterRoomOk.setZsleve(zsleve);
			enterRoomOk.setDoubleMark(doubleMark);
			enterRoomOk.setDifficulty(room.getDifficulty());
			enterRoomOk.setPlayerStar(playerStar);
			enterRoomOk.setIsvip(isvip);
			enterRoomOk.setPoints(room.getPoints());
			for (BossSeat s : seatList) {
				if (null != s.getPlayer()) {
					s.getPlayer().sendData(enterRoomOk);
				}
			}
		}
	}

	/**
	 * 或用户所在位置
	 * 
	 * @param roomId
	 * @param playerId
	 * @return
	 */
	public int getPlayerSeat(int roomId, int playerId) {
		int index = -1;
		try {
			BossRoom room = mapRoom.get(roomId);
			List<BossSeat> seatList = room.getPlayerList();
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
	public void extRoom(int roomId, int oldseat, int currentPlayerId) {
		BossRoom room = mapRoom == null ? null : mapRoom.get(roomId);
		if (null == room) {
			return;
		}
		synchronized (room) {
			int playerId = 0;
			List<BossSeat> seatList = room.getPlayerList();
			BossSeat seat = seatList.get(oldseat);
			if (null != seat.getPlayer()) {
				playerId = seat.getPlayer().getId();
				// ServiceUtils.out("player:" + playerId + "-----------ExtRoom:"
				// + room.getPlayerNum());
				seat.getPlayer().setBossmapRoomId(0);
				QuitRoomOk quitRoomOk = new QuitRoomOk();
				quitRoomOk.setMark(playerId != currentPlayerId && 0 != currentPlayerId);
				seat.getPlayer().sendData(quitRoomOk);

				// 发送语音房间相关协议
				SoundRoom soundRoom = new SoundRoom();
				soundRoom.setSeverId(Server.config.getServerId());
				soundRoom.setRoomId(roomId);
				soundRoom.setLocation(2);
				soundRoom.setMark(1);
				seat.getPlayer().sendData(soundRoom);

				seat.setPlayer(null);
				seat.setReady(false);
				room.setPlayerNum(room.getPlayerNum() - 1);
			}
			if (0 >= room.getPlayerNum()) {
				deletRoom(room);
			} else {
				if (playerId == room.getWnersId()) {// 如果房主退出则更新房主
					for (BossSeat st : seatList) {
						if (null != st.getPlayer()) {
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
		BossRoom room = mapRoom == null ? null : mapRoom.get(roomId);
		if (null == room) {
			return;
		}
		synchronized (room) {
			List<BossSeat> seatList = room.getPlayerList();
			BossSeat nseat = seatList.get(newSeat);
			if (null != nseat.getPlayer()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_SHP_MESSAGE);
			}
			if (!nseat.isUsed()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_SNA_MESSAGE);
			}
			WorldPlayer player = seatList.get(oldSeat).getPlayer();
			if (null == player) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_SNP_MESSAGE);
			}
			BossSeat oseat = seatList.get(oldSeat);
			oseat.setPlayer(null);
			nseat.setPlayer(player);
			if (oseat.isReady()) {
				oseat.setReady(false);
				nseat.setReady(true);
			}
		}
	}

	/**
	 * 删除房间
	 * 
	 * @param room
	 */
	public void deletRoom(BossRoom room) {
		// ServiceUtils.out("DeletRoom:" + room.getRoomId());
		mapRoom.remove(room.getRoomId());
		listRoom.remove(room);
		room = null;
	}

	/**
	 * 获取房间列表
	 * 
	 * @return
	 */
	public Vector<BossRoom> getListRoom() {
		return listRoom;
	}

	/**
	 * 获取房间
	 * 
	 * @param roomId
	 * @return
	 */
	public BossRoom getRoom(int roomId) {
		return mapRoom.get(roomId);
	}

	/**
	 * 获取房间内玩家列表
	 * 
	 * @param roomId
	 * @return
	 */
	public List<WorldPlayer> getPlayerList(int roomId) {
		List<WorldPlayer> playerList = new ArrayList<WorldPlayer>();
		List<BossSeat> seatList = mapRoom.get(roomId).getPlayerList();
		for (BossSeat seat : seatList) {
			if (null != seat.getPlayer()) {
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
		BossRoom room = mapRoom == null ? null : mapRoom.get(roomId);
		if (null == room) {
			return true;
		}
		synchronized (room) {
			List<BossSeat> seatList = room.getPlayerList();
			boolean ready = true;
			for (BossSeat seat : seatList) {
				if (seat.isUsed() && !seat.isReady() && null != seat.getPlayer()) {
					ready = false;
				}
			}
			return ready;
		}
	}

	/**
	 * 获取假房间列表
	 * 
	 * @return
	 */
	public List<BossRoom> getLeaveRoom() {
		return leaveRoom;
	}
}
