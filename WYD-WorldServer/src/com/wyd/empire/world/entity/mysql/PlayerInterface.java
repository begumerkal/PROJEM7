package com.wyd.empire.world.entity.mysql;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the log_playerinterface database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "log_playerinterface")
public class PlayerInterface implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date enterTime;
	private Date leaveTime;
	private Player player;
	private Interface interfaces;

	public PlayerInterface() {
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
	@Column(name = "enterTime")
	public Date getEnterTime() {
		return this.enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	@Basic()
	@Column(name = "leaveTime")
	public Date getLeaveTime() {
		return this.leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	// bi-directional many-to-one association to Player
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "playerId", referencedColumnName = "id")
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	// bi-directional many-to-one association to TabInterface
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interfaceId", referencedColumnName = "id")
	public Interface getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Interface interfaces) {
		this.interfaces = interfaces;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlayerInterface)) {
			return false;
		}
		PlayerInterface castOther = (PlayerInterface) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}