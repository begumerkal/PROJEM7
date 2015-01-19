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
@Table(name = "tab_bossmap_buff")
public class BossmapBuff implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int group;
	private String name;
	private String icon;
	private int type;// 类型0攻击加成,1回血,2无敌,3金币,4冰冻,5恢复怒气
	private int effect1;
	private int effect2;
	private int num;// 有效回合
	private int realityPercent;// 概率

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
	@Column(name = "buff_group")
	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Basic()
	@Column(name = "buff_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "buff_icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Basic()
	@Column(name = "buff_type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	@Basic()
	@Column(name = "num")
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Basic()
	@Column(name = "reality_percent")
	public int getRealityPercent() {
		return realityPercent;
	}

	public void setRealityPercent(int realityPercent) {
		this.realityPercent = realityPercent;
	}

}
