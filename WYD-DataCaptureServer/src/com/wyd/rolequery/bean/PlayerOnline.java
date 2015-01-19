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
 * The persistent class for the log_playeronline database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "log_playeronline")
public class PlayerOnline implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private String            ipSource;
    private Date              offTime;
    private Date              onTime;
    private Integer           rep;
    private Player            player;
    private Integer			  areaId;

    public PlayerOnline() {
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

    @Basic()
    @Column(name = "ipSource", length = 20)
    public String getIpSource() {
        return this.ipSource;
    }

    public void setIpSource(String ipSource) {
        this.ipSource = ipSource;
    }

    @Basic()
    @Column(name = "offtime")
    public Date getOffTime() {
        return offTime;
    }

    public void setOffTime(Date offTime) {
        this.offTime = offTime;
    }

    @Basic()
    @Column(name = "ontime")
    public Date getOnTime() {
        return onTime;
    }

    public void setOnTime(Date onTime) {
        this.onTime = onTime;
    }

    @Basic()
    @Column(name = "rep", precision = 10)
    public Integer getRep() {
        return this.rep;
    }

    public void setRep(Integer rep) {
        this.rep = rep;
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
    @Column(name = "areaId", precision = 4)
    public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PlayerOnline)) {
            return false;
        }
        PlayerOnline castOther = (PlayerOnline) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}