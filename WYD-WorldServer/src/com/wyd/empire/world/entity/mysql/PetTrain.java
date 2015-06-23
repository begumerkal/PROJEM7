package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_pettrain")
public class PetTrain implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer petLevel; // 宠物级别
	private Integer needExp; // 升级所需要的经验
	private Integer goldNeedExp; // 高级宠物升级所需经验
	private Integer trainExp; // 训练一次所得经验
	private Integer trainTime; // 训练一次所需要时间（分）
	private Integer trainPrice; // 训练一次所需要费用（金币）

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PetTrain)) {
			return false;
		}
		PetTrain castOther = (PetTrain) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
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
	@Column(name = "need_exp", nullable = false, precision = 10)
	public Integer getNeedExp() {
		return needExp;
	}

	public void setNeedExp(Integer needExp) {
		this.needExp = needExp;
	}

	@Basic()
	@Column(name = "train_exp", nullable = false, precision = 10)
	public Integer getTrainExp() {
		return trainExp;
	}

	public void setTrainExp(Integer trainExp) {
		this.trainExp = trainExp;
	}

	@Basic()
	@Column(name = "train_time", nullable = false, precision = 10)
	public Integer getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(Integer trainTime) {
		this.trainTime = trainTime;
	}

	@Basic()
	@Column(name = "train_price", nullable = false, precision = 10)
	public Integer getTrainPrice() {
		return trainPrice;
	}

	public void setTrainPrice(Integer trainPrice) {
		this.trainPrice = trainPrice;
	}

	@Basic()
	@Column(name = "gold_need_exp", nullable = false, precision = 10)
	public Integer getGoldNeedExp() {
		return goldNeedExp;
	}

	public void setGoldNeedExp(Integer goldNeedExp) {
		this.goldNeedExp = goldNeedExp;
	}

	/**
	 * 取升级需要的经验
	 * 
	 * @param quality
	 *            宠物品质0普通1特
	 * @return
	 */
	@Transient
	public int getUpLevelExp(int quality) {
		return quality == 0 ? getNeedExp() : getGoldNeedExp();
	}

}