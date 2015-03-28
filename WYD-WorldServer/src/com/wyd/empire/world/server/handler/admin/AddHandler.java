package com.wyd.empire.world.server.handler.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;
import com.wyd.empire.protocol.data.admin.Add;
import com.wyd.empire.protocol.data.admin.AddResult;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.ActivitiesAward;
import com.wyd.empire.world.bean.BossmapReward;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.bean.ButtonInfo;
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
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.bean.PlayerDIYTitle;
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
import com.wyd.empire.world.bean.WishItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerDIYTitleService;
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
public class AddHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		Add add = (Add) data;
		AddResult addResult = new AddResult(data.getSessionId(), data.getSerial());
		addResult.setSuccess(true);
		int addType = add.getAddType();
		String[] keys = add.getKeys();
		String[] values = add.getValues();
		int[] types = add.getTypes();
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
				}
				valueList.add(value);
			} else {
				valueList.add(null);
			}
		}
		try {
			switch (addType) {
				case 0 :
					ShopItem shopItem = new ShopItem();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(shopItem, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().save(shopItem);
					break;
				case 1 :
					Bulletin bulletin = new Bulletin();
					String areas = "";
					for (int i = 0; i < keys.length; i++) {
						if (!keys[i].equals("areas")) {
							PropertyUtils.setProperty(bulletin, keys[i], valueList.get(i));
						} else {
							areas = (String) valueList.get(i);
						}
					}
					ServiceManager.getManager().getBulletinService().addBulletin(bulletin, areas);
					break;
				case 2 :
					String playerName = "";
					int playerId = Integer.parseInt(valueList.get(0).toString());
					if (playerId != 0) {
						playerName = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId).getName();
					}
					ServiceManager.getManager().getChatService().sendBulletinToWorld(valueList.get(1).toString(), playerName, false);
					break;
				case 3 :
					// if (null == ServiceManager.getManager().getPushService())
					// break;
					// Push push = new Push();
					// for (int i = 0; i < keys.length; i++) {
					// PropertyUtils.setProperty(push, keys[i],
					// valueList.get(i));
					// }
					// ServiceManager.getManager().getPushService().addPush(push);
					break;
				case 5 :
					Mail mail = new Mail();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(mail, keys[i], valueList.get(i));
					}
					mail.setIsStick(Common.IS_STICK);
					mail.setSendName(TipMessages.SYSNAME_MESSAGE);
					ServiceManager.getManager().getMailService().saveMail(mail, null);
					break;
				case 6 :
					Guai guai = new Guai();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(guai, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getGuaiService().save(guai);
					break;
				case 7 :
					Map map = new Map();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(map, keys[i], valueList.get(i));
					}
					Map newMap = (Map) ServiceManager.getManager().getMapsService().getService().save(map);
					ServiceManager.getManager().getMapsService().addMap(newMap);
					break;
				case 8 :
					BossmapReward bossmapReward = new BossmapReward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(bossmapReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getBossmapRewardService().save(bossmapReward);
					break;
				case 9 :
					Task task = new Task();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(task, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getPlayerTaskTitleService().save(task);
					break;
				case 10 :
					Magnification magnification = new Magnification();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(magnification, keys[i], valueList.get(i));
					}
					magnification.setAreaId(ServiceManager.getManager().getConfiguration().getString("areaid"));
					ServiceManager.getManager().getMagnificationService().save(magnification);
					break;
				case 11 :
					ActivitiesAward activitiesAward = new ActivitiesAward();
					String[] areaArr = new String[0];
					for (int i = 0; i < keys.length; i++) {
						if (!keys[i].equals("areaId")) {
							PropertyUtils.setProperty(activitiesAward, keys[i], valueList.get(i));
						} else {
							areaArr = StringUtils.commaDelimitedListToStringArray((String) valueList.get(i));
						}
					}
					activitiesAward.setPlayerIds("");
					List<Object> list = new ArrayList<Object>();
					for (int i = 0; i < areaArr.length; i++) {
						ActivitiesAward aa = new ActivitiesAward();
						PropertyUtils.copyProperties(aa, activitiesAward);
						aa.setAreaId(areaArr[i]);
						list.add(aa);
					}
					ServiceManager.getManager().getActivitiesAwardService().saveOrUpdateAll(list);
					break;
				case 12 :
					LoginReward loginReward = new LoginReward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(loginReward, keys[i], valueList.get(i));
					}
					loginReward.setAreaId(WorldServer.config.getAreaId());
					ServiceManager.getManager().getActivitiesAwardService().save(loginReward);
					break;
				case 13 :
					InviteServiceInfo inviteServiceInfo = new InviteServiceInfo();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(inviteServiceInfo, keys[i], valueList.get(i));
					}
					inviteServiceInfo.setAreaId(WorldServer.config.getAreaId());
					ServiceManager.getManager().getInviteService().getIInviteService().save(inviteServiceInfo);
					break;
				case 14 :
					InviteReward inviteReward = new InviteReward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(inviteReward, keys[i], valueList.get(i));
					}
					inviteReward.setAreaId(WorldServer.config.getAreaId());
					ServiceManager.getManager().getInviteService().getIInviteService().save(inviteReward);
					break;
				case 15 :
					Recommend recommend = new Recommend();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(recommend, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().save(recommend);
					break;
				case 16 :
					Reward signReward = new Reward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(signReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().save(signReward);
					break;
				case 17 :
					Title title = new Title();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(title, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getPlayerTaskTitleService().save(title);
					break;
				case 18 :
					LotteryReward lotteryReward = new LotteryReward();
					for (int i = 0; i < keys.length; i++) {
						if (("shopItem").equals(keys[i])) {
							lotteryReward.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
									.get(ShopItem.class, Integer.parseInt(valueList.get(i).toString())));
						} else {
							PropertyUtils.setProperty(lotteryReward, keys[i], valueList.get(i));
						}
					}
					ServiceManager.getManager().getRechargeRewardService().save(lotteryReward);
					break;
				case 19 : // 增加公会技能
					ConsortiaSkill consortiaSkill = new ConsortiaSkill();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(consortiaSkill, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().save(consortiaSkill);
					break;
				case 20 : // 新增提示
					Tips tips = new Tips();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(tips, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getShopItemService().save(tips);
					break;
				case 21 : // 新增道具
					Tools tools = new Tools();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(tools, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getToolsService().save(tools);
					break;
				case 22 :// 爱心许愿抽取奖励
					WishItem wishItem = new WishItem();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(wishItem, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getLotteryService().save(wishItem);
					break;
				case 23 :// 首充奖励表
					RechargeReward rechargeReward = new RechargeReward();
					for (int i = 0; i < keys.length; i++) {
						if (("shopItem").equals(keys[i])) {
							rechargeReward.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
									.get(ShopItem.class, Integer.parseInt(valueList.get(i).toString())));
						} else {
							PropertyUtils.setProperty(rechargeReward, keys[i], valueList.get(i));
						}
					}
					ServiceManager.getManager().getRechargeRewardService().save(rechargeReward);
					break;
				case 24 : // 新增道具
					WeapSkill weapSkill = new WeapSkill();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(weapSkill, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getToolsService().save(weapSkill);
					break;
				case 25 : // 新增强化概率
					Successrate successrate = new Successrate();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(successrate, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getToolsService().save(successrate);
					break;
				case 26 :// 通用物品奖励
					RewardItems rewardItems = new RewardItems();
					for (int i = 0; i < keys.length; i++) {
						if (("shopItem").equals(keys[i])) {
							rewardItems.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
									.get(ShopItem.class, Integer.parseInt(valueList.get(i).toString())));
						} else {
							PropertyUtils.setProperty(rewardItems, keys[i], valueList.get(i));
						}
					}
					ServiceManager.getManager().getRewardItemsService().save(rewardItems);
					break;
				case 27 :// 会员奖励
					VipReward vipReward = new VipReward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(vipReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getActivitiesAwardService().save(vipReward);
					break;
				case 28 :// 全服奖励
					FullServiceReward fullServiceReward = new FullServiceReward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(fullServiceReward, keys[i], valueList.get(i));
					}
					fullServiceReward.setAreaId(ServiceManager.getManager().getConfiguration().getString("areaid"));
					fullServiceReward.setCurrentCount(0);
					ServiceManager.getManager().getRewardItemsService().save(fullServiceReward);
					break;
				case 31 :// 日常活动
				{
					DailyActivity dailyActivity = new DailyActivity();
					String[] areaArray = new String[0];
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					for (int i = 0; i < keys.length; i++) {
						if (keys[i].equals("startTime") || keys[i].equals("endTime")) {
							PropertyUtils.setProperty(dailyActivity, keys[i], sdf.parse((String) valueList.get(i)));
						} else if (!keys[i].equals("areaId")) {
							PropertyUtils.setProperty(dailyActivity, keys[i], valueList.get(i));
						} else {
							areaArray = StringUtils.commaDelimitedListToStringArray((String) valueList.get(i));
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
					List<Object> dailyActivityList = new ArrayList<Object>();
					for (int i = 0; i < areaArray.length; i++) {
						DailyActivity da = new DailyActivity();
						PropertyUtils.copyProperties(da, dailyActivity);
						da.setAreaId(areaArray[i]);
						dailyActivityList.add(da);
					}
					ServiceManager.getManager().getIDailyActivityService().saveOrUpdateAll(dailyActivityList);
					break;
				}
				case 33 :
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					PlayerDIYTitle diyTitle = new PlayerDIYTitle();
					for (int i = 0; i < keys.length; i++) {
						if ("startDate".equals(keys[i])) {
							if (valueList.get(i) != null) {
								String datestr = valueList.get(i).toString();
								diyTitle.setStartDate(sf.parse(datestr));
							} else {
								diyTitle.setStartDate(new Date());
							}
						} else if (!"days".equals(keys[i])) {
							PropertyUtils.setProperty(diyTitle, keys[i], valueList.get(i));
						}
					}
					for (int i = 0; i < keys.length; i++) {
						if ("days".equals(keys[i])) {
							setTitleEndDate(diyTitle, keys[i], valueList.get(i));
						}
					}
					IPlayerDIYTitleService playerDIYTitleService = ServiceManager.getManager().getPlayerDIYTitleService();
					// 如果玩家原来就拥有这个称号则使它过期，保存新称号
					PlayerDIYTitle playerDIYTitle = playerDIYTitleService.getTitleByName(diyTitle.getPlayerId(), diyTitle.getTitle());
					if (playerDIYTitle != null) {
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.MINUTE, -1);
						playerDIYTitle.setEndDate(cal.getTime());
						playerDIYTitleService.update(playerDIYTitle);
					}
					playerDIYTitleService.save(diyTitle);
					break;
				case 36 : // 新增付费包奖励 TODO 此数据服务端开发人员未将它存到内存中去
					PayAppReward appReward = new PayAppReward();
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
					ServiceManager.getManager().getRechargeRewardService().save(appReward);
					if (!mailContent.equals(null) && !mailTitle.equals(null)) {
						ServiceManager.getManager().getPayAppRewardService().updateMail(mailTitle, mailContent);
					}
					break;
				case 37 :// button信息
					ButtonInfo buttonInfo = new ButtonInfo();
					buttonInfo.setAreaId(WorldServer.config.getAreaId());
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(buttonInfo, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getOperationConfigService().save(buttonInfo);
					List<WorldPlayer> playerList = new ArrayList<WorldPlayer>(ServiceManager.getManager().getPlayerService().getAllPlayer());
					for (WorldPlayer worldPlayer : playerList) {
						if (worldPlayer.getButtonInfo() != null) {
							worldPlayer.setButtonInfo(null);
						}
					}
					break;
				case 39 :// 新增推送列表
					LocalPushList localPushList = new LocalPushList();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(localPushList, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getLocalPushListService().saveLocalPush(localPushList);
					ServiceManager.getManager().getLocalPushListService().updateVersion(-1);
					ServiceManager.getManager().getLocalPushListService().initPushData();
					// new GetPushListHandler().notifyOnliePlayer();

					break;
				case 40 :// 新增分包奖励
					DownloadReward downloadReward = new DownloadReward();
					for (int i = 0; i < keys.length; i++) {
						PropertyUtils.setProperty(downloadReward, keys[i], valueList.get(i));
					}
					ServiceManager.getManager().getDownloadRewardService().save(downloadReward);
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			addResult.setSuccess(false);
		}
		session.write(addResult);
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