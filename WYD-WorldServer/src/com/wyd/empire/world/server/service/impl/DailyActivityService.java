package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.wyd.empire.world.bean.DailyActivity;
import com.wyd.empire.world.item.DailyActivityVo;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.server.service.base.IDailyActivityService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class DailyActivityService {
	public static final int turnover = 1;
	public static final int smashEgg = 2;
	public static final int task = 3;
	public static final int propose = 4;
	public static final int bossmapBattle = 5;
	public static final int battle = 6;
	public static final int buyConsortiaSkill = 7;
	public static final int login = 8;
	public static final int a_exp = 1;
	public static final int a_gold = 2;
	public static final int a_medal = 3;
	public static final int a_equipment_day = 4;
	public static final int s_gold = 1;
	public static final int s_medal = 2;
	public static final int s_ticket = 3;
	public static final int s_time = 4;
	IDailyActivityService dailyActivityService;
	List<DailyActivityVo> dailyActivityList = new CopyOnWriteArrayList<DailyActivityVo>();

	public DailyActivityService(IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public void initData() {
		List<?> temp = dailyActivityService.getAllDailyActivity();
		synchronized (dailyActivityList) {
			dailyActivityList.clear();
			for (Object o : temp) {
				dailyActivityList.add(new DailyActivityVo((DailyActivity) o));
			}
		}
	}

	private DailyRewardVo getReward(int condition) {
		DailyRewardVo dailyRewardVo = new DailyRewardVo();
		for (DailyActivityVo d : dailyActivityList) {
			if (d.containsCondition(condition) && d.onTime()) {
				dailyRewardVo.addReward(d);
			}
		}
		return dailyRewardVo;
	}

	public DailyRewardVo getTurnoverReward() {
		return getReward(turnover);
	}

	public DailyRewardVo getSmashEggReward() {
		return getReward(smashEgg);
	}

	public DailyRewardVo getTaskReward() {
		return getReward(task);
	}

	public DailyRewardVo getProposeReward() {
		return getReward(propose);
	}

	public DailyRewardVo getBossmapBattleReward() {
		return getReward(bossmapBattle);
	}

	public DailyRewardVo getBattleReward() {
		return getReward(battle);
	}

	public DailyRewardVo getBuyConsortiaSkillReward() {
		return getReward(buyConsortiaSkill);
	}

	public DailyRewardVo getLoginReward() {
		return getReward(login);
	}

	public int getRewardedVal(int val, float rate) {
		if (val > 0)
			return (int) Math.ceil(val * rate);
		else
			return val;
	}

	public void saveLogActivitiesAward(DailyRewardVo dailyRewardVo, int playerId) {
		ServiceManager.getManager().getSchedulingService().saveLogActivitiesAward(dailyRewardVo, playerId);
	}

	public List<DailyActivityVo> getAllActive() {
		List<DailyActivityVo> list = new ArrayList<DailyActivityVo>();
		for (DailyActivityVo d : dailyActivityList) {
			if (d.isActive()) {
				list.add(d);
			}
		}
		Collections.sort(list, new Comparator<DailyActivityVo>() {
			@Override
			public int compare(DailyActivityVo o1, DailyActivityVo o2) {
				return o1.getDaysOfWeek() - o2.getDaysOfWeek();
			}
		});
		int dayofweek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		List<DailyActivityVo> result = new ArrayList<DailyActivityVo>();
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDaysOfWeek() >= dayofweek) {
				result.add(list.get(i));
			} else {
				index = i;
			}
		}
		if (index != -1) {
			for (int i = 0; i <= index; i++) {
				result.add(list.get(i));
			}
		}
		return result;
	}

	public void loginDailyActivity(int playerId) {
		ServiceManager.getManager().getSchedulingService().saveloginAward(getLoginReward(), playerId);
	}
}
