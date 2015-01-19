package com.wyd.service.bean;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 世界杯积分
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_points")
public class WorldCupPoints implements Serializable {
    // default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
    private Integer id;
    private String accountId;
    private int points;//积分
    private int useDiam;//消耗的钻石数
    
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
    @Column(name = "accountId",length=25)
    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    @Basic()
    @Column(name = "points")
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	@Basic()
    @Column(name = "useDiam")
	public int getUseDiam() {
		return useDiam;
	}

	public void setUseDiam(int useDiam) {
		this.useDiam = useDiam;
	}
}