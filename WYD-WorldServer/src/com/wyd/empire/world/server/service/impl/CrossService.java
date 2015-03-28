package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.protocol.data.battle.Hurt;
import com.wyd.empire.protocol.data.battle.PositionsInMap;
import com.wyd.empire.protocol.data.battle.Shoot;
import com.wyd.empire.protocol.data.battle.SkillEquip;
import com.wyd.empire.protocol.data.chat.ReceiveMessage;
import com.wyd.empire.protocol.data.cross.CrossBeFrozen;
import com.wyd.empire.protocol.data.cross.CrossBigSkill;
import com.wyd.empire.protocol.data.cross.CrossChat;
import com.wyd.empire.protocol.data.cross.CrossEndCurRound;
import com.wyd.empire.protocol.data.cross.CrossFinishLoading;
import com.wyd.empire.protocol.data.cross.CrossFly;
import com.wyd.empire.protocol.data.cross.CrossGetAttackSequence;
import com.wyd.empire.protocol.data.cross.CrossHurt;
import com.wyd.empire.protocol.data.cross.CrossLoadingPercent;
import com.wyd.empire.protocol.data.cross.CrossOutOfScene;
import com.wyd.empire.protocol.data.cross.CrossPair;
import com.wyd.empire.protocol.data.cross.CrossPass;
import com.wyd.empire.protocol.data.cross.CrossPlayerInfo;
import com.wyd.empire.protocol.data.cross.CrossPlayerLose;
import com.wyd.empire.protocol.data.cross.CrossPlayerMove;
import com.wyd.empire.protocol.data.cross.CrossPositionsInMap;
import com.wyd.empire.protocol.data.cross.CrossQuitBattle;
import com.wyd.empire.protocol.data.cross.CrossRebornPosition;
import com.wyd.empire.protocol.data.cross.CrossShakeHands;
import com.wyd.empire.protocol.data.cross.CrossShoot;
import com.wyd.empire.protocol.data.cross.CrossSkillEquip;
import com.wyd.empire.protocol.data.cross.CrossStartLoading;
import com.wyd.empire.protocol.data.cross.CrossStartNewTimer;
import com.wyd.empire.protocol.data.cross.CrossUseFly;
import com.wyd.empire.protocol.data.cross.CrossUsingFace;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.skeleton.BattleSkeleton;
import com.wyd.protocol.data.AbstractData;

/**
 * 跨服服务
 * 
 * @author zguoqiu
 */
public class CrossService {

	private ConcurrentHashMap<Integer, PlayerInfo> playerInfoMap = new ConcurrentHashMap<Integer, PlayerInfo>();
	private ConcurrentHashMap<String, Integer> crossPlayerIdMap = new ConcurrentHashMap<String, Integer>();

	public void send(AbstractData data) {
		BattleSkeleton battleSkeleton = ServiceManager.getManager().getBattleSkeleton();
		if (null != battleSkeleton) {
			battleSkeleton.send(data);
		}
	}

	/**
	 * 跨服配对
	 * 
	 * @param room
	 */
	public void sendPairInfo(Room room) {
		CrossPair crossPair = new CrossPair();
		crossPair.setRoomId(room.getRoomId());
		crossPair.setBattleMode(room.getBattleMode());
		crossPair.setPlayerNum(room.getPlayerNum());
		crossPair.setRoomChannel(room.getRoomChannel());
		if (6 != room.getBattleMode()) {
			crossPair.setAverageFighting(room.getAvgFighting());
		} else {
			crossPair.setAverageFighting(room.getAvgIntegral());
		}
		crossPair.setVersion(WorldServer.config.getVersion());
		send(crossPair);
	}

