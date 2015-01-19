package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_successrate")
public class Successrate implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer gold;
	private Integer addpower;
	private Integer stone1;
	private Integer stone2;
	private Integer stone3;
	private Integer stone4;
	private Integer stone5;
	private Integer addhead;
	private Integer addface;
	private Integer addbody;
	private Integer addwing;
	private Integer stone1real;
	private Integer stone2real;
	private Integer stone3real;
	private Integer stone4real;
	private Integer stone5real;
	private Integer missTimes;
	private Integer stone1Used;
	private Integer stone2Used;
	private Integer stone3Used;
	private Integer stone4Used;
	private Integer stone5Used;
	private Integer stone1realUsed;
	private Integer stone2realUsed;
	private Integer stone3realUsed;
	private Integer stone4realUsed;
	private Integer stone5realUsed;
	private int addring;
	private int addnecklace;

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
	@Column(name = "gold", precision = 10)
	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	@Basic()
	@Column(name = "addpower", precision = 10)
	public Integer getAddpower() {
		return addpower;
	}

	public void setAddpower(Integer addpower) {
		this.addpower = addpower;
	}

	@Basic()
	@Column(name = "stone1", precision = 10)
	public Integer getStone1() {
		return stone1;
	}

	public void setStone1(Integer stone1) {
		this.stone1 = stone1;
	}

	@Basic()
	@Column(name = "stone2", precision = 10)
	public Integer getStone2() {
		return stone2;
	}

	public void setStone2(Integer stone2) {
		this.stone2 = stone2;
	}

	@Basic()
	@Column(name = "stone3", precision = 10)
	public Integer getStone3() {
		return stone3;
	}

	public void setStone3(Integer stone3) {
		this.stone3 = stone3;
	}

	@Basic()
	@Column(name = "stone4", precision = 10)
	public Integer getStone4() {
		return stone4;
	}

	public void setStone4(Integer stone4) {
		this.stone4 = stone4;
	}

	@Basic()
	@Column(name = "stone5", precision = 10)
	public Integer getStone5() {
		return stone5;
	}

	public void setStone5(Integer stone5) {
		this.stone5 = stone5;
	}

	@Basic()
	@Column(name = "addhead", precision = 10)
	public Integer getAddhead() {
		return addhead;
	}

	public void setAddhead(Integer addhead) {
		this.addhead = addhead;
	}

	@Basic()
	@Column(name = "addface", precision = 10)
	public Integer getAddface() {
		return addface;
	}

	public void setAddface(Integer addface) {
		this.addface = addface;
	}

	@Basic()
	@Column(name = "addbody", precision = 10)
	public Integer getAddbody() {
		return addbody;
	}

	public void setAddbody(Integer addbody) {
		this.addbody = addbody;
	}

	@Basic()
	@Column(name = "addwing", precision = 10)
	public Integer getAddwing() {
		return addwing;
	}

	public void setAddwing(Integer addwing) {
		this.addwing = addwing;
	}

	@Basic()
	@Column(name = "stone2_real", precision = 10)
	public Integer getStone2real() {
		return stone2real;
	}

	public void setStone2real(Integer stone2real) {
		this.stone2real = stone2real;
	}

	@Basic()
	@Column(name = "stone3_real", precision = 10)
	public Integer getStone3real() {
		return stone3real;
	}

	public void setStone3real(Integer stone3real) {
		this.stone3real = stone3real;
	}

	@Basic()
	@Column(name = "stone4_real", precision = 10)
	public Integer getStone4real() {
		return stone4real;
	}

	public void setStone4real(Integer stone4real) {
		this.stone4real = stone4real;
	}

	@Basic()
	@Column(name = "stone5_real", precision = 10)
	public Integer getStone5real() {
		return stone5real;
	}

	public void setStone5real(Integer stone5real) {
		this.stone5real = stone5real;
	}

	@Basic()
	@Column(name = "miss_times", precision = 10)
	public Integer getMissTimes() {
		return missTimes;
	}

	public void setMissTimes(Integer missTimes) {
		this.missTimes = missTimes;
	}

	@Basic()
	@Column(name = "stone1_used", precision = 10)
	public Integer getStone1Used() {
		return stone1Used;
	}

	public void setStone1Used(Integer stone1Used) {
		this.stone1Used = stone1Used;
	}

	@Basic()
	@Column(name = "stone2_used", precision = 10)
	public Integer getStone2Used() {
		return stone2Used;
	}

	public void setStone2Used(Integer stone2Used) {
		this.stone2Used = stone2Used;
	}

	@Basic()
	@Column(name = "stone3_used", precision = 10)
	public Integer getStone3Used() {
		return stone3Used;
	}

	public void setStone3Used(Integer stone3Used) {
		this.stone3Used = stone3Used;
	}

	@Basic()
	@Column(name = "stone4_used", precision = 10)
	public Integer getStone4Used() {
		return stone4Used;
	}

	public void setStone4Used(Integer stone4Used) {
		this.stone4Used = stone4Used;
	}

	@Basic()
	@Column(name = "stone5_used", precision = 10)
	public Integer getStone5Used() {
		return stone5Used;
	}

	public void setStone5Used(Integer stone5Used) {
		this.stone5Used = stone5Used;
	}

	@Basic()
	@Column(name = "stone2_real_used", precision = 10)
	public Integer getStone2realUsed() {
		return stone2realUsed;
	}

	public void setStone2realUsed(Integer stone2realUsed) {
		this.stone2realUsed = stone2realUsed;
	}

	@Basic()
	@Column(name = "stone3_real_used", precision = 10)
	public Integer getStone3realUsed() {
		return stone3realUsed;
	}

	public void setStone3realUsed(Integer stone3realUsed) {
		this.stone3realUsed = stone3realUsed;
	}

	@Basic()
	@Column(name = "stone4_real_used", precision = 10)
	public Integer getStone4realUsed() {
		return stone4realUsed;
	}

	public void setStone4realUsed(Integer stone4realUsed) {
		this.stone4realUsed = stone4realUsed;
	}

	@Basic()
	@Column(name = "stone5_real_used", precision = 10)
	public Integer getStone5realUsed() {
		return stone5realUsed;
	}

	public void setStone5realUsed(Integer stone5realUsed) {
		this.stone5realUsed = stone5realUsed;
	}

	@Basic()
	@Column(name = "stone1_real", precision = 10)
	public Integer getStone1real() {
		return stone1real;
	}

	public void setStone1real(Integer stone1real) {
		this.stone1real = stone1real;
	}

	@Basic()
	@Column(name = "stone1_real_used", precision = 10)
	public Integer getStone1realUsed() {
		return stone1realUsed;
	}

	public void setStone1realUsed(Integer stone1realUsed) {
		this.stone1realUsed = stone1realUsed;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Successrate)) {
			return false;
		}
		Successrate castOther = (Successrate) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Basic()
	@Column(name = "addring", precision = 10)
	public int getAddring() {
		return addring;
	}

	public void setAddring(int addring) {
		this.addring = addring;
	}

	@Basic()
	@Column(name = "addnecklace", precision = 10)
	public int getAddnecklace() {
		return addnecklace;
	}

	public void setAddnecklace(int addnecklace) {
		this.addnecklace = addnecklace;
	}
}