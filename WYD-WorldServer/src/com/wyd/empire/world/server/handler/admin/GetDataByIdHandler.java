package com.wyd.empire.world.server.handler.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.hibernate.Hibernate;

import net.sf.json.JSONObject;

import com.wyd.empire.protocol.data.admin.GetDataById;
import com.wyd.empire.protocol.data.admin.GetDataByIdResult;
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
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.bean.PlayerDIYTitle;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.bean.RechargeCrit;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.bean.RewardItems;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.bean.Successrate;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.bean.Tips;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bean.VipReward;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.bean.WishItem;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 根据ID查询
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class GetDataByIdHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		GetDataByIdResult getDataByIdResult = new GetDataByIdResult(data.getSessionId(), data.getSerial());
		try {
			GetDataById getDataById = (GetDataById) data;
			int id = getDataById.getId();
			switch (getDataById.getSelectType()) {
				case 2 :
					Bulletin bulletin = ServiceManager.getManager().getBulletinService().findById(id);
					JSONObject bulletinObj = JSONObject.fromObject(bulletin);
					getDataByIdResult.setContent(bulletinObj.toString());
					break;
				case 4 :
					Mail mail = (Mail) ServiceManager.getManager().getMailService().getMail(id);
					if (mail != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(mail).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 5 :
					Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(id);
					if (task != null) {
						JSONObject taskObj = JSONObject.fromObject(task);
						getDataByIdResult.setContent(taskObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 6 :
					PlayerItemsFromShop playerItemsFromShop = (PlayerItemsFromShop) ServiceManager.getManager()
							.getPlayerItemsFromShopService().get(PlayerItemsFromShop.class, id);
					if (playerItemsFromShop != null) {
						WorldPlayer player = ServiceManager.getManager().getPlayerService()
								.getWorldPlayerById(playerItemsFromShop.getPlayerId());
						Properties properties = new Properties();
						properties.put("id", playerItemsFromShop.getId());
						properties.put("shopItemId", playerItemsFromShop.getShopItem().getId());
						properties.put("playerId", player.getId());
						properties.put("playerName", player.getName());
						properties.put("shopItemName", playerItemsFromShop.getShopItem().getName());
						properties.put("buyTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(playerItemsFromShop.getBuyTime()));
						properties.put("lastTime", playerItemsFromShop.getPLastTime());
						properties.put("lastNum", playerItemsFromShop.getPLastNum());
						properties.put("addHP", playerItemsFromShop.getPAddHp());
						properties.put("addPower", 0);
						properties.put("addAttack", playerItemsFromShop.getPAddAttack());
						properties.put("addDefend", playerItemsFromShop.getPAddDefend());
						properties.put("attackArea", 0);
						properties.put("isInUsed", playerItemsFromShop.getIsInUsed());
						properties.put("skillful", playerItemsFromShop.getSkillful());
						properties.put("dispearAtOverTime", playerItemsFromShop.getDispearAtOverTime());
						properties.put("hollNum", playerItemsFromShop.getHollNum());
						properties.put("hollUsedNum", playerItemsFromShop.getHollUsedNum());
						properties.put("criticalCoefficient", 0);
						properties.put("expExtraRate", playerItemsFromShop.getPExpExtraRate());
						properties.put("useLastTime", playerItemsFromShop.getPUseLastTime());
						properties.put("strongLevel", playerItemsFromShop.getStrongLevel());
						properties.put("weapSkill1", playerItemsFromShop.getWeapSkill1());
						properties.put("weapSkill2", playerItemsFromShop.getWeapSkill2());
						properties.put("stars", playerItemsFromShop.getStars());
						JSONObject playerItemsFromShopObj = JSONObject.fromObject(properties);
						getDataByIdResult.setContent(playerItemsFromShopObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 7 :
					Guai guai = (Guai) ServiceManager.getManager().getTaskService().getService().get(Guai.class, id);
					if (guai != null) {
						JSONObject guaiObj = JSONObject.fromObject(guai);
						getDataByIdResult.setContent(guaiObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 8 :
					BossmapReward bossmapReward = (BossmapReward) ServiceManager.getManager().getBossmapRewardService()
							.get(BossmapReward.class, id);
					if (bossmapReward != null) {
						JSONObject bossmapRewardObj = JSONObject.fromObject(bossmapReward);
						getDataByIdResult.setContent(bossmapRewardObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 9 :
					Map map = (Map) ServiceManager.getManager().getMapsService().getService().get(Map.class, id);
					if (map != null) {
						JSONObject mapObj = JSONObject.fromObject(map);
						getDataByIdResult.setContent(mapObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 10 :
					Magnification magnification = (Magnification) ServiceManager.getManager().getMagnificationService()
							.get(Magnification.class, id);
					if (magnification != null) {
						Properties properties = new Properties();
						properties.put("id", magnification.getId());
						properties.put("shopId", magnification.getShopId());
						properties.put("discount", magnification.getDiscount());
						properties.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(magnification.getStartTime()));
						properties.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(magnification.getEndTime()));
						getDataByIdResult.setContent(JSONObject.fromObject(properties).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 11 :
					ActivitiesAward activitiesAward = (ActivitiesAward) ServiceManager.getManager().getMagnificationService()
							.get(ActivitiesAward.class, id);
					if (activitiesAward != null) {
						JSONObject magnificationObj = JSONObject.fromObject(activitiesAward);
						getDataByIdResult.setContent(magnificationObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 12 :
					LoginReward loginReward = (LoginReward) ServiceManager.getManager().getIPlayerService().get(LoginReward.class, id);
					if (loginReward != null) {
						JSONObject loginRewardObj = JSONObject.fromObject(loginReward);
						getDataByIdResult.setContent(loginRewardObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 13 :
					InviteReward inviteReward = (InviteReward) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(InviteReward.class, id);
					if (inviteReward != null) {
						JSONObject inviteRewardObj = JSONObject.fromObject(inviteReward);
						getDataByIdResult.setContent(inviteRewardObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 14 :
					InviteServiceInfo inviteServiceInfo = (InviteServiceInfo) ServiceManager.getManager().getInviteService()
							.getIInviteService().get(InviteServiceInfo.class, id);
					if (inviteServiceInfo != null) {
						JSONObject inviteServiceInfoObj = JSONObject.fromObject(inviteServiceInfo);
						getDataByIdResult.setContent(inviteServiceInfoObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 15 :
					Reward signReward = (Reward) ServiceManager.getManager().getShopItemService().get(Reward.class, id);
					if (signReward != null) {
						JSONObject signRewardObj = JSONObject.fromObject(signReward);
						getDataByIdResult.setContent(signRewardObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 16 :
					Title title = ServiceManager.getManager().getPlayerTaskTitleService().getTitle(id);
					if (title != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(title).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 17 :
					LotteryReward lotteryReward = ServiceManager.getManager().getRechargeRewardService().findLotteryRewardById(id);
					if (lotteryReward != null) {
						Properties properties = new Properties();
						properties.put("id", lotteryReward.getId());
						properties.put("shopId", lotteryReward.getShopItem().getId());
						properties.put("shopName", lotteryReward.getShopItem().getName());
						properties.put("days", lotteryReward.getDays());
						properties.put("count", lotteryReward.getCount());
						properties.put("startChance", lotteryReward.getStartChance());
						properties.put("endChance", lotteryReward.getEndChance());
						properties.put("chance", lotteryReward.getChance());
						properties.put("strongLevel", lotteryReward.getStrongLevel());
						getDataByIdResult.setContent(JSONObject.fromObject(properties).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 18 : // 根据游戏加载提示ID查找详细信息
					Tips tips = (Tips) ServiceManager.getManager().getInviteService().getIInviteService().get(Tips.class, id);
					if (tips != null) {
						JSONObject consortiaSkillObj = JSONObject.fromObject(tips);
						getDataByIdResult.setContent(consortiaSkillObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 19 : // 公会技能
					ConsortiaSkill consortiaSkill = (ConsortiaSkill) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(ConsortiaSkill.class, id);
					if (consortiaSkill != null) {
						JSONObject consortiaSkillObj = JSONObject.fromObject(consortiaSkill);
						getDataByIdResult.setContent(consortiaSkillObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 20 :// 根据id查询爱心许愿抽取奖励
					WishItem wishItem = ServiceManager.getManager().getLotteryService().getById(id);
					if (wishItem != null) {
						Properties properties = new Properties();
						properties.put("id", wishItem.getId());
						properties.put("rate", wishItem.getRate());
						properties.put("reward", wishItem.getReward());
						properties.put("reward_rate", wishItem.getRewardRate());
						properties.put("type", wishItem.getType());
						getDataByIdResult.setContent(JSONObject.fromObject(properties).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 21 :// 首充奖励
					RechargeReward rewardItems = ServiceManager.getManager().getRechargeRewardService().getRechargeRewardById(id);
					if (rewardItems != null) {
						Properties properties = new Properties();
						properties.put("id", rewardItems.getId());
						properties.put("shopId", rewardItems.getShopItem().getId());
						properties.put("shopName", rewardItems.getShopItem().getName());
						properties.put("days", rewardItems.getDays());
						properties.put("count", rewardItems.getCount());
						properties.put("strongLevel", rewardItems.getStrongLevel());
						properties.put("type", rewardItems.getType());
						getDataByIdResult.setContent(JSONObject.fromObject(properties).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 22 : // 根据id查询武器被动技能表
					WeapSkill weapSkill = (WeapSkill) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(WeapSkill.class, id);
					if (weapSkill != null) {
						JSONObject weapSkillObj = JSONObject.fromObject(weapSkill);
						getDataByIdResult.setContent(weapSkillObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 23 : // 根据id查询强化概率
					Successrate successrate = (Successrate) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(Successrate.class, id);
					if (successrate != null) {
						JSONObject successrateObj = JSONObject.fromObject(successrate);
						getDataByIdResult.setContent(successrateObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 24 : // 查询道具
					Tools tools = (Tools) ServiceManager.getManager().getInviteService().getIInviteService().get(Tools.class, id);
					if (tools != null) {
						JSONObject toolsObj = JSONObject.fromObject(tools);
						getDataByIdResult.setContent(toolsObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 25 : // 通用物品奖励
					RewardItems rewardItems1 = ServiceManager.getManager().getRewardItemsService().getRewardItemsById(id);
					if (rewardItems1 != null) {
						Properties properties = new Properties();
						properties.put("id", rewardItems1.getId());
						properties.put("shopId", rewardItems1.getShopItem().getId());
						properties.put("shopName", rewardItems1.getShopItem().getName());
						properties.put("days", rewardItems1.getDays());
						properties.put("count", rewardItems1.getCount());
						properties.put("end", rewardItems1.getEnd());
						properties.put("itemName", rewardItems1.getItemName());
						properties.put("probability", rewardItems1.getProbability());
						properties.put("start", rewardItems1.getStart());
						getDataByIdResult.setContent(JSONObject.fromObject(properties).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 26 : // 会员奖励
					VipReward vipReward = (VipReward) ServiceManager.getManager().getInviteService().getIInviteService()
							.get(VipReward.class, id);
					if (vipReward != null) {
						JSONObject vipRewardObj = JSONObject.fromObject(vipReward);
						getDataByIdResult.setContent(vipRewardObj.toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 27 : // 全服奖励
					FullServiceReward fullServiceReward = (FullServiceReward) ServiceManager.getManager().getRewardItemsService()
							.get(FullServiceReward.class, id);
					if (fullServiceReward != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(fullServiceReward).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 31 :
					// Consortia consortia =
					// ServiceManager.getManager().getConsortiaService().getConsortiaById(id);
					Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService().get(Consortia.class, id);
					if (consortia != null) {
						Hibernate.isInitialized(consortia.getPresident());
						// Hibernate.isInitialized(consortia.getPlayerSinConsortias());
						Properties con = new Properties();
						con.put("id", consortia.getId());
						con.put("name", consortia.getName());
						con.put("presidentId", consortia.getPresident().getId());
						con.put("level", consortia.getLevel());
						con.put("totalMember", consortia.getTotalMember());
						con.put("hosId", consortia.getHosId());
						getDataByIdResult.setContent(JSONObject.fromObject(con).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 32 :
					getDataByIdResult.setContent(JSONObject.fromObject(
							ServiceManager.getManager().getOperationConfigService().getCurAreaIdAndName()).toString());
					break;
				case 34 :
					DailyActivity dailyActivity = (DailyActivity) ServiceManager.getManager().getIDailyActivityService()
							.get(DailyActivity.class, id);
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					Properties p = new Properties();
					p.put("id", dailyActivity.getId());
					p.put("name", dailyActivity.getName());
					p.put("startTime", sdf.format(dailyActivity.getStartTime()));
					p.put("endTime", sdf.format(dailyActivity.getEndTime()));
					p.put("daysOfWeek", dailyActivity.getDaysOfWeek());
					p.put("description", dailyActivity.getDescription());
					p.put("awardCondition", dailyActivity.getAwardCondition());
					p.put("rewardsAdd", dailyActivity.getRewardsAdd());
					p.put("rewardRateAdd", dailyActivity.getRewardRateAdd());
					p.put("rewardsSub", dailyActivity.getRewardsSub());
					p.put("rewardRateSub", dailyActivity.getRewardRateSub());
					if (dailyActivity.getRewardItems() != null) {
						p.put("rewardItems", dailyActivity.getRewardItems());
					} else {
						p.put("rewardItems", "");
					}
					if (dailyActivity.getMailTitle() != null) {
						p.put("mailTitle", dailyActivity.getMailTitle());
					} else {
						p.put("mailTitle", "");
					}
					if (dailyActivity.getMailContent() != null) {
						p.put("mailContent", dailyActivity.getMailContent());
					} else {
						p.put("mailContent", "");
					}
					p.put("status", dailyActivity.getStatus());
					getDataByIdResult.setContent(JSONObject.fromObject(p).toString());
					break;
				case 36 :
					if (id > 0) {
						PlayerDIYTitle title1 = (PlayerDIYTitle) ServiceManager.getManager().getPlayerDIYTitleService()
								.get(PlayerDIYTitle.class, id);
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Properties playerTitle = new Properties();
						playerTitle.put("titleId", title1.getId());
						playerTitle.put("playerId", title1.getPlayerId());
						playerTitle.put("title", title1.getTitle());
						playerTitle.put("icon", title1.getIcon());
						playerTitle.put("startDate", sf.format(title1.getStartDate()));
						playerTitle.put("days", daysLeft(title1.getEndDate()));
						playerTitle.put("titleDesc", title1.getTitleDesc());
						getDataByIdResult.setContent(JSONObject.fromObject(playerTitle).toString());
					}
					break;
				case 39 : // 根据id查询付费包奖励 TODO 此数据服务端开发人员未将它存到内存中去
					PayAppReward appReward = ServiceManager.getManager().getPayAppRewardService().findRewardById(id);
					if (appReward != null) {
						Properties properties = new Properties();
						properties.put("id", appReward.getId());
						properties.put("shopId", appReward.getShopItem().getId());
						properties.put("shopName", appReward.getShopItem().getName());
						properties.put("days", appReward.getDays());
						properties.put("count", appReward.getCount());
						properties.put("mailTitle", appReward.getMailTitle());
						properties.put("mailContent", appReward.getMailContent());
						properties.put("strongLevel", appReward.getStrongLevel());
						getDataByIdResult.setContent(JSONObject.fromObject(properties).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 40 : // 根据id查询button信息
					ButtonInfo buttonInfo = (ButtonInfo) ServiceManager.getManager().getOperationConfigService().get(ButtonInfo.class, id);
					if (buttonInfo != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(buttonInfo).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 42 : // 根据id查询充值暴击概率信息
					RechargeCrit rechargeCrit = (RechargeCrit) ServiceManager.getManager().getRechargeService().get(RechargeCrit.class, id);
					if (rechargeCrit != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(rechargeCrit).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 43 : // 根据id查询玩家自定义头像等信息
					PlayerPicture playerPicture = (PlayerPicture) ServiceManager.getManager().getPictureUploadService()
							.get(PlayerPicture.class, id);
					if (playerPicture != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(playerPicture).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 44 : // 根据id查询本地推送信息
					LocalPushList localPushList = (LocalPushList) ServiceManager.getManager().getLocalPushListService()
							.findLocalPushById(id);
					if (localPushList != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(localPushList).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 45 : // 根据id查询充值信息
					Recharge recharge = (Recharge) ServiceManager.getManager().getRechargeService().get(Recharge.class, id);
					if (recharge != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(recharge).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				case 47 : // 根据id查询充值信息
					DownloadReward downloadReward = (DownloadReward) ServiceManager.getManager().getDownloadRewardService()
							.get(DownloadReward.class, id);
					if (downloadReward != null) {
						getDataByIdResult.setContent(JSONObject.fromObject(downloadReward).toString());
					} else {
						getDataByIdResult.setContent("");
					}
					break;
				default :
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			getDataByIdResult.setContent("");
		}
		session.write(getDataByIdResult);
	}

	private int daysLeft(Date endDate) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		int maxDay = cal.getMaximum(Calendar.DAY_OF_YEAR);
		cal.setTime(endDate);
		int endYear = cal.get(Calendar.YEAR);
		int endDay = cal.get(Calendar.DAY_OF_YEAR);
		if (year == endYear) {
			return endDay - day;
		} else if (endYear > year) {
			day += 1;
			return maxDay - day + endDay;
		}
		return -1;
	}

}