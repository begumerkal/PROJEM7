package com.wyd.service.bean;
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
 * The persistent class for the tab_admin database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name="tab_buffrecord")
public class BuffRecord implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
    private Integer id;
    private String buffName;//buff名称
	private String buffCode;//buff关键字
	private int addType;//增长类型0百分比，1指定数值
	private int quantity;//增长数量
	private Date endtime;//结束时间
	private int surplus;//剩余时间（秒）
	private ConsortiaSkill consortiaSkill;
	private int playerId;
	private int buffType;


    @Id()
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", unique=true, nullable=false, precision=10)
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Basic()
    @Column(name="playerId", precision=10)
    public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	@Basic()
    @Column(name="buffName", length=50)
    public String getBuffName() {
		return buffName;
	}
	public void setBuffName(String buffName) {
		this.buffName = buffName;
	}
	
	@Basic()
    @Column(name="buffCode", length=20)
	public String getBuffCode() {
		return buffCode;
	}
	public void setBuffCode(String buffCode) {
		this.buffCode = buffCode;
	}
	
	@Basic()
    @Column(name="addType", precision=10)
	public int getAddType() {
		return addType;
	}
	public void setAddType(int addType) {
		this.addType = addType;
	}
	
	@Basic()
    @Column(name="quantity", precision=10)
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Basic()
    @Column(name="endtime")
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@Basic()
    @Column(name="surplus", precision=10)
	public int getSurplus() {
		return surplus;
	}
	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}
	
	@Basic()
    @Column(name="buffType", precision=10)
	 public int getBuffType() {
		return buffType;
	}
	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}
	//bi-directional many-to-one association to Admin
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="skillId", referencedColumnName="id")
	public ConsortiaSkill getConsortiaSkill() {
		return consortiaSkill;
	}
	public void setConsortiaSkill(ConsortiaSkill consortiaSkill) {
		this.consortiaSkill = consortiaSkill;
	}
	
	public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BuffRecord)) {
            return false;
        }
        BuffRecord castOther = (BuffRecord)other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }
    
	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }   

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }
}