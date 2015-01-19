package com.wyd.empire.world.server.handler.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

import com.wyd.db.page.PageList;
import com.wyd.empire.protocol.data.admin.GetData;
import com.wyd.empire.protocol.data.admin.GetDataResult;
import com.wyd.empire.world.bean.BillingPoint;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.DailyActivity;
import com.wyd.empire.world.bean.DownloadReward;
import com.wyd.empire.world.bean.FullServiceReward;
import com.wyd.empire.world.bean.LocalPushList;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.Magnification;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerBill;
import com.wyd.empire.world.bean.PlayerDIYTitle;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.bean.Promotions;
import com.wyd.empire.world.bean.Push;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.bean.RewardItems;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.bean.VipReward;
import com.wyd.empire.world.bean.WishItem;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.KeyProcessService;
import com.wyd.empire.world.common.util.PageListUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.StrValueUtils;
import com.wyd.empire.world.common.util.VersionUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.empire.world.task.PlayerTaskInfo;
import com.wyd.empire.world.title.PlayerTitleInfo;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * gm工具数据查询
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class GetDataHandler implements IDataHandler {
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		GetData getData = (GetData) data;
		GetDataResult getDataResult = new GetDataResult(data.getSessionId(), data.getSerial());
		int selectType = getData.getSelectType();
		String key = getData.getKey();
		int pageIndex = getData.getPageIndex();
		int pageSize = getData.getPageCount();
		getDataResult.setSelectType(selectType);
		getDataResult.setKey(key);
		getDataResult.setPageIndex(pageIndex);
		getDataResult.setPageCount(pageSize);
		PageList pageList;
		switch (selectType) {
			case 0 :
				String[] keys = key.split("\\|");
				List dataList = new ArrayList();
				if (keys.length == 5 && StringUtils.hasText(keys[4])) {
					if (("0").equals(keys[4])) {
					} else {
						Collection<WorldPlayer> worldPlayerList = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
						if (worldPlayerList != null && worldPlayerList.size() > 0) {
							for (WorldPlayer player : worldPlayerList) {
								Properties p = new Properties();
								p.put("id", player.getId());
								p.put("name", player.getPlayer().getName());
								p.put("sex", player.getPlayer().getSex());
								p.put("level", player.getPlayer().getLevel());
								p.put("moneyGold", player.getPlayer().getMoneyGold());
								p.put("amount", player.getDiamond());
								p.put("exp", player.getPlayer().getExp());
								if (player.getPlayer().getBeTime() != null
										&& DateUtil.compareDateOnSecond(new Date(), player.getPlayer().getBeTime()) > 0) {
									p.put("status", 1);
								} else {
									p.put("status", player.getPlayer().getStatus());
								}
								p.put("hp", player.getPlayer().getHp());
								p.put("attack", player.getPlayer().getAttack());
								p.put("defend", player.getPlayer().getDefend());
								p.put("defense", player.getPlayer().getDefense());
								p.put("bossmapProgress", player.getPlayer().getBossmapProgress());
								p.put("winTimes1v1Athletics", player.getPlayer().getWinTimes1v1Athletics());
								p.put("playTimes1v1Athletics", player.getPlayer().getPlayTimes1v1Athletics());
								p.put("isOnline", true);
								p.put("vipLevel", player.getPlayer().getVipLevel() == null ? 0 : player.getPlayer().getVipLevel());
								p.put("guildName", player.getGuildName());
								p.put("guildId", player.getGuildId());
								p.put("vitality", player.getVigor());
								dataList.add(p);
							}
							if (dataList.size() > 0) {
								getDataResult.setDataCount(dataList.size());
								PageListUtil<Properties> values = new PageListUtil<Properties>();
								pageIndex = pageIndex + 1;
								List<Properties> renVal = values.getList(pageIndex, pageSize, dataList);
								ServiceManager.getManager().getMarryService().getSpouseId(renVal);
								JSONArray playerArray = JSONArray.fromObject(renVal);
								getDataResult.setContent(playerArray.toString());
							} else {
								getDataResult.setDataCount(0);
								getDataResult.setContent("");
							}
						} else {
							getDataResult.setDataCount(0);
							getDataResult.setContent("");
						}
						worldPlayerList = null;
					}
				} else {
					pageList = ServiceManager.getManager().getPlayerService().getService().getPlayerByKey(key, pageIndex, pageSize);
					getDataResult.setDataCount(pageList.getFullListSize());
					if (null != pageList && pageList.getList().size() > 0) {
						for (Object obj : pageList.getList()) {
							Player player = (Player) obj;
							boolean isOnline = false;
							WorldPlayer wplayer = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(player.getId());
							if (null != wplayer) {
								isOnline = true;
							}
							Properties p = new Properties();
							p.put("id", player.getId());
							p.put("name", player.getName());
							p.put("sex", player.getSex());
							p.put("level", player.getLevel());
							p.put("moneyGold", player.getMoneyGold());
							p.put("amount", player.getAmount());
							p.put("exp", player.getExp());
							if (player.getBeTime() != null && DateUtil.compareDateOnSecond(new Date(), player.getBeTime()) > 0) {
								p.put("status", 1);
							} else {
								p.put("status", player.getStatus());
							}
							p.put("hp", player.getHp());
							p.put("attack", player.getAttack());
							p.put("defend", player.getDefend());
							p.put("defense", player.getDefense());
							p.put("bossmapProgress", player.getBossmapProgress());
							p.put("winTimes1v1Athletics", player.getWinTimes1v1Athletics());
							p.put("playTimes1v1Athletics", player.getPlayTimes1v1Athletics());
							p.put("isOnline", isOnline);
							p.put("vipLevel", player.getVipLevel() == null ? 0 : player.getVipLevel());
							Object[] v = ServiceManager.getManager().getConsortiaService().getConsortiaNameAndIdByPlayerId(player.getId());
							p.put("guildName", v[0]);
							p.put("guildId", v[1]);
							p.put("vitality", isOnline == true ? wplayer.getVigor() : 0);
							dataList.add(p);
						}
						ServiceManager.getManager().getMarryService().getSpouseId(dataList);
						JSONArray jsonArray = JSONArray.fromObject(dataList);
						getDataResult.setContent(jsonArray.toString());
					} else {
						getDataResult.setContent("");
					}
				}
				break;
			case 1 :
				pageList = ServiceManager.getManager().getPlayerService().getService().getBannedPlayer(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray jsonArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(jsonArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 2 :
				pageList = ServiceManager.getManager().getPlayerService().getService().getGagPlayers(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray jsonArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(jsonArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 3 :
				pageList = ServiceManager.getManager().getShopItemService().getItemList(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray jsonArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(jsonArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 4 :
				List<Bulletin> bulletinList = ServiceManager.getManager().getBulletinService().getBulletinList();
				if (null != bulletinList && bulletinList.size() > 0) {
					JSONArray jsonArray = JSONArray.fromObject(bulletinList);
					getDataResult.setContent(jsonArray.toString());
				}
				break;
			case 5 :
				String[] dates = key.split("\\|");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < dates.length; i++) {
					switch (i) {
						case 0 :
							sb.append(dates[0]).append("|").append(dates[1]).append("|");
							break;
						case 2 :
							if (ServiceUtils.isNumeric(dates[2])) {
								sb.append(dates[2]);
							} else {
								Player player = ServiceManager.getManager().getIPlayerService().getPlayerByName(dates[2], true);
								if (player != null) {
									sb.append(player.getId());
								}
							}
							sb.append("|");
							break;
						case 3 :
							sb.append(dates[3]).append("|");
							break;
						case 4 :
							sb.append(dates[4]).append("|");
							break;
						case 5 :
							sb.append(dates[5]).append("|");
							break;
						case 6 :
							sb.append(dates[6]);
							break;
						default :
							break;
					}
				}
				pageList = ServiceManager.getManager().getMailService().getSuggestionBox(sb.toString(), pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					List<Properties> mailList = new ArrayList<Properties>();
					for (Object obj : pageList.getList()) {
						Mail mail = (Mail) obj;
						Properties p = new Properties();
						p.put("id", mail.getId());
						p.put("sendId", mail.getSendId());
						if (0 != mail.getSendId()) {
							Player sended = (Player) ServiceManager.getManager().getIPlayerService().get(Player.class, mail.getSendId());
							if (sended != null) {
								p.put("sendName", sended.getName());
								p.put("serverId",
										ServiceManager.getManager().getConfiguration().getString("areaid").split("_")[0]
												+ sended.getAreaId() == null ? "" : sended.getAreaId());
							} else {
								p.put("sendName", "");
								p.put("serverId", "");
							}
						} else {
							p.put("sendName", "");
							p.put("serverId", "");
						}
						p.put("receivedId", mail.getReceivedId());
						Player received = (Player) ServiceManager.getManager().getIPlayerService().get(Player.class, mail.getReceivedId());
						if (received != null) {
							p.put("receivedName", received.getName());
						} else {
							p.put("receivedName", "");
						}
						p.put("theme", mail.getTheme());
						p.put("content", mail.getContent());
						p.put("type", mail.getType());
						p.put("sendTime", mail.getSendTime());
						p.put("isRead", mail.getIsRead());
						p.put("blackMail", mail.getBlackMail());
						p.put("remark", mail.getRemark() == null ? "" : mail.getRemark());
						p.put("isHandle", mail.getIsHandle() == null ? "" : mail.getIsHandle());
						p.put("isStick", mail.getIsStick());
						mailList.add(p);
					}
					JSONArray jsonArray5 = JSONArray.fromObject(mailList);
					getDataResult.setContent(jsonArray5.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 6 :
				String[] dates1 = key.split("\\|");
				StringBuilder sb1 = new StringBuilder();
				for (int i = 0; i < dates1.length; i++) {
					switch (i) {
						case 0 :
							sb1.append(dates1[0]).append("|").append(dates1[1]).append("|");
							break;
						case 2 :
							if (ServiceUtils.isNumeric(dates1[2])) {
								sb1.append(dates1[2]);
							} else {
								Player player = ServiceManager.getManager().getIPlayerService().getPlayerByName(dates1[2], true);
								if (player != null) {
									sb1.append(player.getId());
								}
							}
							sb1.append("|");
							break;
						case 3 :
							sb1.append(dates1[3]).append("|");
							break;
						case 4 :
							sb1.append(dates1[4]);
					}
				}
				pageList = ServiceManager.getManager().getMailService().getGMMail(sb1.toString(), pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					List<Properties> mailList = new ArrayList<Properties>();
					for (Object obj : pageList.getList()) {
						Mail mail = (Mail) obj;
						Properties p = new Properties();
						p.put("id", mail.getId());
						p.put("receivedId", mail.getReceivedId());
						Player received = (Player) ServiceManager.getManager().getIPlayerService().get(Player.class, mail.getReceivedId());
						if (received != null) {
							p.put("receivedName", received.getName());
							p.put("serverId",
									ServiceManager.getManager().getConfiguration().getString("areaid").split("_")[0] + received.getAreaId() == null
											? ""
											: received.getAreaId());
						} else {
							p.put("receivedName", "");
							p.put("serverId", "");
						}
						p.put("sendId", mail.getSendId());
						if (0 != mail.getSendId()) {
							Player sended = (Player) ServiceManager.getManager().getIPlayerService().get(Player.class, mail.getSendId());
							if (sended != null) {
								p.put("sendName", sended.getName());
								if (!StringUtils.hasText(p.getProperty("serverId"))) {
									p.put("serverId", ServiceManager.getManager().getConfiguration().getString("areaid").split("_")[0]
											+ sended.getAreaId() == null ? "" : sended.getAreaId());
								}
							} else {
								p.put("sendName", "");
							}
						} else {
							p.put("sendName", "");
						}
						p.put("theme", mail.getTheme());
						p.put("content", mail.getContent() == null ? "" : mail.getContent());
						p.put("type", mail.getType());
						p.put("sendTime", mail.getSendTime());
						p.put("isRead", mail.getIsRead());
						p.put("blackMail", mail.getBlackMail());
						p.put("remark", mail.getRemark() == null ? "" : mail.getRemark());
						p.put("isHandle", mail.getIsHandle() == null ? "" : mail.getIsHandle());
						p.put("isStick", mail.getIsStick());
						mailList.add(p);
					}
					JSONArray jsonArray6 = JSONArray.fromObject(mailList);
					getDataResult.setContent(jsonArray6.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 7 :
				List<Push> pushList = ServiceManager.getManager().getBulletinService().getService().getAllPush();
				JSONArray jsonArray = JSONArray.fromObject(pushList);
				getDataResult.setContent(jsonArray.toString());
				break;
			case 8 : {
				List<ShopItemsPrice> list = ServiceManager.getManager().getiShopItemsPriceService().getItemPrice(Integer.parseInt(key));
				if (list != null && list.size() > 0) {
					List<Properties> contents = new ArrayList<Properties>();
					for (ShopItemsPrice price : list) {
						Properties p = new Properties();
						p.put("id", price.getId());
						p.put("costType", price.getCostType());
						p.put("costUseGold", price.getCostUseGold());
						p.put("costUseTickets", price.getCostUseTickets());
						p.put("count", price.getCount());
						p.put("days", price.getDays());
						contents.add(p);
					}
					JSONArray array = JSONArray.fromObject(contents);
					getDataResult.setContent(array.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			}
			case 9 : {
				Properties p = new Properties();
				p.put("num", VersionUtils.select("num"));
				p.put("updateurl", VersionUtils.select("updateurl"));
				p.put("remark", VersionUtils.select("remark"));
				p.put("appraisal", VersionUtils.select("appraisal"));
				JSONObject jsonObject = JSONObject.fromObject(p);
				getDataResult.setContent(jsonObject.toString());
				break;
			}
			case 13 :
				OperationConfig config = ServiceManager.getManager().getVersionService().getVersion();
				Properties pConfig = new Properties();
				pConfig.put("about", config.getAbout());
				pConfig.put("notice", config.getNotice());
				pConfig.put("help", config.getHelp());
				// pConfig.put("pushEndTime", config.getPushEndTime());
				// pConfig.put("pushStartTime", config.getPushStartTime());
				pConfig.put("prices", config.getPrices());
				// pConfig.put("firstChargeReward",
				// config.getFirstChargeReward());
				// pConfig.put("moreChargeReward",
				// config.getMoreChargeReward());
				// pConfig.put("discount", config.getDiscount());
				pConfig.put("strengthenLevel", config.getStrengthenLevel());
				// pConfig.put("marryDetail", config.getMarryDetail());
				pConfig.put("dhPrice", config.getDhPrice());
				pConfig.put("jhPrice", config.getJhPrice());
				pConfig.put("marryDiamond", config.getMarryDiamond());
				pConfig.put("reDramond", config.getReDramond());
				pConfig.put("reBadge", config.getReBadge());
				pConfig.put("specialMark", config.getSpecialMark());
				pConfig.put("addition", config.getAddition());
				// pConfig.put("firstChargerewardType",
				// config.getFirstChargeRewardType());
				// pConfig.put("chargeReward", config.getChargeReward());
				// pConfig.put("chargeRewardRemark",
				// config.getChargeRewardRemark());
				pConfig.put("wbPicUrl", config.getWbPicUrl());
				pConfig.put("recallDay", config.getRecallDay());
				pConfig.put("rankreward", config.getRankreward());
				pConfig.put("fundAllocation", config.getFundAllocation());
				// pConfig.put("starsRemark", config.getStarsRemark() == null ?
				// "" :
				// config.getStarsRemark());
				pConfig.put("rebirthRemark", config.getRebirthRemark() == null ? "" : config.getRebirthRemark());
				pConfig.put("shopDiscount", config.getShopDiscount());
				pConfig.put("shopNoDiscountId", config.getShopNoDiscountId() == null ? "" : config.getShopNoDiscountId());
				// pConfig.put("dayTaskNum", config.getDayTaskNum());
				// pConfig.put("noviceRemark", config.getNoviceRemark());
				pConfig.put("noviceType", config.getNoviceType());
				pConfig.put("firstRankReward", config.getFirstRankReward());
				pConfig.put("unitPrice", config.getUnitPrice());
				pConfig.put("blastLevel", config.getBlastLevel());
				pConfig.put("guideLevel", config.getGuideLevel());
				pConfig.put("drawDetail", config.getDrawDetail() == null ? "" : config.getDrawDetail());
				pConfig.put("challengeReward", config.getChallengeReward() == null ? "" : config.getChallengeReward());
				pConfig.put("challengeJson", config.getChallengeJson() == null ? "" : config.getChallengeJson());
				pConfig.put("worldBoss", config.getWorldBoss() == null ? "" : config.getWorldBoss());
				pConfig.put("crossLevel", config.getCrossLevel());
				pConfig.put("moreGame", config.getMoreGame() == null ? "" : config.getMoreGame());
				// pConfig.put("bubbleTips", config.getBubbleTips() == null ? ""
				// :
				// config.getBubbleTips());
				pConfig.put("showTipsTimePeriod", config.getShowTipsTimePeriod() == null ? "" : config.getShowTipsTimePeriod());
				pConfig.put("levelParabolaRange", config.getLevelParabolaRange() == null ? "" : config.getLevelParabolaRange());
				pConfig.put("strenthFlag", config.getStrenthFlag() == null ? "" : config.getStrenthFlag());
				pConfig.put("wedTime", config.getWedTime() == null ? "" : config.getWedTime());
				pConfig.put("wedconfig", config.getWedconfig() == null ? "" : config.getWedconfig()); // 结婚礼堂相关配置
				pConfig.put("expRate", config.getExpRate() == null ? "" : config.getExpRate()); // 战斗场次的经验比例
				pConfig.put("robotSkill", config.getRobotSkill() == null ? "" : config.getRobotSkill()); // 机器人技能使用方案
				pConfig.put("supplPrice", config.getSupplPrice() == null ? "" : config.getSupplPrice()); // 签到补签价格
				pConfig.put("rechargeInterval", config.getRechargeInterval() == null ? "" : config.getRechargeInterval());// 根据充值金额间隔配置每日首充奖励每次
																															// 格式：1#100|2#200|3#300
				pConfig.put("becomeRobot", config.getBecomeRobot());// 玩家掉线后是否变为机器人
				JSONObject pConfigObject = JSONObject.fromObject(pConfig);
				getDataResult.setContent(pConfigObject.toString());
				break;
			case 20 :
				List<Recharge> rechargeList = ServiceManager.getManager().getRechargeService().getAll(Recharge.class);
				JSONArray rechargeArray = JSONArray.fromObject(rechargeList);
				getDataResult.setContent(rechargeArray.toString());
				break;
			case 21 :
				pageList = ServiceManager.getManager().getPlayerTaskTitleService().findAllTask(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray taskArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(taskArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 22 :
				pageList = ServiceManager.getManager().getGuaiService().findAllGuai(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 23 :
				pageList = ServiceManager.getManager().getMapsService().getService().findAllMap(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray mapArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(mapArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 24 :
				pageList = ServiceManager.getManager().getBossmapRewardService().findMapReward(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray mapRewardArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(mapRewardArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 25 :
				pageList = ServiceManager.getManager().getPlayerItemsFromShopService()
						.getPlayerItemsFromShopByPlayerId(Integer.parseInt(key), pageIndex, pageSize);
				List<Object> itemsList = pageList.getList();
				getDataResult.setDataCount(pageList.getFullListSize());
				if (itemsList != null && itemsList.size() > 0) {
					List<Properties> propertiesList = new ArrayList<Properties>();
					PlayerItemsFromShop playerItemsFromShop;
					for (Object obj : itemsList) {
						playerItemsFromShop = (PlayerItemsFromShop) obj;
						Player player = ServiceManager.getManager().getPlayerService().getPlayerById(playerItemsFromShop.getPlayerId());
						Properties properties = new Properties();
						properties.put("id", playerItemsFromShop.getId());
						properties.put("playerId", playerItemsFromShop.getPlayerId());
						properties.put("shopItemId", playerItemsFromShop.getShopItem().getId());
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
						propertiesList.add(properties);
					}
					JSONArray playerArray = JSONArray.fromObject(propertiesList);
					getDataResult.setContent(playerArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 26 :
				List<Magnification> magnificationList = ServiceManager.getManager().getMagnificationService().findAllMagnification();
				if (null != magnificationList && magnificationList.size() > 0) {
					List<Properties> propertiesList = new ArrayList<Properties>();
					for (Magnification magnification : magnificationList) {
						Properties properties = new Properties();
						properties.put("id", magnification.getId());
						properties.put("shopId", magnification.getShopId());
						properties.put("discount", magnification.getDiscount());
						properties.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(magnification.getStartTime()));
						properties.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(magnification.getEndTime()));
						propertiesList.add(properties);
					}
					getDataResult.setContent(JSONArray.fromObject(propertiesList).toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 27 :
				pageList = ServiceManager.getManager().getPlayerBillService().getBillPageList(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					List<Properties> billList = new ArrayList<Properties>();
					for (Object obj : pageList.getList()) {
						PlayerBill playerBill = (PlayerBill) obj;
						Properties properties = new Properties();
						properties.put("id", playerBill.getId());
						properties.put("playerId", playerBill.getPlayerId());
						properties.put("createTime", playerBill.getCreateTime());
						properties.put("amount", playerBill.getAmount());
						properties.put("origin", playerBill.getOrigin());
						properties.put("chargePrice", playerBill.getChargePrice());
						properties.put("remark", playerBill.getRemark());
						properties.put("orderNum", playerBill.getOrderNum() == null ? "" : playerBill.getOrderNum());
						billList.add(properties);
					}
					JSONArray playerBillArray = JSONArray.fromObject(billList);
					getDataResult.setContent(playerBillArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 28 :
				pageList = ServiceManager.getManager().getActivitiesAwardService().findAllActivity(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 29 :
				List promotionsList = new ArrayList();
				pageList = ServiceManager.getManager().getPromotionsService().getIPromotionsService()
						.findAllPromotions(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					for (Object obj : pageList.getList()) {
						Promotions promotions = (Promotions) obj;
						if (null == promotions.getShopItem()) {
							continue;
						}
						Properties promotionsP = new Properties();
						promotionsP.put("id", promotions.getId());
						promotionsP.put("shopId", promotions.getShopItem().getId());
						promotionsP.put("shopName", promotions.getShopItem().getName());
						promotionsP.put("quantity", promotions.getQuantity());
						promotionsP.put("isActivate", promotions.getIsActivate());
						promotionsP.put("discount", promotions.getDiscount());
						promotionsP.put("gold", promotions.getGold());
						promotionsP.put("count", promotions.getCount());
						promotionsP.put("days", promotions.getDays());
						promotionsP.put("personal", promotions.getPersonal());
						promotionsList.add(promotionsP);
					}
					JSONArray promotionsJson = JSONArray.fromObject(promotionsList);
					getDataResult.setContent(promotionsJson.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 30 :
				pageList = ServiceManager.getManager().getIPlayerService().findAllRecalls(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray RecallArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(RecallArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 31 :
				pageList = ServiceManager.getManager().getInviteService().getIInviteService().findAllServerInfo(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray serverInfoArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(serverInfoArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 32 :
				pageList = ServiceManager.getManager().getInviteService().getIInviteService().findAllInviteReward(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray inviteRewardArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(inviteRewardArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 33 :
				pageList = ServiceManager.getManager().getShopItemService().findRecommendList(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray RecallArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(RecallArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 34 :
				List<Reward> signRewardList = ServiceManager.getManager().getShopItemService().getAll(Reward.class);
				if (null != signRewardList && signRewardList.size() > 0) {
					getDataResult.setContent(JSONArray.fromObject(signRewardList).toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 35 : // 获取称号列表
				pageList = ServiceManager.getManager().getPlayerTaskTitleService().findAllTitle(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray json = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(json.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 36 : // 根据条件查询出所有活动奖励日志
				if (key.equals(null) || key.equals(""))
					break;
				pageList = ServiceManager.getManager().getActivitiesAwardService().findLogActivity(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 37 : // 查询配置文件中SIGN_INFO的值
				getDataResult.setContent(StrValueUtils.getInstance().getValueByKey("SIGN_INFO"));
				break;
			case 38 : // 根据条件查询出所有活动奖励日志
				if (key.equals(null) || key.equals(""))
					break;
				getDataResult.setDataCount(ServiceManager.getManager().getActivitiesAwardService().findCountLogActivity(key));
				break;
			case 39 : // 39道具列表
				pageList = ServiceManager.getManager().getToolsService().getToolsList(pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 40 :// 根据条件查询出充值抽奖
				List<LotteryReward> lotteryRewardList = ServiceManager.getManager().getRechargeRewardService().findAllLotteryReward();
				List<Properties> propertiesList = new ArrayList<Properties>();
				for (LotteryReward lotteryReward : lotteryRewardList) {
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
					propertiesList.add(properties);
				}
				JSONArray playerArray = JSONArray.fromObject(propertiesList);
				getDataResult.setContent(playerArray.toString());
				break;
			case 41 : // 获得所有公会技能
				pageList = ServiceManager.getManager().getConsortiaService().getConsortiaListSkill(pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 42 : // 查询游戏加载提示列表
				pageList = ServiceManager.getManager().getRewardItemsService().getTipsList(pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 43 :// 查询爱心许愿抽取奖励表
				List<WishItem> wishItems = ServiceManager.getManager().getLotteryService().getWishItemList();
				List<Properties> wishItemsProperties = new ArrayList<Properties>();
				for (WishItem wishItem : wishItems) {
					Properties properties = new Properties();
					properties.put("id", wishItem.getId());
					properties.put("type", wishItem.getType());
					properties.put("rate", wishItem.getRate());
					properties.put("reward", wishItem.getReward());
					properties.put("reward_rate", wishItem.getRewardRate());
					wishItemsProperties.add(properties);
				}
				JSONArray wishItemsJson = JSONArray.fromObject(wishItemsProperties);
				getDataResult.setContent(wishItemsJson.toString());
				break;
			case 44 :// 44查询首充奖励
				List<Object> objList = ServiceManager.getManager().getRechargeRewardService().getRechargeRewardList(pageIndex, pageSize);
				List<Properties> pptList = new ArrayList<Properties>();
				for (Object obj : objList) {
					RechargeReward rechargeReward = (RechargeReward) obj;
					Properties properties = new Properties();
					properties.put("id", rechargeReward.getId());
					properties.put("shopId", rechargeReward.getShopItem().getId());
					properties.put("shopName", rechargeReward.getShopItem().getName());
					properties.put("days", rechargeReward.getDays());
					properties.put("count", rechargeReward.getCount());
					properties.put("strongLevel", rechargeReward.getStrongLevel());
					properties.put("type", rechargeReward.getType());
					pptList.add(properties);
				}
				JSONArray array = JSONArray.fromObject(pptList);
				getDataResult.setContent(array.toString());
				break;
			case 45 : // 查询武器被动技能列表
				pageList = ServiceManager.getManager().getStrengthenService().getWeapSkillList(pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 46 : // 46查询强化概率列表
				pageList = ServiceManager.getManager().getRechargeRewardService().getSuccessrateList(pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 47 :// 通用物品奖励
				List<RewardItems> rewardItemsList = ServiceManager.getManager().getRewardItemsService().getRewardItemsList();
				List<Properties> rewardItemsPPTList = new ArrayList<Properties>();
				for (RewardItems rewardItems : rewardItemsList) {
					Properties properties = new Properties();
					properties.put("id", rewardItems.getId());
					properties.put("shopId", rewardItems.getShopItem().getId());
					properties.put("shopName", rewardItems.getShopItem().getName());
					properties.put("days", rewardItems.getDays());
					properties.put("count", rewardItems.getCount());
					properties.put("end", rewardItems.getEnd());
					properties.put("itemName", rewardItems.getItemName());
					properties.put("probability", rewardItems.getProbability());
					properties.put("start", rewardItems.getStart());
					rewardItemsPPTList.add(properties);
				}
				JSONArray rewardItemsArray = JSONArray.fromObject(rewardItemsPPTList);
				getDataResult.setContent(rewardItemsArray.toString());
				break;
			case 48 :// 查询会员奖励
				List<VipReward> vipRewardList = ServiceManager.getManager().getRechargeService().getAll(VipReward.class);
				JSONArray vipRewardArray = JSONArray.fromObject(vipRewardList);
				getDataResult.setContent(vipRewardArray.toString());
				break;
			case 49 :// 查询全服奖励
				List<FullServiceReward> fullServiceRewardList = ServiceManager.getManager().getRewardItemsService()
						.findAllFullServiceReward();
				getDataResult.setContent(JSONArray.fromObject(fullServiceRewardList).toString());
				break;
			case 52 :
				List consortiaList = new ArrayList();
				if (StringUtils.hasText(key)) {
					List<Consortia> consortias = ServiceManager.getManager().getConsortiaService().getConsortiaByKey(key);
					if (consortias != null) {
						for (Consortia consortia : consortias) {
							Properties cp = new Properties();
							cp.put("id", consortia.getId());
							cp.put("name", consortia.getName());
							cp.put("presidentId", consortia.getPresident().getId());
							cp.put("totalMember", consortia.getTotalMember());
							cp.put("level", consortia.getLevel());
							cp.put("hosId", consortia.getHosId());
							consortiaList.add(cp);
						}
						getDataResult.setContent(JSONArray.fromObject(consortiaList).toString());
					} else {
						getDataResult.setContent("");
					}
				} else {
					pageIndex = pageIndex + 1;
					PageList pl = ServiceManager.getManager().getConsortiaService().getConsortiaListByPage(pageIndex, pageSize);
					List<Object> result = pl.getList();
					if (result != null && result.size() > 0) {
						for (Object obj : result) {
							Consortia consortia = (Consortia) obj;
							Properties cp = new Properties();
							cp.put("id", consortia.getId());
							cp.put("name", consortia.getName());
							cp.put("presidentId", consortia.getPresident().getId());
							cp.put("totalMember", consortia.getTotalMember());
							cp.put("level", consortia.getLevel());
							cp.put("hosId", consortia.getHosId());
							consortiaList.add(cp);
						}
						if (consortiaList.size() > 0) {
							getDataResult.setDataCount(pl.getFullListSize());
							getDataResult.setContent(JSONArray.fromObject(consortiaList).toString());
						} else {
							getDataResult.setDataCount(0);
							getDataResult.setContent("");
						}
					} else {
						getDataResult.setDataCount(0);
						getDataResult.setContent("");
					}
				}
				break;
			case 53 :
				List consortiaMember = new ArrayList();
				String[] tokens = key.split(Pattern.quote("|"));
				if (tokens.length == 2 && StringUtils.hasText(tokens[1])) {
					PlayerSinConsortia playerSinConsortia = ServiceManager.getManager().getPlayerSinConsortiaService()
							.getCommunityMember(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
					if (playerSinConsortia != null) {
						Player player = playerSinConsortia.getPlayer();
						Properties p = new Properties();
						p.put("id", player.getId());
						p.put("name", player.getName());
						p.put("position", playerSinConsortia.getPosition());
						p.put("identity", playerSinConsortia.getIdentity());
						p.put("contribute", playerSinConsortia.getContribute());
						p.put("discontribute", playerSinConsortia.getDiscontribute());
						consortiaMember.add(p);
						getDataResult.setDataCount(consortiaMember.size());
						getDataResult.setContent(JSONArray.fromObject(consortiaMember).toString());
					} else {
						getDataResult.setDataCount(0);
						getDataResult.setContent("");
					}
				} else {
					PageList psc = ServiceManager.getManager().getPlayerSinConsortiaService()
							.getCommunityMembers(Integer.parseInt(tokens[0]), 1, pageIndex, pageSize);
					List<?> psclist = psc.getList();
					if (!psclist.isEmpty()) {
						for (Object o : psclist) {
							PlayerSinConsortia playerSinConsortia = (PlayerSinConsortia) o;
							Player player = playerSinConsortia.getPlayer();
							Properties p = new Properties();
							p.put("id", player.getId());
							p.put("name", player.getName());
							p.put("position", playerSinConsortia.getPosition());
							p.put("identity", playerSinConsortia.getIdentity());
							p.put("contribute", playerSinConsortia.getContribute());
							p.put("discontribute", playerSinConsortia.getDiscontribute());
							consortiaMember.add(p);
						}
						getDataResult.setDataCount(psc.getFullListSize());
						getDataResult.setContent(JSONArray.fromObject(consortiaMember).toString());
					} else {
						getDataResult.setDataCount(0);
						getDataResult.setContent("");
					}
				}
				break;
			case 54 :
				List<Properties> resultData = new ArrayList<Properties>();
				List<Object[]> list = ServiceManager.getManager().getOperationConfigService().getAllAreaIdAndName();
				for (Object[] obj : list) {
					Properties p = new Properties();
					p.put("areaId", obj[0]);
					if (obj[1] == null) {
						p.put("areaName", obj[0]);
					} else {
						p.put("areaName", obj[1]);
					}
					resultData.add(p);
				}
				getDataResult.setDataCount(resultData.size());
				getDataResult.setContent(JSONArray.fromObject(resultData).toString());
				break;
			case 57 :
				List<PlayerTaskInfo> playerTaskInfoList = ServiceManager.getManager().getTaskService()
						.getPlayerTaskInfoList(Integer.valueOf(key));
				if (playerTaskInfoList != null && !playerTaskInfoList.isEmpty()) {
					getDataResult.setDataCount(playerTaskInfoList.size());
					PageListUtil<PlayerTaskInfo> playerTaskInfoValues = new PageListUtil<PlayerTaskInfo>();
					List<PlayerTaskInfo> taskVal = playerTaskInfoValues.getList(pageIndex, pageSize, playerTaskInfoList);
					JSONArray playerArray2 = JSONArray.fromObject(taskVal);
					getDataResult.setContent(playerArray2.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 58 :
				List<Properties> dailyActivities = new ArrayList<Properties>();
				PageList pl = ServiceManager.getManager().getIDailyActivityService().getDailyActivityByPage(pageIndex, pageSize);
				if (!pl.getList().isEmpty()) {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					for (Object o : pl.getList()) {
						DailyActivity dailyActivity = (DailyActivity) o;
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
						dailyActivities.add(p);
					}
					getDataResult.setDataCount(pl.getFullListSize());
					getDataResult.setContent(JSONArray.fromObject(dailyActivities).toString());
				} else {
					getDataResult.setDataCount(0);
					getDataResult.setContent("");
				}
				break;
			case 61 :
				List<Properties> playerTitleResult = new ArrayList<Properties>();
				List<PlayerDIYTitle> titleList;
				if (StringUtils.hasText(key)) {
					titleList = ServiceManager.getManager().getPlayerDIYTitleService().getDIYTitles(Integer.parseInt(key));
				} else {
					titleList = ServiceManager.getManager().getPlayerDIYTitleService().getDIYTitles();
				}
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				for (PlayerDIYTitle title : titleList) {
					int playerId = title.getPlayerId();
					Player player = ServiceManager.getManager().getPlayerService().getPlayerById(playerId);
					if (player == null) {
						continue;
					}
					String playerName = player.getName();
					Properties playerTitle = new Properties();
					playerTitle.put("titleId", title.getId());
					playerTitle.put("playerName", playerName);
					playerTitle.put("playerId", playerId);
					playerTitle.put("title", title.getTitle());
					playerTitle.put("titleDesc", title.getTitleDesc());
					playerTitle.put("endDate", sf.format(title.getEndDate()));
					playerTitleResult.add(playerTitle);
				}

				getDataResult.setDataCount(playerTitleResult.size());
				getDataResult.setContent(JSONArray.fromObject(playerTitleResult).toString());
				break;
			case 65 :
				List<PlayerTitleInfo> playerTitleInfoList = ServiceManager.getManager().getTitleService()
						.getPlayerTitleInfoList(Integer.valueOf(key));
				if (playerTitleInfoList != null && !playerTitleInfoList.isEmpty()) {
					getDataResult.setDataCount(playerTitleInfoList.size());
					PageListUtil<PlayerTitleInfo> playerTaskInfoValues = new PageListUtil<PlayerTitleInfo>();
					List<PlayerTitleInfo> taskVal = playerTaskInfoValues.getList(pageIndex, pageSize, playerTitleInfoList);
					JSONArray playerArray2 = JSONArray.fromObject(taskVal);
					getDataResult.setContent(playerArray2.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 67 :// 查询付费包奖励 TODO 此数据服务端开发人员未将它存到内存中去
				List appRewardList = new ArrayList();
				pageList = ServiceManager.getManager().getPayAppRewardService().findAllPayAppReward(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					for (Object obj : pageList.getList()) {
						PayAppReward appReward = (PayAppReward) obj;
						Properties properties = new Properties();
						properties.put("id", appReward.getId());
						properties.put("shopId", appReward.getShopItem().getId());
						properties.put("shopName", appReward.getShopItem().getName());
						properties.put("days", appReward.getDays());
						properties.put("count", appReward.getCount());
						properties.put("mailTitle", appReward.getMailTitle());
						properties.put("mailContent", appReward.getMailContent());
						properties.put("strongLevel", appReward.getStrongLevel());
						appRewardList.add(properties);
					}
					JSONArray promotionsJson = JSONArray.fromObject(appRewardList);
					getDataResult.setContent(promotionsJson.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 68 : // 68查询按钮信息列表
				pageList = ServiceManager.getManager().getOperationConfigService().getButtonInfoList(pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray guaiArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(guaiArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 70 : // 70获取充值暴击概率分页列表
				pageList = ServiceManager.getManager().getRechargeService().getRechargeCritList(pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray rechargeCritArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(rechargeCritArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 71 : // 71查询玩家自定义头像等信息列表
				pageList = ServiceManager.getManager().getPictureUploadService().getPicturePageList(key, pageIndex, pageSize);
				getDataResult.setDataCount(pageList.getFullListSize());
				if (null != pageList && pageList.getList().size() > 0) {
					JSONArray pictureArray = JSONArray.fromObject(pageList.getList());
					getDataResult.setContent(pictureArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 72 :// 查询外挂列表
				KeyProcessService service = KeyProcessService.getInstance();
				JSONArray keyProcessArray = JSONArray.fromObject(service.getKeyProcesses());
				getDataResult.setContent(keyProcessArray.toString());
				break;
			case 73 :// 查询本地推送列表
				List<LocalPushList> localPushList = new ArrayList<LocalPushList>();
				localPushList = ServiceManager.getManager().getLocalPushListService().getPushList();
				if (localPushList != null && localPushList.size() > 0) {
					JSONArray localPushArray = JSONArray.fromObject(localPushList);
					getDataResult.setContent(localPushArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 74 :// 查询安卓充值信息
				List<BillingPoint> billingPoints = new ArrayList<BillingPoint>();
				billingPoints = ServiceManager.getManager().getOrderService().getPointList();
				if (billingPoints != null && billingPoints.size() > 0) {
					JSONArray billingPointsArray = JSONArray.fromObject(billingPoints);
					getDataResult.setContent(billingPointsArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;
			case 75 :// 查询分包下载列表
				List<DownloadReward> downloadRewards = new ArrayList<DownloadReward>();
				downloadRewards = ServiceManager.getManager().getDownloadRewardService().getAll(DownloadReward.class);
				if (downloadRewards != null && downloadRewards.size() > 0) {
					JSONArray downloadRewardsArray = JSONArray.fromObject(downloadRewards);
					getDataResult.setContent(downloadRewardsArray.toString());
				} else {
					getDataResult.setContent("");
				}
				break;

		}
		session.write(getDataResult);
	}
}