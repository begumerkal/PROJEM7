package com.wyd.empire.world.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.bean.GoldCount;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerLevel;
import com.wyd.empire.world.bean.PlayerOnline;
import com.wyd.empire.world.bean.PlayerStaWeek;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShoppingEveryday;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.BattleLog;
import com.wyd.empire.world.logs.ShopItemLog;
import com.wyd.empire.world.logs.TaskLog;
import com.wyd.empire.world.logs.ToolsLog;
import com.wyd.empire.world.player.Record;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.player.GetTopRecordHandler;
import com.wyd.empire.world.server.service.base.ILogStatisticsService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class SystemLogService {
	public static final int RECORD_COUNT = 100;
	public static final String GSBATTLE = TipMessages.LOG1;
	public static final String GSTASK = TipMessages.LOG2;
	public static final String GSBUYPRO = TipMessages.LOG3;
	ILogStatisticsService logService;
	ConcurrentHashMap<Integer, ToolsLog> toolsLogMap = new ConcurrentHashMap<Integer, ToolsLog>();
	ConcurrentHashMap<String, BattleLog> battleLogMap = new ConcurrentHashMap<String, BattleLog>();
	ConcurrentHashMap<Integer, TaskLog> taskLogMap = new ConcurrentHashMap<Integer, TaskLog>();
	ConcurrentHashMap<Integer, ShopItemLog> itemLogMap = new ConcurrentHashMap<Integer, ShopItemLog>();
	// 排行榜数据
	List<PlayerStaWeek> weekLevel;
	List<PlayerStaWeek> weekWin;
	List<PlayerStaWeek> weekGold;
	List<PlayerStaWeek> weekTick;
	List<PlayerStaWeek> monthLevel;
	List<PlayerStaWeek> monthWin;
	List<PlayerStaWeek> monthGold;
	List<PlayerStaWeek> monthTick;
	List<Record> nowLevel;
	List<Record> nowWin;
	List<Record> nowGold;
	List<Record> nowTick;
	List<Record> nowFight;
	List<Record> nowAttack;
	List<Record> nowHP;
	Comparator<WorldPlayer> fcComparator = new FightingComparator();
	Comparator<WorldPlayer> attackComparator = new AttackComparator();
	Comparator<WorldPlayer> hpComparator = new HPComparator();

	public ILogStatisticsService getLogService() {
		return logService;
	}

	public void setLogService(ILogStatisticsService logService) {
		this.logService = logService;
	}

	public void thirtyMinute() {
		ServiceUtils.sleepRandomTime();
		System.out
				.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":" + "SystemLogService thirtyMinute runing...");
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask(0));
	}

	public void oneDay() {
		ServiceUtils.sleepRandomTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":" + "SystemLogService oneDay runing...");
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask(1));
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask(4));
	}

	public void sevenDay() {
		ServiceUtils.sleepRandomTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":" + "SystemLogService sevenDay runing...");
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask(2));
	}

	public void oneMonth() {
		ServiceUtils.sleepRandomTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":" + "SystemLogService oneMonth runing...");
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask(3));
	}

	/**
	 * @param type
	 *            0重置排行榜数据,1保存每日统计日志,2保存每周排行榜记录,3保存月排行榜记录
	 * @return
	 */
	private Runnable createTask(int type) {
		return new TaskThread(type);
	}

	public class TaskThread implements Runnable {
		private int type; // 0重置排行榜数据,1保存每日统计日志,2保存每周排行榜记录,3保存月排行榜记录

		/**
		 * @param type
		 *            0重置排行榜数据,1保存每日统计日志,2保存每周排行榜记录,3保存月排行榜记录,4更新vip经验等级
		 */
		public TaskThread(int type) {
			this.type = type;
		}

		// 重置前獲取排行數據

		@Override
		public void run() {
			switch (type) {
				case 0 :
					initialTopRecord();
					break;
				case 1 :
					saveShopItemLog();
					break;
				case 2 :
					Calendar calendar_w = Calendar.getInstance();
					calendar_w.add(Calendar.DAY_OF_MONTH, -7);
					int wrmNum_w = calendar_w.get(Calendar.WEEK_OF_YEAR);
					weekLevel = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(0, wrmNum_w, "levelRank", "levelRank desc, exp", GetTopRecordHandler.MAX_COUNT);
					weekWin = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(0, wrmNum_w, "winNumRank", "winNumRank", GetTopRecordHandler.MAX_COUNT);
					weekGold = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(0, wrmNum_w, "goldRank", "goldRank", GetTopRecordHandler.MAX_COUNT);
					weekTick = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(0, wrmNum_w, "ticketRank", "ticketRank", GetTopRecordHandler.MAX_COUNT);
					clearTopData(0);
					break;
				case 3 :
					Calendar calendar_m = Calendar.getInstance();
					calendar_m.add(Calendar.MONTH, -1);
					int wrmNum_m = calendar_m.get(Calendar.MONTH);
					monthLevel = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(1, wrmNum_m, "levelRank", "levelRank desc, exp", GetTopRecordHandler.MAX_COUNT);
					monthWin = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(1, wrmNum_m, "winNumRank", "winNumRank", GetTopRecordHandler.MAX_COUNT);
					monthGold = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(1, wrmNum_m, "goldRank", "goldRank", GetTopRecordHandler.MAX_COUNT);
					monthTick = ServiceManager.getManager().getPlayerStaWeekService()
							.getTopRecord(1, wrmNum_m, "ticketRank", "ticketRank", GetTopRecordHandler.MAX_COUNT);
					clearTopData(1);
					break;
				case 4 :
					// ServiceManager.getManager().getPlayerItemsFromShopService().saveVipExp();
					break;
			}
		}
	}

	public class TaskThread2 implements Runnable {
		private Player player;

		public TaskThread2(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			PlayerStaWeek npsw;
			PlayerStaWeek opsw;
			int level;
			int exp;// 经验排行
			int winNum;
			int gold;
			int ticket;
			int levelRank;// 等级排行
			int winNumRank;// 胜利次数排行
			int goldRank;// 金币排行
			int ticketRank;// 钻石排行
			for (int type = 0; type < 2; type++) {
				Calendar calendar = Calendar.getInstance();
				int lastWrmNum = 0;
				int wrmNum = 0;
				switch (type) {
					case 0 :
						wrmNum = calendar.get(Calendar.WEEK_OF_YEAR);
						calendar.add(Calendar.DAY_OF_MONTH, -7);
						lastWrmNum = calendar.get(Calendar.WEEK_OF_YEAR);
						break;
					case 1 :
						wrmNum = calendar.get(Calendar.MONTH);
						calendar.add(Calendar.MONTH, -1);
						lastWrmNum = calendar.get(Calendar.MONTH);
						break;
				}
				opsw = ServiceManager.getManager().getPlayerStaWeekService().getPlayerStaWeekByPlayerId(player.getId(), type, lastWrmNum);
				npsw = ServiceManager.getManager().getPlayerStaWeekService().getPlayerStaWeekByPlayerId(player.getId(), type, wrmNum);
				if (null == npsw) {
					npsw = new PlayerStaWeek();
					npsw.setPlayerId(player.getId());
					npsw.setAreaId(WorldServer.config.getAreaId());
				}
				level = player.getLevel();
				exp = player.getExp();
				winNum = player.getWinTimes1v1Athletics() + player.getWinTimes1v1Champion() + player.getWinTimes1v1Relive()
						+ player.getWinTimes2v2Athletics() + player.getWinTimes2v2Champion() + player.getWinTimes2v2Relive()
						+ player.getWinTimes3v3Athletics() + player.getWinTimes3v3Champion() + player.getWinTimes3v3Relive();
				gold = player.getMoneyGold();
				ticket = player.getAmount();
				if (null == opsw) {
					opsw = new PlayerStaWeek();
					opsw.setPlayerId(player.getId());
					opsw.setAreaId(WorldServer.config.getAreaId());
					opsw.setLevel(level);
					opsw.setExp(exp);
					opsw.setWinNum(winNum);
					opsw.setGold(gold);
					opsw.setTicket(ticket);
					opsw.setLevelRank(0);
					opsw.setWinNumRank(0);
					opsw.setGoldRank(0);
					opsw.setTicketRank(0);
					opsw.setIsWeek(type);
					opsw.setWrmNum(lastWrmNum);
					ServiceManager.getManager().getPlayerStaWeekService().save(opsw);
				}
				levelRank = level - opsw.getLevel();
				winNumRank = winNum - opsw.getWinNum();
				goldRank = gold - opsw.getGold();
				ticketRank = ticket - opsw.getTicket();
				npsw.setLevel(level);
				npsw.setExp(exp);
				npsw.setWinNum(winNum);
				npsw.setGold(gold);
				npsw.setTicket(ticket);
				npsw.setLevelRank(levelRank);
				npsw.setWinNumRank(winNumRank);
				npsw.setGoldRank(goldRank);
				npsw.setTicketRank(ticketRank);
				npsw.setIsWeek(type);
				npsw.setWrmNum(wrmNum);
				ServiceManager.getManager().getPlayerStaWeekService().save(npsw);
			}
		}
	}

	/**
	 * 重置排行榜数据
	 */
	public void initialTopRecord() {
		// 存放老记录
		List<WorldPlayer> playerList1 = new ArrayList<WorldPlayer>(ServiceManager.getManager().getPlayerService().getAllPlayer());
		if ((null != nowLevel && !nowLevel.isEmpty()) && (null != nowWin && !nowWin.isEmpty()) && (null != nowFight && !nowFight.isEmpty())) {
			for (int i = 0; i < GetTopRecordHandler.MAX_COUNT && i < playerList1.size(); i++) {
				if (i < nowLevel.size()) {
					Common.oldLevelMap.put(nowLevel.get(i).getName(), i + 1);
				}
				if (i < nowWin.size()) {
					Common.oldWinMap.put(nowWin.get(i).getName(), i + 1);
				}
				if (i < nowFight.size()) {
					Common.oldFightMap.put(nowFight.get(i).getName(), i + 1);
				}
			}
		}
		nowLevel = ServiceManager.getManager().getPlayerService().getService().getPlayerRecord(0);
		nowWin = ServiceManager.getManager().getPlayerService().getService().getPlayerRecord(1);
		nowGold = ServiceManager.getManager().getPlayerService().getService().getPlayerRecord(2);
		nowTick = ServiceManager.getManager().getPlayerService().getService().getPlayerRecord(3);
		List<WorldPlayer> playerList = new ArrayList<WorldPlayer>(ServiceManager.getManager().getPlayerService().getAllPlayer());
		Collections.sort(playerList, fcComparator);
		List<Record> fightList = new ArrayList<Record>();
		Record record;
		WorldPlayer player;
		for (int i = 0; i < GetTopRecordHandler.MAX_COUNT && i < playerList.size(); i++) {
			player = playerList.get(i);
			record = new Record();
			fightList.add(record);
			record.setId(player.getId());
			record.setName(player.getName());
			record.setData(player.getFighting());

		}
		nowFight = fightList;
		nowHP = fightList;
		// 攻击力排行
		nowAttack = sortAttack(playerList);
		// 生命排行
		nowHP = sortHP(playerList);
	}

	private List<Record> sortAttack(List<WorldPlayer> playerList) {
		Collections.sort(playerList, attackComparator);
		List<Record> attackList = new ArrayList<Record>();
		Record record;
		WorldPlayer player;
		for (int i = 0; i < GetTopRecordHandler.MAX_COUNT && i < playerList.size(); i++) {
			player = playerList.get(i);
			record = new Record();
			attackList.add(record);
			record.setId(player.getId());
			record.setName(player.getName());
			record.setData(player.getAttack());
		}
		return attackList;
	}

	private List<Record> sortHP(List<WorldPlayer> playerList) {
		Collections.sort(playerList, hpComparator);
		List<Record> hpList = new ArrayList<Record>();
		Record record;
		WorldPlayer player;
		for (int i = 0; i < GetTopRecordHandler.MAX_COUNT && i < playerList.size(); i++) {
			player = playerList.get(i);
			record = new Record();
			hpList.add(record);
			record.setId(player.getId());
			record.setName(player.getName());
			record.setData(player.getMaxHP());
		}
		return hpList;
	}

	@SuppressWarnings("unchecked")
	public void initialLogService() {
		ToolsLog toolsLog;
		List<Tools> toolsList = logService.getAll(Tools.class);
		for (Tools tool : toolsList) {
			toolsLog = new ToolsLog();
			toolsLogMap.put(tool.getId(), toolsLog);
			toolsLog.setToolsId(tool.getId());
			toolsLog.setType(tool.getType());
			toolsLog.setUsedTimes(0);
		}
		TaskLog taskLog;
		List<Task> taskList = logService.getAll(Task.class);
		for (Task task : taskList) {
			taskLog = new TaskLog();
			taskLogMap.put(task.getId(), taskLog);
			taskLog.setTaskId(task.getId());
			taskLog.setGetTaskNum(0);
			taskLog.setFinishTaskNum(0);
		}
		ShopItemLog itemLog;
		List<ShopItem> shopItemList = ServiceManager.getManager().getShopItemService().getAllOnSaleShopList(0);
		for (ShopItem shopItem : shopItemList) {
			itemLog = new ShopItemLog();
			itemLogMap.put(shopItem.getId(), itemLog);
			itemLog.setItemId(shopItem.getId());
			itemLog.setSelledNum(0);
		}
		BattleLog battleLog;
		String key;
		for (int i1 = 1; i1 < 4; i1++) {
			for (int i2 = 1; i2 < 4; i2++) {
				for (int i3 = 1; i3 < 3; i3++) {
					for (int i4 = 0; i4 < 2; i4++) {
						battleLog = new BattleLog();
						key = "" + i1 + i2 + i3 + i4;
						battleLogMap.put(key, battleLog);
						battleLog.setBattleMode(i1);
						battleLog.setPlayerNumMode(i2);
						battleLog.setTogetherType(i3);
						battleLog.setFightWithAi(i4);
					}
				}
			}
		}
	}

	/**
	 * 保存玩家上线日志
	 * 
	 * @param player
	 */
	public void savePlayerOnlineLog(WorldPlayer player) {
		Date sdate = new Date(player.getLoginTime());
		Date edate = new Date();
		int rep = (int) (edate.getTime() - player.getLoginTime()) / 60000;
		PlayerOnline playerOnline = new PlayerOnline();
		playerOnline.getPlayer().setId(player.getId());
		playerOnline.setOnTime(sdate);
		playerOnline.setOffTime(edate);
		playerOnline.setRep(rep);
		playerOnline.setAreaId(player.getPlayer().getAreaId());
		logService.save(playerOnline);
		// player.setLoginTime(0);
		player.setLastOnLineTime(System.currentTimeMillis());
	}

	// private void savePropsSkillLog() {
	// Date date = new Date();
	// PropsSkillsUsed propsSkillsUsed;
	// for (ToolsLog toolsLog : toolsLogMap.values()) {
	// propsSkillsUsed = new PropsSkillsUsed();
	// propsSkillsUsed.setInday(date);
	// propsSkillsUsed.getTool().setId(toolsLog.getToolsId());
	// propsSkillsUsed.setType(toolsLog.getType());
	// propsSkillsUsed.setUsedTimes(toolsLog.getUsedTimes());
	// logService.save(propsSkillsUsed);
	// toolsLog.setUsedTimes(0);
	// }
	// }
	/**
	 * 更新技能道具使用次数
	 * 
	 * @param tool
	 */
	public void updatePropsSkillLog(Tools tool) {
		ToolsLog toolsLog = toolsLogMap.get(tool.getId());
		if (null == toolsLog) {
			toolsLog = new ToolsLog();
			toolsLogMap.put(tool.getId(), toolsLog);
			toolsLog.setToolsId(tool.getId());
			toolsLog.setType(tool.getType());
			toolsLog.setUsedTimes(0);
		}
		toolsLog.setUsedTimes(toolsLog.getUsedTimes() + 1);
	}

	// private void saveBattleLog() {
	// Date date = new Date();
	// BattleCount battleCount;
	// for (BattleLog battleLog : battleLogMap.values()) {
	// battleCount = new BattleCount();
	// battleCount.setBattleMode(battleLog.getBattleMode());
	// battleCount.setPlayerNumMode(battleLog.getPlayerNumMode());
	// battleCount.setInday(date);
	// battleCount.setBattleTimes(battleLog.getBattleTimes());
	// battleCount.setAverageTime(battleLog.getAverageTime());
	// battleCount.setTogetherType(battleLog.getTogetherType());
	// battleCount.setFightWithAi(battleLog.getFightWithAi());
	// logService.save(battleCount);
	// battleLog.setBattleTimes(0);
	// battleLog.setAverageTime(0);
	// }
	// }
	/**
	 * 更新战斗日志
	 * 
	 * @param battleTeam
	 */
	public void updateBattleLog(BattleTeam battleTeam) {
		String key = "" + battleTeam.getBattleMode() + battleTeam.getPlayerNumMode() + battleTeam.getTogetherType()
				+ battleTeam.getFightWithAI();
		BattleLog battleLog = battleLogMap.get(key);
		battleLog.setBattleTimes(battleLog.getBattleTimes() + 1);
		int length = (int) (System.currentTimeMillis() - battleTeam.getStartTime());
		battleLog.setAverageTime(battleLog.getAverageTime() + length);
	}

	/**
	 * 保存玩家金币变更记录
	 * 
	 * @param playerId
	 * @param amount
	 * @param origin
	 * @param remark
	 */
	public void saveGoldLog(int playerId, int amount, String origin, String remark) {
		GoldCount goldCount = new GoldCount();
		goldCount.setPlayerId(playerId);
		goldCount.setCreateTime(new Date());
		goldCount.setAmount(amount);
		goldCount.setOrigin(origin);
		goldCount.setRemark(remark);
		logService.save(goldCount);
	}

	/**
	 * 保存玩家等级变更记录
	 * 
	 * @param player
	 */
	public void savePlayerLevelLog(WorldPlayer player) {
		PlayerLevel playerLevel = new PlayerLevel();
		playerLevel.getPlayer().setId(player.getId());
		playerLevel.setLevel(player.getLevel());
		playerLevel.setChangeTime(new Date());
		logService.save(playerLevel);
	}

	/**
	 * 获取玩家最高等级
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getPlayerTopLevel(int playerId) {
		List<PlayerLevel> playerLevel = logService.getList("from PlayerLevel where playerId=? order by level desc limit 1",
				new Object[]{playerId});
		if (null != playerLevel && playerLevel.size() > 0) {
			return playerLevel.get(0).getLevel();
		} else {
			return 0;
		}
	}

	// /**
	// * 保存任务每天完成情况日志
	// */
	// private void saveTaskLog() {
	// TaskEveryday taskEveryday;
	// for (TaskLog taskLog : taskLogMap.values()) {
	// taskEveryday = new TaskEveryday();
	// taskEveryday.getTask().setId(taskLog.getTaskId());
	// taskEveryday.setStaTime(new Date());
	// taskEveryday.setGetTaskNum(taskLog.getGetTaskNum());
	// taskEveryday.setFinishTaskNum(taskLog.getFinishTaskNum());
	// logService.save(taskEveryday);
	// taskLog.setGetTaskNum(0);
	// taskLog.setFinishTaskNum(0);
	// }
	// }
	/**
	 * 更新任务接受或完成记录
	 * 
	 * @param taskId
	 * @param type
	 *            0为接受记录，1为完成记录
	 */
	public void updateTaskLog(int taskId, int type) {
		TaskLog taskLog = taskLogMap.get(taskId);
		if (null == taskLog) {
			taskLog = new TaskLog();
			taskLogMap.put(taskId, taskLog);
			taskLog.setTaskId(taskId);
			taskLog.setGetTaskNum(0);
			taskLog.setFinishTaskNum(0);
		}
		if (0 == type) {
			taskLog.setGetTaskNum(taskLog.getGetTaskNum() + 1);
		} else {
			taskLog.setFinishTaskNum(taskLog.getFinishTaskNum() + 1);
		}
	}

	private void saveShopItemLog() {
		ShoppingEveryday shoppingEveryday;
		for (ShopItemLog itemLog : itemLogMap.values()) {
			shoppingEveryday = new ShoppingEveryday();
			shoppingEveryday.getShopItem().setId(itemLog.getItemId());
			shoppingEveryday.setStaTime(new Date());
			shoppingEveryday.setSelledNum(itemLog.getSelledNum());
			logService.save(shoppingEveryday);
			itemLog.setSelledNum(0);
		}
	}

	/**
	 * 更新商品购买记录
	 * 
	 * @param itemId
	 * @param count
	 *            购买数量
	 */
	public void updateShopItemLog(int itemId) {
		ShopItemLog itemLog = itemLogMap.get(itemId);
		if (null == itemLog) {
			itemLog = new ShopItemLog();
			itemLogMap.put(itemId, itemLog);
			itemLog.setItemId(itemId);
			itemLog.setSelledNum(0);
		}
		itemLog.setSelledNum(itemLog.getSelledNum() + 1);
	}

	/**
	 * 清除过期排行数据
	 * 
	 * @param type
	 *            0周，1月
	 */
	public void clearTopData(int type) {
		Calendar calendar = Calendar.getInstance();
		int lastWrmNum = 0;
		int wrmNum = 0;
		switch (type) {
			case 0 :
				wrmNum = calendar.get(Calendar.WEEK_OF_YEAR);
				calendar.add(Calendar.DAY_OF_MONTH, -7);
				lastWrmNum = calendar.get(Calendar.WEEK_OF_YEAR);
				break;
			case 1 :
				wrmNum = calendar.get(Calendar.MONTH);
				calendar.add(Calendar.MONTH, -1);
				lastWrmNum = calendar.get(Calendar.MONTH);
				break;
		}
		System.out.println("wrmNum:" + wrmNum + "-----lastWrmNum:" + lastWrmNum);
		ServiceManager.getManager().getPlayerStaWeekService().clearTopData(type, wrmNum, lastWrmNum);
	}

	/**
	 * 保存玩家周数据统计
	 * 
	 * @param player
	 */
	public void saveWOMTopRecord(Player player) {
		ServiceManager.getManager().getSimpleThreadPool().execute(new TaskThread2(player));
	}

	/**
	 * 获取玩家最后一次登录时间
	 * 
	 * @param playerid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long getLastLoginTime(int playerid) {
		List<PlayerOnline> poList = logService.getList("from PlayerOnline where playerId = ? order by id desc limit 1",
				new Object[]{playerid});
		if (null != poList && poList.size() > 0) {
			return poList.get(0).getOnTime().getTime();
		} else {
			return 0;
		}
	}

	/**
	 * 获得玩家周等级增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getWeekLevel() {
		if (null == weekLevel) {
			Calendar calendar_w = Calendar.getInstance();
			calendar_w.add(Calendar.DAY_OF_MONTH, -7);
			int wrmNum_w = calendar_w.get(Calendar.WEEK_OF_YEAR);
			weekLevel = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(0, wrmNum_w, "levelRank", "levelRank desc, exp", GetTopRecordHandler.MAX_COUNT);
		}
		return weekLevel;
	}

	/**
	 * 获得玩家周胜利增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getWeekWin() {
		if (null == weekWin) {
			Calendar calendar_w = Calendar.getInstance();
			calendar_w.add(Calendar.DAY_OF_MONTH, -7);
			int wrmNum_w = calendar_w.get(Calendar.WEEK_OF_YEAR);
			weekWin = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(0, wrmNum_w, "winNumRank", "winNumRank", GetTopRecordHandler.MAX_COUNT);
		}
		return weekWin;
	}

	/**
	 * 获得玩家周金币增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getWeekGold() {
		if (null == weekGold) {
			Calendar calendar_w = Calendar.getInstance();
			calendar_w.add(Calendar.DAY_OF_MONTH, -7);
			int wrmNum_w = calendar_w.get(Calendar.WEEK_OF_YEAR);
			weekGold = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(0, wrmNum_w, "goldRank", "goldRank", GetTopRecordHandler.MAX_COUNT);
		}
		return weekGold;
	}

	/**
	 * 获得玩家周钻石增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getWeekTick() {
		if (null == weekTick) {
			Calendar calendar_w = Calendar.getInstance();
			calendar_w.add(Calendar.DAY_OF_MONTH, -7);
			int wrmNum_w = calendar_w.get(Calendar.WEEK_OF_YEAR);
			weekTick = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(0, wrmNum_w, "ticketRank", "ticketRank", GetTopRecordHandler.MAX_COUNT);
		}
		return weekTick;
	}

	/**
	 * 获得玩家月等级增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getMonthLevel() {
		if (null == monthLevel) {
			Calendar calendar_m = Calendar.getInstance();
			calendar_m.add(Calendar.MONTH, -1);
			int wrmNum_m = calendar_m.get(Calendar.MONTH);
			monthLevel = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(1, wrmNum_m, "levelRank", "levelRank desc, exp", GetTopRecordHandler.MAX_COUNT);
		}
		return monthLevel;
	}

	/**
	 * 获得玩家月胜利次数增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getMonthWin() {
		if (null == monthWin) {
			Calendar calendar_m = Calendar.getInstance();
			calendar_m.add(Calendar.MONTH, -1);
			int wrmNum_m = calendar_m.get(Calendar.MONTH);
			monthWin = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(1, wrmNum_m, "winNumRank", "winNumRank", GetTopRecordHandler.MAX_COUNT);
		}
		return monthWin;
	}

	/**
	 * 获得玩家月金币增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getMonthGold() {
		if (null == monthGold) {
			Calendar calendar_m = Calendar.getInstance();
			calendar_m.add(Calendar.MONTH, -1);
			int wrmNum_m = calendar_m.get(Calendar.MONTH);
			monthGold = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(1, wrmNum_m, "goldRank", "goldRank", GetTopRecordHandler.MAX_COUNT);
		}
		return monthGold;
	}

	/**
	 * 获得玩家月钻石增加排行榜
	 * 
	 * @return
	 */
	public List<PlayerStaWeek> getMonthTick() {
		if (null == monthTick) {
			Calendar calendar_m = Calendar.getInstance();
			calendar_m.add(Calendar.MONTH, -1);
			int wrmNum_m = calendar_m.get(Calendar.MONTH);
			monthTick = ServiceManager.getManager().getPlayerStaWeekService()
					.getTopRecord(1, wrmNum_m, "ticketRank", "ticketRank", GetTopRecordHandler.MAX_COUNT);
		}
		return monthTick;
	}

	/**
	 * 获得玩家总等级增加排行榜
	 * 
	 * @return
	 */
	public List<Record> getNowLevel() {
		return nowLevel;
	}

	/**
	 * 获得玩家总胜利次数排行榜
	 * 
	 * @return
	 */
	public List<Record> getNowWin() {
		return nowWin;
	}

	/**
	 * 获得玩家总金币排行榜
	 * 
	 * @return
	 */
	public List<Record> getNowGold() {
		return nowGold;
	}

	/**
	 * 获得玩家战斗力总榜
	 * 
	 * @return
	 */
	public List<Record> getNowFight() {
		return nowFight;
	}

	/**
	 * 获得玩家攻击力总榜
	 * 
	 * @return
	 */
	public List<Record> getNowAttack() {
		return nowAttack;
	}

	/**
	 * 获得玩家血量总榜
	 * 
	 * @return
	 */
	public List<Record> getNowHP() {
		return nowHP;
	}

	/**
	 * 获得玩家总钻石排行榜
	 * 
	 * @return
	 */
	public List<Record> getNowTick() {
		return nowTick;
	}

	class FightingComparator implements Comparator<WorldPlayer> {
		public int compare(WorldPlayer player1, WorldPlayer player2) {
			int f = player2.getFighting() - player1.getFighting();
			if (f < 0)
				f = -1;
			if (f > 0)
				f = 1;
			return f;
		}
	}

	class AttackComparator implements Comparator<WorldPlayer> {
		public int compare(WorldPlayer player1, WorldPlayer player2) {
			return player2.getAttack() - player1.getAttack();

		}
	}

	public static void main(String[] args) {
		Calendar calendar_w = Calendar.getInstance();
		calendar_w.add(Calendar.DAY_OF_MONTH, -7);
		int wrmNum_w = calendar_w.get(Calendar.WEEK_OF_YEAR);
		System.out.println(wrmNum_w);
	}

	class HPComparator implements Comparator<WorldPlayer> {
		public int compare(WorldPlayer player1, WorldPlayer player2) {
			return player2.getMaxHP() - player1.getMaxHP();

		}
	}

}
