package com.wyd.serial.bean;
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
 * The persistent class for the apps database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name="apps")
public class App implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private java.sql.Timestamp createdAt;
	private byte disable;
	private String nameChinese;
	private String nameEnglish;
	private String notifyUrl;
	private String remark;
    private Integer scale;
    private String  token;
    private String companyName;
    private String servicesPhone;
    private String payExplain;
    private String cpId;
    private String cpServiceId;
    private String gameId;
    private String notifyIp;
	private java.sql.Timestamp updatedAt;
    
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
	@Column(name="created_at")
	public java.sql.Timestamp getCreatedAt() {
		return this.createdAt;
	}
	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Basic()
	@Column(name="disable", nullable=false)
	public byte getDisable() {
		return this.disable;
	}
	public void setDisable(byte disable) {
		this.disable = disable;
	}

	@Basic()
	@Column(name="name_chinese", unique=true, nullable=false, length=15)
	public String getNameChinese() {
		return this.nameChinese;
	}
	public void setNameChinese(String nameChinese) {
		this.nameChinese = nameChinese;
	}

	@Basic()
	@Column(name="name_english", unique=true, nullable=false, length=15)
	public String getNameEnglish() {
		return this.nameEnglish;
	}
	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	@Basic()
	@Column(name="notify_url", length=256)
	public String getNotifyUrl() {
		return this.notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Basic()
	@Column(name="remark", length=80)
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Basic()
	@Column(name="updated_at")
	public java.sql.Timestamp getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(java.sql.Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
    @Column(name="company_name", length=50)
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    @Column(name="services_phone", length=20)
    public String getServicesPhone() {
        return this.servicesPhone;
    }
    
    public void setServicesPhone(String servicesPhone) {
        this.servicesPhone = servicesPhone;
    }
    
    @Column(name="pay_explain")
    public String getPayExplain() {
        return this.payExplain;
    }
    
    public void setPayExplain(String payExplain) {
        this.payExplain = payExplain;
    }
    
    @Column(name="cp_id", length=30)
    public String getCpId() {
        return this.cpId;
    }
    
    public void setCpId(String cpId) {
        this.cpId = cpId;
    }
    
    @Column(name="cp_serviceID", length=30)
    public String getCpServiceId() {
        return this.cpServiceId;
    }
    
    public void setCpServiceId(String cpServiceId) {
        this.cpServiceId = cpServiceId;
    }

    @Basic()
    @Column(name="i_game_id", length=20)
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Basic()
    @Column(name="i_notify_ip", length=255)
    public String getNotifyIp() {
        return notifyIp;
    }

    public void setNotifyIp(String notifyIp) {
        this.notifyIp = notifyIp;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof App)) {
            return false;
        }
        App castOther = (App)other;
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