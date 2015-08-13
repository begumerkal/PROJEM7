package com.app.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base3LoadingTips entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base3_loading_tips", catalog = "game3")
public class Base3LoadingTips implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer sceneType;
	private String tips;
	private String pictureName;

	// Constructors

	/** default constructor */
	public Base3LoadingTips() {
	}

	/** full constructor */
	public Base3LoadingTips(Integer id, Integer sceneType, String tips,
			String pictureName) {
		this.id = id;
		this.sceneType = sceneType;
		this.tips = tips;
		this.pictureName = pictureName;
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

	@Column(name = "scene_type", nullable = false)
	public Integer getSceneType() {
		return this.sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

	@Column(name = "tips", nullable = false, length = 512)
	public String getTips() {
		return this.tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Column(name = "picture_name", nullable = false, length = 126)
	public String getPictureName() {
		return this.pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

}