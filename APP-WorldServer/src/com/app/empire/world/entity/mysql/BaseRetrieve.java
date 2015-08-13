package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseRetrieve entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_retrieve", catalog = "game3")
public class BaseRetrieve implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer moduleId;
	private Integer copyId;
	private Integer needGold;
	private Integer needDiamond;
	private String retrieveA;
	private String retrieveB;

	// Constructors

	/** default constructor */
	public BaseRetrieve() {
	}

	/** full constructor */
	public BaseRetrieve(Integer id, Integer moduleId, Integer copyId,
			Integer needGold, Integer needDiamond, String retrieveA,
			String retrieveB) {
		this.id = id;
		this.moduleId = moduleId;
		this.copyId = copyId;
		this.needGold = needGold;
		this.needDiamond = needDiamond;
		this.retrieveA = retrieveA;
		this.retrieveB = retrieveB;
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

	@Column(name = "module_id", nullable = false)
	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "copy_id", nullable = false)
	public Integer getCopyId() {
		return this.copyId;
	}

	public void setCopyId(Integer copyId) {
		this.copyId = copyId;
	}

	@Column(name = "need_gold", nullable = false)
	public Integer getNeedGold() {
		return this.needGold;
	}

	public void setNeedGold(Integer needGold) {
		this.needGold = needGold;
	}

	@Column(name = "need_diamond", nullable = false)
	public Integer getNeedDiamond() {
		return this.needDiamond;
	}

	public void setNeedDiamond(Integer needDiamond) {
		this.needDiamond = needDiamond;
	}

	@Column(name = "retrieve_a", nullable = false, length = 500)
	public String getRetrieveA() {
		return this.retrieveA;
	}

	public void setRetrieveA(String retrieveA) {
		this.retrieveA = retrieveA;
	}

	@Column(name = "retrieve_b", nullable = false, length = 500)
	public String getRetrieveB() {
		return this.retrieveB;
	}

	public void setRetrieveB(String retrieveB) {
		this.retrieveB = retrieveB;
	}

}