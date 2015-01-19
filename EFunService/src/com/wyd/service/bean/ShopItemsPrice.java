package com.wyd.service.bean;
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
 * The persistent class for the tab_shopitemsprice database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_shopitemsprice")
public class ShopItemsPrice implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private Byte              costType;
    private Integer           costUseGold;
    private Integer           costUseTickets;
    private Integer           count;
    private Integer           days;
    private ShopItem          shopItem;

    public ShopItemsPrice() {
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
    @Column(name = "costType", precision = 3)
    public Byte getCostType() {
        return this.costType;
    }

    public void setCostType(Byte costType) {
        this.costType = costType;
    }

    @Basic()
    @Column(name = "costUseGold", precision = 10)
    public Integer getCostUseGold() {
        return this.costUseGold;
    }

    public void setCostUseGold(Integer costUseGold) {
        this.costUseGold = costUseGold;
    }

    @Basic()
    @Column(name = "costUseTickets", precision = 10)
    public Integer getCostUseTickets() {
        return this.costUseTickets;
    }

    public void setCostUseTickets(Integer costUseTickets) {
        this.costUseTickets = costUseTickets;
    }

    @Basic()
    @Column(name = "count", precision = 10)
    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic()
    @Column(name = "days", precision = 10)
    public Integer getDays() {
        return this.days;
    }

    public void setDays(Integer days) {
        this.days = days;
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

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ShopItemsPrice)) {
            return false;
        }
        ShopItemsPrice castOther = (ShopItemsPrice) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}