	/**
	 * 跨服加载
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void startLoading(int battleId, int playerId) {
		CrossStartLoading startLoading = new CrossStartLoading();
		startLoading.setBattleId(battleId);
		startLoading.setPlayerId(playerId);
		send(startLoading);
	}

	/**
	 * 通知服务器地图可用初始坐标
	 * 
	 * @param positionsInMap
	 * @param playerId
	 */
	public void positionsInMap(PositionsInMap positionsInMap, int playerId) {
		CrossPositionsInMap pMap = new CrossPositionsInMap();
		pMap.setPlayerId(playerId);
		pMap.setBattleId(positionsInMap.getBattleId());
		pMap.setPostionCount(positionsInMap.getPostionCount());
		pMap.setPostionX(positionsInMap.getX());
		pMap.setPostionY(positionsInMap.getY());
		send(pMap);
	}

	/**
	 * 请求攻击顺序
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void requestAttackSequence(int battleId, int playerId) {
		CrossGetAttackSequence cgas = new CrossGetAttackSequence();
		cgas.setBattleId(battleId);
		cgas.setPlayerId(playerId);
		send(cgas);
	}

	/**
	 * 通知服务器资源加载完成
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void finishLoading(int battleId, int playerId) {
		CrossFinishLoading cfl = new CrossFinishLoading();
		cfl.setBattleId(battleId);
		cfl.setPlayerId(playerId);
		send(cfl);
	}

	/**
	 * 开始新一回合操作
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void startNewTimer(int battleId, int playerId) {
		CrossStartNewTimer csnt = new CrossStartNewTimer();
		csnt.setBattleId(battleId);
		csnt.setPlayerId(playerId);
		send(csnt);
	}

	/**
	 * 解释本回合操作
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void endCurRound(int battleId, int playerId, int currentPlayerId) {
		CrossEndCurRound cecr = new CrossEndCurRound();
		cecr.setBattleId(battleId);
		cecr.setPlayerId(playerId);
		cecr.setCurrentPlayerId(currentPlayerId);
		send(cecr);
	}

	/**
	 * 玩家移动
	 * 
	 * @param battleId
	 * @param playerId
	 * @param movecount
	 * @param movestep
	 * @param curPositionX
	 * @param curPositionY
	 * @param movecountFloat
	 */
	public void playerMove(int battleId, int playerId, int movecount, byte[] movestep, int curPositionX, int curPositionY,
			float movecountFloat) {
		CrossPlayerMove cpm = new CrossPlayerMove();
		cpm.setBattleId(battleId);
		cpm.setPlayerId(playerId);
		cpm.setMovecount(movecount);
		cpm.setMovestep(movestep);
		cpm.setCurPositionX(curPositionX);
		cpm.setCurPositionY(curPositionY);
		cpm.setMovecountFloat((int) (movecountFloat * 100));
		send(cpm);
	}

