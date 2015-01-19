package com.wyd.empire.world.bean;

//default package
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TabRechargeReward entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_download_reward")
public class DownloadReward implements java.io.Serializable {
	/**
	  * 
	  */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private int playerLv;
	private String reward;

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

	@Column(name = "playerLv", precision = 10)
	public int getPlayerLv() {
		return this.playerLv;
	}

	public void setPlayerLv(int playerLv) {
		this.playerLv = playerLv;
	}

	@Column(name = "reward", length = 255)
	public String getReward() {
		return this.reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
}