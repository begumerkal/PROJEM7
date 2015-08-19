package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base1MapScene entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base1_map_scene", catalog = "game3")
public class Base1MapScene implements java.io.Serializable {

	// Fields

	private Integer id;
	private String mapResId;
	private Integer type;
	private Integer father;
	private String startX;
	private String name;
	private String condition;
	private String born;
	private String areas;
	private Integer status;
	private String effect;
	private Integer w;
	private Integer h;
	private Integer allowH;
	private Integer music;
	private String info;
	private Integer warMode;
	private Integer lightMap;

	// Constructors

	/** default constructor */
	public Base1MapScene() {
	}

	/** full constructor */
	public Base1MapScene(Integer id, String mapResId, Integer type,
			Integer father, String startX, String name, String condition,
			String born, String areas, Integer status, String effect,
			Integer w, Integer h, Integer allowH, Integer music, String info,
			Integer warMode, Integer lightMap) {
		this.id = id;
		this.mapResId = mapResId;
		this.type = type;
		this.father = father;
		this.startX = startX;
		this.name = name;
		this.condition = condition;
		this.born = born;
		this.areas = areas;
		this.status = status;
		this.effect = effect;
		this.w = w;
		this.h = h;
		this.allowH = allowH;
		this.music = music;
		this.info = info;
		this.warMode = warMode;
		this.lightMap = lightMap;
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

	@Column(name = "map_res_id", nullable = false, length = 256)
	public String getMapResId() {
		return this.mapResId;
	}

	public void setMapResId(String mapResId) {
		this.mapResId = mapResId;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "father", nullable = false)
	public Integer getFather() {
		return this.father;
	}

	public void setFather(Integer father) {
		this.father = father;
	}

	@Column(name = "start_x", nullable = false, length = 100)
	public String getStartX() {
		return this.startX;
	}

	public void setStartX(String startX) {
		this.startX = startX;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "condition", nullable = false, length = 256)
	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name = "born", nullable = false, length = 256)
	public String getBorn() {
		return this.born;
	}

	public void setBorn(String born) {
		this.born = born;
	}

	@Column(name = "areas", nullable = false, length = 256)
	public String getAreas() {
		return this.areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "effect", nullable = false, length = 128)
	public String getEffect() {
		return this.effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	@Column(name = "w", nullable = false)
	public Integer getW() {
		return this.w;
	}

	public void setW(Integer w) {
		this.w = w;
	}

	@Column(name = "h", nullable = false)
	public Integer getH() {
		return this.h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

	@Column(name = "allow_h", nullable = false)
	public Integer getAllowH() {
		return this.allowH;
	}

	public void setAllowH(Integer allowH) {
		this.allowH = allowH;
	}

	@Column(name = "music", nullable = false)
	public Integer getMusic() {
		return this.music;
	}

	public void setMusic(Integer music) {
		this.music = music;
	}

	@Column(name = "info", nullable = false, length = 300)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "WarMode", nullable = false)
	public Integer getWarMode() {
		return this.warMode;
	}

	public void setWarMode(Integer warMode) {
		this.warMode = warMode;
	}

	@Column(name = "LightMap", nullable = false)
	public Integer getLightMap() {
		return this.lightMap;
	}

	public void setLightMap(Integer lightMap) {
		this.lightMap = lightMap;
	}

}