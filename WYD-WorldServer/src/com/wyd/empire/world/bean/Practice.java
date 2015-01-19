package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_active_reward database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_practice")
public class Practice implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id; // 修炼等级主建id
	private String peculiarityValue;// '特殊属性值（玩家属性类型:增加数值|玩家属性类型:增加数值）',
	private Integer playerLeve; // 等级要求
	private int exp; // 所需经验
	private int lowMedalExchangeExp; // 普通勋章兑换经验值
	private int seniorMedalExchangeExp; // 高级勋章兑换经验值
	private int dayConsumptionNumber; // 日消耗数

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pr_id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "peculiarity_value", length = 500)
	public String getPeculiarityValue() {
		return peculiarityValue;
	}

	public void setPeculiarityValue(String peculiarityValue) {
		this.peculiarityValue = peculiarityValue;
	}

	@Basic()
	@Column(name = "player_leve", length = 3)
	public Integer getPlayerLeve() {
		return playerLeve;
	}

	public void setPlayerLeve(Integer playerLeve) {
		this.playerLeve = playerLeve;
	}

	@Basic()
	@Column(name = "exp")
	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Basic()
	@Column(name = "low_medal_exchange_exp")
	public int getLowMedalExchangeExp() {
		return lowMedalExchangeExp;
	}

	public void setLowMedalExchangeExp(int lowMedalExchangeExp) {
		this.lowMedalExchangeExp = lowMedalExchangeExp;
	}

	@Basic()
	@Column(name = "senior_medal_exchange_exp")
	public int getSeniorMedalExchangeExp() {
		return seniorMedalExchangeExp;
	}

	public void setSeniorMedalExchangeExp(int seniorMedalExchangeExp) {
		this.seniorMedalExchangeExp = seniorMedalExchangeExp;
	}

	@Basic()
	@Column(name = "day_consumption_number", length = 3)
	public int getDayConsumptionNumber() {
		return dayConsumptionNumber;
	}

	public void setDayConsumptionNumber(int dayConsumptionNumber) {
		this.dayConsumptionNumber = dayConsumptionNumber;
	}

}