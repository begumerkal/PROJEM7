package com.wyd.empire.world.model.player;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.entity.mongo.Player;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.model.Client;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.protocol.data.AbstractData;

/**
 * 封装游戏世界角色类， 游戏角色的基本属性操作都在本类中继承或实现。
 * 
 * @author doter
 */
public class WorldPlayer {
	private Logger log = Logger.getLogger(WorldPlayer.class);

	/**
	 * 最后发送聊天时间
	 */
	private long lastSendMsgTime;
	private long loginTime; // 登录时间
	private boolean everydayFirstLogin; // 是否每天初次登录
	private ConnectSession connSession = null; // 当前所对应的Session
	private Client accountClient = null; // 当前所对应的帐户Client
	private Player player;
	private List<PlayerTask> taskIngList; // 玩家进行中的任务列表

	/**
	 * 构造函数，初始化游戏人物各项数值
	 * 
	 * @param player
	 * @throws Exception
	 */
	public WorldPlayer(Player player) {
		this.player = player;
	}

	public void init() {
	}

	/**
	 * 设置玩家副本星级
	 * 
	 * @param mapId
	 * @param star
	 */
	public void updatePlayerStarInfo(int mapId, int star) {
	}

	/**
	 * 玩家是否当天初次登陆
	 * 
	 * @return
	 */
	public boolean isEverydayFirstLogin() {
		return everydayFirstLogin;
	}

	public void setEverydayFirstLogin(boolean everydayFirstLogin) {
		this.everydayFirstLogin = everydayFirstLogin;
	}

	// 当前所对应的Session
	public void setConnectSession(ConnectSession session) {
		this.connSession = session;
	}

	public ConnectSession getConnectSession() {
		return this.connSession;
	}

	// 当前所对应的帐户Client
	public void setAccountClient(Client client) {
		this.accountClient = client;
	}

	public Client getClient() {
		return this.accountClient;
	}

	public void sendData(AbstractData data) {
		if (null != connSession)
			connSession.write(data, player.getId());
	}

	public Player getPlayer() {
		return player;
	}

	public long getLastSendMsgTime() {
		return this.lastSendMsgTime;
	}

