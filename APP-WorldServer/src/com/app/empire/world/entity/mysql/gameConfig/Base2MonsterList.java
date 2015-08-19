package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base2MonsterList entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base2_monster_list", catalog = "game3")
public class Base2MonsterList implements java.io.Serializable {

	// Fields

	private Integer id;
	private String monsterId;
	private String info;

	// Constructors

	/** default constructor */
	public Base2MonsterList() {
	}

	/** full constructor */
	public Base2MonsterList(Integer id, String monsterId, String info) {
		this.id = id;
		this.monsterId = monsterId;
		this.info = info;
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

	@Column(name = "monster_id", nullable = false)
	public String getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(String monsterId) {
		this.monsterId = monsterId;
	}

	@Column(name = "info", nullable = false, length = 10)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}