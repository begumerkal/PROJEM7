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
 * 套卡属性配置表
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_cardgroup")
public class CardGroup implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int groupId; // 套卡ID
	private int addCritical; // 暴击
	private int addReduceCrit; // 免爆
	private int addWreckDefense; // 破防
	private int addInjuryFree; // 免伤
	private int addHp; // 生命
	private int addAttack; // 攻击
	private int addDefend; // 防御
	private int num; // 卡牌数量

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
	@Column(name = "group_id")
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Basic()
	@Column(name = "add_critical")
	public int getAddCritical() {
		return addCritical;
	}

	public void setAddCritical(int addCritical) {
		this.addCritical = addCritical;
	}

	@Basic()
	@Column(name = "add_reduce_crit")
	public int getAddReduceCrit() {
		return addReduceCrit;
	}

	public void setAddReduceCrit(int addReduceCrit) {
		this.addReduceCrit = addReduceCrit;
	}

	@Basic()
	@Column(name = "add_wreck_defense")
	public int getAddWreckDefense() {
		return addWreckDefense;
	}

	public void setAddWreckDefense(int addWreckDefense) {
		this.addWreckDefense = addWreckDefense;
	}

	@Basic()
	@Column(name = "add_injury_free")
	public int getAddInjuryFree() {
		return addInjuryFree;
	}

	public void setAddInjuryFree(int addInjuryFree) {
		this.addInjuryFree = addInjuryFree;
	}

	@Basic()
	@Column(name = "add_hp")
	public int getAddHp() {
		return addHp;
	}

	public void setAddHp(int addHp) {
		this.addHp = addHp;
	}

	@Basic()
	@Column(name = "add_attack")
	public int getAddAttack() {
		return addAttack;
	}

	public void setAddAttack(int addAttack) {
		this.addAttack = addAttack;
	}

	@Basic()
	@Column(name = "add_defend")
	public int getAddDefend() {
		return addDefend;
	}

	public void setAddDefend(int addDefend) {
		this.addDefend = addDefend;
	}

	@Basic()
	@Column(name = "num")
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}