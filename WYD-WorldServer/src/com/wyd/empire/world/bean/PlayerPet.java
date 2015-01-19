package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Transient;

/**
 * 玩家宠物表.
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_player_pets")
public class PlayerPet implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer id; //
	private int playerId; // 玩家ID
	private int level; // 级别
	private Date createTime; // 创建时间
	private PetItem pet; // 宠物
	private Integer petHP; // 宠物生命
	private Integer petAttack; // 宠物攻击
	private Integer petDefend; // 宠物防御
	private float culHP; // 培养生命
	private float culAttack; // 培养攻击
	private float culDefend; // 培养防御
	private int petExp; // 宠物经验
	private boolean isInUsed; // 是否在使用
	private Date trainEndTime; // 训练结束时间
	private PetSkill skill; // 技能
	private int inheritance; // 传承。0初始，1已传承，2已被传承，3已&被传承
	private int culHPNum; // 培养生命数值
	private int culAttackNum; // 培养攻击数值
	private int culDefendNum; // 培养防御数值

	@Basic()
	@Column(name = "cul_hp_num", length = 2)
	public int getCulHPNum() {
		return culHPNum;
	}

	public void setCulHPNum(int culHPNum) {
		this.culHPNum = culHPNum;
	}

	@Basic()
	@Column(name = "cul_attack_num", length = 2)
	public int getCulAttackNum() {
		return culAttackNum;
	}

	public void setCulAttackNum(int culAttackNum) {
		this.culAttackNum = culAttackNum;
	}

	@Basic()
	@Column(name = "cul_defend_num", length = 2)
	public int getCulDefendNum() {
		return culDefendNum;
	}

	public void setCulDefendNum(int culDefendNum) {
		this.culDefendNum = culDefendNum;
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
	@Column(name = "pet_level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "train_EndTime")
	public Date getTrainEndTime() {
		return trainEndTime;
	}

	public void setTrainEndTime(Date trainEndTime) {
		this.trainEndTime = trainEndTime;
	}

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
	@Column(name = "player_id")
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "createTime")
	public Date getCreateTime() {
		createTime = null == createTime ? new Date(System.currentTimeMillis()) : createTime;
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pet_id", referencedColumnName = "id")
	public PetItem getPet() {
		return pet;
	}

	public void setPet(PetItem pet) {
		this.pet = pet;
	}

	@Basic()
	@Column(name = "pet_hp")
	public Integer getPetHP() {
		return petHP;
	}

	public void setPetHP(Integer petHP) {
		this.petHP = petHP;
	}

	@Basic()
	@Column(name = "pet_attack")
	public Integer getPetAttack() {
		return petAttack;
	}

	public void setPetAttack(Integer petAttack) {
		this.petAttack = petAttack;
	}

	@Basic()
	@Column(name = "pet_defend")
	public Integer getPetDefend() {
		return petDefend;
	}

	public void setPetDefend(Integer petDefend) {
		this.petDefend = petDefend;
	}

	@Basic()
	@Column(name = "isInUsed")
	public boolean isInUsed() {
		return isInUsed;
	}

	public void setInUsed(boolean isInUsed) {
		this.isInUsed = isInUsed;
	}

	@Basic()
	@Column(name = "pet_exp")
	public int getPetExp() {
		return petExp;
	}

	public void setPetExp(int petExp) {
		this.petExp = petExp;
	}

	@Basic()
	@Column(name = "cul_hp")
	public float getCulHP() {
		return culHP;
	}

	public void setCulHP(float culHP) {
		this.culHP = culHP;
	}

	@Basic()
	@Column(name = "cul_attack")
	public float getCulAttack() {
		return culAttack;
	}

	public void setCulAttack(float culAttack) {
		this.culAttack = culAttack;
	}

	@Basic()
	@Column(name = "cul_defend")
	public float getCulDefend() {
		return culDefend;
	}

	public void setCulDefend(float culDefend) {
		this.culDefend = culDefend;
	}

	@Basic()
	@Column(name = "inheritance")
	public int getInheritance() {
		return inheritance;
	}

	public void setInheritance(int inheritance) {
		this.inheritance = inheritance;
	}

	/**
	 * 得到总生命
	 * 
	 * @return
	 */
	@Transient
	public int getHP() {
		return (int) (petHP + culHP);
	}

	/**
	 * 得到总攻击
	 * 
	 * @return
	 */
	@Transient
	public int getAttack() {
		return (int) (petAttack + culAttack);
	}

	/**
	 * 得到总防御
	 * 
	 * @return
	 */
	@Transient
	public int getDefend() {
		return (int) (petDefend + culDefend);
	}

	/**
	 * 宠物名称（自动区分进化前后）
	 * 
	 * @return
	 */
	@Transient
	public String getPetName() {
		if (getLevel() >= pet.getEvoLevel()) {
			return pet.getEvoName();
		}
		return pet.getName();
	}

	/**
	 * 宠物形像（自动区分进化前后）
	 * 
	 * @return
	 */
	@Transient
	public String getPetPicture() {
		if (getLevel() >= pet.getEvoLevel()) {
			return pet.getEvoPicture();
		}
		return pet.getPicture();
	}

	/**
	 * 宠物图标（自动区分进化前后）
	 * 
	 * @return
	 */
	@Transient
	public String getPetIcon() {
		if (getLevel() >= pet.getEvoLevel()) {
			return pet.getEvoIcon();
		}
		return pet.getIcon();
	}
}
