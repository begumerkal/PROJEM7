package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.ChallengeRecord;
import com.wyd.empire.world.bean.IntegralArea;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IRankRecordDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IChallengeService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.IntegralService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class ChallengeService implements IChallengeService {
	private IRankRecordDao dao;
	Logger log = Logger.getLogger(ChallengeService.class);
	private static JSONObject challengeJson;
	private static Date openNoticeDate; // 挑战赛开启通知时间，如果时间等于当前天说明今天已通知过了。
	private static boolean startMark = false;
	private static Map<Integer, ChallengeRecord> integralMap = null;

	static {
		// OperationConfig operationConfig =
		// ServiceManager.getManager().getVersionService().getVersion();
		challengeJson = JSONObject.fromObject("{startTime: \"20:00\",endTime: \"22:00\"}");
	}

	public static void initConfig(String config) {
		challengeJson = JSONObject.fromObject(config);
	}

	public IRankRecordDao getDao() {
		return dao;
	}

	public void setDao(IRankRecordDao dao) {
		this.dao = dao;
	}

	public void inintData() {
		ServiceManager.getManager().getThreadIntegralService().runOrder(IntegralService.INTEGRAL_GET_INFO);
		List<ChallengeRecord> integralList = dao.getAllIntegral();
		Map<Integer, ChallengeRecord> map = new HashMap<Integer, ChallengeRecord>();
		for (ChallengeRecord integral : integralList) {
			map.put(integral.getPlayerId(), integral);
		}
		integralMap = map;
	}

	/**
	 * 检测当前是否开启挑战赛
	 * 
	 * @return
	 */
	@Override
	public boolean isInTime() {
		return isInTime(new Date());
	}

	public boolean isInTime(Date date) {
		JSONObject json = stringToJson();
		Calendar cal = Calendar.getInstance();
		int weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1; // 周日是0
		String timeStr = json.optString("week" + weekNum);
		if (null != timeStr && "OPEN".equals(timeStr)) {
			Date[] scope = getTimeScope();
			return isBetweenTime(date, scope);
		} else {
			return false;
		}
	}

	/**
	 * 供系统定时任务调用，如果当前时间在挑战赛开启时间内则开启；如果五分钟后开启则发通知（一天只发一次）
	 * 
	 * @return
	 */
	@Override
	public void sysCheckStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		if (isInTime(cal.getTime())) {
			if (!startMark) {
				int weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1; // 周日是0
				JSONObject json = stringToJson();
				String timeStr = json.optString("seasonOpen");
				if (null != timeStr && Integer.parseInt(timeStr) == weekNum) {
					// 清空弹王积分
					deleteChallengeRecord();
					deleteIntegralByArea();
				}
				log.info("挑战赛时间开启...");
				// 发公告
				ServiceManager.getManager().getChatService().openChallenge();
				startMark = true;
				ServiceManager.getManager().getButtonInfoService().synButtonInfo();
			}
		} else {
			cal.add(Calendar.MINUTE, 10);
			if (isInTime(cal.getTime()) && !eqToday(openNoticeDate)) {
				// 发公告
				ServiceManager.getManager().getChatService().openChallenge();
				openNoticeDate = cal.getTime();
			}
		}
	}

	public boolean isStart() {
		return startMark;
	}

	public Date[] getTimeScope() {
		Date[] dates = new Date[2];
		try {
			JSONObject json = stringToJson();
			String timeStr = json.getString("startTime");
			dates[0] = parseDate(timeStr);
			timeStr = json.getString("endTime");
			dates[1] = parseDate(timeStr);
		} catch (Exception e) {
			log.error("Time error or config is null");
			return null;
		}
		return dates;
	}

	/**
	 * 格式HH:mm
	 * 
	 * @return
	 */
	private Date parseDate(String HHmm) {
		Calendar cal = Calendar.getInstance();
		String[] HH_mm = HHmm.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(HH_mm[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(HH_mm[1]));
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public boolean isBetweenTime(Date now, Date[] scope) {
		return (now.compareTo(scope[0]) >= 0 && now.compareTo(scope[1]) < 0);
	}

	public boolean eqToday(Date date) {
		if (date == null)
			return false;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(date);
		return day == cal.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public void sysCheckEndTime() {
		try {
			Date[] startEnd = getTimeScope();
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			if (startMark) {
				if (now.compareTo(startEnd[1]) >= 0) {
					timeOver();
					log.info("挑战赛时间结束...");
					startMark = false;
					ServiceManager.getManager().getButtonInfoService().synButtonInfo();
				} else {
					cal.add(Calendar.MINUTE, 1);
					if (cal.getTime().compareTo(startEnd[1]) >= 0) {
						// 发公告
						ServiceManager.getManager().getChatService().closeChallenge();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sysCheckStartEndTime() {
		sysCheckStartTime();
		sysCheckEndTime();
	}

	// 时间到
	public void timeOver() {
		// 发公告
		ServiceManager.getManager().getChatService().closeChallenge();
		ServiceManager.getManager().getThreadIntegralService().runOrder(IntegralService.INTEGRAL_TOP_THREE);
		// 发送每日结算奖励开始--------------------------------------------------
		int goldPrice = ServiceManager.getManager().getVersionService().getInteToGold();
		List<ChallengeRecord> crList = new ArrayList<ChallengeRecord>(integralMap.values());
		WorldPlayer wp;
		for (ChallengeRecord cr : crList) {
			try {
				if (cr.getIntegral() - cr.getLastIntegral() > 0) {
					wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(cr.getPlayerId());
					if (null != wp) {
						int addMoemey = (cr.getIntegral() - cr.getLastIntegral()) * goldPrice;
						ServiceManager.getManager().getPlayerService().updatePlayerGold(wp, addMoemey, "挑战赛每日结算", "-- " + " --");
						sendMail(
								wp.getId(),
								TipMessages.CHALLENGE_MAIL_EVERYDAY.replace("{1}", (cr.getIntegral() - cr.getLastIntegral()) + "").replace(
										"{2}", addMoemey + ""));
					}
				}
				cr.setWinNum(0);
				cr.setLastIntegral(cr.getIntegral());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServiceManager.getManager().getChallengeSerService().updateIntegralByArea();
		// 发送每日结算奖励结束--------------------------------------------------
		JSONObject json = stringToJson();
		Calendar cal = Calendar.getInstance();
		int weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1; // 周日是0
		String timeStr = json.optString("seasonClose");
		if (null != timeStr && Integer.parseInt(timeStr) == weekNum) {
			ServiceManager.getManager().getThreadIntegralService().runOrder(IntegralService.INTEGRAL_ALL_LIST);
		}
		log.info("挑战赛每日奖励...");
	}

	private void sendMail(int playerId, String content) {
		Mail mail = new Mail();
		mail.setContent(content);
		mail.setIsRead(false);
		mail.setReceivedId(playerId);
		mail.setSendId(0);
		mail.setSendName(TipMessages.SYSNAME_MESSAGE);
		mail.setSendTime(new Date());
		mail.setTheme(TipMessages.CHALLENGE_MAIL_THEME);
		mail.setType(1);
		mail.setBlackMail(false);
		mail.setIsStick(Common.IS_STICK);
		ServiceManager.getManager().getMailService().saveMail(mail, null);
	}

	/**
	 * 获得挑战赛配置
	 * 
	 * @param str
	 * @return
	 */
	public JSONObject stringToJson() {
		return challengeJson;
	}

	/**
	 * 清理挑战赛积分
	 */
	public void deleteIntegralByArea() {
		ServiceManager.getManager().getThreadIntegralService().runOrder(IntegralService.INTEGRAL_CLEAN_UP);
	}

	/**
	 * 获取玩家弹王积分
	 * 
	 * @param playerId
	 * @return
	 */
	public int getIntegral(int playerId) {
		ChallengeRecord integral = integralMap.get(playerId);
		if (null == integral) {
			return 0;
		}
		return integral.getIntegral();
	}

	/**
	 * 获取玩家弹王连胜次数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getArrayWinNum(int playerId) {
		ChallengeRecord integral = integralMap.get(playerId);
		if (null == integral) {
			return 0;
		}
		return integral.getWinNum();
	}

	/**
	 * 玩家获得弹王积分
	 * 
	 * @param player
	 * @param integral
	 * @param isWin
	 * @return
	 */
	public ChallengeRecord addIntegral(WorldPlayer player, int integral, boolean isWin) {
		ChallengeRecord cr = integralMap.get(player.getId());
		if (null == cr) {
			cr = new ChallengeRecord();
			cr.setPlayerId(player.getId());
			cr.setServiceId(WorldServer.config.getServerId());
			integralMap.put(player.getId(), cr);
		}
		cr.setIntegral(cr.getIntegral() + integral);
		int winNum = cr.getWinNum();
		if (isWin) {
			cr.setWinNum(cr.getWinNum() + 1);
		} else {
			cr.setWinNum(0);
		}
		if (0 == cr.getId()) {
			ChallengeRecord newCR = (ChallengeRecord) dao.save(cr);
			cr.setId(newCR.getId());
		} else {
			if (integral != 0 || cr.getWinNum() != winNum) {
				dao.update(cr);
			}
		}
		if (0 != integral) {
			ServiceManager.getManager().getThreadIntegralService().addIntegral(player, cr.getIntegral());
		}
		return cr;
	}

	/**
	 * 获得玩家的弹王积分明细
	 * 
	 * @param playerId
	 * @return
	 */
	public ChallengeRecord getChallengeRecord(int playerId) {
		return integralMap.get(playerId);
	}

	/**
	 * 获得玩家挑战赛应得奖励
	 * 
	 * @param rankNum
	 * @return
	 */
	public IntegralArea getIntegralAreaByRank(int rankNum) {
		return dao.getIntegralAreaByRank(rankNum);
	}

	/**
	 * 清除挑战赛记录
	 */
	public void deleteChallengeRecord() {
		integralMap = new HashMap<Integer, ChallengeRecord>();
		dao.deleteChallengeRecord();
	}

	/**
	 * 更新积分
	 */
	public void updateIntegralByArea() {
		dao.updateIntegralByArea();
	}
}