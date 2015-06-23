package com.wyd.empire.world.entity.mysql;

// default package
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

/**
 * TabPlayerFund entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_player_fund")
public class PlayerFund implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Player player;
	private Byte lowFund;
	private Byte middleFund;
	private Byte highFund;
	private Date updateTime;
	private String lowFundInfo;
	private String middleFundInfo;
	private String highFundInfo;

	// Constructors
	/** default constructor */
	public PlayerFund() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Column(name = "low_fund")
	public Byte getLowFund() {
		return this.lowFund;
	}

	public void setLowFund(Byte lowFund) {
		this.lowFund = lowFund;
	}

	@Column(name = "middle_fund")
	public Byte getMiddleFund() {
		return this.middleFund;
	}

	public void setMiddleFund(Byte middleFund) {
		this.middleFund = middleFund;
	}

	@Column(name = "high_fund")
	public Byte getHighFund() {
		return this.highFund;
	}

	public void setHighFund(Byte highFund) {
		this.highFund = highFund;
	}

	@Column(name = "update_time", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic()
	@Column(name = "low_fund_info", nullable = true, length = 500)
	public String getLowFundInfo() {
		return lowFundInfo;
	}

	public void setLowFundInfo(String lowFundInfo) {
		this.lowFundInfo = lowFundInfo;
	}

	@Basic()
	@Column(name = "middle_fund_info", nullable = true, length = 500)
	public String getMiddleFundInfo() {
		return middleFundInfo;
	}

	public void setMiddleFundInfo(String middleFundInfo) {
		this.middleFundInfo = middleFundInfo;
	}

	@Basic()
	@Column(name = "high_fund_info", nullable = true, length = 500)
	public String getHighFundInfo() {
		return highFundInfo;
	}

	public void setHighFundInfo(String highFundInfo) {
		this.highFundInfo = highFundInfo;
	}

}