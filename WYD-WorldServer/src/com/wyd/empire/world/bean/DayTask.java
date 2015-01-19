package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.server.service.base.impl.PlayerTaskTitleService;

/**
 * The persistent class for the tab_task database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_day_task")
public class DayTask implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String taskType; // 任务类型
	private String typeWeights; // 任务权值
	private String activateId; // 激活id
	private String disableId; // 禁用id
	private String target; // 条件
	private String targetWeights; // 条件权值
	private String targetInfo; // 条件参数
	private String reward; // 任务奖励
	private int quickCost; // 快速完成任务花费
	private int upLevelCost; // 提升奖励花费
	private List<Integer> typeList;// 任务类型列表
	private List<Integer> typeWeightsList;// 任务类型权值列表
	private List<Integer> activateIdList;// 激活id列表
	private List<Integer> disableIdList;// 禁用id列表
	private List<Integer> targetList;// 条件列表
	private List<Integer> targetWeightsList;// 条件权值列表
	private List<Info> targetInfoList;// 条件参数列表
	private List<RewardInfo> manRewardList;
	private List<RewardInfo> womRewardList;
	private java.util.Map<Integer, List<Integer>> manRewardMap;
	private java.util.Map<Integer, List<Integer>> womRewardMap;
	private List<Integer> expend;

	public DayTask() {
	}

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
	@Column(name = "task_type", length = 100)
	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Basic()
	@Column(name = "type_weights", length = 100)
	public String getTypeWeights() {
		return typeWeights;
	}

	public void setTypeWeights(String typeWeights) {
		this.typeWeights = typeWeights;
	}

	@Basic()
	@Column(name = "activate_id", length = 100)
	public String getActivateId() {
		return activateId;
	}

	public void setActivateId(String activateId) {
		this.activateId = activateId;
	}

	@Basic()
	@Column(name = "disable_id", length = 100)
	public String getDisableId() {
		return disableId;
	}

	public void setDisableId(String disableId) {
		this.disableId = disableId;
	}

	@Basic()
	@Column(name = "target", length = 100)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Basic()
	@Column(name = "target_weights", length = 100)
	public String getTargetWeights() {
		return targetWeights;
	}

	public void setTargetWeights(String targetWeights) {
		this.targetWeights = targetWeights;
	}

	@Basic()
	@Column(name = "target_info", length = 100)
	public String getTargetInfo() {
		return targetInfo;
	}

	public void setTargetInfo(String targetInfo) {
		this.targetInfo = targetInfo;
	}

	@Basic()
	@Column(name = "reward", length = 100)
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	@Basic()
	@Column(name = "quick_cost", precision = 10)
	public int getQuickCost() {
		return quickCost < 0 ? 0 : quickCost;
	}

	public void setQuickCost(int quickCost) {
		this.quickCost = quickCost;
	}

	@Basic()
	@Column(name = "up_level_cost", precision = 10)
	public int getUpLevelCost() {
		return upLevelCost < 0 ? 0 : upLevelCost;
	}

	public void setUpLevelCost(int upLevelCost) {
		this.upLevelCost = upLevelCost;
	}

	public void initData() {
		typeList = new ArrayList<Integer>();
		typeWeightsList = new ArrayList<Integer>();
		activateIdList = new ArrayList<Integer>();
		disableIdList = new ArrayList<Integer>();
		targetList = new ArrayList<Integer>();
		targetWeightsList = new ArrayList<Integer>();
		targetInfoList = new ArrayList<DayTask.Info>();
		String taskType = this.taskType.replace("[", "").replace("]", "");
		for (String type : taskType.split(",")) {
			typeList.add(Integer.parseInt(type));
		}
		String typeWeights = this.typeWeights.replace("[", "").replace("]", "");
		for (String weights : typeWeights.split(",")) {
			typeWeightsList.add(Integer.parseInt(weights));
		}
		String activateId = this.activateId.replace("[", "").replace("]", "");
		for (String id : activateId.split(",")) {
			activateIdList.add(Integer.parseInt(id));
		}
		String disableId = this.disableId.replace("[", "").replace("]", "");
		for (String id : disableId.split(",")) {
			disableIdList.add(Integer.parseInt(id));
		}
		String targets = this.target.replace("[", "").replace("]", "");
		for (String target : targets.split(",")) {
			targetList.add(Integer.parseInt(target));
		}
		String targetWeights = this.targetWeights.replace("[", "").replace("]", "");
		for (String weights : targetWeights.split(",")) {
			targetWeightsList.add(Integer.parseInt(weights));
		}
		for (String targetInfo : this.targetInfo.split("&")) {
			String[] infos = targetInfo.replace("[", "").replace("]", "").split(",");
			Info info = new Info();
			targetInfoList.add(info);
			info.setMaxLevel(Integer.parseInt(infos[0]));
			int num1 = Integer.parseInt(infos[1]);
			int num2 = Integer.parseInt(infos[2]);
			info.setMinNum(num1 > num2 ? num2 : num1);
			info.setMaxNum(num1 > num2 ? num1 : num2);
		}
		manRewardList = ServiceUtils.getRewardInfo(reward, 0);
		womRewardList = ServiceUtils.getRewardInfo(reward, 1);
		manRewardMap = new HashMap<Integer, List<Integer>>();
		womRewardMap = new HashMap<Integer, List<Integer>>();
		// vip最高等级等级10级，0表示非vip
		for (int vipLevel = 0; vipLevel <= 10; vipLevel++) {
			List<Integer> rcList = new ArrayList<Integer>();
			manRewardMap.put(vipLevel, rcList);
			for (RewardInfo ri : manRewardList) {
				for (int i = 0; i <= Common.TASK_TOP_LEVEL; i++) {
					rcList.add(PlayerTaskTitleService.getRewardAddition(vipLevel, ri, i, Common.TASK_TYPE_DAY));
				}
			}
			rcList = new ArrayList<Integer>();
			womRewardMap.put(vipLevel, rcList);
			for (RewardInfo ri : womRewardList) {
				for (int i = 0; i <= Common.TASK_TOP_LEVEL; i++) {
					rcList.add(PlayerTaskTitleService.getRewardAddition(vipLevel, ri, i, Common.TASK_TYPE_DAY));
				}
			}
		}
		expend = new ArrayList<Integer>();
		for (int i = 0; i <= Common.TASK_TOP_LEVEL; i++) {
			expend.add(PlayerTaskTitleService.getUpLevelCost(getUpLevelCost(), i));
		}
	}

	public class Info {
		private int minNum;
		private int maxNum;
		private int maxLevel;

		public int getMinNum() {
			return minNum;
		}

		public void setMinNum(int minNum) {
			this.minNum = minNum;
		}

		public int getMaxNum() {
			return maxNum;
		}

		public void setMaxNum(int maxNum) {
			this.maxNum = maxNum;
		}

		public int getMaxLevel() {
			return maxLevel;
		}

		public void setMaxLevel(int maxLevel) {
			this.maxLevel = maxLevel;
		}
	}

	@Transient
	public List<Integer> getTypeList() {
		return typeList;
	}

	@Transient
	public List<Integer> getTypeWeightsList() {
		return typeWeightsList;
	}

	@Transient
	public List<Integer> getActivateIdList() {
		return activateIdList;
	}

	@Transient
	public List<Integer> getDisableIdList() {
		return disableIdList;
	}

	@Transient
	public List<Integer> getTargetList() {
		return targetList;
	}

	@Transient
	public List<Integer> getTargetWeightsList() {
		return targetWeightsList;
	}

	@Transient
	public int getTargetValue(int level) {
		for (Info info : targetInfoList) {
			if (level <= info.getMaxLevel()) {
				return ServiceUtils.getRandomNum(info.getMinNum(), info.getMaxNum() + 1);
			}
		}
		return 0;
	}

	@Transient
	public List<RewardInfo> getRewardBySex(int sex) {
		if (0 == sex) {
			return manRewardList;
		} else {
			return womRewardList;
		}
	}

	@Transient
	public List<Integer> getExpendList() {
		return expend;
	}

	@Transient
	public List<Integer> getRewardAddition(int sex, int vipLevel, int level) {
		List<Integer> rewardList;
		if (0 == sex) {
			rewardList = manRewardMap.get(vipLevel);
		} else {
			rewardList = womRewardMap.get(vipLevel);
		}
		List<Integer> rList = new ArrayList<Integer>();
		for (Integer count : rewardList) {
			rList.add(PlayerTaskTitleService.getLevelAddition(count, level));
		}
		return rList;
	}
}