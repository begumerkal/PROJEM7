package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 升星信息表
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_stars_info")
public class StarsInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int level; // 星级（不能重复）
	private int goldRate; // 升星消耗金币系数
	private int needExp; // 升星需要的经验
	private int addHPRate; // 血量提升比率（0到100）
	private int addDFRate; // 防御提升比率（0到100）
	private int addATRate; // 攻击提升比率（0到100）
	private int addCriticalRate; // 暴击
	private int addReduceRate; // 免爆
	private int addWreckRate; // 破防

	public StarsInfo() {
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "level", precision = 2)
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "gold_rate", precision = 2)
	public int getGoldRate() {
		return goldRate;
	}

	public void setGoldRate(int goldRate) {
		this.goldRate = goldRate;
	}

	@Basic()
	@Column(name = "add_hp_rate", precision = 10)
	public int getAddHPRate() {
		return addHPRate;
	}

	public void setAddHPRate(int addHPRate) {
		this.addHPRate = addHPRate;
	}

	@Basic()
	@Column(name = "add_df_rate", precision = 10)
	public int getAddDFRate() {
		return addDFRate;
	}

	public void setAddDFRate(int addDFRate) {
		this.addDFRate = addDFRate;
	}

	@Basic()
	@Column(name = "add_at_rate", precision = 10)
	public int getAddATRate() {
		return addATRate;
	}

	public void setAddATRate(int addATRate) {
		this.addATRate = addATRate;
	}

	@Basic()
	@Column(name = "need_exp", precision = 10)
	public int getNeedExp() {
		return needExp;
	}

	public void setNeedExp(int needExp) {
		this.needExp = needExp;
	}

	@Basic()
	@Column(name = "add_critical_rate")
	public int getAddCriticalRate() {
		return addCriticalRate;
	}

	public void setAddCriticalRate(int addCriticalRate) {
		this.addCriticalRate = addCriticalRate;
	}

	@Basic()
	@Column(name = "add_reduce_rate")
	public int getAddReduceRate() {
		return addReduceRate;
	}

	public void setAddReduceRate(int addReduceRate) {
		this.addReduceRate = addReduceRate;
	}

	@Basic()
	@Column(name = "add_wreck_rate")
	public int getAddWreckRate() {
		return addWreckRate;
	}

	public void setAddWreckRate(int addWreckRate) {
		this.addWreckRate = addWreckRate;
	}
}