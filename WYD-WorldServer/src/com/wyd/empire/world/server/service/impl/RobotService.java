package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class RobotService implements Runnable {
	private Map<Integer, Map<Integer, List<String>>> robotMap = new HashMap<Integer, Map<Integer, List<String>>>();
	private List<WorldPlayer> offlinePlayer = new ArrayList<WorldPlayer>();
	private int highLevelCount = 0;// 高级机器人数量
	private int lowLevelCount = 0;// 低级机器人数量
	private static int MAXLEVEL = 100;
	private static int MAXFIGHT = 100001;

	public void start() {
		Thread t = new Thread(this);
		t.setName("RobotService-Thread");
		t.start();
	}

	public void run() {
		System.out.println("开始初始化机器人数据.........");
		long time = System.currentTimeMillis();
		inint();
		System.out.println("初始化机器人数据完成 耗时：" + (System.currentTimeMillis() - time) / 1000 + "秒 加载低级机器人：" + lowLevelCount + " 加载高级机器人:"
				+ highLevelCount);
	}

	public void inint() {
		MAXLEVEL = WorldServer.config.getMaxLevel(1) + 2;
		MAXFIGHT = 100001;
		PlayerService playerService = ServiceManager.getManager().getPlayerService();
		List<Object[]> playerInfoList = playerService.getService().getPlayerLevelAndFight();
		int maxFight = playerService.getService().getMaxFight();
		int maxLevel = playerService.getService().getMaxLevel();
		for (int fight = 100; fight < MAXFIGHT; fight += 50) {// 按50战斗力分一个等级
			Map<Integer, List<String>> levelMap = new HashMap<Integer, List<String>>();
			robotMap.put(fight, levelMap);
			for (int level = 5; level < MAXLEVEL; level += 5) {// 按5级分一个等级
				List<String> playerList = new ArrayList<String>();
				levelMap.put(level, playerList);
				if (fight <= maxFight && level <= maxLevel) {
					for (int i = playerInfoList.size() - 1; i > -1; i--) {
						Object[] info = playerInfoList.get(i);
						Integer playerId = (Integer) info[0];
						Integer playerLevel = (Integer) info[1];
						Integer playerFight = (Integer) info[2];
						if (playerService.playerIsOnline(playerId)) {
							playerInfoList.remove(i);
							continue;
						}
						if (playerLevel > level - 5 && playerLevel <= level && playerFight > fight - 50 && playerFight <= fight) {
							playerInfoList.remove(i);
							playerList.add(playerId.toString());
							WorldPlayer player = playerService.getWorldPlayerById(playerId);
							player.setRobotList(playerList);
							addOfflinePlayer(player);
						}
					}
				}
			}
		}
	}

	private void addOfflinePlayer(WorldPlayer player) {
		offlinePlayer.add(player);
		if (WorldPlayer.PLAYER_CHANNEL_LOW == player.getBattleChannel()) {
			lowLevelCount++;
		} else {
			highLevelCount++;
		}
	}

	private void removeOfflinePlayer(WorldPlayer player) {
		offlinePlayer.remove(player);
		if (WorldPlayer.PLAYER_CHANNEL_LOW == player.getBattleChannel()) {
			lowLevelCount--;
		} else {
			highLevelCount--;
		}
	}

	/**
	 * 增加机器人（玩家下线调用）
	 * 
	 * @param player
	 */
	public void addRobot(WorldPlayer player) {
		int[] robotInfo = getRobotInfo(player.getLevel(), player.getPlayer().getFight());
		Map<Integer, List<String>> levelMap = robotMap.get(robotInfo[1]);
		if (null != levelMap) {
			List<String> idList = levelMap.get(robotInfo[0]);
			if (null != idList) {
				idList.add(player.getId() + "");
				player.setRobotList(idList);
				addOfflinePlayer(player);
				if (idList.size() > 30) {
					player = ServiceManager.getManager().getPlayerService().getLoadPlayer(Integer.parseInt(idList.get(0)));
					removeRobot(player);
				}
			}
		}
	}

	/**
	 * 移除机器人（玩家上线调用）
	 * 
	 * @param player
	 */
	public void removeRobot(WorldPlayer player) {
		List<String> idList = player.getRobotList();
		if (null != idList) {
			idList.remove(player.getId() + "");
			player.setRobotList(null);
			removeOfflinePlayer(player);
		}
	}

	public int[] getRobotInfo(int level, int fight) {
		level = (int) Math.ceil(level / 5f) * 5;
		fight = (int) Math.ceil(fight / 50f) * 50;
		return new int[]{level, fight};
	}

	/**
	 * 获取机器人
	 * 
	 * @param player
	 * @param playerIdSet
	 * @return
	 */
	public WorldPlayer getRobot(WorldPlayer player, Set<String> playerIdSet) {
		return getRobot(player.getLevel(), player.getPlayer().getFight(), playerIdSet, player.getBattleChannel());
	}

	/**
	 * 获取机器人
	 * 
	 * @param level
	 * @param fight
	 * @param playerIdSet
	 * @return
	 */
	public WorldPlayer getRobot(int level, int fight, Set<String> playerIdSet, int battleCannel) {
		WorldPlayer player = null;
		int[] robotInfo = getRobotInfo(level, fight);
		int maxFight = robotInfo[1];
		int minFight = robotInfo[1];
		while (null == player && (minFight > 99 || maxFight < MAXFIGHT)) {
			if (maxFight < MAXFIGHT) {
				player = getRandomPlayer(robotMap.get(maxFight), robotInfo[0], playerIdSet, battleCannel);
			}
			if (null == player && maxFight != minFight && minFight > 99) {
				player = getRandomPlayer(robotMap.get(minFight), robotInfo[0], playerIdSet, battleCannel);
			}
			maxFight += 50;
			minFight -= 50;
		}
		return player;
	}

	/**
	 * 获取机器人
	 * 
	 * @param levelMap
	 * @param level
	 * @param playerIdSet
	 * @return
	 */
	public WorldPlayer getRandomPlayer(Map<Integer, List<String>> levelMap, int level, Set<String> playerIdSet, int battleCannel) {
		WorldPlayer player = null;
		int max;
		int min;
		if (level > 15) {
			max = MAXLEVEL;
			min = 15;
		} else {
			max = 16;
			min = 4;
		}
		int maxLevel = level;
		int minLevel = level;
		if (null != levelMap) {
			while (null == player && (minLevel > min || maxLevel < max)) {
				if (maxLevel < max) {
					player = getRandomPlayer(levelMap.get(maxLevel), playerIdSet, battleCannel);
				}
				if (null == player && maxLevel != minLevel && minLevel > min) {
					player = getRandomPlayer(levelMap.get(minLevel), playerIdSet, battleCannel);
				}
				maxLevel += 5;
				minLevel -= 5;
			}
		}
		return player;
	}

	/**
	 * 获取机器人
	 * 
	 * @param idList
	 * @param playerIdSet
	 * @return
	 */
	public WorldPlayer getRandomPlayer(List<String> idList, Set<String> playerIdSet, int battleCannel) {
		WorldPlayer player = null;
		if (null != idList && idList.size() > 0) {
			idList = new ArrayList<String>(idList);
			for (int i = idList.size() - 1; i >= 0; i--) {
				if (playerIdSet.contains(idList.get(i))) {
					idList.remove(i);
				}
			}
			if (idList.size() > 0) {
				List<String> newList = new ArrayList<String>(idList);
				while (newList.size() > 0) {
					String id = newList.remove(ServiceUtils.getRandomNum(0, newList.size()));
					player = ServiceManager.getManager().getPlayerService().getLoadPlayer(Integer.parseInt(id));
					if (null != player && player.getBattleChannel() != battleCannel) {
						player = null;
					}
					if (null != player) {
						return player;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取离线玩家
	 * 
	 * @return
	 */
	public List<WorldPlayer> getOfflinePlayer() {
		return new ArrayList<WorldPlayer>(offlinePlayer);
	}

	/**
	 * 获取玩家所在频道的机器人数量
	 * 
	 * @param battleChannel
	 * @return
	 */
	public int getRobotCount(int battleChannel) {
		if (WorldPlayer.PLAYER_CHANNEL_LOW == battleChannel) {
			return lowLevelCount;
		} else {
			return highLevelCount;
		}
	}
}
