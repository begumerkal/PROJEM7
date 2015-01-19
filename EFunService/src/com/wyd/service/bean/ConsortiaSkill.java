package com.wyd.service.bean;
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
@Table(name="tab_consortiaskill")
public class ConsortiaSkill implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer comLv;
    private String comSkillName;
    private String icon;
    private String desc;
    private Integer costUseTickets;
    private Integer costUseGold;
    private Integer type;
    private Integer param;
    private Integer costUseContribution;


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
	@Column(name="com_lv", precision=10)
	public Integer getComLv() {
		return comLv;
	}
	public void setComLv(Integer comLv) {
		this.comLv = comLv;
	}
	
	@Basic()
	@Column(name="com_skill_name", length=255)
	public String getComSkillName() {
		return comSkillName;
	}
	public void setComSkillName(String comSkillName) {
		this.comSkillName = comSkillName;
	}
	
	@Basic()
	@Column(name="icon", length=255)
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Basic()
	@Column(name="skill_desc", length=255)
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Basic()
	@Column(name="costUseTickets", precision=10)
	public Integer getCostUseTickets() {
		return costUseTickets;
	}
	public void setCostUseTickets(Integer costUseTickets) {
		this.costUseTickets = costUseTickets;
	}
	
	@Basic()
	@Column(name="costUseGold", precision=10)
	public Integer getCostUseGold() {
		return costUseGold;
	}
	public void setCostUseGold(Integer costUseGold) {
		this.costUseGold = costUseGold;
	}
	
	@Basic()
	@Column(name="type", precision=4)
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Basic()
	@Column(name="param", precision=10)
	public Integer getParam() {
		return param;
	}
	public void setParam(Integer param) {
		this.param = param;
	}
	
	@Basic()
	@Column(name="costUseContribution", precision=10)
	public Integer getCostUseContribution() {
		return costUseContribution;
	}
	public void setCostUseContribution(Integer costUseContribution) {
		this.costUseContribution = costUseContribution;
	}
	
	public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConsortiaSkill)) {
            return false;
        }
        ConsortiaSkill castOther = (ConsortiaSkill)other;
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