	/**
	 * 玩家使用道具技能
	 * 
	 * @param skillEquip
	 */
	public void skillEquip(SkillEquip skillEquip) {
		int battleId = skillEquip.getBattleId();
		int playerId = skillEquip.getPlayerId();
		int itemcount = skillEquip.getItemcount();
		int[] itmeIds = skillEquip.getItmeIds();
		int tiredValue = 0;
		int consumePower = 0;
		int selfAddHP = 0;
		List<Integer> playerIds = new ArrayList<Integer>();
		List<Integer> allAddHP = new ArrayList<Integer>();
		List<Tools> toolsList = ServiceManager.getManager().getToolsService().getToolsListByIds(itmeIds);
		List<Integer> toolsType = new ArrayList<Integer>();
		List<Integer> toolsSubType = new ArrayList<Integer>();
		List<Integer> toolsParam1 = new ArrayList<Integer>();
		List<Integer> toolsParam2 = new ArrayList<Integer>();
		boolean completed = false;
		if (isThisServerPlayer(playerId)) {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(getPlayerId(playerId));
			if (null == player) {
				return;
			}
			Room room = ServiceManager.getManager().getRoomService().getRoom(getRoomId(playerId));
			if (null == room) {
				return;
			}
			Seat playerSeat = null;
			for (Seat seat : room.getPlayerList()) {
				if (null != seat.getPlayer()
						&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) == playerId) {
					playerSeat = seat;
				}
			}
			if (null == playerSeat) {
				return;
			}
			for (Tools tools : toolsList) {
				ServiceManager.getManager().getLogSerivce().updatePropsSkillLog(tools);
				tiredValue += tools.getTireValue();
				consumePower += tools.getConsumePower();
				toolsType.add(tools.getType());
				toolsSubType.add(tools.getSubtype());
				toolsParam1.add(tools.getParam1());
				toolsParam2.add(tools.getParam2());
				if (1 == tools.getType().intValue()) {
					switch (tools.getSubtype().intValue()) {
						case 0 :
							selfAddHP = tools.getParam1();
							// 公会技能加成
							selfAddHP = (int) ServiceManager.getManager().getBuffService().getAddition(player, selfAddHP, Buff.CTREAT);
							selfAddHP = (int) ServiceManager.getManager().getBuffService().getAddition(player, selfAddHP, Buff.CADDHP);
							break;
						case 1 :
							for (Seat seat : room.getPlayerList()) {
								if (null != seat.getPlayer()) {
									int chp = tools.getParam1();
									chp = (int) ServiceManager.getManager().getBuffService()
											.getAddition(seat.getPlayer(), chp, Buff.CTREAT);
									chp = (int) ServiceManager.getManager().getBuffService()
											.getAddition(seat.getPlayer(), chp, Buff.CADDHP);
									playerIds.add(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
									allAddHP.add(chp);
								}
							}
							break;
					}
				}
			}
			completed = true;
		}
		CrossSkillEquip cse = new CrossSkillEquip();
		cse.setBattleId(battleId);
		cse.setPlayerId(playerId);
		cse.setItemcount(itemcount);
		cse.setItmeIds(itmeIds);
		cse.setTiredValue(tiredValue);
		cse.setConsumePower(consumePower);
		cse.setSelfAddHP(selfAddHP);
		cse.setPlayerIds(ServiceUtils.getInts(playerIds.toArray()));
		cse.setAllAddHP(ServiceUtils.getInts(allAddHP.toArray()));
		cse.setCompleted(completed);
		cse.setToolsType(ServiceUtils.getInts(toolsType.toArray()));
		cse.setToolsSubType(ServiceUtils.getInts(toolsSubType.toArray()));
		cse.setToolsParam1(ServiceUtils.getInts(toolsParam1.toArray()));
		cse.setToolsParam2(ServiceUtils.getInts(toolsParam2.toArray()));
		send(cse);
	}

	/**
	 * 使用大招
	 * 
	 * @param battleId
	 * @param player
	 */
	public void bigSkill(int battleId, int playerId) {
		CrossBigSkill cbs = new CrossBigSkill();
		cbs.setBattleId(battleId);
		cbs.setPlayerId(playerId);
		send(cbs);
	}

	/**
	 * 玩家射击
	 * 
	 * @param shoot
	 */
	public void shoot(Shoot shoot) {
		CrossShoot crossShoot = new CrossShoot();
		crossShoot.setBattleId(shoot.getBattleId());
		crossShoot.setPlayerId(shoot.getPlayerId());
		crossShoot.setSpeedx(shoot.getSpeedx());
		crossShoot.setSpeedy(shoot.getSpeedy());
		crossShoot.setLeftRight(shoot.getLeftRight());
		crossShoot.setStartx(shoot.getStartx());
		crossShoot.setStarty(shoot.getStarty());
		crossShoot.setPlayerCount(shoot.getPlayerCount());
		crossShoot.setPlayerIds(shoot.getPlayerIds());
		crossShoot.setCurPositionX(shoot.getCurPositionX());
		crossShoot.setCurPositionY(shoot.getCurPositionY());
		crossShoot.setAttackerRandom(shoot.getAttackerRandom());
		send(crossShoot);
	}

