package com.wyd.empire.world.entity.mysql;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * BaseTeamLv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_team_lv", catalog = "game3", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class BaseTeamLv implements java.io.Serializable {

	// Fields

	private BaseTeamLvId id;

	// Constructors

	/** default constructor */
	public BaseTeamLv() {
	}

	/** full constructor */
	public BaseTeamLv(BaseTeamLvId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "id", unique = true, nullable = false)),
			@AttributeOverride(name = "experienceTop", column = @Column(name = "experience_top", nullable = false)),
			@AttributeOverride(name = "energyTop", column = @Column(name = "energy_top", nullable = false)),
			@AttributeOverride(name = "heroLvTop", column = @Column(name = "hero_lv_top", nullable = false)),
			@AttributeOverride(name = "maxHero", column = @Column(name = "max_hero", nullable = false)),
			@AttributeOverride(name = "award", column = @Column(name = "award", nullable = false, length = 512)) })
	public BaseTeamLvId getId() {
		return this.id;
	}

	public void setId(BaseTeamLvId id) {
		this.id = id;
	}

}