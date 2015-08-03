package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BaseTeamLvId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class BaseTeamLvId implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer experienceTop;
	private Integer energyTop;
	private Integer heroLvTop;
	private Integer maxHero;
	private String award;

	// Constructors

	/** default constructor */
	public BaseTeamLvId() {
	}

	/** full constructor */
	public BaseTeamLvId(Integer id, Integer experienceTop, Integer energyTop,
			Integer heroLvTop, Integer maxHero, String award) {
		this.id = id;
		this.experienceTop = experienceTop;
		this.energyTop = energyTop;
		this.heroLvTop = heroLvTop;
		this.maxHero = maxHero;
		this.award = award;
	}

	// Property accessors

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "experience_top", nullable = false)
	public Integer getExperienceTop() {
		return this.experienceTop;
	}

	public void setExperienceTop(Integer experienceTop) {
		this.experienceTop = experienceTop;
	}

	@Column(name = "energy_top", nullable = false)
	public Integer getEnergyTop() {
		return this.energyTop;
	}

	public void setEnergyTop(Integer energyTop) {
		this.energyTop = energyTop;
	}

	@Column(name = "hero_lv_top", nullable = false)
	public Integer getHeroLvTop() {
		return this.heroLvTop;
	}

	public void setHeroLvTop(Integer heroLvTop) {
		this.heroLvTop = heroLvTop;
	}

	@Column(name = "max_hero", nullable = false)
	public Integer getMaxHero() {
		return this.maxHero;
	}

	public void setMaxHero(Integer maxHero) {
		this.maxHero = maxHero;
	}

	@Column(name = "award", nullable = false, length = 512)
	public String getAward() {
		return this.award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BaseTeamLvId))
			return false;
		BaseTeamLvId castOther = (BaseTeamLvId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getExperienceTop() == castOther.getExperienceTop()) || (this
						.getExperienceTop() != null
						&& castOther.getExperienceTop() != null && this
						.getExperienceTop()
						.equals(castOther.getExperienceTop())))
				&& ((this.getEnergyTop() == castOther.getEnergyTop()) || (this
						.getEnergyTop() != null
						&& castOther.getEnergyTop() != null && this
						.getEnergyTop().equals(castOther.getEnergyTop())))
				&& ((this.getHeroLvTop() == castOther.getHeroLvTop()) || (this
						.getHeroLvTop() != null
						&& castOther.getHeroLvTop() != null && this
						.getHeroLvTop().equals(castOther.getHeroLvTop())))
				&& ((this.getMaxHero() == castOther.getMaxHero()) || (this
						.getMaxHero() != null && castOther.getMaxHero() != null && this
						.getMaxHero().equals(castOther.getMaxHero())))
				&& ((this.getAward() == castOther.getAward()) || (this
						.getAward() != null && castOther.getAward() != null && this
						.getAward().equals(castOther.getAward())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37
				* result
				+ (getExperienceTop() == null ? 0 : this.getExperienceTop()
						.hashCode());
		result = 37 * result
				+ (getEnergyTop() == null ? 0 : this.getEnergyTop().hashCode());
		result = 37 * result
				+ (getHeroLvTop() == null ? 0 : this.getHeroLvTop().hashCode());
		result = 37 * result
				+ (getMaxHero() == null ? 0 : this.getMaxHero().hashCode());
		result = 37 * result
				+ (getAward() == null ? 0 : this.getAward().hashCode());
		return result;
	}

}