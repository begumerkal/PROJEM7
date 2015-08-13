package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Base1HeroExtId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class Base1HeroExtId implements java.io.Serializable {

	// Fields

	private Integer heroExtId;
	private Integer heroBaseId;
	private Integer nextHeroExtId;
	private String baseSkill;
	private Integer lv;
	private Integer experience;
	private Integer quality;
	private Integer color;
	private Integer force;
	private String baseEquip;
	private String baseProperty;
	private String weaponsId;
	private Integer exclusiveId;

	// Constructors

	/** default constructor */
	public Base1HeroExtId() {
	}

	/** full constructor */
	public Base1HeroExtId(Integer heroExtId, Integer heroBaseId,
			Integer nextHeroExtId, String baseSkill, Integer lv,
			Integer experience, Integer quality, Integer color, Integer force,
			String baseEquip, String baseProperty, String weaponsId,
			Integer exclusiveId) {
		this.heroExtId = heroExtId;
		this.heroBaseId = heroBaseId;
		this.nextHeroExtId = nextHeroExtId;
		this.baseSkill = baseSkill;
		this.lv = lv;
		this.experience = experience;
		this.quality = quality;
		this.color = color;
		this.force = force;
		this.baseEquip = baseEquip;
		this.baseProperty = baseProperty;
		this.weaponsId = weaponsId;
		this.exclusiveId = exclusiveId;
	}

	// Property accessors

	@Column(name = "hero_ext_id", unique = true, nullable = false)
	public Integer getHeroExtId() {
		return this.heroExtId;
	}

	public void setHeroExtId(Integer heroExtId) {
		this.heroExtId = heroExtId;
	}

	@Column(name = "hero_base_id", nullable = false)
	public Integer getHeroBaseId() {
		return this.heroBaseId;
	}

	public void setHeroBaseId(Integer heroBaseId) {
		this.heroBaseId = heroBaseId;
	}

	@Column(name = "next_hero_extId", nullable = false)
	public Integer getNextHeroExtId() {
		return this.nextHeroExtId;
	}

	public void setNextHeroExtId(Integer nextHeroExtId) {
		this.nextHeroExtId = nextHeroExtId;
	}

	@Column(name = "base_skill", nullable = false, length = 200)
	public String getBaseSkill() {
		return this.baseSkill;
	}

	public void setBaseSkill(String baseSkill) {
		this.baseSkill = baseSkill;
	}

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "experience", nullable = false)
	public Integer getExperience() {
		return this.experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	@Column(name = "quality", nullable = false)
	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	@Column(name = "color", nullable = false)
	public Integer getColor() {
		return this.color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	@Column(name = "force", nullable = false)
	public Integer getForce() {
		return this.force;
	}

	public void setForce(Integer force) {
		this.force = force;
	}

	@Column(name = "base_equip", nullable = false)
	public String getBaseEquip() {
		return this.baseEquip;
	}

	public void setBaseEquip(String baseEquip) {
		this.baseEquip = baseEquip;
	}

	@Column(name = "base_property", nullable = false, length = 256)
	public String getBaseProperty() {
		return this.baseProperty;
	}

	public void setBaseProperty(String baseProperty) {
		this.baseProperty = baseProperty;
	}

	@Column(name = "weapons_id", nullable = false, length = 11)
	public String getWeaponsId() {
		return this.weaponsId;
	}

	public void setWeaponsId(String weaponsId) {
		this.weaponsId = weaponsId;
	}

	@Column(name = "exclusive_id", nullable = false)
	public Integer getExclusiveId() {
		return this.exclusiveId;
	}

	public void setExclusiveId(Integer exclusiveId) {
		this.exclusiveId = exclusiveId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Base1HeroExtId))
			return false;
		Base1HeroExtId castOther = (Base1HeroExtId) other;

		return ((this.getHeroExtId() == castOther.getHeroExtId()) || (this
				.getHeroExtId() != null && castOther.getHeroExtId() != null && this
				.getHeroExtId().equals(castOther.getHeroExtId())))
				&& ((this.getHeroBaseId() == castOther.getHeroBaseId()) || (this
						.getHeroBaseId() != null
						&& castOther.getHeroBaseId() != null && this
						.getHeroBaseId().equals(castOther.getHeroBaseId())))
				&& ((this.getNextHeroExtId() == castOther.getNextHeroExtId()) || (this
						.getNextHeroExtId() != null
						&& castOther.getNextHeroExtId() != null && this
						.getNextHeroExtId()
						.equals(castOther.getNextHeroExtId())))
				&& ((this.getBaseSkill() == castOther.getBaseSkill()) || (this
						.getBaseSkill() != null
						&& castOther.getBaseSkill() != null && this
						.getBaseSkill().equals(castOther.getBaseSkill())))
				&& ((this.getLv() == castOther.getLv()) || (this.getLv() != null
						&& castOther.getLv() != null && this.getLv().equals(
						castOther.getLv())))
				&& ((this.getExperience() == castOther.getExperience()) || (this
						.getExperience() != null
						&& castOther.getExperience() != null && this
						.getExperience().equals(castOther.getExperience())))
				&& ((this.getQuality() == castOther.getQuality()) || (this
						.getQuality() != null && castOther.getQuality() != null && this
						.getQuality().equals(castOther.getQuality())))
				&& ((this.getColor() == castOther.getColor()) || (this
						.getColor() != null && castOther.getColor() != null && this
						.getColor().equals(castOther.getColor())))
				&& ((this.getForce() == castOther.getForce()) || (this
						.getForce() != null && castOther.getForce() != null && this
						.getForce().equals(castOther.getForce())))
				&& ((this.getBaseEquip() == castOther.getBaseEquip()) || (this
						.getBaseEquip() != null
						&& castOther.getBaseEquip() != null && this
						.getBaseEquip().equals(castOther.getBaseEquip())))
				&& ((this.getBaseProperty() == castOther.getBaseProperty()) || (this
						.getBaseProperty() != null
						&& castOther.getBaseProperty() != null && this
						.getBaseProperty().equals(castOther.getBaseProperty())))
				&& ((this.getWeaponsId() == castOther.getWeaponsId()) || (this
						.getWeaponsId() != null
						&& castOther.getWeaponsId() != null && this
						.getWeaponsId().equals(castOther.getWeaponsId())))
				&& ((this.getExclusiveId() == castOther.getExclusiveId()) || (this
						.getExclusiveId() != null
						&& castOther.getExclusiveId() != null && this
						.getExclusiveId().equals(castOther.getExclusiveId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getHeroExtId() == null ? 0 : this.getHeroExtId().hashCode());
		result = 37
				* result
				+ (getHeroBaseId() == null ? 0 : this.getHeroBaseId()
						.hashCode());
		result = 37
				* result
				+ (getNextHeroExtId() == null ? 0 : this.getNextHeroExtId()
						.hashCode());
		result = 37 * result
				+ (getBaseSkill() == null ? 0 : this.getBaseSkill().hashCode());
		result = 37 * result + (getLv() == null ? 0 : this.getLv().hashCode());
		result = 37
				* result
				+ (getExperience() == null ? 0 : this.getExperience()
						.hashCode());
		result = 37 * result
				+ (getQuality() == null ? 0 : this.getQuality().hashCode());
		result = 37 * result
				+ (getColor() == null ? 0 : this.getColor().hashCode());
		result = 37 * result
				+ (getForce() == null ? 0 : this.getForce().hashCode());
		result = 37 * result
				+ (getBaseEquip() == null ? 0 : this.getBaseEquip().hashCode());
		result = 37
				* result
				+ (getBaseProperty() == null ? 0 : this.getBaseProperty()
						.hashCode());
		result = 37 * result
				+ (getWeaponsId() == null ? 0 : this.getWeaponsId().hashCode());
		result = 37
				* result
				+ (getExclusiveId() == null ? 0 : this.getExclusiveId()
						.hashCode());
		return result;
	}

}