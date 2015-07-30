package com.wyd.empire.world.entity.mysql;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BaseUserExt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_user_ext", catalog = "game3")
public class BaseUserExt implements java.io.Serializable {

	// Fields

	private BaseUserExtId id;

	// Constructors

	/** default constructor */
	public BaseUserExt() {
	}

	/** full constructor */
	public BaseUserExt(BaseUserExtId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "openModule", column = @Column(name = "open_module", nullable = false, length = 256)),
			@AttributeOverride(name = "openNpc", column = @Column(name = "open_npc", nullable = false, length = 256)),
			@AttributeOverride(name = "sign", column = @Column(name = "sign", nullable = false, length = 30)),
			@AttributeOverride(name = "downAward", column = @Column(name = "down_award", nullable = false)),
			@AttributeOverride(name = "downDays", column = @Column(name = "down_days", nullable = false)),
			@AttributeOverride(name = "recoverEnergyTime", column = @Column(name = "recover_energy_time", nullable = false)),
			@AttributeOverride(name = "todayActCountTime", column = @Column(name = "today_act_count_time", nullable = false)),
			@AttributeOverride(name = "skillPointActTime", column = @Column(name = "skill_point_act_time", nullable = false)),
			@AttributeOverride(name = "mallFreshTime", column = @Column(name = "mall_fresh_time", nullable = false)),
			@AttributeOverride(name = "quartermasterBuyTime1", column = @Column(name = "quartermaster_buy_time1", nullable = false)),
			@AttributeOverride(name = "quartermasterBuyTime2", column = @Column(name = "quartermaster_buy_time2", nullable = false)),
			@AttributeOverride(name = "spreeTimes", column = @Column(name = "spree_times", nullable = false)),
			@AttributeOverride(name = "quartermasterBuy", column = @Column(name = "quartermaster_buy", nullable = false)) })
	public BaseUserExtId getId() {
		return this.id;
	}

	public void setId(BaseUserExtId id) {
		this.id = id;
	}

}