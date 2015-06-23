package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

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
 * The persistent class for the tab_adminandapp database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_adminandapp")
public class AdminAndApp implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Admin admin;
	private Application application;

	public AdminAndApp() {
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

	// bi-directional many-to-one association to Admin
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adminId", referencedColumnName = "id")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	// bi-directional many-to-one association to Admin
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appId", referencedColumnName = "id")
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdminAndApp)) {
			return false;
		}
		AdminAndApp castOther = (AdminAndApp) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}