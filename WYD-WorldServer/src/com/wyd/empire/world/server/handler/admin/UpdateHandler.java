package com.wyd.empire.world.server.handler.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;
import com.wyd.empire.protocol.data.admin.Update;
import com.wyd.empire.protocol.data.admin.UpdateResult;
import com.wyd.empire.protocol.data.cache.ChangeShop;
import com.wyd.empire.world.bean.ActivitiesAward;
import com.wyd.empire.world.bean.BossmapReward;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.bean.DailyActivity;
import com.wyd.empire.world.bean.DownloadReward;
import com.wyd.empire.world.bean.FullServiceReward;
import com.wyd.empire.world.bean.Guai;
import com.wyd.empire.world.bean.InviteReward;
import com.wyd.empire.world.bean.InviteServiceInfo;
import com.wyd.empire.world.bean.LocalPushList;
import com.wyd.empire.world.bean.LoginReward;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.Magnification;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerDIYTitle;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.bean.Promotions;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.bean.RechargeCrit;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.bean.Recommend;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.bean.RewardItems;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.Successrate;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Tips;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bean.VipReward;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.FundUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * gm工具数据查询
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class UpdateHandler implements IDataHandler {
	@SuppressWarnings("unused")
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		Update update = (Update) data;
		UpdateResult updateResult = new UpdateResult(data.getSessionId(), data.getSerial());
		int updateType = update.getUpdateType();
		int id = update.getId();
		String[] keys = update.getKeys();
		String[] values = update.getValues();
		int[] types = update.getTypes();
		List<Object> valueList = new ArrayList<Object>();
		for (int i = 0; i < keys.length; i++) {
			if (StringUtils.hasText(values[i]) && !("null").equals(values[i])) {
				Object value = values[i];
				switch (types[i]) {
					case 1 :
						value = values[i];
						break;
					case 2 :
						value = Integer.parseInt(values[i]);
						break;
					case 3 :
						value = Byte.parseByte(values[i]);
						break;
					case 4 :
						value = new Date((Long.parseLong(values[i]) * 60 * 1000));
						break;
					case 5 :
						value = Boolean.parseBoolean(values[i]);
						break;
					case 6 :
						value = Short.valueOf(values[i]);
						break;
					case 7 :
						value = Float.valueOf(values[i]);
						break;
				}
				valueList.add(value);
			} else {
				valueList.add(null);
			}
		}
		try {
			switch (updateType) {
				case 0 :
					WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(id);
					if (null != worldPlayer) {
						Player player = worldPlayer.getPlayer();
						int vipIndex = 0;
						for (int i = 0; i < keys.length; i++) {
							if (("name").equals(keys[i]) && !(valueList.get(i).toString().equals(player.getName()))) {
								if (!ServiceManager.getManager().getIPlayerService().checkName(valueList.get(i).toString())) {
									throw new Exception();
								}
							}
							if (("vipLevel").equals(keys[i])) {
								vipIndex = i;
								continue;
							}
							PropertyUtils.setProperty(player, keys[i], valueList.get(i));
						}
						if (player.getVipLevel().intValue() != Integer.parseInt(valueList.get(vipIndex).toString())) {
							worldPlayer.setVipInfo(Integer.parseInt(valueList.get(vipIndex).toString()), 0);
							int vipExp = 0;
							switch (player.getVipLevel()) {
								case 1 :
									vipExp = 0;
									break;
								case 2 :
									vipExp = 300;
									break;
								case 3 :
									vipExp = 800;
									break;
								case 4 :
									vipExp = 1800;
									break;
								case 5 :
									vipExp = 4300;
									break;
								case 6 :
									vipExp = 9300;
									break;
								case 7 :
									vipExp = 19300;
									break;
								case 8 :
									vipExp = 44300;
									break;
								case 9 :
									vipExp = 94300;
									break;
								case 10 :
									vipExp = 194300;
									break;
								default :
									break;
							}
						}
						ServiceManager.getManager().getPlayerService().savePlayerData(player);
						worldPlayer.updateFight();
						ServiceManager.getManager().getPlayerService().sendPlayerInfo(worldPlayer);
					}
					break;
				case 1 :
					ShopItem shopItem = ServiceManager.getManager().getShopItemService().getShopItemById(id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(shopItem, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().update(shopItem);
					// 更新上下架推送到在线玩家
					ChangeShop change = new ChangeShop();
					change.setId(shopItem.getId());
					change.setIsOnSale(shopItem.getIsOnSale());
					Collection<WorldPlayer> onlinePlayers = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
					for (WorldPlayer player : onlinePlayers) {
						player.sendData(change);
					}
					break;
				case 2 :
					Bulletin bulletin = (Bulletin) ServiceManager.getManager().getBulletinService().getService().get(Bulletin.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(bulletin, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getBulletinService().getService().update(bulletin);
					break;
				case 3 :
					// if (null == ServiceManager.getManager().getPushService())
					// break;
					// Push push = new Push();
					// for (int i = 0; i < keys.length; i++) {
					// PropertyUtils.setProperty(push, keys[i],
					// valueList.get(i));
					// }
					// ServiceManager.getManager().getPushService().editPush(push);
					break;
				case 5 :
					Recharge recharge = (Recharge) ServiceManager.getManager().getRechargeService().get(Recharge.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(recharge, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getRechargeService().update(recharge);
					break;
				case 6 :
					OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
					String areasIds = "";
					for (int i = 0; i < keys.length; i++) {
						if (("areas").equals(keys[i])) {
							if (valueList.get(i) != null && !valueList.get(i).toString().equals("")) {
								areasIds = valueList.get(i).toString();
							}
						} else {
							PropertyUtils.setProperty(operationConfig, keys[i], valueList.get(i));
						}

					}
					ServiceManager.getManager().getVersionService().getService().update(operationConfig);
					FundUtil.initMap();
					ServiceManager.getManager().getVersionService().getService().initData();
					if (!areasIds.equals(null) && !areasIds.equals("")) {
						ServiceManager.getManager().getVersionService().getService().updateCfgByAreasIds(operationConfig, areasIds);
					}
					break;
				case 7 :
					Mail mail = ServiceManager.getManager().getMailService().getMail(id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(mail, keys[i], valueList.get(i));
					}
					mail.setIsStick(Common.IS_STICK);
					ServiceManager.getManager().getMailService().update(mail);
					break;
				case 8 :
					Guai guai = (Guai) ServiceManager.getManager().getGuaiService().get(Guai.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(guai, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getMailService().update(guai);
					break;
				case 9 :
					Map map = (Map) ServiceManager.getManager().getGuaiService().get(Map.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(map, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getMapsService().getService().update(map);
					ServiceManager.getManager().getMapsService().updateMap(map);
					break;
				case 10 :
					BossmapReward bossmapReward = (BossmapReward) ServiceManager.getManager().getBossmapRewardService()
							.get(BossmapReward.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(bossmapReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getBossmapRewardService().update(bossmapReward);
					break;
				case 11 :
					Task task = (Task) ServiceManager.getManager().getTaskService().getService().getTaskById(id);
					if (task != null) {
						for (int i = 0; i < keys.length; i++) {
							PropertyUtils.setProperty(task, keys[i], valueList.get(i));
						}
						ServiceManager.getManager().getPlayerTaskTitleService().update(task);
					}
					break;
				case 12 :
					IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
					PlayerItemsFromShop playerItemsFromShop = (PlayerItemsFromShop) playerItemsFromShopService.get(
							PlayerItemsFromShop.class, id);
					playerItemsFromShop = playerItemsFromShopService.uniquePlayerItem(playerItemsFromShop.getPlayerId(),
							playerItemsFromShop.getShopItem().getId());
					playerItemsFromShop.setBuyTime((Date) valueList.get(0));
					playerItemsFromShop.setDispearAtOverTime(Boolean.valueOf(values[1]));
					playerItemsFromShop.setHollNum(Integer.parseInt(values[2]));
					playerItemsFromShop.setHollUsedNum(Integer.parseInt(values[3]));
					playerItemsFromShop.setIsInUsed(Boolean.valueOf(values[4]));
					playerItemsFromShop.setPExpExtraRate(Integer.parseInt(values[10]));
					playerItemsFromShop.setPLastNum(Integer.parseInt(values[11]));
					playerItemsFromShop.setPLastTime(Integer.parseInt(values[12]));
					playerItemsFromShop.setPUseLastTime(Integer.parseInt(values[13]));
					playerItemsFromShop.setSkillful(Integer.parseInt(values[14]));
					playerItemsFromShop.updateStrongLevel(Integer.parseInt(values[15]));
					playerItemsFromShop.setWeapSkill1(Integer.parseInt(values[16]));
					playerItemsFromShop.setWeapSkill2(Integer.parseInt(values[17]));
					playerItemsFromShop.setStars(Integer.parseInt(values[18]));
					ShopItem playerItem = playerItemsFromShop.getShopItem();
					playerItemsFromShop.pAddAttack();
					playerItemsFromShop.pAddDefend();
					playerItemsFromShop.pAddHp();
					ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItemsFromShop);
					if (playerItemsFromShop.getIsInUsed()) {
						WorldPlayer wPlayer = ServiceManager.getManager().getPlayerService()
								.getWorldPlayerById(playerItemsFromShop.getPlayerId());
						wPlayer.updateFight();
					}
					break;
				case 13 :
					Magnification magnification = (Magnification) ServiceManager.getManager().getActivitiesAwardService()
							.get(Magnification.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(magnification, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getMagnificationService().update(magnification);
					break;
				case 14 :
					ActivitiesAward activitiesAward = (ActivitiesAward) ServiceManager.getManager().getActivitiesAwardService()
							.get(ActivitiesAward.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(activitiesAward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getActivitiesAwardService().update(activitiesAward);
					break;
				case 15 :
					Promotions promotions = (Promotions) ServiceManager.getManager().getPromotionsService().getIPromotionsService()
							.get(Promotions.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(promotions, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getActivitiesAwardService().update(promotions);
					break;
				case 16 :
					LoginReward loginReward = (LoginReward) ServiceManager.getManager().getPromotionsService().getIPromotionsService()
							.get(LoginReward.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(loginReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getActivitiesAwardService().update(loginReward);
					break;
				case 17 :
					InviteServiceInfo inviteServiceInfo = (InviteServiceInfo) ServiceManager.getManager().getInviteService()
							.getIInviteService().get(InviteServiceInfo.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(inviteServiceInfo, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getInviteService().getIInviteService().update(inviteServiceInfo);
					break;
				case 18 :
					InviteReward inviteReward = (InviteReward) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(InviteReward.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(inviteReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getInviteService().getIInviteService().update(inviteReward);
					break;
				case 19 :
					Recommend recommend = (Recommend) ServiceManager.getManager().getShopItemService().get(Recommend.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(recommend, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().update(recommend);
					break;
				case 20 :
					Reward everyDayReward = (Reward) ServiceManager.getManager().getShopItemService().get(Reward.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(everyDayReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().update(everyDayReward);
					break;
				case 21 : // 成就管理编辑
					Title title = (Title) ServiceManager.getManager().getPlayerTaskTitleService().getTitle(id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(title, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getPlayerTaskTitleService().update(title);
					break;
				case 24 : // 修改公会技能
					ConsortiaSkill consortiaSkill = (ConsortiaSkill) ServiceManager.getManager().getConsortiaService()
							.get(ConsortiaSkill.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(consortiaSkill, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getConsortiaService().update(consortiaSkill);
					break;
				case 25 : // 修改加载提示信息
					Tips tips = (Tips) ServiceManager.getManager().getConsortiaService().get(Tips.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(tips, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getConsortiaService().update(tips);
					break;
				case 26 :// 修改充值抽奖
					LotteryReward lotteryReward = (LotteryReward) ServiceManager.getManager().getRechargeRewardService()
							.get(LotteryReward.class, id);
					for (int i = 0; i < keys.length; i++) {
						if (("shopItem").equals(keys[i])) {
							lotteryReward.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
									.get(ShopItem.class, Integer.parseInt(valueList.get(i).toString())));
						} else {
							PropertyUtils.setProperty(lotteryReward, keys[i], valueList.get(i));
						}
					}
					ServiceManager.getManager().getRechargeRewardService().update(lotteryReward);
					break;
				case 27 :// 27更新道具
					Tools tools = (Tools) ServiceManager.getManager().getInviteService().getIInviteService().get(Tools.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(tools, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getToolsService().update(tools);
					break;
				case 28 :// 根据id查询爱心许愿抽取奖励
					break;
				case 29 :// 根据商品id查询首充奖励
					RechargeReward rechargeReward = ServiceManager.getManager().getRechargeRewardService().getRechargeRewardById(id);
					for (int i = 0; i < keys.length; i++) {
						if (("shopItem").equals(keys[i])) {
							rechargeReward.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
									.get(ShopItem.class, Integer.parseInt(valueList.get(i).toString())));
						} else {
							PropertyUtils.setProperty(rechargeReward, keys[i], valueList.get(i));
						}
					}
					ServiceManager.getManager().getRechargeRewardService().update(rechargeReward);
					break;
				case 30 :// 根据id查询武器被动技能
					WeapSkill weapSkill = (WeapSkill) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(WeapSkill.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(weapSkill, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getToolsService().update(weapSkill);
					break;
				case 31 :// 根据id查询武器被动技能
					Successrate successrate = (Successrate) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(Successrate.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(successrate, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getToolsService().update(successrate);
					break;
				case 32 :// 更新通用物品奖励
					RewardItems rewardItems = ServiceManager.getManager().getRewardItemsService().getRewardItemsById(id);
					for (int i = 0; i < keys.length; i++) {
						if (("shopItem").equals(keys[i])) {
							rewardItems.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
									.get(ShopItem.class, Integer.parseInt(valueList.get(i).toString())));
						} else {
							PropertyUtils.setProperty(rewardItems, keys[i], valueList.get(i));
						}
					}
					ServiceManager.getManager().getRewardItemsService().update(rewardItems);
					break;
				case 33 :// 更新会员奖励
					VipReward vipReward = (VipReward) ServiceManager.getManager().getPromotionsService().getIPromotionsService()
							.get(VipReward.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(vipReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getActivitiesAwardService().update(vipReward);
					break;
				case 34 :// 更新全服奖励
					FullServiceReward fullServiceReward = (FullServiceReward) ServiceManager.getManager().getRewardItemsService()
							.get(FullServiceReward.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(fullServiceReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getRewardItemsService().update(fullServiceReward);
					break;
				case 39 :
					// WorldPlayer wp =
					// ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer((Integer)
					// valueList.get(0));
					// if (wp != null) {
					// PlayerTask taskIng = wp.getTaskIngByTaskId((Integer)
					// valueList.get(1));
					// if (taskIng != null) {
					// String[] statusString =
					// valueList.get(2).toString().split(",");
					// int[] status = new int[statusString.length];
					// for (int i = 0; i < statusString.length; i++) {
					// try {
					// status[i] = Integer.parseInt(statusString[i]);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					// }
					// taskIng.setTargetStatus(status);
					// }
					// }
					break;
				case 40 :
					DailyActivity dailyActivity = (DailyActivity) ServiceManager.getManager().getIDailyActivityService()
							.get(DailyActivity.class, id);
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					for (int i = 0; i < keys.length; i++) {
						if (keys[i].equals("startTime") || keys[i].equals("endTime")) {
							PropertyUtils.setProperty(dailyActivity, keys[i], sdf.parse((String) valueList.get(i)));
						} else {
							PropertyUtils.setProperty(dailyActivity, keys[i], valueList.get(i));
						}
					}
					if (dailyActivity.getRewardsAdd() == null) {
						dailyActivity.setRewardsAdd("");
					}
					if (dailyActivity.getRewardsSub() == null) {
						dailyActivity.setRewardsSub("");
					}
					if (dailyActivity.getRewardItems() == null) {
						dailyActivity.setRewardItems("");
					}
					if (dailyActivity.getMailTitle() == null) {
						dailyActivity.setMailTitle("");
					}
					if (dailyActivity.getMailContent() == null) {
						dailyActivity.setMailContent("");
					}
					ServiceManager.getManager().getIDailyActivityService().update(dailyActivity);
					break;
				case 43 : // 玩家称号编辑
					int titleIndex = Integer.parseInt(values[0]);
					int titleId = Integer.parseInt(values[1]);
					String titleTargetStatus = values[2];
					byte titleStatus = Byte.parseByte(values[3]);
					boolean titleIsNew = Boolean.parseBoolean(values[4]);
					ServiceManager.getManager().getTitleService()
							.updateTitleByGm(id, titleIndex, titleId, titleTargetStatus, titleStatus, titleIsNew);
					break;
				case 46 : // 玩家任务编辑
					int taskIndex = Integer.parseInt(values[0]);
					int taskId = Integer.parseInt(values[1]);
					String taskTargetStatus = values[2];
					byte taskStatus = Byte.parseByte(values[3]);
					boolean taskIsNew = Boolean.parseBoolean(values[4]);
					ServiceManager.getManager().getTaskService()
							.updateTaskByGm(id, taskIndex, taskId, taskTargetStatus, taskStatus, taskIsNew);
					break;
				case 52 :
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					PlayerDIYTitle title1 = (PlayerDIYTitle) ServiceManager.getManager().getPlayerDIYTitleService()
							.get(PlayerDIYTitle.class, id);
					for (int i = 0; i < keys.length; i++) {
						if ("startDate".equals(keys[i])) {
							if (valueList.get(i) != null) {
								String datestr = valueList.get(i).toString();
								title1.setStartDate(sf.parse(datestr));
							} else {
								title1.setStartDate(new Date());
							}
						} else if (!"days".equals(keys[i])) {
							PropertyUtils.setProperty(title1, keys[i], valueList.get(i));
						}
					}
					for (int i = 0; i < keys.length; i++) {
						if ("days".equals(keys[i])) {
							setTitleEndDate(title1, keys[i], valueList.get(i));
						}
					}
					ServiceManager.getManager().getPlayerDIYTitleService().update(title1);
					break;
				case 57 :// 57编辑付费包奖励 , TODO 此数据服务端开发人员未将它存到内存中去
					PayAppReward appReward = (PayAppReward) ServiceManager.getManager().getPayAppRewardService()
							.get(PayAppReward.class, id);
					String mailTitle = "";
					String mailContent = "";
					for (int i = 0; i < keys.length; i++) {
						if (("shopItem").equals(keys[i])) {
							appReward.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
									.get(ShopItem.class, Integer.parseInt(valueList.get(i).toString())));
						} else {
							PropertyUtils.setProperty(appReward, keys[i], valueList.get(i));
							if (keys[i].equals("mailTitle")) {
								mailTitle = valueList.get(i).toString();
							} else if (keys[i].equals("mailContent")) {
								mailContent = valueList.get(i).toString();
							}
						}
					}
					ServiceManager.getManager().getPayAppRewardService().update(appReward);
					if (!mailContent.equals(null) && !mailTitle.equals(null)) {
						ServiceManager.getManager().getPayAppRewardService().updateMail(mailTitle, mailContent);
					}
					break;
				case 58 :// 更新button信息
					ButtonInfo buttonInfo = (ButtonInfo) ServiceManager.getManager().getOperationConfigService().get(ButtonInfo.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(buttonInfo, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getRewardItemsService().update(buttonInfo);
					List<WorldPlayer> playerList = new ArrayList<WorldPlayer>(ServiceManager.getManager().getPlayerService().getAllPlayer());
					for (WorldPlayer worldPlayers : playerList) {
						if (worldPlayers.getButtonInfo() != null) {
							worldPlayers.setButtonInfo(null);
						}
					}
					break;
				case 60 :// 更新充值暴击概率
					RechargeCrit rechargeCrit = (RechargeCrit) ServiceManager.getManager().getRechargeService().get(RechargeCrit.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(rechargeCrit, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getRewardItemsService().update(rechargeCrit);
					RechargeCrit rechargeCrit2 = (RechargeCrit) ServiceManager.getManager().getRechargeService()
							.get(RechargeCrit.class, id + 1);
					if (rechargeCrit2 != null) {
						rechargeCrit2.setLowNum(rechargeCrit.getHighNum() + 1);
						ServiceManager.getManager().getRewardItemsService().update(rechargeCrit2);
					}
					break;
				case 61 :// 更新玩家自定义头像等信息
					PlayerPicture playerPicture = (PlayerPicture) ServiceManager.getManager().getPictureUploadService()
							.get(PlayerPicture.class, id);
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(playerPicture, keys[i], valueList.get(i));
					}
					playerPicture.setAuditedTime(new Date());
					ServiceManager.getManager().getRewardItemsService().update(playerPicture);
					WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(playerPicture.getPlayerId());
					if (null != player) {
						player.setPlayerPicture(playerPicture);
					}
					break;
				case 62 :// 更新本地推送信息
					LocalPushList localPushList = new LocalPushList();
					/*
					 * for (int i = 0; i < values.length; i++) {
					 * System.out.println("i = " + i + " , values = " +
					 * values[i]); }
					 */
					localPushList.setId(Integer.parseInt(values[0]));
					localPushList.setTitle(values[1]);
					localPushList.setContent(values[2]);
					localPushList.setStartTime(new Date((Long.parseLong(values[3]) * 60 * 1000)));
					localPushList.setEndTime(new Date((Long.parseLong(values[4]) * 60 * 1000)));
					localPushList.setStartPushTime(new Date((Long.parseLong(values[5]) * 60 * 1000)));
					localPushList.setDaysOfWeek(values[6]);
					localPushList.setType(Integer.parseInt(values[7]));
					localPushList.setVersion(Integer.parseInt(values[8]));
					localPushList.setOfflineHour(Integer.parseInt(values[9]));
					// ServiceManager.getManager().getPictureUploadService().update(localPushList);
					ServiceManager.getManager().getLocalPushListService().update(localPushList);
					ServiceManager.getManager().getLocalPushListService().updateVersion(-1);
					ServiceManager.getManager().getLocalPushListService().initPushData();
					// new GetPushListHandler().notifyOnliePlayer();
					break;
				case 63 :// 更新本地推送信息版本
					String localPushVersion = values[0];
					ServiceManager.getManager().getLocalPushListService().updateVersion(Integer.parseInt(localPushVersion));
					ServiceManager.getManager().getLocalPushListService().initPushData();
					// new GetPushListHandler().notifyOnliePlayer();
					break;
				case 64 :// 更新IOS充值信息
					Recharge IOSRecharge = new Recharge();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(IOSRecharge, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getRechargeService().update(IOSRecharge);
					break;
				case 65 :// 更新公会等级
					Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService()
							.get(Consortia.class, Integer.parseInt(values[0]));
					consortia.setLevel(Integer.parseInt(values[1]));
					ServiceManager.getManager().getConsortiaService().update(consortia);
					break;
				case 66 :// 更新分包下载
					DownloadReward downloadReward = new DownloadReward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(downloadReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getDownloadRewardService().update(downloadReward);
					break;
			}
			updateResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			updateResult.setSuccess(false);
		}
		session.write(updateResult);
	}

	private void setTitleEndDate(PlayerDIYTitle title, String key, Object days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(title.getStartDate());
		Integer d = (Integer) days;
		d = d.intValue() == -1 ? 999999 : d;// -1表示永久
		cal.add(Calendar.DAY_OF_YEAR, d);
		title.setEndDate(cal.getTime());
	}
}