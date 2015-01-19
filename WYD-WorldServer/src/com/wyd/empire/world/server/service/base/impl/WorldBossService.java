package com.wyd.empire.world.server.service.base.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.bossmapbattle.GameOver;
import com.wyd.empire.protocol.data.worldbosshall.SendSettlementInfo;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.BossmapBuff;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.dao.IWorldBossDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.server.service.impl.WorldBossRoomService;
import com.wyd.empire.world.worldbosshall.WorldBossRoom;

/**
 * The service class for the TabConsortiaright entity.
 */
public class WorldBossService extends UniversalManagerImpl implements IWorldBossService {
	Logger log = Logger.getLogger(WorldBossService.class);
	private static Date openNoticeDate; // 世界BOSS开启通知时间，如果时间等于当前天说明今天已通知过了。
	private static Date openRoomDate; // 世界BOSS开启时间，如果时间等于当前天说明今天已通知过了。
	private static Date closeNoticeDate;
	private static Date startTime;
	/**
	 * The dao instance injected by Spring.
	 */
	private IWorldBossDao dao;
	private WorldBossRoomService roomService = null;
	private ChatService chatService = null;

	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */

	public WorldBossService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IWorldBossService getInstance(ApplicationContext context) {
		return (IWorldBossService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IWorldBossDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IWorldBossDao getDao() {
		return this.dao;
	}

	/**
	 * 当前时间是否离世界BOSS开启时间差MINUTE分钟
	 * 
	 * @param MINUTE
	 * @return
	 */
	@Override
	public boolean isBeforeTime(int MINUTE) {
		Date[] scope = getTimeScope();
		long startTime = scope[0].getTime();
		long cha = startTime - Calendar.getInstance().getTime().getTime();
		cha = cha / (60 * 1000);
		return cha < 0 ? false : cha < MINUTE;
	}

	@Override
	public boolean isInTime() {
		return isInTime(new Date());
	}

	public boolean isInTime(Date date) {
		Date[] scope = getTimeScope();
		return isBetweenTime(date, scope);
	}

	/**
	 * 供系统定时任务调用，如果当前时间在世界BOSS开启时间内则开启；如果五分钟后开启则发通知（一天只发一次）
	 * 
	 * @return
	 */
	@Override
	public void sysCheckStartTime() {
		chatService = ServiceManager.getManager().getChatService();
		roomService = WorldBossRoomService.getInstance();
		Calendar cal = Calendar.getInstance();
		if (isInTime(cal.getTime())) {
			WorldBossRoom room = roomService.getRoomByMap(Common.WORLDBOSS_DEFAULT_MAP);
			// 如果今天已经开过就不要再开了
			if (!eqToday(openRoomDate)) {
				if (room == null || !room.isOpen()) {
					// 开启
					roomService.openRoom(Common.WORLDBOSS_DEFAULT_MAP);
					log.info("世界BOSS时间开启...");
					openRoomDate = cal.getTime();
					// 发公告
					chatService.openWorldBoss(1);
				}
				ServiceManager.getManager().getButtonInfoService().synButtonInfo();
			}
		} else {
			if (isBeforeTime(5) && !eqToday(openNoticeDate)) {
				// 提前五分钟发公告
				chatService.openWorldBoss(0);
				openNoticeDate = cal.getTime();
				// 提前五分钟创建BOSS
				roomService.createBoss(Common.WORLDBOSS_DEFAULT_MAP);
				ServiceManager.getManager().getButtonInfoService().synButtonInfo();
			}
		}

	}

	public void gameOver(int mapId) {
		roomService = WorldBossRoomService.getInstance();
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		Combat boss = room.getBoss();
		int bossMaxHp = boss.getMaxHP();
		int totalHurt = bossMaxHp - boss.getHp();

		// 取出所有参与世界BOSS的玩家（按伤害排序）
		List<Combat> combatList = room.hurtTop(-1);
		int rank = 1;// 排名
		int killerId = room.getKiller() == null ? 0 : room.getKiller().getId();
		for (Combat combat : combatList) {
			if (null != combat.getPlayer()) {
				boolean isKiller = killerId == combat.getId();
				log.info(combat.getId() + " " + rank + " " + totalHurt + " " + isKiller);
				// 通知参与的玩家游戏结束了，进入结算界面
				sendGameOverToPlayer(combat, rank, totalHurt, isKiller, room.getKiller() != null);
				// 发放礼包
				playerGetSpreegift(combat, isKiller, boss, rank);
				rank++;
			}
		}
		boss.setDead(true);
		ServiceManager.getManager().getButtonInfoService().synButtonInfo();
	}

	private void playerGetSpreegift(Combat combat, boolean isKiller, Combat boss, int rank) {
		try {
			String content = "";
			int bossMaxHp = boss.getMaxHP();
			int playerId = combat.getId();
			int totalHurt = combat.getTotalHurt();
			if (boss.getHp() < 1) {
				content = TipMessages.WORLDBOSS_OVER_MAIL_PART1;
			} else {
				content = TipMessages.WORLDBOSS_OVER_MAIL_PART2;
			}
			String content1 = TipMessages.WORLDBOSS_OVER_MAIL_PART3;
			double hurtPercent = (totalHurt * 1.0d / bossMaxHp) * 100;
			content1 = content1.replace("{1}", totalHurt + "");
			content1 = content1.replace("{2}", formatNumber(hurtPercent));
			content1 = content1.replace("{3}", rank + "");

			String content2 = TipMessages.WORLDBOSS_OVER_MAIL_PART4;
			// 伤害输出占比奖励
			int spreegiftId1 = getPercentSpreegift(totalHurt, bossMaxHp);
			playerGetItem(playerId, spreegiftId1);
			if (spreegiftId1 != 0) {
				content2 = content2.replace("{4}", getSpreegiftName(spreegiftId1));
			} else {
				content2 = content2.replace("{4}、", "");
			}
			// 排名奖励
			int spreegiftId2 = getRankSpreegift(rank);
			playerGetItem(playerId, spreegiftId2);
			if (spreegiftId2 != 0) {
				content2 = content2.replace("{5}", getSpreegiftName(spreegiftId2));
			} else {
				content2 = content2.replace("{5}、", "");
			}
			int spreegiftId3 = 0;
			// 最后一击
			if (isKiller) {
				spreegiftId3 = 698;
				playerGetItem(playerId, spreegiftId3);
				content2 = content2.replace("{6}", getSpreegiftName(spreegiftId3));
			} else {
				content2 = content2.replace("、{6}", "");
			}
			// 没有获得礼包。
			if ((spreegiftId3 + spreegiftId2 + spreegiftId1) == 0) {
				content2 = "";
			}
			//
			// 邮件通知
			sendMail(playerId, content + "\n" + content1 + "\n" + content2);
		} catch (Exception ex) {
			log.error(ex, ex);
		}

	}

	// 获取排名奖励礼包
	private int getRankSpreegift(int rank) {
		if (rank == 1) {
			return 690;
		} else if (rank == 2) {
			return 691;
		} else if (rank == 3) {
			return 692;
		} else if (rank >= 3 && rank <= 10) {
			return 693;
		} else if (rank >= 11 && rank <= 50) {
			return 694;
		} else if (rank >= 51 && rank <= 150) {
			return 695;
		} else if (rank >= 151 && rank <= 800) {
			return 696;
		} else if (rank >= 801 && rank <= 500000) {
			return 697;
		}
		return 0;
	}

	// 获取伤害输出占比礼包
	private int getPercentSpreegift(int totalHurt, int bossMaxHp) {
		double percent5 = bossMaxHp * 0.05;
		double percent10 = bossMaxHp * 0.1;
		double percent15 = bossMaxHp * 0.15;
		double percent20 = bossMaxHp * 0.2;
		if (totalHurt > percent20) {
			return 686;
		} else if (totalHurt > percent15) {
			return 687;
		} else if (totalHurt > percent10) {
			return 688;
		} else if (totalHurt > percent5) {
			return 689;
		}
		return 0;
	}

	private void playerGetItem(int playerId, int itemId) {
		if (itemId > 0) {
			ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(playerId, itemId, -1, -1, 1, 25, "世界BOSS", 0, 0, 0);
		}

	}

	private String getSpreegiftName(int id) {
		return ServiceManager.getManager().getShopItemService().getShopItemById(id).getName();
	}

	// 通知参与的玩家游戏结束了，进入结算界面
	private void sendGameOverToPlayer(Combat combat, int rank, int totalHurt, boolean isKiller, boolean win) {
		SendSettlementInfo info = new SendSettlementInfo();
		int hurt = combat.getTotalHurt();
		info.setHurtValue(hurt);
		info.setHurtRank(rank);
		info.setHurtPercent((int) ((hurt * 1.0d / totalHurt) * 10000));
		info.setLastKillGift(isKiller);
		info.setWin(win);
		// combat.getPlayer().setBossmapBattleId(0);
		combat.getPlayer().sendData(info);
	}

	// BOSS未死但时间到
	//
	public void timeOver(int mapId) {
		System.out.println("活动时间到！");
		roomService = WorldBossRoomService.getInstance();
		chatService = ServiceManager.getManager().getChatService();
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		room.getBoss().setDead(true);
		if (room.isOpen()) {
			// 发公告
			chatService.closeWorldBoss();
			room.close();
			room.setKiller(null);// 超时结束killer必然是空
			gameOver(mapId);
		}
	}

	/**
	 * BOSS被杀死
	 * 
	 * @param mapId
	 */
	public void bossDead(int mapId) {
		roomService = WorldBossRoomService.getInstance();
		chatService = ServiceManager.getManager().getChatService();
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		if (room.isOpen()) {
			Combat boss = room.getBoss();
			if (boss.isDead()) {
				room.close();
				// BOSS被杀死
				WorldPlayer killer = room.getKiller();
				// 发公告
				chatService.closeWorldBoss(killer.getName());
				gameOver(mapId);
				GameLogService.killWordBoss(killer.getId(), killer.getLevel());
			}
		}
	}

	public void playerDead(int battleId, int mapId, List<Combat> combatList) {
		try {
			com.wyd.empire.world.bean.Map map = ServiceManager.getManager().getMapsService().getWorldBossMapById(mapId);
			int xhsp = 0;
			List<RewardInfo> mapRewards = ServiceUtils.getRewardInfo(map.getReward(), 2);
			for (RewardInfo reward : mapRewards) {
				switch (reward.getItemId()) {
					case Common.STARSOULDEBRISID :
						xhsp = reward.getCount();
						break;
				}
			}

			GameOver gameOver = new GameOver();
			setWorldBossOver(battleId, gameOver);
			for (Combat combat : combatList) {
				if (!combat.isRobot() && !combat.isLost() && null != combat.getPlayer()) {
					combat.setHp(0);
					combat.setLost(true);
					combat.setDead(true);
					WorldPlayer player = combat.getPlayer();
					player.sendData(gameOver);
					// 设置CDTIME
					setCDTime(combat.getPlayer().getId(), mapId);
					player.setBossmapBattleId(0);
					if (xhsp > 0) {
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(combat.getPlayer().getId(), Common.STARSOULDEBRISID, xhsp, 25, "", 0, 0, 0);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void setWorldBossOver(int battleId, GameOver gameOver) {
		int playerCount = 1;
		int[] playerIds = new int[playerCount];
		int[] shootRate = new int[playerCount];
		int[] totalHurt = new int[playerCount];
		int[] killCount = new int[playerCount];
		int[] beKilledCount = new int[playerCount];
		int[] addExp = new int[playerCount];
		int[] Exp = new int[playerCount];
		int[] upgradeExp = new int[playerCount];
		int[] nextUpgradeExp = new int[playerCount];
		int[] star = new int[playerCount];
		int[] pices = new int[playerCount];
		int eggCount = 18;
		int[] egg_playeId = new int[18];
		String[] egg_Item_Name = initStrings(eggCount);
		String[] egg_item_icon = initStrings(eggCount);
		int[] egg_ItemNum = new int[18];
		gameOver.setBattleId(battleId);
		gameOver.setFirstHurtPlayerId(0);
		gameOver.setWinCamp(1);
		gameOver.setPlayerCount(playerCount);
		gameOver.setPlayerIds(playerIds);
		gameOver.setShootRate(shootRate);
		gameOver.setTotalHurt(totalHurt);
		gameOver.setKillCount(killCount);
		gameOver.setBeKilledCount(beKilledCount);
		gameOver.setAddExp(addExp);
		gameOver.setExp(Exp);
		gameOver.setUpgradeExp(upgradeExp);
		gameOver.setNextUpgradeExp(nextUpgradeExp);
		gameOver.setStar(star);
		gameOver.setPices(pices);
		gameOver.setEggCount(eggCount);
		gameOver.setEgg_item_icon(egg_item_icon);
		gameOver.setEgg_Item_Name(egg_Item_Name);
		gameOver.setEgg_ItemNum(egg_ItemNum);
		gameOver.setEgg_playeId(egg_playeId);
		int[] i = {0};
		gameOver.setIsMarry(i);
		gameOver.setGuildAddExp(i);
		gameOver.setIsDoubleExpCard(i);
		gameOver.setActivityAddExp(i);
	}

	public Date[] getTimeScope() {
		Date[] dates = new Date[2];
		try {
			OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
			JSONObject json = JSONObject.fromObject(operationConfig.getWorldBoss());
			String timeStr = json.getString("startTime");
			dates[0] = parseDate(timeStr);
			timeStr = json.getString("endTime");
			dates[1] = parseDate(timeStr);
			// 以下为了测试方便，当开启时间变化时重置世界BOSS开启流程
			if (startTime != null && !eqDateTiem(dates[0], startTime)) {
				openNoticeDate = null;
				openRoomDate = null;
				closeNoticeDate = null;
				WorldBossRoom room = roomService.getRoomByMap(Common.WORLDBOSS_DEFAULT_MAP);
				if (room != null) {
					room.close();
				}
				System.out.println("开启时间已变化……");
			}
			startTime = dates[0];
		} catch (Exception e) {
			log.error("Time error or config is null");
			return null;
		}
		return dates;
	}

	/**
	 * 格式HH:mm
	 * 
	 * @return
	 */
	private Date parseDate(String HHmm) {
		Calendar cal = Calendar.getInstance();
		String[] HH_mm = HHmm.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(HH_mm[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(HH_mm[1]));
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 对比时分
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	private boolean eqDateTiem(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int hour1 = cal1.get(Calendar.HOUR);
		int minute1 = cal1.get(Calendar.MINUTE);
		int hour2 = cal2.get(Calendar.HOUR);
		int minute2 = cal2.get(Calendar.MINUTE);
		return hour1 == hour2 && minute1 == minute2;
	}

	@Override
	public int getCDTime(int playerId, int mapId) {
		roomService = WorldBossRoomService.getInstance();
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		return (int) (room.getPlayerCDTime(playerId) / 1000);
	}

	@Override
	public void setCDTime(int playerId, int mapId) {
		roomService = WorldBossRoomService.getInstance();
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		room.setPlayerCDTime(playerId);
	}

	@Override
	public void clearCDTime(int playerId, int mapId) {
		roomService = WorldBossRoomService.getInstance();
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		room.setPlayerCDTime(playerId, 0);
	}

	public boolean isBetweenTime(Date now, Date[] scope) {
		if (null != now && null != scope && scope.length > 1) {
			return (now.compareTo(scope[0]) > -1 && now.compareTo(scope[1]) < 0);
		}
		return false;
	}

	public boolean eqToday(Date date) {
		if (date == null)
			return false;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(date);
		return day == cal.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public void sysCheckEndTime() {
		roomService = WorldBossRoomService.getInstance();
		Date[] startEnd = getTimeScope();
		if (!eqToday(closeNoticeDate)) {
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			if (now.compareTo(startEnd[1]) > -1) {
				List<WorldBossRoom> roomList = roomService.getAllRoom();
				for (WorldBossRoom room : roomList) {
					if (room.isOpen()) {
						timeOver(room.getMapId());
						closeNoticeDate = now;
						log.info("世界BOSS时间结束...");
					}
				}
			}
		}
	}

	private void sendMail(int playerId, String content) {
		Mail mail = new Mail();
		mail.setContent(content);
		mail.setIsRead(false);
		mail.setReceivedId(playerId);
		mail.setSendId(0);
		mail.setSendName(TipMessages.SYSNAME_MESSAGE);
		mail.setSendTime(new Date());
		mail.setTheme(TipMessages.WORLDBOSS_OVER_MAIL_TITLE);
		mail.setType(1);
		mail.setBlackMail(false);
		mail.setIsStick(Common.IS_STICK);
		try {
			ServiceManager.getManager().getMailService().saveMail(mail, null);
		} catch (Exception ex) {
			log.error(ex + " content:" + content, ex);
		}

	}

	@Override
	public void sysCheckStartEndTime() {
		sysCheckStartTime();
		sysCheckEndTime();
	}

	/**
	 * 进入房间
	 * 
	 * @param player
	 */
	public int enter(WorldPlayer player, int roomId) {
		WorldBossRoom room = roomService.getRoomById(roomId);
		int state = room.addPlayer(player);
		if (state == 1) {
			Combat combat = room.findPlayer(player.getId());
			BossBattleTeamService bossBattleTeamService = ServiceManager.getManager().getBossBattleTeamService();
			int battleId = bossBattleTeamService.createWordBossBattleTeam(combat, room.getBoss(), room.getMapId());
			bossBattleTeamService.worldBossSort(battleId);
			player.setBossmapBattleId(battleId);
			return battleId;
		}
		return state;

	}

	/**
	 * 通过地图ID判断是否是世界BOSS战斗
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean isWorldBossBattle(int mapId) {
		MapsService mapsService = ServiceManager.getManager().getMapsService();
		return mapsService.getWorldBossMapById(mapId) != null;
	}

	/**
	 * 1,玩家伤害输出达到条件后，发系统公告 2,BOSS没血了，记录杀死BOSS的人
	 * 注：关闭世界BOSS并不在这里做，由BossBattleTeamService线程控制
	 * 
	 * @param mapId
	 * @param combat
	 */

	public void checkHurt(int mapId, Combat combat) {
		int totalHurt = combat.getTotalHurt();
		roomService = WorldBossRoomService.getInstance();
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		if (room == null || !room.isOpen()) {
			return;
		}

		ConcurrentHashMap<String, Integer> playerHurtNotice = room.getPlayerHurtNotice();
		int bossMaxHp = room.getBoss().getMaxHP();
		double percent5 = bossMaxHp * 0.05;
		double percent10 = bossMaxHp * 0.1;
		double percent15 = bossMaxHp * 0.15;
		double percent20 = bossMaxHp * 0.2;
		chatService = ServiceManager.getManager().getChatService();
		String playerName = combat.getName();
		Integer percent = playerHurtNotice.get(playerName);
		percent = percent == null ? 0 : percent;
		// 等于空表示未发过，可以发。

		if (totalHurt >= percent20) {
			// pertcent==20表示达到20%的广播已经发过了，不能再发。
			if (percent != 20) {
				chatService.hurtWorldBoss(playerName, 20);
				playerHurtNotice.put(playerName, 20);
			}
		} else if (totalHurt >= percent15) {
			// pertcent==15表示达到15%的广播已经发过了，不能再发。
			if (percent != 15) {
				chatService.hurtWorldBoss(playerName, 15);
				playerHurtNotice.put(playerName, 15);
			}
		} else if (totalHurt >= percent10) {
			// pertcent==10表示达到10%的广播已经发过了，不能再发。
			if (percent != 10) {
				chatService.hurtWorldBoss(playerName, 10);
				playerHurtNotice.put(playerName, 10);
			}
		} else if (totalHurt >= percent5) {
			// pertcent==5表示达到5%的广播已经发过了，不能再发。
			if (percent != 5) {
				chatService.hurtWorldBoss(playerName, 5);
				playerHurtNotice.put(playerName, 5);
			}

		}

		// 如果BOSS没血了，记录杀死BOSS的玩家
		int bossHp = room.getBoss().getHp();
		if (bossHp < 1 && room.getKiller() == null) {
			System.out.println("记录killer" + combat.getName());
			room.setKiller(combat.getPlayer());
			room.getBoss().setDead(true);
		}

	}

	// 建立并初始化数组
	private String[] initStrings(int size) {
		String[] strs = new String[size];
		for (int i = 0; i < size; i++) {
			strs[i] = "";
		}
		return strs;
	}

	private String formatNumber(double d) {
		String val = d + "";
		if (d < 0.0001) {
			val = String.format("%.6f", d);
		} else if (d < 0.001) {
			val = String.format("%.4f", d);
		} else if (d < 0.01) {
			val = String.format("%.3f", d);
		} else if (d < 0.1) {
			val = String.format("%.2f", d);
		} else if (d < 1) {
			val = String.format("%.1f", d);
		} else {
			val = String.format("%.0f", d);
		}
		return val;
	}

	/**
	 * 设置世界BOSS的可触发BUFF
	 * 
	 * @param buffSet
	 */
	public void refreshWorldBossBuffSet(HashSet<Integer> playerBuffSet, int mapId) {
		WorldBossRoom room = roomService.getRoomByMap(mapId);
		// buff数量有限，发完就没有了
		if (room != null && room.getBuffNum() < 1) {
			playerBuffSet.clear();
			return;
		}
		int xinghun = room.getXinghun();
		if (playerBuffSet.size() < 4) {
			List<BossmapBuff> buffList = ServiceManager.getManager().getBossmapBuffService().findByGroup(1);
			int totalPrecent = 0;
			for (int i = buffList.size() - 1; i >= 0; i--) {
				BossmapBuff buff = buffList.get(i);
				if (playerBuffSet.contains(buff.getId())) {
					buffList.remove(i);// 如果上次没有打中，则这次不要再抽中
				} else if (buff.getType() == 6 && xinghun < 1) {
					// 星魂派完就没有了
					buffList.remove(i);
				} else {
					totalPrecent += buff.getRealityPercent();
				}

			}

			int i = 0;
			while (playerBuffSet.size() < 4 && i < 4) {
				i++;
				int random = ServiceUtils.getRandomNum(1, totalPrecent + 1);
				int precent = 0;
				for (BossmapBuff buff : buffList) {
					precent += buff.getRealityPercent();
					if (precent >= random) {
						buffList.remove(buff);
						totalPrecent -= buff.getRealityPercent();
						playerBuffSet.add(buff.getId());
						room.useBuffNum(1);
						if (buff.getType() == 6) {
							room.useXinghun(1);
						}
						break;
					}
				}
			}
		}
	}
}