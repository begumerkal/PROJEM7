package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The persistent class for the tab_player_reward database table.
 * 
 * @author zguoqiu
 */
@Entity()
@Table(name = "tab_player_reward")
public class PlayerReward implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int playerId; // 玩家id
	// 签到记录--------------------------------------------------------------------
	private String signDays; // 签到日期
	private int signMonth; // 签到月份
	private int supplTimes; // 补签次数
	private String totalRewards; // 累计签到奖励领取记录
	private List<Integer> signDayList;
	private List<Integer> signRewardList;
	// 登录记录--------------------------------------------------------------------
	private int loginDays; // 累计登录天数
	private String loginRewards; // 累计登录奖励领取记录
	private String loginTargetRewards; // 登录目标奖励领取记录
	private List<Integer> loginRewardList;
	private List<Integer> loginTargetRewardList;
	// 等级记录--------------------------------------------------------------------
	private String levelRewards; // 等级奖励领取记录
	private String levelTargetRewards; // 等级目标奖励领取记录
	private List<Integer> levelRewardList;
	private List<Integer> levelTargetRewardList;
	// 在线奖励记录-----------------------------------------------------------------
	private int onlineDay; // 在线奖励记录日期
	private int onlineMinutes; // 领取在线奖励目标分钟数
	private long onlineTime; // 在线奖励可领取时间
	private int lotteryMinutes; // 抽奖目标分钟数
	private long lotteryTime; // 抽奖次数增加时间
	private int lotteryTimes; // 当前可抽奖次数
	private int lotteryParam; // 当前可抽奖的分钟数

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "player_id", precision = 10)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "sign_days", length = 255)
	public String getSignDays() {
		return null == signDays ? "" : signDays;
	}

	public void setSignDays(String signDays) {
		this.signDays = signDays;
	}

	@Basic()
	@Column(name = "sign_month", precision = 10)
	public int getSignMonth() {
		return signMonth;
	}

	public void setSignMonth(int signMonth) {
		this.signMonth = signMonth;
	}

	@Basic()
	@Column(name = "suppl_times", precision = 10)
	public int getSupplTimes() {
		return supplTimes;
	}

	public void setSupplTimes(int supplTimes) {
		this.supplTimes = supplTimes;
	}

	@Basic()
	@Column(name = "total_rewards", length = 255)
	public String getTotalRewards() {
		return null == totalRewards ? "" : totalRewards;
	}

	public void setTotalRewards(String totalRewards) {
		this.totalRewards = totalRewards;
	}

	@Basic()
	@Column(name = "login_days", precision = 10)
	public int getLoginDays() {
		return loginDays;
	}

	public void setLoginDays(int loginDays) {
		this.loginDays = loginDays;
	}

	@Basic()
	@Column(name = "login_rewards", length = 255)
	public String getLoginRewards() {
		return null == loginRewards ? "" : loginRewards;
	}

	public void setLoginRewards(String loginRewards) {
		this.loginRewards = loginRewards;
	}

	@Basic()
	@Column(name = "login_target_rewards", length = 255)
	public String getLoginTargetRewards() {
		return null == loginTargetRewards ? "" : loginTargetRewards;
	}

	public void setLoginTargetRewards(String loginTargetRewards) {
		this.loginTargetRewards = loginTargetRewards;
	}

	@Basic()
	@Column(name = "level_rewards", length = 255)
	public String getLevelRewards() {
		return null == levelRewards ? "" : levelRewards;
	}

	public void setLevelRewards(String levelRewards) {
		this.levelRewards = levelRewards;
	}

	@Basic()
	@Column(name = "level_target_rewards", length = 255)
	public String getLevelTargetRewards() {
		return null == levelTargetRewards ? "" : levelTargetRewards;
	}

	public void setLevelTargetRewards(String levelTargetRewards) {
		this.levelTargetRewards = levelTargetRewards;
	}

	@Basic()
	@Column(name = "online_day", precision = 10)
	public int getOnlineDay() {
		return onlineDay;
	}

	public void setOnlineDay(int onlineDay) {
		this.onlineDay = onlineDay;
	}

	@Basic()
	@Column(name = "online_minutes", precision = 10)
	public int getOnlineMinutes() {
		return onlineMinutes;
	}

	public void setOnlineMinutes(int onlineMinutes) {
		this.onlineMinutes = onlineMinutes;
	}

	@Basic()
	@Column(name = "online_time", precision = 16)
	public long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}

	@Basic()
	@Column(name = "lottery_minutes", precision = 10)
	public int getLotteryMinutes() {
		return lotteryMinutes;
	}

	public void setLotteryMinutes(int lotteryMinutes) {
		this.lotteryMinutes = lotteryMinutes;
	}

	@Basic()
	@Column(name = "lottery_time", precision = 16)
	public long getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(long lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	@Basic()
	@Column(name = "lottery_times", precision = 10)
	public int getLotteryTimes() {
		return lotteryTimes;
	}

	public void setLotteryTimes(int lotteryTimes) {
		this.lotteryTimes = lotteryTimes;
	}

	@Basic()
	@Column(name = "lottery_param", precision = 10)
	public int getLotteryParam() {
		return lotteryParam;
	}

	public void setLotteryParam(int lotteryParam) {
		this.lotteryParam = lotteryParam;
	}

	// 签到记录--------------------------------------------------------------------
	@Transient
	public void initSignData() {
		signDays = "";
		signMonth = DateUtil.getCurrentMonth();
		supplTimes = 0;
		totalRewards = "";
		refreshSignData();
	}

	/**
	 * 更新签到信息
	 * 
	 * @param signDays
	 */
	public void updateSignDays(String signDays) {
		setSignDays(signDays);
		refreshSignData();
		WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
		ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
	}

	/**
	 * 更新签到信息
	 * 
	 * @param signDays
	 */
	public void updateTotalRewards(String totalRewards) {
		setTotalRewards(totalRewards);
		refreshSignData();
		WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
		ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
	}

	@Transient
	public void refreshSignData() {
		List<Integer> signDayList = new ArrayList<Integer>();
		if (getSignDays().length() > 0) {
			for (String day : signDays.split(",")) {
				signDayList.add(Integer.parseInt(day));
			}
		}
		Comparator<Integer> scComparator = new SignComparator();
		Collections.sort(signDayList, scComparator);
		this.signDayList = signDayList;
		List<Integer> signRewardList = new ArrayList<Integer>();
		if (getTotalRewards().length() > 0) {
			for (String day : totalRewards.split(",")) {
				signRewardList.add(Integer.parseInt(day));
			}
		}
		this.signRewardList = signRewardList;
	}

	/**
	 * 判断某个日期是否已签到
	 * 
	 * @param dayOfMonth
	 * @return
	 */
	@Transient
	public boolean isSign(Integer dayOfMonth) {
		if (null == signDayList)
			refreshSignData();
		return signDayList.contains(dayOfMonth);
	}

	/**
	 * 判断某个签到奖励是否已领取
	 * 
	 * @param totalSignDays
	 * @return
	 */
	@Transient
	public boolean isSignReward(Integer totalSignDays) {
		if (null == signRewardList)
			refreshSignData();
		return signRewardList.contains(totalSignDays);
	}

	/**
	 * 获取玩家的签到日期
	 * 
	 * @return
	 */
	@Transient
	public List<Integer> getSignDayList() {
		if (null == signDayList)
			refreshSignData();
		return signDayList;
	}

	/**
	 * 获取玩家连续签到天数
	 * 
	 * @return
	 */
	@Transient
	public int getArraySigns() {
		if (null == signDayList)
			refreshSignData();
		if (signDayList.size() < 1 || (DateUtil.getCurrentDay() - signDayList.get(signDayList.size() - 1)) > 1) {
			return 0;
		} else {
			int as = 1;
			for (int i = signDayList.size() - 1; i > 0; i--) {
				if (signDayList.get(i) - signDayList.get(i - 1) > 1) {
					break;
				} else {
					as++;
				}
			}
			return as;
		}
	}

	/**
	 * 获取本月需补签次数
	 * 
	 * @return
	 */
	@Transient
	public int getSupplSigns() {
		if (null == signDayList)
			refreshSignData();
		int supplSings = DateUtil.getCurrentDay() - signDayList.size();
		if (!isSign(DateUtil.getCurrentDay())) {
			supplSings--;
		}
		return supplSings;
	}

	public class SignComparator implements Comparator<Integer> {
		public int compare(Integer sc1, Integer sc2) {
			return sc1 - sc2;
		}
	}

	// 登录记录--------------------------------------------------------------------
	/**
	 * 更新登录奖励领取信息
	 * 
	 * @param signDays
	 */
	public void updateLoginRewards(String loginRewards) {
		setLoginRewards(loginRewards);
		refreshLoginData();
	}

	/**
	 * 更新登录目标奖励领取信息
	 * 
	 * @param signDays
	 */
	public void updateLoginTargetRewards(String loginTargetRewards) {
		setLoginTargetRewards(loginTargetRewards);
		refreshLoginData();
	}

	@Transient
	public void refreshLoginData() {
		List<Integer> loginRewardList = new ArrayList<Integer>();
		if (getLoginRewards().length() > 0) {
			for (String day : loginRewards.split(",")) {
				loginRewardList.add(Integer.parseInt(day));
			}
		}
		this.loginRewardList = loginRewardList;
		List<Integer> loginTargetRewardList = new ArrayList<Integer>();
		if (getLoginTargetRewards().length() > 0) {
			for (String day : loginTargetRewards.split(",")) {
				loginTargetRewardList.add(Integer.parseInt(day));
			}
		}
		this.loginTargetRewardList = loginTargetRewardList;
		WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
		ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
	}

	/**
	 * 判断某个登录奖励是否已领取
	 * 
	 * @param totalLoginDays
	 * @return
	 */
	@Transient
	public boolean isLoginReward(Integer totalLoginDays) {
		if (null == loginRewardList)
			refreshLoginData();
		return loginRewardList.contains(totalLoginDays);
	}

	/**
	 * 判断某个登录目标奖励是否已领取
	 * 
	 * @param totalLoginDays
	 * @return
	 */
	@Transient
	public boolean isLoginTargetReward(Integer totalLoginDays) {
		if (null == loginTargetRewardList)
			refreshLoginData();
		return loginTargetRewardList.contains(totalLoginDays);
	}

	// 等级记录--------------------------------------------------------------------
	/**
	 * 更新等级奖励领取信息
	 * 
	 * @param signDays
	 */
	public void updateLevelRewards(String levelRewards) {
		setLevelRewards(levelRewards);
		refreshLevelData();
	}

	/**
	 * 更新等级目标奖励领取信息
	 * 
	 * @param signDays
	 */
	public void updateLevelTargetRewards(String levelTargetRewards) {
		setLevelTargetRewards(levelTargetRewards);
		refreshLevelData();
	}

	@Transient
	public void refreshLevelData() {
		List<Integer> levelRewardList = new ArrayList<Integer>();
		if (getLevelRewards().length() > 0) {
			for (String level : levelRewards.split(",")) {
				levelRewardList.add(Integer.parseInt(level));
			}
		}
		this.levelRewardList = levelRewardList;
		List<Integer> levelTargetRewardList = new ArrayList<Integer>();
		if (getLevelTargetRewards().length() > 0) {
			for (String level : levelTargetRewards.split(",")) {
				levelTargetRewardList.add(Integer.parseInt(level));
			}
		}
		this.levelTargetRewardList = levelTargetRewardList;
		WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
		ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
	}

	/**
	 * 判断某个等级奖励是否已领取
	 * 
	 * @param totalLoginDays
	 * @return
	 */
	@Transient
	public boolean isLevelReward(Integer level) {
		if (null == levelRewardList)
			refreshLevelData();
		return levelRewardList.contains(level);
	}

	/**
	 * 判断某个等级目标奖励是否已领取
	 * 
	 * @param totalLoginDays
	 * @return
	 */
	@Transient
	public boolean isLevelTargetReward(Integer level) {
		if (null == levelTargetRewardList)
			refreshLevelData();
		return levelTargetRewardList.contains(level);
	}

	// 在线奖励记录-----------------------------------------------------------------
	public void initOnlineData() {
		StringBuffer daysb = new StringBuffer();
		daysb.append(DateUtil.getCurrentYear());
		daysb.append(DateUtil.getCurrentMonth());
		daysb.append(DateUtil.getCurrentDay());
		int day = Integer.parseInt(daysb.toString());
		if (this.onlineDay != day) {
			this.onlineDay = day;
			Reward signReward = ServiceManager.getManager().getTaskService().getService().getRewardByParam(0, 7);
			this.lotteryMinutes = null != signReward ? signReward.getParam() : 0;
			this.lotteryTime = System.currentTimeMillis() + (this.lotteryMinutes * 60000);
			this.lotteryTimes = 0;
			this.lotteryParam = this.lotteryMinutes;
		}
		// 初次初始化玩家在线奖励信息
		if (this.onlineMinutes == 0) {
			Reward signReward = ServiceManager.getManager().getTaskService().getService().getRewardByParam(0, 6);
			if (null != signReward) {
				this.onlineMinutes = signReward.getParam();
				this.onlineTime = System.currentTimeMillis() + (this.onlineMinutes * 60000);
			}
		} else if (this.onlineTime == 0) {// 检查是否有新增的在线奖励信息
			receiveOnlineReward();
		}
	}

	/**
	 * 检查在线奖励信息
	 */
	public void checkLotteryReward() {
		initOnlineData();
		long timeMillis = System.currentTimeMillis() - onlineTime;
		boolean check = false;
		if (-1 < timeMillis && timeMillis < 120000) {
			check = true;
		}
		if (lotteryMinutes > 0 && lotteryTime <= System.currentTimeMillis()) {
			this.lotteryTimes++;
			Reward signReward = ServiceManager.getManager().getTaskService().getService().getRewardByParam(this.lotteryMinutes + 1, 7);
			this.lotteryMinutes = null != signReward ? signReward.getParam() : 0;
			this.lotteryTime = System.currentTimeMillis() + (this.lotteryMinutes * 60000);
			check = true;
			ServiceManager.getManager().getTaskService().getService().savePlayerSignInfo(playerId);
		}
		if (check) {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
			ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
		}
	}

	/**
	 * 领取在线奖励更新在线奖励信息
	 */
	public void receiveOnlineReward() {
		Reward signReward = ServiceManager.getManager().getTaskService().getService().getRewardByParam(this.onlineMinutes + 1, 6);
		boolean check = false;
		if (null != signReward) {
			this.onlineMinutes = signReward.getParam();
			this.onlineTime = System.currentTimeMillis() + (this.onlineMinutes * 60000);
			check = true;
		} else if (this.onlineTime != 0) {
			this.onlineTime = 0;
			check = true;
		}
		if (check) {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
			ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
		}
	}

	/**
	 * 领取在线抽奖更新抽奖信息
	 */
	public void receiveLotteryReward() {
		this.lotteryTimes--;
		Reward signReward = ServiceManager.getManager().getTaskService().getService().getRewardByParam(this.lotteryParam + 1, 6);
		if (null != signReward) {
			this.lotteryParam = signReward.getParam();
		} else {
			this.lotteryTimes = 0;
			this.lotteryMinutes = 0;
		}
		WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
		ServiceManager.getManager().getTaskService().getService().synRewardNum(player);
	}
}
