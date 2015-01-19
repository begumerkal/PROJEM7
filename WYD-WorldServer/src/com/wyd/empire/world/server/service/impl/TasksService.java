package com.wyd.empire.world.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.PlayerTaskTitle;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.SerializeUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerTaskTitleService;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.empire.world.task.PlayerTaskInfo;
import com.wyd.empire.world.task.TaskKey;

public class TasksService {
	private ITaskService taskService;
	private long lastUpdateTaskTime = 0;

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public void oneDay() {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":" + "TasksService oneDay runing...");
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask());
	}

	/**
	 * 重置日常任务
	 * 
	 * @return
	 */
	private Runnable createTask() {
		return new TaskThread();
	}

	public class TaskThread implements Runnable {
		@Override
		public void run() {
			Collection<WorldPlayer> playerList = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
			for (WorldPlayer player : playerList) {
				if (!DateUtil.isSameDate(player.getPlayer().getTaskUpdateTime(), new Date())) {
					ServiceManager.getManager().getPlayerTaskTitleService().initialPlayerTask(player);
				}
			}
		}
	}

	/**
	 * 保存玩家任务列表信息
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @throws Exception
	 *             复制对象内容出错时抛出此异常
	 */
	public List<PlayerTask> savePlayerTask(WorldPlayer worldPlayer) {
		PlayerTaskTitle playerTaskTitle = ServiceManager.getManager().getPlayerTaskTitleService()
				.getPlayerTaskTitleByPlayerId(worldPlayer.getId());
		if (playerTaskTitle == null) {
			playerTaskTitle = new PlayerTaskTitle();
			playerTaskTitle.setPlayer(worldPlayer.getPlayer());
		}
		List<PlayerTask> playerTaskVoList = new ArrayList<PlayerTask>();
		for (PlayerTask playerTaskVo : worldPlayer.getTaskIngList()) {
			playerTaskVoList.add(playerTaskVo);
		}
		playerTaskTitle.setTaskList(SerializeUtil.serialize(playerTaskVoList));
		taskService.save(playerTaskTitle);
		return playerTaskVoList;
	}

	public ITaskService getService() {
		return taskService;
	}

	/**
	 * 检查战斗任务
	 * 
	 * @param battleTeam
	 * @return
	 */
	public void battleTask(BattleTeam battleTeam, int winCamp) {
		List<Integer> subTypes = new ArrayList<Integer>();
		List<Integer> baseTargets = new ArrayList<Integer>();
		List<Integer> baseCounts = new ArrayList<Integer>();
		// 任务完成条件检查
		StringBuffer chead = new StringBuffer();
		switch (battleTeam.getBattleMode()) {
			case 1 :
				chead.append(TaskKey.BSJJ);
				chead.append(",");
				subTypes.add(1);
				subTypes.add(2);
				baseTargets.add(1);
				baseCounts.add(1);
				break;
			case 2 :
				chead.append(TaskKey.BSFH);
				chead.append(",");
				subTypes.add(1);
				subTypes.add(3);
				baseTargets.add(1);
				baseCounts.add(1);
				break;
			case 3 :
				chead.append(TaskKey.BSHZ);
				chead.append(",");
				subTypes.add(1);
				subTypes.add(4);
				baseTargets.add(1);
				baseCounts.add(1);
				break;
			case 4 :
				chead.append(TaskKey.BSPW);
				chead.append(",");
				break;
			case 5 :
				chead.append(TaskKey.BSGH);
				chead.append(",");
				subTypes.add(1);
				subTypes.add(5);
				baseTargets.add(1);
				baseCounts.add(1);
				break;
			case 6 :
				chead.append(TaskKey.BSDW);
				chead.append(",");
				break;
		}
		switch (battleTeam.getPlayerNumMode()) {
			case 1 :
				chead.append(TaskKey.BPNO);
				chead.append(",");
				break;
			case 2 :
				chead.append(TaskKey.BPNT);
				chead.append(",");
				break;
			case 3 :
				chead.append(TaskKey.BPNS);
				chead.append(",");
				break;
		}
		StringBuffer cbody;
		for (Combat combat : battleTeam.getCombatList()) {
			if (combat.isRobot()) {
				continue;
			}
			List<Integer> targets = new ArrayList<Integer>(baseTargets);
			List<Integer> counts = new ArrayList<Integer>(baseCounts);
			if (battleTeam.getFirstHurtPlayerId() == combat.getId()) {
				targets.add(3);
				counts.add(1);
			}
			targets.add(4);
			counts.add(combat.getKillCount());
			cbody = new StringBuffer(chead.toString());
			boolean ghdz = false;
			boolean hhdz = false;
			boolean fqdz = false;
			List<Integer> playerList = new ArrayList<Integer>();
			for (Combat cb : battleTeam.getCombatList()) {
				if (combat.getCamp() == cb.getCamp() && combat.getId() != cb.getId()) {
					playerList.add(cb.getId());
				}
			}
			if (ServiceManager.getManager().getPlayerSinConsortiaService().checkPlayerInSameConsortia(combat.getId(), playerList)) {
				cbody.append(TaskKey.BRGX);
				cbody.append(",");
				ghdz = true;
			}
			if (ServiceManager.getManager().getFriendService().checkSomePlayerIsFriend(combat.getId(), playerList)) {
				cbody.append(TaskKey.BRHX);
				cbody.append(",");
				hhdz = true;
			}
			if (ServiceManager.getManager().getFriendService().checkSomePlayerIsMarry(combat.getId(), combat.getSex(), playerList)) {
				cbody.append(TaskKey.BRFQZ);
				cbody.append(",");
				fqdz = true;
			}
			playerList = null;
			if (combat.getActionTimes() < 1) {
				cbody.append(TaskKey.BRWXS);
				cbody.append(",");
			}
			if (combat.getBeKilledCount() > 0 && combat.getBeKillRound() < 3) {
				cbody.append(TaskKey.BRLHS);
				cbody.append(",");
			}
			if (combat.getShootTimes() == combat.getHuntTimes()) {
				cbody.append(TaskKey.BRMZ);
				cbody.append(",");
			}
			if (!combat.isRobot() && !combat.isLost()) {
				if (combat.getCamp() == winCamp) {
					if (subTypes.size() > 0) {
						targets.add(2);
						counts.add(1);
					}
					combat.getPlayer().setWin(combat.getPlayer().getWin() + 1);
					cbody.append(TaskKey.BRHS);
					cbody.append(",");
					cbody.append(TaskKey.BRLS);
					cbody.append("*");
					cbody.append(combat.getPlayer().getWin());
					cbody.append(",");
					if (ghdz) {
						combat.getPlayer().setGhwin(combat.getPlayer().getGhwin() + 1);
						cbody.append(TaskKey.BRGXS);
						cbody.append(",");
						for (int i = 3; i <= combat.getPlayer().getGhwin(); i++) {
							cbody.append(TaskKey.BRGLS);
							cbody.append("*");
							cbody.append(i);
							cbody.append(",");
						}
					} else {
						combat.getPlayer().setGhwin(0);
					}
					if (hhdz) {
						combat.getPlayer().setHhwin(combat.getPlayer().getHhwin() + 1);
						cbody.append(TaskKey.BRHXS);
						cbody.append(",");
						for (int i = 3; i <= combat.getPlayer().getHhwin(); i++) {
							cbody.append(TaskKey.BRHLS);
							cbody.append("*");
							cbody.append(i);
							cbody.append(",");
						}
					} else {
						combat.getPlayer().setHhwin(0);
					}
					if (fqdz) {
						combat.getPlayer().setFqwin(combat.getPlayer().getFqwin() + 1);
						cbody.append(TaskKey.BRFQS);
						cbody.append(",");
						for (int i = 3; i <= combat.getPlayer().getFqwin(); i++) {
							cbody.append(TaskKey.BRFQLS);
							cbody.append("*");
							cbody.append(i);
							cbody.append(",");
						}
					} else {
						combat.getPlayer().setFqwin(0);
					}
				} else {
					combat.getPlayer().setWin(0);
					combat.getPlayer().setGhwin(0);
					combat.getPlayer().setHhwin(0);
					combat.getPlayer().setFqwin(0);
				}
			}
			taskService.playerTaskCheck(combat.getPlayer(), cbody.toString(), 1, TaskKey.DAY_TASK_ID_BATTLE, subTypes, targets, counts,
					null);
		}
	}

	/**
	 * 世界BOSS
	 * 
	 * @param player
	 */
	public void worldBoss(WorldPlayer player) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, TaskKey.BSSJ + ",", 1, TaskKey.DAY_TASK_ID_BOSS, subTypes, targets, counts, null);
	}

	/**
	 * 更新战斗中使用道具的任务
	 * 
	 * @param combat
	 * @param tNo
	 *            本阵营人数
	 * @param toolsList
	 */
	public void battleToolsTask(Combat combat, BattleTeam battleTeam, int tNo, List<Tools> toolsList) {
		// 任务完成条件------------------------------使用技能，道具，给队友
		if (!combat.isRobot() && !combat.isLost()) {
			for (Tools tools : toolsList) {
				String condition = TaskKey.SYDJ + "*-1,";
				condition += TaskKey.SYDJ + "*" + tools.getId() + ",";
				if (null != tools && 1 == tools.getType()) {
					if (tNo > 1 && (1 == tools.getSubtype() || 5 == tools.getSubtype())) {
						condition += TaskKey.DMDY + ",";
					}
					if (3 == battleTeam.getBattleMode()) {
						condition += TaskKey.PWS + ",";
					} else if (1 == battleTeam.getBattleMode() && combat.isDead()) {
						condition += TaskKey.JJGH + ",";
					}
					taskService.playerTaskCheck(combat.getPlayer(), condition, 1, 0, null, null, null, null);
				}
			}
		}
	}

	/**
	 * 使用飞行技能
	 */
	public void flySkill(WorldPlayer player) {
		String condition = TaskKey.SYDJ + "*1,";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 更新击退玩家的任务
	 * 
	 * @param combat
	 * @param combatList
	 */
	public void battleBeatTask(Combat combat, Combat dead) {
		if (combat.isRobot()) {
			return;
		}
		String condition = "";
		if (combat.getSex() != dead.getSex()) {
			condition += TaskKey.JXYX + ",";
		}
		if (combat.getCamp() != dead.getCamp()) {
			condition += TaskKey.JZDF + ",";
		} else {
			condition += TaskKey.JZYF + ",";
		}
		taskService.playerTaskCheck(combat.getPlayer(), condition, 1, 0, null, null, null, null);
	}

	/**
	 * 更新击退玩家的任务
	 * 
	 * @param player
	 * @param shootSex
	 * @param shootCamp
	 * @param deadSex
	 * @param deadCamp
	 */
	public void battleBeatTask(WorldPlayer player, int shootSex, int shootCamp, int[] deadSex, int[] deadCamp) {
		for (int i = 0; i < deadSex.length; i++) {
			String condition = "";
			if (shootSex != deadSex[i]) {
				condition += TaskKey.JXYX + ",";
			}
			if (shootCamp != deadCamp[i]) {
				condition += TaskKey.JZDF + ",";
			} else {
				condition += TaskKey.JZYF + ",";
			}
			taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
		}
	}

	/**
	 * 添加好友
	 * 
	 * @param player
	 */
	public void addFriend(WorldPlayer player) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(2);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, TaskKey.HDFRIEND + ",", 1, TaskKey.DAY_TASK_ID_FRIEND, subTypes, targets, counts, null);
	}

	/**
	 * 更新在线时间任务
	 */
	public void updateOnline(WorldPlayer player) {
		// ServiceManager.getManager().getTaskService().getService().playerTaskCheck(player,
		// TaskKey.ONLINE+"*"+player.getOnLineTime()+",");
		taskService.playerTaskCheck(player, TaskKey.ONLINE + ",", 1, 0, null, null, null, null);
	}

	/**
	 * 更新创建或加入公会的任务
	 * 
	 * @param player
	 * @param type
	 *            0：创建公会，1:加入公会
	 */
	public void updateConsortiaTask(WorldPlayer player, int type) {
		switch (type) {
			case 0 :
				taskService.playerTaskCheck(player, TaskKey.GHCJ + ",", 1, 0, null, null, null, null);
				break;
			case 1 :
				taskService.playerTaskCheck(player, TaskKey.GHJR + ",", 1, 0, null, null, null, null);
				break;
		}
	}

	/**
	 * 购买物品
	 * 
	 * @param player
	 * @param itemId
	 */
	public void buySomething(WorldPlayer player, int itemId, int count) {
		String condition = TaskKey.GMWP + "*-1,";
		condition += TaskKey.GMWP + "*" + itemId + ",";
		taskService.playerTaskCheck(player, condition, count, 0, null, null, null, null);
	}

	/**
	 * 使用物品
	 * 
	 * @param player
	 * @param itemId
	 * @param type
	 *            0道具，1物品
	 */
	public void useSomething(WorldPlayer player, int itemId, int type) {
		String condition = "";
		switch (type) {
			case 0 :
				condition += TaskKey.SYDJ + "*-1,";
				condition += TaskKey.SYDJ + "*" + itemId + ",";
				break;
			case 1 :
				condition += TaskKey.SYWP + "*-1,";
				condition += TaskKey.SYWP + "*" + itemId + ",";
				break;
		}
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 装备物品
	 * 
	 * @param player
	 * @param itemId
	 */
	public void useEquipment(WorldPlayer player, int itemId) {
		String condition = TaskKey.ZBWP + "*-1,";
		condition += TaskKey.ZBWP + "*" + itemId + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 玩家升级
	 * 
	 * @param player
	 */
	public void upLevel(WorldPlayer player, int level) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 1; i <= level; i++) {
			sbf.append(TaskKey.LEVEL);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
		}
		taskService.playerTaskCheck(player, sbf.toString(), 1, 0, null, null, null, null);
	}

	/**
	 * 玩家使用大招
	 * 
	 * @param player
	 */
	public void useBigSkill(WorldPlayer player) {
		String condition = TaskKey.BIGSKILL + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 进入界面
	 * 
	 * @param player
	 * @param iId
	 */
	public void enterInterface(WorldPlayer player, int iId) {
		String condition = TaskKey.JRJM + "*" + iId + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 充值
	 * 
	 * @param player
	 * @param ticket
	 */
	public void czticket(WorldPlayer player, int ticket) {
		String condition = TaskKey.CZTICKET + "," + TaskKey.CZTICKET + "*60,";
		taskService.playerTaskCheck(player, condition, ticket, 0, null, null, null, null);
	}

	/**
	 * 完成新手教学
	 * 
	 * @param player
	 */
	public void tiroTask(WorldPlayer player) {
		String condition = TaskKey.XSRW + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 武器熟练度变更
	 * 
	 * @param player
	 * @param ticket
	 */
	public void wqsldRask(WorldPlayer player, int skillful) {
		String condition = TaskKey.WQSLD;
		if (skillful > 9999) {
			condition += "*100,";
		} else if (skillful > 4999) {
			condition += "*50,";
		}
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 通关副本
	 * 
	 * @param player
	 * @param fbId
	 */
	public void tgfb(WorldPlayer player, int fbId, int diff) {
		StringBuffer sbf = new StringBuffer();
		// 任意副本任意难度
		sbf.append(TaskKey.TGFB);
		sbf.append("*-1*-1,");
		// 指定副本指定难度
		sbf.append(TaskKey.TGFB);
		sbf.append("*");
		sbf.append(fbId);
		sbf.append("*");
		sbf.append(diff);
		sbf.append(",");
		// 任意副本指定难度
		sbf.append(TaskKey.TGFB);
		sbf.append("*-1*");
		sbf.append(diff);
		sbf.append(",");
		// 指定副本任意难度
		sbf.append(TaskKey.TGFB);
		sbf.append("*");
		sbf.append(fbId);
		sbf.append("*-1,");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		subTypes.add(2);
		subTypes.add(3);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_COPY, subTypes, targets, counts, fbId + "");
	}

	/**
	 * 通关单人副本
	 * 
	 * @param player
	 * @param fbId
	 */
	public void singMap(WorldPlayer player, int fbId) {
		StringBuffer sbf = new StringBuffer();
		// 通关任意单人副本
		sbf.append(TaskKey.TGDRFB);
		sbf.append("*-1,");
		// 通关指定单人副本
		sbf.append(TaskKey.TGDRFB);
		sbf.append("*");
		sbf.append(fbId);
		sbf.append(",");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		subTypes.add(2);
		subTypes.add(3);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_SINFLE, subTypes, targets, counts, fbId + "");
	}

	/**
	 * 击杀怪物
	 * 
	 * @param player
	 * @param gId
	 */
	public void killGuai(WorldPlayer player, int gId) {
		String condition = TaskKey.KILL + "*" + gId + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 强化任务
	 * 
	 * @param player
	 * @param level
	 */
	public void strengthen(WorldPlayer player, int itemId, int level) {
		StringBuffer sbf = new StringBuffer();
		// 任意物品任意等级
		sbf.append(TaskKey.QHWP);
		sbf.append("*-1*-1,");
		// 指定物品指定等级
		sbf.append(TaskKey.QHWP);
		sbf.append("*");
		sbf.append(itemId);
		sbf.append("*");
		sbf.append(level);
		sbf.append(",");
		// 任意物品指定等级
		sbf.append(TaskKey.QHWP);
		sbf.append("*-1*");
		sbf.append(level);
		sbf.append(",");
		// 指定物品任意等级
		sbf.append(TaskKey.QHWP);
		sbf.append("*");
		sbf.append(itemId);
		sbf.append("*-1,");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_ITEM, subTypes, targets, counts, null);
	}

	/**
	 * 升星任务
	 * 
	 * @param player
	 */
	public void upStar(WorldPlayer player, int itemId, int level) {
		StringBuffer sbf = new StringBuffer();
		// 任意物品任意等级
		sbf.append(TaskKey.SXWP);
		sbf.append("*-1*-1,");
		for (int i = 1; i <= level; i++) {
			// 指定物品指定等级
			sbf.append(TaskKey.SXWP);
			sbf.append("*");
			sbf.append(itemId);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
			// 任意物品指定等级
			sbf.append(TaskKey.SXWP);
			sbf.append("*-1*");
			sbf.append(i);
			sbf.append(",");
		}
		// 指定物品任意等级
		sbf.append(TaskKey.SXWP);
		sbf.append("*");
		sbf.append(itemId);
		sbf.append("*-1,");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(2);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_ITEM, subTypes, targets, counts, null);
	}

	/**
	 * 镶嵌任务
	 * 
	 * @param player
	 * @param itemId
	 */
	public void mosaic(WorldPlayer player, int itemId, int gemId) {
		StringBuffer sbf = new StringBuffer();
		// 任意物品任意宝石
		sbf.append(TaskKey.XQWP);
		sbf.append("*-1*-1,");
		// 指定物品指定宝石
		sbf.append(TaskKey.XQWP);
		sbf.append("*");
		sbf.append(itemId);
		sbf.append("*");
		sbf.append(gemId);
		sbf.append(",");
		// 任意物品指定宝石
		sbf.append(TaskKey.XQWP);
		sbf.append("*-1*");
		sbf.append(gemId);
		sbf.append(",");
		// 指定物品任意宝石
		sbf.append(TaskKey.XQWP);
		sbf.append("*");
		sbf.append(itemId);
		sbf.append("*-1,");
		taskService.playerTaskCheck(player, sbf.toString(), 1, 0, null, null, null, null);
	}

	/**
	 * 物品重铸
	 * 
	 * @param player
	 */
	public void czwp(WorldPlayer player, int itemId) {
		StringBuffer sbf = new StringBuffer();
		// 重铸任意物品
		sbf.append(TaskKey.CZWQ);
		sbf.append("*-1,");
		// 重铸指定物品
		sbf.append(TaskKey.CZWQ);
		sbf.append("*");
		sbf.append(itemId);
		sbf.append(",");
		taskService.playerTaskCheck(player, sbf.toString(), 1, 0, null, null, null, null);
	}

	/**
	 * 转移物品
	 * 
	 * @param player
	 * @param itemId
	 */
	public void zywp(WorldPlayer player, int itemId) {
		StringBuffer sbf = new StringBuffer();
		// 转移任意物品
		sbf.append(TaskKey.ZYWP);
		sbf.append("*-1,");
		// 转移指定物品
		sbf.append(TaskKey.ZYWP);
		sbf.append("*");
		sbf.append(itemId);
		sbf.append(",");
		taskService.playerTaskCheck(player, sbf.toString(), 1, 0, null, null, null, null);
	}

	/**
	 * 合成任务
	 * 
	 * @param player
	 * @param level
	 */
	public void synthesis(WorldPlayer player, int level) {
		String condition = TaskKey.HCWP + "*-1,";
		condition += TaskKey.HCWP + "*" + level + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 检测并更新玩家发送微博任务
	 * 
	 * @param player
	 *            玩家信息
	 */
	public void checkWeiBo(WorldPlayer player) {
		String condition = TaskKey.FXWB + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 绑定帐号
	 * 
	 * @param player
	 */
	public void bindAccount(WorldPlayer player) {
		String condition = TaskKey.ZCZH + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 结婚
	 * 
	 * @param player
	 */
	public void marry(WorldPlayer player) {
		String condition = TaskKey.JH + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 签到
	 * 
	 * @param player
	 */
	public void qd(WorldPlayer player) {
		String condition = TaskKey.MRQD + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 累积登陆
	 * 
	 * @param player
	 */
	public void logined(WorldPlayer player) {
		String condition = TaskKey.LJDL + ",";
		taskService.playerTaskCheck(player, condition, 1, 0, null, null, null, null);
	}

	/**
	 * 爱心许愿
	 * 
	 * @param player
	 */
	public void wishing(WorldPlayer player) {
		String condition = TaskKey.AXXY + ",";
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, condition, 1, TaskKey.DAY_TASK_ID_WISH, subTypes, targets, counts, null);
	}

	/**
	 * 巨龙遗迹探险
	 * 
	 * @param player
	 */
	public void exploreDragon(WorldPlayer player) {
		String condition = TaskKey.JLYJ + ",";
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(3);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, condition, 1, TaskKey.DAY_TASK_ID_ADVE, subTypes, targets, counts, null);
	}

	/**
	 * 黄金森林探险
	 * 
	 * @param player
	 */
	public void exploreGold(WorldPlayer player) {
		String condition = TaskKey.HJSL + ",";
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, condition, 1, TaskKey.DAY_TASK_ID_ADVE, subTypes, targets, counts, null);
	}

	/**
	 * 海贼宝藏探险
	 * 
	 * @param player
	 */
	public void explorePirates(WorldPlayer player) {
		String condition = TaskKey.HDBZ + ",";
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(2);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, condition, 1, TaskKey.DAY_TASK_ID_ADVE, subTypes, targets, counts, null);
	}

	/**
	 * 宠物驯服
	 * 
	 * @param player
	 */
	public void petTame(WorldPlayer player, int petId) {
		StringBuffer sbf = new StringBuffer();
		// 驯服任意宠物
		sbf.append(TaskKey.CWXF);
		sbf.append("*-1,");
		// 驯服指定宠物
		sbf.append(TaskKey.CWXF);
		sbf.append("*");
		sbf.append(petId);
		sbf.append(",");
		taskService.playerTaskCheck(player, sbf.toString(), 1, 0, null, null, null, null);
	}

	/**
	 * 宠物培养
	 * 
	 * @param player
	 */
	public void petCulture(WorldPlayer player, int petId) {
		StringBuffer sbf = new StringBuffer();
		// 培养任意宠物
		sbf.append(TaskKey.CWPY);
		sbf.append("*-1,");
		// 培养指定宠物
		sbf.append(TaskKey.CWPY);
		sbf.append("*");
		sbf.append(petId);
		sbf.append(",");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(2);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_PET, subTypes, targets, counts, null);
	}

	/**
	 * 宠物训练
	 * 
	 * @param player
	 */
	public void petTraining(WorldPlayer player, int petId) {
		StringBuffer sbf = new StringBuffer();
		// 训练任意宠物
		sbf.append(TaskKey.CWXL);
		sbf.append("*-1,");
		// 训练指定宠物
		sbf.append(TaskKey.CWXL);
		sbf.append("*");
		sbf.append(petId);
		sbf.append(",");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_PET, subTypes, targets, counts, null);
	}

	/**
	 * 玩家获得经验
	 * 
	 * @param player
	 * @param count
	 */
	public void getExp(WorldPlayer player, int count) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(count);
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_PLAYER, subTypes, targets, counts, null);
	}

	/**
	 * 玩家提升修炼
	 * 
	 * @param player
	 */
	public void practice(WorldPlayer player) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(TaskKey.JSXL);
		sbf.append(",");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(2);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_PLAYER, subTypes, targets, counts, null);
	}

	/**
	 * 玩家提升星魂
	 * 
	 * @param player
	 */
	public void soul(WorldPlayer player) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(TaskKey.XHTS);
		sbf.append(",");
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(3);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, sbf.toString(), 1, TaskKey.DAY_TASK_ID_PLAYER, subTypes, targets, counts, null);
	}

	/**
	 * 兑换卡牌
	 * 
	 * @param player
	 * @param quality
	 */
	public void dhkp(WorldPlayer player, int quality) {
		StringBuffer sbf = new StringBuffer();
		// 兑换任意品质卡牌
		sbf.append(TaskKey.DHKP);
		sbf.append("*-1,");
		// 兑换任指定质卡牌
		sbf.append(TaskKey.DHKP);
		sbf.append("*");
		sbf.append(quality);
		sbf.append(",");
		taskService.playerTaskCheck(player, sbf.toString(), 1, 0, null, null, null, null);
	}

	/**
	 * 增加金币
	 * 
	 * @param player
	 * @param count
	 */
	public void addGold(WorldPlayer player, int count) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(count);
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_GOLD, subTypes, targets, counts, null);
	}

	/**
	 * 消耗金币
	 * 
	 * @param player
	 * @param count
	 */
	public void useGold(WorldPlayer player, int count) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(2);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(Math.abs(count));
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_GOLD, subTypes, targets, counts, null);
	}

	/**
	 * 增加钻石
	 * 
	 * @param player
	 * @param count
	 */
	public void addDiamond(WorldPlayer player, int count) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(count);
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_DIAM, subTypes, targets, counts, null);
	}

	/**
	 * 消耗钻石
	 * 
	 * @param player
	 * @param count
	 */
	public void useDiamond(WorldPlayer player, int count) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(2);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(Math.abs(count));
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_DIAM, subTypes, targets, counts, null);
	}

	/**
	 * 聊天任务
	 * 
	 * @param player
	 * @param channel
	 *            1时间，2当前，3工会
	 */
	public void chat(WorldPlayer player, int channel) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(channel);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_CHAT, subTypes, targets, counts, null);
	}

	/**
	 * 好友聊天
	 * 
	 * @param player
	 */
	public void friendChat(WorldPlayer player) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_FRIEND, subTypes, targets, counts, null);
	}

	/**
	 * 完成每日任务
	 * 
	 * @param player
	 */
	public void doDayTask(WorldPlayer player) {
		List<Integer> subTypes = new ArrayList<Integer>();
		subTypes.add(1);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		List<Integer> counts = new ArrayList<Integer>();
		counts.add(1);
		taskService.playerTaskCheck(player, null, 1, TaskKey.DAY_TASK_ID_DAY, subTypes, targets, counts, null);
	}

	/**
	 * 完成玩家绑定帐号任务
	 * 
	 * @param worldPlayer
	 */
	public void checkRegisterTask(WorldPlayer worldPlayer) {
		if (!worldPlayer.getClient().getUdid().equals(worldPlayer.getClient().getName())) {
			bindAccount(worldPlayer);
		}
	}

	/**
	 * 根据玩家ID，获取玩家任务完成情况列表
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家任务完成情况列表
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerTaskInfo> getPlayerTaskInfoList(int playerId) {
		List<PlayerTaskInfo> playerTaskInfoList = null;
		List<PlayerTask> playerTaskList = null;
		WorldPlayer taskWorldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
		if (taskWorldPlayer != null && taskWorldPlayer.getTaskIngList() != null && !taskWorldPlayer.getTaskIngList().isEmpty()) {
			playerTaskList = new ArrayList<PlayerTask>(taskWorldPlayer.getTaskIngList());
		} else {
			try {
				PlayerTaskTitle playerTaskTitle = ServiceManager.getManager().getPlayerTaskTitleService()
						.getPlayerTaskTitleByPlayerId(playerId);
				if (playerTaskTitle != null) {
					playerTaskList = (List<PlayerTask>) SerializeUtil.unserialize(playerTaskTitle.getTaskList());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (playerTaskList != null) {
			playerTaskInfoList = new ArrayList<PlayerTaskInfo>();
			for (PlayerTask playerTaskVo : playerTaskList) {
				if (playerTaskVo.getTaskType() != Common.TASK_TYPE_DAY) {
					Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(playerTaskVo.getTaskId());
					PlayerTaskInfo playerTaskInfo = new PlayerTaskInfo(task.getId(), task.getTaskType(), task.getTargetValueList());
					playerTaskInfo.setTaskId(task.getId());
					playerTaskInfo.setTaskName(task.getTaskName());
					playerTaskInfo.setTaskType(task.getTaskType());
					playerTaskInfo.setStatus(playerTaskVo.getStatus());
					playerTaskInfo.setEndTime(playerTaskVo.getEndTime());
					playerTaskInfo.setTargetStatus(playerTaskVo.getTargetStatus());
					playerTaskInfoList.add(playerTaskInfo);
				}
			}
		}
		return playerTaskInfoList;
	}

	/**
	 * GM工具编辑任务内容
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param taskIndex
	 *            任务列表索引
	 * @param taskId
	 *            任务ID
	 * @param taskTargetStatus
	 *            任务完成情况
	 * @param taskStatus
	 *            任务完成状态
	 * @param isNew
	 *            是否标记为新任务
	 * @param bResult
	 *            操作结果
	 */
	@SuppressWarnings("unchecked")
	public boolean updateTaskByGm(int playerId, int taskIndex, int taskId, String taskTargetStatus, byte taskStatus, boolean isNew) {
		boolean bResult = false;
		WorldPlayer taskWorldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
		IPlayerTaskTitleService playerTaskTitleService = ServiceManager.getManager().getPlayerTaskTitleService();
		if (taskWorldPlayer != null && taskWorldPlayer.isOnline() && taskWorldPlayer.getTaskIngList() != null
				&& !taskWorldPlayer.getTaskIngList().isEmpty()) { // 如果有在线缓存信息，则只修改缓存数据
			List<PlayerTask> taskIngList = taskWorldPlayer.getTaskIngList();
			PlayerTask taskIng = taskIngList.get(taskIndex);
			if (taskIng != null && taskIng.getTaskId() == taskId) {
				playerTaskTitleService.updatePlayerTaskStatus(taskWorldPlayer, taskIng, taskStatus, false);
				if (taskStatus == Common.TASK_STATUS_FINISHING) {
					playerTaskTitleService.initTaskIng(taskWorldPlayer, taskIng, taskWorldPlayer.getId(), taskWorldPlayer.getLevel());
				} else if (taskStatus == Common.TASK_STATUS_FINISHED && taskService.isParentTask(taskId)) {
					playerTaskTitleService.initialTask(taskWorldPlayer, taskWorldPlayer.getTaskIngList(), taskWorldPlayer.getLevel(),
							taskWorldPlayer.getId());
				}
				bResult = true;
			}
		} else {
			PlayerTaskTitle playerTaskTitle = playerTaskTitleService.getPlayerTaskTitleByPlayerId(playerId);
			if (playerTaskTitle != null) {
				try {
					List<PlayerTask> playerTaskVoList = (List<PlayerTask>) SerializeUtil.unserialize(playerTaskTitle.getTaskList());
					if (playerTaskVoList != null && !playerTaskVoList.isEmpty()) {
						PlayerTask playerTaskVo = playerTaskVoList.get(taskIndex);
						if (playerTaskVo != null && playerTaskVo.getTaskId() == taskId) {
							playerTaskTitleService.updatePlayerTaskStatus(taskWorldPlayer, playerTaskVo, taskStatus, false);
							playerTaskTitle.setTaskList(SerializeUtil.serialize(playerTaskVoList));
							playerTaskTitleService.update(playerTaskTitle);
							bResult = true;
							if (taskWorldPlayer != null) {
								taskWorldPlayer.setTaskIngList(playerTaskVoList);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bResult;
	}

	/**
	 * 初始化最后更新任务时间
	 */
	public void initUpdateTaskTime() {
		Date lastTaskUpdateTime = ServiceManager.getManager().getPlayerTaskTitleService().getLastTaskUpdateTime();
		Date lastTitleUpdateTime = ServiceManager.getManager().getPlayerTaskTitleService().getLastTitleUpdateTime();
		if (lastTaskUpdateTime == null || lastTitleUpdateTime == null) {
			this.lastUpdateTaskTime = new Date().getTime();
		} else {
			if (lastTaskUpdateTime.getTime() > lastTitleUpdateTime.getTime()) {
				this.lastUpdateTaskTime = lastTaskUpdateTime.getTime();
			} else {
				this.lastUpdateTaskTime = lastTitleUpdateTime.getTime();
			}
		}
	}

	/**
	 * 判断是否需要同步任务数据
	 * 
	 * @param synchronousTime
	 *            用户上次同步到的任务时间点
	 * @return 是否需要同步任务数据
	 */
	public boolean isSynchronousTask(Date synchronousTime) {
		if (synchronousTime == null || synchronousTime.getTime() < this.lastUpdateTaskTime) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 同步新增加的任务列表
	 * 
	 * @param player
	 *            玩家信息
	 * @return 增加任务数量
	 * @throws Exception
	 *             同步任务失败时抛出此异常
	 */
	public int synchronousTask(WorldPlayer worldPlayer) throws Exception {
		IPlayerTaskTitleService playerTaskTieleService = ServiceManager.getManager().getPlayerTaskTitleService();
		int count = 0;
		List<Task> taskList = ServiceManager.getManager().getTaskService().getService().getAllTaskList();
		List<PlayerTask> taskIngList = worldPlayer.getTaskIngList();
		// 生成已经有任务ID列表
		List<Integer> hasTaskIdList = new ArrayList<Integer>();
		if (taskIngList != null) {
			for (PlayerTask taskIng : taskIngList) {
				if (taskIng.getTaskType() != Common.TASK_TYPE_DAY) {
					hasTaskIdList.add(taskIng.getTaskId());
				}
			}
		} else {
			taskIngList = new ArrayList<PlayerTask>();
			worldPlayer.setTaskIngList(taskIngList);
		}
		boolean isInit = false;
		List<Integer> delTaskIdList = new ArrayList<Integer>();
		for (Task task : taskList) {
			// 对有效的任务进行同步
			if (task.getStatus() == Common.STATUS_SHOW) {
				if (!hasTaskIdList.contains(task.getId().intValue())) {
					PlayerTask taskIng = new PlayerTask(task.getId(), task.getTaskType(), task.getTargetValueList());
					if (playerTaskTieleService.isTriggerTask(task.getId(), taskIngList, worldPlayer.getLevel())) {
						playerTaskTieleService.updatePlayerTaskStatus(worldPlayer, taskIng, Common.TASK_STATUS_FINISHING, false);
					} else {
						playerTaskTieleService.updatePlayerTaskStatus(worldPlayer, taskIng, Common.TASK_STATUS_UNTRIGGERED, false);
					}
					taskIngList.add(taskIng);
					isInit = true;
					count++;
				}
			} else { // 对已经禁用的任务并且玩家任务列表中存在的任务ID保存起来
				if (hasTaskIdList.contains(task.getId().intValue())) {
					delTaskIdList.add(task.getId());
					isInit = true;
				}
			}
			hasTaskIdList.remove(task.getId());
		}
		delTaskIdList.addAll(hasTaskIdList);
		// 对已经禁用的任务并且玩家任务列表中存在的任务进行删除
		if (!delTaskIdList.isEmpty()) {
			isInit = true;
			for (int i = taskIngList.size() - 1; i >= 0; i--) {
				if (delTaskIdList.contains(taskIngList.get(i).getTaskId())) {
					taskIngList.remove(i);
				}
			}
		}
		if (isInit) {
			Comparator<PlayerTask> ascComparator = new TaskComparator();
			Collections.sort(taskIngList, ascComparator);
		}
		return count;
	}

	/**
	 * 内部类，对任务列表进行排序
	 * 
	 * @author sunzx
	 */
	public class TaskComparator implements Comparator<PlayerTask> {
		public int compare(PlayerTask taskIng1, PlayerTask taskIng2) {
			return taskIng1.getTaskId() - taskIng2.getTaskId();
		}
	}
}
