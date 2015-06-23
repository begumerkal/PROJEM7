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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the tab_consortia database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_consortia")
public class Consortia implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String declaration;
	private String insideNotice;
	private Boolean isAcceptMember;
	private Integer level;
	private Integer money;
	private String name;
	private Integer prestige;
	private Integer rank;
	private Integer totalMember;
	private java.util.Set<PlayerSinConsortia> playerSinConsortias;
	private Player president;
	private Integer winNum;
	private Integer totalNum;
	private Integer hosId;
	private Date hosTime;
	private Integer createId;
	private Date createTime;
	private Integer todayNum;
	private Integer todayWin;

	public Consortia() {
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
	@Column(name = "declaration", length = 200)
	public String getDeclaration() {
		return this.declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	@Basic()
	@Column(name = "insideNotice", length = 200)
	public String getInsideNotice() {
		return this.insideNotice;
	}

	public void setInsideNotice(String insideNotice) {
		this.insideNotice = insideNotice;
	}

	@Basic()
	@Column(name = "isAcceptMember")
	public Boolean getIsAcceptMember() {
		return this.isAcceptMember;
	}

	public void setIsAcceptMember(Boolean isAcceptMember) {
		this.isAcceptMember = isAcceptMember;
	}

	@Basic()
	@Column(name = "level", precision = 10)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "money", precision = 10)
	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Basic()
	@Column(name = "name", length = 16)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "prestige", precision = 10)
	public Integer getPrestige() {
		if (null == this.prestige) {
			return 0;
		} else {
			return this.prestige;
		}
	}

	public void setPrestige(Integer prestige) {
		this.prestige = prestige;
	}

	@Basic()
	@Column(name = "rank", precision = 10)
	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Basic()
	@Column(name = "totalMember", precision = 10)
	public Integer getTotalMember() {
		return this.totalMember;
	}

	public void setTotalMember(Integer totalMember) {
		this.totalMember = totalMember;
	}

	// bi-directional many-to-one association to TabPlayersinconsortia
	@OneToMany(mappedBy = "consortia", fetch = FetchType.LAZY)
	public java.util.Set<PlayerSinConsortia> getPlayerSinConsortias() {
		return this.playerSinConsortias;
	}

	public void setPlayerSinConsortias(java.util.Set<PlayerSinConsortia> playerSinConsortias) {
		this.playerSinConsortias = playerSinConsortias;
	}

	// bi-directional many-to-one association to Player
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "presidentId", referencedColumnName = "id")
	public Player getPresident() {
		return president;
	}

	public void setPresident(Player president) {
		this.president = president;
	}

	@Basic()
	@Column(name = "winNum", precision = 10)
	public Integer getWinNum() {
		return winNum;
	}

	public void setWinNum(Integer winNum) {
		this.winNum = winNum;
	}

	@Basic()
	@Column(name = "totalNum", precision = 10)
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	@Basic()
	@Column(name = "hosId", precision = 10)
	public Integer getHosId() {
		return hosId;
	}

	public void setHosId(Integer hosId) {
		this.hosId = hosId;
	}

	@Basic()
	@Column(name = "hosTime")
	public Date getHosTime() {
		return hosTime;
	}

	public void setHosTime(Date hosTime) {
		this.hosTime = hosTime;
	}

	@Basic()
	@Column(name = "createId", precision = 10)
	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	@Basic()
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic()
	@Column(name = "todayNum", precision = 10)
	public Integer getTodayNum() {
		return todayNum;
	}

	public void setTodayNum(Integer todayNum) {
		this.todayNum = todayNum;
	}

	@Basic()
	@Column(name = "todayWin", precision = 10)
	public Integer getTodayWin() {
		return todayWin;
	}

	public void setTodayWin(Integer todayWin) {
		this.todayWin = todayWin;
	}

	public void check() {
		declaration = null == declaration ? "" : declaration;
		insideNotice = null == insideNotice ? "" : insideNotice;
		isAcceptMember = null == isAcceptMember ? true : isAcceptMember;
		level = null == level ? 1 : level;
		money = null == money ? 0 : money;
		prestige = null == prestige ? 0 : prestige;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Consortia)) {
			return false;
		}
		Consortia castOther = (Consortia) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}