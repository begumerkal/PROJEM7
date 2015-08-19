package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Base3TaskConditionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class Base3TaskConditionId implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskId;
	private Integer order;
	private String conditionName;
	private Integer conditionType;
	private String conditionValue;
	private Integer conditionSum;
	private Integer sumAcc;
	private String guideXy;
	private String conditionChat;
	private String noinfo;
	private String conditionOptions;
	private Boolean open;

	// Constructors

	/** default constructor */
	public Base3TaskConditionId() {
	}

	/** full constructor */
	public Base3TaskConditionId(Integer id, Integer taskId, Integer order,
			String conditionName, Integer conditionType, String conditionValue,
			Integer conditionSum, Integer sumAcc, String guideXy,
			String conditionChat, String noinfo, String conditionOptions,
			Boolean open) {
		this.id = id;
		this.taskId = taskId;
		this.order = order;
		this.conditionName = conditionName;
		this.conditionType = conditionType;
		this.conditionValue = conditionValue;
		this.conditionSum = conditionSum;
		this.sumAcc = sumAcc;
		this.guideXy = guideXy;
		this.conditionChat = conditionChat;
		this.noinfo = noinfo;
		this.conditionOptions = conditionOptions;
		this.open = open;
	}

	// Property accessors

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "task_id", nullable = false)
	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "order", nullable = false)
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "condition_name", nullable = false, length = 200)
	public String getConditionName() {
		return this.conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	@Column(name = "condition_type", nullable = false)
	public Integer getConditionType() {
		return this.conditionType;
	}

	public void setConditionType(Integer conditionType) {
		this.conditionType = conditionType;
	}

	@Column(name = "condition_value", nullable = false, length = 64)
	public String getConditionValue() {
		return this.conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	@Column(name = "condition_sum", nullable = false)
	public Integer getConditionSum() {
		return this.conditionSum;
	}

	public void setConditionSum(Integer conditionSum) {
		this.conditionSum = conditionSum;
	}

	@Column(name = "sum_acc", nullable = false)
	public Integer getSumAcc() {
		return this.sumAcc;
	}

	public void setSumAcc(Integer sumAcc) {
		this.sumAcc = sumAcc;
	}

	@Column(name = "guide_xy", nullable = false, length = 60)
	public String getGuideXy() {
		return this.guideXy;
	}

	public void setGuideXy(String guideXy) {
		this.guideXy = guideXy;
	}

	@Column(name = "condition_chat", nullable = false, length = 250)
	public String getConditionChat() {
		return this.conditionChat;
	}

	public void setConditionChat(String conditionChat) {
		this.conditionChat = conditionChat;
	}

	@Column(name = "noinfo", nullable = false, length = 250)
	public String getNoinfo() {
		return this.noinfo;
	}

	public void setNoinfo(String noinfo) {
		this.noinfo = noinfo;
	}

	@Column(name = "condition_options", nullable = false, length = 50)
	public String getConditionOptions() {
		return this.conditionOptions;
	}

	public void setConditionOptions(String conditionOptions) {
		this.conditionOptions = conditionOptions;
	}

	@Column(name = "open", nullable = false)
	public Boolean getOpen() {
		return this.open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Base3TaskConditionId))
			return false;
		Base3TaskConditionId castOther = (Base3TaskConditionId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getTaskId() == castOther.getTaskId()) || (this
						.getTaskId() != null && castOther.getTaskId() != null && this
						.getTaskId().equals(castOther.getTaskId())))
				&& ((this.getOrder() == castOther.getOrder()) || (this
						.getOrder() != null && castOther.getOrder() != null && this
						.getOrder().equals(castOther.getOrder())))
				&& ((this.getConditionName() == castOther.getConditionName()) || (this
						.getConditionName() != null
						&& castOther.getConditionName() != null && this
						.getConditionName()
						.equals(castOther.getConditionName())))
				&& ((this.getConditionType() == castOther.getConditionType()) || (this
						.getConditionType() != null
						&& castOther.getConditionType() != null && this
						.getConditionType()
						.equals(castOther.getConditionType())))
				&& ((this.getConditionValue() == castOther.getConditionValue()) || (this
						.getConditionValue() != null
						&& castOther.getConditionValue() != null && this
						.getConditionValue().equals(
								castOther.getConditionValue())))
				&& ((this.getConditionSum() == castOther.getConditionSum()) || (this
						.getConditionSum() != null
						&& castOther.getConditionSum() != null && this
						.getConditionSum().equals(castOther.getConditionSum())))
				&& ((this.getSumAcc() == castOther.getSumAcc()) || (this
						.getSumAcc() != null && castOther.getSumAcc() != null && this
						.getSumAcc().equals(castOther.getSumAcc())))
				&& ((this.getGuideXy() == castOther.getGuideXy()) || (this
						.getGuideXy() != null && castOther.getGuideXy() != null && this
						.getGuideXy().equals(castOther.getGuideXy())))
				&& ((this.getConditionChat() == castOther.getConditionChat()) || (this
						.getConditionChat() != null
						&& castOther.getConditionChat() != null && this
						.getConditionChat()
						.equals(castOther.getConditionChat())))
				&& ((this.getNoinfo() == castOther.getNoinfo()) || (this
						.getNoinfo() != null && castOther.getNoinfo() != null && this
						.getNoinfo().equals(castOther.getNoinfo())))
				&& ((this.getConditionOptions() == castOther
						.getConditionOptions()) || (this.getConditionOptions() != null
						&& castOther.getConditionOptions() != null && this
						.getConditionOptions().equals(
								castOther.getConditionOptions())))
				&& ((this.getOpen() == castOther.getOpen()) || (this.getOpen() != null
						&& castOther.getOpen() != null && this.getOpen()
						.equals(castOther.getOpen())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getTaskId() == null ? 0 : this.getTaskId().hashCode());
		result = 37 * result
				+ (getOrder() == null ? 0 : this.getOrder().hashCode());
		result = 37
				* result
				+ (getConditionName() == null ? 0 : this.getConditionName()
						.hashCode());
		result = 37
				* result
				+ (getConditionType() == null ? 0 : this.getConditionType()
						.hashCode());
		result = 37
				* result
				+ (getConditionValue() == null ? 0 : this.getConditionValue()
						.hashCode());
		result = 37
				* result
				+ (getConditionSum() == null ? 0 : this.getConditionSum()
						.hashCode());
		result = 37 * result
				+ (getSumAcc() == null ? 0 : this.getSumAcc().hashCode());
		result = 37 * result
				+ (getGuideXy() == null ? 0 : this.getGuideXy().hashCode());
		result = 37
				* result
				+ (getConditionChat() == null ? 0 : this.getConditionChat()
						.hashCode());
		result = 37 * result
				+ (getNoinfo() == null ? 0 : this.getNoinfo().hashCode());
		result = 37
				* result
				+ (getConditionOptions() == null ? 0 : this
						.getConditionOptions().hashCode());
		result = 37 * result
				+ (getOpen() == null ? 0 : this.getOpen().hashCode());
		return result;
	}

}