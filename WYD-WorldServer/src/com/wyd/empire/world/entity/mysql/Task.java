package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.log4j.Logger;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;

/**
 * The persistent class for the tab_task database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_task")
public class Task implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	// private String content;
	// private Date endDay;
	private int level; // 任务触发等级
	private String parentId; // 父任务id
	// private Integer parentNum;
	// private Integer prompt;
	private String reward; // 任务奖励
	// private Date startDay;
	private String target; // 任务目标脚本
	// private String targetDesc;
	// private String taskDesc;
	private String taskName; // 任务名称
	// private String taskScript; // 任务脚本
	private byte taskType; // 0：主线任务 ,1：活跃度任务
	// private byte taskSubType; // 任务子类型
	// private int completion; // 快速完成任务需要的钻石
	// private String inTime; // 任务进行的时间（发给客户端显示）
	private Date updateTime;
	private byte status;
	private List<String[]> targetTitleList;
	private int[] targetValueList;
	private List<RewardInfo> manRewardList;
	private List<RewardInfo> womRewardList;

	public Task() {
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

	// @Basic()
	// @Column(name = "content", length = 100)
	// public String getContent() {
	// return this.content;
	// }
	//
	// public void setContent(String content) {
	// this.content = content;
	// }
	// @Basic()
	// @Column(name = "endDay")
	// public Date getEndDay() {
	// return this.endDay;
	// }
	//
	// public void setEndDay(Date endDay) {
	// this.endDay = endDay;
	// }
	@Basic()
	@Column(name = "level", precision = 10)
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "parent_id", length = 30)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	// @Basic()
	// @Column(name = "parentNum", precision = 10)
	// public Integer getParentNum() {
	// return this.parentNum;
	// }
	//
	// public void setParentNum(Integer parentNum) {
	// this.parentNum = parentNum;
	// }
	// @Basic()
	// @Column(name = "prompt", precision = 10)
	// public Integer getPrompt() {
	// return this.prompt;
	// }
	//
	// public void setPrompt(Integer prompt) {
	// this.prompt = prompt;
	// }
	@Basic()
	@Column(name = "reward", length = 500)
	public String getReward() {
		return this.reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	// @Basic()
	// @Column(name = "startDay")
	// public Date getStartDay() {
	// return this.startDay;
	// }
	//
	// public void setStartDay(Date startDay) {
	// this.startDay = startDay;
	// }
	@Basic()
	@Column(name = "target", length = 50)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	// @Basic()
	// @Column(name = "targetDesc", length = 100)
	// public String getTargetDesc() {
	// return this.targetDesc;
	// }
	//
	// public void setTargetDesc(String targetDesc) {
	// this.targetDesc = targetDesc;
	// }
	// @Basic()
	// @Column(name = "taskDesc", length = 200)
	// public String getTaskDesc() {
	// return this.taskDesc;
	// }
	//
	// public void setTaskDesc(String taskDesc) {
	// this.taskDesc = taskDesc;
	// }
	@Basic()
	@Column(name = "task_name", length = 30)
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	// @Basic()
	// @Column(name = "task_script", length = 100)
	// public String getTaskScript() {
	// return this.taskScript;
	// }
	//
	// public void setTaskScript(String taskScript) {
	// this.taskScript = taskScript;
	// }

	@Basic()
	@Column(name = "task_type", precision = 3)
	public byte getTaskType() {
		return this.taskType;
	}

	public void setTaskType(byte taskType) {
		this.taskType = taskType;
	}

	// @Basic()
	// @Column(name = "task_sub_type", precision = 3)
	// public byte getTaskSubType() {
	// return taskSubType;
	// }
	//
	// public void setTaskSubType(byte taskSubType) {
	// this.taskSubType = taskSubType;
	// }
	//
	// @Basic()
	// @Column(name = "completion", precision = 10)
	// public int getCompletion() {
	// return completion;
	// }
	//
	// public void setCompletion(int completion) {
	// this.completion = completion;
	// }

	// @Basic()
	// @Column(name = "in_time", length = 30)
	// public String getInTime() {
	// return inTime;
	// }
	//
	// public void setInTime(String inTime) {
	// this.inTime = inTime;
	// }

	@Basic()
	@Column(name = "update_time", length = 19)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic()
	@Column(name = "status")
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public void initTargetData() {
		targetTitleList = new ArrayList<String[]>();
		String[] targers = target.split("&");
		targetValueList = new int[targers.length];
		int i = 0;
		for (String targer : targers) {
			String[] tempTargers = targer.split("=");
			if (tempTargers.length != 2) {
				Logger.getLogger(Task.class).error("Task InitTargetData Fail: " + id);
				continue;
			}
			targetTitleList.add(tempTargers[0].split(","));
			targetValueList[i] = Integer.parseInt(tempTargers[1]);
			i++;
		}
		manRewardList = ServiceUtils.getRewardInfo(reward, 0);
		womRewardList = ServiceUtils.getRewardInfo(reward, 1);
	}

	@Transient
	public List<String[]> getTargetTitleList() {
		return targetTitleList;
	}

	@Transient
	public int[] getTargetValueList() {
		return targetValueList;
	}

	@Transient
	public List<RewardInfo> getRewardBySex(int sex) {
		if (0 == sex) {
			return manRewardList;
		} else {
			return womRewardList;
		}
	}
}