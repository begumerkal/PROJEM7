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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the tab_friend database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_friend")
public class Friend implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean blackList;
	private Boolean privateChat;
	private Player minePlayer;
	private Player otherPlayer;

	public Friend() {
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
	@Column(name = "blackList", precision = 3)
	public Boolean getBlackList() {
		return this.blackList;
	}

	public void setBlackList(Boolean blackList) {
		this.blackList = blackList;
	}

	@Basic()
	@Column(name = "privateChat", precision = 3)
	public Boolean getPrivateChat() {
		return this.privateChat;
	}

	public void setPrivateChat(Boolean privateChat) {
		this.privateChat = privateChat;
	}

	// bi-directional many-to-one association to Player
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "myId", referencedColumnName = "id")
	public Player getMinePlayer() {
		return minePlayer;
	}

	public void setMinePlayer(Player minePlayer) {
		this.minePlayer = minePlayer;
	}

	// bi-directional many-to-one association to Player
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friendId", referencedColumnName = "id")
	public Player getOtherPlayer() {
		return otherPlayer;
	}

	public void setOtherPlayer(Player otherPlayer) {
		this.otherPlayer = otherPlayer;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Friend)) {
			return false;
		}
		Friend castOther = (Friend) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}