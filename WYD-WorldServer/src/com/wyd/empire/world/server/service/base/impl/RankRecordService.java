package com.wyd.empire.world.server.service.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.RankRecord;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.dao.IRankRecordDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IRankRecordService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class RankRecordService extends UniversalManagerImpl implements IRankRecordService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IRankRecordDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "RankRecordService";

	public RankRecordService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IRankRecordService getInstance(ApplicationContext context) {
		return (IRankRecordService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IRankRecordDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IRankRecordDao getDao() {
		return this.dao;
	}

	/**
	 * 获得排位赛排名
	 * 
	 * @return
	 */
	public List<RankRecord> getRankRecordList(int limitNum) {
		List<RankRecord> rrList = dao.getRankRecordList(limitNum);
		for (RankRecord rr : rrList) {
			Hibernate.initialize(rr.getPlayer());
		}
		return rrList;
	}

	/**
	 * 获得玩家排名
	 * 
	 * @return
	 */
	public Integer getPlayerRankNum(int playerId) {
		return dao.getPlayerRankNum(playerId);
	}

	/**
	 * 获得玩家排名赛对象
	 * 
	 * @return
	 */
	public RankRecord getPlayerRankByPlayerId(int playerId) {
		return dao.getPlayerRankByPlayerId(playerId);
	}

	/**
	 * 清除排位赛记录
	 */
	public void deleteRankRecordForStart() {
		dao.deleteRankRecordForStart();
	}

	/**
	 * 将积分更新到玩家表
	 */
	public void updateHonorToPlayer() {
		List<RankRecord> rrList = dao.getRankRecordList(0);
		for (RankRecord rr : rrList) {
			Hibernate.initialize(rr.getPlayer());
		}
		WorldPlayer worldPlayer;
		int addIntegral = 0;
		for (RankRecord rr : rrList) {
			worldPlayer = ServiceManager.getManager().getPlayerService().getLoadPlayer(rr.getPlayer().getId());
			addIntegral = rr.getIntegral() - rr.getLastIntegral() < 0 ? 0 : rr.getIntegral() - rr.getLastIntegral();
			if (null != worldPlayer) {
				worldPlayer.getPlayer().setHonor(worldPlayer.getPlayer().getHonor() + addIntegral);
				rr.setLastIntegral(rr.getIntegral());
				updatePlayerHonorLevel(worldPlayer.getPlayer());
				ServiceManager.getManager().getPlayerService().savePlayerData(worldPlayer.getPlayer());
			} else {
				rr.getPlayer().setHonor(rr.getPlayer().getHonor() + addIntegral);
				rr.setLastIntegral(rr.getIntegral());
				updatePlayerHonorLevel(rr.getPlayer());
				this.update(rr.getPlayer());
			}
			this.update(rr);
		}
	}

	/**
	 * 更新玩家排位赛等级
	 * 
	 * @param player
	 */
	public void updatePlayerHonorLevel(Player player) {
		// 计算出下一级所需的荣誉值
		int nextHonor = 120 * (int) Math.pow(player.getHonorLevel(), 3);
		while (player.getHonor() > nextHonor && player.getHonorLevel() < 14) {
			player.setHonorLevel(player.getHonorLevel() + 1);
			List<ShopItem> siList = dao.getHonorReward(player.getSex(), player.getHonorLevel());
			if (siList.size() > 0) {
				for (ShopItem si : siList) {
					// 发放奖励
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), si.getId(), -1, -1, -1, 11, null, 0, 0, 0);
					// 发邮件
					Mail mail = new Mail();
					mail.setContent(TipMessages.HONOR_MAIL2.replace("XX", TipMessages.honorMap.get("HONORLEVEL" + player.getHonorLevel()))
							.replace("YY", si.getName()));
					mail.setReceivedId(player.getId());
					mail.setSendId(0);
					mail.setSendName(TipMessages.SYSNAME_MESSAGE);
					mail.setSendTime(new Date());
					mail.setIsRead(false);
					mail.setType(1);
					mail.setBlackMail(false);
					mail.setIsStick(Common.IS_STICK);
					mail.setTheme(TipMessages.SYS_MAIL);
					ServiceManager.getManager().getMailService().saveMail(mail, null);
				}
			}
			nextHonor = 120 * (int) Math.pow(player.getHonorLevel(), 3);
		}
		WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(player.getId());
		if (worldPlayer != null) {
			// 更新角色信息 - 排位
			Map<String, String> info = new HashMap<String, String>();
			info.put("rank", player.getHonorLevel() + "");
			ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, worldPlayer);
		}
	}

	/**
	 * 获得排位赛前十的奖励
	 * 
	 * @return
	 */
	public List<RewardInfo> getRankReward() {
		String rankReward = ServiceManager.getManager().getVersionService().getVersion().getRankreward();
		List<RewardInfo> list = ServiceUtils.getRewardInfo(rankReward);
		return list;
	}

	/**
	 * 玩家获得排位赛奖励（前十名）
	 */
	public void playerGetRankReward() {
		List<RankRecord> rrList = dao.getRankRecordList(10);
		List<RewardInfo> rewardList = getRankReward();
		ShopItem si;
		int index = 1;
		for (RankRecord rr : rrList) {
			String rewardStr = "";
			for (RewardInfo reward : rewardList) {
				si = (ShopItem) dao.get(ShopItem.class, reward.getItemId());
				// 发放奖励
				if (reward.isAddDay()) {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(rr.getPlayer().getId(), reward.getItemId(), -1, reward.getCount(), -1, 11, null, 0, 0, 0);
				} else {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(rr.getPlayer().getId(), reward.getItemId(), -1, -1, reward.getCount(), 11, null, 0, 0, 0);
				}
				rewardStr += " [" + si.getName() + "] ";
			}
			// 发邮件
			Mail mail = new Mail();
			mail.setContent(TipMessages.HONOR_MAIL1.replace("XX", index + "").replace("YY", rewardStr));
			mail.setReceivedId(rr.getPlayer().getId());
			mail.setSendId(0);
			mail.setSendName(TipMessages.SYSNAME_MESSAGE);
			mail.setSendTime(new Date());
			mail.setIsRead(false);
			mail.setType(1);
			mail.setBlackMail(false);
			mail.setIsStick(Common.IS_STICK);
			mail.setTheme(TipMessages.SYS_MAIL);
			ServiceManager.getManager().getMailService().saveMail(mail, null);
			index++;
		}
	}

	/**
	 * 给予排位赛第一名奖励
	 */
	public void giveFirstRankReward() {
		List<RankRecord> rrList = dao.getRankRecordList(1);
		String rewardStr = " [";
		OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
		for (RankRecord rr : rrList) {
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(rr.getPlayer().getId());
			// 记录成就
			ServiceManager.getManager().getTitleService().rankFirst(worldPlayer);
			int sta = 0;
			int end = 0;
			String[] awards = operationConfig.getFirstRankReward().split("&");
			for (String award : awards) {
				if (award.startsWith("gold")) {
					sta = award.indexOf("=") + 1;
					String golds = award.substring(sta);
					try {
						ServiceManager.getManager().getPlayerService()
								.updatePlayerGold(worldPlayer, Integer.parseInt(golds), "排位赛奖励", "-- " + " --");
					} catch (Exception e) {
						e.printStackTrace();
					}
					rewardStr += "金币，";
				} else if (award.startsWith("ticket")) {
					sta = award.indexOf("=") + 1;
					String tickets = award.substring(sta);
					ServiceManager.getManager().getPlayerService()
							.addTicket(worldPlayer, Integer.parseInt(tickets), 0, TradeService.ORIGIN_TASK, 0, "", "排位赛奖励", "", "");
					rewardStr += "钻石，";
				} else {
					sta = award.indexOf("wp*") + 3;
					end = award.indexOf("count*");
					int wpId = Integer.parseInt(award.substring(sta, end));
					sta = end + 6;
					end = award.indexOf("day*");
					int count = Integer.parseInt(award.substring(sta, end));
					sta = end + 4;
					end = award.indexOf("sex*");
					int day = Integer.parseInt(award.substring(sta, end));
					sta = end + 4;
					end = award.indexOf("level*");
					int sex;
					int level = -1;
					if (end > -1) {
						sex = Integer.parseInt(award.substring(sta, end));
						sta = end + 6;
						level = Integer.parseInt(award.substring(sta));
					} else {
						sex = Integer.parseInt(award.substring(sta));
					}
					ShopItem shopItem = (ShopItem) ServiceManager.getManager().getiShopItemsPriceService().get(ShopItem.class, wpId);
					rewardStr += shopItem.getName() + "，";
					if (rr.getPlayer().getSex().intValue() == sex || 2 == sex) {
						try {
							ServiceManager.getManager().getRechargeRewardService().givenItems(worldPlayer, count, day, wpId, level, null);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			rewardStr = rewardStr.substring(0, rewardStr.lastIndexOf("，"));
			rewardStr += "] ";
			// 发邮件
			Mail mail = new Mail();
			mail.setContent(TipMessages.HONOR_MAIL1.replace("XX", "1").replace("YY", rewardStr));
			mail.setReceivedId(rr.getPlayer().getId());
			mail.setSendId(0);
			mail.setSendName(TipMessages.SYSNAME_MESSAGE);
			mail.setSendTime(new Date());
			mail.setIsRead(false);
			mail.setType(1);
			mail.setBlackMail(false);
			mail.setIsStick(Common.IS_STICK);
			mail.setTheme(TipMessages.SYS_MAIL);
			ServiceManager.getManager().getMailService().saveMail(mail, null);
		}
	}

	/**
	 * 更新排位赛相关
	 */
	public void updateRank() {
		updateHonorToPlayer();
		playerGetRankReward();
		// 发送排位赛第一名奖励
		giveFirstRankReward();
	}
}