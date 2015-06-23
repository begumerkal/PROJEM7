package com.wyd.empire.world.entity.mysql;

// default package
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TabLoginReward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_login_reward")
public class LoginReward implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private String areaId;
	private Integer minLevel;
	private Integer maxLevel;
	private String itemReward;
	private String itemRewardRemark;
	private String mailTitle;
	private String mailContent;

	// Constructors
	/** default constructor */
	public LoginReward() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lr_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "min_level")
	public Integer getMinLevel() {
		return this.minLevel;
	}

	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}

	@Column(name = "max_level")
	public Integer getMaxLevel() {
		return this.maxLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}

	@Column(name = "item_reward")
	public String getItemReward() {
		return this.itemReward;
	}

	public void setItemReward(String itemReward) {
		this.itemReward = itemReward;
	}

	@Column(name = "item_reward_remark", length = 500)
	public String getItemRewardRemark() {
		return this.itemRewardRemark;
	}

	public void setItemRewardRemark(String itemRewardRemark) {
		this.itemRewardRemark = itemRewardRemark;
	}

	@Column(name = "mail_title", length = 255)
	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	@Column(name = "mail_content", length = 500)
	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	@Column(name = "area_id", length = 10)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}