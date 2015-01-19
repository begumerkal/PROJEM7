package com.wyd.rolequery.bean;
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
 * The persistent class for the tab_fee database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "log_playerbill")
public class PlayerBill implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private Player            player;
    private Date              createTime;
    private Integer           amount;
    private Integer           origin;
    private String            remark;
    private Float             chargePrice;
    private String            orderNum;
    private String            isFirstRecharge;
    private String            channelId;
    private String            cardType;

    public PlayerBill() {
    	player = new Player();
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

    // bi-directional many-to-one association to Player
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerId", referencedColumnName = "id", nullable = false)
    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Basic()
    @Column(name = "createTime")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic()
    @Column(name = "amount", precision = 10)
    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic()
    @Column(name = "origin", precision = 10)
    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    @Basic()
    @Column(name = "remark", length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic()
    @Column(name = "chargePrice", precision = 10)
    public Float getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(Float chargePrice) {
        this.chargePrice = chargePrice;
    }

    @Basic()
    @Column(name = "orderNum", length = 100)
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Basic()
    @Column(name = "is_first_recharge")
    public String getIsFirstRecharge() {
        return isFirstRecharge;
    }

    public void setIsFirstRecharge(String isFirstRecharge) {
        this.isFirstRecharge = isFirstRecharge;
    }

    @Basic()
    @Column(name = "channel_id", length = 10)
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Basic()
    @Column(name = "card_type", length = 20)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PlayerBill)) {
            return false;
        }
        PlayerBill castOther = (PlayerBill) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}