	public void setLastSendMsgTime(long lastSendMsgTime) {
		this.lastSendMsgTime = lastSendMsgTime;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public String getName() {
		return this.player.getNickname();
	}

	public int getGameAccountId() {
		return this.player.getAccountId();
	}

	public void setTaskIngList(List<PlayerTask> taskIngList) {
		this.taskIngList = taskIngList;
	}

	/**
	 * 获取玩家显示的称号
	 * 
	 * @return
	 */
	public String getPlayerTitle() {
		String title = "";

		return title;
	}

	/**
	 * 初始化玩家成就及任务列表
	 */
	@SuppressWarnings("unchecked")
	public void initialPlayerTaskTitle() {
	}

	public boolean isVip() {
		if (player.getVipLv() > 0)
			return true;
		return false;
	}

	public void writeLog(Object message) {
		StringBuffer msg = new StringBuffer();
		msg.append("玩家:playerId=");
		msg.append(this.player.getId());
		msg.append("---playerName:");
		msg.append(this.getName());
		msg.append("---message=");
		msg.append(message);
		log.info(msg);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerTask getTaskIngByTaskId(int taskId, byte taskType) {
		if (null != taskIngList) {
			for (PlayerTask ti : taskIngList) {
				if (ti.getTaskId() == taskId && ti.getTaskType() == taskType) {
					return ti;
				}
			}
		}
		return null;
	}

	// private int getMaxVigor() {
	// return isVip() ? 150 : 120;
	// }

	// /**
	// * 活力值增涨一点(但不超过最大值) 跨天回复到最大值
	// */
	// public int energyUp(int add) {
	// Date vigorUpdateTime = playerInfo.getVigorUpdateTime();
	// int vigor = playerInfo.getVigor();
	// Calendar cal = Calendar.getInstance();
	// if (!DateUtil.isSameDate(vigorUpdateTime, cal.getTime())) {
	// // 购买次数清零
	// buyVigorCount = 0;
	// buyLimitedCount.clear();
	// }
	// int max = getMaxVigor();
	// if (getVigor() >= max) {
	// return 0;
	// }
	// vigor += add;
	// playerInfo.setVigor(vigor);
	// Map<String, String> info = new HashMap<String, String>();
	// info.put("vigor", getVigor() + "");
	// ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info,
	// this);
	// return add;
	// }

	// /**
	// * 根据上次更新时间到当前时间每5分钟增加一点。
	// */
	// public void addVigorFormTime() {
	// Date vigorUpdateTime = playerInfo.getVigorUpdateTime();
	// if (vigorUpdateTime == null)
	// return;
	// Calendar cal = Calendar.getInstance();
	// long cha = cal.getTime().getTime() - vigorUpdateTime.getTime();
	// long min = cha / (1000 * 60);
	// // 6分钟加一点 注意：更改这个时间同时也要把定时器时间更改过来
	// int addVigor = (int) min / 6;
	// if (addVigor > 0) {
	// addVigor = addVigor > 120 ? 120 : addVigor;
	// for (int i = 0; i < addVigor; i++) {
	// vigorUp(1);
	// }
	// playerInfo.setVigorUpdateTime(cal.getTime());
	// }
	// }
	//
	// /**
	// * 获取活力
	// *
	// * @return
	// */
	// public int getVigor() {
	// return playerInfo.getVigor();
	// }
	//
	// public Date getVigorUpdateTime() {
	// return playerInfo.getVigorUpdateTime();
	// }
	//
	// public void setVigorUpdateTime(Date time) {
	// playerInfo.setVigorUpdateTime(time);
	// }

	// /**
	// * 扣除活力 输入正数
	// *
	// * @return
	// */
	// public void useVigor(int val) {
	// int vigor = getVigor();
	// if (getVigor() < 0)
	// return;
	// vigor -= val;
	// vigor = vigor < 0 ? 0 : vigor;
	// playerInfo.setVigor(vigor);
	// Map<String, String> info = new HashMap<String, String>();
	// info.put("vigor", getVigor() + "");
	// ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info,
	// this);
	// }
	//
	// /**
	// * 增加购买次数
	// */
	// public void addBuyVigorCount() {
	// buyVigorCount++;
	// Map<String, String> info = new HashMap<String, String>();
	// info.put("buyVigorTimes", buyVigorCount + "");
	// ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info,
	// this);
	// }

	// /**
	// * 已购买次数
	// *
	// * @return
	// */
	// public int getBuyVigorCount() {
	// return buyVigorCount;
	// }

	/**
	 * 玩家离线处理
	 */
	public void logout() {
		ServiceManager.getManager().getHttpThreadPool().execute(new LoginOutThread(this));
	}

	/**
	 * 玩家上线处理
	 */
	public void login() {
		ServiceManager.getManager().getHttpThreadPool().execute(new LoginThread(this));
	}

	public class LoginOutThread implements Runnable {
		private WorldPlayer player;

		public LoginOutThread(WorldPlayer player) {
			this.player = player;
		}

		public void run() {
			// 更新玩家信息
			ServiceManager.getManager().getPlayerService().clearPlayer(player);

			// 记录玩家退出日志
			GameLogService.logout(player.getPlayer().getId(), player.getPlayer().getLv(), (int) player.getPlayer().getLoginTime().getTime());
			String area = WorldServer.config.getAreaId().toUpperCase();
		}
	}
	public class LoginThread implements Runnable {
		private WorldPlayer player;
		public LoginThread(WorldPlayer player) {
			this.player = player;
		}
		@Override
		public void run() {
		}
	}

	/**
	 * 玩家是否新手玩家
	 * 
	 * @return
	 */
	public boolean isNewPlayer() {
		return false;
	}

}
