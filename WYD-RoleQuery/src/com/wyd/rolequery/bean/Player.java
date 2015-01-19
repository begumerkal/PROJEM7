package com.wyd.rolequery.bean;
import java.io.Serializable;
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
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private Integer           accountId;
    private Integer           level;
    private String            name;
    private Byte              status;
    private String            mac;

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
    @Column(name = "level", precision = 10)
    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
    @Column(name = "status", precision = 3)
    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic()
    @Column(name = "mac", length = 100)
    public String getMac() {
        if (null == mac) {
            return "";
        } else {
            return mac;
        }
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}