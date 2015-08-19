package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3ModelPosition entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_model_position", catalog = "game3")
public class Base3ModelPosition implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer modelId;
	private String modelName;
	private Integer type;
	private String translate;
	private String rota;
	private Float scale;

	// Constructors

	/** default constructor */
	public Base3ModelPosition() {
	}

	/** full constructor */
	public Base3ModelPosition(Integer id, Integer modelId, String modelName,
			Integer type, String translate, String rota, Float scale) {
		this.id = id;
		this.modelId = modelId;
		this.modelName = modelName;
		this.type = type;
		this.translate = translate;
		this.rota = rota;
		this.scale = scale;
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

	@Column(name = "model_id", nullable = false)
	public Integer getModelId() {
		return this.modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	@Column(name = "model_name", nullable = false, length = 32)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "translate", nullable = false, length = 32)
	public String getTranslate() {
		return this.translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

	@Column(name = "rota", nullable = false, length = 32)
	public String getRota() {
		return this.rota;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}

	@Column(name = "scale", nullable = false, precision = 12, scale = 0)
	public Float getScale() {
		return this.scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

}