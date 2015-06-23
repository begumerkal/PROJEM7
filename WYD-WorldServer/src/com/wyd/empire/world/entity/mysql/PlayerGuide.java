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
 * The persistent class for the tab_application database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_player_guide")
public class PlayerGuide implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int playerId;
	private int guide;
	private int step; // 步骤

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
	@Column(name = "player_id", precision = 10)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "guide_id", precision = 10)
	public int getGuide() {
		return this.guide;
	}

	public void setGuide(int guide) {
		this.guide = guide;
	}

	@Basic()
	@Column(name = "step", precision = 4)
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
}