package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.bean.PlayerTaskTitle;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.SerializeUtil;
import com.wyd.empire.world.dao.ITitleDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.title.PlayerTitleInfo;
import com.wyd.empire.world.title.PlayerTitleVo;
import com.wyd.empire.world.title.TitleIng;
import com.wyd.empire.world.title.TitleKey;

/**
 * The service class for the TabConsortiaright entity.
 */
public class TitleService extends UniversalManagerImpl implements ITitleService {
	public TitleService() {
		super();
	}

	/**
	 * The dao instance injected by Spring.
	 */
	private ITitleDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "TitleService";

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static ITitleService getInstance(ApplicationContext context) {
		return (ITitleService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(ITitleDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public ITitleDao getDao() {
		return this.dao;
	}

	public void initData() {
		dao.initData();
	}

	public List<TitleIng> createTitleByPlayer(List<PlayerTitleVo> playerTitleVoList) {
		List<TitleIng> titleIngList = new ArrayList<TitleIng>();
		if (playerTitleVoList != null && !playerTitleVoList.isEmpty()) {
			PlayerTitleVo playerTitleVo;
			for (int i = playerTitleVoList.size() - 1; i >= 0; i--) {
				playerTitleVo = playerTitleVoList.get(i);
				Title title = ServiceManager.getManager().getTitleService().getTitleById(playerTitleVo.getTitleId());
				if (null != title) {
					titleIngList.add(ServiceManager.getManager().getPlayerTaskTitleService().getTitleIng(title, playerTitleVo));
				} else {
					playerTitleVoList.remove(i);
				}
			}
		}
		return titleIngList;
	}

	/**
	 * 更新任务完成情况
	 * 
	 * @param playerTask
	 * @param type
	 * @param script
	 */
	public void titleTaskCheck(WorldPlayer worldPlayer, String findshScript, int count) {
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask(worldPlayer, findshScript, count));
	}

	/**
	 * 更新任务完成情况改由独立线程执行
	 * 
	 * @param worldPlayer
	 * @param findshScript
	 * @param count
	 * @return
	 */
	private Runnable createTask(WorldPlayer worldPlayer, String findshScript, int count) {
		return new TaskCheckThread(worldPlayer, findshScript, count);
	}

	public class TaskCheckThread implements Runnable {
		private WorldPlayer worldPlayer;
		private String findshScript;
		private int count;

		public TaskCheckThread(WorldPlayer worldPlayer, String findshScript, int count) {
			this.worldPlayer = worldPlayer;
			this.findshScript = findshScript;
			this.count = count;
		}

		@Override
		public void run() {
			ServiceManager.getManager().getPlayerTaskTitleService().titleTaskCheck(worldPlayer, findshScript, count);
		}
	}

	/**
	 * 将未完成的一次性战斗任务初始化
	 * 
	 * @param player
	 */
	public void initialOneTimesTitle(WorldPlayer worldplayer) {
		ServiceManager.getManager().getPlayerTaskTitleService().initialOneTimesTitle(worldplayer);
	}

	/**
	 * 战斗成就
	 */
	public void battleTask(BattleTeam battleTeam, int winCamp) {
		// 任务完成条件检查
		String chead = TitleKey.ZD + ",";
		switch (battleTeam.getBattleMode()) {
			case 1 :
				chead += TitleKey.JJZD + ",";
				break;
			case 2 :
				chead += TitleKey.BSFH + ",";
				break;
			case 3 :
				chead += TitleKey.BSPW + ",";
				break;
		}
		switch (battleTeam.getPlayerNumMode()) {
			case 1 :
				chead += TitleKey.OOZD + ",";
				break;
		}
		String cbody = "";
		// List<Combat> combatList = new
		// ArrayList<Combat>(battleTeam.getCombatList());
		for (Combat combat : battleTeam.getCombatList()) {
			if (combat.isRobot()) {
				continue;
			}
			cbody = chead;
			// List<Integer> playerList = new ArrayList<Integer>();
			// for (Combat cb : battleTeam.getCombatList()) {
			// if (combat.getCamp() == cb.getCamp() &&
			// combat.getPlayer().getId() != cb.getPlayer().getId()) {
			// playerList.add(cb.getPlayer().getId());
			// }
			// }
			if (combat.getActionTimes() < 1 && combat.isDead()) {
				cbody += TitleKey.WCSS + ",";
			}
			if (combat.getBeKilledCount() > 0 && combat.getBeKillRound() < 3) {
				cbody += TitleKey.ZDLHS + ",";
			}
			titleTaskCheck(combat.getPlayer(), cbody, 1);
		}
		// combatList = null;
	}

	/**
	 * 更新战斗中使用道具的任务
	 * 
	 * @param combat
	 * @param toolsList
	 */
	public void battleToolsTask(Combat combat, List<Tools> toolsList) {
		if (!combat.isRobot() && !combat.isLost()) {
			for (Tools tools : toolsList) {
				String condition = TitleKey.SYDJ + "*-1,";
				condition += TitleKey.SYDJ + "*" + tools.getId() + ",";
				titleTaskCheck(combat.getPlayer(), condition, 1);
			}
		}
	}

	/**
	 * 更新击退玩家的任务
	 * 
	 * @param combat
	 * @param combatList
	 * @param killType
	 *            0击杀，1坑杀
	 */
	public void battleBeatTask(Combat combat, Combat dead, int killType) {
		if (combat.isRobot()) {
			return;
		}
		String condition = "";
		if (combat.getSex() != dead.getSex()) {
			condition += TitleKey.JSYX + ",";
		}
		if (combat.getCamp() != dead.getCamp()) {
			condition += TitleKey.JSDF + ",";
		} else {
			if (1 == killType) {
				condition += TitleKey.KSYF + ",";
			}
		}
		if (combat.equals(dead)) {
			condition += TitleKey.ZS + ",";
		}
		titleTaskCheck(combat.getPlayer(), condition, 1);
	}

	/**
	 * 更新击退玩家的任务
	 * 
	 * @param player
	 * @param shootSex
	 * @param shootCamp
	 * @param deadPlayerId
	 * @param deadSex
	 * @param deadCamp
	 * @param killType
	 */
	public void battleBeatTask(WorldPlayer player, int shootSex, int shootCamp, int[] deadPlayerId, int[] deadSex, int[] deadCamp,
			int killType) {
		for (int i = 0; i < deadSex.length; i++) {
			String condition = "";
			if (shootSex != deadSex[i]) {
				condition += TitleKey.JSYX + ",";
			}
			if (shootCamp != deadCamp[i]) {
				condition += TitleKey.JSDF + ",";
			} else {
				if (1 == killType) {
					condition += TitleKey.KSYF + ",";
				}
			}
			if (player.getId() == ServiceManager.getManager().getCrossService().getPlayerId(deadPlayerId[i])) {
				condition += TitleKey.ZS + ",";
			}
			titleTaskCheck(player, condition, 1);
		}
	}

	/**
	 * 添加好友
	 * 
	 * @param player
	 */
	public void addFriend(WorldPlayer player) {
		String condition = TitleKey.YYHY;
		int num = ServiceManager.getManager().getFriendService().getFriendNum(player.getId());
		condition += "*" + num + ",";
		if (num > 200) {
			condition += TitleKey.YYHY + "*200,";
			condition += TitleKey.YYHY + "*100,";
			condition += TitleKey.YYHY + "*50,";
		} else if (num > 100) {
			condition += TitleKey.YYHY + "*100,";
			condition += TitleKey.YYHY + "*50,";
		} else if (num > 50) {
			condition += TitleKey.YYHY + "*50,";
		}
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 使用钻石
	 * 
	 * @param player
	 * @param itemId
	 */
	public void useTicket(WorldPlayer player, int count) {
		String condition = TitleKey.XFTICKET + ",";
		titleTaskCheck(player, condition, count);
	}

	/**
	 * 使用物品
	 * 
	 * @param player
	 * @param itemId
	 */
	public void useSomething(WorldPlayer player, int itemId) {
		String condition = TitleKey.SYWP + "*-1,";
		condition += TitleKey.SYWP + "*" + itemId + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 使用飞行技能
	 */
	public void flySkill(WorldPlayer player) {
		String condition = TitleKey.SYDJ + "*1,";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 玩家升级
	 * 
	 * @param player
	 */
	public void upLevel(WorldPlayer player, int level) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 1; i <= level; i++) {
			sbf.append(TitleKey.LEVEL);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
		}
		titleTaskCheck(player, sbf.toString(), 1);
	}

	/**
	 * 宠物升级
	 * 
	 * @param player
	 */
	public void upPetLevel(WorldPlayer player, int level) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 1; i <= level; i++) {
			sbf.append(TitleKey.CWDJ);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
		}
		titleTaskCheck(player, sbf.toString(), 1);
	}

	/**
	 * 玩家使用大招
	 * 
	 * @param player
	 */
	public void useBigSkill(WorldPlayer player) {
		String condition = TitleKey.BIGSKILL + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 完成任务
	 * 
	 * @param player
	 */
	public void completeTask(WorldPlayer player) {
		String condition = TitleKey.WCRW + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 合成
	 * 
	 * @param player
	 */
	public void synthesis(WorldPlayer player) {
		String condition = TitleKey.HCCF + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 强化
	 * 
	 * @param player
	 */
	public void strengthen(WorldPlayer player, int level) {
		StringBuffer condition = new StringBuffer(TitleKey.QH).append("*-1,");
		for (int i = 1; i <= level; i++) {
			condition.append(TitleKey.QH).append("*").append(level).append(",");
		}
		titleTaskCheck(player, condition.toString(), 1);
	}

	/**
	 * 升星
	 * 
	 * @param player
	 */
	public void upgrade(WorldPlayer player, int star) {
		StringBuffer condition = new StringBuffer(TitleKey.SX).append("*-1,");
		for (int i = 1; i <= star; i++) {
			condition.append(TitleKey.SX).append("*").append(star).append(",");
		}
		titleTaskCheck(player, condition.toString(), 1);
	}

	/**
	 * 击杀怪物
	 * 
	 * @param player
	 * @param gId
	 */
	public void killGuai(WorldPlayer player, int gId) {
		String condition = TitleKey.JSGW + "-1*,";
		condition += TitleKey.JSGW + "*" + gId + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 通关副本
	 * 
	 * @param player
	 * @param fbId
	 */
	public void tgfb(WorldPlayer player, int fbId) {
		String condition = TitleKey.TGFB + "*" + fbId + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 复活
	 * 
	 * @param player
	 */
	public void fuHuo(WorldPlayer player) {
		String condition = TitleKey.ZDFH + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 更新创建或加入公会的任务
	 * 
	 * @param player
	 */
	public void joinConsortiaTask(WorldPlayer player) {
		String condition = TitleKey.GHJR + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 退出公会的任务
	 * 
	 * @param player
	 */
	public void leaveConsortiaTask(WorldPlayer player) {
		List<TitleIng> titleList = player.getTitleIngList();
		if (null != titleList) {
			for (TitleIng titleIng : titleList) {
				if (1 == titleIng.getTitleId()) {
					Title title = ServiceManager.getManager().getTitleService().getTitleById(titleIng.getTitleId());
					if (null == title.getTarget() || title.getTarget().equals("")) {
						titleIng.setStatus(Common.TITLE_STATUS_OBTAIN);
					} else {
						titleIng.setStatus(Common.TITLE_STATUS_NOT_OBTAIN);
					}
					titleIng.setTarget(title.getTarget());
					titleIng.setTargetStatus("ghjr=0");
					titleIng.init();
					break;
				}
			}
		}
	}

	/**
	 * 开通会员
	 * 
	 * @param player
	 */
	public void openVIP(WorldPlayer player) {
		String condition = TitleKey.KTHY + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 转生
	 * 
	 * @param player
	 */
	public void rebirth(WorldPlayer player) {
		String condition = TitleKey.JSZS + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 排位赛第一名
	 */
	public void rankFirst(WorldPlayer player) {
		String condition = TitleKey.PWS + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 弹王挑战赛第一名
	 */
	public void inteRankFirst(WorldPlayer player) {
		String condition = TitleKey.DWTZ + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 订婚
	 * 
	 * @param player
	 */
	public void marryTask(WorldPlayer player) {
		String condition = TitleKey.JH + ",";
		titleTaskCheck(player, condition, 1);
	}

	/**
	 * 离婚
	 * 
	 * @param player
	 */
	public void divorce(WorldPlayer player) {
		List<TitleIng> titleList = player.getTitleIngList();
		if (null != titleList) {
			for (TitleIng titleIng : titleList) {
				if (2 == titleIng.getTitleId()) {
					Title title = ServiceManager.getManager().getTitleService().getTitleById(titleIng.getTitleId());
					if (null == title.getTarget() || title.getTarget().equals("")) {
						titleIng.setStatus(Common.TITLE_STATUS_OBTAIN);
					} else {
						titleIng.setStatus(Common.TITLE_STATUS_NOT_OBTAIN);
					}
					titleIng.setTarget(title.getTarget());
					titleIng.setTargetStatus("jh=0");
					titleIng.init();
					break;
				}
			}
		}
	}

	/**
	 * 获取婚姻称号
	 * 
	 * @param player
	 * @return
	 */
	public String getMarryTitle(Player player) {
		MarryRecord marryRecord = ServiceManager.getManager().getMarryService()
				.getSingleMarryRecordByPlayerId(player.getSex(), player.getId(), 1);
		if (null == marryRecord) {
			return "";
		}
		int mId = 0;
		String title;
		if (0 == player.getSex()) {
			mId = marryRecord.getWomanId();
			if (1 == marryRecord.getStatusMode()) {
				title = TipMessages.MARRYTITLE1;
			} else {
				title = TipMessages.MARRYTITLE3;
			}
		} else {
			mId = marryRecord.getManId();
			if (1 == marryRecord.getStatusMode()) {
				title = TipMessages.MARRYTITLE2;
			} else {
				title = TipMessages.MARRYTITLE4;
			}
		}
		Player p = ServiceManager.getManager().getPlayerService().getPlayerById(mId);
		return title.replace("###", p.getName());
	}

	/**
	 * 获取公会称号
	 * 
	 * @return
	 */
	public String guildTitle(WorldPlayer player) {
		String title = player.getGuildName() + "•";
		PlayerSinConsortia playerSinConsortia = ServiceManager.getManager().getPlayerSinConsortiaService()
				.findPlayerSinConsortia(player.getId());
		if (null == playerSinConsortia) {
			leaveConsortiaTask(player);
			return "";
		}
		title += playerSinConsortia.getPositionName();
		return title;
	}

	/**
	 * 根据称号ID获取称号对象
	 * 
	 * @param titleId
	 *            称号ID
	 * @return
	 */
	public Title getTitleById(int titleId) {
		return dao.getTitleById(titleId);
	}

	/**
	 * 设置玩家成就完成情况列表
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家成就完成情况列表
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerTitleInfo> getPlayerTitleInfoList(int playerId) {
		List<PlayerTitleInfo> playerTitleInfoList = null;
		List<PlayerTitleVo> playerTitleVoList = null;
		WorldPlayer titleWorldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
		if (titleWorldPlayer != null && titleWorldPlayer.getTitleIngList() != null && !titleWorldPlayer.getTitleIngList().isEmpty()) {
			playerTitleVoList = new ArrayList<PlayerTitleVo>(titleWorldPlayer.getTitleIngList());
		} else {
			PlayerTaskTitle playerTaskTitle = ServiceManager.getManager().getPlayerTaskTitleService()
					.getPlayerTaskTitleByPlayerId(playerId);
			if (playerTaskTitle != null) {
				try {
					playerTitleVoList = (List<PlayerTitleVo>) SerializeUtil.unserialize(playerTaskTitle.getTitleList());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (playerTitleVoList != null && !playerTitleVoList.isEmpty()) {
			playerTitleInfoList = new ArrayList<PlayerTitleInfo>();
			for (PlayerTitleVo playerTitleVo : playerTitleVoList) {
				Title title = ServiceManager.getManager().getTitleService().getTitleById(playerTitleVo.getTitleId());
				PlayerTitleInfo playerTitleInfo = new PlayerTitleInfo(title.getId());
				playerTitleInfo.setTitleId(title.getId());
				playerTitleInfo.setTitleName(title.getTitle());
				playerTitleInfo.setStatus(playerTitleVo.getStatus());
				playerTitleInfo.setEndTime(playerTitleVo.getEndTime());
				playerTitleInfo.setTargetStatus(playerTitleVo.getTargetStatus());
				playerTitleInfo.setNew(playerTitleVo.isNew());
				playerTitleInfoList.add(playerTitleInfo);
			}
		}
		return playerTitleInfoList;
	}

	/**
	 * 更新玩家称号信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param titleIndex
	 *            成就列表索引
	 * @param titleId
	 *            成就ID
	 * @param titleTargetStatus
	 *            成就任务完成情况
	 * @param titleStatus
	 *            成就完成状态
	 * @param isNew
	 *            是否标记为新
	 * @return 是否更新成功
	 */
	@SuppressWarnings("unchecked")
	public boolean updateTitleByGm(int playerId, int titleIndex, int titleId, String titleTargetStatus, byte titleStatus, boolean isNew) {
		boolean bReuslt = false;
		WorldPlayer titleWorldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
		if (titleWorldPlayer != null && titleWorldPlayer.isOnline() && titleWorldPlayer.getTitleIngList() != null
				&& !titleWorldPlayer.getTitleIngList().isEmpty()) { // 如果有在线缓存信息，则只修改缓存数据
			List<TitleIng> titleIngList = titleWorldPlayer.getTitleIngList();
			TitleIng titleIng = titleIngList.get(titleIndex);
			if (titleIng != null && titleIng.getTitleId() == titleId) {
				titleIng.setStatus(titleStatus);
				titleIng.setTargetStatus(titleTargetStatus);
				titleIng.setNew(isNew);
				titleIng.init();
				bReuslt = true;
			}
		} else {
			PlayerTaskTitle playerTaskTitle = ServiceManager.getManager().getPlayerTaskTitleService()
					.getPlayerTaskTitleByPlayerId(playerId);
			if (playerTaskTitle != null) {
				try {
					List<PlayerTitleVo> playerTitleVoList = (List<PlayerTitleVo>) SerializeUtil.unserialize(playerTaskTitle.getTitleList());
					if (playerTitleVoList != null && !playerTitleVoList.isEmpty()) {
						PlayerTitleVo playerTitleVo = playerTitleVoList.get(titleIndex);
						if (playerTitleVo != null && playerTitleVo.getTitleId() == titleId) {
							playerTitleVo.setStatus(titleStatus);
							playerTitleVo.setTargetStatus(titleTargetStatus);
							playerTitleVo.setNew(isNew);
							playerTaskTitle.setTitleList(SerializeUtil.serialize(playerTitleVoList));
							ServiceManager.getManager().getPlayerTaskTitleService().update(playerTaskTitle);
							if (titleWorldPlayer != null) {
								titleWorldPlayer.initialTitle(playerTitleVoList);
							}
							bReuslt = true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bReuslt;
	}

	@Override
	public void exploreDragon(WorldPlayer player, int star) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 1; i <= star; i++) {
			sbf.append(TitleKey.JLTX);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
		}
		titleTaskCheck(player, sbf.toString(), 1);
	}

	@Override
	public void exploreGold(WorldPlayer player, int star) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 1; i <= star; i++) {
			sbf.append(TitleKey.HJTX);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
		}
		titleTaskCheck(player, sbf.toString(), 1);
	}

	@Override
	public void explorePirates(WorldPlayer player, int star) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 1; i <= star; i++) {
			sbf.append(TitleKey.HZTX);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
		}
		titleTaskCheck(player, sbf.toString(), 1);
	}

	@Override
	public void activity(WorldPlayer player, int val) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 1; i <= val; i++) {
			sbf.append(TitleKey.HYZ);
			sbf.append("*");
			sbf.append(i);
			sbf.append(",");
		}
		titleTaskCheck(player, sbf.toString(), 1);
	}

	/**
	 * 同步新增加的称号列表
	 * 
	 * @param player
	 *            玩家信息
	 * @return 增加称号数量
	 */
	public int synchronousTitle(WorldPlayer worldPlayer) {
		int count = 0;
		List<Title> titleList = ServiceManager.getManager().getPlayerTaskTitleService().getEffectiveTitleList();
		List<Integer> playerTitleIdList = new ArrayList<Integer>();
		List<TitleIng> titleIngList = worldPlayer.getTitleIngList();
		if (titleIngList != null) {
			for (TitleIng titleIng : titleIngList) {
				playerTitleIdList.add(titleIng.getTitleId());
			}
		} else {
			titleIngList = new ArrayList<TitleIng>();
		}
		for (Title title : titleList) {
			if (!playerTitleIdList.contains(title.getId().intValue())) {
				TitleIng titleIng = new TitleIng(title.getId());
				if (title.getId() == Common.TITLE_INIT_ID) {
					titleIng.setTargetStatus("");
					titleIng.setStatus(Common.TITLE_STATUS_OBTAIN);
					titleIng.setNew(false);
				} else {
					titleIng.setTargetStatus("");
					titleIng.setStatus(Common.TITLE_STATUS_NOT_OBTAIN);
					titleIng.setNew(true);
				}
				titleIngList.add(titleIng);
				count++;
			}
		}
		if (count > 0) {
			worldPlayer.setTitleIngList(titleIngList);
			worldPlayer.initialTitle(ServiceManager.getManager().getPlayerTaskTitleService().savePlayerTitle(worldPlayer));
		}
		return count;
	}

}