	/**
	 * 玩家受到伤害
	 * 
	 * @param battleId
	 * @param playerId
	 * @param hurtcount
	 * @param playerIds
	 * @param hurtvalue
	 * @param hurtToBloodRate
	 */
	public void hurt(Hurt hurt) {
		CrossHurt crossHurt = new CrossHurt();
		crossHurt.setBattleId(hurt.getBattleId());
		crossHurt.setPlayerId(hurt.getPlayerId());
		crossHurt.setHurtcount(hurt.getHurtcount());
		crossHurt.setPlayerIds(hurt.getPlayerIds());
		crossHurt.setHurtvalue(hurt.getHurtvalue());
		crossHurt.setDistance(hurt.getDistance());
		crossHurt.setTargetRandom(hurt.getTargetRandom());
		crossHurt.setAttackerRandom(hurt.getAttackerRandom());
		crossHurt.setHurtToBloodRate(hurt.getHurtToBloodRate());
		crossHurt.setAttackType(hurt.getAttackType());
		crossHurt.setHurtFloat(hurt.getHurtFloat());
		send(crossHurt);
	}

	/**
	 * 玩家飞行
	 * 
	 * @param battleId
	 * @param playerId
	 * @param speedx
	 * @param speedy
	 * @param leftRight
	 * @param isEquip
	 * @param startx
	 * @param starty
	 * @param playerCount
	 * @param playerIds
	 * @param curPositionX
	 * @param curPositionY
	 */
	public void fly(int battleId, int playerId, int speedx, int speedy, byte leftRight, int isEquip, int startx, int starty,
			int playerCount, int[] playerIds, int[] curPositionX, int[] curPositionY) {
		CrossFly crossFly = new CrossFly();
		crossFly.setBattleId(battleId);
		crossFly.setPlayerId(playerId);
		crossFly.setSpeedx(speedx);
		crossFly.setSpeedy(speedy);
		crossFly.setLeftRight(leftRight);
		crossFly.setIsEquip(isEquip);
		crossFly.setStartx(startx);
		crossFly.setStarty(starty);
		crossFly.setPlayerCount(playerCount);
		crossFly.setPlayerIds(playerIds);
		crossFly.setCurPositionX(curPositionX);
		crossFly.setCurPositionY(curPositionY);
		send(crossFly);
		if (isThisServerPlayer(playerId)) {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(getPlayerId(playerId));
			if (null == player)
				return;
			Seat seat = ServiceManager.getManager().getRoomService().getSeat(getRoomId(playerId), player.getId());
			if (null == seat)
				return;
			if (!seat.isRobot()) {
				ServiceManager.getManager().getTaskService().flySkill(player);
				ServiceManager.getManager().getTitleService().flySkill(player);
			}
		}
	}

	/**
	 * 开始新一回合操作
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void pass(int battleId, int playerId) {
		CrossPass crossPass = new CrossPass();
		crossPass.setBattleId(battleId);
		crossPass.setPlayerId(playerId);
		send(crossPass);
	}

	/**
	 * 角色复活
	 * 
	 * @param battleId
	 * @param playerId
	 * @param playercount
	 * @param playerIds
	 * @param postionX
	 * @param postionY
	 */
	public void rebornPosition(int battleId, int playerId, int playercount, int[] playerIds, int[] postionX, int[] postionY) {
		CrossRebornPosition crp = new CrossRebornPosition();
		crp.setBattleId(battleId);
		crp.setPlayerId(playerId);
		crp.setPlayercount(playercount);
		crp.setPlayerIds(playerIds);
		crp.setPostionX(postionX);
		crp.setPostionY(postionY);
		send(crp);
	}

