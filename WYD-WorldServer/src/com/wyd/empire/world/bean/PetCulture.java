package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 宠物培养表.
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_petculture")
public class PetCulture implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer petLevel; // 宠物级别
	private Integer normalHpMax; // 普通宠物生命培养上限
	private Integer normalAtkMax; // 普通宠物攻击培养上限
	private Integer normalDefMax; // 普通宠物防御培养上限
	private Integer goldHpMax; // 金色宠物培养上限
	private Integer goldAtkMax; // 金色宠物培养上限
	private Integer goldDefMax; // 金色宠物培养上限
	private Integer cultureGold; // 培养消耗金币
	private Integer cultureDiamond; // 培养消耗钻石
	private float hpCoef; // 血量系数
	private float attackCoef; // 攻击系数
	private float defendCoef; // 防御系数

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "pet_level", nullable = false, precision = 2)
	public Integer getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(Integer petLevel) {
		this.petLevel = petLevel;
	}

	@Basic()
	@Column(name = "normal_hp_max", nullable = false, precision = 10)
	public Integer getNormalHpMax() {
		return normalHpMax;
	}

	public void setNormalHpMax(Integer normalHpMax) {
		this.normalHpMax = normalHpMax;
	}

	@Basic()
	@Column(name = "gold_hp_max", nullable = false, precision = 10)
	public Integer getGoldHpMax() {
		return goldHpMax;
	}

	public void setGoldHpMax(Integer goldHpMax) {
		this.goldHpMax = goldHpMax;
	}

	@Basic()
	@Column(name = "culture_gold", nullable = false, precision = 10)
	public Integer getCultureGold() {
		return cultureGold;
	}

	public void setCultureGold(Integer cultureGold) {
		this.cultureGold = cultureGold;
	}

	@Basic()
	@Column(name = "culture_diamond", nullable = false, precision = 10)
	public Integer getCultureDiamond() {
		return cultureDiamond;
	}

	public void setCultureDiamond(Integer cultureDiamond) {
		this.cultureDiamond = cultureDiamond;
	}

	@Basic()
	@Column(name = "hp_coef", nullable = false, precision = 2)
	public float getHpCoef() {
		return hpCoef;
	}

	public void setHpCoef(float hpCoef) {
		this.hpCoef = hpCoef;
	}

	@Basic()
	@Column(name = "attack_coef", nullable = false, precision = 2)
	public float getAttackCoef() {
		return attackCoef;
	}

	public void setAttackCoef(float attackCoef) {
		this.attackCoef = attackCoef;
	}

	@Basic()
	@Column(name = "defend_coef", nullable = false, precision = 2)
	public float getDefendCoef() {
		return defendCoef;
	}

	public void setDefendCoef(float defendCoef) {
		this.defendCoef = defendCoef;
	}

	@Basic()
	@Column(name = "normal_atk_max", nullable = false, precision = 10)
	public Integer getNormalAtkMax() {
		return normalAtkMax;
	}

	public void setNormalAtkMax(Integer normalAtkMax) {
		this.normalAtkMax = normalAtkMax;
	}

	@Basic()
	@Column(name = "normal_def_max", nullable = false, precision = 10)
	public Integer getNormalDefMax() {
		return normalDefMax;
	}

	public void setNormalDefMax(Integer normalDefMax) {
		this.normalDefMax = normalDefMax;
	}

	@Basic()
	@Column(name = "gold_atk_max", nullable = false, precision = 10)
	public Integer getGoldAtkMax() {
		return goldAtkMax;
	}

	public void setGoldAtkMax(Integer goldAtkMax) {
		this.goldAtkMax = goldAtkMax;
	}

	@Basic()
	@Column(name = "gold_def_max", nullable = false, precision = 10)
	public Integer getGoldDefMax() {
		return goldDefMax;
	}

	public void setGoldDefMax(Integer goldDefMax) {
		this.goldDefMax = goldDefMax;
	}

	/**
	 * 取生命培养上限
	 * 
	 * @param quality
	 *            宠物品质0普通1特
	 * @return
	 */
	@Transient
	public int getHpMax(int quality) {
		return quality == 0 ? getNormalHpMax() : getGoldHpMax();
	}

	/**
	 * 取攻击培养上限
	 * 
	 * @param quality
	 *            宠物品质0普通1特
	 * @return
	 */
	@Transient
	public int getAtkMax(int quality) {
		return quality == 0 ? getNormalAtkMax() : getGoldAtkMax();
	}

	/**
	 * 取防御培养上限
	 * 
	 * @param quality
	 *            宠物品质0普通1特
	 * @return
	 */
	@Transient
	public int getDefMax(int quality) {
		return quality == 0 ? getNormalDefMax() : getGoldDefMax();
	}

}