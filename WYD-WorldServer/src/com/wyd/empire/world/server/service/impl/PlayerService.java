package com.wyd.empire.world.server.service.impl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.Protocol;
import com.wyd.empire.protocol.data.bossmapbattle.OtherRewardOk;
import com.wyd.empire.protocol.data.bossmapbattle.RewardOk;
import com.wyd.empire.protocol.data.cache.UpdatePlayer;
import com.wyd.empire.protocol.data.exchange.ResponseRefresh;
import com.wyd.empire.protocol.data.player.RechargeCritResultOk;
import com.wyd.empire.protocol.data.player.UpdatePlayerLevel;
import com.wyd.empire.protocol.data.purchase.BuyFailed;
import com.wyd.empire.protocol.data.purchase.BuySuccess;
import com.wyd.empire.protocol.data.trate.BuyResult;
import com.wyd.empire.protocol.data.vip.VipReceiveInfo;
import com.wyd.empire.protocol.data.wedding.RemoveEngagementOK;
import com.wyd.empire.protocol.data.wedding.RemoveEngagementToCouple;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.LoginReward;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerBill;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.bean.RechargeCrit;
import com.wyd.empire.world.bean.Rewardrecord;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.CreatePlayerException;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.exception.ProtocolException;
/**
 * 类 <code>PlayerService</code>处理与玩家相关操作业务处理逻辑层
 * 
 * @since JDK 1.6
 */
public class PlayerService implements Runnable {
	public static final int DEFAULT_BAGSIZE = 45;
	public static final int DEFAULT_BANKSIZE = 18;
	public static final int OUT_TIME = 60000; // 自动断线时间
	private Logger log = Logger.getLogger(PlayerService.class);
	private Logger onlineLog = Logger.getLogger("onlineLog");
	private Logger vipExpLog = Logger.getLogger("vipExpLog");
	/**
	 * 玩家playerID与WorldPlayer对应关系HashMap，原名players
	 */
	private ConcurrentHashMap<Integer, WorldPlayer> onlineplayer = new ConcurrentHashMap<Integer, WorldPlayer>();
	/**
	 * 所有玩家
	 */
	private ConcurrentHashMap<Integer, WorldPlayer> allid2player = new ConcurrentHashMap<Integer, WorldPlayer>();
	/**
	 * 所有玩家
	 */
	private ConcurrentHashMap<String, WorldPlayer> allname2player = new ConcurrentHashMap<String, WorldPlayer>();

