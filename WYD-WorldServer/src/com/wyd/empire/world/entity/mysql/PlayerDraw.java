package com.wyd.empire.world.entity.mysql;

// default package
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * LogFundRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_player_draw")
public class PlayerDraw implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// Fields
	private int id;
	private int playerId;
	private String drawNum;
	private String drawFailNum;
	private String starNum;
	private String refreshNum;

	// Constructors
	/** default constructor */
	public PlayerDraw() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "playerId", precision = 10)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "drawNum")
	public String getDrawNum() {
		return drawNum;
	}

	public void setDrawNum(String drawNum) {
		this.drawNum = drawNum;
	}

	@Basic()
	@Column(name = "drawFailNum")
	public String getDrawFailNum() {
		return drawFailNum;
	}

	public void setDrawFailNum(String drawFailNum) {
		this.drawFailNum = drawFailNum;
	}

	@Basic()
	@Column(name = "starNum")
	public String getStarNum() {
		return starNum;
	}

	public void setStarNum(String starNum) {
		this.starNum = starNum;
	}

	@Basic()
	@Column(name = "refreshNum")
	public String getRefreshNum() {
		return refreshNum;
	}

	public void setRefreshNum(String refreshNum) {
		this.refreshNum = refreshNum;
	}

}