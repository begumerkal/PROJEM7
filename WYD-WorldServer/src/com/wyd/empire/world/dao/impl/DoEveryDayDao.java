package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.bean.DoEveryDay;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.dao.IDoEveryDayDao;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class DoEveryDayDao extends UniversalDaoHibernate implements IDoEveryDayDao {
	private static final String formatString = "HHmm";
	Logger log = Logger.getLogger(DoEveryDayDao.class);
	// Map<每日必做，我要变强 ＩＤ, 每日必做，我要变强 列表>
	private Map<Integer, DoEveryDay> allDoEveryDayMap = new ConcurrentHashMap<Integer, DoEveryDay>();

	public DoEveryDayDao() {
		super();
	}

	/**
	 * 获取每日必做,我要变强表最大ID
	 * 
	 * @param playerId
	 * @return
	 */
	public Object getMaxId() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("select max(id) FROM tab_doeveryday limit 1");
		return getClassObj(hql.toString(), 1, values.toArray());
	}

	public Map<Integer, DoEveryDay> getAllDoEveryDayMap() {
		return allDoEveryDayMap;
	}

	/**
	 * 初始化每日必做,我要变强列表
	 */
	@SuppressWarnings("unchecked")
	public void initDoEveryDay() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + DoEveryDay.class.getSimpleName());
		List<DoEveryDay> allList = getList(hql.toString(), values.toArray());
		for (DoEveryDay doEveryDay : allList) {
			doEveryDay.setNormalState();
			allDoEveryDayMap.put(doEveryDay.getId(), doEveryDay);
		}
	}

	/**
	 * 根据类型返回每日必做，我要变强 数据列表
	 * 
	 * @param type
	 *            1：每日必做，2：我要变强
	 * @return
	 */
	public List<DoEveryDay> getDoEveryDayListByType(byte type) {
		List<DoEveryDay> list = new ArrayList<DoEveryDay>();
		for (DoEveryDay value : allDoEveryDayMap.values()) {
			if (value.getType() == type) {
				list.add(value);
			}
		}
		return list;
	}

	/**
	 * 获取每日必做列表
	 * 
	 * @return
	 */
	public List<DoEveryDayVo> getDoEveryDayList() {
		List<DoEveryDay> list = getDoEveryDayListByType((byte) 1);
		List<DoEveryDayVo> allDayList = new ArrayList<DoEveryDayVo>();
		List<DoEveryDayVo> activateList = new ArrayList<DoEveryDayVo>();
		DoEveryDay ded;
		for (int i = list.size() - 1; i >= 0; i--) {
			try {
				ded = list.get(i);
				// 全天全时段任务
				if ("all".equals(ded.getDoTime()) && "all".equals(ded.getDoDay())) {
					DoEveryDayVo dedv = new DoEveryDayVo(list.remove(i));
					dedv.setActivate(true);
					allDayList.add(dedv);
				} else {
					boolean activate = false;
					if (null != ded.getDoTime() && null != ded.getDoDay()) {
						boolean check = false;
						// 分时段任务
						if (!"all".equals(ded.getDoTime())) {
							String[] doTime = ded.getDoTime().split("-");
							int sdt = Integer.parseInt(doTime[0].replace(":", ""));
							int edt = Integer.parseInt(doTime[1].replace(":", ""));
							int ndt = Integer.parseInt(DateUtil.format(new Date(), formatString));
							if (ndt >= sdt && ndt <= edt) {
								check = true;
							}
						} else {
							check = true;
						}
						// 如果在任务触发时段内则判断是否在任务的触发日期内
						if (check) {
							// 分日期任务
							if (!"all".equals(ded.getDoDay())) {
								String doDay = ded.getDoDay();
								if (doDay.startsWith("week:")) {// 周循环任务
									doDay = doDay.replace("week:", "");
									String[] days = doDay.split(",");
									for (String day : days) {
										if (Integer.parseInt(day) == DateUtil.getDayOfWeek(new Date())) {
											activate = true;
											break;
										}
									}
								}
								if (doDay.startsWith("month:")) {// 月循环任务
									doDay = doDay.replace("month:", "");
									String[] days = doDay.split(",");
									for (String day : days) {
										if (Integer.parseInt(day) == DateUtil.getDayOfMonth(new Date())) {
											activate = true;
											break;
										}
									}
								}
							} else {
								activate = true;
							}
						}
					}
					if (activate) {
						DoEveryDayVo dedv = new DoEveryDayVo(list.remove(i));
						dedv.setActivate(true);
						activateList.add(dedv);
					}
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		activateList.addAll(allDayList);
		for (DoEveryDay de : list) {
			activateList.add(new DoEveryDayVo(de));
		}
		return activateList;
	}

	public class DoEveryDayVo {
		private DoEveryDay ded;
		private boolean activate;

		public DoEveryDayVo(DoEveryDay ded) {
			this.ded = ded;
		}

		public DoEveryDay getDed() {
			return ded;
		}

		public void setDed(DoEveryDay ded) {
			this.ded = ded;
		}

		public boolean isActivate() {
			return activate;
		}

		public void setActivate(boolean activate) {
			this.activate = activate;
		}
	}
}