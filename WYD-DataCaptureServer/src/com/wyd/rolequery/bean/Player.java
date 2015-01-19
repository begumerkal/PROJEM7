package com.wyd.rolequery.bean;
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
 * The persistent class for the tab_player database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_player")
public class Player implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    
   private Integer           id; //角色id
   private Integer           accountId;//对应的分区帐号id(tab_empireaccount.id)
   private Integer           areaId;//分区id
   private Integer           channel;//所属渠道号
   private String            name;//角色名称 
   private Date              createTime;//创建时间
  		  

    public Player() {
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
    @Column(name = "accountId", precision = 10)
    public Integer getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Basic()
    @Column(name = "areaId", precision = 10)
    public Integer getAreaId() {
        return this.areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }


    @Basic()
    @Column(name = "channel", precision = 10)
    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    @Basic()
    @Column(name = "name", length = 255)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic()
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}