	@Override
	public void run() {
		int i = 0;
		while (true) {
			try {
				Thread.sleep(60000L);
				updatePlayerOnLineTime();
				int onlineNum = onlineplayer.size();
				this.onlineLog.info(TipMessages.ONLINE_PLAYER_NUM + onlineNum);
				GameLogService.onlineNum(onlineNum);
				i++;
				if (i % 60 == 0) {
					clearDeadPlayer();
				}
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

	// 清理超过两天未登录的用户
	public void clearDeadPlayer() {
		Collection<WorldPlayer> lostPlayer = getAllPlayer();
		long time = System.currentTimeMillis();
		for (WorldPlayer player : lostPlayer) {
			try {
//				int lostMinute = (int) ((time - player.getActionTime()) / 60000);
//				if (lostMinute > 2880 && !player.isInRobotList()) {
//					onlineplayer.remove(player.getId());
//					allid2player.remove(player.getId());
//					allname2player.remove(player.getName());
//					ServiceManager.getManager().getPlayerItemsFromShopService().unLoadPlayerItem(player.getId());
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void updatePlayerOnLineTime() {
		for (WorldPlayer player : this.onlineplayer.values()) {
			try {
				player.setOnLineTime(player.getOnLineTime() + 1);
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
		if (allid2player.containsKey(player.getId())) {
			return allid2player.get(player.getId());
		} else {
			WorldPlayer worldPlayer = new WorldPlayer(player);
			worldPlayer.setActionTime(System.currentTimeMillis());
			allid2player.put(worldPlayer.getId(), worldPlayer);
			allname2player.put(worldPlayer.getName(), worldPlayer);
			worldPlayer.initial();
			return worldPlayer;
		}
	}

	/**
	 * 检查玩家是否在线 *
	 * 
	 * @param id
	 * @return
	 */
	public boolean playerIsOnline(int id) {
		return this.onlineplayer.containsKey(id);
	}

	/**
	 * 获取在线玩家
	 * 
	 * @param id
	 *            玩家id号
	 * @return <tt>WorldPlayer Object</tt>
	 */
	public WorldPlayer getOnlineWorldPlayer(int id) {
		return (this.onlineplayer.get(id));
	}

	/**
	 * 获取已经加载的玩家
	 * 
	 * @param id
	 * @return
	 */
	public WorldPlayer getLoadPlayer(int id) {
		return (this.allid2player.get(id));
	}

	/**
	 * 根据玩家id获取WorldPlayer对象包括在线和离线
	 * 
	 * @param playerId
	 * @return
	 */
	public WorldPlayer getWorldPlayerById(int playerId) {
		WorldPlayer worldPlayer = this.allid2player.get(playerId);
		if (worldPlayer == null) {
			Player player = getPlayerById(playerId);
			if (null != player) {
				worldPlayer = createWorldPlayer(player);
			}
		}
		return worldPlayer;
	}

	/**
	 * 根据玩家name获取WorldPlayer对象包括在线和离线
	 * 
	 * @param actorName
	 * @return
	 */
	public WorldPlayer getWorldPlayerByName(String actorName) {
		WorldPlayer worldPlayer = this.allname2player.get(actorName);
		if (worldPlayer == null) {
			Player player = getPlayerByName(actorName);
			if (null != player) {
				worldPlayer = createWorldPlayer(player);
			}
		}
		return worldPlayer;
	}

	/**
	 * 根据角色名称从数据库中查询玩家信息
	 * 
	 * @param actorName
	 * @return
	 */
	public Player getPlayerByName(String actorName) {
		return this.getService().getPlayerByName(actorName);
	}

	/**
	 * 根据玩家id从数据库中查询玩家信息
	 * 
	 * @param playerId
	 * @return
	 */
	public Player getPlayerById(int playerId) {
		return this.getService().getPlayerById(playerId);
	}

	/**
	 * 获取玩家帐号分区下的所有角色
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Player> getPlayerList(int accountId) {
		return this.getService().getPlayerList(accountId);
	}

	/**
	 * 获取当前在线玩家拷贝列表
	 * 
	 * @return
	 */
	public Collection<WorldPlayer> getOnlinePlayer() {
		return onlineplayer.values();
	}

	/**
	 * 获取所有已加载玩家包括在线和离线拷贝列表
	 * 
	 * @return
	 */
	public Collection<WorldPlayer> getAllPlayer() {
		return allid2player.values();
	}

	/**
	 * 获取当前在线玩家数量
	 * 
	 * @return
	 */
	public int getOnlinePlayerNum() {
		return onlineplayer.size();
	}

	/**
	 * 获取总玩家数量
	 * 
	 * @return
	 */
	public int getAllPlayerCount() {
		return allid2player.size();
	}

	/**
	 * 从playerService中，释放对应的worldPlayer对象
	 * 
	 * @param player
	 * @return
	 */
	public boolean release(WorldPlayer player) {
		synchronized (player) {
			if (player == null)
				return false;
			unRegistry(player);
			savePlayerData(player.getPlayer());
			writeLog("注销保存玩家信息：id=" + player.getId() + "---name=" + player.getName() + "---level=" + player.getLevel());
		}
		return false;
	}

	/**
	 * 从playerService里注销玩家
	 * 
	 * @param player
	 */
	public void unRegistry(WorldPlayer player) {
		player.logout();
		this.onlineplayer.remove(player.getId());
	}

	/**
	 * 将角色储存到服务器,用户转换人物时不必重复加载
	 * 
	 * @param player
	 */
	public void registry(WorldPlayer player) {
		player.login();
		onlineplayer.put(player.getId(), player);
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
				player.setUpdateTime(new Date());// 更新数据时间
				this.getService().save(player);
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
	public Player createPlayer(int gameAccountId, String playerName, byte sex, int channel, String userName) throws CreatePlayerException {
		try {
			String name = playerName.trim();
			if (name.getBytes("gbk").length == 0)
				throw new CreatePlayerException(ErrorMessages.PLAYER_CREATENAME);
			if (!(ServiceUtils.checkString(name, false)))
				throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_WRONG);
			if (name.matches("[a-zA-Z]+")) {
				if (name.length() > 16)
					throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_LONG_CHAR);
			} else {
				if (name.length() > 8)
					throw new CreatePlayerException(ErrorMessages.PLAYER_NAME_LONG);
			}
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
			List<Player> listPlayer = this.getService().getPlayerList(gameAccountId);
			if (listPlayer != null) {
				count = listPlayer.size();
			}
			if (count >= 4) {
				throw new CreatePlayerException(ErrorMessages.PLAYER_CREATECOUNT);
			}
			if (!this.getService().checkName(name)) {
				throw new CreatePlayerException(ErrorMessages.PLAYER_SAMENAME);
			}
			Player newPlayer = createDefaultPlayer(gameAccountId, name, sex, channel, userName);
			// 记录角色创建日志
			GameLogService.createPlayer(newPlayer.getId(), newPlayer.getName());
			return newPlayer;
		} catch (CreatePlayerException e) {
			throw e;
		} catch (Exception e) {
			this.log.error(e, e);
			throw new CreatePlayerException(ErrorMessages.PLAYER_CREATE_FAILED);
		}
	}

	/**
	 * 创建默认角色对象
	 * 
	 * @param accountId
	 * @param playerName
	 * @param sex
	 * @param growUpId
	 * @param modifyNameTimes
	 * @param flg
	 * @return
	 */
	private Player createDefaultPlayer(int accountId, String playerName, byte sex, int channel, String userName) {
		Player player = new Player();
		player.setAccountId(accountId);
		player.setStatus((byte) 1);
		player.setAreaId(WorldServer.config.getMachineCode());
		player.setVipLevel(0);
		player.setVipExp(0);
		player.setName(playerName);
		player.setSex(sex);
		player.setLevel(1);
		player.setExp(0);
		player.setHp(0);
		player.setAttack(0);
		player.setDefend(0);
		player.setDefense(0);
		player.setPlayTimes1v1Athletics(0);
		player.setWinTimes1v1Athletics(0);
		player.setPlayTimes2v2Athletics(0);
		player.setWinTimes2v2Athletics(0);
		player.setPlayTimes3v3Athletics(0);
		player.setWinTimes3v3Athletics(0);
		player.setPlayTimes1v1Champion(0);
		player.setWinTimes1v1Champion(0);
		player.setPlayTimes2v2Champion(0);
		player.setWinTimes2v2Champion(0);
		player.setPlayTimes3v3Champion(0);
		player.setWinTimes3v3Champion(0);
		player.setPlayTimes1v1Relive(0);
		player.setWinTimes1v1Relive(0);
		player.setPlayTimes2v2Relive(0);
		player.setWinTimes2v2Relive(0);
		player.setPlayTimes3v3Relive(0);
		player.setWinTimes3v3Relive(0);
		player.setMoneyGold(0);
		player.setHonor(0);
		player.setGp(0);
		player.setItemDj1(0);
		player.setItemDj2(0);
		player.setItemDj3(0);
		player.setItemDj4(-1);
		player.setItemJn1(0);
		player.setItemJn2(0);
		player.setItemJn3(0);
		player.setItemJn4(-1);
		player.setChannel(channel);
		if (userName.startsWith("Facebook_")) {
			player.setAmount(100);
		} else {
			player.setAmount(0);
		}
		player.setCreateTime(new Date());// 创建时间
		player.setLotteryCount(0);
		player.setUpdateTime(player.getCreateTime());// 更新数据时间
		player.setTaskUpdateTime(player.getCreateTime());// 重置任务时间
		player.setHonorLevel(1);
		player.setWbUserId(Common.PLAYER_DEFAULT_WB);
		player.setWbUserIcon(Common.PLAYER_DEFAULT_WB);
		player = (Player) this.getService().save(player);
		return player;
	}

	/**
	 * 根据角色名称，账号id 读取角色相关信息
	 * 
	 * @param name
	 *            角色名称
	 * @param accountId
	 *            账号id
	 * @return
	 * @throws Exception
	 */
	public WorldPlayer loadWorldPlayer(String name, Client client) throws Exception {
		WorldPlayer worldPlayer = this.allname2player.get(name);
		if (worldPlayer == null) {
			Player player = getService().getPlayerByName(name);
			if (player != null) {
				worldPlayer = createWorldPlayer(player);
				worldPlayer.firstLoad();
				this.log.info("ID[" + worldPlayer.getId() + "]Level[" + worldPlayer.getLevel() + "] load from db");
			} else {
				return null;
			}
		} else {
			clearPlayer(worldPlayer);
			Player player;
			if (worldPlayer.isFirstOnlin()) {
				player = getService().getPlayerById(worldPlayer.getId());
				worldPlayer.setPlayer(player);
				worldPlayer.firstLoad();
			} else {
				player = worldPlayer.getPlayer();
			}
			this.log.info("ID[" + worldPlayer.getId() + "]Level[" + worldPlayer.getLevel() + "] load from cache");
		}
		if (worldPlayer.getGameAccountId() == client.getGameAccountId()) {
			registry(worldPlayer);
			return worldPlayer;
		}
		this.log.info("GAMEACCOUNTID[" + client.getGameAccountId() + "]FAIL TO LOGIN " + name);
		return null;
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
//		try {
//			if (null == worldPlayer.getTaskIngList() || worldPlayer.getTaskIngList().isEmpty() || null == worldPlayer.getTitleIngList()
//					|| worldPlayer.getTitleIngList().isEmpty()) {
//				worldPlayer.initialPlayerTaskTitle();
//			}
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	/**
	 * 清理玩家的战斗状态
	 * 
	 * @param player
	 */
	public void clearPlayer(WorldPlayer player) {
		int roomId = player.getRoomId();
		int battleId = player.getBattleId();
		int bossMapRoomId = player.getBossmapRoomId();
		int bossMapBattleId = player.getBossmapBattleId();
		// 如果玩家在对战则退出战斗
		if (0 != battleId) {
			try {
				if (battleId > 0) {
				} else {
					ServiceManager.getManager().getCrossService().playerLost(battleId, player.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		player.setRoomId(0);
		player.setBossmapRoomId(0);
		player.setBattleId(0);
		player.setBossmapBattleId(0);
//		player.outSingleMap();
	}

	/**
	 * 重命名角色名称
	 * 
	 * @param player
	 * @param newPlayerName
	 * @throws Exception
	 */
	public void modifyName(WorldPlayer player, String newPlayerName) throws Exception {
		try {
			String name = newPlayerName.trim();
			String oldName = player.getName();
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
			if (!this.getService().checkName(name)) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYER_SAMENAME);
			}
			player.getPlayer().setName(name);
			savePlayerData(player.getPlayer());
			writeLog("修改昵称保存玩家信息：id=" + player.getId() + "---name=" + player.getName() + "---level=" + player.getLevel());
			this.allname2player.put(newPlayerName, player);
			this.allname2player.remove(oldName);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 获取玩家数据表操作对象
	 * 
	 * @return
	 */
	public IPlayerService getService() {
		return ServiceManager.getManager().getIPlayerService();
	}

	/**
	 * 更新玩家对战历史记录
	 * 
	 * @param player
	 * @param battleMode
	 * @param pnMode
	 * @param win
	 */
	public void updateBattleHistory(Player player, int battleMode, int pnMode, boolean win) { }

	/**
	 * 增加玩家经验
	 * 
	 * @param playerId
	 * @param exp
	 * @throws Exception
	 */
	public void updatePlayerEXP(WorldPlayer player, int exp) throws Exception {
		if (exp < 0 && player.isVip() && player.getPlayer().getVipLevel() > 3) {// vip强退不扣经验,2.1改为3级以上不扣经验
			return;
		}
		int pChannel1 = 1;
		int level = player.getLevel();
		int sjexp = getUpgradeExp(level, player.getPlayer().getZsLevel());
		int dqexp = player.getPlayer().getExp() + exp;
		String savelog = null;
		if (dqexp < sjexp) {
			if (dqexp < 0) {
				player.getPlayer().setExp(0);
			} else {
				player.getPlayer().setExp(dqexp);
			}
			String expRate = ServiceManager.getManager().getVersionService().getVersion().getExpRate();
			savelog = "玩家增加经验：id=" + player.getId() + "---name=" + player.getName() + "---exp=" + exp + "-------当前经验:"
					+ player.getPlayer().getExp() + "------当日胜利次数：" + player.getBattleNum() + "-----打折比例" + expRate;
		} else {
			if (player.getPlayer().getLevel() < WorldServer.config.getMaxLevel(player.getPlayer().getZsLevel())) {
				player.getPlayer().setLevel(level + 1);
				player.getPlayer().setExp(dqexp - sjexp);
				try {
					UpdatePlayerLevel updatePlayerLevel = new UpdatePlayerLevel();
					updatePlayerLevel.setLevel(player.getPlayer().getLevel());
					player.sendData(updatePlayerLevel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				GameLogService.upLivel(player.getId(), player.getLevel());
				savelog = "玩家增加等级：id=" + player.getId() + "---name=" + player.getName() + "---oldlevel=" + level + "---newlevel="
						+ player.getLevel();
				// 触发宠物升级检查,如果玩家有足够经验时也会升级
				checkPetLevel(player);
			} else {
				player.getPlayer().setExp(sjexp);
			}
		}
		savePlayerData(player.getPlayer());
		if (null != savelog)
			writeLog(savelog);
		int pChannel2 = 1;
		if (pChannel1 != pChannel2 && player.getChannelId() == Common.GAME_INTERFACE_HALL) {// 如果玩家所在战斗频道变更，并且在大厅界面则切换频道
			ServiceManager
					.getManager()
					.getChatService()
					.syncChannels(player.getId(),
							new String[]{ChatService.CHAT_CURRENT_CHANNEL + "_" + player.getChannelId() + "" + pChannel2},
							new String[]{ChatService.CHAT_CURRENT_CHANNEL + "_" + player.getChannelId() + "" + pChannel1});
		}
		player.updateFight();
		// 更新角色信息-等级,经验,下级经验
		Map<String, String> info = new HashMap<String, String>();
		info.put("level", player.getLevel() + "");
		info.put("exp", player.getPlayer().getExp() + "");
		info.put("maxExp", getUpgradeExp(player.getLevel(), player.getPlayer().getZsLevel()) + "");
		int cardSeatNum = player.getPlayer().getZsLevel() > 0 ? 99 : player.getLevel();
		cardSeatNum = cardSeatNum > 99 ? 99 : cardSeatNum;
		info.put("cardSeatNum", cardSeatNum + "");
		sendUpdatePlayer(info, player);
	}

	// 触发宠物升级检查,如果玩家有足够经验时也会升级
	private void checkPetLevel(WorldPlayer player) {

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
	public void updatePlayerGold(WorldPlayer worldPlayer, int gold, String origin, String remark) throws Exception {
		if (0 == gold)
			return;
		Player player = worldPlayer.getPlayer();
		int myGold = player.getMoneyGold() + gold;
		player.setMoneyGold(myGold);
		savePlayerData(player);
		// writeLog("玩家增加金币保存玩家信息：id=" + player.getId() + "---name=" +
		// player.getName() + "---level=" + player.getLevel());
		Map<String, String> info = new HashMap<String, String>();
		info.put("gold", worldPlayer.getMoney() + "");
		sendUpdatePlayer(info, worldPlayer);
		if (gold > 0) {
			GameLogService.addGold(player.getId(), player.getLevel(), origin, gold, remark);
		} else {
			GameLogService.useGold(player.getId(), player.getLevel(), origin, gold, remark);
		}
	}

	/**
	 * 获取玩家升级所需经验
	 * 
	 * @param level
	 *            玩家当前等级
	 * @param zsLevel
	 *            玩家转生等级
	 * @return
	 */
	public int getUpgradeExp(int level, int zsLevel) {
		if (zsLevel > 0) {
			return 50 * level * level * level + 2 * level + 50000;
		} else {
			return 5 * level * level - 2 * level + 50;
		}
	}

	public void writeLog(Object message) {
		log.info(message);
	}

	/**
	 * 增加点卷
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
	 * 使用钻石
	 * 
	 * @param worldPlayer
	 *            玩家
	 * @param amount
	 *            使用的钻石数量
	 * @param origin
	 *            来源
	 * @param itemId
	 *            物品ID
	 * @param itemPriceId
	 *            物品价格
	 * @param remark
	 * @throws ProtocolException
	 */
	public void useTicket(WorldPlayer worldPlayer, int amount, int origin, int[] itemId, int[] itemPriceId, String remark)
			throws ProtocolException {}

 
	
	/**
	 * 踢玩家下线
	 * 
	 * @param playerId
	 */
	public void killLine(int playerId) {
		WorldPlayer worldPlayer = getOnlineWorldPlayer(playerId);
		if (null != worldPlayer) {
			clearPlayer(worldPlayer);
			worldPlayer.getConnectSession().killSession(worldPlayer.getClient().getSessionId());
			worldPlayer.getConnectSession().removeClient(worldPlayer.getClient());
		}
	}


 
 

	/**
	 * 推送玩家信息到客户端
	 * 
	 * @param player
	 */
	public void sendPlayerInfo(WorldPlayer player) {
		if (player == null || player.getPlayer() == null || !player.isOnline())
			return;
		com.wyd.empire.protocol.data.cache.PlayerInfo playerInfo = new com.wyd.empire.protocol.data.cache.PlayerInfo();
		Player playerBean = player.getPlayer();
		playerInfo.setId(player.getId());
		playerInfo.setName(player.getName());
		playerInfo.setSex(playerBean.getSex());
		playerInfo.setTitle(player.getPlayerTitle());
		// 公会
		playerInfo.setLevel(player.getLevel());
		playerInfo.setExp(playerBean.getExp());
		playerInfo.setMaxExp(getUpgradeExp(player.getLevel(), player.getPlayer().getZsLevel()));
		playerInfo.setRank(playerBean.getHonorLevel());
		if (player.isVip()) {
			playerInfo.setVipLevel(playerBean.getVipLevel());
		} else {
			playerInfo.setVipLevel(0);
		}
		playerInfo.setRedDiamond(player.getDiamond());
		playerInfo.setBlueDiamond(player.getDiamond());
		playerInfo.setGold(playerBean.getMoneyGold());
		playerInfo.setMedal(player.getMedalNum());
		playerInfo.setWinNum(playerBean.getWinNum());
		playerInfo.setPlayNum(playerBean.getPlayNum());
		playerInfo.setZsLevel(playerBean.getZsLevel());
		playerInfo.setFighting(player.getFighting());
		playerInfo.setForce(player.getForce());
		playerInfo.setHp(player.getMaxHP());
		playerInfo.setArmor(player.getArmor());
		playerInfo.setAttack(player.getAttack());
		playerInfo.setAgility(player.getAgility());
		playerInfo.setDefend(player.getDefend());
		playerInfo.setPhysique(player.getPhysique());
		playerInfo.setCritRate(player.getCrit());
		playerInfo.setInjuryFree(player.getInjuryFree());
		playerInfo.setReduceCrit(player.getReduceCrit());
		playerInfo.setPhysical(player.getMaxPF());
		playerInfo.setWreckDefense(player.getWreckDefense());
		playerInfo.setLuck(player.getLuck());
		PlayerPicture privateInfo = null;
		playerInfo.setAge(privateInfo.getAge());
		playerInfo.setSignature(privateInfo.getSignatureContent());
		playerInfo.setConstellation(privateInfo.getConstellation());
		// 玩家头像
		setPictureUrl(privateInfo, playerInfo);
		playerInfo.setMateName("");
		// 设置buff
		if (player.getBuffList() != null) {
			playerInfo.setBuff(JSONArray.fromObject(player.getBuffList()).toString());
		} else {
			playerInfo.setBuff("[]");
		}
		int cardSeatNum = player.getPlayer().getZsLevel() > 0 ? 99 : player.getLevel();
		cardSeatNum = cardSeatNum > 99 ? 99 : cardSeatNum;
		playerInfo.setCardSeatNum(cardSeatNum);
		playerInfo.setVigor(player.getVigor());
		playerInfo.setMaxVigor(player.isVip() ? 150 : 120);
		playerInfo.setBuyVigorTimes(player.getBuyVigorCount());
		playerInfo.setMaxBuyVigorTimes(player.getMaxBuyVigorCount());
		playerInfo.setSoulDot(player.getPlayerInfo().getSoulDot());
		playerInfo.setStarSoulLeve(player.getPlayerInfo().getStarSoulLeve() == 0 ? 1 : player.getPlayerInfo().getStarSoulLeve());
		playerInfo.setSeniorMedlNumber(player.getSeniorMedalNum());
		playerInfo.setPracticeLeve(player.getPlayerInfo().getPracticeLeve() == 0 ? 1 : player.getPlayerInfo().getPracticeLeve());
		playerInfo.setPracticeAttributeExp((player.getPlayerInfo().getPracticeAttributeExp() == null || player.getPlayerInfo()
				.getPracticeAttributeExp().equals("")) ? "0,0,0,0,0" : player.getPlayerInfo().getPracticeAttributeExp());
		// 获得特殊标示
		Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
		// 使用勋章上限数
		int useLimitNumber;
		// 如果玩家配置每日使用勋章上限数默认为玩家当前等级
		if (map.get("useLimitNumber") == null) {
			useLimitNumber = player.getLevel();
		} else {
			useLimitNumber = map.get("useLimitNumber");
		}
		// 今日还可以使用勋章数
		int useTodayNumber = 0;
		// 每日重置今日勋章可用数
		if (player.getPlayerInfo().getLastPracticeTime() == null
				|| !DateUtil.isSameDate(player.getPlayerInfo().getLastPracticeTime(), new Date())) {
			useTodayNumber = useLimitNumber;
		} else {
			useTodayNumber = player.getPlayerInfo().getUseTodayNumber();
		}
		playerInfo.setUseTodayNumber(useTodayNumber);
		playerInfo.setUseLimitNumber(useLimitNumber);
		// playerInfo.setPracticeStatus(player.getPlayerInfo().getPracticeStatus());
		// 设置微博信息
		setWeibo(player, playerInfo);
		int petBarNum = 1;
		playerInfo.setPetBarNum(petBarNum);
		player.sendData(playerInfo);
	}

 
	private void setPictureUrl(PlayerPicture privateInfo, com.wyd.empire.protocol.data.cache.PlayerInfo playerInfo) {
		String[] testArry = privateInfo.getPictureUrlTest().equals("") ? new String[0] : privateInfo.getPictureUrlTest().split(",");
		String testUrl = "";
		// 格式调整非空状态下前后加上","
		String passUrl = "," + privateInfo.getPictureUrlPass() + ",";
		// 处理对应的待审核图片替换已经更改的图片显示
		int index = 0;
		for (String urls : testArry) {
			// 格式： 待审核替换地址 # 待被替换图片地址 192.168.1.2#192.168.1.8
			String[] url = urls.split("#");
			if (index > 0) {
				testUrl += ",";
			}
			testUrl += url[0];
			if (url.length != 2) { // 容错处理
				passUrl = passUrl.replaceAll(url[1] + ",", "");
			}
			index++;
		}
		// 最终格式处理去除多余的","
		if (!passUrl.equals("") && !passUrl.equals(",")) {
			passUrl = passUrl.substring(1, passUrl.length() - 1);
		} else if (passUrl.equals(",")) {
			passUrl = "";
		}
		playerInfo.setPictureUrl(passUrl);
		playerInfo.setPendingUrl(testUrl);
	}

 

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

	// 微博信息
	private void setWeibo(WorldPlayer player, com.wyd.empire.protocol.data.cache.PlayerInfo playerInfo) {
		String[] wbIdSplits = player.getPlayer().getWbUserId().split(",");
		String[] wbIconsSplit = player.getPlayer().getWbUserIcon().split(",");
		String wbId = "", wbIcon = "";
		for (String wbIdStr : wbIdSplits) {
			String[] wbIdSplit = wbIdStr.split("=");
			wbId = wbIdSplit[1];
			if (wbId.length() > 3 && !"null".equalsIgnoreCase(wbId)) {
				break;
			}
		}
		for (String wbIconStr : wbIconsSplit) {
			String[] wbIconSplit = wbIconStr.split("=");
			wbIcon = wbIconSplit[1];
			if (wbIcon.length() > 10) {
				break;
			}
		}
		playerInfo.setWeibo(wbId + "," + wbIcon);
	}

	// 战力及影响战力属性
	public void updateAttribute(WorldPlayer player) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("fighting", player.getFighting() + "");
		info.put("hp", player.getMaxHP() + "");
		info.put("armor", player.getArmor() + "");
		info.put("attack", player.getAttack() + "");
		info.put("agility", player.getAgility() + "");
		info.put("defend", player.getDefend() + "");
		info.put("physique", player.getPhysique() + "");
		info.put("dritRate", player.getCrit() + "");
		info.put("injuryFree", player.getInjuryFree() + "");
		info.put("reduceCrit", player.getReduceCrit() + "");
		info.put("physical", player.getPhysique() + "");
		info.put("wreckDefense", player.getWreckDefense() + "");
		info.put("luck", player.getLuck() + "");
		info.put("force", player.getForce() + "");
		info.put("critRate", player.getCrit() + "");
		sendUpdatePlayer(info, player);
	}

	/**
	 * 更新玩家头像
	 * 
	 * @param player
	 * @param privateInfo
	 */
	public void sendUpdatePrivateInfo(WorldPlayer player, PlayerPicture privateInfo) {
		com.wyd.empire.protocol.data.cache.PlayerInfo playerInfo = new com.wyd.empire.protocol.data.cache.PlayerInfo();
		playerInfo.setAge(privateInfo.getAge());
		playerInfo.setSignature(privateInfo.getSignatureContent());
		playerInfo.setConstellation(privateInfo.getConstellation());
		setPictureUrl(privateInfo, playerInfo);
		Map<String, String> info = new HashMap<String, String>();
		info.put("age", playerInfo.getAge() + "");
		info.put("signature", playerInfo.getSignature());
		info.put("constellation", playerInfo.getConstellation() + "");
		info.put("pictureUrl", playerInfo.getPictureUrl());
		info.put("pendingUrl", playerInfo.getPendingUrl());
		sendUpdatePlayer(info, player);
	}

	/**
	 * 供定时器调用，用于定时所有在线玩家增加活力
	 */
	public void sysPlayersVigorUp() {
		Collection<WorldPlayer> playerList = getOnlinePlayer();
		Calendar cal = Calendar.getInstance();
		for (WorldPlayer player : playerList) {
			player.vigorUp(1);
			player.setVigorUpdateTime(cal.getTime());
		}
	}

}