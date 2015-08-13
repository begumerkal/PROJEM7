package com.app.empire.world.entity.mysql;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Base3TaskCondition entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_task_condition", catalog = "game3", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Base3TaskCondition implements java.io.Serializable {

	// Fields

	private Base3TaskConditionId id;

	// Constructors

	/** default constructor */
	public Base3TaskCondition() {
	}

	/** full constructor */
	public Base3TaskCondition(Base3TaskConditionId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "id", unique = true, nullable = false)),
			@AttributeOverride(name = "taskId", column = @Column(name = "task_id", nullable = false)),
			@AttributeOverride(name = "order", column = @Column(name = "order", nullable = false)),
			@AttributeOverride(name = "conditionName", column = @Column(name = "condition_name", nullable = false, length = 200)),
			@AttributeOverride(name = "conditionType", column = @Column(name = "condition_type", nullable = false)),
			@AttributeOverride(name = "conditionValue", column = @Column(name = "condition_value", nullable = false, length = 64)),
			@AttributeOverride(name = "conditionSum", column = @Column(name = "condition_sum", nullable = false)),
			@AttributeOverride(name = "sumAcc", column = @Column(name = "sum_acc", nullable = false)),
			@AttributeOverride(name = "guideXy", column = @Column(name = "guide_xy", nullable = false, length = 60)),
			@AttributeOverride(name = "conditionChat", column = @Column(name = "condition_chat", nullable = false, length = 250)),
			@AttributeOverride(name = "noinfo", column = @Column(name = "noinfo", nullable = false, length = 250)),
			@AttributeOverride(name = "conditionOptions", column = @Column(name = "condition_options", nullable = false, length = 50)),
			@AttributeOverride(name = "open", column = @Column(name = "open", nullable = false)) })
	public Base3TaskConditionId getId() {
		return this.id;
	}

	public void setId(Base3TaskConditionId id) {
		this.id = id;
	}

}