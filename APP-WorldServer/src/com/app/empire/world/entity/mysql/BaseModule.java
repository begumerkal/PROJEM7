package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseModule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_module", catalog = "game3")
public class BaseModule implements java.io.Serializable {

	// Fields

	private Integer id;
	private String moduleName;
	private Integer open;
	private Integer count;
	private String info;
	private Integer order;

	// Constructors

	/** default constructor */
	public BaseModule() {
	}

	/** full constructor */
	public BaseModule(Integer id, String moduleName, Integer open,
			Integer count, String info, Integer order) {
		this.id = id;
		this.moduleName = moduleName;
		this.open = open;
		this.count = count;
		this.info = info;
		this.order = order;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "module_name", nullable = false, length = 10)
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "open", nullable = false)
	public Integer getOpen() {
		return this.open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	@Column(name = "count", nullable = false)
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "info", nullable = false, length = 300)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "order", nullable = false)
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}