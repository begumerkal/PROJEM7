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

@Entity()
@Table(name = "tab_promotions")
public class Promotions implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ShopItem shopItem;
	private Integer quantity;
	private String isActivate;
	private Integer discount;
	private Integer gold;
	private Integer count;
	private Integer days;
	/** 数量限制对象，1：个人，2：全服 */
	private Integer personal;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// bi-directional many-to-one association to ShopItem
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopItemId", referencedColumnName = "id")
	public ShopItem getShopItem() {
		return this.shopItem;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	@Basic()
	@Column(name = "quantity", precision = 10)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Basic()
	@Column(name = "is_activate", length = 11)
	public String getIsActivate() {
		if (null == isActivate) {
			return "N";
		} else {
			return isActivate;
		}
	}

	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate;
	}

	@Basic()
	@Column(name = "discount")
	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	@Basic()
	@Column(name = "gold", length = 11)
	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	@Basic()
	@Column(name = "count", length = 11)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Basic()
	@Column(name = "days", length = 11)
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Basic()
	@Column(name = "personal", length = 11)
	public Integer getPersonal() {
		return personal;
	}

	public void setPersonal(Integer personal) {
		this.personal = personal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Promotions)) {
			return false;
		}
		Promotions castOther = (Promotions) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}
