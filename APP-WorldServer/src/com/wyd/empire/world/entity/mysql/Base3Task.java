package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3Task entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_task", catalog = "game3")
public class Base3Task implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskPackId;
	private String name;
	private Boolean type;
	private Integer order;
	private Integer receiveNpcId;
	private Integer completeNpcId;
	private Integer activationTaskId;
	private String activationCondition;
	private Integer reciveType;
	private String reciveCondition;
	private String reciveChat;
	private String reciveAward;
	private String reciveAct;
	private String completeChat;
	private String completeAward;
	private Integer goldAward;
	private Integer experienceAward;
	private String completeAct;
	private String info;
	private String taskDes;
	private Integer star;
	private Integer runTime;
	private Integer cycleTime;
	private Integer cycleCount;
	private Integer cycleIntervalTime;
	private Integer isQuit;
	private Integer isOver;

	// Constructors

	/** default constructor */
	public Base3Task() {
	}

	/** full constructor */
	public Base3Task(Integer id, Integer taskPackId, String name, Boolean type,
			Integer order, Integer receiveNpcId, Integer completeNpcId,
			Integer activationTaskId, String activationCondition,
			Integer reciveType, String reciveCondition, String reciveChat,
			String reciveAward, String reciveAct, String completeChat,
			String completeAward, Integer goldAward, Integer experienceAward,
			String completeAct, String info, String taskDes, Integer star,
			Integer runTime, Integer cycleTime, Integer cycleCount,
			Integer cycleIntervalTime, Integer isQuit, Integer isOver) {
		this.id = id;
		this.taskPackId = taskPackId;
		this.name = name;
		this.type = type;
		this.order = order;
		this.receiveNpcId = receiveNpcId;
		this.completeNpcId = completeNpcId;
		this.activationTaskId = activationTaskId;
		this.activationCondition = activationCondition;
		this.reciveType = reciveType;
		this.reciveCondition = reciveCondition;
		this.reciveChat = reciveChat;
		this.reciveAward = reciveAward;
		this.reciveAct = reciveAct;
		this.completeChat = completeChat;
		this.completeAward = completeAward;
		this.goldAward = goldAward;
		this.experienceAward = experienceAward;
		this.completeAct = completeAct;
		this.info = info;
		this.taskDes = taskDes;
		this.star = star;
		this.runTime = runTime;
		this.cycleTime = cycleTime;
		this.cycleCount = cycleCount;
		this.cycleIntervalTime = cycleIntervalTime;
		this.isQuit = isQuit;
		this.isOver = isOver;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "task_pack_id", nullable = false)
	public Integer getTaskPackId() {
		return this.taskPackId;
	}

	public void setTaskPackId(Integer taskPackId) {
		this.taskPackId = taskPackId;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", nullable = false)
	public Boolean getType() {
		return this.type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	@Column(name = "order", nullable = false)
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "receive_npc_id", nullable = false)
	public Integer getReceiveNpcId() {
		return this.receiveNpcId;
	}

	public void setReceiveNpcId(Integer receiveNpcId) {
		this.receiveNpcId = receiveNpcId;
	}

	@Column(name = "complete_npc_id", nullable = false)
	public Integer getCompleteNpcId() {
		return this.completeNpcId;
	}

	public void setCompleteNpcId(Integer completeNpcId) {
		this.completeNpcId = completeNpcId;
	}

	@Column(name = "activation_task_id", nullable = false)
	public Integer getActivationTaskId() {
		return this.activationTaskId;
	}

	public void setActivationTaskId(Integer activationTaskId) {
		this.activationTaskId = activationTaskId;
	}

	@Column(name = "activation_condition", nullable = false, length = 128)
	public String getActivationCondition() {
		return this.activationCondition;
	}

	public void setActivationCondition(String activationCondition) {
		this.activationCondition = activationCondition;
	}

	@Column(name = "recive_type", nullable = false)
	public Integer getReciveType() {
		return this.reciveType;
	}

	public void setReciveType(Integer reciveType) {
		this.reciveType = reciveType;
	}

	@Column(name = "recive_condition", nullable = false, length = 128)
	public String getReciveCondition() {
		return this.reciveCondition;
	}

	public void setReciveCondition(String reciveCondition) {
		this.reciveCondition = reciveCondition;
	}

	@Column(name = "recive_chat", nullable = false, length = 512)
	public String getReciveChat() {
		return this.reciveChat;
	}

	public void setReciveChat(String reciveChat) {
		this.reciveChat = reciveChat;
	}

	@Column(name = "recive_award", nullable = false, length = 256)
	public String getReciveAward() {
		return this.reciveAward;
	}

	public void setReciveAward(String reciveAward) {
		this.reciveAward = reciveAward;
	}

	@Column(name = "recive_act", nullable = false, length = 256)
	public String getReciveAct() {
		return this.reciveAct;
	}

	public void setReciveAct(String reciveAct) {
		this.reciveAct = reciveAct;
	}

	@Column(name = "complete_chat", nullable = false, length = 512)
	public String getCompleteChat() {
		return this.completeChat;
	}

	public void setCompleteChat(String completeChat) {
		this.completeChat = completeChat;
	}

	@Column(name = "complete_award", nullable = false, length = 256)
	public String getCompleteAward() {
		return this.completeAward;
	}

	public void setCompleteAward(String completeAward) {
		this.completeAward = completeAward;
	}

	@Column(name = "gold_award", nullable = false)
	public Integer getGoldAward() {
		return this.goldAward;
	}

	public void setGoldAward(Integer goldAward) {
		this.goldAward = goldAward;
	}

	@Column(name = "experience_award", nullable = false)
	public Integer getExperienceAward() {
		return this.experienceAward;
	}

	public void setExperienceAward(Integer experienceAward) {
		this.experienceAward = experienceAward;
	}

	@Column(name = "complete_act", nullable = false, length = 256)
	public String getCompleteAct() {
		return this.completeAct;
	}

	public void setCompleteAct(String completeAct) {
		this.completeAct = completeAct;
	}

	@Column(name = "info", nullable = false, length = 65535)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "task_des", nullable = false, length = 512)
	public String getTaskDes() {
		return this.taskDes;
	}

	public void setTaskDes(String taskDes) {
		this.taskDes = taskDes;
	}

	@Column(name = "star", nullable = false)
	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	@Column(name = "run_time", nullable = false)
	public Integer getRunTime() {
		return this.runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	@Column(name = "cycle_time", nullable = false)
	public Integer getCycleTime() {
		return this.cycleTime;
	}

	public void setCycleTime(Integer cycleTime) {
		this.cycleTime = cycleTime;
	}

	@Column(name = "cycle_count", nullable = false)
	public Integer getCycleCount() {
		return this.cycleCount;
	}

	public void setCycleCount(Integer cycleCount) {
		this.cycleCount = cycleCount;
	}

	@Column(name = "cycle_Interval_time", nullable = false)
	public Integer getCycleIntervalTime() {
		return this.cycleIntervalTime;
	}

	public void setCycleIntervalTime(Integer cycleIntervalTime) {
		this.cycleIntervalTime = cycleIntervalTime;
	}

	@Column(name = "is_quit", nullable = false)
	public Integer getIsQuit() {
		return this.isQuit;
	}

	public void setIsQuit(Integer isQuit) {
		this.isQuit = isQuit;
	}

	@Column(name = "is_over", nullable = false)
	public Integer getIsOver() {
		return this.isOver;
	}

	public void setIsOver(Integer isOver) {
		this.isOver = isOver;
	}

}