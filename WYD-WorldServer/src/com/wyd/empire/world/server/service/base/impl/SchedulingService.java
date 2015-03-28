package com.wyd.empire.world.server.service.base.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.ActivitiesAward;
import com.wyd.empire.world.bean.LogActivitiesAward;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.dao.ISchedulingDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.item.DailyActivityVo;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ISchedulingService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

/**
 * The service class for the Activity entity.
 */
public class SchedulingService extends UniversalManagerImpl implements ISchedulingService {
	/** 完成条件--充值 */
	public static final String RECHARGE = "recharge";
	/** 完成条件--充值副类型 */
	public static final String RECHARGE_TYPE = "rechargeType";
	/** 完成条件--充值返钻 */
	public static final String BACK_AMOUNT = "backAmount";
	/** 完成条件--强化 */
	public static final String STRONG = "strong";
	/** 完成条件--商品 */
	public static final String SHOP_ITEM = "shopItem";
	/** 完成条件--商城消费 */
	public static final String SHOP_ITEM_CONSUME = "consume";
	/** 完成条件--性别 */
	public static final String PLAYER_SEX = "sex";
	/** 完成条件--在线时长 */
	public static final String ON_LINE_TIME = "onlineTime";
	/** 完成条件--登录次数 */
	public static final String ON_LINE_NUMBER = "onlineNum";
	/** 完成条件--结婚 */
	public static final String MARRY_RECORD = "marry";
	/** 完成条件--升星 */
	public static final String STARS_INFO = "stars";
	/** 完成条件--充值副类型--单笔充值 */
	public static final int RECHARGE_SUB_TYPE_1 = 1;
	/** 完成条件--充值副类型--每天第一笔充值 */
	public static final int RECHARGE_SUB_TYPE_2 = 2;
	/** 完成条件--充值副类型--充值任意金额 */
	public static final int RECHARGE_SUB_TYPE_3 = 3;
	/** 完成条件--充值副类型--累计充值金额 */
	public static final int RECHARGE_SUB_TYPE_4 = 4;
	Logger log = Logger.getLogger(SchedulingService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private ISchedulingDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "SchedulingService";

	public SchedulingService() {
		super();
	}

	/**
	 * Returns the singleton <code>IActivityService</code> instance.
	 */
	public static ISchedulingService getInstance(ApplicationContext context) {
		return (ISchedulingService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(ISchedulingDao dao) {
		this.dao = dao;
		super.setDao(dao);
	}

	public ISchedulingDao getDao() {
		return this.dao;
	}

	public void sendItemsToPlayer() {
		ServiceUtils.sleepRandomTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":"
				+ "SchedulingService sendItemsToPlayer runing...");
		List<LogActivitiesAward> list = dao.findAllLogAward(WorldServer.config.getAreaId(), Common.LOG_AWARD_STATUS_N);
		WorldPlayer player = null;
		LogActivitiesAward currentLogActivitiesAward = null;
		try {
			if (list != null && list.size() > 0) {
				for (LogActivitiesAward logActivitiesAward : list) {
					currentLogActivitiesAward = logActivitiesAward;
					player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(logActivitiesAward.getPlayerId());
					if (player != null) {
						// 获得奖励列表
						List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(logActivitiesAward.getItemsAward(), player.getPlayer()
								.getSex().intValue());
						for (RewardInfo rewardVo : rewardList) {
							if (rewardVo.getItemId() == Common.EXPID) {
								ServiceManager.getManager().getPlayerService().updatePlayerEXP(player, rewardVo.getCount());
							} else if (rewardVo.getItemId() == Common.GOLDID) {
								ServiceManager.getManager().getPlayerService()
										.updatePlayerGold(player, rewardVo.getCount(), "活动奖励GM", "-- " + " --");
							} else if (rewardVo.getItemId() == Common.DIAMONDID) {
								ServiceManager.getManager().getPlayerService()
										.addTicket(player, rewardVo.getCount(), 0, TradeService.ORIGIN_GM, 0, "", "物品发放", "", "");
							} else {
								if (rewardVo.getCount() > 1000) {
									throw new Exception("物品给予数据异常");
								}
								if (rewardVo.isAddDay()) {
									ServiceManager.getManager().getRechargeRewardService()
											.givenItems(player, -1, rewardVo.getCount(), rewardVo.getItemId(), rewardVo.getLevel(), null);
								} else {
									ServiceManager.getManager().getRechargeRewardService()
											.givenItems(player, rewardVo.getCount(), -1, rewardVo.getItemId(), rewardVo.getLevel(), null);
								}
							}
						}
						// 保存邮件
						Mail mail = new Mail();
						mail.setTheme(logActivitiesAward.getMailTitle());
						mail.setContent(logActivitiesAward.getMailContent());
						mail.setIsRead(false);
						mail.setReceivedId(player.getId());
						mail.setSendId(0);
						mail.setSendName(TipMessages.SYSNAME_MESSAGE);
						mail.setSendTime(new Date());
						mail.setType(1);
						mail.setBlackMail(false);
						mail.setIsStick(Common.IS_STICK);
						ServiceManager.getManager().getMailService().saveMail(mail, null);
					}
					logActivitiesAward.setSendTime(new Date());
					logActivitiesAward.setIsSend(Common.LOG_AWARD_STATUS_Y);
					dao.update(logActivitiesAward);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
			log.info("SchedulingService SendItemsToPlayer Error : 玩家ID--" + player.getId() + "--玩家名称--" + player.getName() + "--活动名称--"
					+ currentLogActivitiesAward.getActivityName() + "--区域--" + currentLogActivitiesAward.getAreaId());
		}
	}

	@SuppressWarnings("unchecked")
	public void saveLogActivitiesAward() {
		ServiceUtils.sleepRandomTime();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":"
				+ "SchedulingService saveLogActivitiesAward runing...");
		List<ActivitiesAward> list = ServiceManager.getManager().getActivitiesAwardService().findAllActivity(WorldServer.config.getAreaId());
		if (list != null && list.size() > 0) {
			StringBuilder sb = new StringBuilder();
			Map<String, String> map = null;
			for (ActivitiesAward activitiesAward : list) {
				// 活动未结束，执行物品奖励发送
				if ((Common.LOG_AWARD_STATUS_N).equals(activitiesAward.getIsSend())) {
					map = formatParam(activitiesAward.getFinishCondition());
					sb.delete(0, sb.length());
					try {
						switch (activitiesAward.getFinishType()) {
							case Common.LOG_AWARD_TYPE_0 :// 充值
								switch (Integer.parseInt(map.get(RECHARGE_TYPE))) {
									case RECHARGE_SUB_TYPE_1 :
										sb.append(" SELECT l.playerId,l.amount,l.id FROM log_playerbill l ,tab_player p WHERE l.playerId=p.id AND p.areaId=? AND l.createTime BETWEEN ? AND ? AND l.origin=? ");
										sb.append(" ");
										sb.append(map.get(RECHARGE));
										List<Object[]> rechargelist = (List<Object[]>) this.getListBySql(sb.toString(),
												new Object[]{ServiceManager.getManager().getConfiguration().getString("machinecode"),
														activitiesAward.getStartTime(), activitiesAward.getEndTime(),
														TradeService.ORIGIN_RECH});
										for (Object[] obj : rechargelist) {
											if (!ServiceManager.getManager().getActivitiesAwardService()
													.isGive(Integer.parseInt(obj[0].toString()), Integer.parseInt(obj[2].toString()))) {
												saveLogActivitiesAward(map.get(BACK_AMOUNT), activitiesAward, obj[0].toString(),
														obj[1].toString(), Integer.parseInt(obj[2].toString()), null);
											}
										}
										break;
									case RECHARGE_SUB_TYPE_2 :
										sb.append(" SELECT l.playerId,l.createTime,l.amount FROM log_playerbill l,tab_player p WHERE l.playerId=p.id AND p.areaId=? AND l.origin=? AND l.createTime BETWEEN ? AND ? ");
										if (!("-1").equals(map.get(RECHARGE))) {
											sb.append(" ");
											sb.append(map.get(RECHARGE));
										}
										sb.append(" ");
										sb.append("GROUP BY l.createTime,l.playerId");
										sb.append(" ");
										sb.append("ORDER BY l.createTime ASC");
										List<Object[]> rechargelist2 = (List<Object[]>) this.getListBySql(sb.toString(), new Object[]{
												ServiceManager.getManager().getConfiguration().getString("machinecode"),
												TradeService.ORIGIN_RECH, activitiesAward.getStartTime(), activitiesAward.getEndTime()});
										for (Object[] obj : rechargelist2) {
											Calendar calendar = Calendar.getInstance();
											calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[1].toString()));
											int month = calendar.get(Calendar.MONTH) + 1;
											String currentTime = calendar.get(Calendar.YEAR) + "-" + month + "-"
													+ calendar.get(Calendar.DAY_OF_MONTH) + "";
											if (!ServiceManager
													.getManager()
													.getActivitiesAwardService()
													.isSend(currentTime, Integer.parseInt(obj[0].toString()),
															activitiesAward.getActivityName())) {
												saveLogActivitiesAward(map.get(BACK_AMOUNT), activitiesAward, obj[0].toString(),
														obj[2].toString(), null, obj[1].toString());
											}
										}
										break;
									case RECHARGE_SUB_TYPE_3 :
										sb.append(" SELECT l.playerId,l.createTime,l.amount FROM log_playerbill l,tab_player p WHERE l.playerId=p.id AND p.areaId=? AND l.createTime BETWEEN ? AND ? AND l.origin=? GROUP BY l.playerId,DATE(l.createTime) ");
										List<Object[]> rechargelist3 = (List<Object[]>) this.getListBySql(sb.toString(),
												new Object[]{ServiceManager.getManager().getConfiguration().getString("machinecode"),
														activitiesAward.getStartTime(), activitiesAward.getEndTime(),
														TradeService.ORIGIN_RECH});
										for (Object[] obj : rechargelist3) {
											Calendar calendar = Calendar.getInstance();
											calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[1].toString()));
											int month = calendar.get(Calendar.MONTH) + 1;
											String currentTime = calendar.get(Calendar.YEAR) + "-" + month + "-"
													+ calendar.get(Calendar.DAY_OF_MONTH) + "";
											if (!ServiceManager
													.getManager()
													.getActivitiesAwardService()
													.isSend(currentTime, Integer.parseInt(obj[0].toString()),
															activitiesAward.getActivityName())) {
												saveLogActivitiesAward(map.get(BACK_AMOUNT), activitiesAward, obj[0].toString(),
														obj[2].toString(), null, obj[1].toString());
											}
										}
										break;
									case RECHARGE_SUB_TYPE_4 :
										if (new Date().getTime() > activitiesAward.getEndTime().getTime()) {// 活动结束时才发送奖励
											sb.append(" SELECT l.playerId,SUM(l.amount) FROM log_playerbill l,tab_player p WHERE l.playerId=p.id AND p.areaId=? AND l.origin=? AND l.createTime BETWEEN ? AND ? GROUP BY l.playerId ");
											sb.append(" ");
											sb.append(map.get(RECHARGE));
											List<Object[]> rechargelist4 = (List<Object[]>) this
													.getListBySql(
															sb.toString(),
															new Object[]{
																	ServiceManager.getManager().getConfiguration().getString("machinecode"),
																	TradeService.ORIGIN_RECH, activitiesAward.getStartTime(),
																	activitiesAward.getEndTime()});
											for (Object[] obj : rechargelist4) {
												saveRechargeAward(map.get(BACK_AMOUNT), activitiesAward, obj[0].toString(),
														obj[1].toString());
											}
										}
										break;
								}
								break;
							case Common.LOG_AWARD_TYPE_1 :// 强化
								if (new Date().getTime() > activitiesAward.getEndTime().getTime()) {// 活动结束时才发送奖励
									sb.append(" SELECT p.id,DATE(l.createTime),l.level FROM log_strongerecord l,tab_player p,tab_shopitems s WHERE l.itemId=s.id AND l.playerId=p.id AND p.areaId=? AND l.type=0 AND l.createTime BETWEEN ? AND ? ");
									sb.append(" ");
									sb.append(map.get(STRONG));
									if (!("-1").equals(map.get(PLAYER_SEX))) {
										sb.append(" ");
										sb.append(map.get(PLAYER_SEX));
									}
									sb.append(" ");
									sb.append(" ORDER BY l.level DESC ");
									List<Object[]> objectlist = (List<Object[]>) this.getListBySql(sb.toString(),
											new Object[]{ServiceManager.getManager().getConfiguration().getString("machinecode"),
													activitiesAward.getStartTime(), activitiesAward.getEndTime()});
									saveLog(activitiesAward, objectlist);
								}
								break;
							case Common.LOG_AWARD_TYPE_2 :// 新品上架
								Date dateTime = new Date();
								List<Object> values = new ArrayList<Object>();
								sb.append(" UPDATE ShopItem SET isOnSale=? WHERE 1=1 ");
								sb.append(" ");
								sb.append(map.get(SHOP_ITEM));
								if (activitiesAward.getStartTime().getTime() < dateTime.getTime()
										&& dateTime.getTime() < activitiesAward.getEndTime().getTime()) {
									values.add(Common.SHOP_ITEM_ON_SALE_YES);
								} else {
									values.add(Common.SHOP_ITEM_ON_SALE_NO);
								}
								dao.execute(sb.toString(), values.toArray());
								break;
							case Common.LOG_AWARD_TYPE_3 :// 在线时长
								if (new Date().getTime() > activitiesAward.getEndTime().getTime()) {// 活动结束时才发送奖励
									sb.append(" SELECT l.playerId,SUM(l.rep) FROM log_playeronline l,tab_player p WHERE l.playerId=p.id AND p.areaId=? AND l.ontime BETWEEN ? and ? ");
									if (!("-1").equals(map.get(PLAYER_SEX))) {
										sb.append(" ");
										sb.append(map.get(PLAYER_SEX));
									}
									sb.append(" ");
									sb.append("GROUP BY l.playerId");
									sb.append(" ");
									sb.append(map.get(ON_LINE_TIME));
									List<Object[]> onlineList = (List<Object[]>) this.getListBySql(sb.toString(),
											new Object[]{ServiceManager.getManager().getConfiguration().getString("machinecode"),
													activitiesAward.getStartTime(), activitiesAward.getEndTime()});
									saveLog(activitiesAward, onlineList);
								}
								break;
							case Common.LOG_AWARD_TYPE_4 :// 商城消费
								if (new Date().getTime() > activitiesAward.getEndTime().getTime()) {// 活动结束时才发送奖励
									sb.append(" SELECT l.playerId,SUM(l.amount) FROM log_playerbill l ,tab_player p WHERE l.playerId=p.id AND p.areaId=? AND l.origin=? AND l.createTime BETWEEN ? AND ? GROUP BY l.playerId ");
									sb.append(" ");
									sb.append(map.get(SHOP_ITEM_CONSUME));
									List<Object[]> shopItemlist = (List<Object[]>) this.getListBySql(sb.toString(), new Object[]{
											ServiceManager.getManager().getConfiguration().getString("machinecode"),
											TradeService.ORIGIN_BUY, activitiesAward.getStartTime(), activitiesAward.getEndTime()});
									for (Object[] obj : shopItemlist) {
										saveRechargeAward(map.get(BACK_AMOUNT), activitiesAward, obj[0].toString(), obj[1].toString()
												.replace("-", ""));
									}
								}
								break;
							case Common.LOG_AWARD_TYPE_5 :// 连续登陆并且在线时长
								if (new Date().getTime() > activitiesAward.getEndTime().getTime()) {// 活动结束时才发送奖励
									if (map.get(ON_LINE_NUMBER) == null || ("-1").equals(map.get(ON_LINE_NUMBER))) {
										break;
									}
									sb.append(" SELECT l.playerId,SUM(l.rep) FROM log_playeronline l,tab_player p WHERE l.playerId=p.id AND p.areaId=? AND l.ontime BETWEEN ? and ? ");
									if (!("-1").equals(map.get(PLAYER_SEX))) {
										sb.append(" ");
										sb.append(map.get(PLAYER_SEX));
									}
									sb.append(" ");
									sb.append("GROUP BY l.playerId");
									sb.append(" ");
									sb.append(map.get(ON_LINE_TIME));
									List<Object[]> onlineList2 = (List<Object[]>) this.getListBySql(sb.toString(),
											new Object[]{ServiceManager.getManager().getConfiguration().getString("machinecode"),
													activitiesAward.getStartTime(), activitiesAward.getEndTime()});
									for (Object[] obj : onlineList2) {
										if (Integer.parseInt(map.get(ON_LINE_NUMBER)) <= ServiceManager
												.getManager()
												.getTaskService()
												.getService()
												.getLoingNumByPlayerId(Integer.parseInt(obj[0].toString()),
														Integer.parseInt(map.get(ON_LINE_NUMBER)))) {
											saveLogActivitiesAward(activitiesAward, obj[0].toString());
										}
									}
								}
								break;
							case Common.LOG_AWARD_TYPE_6 :// 结婚
								sb.append(" SELECT m.manId,m.womanId FROM tab_marryrecord m,tab_player p WHERE m.manId=p.id AND p.areaId=? AND m.statusMode=2 AND m.marryTime BETWEEN ? AND ? ");
								sb.append(" ");
								sb.append(map.get(MARRY_RECORD));
								List<Object[]> marrylist = (List<Object[]>) this.getListBySql(sb.toString(),
										new Object[]{ServiceManager.getManager().getConfiguration().getString("machinecode"),
												activitiesAward.getStartTime(), activitiesAward.getEndTime()});
								for (Object[] obj : marrylist) {
									saveLogActivitiesAward(activitiesAward, obj[0].toString());
									saveLogActivitiesAward(activitiesAward, obj[1].toString());
								}
								break;
							case Common.LOG_AWARD_TYPE_7 :// 升星
								if (new Date().getTime() > activitiesAward.getEndTime().getTime()) {// 活动结束时才发送奖励
									sb.append(" SELECT p.id,DATE(l.createTime),l.level FROM log_strongerecord l,tab_player p,tab_shopitems s WHERE l.itemId=s.id AND l.playerId=p.id AND p.areaId=? AND l.type=? AND l.createTime BETWEEN ? AND ? ");
									sb.append(" ");
									sb.append(map.get(STARS_INFO));
									sb.append(" ");
									sb.append(" ORDER BY l.level DESC ");
									List<Object[]> objectlist = (List<Object[]>) this.getListBySql(sb.toString(), new Object[]{
											ServiceManager.getManager().getConfiguration().getString("machinecode"),
											Common.STRONGE_RECORD_STAR, activitiesAward.getStartTime(), activitiesAward.getEndTime()});
									saveLog(activitiesAward, objectlist);
								}
								break;
							default :
								break;
						}
					} catch (Exception e) {
						log.info("SchedulingService Error : --活动名称--" + activitiesAward.getActivityName() + "--SQL语句--" + sb.toString());
						e.printStackTrace();
					}
				}
				// 如果活动已经结束，设置活动结束
				if (new Date().getTime() > activitiesAward.getEndTime().getTime()
						&& (Common.LOG_AWARD_STATUS_N).equals(activitiesAward.getIsSend())) {
					activitiesAward.setIsSend(Common.LOG_AWARD_STATUS_Y);
					activitiesAward.setPlayerIds("");
					dao.update(activitiesAward);
				}
			}
		}
	}

	/**
	 * 充值包含返钻保存
	 * 
	 * @param backAmount
	 *            返钻
	 * @param activitiesAward
	 *            活动对象
	 * @param playerId
	 *            玩家ID
	 * @param amount
	 *            钻石数
	 */
	private void saveRechargeAward(String backAmount, ActivitiesAward activitiesAward, String playerId, String amount) {
		if (!activitiesAward.getPlayerIds().contains(playerId)) {
			saveLogActivitiesAward(backAmount, activitiesAward, playerId, amount, null, null);
		}
	}

	/**
	 * 保存活动方法日志信息
	 * 
	 * @param backAmount
	 *            返钻
	 * @param activitiesAward
	 *            活动对象
	 * @param playerId
	 *            玩家ID
	 * @param amount
	 *            钻石数
	 */
	private void saveLogActivitiesAward(String backAmount, ActivitiesAward activitiesAward, String playerId, String amount,
			Integer playerBillId, String createTime) {
		String itemsAward = "";
		String itemRemark = "";
		if (activitiesAward.getItemFormula() != null && !("").equals(activitiesAward.getItemFormula())) {
			itemsAward = activitiesAward.getItemFormula();
			itemRemark = activitiesAward.getItemRemark();
		}
		if (!("-1").equals(backAmount)) {
			int tempAmount = (int) (Integer.parseInt(amount) * (Integer.parseInt(backAmount) / 100.0));
			if (!("").equals(itemsAward)) {
				itemsAward += "&";
				itemRemark += ",";
			}
			itemsAward += "ticket=" + tempAmount;
			itemRemark += TipMessages.ACTIVITIES_AWARD_AMOUNT.replace("{0}", activitiesAward.getActivityName()).replace("{1}",
					tempAmount + "");
		}
		LogActivitiesAward logActivitiesAward = new LogActivitiesAward();
		logActivitiesAward.setPlayerId(Integer.parseInt(playerId));
		logActivitiesAward.setPlayerBillId(playerBillId);
		logActivitiesAward.setAreaId(WorldServer.config.getAreaId());
		logActivitiesAward.setActivityName(activitiesAward.getActivityName());
		logActivitiesAward.setStartTime(activitiesAward.getStartTime());
		logActivitiesAward.setEndTime(activitiesAward.getEndTime());
		logActivitiesAward.setIsSend(Common.LOG_AWARD_STATUS_N);
		logActivitiesAward.setItemsAward(itemsAward);
		logActivitiesAward.setItemsAwardRemark(itemRemark);
		if (createTime != null) {
			try {
				logActivitiesAward.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			logActivitiesAward.setCreateTime(new Date());
		}
		logActivitiesAward.setMailTitle(activitiesAward.getMailTitle());
		logActivitiesAward.setMailContent(activitiesAward.getMailContent());
		dao.save(logActivitiesAward);
		String playerIds = "";
		if (activitiesAward.getPlayerIds() != null && !("").equals(activitiesAward.getPlayerIds())) {
			playerIds = activitiesAward.getPlayerIds() + "," + playerId;
		} else {
			playerIds = playerId;
		}
		activitiesAward.setPlayerIds(playerIds);
		dao.update(activitiesAward);
	}

	/**
	 * 保存活动方法日志信息
	 * 
	 * @param activitiesAward
	 *            活动对象
	 * @param Objectlist
	 *            查询结果集
	 */
	private void saveLog(ActivitiesAward activitiesAward, List<Object[]> objectlist) {
		for (Object[] obj : objectlist) {
			saveLogActivitiesAward(activitiesAward, obj[0].toString());
		}
	}

	/**
	 * 保存活动方法日志信息
	 * 
	 * @param activitiesAward
	 *            活动对象
	 * @param playerId
	 *            玩家ID
	 */
	private void saveLogActivitiesAward(ActivitiesAward activitiesAward, String playerId) {
		if (!activitiesAward.getPlayerIds().contains(playerId)) {
			LogActivitiesAward logActivitiesAward = new LogActivitiesAward();
			logActivitiesAward.setPlayerId(Integer.parseInt(playerId));
			logActivitiesAward.setAreaId(WorldServer.config.getAreaId());
			logActivitiesAward.setActivityName(activitiesAward.getActivityName());
			logActivitiesAward.setStartTime(activitiesAward.getStartTime());
			logActivitiesAward.setEndTime(activitiesAward.getEndTime());
			logActivitiesAward.setIsSend(Common.LOG_AWARD_STATUS_N);
			logActivitiesAward.setItemsAward(activitiesAward.getItemFormula());
			logActivitiesAward.setItemsAwardRemark(activitiesAward.getItemRemark());
			logActivitiesAward.setCreateTime(new Date());
			logActivitiesAward.setMailTitle(activitiesAward.getMailTitle());
			logActivitiesAward.setMailContent(activitiesAward.getMailContent());
			dao.save(logActivitiesAward);
			String playerIds = "";
			if (activitiesAward.getPlayerIds() != null && !("").equals(activitiesAward.getPlayerIds())) {
				playerIds = activitiesAward.getPlayerIds() + "," + playerId;
			} else {
				playerIds = playerId;
			}
			activitiesAward.setPlayerIds(playerIds);
			dao.update(activitiesAward);
		}
	}

	/**
	 * 日常活动奖励
	 */
	@Override
	public void saveLogActivitiesAward(DailyRewardVo dailyRewardVo, int playerId) {
		for (DailyActivityVo dailyActivityVo : dailyRewardVo.getDailyActivityVos()) {
			if (StringUtils.hasText(dailyActivityVo.getRewardItems())) {
				LogActivitiesAward logActivitiesAward = new LogActivitiesAward();
				logActivitiesAward.setPlayerId(playerId);
				logActivitiesAward.setAreaId(WorldServer.config.getAreaId());
				logActivitiesAward.setActivityName(dailyActivityVo.getName());
				logActivitiesAward.setStartTime(new Date());
				logActivitiesAward.setEndTime(new Date());
				logActivitiesAward.setIsSend(Common.LOG_AWARD_STATUS_N);
				logActivitiesAward.setItemsAward(dailyActivityVo.getRewardItems());
				logActivitiesAward.setItemsAwardRemark("");
				logActivitiesAward.setCreateTime(new Date());
				logActivitiesAward.setMailTitle(dailyActivityVo.getMailTitle());
				logActivitiesAward.setMailContent(dailyActivityVo.getMailContent());
				dao.save(logActivitiesAward);
			}
		}
	}

	/**
	 * 登录日常活动奖励
	 */
	@Override
	public void saveloginAward(DailyRewardVo dailyRewardVo, int playerId) {
		Date date = new Date();
		List<String> list = dao.getLoginAward(playerId, date);
		for (DailyActivityVo dailyActivityVo : dailyRewardVo.getDailyActivityVos()) {
			if (StringUtils.hasText(dailyActivityVo.getRewardItems()) && !list.contains(dailyActivityVo.getName())) {
				LogActivitiesAward logActivitiesAward = new LogActivitiesAward();
				logActivitiesAward.setPlayerId(playerId);
				logActivitiesAward.setAreaId(WorldServer.config.getAreaId());
				logActivitiesAward.setActivityName(dailyActivityVo.getName());
				logActivitiesAward.setStartTime(date);
				logActivitiesAward.setEndTime(date);
				logActivitiesAward.setIsSend(Common.LOG_AWARD_STATUS_N);
				logActivitiesAward.setItemsAward(dailyActivityVo.getRewardItems());
				logActivitiesAward.setItemsAwardRemark("");
				logActivitiesAward.setCreateTime(date);
				logActivitiesAward.setMailTitle(dailyActivityVo.getMailTitle());
				logActivitiesAward.setMailContent(dailyActivityVo.getMailContent());
				dao.save(logActivitiesAward);
			}
		}
	}

	/**
	 * 封装完成条件参数
	 * 
	 * @param param
	 *            完成条件参数
	 * @return
	 */
	private Map<String, String> formatParam(String param) {
		String[] markStr = param.split("&");
		Map<String, String> map = new HashMap<String, String>();
		String[] str;
		for (String s : markStr) {
			str = s.split("\\|");
			map.put(str[0], str[1]);
		}
		return map;
	}
}