	/**
	 * 玩家强退游戏
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void quitBattle(int battleId, int playerId, int roomId) {
		CrossQuitBattle cqb = new CrossQuitBattle();
		cqb.setBattleId(battleId);
		cqb.setPlayerId(playerId);
		send(cqb);
		int id = getPlayerId(playerId);
		if (0 != roomId && null != ServiceManager.getManager().getChallengeService().getRoom(roomId)) {
			int index = ServiceManager.getManager().getChallengeService().getPlayerSeat(roomId, id);
			ServiceManager.getManager().getChallengeService().exRoom(roomId, index);
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(id);
			if (null != player) {
				ServiceManager.getManager().getChallengeSerService().addIntegral(player, 0, false);
			}
		} else if (0 != roomId && null != ServiceManager.getManager().getRoomService().getRoom(roomId)) {
			int index = ServiceManager.getManager().getRoomService().getPlayerSeat(roomId, id);
			ServiceManager.getManager().getRoomService().exRoom(roomId, index, id);
		}
	}

	/**
	 * 发送玩家加载进度
	 * 
	 * @param battleId
	 * @param playerId
	 * @param percent
	 */
	public void loadingPercent(int battleId, int playerId, int percent) {
		CrossLoadingPercent clp = new CrossLoadingPercent();
		clp.setBattleId(battleId);
		clp.setCurrentPlayerId(playerId);
		clp.setPercent(percent);
		send(clp);
	}

	/**
	 * 发送使用的表情
	 * 
	 * @param battleId
	 * @param playerId
	 * @param faceId
	 */
	public void usingFace(int battleId, int playerId, int currentPlayerId, int faceId) {
		CrossUsingFace crossUsingFace = new CrossUsingFace();
		crossUsingFace.setPlayerId(playerId);
		crossUsingFace.setBattleId(battleId);
		crossUsingFace.setCurrentPlayerId(currentPlayerId);
		crossUsingFace.setFaceId(faceId);
		send(crossUsingFace);
	}

	/**
	 * 使用飞行技能
	 * 
	 * @param battleId
	 * @param playerId
	 * @param currentPlayerId
	 */
	public void useFly(int battleId, int playerId, int currentPlayerId) {
		CrossUseFly crossUseFly = new CrossUseFly();
		crossUseFly.setBattleId(battleId);
		crossUseFly.setPlayerId(playerId);
		crossUseFly.setCurrentPlayerId(currentPlayerId);
		send(crossUseFly);
	}

	/**
	 * 玩家被坑
	 * 
	 * @param battleId
	 * @param playerId
	 * @param currentPlayerId
	 */
	public void outOfScene(int battleId, int playerId, int currentPlayerId) {
		CrossOutOfScene outOfScene = new CrossOutOfScene();
		outOfScene.setBattleId(battleId);
		outOfScene.setPlayerId(playerId);
		outOfScene.setCurrentPlayerId(currentPlayerId);
		send(outOfScene);
	}

	/**
	 * 被冰冻
	 * 
	 * @param battleId
	 * @param playerIds
	 */
	public void beFrozen(int battleId, int[] playerIds) {
		CrossBeFrozen beFrozen = new CrossBeFrozen();
		beFrozen.setBattleId(battleId);
		beFrozen.setPlayerIds(playerIds);
		send(beFrozen);
	}

