package com.wyd.empire.world.service.base.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyd.empire.protocol.data.account.RoleLogin;
import com.wyd.empire.protocol.data.cache.PlayerInfo;
import com.wyd.empire.protocol.data.cache.UpdatePlayer;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.mongo.impl.PlayerDao;
import com.wyd.empire.world.entity.mongo.Player;
import com.wyd.empire.world.exception.CreatePlayerException;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.model.Client;
import com.wyd.empire.world.model.player.WorldPlayer;
/**
 * 类 <code>PlayerService</code>处理与玩家相关操作业务处理逻辑层
 * 
 * @since JDK 1.6
 */
@Service
public class PlayerService implements Runnable {
	private Logger log = Logger.getLogger(PlayerService.class);
	private Logger onlineLog = Logger.getLogger("onlineLog");
	private Logger vipExpLog = Logger.getLogger("vipExpLog");

	@Autowired
	private PlayerDao playerDao;
	/**
	 * 玩家playerID与WorldPlayer对应关系HashMap，原名players
	 */
	private ConcurrentHashMap<Integer, WorldPlayer> worldPlayers = new ConcurrentHashMap<Integer, WorldPlayer>();

	public ConcurrentHashMap<Integer, WorldPlayer> getWorldPlayers() {
		return worldPlayers;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(60000L);
				// updatePlayerOnLineTime();
				int onlineNum = worldPlayers.size();
				this.onlineLog.info(TipMessages.ONLINE_PLAYER_NUM + onlineNum);
				GameLogService.onlineNum(onlineNum);
			} catch (Exception e) {
				this.log.error(e, e);
			}
		}
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("PlayerService-Thread");
		t.start();
	}
	/** 更新玩家在线时间 */
	private void updatePlayerOnLineTime() {
		for (WorldPlayer worldplayer : this.worldPlayers.values()) {
			try {
				Player player = worldplayer.getPlayer();
				long loginTime = player.getLoginTime().getTime();
				long loginOutTime = player.getLoginOutTime().getTime();
				int longTime = (int) (loginOutTime - loginTime);
				int onLineTime = player.getOnLineTime();
				if (longTime > 0) {
					onLineTime += longTime;
					player.setOnLineTime(onLineTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建WorldPlayer对象
	 * 
	 * @param player
	 * @return
	 */
	private WorldPlayer createWorldPlayer(Player player) {
		if (worldPlayers.containsKey(player.getId())) {
			return worldPlayers.get(player.getId());
		} else {
			WorldPlayer worldPlayer = new WorldPlayer(player);
			worldPlayers.put(worldPlayer.getPlayer().getId(), worldPlayer);
			worldPlayer.init();
			return worldPlayer;
		}
	}

	/**
	 * 检查玩家是否在线 *
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean playerIsOnline(int playerId) {
		return this.worldPlayers.containsKey(playerId);
	}

	/**
	 * 获取已经加载的玩家
	 * 
	 * @param playerId
	 * @return
	 */
	public WorldPlayer getPlayer(int playerId) {
		return this.worldPlayers.get(playerId);
	}

	/**
	 * 根据角色名称从数据库中查询玩家信息
	 * 
	 * @param actorName
	 * @return
	 */
	public Player getPlayerByName(String actorName) {
		return playerDao.getPlayerByName(actorName);
	}

	/**
	 * 根据玩家id从数据库中查询玩家信息
	 * 
	 * @param playerId
	 * @return
	 */
	public Player getPlayerById(int playerId) {
		return playerDao.getPlayerById(playerId);
	}

	/**
	 * 获取玩家帐号分区下的所有角色
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Player> getPlayerList(int accountId) {
		return playerDao.getPlayerListByAccountId(accountId);
	}

	/**
	 * 从playerService中，释放对应的worldPlayer对象
	 * 
	 * @param player
	 * @return
	 */
	public boolean release(WorldPlayer worldPlayer) {
		synchronized (worldPlayer) {
			if (worldPlayer == null)
				return false;
			clearPlayer(worldPlayer);
			savePlayerData(worldPlayer.getPlayer());
			writeLog("注销保存玩家信息：id=" + worldPlayer.getPlayer().getId() + "---name=" + worldPlayer.getName() + "---level="
					+ worldPlayer.getPlayer().getLv());
		}
		return false;
	}
	/**
	 * 踢玩家下线
	 * 
	 * @param playerId
	 */
	public void killLine(int accountId) {
		WorldPlayer worldPlayer = this.worldPlayers.remove(accountId);
		if (null != worldPlayer) {
			worldPlayer.getConnectSession().killSession(worldPlayer.getClient().getSessionId());
			worldPlayer.getConnectSession().removeClient(worldPlayer.getClient());
		}
	}
	/**
	 * 从playerService里注销玩家
	 * 
	 * @param player
	 */
	public void clearPlayer(WorldPlayer player) {
		player.logout();
		this.worldPlayers.remove(player.getPlayer().getId());
	}

	/**
	 * 保存玩家信息
	 * 
	 * @param player
	 *            玩家信息
	 */
	public void savePlayerData(Player player) {
		try {
			synchronized (player) {
				playerDao.save(player);
			}
		} catch (Exception e) {
			log.equals(e);
		}
	}

	/**
	 * 创建游戏角色
	 * 
	 * @param gameAccountId
	 * @param playerName
	 * @param sex
	 * @param alignment
	 * @param model
	 * @return
	 * @throws CreatePlayerException
	 */
	public Player createPlayer(int accountId, String nickname, int hero_ext_id, int channel, String clientModel, String systemName,
			String systemVersion) throws CreatePlayerException {
		try {
			String name = nickname.trim();
			if (name.getBytes("gbk").length == 0)
				throw new CreatePlayerException(ErrorMessages.PLAYER_CREATENAME);
			if (!(ServiceUtils.checkString(name, false)))
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_WRONG);

			if (name.length() > 16)
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_LONG_CHAR);

			if (name.length() < 1)
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_SHORT);
			if (KeywordsUtil.isInvalidName(name.toLowerCase()))
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_WRONG);
			String newName = KeywordsUtil.filterKeywords(name);
			if (!(newName.equals(name))) {
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_WRONG);
			}
			if (ServiceUtils.isNumeric(name)) {
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAMEALLNUM_FAILED);
			}
			int count = 0;
			List<Player> listPlayer = this.playerDao.getPlayerListByAccountId(accountId);
			if (listPlayer != null) {
				count = listPlayer.size();
			}
			if (count >= 4) {
				throw new CreatePlayerException(ErrorMessages.PLAYER_CREATECOUNT);
			}
			if (this.playerDao.getPlayerByName(name) != null) {
				throw new CreatePlayerException(ErrorMessages.PLAYER_SAMENAME);
			}

			Player newPlayer = new Player();
			newPlayer.setAccountId(accountId);
			newPlayer.setNickname(nickname);
			newPlayer.setCreateTime(new Date());
			newPlayer.setLoginTime(new Date());
			newPlayer.setLv(1);
			newPlayer.setLvExp(0);
			newPlayer.setVipLv(0);
			newPlayer.setVipExp(0);
			newPlayer.setStatus((byte) 1);
			newPlayer.setMoney(0);
			newPlayer.setClientModel(clientModel);
			newPlayer.setSystemName(systemName);
			newPlayer.setSystemVersion(systemVersion);
			newPlayer.setProperty("");
			newPlayer.setFight(0);
			newPlayer = this.playerDao.insert(newPlayer);

			// 记录角色创建日志
			GameLogService.createPlayer(newPlayer.getId(), newPlayer.getNickname());
			return newPlayer;
		} catch (CreatePlayerException e) {
			throw e;
		} catch (Exception e) {
			this.log.error(e, e);
			throw new CreatePlayerException(ErrorMessages.PLAYER_CREATE_FAILED);
		}
	}

	/**
	 * 根据角色名称，账号id 读取角色相关信息 没有则创建新角色
	 * 
	 * @param name
	 *            角色名称
	 * @param accountId
	 *            账号id
	 * @return
	 * @throws Exception
	 */
	public WorldPlayer loadWorldPlayer(Client client, RoleLogin roleLoginData) throws Exception {
		int accountId = client.getAccountId();
		int playerId = client.getPlayerId();
		String nickname = roleLoginData.getNickname();
		WorldPlayer worldPlayer;
		if (playerId == -1) {
			Player player = playerDao.getPlayerByName(accountId, nickname);
			// 不存在就创建角色
			if (player == null) {
				player = createPlayer(accountId, nickname, roleLoginData.getHeroExtId(), client.getChannel(),
						roleLoginData.getClientModel(), roleLoginData.getSystemName(), roleLoginData.getSystemVersion());
			}
			worldPlayer = createWorldPlayer(player);
			this.log.info("createWorldPlayer ID[" + player.getId() + "]Level[" + player.getLv() + "] load from db");
		} else {
			worldPlayer = this.worldPlayers.get(playerId);
		}

		this.log.info("GAMEACCOUNTID[" + accountId + "]FAIL TO LOGIN " + nickname);
		worldPlayer.setLoginTime(System.currentTimeMillis());
		return worldPlayer;
	}

	/**
	 * 同步并重置每日任务
	 * 
	 * @param worldPlayer
	 *            玩家信息
	 * @param nowTime
	 *            当天凌晨时间点
	 */
	public void updateTask(WorldPlayer worldPlayer) {
		// try {
		// if (null == worldPlayer.getTaskIngList() ||
		// worldPlayer.getTaskIngList().isEmpty() || null ==
		// worldPlayer.getTitleIngList()
		// || worldPlayer.getTitleIngList().isEmpty()) {
		// worldPlayer.initialPlayerTaskTitle();
		// }
		//
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
	}

	/**
	 * 重命名角色名称
	 * 
	 * @param player
	 * @param newPlayerName
	 * @throws Exception
	 */
	public void updateName(WorldPlayer player, String nickname) throws Exception {
		try {
			String name = nickname.trim();
			if (name.length() == 0) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_CREATENAME);
			}
			if (name.getBytes("GBK").length > 16) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_NAME_LONG);
			}
			if (name.getBytes("GBK").length < 2) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_NAME_SHORT);
			}
			if (KeywordsUtil.isInvalidName(name.toLowerCase())) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_NAME_WRONG);
			}
			if (!(ServiceUtils.checkString(name, false))) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_NAME_WRONG);
			}
			if (ServiceUtils.isNumeric(name)) {
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAMEALLNUM_FAILED);
			}
			String newName = KeywordsUtil.filterKeywords(name);
			if (!(newName.equals(name))) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_NAME_WRONG);
			}
			if (this.playerDao.getPlayerByName(name) != null) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_SAMENAME);
			}
			player.getPlayer().setNickname(name);
			savePlayerData(player.getPlayer());
			writeLog("修改昵称保存玩家信息：id=" + player.getPlayer().getId() + "---name=" + player.getName() + "---level="
					+ player.getPlayer().getLv());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 增加玩家经验
	 * 
	 * @param playerId
	 * @param exp
	 * @throws Exception
	 */
	public void addPlayerEXP(WorldPlayer worldPlayer, int exp) throws Exception {
		Player player = worldPlayer.getPlayer();
		int pChannel1 = 1;
		int lv = player.getLv();
		int lvExp = player.getLvExp() + exp;

		//
		// int sjexp = getUpgradeExp(level, player.getPlayer().getZsLevel());
		// int dqexp = player.getPlayer().getExp() + exp;
		// String savelog = null;
		// if (dqexp < sjexp) {
		// if (dqexp < 0) {
		// player.getPlayer().setExp(0);
		// } else {
		// player.getPlayer().setExp(dqexp);
		// }
		// } else {
		// if (player.getPlayer().getLevel() < WorldServer.config.getMaxLevel())
		// {
		// player.getPlayer().setLevel(level + 1);
		// player.getPlayer().setExp(dqexp - sjexp);
		// try {
		// UpdatePlayerLevel updatePlayerLevel = new UpdatePlayerLevel();
		// updatePlayerLevel.setLevel(player.getPlayer().getLevel());
		// player.sendData(updatePlayerLevel);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// GameLogService.upLivel(player.getId(), player.getLevel());
		// savelog = "玩家增加等级：id=" + player.getId() + "---name=" +
		// player.getName() + "---oldlevel=" + level + "---newlevel="
		// + player.getLevel();
		// // 触发宠物升级检查,如果玩家有足够经验时也会升级
		// checkPetLevel(player);
		// } else {
		// player.getPlayer().setExp(sjexp);
		// }
		// }
		// savePlayerData(player.getPlayer());
		// if (null != savelog)
		// writeLog(savelog);
		// int pChannel2 = 1;
		// if (pChannel1 != pChannel2 && player.getChannelId() ==
		// Common.GAME_INTERFACE_HALL) {// 如果玩家所在战斗频道变更，并且在大厅界面则切换频道
		// ServiceManager
		// .getManager()
		// .getChatService()
		// .syncChannels(player.getId(),
		// new String[]{ChatService.CHAT_CURRENT_CHANNEL + "_" +
		// player.getChannelId() + "" + pChannel2},
		// new String[]{ChatService.CHAT_CURRENT_CHANNEL + "_" +
		// player.getChannelId() + "" + pChannel1});
		// }
		// player.updateFight();
		// // 更新角色信息-等级,经验,下级经验
		// Map<String, String> info = new HashMap<String, String>();
		// info.put("level", player.getLevel() + "");
		// info.put("exp", player.getPlayer().getExp() + "");
		// info.put("maxExp", getUpgradeExp(player.getLevel(),
		// player.getPlayer().getZsLevel()) + "");
		// int cardSeatNum = player.getPlayer().getZsLevel() > 0 ? 99 :
		// player.getLevel();
		// cardSeatNum = cardSeatNum > 99 ? 99 : cardSeatNum;
		// info.put("cardSeatNum", cardSeatNum + "");
		// sendUpdatePlayer(info, player);
	}
	/**
	 * 更新玩家的金币数量
	 * 
	 * @param playerId
	 * @param gold
	 * @param origin
	 *            金币来源
	 * @param remark
	 *            备注
	 * @throws Exception
	 */
	public boolean updatePlayerGold(WorldPlayer worldPlayer, int gold, String origin, String remark) throws Exception {

		return true;
		// if (0 == gold)
		// return;
		// Player player = worldPlayer.getPlayer();
		// int myGold = player.getMoneyGold() + gold;
		// player.setMoneyGold(myGold);
		// savePlayerData(player);
		// // writeLog("玩家增加金币保存玩家信息：id=" + player.getId() + "---name=" +
		// // player.getName() + "---level=" + player.getLevel());
		// Map<String, String> info = new HashMap<String, String>();
		// info.put("gold", worldPlayer.getMoney() + "");
		// sendUpdatePlayer(info, worldPlayer);
		// if (gold > 0) {
		// GameLogService.addGold(player.getId(), player.getLevel(), origin,
		// gold, remark);
		// } else {
		// GameLogService.useGold(player.getId(), player.getLevel(), origin,
		// gold, remark);
		// }
	}

	public void writeLog(Object message) {
		log.info(message);
	}

	/**
	 * 增加点卷(充值后调用)
	 * 
	 * @param player
	 * @param amount
	 *            增加的代币数量
	 * @param giftAmount
	 *            赠送的钻石数量
	 * @param origin
	 *            增加的途径
	 * @param price
	 *            价格
	 * @param orderNum
	 *            单号
	 * @param remark
	 *            备注
	 */
	public void addTicket(WorldPlayer worldPlayer, int amount, int giftAmount, int origin, float price, String orderNum, String remark,
			String channelId, String cardType) {

		// if (amount > 0) {
		// try {
		// // 记录充值日志
		// if (origin == TradeService.ORIGIN_RECH) {
		// Client client = worldPlayer.getClient();
		// int accountId = null == client ? 0 : client.getAccountId();
		// GameLogService.recharge(worldPlayer.getId(), worldPlayer.getLevel(),
		// accountId, channelId, cardType, orderNum, price, amount, giftAmount,
		// remark);
		// }
		// Player player = worldPlayer.getPlayer();
		// player.setAmount(player.getAmount() + amount + giftAmount);
		// savePlayerData(player);
		// // 保存代币变更记录
		// PlayerBill playerBill = new PlayerBill();
		// playerBill.setPlayerId(player.getId());
		// playerBill.setCreateTime(new Date());
		// playerBill.setAmount(amount);
		// playerBill.setOrigin(origin);
		// playerBill.setRemark(remark);
		// playerBill.setChargePrice(price);
		// playerBill.setOrderNum(orderNum);
		// playerBill.setChannelId(channelId);
		// playerBill.setCardType(cardType);
		// playerBill.setGiftAmount(giftAmount);
		// if (origin == TradeService.ORIGIN_RECH) {
		// IPlayerBillService billService =
		// ServiceManager.getManager().getPlayerBillService();
		// if (billService.playerIsFirstCharge(worldPlayer.getPlayer())) {
		// playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_N);
		// } else {
		// playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_Y);
		// }
		// // 每日首充
		// if (!billService.playerIsEveryDayFirstCharge(player)) {
		// // 获取配置
		// Map<Integer, Integer> map =
		// ServiceManager.getManager().getVersionService().getRechargeIntervalMap();
		// Integer limitNum =
		// ServiceManager.getManager().getVersionService().getSpecialMarkByKey("numberCaps");
		// limitNum = (limitNum == null) ? 10 : limitNum;
		// int num = 1;
		// for (int i = 10; i > 0; i--) {
		// Integer val = map.get(i);
		// if (val != null) {
		// if(val>0 && amount>val){
		// num = i;
		// break;
		// }
		// }
		// }
		// num += worldPlayer.getPlayerInfo().getEveryDayFirstChargeNum();
		// if (num > limitNum) num = limitNum;
		// // 记录可以领取的每日首充次数
		// worldPlayer.getPlayerInfo().setEveryDayFirstChargeNum(num);
		// worldPlayer.updatePlayerInfo();
		// }
		// // vip 计算
		// int oldVipExp = player.getVipExp();
		// int totalExp = oldVipExp + amount;
		// totalExp = totalExp > 150000 ? 150000 : totalExp;
		// if (oldVipExp < 150000) {
		// int vipLevel = getVIPLevel(totalExp);
		// // 保存用户信息及推送给前段
		// worldPlayer.setVipInfo(vipLevel, totalExp);
		// vipExpLog.info("VIP经验变更记录：" + "-玩家ID:" + player.getId() + "-玩家名称：" +
		// player.getName() + "-充值钻石：" + amount + "-原经验：" + oldVipExp + "-现经验："
		// + totalExp);
		// }
		// int vipLevel = worldPlayer.getPlayer().getVipLevel();
		// BuySuccess buySuccess = new BuySuccess();
		// buySuccess.setOrderNum(orderNum);
		// buySuccess.setTisket(player.getAmount());
		// buySuccess.setVipLv(vipLevel);
		// buySuccess.setNeedTicket(getVIPExp(vipLevel + 1) - totalExp);
		// buySuccess.setVipExp(totalExp);
		// buySuccess.setAddTicket(amount);
		// buySuccess.setGiftTicket(giftAmount);
		// worldPlayer.sendData(buySuccess);
		// worldPlayer.updateButtonInfo(Common.BUTTON_ID_RECHARGE, false, 30);
		// } else {
		// playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_Y);
		// }
		// ServiceManager.getManager().getPlayerBillService().save(playerBill);
		// // 非充值暴击，保存邮件
		// if (TradeService.ORIGIN_RECHARGECRIT != origin) {
		// Mail mail = new Mail();
		// mail.setContent(TipMessages.TICKETNOTICECONTENT1 + amount +
		// TipMessages.TICKETNOTICECONTENT2);
		// mail.setIsRead(false);
		// mail.setReceivedId(player.getId());
		// mail.setSendId(0);
		// mail.setSendName(TipMessages.SYSNAME_MESSAGE);
		// mail.setSendTime(new Date());
		// String ticketNotice = TipMessages.TICKETNOTICE;
		// if(origin==TradeService.DAY_ORIGIN_RECHARGE_RWARD){
		// ticketNotice =
		// ticketNotice+" ["+TipMessages.DAYORIGINRECHARGERWARD+"]";
		// }else if (origin == TradeService.ORIGIN_RECHARGE_RWARD) {
		// ticketNotice = ticketNotice+" ["+TipMessages.ORIGINRECHARGERWARD+"]";
		// }
		// mail.setTheme(ticketNotice);
		// mail.setType(1);
		// mail.setBlackMail(false);
		// mail.setIsStick(Common.IS_STICK);
		// ServiceManager.getManager().getMailService().saveMail(mail, null);
		// }
		// if (TradeService.ORIGIN_RECH == origin) {
		// // PurchaseUtils.removePurchases(orderNum);
		// ServiceManager.getManager().getTaskService().czticket(worldPlayer,
		// amount);
		// worldPlayer.setCanGiveDiamond(amount);
		// }
		// Map<String, String> info = new HashMap<String, String>();
		// info.put("blueDiamond", worldPlayer.getDiamond() + "");
		// sendUpdatePlayer(info, worldPlayer);
		// // 调用充值暴击累积返利的方法
		// if (TradeService.ORIGIN_RECH == origin &&
		// ServiceManager.getManager().getVersionService().isOpenRechargeCritFlag())
		// {
		// delRechargeCrit(amount, worldPlayer);
		// }
		// GameLogService.addMoney(worldPlayer.getId(), worldPlayer.getLevel(),
		// origin, amount, remark);
		// ServiceManager.getManager().getTaskService().addDiamond(worldPlayer,
		// amount);
		// } catch (Exception e) {
		// e.printStackTrace();
		// if (TradeService.ORIGIN_RECH == origin) {
		// BuyFailed buyFailed = new BuyFailed();
		// buyFailed.setOrderNum(orderNum);
		// buyFailed.setCode(1);
		// worldPlayer.sendData(buyFailed);
		// }
		// }
		// }
	}

	/**
	 * 增加点卷
	 * 
	 * @param player
	 * @param amount
	 *            增加的代币数量
	 * @param origin
	 *            增加的途径
	 * @param price
	 *            价格
	 * @param orderNum
	 *            单号
	 * @param remark
	 *            备注
	 */
	public void addTicketGm(WorldPlayer worldPlayer, int amount, int origin, float price, String orderNum, String remark, String channelId,
			String cardType, Date currentDate) {
		// try {
		// // 记录充值日志
		// if (origin == TradeService.ORIGIN_RECH) {
		// Client client = worldPlayer.getClient();
		// int accountId = null == client ? 0 : client.getAccountId();
		// GameLogService.recharge(worldPlayer.getId(), worldPlayer.getLevel(),
		// accountId, channelId, cardType, orderNum, price, amount, 0, remark);
		// }
		// Player player = worldPlayer.getPlayer();
		// player.setAmount(player.getAmount() + amount);
		// // GM工具调用充值暴击累积返利
		// // delRechargeCrit(amount, worldPlayer);
		// savePlayerData(player);
		// // 保存代币变更记录
		// PlayerBill playerBill = new PlayerBill();
		// playerBill.setPlayerId(player.getId());
		// playerBill.setCreateTime(currentDate);
		// playerBill.setAmount(amount);
		// playerBill.setOrigin(origin);
		// playerBill.setRemark(remark);
		// playerBill.setChargePrice(price);
		// playerBill.setOrderNum(orderNum);
		// playerBill.setChannelId(channelId);
		// playerBill.setCardType(cardType);
		// if (origin == TradeService.ORIGIN_RECH) {
		// if
		// (ServiceManager.getManager().getPlayerBillService().playerIsFirstCharge(worldPlayer.getPlayer()))
		// {
		// playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_N);
		// } else {
		// playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_Y);
		// }
		// } else {
		// playerBill.setIsFirstRecharge(Common.PLAYER_BILL_STATUS_Y);
		// }
		// ServiceManager.getManager().getPlayerBillService().save(playerBill);
		// // 保存邮件
		// Mail mail = new Mail();
		// mail.setContent(TipMessages.TICKETNOTICECONTENT1 + amount +
		// TipMessages.TICKETNOTICECONTENT2);
		// mail.setIsRead(false);
		// mail.setReceivedId(player.getId());
		// mail.setSendId(0);
		// mail.setSendName(TipMessages.SYSNAME_MESSAGE);
		// mail.setSendTime(new Date());
		// mail.setTheme(TipMessages.TICKETNOTICE);
		// mail.setType(1);
		// mail.setBlackMail(false);
		// mail.setIsStick(Common.IS_STICK);
		// ServiceManager.getManager().getMailService().saveMail(mail, null);
		// if (TradeService.ORIGIN_RECH == origin) {
		// ServiceManager.getManager().getTaskService().czticket(worldPlayer,
		// amount);
		// if
		// (ServiceManager.getManager().getVersionService().isOpenRechargeCritFlag())
		// delRechargeCrit(amount, worldPlayer);
		// worldPlayer.setCanGiveDiamond(amount);
		// }
		// Map<String, String> info = new HashMap<String, String>();
		// info.put("blueDiamond", worldPlayer.getDiamond() + "");
		// sendUpdatePlayer(info, worldPlayer);
		// GameLogService.addMoney(worldPlayer.getId(), worldPlayer.getLevel(),
		// origin, amount, remark);
		// ServiceManager.getManager().getTaskService().addDiamond(worldPlayer,
		// amount);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 推送玩家信息到客户端
	 * 
	 * @param player
	 */
	public void sendPlayerInfo(WorldPlayer worldPlayer) {
		if (worldPlayer == null || worldPlayer.getPlayer() == null)
			return;
		Player player = worldPlayer.getPlayer();

		PlayerInfo playerInfo = new PlayerInfo();
		playerInfo.setId(player.getId());
		playerInfo.setName(player.getNickname());
		worldPlayer.sendData(playerInfo);
	}

	//推送修改后的数据，如金币，经验，等级等
	public void sendUpdatePlayer(Map<String, String> info, WorldPlayer player) {
		int size = info.size();
		String[] key = new String[size];
		String[] value = new String[size];
		int i = 0;
		Set<String> keyset = info.keySet();
		for (String k : keyset) {
			key[i] = k;
			value[i] = info.get(k);
			i++;
		}
		UpdatePlayer updatePlayerInfo = new UpdatePlayer();
		updatePlayerInfo.setKey(key);
		updatePlayerInfo.setValue(value);
		player.sendData(updatePlayerInfo);
	}

	// 定时触发
	public void sysPlayersVigorUp() {
		// System.out.println();
	}

}