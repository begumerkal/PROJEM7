package com.app.empire.world.entity.mysql;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Base1HeroExt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_hero_ext", catalog = "game3", uniqueConstraints = @UniqueConstraint(columnNames = "hero_ext_id"))
public class Base1HeroExt implements java.io.Serializable {

	// Fields

	private Base1HeroExtId id;

	// Constructors

	/** default constructor */
	public Base1HeroExt() {
	}

	/** full constructor */
	public Base1HeroExt(Base1HeroExtId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "heroExtId", column = @Column(name = "hero_ext_id", unique = true, nullable = false)),
			@AttributeOverride(name = "heroBaseId", column = @Column(name = "hero_base_id", nullable = false)),
			@AttributeOverride(name = "nextHeroExtId", column = @Column(name = "next_hero_extId", nullable = false)),
			@AttributeOverride(name = "baseSkill", column = @Column(name = "base_skill", nullable = false, length = 200)),
			@AttributeOverride(name = "lv", column = @Column(name = "lv", nullable = false)),
			@AttributeOverride(name = "experience", column = @Column(name = "experience", nullable = false)),
			@AttributeOverride(name = "quality", column = @Column(name = "quality", nullable = false)),
			@AttributeOverride(name = "color", column = @Column(name = "color", nullable = false)),
			@AttributeOverride(name = "force", column = @Column(name = "force", nullable = false)),
			@AttributeOverride(name = "baseEquip", column = @Column(name = "base_equip", nullable = false)),
			@AttributeOverride(name = "baseProperty", column = @Column(name = "base_property", nullable = false, length = 256)),
			@AttributeOverride(name = "weaponsId", column = @Column(name = "weapons_id", nullable = false, length = 11)),
			@AttributeOverride(name = "exclusiveId", column = @Column(name = "exclusive_id", nullable = false)) })
	public Base1HeroExtId getId() {
		return this.id;
	}

	public void setId(Base1HeroExtId id) {
		this.id = id;
	}

}