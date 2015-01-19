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
 * The persistent class for the tab_recharge database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_recharge")
public class Recharge implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String commodityId;
	private String icon;
	private int number;
	private int giftNumber;
	private Float price;
	// private String firstCharge;
	// private String moreCharge;
	// private String firstChargeRemark;
	// private String moreChargeRemark;
	private int channelId;
	private int rate; // 比例，万分比

	public Recharge() {
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
	@Column(name = "commodityId", nullable = false, length = 100)
	public String getCommodityId() {
		return this.commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	@Basic()
	@Column(name = "icon", nullable = false, length = 100)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Basic()
	@Column(name = "number", nullable = false, precision = 10)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Basic()
	@Column(name = "giftNumber", precision = 10)
	public int getGiftNumber() {
		return giftNumber;
	}

	public void setGiftNumber(int giftNumber) {
		this.giftNumber = giftNumber;
	}

	@Basic()
	@Column(name = "price", nullable = false, precision = 10)
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	// @Basic()
	// @Column(name = "first_charge", length = 255)
	// public String getFirstCharge() {
	// return firstCharge;
	// }
	//
	// public void setFirstCharge(String firstCharge) {
	// this.firstCharge = firstCharge;
	// }
	//
	// @Basic()
	// @Column(name = "more_charge", length = 255)
	// public String getMoreCharge() {
	// return moreCharge;
	// }
	//
	// public void setMoreCharge(String moreCharge) {
	// this.moreCharge = moreCharge;
	// }
	//
	// @Basic()
	// @Column(name = "first_charge_remark", length = 255)
	// public String getFirstChargeRemark() {
	// return firstChargeRemark;
	// }
	//
	// public void setFirstChargeRemark(String firstChargeRemark) {
	// this.firstChargeRemark = firstChargeRemark;
	// }
	//
	// @Basic()
	// @Column(name = "more_charge_remark", length = 255)
	// public String getMoreChargeRemark() {
	// return moreChargeRemark;
	// }
	//
	// public void setMoreChargeRemark(String moreChargeRemark) {
	// this.moreChargeRemark = moreChargeRemark;
	// }

	@Basic()
	@Column(name = "channel_id", precision = 10)
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	@Basic()
	@Column(name = "rate", precision = 10)
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Recharge)) {
			return false;
		}
		Recharge castOther = (Recharge) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}