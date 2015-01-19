package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "tab_map_event")
public class MapEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private int effect1;
	private int effect2;

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "effect1")
	public int getEffect1() {
		return effect1;
	}

	public void setEffect1(int effect1) {
		this.effect1 = effect1;
	}

	@Basic()
	@Column(name = "effect2")
	public int getEffect2() {
		return effect2;
	}

	public void setEffect2(int effect2) {
		this.effect2 = effect2;
	}

}
