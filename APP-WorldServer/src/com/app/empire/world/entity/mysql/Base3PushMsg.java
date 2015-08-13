package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3PushMsg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_push_msg", catalog = "game3")
public class Base3PushMsg implements java.io.Serializable {

	// Fields

	private Integer id;
	private String gameContent;
	private Integer pushCondition;
	private String pushTime;
	private String pushContent;

	// Constructors

	/** default constructor */
	public Base3PushMsg() {
	}

	/** full constructor */
	public Base3PushMsg(Integer id, String gameContent, Integer pushCondition,
			String pushTime, String pushContent) {
		this.id = id;
		this.gameContent = gameContent;
		this.pushCondition = pushCondition;
		this.pushTime = pushTime;
		this.pushContent = pushContent;
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

	@Column(name = "game_content", nullable = false, length = 258)
	public String getGameContent() {
		return this.gameContent;
	}

	public void setGameContent(String gameContent) {
		this.gameContent = gameContent;
	}

	@Column(name = "push_condition", nullable = false)
	public Integer getPushCondition() {
		return this.pushCondition;
	}

	public void setPushCondition(Integer pushCondition) {
		this.pushCondition = pushCondition;
	}

	@Column(name = "push_time", nullable = false, length = 52)
	public String getPushTime() {
		return this.pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}

	@Column(name = "push_content", nullable = false, length = 512)
	public String getPushContent() {
		return this.pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

}