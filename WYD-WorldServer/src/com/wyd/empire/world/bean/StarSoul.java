package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_active_reward database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_star_soul")
public class StarSoul implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id; // 星魂等级
	private String peculiarityValue;// '特殊属性值（玩家属性类型:增加数值:碎片消费值:金币消费值|玩家属性类型:增加数值:碎片消费值:金币消费值）',
	private Integer playerLeve; // 等级要求

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ss_id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "peculiarity_value", length = 1000)
	public String getPeculiarityValue() {
		return peculiarityValue;
	}

	public void setPeculiarityValue(String peculiarityValue) {
		this.peculiarityValue = peculiarityValue;
	}

	@Basic()
	@Column(name = "player_level", length = 3)
	public Integer getPlayerLeve() {
		return playerLeve;
	}

	public void setPlayerLeve(Integer playerLeve) {
		this.playerLeve = playerLeve;
	}

}