	/**
	 * 玩家掉线
	 * 
	 * @param battleId
	 * @param playerId
	 */
	public void playerLost(int battleId, int playerId) {
		CrossPlayerLose playerLose = new CrossPlayerLose();
		playerLose.setBattleId(battleId);
		playerLose.setCurrentPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(playerId));
		send(playerLose);
	}

	public void sendMessage(int battleId, ReceiveMessage receiveMessage) {
		CrossChat crossChat = new CrossChat();
		crossChat.setBattleId(battleId);
		crossChat.setChannel(receiveMessage.getChannel());
		crossChat.setSendId(getCrossPlayerId(receiveMessage.getSendId()));
		crossChat.setSendName(receiveMessage.getSendName());
		crossChat.setReceiveId(receiveMessage.getReveId());
		crossChat.setReceiveName(receiveMessage.getReveName());
		crossChat.setMessage(receiveMessage.getMessage());
		send(crossChat);
	}

	public void getPlayerInfo(int battleId, int playerId, int friendId) {
		CrossPlayerInfo cce = new CrossPlayerInfo();
		cce.setBattleId(battleId);
		cce.setPlayerId(playerId);
		cce.setFriendId(friendId);
		send(cce);
	}

	public void crossShakeHands(int battleId, int crossPlayerId) {
		if (crossPlayerId > 0) {
			CrossShakeHands csh = new CrossShakeHands();
			csh.setBattleId(battleId);
			csh.setPlayerId(crossPlayerId);
			send(csh);
		}
	}

	public void enBattle(int crossPlayerId, String serverId, int roomId, int playerId) {
		playerInfoMap.put(crossPlayerId, new PlayerInfo(Integer.parseInt(serverId), roomId, playerId));
		crossPlayerIdMap.put(serverId + "-" + playerId, crossPlayerId);
	}

	public void extBattle(int crossPlayerId, int roomId) {
		PlayerInfo playerInfo = playerInfoMap.get(crossPlayerId);
		if (null != playerInfo) {
			if (playerInfo.getServerId() != WorldServer.config.getServerId() || playerInfo.getRoomId() == roomId) {
				playerInfoMap.remove(crossPlayerId);
				crossPlayerIdMap.remove(playerInfo.getServerId() + "-" + playerInfo.getPlayerId());
			}
		}
	}

	/**
	 * 判断某个玩家是否本服玩家
	 * 
	 * @param crossPlayerId
	 * @return true本服玩家，false跨服玩家
	 */
	public boolean isThisServerPlayer(int crossPlayerId) {
		PlayerInfo playerInfo = playerInfoMap.get(crossPlayerId);
		if (null != playerInfo && WorldServer.config.getServerId() == playerInfo.getServerId()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据跨服玩家id获取本服玩家id
	 * 
	 * @param crossPlayerId
	 * @return 0表示非本服玩家
	 */
	public int getPlayerId(int crossPlayerId) {
		int playerId = 0;
		PlayerInfo playerInfo = playerInfoMap.get(crossPlayerId);
		if (null != playerInfo && WorldServer.config.getServerId() == playerInfo.getServerId()) {
			playerId = playerInfo.getPlayerId();
		}
		return playerId;
	}

	/**
	 * 根据跨服玩家id获取玩家id
	 * 
	 * @param crossPlayerId
	 * @return
	 */
	public int getAllServerPlayerId(int crossPlayerId) {
		int playerId = 0;
		PlayerInfo playerInfo = playerInfoMap.get(crossPlayerId);
		if (null != playerInfo) {
			playerId = playerInfo.getPlayerId();
		}
		return playerId;
	}

	/**
	 * 根据跨服玩家id获取玩家所在房间id
	 * 
	 * @param crossPlayerId
	 * @return
	 */
	public int getRoomId(int crossPlayerId) {
		int roomId = 0;
		PlayerInfo playerInfo = playerInfoMap.get(crossPlayerId);
		if (null != playerInfo && WorldServer.config.getServerId() == playerInfo.getServerId()) {
			roomId = playerInfo.getRoomId();
		}
		return roomId;
	}

	/**
	 * 根据玩家id获取跨服玩家id
	 * 
	 * @param crossPlayerId
	 * @return
	 */
	public int getCrossPlayerId(int playerId) {
		Integer crossPlayerId = crossPlayerIdMap.get(WorldServer.config.getServerId() + "-" + playerId);
		if (null != crossPlayerId) {
			return crossPlayerId;
		} else {
			return 0;
		}
	}

	public class PlayerInfo {
		private int serverId;
		private int roomId;
		private int playerId;

		public PlayerInfo(int serverId, int roomId, int playerId) {
			this.serverId = serverId;
			this.playerId = playerId;
			this.roomId = roomId;
		}

		public int getServerId() {
			return serverId;
		}

		public int getPlayerId() {
			return playerId;
		}

		public int getRoomId() {
			return roomId;
		}
	}
}
