package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 宠物表.
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_petitems")
public class PetItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String evoName; // 进化后的名字
	private String icon;
	private String evoIcon; // 进化后的图标
	private String picture;
	private String evoPicture; // 进化后的图相
	private Integer quality; // 类型0普通1高级
	private Integer initHp; // 初始生命
	private Integer initAttack; // 初始攻击
	private Integer initDefend; // 初始防御
	private Integer hpGrowth; // 生命成长
	private Integer attackGrowth; // 攻击成长
	private Integer defendGrowth; // 防御成长
	private PetSkill skill; // 技能
	private PetSkill evoSkill; // 进化后的技能
	private Integer maxLevel; // 最高等级
	private Integer money; // 宠物售价
	private Integer callLevel; // 召唤等级限制
	private int evoLevel; // 进化等级
	private Integer payType; // 宠物的支付类型
	private Integer version; // 宠物在客户端执行的版本1旧版，2新版

	@Basic()
	@Column(name = "evo_level", nullable = false, precision = 2)
	public int getEvoLevel() {
		return evoLevel;
	}

	public void setEvoLevel(int evoLevel) {
		this.evoLevel = evoLevel;
	}

	@Basic()
	@Column(name = "hp_growth", nullable = false, precision = 10)
	public Integer getHpGrowth() {
		return hpGrowth;
	}

	public void setHpGrowth(Integer hpGrowth) {
		this.hpGrowth = hpGrowth;
	}

	@Basic()
	@Column(name = "attack_growth", nullable = false, precision = 10)
	public Integer getAttackGrowth() {
		return attackGrowth;
	}

	public void setAttackGrowth(Integer attackGrowth) {
		this.attackGrowth = attackGrowth;
	}

	@Basic()
	@Column(name = "defend_growth", nullable = false, precision = 10)
	public Integer getDefendGrowth() {
		return defendGrowth;
	}

	public void setDefendGrowth(Integer defendGrowth) {
		this.defendGrowth = defendGrowth;
	}

	@Basic()
	@Column(name = "max_level", nullable = false, precision = 2)
	public Integer getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
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
	@Column(name = "name", nullable = false, length = 16)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "init_attack", nullable = false, precision = 10)
	public Integer getInitAttack() {
		return this.initAttack;
	}

	@Basic()
	@Column(name = "icon", nullable = false, length = 256)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setInitAttack(Integer initAttack) {
		this.initAttack = initAttack;
	}

	@Basic()
	@Column(name = "init_defend", nullable = false, precision = 10)
	public Integer getInitDefend() {
		return this.initDefend;
	}

	public void setInitDefend(Integer initDefend) {
		this.initDefend = initDefend;
	}

	@Basic()
	@Column(name = "init_HP", nullable = false, precision = 10)
	public Integer getInitHp() {
		return this.initHp;
	}

	public void setInitHp(Integer initHp) {
		this.initHp = initHp;
	}

	@Basic()
	@Column(name = "quality", nullable = false, precision = 4)
	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	@Basic()
	@Column(name = "picture", nullable = false, length = 256)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "skill_id", referencedColumnName = "id")
	public PetSkill getSkill() {
		return skill;
	}

	public void setSkill(PetSkill skill) {
		this.skill = skill;
	}

	@Basic()
	@Column(name = "money", nullable = false, precision = 10)
	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Basic()
	@Column(name = "call_level", nullable = false, precision = 2)
	public Integer getCallLevel() {
		return callLevel;
	}

	public void setCallLevel(Integer callLevel) {
		this.callLevel = callLevel;
	}

	@Basic()
	@Column(name = "evo_icon", nullable = false, length = 256)
	public String getEvoIcon() {
		return evoIcon;
	}

	public void setEvoIcon(String evoIcon) {
		this.evoIcon = evoIcon;
	}

	@Column(name = "evo_name", nullable = false, length = 16)
	public String getEvoName() {
		return evoName;
	}

	public void setEvoName(String evoName) {
		this.evoName = evoName;
	}

	@Basic()
	@Column(name = "evo_picture", nullable = false, length = 16)
	public String getEvoPicture() {
		return evoPicture;
	}

	public void setEvoPicture(String evoPicture) {
		this.evoPicture = evoPicture;
	}

	@Basic()
	@Column(name = "pay_type", nullable = false, precision = 2)
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "evo_skill_id", referencedColumnName = "id")
	public PetSkill getEvoSkill() {
		return evoSkill;
	}

	public void setEvoSkill(PetSkill evoSkill) {
		this.evoSkill = evoSkill;
	}

	@Basic()
	@Column(name = "version", nullable = false, precision = 2)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}