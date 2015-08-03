package com.wyd.empire.world.entity.mysql;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Base2Npc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base2_npc", catalog = "game3", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Base2Npc implements java.io.Serializable {

	// Fields

	private Base2NpcId id;

	// Constructors

	/** default constructor */
	public Base2Npc() {
	}

	/** full constructor */
	public Base2Npc(Base2NpcId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "id", unique = true, nullable = false)),
			@AttributeOverride(name = "name", column = @Column(name = "name", nullable = false, length = 12)),
			@AttributeOverride(name = "nickname", column = @Column(name = "nickname", nullable = false, length = 25)),
			@AttributeOverride(name = "showId", column = @Column(name = "show_id", nullable = false, length = 4)),
			@AttributeOverride(name = "incity", column = @Column(name = "incity", nullable = false)),
			@AttributeOverride(name = "sex", column = @Column(name = "sex", nullable = false)),
			@AttributeOverride(name = "lv", column = @Column(name = "lv", nullable = false)),
			@AttributeOverride(name = "color", column = @Column(name = "color", nullable = false)),
			@AttributeOverride(name = "dialogue", column = @Column(name = "dialogue", nullable = false, length = 125)),
			@AttributeOverride(name = "type", column = @Column(name = "type", nullable = false)),
			@AttributeOverride(name = "action", column = @Column(name = "action", nullable = false)),
			@AttributeOverride(name = "invisibleFall", column = @Column(name = "invisible_fall", nullable = false)),
			@AttributeOverride(name = "rota", column = @Column(name = "rota", nullable = false, length = 64)),
			@AttributeOverride(name = "pos", column = @Column(name = "pos", nullable = false, length = 64)),
			@AttributeOverride(name = "jumpSceneId", column = @Column(name = "jump_scene_id", nullable = false)),
			@AttributeOverride(name = "iconName", column = @Column(name = "icon_name", nullable = false, length = 32)),
			@AttributeOverride(name = "iconOffset", column = @Column(name = "icon_offset", nullable = false, precision = 12, scale = 0)) })
	public Base2NpcId getId() {
		return this.id;
	}

	public void setId(Base2NpcId id) {
		this.id = id;
	}

}