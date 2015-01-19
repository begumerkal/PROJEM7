package com.wyd.service.bean;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 世界杯兑换码
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_code")
public class WorldCupCode implements Serializable {
    // default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
    private Integer id;
    private int goals;//进球数
    private String code;
    private Date sendTime;//发放时间
    private String owner;//拥有人
    
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
    @Column(name = "goals",length=4)
	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}
	@Basic()
    @Column(name = "code",length=50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Basic()
    @Column(name = "sendTime")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@Basic()
    @Column(name = "owner",length=25)
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

   
}