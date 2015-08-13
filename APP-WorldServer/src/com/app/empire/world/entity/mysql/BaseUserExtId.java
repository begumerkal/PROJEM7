package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BaseUserExtId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class BaseUserExtId implements java.io.Serializable {

	// Fields

	private String openModule;
	private String openNpc;
	private String sign;
	private Integer downAward;
	private Integer downDays;
	private Integer recoverEnergyTime;
	private Integer todayActCountTime;
	private Integer skillPointActTime;
	private Integer mallFreshTime;
	private Integer quartermasterBuyTime1;
	private Integer quartermasterBuyTime2;
	private Integer spreeTimes;
	private Integer quartermasterBuy;

	// Constructors

	/** default constructor */
	public BaseUserExtId() {
	}

	/** full constructor */
	public BaseUserExtId(String openModule, String openNpc, String sign,
			Integer downAward, Integer downDays, Integer recoverEnergyTime,
			Integer todayActCountTime, Integer skillPointActTime,
			Integer mallFreshTime, Integer quartermasterBuyTime1,
			Integer quartermasterBuyTime2, Integer spreeTimes,
			Integer quartermasterBuy) {
		this.openModule = openModule;
		this.openNpc = openNpc;
		this.sign = sign;
		this.downAward = downAward;
		this.downDays = downDays;
		this.recoverEnergyTime = recoverEnergyTime;
		this.todayActCountTime = todayActCountTime;
		this.skillPointActTime = skillPointActTime;
		this.mallFreshTime = mallFreshTime;
		this.quartermasterBuyTime1 = quartermasterBuyTime1;
		this.quartermasterBuyTime2 = quartermasterBuyTime2;
		this.spreeTimes = spreeTimes;
		this.quartermasterBuy = quartermasterBuy;
	}

	// Property accessors

	@Column(name = "open_module", nullable = false, length = 256)
	public String getOpenModule() {
		return this.openModule;
	}

	public void setOpenModule(String openModule) {
		this.openModule = openModule;
	}

	@Column(name = "open_npc", nullable = false, length = 256)
	public String getOpenNpc() {
		return this.openNpc;
	}

	public void setOpenNpc(String openNpc) {
		this.openNpc = openNpc;
	}

	@Column(name = "sign", nullable = false, length = 30)
	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(name = "down_award", nullable = false)
	public Integer getDownAward() {
		return this.downAward;
	}

	public void setDownAward(Integer downAward) {
		this.downAward = downAward;
	}

	@Column(name = "down_days", nullable = false)
	public Integer getDownDays() {
		return this.downDays;
	}

	public void setDownDays(Integer downDays) {
		this.downDays = downDays;
	}

	@Column(name = "recover_energy_time", nullable = false)
	public Integer getRecoverEnergyTime() {
		return this.recoverEnergyTime;
	}

	public void setRecoverEnergyTime(Integer recoverEnergyTime) {
		this.recoverEnergyTime = recoverEnergyTime;
	}

	@Column(name = "today_act_count_time", nullable = false)
	public Integer getTodayActCountTime() {
		return this.todayActCountTime;
	}

	public void setTodayActCountTime(Integer todayActCountTime) {
		this.todayActCountTime = todayActCountTime;
	}

	@Column(name = "skill_point_act_time", nullable = false)
	public Integer getSkillPointActTime() {
		return this.skillPointActTime;
	}

	public void setSkillPointActTime(Integer skillPointActTime) {
		this.skillPointActTime = skillPointActTime;
	}

	@Column(name = "mall_fresh_time", nullable = false)
	public Integer getMallFreshTime() {
		return this.mallFreshTime;
	}

	public void setMallFreshTime(Integer mallFreshTime) {
		this.mallFreshTime = mallFreshTime;
	}

	@Column(name = "quartermaster_buy_time1", nullable = false)
	public Integer getQuartermasterBuyTime1() {
		return this.quartermasterBuyTime1;
	}

	public void setQuartermasterBuyTime1(Integer quartermasterBuyTime1) {
		this.quartermasterBuyTime1 = quartermasterBuyTime1;
	}

	@Column(name = "quartermaster_buy_time2", nullable = false)
	public Integer getQuartermasterBuyTime2() {
		return this.quartermasterBuyTime2;
	}

	public void setQuartermasterBuyTime2(Integer quartermasterBuyTime2) {
		this.quartermasterBuyTime2 = quartermasterBuyTime2;
	}

	@Column(name = "spree_times", nullable = false)
	public Integer getSpreeTimes() {
		return this.spreeTimes;
	}

	public void setSpreeTimes(Integer spreeTimes) {
		this.spreeTimes = spreeTimes;
	}

	@Column(name = "quartermaster_buy", nullable = false)
	public Integer getQuartermasterBuy() {
		return this.quartermasterBuy;
	}

	public void setQuartermasterBuy(Integer quartermasterBuy) {
		this.quartermasterBuy = quartermasterBuy;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BaseUserExtId))
			return false;
		BaseUserExtId castOther = (BaseUserExtId) other;

		return ((this.getOpenModule() == castOther.getOpenModule()) || (this
				.getOpenModule() != null && castOther.getOpenModule() != null && this
				.getOpenModule().equals(castOther.getOpenModule())))
				&& ((this.getOpenNpc() == castOther.getOpenNpc()) || (this
						.getOpenNpc() != null && castOther.getOpenNpc() != null && this
						.getOpenNpc().equals(castOther.getOpenNpc())))
				&& ((this.getSign() == castOther.getSign()) || (this.getSign() != null
						&& castOther.getSign() != null && this.getSign()
						.equals(castOther.getSign())))
				&& ((this.getDownAward() == castOther.getDownAward()) || (this
						.getDownAward() != null
						&& castOther.getDownAward() != null && this
						.getDownAward().equals(castOther.getDownAward())))
				&& ((this.getDownDays() == castOther.getDownDays()) || (this
						.getDownDays() != null
						&& castOther.getDownDays() != null && this
						.getDownDays().equals(castOther.getDownDays())))
				&& ((this.getRecoverEnergyTime() == castOther
						.getRecoverEnergyTime()) || (this
						.getRecoverEnergyTime() != null
						&& castOther.getRecoverEnergyTime() != null && this
						.getRecoverEnergyTime().equals(
								castOther.getRecoverEnergyTime())))
				&& ((this.getTodayActCountTime() == castOther
						.getTodayActCountTime()) || (this
						.getTodayActCountTime() != null
						&& castOther.getTodayActCountTime() != null && this
						.getTodayActCountTime().equals(
								castOther.getTodayActCountTime())))
				&& ((this.getSkillPointActTime() == castOther
						.getSkillPointActTime()) || (this
						.getSkillPointActTime() != null
						&& castOther.getSkillPointActTime() != null && this
						.getSkillPointActTime().equals(
								castOther.getSkillPointActTime())))
				&& ((this.getMallFreshTime() == castOther.getMallFreshTime()) || (this
						.getMallFreshTime() != null
						&& castOther.getMallFreshTime() != null && this
						.getMallFreshTime()
						.equals(castOther.getMallFreshTime())))
				&& ((this.getQuartermasterBuyTime1() == castOther
						.getQuartermasterBuyTime1()) || (this
						.getQuartermasterBuyTime1() != null
						&& castOther.getQuartermasterBuyTime1() != null && this
						.getQuartermasterBuyTime1().equals(
								castOther.getQuartermasterBuyTime1())))
				&& ((this.getQuartermasterBuyTime2() == castOther
						.getQuartermasterBuyTime2()) || (this
						.getQuartermasterBuyTime2() != null
						&& castOther.getQuartermasterBuyTime2() != null && this
						.getQuartermasterBuyTime2().equals(
								castOther.getQuartermasterBuyTime2())))
				&& ((this.getSpreeTimes() == castOther.getSpreeTimes()) || (this
						.getSpreeTimes() != null
						&& castOther.getSpreeTimes() != null && this
						.getSpreeTimes().equals(castOther.getSpreeTimes())))
				&& ((this.getQuartermasterBuy() == castOther
						.getQuartermasterBuy()) || (this.getQuartermasterBuy() != null
						&& castOther.getQuartermasterBuy() != null && this
						.getQuartermasterBuy().equals(
								castOther.getQuartermasterBuy())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getOpenModule() == null ? 0 : this.getOpenModule()
						.hashCode());
		result = 37 * result
				+ (getOpenNpc() == null ? 0 : this.getOpenNpc().hashCode());
		result = 37 * result
				+ (getSign() == null ? 0 : this.getSign().hashCode());
		result = 37 * result
				+ (getDownAward() == null ? 0 : this.getDownAward().hashCode());
		result = 37 * result
				+ (getDownDays() == null ? 0 : this.getDownDays().hashCode());
		result = 37
				* result
				+ (getRecoverEnergyTime() == null ? 0 : this
						.getRecoverEnergyTime().hashCode());
		result = 37
				* result
				+ (getTodayActCountTime() == null ? 0 : this
						.getTodayActCountTime().hashCode());
		result = 37
				* result
				+ (getSkillPointActTime() == null ? 0 : this
						.getSkillPointActTime().hashCode());
		result = 37
				* result
				+ (getMallFreshTime() == null ? 0 : this.getMallFreshTime()
						.hashCode());
		result = 37
				* result
				+ (getQuartermasterBuyTime1() == null ? 0 : this
						.getQuartermasterBuyTime1().hashCode());
		result = 37
				* result
				+ (getQuartermasterBuyTime2() == null ? 0 : this
						.getQuartermasterBuyTime2().hashCode());
		result = 37
				* result
				+ (getSpreeTimes() == null ? 0 : this.getSpreeTimes()
						.hashCode());
		result = 37
				* result
				+ (getQuartermasterBuy() == null ? 0 : this
						.getQuartermasterBuy().hashCode());
		return result;
	}

}