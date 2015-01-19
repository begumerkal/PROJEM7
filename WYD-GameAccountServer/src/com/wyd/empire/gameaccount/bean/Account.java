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
 * The persistent class for the tab_account database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_account")
public class Account implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// default serial version id, required for serializable classes.
    private Integer            id;
    private String             udid;
    private String             username;
    private String             password;
    private Integer            status;
    private java.sql.Timestamp createtime;
    private String             name;
    private String             idnumber;
    private Byte               sex;
    private String             job;
    private String             phone;
    private String             address;
    private String             interest;
    private String			   token;
    private String   		   areaId;

	public Account() {
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
    @Column(name = "address", length = 255)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic()
    @Column(name = "createtime", nullable = false)
    public java.sql.Timestamp getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(java.sql.Timestamp createtime) {
        this.createtime = createtime;
    }

    @Basic()
    @Column(name = "idnumber", length = 20)
    public String getIdnumber() {
        return this.idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    @Basic()
    @Column(name = "interest", length = 255)
    public String getInterest() {
        return this.interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @Basic()
    @Column(name = "job", length = 255)
    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
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
    @Column(name = "password", length = 255)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic()
    @Column(name = "phone", length = 255)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic()
    @Column(name = "sex", precision = 3)
    public Byte getSex() {
        return this.sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    @Basic()
    @Column(name = "status", nullable = false, length = 1)
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic()
    @Column(name = "username", length = 255)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
	
    @Basic()
    @Column(name = "udid", length = 40)
	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	@Basic()
    @Column(name = "token", length = 100)
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Basic()
    @Column(name = "areaId", length = 20)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Account)) {
            return false;
        }
        Account castOther = (Account) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}