package com.wyd.empire.world.item;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.wyd.empire.world.bean.DailyActivity;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.impl.DailyActivityService;

public class DailyActivityVo {
	Set<Integer> conditions = new HashSet<Integer>();
	Set<Integer> rewardsAdd = new HashSet<Integer>();
	Set<Integer> rewardsSub = new HashSet<Integer>();
	private DailyActivity dailyActivity;

	public DailyActivityVo(DailyActivity dailyActivity) {
		this.dailyActivity = dailyActivity;
		Set<?> temp;
		temp = StringUtils.commaDelimitedListToSet(dailyActivity.getAwardCondition());
		for (Object token : temp) {
			conditions.add(new Integer((String) token));
		}
		temp = StringUtils.commaDelimitedListToSet(dailyActivity.getRewardsAdd());
		for (Object token : temp) {
			rewardsAdd.add(new Integer((String) token));
		}
		temp = StringUtils.commaDelimitedListToSet(dailyActivity.getRewardsSub());
		for (Object token : temp) {
			rewardsSub.add(new Integer((String) token));
		}
	}

	public boolean containsCondition(int condition) {
		return conditions.contains(condition);
	}

	public boolean onTime() {
		if (!isActive()) {
			return false;
		}
		Calendar temp = Calendar.getInstance();
		if (temp.get(Calendar.DAY_OF_WEEK) != dailyActivity.getDaysOfWeek()) {
			return false;
		}
		Date start = dailyActivity.getStartTime();
		Date end = dailyActivity.getEndTime();
		if (start.getTime() == end.getTime()) {
			return true;
		}
		boolean flag = false;
		if (end.before(start)) {
			flag = true;
			end = new Date(DateUtil.DAY_MSELS + end.getTime());
		}
		temp = new GregorianCalendar(1970, 0, 1, temp.get(Calendar.HOUR_OF_DAY), temp.get(Calendar.MINUTE), temp.get(Calendar.SECOND));
		Date now = temp.getTime();
		if (flag && now.getTime() + DateUtil.DAY_MSELS < end.getTime()) {
			now = new Date(now.getTime() + DateUtil.DAY_MSELS);
		}
		if (now.after(start) && now.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isActive() {
		if (dailyActivity.getStatus() == 1) {
			return true;
		} else {
			return false;
		}
	}

	private int getAddRate(int a_) {
		if (rewardsAdd.contains(a_)) {
			return dailyActivity.getRewardRateAdd();
		} else {
			return 0;
		}
	}

	private int getSubRate(int s_) {
		if (rewardsSub.contains(s_)) {
			return dailyActivity.getRewardRateSub();
		} else {
			return 100;
		}
	}

	public int getId() {
		return dailyActivity.getId();
	}

	public String getName() {
		return dailyActivity.getName();
	}

	public String getDescription() {
		return dailyActivity.getDescription();
	}

	public String getTime() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		switch (dailyActivity.getDaysOfWeek()) {
			case 1 :
				sb.append(TipMessages.SUNDAY);
				break;
			case 2 :
				sb.append(TipMessages.MONDAY);
				break;
			case 3 :
				sb.append(TipMessages.TUESDAY);
				break;
			case 4 :
				sb.append(TipMessages.WEDNESDAY);
				break;
			case 5 :
				sb.append(TipMessages.THURSDAY);
				break;
			case 6 :
				sb.append(TipMessages.FRIDAY);
				break;
			case 7 :
				sb.append(TipMessages.SATURDAY);
				break;
		}
		sb.append(sdf.format(dailyActivity.getStartTime()));
		sb.append("~");
		sb.append(sdf.format(dailyActivity.getEndTime()));
		return sb.toString();
	}

	public int getDaysOfWeek() {
		return dailyActivity.getDaysOfWeek();
	}

	public int getExp() {
		return getAddRate(DailyActivityService.a_exp);
	}

	public int getGold() {
		return getAddRate(DailyActivityService.a_gold);
	}

	public int getMedal() {
		return getAddRate(DailyActivityService.a_medal);
	}

	public int getEquipmentDay() {
		return getAddRate(DailyActivityService.a_equipment_day);
	}

	public int getSubGold() {
		return getSubRate(DailyActivityService.s_gold);
	}

	public int getSubMedal() {
		return getSubRate(DailyActivityService.s_medal);
	}

	public int getSubTicket() {
		return getSubRate(DailyActivityService.s_ticket);
	}

	public int getSubTime() {
		return getSubRate(DailyActivityService.s_time);
	}

	public String getRewardItems() {
		return dailyActivity.getRewardItems();
	}

	public String getMailTitle() {
		return dailyActivity.getMailTitle();
	}

	public String getMailContent() {
		return dailyActivity.getMailContent();
	}
}
