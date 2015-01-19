package com.wyd.empire.gameaccount.bean;
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
 * The persistent class for the tab_empireaccount database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_empireaccount")
public class Empireaccount implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long  serialVersionUID = 1L;
    private Integer            id;
    private Integer            accountId;
    private String             name;
    private String             clientModel;
    private String             version;
    private java.sql.Timestamp createTime;
    private Integer            totalLoginTimes;
    private java.sql.Timestamp lastLoginTime;
    private String             ipAddress;
    private String             serverid;
    private Byte               status;
    private Integer            onLineTime;
    private Integer            maxLevel;
    private Integer            machinecode;
    private Integer            channel;
    private String             systemName;
    private String             systemVersion;

    public Empireaccount() {
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
    @Column(name = "createTime", nullable = false)
    public java.sql.Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic()
    @Column(name = "onLineTime", precision = 10)
    public Integer getOnLineTime() {
        return this.onLineTime;
    }

    public void setOnLineTime(Integer onLineTime) {
        this.onLineTime = onLineTime;
    }

    @Basic()
    @Column(name = "lastLoginTime")
    public java.sql.Timestamp getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(java.sql.Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic()
    @Column(name = "totalLoginTimes", precision = 10)
    public Integer getTotalLoginTimes() {
        return this.totalLoginTimes;
    }

    public void setTotalLoginTimes(Integer totalLoginTimes) {
        this.totalLoginTimes = totalLoginTimes;
    }

    @Basic()
    @Column(name = "clientModel", length = 255)
    public String getClientModel() {
        return this.clientModel;
    }

    public void setClientModel(String clientModel) {
        this.clientModel = clientModel;
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
    @Column(name = "serverid", length = 255)
    public String getServerid() {
        return this.serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    @Basic()
    @Column(name = "ipAddress", length = 255)
    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
    @Column(name = "maxLevel", nullable = false, precision = 10)
    public Integer getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Basic()
    @Column(name = "version", length = 255)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
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
    @Column(name = "machinecode", precision = 10)
    public Integer getMachinecode() {
        return machinecode;
    }

    public void setMachinecode(Integer machinecode) {
        this.machinecode = machinecode;
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
    @Column(name = "systemName", length = 50)
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Basic()
    @Column(name = "systemVersion", length = 50)
    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Empireaccount)) {
            return false;
        }
        Empireaccount castOther = (Empireaccount) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}